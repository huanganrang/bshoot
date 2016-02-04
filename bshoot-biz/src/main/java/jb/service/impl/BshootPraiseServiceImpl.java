package jb.service.impl;

import component.redis.model.CounterType;
import component.redis.service.CounterServiceI;
import component.redis.service.FetchValue;
import jb.absx.F;
import jb.dao.BshootDaoI;
import jb.dao.BshootPraiseDaoI;
import jb.model.Tbshoot;
import jb.model.TbshootPraise;
import jb.pageModel.*;
import jb.service.BshootPraiseServiceI;
import jb.service.MessageServiceI;
import jb.service.UserPraiseRecordServiceI;
import jb.service.UserProfileServiceI;
import jb.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.util.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class BshootPraiseServiceImpl extends BaseServiceImpl<BshootPraise> implements BshootPraiseServiceI {

	@Autowired
	private BshootPraiseDaoI bshootPraiseDao;
	@Autowired
	private CounterServiceI counterService;
	@Autowired
	private MessageServiceI messageServiceImpl;
	@Autowired
	private BshootDaoI bshootDao;
	@Autowired
	private UserPraiseRecordServiceI userPraiseRecordService;
	@Autowired
	private UserProfileServiceI userProfileService;

	@Override
	public DataGrid dataGrid(BshootPraise bshootPraise, PageHelper ph) {
		List<BshootPraise> ol = new ArrayList<BshootPraise>();
		String hql = " from TbshootPraise t ";
		DataGrid dg = dataGridQuery(hql, ph, bshootPraise, bshootPraiseDao);
		@SuppressWarnings("unchecked")
		List<TbshootPraise> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TbshootPraise t : l) {
				BshootPraise o = new BshootPraise();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}


	protected String whereHql(BshootPraise bshootPraise, Map<String, Object> params) {
		String whereHql = "";
		if (bshootPraise != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(bshootPraise.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", bshootPraise.getUserId());
			}
			if (!F.empty(bshootPraise.getBshootId())) {
				whereHql += " and t.bshootId = :bshootId";
				params.put("bshootId", bshootPraise.getBshootId());
			}
		}
		return whereHql;
	}

	@Override
	public int add(final BshootPraise bshootPraise, String currentUser) {
		if(get(bshootPraise.getBshootId(), bshootPraise.getUserId())!=null)
			return -1;
		TbshootPraise t = new TbshootPraise();
		MyBeanUtils.copyProperties(bshootPraise, t, new String[] {},true);
		t.setId(UUID.randomUUID().toString());
		bshootPraise.setId(t.getId());
		t.setPraiseDatetime(new Date());
		bshootPraiseDao.save(t);
		//动态的打赏计数+n
		counterService.automicChangeCount(bshootPraise.getBshootId(), CounterType.PRAISE, bshootPraise.getPraiseNum(), new FetchValue() {
			@Override
			public Integer fetchValue() {
				return getCount(bshootPraise.getBshootId()).intValue();
			}
		});
		//被打赏用户打赏数量计数
		counterService.automicChangeCount(bshootPraise.getUserId(), CounterType.PRAISE, bshootPraise.getPraiseNum(), new FetchValue() {
			@Override
			public Integer fetchValue() {
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("bsUserId",bshootPraise.getUserId());
				Long count = bshootPraiseDao.count("select sum(t.praiseNum) from TbshootPraise t where t.bs_userId =:bsUserId)");
				if(null==count) count= 0L;
				UserProfile userProfile = new UserProfile();
				userProfile.setId(bshootPraise.getUserId());
				userProfile.setPraiseNum(count.intValue());
				userProfileService.edit(userProfile);
				return count.intValue();
			}
		});

		Tbshoot bshoot = bshootDao.get(Tbshoot.class,bshootPraise.getBshootId());
		UserPraiseRecord userPraiseRecord = new UserPraiseRecord();
		userPraiseRecord.setPraiseNum(bshootPraise.getPraiseNum());
		userPraiseRecord.setUserId(bshoot.getUserId());
		userPraiseRecord.setPraiseType(UserPraiseRecord.PRAISE_TYPE_01); //打赏获取
		userPraiseRecord.setRelationOutid(t.getId());
		userPraiseRecordService.add(userPraiseRecord);
		userPraiseRecord.setId(null);
		userPraiseRecord.setPraiseNum(-bshootPraise.getPraiseNum());
		userPraiseRecord.setUserId(bshootPraise.getUserId());
		userPraiseRecordService.add(userPraiseRecord);
		messageServiceImpl.addAndSendMessage(MessageServiceI.MT_04,bshoot.getUserId(),bshootPraise.getId(),"用户["+currentUser+"]打赏了您的动态");
		return 1;
	}

	@Override
	public BshootPraise get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TbshootPraise t = bshootPraiseDao.get("from TbshootPraise t  where t.id = :id", params);
		BshootPraise o = new BshootPraise();
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(BshootPraise bshootPraise) {
		TbshootPraise t = bshootPraiseDao.get(TbshootPraise.class, bshootPraise.getId());
		if (t != null) {
			BeanUtils.copyProperties(bshootPraise, t, new String[] { "id" , "createdatetime" });
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		final TbshootPraise t = bshootPraiseDao.get(TbshootPraise.class, id);
		bshootPraiseDao.delete(t);
		//打赏计数-1,如果这里出了错，后面可以通过job去纠正
		counterService.automicChangeCount(t.getBshootId(), CounterType.PRAISE, -1, new FetchValue() {
			@Override
			public Integer fetchValue() {
				return getCount(t.getBshootId()).intValue();
			}
		});
	}


	@Override
	public int deleteBshootPraise(BshootPraise bshootPraise) {
		BshootPraise bc = get(bshootPraise.getBshootId(), bshootPraise.getUserId());
		if(bc==null){
			return -1;
		}else{
			delete(bc.getId());
		}
		return 0;

	}

	private void updateCount(String bshootId,Long sum){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", bshootId);
		params.put("sum", sum);
		bshootDao.executeSql("update bshoot t set t.bs_praise = :sum where t.id=:id", params);
	}

	private Long getCount(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		Long l =  bshootPraiseDao.count("select sum(t.praiseNum) from TbshootPraise t where t.bshootId =:bshootId", params);
		if(l == null) l = 0L;
		updateCount(bshootId,l);
		return l;
	}

	private void updateCountReduce(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		params.put("id", bshootId);
		bshootDao.executeSql("update bshoot t set t.bs_praise = (select count(*)-1 from bshoot_praise b where b.bshoot_id =:bshootId) where t.id=:id", params);
	}

	//分组统计
	@Override
	public Map<String,Integer> countGroup(Date begin, Date end){
		Map<String,Object> params = new HashMap<>();
		params.put("startDate",begin);
		params.put("endDate",end);
		List<Map> result = bshootPraiseDao.findBySql2Map("select t1.bshoot_id as bshootId,sum(t1.bs_praise) as count from bshoot_praise t1 where " +
				"EXISTS(SELECT bshoot_id FROM bshoot_praise t2 " +
				"where t1.bshoot_id=t2.bshoot_id and t1.bs_praise=t2.bs_praise  " +
				"and t2.praise_datetime>=:startDate and t2.praise_datetime<=:endDate) " +
				"GROUP BY t1.bshoot_id", params);
		if(CollectionUtils.isEmpty(result)) return  null;
		Map<String,Integer> countGroup = new HashMap<>();
		for(Map ele:result){
			Set keySet = ele.keySet();
			String bshootId = null;
			Integer count = null;
			for(Iterator it=keySet.iterator();it.hasNext();){
				String key = (String) it.next();
				if(key.equals("bshootId")) bshootId = (String) ele.get(key);
				if(key.equals("count")) count = ((BigDecimal) ele.get(key)).intValue();
			}
			if(null!=bshootId&&null!=count)
			  countGroup.put(bshootId,count);
		}
		return countGroup;
	}

	public BshootPraise get(String bshootId, String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		params.put("userId", userId);
		TbshootPraise t = bshootPraiseDao.get("from TbshootPraise t  where t.bshootId = :bshootId and t.userId = :userId", params);
		if(t==null)return null;
		BshootPraise o = new BshootPraise();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public List<String> mePraiseCommentUser(PraiseCommentRequest praiseCommentRequest) {
		return bshootPraiseDao.mePraiseCommentUser(praiseCommentRequest);
	}

	@Override
	public List<String> friendHasPraisedUser(PraiseCommentRequest praiseCommentRequest) {
		return bshootPraiseDao.friendHasPraisedUser(praiseCommentRequest);
	}

	@Override
	public List<String> singleFriendHasPraisedUser(PraiseCommentRequest praiseCommentRequest) {
		return bshootPraiseDao.singleFriendHasPraisedUser(praiseCommentRequest);
	}

}

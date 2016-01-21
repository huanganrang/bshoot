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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		BeanUtils.copyProperties(bshootPraise, t);
		t.setId(UUID.randomUUID().toString());
		bshootPraise.setId(t.getId());
		//t.setCreatedatetime(new Date());
		t.setPraiseDatetime(new Date());
		bshootPraiseDao.save(t);
		updateCount(bshootPraise.getBshootId());
		//动态的打赏计数+1
		counterService.automicChangeCount(bshootPraise.getBshootId(), CounterType.PRAISE, 1, new FetchValue() {
			@Override
			public Integer fetchValue() {
				return getCount(bshootPraise.getBshootId()).intValue();
			}
		});
		Tbshoot bshoot = bshootDao.get(Tbshoot.class,bshootPraise.getBshootId());
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
		updateCountReduce(t.getBshootId());
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

	private void updateCount(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		params.put("id", bshootId);
		bshootDao.executeSql("update bshoot t set t.bs_praise = (select count(*)+1 from bshoot_praise b where b.bshoot_id =:bshootId) where t.id=:id", params);
	}

	private Long getCount(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		return bshootPraiseDao.count("select count(1) from TbshootPraise t where t.bshootId =:bshootId)", params);
	}

	private void updateCountReduce(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		params.put("id", bshootId);
		bshootDao.executeSql("update bshoot t set t.bs_praise = (select count(*)-1 from bshoot_praise b where b.bshoot_id =:bshootId) where t.id=:id", params);
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

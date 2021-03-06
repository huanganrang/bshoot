package jb.service.impl;

import com.alibaba.fastjson.JSONObject;
import component.ons.service.ComCounterProducer;
import component.redis.model.CounterType;
import component.redis.service.CounterServiceI;
import component.redis.service.FetchValue;
import jb.absx.F;
import jb.dao.BshootCommentDaoI;
import jb.dao.BshootDaoI;
import jb.dao.CommentPraiseDaoI;
import jb.dao.UserDaoI;
import jb.model.Tbshoot;
import jb.model.TbshootComment;
import jb.model.TcommentPraise;
import jb.model.Tuser;
import jb.pageModel.BshootComment;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.BshootCommentServiceI;
import jb.service.CounterHandler;
import jb.service.MessageServiceI;
import jb.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class BshootCommentServiceImpl extends BaseServiceImpl<BshootComment> implements BshootCommentServiceI,CounterHandler,InitializingBean {

	@Autowired
	private BshootCommentDaoI bshootCommentDao;
	
	@Autowired
	private CommentPraiseDaoI commentPraiseDao;
	@Autowired
	private CounterServiceI counterService;
	
	@Autowired
	private UserDaoI userDao;
	@Autowired
	private MessageServiceI messageServiceImpl;

	@Autowired
	private BshootDaoI bshootDao;

	@Override
	public DataGrid dataGrid(BshootComment bshootComment, PageHelper ph) {
		List<BshootComment> ol = new ArrayList<BshootComment>();
		String hql = " from TbshootComment t ";
		DataGrid dg = dataGridQuery(hql, ph, bshootComment, bshootCommentDao);
		@SuppressWarnings("unchecked")
		List<TbshootComment> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TbshootComment t : l) {
				BshootComment o = new BshootComment();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		setUserInfo(dg);
		return dg;
	}
	

	@SuppressWarnings("unchecked")
	private void setUserInfo(DataGrid dataGrid) {
		List<BshootComment> comments = dataGrid.getRows();
		if(comments != null && comments.size()>0){
			
			List<String> pidList = new ArrayList<String>();
			
			List<String> userIdList = new ArrayList<String>();
			//String[] userIds = new String[comments.size()];
			//int i = 0;
			for(BshootComment c : comments){
				if(!userIdList.contains(c.getUserId())) {
					userIdList.add(c.getUserId());
				}
				//userIds[i++] = c.getUserId();
				if(!F.empty(c.getParentId()) && !pidList.contains(c.getParentId())) {
					pidList.add(c.getParentId());
				}
			}
			
			Map<String, String> pMap = new HashMap<String, String>();
			if(pidList.size() > 0) {
				List<TbshootComment> pComments = bshootCommentDao.getTbshootComments(pidList);
				List<String> pUserIdList = new ArrayList<String>();
				Map<String, String> cMap = new HashMap<String, String>();
				for(TbshootComment c : pComments) {
					if(!pUserIdList.contains(c.getUserId())) {
						pUserIdList.add(c.getUserId());
						cMap.put(c.getUserId(), c.getId());
					}
				}
				List<Tuser> list = userDao.getTusers(pUserIdList.toArray(new String[pUserIdList.size()]));
				for(Tuser t : list) {
					pMap.put(cMap.get(t.getId()), t.getNickname());
				}
			}
			
			List<Tuser> list = userDao.getTusers(userIdList.toArray(new String[userIdList.size()]));
			Map<String, Tuser> map = new HashMap<String, Tuser>();
			for(Tuser t : list){
				map.put(t.getId(), t);
			}
			for(BshootComment c : comments){
				Tuser t = map.get(c.getUserId());
				c.setUserName(t.getNickname());
				c.setUserHeadImage(t.getHeadImage());
				if(!F.empty(c.getParentId())) {
					c.setParentUserName(pMap.get(c.getParentId()));
				}
			}
		}
	}


	protected String whereHql(BshootComment bshootComment, Map<String, Object> params) {
		String whereHql = "";	
		if (bshootComment != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(bshootComment.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", bshootComment.getUserId());
			}		
			if (!F.empty(bshootComment.getBshootId())) {
				whereHql += " and t.bshootId = :bshootId";
				params.put("bshootId", bshootComment.getBshootId());
			}		
			if (!F.empty(bshootComment.getParentId())) {
				whereHql += " and t.parentId = :parentId";
				params.put("parentId", bshootComment.getParentId());
			}		
			if (!F.empty(bshootComment.getBsCommentText())) {
				whereHql += " and t.bsCommentText = :bsCommentText";
				params.put("bsCommentText", bshootComment.getBsCommentText());
			}		
		}	
		return whereHql;
	}

	@Override
	public TbshootComment add(final BshootComment bshootComment,String currentUser) {
		TbshootComment t = new TbshootComment();
		BeanUtils.copyProperties(bshootComment, t);
		t.setId(UUID.randomUUID().toString());
		t.setCommentDatetime(new Date());
		bshootCommentDao.save(t);
		//动态的打赏计数+1
		counterService.automicChangeCount(bshootComment.getBshootId(), CounterType.COMMENT, 1);
		ComCounterProducer.getInstance().send(CounterType.COMMENT,t,t.getId());

		return t;
	}

	private Long getCount(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		Long l= bshootDao.count("select count(t.id) from TbshootComment t where t.bshootId =:bshootId", params);
		if(l == null) l = 0L;
		updateCount(bshootId,l);
		return l;
	}

	//分组统计
	@Override
	public Map<String,Integer> countGroup(Date begin, Date end){
		Map<String,Object> params = new HashMap<>();
		params.put("startDate",begin);
		params.put("endDate",end);
		List<Map> result = bshootCommentDao.findBySql2Map("select t1.bshoot_id as bshootId,count(t1.bshoot_id) as count from bshoot_comment t1 where " +
				"EXISTS(SELECT bshoot_id FROM bshoot_comment t2 " +
				"where t1.bshoot_id=t2.bshoot_id " +
				"and t2.comment_datetime>=:startDate and t2.comment_datetime<=:endDate) " +
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
				if(key.equals("count")) count = ((BigInteger) ele.get(key)).intValue();
			}
			if(null!=bshootId&&null!=count)
				countGroup.put(bshootId,count);
		}
		return countGroup;
	}

	@Override
	public BshootComment get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TbshootComment t = bshootCommentDao.get("from TbshootComment t  where t.id = :id", params);
		BshootComment o = new BshootComment();
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(BshootComment bshootComment) {
		TbshootComment t = bshootCommentDao.get(TbshootComment.class, bshootComment.getId());
		if (t != null) {
			BeanUtils.copyProperties(bshootComment, t, new String[] { "id" , "createdatetime" });
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		final TbshootComment t = bshootCommentDao.get(TbshootComment.class, id);
		bshootCommentDao.delete(t);
		deleteCommentPraise(id);
		updateCountReduce(t.getBshootId());
		//打赏计数-1
		counterService.automicChangeCount(t.getBshootId(), CounterType.COMMENT, -1);
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataGrid dataGrid(BshootComment bshootComment, PageHelper ph,
			String userId) {
		DataGrid dataGrid = dataGrid(bshootComment, ph);
		List<BshootComment> bshootComments = dataGrid.getRows();
		if(bshootComments!=null&&bshootComments.size()>0){
			String[] commentIds = new String[bshootComments.size()];
			int i = 0;
			for(BshootComment b :bshootComments){
				commentIds[i++] = b.getId();
			}
			List<TcommentPraise> list = commentPraiseDao.getTcommentPraises(userId, commentIds);
			Map<String,String> map = new HashMap<String,String>();
			for(TcommentPraise t : list){
				map.put(t.getCommentId(), t.getCommentId());
			}
			for(BshootComment b :bshootComments){
				if(map.get(b.getId())!=null){
					b.setPraised(Constants.GLOBAL_BOOLEAN_TRUE);
				}else{
					b.setPraised(Constants.GLOBAL_BOOLEAN_FALSE);
				}
			}
		}	
		return dataGrid;
	}
	
	private void updateCount(String bshootId,long sum){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", bshootId);
		params.put("sum", sum);
		bshootDao.executeSql("update bshoot t set t.bs_comment = :sum where t.id=:id", params);
	}

	private void updateCountReduce(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", bshootId);
		bshootDao.executeSql("update bshoot t set t.bs_comment = ifnull(t.bs_comment, 0) - 1 where t.id=:id", params);
	}

	private void deleteCommentPraise(String commentId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("commentId", commentId);
		commentPraiseDao.executeSql("delete from comment_praise where comment_id=:commentId", params);
	}

	@Override
	public CounterType getCounterType() {
		return CounterType.COMMENT;
	}

	@Override
	public void handle(String message) {
		BshootComment bshootComment = JSONObject.parseObject(message,BshootComment.class);
		Tbshoot bshoot = bshootDao.get(Tbshoot.class,bshootComment.getBshootId());
		Tuser tuser = userDao.get(Tuser.class,bshootComment.getUserId());
		messageServiceImpl.addAndSendMessage(MessageServiceI.MT_03,bshoot.getUserId(),bshootComment.getId(),"用户["+tuser.getName()+"]评论了您的动态");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//打赏计数-1
		counterService.registerFetchValue(CounterType.COMMENT,new FetchValue() {
			@Override
			public Integer fetchValue(String key) {
				return getCount(key).intValue();
			}
		});
	}
}

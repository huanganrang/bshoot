package jb.service.impl;

import component.redis.model.CounterType;
import component.redis.service.CounterServiceI;
import component.redis.service.FetchValue;
import jb.absx.F;
import jb.dao.BaseDaoI;
import jb.dao.UserAttentionDaoI;
import jb.dao.UserDaoI;
import jb.dao.UserProfileDaoI;
import jb.model.Tuser;
import jb.model.TuserAttention;
import jb.pageModel.*;
import jb.service.UserAttentionServiceI;
import jb.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class UserAttentionServiceImpl extends BaseServiceImpl<UserAttention> implements UserAttentionServiceI {

	@Autowired
	private UserAttentionDaoI userAttentionDao;

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private CounterServiceI counterService;

	@Autowired
	private UserProfileDaoI userProfileDao;

	@Override
	public DataGrid dataGrid(UserAttention userAttention, PageHelper ph) {
		List<UserAttention> ol = new ArrayList<UserAttention>();
		String hql = " from TuserAttention t ";
		DataGrid dg = dataGridQuery(hql, ph, userAttention, userAttentionDao);
		@SuppressWarnings("unchecked")
		List<TuserAttention> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TuserAttention t : l) {
				UserAttention o = new UserAttention();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(UserAttention userAttention, Map<String, Object> params) {
		String whereHql = "";	
		if (userAttention != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(userAttention.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", userAttention.getUserId());
			}		
			if (!F.empty(userAttention.getAttUserId())) {
				whereHql += " and t.attUserId = :attUserId";
				params.put("attUserId", userAttention.getAttUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public int add(final UserAttention userAttention) {
		if(get(userAttention.getUserId(), userAttention.getAttUserId())!=null){		
			return -1;
		}
		TuserAttention t = new TuserAttention();
		BeanUtils.copyProperties(userAttention, t);
		t.setId(UUID.randomUUID().toString());
		t.setAttentionDatetime(new Date());
		userAttentionDao.save(t);
		userAttCount(userAttention.getUserId(),userAttention.getAttUserId(),1);
		return 1;
	}

	//获得用户关注的人数量
	private Long getAttCount(String userId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		Long l =  userAttentionDao.count("select count(t.attUserId) from TuserAttention t where t.userId =:userId", params);
		if(l == null) l = 0L;
		updateAttCount(userId,l);
		return l;
	}

	private void updateAttCount(String userId,long sum){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("sum", sum);
		userProfileDao.executeSql("update user_profile t set t.att_num = :sum where t.id=:userId", params);
	}

	//获得用户粉丝数量
	private Long getBeAttCount(String userId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		Long l =  userAttentionDao.count("select count(t.userId) from TuserAttention t where t.attUserId =:userId", params);
		if(l == null) l = 0L;
		updateAttCount(userId,l);
		return l;
	}

	private void updateBeAttCount(String userId,long sum){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("sum", sum);
		userProfileDao.executeSql("update user_profile t set t.fans_num = :sum where t.id=:userId", params);
	}

	//关注数分组统计
	@Override
	public Map<String,Integer> attCountGroup(Date begin, Date end){
		Map<String,Object> params = new HashMap<>();
		params.put("startDate",begin);
		params.put("endDate",end);
		List<Map> result = userAttentionDao.findBySql2Map("select t1.user_id as userId,count(t1.att_user_id) as count from user_attention t1 where " +
				"EXISTS(SELECT id FROM user_attention t2 " +
				"where t1.user_id=t2.user_id " +
				"and t2.update_datetime>=:startDate and t2.update_datetime<=:endDate) " +
				"GROUP BY t1.user_id", params);
		if(CollectionUtils.isEmpty(result)) return  null;
		Map<String,Integer> countGroup = new HashMap<>();
		for(Map ele:result){
			Set keySet = ele.keySet();
			String userId = null;
			Integer count = null;
			for(Iterator it=keySet.iterator();it.hasNext();){
				String key = (String) it.next();
				if(key.equals("userId")) userId = (String) ele.get(key);
				if(key.equals("count")) count = ((BigInteger) ele.get(key)).intValue();
			}
			if(null!=userId&&null!=count)
				countGroup.put(userId,count);
		}
		return countGroup;
	}

	//粉丝数分组统计
	@Override
	public Map<String,Integer> fansCountGroup(Date begin, Date end){
		Map<String,Object> params = new HashMap<>();
		params.put("startDate",begin);
		params.put("endDate",end);
		List<Map> result = userAttentionDao.findBySql2Map("select t1.att_user_id as userId,count(t1.user_id) as count from user_attention t1 where " +
				"EXISTS(SELECT id FROM user_attention t2 " +
				"where t1.att_user_id=t2.att_user_id " +
				"and t2.update_datetime>=:startDate and t2.update_datetime<=:endDate) " +
				"GROUP BY t1.att_user_id", params);
		if(CollectionUtils.isEmpty(result)) return  null;
		Map<String,Integer> countGroup = new HashMap<>();
		for(Map ele:result){
			Set keySet = ele.keySet();
			String userId = null;
			Integer count = null;
			for(Iterator it=keySet.iterator();it.hasNext();){
				String key = (String) it.next();
				if(key.equals("userId")) userId = (String) ele.get(key);
				if(key.equals("count")) count = ((BigInteger) ele.get(key)).intValue();
			}
			if(null!=userId&&null!=count)
				countGroup.put(userId,count);
		}
		return countGroup;
	}

	@Override
	public UserAttention get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserAttention t = userAttentionDao.get("from TuserAttention t  where t.id = :id", params);
		UserAttention o = new UserAttention();
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		return o;
	}

	public List<TuserAttention> getUserAttentionList(String attentionGroup) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attentionGroup", attentionGroup);
		List<TuserAttention> t = userAttentionDao.find("from TuserAttention t where t.attentionGroup = :attentionGroup", params);
		return t;
	}

	@Override
	public TuserAttention get(String userId,String attUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("attUserId", attUserId);		
		TuserAttention t = userAttentionDao.get("from TuserAttention t  where t.userId = :userId and t.attUserId = :attUserId", params);
		return t;
	}

	@Override
	public List<String> friendCommonAtt(AttentionRequest attentionRequest) {
		return userAttentionDao.friendCommonAtt(attentionRequest);
	}

	@Override
	public List<String> singleFriendAtt(AttentionRequest attentionRequest) {
		return userAttentionDao.singleFriendAtt(attentionRequest);
	}

	@Override
	public DataGrid dataGridMyFriend(UserAttention userAttention, PageHelper ph) {
		DataGrid dg = new DataGrid();
		String hql = "select u from Tuser u ,TuserAttention t ";
		Map<String, Object> params = new HashMap<String, Object>();
		//我的好友
		if(!F.empty(userAttention.getUserId())){
			hql += "where u.id = t.attUserId and t.userId = :userId and t.isFriend is not null and t.isDelete=0";
			params.put("userId",userAttention.getUserId());
		}
		List<Tuser> l = userDao.find(hql + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(userDao.count("select count(*) " + hql.substring(8) , params));
		List<User> ol = new ArrayList<User>();
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User o = new User();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public DataGrid getUserInfos(String key) {
		DataGrid dg = new DataGrid();
		String hql = "select t from Tuser t where t.mobile='"+key+"' or t.nickname like '%"+key+"'";
		List<Tuser> ul = userDao.find(hql);
		List<User> userList = new ArrayList<User>();
		for(Tuser tUser:ul){
			User u = new User();
			BeanUtils.copyProperties(tUser, u);
			userList.add(u);
		}
		if(userList != null && userList.size() > 0){
			dg.setTotal(new Long(userList.size()));
		}
		dg.setRows(userList);
		return dg;
	}

	@Override
	public void edit(UserAttention userAttention) {
		TuserAttention t = userAttentionDao.get(TuserAttention.class, userAttention.getId());
		if (t != null) {
			BeanUtils.copyProperties(userAttention, t, new String[] { "id" , "createdatetime" });
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public int editAttentionGroup(UserAttention userAttention) {
		if(!F.empty(userAttention.getUserId()) && !F.empty(userAttention.getAttUserId())){
			TuserAttention t = get(userAttention.getUserId(),userAttention.getAttUserId());
			t.setAttentionGroup(userAttention.getAttentionGroup());
			userAttentionDao.save(t);
			return 1;
		}
		return -1;
	}

	@Override
	public int delUserAtteGroup(UserAttentionGroup userAttentionGroup){
		if(!F.empty(userAttentionGroup.getId())){
			String sql = "update user_attention set attention_group=null where attention_group="+userAttentionGroup.getId();
			userAttentionDao.executeSql(sql);
			return 1;
		}
		return -1;
	}

	@Override
	public void delete(String id) {
		userAttentionDao.delete(userAttentionDao.get(TuserAttention.class, id));
	}


	@Override
	public int deleteUa(UserAttention userAttention) {
		TuserAttention ua = get(userAttention.getUserId(), userAttention.getAttUserId());
		if(ua==null){		
			return -1;
		}
		delete(ua.getId());
		return 1;
	}

	@Override
	public int deleteAttention(final UserAttention userAttention) {
		final TuserAttention t = get(userAttention.getUserId(), userAttention.getAttUserId());
		if(t==null){
			return -1;
		}else{
			t.setIsDelete(1);
			t.setAttentionDatetime(new Date());
			if(get(userAttention.getAttUserId(), userAttention.getUserId()) != null){
				TuserAttention tu = get(userAttention.getAttUserId(), userAttention.getUserId());
				if(tu.getIsFriend()!=null){
					tu.setIsFriend(null);
					userAttentionDao.save(tu);
				}
			}
			if(t.getIsFriend()!=null){
				t.setIsFriend(null);
			}
			userAttentionDao.save(t);
			userAttCount(userAttention.getUserId(),userAttention.getAttUserId(),-1);
			return 1;
		}
	}

	@Override
	public int isAttention(UserAttention userAttention){
		TuserAttention ua = get(userAttention.getUserId(), userAttention.getAttUserId());
		if(ua==null){
			return -1;//未关注
		}else if(ua.getIsDelete()!=0){
			return -1;//未关注
		}
		return 1;//已关注
	}


	@Override
	public DataGrid dataGridUser(UserAttention userAttention, PageHelper ph) {
		List<User> ol = new ArrayList<User>();
		
		DataGrid dg = dataGridByType(ph, userAttention, userAttentionDao);
		@SuppressWarnings("unchecked")
		List<Tuser> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User o = new User();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataGrid dataGridUser(UserAttention userAttention, PageHelper ph, String userId) {
		DataGrid dg = dataGridUser(userAttention, ph);
		List<User> users = dg.getRows();
		if(userId != null && users!=null&&users.size()>0){
			String[] attUserIds = new String[users.size()];
			int i = 0;
			for(User u :users){
				attUserIds[i++] = u.getId();
			}
			List<TuserAttention> list = userAttentionDao.getTuserAttentions(userId, attUserIds);
			Map<String,String> map = new HashMap<String,String>();
			for(TuserAttention t : list){
				map.put(t.getAttUserId(), t.getAttUserId());
			}
			for(User u :users){
				if(map.get(u.getId())!=null){
					u.setAttred(Constants.GLOBAL_BOOLEAN_TRUE);
				}else{
					u.setAttred(Constants.GLOBAL_BOOLEAN_FALSE);
				}
			}
		}	
		return dg;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private DataGrid dataGridByType(PageHelper ph,UserAttention userAttention,BaseDaoI dao){
		DataGrid dg = new DataGrid();
		String hql = "select u from Tuser u ,TuserAttention t  ";
		Map<String, Object> params = new HashMap<String, Object>();
		//我的关注好友
		if(!F.empty(userAttention.getUserId())){
			hql +="where u.id = t.attUserId and t.userId = :userId";
			params.put("userId",userAttention.getUserId());
		//我的粉丝	
		}else if(!F.empty(userAttention.getAttUserId())){
			hql +="where u.id = t.userId and t.attUserId = :userId";
			params.put("userId",userAttention.getAttUserId());
		}		
		List<Bshoot> l = dao.find(hql   + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(dao.count("select count(*) " + hql.substring(8) , params));
		dg.setRows(l);
		return dg;
	}

	@Override
	public int addAttention(UserAttention userAttention) {
		if(get(userAttention.getUserId(), userAttention.getAttUserId())!=null){
			TuserAttention t = get(userAttention.getUserId(), userAttention.getAttUserId());
			if(t.getIsDelete()==0){
				return -1;//已关注
			}else {
				t.setIsDelete(0);
				t.setAttentionDatetime(new Date());
				//检查对方有没有关注我
				if(get(userAttention.getAttUserId(), userAttention.getUserId())!=null){
					TuserAttention tu = get(userAttention.getAttUserId(), userAttention.getUserId());
					//如果对方关注我了，则把is_friend参数改为1，变相互关注的好友
					if(tu.getIsDelete() == 0){
						t.setIsFriend(1);
						tu.setIsFriend(1);
						userAttentionDao.save(t);
						userAttentionDao.save(tu);
						userAttCount(userAttention.getUserId(),userAttention.getAttUserId(),1);
						return 1;
					}else{
						t.setIsFriend(null);
						userAttentionDao.save(t);
						return 0;
					}
				}else{
					t.setIsFriend(null);
					userAttentionDao.save(t);
					userAttCount(userAttention.getUserId(),userAttention.getAttUserId(),1);
					return 0;
				}
			}
		}else {
			TuserAttention t = new TuserAttention();
			BeanUtils.copyProperties(userAttention, t);
			t.setId(UUID.randomUUID().toString());
			t.setAttentionDatetime(new Date());
			TuserAttention tu = get(userAttention.getAttUserId(), userAttention.getUserId());
			if(tu!=null){
				if(tu.getIsDelete()==null||tu.getIsDelete() == 0){
					t.setIsFriend(1);
					tu.setIsFriend(1);
					userAttentionDao.save(t);
					userAttentionDao.save(tu);
					userAttCount(userAttention.getUserId(),userAttention.getAttUserId(),1);
					return 1;
				}else{
					t.setIsFriend(null);
					userAttentionDao.save(t);
					userAttCount(userAttention.getUserId(),userAttention.getAttUserId(),1);
					return 0;
				}
			}else{
				t.setIsFriend(null);
				userAttentionDao.save(t);
				userAttCount(userAttention.getUserId(),userAttention.getAttUserId(),1);
				return 0;
			}
		}
	}

	private void userAttCount(final String userId, final String attUserId,Integer num){
		//给用户关注的人计数+1
		counterService.automicChangeCount(userId, CounterType.ATT,num, new FetchValue() {
			@Override
			public Integer fetchValue() {
				return getAttCount(userId).intValue();
			}
		});
		//给被用户关注过人的被关注计数+1
		counterService.automicChangeCount(attUserId, CounterType.BEATT,num, new FetchValue() {
			@Override
			public Integer fetchValue() {
				return getBeAttCount(attUserId).intValue();
			}
		});
	}

	@Override
	public DataGrid dataGridUserByGroup(UserAttention userAttention, PageHelper ph) {
		List<User> ol = new ArrayList<User>();
		DataGrid dg = dataGridByGroup(userAttention, ph, userAttentionDao);
		@SuppressWarnings("unchecked")
		List<Tuser> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User o = new User();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	private DataGrid dataGridByGroup(UserAttention userAttention, PageHelper ph, BaseDaoI dao){
		DataGrid dg = new DataGrid();
		String hql = "select u from Tuser u ,TuserAttention t ";
		Map<String, Object> params = new HashMap<String, Object>();
		//我的关注
		if(!F.empty(userAttention.getUserId())){
			hql += "where u.id = t.attUserId and t.userId = :userId and t.isFriend is null and t.isDelete=0 ";
			params.put("userId",userAttention.getUserId());
			//按分组查找
			if(!F.empty(userAttention.getAttentionGroup())){
				hql += "and t.attentionGroup = :attentionGroup";
				params.put("attentionGroup",userAttention.getAttentionGroup());
			}
		}
		List<Tuser> l = dao.find(hql   + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(dao.count("select count(*) " + hql.substring(8) , params));
		dg.setRows(l);
		return dg;
	}

}

package jb.service.impl;

import jb.absx.F;
import jb.dao.BaseDaoI;
import jb.dao.UserAttentionDaoI;
import jb.model.Tuser;
import jb.model.TuserAttention;
import jb.pageModel.*;
import jb.service.UserAttentionServiceI;
import jb.util.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserAttentionServiceImpl extends BaseServiceImpl<UserAttention> implements UserAttentionServiceI {

	@Autowired
	private UserAttentionDaoI userAttentionDao;

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
	public int add(UserAttention userAttention) {
		if(get(userAttention.getUserId(), userAttention.getAttUserId())!=null){		
			return -1;
		}
		TuserAttention t = new TuserAttention();
		BeanUtils.copyProperties(userAttention, t);
		t.setId(UUID.randomUUID().toString());
		t.setAttentionDatetime(new Date());
		userAttentionDao.save(t);
		return 1;
	}

	@Override
	public UserAttention get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserAttention t = userAttentionDao.get("from TuserAttention t  where t.id = :id", params);
		UserAttention o = new UserAttention();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	public List<TuserAttention> getUserAttentionList(String attentionGroup) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attentionGroup", attentionGroup);
		List<TuserAttention> t = userAttentionDao.find("from TuserAttention t  where t.attentionGroup = :attentionGroup", params);
		return t;
	}
	
	public UserAttention get(String userId,String attUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("attUserId", attUserId);		
		TuserAttention t = userAttentionDao.get("from TuserAttention t  where t.userId = :userId and t.attUserId = :attUserId", params);
		if(t==null)
			return null;
		UserAttention o = new UserAttention();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public List<String> friendCommonAtt(String userId,int start,int rows) {
		return userAttentionDao.friendCommonAtt(userId,start,rows);
	}

	@Override
	public List<String> singleFriednAtt(String userId, int start, int rows) {
		return userAttentionDao.singleFriednAtt(userId,start,rows);
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
		if(!F.empty(userAttention.getAttUserId())){
			UserAttention ua = get(userAttention.getUserId(),userAttention.getAttUserId());
			TuserAttention t = new TuserAttention();
			BeanUtils.copyProperties(ua, t);
			t.setAttentionGroup(userAttention.getAttentionGroup());
			userAttentionDao.save(t);
			return 1;//修改分组成功
		}
		TuserAttention t = userAttentionDao.get(TuserAttention.class, userAttention.getId());
		if(t==null){
			return -1;//修改分组失败,未传入关注id或者被关注用户id
		}
		t.setAttentionGroup(userAttention.getAttentionGroup());
		userAttentionDao.save(t);
		return 1;//修改分组成功
	}

	@Override
	public int delUserAtteGroup(UserAttentionGroup userAttentionGroup){
		if(!F.empty(userAttentionGroup.getId())){
			List<TuserAttention> list = getUserAttentionList(userAttentionGroup.getId());
			for(TuserAttention tuserAttention : list){
				tuserAttention.setAttentionGroup(null);
				userAttentionDao.save(tuserAttention);
			}
			return 1;//删除分组成功
		}
		return -1;//删除分组失败
	}

	@Override
	public void delete(String id) {
		userAttentionDao.delete(userAttentionDao.get(TuserAttention.class, id));
	}


	@Override
	public int deleteUa(UserAttention userAttention) {
		UserAttention ua = get(userAttention.getUserId(), userAttention.getAttUserId());
		if(ua==null){		
			return -1;
		}
		delete(ua.getId());
		return 1;
	}

	@Override
	public int deleteAttention(UserAttention userAttention) {
		UserAttention ua = get(userAttention.getUserId(), userAttention.getAttUserId());
		if(ua==null){
			return -1;
		}
		TuserAttention t = new TuserAttention();
		BeanUtils.copyProperties(ua, t);
		t.setIsDelete(1);
		t.setAttentionDatetime(new Date());
		userAttentionDao.save(t);
		return 1;
	}

	@Override
	public int idAttention(UserAttention userAttention){
		UserAttention ua = get(userAttention.getUserId(), userAttention.getAttUserId());
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
		if(get(userAttention.getUserId(), userAttention.getAttUserId())!=null && userAttention.getIsDelete()==0){
			return -1;//已关注
		}else if(get(userAttention.getUserId(), userAttention.getAttUserId())!=null && userAttention.getIsDelete()!=0){
			UserAttention ua = get(userAttention.getUserId(), userAttention.getAttUserId());
			TuserAttention t = new TuserAttention();
			BeanUtils.copyProperties(ua, t);
			t.setIsDelete(0);
			t.setAttentionDatetime(new Date());
			userAttentionDao.save(t);
			return 1;
		}else {
			TuserAttention t = new TuserAttention();
			BeanUtils.copyProperties(userAttention, t);
			t.setId(UUID.randomUUID().toString());
			t.setAttentionDatetime(new Date());
			userAttentionDao.save(t);
			return 1;
		}
	}

	@Override
	public DataGrid dataGridUserByGroup(UserAttention userAttention, PageHelper ph) {
		List<User> ol = new ArrayList<User>();

		DataGrid dg = dataGridByGroup(ph, userAttention, userAttentionDao);
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

	private DataGrid dataGridByGroup(PageHelper ph,UserAttention userAttention,BaseDaoI dao){
		DataGrid dg = new DataGrid();
		String hql = "select u from Tuser u ,TuserAttention t  ";
		Map<String, Object> params = new HashMap<String, Object>();
		//我的关注好友
		if(!F.empty(userAttention.getUserId())){
			hql +="where u.id = t.attUserId and t.userId = :userId";
			params.put("userId",userAttention.getUserId());
			//按分组查找
			if(!F.empty(userAttention.getAttentionGroup())){
				hql +=" and t.attentionGroup = :attentionGroup";
				params.put("attentionGroup",userAttention.getAttentionGroup());
			}
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

}

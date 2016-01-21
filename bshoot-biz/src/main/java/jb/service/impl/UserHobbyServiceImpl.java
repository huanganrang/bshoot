package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jb.absx.F;
import jb.dao.UserHobbyDaoI;
import jb.model.TuserHobby;
import jb.pageModel.UserHobby;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.UserHobbyServiceI;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class UserHobbyServiceImpl extends BaseServiceImpl<UserHobby> implements UserHobbyServiceI {

	@Autowired
	private UserHobbyDaoI userHobbyDao;

	@Override
	public DataGrid dataGrid(UserHobby userHobby, PageHelper ph) {
		List<UserHobby> ol = new ArrayList<UserHobby>();
		String hql = " from TuserHobby t ";
		DataGrid dg = dataGridQuery(hql, ph, userHobby, userHobbyDao);
		@SuppressWarnings("unchecked")
		List<TuserHobby> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TuserHobby t : l) {
				UserHobby o = new UserHobby();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public UserHobby getUserHobby(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		TuserHobby t = userHobbyDao.get("from TuserHobby t  where t.userId = :userId", params);
		UserHobby o = new UserHobby();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public List<UserHobby> getUserHobbies(List<String> userIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userIds", userIds);
		List<TuserHobby> tuserHobbies = userHobbyDao.find("from TuserHobby t  where t.userId in (:userIds)", params);
		UserHobby o = null;
		List<UserHobby> userHobbies = new ArrayList<UserHobby>();
		for(TuserHobby t:tuserHobbies){
            o = new UserHobby();
			BeanUtils.copyProperties(t, o);
			userHobbies.add(o);
		}
		return userHobbies;
	}

	protected String whereHql(UserHobby userHobby, Map<String, Object> params) {
		String whereHql = "";	
		if (userHobby != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(userHobby.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", userHobby.getUserId());
			}		
			if (!F.empty(userHobby.getHobbyType())) {
				whereHql += " and t.hobbyType = :hobbyType";
				params.put("hobbyType", userHobby.getHobbyType());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(UserHobby userHobby) {
		TuserHobby t = new TuserHobby();
		BeanUtils.copyProperties(userHobby, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		userHobbyDao.saveOrUpdate(t);
	}

	@Override
	public UserHobby get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserHobby t = userHobbyDao.get("from TuserHobby t  where t.id = :id", params);
		UserHobby o = new UserHobby();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(UserHobby userHobby) {
		TuserHobby t = userHobbyDao.get(TuserHobby.class, userHobby.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(userHobby, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userHobbyDao.delete(userHobbyDao.get(TuserHobby.class, id));
	}

	@Override
	public void saveOrUpdateUserHobby(UserHobby userHobby){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId",userHobby.getUserId());
		//保存或更新用户兴趣
		TuserHobby u = userHobbyDao.get("from TuserHobby t where t.userId=:userId", param);
		if(null!=u) {
			u.setHobbyType(userHobby.getHobbyType());
			userHobbyDao.update(u);
		}else{
			u = new TuserHobby();
			BeanUtils.copyProperties(userHobby,u);
			u.setCreateDatetime(new Date());
			u.setId(UUID.randomUUID().toString());
			userHobbyDao.save(u);
		}
	}

}

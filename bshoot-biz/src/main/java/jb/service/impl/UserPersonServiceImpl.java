package jb.service.impl;

import jb.absx.F;
import jb.dao.UserDaoI;
import jb.dao.UserPersonDaoI;
import jb.model.Tuser;
import jb.model.TuserPerson;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.User;
import jb.pageModel.UserPerson;
import jb.service.UserPersonServiceI;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserPersonServiceImpl extends BaseServiceImpl<UserPerson> implements UserPersonServiceI {

	@Autowired
	private UserPersonDaoI userPersonDao;

	@Autowired
	private UserDaoI userDao;

	@Override
	public DataGrid dataGrid(UserPerson userPerson, PageHelper ph) {
		List<UserPerson> ol = new ArrayList<UserPerson>();
		String hql = " from TuserPerson t ";
		DataGrid dg = dataGridQuery(hql, ph, userPerson, userPersonDao);
		@SuppressWarnings("unchecked")
		List<TuserPerson> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TuserPerson t : l) {
				UserPerson o = new UserPerson();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(UserPerson userPerson, Map<String, Object> params) {
		String whereHql = "";	
		if (userPerson != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(userPerson.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", userPerson.getUserId());
			}		
			if (!F.empty(userPerson.getAttUserId())) {
				whereHql += " and t.attUserId = :attUserId";
				params.put("attUserId", userPerson.getAttUserId());
			}		

		}	
		return whereHql;
	}

	@Override
	public void add(UserPerson userPerson) {
		TuserPerson t = new TuserPerson();
		BeanUtils.copyProperties(userPerson, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		userPersonDao.save(t);
	}

	@Override
	public UserPerson get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserPerson t = userPersonDao.get("from TuserPerson t  where t.id = :id", params);
		UserPerson o = new UserPerson();
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(UserPerson userPerson) {
		TuserPerson t = userPersonDao.get(TuserPerson.class, userPerson.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(userPerson, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userPersonDao.delete(userPersonDao.get(TuserPerson.class, id));
	}

	@Override
	public void delUserPersonGroup(String id) {
		if(!F.empty(id)){
			String sql = "update user_person set person_group=null where person_group="+id;
			userPersonDao.executeSql(sql);
		}
	}

	private TuserPerson get(String userId, String attUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("attUserId", attUserId);
		TuserPerson t = userPersonDao.get("from TuserPerson t where t.userId = :userId and t.attUserId = :attUserId", params);
		return t;
	}

	@Override
	public int addUserPerson(UserPerson userPerson) {
		if(get(userPerson.getUserId(), userPerson.getAttUserId())!=null){
			TuserPerson t = get(userPerson.getUserId(), userPerson.getAttUserId());
			if(t.getIsDelete()==0){
				return -1;//已是人脉圈好友
			}else {
				t.setIsDelete(0);
				userPersonDao.save(t);
				return 1;
			}
		}else {
			TuserPerson t = new TuserPerson();
			BeanUtils.copyProperties(userPerson, t);
			t.setId(UUID.randomUUID().toString());
			t.setCreateDatetime(new Date());
			t.setIsDelete(0);
			userPersonDao.save(t);
			return 1;
		}
	}

	@Override
	public int deleteUserPerson(UserPerson userPerson) {
		TuserPerson t = get(userPerson.getUserId(), userPerson.getAttUserId());
		if(t==null){
			return -1;
		}else{
			t.setIsDelete(1);
			userPersonDao.save(t);
			return 1;
		}
	}

	@Override
	public DataGrid dataGridMyUserPerson(UserPerson userPerson, PageHelper ph) {
		DataGrid dg = new DataGrid();
		String hql = "select u from Tuser u ,TuserPerson t ";
		Map<String, Object> params = new HashMap<String, Object>();
		if(!F.empty(userPerson.getUserId())){
			hql +="where u.id = t.attUserId and t.userId = :userId and t.isDelete=0";
			params.put("userId",userPerson.getUserId());
		}else if(!F.empty(userPerson.getAttUserId())){//查询加了我人脉圈好友的人
			hql +="where u.id = t.userId and t.attUserId = :userId";
			params.put("userId",userPerson.getAttUserId());
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
	public int isUserPerson(UserPerson userPerson) {
		TuserPerson u = get(userPerson.getUserId(), userPerson.getAttUserId());
		if(u == null){
			return -1;
		}else {
			if(u.getIsDelete()==0){
				return 0;
			}else {
				return -1;
			}
		}
	}

}

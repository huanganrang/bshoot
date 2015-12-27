package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jb.absx.F;
import jb.dao.UserMobilePersonDaoI;
import jb.model.TuserMobilePerson;
import jb.pageModel.UserMobilePerson;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.UserMobilePersonServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class UserMobilePersonServiceImpl extends BaseServiceImpl<UserMobilePerson> implements UserMobilePersonServiceI {

	@Autowired
	private UserMobilePersonDaoI userMobilePersonDao;

	@Override
	public DataGrid dataGrid(UserMobilePerson userMobilePerson, PageHelper ph) {
		List<UserMobilePerson> ol = new ArrayList<UserMobilePerson>();
		String hql = " from TuserMobilePerson t ";
		DataGrid dg = dataGridQuery(hql, ph, userMobilePerson, userMobilePersonDao);
		@SuppressWarnings("unchecked")
		List<TuserMobilePerson> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TuserMobilePerson t : l) {
				UserMobilePerson o = new UserMobilePerson();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(UserMobilePerson userMobilePerson, Map<String, Object> params) {
		String whereHql = "";	
		if (userMobilePerson != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(userMobilePerson.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", userMobilePerson.getUserId());
			}		
			if (!F.empty(userMobilePerson.getFriendName())) {
				whereHql += " and t.friendName = :friendName";
				params.put("friendName", userMobilePerson.getFriendName());
			}		
			if (!F.empty(userMobilePerson.getMobile())) {
				whereHql += " and t.mobile = :mobile";
				params.put("mobile", userMobilePerson.getMobile());
			}		
			if (!F.empty(userMobilePerson.getIsDelete())) {
				whereHql += " and t.isDelete = :isDelete";
				params.put("isDelete", userMobilePerson.getIsDelete());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(UserMobilePerson userMobilePerson) {
		TuserMobilePerson t = new TuserMobilePerson();
		BeanUtils.copyProperties(userMobilePerson, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		userMobilePersonDao.save(t);
	}

	@Override
	public UserMobilePerson get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserMobilePerson t = userMobilePersonDao.get("from TuserMobilePerson t  where t.id = :id", params);
		UserMobilePerson o = new UserMobilePerson();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(UserMobilePerson userMobilePerson) {
		TuserMobilePerson t = userMobilePersonDao.get(TuserMobilePerson.class, userMobilePerson.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(userMobilePerson, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userMobilePersonDao.delete(userMobilePersonDao.get(TuserMobilePerson.class, id));
	}

}

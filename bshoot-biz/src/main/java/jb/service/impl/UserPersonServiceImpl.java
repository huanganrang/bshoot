package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jb.absx.F;
import jb.dao.UserPersonDaoI;
import jb.model.TuserPerson;
import jb.pageModel.UserPerson;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.UserPersonServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class UserPersonServiceImpl extends BaseServiceImpl<UserPerson> implements UserPersonServiceI {

	@Autowired
	private UserPersonDaoI userPersonDao;

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
			if (!F.empty(userPerson.getIsDelete())) {
				whereHql += " and t.isDelete = :isDelete";
				params.put("isDelete", userPerson.getIsDelete());
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

}

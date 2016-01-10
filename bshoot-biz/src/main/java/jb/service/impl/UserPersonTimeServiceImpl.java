package jb.service.impl;

import jb.absx.F;
import jb.dao.UserPersonTimeDaoI;
import jb.model.TuserPersonTime;
import jb.pageModel.Bshoot;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.UserPersonTime;
import jb.service.UserPersonTimeServiceI;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserPersonTimeServiceImpl extends BaseServiceImpl<UserPersonTime> implements UserPersonTimeServiceI {

	@Autowired
	private UserPersonTimeDaoI userPersonTimeDao;

	@Override
	public DataGrid dataGrid(UserPersonTime userPersonTime, PageHelper ph) {
		List<UserPersonTime> ol = new ArrayList<UserPersonTime>();
		String hql = " from TuserPersonTime t ";
		DataGrid dg = dataGridQuery(hql, ph, userPersonTime, userPersonTimeDao);
		@SuppressWarnings("unchecked")
		List<TuserPersonTime> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TuserPersonTime t : l) {
				UserPersonTime o = new UserPersonTime();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(UserPersonTime userPersonTime, Map<String, Object> params) {
		String whereHql = "";	
		if (userPersonTime != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(userPersonTime.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", userPersonTime.getUserId());
			}		
			if (!F.empty(userPersonTime.getBsId())) {
				whereHql += " and t.bsId = :bsId";
				params.put("bsId", userPersonTime.getBsId());
			}		

		}	
		return whereHql;
	}

	@Override
	public void add(UserPersonTime userPersonTime) {
		TuserPersonTime t = new TuserPersonTime();
		BeanUtils.copyProperties(userPersonTime, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		userPersonTimeDao.save(t);
	}

	@Override
	public UserPersonTime get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserPersonTime t = userPersonTimeDao.get("from TuserPersonTime t  where t.id = :id", params);
		UserPersonTime o = new UserPersonTime();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(UserPersonTime userPersonTime) {
		TuserPersonTime t = userPersonTimeDao.get(TuserPersonTime.class, userPersonTime.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(userPersonTime, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userPersonTimeDao.delete(userPersonTimeDao.get(TuserPersonTime.class, id));
	}

	@Override
	public DataGrid dataGridUserPersonTime(UserPersonTime userPersonTime, Bshoot bshoot, PageHelper ph) {
		DataGrid dg = new DataGrid();
		String hql = "select u from TuserPersonTime u ,Tbshoot t where u.isDelete=0 ";
		Map<String, Object> params = new HashMap<String, Object>();
		if(!F.empty(userPersonTime.getUserId())){
			hql += "and u.bsId = t.id and u.userId = :userId ";
			params.put("userId",userPersonTime.getUserId());
			if(userPersonTime.getPersonType() != null){
				hql += "and u.personType = :personType ";
				params.put("personType",userPersonTime.getPersonType());
			}
			if(bshoot.getBsFileType() != null){
				hql += "and t.bsFileType = :bsFileType ";
				params.put("bsFileType",bshoot.getBsFileType());
			}
		}
		List<TuserPersonTime> l = userPersonTimeDao.find(hql + orderHql(ph), params, ph.getPage(), ph.getRows());
		List<UserPersonTime> ol = new ArrayList<UserPersonTime>();
		if (l != null && l.size() > 0) {
			for (TuserPersonTime t : l) {
				UserPersonTime o = new UserPersonTime();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setTotal(userPersonTimeDao.count("select count(*) " + hql.substring(8) , params));
		dg.setRows(ol);
		return dg;
	}

}

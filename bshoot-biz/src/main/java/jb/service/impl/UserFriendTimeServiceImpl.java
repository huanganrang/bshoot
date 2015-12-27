package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jb.absx.F;
import jb.dao.UserFriendTimeDaoI;
import jb.model.TuserFriendTime;
import jb.pageModel.UserFriendTime;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.UserFriendTimeServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class UserFriendTimeServiceImpl extends BaseServiceImpl<UserFriendTime> implements UserFriendTimeServiceI {

	@Autowired
	private UserFriendTimeDaoI userFriendTimeDao;

	@Override
	public DataGrid dataGrid(UserFriendTime userFriendTime, PageHelper ph) {
		List<UserFriendTime> ol = new ArrayList<UserFriendTime>();
		String hql = " from TuserFriendTime t ";
		DataGrid dg = dataGridQuery(hql, ph, userFriendTime, userFriendTimeDao);
		@SuppressWarnings("unchecked")
		List<TuserFriendTime> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TuserFriendTime t : l) {
				UserFriendTime o = new UserFriendTime();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(UserFriendTime userFriendTime, Map<String, Object> params) {
		String whereHql = "";	
		if (userFriendTime != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(userFriendTime.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", userFriendTime.getUserId());
			}		
			if (!F.empty(userFriendTime.getBsId())) {
				whereHql += " and t.bsId = :bsId";
				params.put("bsId", userFriendTime.getBsId());
			}		

		}	
		return whereHql;
	}

	@Override
	public void add(UserFriendTime userFriendTime) {
		TuserFriendTime t = new TuserFriendTime();
		BeanUtils.copyProperties(userFriendTime, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		userFriendTimeDao.save(t);
	}

	@Override
	public UserFriendTime get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserFriendTime t = userFriendTimeDao.get("from TuserFriendTime t  where t.id = :id", params);
		UserFriendTime o = new UserFriendTime();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(UserFriendTime userFriendTime) {
		TuserFriendTime t = userFriendTimeDao.get(TuserFriendTime.class, userFriendTime.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(userFriendTime, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userFriendTimeDao.delete(userFriendTimeDao.get(TuserFriendTime.class, id));
	}

}

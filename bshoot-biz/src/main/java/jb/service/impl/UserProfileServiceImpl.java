package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jb.absx.F;
import jb.dao.UserProfileDaoI;
import jb.model.TuserProfile;
import jb.pageModel.UserProfile;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.UserProfileServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfile> implements UserProfileServiceI {

	@Autowired
	private UserProfileDaoI userProfileDao;

	@Override
	public DataGrid dataGrid(UserProfile userProfile, PageHelper ph) {
		List<UserProfile> ol = new ArrayList<UserProfile>();
		String hql = " from TuserProfile t ";
		DataGrid dg = dataGridQuery(hql, ph, userProfile, userProfileDao);
		@SuppressWarnings("unchecked")
		List<TuserProfile> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TuserProfile t : l) {
				UserProfile o = new UserProfile();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(UserProfile userProfile, Map<String, Object> params) {
		String whereHql = "";	
		if (userProfile != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(userProfile.getMemberV())) {
				whereHql += " and t.memberV = :memberV";
				params.put("memberV", userProfile.getMemberV());
			}		
			if (!F.empty(userProfile.getAttNum())) {
				whereHql += " and t.attNum = :attNum";
				params.put("attNum", userProfile.getAttNum());
			}		
			if (!F.empty(userProfile.getFansNum())) {
				whereHql += " and t.fansNum = :fansNum";
				params.put("fansNum", userProfile.getFansNum());
			}		
			if (!F.empty(userProfile.getPraiseNum())) {
				whereHql += " and t.praiseNum = :praiseNum";
				params.put("praiseNum", userProfile.getPraiseNum());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(UserProfile userProfile) {
		TuserProfile t = new TuserProfile();
		BeanUtils.copyProperties(userProfile, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		userProfileDao.save(t);
	}

	@Override
	public UserProfile get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserProfile t = userProfileDao.get("from TuserProfile t  where t.id = :id", params);
		UserProfile o = new UserProfile();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(UserProfile userProfile) {
		TuserProfile t = userProfileDao.get(TuserProfile.class, userProfile.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(userProfile, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userProfileDao.delete(userProfileDao.get(TuserProfile.class, id));
	}

}

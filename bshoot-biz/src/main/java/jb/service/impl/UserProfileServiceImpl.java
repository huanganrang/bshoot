package jb.service.impl;

import java.math.BigDecimal;
import java.util.*;

import component.redis.model.CounterType;
import component.redis.model.UserProfileCounter;
import component.redis.service.CounterServiceI;
import jb.absx.F;
import jb.dao.UserProfileDaoI;
import jb.model.TuserProfile;
import jb.pageModel.UserProfile;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.UserProfileServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfile> implements UserProfileServiceI {

	@Autowired
	private UserProfileDaoI userProfileDao;
	@Autowired
	private CounterServiceI counterService;

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
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		//获得用户关注数，粉丝数，打赏量计数
		UserProfileCounter userProfileCounter = counterService.getCounterByUser(id);
		if(null!=userProfileCounter){
			if(0!=userProfileCounter.getAttCount())
			o.setAttNum(userProfileCounter.getAttCount());
			if(0!=userProfileCounter.getPraiseCount())
			o.setPraiseNum(userProfileCounter.getPraiseCount());
			if(0!=userProfileCounter.getFansCount())
			o.setFansNum(userProfileCounter.getFansCount());
		}
		return o;
	}

	@Override
	public void edit(UserProfile userProfile) {
		TuserProfile t = userProfileDao.get(TuserProfile.class, userProfile.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(userProfile, t, new String[] { "id" , "createdatetime" },true);
			userProfileDao.update(t);
		}
	}

	@Override
	public void delete(String id) {
		userProfileDao.delete(userProfileDao.get(TuserProfile.class, id));
	}

	@Override
	public Long getUserProfileCount(){
		return userProfileDao.count("select count(id) from TuserProfile");
	}

	@Override
	public Map<String,Integer> countMonthPraise(Date begin, Date end, int start, int limit){
		Map<String,Object> param = new HashMap<>();
		param.put("startDate",begin);
		param.put("endDate",end);
		param.put("start",start);
		param.put("limit",limit);
		List<Map> result = userProfileDao.findBySql2Map("select user_id as userId,sum(bs_praise) as bsPraise from bshoot t1 where EXISTS (select id from user_profile t2 where t1.user_id=t2.id limit :start,:limit) and t1.update_datetime>=:startDate and t1.update_datetime<=:endDate group by t1.user_id",param);
		if(CollectionUtils.isEmpty(result)) return  null;
		Map<String,Integer> countGroup = new HashMap<>();
		for(Map ele:result){
			Set keySet = ele.keySet();
			String userId = null;
			Integer bsPraise = null;
			for(Iterator it=keySet.iterator();it.hasNext();){
				String key = (String) it.next();
				if(key.equals("userId")) userId = (String) ele.get(key);
				if(key.equals("bsPraise")) bsPraise = ((BigDecimal) ele.get(key)).intValue();
			}
			if(null!=userId&&null!=bsPraise)
				countGroup.put(userId, bsPraise);
		}
		return countGroup;
	}

	@Override
	public void updateMonthPraise(String userId,Integer monthPraise){
		Map<String,Object> params = new HashMap<>();
		params.put("userId",userId);
		params.put("monthPraise",monthPraise);
		userProfileDao.executeSql("update user_profile set month_praise=:monthPraise where id=:userId",params);
	}

	@Override
	public void updateUserProfileCount(String userId,Integer count,CounterType counterType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",userId);
		String sql = "update user_profile  set :countField=:count where id=:id";
		String countField=null;
		if(counterType==CounterType.PRAISE){
			countField = "praise_num";
		}else if(counterType==CounterType.ATT){
			countField= "att_num";
		}else if(counterType==CounterType.BEATT){
			countField= "fans_num";
		}
		sql = sql.replace(":countField",countField);
		params.put("count",count);
		userProfileDao.executeSql(sql,params);
	}
}

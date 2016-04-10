package jb.service.impl;

import jb.absx.F;
import jb.dao.UserAttentionDaoI;
import jb.dao.UserFriendTimeDaoI;
import jb.model.TuserAttention;
import jb.model.TuserFriendTime;
import jb.pageModel.*;
import jb.service.*;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserFriendTimeServiceImpl extends BaseServiceImpl<UserFriendTime> implements UserFriendTimeServiceI {

	@Autowired
	private UserFriendTimeDaoI userFriendTimeDao;

	@Autowired
	private UserAttentionDaoI userAttentionDao;

	@Autowired
	private BshootServiceI bshootService;

	@Autowired
	private UserAttentionServiceI userAttentionService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private UserHobbyServiceI userHobbyService;

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
		if(null!=t)
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

	private UserAttention get(String userId,String attUserId) {
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
	public DataGrid dataGridUserFriendTime(UserFriendTime userFriendTime, UserAttention userAttention, Bshoot bshoot, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<UserFriendTime> ol = new ArrayList<UserFriendTime>();
		String hql = "select u from TuserFriendTime u ,Tbshoot t where u.isDelete=0 ";
		Map<String, Object> params = new HashMap<String, Object>();
		if(!F.empty(userFriendTime.getUserId())){
			hql += "and u.bsId = t.id and u.userId = :userId ";
			params.put("userId",userFriendTime.getUserId());
			if(bshoot.getBsFileType() != null){
				hql += "and t.bsFileType = :bsFileType ";
				params.put("bsFileType",bshoot.getBsFileType());
			}
			if(userFriendTime.getFriendType() != null){
				hql += "and u.friendType = :friendType ";
				params.put("friendType",userFriendTime.getFriendType());
			}
			List<TuserFriendTime> l = userFriendTimeDao.find(hql + orderHql(ph), params, ph.getPage(), ph.getRows());
			if (l != null && l.size() > 0) {
				//查询关注动态
				if(userFriendTime.getFriendType() == 0){
					//分组查询
					if(!F.empty(userAttention.getAttentionGroup())){
						for (TuserFriendTime t : l) {
							Bshoot bs = bshootService.get(t.getBsId());
							String us = bs.getUserId();
							TuserAttention ua = userAttentionService.get(userFriendTime.getUserId(), us);
							if(userAttention.getAttentionGroup().equals(ua.getAttentionGroup())){
								UserFriendTime o = new UserFriendTime();
								BeanUtils.copyProperties(t, o);
								User user = userService.get(us, true);
								bs.setUserHeadImage(user.getHeadImage());
								bs.setUserName(user.getName());
								UserHobby userHobby_bs = userHobbyService.getUserHobby(us);
								List<String> hobby_bs = userHobby_bs.getHobbyTypeName();
								UserHobby userHobby_o = userHobbyService.getUserHobby(o.getUserId());
								List<String> hobby_o = userHobby_o.getHobbyTypeName();
								if(hobby_bs != null && hobby_bs.size() > 0 && hobby_o != null && hobby_o.size() > 0) {
									List<String> hobby = new ArrayList<>();
									for(String hy : hobby_bs) {
										if(hobby_o.contains(hy)) {
											hobby.add(hy);//共同兴趣
										}
									}
									bs.setHobby(hobby);
								}
								o.setBshoot(bs);
								ol.add(o);
							}
						}
						dg.setTotal((long) ol.size());
						dg.setRows(ol);
						return dg;
					}else {
						for (TuserFriendTime t : l) {
							Bshoot bs = bshootService.get(t.getBsId());
							UserFriendTime o = new UserFriendTime();
							BeanUtils.copyProperties(t, o);
							String us = bs.getUserId();
							User user = userService.get(us, true);
							bs.setUserHeadImage(user.getHeadImage());
							bs.setUserName(user.getName());
							UserHobby userHobby_bs = userHobbyService.getUserHobby(us);
							List<String> hobby_bs = userHobby_bs.getHobbyTypeName();
							UserHobby userHobby_o = userHobbyService.getUserHobby(o.getUserId());
							List<String> hobby_o = userHobby_o.getHobbyTypeName();
							if(hobby_bs != null && hobby_bs.size() > 0 && hobby_o != null && hobby_o.size() > 0) {
								List<String> hobby = new ArrayList<>();
								for(String hy : hobby_bs) {
									if(hobby_o.contains(hy)) {
										hobby.add(hy);//共同兴趣
									}
								}
								bs.setHobby(hobby);
							}

							o.setBshoot(bs);
							ol.add(o);
						}
						dg.setTotal((long) ol.size());
						dg.setRows(ol);
						return dg;
					}
				}else if(userFriendTime.getFriendType() == 1){//查询好友动态
					for (TuserFriendTime t : l) {
						Bshoot bs = bshootService.get(t.getBsId());
						UserFriendTime o = new UserFriendTime();
						BeanUtils.copyProperties(t, o);
						String us = bs.getUserId();
						User user = userService.get(us, true);
						bs.setUserHeadImage(user.getHeadImage());
						bs.setUserName(user.getName());
						UserHobby userHobby_bs = userHobbyService.getUserHobby(us);
						List<String> hobby_bs = userHobby_bs.getHobbyTypeName();
						UserHobby userHobby_o = userHobbyService.getUserHobby(o.getUserId());
						List<String> hobby_o = userHobby_o.getHobbyTypeName();
						if(hobby_bs != null && hobby_bs.size() > 0 && hobby_o != null && hobby_o.size() > 0) {
							List<String> hobby = new ArrayList<>();
							for(String hy : hobby_bs) {
								if(hobby_o.contains(hy)) {
									hobby.add(hy);//共同兴趣
								}
							}
							bs.setHobby(hobby);
						}
						o.setBshoot(bs);
						ol.add(o);
					}
					dg.setTotal((long) ol.size());
					dg.setRows(ol);
					return dg;
				}
			}
		}
		return dg;
	}

}

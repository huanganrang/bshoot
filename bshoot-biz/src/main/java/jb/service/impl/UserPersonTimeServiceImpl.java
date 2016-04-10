package jb.service.impl;

import jb.absx.F;
import jb.dao.UserPersonTimeDaoI;
import jb.model.TuserPersonTime;
import jb.pageModel.*;
import jb.service.*;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserPersonTimeServiceImpl extends BaseServiceImpl<UserPersonTime> implements UserPersonTimeServiceI {

	@Autowired
	private UserPersonTimeDaoI userPersonTimeDao;

	@Autowired
	private BshootServiceI bshootService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private UserHobbyServiceI userHobbyService;

	@Autowired
	private BshootCommentServiceI bshootCommentService;

	@Autowired
	private UserMobilePersonServiceI userMobilePersonService;

	@Autowired
	private UserPersonServiceI userPersonService;

	@Autowired
	private UserAttentionServiceI userAttentionService;

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
		if(null!=t)
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
				Bshoot bs = bshootService.get(t.getBsId());
				UserPersonTime o = new UserPersonTime();
				BeanUtils.copyProperties(t, o);
				String us = bs.getUserId();
				User user = userService.get(us, true);
				bs.setUserHeadImage(user.getHeadImage());
				bs.setUserName(user.getName());
				bs.setMemberV(user.getMemberV());
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
				BshootComment bshootComment = new BshootComment();
				bshootComment.setBshootId(t.getBsId());
				PageHelper phl = new PageHelper();
				phl.setOrder("desc");
				phl.setSort("commentDatetime");
				phl.setPage(1);
				phl.setRows(10);
				DataGrid comments = bshootCommentService.dataGrid(bshootComment, phl);
				bs.setComments(comments);//评论
				o.setBshoot(bs);
				List<User> uu = new ArrayList<>();//共同的好友或共同的手机通讯录上的人
				if(t.getPersonType() == 0) {
					//共同的软件通讯录好友
					UserAttention ua = new UserAttention();
					ua.setUserId(t.getUserId());
					DataGrid da = userAttentionService.dataGridMyFriend(ua, new PageHelper());//我的好友
					List<User> la = da.getRows();
					UserPerson up = new UserPerson();
					up.setAttUserId(us);
					DataGrid dp = userPersonService.dataGridMyUserPerson(up, new PageHelper());//谁加了资源发布都的人脉圈好友
					List<User> lp = dp.getRows();
					if (la != null && la.size() > 0 && lp != null && lp.size() > 0) {
						for (User aa : la) {
							for (User pp : lp) {
								if (aa.getId().equals(pp.getId())) {
									uu.add(aa);
								}
							}

						}
					}
				}else if(t.getPersonType() == 1) {
					//共同的手机通讯录上的人
					UserMobilePerson um = new UserMobilePerson();
					um.setUserId(t.getUserId());
					DataGrid dm = userMobilePersonService.dataGrid(um, new PageHelper());//我的手机通讯录好友
					um.setUserId(us);
					DataGrid ds = userMobilePersonService.dataGrid(um, new PageHelper());//我的手机通讯录好友
					List<User> la = dm.getRows();
					List<User> lp = ds.getRows();
					if(la != null && la.size() > 0 && lp != null && lp.size() > 0) {
						for(User aa : la) {
							for (User pp : lp) {
								if(aa.getId().equals(pp.getId())) {
									uu.add(aa);
								}
							}
						}
					}
				}
				o.setCommonFriends(uu);
				ol.add(o);
			}
		}
		dg.setTotal(userPersonTimeDao.count("select count(*) " + hql.substring(8) , params));
		dg.setRows(ol);
		return dg;
	}

}

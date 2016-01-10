package jb.service.impl;

import jb.absx.F;
import jb.dao.UserDaoI;
import jb.dao.UserMobilePersonDaoI;
import jb.model.Tuser;
import jb.model.TuserMobilePerson;
import jb.pageModel.*;
import jb.service.UserAttentionServiceI;
import jb.service.UserMobilePersonServiceI;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserMobilePersonServiceImpl extends BaseServiceImpl<UserMobilePerson> implements UserMobilePersonServiceI {

	@Autowired
	private UserMobilePersonDaoI userMobilePersonDao;

	@Autowired
	private UserAttentionServiceI userAttentionService;

	@Autowired
	private UserDaoI userDao;

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

	@Override
	public List<String> notAttMobilePerson(UserMobilePersonRequest request) {
		return userMobilePersonDao.notAttMobilePerson(request);
	}

	@Override
	public List<String> noAttMobilePersonPerson(UserMobilePersonRequest request) {
		return  userMobilePersonDao.noAttMobilePersonPerson(request);
	}

	private UserMobilePerson get(String userId, String friendName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("friendName", friendName);
		TuserMobilePerson t = userMobilePersonDao.get("from TuserMobilePerson t  where t.userId = :userId and t.friendName = :friendName", params);
		if(t==null)
			return null;
		UserMobilePerson o = new UserMobilePerson();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	private List<User> mobileUser(String mobile) {
		List<User> ls = new ArrayList<User>();
		if(!F.empty(mobile)){
			String hql = "";
			if(mobile.contains(",")){
				String[] mobiles = mobile.split(",");
				for (int i=0;i<mobiles.length;i++){
					hql = "from Tuser t where t.mobile like %"+mobiles[i]+"%";
					List<Tuser> ts = userDao.find(hql);
					if(ts != null && ts.size()>0){
						for (Tuser t : ts){
							User u = new User();
							BeanUtils.copyProperties(t, u);
							ls.add(u);
						}
					}
				}
			}else {
				hql = "from Tuser t where t.mobile like %"+mobile+"%";
				List<Tuser> ts = userDao.find(hql);
				if(ts != null && ts.size()>0){
					for (Tuser t : ts){
						User u = new User();
						BeanUtils.copyProperties(t, u);
						ls.add(u);
					}
				}
			}
		}
		return ls;
	}

	@Override
	public int addMobilePerson(UserMobilePerson userMobilePerson) {
		if(get(userMobilePerson.getUserId(), userMobilePerson.getFriendName())!=null && userMobilePerson.getIsDelete()==0){
			return -1;//已存在
		}else if(get(userMobilePerson.getUserId(), userMobilePerson.getFriendName())!=null && userMobilePerson.getIsDelete()!=0){
			UserMobilePerson ua = get(userMobilePerson.getUserId(), userMobilePerson.getFriendName());
			TuserMobilePerson t = new TuserMobilePerson();
			BeanUtils.copyProperties(ua, t);
			t.setIsDelete(0);
			userMobilePersonDao.save(t);
			return 0;
		}else {
			TuserMobilePerson t = new TuserMobilePerson();
			BeanUtils.copyProperties(userMobilePerson, t);
			t.setId(UUID.randomUUID().toString());
			t.setCreateDatetime(new Date());
			t.setIsDelete(0);
			//friendId，用户id，是否有用该手机号码注册或绑定
			if(!F.empty(userMobilePerson.getMobile())){
				List<User> ls = mobileUser(userMobilePerson.getMobile());
				String userIds = null;
				if(ls != null && ls.size()>0){
					for (int i=0;i<ls.size();i++){
						if(i!=ls.size()-1){
							userIds += ls.get(i).getId()+",";
						}else {
							userIds += ls.get(i).getId();
						}
					}
					t.setFriendId(userIds);
				}
			}
			return 0;
		}
	}

	@Override
	public int deleteMobilePerson(UserMobilePerson userMobilePerson) {
		if(!F.empty(userMobilePerson.getUserId())){
			TuserMobilePerson um = userMobilePersonDao.get(TuserMobilePerson.class, userMobilePerson.getId());
			if(um != null){
				um.setIsDelete(1);
				userMobilePersonDao.save(um);
				return 0;
			}
		}
		return -1;
	}

	@Override
	public DataGrid dataGridRegMobilePerson(UserMobilePerson userMobilePerson, PageHelper ph) {
		List<UserMobilePerson> ol = new ArrayList<UserMobilePerson>();
		Map<String, Object> params = new HashMap<String, Object>();
		Boolean isAtten = false;
		DataGrid dg = new DataGrid();
		params.put("userId",userMobilePerson.getUserId());
		String hql = "select t from TuserMobilePerson t where t.friendId is not null and t.userId = :userId and t.isDelete=0";
		List<TuserMobilePerson> l = userMobilePersonDao.find(hql + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (TuserMobilePerson t : l) {
				//已注册的手机联系人
				if(!F.empty(t.getFriendId())){
					if(t.getFriendId().contains(",")){
						String[] friendIds = t.getFriendId().split(",");
						for (int i=0;i<friendIds.length;i++){
							UserAttention ua = new UserAttention();
							ua.setUserId(userMobilePerson.getUserId());
							ua.setAttUserId(friendIds[i]);
							if(userAttentionService.isAttention(ua)==1){
								isAtten = true;
							}
						}
					}else {
						UserAttention ua = new UserAttention();
						ua.setUserId(userMobilePerson.getUserId());
						ua.setAttUserId(t.getFriendId());
						if(userAttentionService.isAttention(ua)==1){
							isAtten = true;
						}
					}
					if(!isAtten){
						UserMobilePerson o = new UserMobilePerson();
						BeanUtils.copyProperties(t, o);
						ol.add(o);
					}
				}
			}
		}
		dg.setRows(ol);
		return dg;
	}

	@Override
	public DataGrid dataGridNoRegMobilePerson(UserMobilePerson userMobilePerson, PageHelper ph) {
		List<UserMobilePerson> ol = new ArrayList<UserMobilePerson>();
		Map<String, Object> params = new HashMap<String, Object>();
		DataGrid dg = new DataGrid();
		params.put("userId",userMobilePerson.getUserId());
		String hql = " from TuserMobilePerson t where t.userId = :userId and t.friendId is null";
		List<TuserMobilePerson> l = userMobilePersonDao.find(hql + orderHql(ph), params, ph.getPage(), ph.getRows());
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

}

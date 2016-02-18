package jb.service.impl;

import component.redis.service.RedisUserServiceImpl;
import jb.absx.F;
import jb.dao.ResourceDaoI;
import jb.dao.RoleDaoI;
import jb.dao.UserAttentionDaoI;
import jb.dao.UserDaoI;
import jb.listener.Application;
import jb.model.Tresource;
import jb.model.Trole;
import jb.model.Tuser;
import jb.model.TuserAttention;
import jb.pageModel.*;
import jb.service.UserProfileServiceI;
import jb.service.UserServiceI;
import jb.util.Constants;
import jb.util.MD5Util;
import jb.util.MyBeanUtils;
import jb.util.PathUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private RoleDaoI roleDao;

	@Autowired
	private ResourceDaoI resourceDao;
	
	@Autowired
	private UserAttentionDaoI userAttentionDao;

	@Resource
	private RedisUserServiceImpl redisUserService;

	@Autowired
	private UserProfileServiceI userProfileService;

	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("pwd", MD5Util.md5(user.getPwd()));
		String where = "";
		if(!F.empty(user.getUtype())) {
			params.put("utype", user.getUtype());
			where += " and t.utype = :utype";
		}
		Tuser t = userDao.get("from Tuser t where t.name = :name and t.pwd = :pwd" + where, params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			//更新userprofile中的登录位置
			UserProfile userProfile = new UserProfile();
			userProfile.setId(user.getId());
			userProfile.setLoginArea(user.getLoginArea());
			userProfile.setLgX(user.getLgX());
			userProfile.setLgY(user.getLgY());
			userProfile.setLastLoginTime(new Date());
			userProfileService.edit(userProfile);
			return user;
		}
		return null;
	}

	@Override
	synchronized public void reg(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String validateCode = redisUserService.getValidateCode(user.getMobile());
		if(F.empty(user.getValidateCode())||!user.getValidateCode().equals(validateCode)){
			throw new Exception(Application.getString("EX0002"));
		}
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name", params) > 0) {
			throw new Exception("登录名已存在！");
		}
		if(!F.empty(user.getMobile())) {
			params = new HashMap<String, Object>();
			params.put("mobile", user.getMobile());
			if (userDao.count("select count(*) from Tuser t where t.mobile = :mobile", params) > 0) {
				throw new Exception("手机号已存在！");
			}
		}
		params = new HashMap<String, Object>();
		params.put("email", user.getEmail());
		if(!F.empty(user.getEmail())
				&& userDao.count("select count(*) from Tuser t where t.email = :email", params) > 0) {
			throw new Exception("邮箱已被使用！");
		}
		params = new HashMap<String, Object>();
		params.put("nickname", user.getNickname());
		if(!F.empty(user.getNickname())
				&& userDao.count("select count(*) from Tuser t where t.nickname = :nickname", params) > 0) {
			throw new Exception("昵称已被使用！");
		}
		params = new HashMap<String, Object>();
		params.put("recommend", user.getRecommend());
		if(!F.empty(user.getRecommend())) {
			Tuser ta = userDao.get("from Tuser t where t.name = :recommend", params);
			if(ta != null) {
				user.setRecommend(ta.getId());
			} else {
				user.setRecommend(null);
			}
		}
		
		if(F.empty(user.getNickname())) {
			user.setNickname(user.getName());
		}
		
		Tuser u = new Tuser();
		user.setId(UUID.randomUUID().toString());
		user.setPwd(MD5Util.md5(user.getPwd()));
		user.setCreatedatetime(new Date());
		BeanUtils.copyProperties(user, u);
		u.setUtype("UT02");
		userDao.save(u);
	}

	@Override
	public void updatePwd(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile",user.getMobile());
		Tuser tuser = userDao.get("from Tuser t  where t.mobile = :mobile", params);
		if (tuser!=null) {
			tuser.setPwd(MD5Util.md5(user.getPwd()));
			tuser.setModifydatetime(new Date());
		}
	}

	@Override
	public DataGrid dataGrid(User user, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		List<Tuser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				Set<Trole> roles = t.getTroles();
				if (roles != null && !roles.isEmpty()) {
					String roleIds = "";
					String roleNames = "";
					boolean b = false;
					for (Trole tr : roles) {
						if (b) {
							roleIds += ",";
							roleNames += ",";
						} else {
							b = true;
						}
						roleIds += tr.getId();
						roleNames += tr.getName();
					}
					u.setRoleIds(roleIds);
					u.setRoleNames(roleNames);
				}
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql + whereHql(user, params), params));
		return dg;
	}

	private String whereHql(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where 1=1 ";
			if (user.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + user.getName() + "%%");
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", user.getModifydatetimeEnd());
			}
			if (!F.empty(user.getEmail())) {
				hql += " and t.email = :email";
				params.put("email", user.getEmail());
			}
			if (!F.empty(user.getNickname())) {
				hql += " and t.nickname = :nickname";
				params.put("nickname", user.getNickname());
			}
			if (!F.empty(user.getUtype())) {
				hql += " and t.utype = :utype";
				params.put("utype", user.getUtype());
			}
		}
		return hql;
	}

	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	synchronized public void add(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			BeanUtils.copyProperties(user, u);
			u.setCreatedatetime(new Date());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setUtype("UT01");
			userDao.save(u);
		}
	}

	@Override
	public User get(String id) {
		return get(id, false);
	}
	
	@Override
	public User get(String id, boolean isBshootUser) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("select distinct t from Tuser t left join fetch t.troles role where t.id = :id", params);
		User u = new User();
		BeanUtils.copyProperties(t, u);
		if(!isBshootUser) {
			if (t.getTroles() != null && !t.getTroles().isEmpty()) {
				String roleIds = "";
				String roleNames = "";
				boolean b = false;
				for (Trole role : t.getTroles()) {
					if (b) {
						roleIds += ",";
						roleNames += ",";
					} else {
						b = true;
					}
					roleIds += role.getId();
					roleNames += role.getName();
				}
				u.setRoleIds(roleIds);
				u.setRoleNames(roleNames);
			}
		}
		return u;
	}
	
	public User get(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(user, params);
		Tuser t = userDao.get("from Tuser t " + whereHql, params);
		if(t != null) {
			BeanUtils.copyProperties(t, user);
			return user;
		}
		
		return null;
	}

	@Override
	public List<User> getUsers(List<String> userIds) {
		if(CollectionUtils.isEmpty(userIds)) return null;
		Map<String,Object> parmas = new HashMap<String, Object>();
		List<String> uniqueIds = new ArrayList<String>();
		for(String id:userIds){
			if(!uniqueIds.contains(id))
				uniqueIds.add(id);
		}
		parmas.put("alist", uniqueIds);
		List<Tuser> t = userDao.find("from Tuser t where t.id in (:alist)",parmas);
		List<User> userList = new ArrayList<User>();
		for(Tuser tUser:t){
			User u = new User();
			BeanUtils.copyProperties(tUser, u);
			userList.add(u);
		}
		return userList;
	}

	@Override
	synchronized public void edit(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name and t.id != :id", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = userDao.get(Tuser.class, user.getId());
			MyBeanUtils.copyProperties(user, u, new String[] { "pwd", "createdatetime" }, true);
			u.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userDao.delete(userDao.get(Tuser.class, id));
	}
	
	/**
	 * 修改时检测属性是否存在
	 */
	public boolean exists(User user) {
		String userId = user.getId();
		user.setId(null);
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(user, params);
		Tuser t = userDao.get("from Tuser t " + whereHql, params);
		if(t != null && !t.getId().equals(userId)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void grant(String ids, User user) {
		if (ids != null && ids.length() > 0) {
			List<Trole> roles = new ArrayList<Trole>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleDao.get(Trole.class, roleId));
				}
			}
			for (String id : ids.split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					Tuser t = userDao.get(Tuser.class, id);
					t.setTroles(new HashSet<Trole>(roles));
				}
			}
		}
	}

	@Override
	public List<String> resourceList(String id) {
		List<String> resourceList = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("from Tuser t join fetch t.troles role join fetch role.tresources resource where t.id = :id", params);
		if (t != null) {
			Set<Trole> roles = t.getTroles();
			if (roles != null && !roles.isEmpty()) {
				for (Trole role : roles) {
					Set<Tresource> resources = role.getTresources();
					if (resources != null && !resources.isEmpty()) {
						for (Tresource resource : resources) {
							if (resource != null && resource.getUrl() != null) {
								resourceList.add(resource.getUrl());
							}
						}
					}
				}
			}
		}
		return resourceList;
	}

	@Override
	public void editPwd(User user) {
		if (user != null && user.getPwd() != null && !user.getPwd().trim().equalsIgnoreCase("")) {
			Tuser u = userDao.get(Tuser.class, user.getId());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setModifydatetime(new Date());
		}
	}

	@Override
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) {
		Tuser u = userDao.get(Tuser.class, sessionInfo.getId());
		if (u.getPwd().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			u.setPwd(MD5Util.md5(pwd));
			u.setModifydatetime(new Date());
			return true;
		}
		return false;
	}

	@Override
	public List<User> loginCombobox(String q) {
		if (q == null) {
			q = "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		List<Tuser> tl = userDao.find("from Tuser t where t.name like :name order by name", params, 1, 10);
		List<User> ul = new ArrayList<User>();
		if (tl != null && tl.size() > 0) {
			for (Tuser t : tl) {
				User u = new User();
				u.setName(t.getName());
				ul.add(u);
			}
		}
		return ul;
	}

	@Override
	public DataGrid loginCombogrid(String q, PageHelper ph) {
		if (q == null) {
			q = "";
		}
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		List<Tuser> tl = userDao.find("from Tuser t where t.name like :name order by " + ph.getSort() + " " + ph.getOrder(), params, ph.getPage(), ph.getRows());
		if (tl != null && tl.size() > 0) {
			for (Tuser t : tl) {
				User u = new User();
				u.setName(t.getName());
				u.setCreatedatetime(t.getCreatedatetime());
				u.setModifydatetime(t.getModifydatetime());
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) from Tuser t where t.name like :name", params));
		return dg;
	}

	@Override
	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("s", k);
			params.put("e", k + 2);
			k = k + 2;
			l.add(userDao.count("select count(*) from Tuser t where HOUR(t.createdatetime)>=:s and HOUR(t.createdatetime)<:e", params));
		}
		return l;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> userIndex(String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bUserId", userId);
		params.put("bzUserId", userId);
		params.put("uaUserId", userId);
		params.put("attUserId", userId);
		params.put("pbUserId", userId);
		String sql = "select (select count(*) from bshoot b where b.user_id = :bUserId and b.status=1 and b.parent_id is null) as bsNum,"
				+ "(select count(*) from bshoot b where b.user_id = :bzUserId and b.status=1 and b.parent_id is not null) as bszNum,"
				+ "(select count(*) from user_attention ua where ua.user_id = :uaUserId) as uaNum,"
				+ "(select count(*) from user_attention ua1 where ua1.att_user_id = :attUserId) as uaedNum,"
				+ "(select sum(pb.bs_praise) from bshoot pb where pb.status=1 and pb.user_id = :pbUserId) as pbsNum";
		List<Map> list = userDao.findBySql2Map(sql, params, 0, 1);
		if(list!=null&&list.size()>0) 
			return list.get(0);
		return null;
	}

	@Override
	public DataGrid dataGridHobby(User user, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tuser t WHERE NOT exists (SELECT 1 FROM TuserAttention ua WHERE ua.attUserId=t.id and ua.userId = :userId) and t.utype='UT02'";
		params.put("userId", user.getId());
		List<Tuser> l = userDao.find(hql, params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);				
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql , params));
		return dg;
	}

	@Override
	public DataGrid dataGridForApi(User user, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tuser t ";
		List<Tuser> l = userDao.find(hql + whereHqlApi(user, params), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);				
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql + whereHqlApi(user, params), params));
		return dg;
	}

	private String whereHqlApi(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where t.utype = 'UT02' and NOT exists (SELECT 1 FROM TuserAttention ua WHERE ua.attUserId=t.id and ua.userId = :userId)";
			params.put("userId", user.getId());
			if (user.getName() != null) {
				hql += " and (t.name like :name or t.nickname like :nickname)";
				params.put("name", "%%" + user.getName() + "%%");
				params.put("nickname", "%%" + user.getName() + "%%");
			}
			if (user.getIsStar() != null) {
				hql += " and t.isStar = :isStar";
				params.put("isStar", user.getIsStar());
			}
			if (user.getIsTarento()!= null) {
				hql += " and t.isTarento = :isTarento";
				params.put("isTarento", user.getIsTarento());
			}
		}
		return hql;
	}

	@Override
	public DataGrid dataGridNewFriend(User user, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tuser t where exists (SELECT 1 FROM Tmessage m WHERE m.relationId=t.id and m.mtype = :mType and m.userId = :userId)";
		params.put("userId", user.getId());
		params.put("mType", "MT01");
		List<Tuser> l = userDao.find(hql, params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);				
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql, params));
		return dg;
	}

	/**
	 * 首页搜索（相关用户）
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public DataGrid dataGridSearch(User user, PageHelper ph, String userId) {
		DataGrid dg = new DataGrid();
		String sql = "select t.id id, t.head_image headImage, t.nickname nickname, t.member_v memberV, t.area area, t.sex sex, "
				+ "(select count(*) from user_attention ua where ua.att_user_id = t.id) as uaedNum from tuser t ";
		String where = " where t.utype='UT02' ";
		Map<String, Object> params = new HashMap<String, Object>();
		if(!F.empty(user.getNickname())) {
			where += " and t.nickname like :nickname";
			params.put("nickname", "%%" + user.getNickname() + "%%");
		}
		
		dg.setTotal(userDao.count("select count(*) from Tuser t " + where, params));
		List<Map> l = userDao.findBySql2Map(sql + where + " order by uaedNum desc", params, ph.getPage(), ph.getRows());
		if(l!=null&&l.size()>0){
			String[] attUserIds = new String[l.size()];
			int i = 0;
			for(Map m :l){
				attUserIds[i++] = (String)m.get("id");
				m.put("headImage", PathUtil.getHeadImagePath((String)m.get("headImage")));
				m.put("attred", Constants.GLOBAL_BOOLEAN_FALSE);
			}
			if(userId != null) {
				List<TuserAttention> list = userAttentionDao.getTuserAttentions(userId, attUserIds);
				Map<String,String> map = new HashMap<String,String>();
				for(TuserAttention t : list){
					map.put(t.getAttUserId(), t.getAttUserId());
				}
				for(Map m :l){
					if(map.get((String)m.get("id")) != null){
						m.put("attred", Constants.GLOBAL_BOOLEAN_TRUE);
					}else{
						m.put("attred", Constants.GLOBAL_BOOLEAN_FALSE);
					}
				}
			} 
		}	
		dg.setRows(l);
		return dg;
	}

}

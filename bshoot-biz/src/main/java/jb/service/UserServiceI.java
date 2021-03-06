package jb.service;

import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.SessionInfo;
import jb.pageModel.User;

import java.util.List;
import java.util.Map;

/**
 * 用户Service
 * 
 * @author John
 * 
 */
public interface UserServiceI {

	/**
	 * 用户登录
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @return 用户对象
	 */
	public User login(User user);

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @throws Exception
	 */
	public void reg(User user) throws Exception;

	/**
	 * 修改密码
	 * @param user
	 */
	public void updatePwd(User user);

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	public DataGrid dataGrid(User user, PageHelper ph);
	
	/**
	 * 感兴趣的
	 * @param user
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridHobby(User user, PageHelper ph);
	
	/**
	 * api查询用的
	 * @param user
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridForApi(User user, PageHelper ph);
	
	/**
	 * 新好友
	 * @param user
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridNewFriend(User user, PageHelper ph);
	
	/**
	 * 首页搜索（相关用户）
	 * @param user
	 * @param ph
	 * @param userId
	 * @return
	 */
	public DataGrid dataGridSearch(User user, PageHelper ph, String userId);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void add(User user) throws Exception;

	/**
	 * 获得用户对象
	 * 
	 * @param id
	 * @return
	 */
	public User get(String id);
	
	/**
	 * 获得用户对象
	 * @param id
	 * @param isBshootUser 是否美拍用户
	 * @return
	 */
	public User get(String id, boolean isBshootUser);
	
	public User get(User user);

	/**
	 * 根据多个用户id获得用户信息
	 * @param userIds
	 * @return
	 */
	public List<User> getUsers(List<String> userIds);

	/**
	 * 编辑用户
	 * 
	 * @param user
	 */
	public void edit(User user) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	public void delete(String id);
	
	public boolean exists(User user);

	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @param user
	 *            需要user.roleIds的属性值
	 */
	public void grant(String ids, User user);

	/**
	 * 获得用户能访问的资源地址
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public List<String> resourceList(String id);

	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 */
	public void editPwd(User user);

	/**
	 * 修改用户自己的密码
	 * 
	 * @param sessionInfo
	 * @param oldPwd
	 * @param pwd
	 * @return
	 */
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);

	/**
	 * 用户登录时的autocomplete
	 * 
	 * @param q
	 *            参数
	 * @return
	 */
	public List<User> loginCombobox(String q);

	/**
	 * 用户登录时的combogrid
	 * 
	 * @param q
	 * @param ph
	 * @return
	 */
	public DataGrid loginCombogrid(String q, PageHelper ph);

	/**
	 * 用户创建时间图表
	 * 
	 * @return
	 */
	public List<Long> userCreateDatetimeChart();
		
	
	/**
	 * 我首页
	 * @param userId
	 * @return
	 */
	public Map<String,Object> userIndex(String userId);


	User getUserInfo(String userId);
}

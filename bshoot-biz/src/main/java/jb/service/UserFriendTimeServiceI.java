package jb.service;

import jb.pageModel.UserFriendTime;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface UserFriendTimeServiceI {

	/**
	 * 获取UserFriendTime数据表格
	 * 
	 * @param userFriendTime
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(UserFriendTime userFriendTime, PageHelper ph);

	/**
	 * 添加UserFriendTime
	 * 
	 * @param userFriendTime
	 */
	public void add(UserFriendTime userFriendTime);

	/**
	 * 获得UserFriendTime对象
	 * 
	 * @param id
	 * @return
	 */
	public UserFriendTime get(String id);

	/**
	 * 修改UserFriendTime
	 * 
	 * @param userFriendTime
	 */
	public void edit(UserFriendTime userFriendTime);

	/**
	 * 删除UserFriendTime
	 * 
	 * @param id
	 */
	public void delete(String id);

}

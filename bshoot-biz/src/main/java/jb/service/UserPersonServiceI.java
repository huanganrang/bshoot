package jb.service;

import jb.pageModel.UserPerson;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface UserPersonServiceI {

	/**
	 * 获取UserPerson数据表格
	 * 
	 * @param userPerson
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(UserPerson userPerson, PageHelper ph);

	/**
	 * 添加UserPerson
	 * 
	 * @param userPerson
	 */
	public void add(UserPerson userPerson);

	/**
	 * 获得UserPerson对象
	 * 
	 * @param id
	 * @return
	 */
	public UserPerson get(String id);

	/**
	 * 修改UserPerson
	 * 
	 * @param userPerson
	 */
	public void edit(UserPerson userPerson);

	/**
	 * 删除UserPerson
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 删除人脉圈好友分组
	 *
	 * @param id
	 */
	public void delUserPersonGroup(String id);
}

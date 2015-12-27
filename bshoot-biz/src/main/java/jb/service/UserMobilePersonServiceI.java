package jb.service;

import jb.pageModel.UserMobilePerson;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface UserMobilePersonServiceI {

	/**
	 * 获取UserMobilePerson数据表格
	 * 
	 * @param userMobilePerson
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(UserMobilePerson userMobilePerson, PageHelper ph);

	/**
	 * 添加UserMobilePerson
	 * 
	 * @param userMobilePerson
	 */
	public void add(UserMobilePerson userMobilePerson);

	/**
	 * 获得UserMobilePerson对象
	 * 
	 * @param id
	 * @return
	 */
	public UserMobilePerson get(String id);

	/**
	 * 修改UserMobilePerson
	 * 
	 * @param userMobilePerson
	 */
	public void edit(UserMobilePerson userMobilePerson);

	/**
	 * 删除UserMobilePerson
	 * 
	 * @param id
	 */
	public void delete(String id);

}

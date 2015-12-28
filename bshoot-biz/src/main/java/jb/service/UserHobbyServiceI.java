package jb.service;

import jb.pageModel.UserHobby;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface UserHobbyServiceI {

	/**
	 * 获取UserHobby数据表格
	 * 
	 * @param userHobby
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(UserHobby userHobby, PageHelper ph);

	/**
	 * 添加UserHobby
	 * 
	 * @param userHobby
	 */
	public void add(UserHobby userHobby);

	/**
	 * 获得UserHobby对象
	 * 
	 * @param id
	 * @return
	 */
	public UserHobby get(String id);

	/**
	 * 修改UserHobby
	 * 
	 * @param userHobby
	 */
	public void edit(UserHobby userHobby);

	/**
	 * 删除UserHobby
	 * 
	 * @param id
	 */
	public void delete(String id);

}
package jb.service;

import jb.pageModel.UserPersonTime;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface UserPersonTimeServiceI {

	/**
	 * 获取UserPersonTime数据表格
	 * 
	 * @param userPersonTime
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(UserPersonTime userPersonTime, PageHelper ph);

	/**
	 * 添加UserPersonTime
	 * 
	 * @param userPersonTime
	 */
	public void add(UserPersonTime userPersonTime);

	/**
	 * 获得UserPersonTime对象
	 * 
	 * @param id
	 * @return
	 */
	public UserPersonTime get(String id);

	/**
	 * 修改UserPersonTime
	 * 
	 * @param userPersonTime
	 */
	public void edit(UserPersonTime userPersonTime);

	/**
	 * 删除UserPersonTime
	 * 
	 * @param id
	 */
	public void delete(String id);

}

package jb.service;

import jb.pageModel.UserProfile;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface UserProfileServiceI {

	/**
	 * 获取UserProfile数据表格
	 * 
	 * @param userProfile
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(UserProfile userProfile, PageHelper ph);

	/**
	 * 添加UserProfile
	 * 
	 * @param userProfile
	 */
	public void add(UserProfile userProfile);

	/**
	 * 获得UserProfile对象
	 * 
	 * @param id
	 * @return
	 */
	public UserProfile get(String id);

	/**
	 * 修改UserProfile
	 * 
	 * @param userProfile
	 */
	public void edit(UserProfile userProfile);

	/**
	 * 删除UserProfile
	 * 
	 * @param id
	 */
	public void delete(String id);

}

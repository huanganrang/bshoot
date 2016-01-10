package jb.service;

import jb.pageModel.UserMobilePerson;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.UserMobilePersonRequest;

import java.util.List;

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

	/**
	 * 我未关注的手机联系人
	 * @return
	 */
	public List<String> notAttMobilePerson(UserMobilePersonRequest request);

	public List<String> noAttMobilePersonPerson(UserMobilePersonRequest request);

	/**
	 * 添加添加进入手机通讯录
	 *
	 * @param userMobilePerson
	 */
	public int addMobilePerson(UserMobilePerson userMobilePerson);

	/**
	 * 删除手机通讯录
	 *
	 * @param userMobilePerson
	 */
	public int deleteMobilePerson(UserMobilePerson userMobilePerson);

	/**
	 * 查询已注册但未关注的手机联系人
	 *
	 * @param userMobilePerson
	 */
	public DataGrid dataGridRegMobilePerson(UserMobilePerson userMobilePerson, PageHelper ph);

	/**
	 * 查询未注册但的手机联系人
	 *
	 * @param userMobilePerson
	 */
	public DataGrid dataGridNoRegMobilePerson(UserMobilePerson userMobilePerson, PageHelper ph);
}

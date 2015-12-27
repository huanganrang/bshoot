package jb.service;

import jb.pageModel.UserPraiseRecord;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface UserPraiseRecordServiceI {

	/**
	 * 获取UserPraiseRecord数据表格
	 * 
	 * @param userPraiseRecord
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(UserPraiseRecord userPraiseRecord, PageHelper ph);

	/**
	 * 添加UserPraiseRecord
	 * 
	 * @param userPraiseRecord
	 */
	public void add(UserPraiseRecord userPraiseRecord);

	/**
	 * 获得UserPraiseRecord对象
	 * 
	 * @param id
	 * @return
	 */
	public UserPraiseRecord get(String id);

	/**
	 * 修改UserPraiseRecord
	 * 
	 * @param userPraiseRecord
	 */
	public void edit(UserPraiseRecord userPraiseRecord);

	/**
	 * 删除UserPraiseRecord
	 * 
	 * @param id
	 */
	public void delete(String id);

}

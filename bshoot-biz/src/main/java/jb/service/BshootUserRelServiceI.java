package jb.service;

import jb.pageModel.BshootUserRel;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface BshootUserRelServiceI {

	/**
	 * 获取BshootUserRel数据表格
	 * 
	 * @param bshootUserRel
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(BshootUserRel bshootUserRel, PageHelper ph);

	/**
	 * 添加BshootUserRel
	 * 
	 * @param bshootUserRel
	 */
	public void add(BshootUserRel bshootUserRel);

	/**
	 * 获得BshootUserRel对象
	 * 
	 * @param id
	 * @return
	 */
	public BshootUserRel get(String id);

	/**
	 * 修改BshootUserRel
	 * 
	 * @param bshootUserRel
	 */
	public void edit(BshootUserRel bshootUserRel);

	/**
	 * 删除BshootUserRel
	 * 
	 * @param id
	 */
	public void delete(String id);

}

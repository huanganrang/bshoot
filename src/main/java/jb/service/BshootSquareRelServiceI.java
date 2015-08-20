package jb.service;

import jb.pageModel.BshootSquareRel;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface BshootSquareRelServiceI {

	/**
	 * 获取BshootSquareRel数据表格
	 * 
	 * @param bshootSquareRel
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(BshootSquareRel bshootSquareRel, PageHelper ph);

	/**
	 * 添加BshootSquareRel
	 * 
	 * @param bshootSquareRel
	 */
	public void add(BshootSquareRel bshootSquareRel);

	/**
	 * 获得BshootSquareRel对象
	 * 
	 * @param id
	 * @return
	 */
	public BshootSquareRel get(String id);

	/**
	 * 修改BshootSquareRel
	 * 
	 * @param bshootSquareRel
	 */
	public void edit(BshootSquareRel bshootSquareRel);

	/**
	 * 删除BshootSquareRel
	 * 
	 * @param id
	 */
	public void delete(String id);

}

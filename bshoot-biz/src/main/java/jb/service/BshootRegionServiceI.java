package jb.service;

import jb.pageModel.BshootRegion;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface BshootRegionServiceI {

	/**
	 * 获取BshootRegion数据表格
	 * 
	 * @param bshootRegion
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(BshootRegion bshootRegion, PageHelper ph);

	/**
	 * 添加BshootRegion
	 * 
	 * @param bshootRegion
	 */
	public void add(BshootRegion bshootRegion);

	/**
	 * 获得BshootRegion对象
	 * 
	 * @param id
	 * @return
	 */
	public BshootRegion get(Integer id);

	/**
	 * 修改BshootRegion
	 * 
	 * @param bshootRegion
	 */
	public void edit(BshootRegion bshootRegion);

	/**
	 * 删除BshootRegion
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	public List<BshootRegion> findAllByParams(BshootRegion bshootRegion);

}

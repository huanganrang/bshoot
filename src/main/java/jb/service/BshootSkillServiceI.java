package jb.service;

import jb.pageModel.BshootSkill;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface BshootSkillServiceI {

	/**
	 * 获取BshootSkill数据表格
	 * 
	 * @param bshootSkill
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(BshootSkill bshootSkill, PageHelper ph);

	/**
	 * 添加BshootSkill
	 * 
	 * @param bshootSkill
	 */
	public void add(BshootSkill bshootSkill);

	/**
	 * 获得BshootSkill对象
	 * 
	 * @param id
	 * @return
	 */
	public BshootSkill get(String id);

	/**
	 * 修改BshootSkill
	 * 
	 * @param bshootSkill
	 */
	public void edit(BshootSkill bshootSkill);

	/**
	 * 删除BshootSkill
	 * 
	 * @param id
	 */
	public void delete(String id);

}

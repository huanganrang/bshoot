package jb.service;

import jb.pageModel.BshootCollect;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.Date;
import java.util.Map;

/**
 * 
 * @author John
 * 
 */
public interface BshootCollectServiceI {

	/**
	 * 获取BshootCollect数据表格
	 * 
	 * @param bshootCollect
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(BshootCollect bshootCollect, PageHelper ph);

	/**
	 * 添加BshootCollect
	 * 
	 * @param bshootCollect
	 */
	public int add(BshootCollect bshootCollect,String currentUser);
	
	/**
	 * 取消关注
	 * @param bshootCollect
	 * @return
	 */
	public int deleteCollect(BshootCollect bshootCollect);

	//分组统计
	Map<String,Integer> countGroup(Date begin, Date end);

	/**
	 * 获得BshootCollect对象
	 * 
	 * @param id
	 * @return
	 */
	public BshootCollect get(String id);
	
	/**
	 * 获得BshootCollect对象
	 * 
	 * @param id
	 * @return
	 */
	public BshootCollect get(String bshootId, String userId);

	/**
	 * 修改BshootCollect
	 * 
	 * @param bshootCollect
	 */
	public void edit(BshootCollect bshootCollect);

	/**
	 * 删除BshootCollect
	 * 
	 * @param id
	 */
	public void delete(String id);

}

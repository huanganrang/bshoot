package jb.service;

import jb.pageModel.BshootPraise;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface BshootPraiseServiceI {

	/**
	 * 获取BshootPraise数据表格
	 * 
	 * @param bshootPraise
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(BshootPraise bshootPraise, PageHelper ph);
	
	/**
	 * 添加BshootPraise
	 * 
	 * @param bshootPraise
	 */
	public int add(BshootPraise bshootPraise);
	
	/**
	 * 取消赞
	 * @param bshootPraise
	 */
	public int deleteBshootPraise(BshootPraise bshootPraise);

	/**
	 * 获得BshootPraise对象
	 * 
	 * @param id
	 * @return
	 */
	public BshootPraise get(String id);

	/**
	 * 修改BshootPraise
	 * 
	 * @param bshootPraise
	 */
	public void edit(BshootPraise bshootPraise);

	/**
	 * 删除BshootPraise
	 * 
	 * @param id
	 */
	public void delete(String id);
	
	public BshootPraise get(String bshootId, String userId) ;

	List<String> mePraiseCommentUser(String userId, int start, int rows);

	List<String> friendHasPraisedUser(String userId, int start, int rows);

	List<String> singleFriendHasPraisedUser(String userId, int start, int rows);
}

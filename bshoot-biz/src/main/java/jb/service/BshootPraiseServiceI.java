package jb.service;

import jb.pageModel.BshootPraise;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.pageModel.PraiseCommentRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	public int add(BshootPraise bshootPraise,String currentUser);
	
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

	//分组统计
	Map<String,Integer> countGroup(Date begin, Date end);

	public BshootPraise get(String bshootId, String userId) ;

	List<String> mePraiseCommentUser(PraiseCommentRequest praiseCommentRequest);

	List<String> friendHasPraisedUser(PraiseCommentRequest praiseCommentRequest);

	List<String> singleFriendHasPraisedUser(PraiseCommentRequest praiseCommentRequest);
}

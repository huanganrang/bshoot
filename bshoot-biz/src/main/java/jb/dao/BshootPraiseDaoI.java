package jb.dao;

import jb.model.TbshootPraise;

import java.util.Date;
import java.util.List;

/**
 * BshootPraise数据库操作类
 * 
 * @author John
 * 
 */
public interface BshootPraiseDaoI extends BaseDaoI<TbshootPraise> {
	public List<TbshootPraise> getTbshootPraises(String userId, String[] bshootIds);

	/**
	 * 好友打赏过的人
	 * @param userId
	 * @param start
	 * @param rows
	 * @return
	 */
	public List<String> friendHasPraisedUser(String userId,int start,int rows);

	public List<String> singleFriendHasPraisedUser(String userId,int start,int rows);

	/**
	 * 我评论打赏过的人
	 * @param userId
	 * @param start
	 * @param rows
	 * @return
	 */
	public List<String> mePraiseCommentUser(String userId,int start,int rows);
}

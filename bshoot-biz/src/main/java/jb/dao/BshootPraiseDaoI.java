package jb.dao;

import jb.model.TbshootPraise;
import jb.pageModel.PraiseCommentRequest;

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
	 * @return
	 */
	public List<String> friendHasPraisedUser(PraiseCommentRequest praiseCommentRequest);

	public List<String> singleFriendHasPraisedUser(PraiseCommentRequest praiseCommentRequest);

	/**
	 * 我评论打赏过的人
	 * @return
	 */
	public List<String> mePraiseCommentUser(PraiseCommentRequest praiseCommentRequest);
}

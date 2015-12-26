package jb.dao;

import jb.model.TcommentPraise;

import java.util.List;

/**
 * CommentPraise数据库操作类
 * 
 * @author John
 * 
 */
public interface CommentPraiseDaoI extends BaseDaoI<TcommentPraise> {
	public List<TcommentPraise> getTcommentPraises(String userId, String[] commentIds);
}

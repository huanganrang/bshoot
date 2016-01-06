package jb.dao;

import jb.model.TuserAttention;

import java.util.List;

/**
 * UserAttention数据库操作类
 * 
 * @author John
 * 
 */
public interface UserAttentionDaoI extends BaseDaoI<TuserAttention> {

	public List<TuserAttention> getTuserAttentions(String userId, String[] attUserIds);

	List<String> friendCommonAtt(String userId, int start,int rows);

	List<String> singleFriednAtt(String userId,int start,int rows);
}

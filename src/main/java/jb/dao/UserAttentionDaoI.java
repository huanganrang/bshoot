package jb.dao;

import java.util.List;

import jb.model.TuserAttention;

/**
 * UserAttention数据库操作类
 * 
 * @author John
 * 
 */
public interface UserAttentionDaoI extends BaseDaoI<TuserAttention> {

	public List<TuserAttention> getTuserAttentions(String userId, String[] attUserIds);

}

package jb.dao;

import jb.model.TuserAttention;
import jb.pageModel.AttentionRequest;

import java.util.List;

/**
 * UserAttention数据库操作类
 * 
 * @author John
 * 
 */
public interface UserAttentionDaoI extends BaseDaoI<TuserAttention> {

	public List<TuserAttention> getTuserAttentions(String userId, String[] attUserIds);

	List<String> friendCommonAtt(AttentionRequest attentionRequest);

	List<String> singleFriendAtt(AttentionRequest attentionRequest);
}

package jb.dao;

import jb.model.TbshootComment;

import java.util.List;

/**
 * BshootComment数据库操作类
 * 
 * @author John
 * 
 */
public interface BshootCommentDaoI extends BaseDaoI<TbshootComment> {

	public List<TbshootComment> getTbshootComments(List<String> idList);

}

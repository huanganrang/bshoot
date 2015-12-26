package jb.dao;

import java.util.List;

import jb.model.TbshootComment;

/**
 * BshootComment数据库操作类
 * 
 * @author John
 * 
 */
public interface BshootCommentDaoI extends BaseDaoI<TbshootComment> {

	public List<TbshootComment> getTbshootComments(List<String> idList);

}

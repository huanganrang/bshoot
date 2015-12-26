package jb.dao;

import jb.model.TbshootPraise;

import java.util.List;

/**
 * BshootPraise数据库操作类
 * 
 * @author John
 * 
 */
public interface BshootPraiseDaoI extends BaseDaoI<TbshootPraise> {
	public List<TbshootPraise> getTbshootPraises(String userId, String[] bshootIds);

}

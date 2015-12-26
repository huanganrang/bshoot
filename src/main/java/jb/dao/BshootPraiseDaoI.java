package jb.dao;

import java.util.List;

import jb.model.TbshootPraise;

/**
 * BshootPraise数据库操作类
 * 
 * @author John
 * 
 */
public interface BshootPraiseDaoI extends BaseDaoI<TbshootPraise> {
	public List<TbshootPraise> getTbshootPraises(String userId,String[] bshootIds);

}

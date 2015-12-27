package jb.service;

import jb.model.Tbugtype;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface BugTypeServiceI {

	/**
	 * 获得BUG类型列表
	 * 
	 * @return
	 */
	public List<Tbugtype> getBugTypeList();

}

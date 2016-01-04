package jb.dao;

import jb.model.TuserMobilePerson;

import java.util.List;

/**
 * UserMobilePerson数据库操作类
 * 
 * @author John
 * 
 */
public interface UserMobilePersonDaoI extends BaseDaoI<TuserMobilePerson> {

    List<TuserMobilePerson> notAttMobilePerson(String userId, int start, int rows);

    List<TuserMobilePerson> noAttMobilePersonPerson(String userId,int start,int rows);
}

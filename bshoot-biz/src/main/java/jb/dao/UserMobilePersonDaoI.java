package jb.dao;

import jb.model.TuserMobilePerson;
import jb.pageModel.UserMobilePersonRequest;

import java.util.List;

/**
 * UserMobilePerson数据库操作类
 * 
 * @author John
 * 
 */
public interface UserMobilePersonDaoI extends BaseDaoI<TuserMobilePerson> {

    List<String> notAttMobilePerson(UserMobilePersonRequest request);

    List<String> noAttMobilePersonPerson(UserMobilePersonRequest request);
}

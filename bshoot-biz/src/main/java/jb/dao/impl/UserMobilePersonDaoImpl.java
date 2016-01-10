package jb.dao.impl;

import jb.dao.UserMobilePersonDaoI;
import jb.model.TuserMobilePerson;

import jb.pageModel.UserMobilePersonRequest;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMobilePersonDaoImpl extends BaseDaoImpl<TuserMobilePerson> implements UserMobilePersonDaoI {

    @Override
    public List<String> notAttMobilePerson(UserMobilePersonRequest userMobilePersonRequest) {
        String sort= "";
        if(userMobilePersonRequest.isRand())
            sort=" ORDER BY rand() ";
        String hql="SELECT * FROM user_mobile_person t1 where t1.is_delete!=1 and t1.user_id=:userId and t1.friend_id!=:userId and t1.friend_id is not null and t1.friend_id not in (select att_user_id from user_attention WHERE is_delete!=1 and user_id=:userId) "+sort+" limit :start,:rows";
        Query query = getCurrentSession().createSQLQuery(hql).addEntity(TuserMobilePerson.class);
        query.setParameter("userId", userMobilePersonRequest.getUserId());
        query.setParameter("start",userMobilePersonRequest.getPage());
        query.setParameter("rows",userMobilePersonRequest.getRows());
        List<String> l = query.list();
        return l;
    }

    @Override
    public List<String> noAttMobilePersonPerson(UserMobilePersonRequest userMobilePersonRequest) {
        String hql="select * from user_mobile_person t1 " +
                "where t1.is_delete!=1 and t1.user_id!=:userId and t1.user_id in (select friend_id from user_mobile_person where is_delete!=1 and user_id=:userId and friend_id is not null) " +
                "and t1.friend_id not in(select att_user_id from user_attention where is_delete!=1 and user_id=:userId) " +
                "limit :start,:rows";

        Query query = getCurrentSession().createSQLQuery(hql).addEntity(TuserMobilePerson.class);
        query.setParameter("userId", userMobilePersonRequest.getUserId());
        query.setParameter("start",userMobilePersonRequest.getPage());
        query.setParameter("rows",userMobilePersonRequest.getRows());
        List<String> l = query.list();
        return l;
    }
}

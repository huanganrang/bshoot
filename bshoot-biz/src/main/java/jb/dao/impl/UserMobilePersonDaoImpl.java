package jb.dao.impl;

import jb.dao.UserMobilePersonDaoI;
import jb.model.TuserMobilePerson;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMobilePersonDaoImpl extends BaseDaoImpl<TuserMobilePerson> implements UserMobilePersonDaoI {

    @Override
    public List<TuserMobilePerson> notAttMobilePerson(String userId, int start, int rows) {
        String hql="SELECT * FROM user_mobile_person t1 where t1.user_id=:userId and t1.friend_id!=:userId and t1.friend_id not in (select att_user_id from user_attention WHERE user_id=:userId) limit :start,:rows";
        Query query = getCurrentSession().createSQLQuery(hql).addEntity(TuserMobilePerson.class);
        query.setParameter("userId", userId);
        query.setParameter("start",start);
        query.setParameter("rows",rows);
        List<TuserMobilePerson> l = query.list();
        return l;
    }

    @Override
    public List<TuserMobilePerson> noAttMobilePersonPerson(String userId, int start, int rows) {
        String hql="select * from user_mobile_person t1 " +
                "where t1.user_id and t1.friend_id!=:userId in (select friend_id from user_mobile_person t1 where user_id=:userId) " +
                "and t1.friend_id not in(select att_user_id from user_attention where user_id=:userId) " +
                "limit :start,:rows";
        Query query = getCurrentSession().createSQLQuery(hql).addEntity(TuserMobilePerson.class);
        query.setParameter("userId", userId);
        query.setParameter("start",start);
        query.setParameter("rows",rows);
        List<TuserMobilePerson> l = query.list();
        return l;
    }
}

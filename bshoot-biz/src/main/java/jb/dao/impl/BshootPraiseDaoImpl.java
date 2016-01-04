package jb.dao.impl;

import jb.dao.BshootPraiseDaoI;
import jb.model.TbshootPraise;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BshootPraiseDaoImpl extends BaseDaoImpl<TbshootPraise> implements BshootPraiseDaoI {

	@SuppressWarnings("unchecked")
	@Override
	public List<TbshootPraise> getTbshootPraises(String userId, String[] bshootIds) {
		if(bshootIds==null||bshootIds.length==0)return null;
		String hql="FROM TbshootPraise t WHERE t.bshootId in (:alist) and t.userId = :userId";  
		Query query = getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", bshootIds); 
		query.setParameter("userId", userId);
		List<TbshootPraise> l = query.list();
		return l;
	}

	@Override
	public List<String> friendHasPraisedUser(String userId, int start, int rows) {
		if(userId==null||userId.trim().length()==0)return null;
		String hql="select DISTINCT(bs_userId) from bshoot_praise where user_id in (select att_user_id from user_attention where user_id=:userId) group by bs_userId HAVING count(bs_userId)>1 ORDER BY praise_datetime  limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql).addEntity(String.class);
		query.setParameter("userId", userId);
		query.setParameter("start", start);
		query.setParameter("rows",rows);
		List<String> l = query.list();
		return l;
	}

	@Override
	public List<String> singleFriendHasPraisedUser(String userId, int start, int rows) {
		if(userId==null||userId.trim().length()==0)return null;
		String hql="select DISTINCT(bs_userId) from bshoot_praise where user_id in (select att_user_id from user_attention where user_id=:userId) ORDER BY praise_datetime limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql).addEntity(String.class);
		query.setParameter("userId", userId);
		query.setParameter("start", start);
		query.setParameter("rows",rows);
		List<String> l = query.list();
		return l;
	}

	@Override
	public List<String> mePraiseCommentUser(String userId, int start, int rows) {
		if(userId==null||userId.trim().length()==0)return null;
		String hql="select DISTINCT(bs_userId) as bs_userId,praise_datetime as create_datetime from bshoot_praise where user_id=:userId " +
				"union select DISTINCT(bs_userId) as bs_userId,comment_datetime as create_datetime from bshoot_comment where user_id=:userId " +
				"ORDER BY create_datetime desc limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql).addEntity(String.class);
		query.setParameter("userId", userId);
		query.setParameter("start",start);
		query.setParameter("rows",rows);
		List<String> l = query.list();
		return l;
	}

}

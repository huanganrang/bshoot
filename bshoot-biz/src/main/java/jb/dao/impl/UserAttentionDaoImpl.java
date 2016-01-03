package jb.dao.impl;

import jb.dao.UserAttentionDaoI;
import jb.model.TuserAttention;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAttentionDaoImpl extends BaseDaoImpl<TuserAttention> implements UserAttentionDaoI {

	@SuppressWarnings("unchecked")
	@Override
	public List<TuserAttention> getTuserAttentions(String userId,
			String[] attUserIds) {
		if(attUserIds==null||attUserIds.length==0)return null;
		String hql="FROM TuserAttention t WHERE t.attUserId in (:alist) and t.userId = :userId";
		Query query = getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", attUserIds); 
		query.setParameter("userId", userId);
		List<TuserAttention> l = query.list();
		return l;
	}

	@Override
	public List<TuserAttention> friendCommonAtt(String userId, int start,int rows) {
		String hql="select * from user_attention  where user_id in (SELECT att_user_id from user_attention WHERE user_id=:userId) group by att_user_id HAVING count(att_user_id)>1 limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql).addEntity(TuserAttention.class);
		query.setParameter("userId", userId);
		query.setParameter("start",start);
		query.setParameter("rows",rows);
		List<TuserAttention> l = query.list();
		return l;
	}

}

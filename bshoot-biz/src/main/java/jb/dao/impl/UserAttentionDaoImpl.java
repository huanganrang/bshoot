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

}

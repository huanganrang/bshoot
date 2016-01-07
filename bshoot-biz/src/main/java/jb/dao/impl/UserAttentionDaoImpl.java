package jb.dao.impl;

import jb.dao.UserAttentionDaoI;
import jb.model.TuserAttention;
import jb.pageModel.AttentionRequest;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
	public List<String> friendCommonAtt(AttentionRequest attentionRequest) {
		String hql="select DISTINCT(att_user_id) from user_attention  where att_user_id!=:userId and is_delete!=1 and user_id in (SELECT att_user_id from user_attention WHERE user_id=:userId and is_friend=1) group by att_user_id HAVING count(att_user_id)>1 limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql);
		query.setParameter("userId", attentionRequest.getUserId());
		query.setParameter("start",attentionRequest.getPage());
		query.setParameter("rows",attentionRequest.getRows());
		List<String> l = query.list();
		return l;
	}

	@Override
	public List<String> singleFriednAtt(AttentionRequest attentionRequest) {
		String attentionAfterDate =  "";
		String rand = "";
		if(null!=attentionRequest.getAttentionAfterDate())
			attentionAfterDate = " and attention_datetime>=:attentionDatetime";
		if(attentionRequest.isRand())
			rand = " ORDER BY rand()";
		String hql="select DISTINCT(att_user_id) from user_attention  where att_user_id!=:userId and is_delete!=1 "+attentionAfterDate+" and user_id in (SELECT att_user_id from user_attention WHERE user_id=:userId and is_friend=1) "+rand+" limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql);
		query.setParameter("userId", attentionRequest.getUserId());
		query.setParameter("start",attentionRequest.getPage());
		if(null!=attentionRequest.getAttentionAfterDate())
		 query.setParameter("attentionDatetime",attentionRequest.getAttentionAfterDate());
		query.setParameter("rows",attentionRequest.getRows());
		List<String> l = query.list();
		return l;
	}

}

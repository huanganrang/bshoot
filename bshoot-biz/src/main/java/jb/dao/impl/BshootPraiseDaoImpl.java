package jb.dao.impl;

import jb.dao.BshootPraiseDaoI;
import jb.model.TbshootPraise;
import jb.pageModel.PraiseCommentRequest;
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
	public List<String> friendHasPraisedUser(PraiseCommentRequest praiseCommentRequest) {
		String hql="select DISTINCT(bs_userId) from bshoot_praise where is_delete!=1 and bs_userId!=:userId and user_id in (select att_user_id from user_attention where user_id=:userId and is_friend=1) group by bs_userId HAVING count(bs_userId)>1 ORDER BY praise_datetime  limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql);
		query.setParameter("userId", praiseCommentRequest.getUserId());
		query.setParameter("start", praiseCommentRequest.getPage());
		query.setParameter("rows",praiseCommentRequest.getRows());
		List<String> l = query.list();
		return l;
	}

	@Override
	public List<String> singleFriendHasPraisedUser(PraiseCommentRequest praiseCommentRequest) {
		String praiseCommentAfterDate =  "";
		String sort = " ORDER BY praise_datetime ";
		if(null!=praiseCommentRequest.getPraiseCommentAfterDate())
			praiseCommentAfterDate = " and praise_datetime>=:praiseDateTime";
		if(praiseCommentRequest.isRand())
			sort = " ORDER BY rand() ";

		String hql="select DISTINCT(bs_userId) from bshoot_praise where is_delete!=1 and bs_userId!=:userId "+praiseCommentAfterDate+" and user_id in (select att_user_id from user_attention where user_id=:userId and is_friend=1) "+sort+" limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql);
		query.setParameter("userId", praiseCommentRequest.getUserId());
		query.setParameter("start", praiseCommentRequest.getPage());
		if(null!=praiseCommentRequest.getPraiseCommentAfterDate())
			query.setParameter("praiseDateTime",praiseCommentRequest.getPraiseCommentAfterDate());
		query.setParameter("rows",praiseCommentRequest.getRows());
		List<String> l = query.list();
		return l;
	}

	@Override
	public List<String> mePraiseCommentUser(PraiseCommentRequest praiseCommentRequest) {
		String praiseCommentAfterDate =  "";
		String sort = " ORDER BY t.create_datetime desc ";
		if(null!=praiseCommentRequest.getPraiseCommentAfterDate())
			praiseCommentAfterDate = " where t.create_datetime>=:praiseCommentDateTime";
		if(praiseCommentRequest.isRand())
			sort = " ORDER BY rand() ";

		String hql="select DISTINCT(bs_userId)  from (select bs_userId,praise_datetime as create_datetime from bshoot_praise where is_delete!=1 and user_id=:userId  and bs_userId!=:userId " +
				"union select bs_userId,comment_datetime as create_datetime from bshoot_comment where is_delete!=1 and user_id=:userId and bs_userId!=:userId ) t " +praiseCommentAfterDate+sort+
				" limit :start,:rows";
		Query query = getCurrentSession().createSQLQuery(hql);
		query.setParameter("userId", praiseCommentRequest.getUserId());
		query.setParameter("start", praiseCommentRequest.getPage());
		if(null!=praiseCommentRequest.getPraiseCommentAfterDate())
			query.setParameter("praiseCommentDateTime",praiseCommentRequest.getPraiseCommentAfterDate());
		query.setParameter("rows",praiseCommentRequest.getRows());
		List<String> l = query.list();
		return l;
	}

}

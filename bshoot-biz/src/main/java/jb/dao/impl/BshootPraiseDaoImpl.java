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

}

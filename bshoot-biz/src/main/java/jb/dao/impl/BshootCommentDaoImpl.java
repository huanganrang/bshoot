package jb.dao.impl;

import jb.dao.BshootCommentDaoI;
import jb.model.TbshootComment;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BshootCommentDaoImpl extends BaseDaoImpl<TbshootComment> implements BshootCommentDaoI {

	@SuppressWarnings("unchecked")
	@Override
	public List<TbshootComment> getTbshootComments(List<String> idList) {
		if(idList==null||idList.size()==0)return null;
		String hql="FROM TbshootComment t WHERE t.id in (:alist)";  
		Query query = getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", idList); 
		List<TbshootComment> l = query.list();
		return l;
	}

}

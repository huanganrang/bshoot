package jb.dao.impl;

import jb.dao.UserDaoI;
import jb.model.Tuser;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<Tuser> implements UserDaoI {

	@SuppressWarnings("unchecked")
	@Override
	public List<Tuser> getTusers(String... userids) {
		if(userids==null||userids.length==0)return null;
		String hql="FROM Tuser t WHERE t.id in (:alist)";  
		Query query = getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", userids); 
		List<Tuser> l = query.list();
		return l;
	}

}

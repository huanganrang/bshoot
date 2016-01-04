package jb.dao.impl;

import jb.dao.BshootDaoI;
import jb.model.Tbshoot;
import jb.util.PathUtil;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Repository
public class BshootDaoImpl extends BaseDaoImpl<Tbshoot> implements BshootDaoI {
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map<String,Object>> executeNearby(final int page, final int rows,final double lgx,final double lgy) {
		
		/*List<Map<String,Object>> result = jdbcTemplate.execute(new CallableStatementCreator() {
	        public CallableStatement createCallableStatement(Connection con) throws SQLException {   
	        	String centStr = "POINT("+lgx+" "+lgy+")";
	            String storedProc = "{call bshootnearby (?,?,?)}";// 调用的sql   
	            CallableStatement cs = con.prepareCall(storedProc);   
	            cs.setString(1, centStr);// 设置输入参数的值   
	            cs.setInt(2, (page-1)*rows);
	            cs.setInt(3, rows);
	            return cs;   
	         }   
	      },  new CallableStatementCallback() {   
		        public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
		            cs.execute();   
		            ResultSet rs = cs.getResultSet();  
		            ResultSetMetaData rsmd = rs.getMetaData();
		            Map<String,Object>  temp = null;
		            List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		            String key = null;
		            while(rs.next()){
		            	temp = new HashMap<String,Object>();
		            	for(int i =1;i<=rsmd.getColumnCount();i++){
		            		key = rsmd.getColumnName(i);
		            		temp.put(key, rs.getObject(i));
		            	}
		            	
		            	temp.put("bs_stream", PathUtil.getBshootPath((String)temp.get("bs_stream")));
		            	temp.put("bs_icon", PathUtil.getBshootPath((String)temp.get("bs_icon")));
		            	result.add(temp);
		            }
		            return result;// 获取输出参数的值   
		        } } );*/
		return null;
	}
	
	
	public List<Tbshoot> getTbshoots(String[] bshootIds) {
		if(bshootIds==null||bshootIds.length==0)return null;
		String hql="FROM Tbshoot t WHERE t.id in (:alist)";  
		Query query = getCurrentSession().createQuery(hql);  
		query.setParameterList("alist", bshootIds); 
		List<Tbshoot> l = query.list();
		return l;
	}

	@Override
	public List<Tbshoot> getUserLastBshoot(List<String> userIds) {
		if(userIds==null||userIds.size()==0)return null;
		String hql="select A.* from bshoot A,(SELECT MAX(create_datetime) last_date FROM bshoot where user_id in (:userId) GROUP BY user_id) B where A.create_datetime=B.last_date and AND A.user_id in (:userId) ";
		Query query = getCurrentSession().createSQLQuery(hql).addEntity(Tbshoot.class);
		query.setParameterList("userId", userIds);
		List<Tbshoot> l = query.list();
		return l;
	}

	@Override
	public Tbshoot getUserLastBshoot(String userId) {
		if(userId==null||userId.trim().length()==0)return null;
		String hql="select * from bshoot  where user_id=:userId order by create_datetime desc limit 0,1";
		Query query = getCurrentSession().createSQLQuery(hql).addEntity(Tbshoot.class);
		query.setParameter("userId",userId);
		List<Tbshoot> l = query.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

}

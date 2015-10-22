package jb.dao.impl;

import jb.dao.BshootDaoI;
import jb.model.Tbshoot;
import jb.pageModel.HotShootRequest;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
	public List<Tbshoot> getUserLastBshoot(List<String> userIds,Date dateLimit) {
		if(userIds==null||userIds.size()==0)return null;
		String hql="select A.* from bshoot A,(SELECT MAX(create_datetime) last_date FROM bshoot where isDelete!=1 and user_id in (:userId) GROUP BY user_id) B where A.create_datetime=B.last_date and A.create_datetime>=:dateLimit and  A.user_id in (:userId) ";
		Query query = getCurrentSession().createSQLQuery(hql).addEntity(Tbshoot.class);
		query.setParameterList("userId", userIds);
		query.setParameter("dateLimit",dateLimit);
		List<Tbshoot> l = query.list();
		return l;
	}

	@Override
	public Tbshoot getUserLastBshoot(String userId,Date dateLimit) {
		if(userId==null||userId.trim().length()==0)return null;
		String hql="select * from bshoot  where user_id=:userId  and isDelete!=1 and create_datetime>=:dateLimit order by create_datetime desc limit 0,1";
		Query query = getCurrentSession().createSQLQuery(hql).addEntity(Tbshoot.class);
		query.setParameter("userId",userId);
		query.setParameter("dateLimit",dateLimit);
		List<Tbshoot> l = query.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public List<Tbshoot> getHotBshoots(HotShootRequest hotShootRequest) {
		String hql = null;
		if(hotShootRequest.getFileType()!=null){
			hql="select * from bshoot t  where t.bs_file_type=:fileType and t.isDelete!=1 and t.create_datetime >=:pubTime and t.bs_praise>=:praiseNum order by t.create_datetime desc  limit :start,:rows";
			if(null!=hotShootRequest.getHobby())
				hql="select * from bshoot t ,user_hobby t1  where t.bs_file_type=:fileType and t.isDelete!=1  and t.create_datetime >=:pubTime and t.bs_praise>=:praiseNum and ("+hotShootRequest.getHobby()+")in (t1.hobby_type) and t1.user_id=t.user_id order by t.create_datetime desc  limit :start,:rows";
		}else{
			hql="select * from bshoot t  where t.isDelete!=1 and  t.create_datetime >=:pubTime  and t.bs_praise>=:praiseNum order by t.create_datetime desc  limit :start,:rows";
			if(null!=hotShootRequest.getHobby())
				hql="select * from bshoot t,user_hobby t1  where t.isDelete!=1 and t.create_datetime >=:pubTime and t.bs_praise>=:praiseNum and ("+hotShootRequest.getHobby()+") in (t1.hobby_type) and t1.user_id=t.user_id order by t.create_datetime desc  limit :start,:rows";

		}

		Query query = getCurrentSession().createSQLQuery(hql).addEntity(Tbshoot.class);
		query.setParameter("pubTime", hotShootRequest.getPubTime());
		if(hotShootRequest.getFileType()!=null)
			query.setParameter("fileType", hotShootRequest.getFileType());
		query.setParameter("praiseNum",hotShootRequest.getPraiseNum());
		query.setParameter("start",hotShootRequest.getStart());
		query.setParameter("rows",hotShootRequest.getRows());
		List<Tbshoot> l = query.list();
		return l;
	}

}

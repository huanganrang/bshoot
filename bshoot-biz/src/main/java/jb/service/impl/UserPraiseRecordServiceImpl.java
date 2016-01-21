package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jb.absx.F;
import jb.dao.UserPraiseRecordDaoI;
import jb.model.TuserPraiseRecord;
import jb.pageModel.UserPraiseRecord;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.UserPraiseRecordServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jb.util.MyBeanUtils;

@Service
public class UserPraiseRecordServiceImpl extends BaseServiceImpl<UserPraiseRecord> implements UserPraiseRecordServiceI {

	@Autowired
	private UserPraiseRecordDaoI userPraiseRecordDao;

	@Override
	public DataGrid dataGrid(UserPraiseRecord userPraiseRecord, PageHelper ph) {
		List<UserPraiseRecord> ol = new ArrayList<UserPraiseRecord>();
		String hql = " from TuserPraiseRecord t ";
		DataGrid dg = dataGridQuery(hql, ph, userPraiseRecord, userPraiseRecordDao);
		@SuppressWarnings("unchecked")
		List<TuserPraiseRecord> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TuserPraiseRecord t : l) {
				UserPraiseRecord o = new UserPraiseRecord();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(UserPraiseRecord userPraiseRecord, Map<String, Object> params) {
		String whereHql = "";	
		if (userPraiseRecord != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(userPraiseRecord.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", userPraiseRecord.getUserId());
			}		
			if (!F.empty(userPraiseRecord.getRelationOutid())) {
				whereHql += " and t.relationOutid = :relationOutid";
				params.put("relationOutid", userPraiseRecord.getRelationOutid());
			}		
			if (!F.empty(userPraiseRecord.getRelationChannel())) {
				whereHql += " and t.relationChannel = :relationChannel";
				params.put("relationChannel", userPraiseRecord.getRelationChannel());
			}		

			if (!F.empty(userPraiseRecord.getPraiseType())) {
				whereHql += " and t.praiseType = :praiseType";
				params.put("praiseType", userPraiseRecord.getPraiseType());
			}		

		}	
		return whereHql;
	}

	@Override
	public void add(UserPraiseRecord userPraiseRecord) {
		TuserPraiseRecord t = new TuserPraiseRecord();
		BeanUtils.copyProperties(userPraiseRecord, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		userPraiseRecordDao.save(t);
	}

	@Override
	public UserPraiseRecord get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TuserPraiseRecord t = userPraiseRecordDao.get("from TuserPraiseRecord t  where t.id = :id", params);
		UserPraiseRecord o = new UserPraiseRecord();
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(UserPraiseRecord userPraiseRecord) {
		TuserPraiseRecord t = userPraiseRecordDao.get(TuserPraiseRecord.class, userPraiseRecord.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(userPraiseRecord, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		userPraiseRecordDao.delete(userPraiseRecordDao.get(TuserPraiseRecord.class, id));
	}

}

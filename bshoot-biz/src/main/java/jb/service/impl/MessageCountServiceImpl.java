package jb.service.impl;

import jb.absx.F;
import jb.dao.MessageCountDaoI;
import jb.model.TmessageCount;
import jb.pageModel.DataGrid;
import jb.pageModel.MessageCount;
import jb.pageModel.PageHelper;
import jb.service.MessageCountServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageCountServiceImpl extends BaseServiceImpl<MessageCount> implements MessageCountServiceI {

	@Autowired
	private MessageCountDaoI messageCountDao;

	@Override
	public DataGrid dataGrid(MessageCount messageCount, PageHelper ph) {
		List<MessageCount> ol = new ArrayList<MessageCount>();
		String hql = " from TmessageCount t ";
		DataGrid dg = dataGridQuery(hql, ph, messageCount, messageCountDao);
		@SuppressWarnings("unchecked")
		List<TmessageCount> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TmessageCount t : l) {
				MessageCount o = new MessageCount();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(MessageCount messageCount, Map<String, Object> params) {
		String whereHql = "";	
		if (messageCount != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(messageCount.getMtype())) {
				whereHql += " and t.mtype = :mtype";
				params.put("mtype", messageCount.getMtype());
			}		
			if (!F.empty(messageCount.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", messageCount.getUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(MessageCount messageCount) {
		TmessageCount t = new TmessageCount();
		BeanUtils.copyProperties(messageCount, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		messageCountDao.save(t);
	}

	@Override
	public MessageCount get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TmessageCount t = messageCountDao.get("from TmessageCount t  where t.id = :id", params);
		MessageCount o = new MessageCount();
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(MessageCount messageCount) {
		TmessageCount t = messageCountDao.get(TmessageCount.class, messageCount.getId());
		if (t != null) {
			BeanUtils.copyProperties(messageCount, t, new String[] { "id" , "createdatetime" });
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		messageCountDao.delete(messageCountDao.get(TmessageCount.class, id));
	}


	@Override
	public Map<String, Integer> getMessageCounts(MessageCount messageCount) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		String hql = " from TmessageCount t ";		
		Map<String, Object> params = new HashMap<String, Object>();
		String where = whereHql(messageCount, params);
		List<TmessageCount> l = messageCountDao.find(hql  + where , params);
		if (l != null && l.size() > 0) {
			for (TmessageCount t : l) {
				if("MT01".equals(t.getMtype())) {
					m.put("newFriendNum", t.getMnumber());
				} else if("MT02".equals(t.getMtype())) {
					m.put("atMineNum", t.getMnumber());
				} else if("MT03".equals(t.getMtype())) {
					m.put("commentNum", t.getMnumber());
				} else if("MT04".equals(t.getMtype())) {
					m.put("praiseNum", t.getMnumber());
				}
			}
		}
		if(m.get("newFriendNum") == null) m.put("newFriendNum", 0);
		if(m.get("atMineNum") == null) m.put("atMineNum", 0);
		if(m.get("commentNum") == null) m.put("commentNum", 0);
		if(m.get("praiseNum") == null) m.put("praiseNum", 0);
		return m;
	}


	@Override
	public boolean deleteMessageCount(String userId, String mtype) {
		String hql = "delete from TmessageCount t where t.userId=:userId and t.mtype=:mtype";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("mtype", mtype);
		return messageCountDao.executeHql(hql, params)>0;
	}

}

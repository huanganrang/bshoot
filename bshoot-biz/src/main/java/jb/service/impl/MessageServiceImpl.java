package jb.service.impl;

import com.alibaba.fastjson.JSON;
import component.message.service.IMessageService;
import jb.absx.F;
import jb.dao.MessageCountDaoI;
import jb.dao.MessageDaoI;
import jb.model.Tmessage;
import jb.model.TmessageCount;
import jb.pageModel.DataGrid;
import jb.pageModel.Message;
import jb.pageModel.PageHelper;
import jb.service.MessageServiceI;
import jb.util.PathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageServiceI {

	@Autowired
	private MessageDaoI messageDao;
	@Autowired
	private MessageCountDaoI messageCountDao;

	@Autowired
	private IMessageService messageService;

	@Override
	public DataGrid dataGrid(Message message, PageHelper ph) {
		List<Message> ol = new ArrayList<Message>();
		String hql = " from Tmessage t ";
		DataGrid dg = dataGridQuery(hql, ph, message, messageDao);
		@SuppressWarnings("unchecked")
		List<Tmessage> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (Tmessage t : l) {
				Message o = new Message();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(Message message, Map<String, Object> params) {
		String whereHql = "";	
		if (message != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(message.getMtype())) {
				whereHql += " and t.mtype = :mtype";
				params.put("mtype", message.getMtype());
			}		
			if (!F.empty(message.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", message.getUserId());
			}		
			if (message.getIsRead()!=null) {
				whereHql += " and t.isRead = :isRead";
				params.put("isRead", message.getIsRead());
			}		
			if (!F.empty(message.getRelationId())) {
				whereHql += " and t.relationId = :relationId";
				params.put("relationId", message.getRelationId());
			}		
			if (!F.empty(message.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", message.getContent());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(Message message) {
		Tmessage t = new Tmessage();
		BeanUtils.copyProperties(message, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		messageDao.save(t);
	}

	@Override
	public Message get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tmessage t = messageDao.get("from Tmessage t  where t.id = :id", params);
		Message o = new Message();
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(Message message) {
		Tmessage t = messageDao.get(Tmessage.class, message.getId());
		if (t != null) {
			BeanUtils.copyProperties(message, t, new String[] { "id" , "createdatetime" });
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		messageDao.delete(messageDao.get(Tmessage.class, id));
	}


	@Override
	public TmessageCount addAndCount(Message message) {
		Tmessage t = new Tmessage();
		BeanUtils.copyProperties(message, t);
		t.setIsRead(false);
		t.setId(UUID.randomUUID().toString());
		t.setCreatedatetime(new Date());
		messageDao.save(t);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mtype", message.getMtype());
		params.put("userId", message.getUserId());
		List<TmessageCount> messageCounts = messageCountDao.find("from TmessageCount t where t.mtype =:mtype and t.userId = :userId",params);
		if(messageCounts!=null&&messageCounts.size()>0){
			TmessageCount tmessageCount = messageCounts.get(0);
			Integer mnumber = tmessageCount.getMnumber();
			tmessageCount.setMnumber(mnumber == null ? 1 : mnumber + 1);
			messageCountDao.saveOrUpdate(tmessageCount);
			return tmessageCount;
		}else{
			TmessageCount tmessageCount = new TmessageCount();
			tmessageCount.setId(UUID.randomUUID().toString());
			tmessageCount.setMnumber(1);
			tmessageCount.setMtype(message.getMtype());
			tmessageCount.setUserId(message.getUserId());
			messageCountDao.save(tmessageCount);
			return tmessageCount;
				
		}	
		
		
	}

	/**
	 * 获取@我的消息列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataGrid dataGridAtMine(Message message, PageHelper ph) {
		DataGrid dg = new DataGrid();
		String sql = "select u.id userId, u.head_image headImage, u.nickname nickname, "
				+ "b.id bshootId, b.bs_description description, b.bs_icon bsIcon, "
				+ "m.content type, m.createdatetime createdate "
				+ "from message m left join bshoot b on b.id = m.relation_id left join tuser u on u.id = b.user_id "
				+ "where m.content='BSHOOT' and m.m_type=:mtype and m.user_id=:userId "
				+ "union all "
				+ "select u.id userId, u.head_image headImage, u.nickname nickname, "
				+ "b.id bshootId, c.bs_comment_text description, b.bs_icon bsIcon, "
				+ "m.content type, m.createdatetime createdate "
				+ "from message m left join bshoot_comment c on c.id = m.relation_id left join bshoot b on b.id = c.bshoot_id left join tuser u on u.id = c.user_id "
				+ "where m.content='COMMENT' and m.m_type=:mtype and m.user_id=:userId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mtype", message.getMtype());
		params.put("userId", message.getUserId());
		
		List<Map> l = messageDao.findBySql2Map("select r.* from (" + sql + ") r order by r.createdate desc", params, ph.getPage(), ph.getRows());
		BigInteger count = messageDao.countBySql("select count(*) from (" + sql + ") r", params);
		dg.setTotal(count == null ? 0 : count.longValue());
		if (l != null && l.size() > 0) {
			for (Map t : l) {
				t.put("headImage", PathUtil.getHeadImagePath((String)t.get("headImage")));
				t.put("bsIcon", PathUtil.getBshootPath((String)t.get("bsIcon")));
			}
		}
		dg.setRows(l);
		return dg;
	}
	
	/**
	 * 获取评论消息列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataGrid dataGridComment(Message message, PageHelper ph) {
		DataGrid dg = new DataGrid();
		String sql = "select u.id userId, u.head_image headImage, u.nickname nickname, "
				+ "b.id bshootId, c.bs_comment_text description, b.bs_icon bsIcon, "
				+ "m.createdatetime createdate "
				+ "from message m left join bshoot_comment c on c.id = m.relation_id left join bshoot b on b.id = c.bshoot_id left join tuser u on u.id = c.user_id "
				+ "where m.m_type=:mtype and m.user_id=:userId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mtype", message.getMtype());
		params.put("userId", message.getUserId());
		
		List<Map> l = messageDao.findBySql2Map("select r.* from (" + sql + ") r order by r.createdate desc", params, ph.getPage(), ph.getRows());
		BigInteger count = messageDao.countBySql("select count(*) from (" + sql + ") r", params);
		dg.setTotal(count == null ? 0 : count.longValue());
		if (l != null && l.size() > 0) {
			for (Map t : l) {
				t.put("headImage", PathUtil.getHeadImagePath((String)t.get("headImage")));
				t.put("bsIcon", PathUtil.getBshootPath((String)t.get("bsIcon")));
			}
		}
		dg.setRows(l);
		return dg;
	}
	
	/**
	 * 获取评论消息列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataGrid dataGridPraise(Message message, PageHelper ph) {
		DataGrid dg = new DataGrid();
		String sql = "select u.id userId, u.head_image headImage, u.nickname nickname, "
				+ "b.id bshootId, b.bs_icon bsIcon, m.createdatetime createdate "
				+ "from message m left join bshoot_praise p on p.id = m.relation_id left join bshoot b on b.id = p.bshoot_id left join tuser u on u.id = p.user_id "
				+ "where m.m_type=:mtype and m.user_id=:userId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mtype", message.getMtype());
		params.put("userId", message.getUserId());
		
		List<Map> l = messageDao.findBySql2Map("select r.* from (" + sql + ") r order by r.createdate desc", params, ph.getPage(), ph.getRows());
		BigInteger count = messageDao.countBySql("select count(*) from (" + sql + ") r", params);
		dg.setTotal(count == null ? 0 : count.longValue());
		if (l != null && l.size() > 0) {
			for (Map t : l) {
				t.put("headImage", PathUtil.getHeadImagePath((String)t.get("headImage")));
				t.put("bsIcon", PathUtil.getBshootPath((String)t.get("bsIcon")));
			}
		}
		dg.setRows(l);
		return dg;
	}

	@Override
	public String addAndSendMessage(String mType, String userId, String relationId, String content) {
		Message message = new Message();
		message.setUserId(userId);
		message.setMtype(mType);
		message.setRelationId(relationId);
		message.setContent(content);
		add(message);
		messageService.sendMessage(userId, JSON.toJSONString(message));
		return message.getId();
	}

	@Override
	public boolean sendMessage(String userId,Object message) {
		if(message == null)return false;
		String messageString = null;
		if(message instanceof String){
			messageString = message.toString();
		}else{
			messageString = JSON.toJSONString(message);
		}
		return messageService.sendMessage(userId,messageString);
	}

}

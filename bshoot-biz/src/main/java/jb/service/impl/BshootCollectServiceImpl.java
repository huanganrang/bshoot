package jb.service.impl;

import component.redis.model.CounterType;
import component.redis.service.CounterServiceI;
import component.redis.service.FetchValue;
import jb.absx.F;
import jb.dao.BshootCollectDaoI;
import jb.dao.BshootDaoI;
import jb.model.Tbshoot;
import jb.model.TbshootCollect;
import jb.pageModel.BshootCollect;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.BshootCollectServiceI;
import jb.service.MessageServiceI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BshootCollectServiceImpl extends BaseServiceImpl<BshootCollect> implements BshootCollectServiceI {

	@Autowired
	private BshootCollectDaoI bshootCollectDao;
	
	@Autowired
	private BshootDaoI bshootDao;
	@Autowired
	private CounterServiceI counterService;
	@Autowired
	private MessageServiceI messageServiceImpl;

	@Override
	public DataGrid dataGrid(BshootCollect bshootCollect, PageHelper ph) {
		List<BshootCollect> ol = new ArrayList<BshootCollect>();
		String hql = " from TbshootCollect t ";
		DataGrid dg = dataGridQuery(hql, ph, bshootCollect, bshootCollectDao);
		@SuppressWarnings("unchecked")
		List<TbshootCollect> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TbshootCollect t : l) {
				BshootCollect o = new BshootCollect();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(BshootCollect bshootCollect, Map<String, Object> params) {
		String whereHql = "";	
		if (bshootCollect != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(bshootCollect.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", bshootCollect.getUserId());
			}		
			if (!F.empty(bshootCollect.getBshootId())) {
				whereHql += " and t.bshootId = :bshootId";
				params.put("bshootId", bshootCollect.getBshootId());
			}		
		}	
		return whereHql;
	}

	@Override
	public int add(final BshootCollect bshootCollect,String currentUser) {
		if(get(bshootCollect.getBshootId(), bshootCollect.getUserId())!=null)
			return -1;
		final TbshootCollect t = new TbshootCollect();
		BeanUtils.copyProperties(bshootCollect, t);
		t.setId(UUID.randomUUID().toString());
		//t.setCreatedatetime(new Date());
		t.setCollectDatetime(new Date());
		bshootCollectDao.save(t);
		updateCount(bshootCollect.getBshootId());
		counterService.automicChangeCount(bshootCollect.getBshootId(), CounterType.COLLECT, 1, new FetchValue() {
			@Override
			public Integer fetchValue() {
				return getCount(bshootCollect.getBshootId()).intValue();
			}
		});
		Tbshoot bshoot = bshootDao.get(Tbshoot.class,bshootCollect.getBshootId());
		messageServiceImpl.addAndSendMessage(MessageServiceI.MT_05,bshoot.getUserId(),bshootCollect.getId(),"用户["+currentUser+"]评论了您的动态");
		return 1;
	}

	private Long getCount(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		return bshootDao.count("select count(1) from TbshootCollect t where t.bshootId =:bshootId)", params);
	}

	@Override
	public BshootCollect get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TbshootCollect t = bshootCollectDao.get("from TbshootCollect t  where t.id = :id", params);
		BshootCollect o = new BshootCollect();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(BshootCollect bshootCollect) {
		TbshootCollect t = bshootCollectDao.get(TbshootCollect.class, bshootCollect.getId());
		if (t != null) {
			BeanUtils.copyProperties(bshootCollect, t, new String[] { "id" , "createdatetime" });
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		final TbshootCollect t = bshootCollectDao.get(TbshootCollect.class, id);
		bshootCollectDao.delete(t);
		updateCountReduce(t.getBshootId());
		counterService.automicChangeCount(t.getBshootId(), CounterType.COLLECT, -1, new FetchValue() {
			@Override
			public Integer fetchValue() {
				return getCount(t.getBshootId()).intValue();
			}
		});
	}

	private void updateCount(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		params.put("id", bshootId);
		bshootDao.executeSql("update bshoot t set t.bs_collect = (select count(*)+1 from bshoot_collect b where b.bshoot_id =:bshootId) where t.id=:id", params);
	}

	private void updateCountReduce(String bshootId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		params.put("id", bshootId);
		bshootDao.executeSql("update bshoot t set t.bs_collect = (select count(*)-1 from bshoot_collect b where b.bshoot_id =:bshootId) where t.id=:id", params);
	}

	@Override
	public BshootCollect get(String bshootId, String userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bshootId", bshootId);
		params.put("userId", userId);
		TbshootCollect t = bshootCollectDao.get("from TbshootCollect t  where t.bshootId = :bshootId and t.userId = :userId", params);
		if(t==null)return null;
		BshootCollect o = new BshootCollect();
		BeanUtils.copyProperties(t, o);
		return o;
	}


	@Override
	public int deleteCollect(BshootCollect bshootCollect) {
		BshootCollect bc = get(bshootCollect.getBshootId(), bshootCollect.getUserId());
		if(bc==null){
			return -1;
		}else{
			delete(bc.getId());
		}
		return 0;
	}
	
}

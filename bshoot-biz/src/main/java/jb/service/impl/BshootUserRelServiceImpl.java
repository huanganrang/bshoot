package jb.service.impl;

import jb.absx.F;
import jb.dao.BshootUserRelDaoI;
import jb.model.TbshootUserRel;
import jb.pageModel.BshootUserRel;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.BshootUserRelServiceI;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BshootUserRelServiceImpl extends BaseServiceImpl<BshootUserRel> implements BshootUserRelServiceI {

	@Autowired
	private BshootUserRelDaoI bshootUserRelDao;

	@Override
	public DataGrid dataGrid(BshootUserRel bshootUserRel, PageHelper ph) {
		List<BshootUserRel> ol = new ArrayList<BshootUserRel>();
		String hql = " from TbshootUserRel t ";
		DataGrid dg = dataGridQuery(hql, ph, bshootUserRel, bshootUserRelDao);
		@SuppressWarnings("unchecked")
		List<TbshootUserRel> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TbshootUserRel t : l) {
				BshootUserRel o = new BshootUserRel();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(BshootUserRel bshootUserRel, Map<String, Object> params) {
		String whereHql = "";	
		if (bshootUserRel != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(bshootUserRel.getBshootId())) {
				whereHql += " and t.bshootId = :bshootId";
				params.put("bshootId", bshootUserRel.getBshootId());
			}		
			if (!F.empty(bshootUserRel.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", bshootUserRel.getUserId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(BshootUserRel bshootUserRel) {
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(bshootUserRel, params);
		if(bshootUserRelDao.count("select count(*) from TbshootUserRel t " + whereHql, params) == 0) {
			TbshootUserRel t = new TbshootUserRel();
			BeanUtils.copyProperties(bshootUserRel, t);
			t.setId(UUID.randomUUID().toString());
			bshootUserRelDao.save(t);
		}
	}

	@Override
	public BshootUserRel get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TbshootUserRel t = bshootUserRelDao.get("from TbshootUserRel t  where t.id = :id", params);
		BshootUserRel o = new BshootUserRel();
		if(null!=t)
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(BshootUserRel bshootUserRel) {
		TbshootUserRel t = bshootUserRelDao.get(TbshootUserRel.class, bshootUserRel.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(bshootUserRel, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		bshootUserRelDao.delete(bshootUserRelDao.get(TbshootUserRel.class, id));
	}

}

package jb.service.impl;

import jb.absx.F;
import jb.dao.BshootSquareRelDaoI;
import jb.model.TbshootSquareRel;
import jb.pageModel.BshootSquareRel;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.BshootSquareRelServiceI;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BshootSquareRelServiceImpl extends BaseServiceImpl<BshootSquareRel> implements BshootSquareRelServiceI {

	@Autowired
	private BshootSquareRelDaoI bshootSquareRelDao;

	@Override
	public DataGrid dataGrid(BshootSquareRel bshootSquareRel, PageHelper ph) {
		List<BshootSquareRel> ol = new ArrayList<BshootSquareRel>();
		String hql = " from TbshootSquareRel t ";
		DataGrid dg = dataGridQuery(hql, ph, bshootSquareRel, bshootSquareRelDao);
		@SuppressWarnings("unchecked")
		List<TbshootSquareRel> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TbshootSquareRel t : l) {
				BshootSquareRel o = new BshootSquareRel();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(BshootSquareRel bshootSquareRel, Map<String, Object> params) {
		String whereHql = "";	
		if (bshootSquareRel != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(bshootSquareRel.getBshootId())) {
				whereHql += " and t.bshootId = :bshootId";
				params.put("bshootId", bshootSquareRel.getBshootId());
			}		
			if (!F.empty(bshootSquareRel.getSquareId())) {
				whereHql += " and t.squareId = :squareId";
				params.put("squareId", bshootSquareRel.getSquareId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(BshootSquareRel bshootSquareRel) {
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(bshootSquareRel, params);
		if(bshootSquareRelDao.count("select count(*) from TbshootSquareRel t " + whereHql, params) == 0) {
			TbshootSquareRel t = new TbshootSquareRel();
			BeanUtils.copyProperties(bshootSquareRel, t);
			t.setId(UUID.randomUUID().toString());
			bshootSquareRelDao.save(t);
		}
	}

	@Override
	public BshootSquareRel get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TbshootSquareRel t = bshootSquareRelDao.get("from TbshootSquareRel t  where t.id = :id", params);
		BshootSquareRel o = new BshootSquareRel();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(BshootSquareRel bshootSquareRel) {
		TbshootSquareRel t = bshootSquareRelDao.get(TbshootSquareRel.class, bshootSquareRel.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(bshootSquareRel, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		bshootSquareRelDao.delete(bshootSquareRelDao.get(TbshootSquareRel.class, id));
	}

}

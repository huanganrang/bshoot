package jb.service.impl;

import jb.absx.F;
import jb.dao.BshootRegionDaoI;
import jb.model.TbshootRegion;
import jb.pageModel.BshootRegion;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.BshootRegionServiceI;
import jb.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BshootRegionServiceImpl extends BaseServiceImpl<BshootRegion> implements BshootRegionServiceI {

	@Autowired
	private BshootRegionDaoI bshootRegionDao;

	@Override
	public DataGrid dataGrid(BshootRegion bshootRegion, PageHelper ph) {
		List<BshootRegion> ol = new ArrayList<BshootRegion>();
		String hql = " from TbshootRegion t ";
		DataGrid dg = dataGridQuery(hql, ph, bshootRegion, bshootRegionDao);
		@SuppressWarnings("unchecked")
		List<TbshootRegion> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TbshootRegion t : l) {
				BshootRegion o = new BshootRegion();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(BshootRegion bshootRegion, Map<String, Object> params) {
		String whereHql = "";	
		if (bshootRegion != null) {
			whereHql += " where 1=1 ";
			if (bshootRegion.getRegionLevel() != null) {
				whereHql += " and t.regionLevel = :regionLevel";
				params.put("regionLevel", bshootRegion.getRegionLevel());
			}	
			if (!F.empty(bshootRegion.getRegionNameZh())) {
				whereHql += " and t.regionNameZh = :regionNameZh";
				params.put("regionNameZh", bshootRegion.getRegionNameZh());
			}		
			if (!F.empty(bshootRegion.getRegionNameEn())) {
				whereHql += " and t.regionNameEn = :regionNameEn";
				params.put("regionNameEn", bshootRegion.getRegionNameEn());
			}		
			if (!F.empty(bshootRegion.getRegionParentId())) {
				whereHql += " and t.regionParentId = :regionParentId";
				params.put("regionParentId", bshootRegion.getRegionParentId());
			}		
			if (!F.empty(bshootRegion.getRegionId())) {
				whereHql += " and t.regionId = :regionId";
				params.put("regionId", bshootRegion.getRegionId());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(BshootRegion bshootRegion) {
		TbshootRegion t = new TbshootRegion();
		BeanUtils.copyProperties(bshootRegion, t);
		//t.setCreatedatetime(new Date());
		bshootRegionDao.save(t);
	}

	@Override
	public BshootRegion get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TbshootRegion t = bshootRegionDao.get("from TbshootRegion t  where t.id = :id", params);
		BshootRegion o = new BshootRegion();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(BshootRegion bshootRegion) {
		TbshootRegion t = bshootRegionDao.get(TbshootRegion.class, bshootRegion.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(bshootRegion, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(Integer id) {
		bshootRegionDao.delete(bshootRegionDao.get(TbshootRegion.class, id));
	}
	
	@Override
	public List<BshootRegion> findAllByParams(BshootRegion bshootRegion) {
		List<BshootRegion> r = new ArrayList<BshootRegion>();
		List<TbshootRegion> l = null;
		Map<String, Object> params = new HashMap<String, Object>();
		String whereHql = whereHql(bshootRegion, params);
		if(!F.empty(bshootRegion.getRegionParentId()) && "0".equals(bshootRegion.getRegionParentId())) {
			l = bshootRegionDao.find("from TbshootRegion r where r.regionParentId in (select new TbshootRegion(t.regionId) from TbshootRegion t " + whereHql + ") order by r.regionId asc", params);
		} else {
			l = bshootRegionDao.find("from TbshootRegion t " + whereHql + " order by t.regionId asc", params);
		}
		if (l != null && l.size() > 0) {
			for (TbshootRegion t : l) {
				BshootRegion o = new BshootRegion();
				BeanUtils.copyProperties(t, o);
				r.add(o);
			}
		}
		return r;
	}

}

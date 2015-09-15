package jb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jb.absx.F;
import jb.dao.BshootSkillDaoI;
import jb.model.TbshootSkill;
import jb.pageModel.BshootSkill;
import jb.pageModel.DataGrid;
import jb.pageModel.PageHelper;
import jb.service.BshootSkillServiceI;
import jb.util.MyBeanUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BshootSkillServiceImpl extends BaseServiceImpl<BshootSkill> implements BshootSkillServiceI {

	@Autowired
	private BshootSkillDaoI bshootSkillDao;

	@Override
	public DataGrid dataGrid(BshootSkill bshootSkill, PageHelper ph) {
		List<BshootSkill> ol = new ArrayList<BshootSkill>();
		String hql = " from TbshootSkill t ";
		DataGrid dg = dataGridQuery(hql, ph, bshootSkill, bshootSkillDao);
		@SuppressWarnings("unchecked")
		List<TbshootSkill> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TbshootSkill t : l) {
				BshootSkill o = new BshootSkill();
				BeanUtils.copyProperties(t, o);
				o.setDescription("api/apiCommon/html?id=" + t.getId());
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(BshootSkill bshootSkill, Map<String, Object> params) {
		String whereHql = "";	
		if (bshootSkill != null) {
			whereHql += " where 1=1 ";
			if (!F.empty(bshootSkill.getTitle())) {
				whereHql += " and t.title like :title";
				params.put("title", "%%" + bshootSkill.getTitle() + "%%");
			}		
			if (!F.empty(bshootSkill.getType())) {
				whereHql += " and t.type = :type";
				params.put("type", bshootSkill.getType());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(BshootSkill bshootSkill) {
		TbshootSkill t = new TbshootSkill();
		BeanUtils.copyProperties(bshootSkill, t);
		t.setId(UUID.randomUUID().toString());
		t.setCreateTime(new Date());
		bshootSkillDao.save(t);
	}

	@Override
	public BshootSkill get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TbshootSkill t = bshootSkillDao.get("from TbshootSkill t  where t.id = :id", params);
		BshootSkill o = new BshootSkill();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(BshootSkill bshootSkill) {
		TbshootSkill t = bshootSkillDao.get(TbshootSkill.class, bshootSkill.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(bshootSkill, t, new String[] { "id" , "createdatetime" },true);
			//t.setModifydatetime(new Date());
		}
	}

	@Override
	public void delete(String id) {
		bshootSkillDao.delete(bshootSkillDao.get(TbshootSkill.class, id));
	}

}

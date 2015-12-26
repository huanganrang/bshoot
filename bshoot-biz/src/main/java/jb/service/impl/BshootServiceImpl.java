package jb.service.impl;

import jb.absx.F;
import jb.dao.BaseDaoI;
import jb.dao.BshootDaoI;
import jb.dao.BshootPraiseDaoI;
import jb.dao.UserDaoI;
import jb.listener.Application;
import jb.model.Tbshoot;
import jb.model.TbshootPraise;
import jb.model.Tuser;
import jb.pageModel.*;
import jb.service.*;
import jb.util.Constants;
import jb.util.MyBeanUtils;
import jb.util.PathUtil;
import jb.util.RoundTool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BshootServiceImpl extends BaseServiceImpl<Bshoot> implements BshootServiceI {

	@Autowired
	private BshootDaoI bshootDao;
	
	@Autowired
	private UserDaoI userDao;
	
	@Autowired
	private BshootSquareServiceI bshootSquareService;
	
	@Autowired
	private BshootSquareRelServiceI bshootSquareRelService;
	
	@Autowired
	private BshootUserRelServiceI bshootUserRelService;
	
	@Autowired
	private BshootPraiseDaoI bshootPraiseDao;
	
	@Autowired
	private BshootCommentServiceI bshootCommentService;
	
	@Override
	public DataGrid dataGrid(Bshoot bshoot, PageHelper ph) {
		List<Bshoot> ol = new ArrayList<Bshoot>();
		String hql = " from Tbshoot t ";
		DataGrid dg = dataGridQuery(hql, ph, bshoot, bshootDao);
		@SuppressWarnings("unchecked")
		List<Tbshoot> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (Tbshoot t : l) {
				Bshoot o = new Bshoot();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	@Override
	public DataGrid dataGrid(Bshoot bshoot, PageHelper ph, int qtype, String userId) {
		List<Bshoot> ol = new ArrayList<Bshoot>();
		String hql = " from Tbshoot t ";
		DataGrid dg = dataGridByType(hql, ph, bshoot, bshootDao,qtype);
		@SuppressWarnings("unchecked")
		List<Tbshoot> l = dg.getRows();
		Map<String, Bshoot> map = new HashMap<String,Bshoot>();
		if(qtype == 2) {
			String[] bshootIds = new String[l.size()];
			int i = 0;
			for(Tbshoot b : l){
				bshootIds[i++] = b.getParentId();
			}
			List<Tbshoot> list = bshootDao.getTbshoots(bshootIds);
			List<Bshoot> blist = new ArrayList<Bshoot>();
			if(list != null) {
				for(Tbshoot t : list){
					Bshoot b = new Bshoot();
					BeanUtils.copyProperties(t, b);
					map.put(t.getId(), b);
					blist.add(b);
				}
				if(userId != null) {
					setPraised(blist, userId);
				}
				setUserInfo(blist);
			}
		}
		if (l != null && l.size() > 0) {
			for (Tbshoot t : l) {
				Bshoot o = new Bshoot();
				BeanUtils.copyProperties(t, o);
				if(!F.empty(t.getParentId()) && map.containsKey(t.getParentId())) {
					o.setParentBshoot(map.get(t.getParentId()));
				}
				ol.add(o);
			}
		}
		if(qtype == 1) {
			if(userId != null) {
				setPraised(ol, userId);
			}
		} else {
			setUserInfo(ol);
		}
		dg.setRows(ol);
		return dg;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private DataGrid dataGridByType(String hql,PageHelper ph,Bshoot t,BaseDaoI dao,int qtype){
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String where = " where t.status = 1 and t.userId = :userId";
		params.put("userId", t.getUserId());
		//我的美拍
		if(qtype==1){
			where +=" and t.parentId is null";
		//我的转发
		}else if(qtype==2){
			where +=" and t.parentId is not null";
		}
		List<Bshoot> l = dao.find(hql  + where + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(dao.count("select count(*) " + hql + where, params));
		dg.setRows(l);
		return dg;
	}
	
	protected String whereHql(Bshoot bshoot, Map<String, Object> params) {
		String whereHql = " where t.status=1 and t.parentId is null";	
		if (bshoot != null) {
			if (!F.empty(bshoot.getBsTitle())) {
				whereHql += " and t.bsTitle = :bsTitle";
				params.put("bsTitle", bshoot.getBsTitle());
			}		
			if (!F.empty(bshoot.getBsTopic())) {
				whereHql += " and t.bsTopic = :bsTopic";
				params.put("bsTopic", bshoot.getBsTopic());
			}		
			if (!F.empty(bshoot.getBsIcon())) {
				whereHql += " and t.bsIcon = :bsIcon";
				params.put("bsIcon", bshoot.getBsIcon());
			}		
			if (!F.empty(bshoot.getBsStream())) {
				whereHql += " and t.bsStream = :bsStream";
				params.put("bsStream", bshoot.getBsStream());
			}		
			/*if (!F.empty(bshoot.getBsCollect())) {
				whereHql += " and t.bsCollect = :bsCollect";
				params.put("bsCollect", bshoot.getBsCollect());
			}		
			if (!F.empty(bshoot.getBsPraise())) {
				whereHql += " and t.bsPraise = :bsPraise";
				params.put("bsPraise", bshoot.getBsPraise());
			}	*/	
			if (!F.empty(bshoot.getBsType())) {
				whereHql += " and t.bsType = :bsType";
				params.put("bsType", bshoot.getBsType());
			}		
			/*if (!F.empty(bshoot.getBsComment())) {
				whereHql += " and t.bsComment = :bsComment";
				params.put("bsComment", bshoot.getBsComment());
			}	*/	
			if (!F.empty(bshoot.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", bshoot.getUserId());
			}		
			if (!F.empty(bshoot.getBsDescription())) {
				whereHql += " and t.bsDescription = :bsDescription";
				params.put("bsDescription", bshoot.getBsDescription());
			}		
			if (!F.empty(bshoot.getBsRemark())) {
				whereHql += " and t.bsRemark = :bsRemark";
				params.put("bsRemark", bshoot.getBsRemark());
			}		
			if (!F.empty(bshoot.getCreatePerson())) {
				whereHql += " and t.createPerson = :createPerson";
				params.put("createPerson", bshoot.getCreatePerson());
			}		
			if (!F.empty(bshoot.getUpdatePerson())) {
				whereHql += " and t.updatePerson = :updatePerson";
				params.put("updatePerson", bshoot.getUpdatePerson());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(Bshoot bshoot) {
		if(F.empty(bshoot.getId()))
			bshoot.setId(UUID.randomUUID().toString());
		Tbshoot t = new Tbshoot();
		BeanUtils.copyProperties(bshoot, t);
		t.setStatus("1");
		t.setCreateDatetime(new Date());
		bshootDao.save(t);
//		updateLocation(t);
	}
	
	@Override
	public List<String> addBshoot(Bshoot bshoot) {
		List<String> attUserIdList = new ArrayList<String>();
		
		this.add(bshoot);
		
		// 建立视频-广场主题对应关系
		if(bshoot.getSquareIds() != null) {
			String[] squareIds = bshoot.getSquareIds().split(",");
			for(String squareId : squareIds) {
				if(F.empty(squareId)) continue;
				
				BshootSquareRel rel = new BshootSquareRel();
				rel.setBshootId(bshoot.getId());
				rel.setSquareId(squareId.trim());
				bshootSquareRelService.add(rel);
			}
		}
		
		String bsDescription = bshoot.getBsDescription() + " ";
		// 建立视频-话题对应关系
		String regex="#(.*?)#";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(bsDescription);
		while(m.find()){
			String squareName = m.group();
			BshootSquare bshootSquare = new BshootSquare();
			bshootSquare.setBssUserId(bshoot.getUserId());
			bshootSquare.setBssName(squareName);
			bshootSquare.setBssType("BT01");
			bshootSquareService.custom(bshootSquare);
			BshootSquareRel rel = new BshootSquareRel();
			rel.setBshootId(bshoot.getId());
			rel.setSquareId(bshootSquare.getId());
			bshootSquareRelService.add(rel);
		}
		
		// 建立视频-@关注好友关系
		regex="@[^@]+?(?=[\\s:：(),.。@])";
		p = Pattern.compile(regex);
		m = p.matcher(bsDescription);
		while(m.find()){
			String nickname = m.group().substring(1); // 去除@
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nickname", nickname);
			Tuser t = userDao.get("from Tuser t where t.nickname = :nickname", params);
			if(t != null && !attUserIdList.contains(t.getId()) && !t.getId().equals(bshoot.getUserId())) {
//				BshootUserRel rel = new BshootUserRel();
//				rel.setBshootId(bshoot.getId());
//				rel.setUserId(t.getId());
//				bshootUserRelService.add(rel);
				attUserIdList.add(t.getId());
			}
		}
		
		return attUserIdList;
	}
	
	@SuppressWarnings("unused")
	private void updateLocation(Tbshoot bshoot){
		try{
			if(F.empty(bshoot.getLgX())){
				bshoot.setLgX("0");
			}
			if(F.empty(bshoot.getLgX())){
				bshoot.setLgX("0");
			}
			double x = RoundTool.round(new Double(bshoot.getLgX()), 6, BigDecimal.ROUND_UP);
			double y = RoundTool.round(new Double(bshoot.getLgY()), 6, BigDecimal.ROUND_UP);
			String updateSql = "update bshoot set location=GEOMFROMTEXT('point("+x+" "+y+")') where id = :id";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", bshoot.getId());
			bshootDao.executeSql(updateSql, params);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public Bshoot get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tbshoot t = bshootDao.get("from Tbshoot t  where t.id = :id", params);
		if(t==null) return null;
		Bshoot o = new Bshoot();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(Bshoot bshoot) {
		Tbshoot t = bshootDao.get(Tbshoot.class, bshoot.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(bshoot, t, new String[] { "id" , "createdatetime", "lgX", "lgY" }, true);
			//t.setModifydatetime(new Date());
//			updateLocation(t);
		}
	}

	@Override
	public void delete(String id) {
		bshootDao.delete(bshootDao.get(Tbshoot.class, id));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataGrid dataGridHot(PageHelper ph, String userId) {
		DataGrid dataGrid = dataGrid(null, ph);
		List<Bshoot> bshoots = dataGrid.getRows();
		setUserInfo(bshoots);
		if(userId != null) {
			setPraised(bshoots, userId);
		}
		return dataGrid;
	}
	@Override
	public DataGrid dataGridBySquare(PageHelper ph, String squareId) {
		
		DataGrid dg = new DataGrid();
		List<Bshoot> ol = new ArrayList<Bshoot>();
		String hql = "select t from Tbshoot t , TbshootSquareRel bs where t.id = bs.bshootId and bs.squareId = :squareId and t.status=1 and t.parentId is null";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("squareId", squareId);
		
		List<Tbshoot> l = bshootDao.find(hql + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(bshootDao.count(hql.replace("select t", "select count(*)") , params));
		if (l != null && l.size() > 0) {
			for (Tbshoot t : l) {
				Bshoot o = new Bshoot();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		setUserInfo(ol);
		dg.setRows(ol);
		return dg;
	}
	
	private void setUserInfo(List<Bshoot> bshoots){
		if(bshoots!=null&&bshoots.size()>0){
			String[] userIds = new String[bshoots.size()];
			int i = 0;
			for(Bshoot b :bshoots){
				userIds[i++] = b.getUserId();
			}
			List<Tuser> list = userDao.getTusers(userIds);
			Map<String,Tuser> map = new HashMap<String,Tuser>();
			for(Tuser t : list){
				map.put(t.getId(), t);
			}
			for(Bshoot b :bshoots){
				Tuser t = map.get(b.getUserId());
				b.setUserHeadImage(t.getHeadImage());
				b.setUserName(F.empty(t.getNickname()) ? t.getName() : t.getNickname());
			}
		}
	}
	
	private void setPraised(List<Bshoot> bshoots, String userId){
		if(bshoots!=null&&bshoots.size()>0){
			String[] bshootIds = new String[bshoots.size()];
			int i = 0;
			for(Bshoot b :bshoots){
				bshootIds[i++] = b.getId();
			}
			List<TbshootPraise> listBshootPraises = bshootPraiseDao.getTbshootPraises(userId, bshootIds);
			Map<String,String> map = new HashMap<String,String>();
			for(TbshootPraise t : listBshootPraises){
				map.put(t.getBshootId(), t.getBshootId());
			}
			for(Bshoot b :bshoots){
				if(map.get(b.getId())!=null){
					b.setPraised(Constants.GLOBAL_BOOLEAN_TRUE);
				}else{
					b.setPraised(Constants.GLOBAL_BOOLEAN_FALSE);
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setCommentAndPraiseList(List<Bshoot> bshoots, String userId) {
		if(bshoots!=null&&bshoots.size()>0){
			PageHelper ph = new PageHelper();
			ph.setPage(1);
			ph.setRows(20);
			ph.setSort("commentDatetime");
			BshootComment bc = new BshootComment();
			DataGrid dataGrid  = null;
			List<Map> praises = null;
			for(Bshoot b :bshoots){
				
				if(b.getBsComment() != null && b.getBsComment() != 0) {
					bc.setBshootId(b.getId());
					// 评论是否赞过	
					dataGrid  = bshootCommentService.dataGrid(bc, ph, userId);
					b.setComments(dataGrid);
				}
				if(b.getBsPraise() != null && b.getBsPraise() != 0) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("bshootId", b.getId());
					String sql = "select t.user_id userId, ifnull(u.nickname, u.name) nickname, u.head_image headImage "
							+ " from bshoot_praise t left join tuser u on u.id = t.user_id "
							+ " where t.bshoot_id = :bshootId order by praise_datetime ";
					praises = bshootPraiseDao.findBySql2Map(sql, params);
					for(Map m : praises) {
						m.put("headImage", PathUtil.getHeadImagePath((String)m.get("headImage")));
					}
					b.setPraises(praises);
				}
			}
		}
	}
	
	/**
	 * 附近视频查询-废弃
	 * @param ph
	 * @param xStr
	 * @param yStr
	 * @param userId
	 * @return
	 */
	public DataGrid dataGridNearby_bak(PageHelper ph,String xStr,String yStr, String userId) {
		double x = RoundTool.round(new Double(xStr), 6, BigDecimal.ROUND_UP);
		double y = RoundTool.round(new Double(yStr), 6, BigDecimal.ROUND_UP);
		List<Map<String,Object>> list = bshootDao.executeNearby(ph.getPage(), ph.getRows(), x, y);
		DataGrid dataGrid = new DataGrid();
		int i = 0;
		String[] userIds = new String[list.size()];
		String[] bshootIds = new String[list.size()];
		for(Map<String,Object> bshoot : list){
			userIds[i] = (String)bshoot.get("user_id");
			bshootIds[i] = (String)bshoot.get("id");
			i++;
		}
		List<Tuser> listUsers = userDao.getTusers(userIds);
		Map<String,Tuser> map = new HashMap<String,Tuser>();
		for(Tuser t : listUsers){
			map.put(t.getId(), t);
		}
		Map<String,String> pMap = new HashMap<String,String>();
		if(userId != null) {
			List<TbshootPraise> listBshootPraises = bshootPraiseDao.getTbshootPraises(userId, bshootIds);
			for(TbshootPraise t : listBshootPraises){
				pMap.put(t.getBshootId(), t.getBshootId());
			}
		}
		for(Map<String,Object> b :list){
			Tuser t = map.get((String)b.get("user_id"));
			b.put("userHeadImage",PathUtil.getHeadImagePath(t.getHeadImage()));
			b.put("nickname",t.getNickname());
			b.put("memberV", t.getMemberV());
			if(pMap.get((String)b.get("id"))!=null){
				b.put("praised", Constants.GLOBAL_BOOLEAN_TRUE);
			}else{
				b.put("praised", Constants.GLOBAL_BOOLEAN_FALSE);
			}
		}
		dataGrid.setRows(list);
		
		return dataGrid;
	}
	
	/**
	 * 附近视频查询
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DataGrid dataGridNearby(PageHelper ph,String xStr,String yStr, String userId) {
		DataGrid dg = new DataGrid();
		String sql = "select id, user_id userId, bs_stream bsStream, bs_icon bsIcon, bs_description bsDescription, bs_play bsPlay, "
				+ "bs_praise bsPraise, bs_comment bsComment, lg_name lgName, create_datetime createDatetime, "
				+ "round(6378.138*2*asin(sqrt(pow(sin(("+yStr+"*pi()/180-lg_y*pi()/180)/2),2)+cos("+yStr+"*pi()/180)*cos(lg_y*pi()/180)*pow(sin(("+xStr+"*pi()/180-lg_x*pi()/180)/2),2)))*1000) as distance "
				+ " from bshoot ";
		String where = " where status=1 and parent_id is null ";
		List<Map> list = bshootDao.findBySql2Map(sql + where + " order by case when distance is null then 1 else 0 end ,distance", ph.getPage(), ph.getRows());
		dg.setTotal(bshootDao.count("select count(*) from Tbshoot " + where));
		
		if(list != null && list.size() > 0) {
			int i = 0;
			String[] userIds = new String[list.size()];
			String[] bshootIds = new String[list.size()];
			for(Map bshoot : list){
				userIds[i] = (String)bshoot.get("userId");
				bshootIds[i] = (String)bshoot.get("id");
				i++;
			}
			List<Tuser> listUsers = userDao.getTusers(userIds);
			Map<String,Tuser> map = new HashMap<String,Tuser>();
			for(Tuser t : listUsers){
				map.put(t.getId(), t);
			}
			Map<String,String> pMap = new HashMap<String,String>();
			if(userId != null) {
				List<TbshootPraise> listBshootPraises = bshootPraiseDao.getTbshootPraises(userId, bshootIds);
				for(TbshootPraise t : listBshootPraises){
					pMap.put(t.getBshootId(), t.getBshootId());
				}
			}
			for(Map b :list){
				Tuser t = map.get((String)b.get("userId"));
				b.put("userHeadImage",PathUtil.getHeadImagePath(t.getHeadImage()));
				b.put("nickname",t.getNickname());
				b.put("memberV", t.getMemberV());
				if(pMap.get((String)b.get("id"))!=null){
					b.put("praised", Constants.GLOBAL_BOOLEAN_TRUE);
				}else{
					b.put("praised", Constants.GLOBAL_BOOLEAN_FALSE);
				}
				b.put("bsStream", PathUtil.getBshootPath((String)b.get("bsStream")));
				b.put("bsIcon", PathUtil.getBshootPath((String)b.get("bsIcon")));
			}
		}
		dg.setRows(list);
		
		return dg;
	}
	
	/**
	 * 关注好友的视频（包括自己的和转发的）
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public DataGrid dataGridByFriend(PageHelper ph, String userId) {
		DataGrid dg = new DataGrid();
		List<Bshoot> ol = new ArrayList<Bshoot>();
		
		String sql = "select distinct t.id id, t.user_id userId, t.bs_stream bsStream, t.bs_icon bsIcon, t.bs_description bsDescription, bs_play bsPlay, "
				+ " t.bs_praise bsPraise, t.bs_comment bsComment, t.lg_name lgName, t.create_datetime createDatetime, t.parent_id parentId "
				+ " from bshoot t left join user_attention ua on ua.att_user_id = t.user_id " 
				+ " where t.status=1 and (ua.user_id = :userId or t.user_id = :userId) ";
		//String hql = "select t from Tbshoot t , TuserAttention ua where t.userId = ua.attUserId and ua.userId = :userId and t.status=1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		
		List<Tbshoot> l = new ArrayList<Tbshoot>();
//		List<Tbshoot> l = bshootDao.find(hql + " order by t.createDatetime desc", params, ph.getPage(), ph.getRows());
//		dg.setTotal(bshootDao.count(hql.replace("select t", "select count(*)") , params));
		List<Map> lm = bshootDao.findBySql2Map(sql + " order by t.create_datetime desc ", params, ph.getPage(), ph.getRows());
		BigInteger count = bshootDao.countBySql("select count(distinct t.id) from bshoot t left join user_attention ua on ua.att_user_id = t.user_id where t.status=1 and (ua.user_id = :userId or t.user_id = :userId)", params);
		dg.setTotal(count == null ? 0 : count.longValue());
		if(lm != null && lm.size() > 0) {
			for(Map m : lm) {
				Tbshoot t = new Tbshoot();
				try {
					org.apache.commons.beanutils.BeanUtils.populate(t, m);
					l.add(t);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (l != null && l.size() > 0) {
			Map<String, Bshoot> map = new HashMap<String,Bshoot>();
			String[] bshootIds = new String[l.size()];
			int i = 0;
			for(Tbshoot b : l){
				bshootIds[i++] = b.getParentId();
			}
			List<Tbshoot> list = bshootDao.getTbshoots(bshootIds);
			List<Bshoot> blist = new ArrayList<Bshoot>();
			if(list != null) {
				for(Tbshoot t : list){
					Bshoot b = new Bshoot();
					BeanUtils.copyProperties(t, b);
					map.put(t.getId(), b);
					blist.add(b);
				}
				if(userId != null) {
					setPraised(blist, userId);
				}
				setUserInfo(blist);
			}
			
			for (Tbshoot t : l) {
				Bshoot o = new Bshoot();
				BeanUtils.copyProperties(t, o);
				if(!F.empty(t.getParentId()) && map.containsKey(t.getParentId())) {
					o.setParentBshoot(map.get(t.getParentId()));
				}
				ol.add(o);
			}
			setCommentAndPraiseList(ol, userId);
		}
		setUserInfo(ol);
		setPraised(ol, userId);
		dg.setRows(ol);
		return dg;
	}
	
	@Override
	public DataGrid dataGridSearch(Bshoot bshoot, PageHelper ph, String userId) {
		DataGrid dg = new DataGrid();
		List<Bshoot> ol = new ArrayList<Bshoot>();
		String hql = " from Tbshoot t where t.status=1 and t.parentId is null";
		Map<String, Object> params = new HashMap<String, Object>();
		if (!F.empty(bshoot.getBsDescription())) {
			hql += " and t.bsDescription like :bsDescription";
			params.put("bsDescription", "%%" + bshoot.getBsDescription() + "%%");
		}	
		
		List<Tbshoot> l = bshootDao.find(hql + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(bshootDao.count("select count(*) " + hql , params));
		if (l != null && l.size() > 0) {
			for (Tbshoot t : l) {
				Bshoot o = new Bshoot();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		setUserInfo(ol);
		if(userId != null) {
			setPraised(ol, userId);
		}
		dg.setRows(ol);
		return dg;
	}
	
	@Override
	public DataGrid dataGridCity(Bshoot bshoot, PageHelper ph, String userId, int type) {
		List<Bshoot> ol = new ArrayList<Bshoot>();
		DataGrid dg = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tbshoot t  where t.status = 1 and t.parentId is null ";
		if(!F.empty(bshoot.getBsArea())) {
			if(type == 1) { // 国内/同城
				hql += " and t.bsArea like :bsArea";
			} else if(type == 2){ // 国外
				hql += " and t.bsArea not like :bsArea";
			}
			params.put("bsArea", bshoot.getBsArea() + "%%");
		}
		
		List<Tbshoot> l = bshootDao.find(hql  + orderHql(ph), params, ph.getPage(), ph.getRows());
		dg.setTotal(bshootDao.count("select count(*) " + hql, params));
		dg.setRows(l);
		
		if (l != null && l.size() > 0) {
			for (Tbshoot t : l) {
				Bshoot o = new Bshoot();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		setUserInfo(ol);
		if(userId != null) {
			setPraised(ol, userId);
		}
		dg.setRows(ol);
		return dg;
	}
	
	@Override
	public void updatePlayNum(String id) {
		int addNum = 1;
		try {
			String numStr = Application.getString("SV400");
			if(!F.empty(numStr)) {
				int num = Integer.valueOf(numStr);
				if(num > 0) {
					addNum = num;
				} else if(num < 0) {
					Random random = new Random();
					addNum = random.nextInt(-num) + 1;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		bshootDao.executeSql("update bshoot t set t.bs_play = ifnull(t.bs_play, 0) + "+addNum+" where t.id=:id", params);
	}
}

package jb.service;

import component.redis.model.BshootCounter;
import component.redis.model.CounterType;
import jb.pageModel.Bshoot;
import jb.pageModel.DataGrid;
import jb.pageModel.HotShootRequest;
import jb.pageModel.PageHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author John
 * 
 */
public interface BshootServiceI {

	/**
	 * 获取Bshoot数据表格
	 * 
	 * @param bshoot
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(Bshoot bshoot, PageHelper ph);
	
	
	/**
	 * 热门视频
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridHot(PageHelper ph, String userId);
	
	/**
	 * 好友视频
	 * @param ph
	 * @param userId
	 * @return
	 */
	public DataGrid dataGridByFriend(PageHelper ph, String userId);

	List<Bshoot>  getMaxPraiseBshoot(List<String> userId, Date dateLimit);

	/**
	 * 附近视频
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridNearby(PageHelper ph, String x, String y, String userId);
	
	/**
	 * 根据广场Code查询
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridBySquare(PageHelper ph, String squareId);

	/**
	 * 获得用户最近一条动态
	 * @param userIds
	 * @return
	 */
	public List<Bshoot> getUserLastBshoot(List<String> userIds,Date dateLimit);

	public Bshoot getUserLastBshoot(String userId,Date dateLimit);
	
	/**
	 * 获取Bshoot数据表格
	 * 
	 * @param bshoot
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @param qtype    
	 *            查询类别
	 * @return
	 */
	public DataGrid dataGrid(Bshoot bshoot, PageHelper ph, int qtype, String userId);
	
	/**
	 * 首页搜索
	 * @param bshoot
	 * @param ph
	 * @return
	 */
	public DataGrid dataGridSearch(Bshoot bshoot, PageHelper ph, String userId);

	/**
	 * 首页热门动态获取
	 * @return
	 */
	public List<Bshoot> getHotBshoots(HotShootRequest hotShootRequest);

	/**
	 * 同城视频
	 * @param bshoot
	 * @param ph
	 * @param userId
	 * @return
	 */
	public DataGrid dataGridCity(Bshoot bshoot, PageHelper ph, String userId, int type);

	/**
	 * 添加Bshoot
	 * 
	 * @param bshoot
	 */
	public void add(Bshoot bshoot);
	
	public List<String> addBshoot(Bshoot bshoot);

	/**
	 * 获得Bshoot对象
	 * 
	 * @param id
	 * @return
	 */
	public Bshoot get(String id);

	/**
	 * 修改Bshoot
	 * 
	 * @param bshoot
	 */
	public void edit(Bshoot bshoot);

	/**
	 * 删除Bshoot
	 * 
	 * @param id
	 */
	public void delete(String id);


	public void updatePlayNum(String id);


	void updateCountByType(String bshootId, int count, CounterType counterType);

	void updateCount(BshootCounter counter);

	//用户打赏总数分组统计
	Map<String,Integer> praiseCountGroup(Date begin, Date end);

	List<Bshoot> getSomeoneBshoot(String userId, Integer fileType, Integer start, int rows);

	/**
	 * 转换资源计数
	 * @param bshoot
	 */
	void convertNumber(Bshoot bshoot);
	void convertNumber(List<Bshoot> bshoot);


}
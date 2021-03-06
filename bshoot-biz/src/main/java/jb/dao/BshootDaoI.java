package jb.dao;

import jb.model.Tbshoot;
import jb.pageModel.HotShootRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Bshoot数据库操作类
 * 
 * @author John
 * 
 */
public interface BshootDaoI extends BaseDaoI<Tbshoot> {
	public List<Map<String,Object>> executeNearby(int page, int rows, double lgx, double lgy);

	public List<Tbshoot> getTbshoots(String[] bshootIds);

	List<Tbshoot> getUserLastBshoot(List<String> userIds,Date dateLimit);

	List<Tbshoot> getMaxPraiseBshoot(List<String> userIds, Date dateLimit);

	Tbshoot getUserLastBshoot(String userId,Date dateLimit);


	List<Tbshoot> getHotBshoots(HotShootRequest hotShootRequest);
}

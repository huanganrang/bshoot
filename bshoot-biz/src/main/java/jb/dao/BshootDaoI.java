package jb.dao;

import jb.model.Tbshoot;

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

	List<Tbshoot> getUserLastBshoot(List<String> userIds);

	Tbshoot getUserLastBshoot(String userId);
}

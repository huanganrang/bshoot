package jb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jb.interceptors.TokenManage;
import jb.pageModel.BshootSquare;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.pageModel.SessionInfo;
import jb.pageModel.User;
import jb.service.BshootCollectServiceI;
import jb.service.BshootServiceI;
import jb.service.BshootSquareServiceI;
import jb.service.UserAttentionServiceI;
import jb.service.UserServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * finder发现模块接口
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiFinderController")
public class ApiFinderController extends BaseController {
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private TokenManage tokenManage;
	
	@Autowired
	private BshootServiceI bshootService;
	
	@Autowired
	private BshootCollectServiceI bshootCollectService;
	
	@Autowired
	private UserAttentionServiceI userAttentionService;
	
	@Autowired
	private BshootSquareServiceI bshootSquareService;
	
	
	/**
	 * 首页热门视频
	 * @param ph
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/hotBshoot")
	public Json hotBshoot(PageHelper ph, HttpServletRequest request) {
		
		Json j = new Json();
		try {
			String userId = null;
			SessionInfo s = getSessionInfo(request);
			if(s != null) {
				userId = s.getId();
			}
			
			ph.setOrder("desc");
			ph.setSort("bsPraise");
			j.setObj(bshootService.dataGridHot(ph, userId));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 附近视频
	 * @param ph
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/nearbyBshoot")
	public Json nearbyBshoot(PageHelper ph,String lg_x,String lg_y, HttpServletRequest request) {
		Json j = new Json();
		try {
			String userId = null;
			SessionInfo s = getSessionInfo(request);
			if(s != null) {
				userId = s.getId();
			}
			j.setObj(bshootService.dataGridNearby(ph,lg_x,lg_y,userId));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 根据广场ID获取视频信息
	 * @param ph
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getBshootBySquare")
	public Json getBshootBySquare(PageHelper ph,String id) {
		Json j = new Json();
		try {
			ph.setOrder("desc");
			ph.setSort("bsPraise");
			j.setObj(bshootService.dataGridBySquare(ph, id));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 获取广场详情
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping("/getBshootSquareDetail")
	public Json getBshootSquareDetail(String id) {
		Json j = new Json();
		try {
			Map map = new HashMap();
			BshootSquare bs = bshootSquareService.get(id);
			User user = userService.get(bs.getBssUserId(), true);
			map.put("square", bs);
			map.put("user", user);
			j.setObj(map);
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	
	/**
	 * 广场列表
	 * @param bshootSquare
	 * @param ph
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bshootSquare")
	public Json bshootSquare(BshootSquare bshootSquare,PageHelper ph) {
		Json j = new Json();
		try {
			bshootSquare.setBssType("BT02");
			j.setObj(bshootSquareService.dataGrid(bshootSquare, ph));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 广场话题列表
	 * @param bshootSquare
	 * @param ph
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bshootSquareTopic")
	public Json bshootSquareTopic(BshootSquare bshootSquare,PageHelper ph) {
		
		Json j = new Json();
		try {
			bshootSquare.setBssType("BT01");
			j.setObj(bshootSquareService.dataGrid(bshootSquare, ph));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	private SessionInfo getSessionInfo(HttpServletRequest request){
		SessionInfo s = tokenManage.getSessionInfo(request);
		return s;
		
	}
}

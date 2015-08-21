package jb.controller;

import javax.servlet.http.HttpServletRequest;

import jb.interceptors.TokenManage;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.pageModel.SessionInfo;
import jb.pageModel.User;
import jb.service.BshootCollectServiceI;
import jb.service.BshootServiceI;
import jb.service.UserAttentionServiceI;
import jb.service.UserServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 好友管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiFriendController")
public class ApiFriendController extends BaseController {
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
	
	
	private SessionInfo getSessionInfo(HttpServletRequest request){
		SessionInfo s = tokenManage.getSessionInfo(request);
		return s;		
	}
	
	/**
	 * 关注好友视频列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/friend_bshoots")
	@ResponseBody
	public Json dataGridMyFriend(PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			j.setObj(bshootService.dataGridByFriend(ph,s.getId()));
			j.setSuccess(true);
			j.setMsg("查询成功");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 明星名人
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/friend_star")
	@ResponseBody
	public Json dataGridStar(PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			User user = new User();
			user.setId(s.getId());
			user.setIsStar(true);
			j.setObj(userService.dataGridForApi(user, ph));
			j.setSuccess(true);
			j.setMsg("查询成功");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	/**
	 * 达人
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/friend_tarento")
	@ResponseBody
	public Json dataGridTarento(PageHelper ph,HttpServletRequest request) {
		
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			User user = new User();
			user.setId(s.getId());
			user.setIsTarento(true);
			j.setObj(userService.dataGridForApi(user, ph));
			j.setSuccess(true);
			j.setMsg("查询成功");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * id or nickname查询
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/friend_search")
	@ResponseBody
	public Json dataGridSearch(PageHelper ph,HttpServletRequest request) {
		
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			User user = new User();
			user.setId(s.getId());
			String name = request.getParameter("name");
			user.setName(name);
			user.setNickname(name);
			j.setObj(userService.dataGridForApi(user, ph));
			j.setSuccess(true);
			j.setMsg("查询成功");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 可能感兴趣
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/friend_hobby")
	@ResponseBody
	public Json dataGridHobby(PageHelper ph,HttpServletRequest request) {
		
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			User user = new User();
			user.setId(s.getId());
			j.setObj(userService.dataGridHobby(user, ph));
			j.setSuccess(true);
			j.setMsg("查询成功");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
}

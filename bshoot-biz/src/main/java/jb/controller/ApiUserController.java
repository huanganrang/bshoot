package jb.controller;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.pageModel.*;
import jb.service.*;
import jb.util.Constants;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Bshoot管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiUserController")
public class ApiUserController extends BaseController {
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
	private BshootToSquareServiceI bshootToSquareService;
	
	@Autowired
	private BshootSkillServiceI bshootSkillService;
	
	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户对象
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Json login(User user, HttpSession session, HttpServletRequest request) {
		Json j = new Json();
		try {
			user.setUtype("UT02");
			User u = userService.login(user);
			if (u != null) {
				String tid = tokenManage.buildToken(user.getId(),user.getName());
				j.setObj(tid);
				j.setSuccess(true);
				j.setMsg("登陆成功！");
			} else {
				j.setMsg("用户名或密码错误！");
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 用户注册
	 * @param user
	 * @param headImageFile
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register")
	public Json register(User user,@RequestParam(required=false) MultipartFile headImageFile, HttpServletRequest request) {
		Json j = new Json();
		try {
			user.setMemberV(null);
			user.setUtype(null);
			user.setThirdUser(null);
			uploadFile(request, user, headImageFile);
			userService.reg(user);	
			j.setObj(tokenManage.buildToken(user.getId(),user.getName()));
			j.setSuccess(true);
			j.setMsg("注册成功");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 头像上传
	 * @param request
	 * @param user
	 * @param headImageFile
	 */
	private void uploadFile(HttpServletRequest request,User user,MultipartFile headImageFile){
		if(headImageFile==null||headImageFile.isEmpty())
			return;
		String realPath = request.getSession().getServletContext().getRealPath("/"+Constants.UPLOADFILE_HEADIMAGE+"/"+user.getName());  
		File file = new File(realPath);
		if(!file.exists())
			file.mkdir();
		String suffix = headImageFile.getOriginalFilename().substring(headImageFile.getOriginalFilename().lastIndexOf("."));
		String fileName = user.getName()+suffix;		
		 try {
			FileUtils.copyInputStreamToFile(headImageFile.getInputStream(), new File(realPath, fileName));
			user.setHeadImage(Constants.UPLOADFILE_HEADIMAGE+"/"+user.getName()+"/"+fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private SessionInfo getSessionInfo(HttpServletRequest request){
		SessionInfo s = tokenManage.getSessionInfo(request);
		return s;		
	}
	
	/**
	 * 关注用户
	 * @param ua
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user_attention")
	public Json userAttention(UserAttention ua,HttpServletRequest request) {
		Json j = new Json();		
		try {
			SessionInfo s = getSessionInfo(request);
			ua.setUserId(s.getId());		
			int r = userAttentionService.add(ua);
			if(r==-1){
				j.setSuccess(false);
				j.setMsg("已经关注！");
			}else{
				j.setSuccess(true);
				j.setMsg("成功！");
				addMessage("MT01",ua.getAttUserId(),ua.getUserId());
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		
		return j;
	}
	
	
	
	/**
	 * 取消关注用户
	 * @param ua
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user_disattention")
	public Json disUserAttention(UserAttention ua,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			ua.setUserId(s.getId());
			int r = userAttentionService.deleteUa(ua);
			if(r==-1){
				j.setSuccess(false);
				j.setMsg("已经取消！");
			}else{
				j.setSuccess(true);
				j.setMsg("成功！");
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 我的首页
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@ResponseBody
	@RequestMapping("/user_index")
	public Json userIndex(HttpServletRequest request) {
		Json j = new Json();
		try {
			String userId = request.getParameter("userId");
			String nickname = request.getParameter("nickname");
			SessionInfo s = getSessionInfo(request);
			if(F.empty(userId)){
				userId = s.getId();
			}
			if(!F.empty(nickname)) {
				nickname = nickname.startsWith("@") ? nickname.substring(1) : nickname;
				User u = new User();
				u.setNickname(nickname);
				u = userService.get(u);
				if(u == null) {
					j.setSuccess(false);
					j.setMsg("不存在该用户");
					return j;
				} else {
					userId = u.getId();
				}
			}
			Map map = userService.userIndex(userId);
			if(s!=null){
				if(userAttentionService.get(s.getId(), userId)!=null){
					map.put("attred", Constants.GLOBAL_BOOLEAN_TRUE);
				}else{
					map.put("attred", Constants.GLOBAL_BOOLEAN_FALSE);
				}
			}else{
				map.put("attred", Constants.GLOBAL_NOT_LOGIN);
			}
			if(map == null){
				j.setSuccess(false);
				j.setMsg("不存在该用户");
			}else{
				User user = userService.get(userId, true);
				map.put("user", user);
				j.setSuccess(true);
				j.setObj(map);
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 用户信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user_info")
	public Json userInfo(String userId, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			if(F.empty(userId))
				userId = s.getId();
			User user = userService.get(userId, true);
			if(user == null){
				j.setSuccess(false);
				j.setMsg("不存在该用户");
			}else{
				user.setPwd(null);
				j.setSuccess(true);
				j.setObj(user);
			}
		
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 个人信息修改
	 * @param lvAccount
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(User user, @RequestParam(required=false) MultipartFile headImageFile, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			user.setId(s.getId());
			user.setName(s.getName());
			if(!F.empty(user.getEmail())) {
				User u = new User();
				u.setEmail(user.getEmail());
				u.setId(s.getId());
				if(userService.exists(u)) {
					j.setSuccess(false);
					j.setMsg("邮箱已被使用！");
					return j;
				}
			}
			if(!F.empty(user.getNickname())) {
				User u = new User();
				u.setNickname(user.getNickname());
				u.setId(s.getId());
				if(userService.exists(u)) {
					j.setSuccess(false);
					j.setMsg("昵称已被使用！");
					return j;
				}
			} else {
				user.setNickname(null);
			}
			
			uploadFile(request, user, headImageFile);
			userService.edit(user);			
			j.setSuccess(true);
			j.setMsg("个人信息修改成功");
			
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 我的美拍
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/user_mybshoots")
	@ResponseBody
	public Json dataGridMyBs(Bshoot bshoot, PageHelper ph,HttpServletRequest request) {
		
		Json j = new Json();
		try {
			String userId = null;
			SessionInfo s = getSessionInfo(request);
			if(s != null) {
				userId = s.getId();
			}
			if(F.empty(bshoot.getUserId())){
				bshoot.setUserId(s.getId());
			}
			j.setObj(bshootService.dataGrid(bshoot, ph, 1, userId));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	

	/**
	 * 我的转发
	 * @param bshoot
	 * @param ph
	 * @param request
	 * @return
	 */
	@RequestMapping("/user_mytranspond")
	@ResponseBody
	public Json dataGridMytranspond(Bshoot bshoot, PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			String userId = null;
			SessionInfo s = getSessionInfo(request);
			if(s != null) {
				userId = s.getId();
			}
			if(F.empty(bshoot.getUserId())){
				bshoot.setUserId(s.getId());
			}
			j.setObj(bshootService.dataGrid(bshoot, ph,2,userId));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 删除美拍/转发
	 * @param bshootId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delBshoot")
	public Json delBshoot(String bshootId, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			Bshoot bshoot = bshootService.get(bshootId);
			if(s.getId().equals(bshoot.getUserId())) {
				bshoot.setStatus("-1");
				bshootService.edit(bshoot);
				j.setSuccess(true);
				j.setMsg("删除成功");
			} else {
				j.setSuccess(false);
				j.setMsg("无权删除他人视频");
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 我关注的好友
	 * @param bshoot
	 * @param ph
	 * @param request
	 * @return
	 */
	@RequestMapping("/user_myattruser")
	@ResponseBody
	public Json dataGridMyattruser(UserAttention userAttention, PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			if(F.empty(userAttention.getUserId())){
				SessionInfo s = getSessionInfo(request);
				userAttention.setUserId(s.getId());
			}
			j.setObj(userAttentionService.dataGridUser(userAttention, ph));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 我的粉丝
	 * @param bshoot
	 * @param ph
	 * @param request
	 * @return
	 */
	@RequestMapping("/user_myattreduser")
	@ResponseBody
	public Json dataGridMyattreduser(UserAttention userAttention, PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			String userId = null;
			SessionInfo s = getSessionInfo(request);
			if(s != null) {
				userId = s.getId();
			}
			if(F.empty(userAttention.getUserId())){
				userAttention.setAttUserId(s.getId());
			}else{
				userAttention.setAttUserId(userAttention.getUserId());
			}
			userAttention.setUserId(null);
			j.setObj(userAttentionService.dataGridUser(userAttention, ph, userId));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 视频投稿到广场
	 * @param bshootToSquare
	 * @param request
	 * @return
	 */
	@RequestMapping("/user_bshootToSquare")
	@ResponseBody
	public Json bshootToSquare(BshootToSquare bshootToSquare, HttpServletRequest request) {
		Json j = new Json();	
		try {
			int i = bshootToSquareService.addFromUser(bshootToSquare);
			if(i==1){
				j.setSuccess(true);
				j.setMsg("添加成功！");		
			}else if(i==-1){
				j.setSuccess(false);
				j.setMsg("在审核中或已经上传到广场");	
			}else{
				j.setSuccess(false);
				j.setMsg("失败");
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 美拍小技巧
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/bshootSkill")
	@ResponseBody
	public Json dataGridBshootSkill(BshootSkill bshootSkill, PageHelper ph,HttpServletRequest request) {
		
		Json j = new Json();
		try {
			ph.setOrder("desc");
			ph.setSort("hot");
			j.setObj(bshootSkillService.dataGrid(bshootSkill, ph));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
}

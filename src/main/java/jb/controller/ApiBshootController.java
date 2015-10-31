package jb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.model.TbshootComment;
import jb.pageModel.Bshoot;
import jb.pageModel.BshootCollect;
import jb.pageModel.BshootComment;
import jb.pageModel.BshootPraise;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.pageModel.SessionInfo;
import jb.pageModel.User;
import jb.service.BshootCollectServiceI;
import jb.service.BshootCommentServiceI;
import jb.service.BshootPraiseServiceI;
import jb.service.BshootServiceI;
import jb.service.UserAttentionServiceI;
import jb.service.UserServiceI;
import jb.util.Constants;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Bshoot管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/bshootController")
public class ApiBshootController extends BaseController {
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private TokenManage tokenManage;
	
	@Autowired
	private BshootServiceI bshootService;
	
	@Autowired
	private BshootCollectServiceI bshootCollectService;
	
	@Autowired
	private BshootPraiseServiceI bshootPraiseService;
	
	@Autowired
	private UserAttentionServiceI userAttentionService;
	
	@Autowired
	private BshootCommentServiceI bshootCommentService;
	
	/**
	 * 美拍点赞
	 * @param bshootPraise
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bshoot_praise")
	public Json bshootPraise(BshootPraise bshootPraise,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			bshootPraise.setUserId(s.getId());
			int r = bshootPraiseService.add(bshootPraise);
			if(r==-1){
				j.setSuccess(false);
				j.setMsg("已经赞过了！");
			}else{
				Bshoot bshoot = bshootService.get(bshootPraise.getBshootId());
				if(!s.getId().equals(bshoot.getUserId())) {
					addMessage("MT04", bshoot.getUserId(), bshootPraise.getId());
				}
				j.setSuccess(true);
				j.setMsg("成功！");
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 美拍取消点赞
	 * @param bshootPraise
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bshoot_dispraise")
	public Json bshootPraiseCancle(BshootPraise bshootPraise,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			bshootPraise.setUserId(s.getId());
			int r = bshootPraiseService.deleteBshootPraise(bshootPraise);
			if(r==-1){
				j.setSuccess(false);
				j.setMsg("已经取消！");
			}else{
				j.setSuccess(true);
				j.setMsg("成功！");
				//addMessage("MT04",bshootPraise.getBshootId());
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 美拍详情
	 * @param b
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bshoot_detail")
	public Json bshootDetail(Bshoot b,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);		
			Bshoot bshoot = bshootService.get(b.getId());
			User user = userService.get(bshoot.getUserId(), true);
			PageHelper ph = new PageHelper();
			ph.setPage(1);
			ph.setRows(20);
			ph.setSort("commentDatetime");
			BshootComment bc = new BshootComment();
			bc.setBshootId(b.getId());
			DataGrid dataGrid  = null;
			// 登录情况下
			if(s != null){
				// 是否赞过
				if(bshootPraiseService.get(b.getId(), s.getId()) != null){
					bshoot.setPraised(Constants.GLOBAL_BOOLEAN_TRUE);
				}else{
					bshoot.setPraised(Constants.GLOBAL_BOOLEAN_FALSE);
				}
				
				// 用户是否关注过
				if(userAttentionService.get(s.getId(), user.getId()) != null){
					user.setAttred(Constants.GLOBAL_BOOLEAN_TRUE);
				}else{
					user.setAttred(Constants.GLOBAL_BOOLEAN_FALSE);
				}
				
				// 评论是否赞过	
				dataGrid  = bshootCommentService.dataGrid(bc, ph,s.getId());
				
			}else{
				user.setAttred(Constants.GLOBAL_NOT_LOGIN);
				bshoot.setPraised(Constants.GLOBAL_NOT_LOGIN);
				dataGrid  = bshootCommentService.dataGrid(bc, ph);
			}
			
			Map<String,Object> obj = new HashMap<String,Object>();
			obj.put("bshoot", bshoot);
			obj.put("user", user);
			obj.put("comments", dataGrid);
			j.setObj(obj);		
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 上传美拍
	 * 
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Json uploadBshoot(Bshoot bshoot,@RequestParam(required=false) MultipartFile[] movies,
			@RequestParam(required=false) MultipartFile[] icons, HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			String realPath = request.getSession().getServletContext().getRealPath("/"+Constants.UPLOADFILE+"/"+s.getName());  
			File file = new File(realPath);
			bshoot.setId(UUID.randomUUID().toString());
			bshoot.setUserId(s.getId());
			if(!file.exists())
				file.mkdir();
			
			if(movies != null) {
				String bsStream = "";
				for(MultipartFile f : movies){
					if(f == null || f.isEmpty()) continue;
					String suffix = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf("."));
					String fileName = bshoot.getId()+suffix;
					if(!"".equals(bsStream)) {
						bsStream += ";";
					}
					bsStream += s.getName()+"/"+fileName;
					 try {
						FileUtils.copyInputStreamToFile(f.getInputStream(), new File(realPath, fileName));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
				bshoot.setBsStream(bsStream);
			}
			
			if(icons != null) {
				String bsIcon = "";
				for(MultipartFile f : icons){
					if(f == null || f.isEmpty()) continue;
					String suffix = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf("."));
					String fileName = bshoot.getId()+suffix;
					if(!"".equals(bsIcon)) {
						bsIcon += ";";
					}
					bsIcon += s.getName()+"/"+fileName;
					 try {
						FileUtils.copyInputStreamToFile(f.getInputStream(), new File(realPath, fileName));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
				bshoot.setBsIcon(bsIcon);
			}
			
			List<String> attUserIds = bshootService.addBshoot(bshoot);	
			if(attUserIds != null && attUserIds.size() > 0) {
				for(String attUserId : attUserIds) {
					addMessage("MT02", attUserId, bshoot.getId(), "BSHOOT");
				}
			}
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	
	/**
	 * 转发美拍
	 * 
	 * @return
	 */
	@RequestMapping("/bshoot_transupload")
	@ResponseBody
	public Json uploadBshoot(Bshoot bshoot, HttpServletRequest request) {
		Json j = new Json();	
		try {
			SessionInfo s = getSessionInfo(request);
			Bshoot parent = bshootService.get(bshoot.getParentId());
			if(!F.empty(parent.getParentId())) {
				bshoot.setParentId(parent.getParentId());
			}
			bshoot.setId(UUID.randomUUID().toString());
			bshoot.setUserId(s.getId());		
	//		bshoot.setBsStream(parent.getBsStream());
	//		bshoot.setBsIcon(parent.getBsIcon());
			bshootService.addBshoot(bshoot);	
			j.setSuccess(true);
			j.setMsg("添加成功！");	
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
//	/**
//	 * 获取Bshoot数据表格
//	 * 
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping("/dataGrid")
//	@ResponseBody
//	public DataGrid dataGrid(Bshoot bshoot, PageHelper ph,HttpServletRequest request) {
//		DataGrid dg = bshootService.dataGrid(bshoot, ph);
//		
//		return dg;
//	}
	
	/**
	 * 视频评论分页找
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/bshoot_comments")
	@ResponseBody
	public Json dataGridBshoot(BshootComment bshootComment, PageHelper ph,HttpServletRequest request) {
		
		Json j = new Json();	
		try {
			SessionInfo s = tokenManage.getSessionInfo(request);
			ph.setSort("commentDatetime");
			j.setObj(bshootCommentService.dataGrid(bshootComment, ph, s.getId()));
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 添加评论
	 * 
	 * @return
	 */
	@RequestMapping("/bshoot_addcomment")
	@ResponseBody
	public Json addComment(BshootComment bshootComment, HttpServletRequest request) {
		Json j = new Json();		
		try {
			SessionInfo s = getSessionInfo(request);
			bshootComment.setUserId(s.getId());
			TbshootComment tbc = bshootCommentService.add(bshootComment);
			Bshoot bshoot = bshootService.get(bshootComment.getBshootId());
			if(!s.getId().equals(bshoot.getUserId())) {
				addMessage("MT03", bshoot.getUserId(), tbc.getId());
			}
			addAtMineMessage(bshootComment);
			j.setSuccess(true);
			j.setMsg("添加成功！");		
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	private void addAtMineMessage(BshootComment bshootComment) {
		List<String> attUserIds = new ArrayList<String>();
		String bsCommentText = bshootComment.getBsCommentText() + " ";
		// 建立视频-@关注好友关系
		String regex="@[^@]+?(?=[\\s:：(),.。@])";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(bsCommentText);
		while(m.find()){
			String nickname = m.group().substring(1); // 去除@
			User user = new User();
			user.setNickname(nickname);
			user = userService.get(user);
			if(user != null && !attUserIds.contains(user.getId()) && !user.getId().equals(bshootComment.getUserId())) {
				attUserIds.add(user.getId());
				addMessage("MT02", user.getId(), bshootComment.getId(), "COMMENT");
			}
		}
	}

	/**
	 * 删除评论
	 * 
	 * @return
	 */
	@RequestMapping("/bshoot_delcomment")
	@ResponseBody
	public Json delComment(BshootComment bshootComment, HttpServletRequest request) {
		Json j = new Json();		
		try {
			SessionInfo s = getSessionInfo(request);
			bshootComment = bshootCommentService.get(bshootComment.getId());
			Bshoot bshoot = bshootService.get(bshootComment.getId());
			if(s.getId().equals(bshootComment.getUserId()) || s.getId().equals(bshoot.getUserId())) {
				bshootCommentService.delete(bshootComment.getId());
				j.setSuccess(true);
				j.setMsg("删除成功！");
			} else {
				j.setSuccess(false);
				j.setMsg("无权删除他人评论！");	
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	private SessionInfo getSessionInfo(HttpServletRequest request){
		SessionInfo s = tokenManage.getSessionInfo(request);
		return s;
		
	}
	
	/**
	 * 美拍播放次数+1
	 * @param bshootPraise
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bshoot_addPlay")
	public Json bshootAddPlay(String bshootId) {
		Json j = new Json();
		try {
			bshootService.updatePlayNum(bshootId);
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 首页搜索（美拍列表）
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/bshoot_bshootSearch")
	@ResponseBody
	public Json dataGridSearch(String keyword, PageHelper ph,HttpServletRequest request) {
		
		Json j = new Json();
		try {
			ph.setOrder("desc");
			ph.setSort("bsPraise");
			String userId = null;
			SessionInfo s = getSessionInfo(request);
			if(s != null) {
				userId = s.getId();
			}
			Bshoot bshoot = new Bshoot();
			bshoot.setBsDescription(keyword);
			j.setObj(bshootService.dataGridSearch(bshoot, ph, userId));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 首页搜索（相关用户）
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/bshoot_userSearch")
	@ResponseBody
	public Json dataGridUserSearch(String keyword, PageHelper ph,HttpServletRequest request) {
		
		Json j = new Json();
		try {
			String userId = null;
			SessionInfo s = getSessionInfo(request);
			if(s != null) {
				userId = s.getId();
			}
			User user = new User();
			user.setNickname(keyword);
			j.setObj(userService.dataGridSearch(user, ph, userId));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 美拍收藏
	 * @param bshoot
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bshoot_collect")
	public Json bshootCollect(BshootCollect bshoot,HttpServletRequest request) {
		Json j = new Json();
		SessionInfo s = getSessionInfo(request);
		bshoot.setUserId(s.getId());
		int r = bshootCollectService.add(bshoot);
		if(r==-1){
			j.setSuccess(false);
			j.setMsg("已经收藏！");
		}else{
			j.setSuccess(true);
			j.setMsg("成功！");
		}
		/*bshootCollectService.add(bshoot);			
		j.setSuccess(true);
		j.setMsg("成功！");*/
		return j;
	}
	
	/**
	 * 美拍收藏取消
	 * @param bshoot
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bshoot_discollect")
	public Json bshootCollectCancle(BshootCollect bshoot,HttpServletRequest request) {
		Json j = new Json();
		SessionInfo s = getSessionInfo(request);
		bshoot.setUserId(s.getId());
		int r = bshootCollectService.deleteCollect(bshoot);
		if(r==-1){
			j.setSuccess(false);
			j.setMsg("已经取消！");
		}else{
			j.setSuccess(true);
			j.setMsg("成功！");
		}
		/*bshootCollectService.add(bshoot);			
		j.setSuccess(true);
		j.setMsg("成功！");*/
		return j;
	}
}

package jb.controller;

import jb.pageModel.CommentPraise;
import jb.pageModel.Json;
import jb.pageModel.SessionInfo;
import jb.service.CommentPraiseServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 评论点赞管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiCommentController")
public class ApiCommentController extends BaseController {

	@Autowired
	private CommentPraiseServiceI commentPraiseService;
	
	
	/**
	 * 评论赞
	 * @param commentPraise
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/commentPraise")
	public Json commentPraise(CommentPraise commentPraise,HttpServletRequest request) {
		Json j = new Json();	
		try {
			SessionInfo s = getSessionInfo(request);
			commentPraise.setUserId(s.getId());		
			int r = commentPraiseService.add(commentPraise);
			if(r==-1){
				j.setSuccess(false);
				j.setMsg("已经赞过！");
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
	 * 取消评论赞
	 * @param commentPraise
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/disCommentPraise")
	public Json disCommentPraise(CommentPraise commentPraise,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			commentPraise.setUserId(s.getId());
			int r = commentPraiseService.deleteCommentPraise(commentPraise);
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
	
	
}

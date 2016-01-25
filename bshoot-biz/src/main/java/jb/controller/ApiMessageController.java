package jb.controller;

import jb.pageModel.*;
import jb.service.BshootPraiseServiceI;
import jb.service.MessageCountServiceI;
import jb.service.MessageServiceI;
import jb.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 消息管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiMessageController")
public class ApiMessageController extends BaseController {
	@Autowired
	private MessageServiceI messageService;
	
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private MessageCountServiceI messageCountService; 
	
	@Autowired
	private BshootPraiseServiceI bshootPraiseService;

	/**
	 * 新朋友
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/message_newFriend")
	@ResponseBody
	public Json dataGridNewFriend(PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			User user = new User();
			user.setId(s.getId());
			DataGrid dg = userService.dataGridNewFriend(user, ph);
			clearMessageCount(s.getId(),"MT01");
			j.setObj(dg);
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * “@我的”消息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/message_atMine")
	@ResponseBody
	public Json dataGridAtMine(PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			Message message = new Message();
			message.setUserId(s.getId());
			message.setMtype("MT02");
			DataGrid dg = messageService.dataGridAtMine(message, ph);
			clearMessageCount(s.getId(),"MT02");
			j.setObj(dg);
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 评论消息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/message_comment")
	@ResponseBody
	public Json dataGridComment(PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			Message message = new Message();
			message.setUserId(s.getId());
			message.setMtype("MT03");
			DataGrid dg = messageService.dataGridComment(message, ph);
			clearMessageCount(s.getId(),"MT03");
			j.setObj(dg);
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 赞消息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/message_praise")
	@ResponseBody
	public Json dataGridPraise(PageHelper ph,HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			Message message = new Message();
			message.setUserId(s.getId());
			message.setMtype("MT04");
			DataGrid dg = messageService.dataGridPraise(message, ph);
			clearMessageCount(s.getId(),"MT04");
			j.setObj(dg);
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 统计消息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/message_count")
	@ResponseBody
	public Json getMessageCounts(HttpServletRequest request) {
		
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			MessageCount messageCount = new MessageCount();
			messageCount.setUserId(s.getId());
			j.setObj(messageCountService.getMessageCounts(messageCount));
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 推送消息测试
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/message_test")
	@ResponseBody
	public Json test(String name, String type) {
		
		Json j = new Json();
		try {
			messageService.sendMessage(name, "{\"mnumber\":2, \"mtype\":\""+type+"\"}");
			j.success();
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}	
	
	/**
	 * 删除消息统计数据
	 * @param userId
	 * @param mtype
	 */
	private void clearMessageCount(String userId,String mtype){
		messageCountService.deleteMessageCount(userId, mtype);
	}
}

package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.UserFriendTime;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.UserFriendTimeServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * UserFriendTime管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/userFriendTimeController")
public class UserFriendTimeController extends BaseController {

	@Autowired
	private UserFriendTimeServiceI userFriendTimeService;


	/**
	 * 跳转到UserFriendTime管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/userfriendtime/userFriendTime";
	}

	/**
	 * 获取UserFriendTime数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(UserFriendTime userFriendTime, PageHelper ph) {
		return userFriendTimeService.dataGrid(userFriendTime, ph);
	}
	/**
	 * 获取UserFriendTime数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(UserFriendTime userFriendTime, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(userFriendTime,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加UserFriendTime页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		UserFriendTime userFriendTime = new UserFriendTime();
		userFriendTime.setId(UUID.randomUUID().toString());
		return "/userfriendtime/userFriendTimeAdd";
	}

	/**
	 * 添加UserFriendTime
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserFriendTime userFriendTime) {
		Json j = new Json();		
		userFriendTimeService.add(userFriendTime);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到UserFriendTime查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		UserFriendTime userFriendTime = userFriendTimeService.get(id);
		request.setAttribute("userFriendTime", userFriendTime);
		return "/userfriendtime/userFriendTimeView";
	}

	/**
	 * 跳转到UserFriendTime修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		UserFriendTime userFriendTime = userFriendTimeService.get(id);
		request.setAttribute("userFriendTime", userFriendTime);
		return "/userfriendtime/userFriendTimeEdit";
	}

	/**
	 * 修改UserFriendTime
	 * 
	 * @param userFriendTime
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserFriendTime userFriendTime) {
		Json j = new Json();		
		userFriendTimeService.edit(userFriendTime);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除UserFriendTime
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		userFriendTimeService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

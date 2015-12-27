package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.UserPersonTime;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.UserPersonTimeServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * UserPersonTime管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/userPersonTimeController")
public class UserPersonTimeController extends BaseController {

	@Autowired
	private UserPersonTimeServiceI userPersonTimeService;


	/**
	 * 跳转到UserPersonTime管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/userpersontime/userPersonTime";
	}

	/**
	 * 获取UserPersonTime数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(UserPersonTime userPersonTime, PageHelper ph) {
		return userPersonTimeService.dataGrid(userPersonTime, ph);
	}
	/**
	 * 获取UserPersonTime数据表格excel
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
	public void download(UserPersonTime userPersonTime, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(userPersonTime,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加UserPersonTime页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		UserPersonTime userPersonTime = new UserPersonTime();
		userPersonTime.setId(UUID.randomUUID().toString());
		return "/userpersontime/userPersonTimeAdd";
	}

	/**
	 * 添加UserPersonTime
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserPersonTime userPersonTime) {
		Json j = new Json();		
		userPersonTimeService.add(userPersonTime);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到UserPersonTime查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		UserPersonTime userPersonTime = userPersonTimeService.get(id);
		request.setAttribute("userPersonTime", userPersonTime);
		return "/userpersontime/userPersonTimeView";
	}

	/**
	 * 跳转到UserPersonTime修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		UserPersonTime userPersonTime = userPersonTimeService.get(id);
		request.setAttribute("userPersonTime", userPersonTime);
		return "/userpersontime/userPersonTimeEdit";
	}

	/**
	 * 修改UserPersonTime
	 * 
	 * @param userPersonTime
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserPersonTime userPersonTime) {
		Json j = new Json();		
		userPersonTimeService.edit(userPersonTime);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除UserPersonTime
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		userPersonTimeService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

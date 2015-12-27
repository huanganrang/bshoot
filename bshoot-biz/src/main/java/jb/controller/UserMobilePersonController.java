package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.UserMobilePerson;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.UserMobilePersonServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * UserMobilePerson管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/userMobilePersonController")
public class UserMobilePersonController extends BaseController {

	@Autowired
	private UserMobilePersonServiceI userMobilePersonService;


	/**
	 * 跳转到UserMobilePerson管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/usermobileperson/userMobilePerson";
	}

	/**
	 * 获取UserMobilePerson数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(UserMobilePerson userMobilePerson, PageHelper ph) {
		return userMobilePersonService.dataGrid(userMobilePerson, ph);
	}
	/**
	 * 获取UserMobilePerson数据表格excel
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
	public void download(UserMobilePerson userMobilePerson, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(userMobilePerson,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加UserMobilePerson页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		UserMobilePerson userMobilePerson = new UserMobilePerson();
		userMobilePerson.setId(UUID.randomUUID().toString());
		return "/usermobileperson/userMobilePersonAdd";
	}

	/**
	 * 添加UserMobilePerson
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserMobilePerson userMobilePerson) {
		Json j = new Json();		
		userMobilePersonService.add(userMobilePerson);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到UserMobilePerson查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		UserMobilePerson userMobilePerson = userMobilePersonService.get(id);
		request.setAttribute("userMobilePerson", userMobilePerson);
		return "/usermobileperson/userMobilePersonView";
	}

	/**
	 * 跳转到UserMobilePerson修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		UserMobilePerson userMobilePerson = userMobilePersonService.get(id);
		request.setAttribute("userMobilePerson", userMobilePerson);
		return "/usermobileperson/userMobilePersonEdit";
	}

	/**
	 * 修改UserMobilePerson
	 * 
	 * @param userMobilePerson
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserMobilePerson userMobilePerson) {
		Json j = new Json();		
		userMobilePersonService.edit(userMobilePerson);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除UserMobilePerson
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		userMobilePersonService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

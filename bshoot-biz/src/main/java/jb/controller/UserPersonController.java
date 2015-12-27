package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.UserPerson;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.UserPersonServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * UserPerson管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/userPersonController")
public class UserPersonController extends BaseController {

	@Autowired
	private UserPersonServiceI userPersonService;


	/**
	 * 跳转到UserPerson管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/userperson/userPerson";
	}

	/**
	 * 获取UserPerson数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(UserPerson userPerson, PageHelper ph) {
		return userPersonService.dataGrid(userPerson, ph);
	}
	/**
	 * 获取UserPerson数据表格excel
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
	public void download(UserPerson userPerson, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(userPerson,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加UserPerson页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		UserPerson userPerson = new UserPerson();
		userPerson.setId(UUID.randomUUID().toString());
		return "/userperson/userPersonAdd";
	}

	/**
	 * 添加UserPerson
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserPerson userPerson) {
		Json j = new Json();		
		userPersonService.add(userPerson);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到UserPerson查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		UserPerson userPerson = userPersonService.get(id);
		request.setAttribute("userPerson", userPerson);
		return "/userperson/userPersonView";
	}

	/**
	 * 跳转到UserPerson修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		UserPerson userPerson = userPersonService.get(id);
		request.setAttribute("userPerson", userPerson);
		return "/userperson/userPersonEdit";
	}

	/**
	 * 修改UserPerson
	 * 
	 * @param userPerson
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserPerson userPerson) {
		Json j = new Json();		
		userPersonService.edit(userPerson);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除UserPerson
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		userPersonService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

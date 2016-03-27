package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import jb.pageModel.*;
import jb.service.BasedataServiceI;
import jb.service.UserHobbyServiceI;

import jb.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * UserHobby管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/userHobbyController")
public class UserHobbyController extends BaseController {

	@Autowired
	private UserHobbyServiceI userHobbyService;
    @Autowired
	private BasedataServiceI basedataService;
    private final String HOBBYTYPE="HO";
	/**
	 * 跳转到UserHobby管理页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/userhobby/userHobby";
	}

	/**
	 * 获取UserHobby数据表格
	 * 
	 * @param user
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(UserHobby userHobby, PageHelper ph) {
		return userHobbyService.dataGrid(userHobby, ph);
	}
	/**
	 * 获取UserHobby数据表格excel
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
	@ApiIgnore
	@RequestMapping("/download")
	public void download(UserHobby userHobby, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(userHobby,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加UserHobby页面
	 * 
	 * @param request
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		UserHobby userHobby = new UserHobby();
		userHobby.setId(UUID.randomUUID().toString());
		return "/userhobby/userHobbyAdd";
	}

	/**
	 * 添加UserHobby
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserHobby userHobby) {
		Json j = new Json();		
		userHobbyService.add(userHobby);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到UserHobby查看页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		UserHobby userHobby = userHobbyService.get(id);
		request.setAttribute("userHobby", userHobby);
		return "/userhobby/userHobbyView";
	}

	/**
	 * 跳转到UserHobby修改页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		UserHobby userHobby = userHobbyService.get(id);
		request.setAttribute("userHobby", userHobby);
		return "/userhobby/userHobbyEdit";
	}

	/**
	 * 修改UserHobby
	 * 
	 * @param userHobby
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserHobby userHobby) {
		Json j = new Json();		
		userHobbyService.edit(userHobby);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

}

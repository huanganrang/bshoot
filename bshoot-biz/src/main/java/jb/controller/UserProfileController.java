package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.UserProfile;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.UserProfileServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * UserProfile管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/userProfileController")
public class UserProfileController extends BaseController {

	@Autowired
	private UserProfileServiceI userProfileService;


	/**
	 * 跳转到UserProfile管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/userprofile/userProfile";
	}

	/**
	 * 获取UserProfile数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(UserProfile userProfile, PageHelper ph) {
		return userProfileService.dataGrid(userProfile, ph);
	}
	/**
	 * 获取UserProfile数据表格excel
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
	public void download(UserProfile userProfile, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(userProfile,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加UserProfile页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		UserProfile userProfile = new UserProfile();
		userProfile.setId(UUID.randomUUID().toString());
		return "/userprofile/userProfileAdd";
	}

	/**
	 * 添加UserProfile
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserProfile userProfile) {
		Json j = new Json();		
		userProfileService.add(userProfile);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到UserProfile查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		UserProfile userProfile = userProfileService.get(id);
		request.setAttribute("userProfile", userProfile);
		return "/userprofile/userProfileView";
	}

	/**
	 * 跳转到UserProfile修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		UserProfile userProfile = userProfileService.get(id);
		request.setAttribute("userProfile", userProfile);
		return "/userprofile/userProfileEdit";
	}

	/**
	 * 修改UserProfile
	 * 
	 * @param userProfile
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserProfile userProfile) {
		Json j = new Json();		
		userProfileService.edit(userProfile);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除UserProfile
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		userProfileService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

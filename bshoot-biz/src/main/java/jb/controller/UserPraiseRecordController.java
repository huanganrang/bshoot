package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.Colum;
import jb.pageModel.UserPraiseRecord;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.UserPraiseRecordServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * UserPraiseRecord管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/userPraiseRecordController")
public class UserPraiseRecordController extends BaseController {

	@Autowired
	private UserPraiseRecordServiceI userPraiseRecordService;


	/**
	 * 跳转到UserPraiseRecord管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/userpraiserecord/userPraiseRecord";
	}

	/**
	 * 获取UserPraiseRecord数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(UserPraiseRecord userPraiseRecord, PageHelper ph) {
		return userPraiseRecordService.dataGrid(userPraiseRecord, ph);
	}
	/**
	 * 获取UserPraiseRecord数据表格excel
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
	public void download(UserPraiseRecord userPraiseRecord, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(userPraiseRecord,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加UserPraiseRecord页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		UserPraiseRecord userPraiseRecord = new UserPraiseRecord();
		userPraiseRecord.setId(UUID.randomUUID().toString());
		return "/userpraiserecord/userPraiseRecordAdd";
	}

	/**
	 * 添加UserPraiseRecord
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserPraiseRecord userPraiseRecord) {
		Json j = new Json();		
		userPraiseRecordService.add(userPraiseRecord);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到UserPraiseRecord查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		UserPraiseRecord userPraiseRecord = userPraiseRecordService.get(id);
		request.setAttribute("userPraiseRecord", userPraiseRecord);
		return "/userpraiserecord/userPraiseRecordView";
	}

	/**
	 * 跳转到UserPraiseRecord修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		UserPraiseRecord userPraiseRecord = userPraiseRecordService.get(id);
		request.setAttribute("userPraiseRecord", userPraiseRecord);
		return "/userpraiserecord/userPraiseRecordEdit";
	}

	/**
	 * 修改UserPraiseRecord
	 * 
	 * @param userPraiseRecord
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserPraiseRecord userPraiseRecord) {
		Json j = new Json();		
		userPraiseRecordService.edit(userPraiseRecord);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除UserPraiseRecord
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		userPraiseRecordService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

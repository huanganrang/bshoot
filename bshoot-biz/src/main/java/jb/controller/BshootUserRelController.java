package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.BshootUserRelServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * BshootUserRel管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/bshootUserRelController")
public class BshootUserRelController extends BaseController {

	@Autowired
	private BshootUserRelServiceI bshootUserRelService;


	/**
	 * 跳转到BshootUserRel管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/bshootuserrel/bshootUserRel";
	}

	/**
	 * 获取BshootUserRel数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(BshootUserRel bshootUserRel, PageHelper ph) {
		return bshootUserRelService.dataGrid(bshootUserRel, ph);
	}
	/**
	 * 获取BshootUserRel数据表格excel
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
	public void download(BshootUserRel bshootUserRel, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(bshootUserRel,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加BshootUserRel页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		BshootUserRel bshootUserRel = new BshootUserRel();
		bshootUserRel.setId(UUID.randomUUID().toString());
		return "/bshootuserrel/bshootUserRelAdd";
	}

	/**
	 * 添加BshootUserRel
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(BshootUserRel bshootUserRel) {
		Json j = new Json();		
		bshootUserRelService.add(bshootUserRel);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到BshootUserRel查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		BshootUserRel bshootUserRel = bshootUserRelService.get(id);
		request.setAttribute("bshootUserRel", bshootUserRel);
		return "/bshootuserrel/bshootUserRelView";
	}

	/**
	 * 跳转到BshootUserRel修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		BshootUserRel bshootUserRel = bshootUserRelService.get(id);
		request.setAttribute("bshootUserRel", bshootUserRel);
		return "/bshootuserrel/bshootUserRelEdit";
	}

	/**
	 * 修改BshootUserRel
	 * 
	 * @param bshootUserRel
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(BshootUserRel bshootUserRel) {
		Json j = new Json();		
		bshootUserRelService.edit(bshootUserRel);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除BshootUserRel
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		bshootUserRelService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

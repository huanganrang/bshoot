package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.BshootSquareRelServiceI;
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
 * BshootSquareRel管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/bshootSquareRelController")
public class BshootSquareRelController extends BaseController {

	@Autowired
	private BshootSquareRelServiceI bshootSquareRelService;


	/**
	 * 跳转到BshootSquareRel管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/bshootsquarerel/bshootSquareRel";
	}

	/**
	 * 获取BshootSquareRel数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(BshootSquareRel bshootSquareRel, PageHelper ph) {
		return bshootSquareRelService.dataGrid(bshootSquareRel, ph);
	}
	/**
	 * 获取BshootSquareRel数据表格excel
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
	public void download(BshootSquareRel bshootSquareRel, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(bshootSquareRel,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加BshootSquareRel页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		BshootSquareRel bshootSquareRel = new BshootSquareRel();
		bshootSquareRel.setId(UUID.randomUUID().toString());
		return "/bshootsquarerel/bshootSquareRelAdd";
	}

	/**
	 * 添加BshootSquareRel
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(BshootSquareRel bshootSquareRel) {
		Json j = new Json();		
		bshootSquareRelService.add(bshootSquareRel);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到BshootSquareRel查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		BshootSquareRel bshootSquareRel = bshootSquareRelService.get(id);
		request.setAttribute("bshootSquareRel", bshootSquareRel);
		return "/bshootsquarerel/bshootSquareRelView";
	}

	/**
	 * 跳转到BshootSquareRel修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		BshootSquareRel bshootSquareRel = bshootSquareRelService.get(id);
		request.setAttribute("bshootSquareRel", bshootSquareRel);
		return "/bshootsquarerel/bshootSquareRelEdit";
	}

	/**
	 * 修改BshootSquareRel
	 * 
	 * @param bshootSquareRel
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(BshootSquareRel bshootSquareRel) {
		Json j = new Json();		
		bshootSquareRelService.edit(bshootSquareRel);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除BshootSquareRel
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		bshootSquareRelService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

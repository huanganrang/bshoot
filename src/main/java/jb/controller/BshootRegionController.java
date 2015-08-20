package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jb.pageModel.BshootRegion;
import jb.pageModel.Colum;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.service.BshootRegionServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * BshootRegion管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/bshootRegionController")
public class BshootRegionController extends BaseController {

	@Autowired
	private BshootRegionServiceI bshootRegionService;


	/**
	 * 跳转到BshootRegion管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/bshootregion/bshootRegion";
	}

	/**
	 * 获取BshootRegion数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(BshootRegion bshootRegion, PageHelper ph) {
		return bshootRegionService.dataGrid(bshootRegion, ph);
	}
	/**
	 * 获取BshootRegion数据表格excel
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
	public void download(BshootRegion bshootRegion, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(bshootRegion,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加BshootRegion页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/bshootregion/bshootRegionAdd";
	}

	/**
	 * 添加BshootRegion
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(BshootRegion bshootRegion) {
		Json j = new Json();		
		bshootRegionService.add(bshootRegion);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到BshootRegion查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		BshootRegion bshootRegion = bshootRegionService.get(id);
		request.setAttribute("bshootRegion", bshootRegion);
		return "/bshootregion/bshootRegionView";
	}

	/**
	 * 跳转到BshootRegion修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		BshootRegion bshootRegion = bshootRegionService.get(id);
		request.setAttribute("bshootRegion", bshootRegion);
		return "/bshootregion/bshootRegionEdit";
	}

	/**
	 * 修改BshootRegion
	 * 
	 * @param bshootRegion
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(BshootRegion bshootRegion) {
		Json j = new Json();		
		bshootRegionService.edit(bshootRegion);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除BshootRegion
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		bshootRegionService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

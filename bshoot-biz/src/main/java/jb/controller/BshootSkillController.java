package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.BasedataServiceI;
import jb.service.BshootSkillServiceI;
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
 * BshootSkill管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/bshootSkillController")
public class BshootSkillController extends BaseController {

	@Autowired
	private BshootSkillServiceI bshootSkillService;

	@Autowired
	private BasedataServiceI basedataService;

	/**
	 * 跳转到BshootSkill管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/bshootskill/bshootSkill";
	}

	/**
	 * 获取BshootSkill数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(BshootSkill bshootSkill, PageHelper ph) {
		return bshootSkillService.dataGrid(bshootSkill, ph);
	}
	/**
	 * 获取BshootSkill数据表格excel
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
	public void download(BshootSkill bshootSkill, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(bshootSkill,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加BshootSkill页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		BshootSkill bshootSkill = new BshootSkill();
		bshootSkill.setId(UUID.randomUUID().toString());
		request.setAttribute("btlist", basedataService.getBaseDatas("ST"));
		return "/bshootskill/bshootSkillAdd";
	}

	/**
	 * 添加BshootSkill
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(BshootSkill bshootSkill) {
		Json j = new Json();		
		bshootSkillService.add(bshootSkill);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到BshootSkill查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		BshootSkill bshootSkill = bshootSkillService.get(id);
		request.setAttribute("bshootSkill", bshootSkill);
		return "/bshootskill/bshootSkillView";
	}

	/**
	 * 跳转到BshootSkill修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		BshootSkill bshootSkill = bshootSkillService.get(id);
		request.setAttribute("bshootSkill", bshootSkill);
		request.setAttribute("btlist", basedataService.getBaseDatas("ST"));
		return "/bshootskill/bshootSkillEdit";
	}

	/**
	 * 修改BshootSkill
	 * 
	 * @param bshootSkill
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(BshootSkill bshootSkill) {
		Json j = new Json();		
		bshootSkillService.edit(bshootSkill);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除BshootSkill
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		bshootSkillService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

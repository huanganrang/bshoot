package jb.controller;

import com.alibaba.fastjson.JSON;
import jb.pageModel.*;
import jb.service.BshootPraiseServiceI;
import jb.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * BshootPraise管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/bshootPraiseController")
public class BshootPraiseController extends BaseController {

	@Autowired
	private BshootPraiseServiceI bshootPraiseService;


	/**
	 * 跳转到BshootPraise管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/bshootpraise/bshootPraise";
	}

	/**
	 * 获取BshootPraise数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(BshootPraise bshootPraise, PageHelper ph) {
		return bshootPraiseService.dataGrid(bshootPraise, ph);
	}
	/**
	 * 获取BshootPraise数据表格excel
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
	public void download(BshootPraise bshootPraise, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(bshootPraise,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加BshootPraise页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		BshootPraise bshootPraise = new BshootPraise();
		bshootPraise.setId(UUID.randomUUID().toString());
		return "/bshootpraise/bshootPraiseAdd";
	}

	/**
	 * 添加BshootPraise
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(BshootPraise bshootPraise,HttpSession session) {
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		bshootPraiseService.add(bshootPraise,sessionInfo.getName());
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到BshootPraise查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		BshootPraise bshootPraise = bshootPraiseService.get(id);
		request.setAttribute("bshootPraise", bshootPraise);
		return "/bshootpraise/bshootPraiseView";
	}

	/**
	 * 跳转到BshootPraise修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		BshootPraise bshootPraise = bshootPraiseService.get(id);
		request.setAttribute("bshootPraise", bshootPraise);
		return "/bshootpraise/bshootPraiseEdit";
	}

	/**
	 * 修改BshootPraise
	 * 
	 * @param bshootPraise
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(BshootPraise bshootPraise) {
		Json j = new Json();		
		bshootPraiseService.edit(bshootPraise);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除BshootPraise
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		bshootPraiseService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

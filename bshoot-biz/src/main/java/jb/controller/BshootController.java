package jb.controller;

import com.alibaba.fastjson.JSON;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import jb.pageModel.*;
import jb.service.BshootServiceI;
import jb.service.BshootSquareServiceI;
import jb.util.ConfigUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * Bshoot管理控制器
 * 
 * @author John
 * 
 */
@Api(value = "bshootController-api",description = "动态相关接口", position =10)
@Controller
@RequestMapping("/bshootController")
public class BshootController extends BaseController {

	@Autowired
	private BshootServiceI bshootService;

	@Autowired
	private BshootSquareServiceI bshootSquareService;

	/**
	 * 跳转到Bshoot管理页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/bshoot/bshoot";
	}

	/**
	 * 获取Bshoot数据表格
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(Bshoot bshoot, PageHelper ph) {
		return bshootService.dataGrid(bshoot, ph);
	}
	/**
	 * 获取Bshoot数据表格excel
	 * 
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
	public void download(Bshoot bshoot, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(bshoot,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加Bshoot页面
	 * 
	 * @param request
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		Bshoot bshoot = new Bshoot();
		bshoot.setId(UUID.randomUUID().toString());
		return "/bshoot/bshootAdd";
	}

	/**
	 * 添加Bshoot
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Bshoot bshoot) {
		Json j = new Json();		
		bshootService.add(bshoot);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 添加Bshoot
	 * 
	 * @return
	 */
	//@RequestMapping("/add")
	@ApiIgnore
	@RequestMapping("/uploadBshoot")
	@ResponseBody
	public Json uploadBshoot(Bshoot bshoot,@RequestParam MultipartFile[] movies,HttpSession session) {
		Json j = new Json();		
		SessionInfo sessionInfo = (SessionInfo)session.getAttribute(ConfigUtil.getSessionInfoName());
		String realPath = session.getServletContext().getRealPath("/uploadfiles/"+sessionInfo.getName());  
		File file = new File(realPath);
		bshoot.setId(UUID.randomUUID().toString());
		bshoot.setUserId(sessionInfo.getId());
		if(!file.exists())
			file.mkdir();
		
		for(MultipartFile f : movies){
			String suffix = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf("."));
			String fileName = bshoot.getId()+suffix;
			bshoot.setBsStream(sessionInfo.getName()+"/"+fileName);
			 try {
				FileUtils.copyInputStreamToFile(f.getInputStream(), new File(realPath, fileName));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		bshootService.add(bshoot);	
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	
	/**
	 * 跳转到Bshoot查看页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		Bshoot bshoot = bshootService.get(id);
		request.setAttribute("bshoot", bshoot);
		return "/bshoot/bshootView";
	}

	/**
	 * 跳转到Bshoot修改页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@SuppressWarnings("unchecked")
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		Bshoot bshoot = bshootService.get(id);
		PageHelper ph = new PageHelper();
		ph.setRows(1000);
		ph.setPage(0);
		List<BshootSquare> bshootSquares = bshootSquareService.dataGrid(null, ph).getRows();
		request.setAttribute("bshoot", bshoot);
		request.setAttribute("bshootSquare", bshootSquares);
		return "/bshoot/bshootEdit";
	}

	/**
	 * 修改Bshoot
	 * 
	 * @param bshoot
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Bshoot bshoot) {
		Json j = new Json();		
		bshootService.edit(bshoot);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除Bshoot
	 * 
	 * @param id
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		bshootService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 我的作品
	 * @param fileType
	 * @param start
	 * @param tokenId
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "我的作品", notes = "我的作品", position = 2,httpMethod = "POST",response = Json.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/myBshoot")
	@ResponseBody
	public Json myBshoot(@ApiParam(value = "只看（-1全部/1图文/2视频/3音乐）",required = true) @RequestParam Integer fileType,@ApiParam(value = "页数(0开始)",required = true) @RequestParam Integer start,@ApiParam(value = "tokenId",required = true) @RequestParam String tokenId,HttpServletRequest request ){
		SessionInfo sessionInfo = getSessionInfo(request);
		List<Bshoot> bshoots = bshootService.getSomeoneBshoot( sessionInfo.getId(), fileType,start,15);
		Json json = new Json();
		json.setSuccess(true);
		json.setObj(bshoots);
		return json;
	}

	/**
	 * 别人的作品
	 * @param userId
	 * @param fileType
	 * @param start
	 * @return
	 */
	@ApiOperation(value = "保存用户兴趣", notes = "保存用户兴趣", position = 2,httpMethod = "POST",response = Json.class,produces = "application/json; charset=utf-8")
	@RequestMapping("/otherBshoot")
	@ResponseBody
	public Json otherBshoot(@ApiParam(value = "用户id",required = true) @RequestParam String userId,@ApiParam(value = "只看（-1全部/1图文/2视频/3音乐）",required = true) @RequestParam Integer fileType,@ApiParam(value = "页数(0开始)",required = true) @RequestParam Integer start){
		List<Bshoot> bshoots = bshootService.getSomeoneBshoot( userId, fileType,start,15);
		Json json = new Json();
		json.setSuccess(true);
		json.setObj(bshoots);
		return json;
	}
}

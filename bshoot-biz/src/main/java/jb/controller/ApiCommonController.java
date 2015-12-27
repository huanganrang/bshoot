package jb.controller;

import jb.pageModel.BshootRegion;
import jb.pageModel.Bug;
import jb.pageModel.Json;
import jb.service.BshootRegionServiceI;
import jb.service.BshootSkillServiceI;
import jb.service.BugServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * 公共模块接口
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiCommon")
public class ApiCommonController extends BaseController {
	
	@Autowired
	private BshootRegionServiceI bshootRegionService;
	
	@Autowired
	private BugServiceI bugService;
	
	@Autowired
	private BshootSkillServiceI bshootSkillService;
	
	/**
	 * 获取行政区域列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/region")
	@ResponseBody
	public Json region(BshootRegion bshootRegion) {
		Json j = new Json();
		try{
			j.setObj(bshootRegionService.findAllByParams(bshootRegion));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}	
	
	/**
	 * 
	 * @param lvAccount
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/error")
	public Json error() {
		Json j = new Json();
		j.setObj("token_expire");
		j.setSuccess(false);
		j.setMsg("token过期，请重新登录！");
		return j;
	}
	
	/**
	 * app错误日志上传
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload_errorlog")
	public Json uploadErrorlog(Bug bug, @RequestParam MultipartFile logFile, HttpServletRequest request) {
		Json j = new Json();
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar calendar = Calendar.getInstance();  
			String dirName = "errorlog/" + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
			bug.setFilePath(uploadFile(request, dirName, logFile, format.format(calendar.getTime())));
			bug.setTypeId("0"); // 错误
			bug.setId(UUID.randomUUID().toString());
			bugService.add(bug);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}
	
	/**
	 * 生成html
	 * @return
	 */
	@RequestMapping("/html")
	public void html(String id, HttpServletResponse response) {
		PrintWriter out = null;
		String content = "";
		try{
			response.setContentType("text/html");  
			response.setCharacterEncoding("UTF-8");
			content = bshootSkillService.get(id).getDescription();
			out = response.getWriter();
			out.write("<html><head>");
			out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\">");
			out.write("<style type=\"text/css\">");
			out.write("body {font-family:\"微软雅黑\";font-size:12px; background-color:#f8f7f5;}");
			out.write("ul,ol,li{padding:0; margin:0;}");
			out.write("img{border:0; line-height:0; width: 100%;}");
			out.write("ol,ul {list-style:none;}");
			out.write("a { color: #000; text-decoration: none; outline: none;}");
			out.write("a img { border: none; }");
			out.write("</style></head><body>");
			out.write(content);
			out.write("</body></html>");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}	
	}	
}

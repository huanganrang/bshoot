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
@Api(value = "apiSwgBshootController-api",description = "动态相关接口", position =10)
@Controller
@RequestMapping("/apiSwgBshootController")
public class ApiSwgBshootController extends BaseController {

	@Autowired
	private BshootServiceI bshootService;

	@Autowired
	private BshootSquareServiceI bshootSquareService;


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
		bshootService.convertNumber(bshoots);
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
	@ApiOperation(value = "别人的作品", notes = "别人的作品", position = 2,httpMethod = "POST",response = Json.class,produces = "application/json; charset=utf-8")
	@RequestMapping("/otherBshoot")
	@ResponseBody
	public Json otherBshoot(@ApiParam(value = "用户id",required = true) @RequestParam String userId,@ApiParam(value = "只看（-1全部/1图文/2视频/3音乐）",required = true) @RequestParam Integer fileType,@ApiParam(value = "页数(0开始)",required = true) @RequestParam Integer start){
		List<Bshoot> bshoots = bshootService.getSomeoneBshoot( userId, fileType,start,15);
		bshootService.convertNumber(bshoots);
		Json json = new Json();
		json.setSuccess(true);
		json.setObj(bshoots);
		return json;
	}
}

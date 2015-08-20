package jb.controller;

import jb.pageModel.BshootRegion;
import jb.pageModel.Json;
import jb.service.BshootRegionServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

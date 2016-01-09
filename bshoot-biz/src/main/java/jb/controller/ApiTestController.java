package jb.controller;

import jb.pageModel.ApiTest;
import jb.pageModel.Json;
import jb.service.ApiTestServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ApiTest管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiTestController")
public class ApiTestController extends BaseController {

	@Autowired
	private ApiTestServiceI apiTestService;

	/**
	 * 获取接口列表
	 *  @return
	 * @param name
	 */
	@RequestMapping("/apilist")
	@ResponseBody
	public Json getApiList(String name) {
		Json j = new Json();
		j.success();
		j.setObj(apiTestService.getApiList(name));
		return j;
	}

	/**
	 * 获取接口信息
	 *
	 * @param apiTest
	 * @return
	 */
	@RequestMapping("/apitest")
	@ResponseBody
	public ApiTest apiTest(ApiTest apiTest) {
		return apiTestService.get(apiTest.getId());
	}

	/**
	 * 添加ApiTest
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(ApiTest apiTest) {
		Json j = new Json();		
		apiTestService.add(apiTest);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 修改ApiTest
	 * 
	 * @param apiTest
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(ApiTest apiTest) {
		Json j = new Json();		
		apiTestService.edit(apiTest);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除ApiTest
	 * 
	 * @param apiTest
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(ApiTest apiTest) {
		Json j = new Json();
		apiTestService.delete(apiTest.getId());
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}

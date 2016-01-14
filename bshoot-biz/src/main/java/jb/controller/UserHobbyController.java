package jb.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import jb.pageModel.*;
import jb.service.BasedataServiceI;
import jb.service.UserHobbyServiceI;

import jb.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * UserHobby管理控制器
 * 
 * @author John
 * 
 */
@Api(value = "userHobbyController-api",description = "兴趣接口", position =4)
@Controller
@RequestMapping("/userHobbyController")
public class UserHobbyController extends BaseController {

	@Autowired
	private UserHobbyServiceI userHobbyService;
    @Autowired
	private BasedataServiceI basedataService;
    private final String HOBBYTYPE="HO";
	/**
	 * 跳转到UserHobby管理页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/userhobby/userHobby";
	}

	/**
	 * 获取UserHobby数据表格
	 * 
	 * @param user
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(UserHobby userHobby, PageHelper ph) {
		return userHobbyService.dataGrid(userHobby, ph);
	}
	/**
	 * 获取UserHobby数据表格excel
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
	@ApiIgnore
	@RequestMapping("/download")
	public void download(UserHobby userHobby, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(userHobby,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加UserHobby页面
	 * 
	 * @param request
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		UserHobby userHobby = new UserHobby();
		userHobby.setId(UUID.randomUUID().toString());
		return "/userhobby/userHobbyAdd";
	}

	/**
	 * 添加UserHobby
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/add")
	@ResponseBody
	public Json add(UserHobby userHobby) {
		Json j = new Json();		
		userHobbyService.add(userHobby);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到UserHobby查看页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		UserHobby userHobby = userHobbyService.get(id);
		request.setAttribute("userHobby", userHobby);
		return "/userhobby/userHobbyView";
	}

	/**
	 * 跳转到UserHobby修改页面
	 * 
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		UserHobby userHobby = userHobbyService.get(id);
		request.setAttribute("userHobby", userHobby);
		return "/userhobby/userHobbyEdit";
	}

	/**
	 * 修改UserHobby
	 * 
	 * @param userHobby
	 * @return
	 */
	@ApiIgnore
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(UserHobby userHobby) {
		Json j = new Json();		
		userHobbyService.edit(userHobby);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	@ApiOperation(value = "保存用户兴趣", notes = "保存用户兴趣", position = 2,httpMethod = "POST",response = Json.class,produces = "application/json; charset=utf-8")
	@RequestMapping("/saveUserHobby")
	@ResponseBody
	public Json saveUserHobby(@ApiParam(value = "兴趣列表(逗号分割，如:HO101,HO201,HO301)",required = true) @RequestParam String hobbies,@ApiIgnore HttpSession session){
		Json j = new Json();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		UserHobby userHobby = new UserHobby();
		userHobby.setUserId(sessionInfo.getId());
		userHobby.setHobbyType(hobbies);
		userHobbyService.saveOrUpdateUserHobby(userHobby);
		j.setMsg("保存成功");
		j.setSuccess(true);
		return j;
	}

	@ApiOperation(value = "获取兴趣列表", notes = "获取兴趣列表", position =3,httpMethod = "POST",response = BaseData.class,produces = "application/json; charset=utf-8")
	@RequestMapping("/getHobbyList")
	@ResponseBody
	public  Collection<BaseData> getHobbyList(){
        List<BaseData> hobbies = basedataService.getBaseDatas(HOBBYTYPE);
		//对兴趣按分类组织
		Map<String,BaseData> category = new HashMap<>();
		BaseData parent = null;
		for(BaseData baseData:hobbies){
			if(StringUtils.isEmpty(baseData.getPid())){
				parent = category.get(baseData.getId());
				if(null!=parent) {
                    baseData.setChildren(parent.getChildren());
				}
				category.put(baseData.getId(),baseData);
			}else{
				parent = category.get(baseData.getPid());
				if(null!=parent) {
					parent.getChildren().add(baseData);
				}else{
					parent = new BaseData();
					parent.setId(baseData.getPid());
					parent.getChildren().add(baseData);
					category.put(baseData.getPid(),parent);
				}
			}
		}
		return  category.values();
	}
}

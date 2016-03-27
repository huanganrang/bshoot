package jb.controller;

import com.alibaba.fastjson.JSON;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import jb.pageModel.*;
import jb.service.BasedataServiceI;
import jb.service.UserHobbyServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * UserHobby管理控制器
 * 
 * @author John
 * 
 */
@Api(value = "apiSwgUserHobbyController-api",description = "兴趣接口", position =4)
@Controller
@RequestMapping("/apiSwgUserHobbyController")
public class ApiUserHobbyController extends BaseController {

	@Autowired
	private UserHobbyServiceI userHobbyService;
    @Autowired
	private BasedataServiceI basedataService;
    private final String HOBBYTYPE="HO";

	@ApiOperation(value = "保存用户兴趣", notes = "保存用户兴趣", position = 2,httpMethod = "POST",response = Json.class,produces = "application/json; charset=utf-8")
	@RequestMapping("/saveUserHobby")
	@ResponseBody
	public Json saveUserHobby(@ApiParam(value = "兴趣列表(逗号分割，如:HO101,HO201,HO301)",required = true) @RequestParam String hobbies,@ApiParam(value="tokenId",required = true) @RequestParam String tokenId,@ApiIgnore HttpServletRequest request){
		Json j = new Json();
		SessionInfo sessionInfo = getSessionInfo(request);
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
		Map<String,BaseData> category = new HashMap<String,BaseData>();
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

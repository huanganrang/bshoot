package jb.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import jb.pageModel.Bshoot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * UserFindController
 * 用户查找页接口
 * @author yibing zhou
 * @date 2016/3/21
 */
@Api(value = "userFindController-api",description = "用户查找页接口", position =1)
@Controller
@RequestMapping("/userFindController")
public class UserFindController {

    //搜索用户
    @ApiOperation(value = "用户搜索", notes = "用户搜索", position = 1,httpMethod = "POST", response = Bshoot.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/searchUser")
    @ResponseBody
    public List<Bshoot> searchUser(@ApiParam(value = "搜索词(支持用户昵称模糊搜索/用户手机号精确搜索)",required = true) @RequestParam String keyWord){
        return null;
    }

    //可能认识的人
    @ApiOperation(value = "可能认识的人", notes = "可能认识的人", position = 2,httpMethod = "POST", response = Bshoot.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/maybeKnow")
    @ResponseBody
    public List<Bshoot> maybeKnow(@ApiParam(value="tokenId",required = true) @RequestParam String tokenId,@ApiIgnore HttpServletRequest request){
        return null;
    }

    //可能感兴趣的人
    @ApiOperation(value = "可能感兴趣的人", notes = "可能感兴趣的人", position = 3,httpMethod = "POST", response = Bshoot.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/maybeInterest")
    @ResponseBody
    public List<Bshoot> maybeInterest(@ApiParam(value="tokenId",required = true) @RequestParam String tokenId,@ApiIgnore HttpServletRequest request){
        return null;
    }

}

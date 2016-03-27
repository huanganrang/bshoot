package jb.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import jb.bizmodel.RecommendUser;
import jb.pageModel.Bshoot;
import jb.pageModel.SessionInfo;
import jb.service.impl.RecommendService;
import jb.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *
 * 推荐相关功能
 * Created by zhou on 2016/1/1.
 */
@Api(value = "apiSwgRecommend-api",description = "首页推荐接口", position =1)
@Controller
@RequestMapping("/apiSwgRecommend")
public class ApiRecommendController extends  BaseController{

    @Autowired
    private RecommendService recommendService;

    //热门推荐
    @ApiOperation(value = "首页热门", notes = "首页热门", position = 1,httpMethod = "POST", response = Bshoot.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/hot")
    @ResponseBody
    public List<Bshoot> recommentHost(@ApiParam(value = "页数",required = true, defaultValue = "0")  @RequestParam Integer start,
                                      @ApiParam(value = "只看（-1全部/1图文/2视频/3音乐）",required = false)  @RequestParam Integer fileType,
                                      @ApiParam(value = "看同兴趣（0全部/1是）",required = false)  @RequestParam Integer interested,
                                      @ApiParam(value="tokenId",required = true) @RequestParam String tokenId,@ApiIgnore HttpServletRequest request) {
        SessionInfo sessionInfo = getSessionInfo(request);
        return  recommendService.recommendHot(sessionInfo.getId(),start,fileType,interested);
    }


    //新人推荐
    @ApiOperation(value = "新人推荐", notes = "新人推荐", position = 2,httpMethod = "POST", response = RecommendUser.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/recommendUser")
    @ResponseBody
    public List<RecommendUser> recommendUser(@ApiParam(value="tokenId",required = true) @RequestParam String tokenId,@ApiIgnore HttpServletRequest request ){
        SessionInfo sessionInfo = getSessionInfo(request);
        return  recommendService.recommendUser(sessionInfo.getId());
    }

    //首页推荐
    @ApiOperation(value = "首页推荐", notes = "首页推荐", position = 3,httpMethod = "POST", response = Bshoot.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/recommend")
    @ResponseBody
    public List<Bshoot> recommend(@ApiParam(value = "页数",required = true, defaultValue = "0")   @RequestParam Integer start,@ApiParam(value="tokenId",required = true) @RequestParam String tokenId,@ApiIgnore HttpServletRequest request) {
        SessionInfo sessionInfo = getSessionInfo(request);
        return  recommendService.recommend(sessionInfo.getId(),start,6);
    }
}

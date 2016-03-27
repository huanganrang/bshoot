package jb.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import jb.pageModel.Bshoot;
import jb.pageModel.SessionInfo;
import jb.service.HotGuideServiceI;
import jb.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 首页热门引导
 * Created by Zhou Yibing on 2015/10/22.
 */
@Api(value = "apiSwgHotGuide-api", description = "热门引导页接口", position =2)
@Controller
@RequestMapping("/apiSwgHotGuide")
public class ApiHotGuideController extends BaseController{

    @Autowired
    private HotGuideServiceI hotGuideServiceImpl;

    //引导外页
    @ApiOperation(value = "热门引导外页", notes = "引导外页", position = 1,httpMethod = "POST", response = Bshoot.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/external")
    @ResponseBody
    public List<Bshoot> guideExternalPage(@ApiParam(value = "页数",required = true, defaultValue = "0")  @RequestParam Integer start,@ApiParam(value="tokenId",required = true) @RequestParam String tokenId,@ApiIgnore HttpServletRequest request) {
        SessionInfo sessionInfo = getSessionInfo(request);
        return  hotGuideServiceImpl.guideExternalPage(sessionInfo.getId(), start,1);
    }

    //引导内页
    @ApiOperation(value = "热门引导内页", notes = "引导内页(引导类型取值仅支持 2:好友关注的人 3:我评论/打赏过的人 4:好友打赏过的人 5:可能感兴趣的人 6:可能认识的人 7:附近的人)", position = 2,httpMethod = "POST", response = Bshoot.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/internal")
    @ResponseBody
    public List<Bshoot> guideInternalPage(@ApiParam(value = "引导类型",required = true, defaultValue = "2")  @RequestParam Integer guideType,@ApiParam(value = "页数",required = true, defaultValue = "0")  @RequestParam Integer start,@ApiParam(value="tokenId",required = true) @RequestParam String tokenId,@ApiIgnore HttpServletRequest request) {
        SessionInfo sessionInfo = getSessionInfo(request);
        return  hotGuideServiceImpl.guideInternalPage(sessionInfo.getId(), guideType, start,6);
    }

}

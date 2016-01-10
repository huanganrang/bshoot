package jb.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import jb.bizmodel.RecommendUser;
import jb.pageModel.Bshoot;
import jb.pageModel.SessionInfo;
import jb.service.ContractGuideServiceI;
import jb.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 通讯录引导
 * Created by Zhou Yibing on 2016/1/8.
 */
@Api(value = "contractGuide-api", description = "通讯录引导页接口", position =3)
@Controller
@RequestMapping("/contractGuide")
public class ContractGuideController extends BaseController{

    @Autowired
    private ContractGuideServiceI contractGuideService;

    //引导外页
    @ApiOperation(value = "通讯录引导外页", notes = "引导外页(引导类型取值仅支持 2:好友关注的人 5:可能感兴趣的人 7:附近的人)", position = 1,httpMethod = "POST", response = RecommendUser.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/external")
    @ResponseBody
    public Map<Integer,List<RecommendUser>> guideExternalPage( @ApiParam(value = "页数",required = true, defaultValue = "0") @RequestParam Integer start,@ApiIgnore HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        return  contractGuideService.guideExternalPage(sessionInfo.getId(), start,1);
    }

    //引导内页
    @ApiOperation(value = "通讯录引导内页", notes = "引导内页(引导类型取值仅支持 2:好友关注的人 5:可能感兴趣的人 7:附近的人)", position = 2,httpMethod = "POST", response = RecommendUser.class,produces = "application/json; charset=utf-8")
    @RequestMapping("/internal")
    @ResponseBody
    public List<RecommendUser> guideInternalPage(@ApiParam(value = "引导类型(引导类型取值仅支持 2:好友关注的人 5:可能感兴趣的人 7:附近的人)",required = true, defaultValue = "2") @RequestParam Integer guideType,
                                                 @ApiParam(value = "页数",required = true, defaultValue = "0") @RequestParam Integer start,@ApiIgnore HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        return  contractGuideService.guideInternalPage(sessionInfo.getId(), guideType, start,6);
    }

}

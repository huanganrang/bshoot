package jb.controller;

import jb.bizmodel.RecommendUser;
import jb.pageModel.Bshoot;
import jb.pageModel.SessionInfo;
import jb.service.ContractGuideServiceI;
import jb.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 通讯录引导
 * Created by Zhou Yibing on 2016/1/8.
 */
@Controller
@RequestMapping("/contractGuide")
public class ContractGuideController {

    @Autowired
    private ContractGuideServiceI contractGuideServiceImpl;

    //引导外页
    @RequestMapping("/external")
    @ResponseBody
    public Map<Integer,List<RecommendUser>> guideExternalPage(Integer start,HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        return  contractGuideServiceImpl.guideExternalPage(sessionInfo.getId(), start,1);
    }

    //引导内页
    @RequestMapping("/internal")
    @ResponseBody
    public List<RecommendUser> guideInternalPage(Integer guideType,Integer start,HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        return  contractGuideServiceImpl.guideInternalPage(sessionInfo.getId(), guideType, start,6);
    }

}

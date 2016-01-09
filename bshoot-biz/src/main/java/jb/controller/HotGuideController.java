package jb.controller;

import jb.pageModel.Bshoot;
import jb.pageModel.SessionInfo;
import jb.service.HotGuideServiceI;
import jb.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 首页热门引导
 * Created by Zhou Yibing on 2015/10/22.
 */

@Controller
@RequestMapping("/hotGuide")
public class HotGuideController {

    @Autowired
    private HotGuideServiceI hotGuideServiceImpl;


    //引导外页
    @RequestMapping("/external")
    @ResponseBody
    public List<Bshoot> guideExternalPage(Integer start,HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        return  hotGuideServiceImpl.guideExternalPage(sessionInfo.getId(), start,1);
    }

    //引导内页
    @RequestMapping("/internal")
    @ResponseBody
    public List<Bshoot> guideInternalPage(Integer guideType,Integer start,HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
        return  hotGuideServiceImpl.guideInternalPage(sessionInfo.getId(), guideType, start,6);
    }

}
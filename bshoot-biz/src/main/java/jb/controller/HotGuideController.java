package jb.controller;

import jb.pageModel.Bshoot;
import jb.service.HotGuideServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public List<Bshoot> guideExternalPage(String userId,Integer start) {
        //获得登录用户id TODO
        return  hotGuideServiceImpl.guideExternalPage(userId, start,1);
    }

    //引导内页
    @RequestMapping("/internal")
    @ResponseBody
    public List<Bshoot> guideInternalPage(String userId,Integer guideType,Integer start) {
        //获得登录用户id TODO
        return  hotGuideServiceImpl.guideInternalPage(userId, guideType, start,6);
    }

}

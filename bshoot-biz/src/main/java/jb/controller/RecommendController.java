package jb.controller;

import jb.bizmodel.RecommendUser;
import jb.pageModel.Bshoot;
import jb.service.impl.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *
 * 推荐相关功能
 * Created by zhou on 2016/1/1.
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController extends  BaseController{

    @Autowired
    private RecommendService recommendService;

    //热门推荐
    @RequestMapping("/hot")
    @ResponseBody
    public List<Bshoot> recommentHost(Integer start,Integer fileType,Integer interested) {
        //获得登录用户id TODO
        return  recommendService.recommendHot("1",start,fileType,interested);
    }


    //新人推荐
    @RequestMapping("/recommendUser")
    @ResponseBody
    public List<RecommendUser> recommendUser() {
        //TODO
        return  recommendService.recommendUser("1");
    }

    //首页推荐
    @RequestMapping("/recommend")
    @ResponseBody
    public List<Bshoot> recommend(Integer start) {
        //TODO
        return  recommendService.recommend("1",start);
    }
}

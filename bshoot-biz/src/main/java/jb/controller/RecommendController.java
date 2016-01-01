package jb.controller;

import jb.service.impl.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}

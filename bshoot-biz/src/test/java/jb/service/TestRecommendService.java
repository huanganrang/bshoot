package jb.service;

import jb.bizmodel.RecommendUser;
import jb.pageModel.Bshoot;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

import java.util.List;

/**
 * Created by Zhou Yibing on 2016/1/5.
 */
public class TestRecommendService extends TestConfig{

    @Autowired
    private RecommendServiceI recommendServiceImpl;

    @Test
    public void testRecommendUser(){
       List<RecommendUser> recommendUserList = recommendServiceImpl.recommendUser("123");
        for(RecommendUser recommendUser:recommendUserList){
            System.out.println(recommendUser);
        }
    }

    @Test
    public void testRecommendHot(){
        List<Bshoot> bshoots = recommendServiceImpl.recommendHot("e2891f2f-6a6f-41d5-933d-28fa796eee93",0,null,1);
        for(Bshoot bshoot:bshoots){
            System.out.println(bshoot);
        }
    }

    @Test
    public void testRecommend(){
       List<Bshoot> bshoots =  recommendServiceImpl.recommend("2", 0);
        for(Bshoot bshoot:bshoots){
            System.out.println(bshoot);
        }
    }

    @Test
    public void testMaybeInterest(){
        /*List<Bshoot> bshoots =  recommendServiceImpl.mabyeInterest("e2891f2f-6a6f-41d5-933d-28fa796eee93","AR02",0, DateUtil.stringToDate(DateUtil.getDateStart(-6)));
        for(Bshoot bshoot:bshoots){
            System.out.println(bshoot);
        }*/
    }
}

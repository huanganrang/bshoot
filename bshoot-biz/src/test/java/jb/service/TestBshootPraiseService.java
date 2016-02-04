package jb.service;

import jb.util.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

import java.util.Date;
import java.util.Map;

/**
 * Created by Zhou Yibing on 2016/2/2.
 */
public class TestBshootPraiseService extends TestConfig {

    @Autowired
    private BshootPraiseServiceI bshootPraiseService;

    @Test
    public void testCountGroup(){
        Date now = new Date();
        Date begin = DateUtil.stringToDate(DateUtil.getDate(-90,DateUtil.DATETIME_FORMAT),DateUtil.DATETIME_FORMAT);
        Map<String,Integer> result = bshootPraiseService.countGroup(begin,now);
        System.out.println(result);
    }
}

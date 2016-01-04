package jb.service;

import com.google.common.collect.Lists;
import jb.pageModel.Bshoot;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

import java.util.List;

/**
 * Created by zhou on 2016/1/3.
 */
public class TestBshootService extends TestConfig{

    @Autowired
    private BshootServiceI bshootServiceImpl;

    @Test
    public void testGetUserLastBshoot(){
        List<Bshoot> bshootList = bshootServiceImpl.getUserLastBshoot(Lists.newArrayList(new String[]{"0cfa0c41-2e72-4663-85d2-59225db6c6e2","1b79d1f6-0edf-474b-b525-08e4e8528ce7","0cfa0c41-2e72-4663-85d2-59225db6c6e2"}));
        for(Bshoot bshoot:bshootList){
            System.out.println(bshoot);
        }
    }

    @Test
    public void testGetUserLastBshoot2(){
        Bshoot bshoot = bshootServiceImpl.getUserLastBshoot("0cfa0c41-2e72-4663-85d2-59225db6c6e2");
        System.out.print(bshoot);
    }
}

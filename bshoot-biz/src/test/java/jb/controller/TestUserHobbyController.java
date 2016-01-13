package jb.controller;

import jb.pageModel.BaseData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

import java.util.Collection;

/**
 * Created by zhou on 2016/1/13.
 */
public class TestUserHobbyController extends TestConfig{
    @Autowired
    private UserHobbyController userHobbyController;

    @Test
    public void testGetHobbyList(){
        Collection<BaseData> hobbies = userHobbyController.getHobbyList();
        for(BaseData baseData:hobbies){
            System.out.print(baseData);
        }
    }
}

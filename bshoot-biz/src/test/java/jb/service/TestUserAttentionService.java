package jb.service;

import jb.pageModel.UserAttention;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

import java.util.List;

/**
 * Created by zhou on 2016/1/3.
 */
public class TestUserAttentionService extends TestConfig {
    @Autowired
    private UserAttentionServiceI userAttentionServiceImpl;

    @Test
    public void testFriendCommonAtt(){
       List<UserAttention> userAttentionList =  userAttentionServiceImpl.friendCommonAtt("1", 0, 6);
        for(UserAttention userAttention:userAttentionList){
            System.out.println(userAttention);
        }
    }
}

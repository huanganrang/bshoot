package jb.service;

import jb.pageModel.UserProfile;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

/**
 * Created by Zhou Yibing on 2016/2/4.
 */
public class TestUserProfileService extends TestConfig{

    @Autowired
    private UserProfileServiceI userProfileService;

    @Test
    public void testEdit(){
        UserProfile userProfile = new UserProfile();
        userProfile.setId("0");
        userProfile.setAttNum(1212);
        userProfileService.edit(userProfile);
    }
}

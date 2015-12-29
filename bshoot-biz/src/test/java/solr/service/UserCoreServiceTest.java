package solr.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;
import solr.cores.UserCoreServiceImpl;
import solr.model.UserEntity;

/**
 * Created by zhou on 2015/12/29.
 */
public class UserCoreServiceTest extends TestConfig{

    @Autowired
    private UserCoreServiceImpl userCoreService;

    @Test
    public void testAddUser(){
        UserEntity userEntity  = new UserEntity();
        userEntity.setId(8998);
        userEntity.setNickname("234");
        userEntity.setBardian("23432");
        userEntity.setHobby(Lists.newArrayList(new String[]{"234","432","23432"}));
        userCoreService.addUser(userEntity);
    }
}

package solr.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;
import solr.cores.UserCoreServiceImpl;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;
import solr.model.query.FakeSolrParam;

/**
 * Created by zhou on 2015/12/29.
 */
public class UserCoreServiceTest extends TestConfig{

    @Autowired
    private UserCoreServiceImpl userCoreService;

    @Test
    public void testAddUser(){
        UserEntity userEntity  = new UserEntity();
        userEntity.setId(2);
        userEntity.setNickname("zhangsan");
        userEntity.setBardian("23432");
        userEntity.setFansNum(120);
        userEntity.setHobby(Lists.newArrayList(new String[]{"234","432","23432"}));
        userCoreService.addEntity(userEntity);
    }

    @Test
    public void testDeleteById(){
        userCoreService.deleteById("2");
        userCoreService.deleteById(new String[]{"3","4","5"});
    }

    @Test
    public void testDeleteByQuery(){
        userCoreService.deleteByQuery("nickname:zhangsan AND fansNum:[100 TO *] AND bardian:23*");
    }

}

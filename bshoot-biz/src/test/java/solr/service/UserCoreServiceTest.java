package solr.service;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;
import solr.cores.CoreService;
import solr.cores.UserCoreServiceImpl;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;
import solr.model.query.FakeSolrParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhou on 2015/12/29.
 */
public class UserCoreServiceTest extends TestConfig{

    @Autowired
    private CoreService userCoreServiceImpl;

    @Test
    public void testAddUser() throws IOException, SolrServerException {
        List<UserEntity> userEntities  = Lists.newArrayList();
        for(int i=1;i<1000;i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(i);
            userEntity.setNickname("zhangsan"+i);
            userEntity.setBardian("23432");
            userEntity.setFansNum(120);
            userEntity.setHobby(Lists.newArrayList(new String[]{"234", "432", "23432"}));
            userEntities.add(userEntity);
        }
        userCoreServiceImpl.addEntities(userEntities);

    }

    @Test
    public void testDeleteById(){
        userCoreServiceImpl.deleteById("2");
        userCoreServiceImpl.deleteById(new String[]{"3","4","5"});
    }

    @Test
    public void testDeleteByQuery(){
        userCoreServiceImpl.deleteByQuery("nickname:zhangsan AND fansNum:[100 TO *] AND bardian:23*");
    }

}

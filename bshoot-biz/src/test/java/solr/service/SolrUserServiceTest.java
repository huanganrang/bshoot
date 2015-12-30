package solr.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;
import solr.cores.CoreService;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;

import java.util.List;

/**
 * Created by Zhou Yibing on 2015/12/30.
 */
public class SolrUserServiceTest extends TestConfig{

    @Autowired
    private SolrUserService solrUserService;

    @Test
    public void queryTest(){
        Criterias criterias = new Criterias();
        criterias.qc("id:12");
        SolrResponse<UserEntity> solrResponse = solrUserService.query(criterias);
        List<UserEntity> userEntityList = solrResponse.getDocs();
        for (UserEntity userEntity:userEntityList)
            System.out.println(userEntity);
    }
}

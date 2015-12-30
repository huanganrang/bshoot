package solr.service;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;
import solr.cores.AbstractCoreService;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;
import solr.model.query.FakeSolrParam;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public class AbstractCoreServiceTest extends TestConfig {

    @Autowired
    private AbstractCoreService abstractCoreService;

    @Test
    public void testAddDoc(){
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id","112");
        solrInputDocument.addField("nickname","dfsdf");
        //abstractCoreService.addDoc(solrInputDocument);
    }

    @Test
    public void testQuery(){
        Criterias criterias = new Criterias();
        criterias.qc("ida:2");
      /*  criterias.between("rentPrice","3000","6000");
        //criterias.or("town",new String[]{"张江","徐泾"});
        criterias.addOrder("rentPrice",FakeSolrParam.SORT_ASC);
        criterias.addLocation("location","31.204892,121.63328",3,FakeSolrParam.SORT_ASC);
        criterias.setFormat(FakeSolrParam.FORMAT_JSON);
        criterias.addField(new String[]{"id","numFound","provinceId","city","estate","suggestion"});*/
        criterias.addField(new String[]{"id","hobby","nickname"});

        abstractCoreService.query(criterias.getFakeSolrParam());
    }
}

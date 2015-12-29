package solr.model.query;

import org.junit.Test;
import solr.model.criteria.Criterias;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public class FakeSolrParamTest {

    @Test
    public void  testRestriction(){
        FakeSolrParam fakeSolrParam = new FakeSolrParam();
        Criterias criterias = fakeSolrParam.createCriterias();
        criterias.qc("provinceId:2");
        criterias.between("rentPrice","3000","6000");
        criterias.or("town",new String[]{"ÕÅ½­","Ðìãþ"});
        criterias.addOrder("rentPrice",FakeSolrParam.SORT_ASC);
        criterias.addOrder("town",FakeSolrParam.SORT_ASC);
        criterias.addLocation("location","31.204892,121.63328",3,null);
        criterias.addGroup("townId");
        criterias.setFormat(FakeSolrParam.FORMAT_CSV);
        criterias.addField(new String[]{"provinceId","city","estate","suggestion"});
        SolrParamParser solrParamParser = new DefaultSolrParamParserImpl();
        System.out.println(solrParamParser.parser(fakeSolrParam));
    }
}

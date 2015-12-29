package solr.model.query;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by zhou on 2015/12/28.
 */
public interface SolrParamParser{
    public SolrQuery parser(FakeSolrParam param);
}

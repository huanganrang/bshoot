package solr.model;

import org.apache.solr.client.solrj.SolrQuery;
import solr.query.FakeSolrParam;

/**
 * Created by zhou on 2015/12/28.
 */
public interface SolrParamParser{
    public SolrQuery parser(FakeSolrParam param);
}

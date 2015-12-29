package solr.model.criteria;

import solr.Exception.SearchException;
import solr.model.query.FakeSolrParam;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public interface Expression {
    public String parse(FakeSolrParam fakeSolrParam) throws SearchException;
}

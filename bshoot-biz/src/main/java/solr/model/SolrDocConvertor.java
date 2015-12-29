package solr.model;

import org.apache.solr.common.SolrDocumentList;

import java.util.List;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public interface SolrDocConvertor {
    public List convert(SolrDocumentList solrDocuments);
}

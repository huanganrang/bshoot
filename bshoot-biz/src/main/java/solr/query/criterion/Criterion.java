package solr.query.criterion;

import solr.Exception.SearchException;
import solr.query.FakeSolrParam;

import java.io.Serializable;

/**
 * 查询标准
 */
public interface Criterion extends Serializable {
	/**
	 * 将各种Criterion保存到FakeSolrParam中
	 * 
	 * @return
	 */
	public String toQueryString(FakeSolrParam param) throws SearchException;
}

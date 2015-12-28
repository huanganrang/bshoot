package solr.query.criterion;

import solr.Exception.SearchException;
import solr.query.FakeSolrParam;

public abstract class DefaultCriterionImpl implements Criterion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4974772742012362252L;

	/**
	 * 是否要加上不需要cache的语句
	 * 
	 * @param field
	 * @return
	 */
	protected String noCacheStat(String field) {
		// if (StringUtils.equals(field, "createdTime") ||
		// StringUtils.equals(field, "pubTime")
		// || StringUtils.equals(field, "addTimeLong")) {
		// // return "{!cache=false cost=150}";
		// }
		return "";
	}

	public String toQueryString(FakeSolrParam param) throws SearchException {
		return "";
	}

}

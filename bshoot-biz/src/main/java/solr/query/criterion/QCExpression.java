package solr.query.criterion;

import org.apache.commons.lang.StringUtils;
import solr.Exception.SearchException;
import solr.query.FakeSolrParam;

/**
 * 查询条件表达式
 * 
 */
public class QCExpression extends DefaultCriterionImpl {

	private static final long serialVersionUID = 29791495279756569L;
	private final String value;

	protected QCExpression(String value) {
		this.value = value;
	}

	public static QCExpression addQCField(String field) {
		return new QCExpression(field);
	}

	/**
	 * 全文检索
	 */
	@Override
	public String toQueryString(FakeSolrParam param) throws SearchException {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		param.setQc(value);
		return value;
	}

	public String getValue() {
		return value;
	}
}
package solr.query.criterion;

import solr.Exception.SearchException;
import solr.query.FakeSolrParam;

/**
 * 不包含
 *
 */
public class NotExpression implements Criterion {

	private static final long serialVersionUID = 29791495279756569L;
	private final Criterion criterion;

	protected NotExpression(Criterion criterion) {
		this.criterion = criterion;
	}

	public String toQueryString(FakeSolrParam param) throws SearchException {
		String query = "-(" + criterion.toQueryString(new FakeSolrParam()) + ")";
		param.getFq().add(query);
		return query;
	}

	public Criterion getCriterion() {
		return criterion;
	}
}

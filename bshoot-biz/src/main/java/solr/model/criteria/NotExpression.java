package solr.model.criteria;

import solr.Exception.SearchException;
import solr.model.query.FakeSolrParam;

/**
 * 不包含
 *
 */
public class NotExpression implements Expression {

	private static final long serialVersionUID = 29791495279756569L;
	private final Expression criterion;

	protected NotExpression(Expression criterion) {
		this.criterion = criterion;
	}

	public String parse(FakeSolrParam param) throws SearchException {
		String query = "-(" + criterion.parse(new FakeSolrParam())+ ")";
		param.getFq().add(query);
		return query;
	}

	public Expression getCriterion() {
		return criterion;
	}
}

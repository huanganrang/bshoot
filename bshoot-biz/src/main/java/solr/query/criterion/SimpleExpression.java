package solr.query.criterion;

import org.apache.commons.lang.StringUtils;
import solr.Exception.SearchException;
import solr.common.SystemUtils;
import solr.query.FakeSolrParam;

/**
 * 最简单的查询标准 包括=,>,>=,<,<=,<>
 * 
 */
public class SimpleExpression extends DefaultCriterionImpl {

	private static final long serialVersionUID = 29791495279756569L;
	private final String field;
	private final String value;
	private boolean ignoreCase;
	private final String op;

	protected SimpleExpression(String field, String value, String op) {
		this.field = field;
		this.value = value;
		this.op = op;
		this.ignoreCase = true;
	}

	protected SimpleExpression(String field, String value, String op, boolean ignoreCase) {
		this.field = field;
		this.value = value;
		this.ignoreCase = ignoreCase;
		this.op = op;
	}

	public SimpleExpression ignoreCase() {
		ignoreCase = true;
		return this;
	}

	/**
	 * 
	 */
	@Override
	public String toQueryString(FakeSolrParam param) throws SearchException {
		String v = SystemUtils.solrStringTrasfer(this.value);
		String query = "";
		if ("=".equalsIgnoreCase(op)) {
			query = this.noCacheStat(this.field) + this.field + ":" + v;
		} else if (">".equalsIgnoreCase(op)) {
			query = this.noCacheStat(this.field) + this.field + ":" + "[" + v + " TO *] -" + this.field + ":" + v;
		} else if ("<".equalsIgnoreCase(op)) {
			query = this.noCacheStat(this.field) + this.field + ":" + "[* TO " + v + "] -" + this.field + ":" + v;
		} else if ("<>".equalsIgnoreCase(op)) {
			query = "-" + this.noCacheStat(this.field) + this.field + ":" + v;
		} else if (">=".equalsIgnoreCase(op)) {
			query = this.noCacheStat(this.field) + this.field + ":" + "[" + v + " TO *]";
		} else if ("<=".equalsIgnoreCase(op)) {
			query = this.noCacheStat(this.field) + this.field + ":" + "[* TO " + v + "]";
		} else if ("v%".equalsIgnoreCase(op)) {
			v = SystemUtils.escapeToSolr(this.value) + "*";
			query = this.noCacheStat(this.field) + this.field + ":" + v;
		} else if ("%v".equalsIgnoreCase(op)) {
			v = "*" + SystemUtils.escapeToSolr(this.value);
			query = this.noCacheStat(this.field) + this.field + ":" + v;
		} else if ("%v%".equalsIgnoreCase(op)) {
			v = "*" + SystemUtils.escapeToSolr(this.value) + "*";
			query = this.noCacheStat(this.field) + this.field + ":" + v;
		}
		if (StringUtils.isNotBlank(query)) {
			param.getFq().add(query);
		}
		/*
		 * else if("like".equalsIgnoreCase(op)){ return new
		 * QueryParam(QueryParamMode
		 * .q,this.field,Arrays.asList(this.value),null); }
		 */
		return query;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public String getOp() {
		return op;
	}
}

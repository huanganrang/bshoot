package solr.model.criteria;
import solr.common.SystemUtils;
import solr.exception.SearchException;
import solr.model.query.FakeSolrParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 各种逻辑查询的组合
 */
public class LogicalExpression implements Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9172408676695825541L;
	private final LogicalExpEnum op; // 符合 OR 或者其他
	private List<Expression> criterions = new ArrayList<Expression>();
	boolean isSameField = false; //是否为同意field的多逻辑组合
	private String field;
	private List<String> values;

	protected LogicalExpression(List<Expression> criterions, LogicalExpEnum op, boolean isSameField) {
		this.criterions = criterions;
		this.op = op;
		this.isSameField = isSameField;
		this.values = null;
	}

	protected LogicalExpression(String field, List<String> values, LogicalExpEnum op, boolean isSameField) {
		this.criterions = null;
		this.op = op;
		this.field = field;
		this.isSameField = isSameField;
		this.values = values;
	}

	@Override
	public String parse(FakeSolrParam param) throws SearchException {
		StringBuilder query = new StringBuilder("");
		if (isSameField) {// 如果是同一filed的话 就对value进行or组合
			int count = 0;
			query.append(this.field + ":(");
			for (String v : values) {
				query.append(SystemUtils.solrStringTrasfer(v));
				if (count < (values.size() - 1)) {
					query.append(op.getQop());
				}
				count++;
			}
			query.append(")");
		} else {// 如果是不同field的组合的话要分开出来
			int count = 0;
			for (Expression cr : criterions) {
				query.append("(" + cr.parse(new FakeSolrParam()) + ")");
				if (count < (criterions.size() - 1)) {
					query.append(op.getQop());
				}
				count++;
			}
		}
		param.getFq().add(query.toString());
		return query.toString();
	}

	public String getOp() {
		return op.getOp();
	}

	@Override
	public String toString() {
		return "LogicalExpression [op=" + getOp() + ", criterions=" + criterions + ", isSameField=" + isSameField
				+ ", field=" + field + ", values=" + values + "]";
	}
}

enum LogicalExpEnum {
	AND(1, "AND", " AND "), OR(2, "OR", " OR ");
	private int type;
	private String op;
	private String qop;

	private LogicalExpEnum(int type, String op, String qop) {
		this.type = type;
		this.op = op;
		this.qop = qop;
	}

	public int getType() {
		return type;
	}

	public String getOp() {
		return op;
	}

	public String getQop() {
		return qop;
	}
}
package solr.model.criteria;


import org.apache.commons.lang.StringUtils;
import solr.model.query.FakeSolrParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 限制条件辅助类
 * 
 */
public class Criterias {

	private FakeSolrParam fakeSolrParam;

	public Criterias(FakeSolrParam fakeSolrParam) {
		this.fakeSolrParam = fakeSolrParam;
	}

	/**
	 * equal
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public Expression eq(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression = new SimpleExpression(Field, value, "=");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * not equal
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public Expression ne(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression =new SimpleExpression(Field, value, "<>");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * greater than
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public Expression gt(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression =  new SimpleExpression(Field, value, ">");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * less than
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public Expression lt(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression = new SimpleExpression(Field, value, "<");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * less than or equal
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public Expression le(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression = new SimpleExpression(Field, value, "<=");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * greater than or equal
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public Expression ge(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression = new SimpleExpression(Field, value, ">=");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * suffix like aaa%
	 * 
	 * @param Field
	 * @param value
	 * @return
	 */
	public Expression sl(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression = new SimpleExpression(Field, value, "v%");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * prefix like aaa%
	 * 
	 * @param Field
	 * @param value
	 * @return
	 */
	public Expression pl(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression =  new SimpleExpression(Field, value, "%v");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * moddlie like %aaa%
	 * 
	 * @param Field
	 * @param value
	 * @return
	 */
	public Expression ml(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		Expression expression = new SimpleExpression(Field, value, "%v%");
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * not
	 *
	 * @return Criterion
	 */
	public Expression not(Expression c) {
		Expression expression = new NotExpression(c);
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * 全文搜索
	 * 
	 * @param value
	 * @return Criterion
	 */
	public Expression qc(String value) {
		Expression expression = new QCExpression(value);
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * between
	 * 
	 * @param Field
	 * @param lo
	 *            value
	 * @param hi
	 *            value
	 * @return Criterion
	 */
	public  Expression between(String field, String lo, String hi) {
		if (StringUtils.isBlank(field)) {
			throw new IllegalArgumentException("'field' can not be empty!");
		}
		Expression expression = new BetweenExpression(field, lo, hi);
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * (field1:v1 OR field2:v2......) 不同field的or的组合
	 * 
	 * @param l
	 * @return
	 */
	public Expression or(Expression... l) {
		if (l == null || l.length <= 1) {
			throw new IllegalArgumentException(
					"'Criterion' can not be null! and size of Criterion must be great than 1!");
		}
		List<Expression> list = new ArrayList<Expression>();
		for (Expression criterion : l) {
			list.add(criterion);
		}
		Expression expression = new LogicalExpression(list, LogicalExpEnum.OR, false);
		expression.parse(fakeSolrParam);
		return expression;
	}

	public Expression or(List<Expression> list) {
		if (list == null || list.size() <= 1) {
			throw new IllegalArgumentException(
					"'Criterion' can not be null! and size of Criterion must be great than 1!");
		}
		Expression expression = new LogicalExpression(list, LogicalExpEnum.OR, false);
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * field1:v1 OR v2 OR......)
	 *
	 * @return Criterion
	 */
	public Expression or(String field, String... values) {
		if (StringUtils.isBlank(field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		if (values == null || values.length <= 1) {
			throw new IllegalArgumentException("'values' can not be null! and size of values must be great than 1!");
		}
		List<String> list = new ArrayList<String>();
		for (String v : values) {
			if (StringUtils.isNotBlank(v)) {
				list.add(v);
			}
		}
		Expression expression = new LogicalExpression(field, list, LogicalExpEnum.OR, true);
		expression.parse(fakeSolrParam);
		return expression;
	}

	/**
	 * field1:v1 OR v2 OR......)
	 *
	 * @return Criterion
	 */
	public Expression or(String field, Collection<String> values) {
		if (StringUtils.isBlank(field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		if (values == null || values.size() <= 1) {
			throw new IllegalArgumentException("'values' can not be null! and size of values must be great than 1!");
		}
		List<String> list = new ArrayList<String>();
		for (String v : values) {
			if (StringUtils.isNotBlank(v)) {
				list.add(v);
			}
		}
		Expression expression = new LogicalExpression(field, list, LogicalExpEnum.OR, true);
		expression.parse(fakeSolrParam);
		return expression;
	}

	public Expression and(Expression... l) {
		if (l == null || l.length <= 1) {
			throw new IllegalArgumentException(
					"'Criterion' can not be null! and size of Criterion must be great than 1!");
		}
		List<Expression> list = new ArrayList<Expression>();
		for (Expression criterion : l) {
			list.add(criterion);
		}
		Expression expression = new LogicalExpression(list, LogicalExpEnum.AND, false);
		expression.parse(fakeSolrParam);
		return expression;
	}

	public Expression and(List<Expression> list) {
		if (list == null || list.size() <= 1) {
			throw new IllegalArgumentException(
					"'Criterion' can not be null! and size of Criterion must be great than 1!");
		}
		Expression expression = new LogicalExpression(list, LogicalExpEnum.AND, false);
		expression.parse(fakeSolrParam);
		return expression;
	}

	public Expression addOrder(String field,String ascending){
		Expression expression = new Order(field,ascending);
		fakeSolrParam.setOt(1);//设置为自定义排序
		expression.parse(fakeSolrParam);
		return expression;
	}

	public Expression addGroup(String field){
		Expression expression = new Group(field);
		expression.parse(fakeSolrParam);
		return expression;
	}

	public Expression addLocation(String field,String value,float distance,String sort){
		Expression expression = new Location(field,value,distance,sort);
		expression.parse(fakeSolrParam);
		return expression;
	}

	public void addField(String... fields){
		for(String f:fields)
			fakeSolrParam.getFl().add(f);
	}

	public void setFormat(String format){
		fakeSolrParam.setFormat(format);
	}
}
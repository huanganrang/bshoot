package solr.query.criterion;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 限制条件辅助类
 * 
 */
public class Restrictions {

	/**
	 * equal
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression eq(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, "=");
	}

	/**
	 * not equal
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression ne(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, "<>");
	}

	/**
	 * greater than
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression gt(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, ">");
	}

	/**
	 * less than
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression lt(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, "<");
	}

	/**
	 * less than or equal
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression le(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, "<=");
	}

	/**
	 * greater than or equal
	 * 
	 * @param Field
	 * @param value
	 * @return Criterion
	 */
	public static SimpleExpression ge(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, ">=");
	}

	/**
	 * suffix like aaa%
	 * 
	 * @param Field
	 * @param value
	 * @return
	 */
	public static SimpleExpression sl(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, "v%");
	}

	/**
	 * prefix like aaa%
	 * 
	 * @param Field
	 * @param value
	 * @return
	 */
	public static SimpleExpression pl(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, "%v");
	}

	/**
	 * moddlie like %aaa%
	 * 
	 * @param Field
	 * @param value
	 * @return
	 */
	public static SimpleExpression ml(String Field, String value) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new SimpleExpression(Field, value, "%v%");
	}

	/**
	 * not
	 *
	 * @return Criterion
	 */
	public static NotExpression not(Criterion c) {
		return new NotExpression(c);
	}

	/**
	 * 全文搜索
	 *
	 * @param value
	 * @return Criterion
	 */
	public static QCExpression ftr(String value) {
		return new QCExpression(value);
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
	public static Criterion between(String Field, String lo, String hi) {
		if (StringUtils.isBlank(Field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		return new BetweenExpression(Field, lo, hi);
	}

	/**
	 * (field1:v1 OR field2:v2......) 不同field的or的组合
	 * 
	 * @param l
	 * @return
	 */
	public static LogicalExpression or(Criterion... l) {
		if (l == null || l.length <= 1) {
			throw new IllegalArgumentException(
					"'Criterion' can not be null! and size of Criterion must be great than 1!");
		}
		List<Criterion> list = new ArrayList<Criterion>();
		for (Criterion criterion : l) {
			list.add(criterion);
		}
		return new LogicalExpression(list, LogicalExpEnum.OR, false);
	}

	public static LogicalExpression or(List<Criterion> list) {
		if (list == null || list.size() <= 1) {
			throw new IllegalArgumentException(
					"'Criterion' can not be null! and size of Criterion must be great than 1!");
		}
		return new LogicalExpression(list, LogicalExpEnum.OR, false);
	}

	/**
	 * field1:v1 OR v2 OR......)
	 *
	 * @return Criterion
	 */
	public static LogicalExpression or(String field, String... values) {
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
		return new LogicalExpression(field, list, LogicalExpEnum.OR, true);
	}

	/**
	 * field1:v1 OR v2 OR......)
	 * @return Criterion
	 */
	public static LogicalExpression or(String field, Collection<String> values) {
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
		return new LogicalExpression(field, list, LogicalExpEnum.OR, true);
	}

	public static LogicalExpression and(Criterion... l) {
		if (l == null || l.length <= 1) {
			throw new IllegalArgumentException(
					"'Criterion' can not be null! and size of Criterion must be great than 1!");
		}
		List<Criterion> list = new ArrayList<Criterion>();
		for (Criterion criterion : l) {
			list.add(criterion);
		}
		return new LogicalExpression(list, LogicalExpEnum.AND, false);
	}

	public static LogicalExpression and(List<Criterion> list) {
		if (list == null || list.size() <= 1) {
			throw new IllegalArgumentException(
					"'Criterion' can not be null! and size of Criterion must be great than 1!");
		}
		return new LogicalExpression(list, LogicalExpEnum.AND, false);
	}
}
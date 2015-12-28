/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2008, Red Hat Middleware LLC or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Middleware LLC.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 *
 */
package solr.query.criterion;

import org.apache.commons.lang.StringUtils;
import solr.Exception.SearchException;
import solr.query.FakeSolrParam;

/**
 * 排序
 */
public class Order implements Criterion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2572216777384185737L;
	private String ascending;
	private String field;

	@Override
	public String toString() {
		return "Order [ascending=" + ascending + ", field=" + field + "]";
	}

	/**
	 * Constructor for Order.
	 */
	protected Order(String field, String ascending) {
		if (StringUtils.isBlank(field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		this.field = field;
		this.ascending = ascending;
	}

	/**
	 * Ascending order
	 *
	 * @param field
	 * @return Order
	 */
	public static Order asc(String field) {
		return new Order(field, "asc");
	}

	/**
	 * Descending order
	 *
	 * @param field
	 * @return Order
	 */
	public static Order desc(String field) {
		return new Order(field, "desc");
	}

	public String getAscending() {
		return ascending;
	}

	public String getField() {
		return field;
	}

	public String toQueryString(FakeSolrParam param) throws SearchException {
		if (StringUtils.equalsIgnoreCase(this.ascending, "asc")) {
			param.getSortAsc().add(this.field);
		} else if (StringUtils.equalsIgnoreCase(this.ascending, "desc")) {
			param.getSortDesc().add(this.field);
		}
		String query = this.field + " " + this.ascending;
		return query;
	}
}

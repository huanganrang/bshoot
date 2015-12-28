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
 * 分组条件
 */
public class Group implements Criterion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2572216777384185737L;
	private String field;

	@Override
	public String toString() {
		return "Group [field=" + field + "]";
	}

	/**
	 * Constructor for Order.
	 */
	protected Group(String field) {
		if (StringUtils.isBlank(field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		this.field = field;
	}

	public static Group addGroupField(String field) {
		return new Group(field);
	}

	public String getField() {
		return field;
	}

	public String setField() {
		return field;
	}

	public String toQueryString(FakeSolrParam param) throws SearchException {
		param.getGroupDesc().add(field);
		String query = this.field;
		return query;
	}
}

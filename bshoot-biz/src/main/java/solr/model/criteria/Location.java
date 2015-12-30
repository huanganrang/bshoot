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
package solr.model.criteria;

import org.apache.commons.lang.StringUtils;
import solr.exception.SearchException;
import solr.model.query.FakeSolrParam;

public class Location implements Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2572216777384185737L;
	private String field;

	private String value;

	private float distance = 1;

	private String sort;

	@Override
	public String toString() {
		return "Location [field=" + field + "]";
	}

	/**
	 * Constructor for distance.
	 */
	protected Location(String field, String value, float distance) {
		this(field, value, distance, "asc");
	}

	/**
	 * Constructor for Order.
	 */
	protected Location(String field, String value, float distance, String sort) {
		if (StringUtils.isBlank(field)) {
			throw new IllegalArgumentException("'Field' can not be empty!");
		}
		if (StringUtils.isBlank(value)) {
			throw new IllegalArgumentException("'value' can not be empty!");
		}
		this.field = field;
		this.value = value;
		this.distance = distance;
		this.sort = sort;
	}

	/**
	 * 缺省具体1公里 location=31.22967,121.5053
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public static Location addLocationField(String field, String value) {
		return addLocationField(field, value, 1);
	}

	/**
	 * location=31.22967,121.5053
	 * 
	 * @param field
	 * @param value
	 * @param distance
	 * @return
	 */
	public static Location addLocationField(String field, String value, float distance) {
		return new Location(field, value, distance);
	}

	public static Location addLocationFieldDesc(String field, String value) {
		return addLocationField(field, value, 0, "desc");
	}

	public static Location addLocationFieldAsc(String field, String value) {
		return addLocationField(field, value, 0, "asc");
	}

	public static Location addLocationField(String field, String value, float distance, String sort) {
		return new Location(field, value, distance, sort);
	}

	public String getField() {
		return field;
	}

	public String setField() {
		return field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String parse(FakeSolrParam param) throws SearchException {
		FakeSolrParam.Location location = new FakeSolrParam.Location(this.field,this.value,this.distance,this.sort);
		param.setLocation(location);
		return "sfield:" + field + " pt:" + value + " " + " d:" + distance + " " + " sort:" + sort;
	}
}

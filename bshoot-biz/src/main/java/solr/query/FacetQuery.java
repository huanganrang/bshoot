package solr.query;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.manyi.iw.search.query.criterion.Criterion;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

public class FacetQuery implements Serializable {

	private static final long serialVersionUID = 7892298909652936762L;

	private List<String> facetFieldList = Lists.newArrayList();

	private List<Criterion> facetSearchList = Lists.newArrayList();

	public FacetQuery addFactField(String factField) {
		Preconditions.checkState(StringUtils.isNotBlank(factField), "factField can not be empty");

		if (!facetFieldList.contains(factField)) {
			facetFieldList.add(factField);
		}
		return this;
	}

	public FacetQuery() {
	}

	public FacetQuery(List<String> facetFieldList) {
		Preconditions.checkState(CollectionUtils.isNotEmpty(facetFieldList), "facetFieldList can not be empty");

		for (String factField : facetFieldList) {
			addFactField(factField);
		}
	}

	public List<Criterion> getFacetSearchList() {
		return facetSearchList;
	}

	public void setFacetSearchList(List<Criterion> facetSearchList) {
		this.facetSearchList = facetSearchList;
	}

	public List<String> getFacetFieldList() {
		return facetFieldList;
	}

}

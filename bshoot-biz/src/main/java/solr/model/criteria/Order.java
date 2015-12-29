package solr.model.criteria;

import org.apache.commons.lang.StringUtils;
import solr.Exception.SearchException;
import solr.model.query.FakeSolrParam;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public class Order implements Expression{

    private String field;
    private String ascending;

    public Order(String field, String ascending) {
        this.field = field;
        this.ascending = ascending;
    }

    public void setField(String field) {
        this.field = field;
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

    @Override
    public String parse(FakeSolrParam fakeSolrParam) throws SearchException {
        if (StringUtils.equalsIgnoreCase(this.ascending,FakeSolrParam.SORT_ASC)) {
            fakeSolrParam.getSortAsc().add(this.field);
        } else if (StringUtils.equalsIgnoreCase(this.ascending, FakeSolrParam.SORT_DESC)) {
            fakeSolrParam.getSortDesc().add(this.field);
        }
        String query = this.field + " " + this.ascending;
        return query;
    }
}

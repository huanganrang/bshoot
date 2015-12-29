package solr.model.criteria;

import solr.Exception.SearchException;
import solr.common.SystemUtils;
import solr.model.query.FakeSolrParam;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public class BetweenExpression implements Expression{
    private String field;
    private String lo;
    private String hi;

    public BetweenExpression(String field, String lo, String hi) {
        this.field = field;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public String toString() {
        return "BetweenExpression{" +
                "field='" + field + '\'' +
                ", lo='" + lo + '\'' +
                ", hi='" + hi + '\'' +
                '}';
    }

    @Override
    public String parse(FakeSolrParam fakeSolrParam) throws SearchException {
        lo = SystemUtils.solrStringTrasfer(this.lo);
        hi = SystemUtils.solrStringTrasfer(this.hi);
        String query = this.field+"["+lo+" TO "+hi+"]";
        fakeSolrParam.getFq().add(query);
        return query;
    }
}

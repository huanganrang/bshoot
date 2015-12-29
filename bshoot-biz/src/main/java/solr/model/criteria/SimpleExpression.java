package solr.model.criteria;

import org.apache.commons.lang.StringUtils;
import solr.Exception.SearchException;
import solr.common.SystemUtils;
import solr.model.query.FakeSolrParam;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public class SimpleExpression implements Expression{
    private String field;
    private String op;
    private String value;

    public SimpleExpression(String field, String op, String value) {
        this.field = field;
        this.op = op;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String parse(FakeSolrParam fakeSolrParam) throws SearchException {
        value = SystemUtils.solrStringTrasfer(this.value);
        String query = "";
        if ("=".equalsIgnoreCase(op)) {
            query = this.field + ":" + value;
        } else if (">".equalsIgnoreCase(op)) {
            query =this.field + ":" + "[" + value + " TO *] -" + this.field + ":" + value;
        } else if ("<".equalsIgnoreCase(op)) {
            query =  this.field + ":" + "[* TO " + value + "] -" + this.field + ":" + value;
        } else if ("<>".equalsIgnoreCase(op)) {
            query = "-" + this.field + ":" + value;
        } else if (">=".equalsIgnoreCase(op)) {
            query = this.field + ":" + "[" + value + " TO *]";
        } else if ("<=".equalsIgnoreCase(op)) {
            query = this.field + ":" + "[* TO " + value + "]";
        } else if ("v%".equalsIgnoreCase(op)) {
            query = this.field + ":" + this.value + "*";;
        } else if ("%v".equalsIgnoreCase(op)) {
            query = this.field + ":" + "*" + this.value;;
        } else if ("%v%".equalsIgnoreCase(op)) {
            query = this.field + ":" + "*" + this.value + "*";
        }
        if (StringUtils.isNotBlank(query)) {
            fakeSolrParam.getFq().add(query);
        }
        return query;
    }
}

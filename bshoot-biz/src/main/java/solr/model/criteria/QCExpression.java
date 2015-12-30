package solr.model.criteria;

import solr.exception.SearchException;
import solr.model.query.FakeSolrParam;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public class QCExpression implements Expression{

    private String value;

    public QCExpression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String parse(FakeSolrParam fakeSolrParam) throws SearchException {
        fakeSolrParam.setQc(value);
        return value;
    }
}

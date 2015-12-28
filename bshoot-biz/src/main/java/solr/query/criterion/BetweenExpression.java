package solr.query.criterion;

import org.apache.commons.lang.StringUtils;
import solr.Exception.SearchException;
import solr.common.SystemUtils;
import solr.query.FakeSolrParam;

/**
 * Between标准
 */
public class BetweenExpression extends DefaultCriterionImpl{
	
	private static final long serialVersionUID = 29791495279756569L;
	private final String field;
	private final String lo;
	private final String hi;
	
	protected BetweenExpression(String field, String lo, String hi) {
		this.field = field;
		this.lo = lo;
		this.hi = hi;
	}

	/**
	 * solr中范围搜索[x TO *]
	 */
	@Override
	public String toQueryString(FakeSolrParam param) throws SearchException {
		if(StringUtils.isBlank(lo) && StringUtils.isBlank(hi)){
			return "";
		}
		String _lo= SystemUtils.solrStringTrasfer(this.lo);
		String _hi= SystemUtils.solrStringTrasfer(this.hi);
		String query = this.noCacheStat(this.field)+this.field+":["+_lo+" TO "+_hi+"]";
		param.getFq().add(query);
		return query;
	}

	public String getField() {
		return field;
	}

	public Object getLo() {
		return lo;
	}

	public Object getHi() {
		return hi;
	}
}

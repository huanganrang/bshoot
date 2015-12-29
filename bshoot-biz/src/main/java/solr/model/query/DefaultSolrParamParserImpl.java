package solr.model.query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * solr查询参数解析类
 * Created by zhou on 2015/12/28.
 */
public class DefaultSolrParamParserImpl implements SolrParamParser{
    public SolrQuery parser(FakeSolrParam param) {
        SolrQuery solrQuery = new SolrQuery();
        if(StringUtils.isNotEmpty(param.getQt())){
            param.setQt(param.getQt());
        }
        if(StringUtils.isNotEmpty(param.getQc())){
            solrQuery.setQuery(param.getQc());
        }else{
            solrQuery.setQuery("*:*");
        }

        if(CollectionUtils.isNotEmpty(param.getFl())){
            String[] fl = new String[param.getFl().size()];
            param.getFl().toArray(fl);
            solrQuery.setFields(fl);
        }

        if(param.getLocation()!=null){
            FakeSolrParam.Location location = param.getLocation();
            solrQuery.setParam("pt",location.getVale());
            solrQuery.setParam("sfield",location.getField());

            if (location.getDistance() > 0) {
                solrQuery.setParam("fq", "{!geofilt}");// 距离过滤函数
                solrQuery.setParam("d",String.valueOf(location.getDistance()));
            }
            solrQuery.setParam("sort", "geodist() " + location.getSort());// 根据距离排序
        }

        if(CollectionUtils.isNotEmpty(param.getFq())){
            String[] fq = new String[param.getFq().size()];
            param.getFq().toArray(fq);
            solrQuery.addFilterQuery(fq);
        }

        if(param.getOt()==1){
            if(CollectionUtils.isNotEmpty(param.getSortAsc())){
                for(String f:param.getSortAsc()){
                    solrQuery.addSort(f, SolrQuery.ORDER.asc);
                }
            }

            if(CollectionUtils.isNotEmpty(param.getSortDesc())){
                for(String f:param.getSortDesc()){
                    solrQuery.addSort(f, SolrQuery.ORDER.desc);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(param.getGroupDesc())) {
            solrQuery.setParam("group", "true");
            solrQuery.setParam("group.ngroups", true);
            solrQuery.setParam("group.limit", "1");
            int num = 0;
            for (String f : param.getGroupDesc()) {
                if (num++ > 0)
                    break;
                solrQuery.setParam("group.field", f);
            }
        }
        solrQuery.setParam("wt",param.getFormat());
        solrQuery.setStart(param.getStart());
        solrQuery.setRows(param.getRows());
        return solrQuery;
    }
}

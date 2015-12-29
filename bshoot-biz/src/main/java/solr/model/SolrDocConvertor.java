package solr.model;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Zhou Yibing on 2015/12/29.
 */
public class SolrDocConvertor{

    public static  List convert(SolrDocumentList solrDocuments,Class clazz) throws IllegalAccessException, InstantiationException {
        List userEntities = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(solrDocuments)){
            Object obj = clazz.newInstance();
            for(SolrDocument solrDocument:solrDocuments){
                for(Field field:clazz.getDeclaredFields()){
                    field.setAccessible(true);
                    Object v = solrDocument.getFieldValue(field.getName());
                    if(null != v)
                        field.set(obj, solrDocument.getFieldValue(field.getName()));
                }
                userEntities.add(obj);
            }
        }
        return userEntities;
    }

    public static SolrInputDocument convert(Object obj) throws IllegalAccessException {
        if(null==obj)
            return null;
        SolrInputDocument solrDocument = new SolrInputDocument();
        //solrDocument.addField("id","1345");
        for(Field field: obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Object o = field.get(obj);
            if(null!=field.get(obj)) {
                solrDocument.addField(field.getName(), o);
            }
        }
        return solrDocument;
    }

    public static String list2String(List list){
        StringBuffer sb = new StringBuffer();
        for(Object obj:list){
            sb.append(obj);
        }
        return sb.toString();
    }
}

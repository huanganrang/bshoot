package solr.cores;

import com.google.common.collect.Lists;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import solr.model.*;
import solr.model.query.FakeSolrParam;

import java.util.List;

/**
 * solr 动态检索服务类
 * Created by zhou on 2015/12/28.
 */
@Service
public class BsCoreServiceImpl extends AbstractCoreService implements CoreService{

    public BsCoreServiceImpl() {
        collection = CoreEnum.bs.getValue();
        init();
    }

    public SolrResponse addBs(BsEntity bsEntity){
        try {
            return super.addDoc(SolrDocConvertor.convert(bsEntity));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SolrResponse addBss(List<BsEntity> bsEntities){
        List<SolrInputDocument> solrInputDocuments = Lists.newArrayList();
        try {
            for(BsEntity userEntity:bsEntities){
                SolrInputDocument solrInputDocument = SolrDocConvertor.convert(userEntity);
                if(null!=solrInputDocument)
                    solrInputDocuments.add(solrInputDocument);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return addDoc(solrInputDocuments);
    }

    public SolrResponse<BsEntity> query(FakeSolrParam solrParams){
        return super.query(solrParams, BsEntity.class);
    }
}

package solr.cores;

import com.google.common.collect.Lists;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.stereotype.Service;
import solr.model.CoreEnum;
import solr.model.SolrDocConvertor;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.query.FakeSolrParam;

import java.util.List;

/**
 * solr用户信息检索服务类
 * Created by zhou on 2015/12/28.
 */
@Service
public class UserCoreServiceImpl extends AbstractCoreService implements  CoreService{

    public UserCoreServiceImpl() {
        collection=CoreEnum.user.getValue();
        init();
    }

    public SolrResponse addUser(UserEntity userEntity){
        try {
            return super.addDoc(SolrDocConvertor.convert(userEntity));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SolrResponse addUsers(List<UserEntity> userEntities){
        List<SolrInputDocument> solrInputDocuments = Lists.newArrayList();
        try {
            for(UserEntity userEntity:userEntities){
                SolrInputDocument solrInputDocument = SolrDocConvertor.convert(userEntity);
                if(null!=solrInputDocument)
                    solrInputDocuments.add(solrInputDocument);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return addDoc(solrInputDocuments);
    }

    public SolrResponse<UserEntity> queryUser(FakeSolrParam solrParams){
        return super.query(solrParams, UserEntity.class);
    }
}

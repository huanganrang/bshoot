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
        coreEnum=CoreEnum.user;
        init();
    }
}

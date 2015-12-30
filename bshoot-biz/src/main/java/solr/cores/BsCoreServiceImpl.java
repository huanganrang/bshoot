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
        coreEnum = CoreEnum.bs;
        init();
    }
}

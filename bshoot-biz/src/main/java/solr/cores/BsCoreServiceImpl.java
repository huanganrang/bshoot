package solr.cores;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import solr.model.*;
/**
 * solr 动态检索服务类
 * Created by zhou on 2015/12/28.
 */
@Component
public class BsCoreServiceImpl extends AbstractCoreService implements CoreService{

    public BsCoreServiceImpl() {
        coreEnum = CoreEnum.bs;
        init();
    }
}

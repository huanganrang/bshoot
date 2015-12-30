package solr.cores;
import org.springframework.stereotype.Service;
import solr.model.CoreEnum;

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

package solr.cores;

import org.apache.solr.common.SolrInputDocument;
import solr.model.SolrResponse;
import solr.model.query.FakeSolrParam;

import java.util.Collection;

/**
 * solr core操作服务类
 * Created by zhou on 2015/12/28.
 */
public interface CoreService {
    /**
     * 检索
     * @param fakeSolrParam
     * @param clazz
     * @return
     */
    public SolrResponse query(FakeSolrParam fakeSolrParam,Class clazz);

    /**
     * 增加文档
     * @param documents
     * @return
     */
    public SolrResponse addDoc(Collection<SolrInputDocument> documents);
}

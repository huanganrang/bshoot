package solr.cores;

import org.apache.solr.common.SolrInputDocument;
import solr.model.SolrResponse;
import solr.model.query.FakeSolrParam;

import java.util.Collection;
import java.util.List;

/**
 * solr core操作服务类
 * Created by zhou on 2015/12/28.
 */
public interface CoreService {
    /**
     * 检索
     * @param fakeSolrParam
     * @return
     */
    public SolrResponse query(FakeSolrParam fakeSolrParam);

    /**
     * 增加文档
     * @param documents
     * @return
     */
    public SolrResponse addDocs(Collection<SolrInputDocument> documents);
    public SolrResponse addDoc(SolrInputDocument document);

    public<T> SolrResponse addEntity(T entity);
    public<T> SolrResponse addEntities(List<T> entities);
}

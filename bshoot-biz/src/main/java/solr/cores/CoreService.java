package solr.cores;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import solr.model.SolrResponse;
import solr.model.query.FakeSolrParam;

import java.io.IOException;
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

    public<T> SolrResponse addEntity(T entity);
    public<T> SolrResponse addEntities(List<T> entities);

    //public<T> SolrResponse update(FakeSolrParam fakeSolrParam,T... entity);

    public SolrResponse deleteByQuery(String param);
    public SolrResponse deleteById(String... ids);

    public void commit(boolean isAutoCommit) throws IOException, SolrServerException;
}

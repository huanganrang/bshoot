package solr.cores;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import solr.model.CoreEnum;
import solr.model.SolrDocConvertor;
import solr.model.SolrResponse;
import solr.model.query.FakeSolrParam;
import solr.model.query.SolrParamParser;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by zhou on 2015/12/28.
 */
@Component
public class AbstractCoreService implements   CoreService{
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String OP_SELECT="select";
    public static final String OP_UPDATE="update";

    protected CoreEnum coreEnum;
    private HttpSolrServer solrServer;
    private String baseUrl;
    private int connectTimeout;
    private int socketTimeout;
    private int defaultMaxConnection;
    private int maxTotalConnection;
    private int maxRetry;
    private boolean isAutoCommit=true;
    @Autowired
    private SolrParamParser solrParamParser;

    public boolean isAutoCommit() {
        return isAutoCommit;
    }

    public void setIsAutoCommit(boolean isAutoCommit) {
        this.isAutoCommit = isAutoCommit;
    }

    public void init(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("solr");
        baseUrl = resourceBundle.getString("solr.url");
        maxRetry = Integer.valueOf(resourceBundle.getString("solr.max.rertry"));
        connectTimeout = Integer.valueOf(resourceBundle.getString("solr.connect.timeout"));
        socketTimeout = Integer.valueOf(resourceBundle.getString("solr.so.timeout"));
        defaultMaxConnection = Integer.valueOf(resourceBundle.getString("solr.max.connection.default"));
        maxTotalConnection = Integer.valueOf(resourceBundle.getString("solr.max.connection.total"));

        solrServer = new HttpSolrServer(baseUrl + "/" + coreEnum.getCoreName());
        solrServer.setMaxRetries(maxRetry);
        solrServer.setConnectionTimeout(connectTimeout);
        solrServer.setSoTimeout(socketTimeout);
        solrServer.setDefaultMaxConnectionsPerHost(defaultMaxConnection);
        solrServer.setMaxTotalConnections(maxTotalConnection);
    }

    @Override
    public SolrResponse query(FakeSolrParam fakeSolrParam){
        if(null==fakeSolrParam){
            logger.error("the param fakeSolrParam is null");
            return null;
        }
        try {
            QueryResponse response = solrServer.query(solrParamParser.parser(fakeSolrParam));
            return this.queryResponse(response);
        } catch (SolrServerException e) {
            logger.error("service the solr server occurred an error.["+e.getMessage()+"]");
        }
        return null;
    }

    @Override
    public <T> SolrResponse addEntity(T entity){
        if(null==entity){
            logger.error("the param entity is null.");
            return null;
        }
        try {
            UpdateResponse response = solrServer.addBean(entity);
            commit(isAutoCommit);
            return this.updateResponse(response);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> SolrResponse addEntities(List<T> entities){
        if(CollectionUtils.isEmpty(entities)){
            logger.error("the param documents is null.");
            return null;
        }
        try {
            UpdateResponse response = solrServer.addBeans(entities.iterator());
            commit(isAutoCommit);
            return this.updateResponse(response);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SolrResponse deleteByQuery(String param) {
        if(StringUtils.isEmpty(param)){
            logger.error("the param fakeSolrParam is null");
            return null;
        }
        try {
            solrServer.deleteByQuery(param);
            commit(isAutoCommit);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SolrResponse deleteById(String... id) {
        if(null==id||id.length==0){
            logger.error("the param id is null");
            return null;
        }
        try {
            solrServer.deleteById(Lists.newArrayList(id));
            commit(isAutoCommit);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void commit(boolean isAutoCommit) throws IOException, SolrServerException {
            if(isAutoCommit)
            solrServer.commit();
    }

    private SolrResponse updateResponse(UpdateResponse updateResponse){
        if(updateResponse!=null){
            SolrResponse<Object> tSolrResponse  =  new SolrResponse();
            tSolrResponse.setOp(OP_UPDATE);
            tSolrResponse.setqTime(updateResponse.getQTime());
            tSolrResponse.setStatus(updateResponse.getStatus());
            return tSolrResponse;
        }
        return null;
    }

    private  SolrResponse queryResponse(QueryResponse queryResponse){
        if(null!=queryResponse){
            SolrResponse tSolrResponse = new SolrResponse();
            tSolrResponse.setOp(OP_SELECT);
            tSolrResponse.setqTime(queryResponse.getQTime());
            tSolrResponse.setStatus(queryResponse.getStatus());
            SolrDocumentList solrDocuments =  queryResponse.getResults();
            List list = queryResponse.getBeans(coreEnum.getClazz());
            tSolrResponse.setDocs(list);
            tSolrResponse.setNumFound(solrDocuments.getNumFound());
            return tSolrResponse;
        }
        return null;
    }
}

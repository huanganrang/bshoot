package solr.cores;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import solr.Exception.SearchException;
import solr.common.SearchClient;
import solr.model.SolrDocConvertor;
import solr.model.SolrResponse;
import solr.model.query.FakeSolrParam;
import solr.model.query.SolrParamParser;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by zhou on 2015/12/28.
 */
@Service
public class AbstractCoreService implements   CoreService{
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String OP_SELECT="select";
    public static final String OP_UPDATE="update";

    protected String collection;
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

        solrServer = new HttpSolrServer(baseUrl + "/" + collection);
        solrServer.setMaxRetries(maxRetry);
        solrServer.setConnectionTimeout(connectTimeout);
        solrServer.setSoTimeout(socketTimeout);
        solrServer.setDefaultMaxConnectionsPerHost(defaultMaxConnection);
        solrServer.setMaxTotalConnections(maxTotalConnection);
    }

    public SolrResponse addDoc(SolrInputDocument document){
        if(null==document){
            logger.error("the param document is null.");
            return null;
        }
        try {
            UpdateResponse response = solrServer.add(document);
            if(isAutoCommit)
            solrServer.commit();
            return this.updateResponse(response);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SolrResponse addDoc(Collection<SolrInputDocument> documents){
        if(CollectionUtils.isEmpty(documents)){
            logger.error("the param documents is null.");
            return null;
        }
        try {
            UpdateResponse response = solrServer.add(documents.iterator());
            if(isAutoCommit)
            solrServer.commit();
            return this.updateResponse(response);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SolrResponse query(FakeSolrParam fakeSolrParam,Class clazz){
        if(null==fakeSolrParam){
            logger.error("the param fakeSolrParam is null");
            return null;
        }
        try {
            QueryResponse response = solrServer.query(solrParamParser.parser(fakeSolrParam));
            return this.queryResponse(response,clazz);
        } catch (SolrServerException e) {
            logger.error("service the solr server occurred an error.["+e.getMessage()+"]");
        }
        return null;
    }

    public SolrResponse updateResponse(UpdateResponse updateResponse){
        if(updateResponse!=null){
            SolrResponse<Object> tSolrResponse  =  new SolrResponse();
            tSolrResponse.setOp(OP_UPDATE);
            tSolrResponse.setqTime(updateResponse.getQTime());
            tSolrResponse.setStatus(updateResponse.getStatus());
            return tSolrResponse;
        }
        return null;
    }

    public  SolrResponse queryResponse(QueryResponse queryResponse,Class clazz){
        if(null!=queryResponse){
            SolrResponse tSolrResponse = new SolrResponse();
            tSolrResponse.setOp(OP_SELECT);
            tSolrResponse.setqTime(queryResponse.getQTime());
            tSolrResponse.setStatus(queryResponse.getStatus());
            SolrDocumentList solrDocuments =  queryResponse.getResults();
            try {
                tSolrResponse.setDocs(SolrDocConvertor.convert(queryResponse.getResults(),clazz));
            } catch (IllegalAccessException e) {
               tSolrResponse.setMsg(e.getMessage());
            } catch (InstantiationException e) {
                tSolrResponse.setMsg(e.getMessage());
            }
            tSolrResponse.setNumFound(solrDocuments.getNumFound());
            return tSolrResponse;
        }
        return null;
    }
}

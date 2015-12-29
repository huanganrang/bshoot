package solr.cores;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import solr.model.SolrDocConvertor;
import solr.model.SolrResponse;
import solr.model.query.FakeSolrParam;
import solr.model.query.SolrParamParser;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by zhou on 2015/12/28.
 */
@Service
public class AbstractCoreService implements   CoreService{
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String OP_SELECT="select";
    public static final String OP_UPDATE="update";

    protected String collection="core_house_shard1_replica1";
    private HttpSolrServer solrServer;
    @Value("${solr.url}")
    private String baseUrl;
    @Value("${solr.connect.timeout}")
    private int connectTimeout;
    @Value("${solr.so.timeout}")
    private int socketTimeout;
    @Value("${solr.max.connection.default}")
    private int defaultMaxConnection;
    @Value("${solr.max.connection.total}")
    private int maxTotalConnection;
    @Value("${solr.max.rertry}")
    private int maxRetry;
    protected boolean isAutoCommit =true;

    @Autowired
    private SolrParamParser solrParamParser;

    @PostConstruct
    public void init(){
        solrServer = new HttpSolrServer(baseUrl+"/"+collection);
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
            solrServer.commit();
            return this.updateResponse(response);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends SolrDocConvertor> SolrResponse query(FakeSolrParam fakeSolrParam,T convertor){
        if(null==fakeSolrParam){
            logger.error("the param fakeSolrParam is null");
            return null;
        }
        try {
            QueryResponse response = solrServer.query(solrParamParser.parser(fakeSolrParam));
            return this.queryResponse(response,convertor);
        } catch (SolrServerException e) {
            logger.error("query the solr server occurred an error.["+e.getMessage()+"]");
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

    public <T extends SolrDocConvertor> SolrResponse queryResponse(QueryResponse queryResponse, T convertor){
        if(null!=queryResponse){
            SolrResponse<T> tSolrResponse = new SolrResponse<T>();
            tSolrResponse.setOp(OP_SELECT);
            tSolrResponse.setqTime(queryResponse.getQTime());
            tSolrResponse.setStatus(queryResponse.getStatus());
            if(queryResponse.getStatus()!=1){
                //TODO
            }
            SolrDocumentList solrDocuments =  queryResponse.getResults();
            tSolrResponse.setDocs(convertor.convert(queryResponse.getResults()));
            tSolrResponse.setNumFound(solrDocuments.getNumFound());
            return tSolrResponse;
        }
        return null;
    }
}

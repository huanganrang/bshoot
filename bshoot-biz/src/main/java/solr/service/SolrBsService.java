package solr.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solr.cores.CoreService;
import solr.model.BsEntity;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;

import java.util.List;

/**
 * Created by zhou on 2015/12/28.
 */
@Service
public class SolrBsService {
    @Autowired
    private CoreService bsCoreServiceImpl;

    public SolrResponse<BsEntity> query(Criterias criterias){
        return bsCoreServiceImpl.query(criterias.getFakeSolrParam());
    }

    public SolrResponse batchAddOrUpdate(BsEntity... bsEntities){
        List<BsEntity> userEntityList = Lists.newArrayList(bsEntities);
        return bsCoreServiceImpl.addEntities(userEntityList);
    }

    public SolrResponse addOrUpdate(BsEntity bsEntity){
        return bsCoreServiceImpl.addEntity(bsEntity);
    }
}

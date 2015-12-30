package solr.service;

import com.google.common.collect.Lists;
import jb.pageModel.UserHobby;
import jb.service.UserHobbyServiceI;
import jb.service.UserProfileServiceI;
import jb.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solr.cores.CoreService;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.criteria.Criterias;

import java.util.List;

/**
 * Created by zhou on 2015/12/28.
 */
@Service
public class SolrUserService{

/*    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private UserHobbyServiceI userHobbyServiceI;
    @Autowired
    private UserProfileServiceI userProfileServiceI;*/

    @Autowired
    private CoreService userCoreServiceImpl;

     public SolrResponse<UserEntity> query(Criterias criterias){
         return userCoreServiceImpl.query(criterias.getFakeSolrParam());
     }

    public SolrResponse batchAddOrUpdate(UserEntity... userEntities){
        List<UserEntity> userEntityList = Lists.newArrayList(userEntities);
        return userCoreServiceImpl.addEntities(userEntityList);
    }

    public SolrResponse addOrUpdate(UserEntity userEntity){
        return userCoreServiceImpl.addEntity(userEntity);
    }


    //quartz定时任务，定时将数据刷入solr库
    public void flushUserData2solr(){
    }
}

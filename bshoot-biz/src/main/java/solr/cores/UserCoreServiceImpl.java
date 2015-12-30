package solr.cores;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import solr.model.CoreEnum;
import solr.model.SolrResponse;
import solr.model.UserEntity;
import solr.model.query.FakeSolrParam;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * solr用户信息检索服务类
 * Created by zhou on 2015/12/28.
 */
@Component
public class UserCoreServiceImpl extends AbstractCoreService implements  CoreService{

    public UserCoreServiceImpl() {
        coreEnum=CoreEnum.user;
        init();
    }

    @Override
    public <T> SolrResponse update(FakeSolrParam fakeSolrParam, T... entity) {
        SolrResponse<UserEntity> response = this.query(fakeSolrParam);
        if(null==response|| CollectionUtils.isEmpty(response.getDocs())) return null;
        List<UserEntity> userList = response.getDocs();
        UserEntity userEntity = null;
        List<String> fields = fakeSolrParam.getFl();
        List<String> ingore = new ArrayList<String>();
        for(Field filed:UserEntity.class.getFields()){
            if(!fields.contains(filed.getName())){
                ingore.add(filed.getName());
            }
        }
        String[] ingorePros =  new String[ingore.size()];
        ingore.toArray(ingorePros);
        List<UserEntity> updatedUser = new ArrayList<UserEntity>();
        for(UserEntity item:userList){
            for(int i=0;i<entity.length;i++) {
                userEntity = (UserEntity) entity[i];
                if (item.getId().equals(userEntity.getId())) {
                    BeanUtils.copyProperties(entity,item,ingorePros);
                    updatedUser.add(item);
                }
            }
        }
        return addEntities(updatedUser);
    }
}

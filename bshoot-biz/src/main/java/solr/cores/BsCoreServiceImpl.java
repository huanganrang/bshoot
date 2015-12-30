package solr.cores;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import solr.model.*;
import solr.model.query.FakeSolrParam;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * solr 动态检索服务类
 * Created by zhou on 2015/12/28.
 */
@Component
public class BsCoreServiceImpl extends AbstractCoreService implements CoreService{

    public BsCoreServiceImpl() {
        coreEnum = CoreEnum.bs;
        init();
    }

    @Override
    public <T> SolrResponse update(FakeSolrParam fakeSolrParam, T... entity) {
        SolrResponse<BsEntity> response = this.query(fakeSolrParam);
        if(null==response|| CollectionUtils.isEmpty(response.getDocs())) return null;
        List<BsEntity> bsList = response.getDocs();
        BsEntity bsEntity = null;
        List<String> fields = fakeSolrParam.getFl();
        List<String> ingore = new ArrayList<String>();
        for(Field filed:UserEntity.class.getFields()){
            if(!fields.contains(filed.getName())){
                ingore.add(filed.getName());
            }
        }
        String[] ingorePros =  new String[ingore.size()];
        ingore.toArray(ingorePros);
        List<BsEntity> updatedBs = new ArrayList<BsEntity>();
        for(BsEntity item:bsList){
            for(int i=0;i<entity.length;i++) {
                bsEntity = (BsEntity) entity[i];
                if (item.getId().equals(bsEntity.getId())) {
                    BeanUtils.copyProperties(entity, item, ingorePros);
                    updatedBs.add(item);
                }
            }
        }
        return addEntities(updatedBs);
    }
}

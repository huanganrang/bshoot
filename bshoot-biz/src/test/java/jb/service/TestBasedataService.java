package jb.service;

import com.google.common.collect.Lists;
import jb.pageModel.BaseData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

import java.util.List;

/**
 * Created by zhou on 2016/1/1.
 */
public class TestBasedataService extends TestConfig{
    @Autowired
    private BasedataServiceI basedataServiceImpl;

    @Test
    public void testGetBaseDatas(){
        List<String> ids = Lists.newArrayList(new String[]{"SV004",
                "SV001",
                "SV002" ,
                "SV003"});
        BaseData bd = basedataServiceImpl.get("SV002");
        System.out.println(bd);
        List<BaseData> baseDatas  = basedataServiceImpl.getBaseDatas(ids);
        for(BaseData baseData:baseDatas){
            System.out.println(baseData);
        }
    }
}

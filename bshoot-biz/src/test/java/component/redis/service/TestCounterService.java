package component.redis.service;

import com.google.common.collect.Lists;
import component.redis.model.BshootCounter;
import component.redis.model.CounterType;
import component.redis.model.UserProfileCounter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

import java.util.List;

/**
 * Created by Zhou Yibing on 2016/1/18.
 */
public class TestCounterService extends TestConfig{

    @Autowired
    private CounterServiceI counterService;

    @Test
    public void testGetCounterByBshoots(){
        List<String> list = Lists.newArrayList(new String[]{"a","b"});
        List<BshootCounter> bshootCounters = counterService.getCounterByBshoots(list);
        for(BshootCounter bshootCounter:bshootCounters){
            System.out.println(bshootCounter);
        }
    }

    @Test
    public void testGetCounterByUserId(){
        UserProfileCounter up = counterService.getCounterByUser("123");
        System.out.println(up);
    }

    @Test
    public void testGetCounterByUsers(){
        List<String> list = Lists.newArrayList(new String[]{"123","111"});
        List<UserProfileCounter> userProfileCounters = counterService.getCounterByUsers(list);
        for(UserProfileCounter userProfileCounter:userProfileCounters){
            System.out.println(userProfileCounter);
        }
    }

    @Test
    public void testDeleteCounterByBshoot(){
        counterService.deleteCounterByBshoot("test");
    }

    @Test
    public void deleteCounterByBshoots(){
        List<String> list = Lists.newArrayList(new String[]{"test1","name"});
        counterService.deleteCounterByBshoots(list);
    }

    @Test
    public void testAutomicChangeCount(){
        counterService.automicChangeCount("a", CounterType.PRAISE, -112);
    }
}

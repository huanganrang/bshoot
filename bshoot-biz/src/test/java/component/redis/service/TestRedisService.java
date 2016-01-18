package component.redis.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import solr.TestConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by zhou on 2016/1/15.
 */
public class TestRedisService extends TestConfig{
    @Autowired
    private RedisServiceImpl redisService;

    @Test
    public void testMgetAll(){
        List<String> keys = Lists.newArrayList(new String[]{"ad", "test", "test1"});
        Map<String,Map<byte[],byte[]>> results =redisService.hGetAll(keys);
        System.out.println(results);
    }

}

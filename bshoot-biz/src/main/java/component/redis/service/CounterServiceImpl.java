package component.redis.service;

import component.redis.exception.CounterException;
import component.redis.model.BshootCounter;
import component.redis.model.Counter;
import component.redis.model.CounterType;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhou on 2016/1/14.
 */
public class CounterServiceImpl implements CounterServiceI{
    @Autowired
    private RedisServiceImpl redisService;

    @Override
    public void increment(String bshootId, CounterType countType) throws CounterException {
        this.changeCount(bshootId,countType,1);
    }

    @Override
    public void changeCount(String bshootId, CounterType countType, Integer num) throws CounterException {
        try {
            redisService.hincreby(bshootId, countType.getType(), num);
        }catch (Exception e){
            e.printStackTrace();
            throw new CounterException("change count has occured an exception，"+e.getMessage());
        }
    }

    @Override
    public void decrement(String bshootId, CounterType countType) throws CounterException {
        this.changeCount(bshootId,countType,-1);
    }

    @Override
    public BshootCounter getCounterByBshoot(String bshootId) throws CounterException {
        try{
             Map<Object,Object> fieldValues = redisService.getHash(bshootId);
             if(null!=fieldValues&&fieldValues.size()>0){
                 BshootCounter bshootCounter = new BshootCounter();
                 Field[] fields = bshootCounter.getClass().getFields();
                 bshootCounter.setBshootId(bshootId);
                 for(Field field:fields){
                   if(field.isAnnotationPresent(Counter.class)){
                       Counter counter = field.getAnnotation(Counter.class);
                       field.set(bshootCounter,fieldValues.get(counter.value()));
                   }
                 }
                 return bshootCounter;
             }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            throw new CounterException("getCounterByBshoot has occured an exception，"+e.getMessage());
        }
    }

    @Override
    public List<BshootCounter> getCounterByBshoots(List<String> bshootIds) throws CounterException {
        try{
        Map<String,Map<byte[],byte[]>> results = redisService.hGetAll(bshootIds);
        List<BshootCounter> bshoots = new ArrayList<>();
        Field[] fields = BshootCounter.class.getFields();
        for(Map.Entry<String,Map<byte[],byte[]>> entry:results.entrySet()){
            BshootCounter bshootCounter = new BshootCounter();
            bshootCounter.setBshootId( entry.getKey());
            Map<byte[],byte[]> fieldValues = entry.getValue();
            if(null==fieldValues||fieldValues.size()==0) {
                bshoots.add(bshootCounter);
                continue;
            }
            for(Field field:fields){
                if(field.isAnnotationPresent(Counter.class)){
                    Counter counter = field.getAnnotation(Counter.class);
                    field.set(bshootCounter,fieldValues.get(counter.value()));
                }
            }
            bshoots.add(bshootCounter);
        }
            return bshoots;
        }catch (Exception e){
            e.printStackTrace();
            throw new CounterException("getCounterByBshoots has occured an exception，"+e.getMessage());
        }
    }

    @Override
    public void deleteCounterByBshoot(String bshootId) throws CounterException {

    }

    @Override
    public void deleteCounterByBshoots(List<String> bshootIds) throws CounterException {

    }
}

package component.redis.service;

import component.redis.exception.CounterException;
import component.redis.model.BshootCounter;
import component.redis.model.Counter;
import component.redis.model.CounterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhou on 2016/1/14.
 */
@Service
public class CounterServiceImpl implements CounterServiceI{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private RedisLock redisLock;

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
    public boolean automicChangeCount(String bshootId,CounterType counterType,Integer num,FetchValue fetchValue) throws CounterException {
        if(null==fetchValue) throw new CounterException("you must implemnts the FetchValue,tell me how to fetch the value!");
        try {
            //获得redis锁，并设置拥有时间600ms,等待锁时间1s
            if(redisLock.lock("lock_"+bshootId,"1",600L,30000L)){
                Object countValue = redisService.getHashValue(bshootId,counterType.getType());
                //1.先查看指定的count字段是否存在
                if(null!=countValue){
                    //count字段与num计算后是否小于0
                    Integer exceptValue = Integer.parseInt(String.valueOf(countValue))+num;
                    if(exceptValue<0) {
                        throw new CounterException("the counter value can not less than 0 after change.[bshootId="+bshootId+",counterType="+counterType.getType()+",num="+num+"]");
                    }
                }else{
                    //1.1不存在则从数据库中获取值，并设置进去
                    num = fetchValue.fetchValue();
                }
                redisService.hincreby(bshootId, counterType.getType(), num);
                return true;
            }
            return false;
        }catch (Exception e){
            throw new CounterException("automicChangeCount has occured an exception,"+e.getMessage());
        }finally {
            //redisLock.unlock("lock_"+bshootId);
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
                 Field[] fields = bshootCounter.getClass().getDeclaredFields();
                 bshootCounter.setBshootId(bshootId);
                 for(Field field:fields){
                     field.setAccessible(true);
                   if(field.isAnnotationPresent(Counter.class)){
                       Counter counter = field.getAnnotation(Counter.class);
                       if(null!=fieldValues.get(counter.value().getType()))
                       field.set(bshootCounter,Integer.parseInt(String.valueOf(fieldValues.get(counter.value().getType()))));
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
            Field[] fields = BshootCounter.class.getDeclaredFields();
            for(String bshootId:bshootIds){
                BshootCounter bshootCounter = new BshootCounter();
                bshootCounter.setBshootId( bshootId);
                Map<byte[],byte[]> fieldValues = results.get(bshootId);
                if(null==fieldValues||fieldValues.size()==0) {
                    bshoots.add(bshootCounter);
                    continue;
                }
                for(Field field:fields){
                    field.setAccessible(true);
                    if(field.isAnnotationPresent(Counter.class)){
                        Counter counter = field.getAnnotation(Counter.class);
                        if(null!=fieldValues.get(counter.value().getType()))
                        field.set(bshootCounter,Integer.parseInt(String.valueOf(fieldValues.get(counter.value().getType()))));
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
         try {
             redisService.delete(bshootId);
         }catch (Exception e){
             throw new CounterException("deleteCounterByBshoot has occured an exception,"+e.getMessage());
         }
    }

    @Override
    public void deleteCounterByBshoots(List<String> bshootIds) throws CounterException {
        try {
            redisService.delete(bshootIds);
        }catch (Exception e){
            throw new CounterException("deleteCounterByBshoots has occured an exception,"+e.getMessage());
        }
    }

    @Override
    public void deleteCounterByBshoot(String bshootId, CounterType counterType) throws CounterException {

    }
}

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
    public void automicChangeCount(String bshootId,CounterType counterType,Integer num,FetchValue fetchValue) throws CounterException {
        if(null==fetchValue) throw new CounterException("you must implemnts the FetchValue,tell me how to fetch the value!");
        try {
            //1.先查看指定的count字段是否存在
            if (!redisService.hexists(bshootId, counterType.getType())) {
                //1.1不存在则从数据库中获取值，并设置进去
                Integer value = fetchValue.fetchValue();
                //没有则set
                if (!redisService.hsetnx(bshootId, counterType.getType(), value)) {//如果设置失败，则可能有其他线程设置了该字段，那么就则值上+或-num
                    redisService.hincreby(bshootId, counterType.getType(), num);
                }
            } else {
                //1.2存在则直接更新字段值
                redisService.hincreby(bshootId, counterType.getType(), num);
            }
        }catch (Exception e){
            throw new CounterException("automicChangeCount has occured an exception,"+e.getMessage());
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
            for(String bshootId:bshootIds){
                BshootCounter bshootCounter = new BshootCounter();
                bshootCounter.setBshootId( bshootId);
                Map<byte[],byte[]> fieldValues = results.get(bshootId);
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

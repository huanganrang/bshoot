package component.redis.service;

import component.redis.exception.CounterException;
import component.redis.model.BshootCounter;
import component.redis.model.Counter;
import component.redis.model.CounterType;
import component.redis.model.UserProfileCounter;
import jb.pageModel.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhou on 2016/1/14.
 */
@Service
public class CounterServiceImpl implements CounterServiceI{
    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private RedisLock redisLock;

    private Map<CounterType,FetchValue> fetchValueContext = new HashMap<CounterType,FetchValue>();

    @Override
    public void increment(String bshootId, CounterType countType) throws CounterException {
        this.changeCount(bshootId,countType,1);
    }

    @Override
    public void increment(String bshootId, CounterType countType, Integer num) throws CounterException {
        this.changeCount(bshootId,countType,num);
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
    public void setCount(String bshootId, CounterType countType, Integer num, long expireTime) throws CounterException{
        try{
            redisService.putHash(bshootId, countType.getType(), num.toString());
            redisService.expire(bshootId,expireTime,TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            throw new CounterException("setCount has occured an exception，"+e.getMessage());
        }
    }

    @Override
    public boolean automicChangeCount(String bshootId, CounterType counterType, Integer num, FetchValue fetchValue) throws CounterException {
        if(null==fetchValue) throw new CounterException("you must implemnts the FetchValue,tell me how to fetch the value!");
        try {
            //获得redis锁，并设置拥有时间600ms,等待锁时间1s
            if(redisLock.lock("lock_"+bshootId,"1",600L,800L)){
                Object countValue = redisService.getHashValue(bshootId,counterType.getType());
                //1.先查看指定的count字段是否存在
                if(null!=countValue){
                    //count字段与num计算后是否小于0
                    Integer exceptValue = parseInt(String.valueOf(countValue))+num;
                    if(exceptValue<0) {
                        throw new CounterException("the counter value can not less than 0 after change.[bshootId="+bshootId+",counterType="+counterType.getType()+",num="+num+"]");
                    }
                    redisService.hincreby(bshootId, counterType.getType(), num);

                }else{
                    //1.1不存在则从数据库中获取值，并设置进去
                    num = fetchValue.fetchValue(bshootId);
                    redisService.hsetnx(bshootId,counterType.getType(),String.valueOf(num));
                    redisService.expire(bshootId,1, TimeUnit.DAYS);
                }
                return true;
            }
            return false;
        }catch (Exception e){
            throw new CounterException("automicChangeCount has occured an exception,"+e.getMessage());
        }finally {
            redisLock.unlock("lock_"+bshootId);
        }
    }


    private Integer automicChangeCountRetunInt(String bshootId, CounterType counterType, Integer num, FetchValue fetchValue) throws CounterException {
        if(null==fetchValue) throw new CounterException("you must implemnts the FetchValue,tell me how to fetch the value!");
        try {
            //获得redis锁，并设置拥有时间600ms,等待锁时间1s
            if(redisLock.lock("lock_"+bshootId,"1",600L,800L)){
                Object countValue = redisService.getHashValue(bshootId,counterType.getType());
                //1.先查看指定的count字段是否存在
                if(null!=countValue){
                    //count字段与num计算后是否小于0
                    Integer exceptValue = parseInt(String.valueOf(countValue)) + num;
                    if(exceptValue<0) {
                        throw new CounterException("the counter value can not less than 0 after change.[bshootId="+bshootId+",counterType="+counterType.getType()+",num="+num+"]");
                    }
                    redisService.hincreby(bshootId, counterType.getType(), num);
                    num = exceptValue;
                }else{
                    //1.1不存在则从数据库中获取值，并设置进去
                    num = fetchValue.fetchValue(bshootId);
                    redisService.hsetnx(bshootId,counterType.getType(),String.valueOf(num));
                    redisService.expire(bshootId,1, TimeUnit.DAYS);
                }
                return num;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            throw new CounterException("automicChangeCount has occured an exception,"+e.getMessage());
        }finally {
            redisLock.unlock("lock_"+bshootId);
        }
    }


    @Override
    public boolean automicChangeCount(String bshootId, CounterType counterType, Integer num) throws CounterException {
        FetchValue fetchValue = fetchValueContext.get(counterType);
        if(fetchValue!=null){
            return automicChangeCount(bshootId,counterType,num,fetchValue);
        }
        return false;
    }

    @Override
    public void decrement(String bshootId, CounterType countType) throws CounterException {
        this.changeCount(bshootId,countType,-1);
    }

    @Override
    public BshootCounter getCounterByBshoot(String bshootId) throws CounterException {
        BshootCounter bshootCounter = new BshootCounter();
        bshootCounter.setId(bshootId);
        fillField(bshootId,bshootCounter);
        return bshootCounter;
    }

    @Override
    public List<BshootCounter> getCounterByBshoots(List<String> bshootIds) throws CounterException {
        return batchGetAndFill(bshootIds, BshootCounter.class);
    }

    @Override
    public UserProfileCounter getCounterByUser(String userId) {
        UserProfileCounter userProfileCounter = new UserProfileCounter();
        userProfileCounter.setId(userId);
        fillField(userId,userProfileCounter);
        return userProfileCounter;
    }

    @Override
    public List<UserProfileCounter> getCounterByUsers(List<String> userIds) throws CounterException {
        return batchGetAndFill(userIds, UserProfileCounter.class);
    }

    private  List batchGetAndFill(List<String> keys,Class clazz){
        try{
            Map<String,Map<byte[],byte[]>> results = redisService.hGetAll(keys);
            List counters = new ArrayList<>();
            Field[] fields = clazz.getDeclaredFields();
            for(String key:keys){
                Object c = clazz.newInstance();
                Map<byte[],byte[]> fieldValues = results.get(key);

                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals("id")) field.set(c, key);
                    if (field.isAnnotationPresent(Counter.class)) {
                        Counter counter = field.getAnnotation(Counter.class);
                        if (null == fieldValues || fieldValues.size() == 0) {
                            FetchValue fetchValue = fetchValueContext.get(counter.value());
                            if(fetchValue!=null){
                                Integer num = automicChangeCountRetunInt(key, counter.value(), 0, fetchValue);
                                if(num!=null)
                                    field.set(c,num);
                            }
                        } else {
                            if (null != fieldValues.get(counter.value().getType()))
                                field.set(c, parseInt(String.valueOf(fieldValues.get(counter.value().getType()))));
                        }
                    }
                }
                counters.add(c);
            }
            return counters;
        }catch (Exception e){
            e.printStackTrace();
            throw new CounterException("batchGetAndFill has occured an exception，"+e.getMessage());
        }
    }

    private int parseInt(String str){
        try{
            str = "null".equals(str)?"0":str;
            return Integer.parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private <T> void  fillField(String key,T c){
        try{
            Map<Object,Object> fieldValues = redisService.getHash(key);

            Field[] fields = c.getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                if(field.isAnnotationPresent(Counter.class)){
                    Counter counter = field.getAnnotation(Counter.class);
                    if(null!=fieldValues&&fieldValues.size()>0) {
                        if (null != fieldValues.get(counter.value().getType()))
                            field.set(c, parseInt(String.valueOf(fieldValues.get(counter.value().getType()))));
                    }else{
                        FetchValue fetchValue = fetchValueContext.get(counter.value());
                        if(fetchValue!=null){
                            Integer num = automicChangeCountRetunInt(key, counter.value(), 0, fetchValue);
                            if(num!=null)
                            field.set(c,num);
                        }
                    }
                    /*FetchValue fetchValue = fetchValueContext.get(counter.value());
                    if(fetchValue != null)
                    fetchValue.fetchValue(key);*/
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new CounterException("fillField has occured an exception，"+e.getMessage());
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

    @Override
    public Object get(String countKey) {
        return redisService.get(countKey);
    }

    @Override
    public void set(String countKey, String time) {
        redisService.set(countKey,time);
    }

    @Override
    public void registerFetchValue(CounterType counterType, FetchValue fetchValue) {
        fetchValueContext.put(counterType,fetchValue);
    }
}

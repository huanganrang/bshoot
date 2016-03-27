package component.redis.service;

import component.redis.exception.CounterException;
import component.redis.model.BshootCounter;
import component.redis.model.CounterType;
import component.redis.model.UserProfileCounter;

import java.util.List;

/**
 * Created by zhou on 2016/1/14.
 * 计数器服务
 * 1.动态评论，打赏，收藏，转发，阅读，播放，分享
 */
public interface CounterServiceI {
    /**
     * 计数+1
     * @param bshootId
     * @param countType
     */
    void increment(String bshootId,CounterType countType) throws CounterException;

    /**
     * 计数+n
     * @param bshootId
     * @param countType
     * @param num
     * @throws CounterException
     */
    void increment(String bshootId,CounterType countType,Integer num) throws CounterException;

    /**
     * 对动态指定计数的增加/减少
     * @param bshootId
     * @param countType
     * @param num 正数表示增加 负数表示减少
     * @throws CounterException
     */
    void changeCount(String bshootId,CounterType countType,Integer num)  throws CounterException;

    void setCount(String bshootId, CounterType countType, Integer num, long expireTime) throws CounterException;

    /**
     * 原子更新计数器
     * @param bshootId
     * @param counterType
     * @param num
     * @param fetchValue 没有值时进行取值的操作
     * @throws CounterException
     */
    boolean automicChangeCount(String bshootId,CounterType counterType,Integer num,FetchValue fetchValue) throws CounterException;

    boolean automicChangeCount(String bshootId,CounterType counterType,Integer num) throws CounterException;

    //boolean isExists(String bshootId,CounterType counterType) throws CounterException;

    //boolean hsetnx(String bshootId,CounterType counterType,int num) throws  CounterException;

    /**
     * 计数-1
     * @param bshootId
     * @param countType
     */
    void decrement(String bshootId,CounterType countType) throws CounterException;

    /**
     * 获得动态的计数
     * @param bshootId
     */
    BshootCounter getCounterByBshoot(String bshootId) throws CounterException;

    /**
     * 批量获得动态的计数
     * @param bshootIds
     */
    List<BshootCounter> getCounterByBshoots(List<String> bshootIds) throws CounterException;

    UserProfileCounter getCounterByUser(String userId) throws CounterException;

    List<UserProfileCounter> getCounterByUsers(List<String> userIds) throws CounterException;

    void deleteCounterByBshoot(String bshootId) throws CounterException;

    void deleteCounterByBshoots(List<String> bshootIds) throws CounterException;

    void deleteCounterByBshoot(String bshootId,CounterType counterType) throws  CounterException;

    Object get(String countKey);
    void set(String countKey,String time);

    /**
     * 注册FetchValue
     * @param counterType
     * @param fetchValue
     */
    void registerFetchValue(CounterType counterType,FetchValue fetchValue);
}

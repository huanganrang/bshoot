package component.redis.service;

import component.redis.exception.CounterException;
import component.redis.model.BshootCounter;
import component.redis.model.CounterType;
import org.springframework.beans.factory.annotation.Autowired;

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
     * 对动态指定计数的增加/减少
     * @param bshootId
     * @param countType
     * @param num 正数表示增加 负数表示减少
     * @throws CounterException
     */
    void changeCount(String bshootId,CounterType countType,Integer num)  throws CounterException;

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

    void deleteCounterByBshoot(String bshootId) throws CounterException;

    void deleteCounterByBshoots(List<String> bshootIds) throws CounterException;
}

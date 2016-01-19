package component.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * redis实现的分布式锁
 * Created by Zhou Yibing on 2016/1/19.
 */
@Component
public class RedisLock {

    @Autowired
    private RedisServiceImpl redisService;
    /**
     * 设置锁
     * @param lock 锁名称
     * @param value 锁值
     * @param expireTime 锁有效期
     * @param waitTime 等待时间
     * @return
     */
    public boolean lock(String lock,String value,Long expireTime,Long waitTime){
        long lastTime = System.currentTimeMillis();
        for(;;){
            if(redisService.setnx(lock,value,expireTime)) {
                return true;
            }
            long now = System.currentTimeMillis();
            waitTime = waitTime-(now -lastTime);
            lastTime=now;
            if(waitTime<=0)
                return false;
        }
    }

    public boolean lock(String lock,String value,Long expireTime){
        return lock(lock,value,expireTime,0L);
    }

    public void unlock(String lock){
        redisService.delete(lock);
    }
}

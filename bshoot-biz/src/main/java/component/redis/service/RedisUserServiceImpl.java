package component.redis.service;

import com.alibaba.fastjson.JSONObject;
import component.redis.Namespace;
import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.interceptors.TokenWrap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by john on 16/1/10.
 */
@Service(value = "redisUserService")
public class RedisUserServiceImpl {
    private long timeout = 1000 * 60 * 30;
    @Resource
    private RedisServiceImpl redisService;

    public boolean setToken(TokenWrap tokenWrap) {
        redisService.set(buildKey(tokenWrap.getTokenId()),JSONObject.toJSONString(tokenWrap), timeout, TimeUnit.SECONDS);
        return true;
    }

    private String buildKey(String token) {
        return Namespace.USER_LOGIN_TOKEN + ":" + token;
    }

    public TokenWrap getToken(String token){
        String json = (String)redisService.get(token);
        if(F.empty(json))return null;
        return JSONObject.parseObject(json,TokenWrap.class);
    }

    public void refresh(String token){
        redisService.expire(buildKey(token),timeout,TimeUnit.SECONDS);
    }
}

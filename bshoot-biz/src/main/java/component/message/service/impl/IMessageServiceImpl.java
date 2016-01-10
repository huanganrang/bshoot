package component.message.service.impl;

import component.message.service.IMessageService;
import component.redis.Namespace;
import component.redis.service.RedisServiceImpl;
import component.redis.util.Key;
import jb.absx.F;
import jb.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 15/12/28.
 */
@Service
public class IMessageServiceImpl implements IMessageService {
    @Resource
    private RedisServiceImpl redisService;
    public boolean sendMessage(String userId, String jsonText) {
        String messageServerIp = (String)redisService.getString(Key.build(Namespace.USER_LOGIN_SERVER_HOST, userId));
        if(F.empty(messageServerIp))return false;
        String url = messageServerIp +":8080/messageCenterController/sendMessage";
        Map<String,String> map = new HashMap<String,String>();
        map.put("userId",userId);
        map.put("content",jsonText);
        try {
            WebUtils.doPost(url,map,5000,5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

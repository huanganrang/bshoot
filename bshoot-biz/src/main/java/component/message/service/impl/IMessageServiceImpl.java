package component.message.service.impl;

import component.message.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Created by john on 15/12/28.
 */
@Service
public class IMessageServiceImpl implements IMessageService {
    public boolean sendMessage(String userId, String jsonText) {
        //TODO redis里获取用户所在ip
        String messageServerIp = "127.0.0.1";
        String url = messageServerIp +":8080/messageCenterController/sendMessage";
        //TODO 发送httpPost数据
        return true;
    }
}

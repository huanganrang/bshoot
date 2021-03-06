package component.message.service.impl;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.demo.Apns4jDemo;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Payload;
import component.message.service.IMessageService;
import component.redis.service.RedisUserServiceImpl;
import jb.absx.F;
import jb.absx.Objectx;
import jb.listener.Application;
import jb.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 15/12/28.
 */
@Service
public class IMessageServiceImpl extends Objectx implements IMessageService {
    @Resource
    private RedisUserServiceImpl redisUserService;
    private static IApnsService apnsService;
    public boolean sendMessage(String userId, String jsonText) {
        String messageServerIp = redisUserService.getUserConnect(userId);
        sendAppleMessage(userId,jsonText);
        if(F.empty(messageServerIp))return false;
        String url = "http://"+messageServerIp +":8088/api/messageCenterController/sendMessage";
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

    private void sendAppleMessage(String userId,String message){
        String token = redisUserService.getAppleToken(userId);
        if(F.empty(token))return;
        Payload payload = new Payload();
        payload.setAlert(message);
			/*payload.setAlertBody("alert body");
			payload.setAlertLocKey("use emotion ok");
			payload.setAlertLocArgs(new String[]{"3"});
			payload.setAlertActionLocKey("lbg_loading_text_0");*/
        payload.setBadge(1);
        payload.setSound("msg.mp3");
        try {
            getApnsService().sendNotification(token, payload);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private IApnsService getApnsService(){
        if (apnsService == null) {
            synchronized(IMessageServiceImpl.class){
                if(apnsService == null){
                    ApnsConfig config = new ApnsConfig();
                    InputStream is = Apns4jDemo.class.getClassLoader().getResourceAsStream("/apns_pro.p12");
                    config.setKeyStore(is);
                    config.setDevEnv(false);
                    config.setPassword("");
                    config.setPoolSize(5);
                    apnsService = ApnsServiceImpl.createInstance(config);
                }
            }
        }
        return apnsService;
    }

    @Override
    public String sendMobileValidateCodeForRegister(String mobilePhone) {
        String validateCode = buildCode();
        String url = getValue("PG001","http://222.73.117.158:80/msg/");
        String userName = getValue("PG002","hangke88");
        String password = getValue("PG003","Txb123456");
        String msg = getValue("PG004","你好你的验证码是：${code}");
        msg = msg.replace("${code}", validateCode);
        try {
            String result = HttpSender.batchSend(url, userName, password, mobilePhone, msg, true, null, null);
            String[] results = result.split("[,\n]");
            if("0".equals(results[1])){
                redisUserService.setValidateCode(mobilePhone,validateCode);
                return validateCode;
            }
        } catch (Exception e) {
            logger.error("发送短信错误",e);
        }
        return null;
    }

    private String buildCode() {
        String validateCode = "";
        java.util.Random random = new java.util.Random();// 定义随机类
        for (int i = 0; i < 6; i++) {
            int result = random.nextInt(10);
            validateCode += result;
        }
        return validateCode;
    }
    private String getValue(String code,String defaultValue){
        String value = Application.getString(code);
        if(F.empty(value)){
            value = defaultValue;
        }
        return value;
    }
}

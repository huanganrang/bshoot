package component.message.service;

/**
 * 消息推送服务
 * Created by john on 15/12/28.
 */
public interface IMessageService {

    boolean sendMessage(String userId, String jsonText);

    /**
     * 注册账号时候，发送短信验证
     * @param mobilePhone
     * @return
     */
    String sendMobileValidateCodeForRegister(String mobilePhone);
}

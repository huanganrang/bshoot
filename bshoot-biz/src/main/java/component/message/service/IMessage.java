package component.message.service;

/**
 * 消息推送服务
 * Created by john on 15/12/28.
 */
public interface IMessage {

    boolean sendMessage(String userId, String jsontext);
}

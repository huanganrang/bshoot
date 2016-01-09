package jb.controller;

import jb.android.push.NotificationManager;
import jb.pageModel.Json;
import org.androidpn.server.xmpp.XmppServer;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xmpp.packet.JID;

import java.util.HashSet;
import java.util.Set;

/**
 * 消息发送服务
 *
 * @author John
 */
@Controller
@RequestMapping("/api//messageCenterController")
public class MessageCenterController extends BaseController {
    @ResponseBody
    @RequestMapping("/sendMessage")
    public Json sendMessage(String userId, String content) {
        Json j = new Json();
        try {
            //TODO 异步处理发送消息
            j.setSuccess(sendMessageByXmpp(userId, content));
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    private boolean sendMessageByXmpp(String userId, String content) {
        try {
            NotificationManager notificationManager = (NotificationManager) XmppServer
                    .getInstance().getBean("notificationManager");
            notificationManager.sendNotifcationToUser(userId,content);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

package jb.listener;

import org.androidpn.server.util.ConfigManager;
import org.androidpn.server.xmpp.XmppServer;

import javax.servlet.ServletContextEvent;

/**
 * 系统全局容器
 * @author John
 *
 */
public class Application extends BaseApplication {
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        XmppServer.getInstance();
        ConfigManager.getInstance().getConfig().setProperty("server.home.dir", BaseApplication.class.getResource("/").getPath());
    }
}

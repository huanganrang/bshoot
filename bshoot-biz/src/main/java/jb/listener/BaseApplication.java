package jb.listener;

import jb.pageModel.BaseData;
import jb.service.BasedataServiceI;
import org.androidpn.server.util.ConfigManager;
import org.androidpn.server.xmpp.XmppServer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

/**
 * Created by john on 15/12/25.
 */
public abstract class BaseApplication implements ServletContextListener {
    private static ServletContext context;
    private static String PREFIX = "SV.";
    @Override
    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        initAppVariable();
    }

    private static void initAppVariable(){
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context);
        BasedataServiceI service = app.getBean(BasedataServiceI.class);
        Map<String,BaseData> map = service.getAppVariable();
        for(String key : map.keySet()){
            context.setAttribute(PREFIX+key, map.get(key));
        }
    }

    /**
     * 刷新全局变量值
     */
    public static void refresh(){
        initAppVariable();
    }

    /**
     * 获取全局变量值
     * @param key
     * @return
     */
    public static String getString(String key){
        BaseData bd = (BaseData)context.getAttribute(PREFIX+key);
        String val = null;
        if(bd != null){
            val = bd.getName();
        }
        return val;
    }

    /**
     * 获取全局变量值
     * @param key
     * @return
     */
    public static BaseData get(String key){
        BaseData bd = (BaseData)context.getAttribute(PREFIX+key);
        return bd;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {


    }

}
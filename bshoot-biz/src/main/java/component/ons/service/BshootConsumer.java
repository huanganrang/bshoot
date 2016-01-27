package component.ons.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.*;
import jb.pageModel.Bshoot;

import java.util.Properties;

/**
 * Created by john on 16/1/27.
 */
public class BshootConsumer {

    private void init(){
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_bshoot_add");
        properties.put(PropertyKeyConst.AccessKey, "K1vxSxnLWUXylCVo");
        properties.put(PropertyKeyConst.SecretKey, "5LPjklci4nkffZLos28KRnnvic0XVi");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("bshoot_add_bshoot", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                handler(new String(message.getBody()));
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }

    private void handler(String message){
        System.out.println(message);
        Bshoot bshoot = JSONObject.parseObject(message,Bshoot.class);
    }
}



package component.ons.service;

import com.aliyun.openservices.ons.api.*;
import component.redis.model.CounterType;
import jb.service.CounterHandler;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by john on 16/1/27.
 * 计数异步处理
 */
public class ComCounterConsumer {
    private Map<CounterType,CounterHandler> handlerMap = new HashMap<CounterType,CounterHandler>();
    private void init(){
        initHandler();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_com_counter");
        properties.put(PropertyKeyConst.AccessKey, "K1vxSxnLWUXylCVo");
        properties.put(PropertyKeyConst.SecretKey, "5LPjklci4nkffZLos28KRnnvic0XVi");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("bshoot_com_counter", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                String tag = message.getTag();
                CounterType counterType = CounterType.getCounterType(tag);
                dispatch(counterType, new String(message.getBody()));
                return Action.CommitMessage;
            }
        });
        consumer.start();

        System.out.println("bshoot_com_counter Consumer Started");
    }

    private void initHandler(){
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        CounterHandler counterHandler = wac.getBean("bshootCommentServiceImpl",CounterHandler.class);
        handlerMap.put(counterHandler.getCounterType(),counterHandler);
        //TODO 其他counter处理者的bean可以写这里
    }
    private void dispatch(CounterType counterType,String message){
        CounterHandler handler = handlerMap.get(counterType);
        if(handler!=null){
            handler.handle(message);
        }
    }

}



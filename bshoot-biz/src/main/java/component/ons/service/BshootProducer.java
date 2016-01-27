package component.ons.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.*;
import jb.pageModel.Bshoot;

import java.util.Properties;

/**
 * Created by john on 16/1/27.
 */
public class BshootProducer {
    private Producer producer;
    private BshootProducer(){

    }
    private static BshootProducer instance;
    private void init(){
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, "PID_bshoot_add");
        properties.put(PropertyKeyConst.AccessKey, "K1vxSxnLWUXylCVo");
        properties.put(PropertyKeyConst.SecretKey, "5LPjklci4nkffZLos28KRnnvic0XVi");
        producer = ONSFactory.createProducer(properties);

        //在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        producer.start();


        // 在应用退出前，销毁Producer对象
        // 注意：如果不销毁也没有问题
        //producer.shutdown();
    }

    public void send(Bshoot bshoot) {
        String json = JSON.toJSONString(bshoot);
        Message msg = new Message(
                //Message Topic
                "bshoot_add_bshoot",
                //Message Tag,
                //可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
                "TagA",
                //Message Body
                //任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
                json.getBytes()
        );

        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        // 以方便您在无法正常收到消息情况下，可通过ONS Console查询消息并补发。
        // 注意：不设置也不会影响消息正常收发
        msg.setKey(bshoot.getId());
        try {
            //发送消息，只要不抛异常就是成功
            SendResult sendResult = producer.send(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static BshootProducer getInstance(){
        if(instance == null){
            synchronized (BshootProducer.class){
                if(instance == null){
                    instance = new BshootProducer();
                    instance.init();
                }
            }
        }
        return instance;
    }
}

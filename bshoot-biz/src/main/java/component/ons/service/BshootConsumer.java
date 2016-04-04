package component.ons.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.Message;
import jb.pageModel.*;
import jb.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by john on 16/1/27.
 */
public class BshootConsumer {

    @Autowired
    private UserAttentionServiceI userAttentionService;

    @Autowired
    private UserFriendTimeServiceI userFriendTimeService;

    @Autowired
    private UserPersonServiceI userPersonService;

    @Autowired
    private UserPersonTimeServiceI userPersonTimeService;

    @Autowired
    private UserMobilePersonServiceI userMobilePersonService;

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
        //插入朋友圈时间线表
        UserAttention userAttention = new UserAttention();
        userAttention.setAttUserId(bshoot.getUserId());
        PageHelper ph = new PageHelper();
        DataGrid dg = userAttentionService.dataGridUser(userAttention, ph);
        List<User> ls = dg.getRows();//谁关注了我
        if(ls != null){
            for(User user : ls){
                UserFriendTime userFriendTime = new UserFriendTime();
                userFriendTime.setUserId(user.getId());
                userFriendTime.setBsId(bshoot.getId());
                userFriendTime.setIsDelete(0);
                userFriendTime.setCreateDatetime(new Date());
                UserAttention ua = new UserAttention();
                ua.setUserId(bshoot.getUserId());
                ua.setAttUserId(user.getId());
                int r = userAttentionService.isAttention(ua);//我是否也关注了对方，判定是单关注还是好友
                if(r == -1){
                    userFriendTime.setFriendType(0);//单向关注
                }else{
                    userFriendTime.setFriendType(1);//双向好友
                }
                userFriendTimeService.add(userFriendTime);
            }
        }
        //插入人脉圈时间线表
        List<User> lam = new ArrayList<User>();//加了我手机通讯录的但没关注我的用户，用户手机通讯录上的人加了我进手机通讯录
        UserPerson userPerson = new UserPerson();
        userPerson.setAttUserId(bshoot.getUserId());
        DataGrid dp = userPersonService.dataGridMyUserPerson(userPerson, ph);//谁加了我人脉圈好友
        List<User> lp = dp.getRows();
        if(lp != null){
            for(User user : lp){
                UserAttention ua = new UserAttention();
                ua.setUserId(user.getId());
                DataGrid da = userAttentionService.dataGridMyFriend(ua, ph);
                List<User> lla = da.getRows();
                if(lla != null){
                    for(User us : lla){
                        UserPersonTime userPersonTime = new UserPersonTime();
                        userPersonTime.setUserId(us.getId());
                        userPersonTime.setBsId(bshoot.getId());
                        userPersonTime.setIsDelete(0);
                        userPersonTime.setCreateDatetime(new Date());
                        userPersonTime.setPersonType(0);//0软件通讯录好友
                        userPersonTimeService.add(userPersonTime);
                    }
                }
            }
        }
        UserMobilePerson userMobilePerson = new UserMobilePerson();
        userMobilePerson.setFriendId(bshoot.getUserId());
        DataGrid dm = userMobilePersonService.dataGridFriendMobilePerson(userMobilePerson, ph);//加我进手机通讯录但没关注我的
        List<User> lm = dm.getRows();
        if(lm != null){
            for(User user : lm){
                lam.add(user);
            }
        }
        UserMobilePerson uu = new UserMobilePerson();
        uu.setFriendId(bshoot.getUserId());
        DataGrid du = userMobilePersonService.dataGridFromMobilePerson(uu, ph);//用户手机通讯录上的人加了我进手机通讯录
        List<User> lu = du.getRows();
        if(lu != null){
            for(User user : lu){
                lam.add(user);
            }
        }
        if(lam != null){
            for(User us : lam){
                UserPersonTime userPersonTime = new UserPersonTime();
                userPersonTime.setUserId(us.getId());
                userPersonTime.setBsId(bshoot.getId());
                userPersonTime.setIsDelete(0);
                userPersonTime.setCreateDatetime(new Date());
                userPersonTime.setPersonType(1);//1手机通讯录
                userPersonTimeService.add(userPersonTime);
            }
        }
    }
}



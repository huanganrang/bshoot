package component.implus.util;

import com.cloopen.rest.sdk.CCPRestSDK;
import component.implus.model.CallAuthen;
import component.implus.model.CallEstablish;
import component.implus.model.CallHangup;
import org.dom4j.Element;

import java.util.HashMap;

/**
 * Created by guxin on 2016/2/14.
 */
public class ImPlusUtil {

    public static String REST_URL = "sandboxapp.cloopen.com";//服务器地址
    public static String REST_PORT = "8883";//服务器端口
    public static String ACCOUNT_SID = "ff8080813d8ab3ed013e030006f80471";//主账号
    public static String AUTH_TOKEN = "86465df545e34922afaa802a9197543b";//主帐号TOKEN
    public static String APPID = "aaf98f8952305ced015239c938f61a18";//应用ID

    /** 解析呼叫鉴权
     * @param e Element
     * @return result
     */
    public String parseCallAuth(Element e) {
        CallAuthen call = new CallAuthen();
        call.setType(e.elementTextTrim("type"));
        call.setOrderId(e.elementTextTrim("orderid"));
        call.setSubId(e.elementTextTrim("subid"));
        call.setCaller(e.elementTextTrim("caller"));
        call.setCalled(e.elementTextTrim("called"));
        call.setCallSid(e.elementTextTrim("callSid"));
        //请在此处增加逻辑判断代码

        //返回的数据,如果需要控制呼叫时长需要增加sessiontime
        String result = "<?xml version='1.0' encoding='UTF-8' ?><Response><statuscode>0000</statuscode><statusmsg>Success</statusmsg><record>1</record></Response>";

        return result;
    }

    /** 解析摘机请求
     * @param e Element
     * @return result
     */
    public String parseCallEstablish(Element e) {
        CallEstablish call = new CallEstablish();
        call.setType(e.elementTextTrim("type"));
        call.setOrderId(e.elementTextTrim("orderid"));
        call.setSubId(e.elementTextTrim("subid"));
        call.setCaller(e.elementTextTrim("caller"));
        call.setCalled(e.elementTextTrim("called"));
        call.setCallSid(e.elementTextTrim("callSid"));
        //请在此处增加逻辑判断代码

        //返回的数据,如果需要控制呼叫时长需要增加sessiontime
        String result = "<?xml version='1.0' encoding='UTF-8' ?><Response><statuscode>0000</statuscode><statusmsg>Success</statusmsg><billdata>ok</billdata></Response>";

        return result;
    }

    /** 解析挂断请求
     * @param e Element
     * @return result
     */
    public String parseHangup(Element e) {
        CallHangup call = new CallHangup();
        call.setType(e.elementTextTrim("type"));
        call.setOrderId(e.elementTextTrim("orderid"));
        call.setSubId(e.elementTextTrim("subid"));
        call.setCaller(e.elementTextTrim("caller"));
        call.setCalled(e.elementTextTrim("called"));
        call.setByeType(e.elementTextTrim("byeType"));
        call.setStarttime(e.elementTextTrim("starttime"));
        call.setEndtime(e.elementTextTrim("endtime"));
        call.setBilldata(e.elementTextTrim("billdata"));
        call.setCallSid(e.elementTextTrim("callSid"));
        call.setRecordurl(e.elementTextTrim("recordurl"));
        //请在此处增加逻辑判断代码

        //返回的数据
        String result = "<?xml version='1.0' encoding='UTF-8'?><Response><statuscode>0000</statuscode><statusmsg>Success</statusmsg><totalfee>0.120000</totalfee></Response>";

        return result;
    }

    /** 初始化
     * @return result
     */
    public CCPRestSDK init(){
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init(REST_URL, REST_PORT);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        return restAPI;
    }

    /**
     * 获取主帐号信息查询
     *
     * @return
     */
    public HashMap<String, Object> queryAccountInfo(){

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        result = restAPI.queryAccountInfo();

        return result;
    }

    /**
     * 创建子帐号
     *
     * @param friendlyName
     *            必选参数 子帐号名称。可由英文字母和阿拉伯数字组成子帐号唯一名称，推荐使用电子邮箱地址
     * @return
     */
    public HashMap<String, Object> createSubAccount(String friendlyName){

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.createSubAccount(friendlyName);//此处用用户ID替代

        return result;
    }

    /**
     * 获取子帐号
     *
     * @param startNo
     *            可选参数 开始的序号，默认从0开始
     * @param offset
     *            可选参数 一次查询的最大条数，最小是1条，最大是100条
     * @return
     */
    public HashMap<String, Object> getSubAccounts(String startNo, String offset){

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.getSubAccounts(startNo, offset);

        return result;
    }

    /**
     * 获取子帐号信息
     *
     * @param friendlyName
     *            必选参数 子帐号名称
     * @return
     */
    public HashMap<String, Object> querySubAccount(String friendlyName){

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.querySubAccount(friendlyName);//此处用用户名替代

        return result;
    }

    /**
     * 发送短信模板请求
     *
     * @param to
     *            必选参数 短信接收端手机号码集合，用英文逗号分开，每批发送的手机号数量不得超过100个
     * @param templateId
     *            必选参数 模板Id
     * @param datas
     *            可选参数 内容数据，用于替换模板中{序号}
     * @return
     */
    public HashMap<String, Object> sendTemplateSMS(String to, String templateId, String[] datas){

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.sendTemplateSMS(to, templateId , datas);

        return result;
    }

    /**
     * 短信模板查询
     *
     * @param templateId
     *            可选参数 模板Id，不带此参数查询全部可用模板
     * @return
     */
    public HashMap<String, Object> QuerySMSTemplate(String templateId){

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.QuerySMSTemplate(templateId);

        return result;
    }

    /**
     * 发送双向回拨请求
     *
     * @param subAccountSid
     *            必选参数 子帐号
     * @param subToken
     *            必选参数 子帐号TOKEN
     * @param from
     *            必选参数 主叫电话号码
     * @param to
     *            必选参数 被叫电话号码
     * @param customerSerNum
     *            可选参数 被叫侧显示的客服号码，根据平台侧显号规则控制
     * @param fromSerNum
     *            可选参数 主叫侧显示的号码，根据平台侧显号规则控制
     * @param promptTone
     *            可选参数 第三方自定义回拨提示音
     * @param userData
     *            可选参数 第三方私有数据
     * @param maxCallTime
     *            可选参数 最大通话时长
     * @param hangupCdrUrl
     *            可选参数 实时话单通知地址
     * @param alwaysPlay
     *            可选参数 是否一直播放提示音
     * @param terminalDtmf
     *            可选参数 用于终止播放提示音的按键
     * @param needBothCdr
     *            可选参数 是否给主被叫发送话单
     * @param needRecord
     *            可选参数 是否录音
     * @param countDownTime
     *            可选参数 设置倒计时时间
     * @param countDownPrompt
     *            可选参数 到达倒计时时间播放的提示音
     * @return
     */
    public HashMap<String, Object> callback(String subAccountSid, String subToken, String from, String to,
                                            String customerSerNum, String fromSerNum, String promptTone,
                                            String alwaysPlay, String terminalDtmf, String userData,
                                            String maxCallTime, String hangupCdrUrl, String needBothCdr,
                                            String needRecord, String countDownTime, String countDownPrompt){

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setSubAccount(subAccountSid, subToken);//初始化子帐号和子帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.callback(from, to, customerSerNum , fromSerNum, promptTone, alwaysPlay, terminalDtmf, userData, maxCallTime, hangupCdrUrl, needBothCdr, needRecord, countDownTime, countDownPrompt);

        return result;
    }

    /**
     * 取消回拨
     *
     * @param subAccountSid
     *            必选参数 子帐号
     * @param subToken
     *            必选参数 子帐号TOKEN
     * @param callSid
     *            必选参数 一个由32个字符组成的电话唯一标识符
     * @param type
     *            可选参数 0： 任意时间都可以挂断电话；1 ：被叫应答前可以挂断电话，其他时段返回错误代码；2： 主叫应答前可以挂断电话，其他时段返回错误代码；默认值为0。
     * @return
     */
    public HashMap<String, Object> CallCancel(String subAccountSid, String subToken, String callSid, String type){

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setSubAccount(subAccountSid, subToken);//初始化子帐号和子帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.CallCancel(callSid, type);

        return result;
    }

    /**
     * 发送外呼通知请求
     *
     * @param to
     *            必选参数 被叫号码
     * @param mediaName
     *            可选参数 语音文件名称，格式 wav。与mediaTxt不能同时为空，不为空时mediaTxt属性失效
     * @param mediaTxt
     *            可选参数 文本内容，默认值为空
     * @param displayNum
     *            可选参数 显示的主叫号码，显示权限由服务侧控制
     * @param playTimes
     *            可选参数 循环播放次数，1－3次，默认播放1次
     * @param type
     *            可选参数 : 如果传入type=1，则播放默认语音文件
     * @param respUrl
     *            可选参数 外呼通知状态通知回调地址，云通讯平台将向该Url地址发送呼叫结果通知
     * @param userData
     *            可选参数 用户私有数据
     * @param maxCallTime
     *            可选参数 最大通话时长
     * @param speed
     *            可选参数 发音速度
     * @param volume
     *            可选参数 音量
     * @param pitch
     *            可选参数 音调
     * @param bgsound
     *            可选参数 背景音编号
     * @return
     */
    public HashMap<String, Object> landingCall(String to, String mediaName,
                                               String mediaTxt, String displayNum, String playTimes, int type,
                                               String respUrl,String userData, String maxCallTime, String speed,
                                               String volume, String pitch, String bgsound) {
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.landingCall(to, mediaName, mediaTxt, displayNum, playTimes, type, respUrl, userData, maxCallTime, speed, volume, pitch, bgsound);

        return result;
    }

    /**
     * 发起语音验证码请求
     *
     * @param verifyCode
     *            必选参数 验证码内容，为数字和英文字母，不区分大小写，长度4-8位
     * @param to
     *            必选参数 接收号码
     * @param displayNum
     *            可选参数 显示主叫号码，显示权限由服务侧控制
     * @param playTimes
     *            可选参数 循环播放次数，1－3次，默认播放1次
     * @param respUrl
     *            可选参数 语音验证码状态通知回调地址，云通讯平台将向该Url地址发送呼叫结果通知
     * @param lang
     *            可选参数 语言类型
     * @param userData
     *            可选参数 第三方私有数据
     * @return
     */
    public HashMap<String, Object> voiceVerify(String verifyCode, String to,
                                               String displayNum, String playTimes, String respUrl, String lang,
                                               String userData) {
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.voiceVerify(verifyCode, to, displayNum, playTimes, respUrl, lang, userData);

        return result;
    }

    /**
     * 呼叫状态查询
     *
     * @param callid
     *            必选参数 呼叫Id
     * @param action
     *            可选参数 查询结果通知的回调url地址
     * @return
     */
    public HashMap<String, Object> QueryCallState(String callid, String action) {
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.QueryCallState(callid, action);

        return result;
    }

    /**
     * 呼叫结果查询
     *
     * @param callSid
     *            必选参数 呼叫Id
     * @return
     */
    public HashMap<String, Object> CallResult(String callSid) {
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = init();
        restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);//初始化主帐号和主帐号TOKEN
        restAPI.setAppId(APPID);//初始化应用ID
        result = restAPI.CallResult(callSid);

        return result;
    }

}

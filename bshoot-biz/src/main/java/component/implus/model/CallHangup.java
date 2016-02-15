/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.cloopen.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package component.implus.model;
/**
 * <p>
 * Title: CallHangup
 * </p>
 * <p>
 * Description: 鉴权响应实体(挂断)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: hisunsray
 * </p>
 * <p>
 * Date: 2013-07-09
 * </p>
 * 
 * @version 1.0
 */
public class CallHangup {

	//呼叫类型: 0:VoIP直拨落地电话；1:双向回拨；2 :VoIP网络通话
	private String type;
	//订单id
	private String orderId;
	//子账号id: type取值为1时，即双向回拨时提供
	private String subId;
	//主叫号码: type取值为0或2时，主叫号码为VoIP帐号；type取值为1时，主叫号码为电话号码
	private String caller;
	//被叫号码: type取值为0时，被叫号码为电话号码；type取值为1时，被叫号码为电话号码；type取值为2时，被叫号码为VoIP帐号
	private String called;
	//通话挂机类型
	private String byeType;
	//通话开始时间,当type为1 也就是回拨时，如果被叫未接听，则为主叫摘机时间；如果被叫接听，则为被叫摘机时间
	private String starttime;
	//通话结束时间
	private String endtime;
	//呼叫的计费私有数据
	private String billdata;
	//一路呼叫的唯一标示:32位字符串
	private String callSid;
	//通话录音完整下载地址
	private String recordurl;
	
	public String getRecordurl() {
		return recordurl;
	}

	public void setRecordurl(String recordurl) {
		this.recordurl = recordurl;
	}

	public String getCallSid() {
		return callSid;
	}

	public void setCallSid(String callSid) {
		this.callSid = callSid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCalled() {
		return called;
	}

	public void setCalled(String called) {
		this.called = called;
	}

	public String getByeType() {
		return byeType;
	}

	public void setByeType(String byeType) {
		this.byeType = byeType;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getBilldata() {
		return billdata;
	}

	public void setBilldata(String billdata) {
		this.billdata = billdata;
	}

	@Override
	public String toString(){
		return "CallHangup [type = "+type+", orderId = "+type+", subId = "+subId+", caller = "+caller+", called = "+called+", byeType = "+byeType+", starttime = "+starttime+", endtime = "+endtime+", billdata = "+billdata+", callSid = "+callSid+", recordurl = "+recordurl+"]";
	}
}

package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class UserProfile implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String memberV;	
	private java.lang.Integer attNum;	
	private java.lang.Integer fansNum;	
	private java.lang.Integer praiseNum;
	private java.lang.String monthPraise;
	private java.lang.String loginArea;
	private Date lastLoginTime;
	private Date lastPubTime;
	private Date createDatetime;			
	private Date updateDatetime;			

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setMemberV(java.lang.String memberV) {
		this.memberV = memberV;
	}
	
	public java.lang.String getMemberV() {
		return this.memberV;
	}
	public void setAttNum(java.lang.Integer attNum) {
		this.attNum = attNum;
	}
	
	public java.lang.Integer getAttNum() {
		return this.attNum;
	}
	public void setFansNum(java.lang.Integer fansNum) {
		this.fansNum = fansNum;
	}
	
	public java.lang.Integer getFansNum() {
		return this.fansNum;
	}
	public void setPraiseNum(java.lang.Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
	
	public java.lang.Integer getPraiseNum() {
		return this.praiseNum;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	
	public Date getCreateDatetime() {
		return this.createDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	
	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}

	public String getMonthPraise() {
		return monthPraise;
	}

	public void setMonthPraise(String monthPraise) {
		this.monthPraise = monthPraise;
	}

	public String getLoginArea() {
		return loginArea;
	}

	public void setLoginArea(String loginArea) {
		this.loginArea = loginArea;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastPubTime() {
		return lastPubTime;
	}

	public void setLastPubTime(Date lastPubTime) {
		this.lastPubTime = lastPubTime;
	}
}

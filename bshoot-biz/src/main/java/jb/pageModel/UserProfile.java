package jb.pageModel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

@SuppressWarnings("serial")
@ApiModel("UserProfile")
public class UserProfile implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	@ApiModelProperty("id")
	private java.lang.String id;
	@ApiModelProperty("大/小v")
	private java.lang.String memberV;
	@ApiModelProperty("关注数")
	private java.lang.Integer attNum;
	@ApiModelProperty("粉丝数")
	private java.lang.Integer fansNum;
	@ApiModelProperty("打赏数")
	private java.lang.Integer praiseNum;
	@ApiModelProperty("月打赏数")
	private java.lang.Integer monthPraise;
	@ApiModelProperty("登录区域")
	private java.lang.String loginArea;
	@ApiModelProperty("最近登录时间")
	private Date lastLoginTime;
	@ApiModelProperty("最近发布时间")
	private Date lastPubTime;
	@ApiModelProperty("创建时间")
	private Date createDatetime;
	@ApiModelProperty("更新时间")
	private Date updateDatetime;
	@ApiModelProperty("登录经度")
	private java.lang.String lgX;
	@ApiModelProperty("登录纬度")
	private java.lang.String lgY;

	

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

	public Integer getMonthPraise() {
		return monthPraise;
	}

	public void setMonthPraise(Integer monthPraise) {
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

	public String getLgY() {
		return lgY;
	}

	public void setLgY(String lgY) {
		this.lgY = lgY;
	}

	public String getLgX() {
		return lgX;
	}

	public void setLgX(String lgX) {
		this.lgX = lgX;
	}
}

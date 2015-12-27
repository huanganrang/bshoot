package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class UserPraiseRecord implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String userId;	
	private java.lang.String relationOutid;	
	private java.lang.String relationChannel;	
	private java.lang.Integer amount;	
	private java.lang.String praiseType;	
	private java.lang.Integer praiseNum;	
	private Date createDatetime;			
	private Date updateDatetime;			

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setRelationOutid(java.lang.String relationOutid) {
		this.relationOutid = relationOutid;
	}
	
	public java.lang.String getRelationOutid() {
		return this.relationOutid;
	}
	public void setRelationChannel(java.lang.String relationChannel) {
		this.relationChannel = relationChannel;
	}
	
	public java.lang.String getRelationChannel() {
		return this.relationChannel;
	}
	public void setAmount(java.lang.Integer amount) {
		this.amount = amount;
	}
	
	public java.lang.Integer getAmount() {
		return this.amount;
	}
	public void setPraiseType(java.lang.String praiseType) {
		this.praiseType = praiseType;
	}
	
	public java.lang.String getPraiseType() {
		return this.praiseType;
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

}

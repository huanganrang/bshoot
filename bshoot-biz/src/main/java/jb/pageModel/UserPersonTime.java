package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class UserPersonTime implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String userId;	
	private java.lang.String bsId;	
	private java.lang.Integer isRead;	
	private java.lang.Integer isPraise;	
	private java.lang.Integer isDelete;
	private java.lang.Integer personType;
	private Date createDatetime;
	private Bshoot bshoot;
	

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
	public void setBsId(java.lang.String bsId) {
		this.bsId = bsId;
	}
	
	public java.lang.String getBsId() {
		return this.bsId;
	}
	public void setIsRead(java.lang.Integer isRead) {
		this.isRead = isRead;
	}
	
	public java.lang.Integer getIsRead() {
		return this.isRead;
	}
	public void setIsPraise(java.lang.Integer isPraise) {
		this.isPraise = isPraise;
	}
	
	public java.lang.Integer getIsPraise() {
		return this.isPraise;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	
	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public Bshoot getBshoot() {
		return bshoot;
	}

	public void setBshoot(Bshoot bshoot) {
		this.bshoot = bshoot;
	}
}

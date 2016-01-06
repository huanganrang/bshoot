package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class UserPerson implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String userId;	
	private java.lang.String attUserId;
	private String personGroup;
	private java.lang.Integer isDelete;	
	private Date createDatetime;			

	

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
	public void setAttUserId(java.lang.String attUserId) {
		this.attUserId = attUserId;
	}

	public String getPersonGroup() {
		return personGroup;
	}

	public void setPersonGroup(String personGroup) {
		this.personGroup = personGroup;
	}

	public java.lang.String getAttUserId() {
		return this.attUserId;
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

}

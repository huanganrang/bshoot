package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class Message implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String mtype;
	private String userId;
	private Boolean isRead;
	private String relationId;
	private String content;
	private Date createdatetime;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getMtype() {
		return this.mtype;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Boolean getIsRead() {
		return this.isRead;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getRelationId() {
		return this.relationId;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	
	public Date getCreatedatetime() {
		return this.createdatetime;
	}

}

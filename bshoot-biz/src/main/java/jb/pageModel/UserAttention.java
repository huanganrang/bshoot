package jb.pageModel;

import java.util.Date;

public class UserAttention implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String userId;
	private String attUserId;
	private Date attentionDatetime;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setAttUserId(String attUserId) {
		this.attUserId = attUserId;
	}

	public String getAttUserId() {
		return this.attUserId;
	}
	public void setAttentionDatetime(Date attentionDatetime) {
		this.attentionDatetime = attentionDatetime;
	}
	
	public Date getAttentionDatetime() {
		return this.attentionDatetime;
	}

}

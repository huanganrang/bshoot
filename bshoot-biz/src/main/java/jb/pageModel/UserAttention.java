package jb.pageModel;

import java.util.Date;

public class UserAttention implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String userId;
	private String attUserId;
	private String attentionGroup;
	private Date attentionDatetime;
	private Integer isDelete;
	private Integer isFriend;

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

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

	public String getAttentionGroup() {
		return attentionGroup;
	}

	public void setAttentionGroup(String attentionGroup) {
		this.attentionGroup = attentionGroup;
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

	public Integer getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(Integer isFriend) {
		this.isFriend = isFriend;
	}
}

package jb.pageModel;

import java.util.Date;

import jb.listener.Application;

public class BshootSkill implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String title;	
	private java.lang.String description;	
	private java.lang.String type;	
	private java.lang.Boolean hotStatus;	
	private java.lang.Float hot;	
	private Date createTime;			

	public String getTypeZh() {
		return Application.getString(this.type);
	}

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	
	public java.lang.String getType() {
		return this.type;
	}
	public void setHotStatus(java.lang.Boolean hotStatus) {
		this.hotStatus = hotStatus;
	}
	
	public java.lang.Boolean getHotStatus() {
		return this.hotStatus;
	}
	public void setHot(java.lang.Float hot) {
		this.hot = hot;
	}
	
	public java.lang.Float getHot() {
		return this.hot;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}

}

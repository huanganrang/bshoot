package jb.pageModel;

import jb.listener.BaseApplication;

import java.util.Date;

public class BshootSkill implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String title;
	private String description;
	private String type;
	private Boolean hotStatus;
	private Float hot;
	private Date createTime;

	public String getTypeZh() {
		return BaseApplication.getString(this.type);
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
	public void setHotStatus(Boolean hotStatus) {
		this.hotStatus = hotStatus;
	}

	public Boolean getHotStatus() {
		return this.hotStatus;
	}
	public void setHot(Float hot) {
		this.hot = hot;
	}

	public Float getHot() {
		return this.hot;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}

}

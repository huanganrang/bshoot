package jb.pageModel;

import jb.util.PathUtil;

import java.util.Date;

public class BshootSquare implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String bssName;
	private String bssDescription;
	private String bssIcon;
	private String bssUserId;
	private Date createDatetime;
	private Date updateDatetime;
	private String createPerson;
	private String updatePerson;
	private String bssType;
	private String bssIconAbsolute;


	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setBssName(String bssName) {
		this.bssName = bssName;
	}

	public String getBssName() {
		return this.bssName;
	}
	public void setBssDescription(String bssDescription) {
		this.bssDescription = bssDescription;
	}

	public String getBssDescription() {
		return this.bssDescription;
	}
	public void setBssIcon(String bssIcon) {
		this.bssIcon = bssIcon;
	}

	public String getBssIcon() {
		return this.bssIcon;
	}
	public void setBssUserId(String bssUserId) {
		this.bssUserId = bssUserId;
	}

	public String getBssUserId() {
		return this.bssUserId;
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
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getCreatePerson() {
		return this.createPerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getUpdatePerson() {
		return this.updatePerson;
	}

	public String getBssType() {
		return bssType;
	}

	public void setBssType(String bssType) {
		this.bssType = bssType;
	}

	public String getBssIconAbsolute() {
		return PathUtil.getBshootSquarePath(bssIcon);
	}	
	
	
	
}

package jb.pageModel;


public class UserMessage implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String userId;
	private String umType;
	private String umMessage;
	private String umRemark;
	private String createDatetime;
	private String updateDatetime;
	private String createPerson;
	private String updatePerson;



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
	public void setUmType(String umType) {
		this.umType = umType;
	}

	public String getUmType() {
		return this.umType;
	}
	public void setUmMessage(String umMessage) {
		this.umMessage = umMessage;
	}

	public String getUmMessage() {
		return this.umMessage;
	}
	public void setUmRemark(String umRemark) {
		this.umRemark = umRemark;
	}

	public String getUmRemark() {
		return this.umRemark;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getCreateDatetime() {
		return this.createDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateDatetime() {
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

}

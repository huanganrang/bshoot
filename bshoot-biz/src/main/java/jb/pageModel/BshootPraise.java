package jb.pageModel;

import java.util.Date;

public class BshootPraise implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String userId;
	private String bshootId;
	private Date praiseDatetime;

	private Integer praiseNum;


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
	public void setBshootId(String bshootId) {
		this.bshootId = bshootId;
	}

	public String getBshootId() {
		return this.bshootId;
	}

	public Date getPraiseDatetime() {
		return praiseDatetime;
	}

	public void setPraiseDatetime(Date praiseDatetime) {
		this.praiseDatetime = praiseDatetime;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
}

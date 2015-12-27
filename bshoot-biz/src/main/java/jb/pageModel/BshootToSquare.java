package jb.pageModel;

import java.util.Date;

public class BshootToSquare implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String bshootId;
	private String squareId;
	private String auditorId;
	private Date auditorTime;
	private String reason;
	private String status;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setBshootId(String bshootId) {
		this.bshootId = bshootId;
	}

	public String getBshootId() {
		return this.bshootId;
	}
	public void setSquareId(String squareId) {
		this.squareId = squareId;
	}

	public String getSquareId() {
		return this.squareId;
	}
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorId() {
		return this.auditorId;
	}
	public void setAuditorTime(Date auditorTime) {
		this.auditorTime = auditorTime;
	}

	public Date getAuditorTime() {
		return this.auditorTime;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return this.reason;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

}

package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DiveCertificateAuthority implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String orgCode;
	private String levelCode;
	private Date authDate;
	private String reverseSide;
	private String rightSide;
	private Date auditDate;
	private String status;
	private Date addtime;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgCode() {
		return this.orgCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelCode() {
		return this.levelCode;
	}
	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public Date getAuthDate() {
		return this.authDate;
	}
	public void setReverseSide(String reverseSide) {
		this.reverseSide = reverseSide;
	}

	public String getReverseSide() {
		return this.reverseSide;
	}
	public void setRightSide(String rightSide) {
		this.rightSide = rightSide;
	}

	public String getRightSide() {
		return this.rightSide;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}

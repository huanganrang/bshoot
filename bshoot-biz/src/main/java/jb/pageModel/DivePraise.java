package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DivePraise implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String businessId;
	private String businessType;
	private Date addtime;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessId() {
		return this.businessId;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessType() {
		return this.businessType;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}

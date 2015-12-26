package jb.pageModel;


public class MessageCount implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String mtype;
	private String userId;
	private Integer mnumber;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getMtype() {
		return this.mtype;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setMnumber(Integer mnumber) {
		this.mnumber = mnumber;
	}

	public Integer getMnumber() {
		return this.mnumber;
	}

}

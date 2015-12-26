package jb.pageModel;

@SuppressWarnings("serial")
public class BshootUserRel implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String bshootId;
	private String userId;



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
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

}

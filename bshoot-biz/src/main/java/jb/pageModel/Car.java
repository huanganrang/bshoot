package jb.pageModel;


public class Car implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String name;
	private String code;
	private String userName;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

}

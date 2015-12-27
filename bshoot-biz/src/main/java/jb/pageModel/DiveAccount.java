package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DiveAccount implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String userName;
	private String password;
	private String icon;
	private String nickname;
	private String sex;
	private String city;
	private String personality;
	private String email;
	private Date addtime;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return this.nickname;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return this.sex;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return this.city;
	}
	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public String getPersonality() {
		return this.personality;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}

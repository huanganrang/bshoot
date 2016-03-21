package jb.pageModel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import jb.listener.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@ApiModel("UserHobby")
public class UserHobby implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	@ApiModelProperty("id")
	private java.lang.String id;
	@ApiModelProperty("用户id")
	private java.lang.String userId;
	@ApiModelProperty("兴趣类型")
	private java.lang.String hobbyType;
	@ApiModelProperty("创建时间")
	private Date createDatetime;
	@ApiModelProperty("修改时间")
	private Date updateDatetime;			

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setHobbyType(java.lang.String hobbyType) {
		this.hobbyType = hobbyType;
	}
	
	public java.lang.String getHobbyType() {
		return this.hobbyType;
	}
	public List<String> getHobbyTypeName() {
		String[] hobby = this.hobbyType.split(",");
		List<String> hobbyName = new ArrayList<>();
		String temp = null;
		for(String str:hobby){
			temp = Application.getString(str);
			if(null!=temp)
			hobbyName.add(temp);
		}
		return hobbyName;
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

}

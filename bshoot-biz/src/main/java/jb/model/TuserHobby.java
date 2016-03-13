
/*
 * @author John
 * @date - 2015-12-27
 */

package jb.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_hobby")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TuserHobby implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserHobby";
	public static final String ALIAS_ID = "主键/用户ID";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_HOBBY_TYPE = "兴趣类型";
	public static final String ALIAS_CREATE_DATETIME = "创建时间";
	public static final String ALIAS_UPDATE_DATETIME = "修改时间";
	
	//date formats
	public static final String FORMAT_CREATE_DATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATE_DATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String userId;
	//@Length(max=4)
	private java.lang.String hobbyType;
	//
	private java.util.Date createDatetime;
	//
	private java.util.Date updateDatetime;
	private java.lang.Integer isDelete;

	//columns END


		public TuserHobby(){
		}
		public TuserHobby(String id) {
			this.id = id;
		}
	

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	@Column(name = "hobby_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getHobbyType() {
		return this.hobbyType;
	}
	
	public void setHobbyType(java.lang.String hobbyType) {
		this.hobbyType = hobbyType;
	}
	

	@Column(name = "create_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getCreateDatetime() {
		return this.createDatetime;
	}
	
	public void setCreateDatetime(java.util.Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	

	@Column(name = "update_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getUpdateDatetime() {
		return this.updateDatetime;
	}
	
	public void setUpdateDatetime(java.util.Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	@Column(name = "is_delete", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}


	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("HobbyType",getHobbyType())
			.append("CreateDatetime",getCreateDatetime())
			.append("UpdateDatetime",getUpdateDatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserHobby == false) return false;
		if(this == obj) return true;
		UserHobby other = (UserHobby)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


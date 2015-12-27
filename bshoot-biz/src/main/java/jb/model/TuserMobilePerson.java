
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
@Table(name = "user_mobile_person")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TuserMobilePerson implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserMobilePerson";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_FRIEND_NAME = "好友名称";
	public static final String ALIAS_MOBILE = "手机号多个";
	public static final String ALIAS_IS_DELETE = "是否删除";
	public static final String ALIAS_CREATE_DATETIME = "创建时间";
	
	//date formats
	public static final String FORMAT_CREATE_DATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=36)
	private java.lang.String userId;
	//@Length(max=36)
	private java.lang.String friendName;
	//@Length(max=72)
	private java.lang.String mobile;
	//
	private java.lang.Integer isDelete;
	//
	private java.util.Date createDatetime;
	//columns END


		public TuserMobilePerson(){
		}
		public TuserMobilePerson(String id) {
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
	
	@Column(name = "friend_name", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getFriendName() {
		return this.friendName;
	}
	
	public void setFriendName(java.lang.String friendName) {
		this.friendName = friendName;
	}
	
	@Column(name = "mobile", unique = false, nullable = true, insertable = true, updatable = true, length = 72)
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "is_delete", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	

	@Column(name = "create_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getCreateDatetime() {
		return this.createDatetime;
	}
	
	public void setCreateDatetime(java.util.Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("FriendName",getFriendName())
			.append("Mobile",getMobile())
			.append("IsDelete",getIsDelete())
			.append("CreateDatetime",getCreateDatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserMobilePerson == false) return false;
		if(this == obj) return true;
		UserMobilePerson other = (UserMobilePerson)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


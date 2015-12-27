
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
@Table(name = "user_person")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TuserPerson implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserPerson";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_ATT_USER_ID = "被关注用户ID";
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
	private java.lang.String attUserId;
	//
	private java.lang.Integer isDelete;
	//
	private java.util.Date createDatetime;
	//columns END


		public TuserPerson(){
		}
		public TuserPerson(String id) {
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
	
	@Column(name = "att_user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getAttUserId() {
		return this.attUserId;
	}
	
	public void setAttUserId(java.lang.String attUserId) {
		this.attUserId = attUserId;
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
			.append("AttUserId",getAttUserId())
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
		if(obj instanceof UserPerson == false) return false;
		if(this == obj) return true;
		UserPerson other = (UserPerson)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


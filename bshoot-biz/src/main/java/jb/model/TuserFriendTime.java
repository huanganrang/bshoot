
/*
 * @author John
 * @date - 2015-12-27
 */

package jb.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_friend_time")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TuserFriendTime implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserFriendTime";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_BS_ID = "资源id";
	public static final String ALIAS_IS_DELETE = "是否删除";
	public static final String ALIAS_IS_READ = "是否已读";
	public static final String ALIAS_FRIEND_TYPE = "0为单向关注1为双向关注";
	public static final String ALIAS_IS_PRAISE = "是否已打赏";
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
	private java.lang.String bsId;
	//
	private java.lang.Integer isDelete;
	//
	private java.lang.Integer isRead;
	//
	private java.lang.Integer isPraise;
	//
	private java.lang.Integer friendType;
	//
	private java.util.Date createDatetime;
	//columns END


		public TuserFriendTime(){
		}
		public TuserFriendTime(String id) {
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
	
	@Column(name = "bs_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getBsId() {
		return this.bsId;
	}
	
	public void setBsId(java.lang.String bsId) {
		this.bsId = bsId;
	}
	
	@Column(name = "is_delete", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	@Column(name = "is_read", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getIsRead() {
		return this.isRead;
	}
	
	public void setIsRead(java.lang.Integer isRead) {
		this.isRead = isRead;
	}
	
	@Column(name = "is_praise", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getIsPraise() {
		return this.isPraise;
	}
	
	public void setIsPraise(java.lang.Integer isPraise) {
		this.isPraise = isPraise;
	}
	

	@Column(name = "create_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getCreateDatetime() {
		return this.createDatetime;
	}
	
	public void setCreateDatetime(java.util.Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name = "friend_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getFriendType() {
		return friendType;
	}

	public void setFriendType(Integer friendType) {
		this.friendType = friendType;
	}

/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("BsId",getBsId())
			.append("IsDelete",getIsDelete())
			.append("IsRead",getIsRead())
			.append("IsPraise",getIsPraise())
			.append("CreateDatetime",getCreateDatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserFriendTime == false) return false;
		if(this == obj) return true;
		UserFriendTime other = (UserFriendTime)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


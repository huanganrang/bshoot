
/*
 * @author John
 * @date - 2015-05-30
 */

package jb.model;

import jb.util.Constants;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "message")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tmessage implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Message";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_MTYPE = "消息类型";
	public static final String ALIAS_USER_ID = "消息所属者";
	public static final String ALIAS_IS_READ = "是否已读";
	public static final String ALIAS_RELATION_ID = "关联业务ID";
	public static final String ALIAS_CONTENT = "消息体";
	public static final String ALIAS_CREATEDATETIME = "消息产生时间";
	
	//date formats
	public static final String FORMAT_CREATEDATETIME = Constants.DATE_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=4)
	private String mtype;
	//@Length(max=36)
	private String userId;
	//
	private Boolean isRead;
	//@Length(max=36)
	private String relationId;
	//@Length(max=256)
	private String content;
	//
	private java.util.Date createdatetime;
	//columns END


		public Tmessage(){
		}
		public Tmessage(String id) {
			this.id = id;
		}


	public void setId(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	@Column(name = "m_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getMtype() {
		return this.mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "is_read", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	@Column(name = "relation_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getRelationId() {
		return this.relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	@Column(name = "createdatetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getCreatedatetime() {
		return this.createdatetime;
	}
	
	public void setCreatedatetime(java.util.Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Mtype",getMtype())
			.append("UserId",getUserId())
			.append("IsRead",getIsRead())
			.append("RelationId",getRelationId())
			.append("Content",getContent())
			.append("Createdatetime",getCreatedatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Message == false) return false;
		if(this == obj) return true;
		Message other = (Message)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


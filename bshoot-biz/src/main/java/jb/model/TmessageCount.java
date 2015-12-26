
/*
 * @author John
 * @date - 2015-05-30
 */

package jb.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "message_count")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TmessageCount implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "MessageCount";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_MTYPE = "消息类型";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_MNUMBER = "消息数量";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=4)
	private String mtype;
	//@Length(max=36)
	private String userId;
	//
	private Integer mnumber;
	//columns END


		public TmessageCount(){
		}
		public TmessageCount(String id) {
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

	@Column(name = "m_number", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getMnumber() {
		return this.mnumber;
	}

	public void setMnumber(Integer mnumber) {
		this.mnumber = mnumber;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Mtype",getMtype())
			.append("UserId",getUserId())
			.append("Mnumber",getMnumber())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MessageCount == false) return false;
		if(this == obj) return true;
		MessageCount other = (MessageCount)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


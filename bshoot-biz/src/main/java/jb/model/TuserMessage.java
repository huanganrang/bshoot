
/*
 * @author John
 * @date - 2015-04-08
 */

package jb.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_message")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TuserMessage implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserMessage";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_UM_TYPE = "消息类型";
	public static final String ALIAS_UM_MESSAGE = "消息";
	public static final String ALIAS_UM_REMARK = "备注";
	public static final String ALIAS_CREATE_DATETIME = "创建时间";
	public static final String ALIAS_UPDATE_DATETIME = "修改时间";
	public static final String ALIAS_CREATE_PERSON = "创建人";
	public static final String ALIAS_UPDATE_PERSON = "修改人";
	
	//date formats
	//public static final String FORMAT_CREATE_DATETIME = DATE_FORMAT;
	//public static final String FORMAT_UPDATE_DATETIME = DATE_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String userId;
	//@Length(max=4)
	private String umType;
	//@Length(max=512)
	private String umMessage;
	//@Length(max=256)
	private String umRemark;
	//
	private Date createDatetime;
	//
	private Date updateDatetime;
	//@Length(max=36)
	private String createPerson;
	//@Length(max=36)
	private String updatePerson;
	//columns END


		public TuserMessage(){
		}
		public TuserMessage(String id) {
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

	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "um_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getUmType() {
		return this.umType;
	}

	public void setUmType(String umType) {
		this.umType = umType;
	}

	@Column(name = "um_message", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getUmMessage() {
		return this.umMessage;
	}

	public void setUmMessage(String umMessage) {
		this.umMessage = umMessage;
	}

	@Column(name = "um_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getUmRemark() {
		return this.umRemark;
	}

	public void setUmRemark(String umRemark) {
		this.umRemark = umRemark;
	}


	@Column(name = "create_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}


	@Column(name = "update_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	@Column(name = "create_person", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getCreatePerson() {
		return this.createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	@Column(name = "update_person", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUpdatePerson() {
		return this.updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("UmType",getUmType())
			.append("UmMessage",getUmMessage())
			.append("UmRemark",getUmRemark())
			.append("CreateDatetime",getCreateDatetime())
			.append("UpdateDatetime",getUpdateDatetime())
			.append("CreatePerson",getCreatePerson())
			.append("UpdatePerson",getUpdatePerson())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserMessage == false) return false;
		if(this == obj) return true;
		UserMessage other = (UserMessage)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


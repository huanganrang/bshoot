
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
@Table(name = "user_profile")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TuserProfile implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "UserProfile";
	public static final String ALIAS_ID = "主键/用户ID";
	public static final String ALIAS_MEMBER_V = "会员级别";
	public static final String ALIAS_ATT_NUM = "关注数";
	public static final String ALIAS_FANS_NUM = "粉丝数";
	public static final String ALIAS_PRAISE_NUM = "打赏数/豆子数（包含所有的）";
	public static final String ALIAS_CREATE_DATETIME = "创建时间";
	public static final String ALIAS_UPDATE_DATETIME = "修改时间";
	
	//date formats
	public static final String FORMAT_CREATE_DATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATE_DATETIME = jb.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private java.lang.String id;
	//@Length(max=4)
	private java.lang.String memberV;
	//
	private java.lang.Integer attNum;
	//
	private java.lang.Integer fansNum;
	//
	private java.lang.Integer praiseNum;
	//
	private java.util.Date createDatetime;
	//
	private java.util.Date updateDatetime;
	//columns END


		public TuserProfile(){
		}
		public TuserProfile(String id) {
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
	
	@Column(name = "member_v", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getMemberV() {
		return this.memberV;
	}
	
	public void setMemberV(java.lang.String memberV) {
		this.memberV = memberV;
	}
	
	@Column(name = "att_num", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getAttNum() {
		return this.attNum;
	}
	
	public void setAttNum(java.lang.Integer attNum) {
		this.attNum = attNum;
	}
	
	@Column(name = "fans_num", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getFansNum() {
		return this.fansNum;
	}
	
	public void setFansNum(java.lang.Integer fansNum) {
		this.fansNum = fansNum;
	}
	
	@Column(name = "praise_num", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getPraiseNum() {
		return this.praiseNum;
	}
	
	public void setPraiseNum(java.lang.Integer praiseNum) {
		this.praiseNum = praiseNum;
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
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("MemberV",getMemberV())
			.append("AttNum",getAttNum())
			.append("FansNum",getFansNum())
			.append("PraiseNum",getPraiseNum())
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
		if(obj instanceof UserProfile == false) return false;
		if(this == obj) return true;
		UserProfile other = (UserProfile)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


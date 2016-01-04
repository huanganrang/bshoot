
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

@Entity
@Table(name = "bshoot_praise")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TbshootPraise implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "BshootPraise";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_BSHOOT_ID = "视频";
	public static final String ALIAS_PRAISE_DATETIME = "赞时间";
	
	//date formats
	//public static final String FORMAT_PRAISE_DATETIME = DATE_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String userId;
	//@Length(max=36)
	private String bshootId;
	//
	private java.util.Date praiseDatetime;

	private Integer praiseNum;
	private String bsUserId;

	//columns END


		public TbshootPraise(){
		}
		public TbshootPraise(String id) {
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

	@Column(name = "bshoot_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getBshootId() {
		return this.bshootId;
	}

	public void setBshootId(String bshootId) {
		this.bshootId = bshootId;
	}
	

	@Column(name = "praise_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getPraiseDatetime() {
		return this.praiseDatetime;
	}
	
	public void setPraiseDatetime(java.util.Date praiseDatetime) {
		this.praiseDatetime = praiseDatetime;
	}
	@Column(name = "is_delete", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public int getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(int praiseNum) {
		this.praiseNum = praiseNum;
	}

	@Column(name = "bs_userId", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getBsUserId() {
		return bsUserId;
	}

	public void setBsUserId(String bsUserId) {
		this.bsUserId = bsUserId;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("BshootId",getBshootId())
			.append("PraiseDatetime",getPraiseDatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BshootPraise == false) return false;
		if(this == obj) return true;
		BshootPraise other = (BshootPraise)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


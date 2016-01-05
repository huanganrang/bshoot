
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
@Table(name = "bshoot_collect")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TbshootCollect implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "BshootCollect";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_BSHOOT_ID = "视频";
	public static final String ALIAS_COLLECT_DATETIME = "收藏时间";
	
	//date formats
	//public static final String FORMAT_COLLECT_DATETIME = DATE_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String userId;
	//@Length(max=36)
	private String bshootId;
	//
	private Date collectDatetime;
	private String bsUserId;
	//columns END


		public TbshootCollect(){
		}
		public TbshootCollect(String id) {
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


	@Column(name = "collect_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCollectDatetime() {
		return this.collectDatetime;
	}

	public void setCollectDatetime(Date collectDatetime) {
		this.collectDatetime = collectDatetime;
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
			.append("CollectDatetime",getCollectDatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BshootCollect == false) return false;
		if(this == obj) return true;
		BshootCollect other = (BshootCollect)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


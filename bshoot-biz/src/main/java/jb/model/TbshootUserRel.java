
/*
 * @author John
 * @date - 2015-08-20
 */

package jb.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bshoot_user_rel")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TbshootUserRel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "BshootUserRel";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_BSHOOT_ID = "视频ID";
	public static final String ALIAS_USER_ID = "关注好友ID";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@NotBlank @Length(max=36)
	private String bshootId;
	//@NotBlank @Length(max=36)
	private String userId;
	//columns END


		public TbshootUserRel(){
		}
		public TbshootUserRel(String id) {
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

	@Column(name = "bshoot_id", unique = false, nullable = false, insertable = true, updatable = true, length = 36)
	public String getBshootId() {
		return this.bshootId;
	}

	public void setBshootId(String bshootId) {
		this.bshootId = bshootId;
	}

	@Column(name = "user_id", unique = false, nullable = false, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("BshootId",getBshootId())
			.append("UserId",getUserId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BshootUserRel == false) return false;
		if(this == obj) return true;
		BshootUserRel other = (BshootUserRel)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


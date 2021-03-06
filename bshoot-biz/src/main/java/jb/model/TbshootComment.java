
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
@Table(name = "bshoot_comment")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TbshootComment implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "BshootComment";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_BSHOOT_ID = "视频";
	public static final String ALIAS_PARENT_ID = "父节点";
	public static final String ALIAS_BS_COMMENT_TEXT = "评论内容";
	public static final String ALIAS_COMMENT_DATETIME = "评论时间";
	
	//date formats
	//public static final String FORMAT_COMMENT_DATETIME = DATE_FORMAT;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@Length(max=36)
	private String userId;
	//@Length(max=36)
	private String bshootId;
	//@Length(max=36)
	private String parentId;
	//@Length(max=256)
	private String bsCommentText;
	//
	private java.util.Date commentDatetime;

	private Integer commentPraise;
	private String bsUserId;
	//columns END


		public TbshootComment(){
		}
		public TbshootComment(String id) {
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

	@Column(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "bs_comment_text", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getBsCommentText() {
		return this.bsCommentText;
	}

	public void setBsCommentText(String bsCommentText) {
		this.bsCommentText = bsCommentText;
	}


	@Column(name = "comment_datetime", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getCommentDatetime() {
		return this.commentDatetime;
	}

	public void setCommentDatetime(java.util.Date commentDatetime) {
		this.commentDatetime = commentDatetime;
	}

	@Column(name = "comment_praise", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getCommentPraise() {
		return commentPraise;
	}
	public void setCommentPraise(Integer commentPraise) {
		this.commentPraise = commentPraise;
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
			.append("ParentId",getParentId())
			.append("BsCommentText",getBsCommentText())
			.append("CommentDatetime",getCommentDatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BshootComment == false) return false;
		if(this == obj) return true;
		BshootComment other = (BshootComment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


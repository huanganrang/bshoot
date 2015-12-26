package jb.pageModel;

import java.util.Date;

public class CommentPraise implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String commentId;
	private String userId;
	private Date praiseDatetime;




	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentId() {
		return this.commentId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setPraiseDatetime(Date praiseDatetime) {
		this.praiseDatetime = praiseDatetime;
	}
	
	public Date getPraiseDatetime() {
		return this.praiseDatetime;
	}

}

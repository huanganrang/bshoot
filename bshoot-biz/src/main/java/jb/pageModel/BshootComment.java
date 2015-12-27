package jb.pageModel;

import jb.util.PathUtil;

import java.util.Date;

public class BshootComment implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String userId;
	private String bshootId;
	private String parentId;
	private String bsCommentText;
	private Date commentDatetime;
	private Integer commentPraise;

	private String praised; // 是否赞过
	private String userHeadImage; // 评论人头像
	private String userName; // 评论人姓名
	private String parentUserName; // 上级用户姓名

	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setBshootId(String bshootId) {
		this.bshootId = bshootId;
	}

	public String getBshootId() {
		return this.bshootId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentId() {
		return this.parentId;
	}
	public void setBsCommentText(String bsCommentText) {
		this.bsCommentText = bsCommentText;
	}

	public String getBsCommentText() {
		return this.bsCommentText;
	}
	public void setCommentDatetime(Date commentDatetime) {
		this.commentDatetime = commentDatetime;
	}

	public Date getCommentDatetime() {
		return this.commentDatetime;
	}

	public Integer getCommentPraise() {
		return commentPraise;
	}

	public void setCommentPraise(Integer commentPraise) {
		this.commentPraise = commentPraise;
	}

	public String getPraised() {
		return praised;
	}

	public void setPraised(String praised) {
		this.praised = praised;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHeadImage() {
		return PathUtil.getHeadImagePath(userHeadImage);
	}

	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}

	public String getParentUserName() {
		return parentUserName;
	}

	public void setParentUserName(String parentUserName) {
		this.parentUserName = parentUserName;
	}
	
}

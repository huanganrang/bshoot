package jb.pageModel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import jb.util.PathUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "rawtypes"})
@ApiModel
public class Bshoot implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	@ApiModelProperty(value = "动态标题",notes = "动态标题")
	private String bsTitle;
	@ApiModelProperty(value = "动态话题")
	private String bsTopic;
	@ApiModelProperty(value = "动态图标")
	private String bsIcon;
	@ApiModelProperty(value = "视频/音频/图片地址")
	private String bsStream;
	@ApiModelProperty(value = "收藏数")
	private Integer bsCollect;
	@ApiModelProperty(value = "点赞数")
	private Integer bsPraise;
	@ApiModelProperty(value = "转发数")
	private Integer bsForward;
	@ApiModelProperty(value = "分享数")
	private Integer bsShare;
	@ApiModelProperty(value = "播放数")
	private Integer bsPlay;
	@ApiModelProperty(value = "动态类型")
	private String bsType;
	@ApiModelProperty(value = "文件类型")
	private Integer bsFileType;//文件类型
	@ApiModelProperty(value = "评论数")
	private Integer bsComment;
	@ApiModelProperty(value = "创建人id")
	private String userId;
	@ApiModelProperty(value = "创建人头像地址")
	private String userHeadImage;
	@ApiModelProperty(value = "创建人昵称")
	private String userName;
	@ApiModelProperty(value = "用户兴趣")
	private List<String> hobby;//用户兴趣
	@ApiModelProperty(value = "发布来源")
	private String publishFrom;//发布来源
	@ApiModelProperty(value = "引导类型 1:新人推荐 2:好友关注的人 3：我评论/打赏过的人 4：好友打赏过的人 5：可能感兴趣的人 6：可能认识的人 7：附近的人")
	private Integer guideType;//引导类型 1:新人推荐 2:好友关注的人 3：我评论/打赏过的人 4：好友打赏过的人 5：可能感兴趣的人 6：可能认识的人 7：附近的人
	private String userHeadImageAbsolute;
	private String bsIconAbsolute;
	private String bsStreamAbsolute;

	@ApiModelProperty(value = "动态内容")
	private String bsDescription;
	@ApiModelProperty(value = "备注")
	private String bsRemark;
	@ApiModelProperty(value = "创建时间")
	private Date createDatetime;
	@ApiModelProperty(value = "修改时间")
	private Date updateDatetime;
	@ApiModelProperty(value = "创建人")
	private String createPerson;
	@ApiModelProperty(value = "更新人")
	private String updatePerson;
	@ApiModelProperty(value = "发布位置经度")
	private String lgX;
	@ApiModelProperty(value = "发布位置纬度")
	private String lgY;
	private String lgName;
	@ApiModelProperty(value = "上级id")
	private String parentId;
	@ApiModelProperty(value = "状态")
	private String status;
	private String bsArea;
	@ApiModelProperty(value = "距离")
	private double distance; //距离
	@ApiModelProperty(value = "是否赞过")
	private String praised; //是否赞过
	@ApiModelProperty(value = "转发的美拍")
	private Bshoot parentBshoot; // 转发的美拍
	@ApiModelProperty(value = "用户等级")
	private String memberV;//用户等级
	private String squareIds; // 广场分类id集合

	private DataGrid comments; // 评论集合

	private List<Map> praises; // 赞集合

	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setBsTitle(String bsTitle) {
		this.bsTitle = bsTitle;
	}

	public String getBsTitle() {
		return this.bsTitle;
	}
	public void setBsTopic(String bsTopic) {
		this.bsTopic = bsTopic;
	}

	public String getBsTopic() {
		return this.bsTopic;
	}
	public void setBsIcon(String bsIcon) {
		this.bsIcon = bsIcon;
	}

	public String getBsIcon() {
		return this.bsIcon;
	}
	public void setBsStream(String bsStream) {
		this.bsStream = bsStream;
	}

	public String getBsStream() {
		return this.bsStream;
	}
	public void setBsCollect(Integer bsCollect) {
		this.bsCollect = bsCollect;
	}

	public Integer getBsCollect() {
		return this.bsCollect;
	}
	public void setBsPraise(Integer bsPraise) {
		this.bsPraise = bsPraise;
	}

	public Integer getBsPraise() {
		return this.bsPraise;
	}


	public Integer getBsPlay() {
		return bsPlay;
	}

	public void setBsPlay(Integer bsPlay) {
		this.bsPlay = bsPlay;
	}
	public void setBsType(String bsType) {
		this.bsType = bsType;
	}

	public String getBsType() {
		return this.bsType;
	}
	public void setBsComment(Integer bsComment) {
		this.bsComment = bsComment;
	}

	public Integer getBsComment() {
		return this.bsComment;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setBsDescription(String bsDescription) {
		this.bsDescription = bsDescription;
	}

	public String getBsDescription() {
		return this.bsDescription;
	}
	public void setBsRemark(String bsRemark) {
		this.bsRemark = bsRemark;
	}

	public String getBsRemark() {
		return this.bsRemark;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getCreatePerson() {
		return this.createPerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public String getUpdatePerson() {
		return this.updatePerson;
	}
	public void setLgX(String lgX) {
		this.lgX = lgX;
	}

	public String getLgX() {
		return this.lgX;
	}
	public void setLgY(String lgY) {
		this.lgY = lgY;
	}

	public String getLgY() {
		return this.lgY;
	}
	public void setLgName(String lgName) {
		this.lgName = lgName;
	}

	public String getLgName() {
		return this.lgName;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public String getUserHeadImage() {
		return userHeadImage;
	}

	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPraised() {
		return praised;
	}

	public void setPraised(String praised) {
		this.praised = praised;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getUserHeadImageAbsolute() {
		return PathUtil.getHeadImagePath(userHeadImage);
	}

	public String getBsIconAbsolute() {
		return PathUtil.getBshootPath(bsIcon);
	}

	public String getBsStreamAbsolute() {
		return PathUtil.getBshootPath(bsStream);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBsArea() {
		return bsArea;
	}

	public void setBsArea(String bsArea) {
		this.bsArea = bsArea;
	}

	public Bshoot getParentBshoot() {
		return parentBshoot;
	}

	public void setParentBshoot(Bshoot parentBshoot) {
		this.parentBshoot = parentBshoot;
	}
	
	public String getSquareIds() {
		return squareIds;
	}

	public void setSquareIds(String squareIds) {
		this.squareIds = squareIds;
	}
	
	public DataGrid getComments() {
		return comments;
	}

	public void setComments(DataGrid comments) {
		this.comments = comments;
	}

	public List<Map> getPraises() {
		return praises;
	}

	public void setPraises(List<Map> praises) {
		this.praises = praises;
	}

	public Integer getBsForward() {
		return bsForward;
	}

	public void setBsForward(Integer bsForward) {
		this.bsForward = bsForward;
	}

	public Integer getBsShare() {
		return bsShare;
	}

	public void setBsShare(Integer bsShare) {
		this.bsShare = bsShare;
	}

	public Integer getBsFileType() {
		return bsFileType;
	}

	public void setBsFileType(Integer bsFileType) {
		this.bsFileType = bsFileType;
	}

	public List<String> getHobby() {
		return hobby;
	}

	public void setHobby(List<String> hobby) {
		this.hobby = hobby;
	}

	public String getPublishFrom() {
		return publishFrom;
	}

	public void setPublishFrom(String publishFrom) {
		this.publishFrom = publishFrom;
	}

	public void setUserHeadImageAbsolute(String userHeadImageAbsolute) {
		this.userHeadImageAbsolute = userHeadImageAbsolute;
	}

	public void setBsIconAbsolute(String bsIconAbsolute) {
		this.bsIconAbsolute = bsIconAbsolute;
	}

	public void setBsStreamAbsolute(String bsStreamAbsolute) {
		this.bsStreamAbsolute = bsStreamAbsolute;
	}

	public String getMemberV() {
		return memberV;
	}

	public void setMemberV(String memberV) {
		this.memberV = memberV;
	}

	public Integer getGuideType() {
		return guideType;
	}

	public void setGuideType(Integer guideType) {
		this.guideType = guideType;
	}

	@Override
	public String toString() {
		return "Bshoot{" +
				"id='" + id + '\'' +
				", bsTitle='" + bsTitle + '\'' +
				", bsTopic='" + bsTopic + '\'' +
				", bsIcon='" + bsIcon + '\'' +
				", bsStream='" + bsStream + '\'' +
				", bsCollect=" + bsCollect +
				", bsPraise=" + bsPraise +
				", bsForward=" + bsForward +
				", bsShare=" + bsShare +
				", bsPlay=" + bsPlay +
				", bsType='" + bsType + '\'' +
				", bsFileType=" + bsFileType +
				", bsComment=" + bsComment +
				", userId='" + userId + '\'' +
				", userHeadImage='" + userHeadImage + '\'' +
				", userName='" + userName + '\'' +
				", hobby=" + hobby +
				", publishFrom='" + publishFrom + '\'' +
				", guideType=" + guideType +
				", userHeadImageAbsolute='" + userHeadImageAbsolute + '\'' +
				", bsIconAbsolute='" + bsIconAbsolute + '\'' +
				", bsStreamAbsolute='" + bsStreamAbsolute + '\'' +
				", bsDescription='" + bsDescription + '\'' +
				", bsRemark='" + bsRemark + '\'' +
				", createDatetime=" + createDatetime +
				", updateDatetime=" + updateDatetime +
				", createPerson='" + createPerson + '\'' +
				", updatePerson='" + updatePerson + '\'' +
				", lgX='" + lgX + '\'' +
				", lgY='" + lgY + '\'' +
				", lgName='" + lgName + '\'' +
				", parentId='" + parentId + '\'' +
				", status='" + status + '\'' +
				", bsArea='" + bsArea + '\'' +
				", distance=" + distance +
				", praised='" + praised + '\'' +
				", parentBshoot=" + parentBshoot +
				", memberV='" + memberV + '\'' +
				", squareIds='" + squareIds + '\'' +
				", comments=" + comments +
				", praises=" + praises +
				'}';
	}
}

package jb.pageModel;

import jb.util.PathUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "rawtypes"})
public class Bshoot implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String bsTitle;
	private String bsTopic;
	private String bsIcon;
	private String bsStream;
	private Integer bsCollect;
	private Integer bsPraise;
	private Integer bsPlay;
	private String bsType;
	private Integer bsComment;
	private String userId;
	private String userHeadImage;
	private String userName;

	private String userHeadImageAbsolute;
	private String bsIconAbsolute;
	private String bsStreamAbsolute;

	private String bsDescription;
	private String bsRemark;
	private Date createDatetime;
	private Date updateDatetime;
	private String createPerson;
	private String updatePerson;
	private String lgX;
	private String lgY;
	private String lgName;
	private String parentId;
	private String status;
	private String bsArea;
	private double distance; //距离
	private String praised; //是否赞过

	private Bshoot parentBshoot; // 转发的美拍

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
				", bsPlay=" + bsPlay +
				", bsType='" + bsType + '\'' +
				", bsComment=" + bsComment +
				", userId='" + userId + '\'' +
				", userHeadImage='" + userHeadImage + '\'' +
				", userName='" + userName + '\'' +
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
				", squareIds='" + squareIds + '\'' +
				", comments=" + comments +
				", praises=" + praises +
				'}';
	}
}

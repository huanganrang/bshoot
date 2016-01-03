
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
@Table(name = "bshoot")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tbshoot implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Bshoot";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_BS_TITLE = "标题";
	public static final String ALIAS_BS_TOPIC = "主题";
	public static final String ALIAS_BS_ICON = "icon";
	public static final String ALIAS_BS_STREAM = "视频文件";
	public static final String ALIAS_BS_COLLECT = "收藏数";
	public static final String ALIAS_BS_PRAISE = "赞数";
	public static final String ALIAS_BS_TYPE = "类别";
	public static final String ALIAS_BS_COMMENT = "评论数";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_BS_DESCRIPTION = "描述";
	public static final String ALIAS_BS_REMARK = "备注";
	public static final String ALIAS_CREATE_DATETIME = "创建时间";
	public static final String ALIAS_UPDATE_DATETIME = "修改时间";
	public static final String ALIAS_CREATE_PERSON = "创建人";
	public static final String ALIAS_UPDATE_PERSON = "修改人";
	public static final String ALIAS_LG_X = "经度";
	public static final String ALIAS_LG_Y = "纬度";
	public static final String ALIAS_LG_NAME = "坐标地点";
	public static final String ALIAS_PARENT_ID = "转发ID";
	
	//date formats
	/*public static final String FORMAT_CREATE_DATETIME = DATE_FORMAT;
	public static final String FORMAT_UPDATE_DATETIME = DATE_FORMAT;*/
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//@Length(max=36)
	private String id;
	//@NotBlank @Length(max=256)
	private String bsTitle;
	//@Length(max=64)
	private String bsTopic;
	//@Length(max=2147483647)
	private String bsIcon;
	//@Length(max=2147483647)
	private String bsStream;
	//
	private Integer bsCollect;
	//
	private Integer bsPraise;
	//
	private Integer bsPlay;
	//@Length(max=4)
	private String bsType;
	//
	private Integer bsComment;
	//@Length(max=36)
	private String userId;
	//@Length(max=512)
	private String bsDescription;
	//@Length(max=256)
	private String bsRemark;
	//
	private java.util.Date createDatetime;
	//
	private java.util.Date updateDatetime;
	//@Length(max=36)
	private String createPerson;
	//@Length(max=36)
	private String updatePerson;
	//columns END
	//@Length(max=18)
	private String lgX;
	//@Length(max=18)
	private String lgY;
	//@Length(max=256)
	private String lgName;
	//@Length(max=36)
	private String parentId;

	//@Length(max=4)
	private String status;
	//@Length(max=256)
	private String bsArea;
	private java.lang.Integer bsForward;
	private java.lang.Integer bsShare;
	private java.lang.Integer isDelete;

		public Tbshoot(){
		}
		public Tbshoot(String id) {
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

	@Column(name = "bs_title", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getBsTitle() {
		return this.bsTitle;
	}

	public void setBsTitle(String bsTitle) {
		this.bsTitle = bsTitle;
	}

	@Column(name = "bs_topic", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getBsTopic() {
		return this.bsTopic;
	}

	public void setBsTopic(String bsTopic) {
		this.bsTopic = bsTopic;
	}

	@Column(name = "bs_icon", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public String getBsIcon() {
		return this.bsIcon;
	}

	public void setBsIcon(String bsIcon) {
		this.bsIcon = bsIcon;
	}

	@Column(name = "bs_stream", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public String getBsStream() {
		return this.bsStream;
	}

	public void setBsStream(String bsStream) {
		this.bsStream = bsStream;
	}

	@Column(name = "bs_collect", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBsCollect() {
		return this.bsCollect;
	}

	public void setBsCollect(Integer bsCollect) {
		this.bsCollect = bsCollect;
	}

	@Column(name = "bs_praise", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBsPraise() {
		return this.bsPraise;
	}

	public void setBsPraise(Integer bsPraise) {
		this.bsPraise = bsPraise;
	}

	@Column(name = "bs_play", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBsPlay() {
		return this.bsPlay;
	}

	public void setBsPlay(Integer bsPlay) {
		this.bsPlay = bsPlay;
	}

	@Column(name = "bs_type", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getBsType() {
		return this.bsType;
	}

	public void setBsType(String bsType) {
		this.bsType = bsType;
	}

	@Column(name = "bs_comment", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBsComment() {
		return this.bsComment;
	}

	public void setBsComment(Integer bsComment) {
		this.bsComment = bsComment;
	}

	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "bs_description", unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	public String getBsDescription() {
		return this.bsDescription;
	}

	public void setBsDescription(String bsDescription) {
		this.bsDescription = bsDescription;
	}

	@Column(name = "bs_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getBsRemark() {
		return this.bsRemark;
	}

	public void setBsRemark(String bsRemark) {
		this.bsRemark = bsRemark;
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

	@Column(name = "lg_x", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getLgX() {
		return this.lgX;
	}

	public void setLgX(String lgX) {
		this.lgX = lgX;
	}

	@Column(name = "lg_y", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
	public String getLgY() {
		return this.lgY;
	}

	public void setLgY(String lgY) {
		this.lgY = lgY;
	}

	@Column(name = "lg_name", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getLgName() {
		return this.lgName;
	}

	public void setLgName(String lgName) {
		this.lgName = lgName;
	}

	@Column(name = "bs_area", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public String getBsArea() {
		return bsArea;
	}

	public void setBsArea(String bsArea) {
		this.bsArea = bsArea;
	}

	@Column(name = "parent_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "isDelete", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "bs_share", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBsShare() {
		return bsShare;
	}

	public void setBsShare(Integer bsShare) {
		this.bsShare = bsShare;
	}

	@Column(name = "bs_forward", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getBsForward() {
		return bsForward;
	}

	public void setBsForward(Integer bsForward) {
		this.bsForward = bsForward;
	}

	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("BsTitle",getBsTitle())
			.append("BsTopic",getBsTopic())
			.append("BsIcon",getBsIcon())
			.append("BsStream",getBsStream())
			.append("BsCollect",getBsCollect())
			.append("BsPraise",getBsPraise())
			.append("BsType",getBsType())
			.append("BsComment",getBsComment())
			.append("UserId",getUserId())
			.append("BsDescription",getBsDescription())
			.append("BsRemark",getBsRemark())
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
		if(obj instanceof Bshoot == false) return false;
		if(this == obj) return true;
		Bshoot other = (Bshoot)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


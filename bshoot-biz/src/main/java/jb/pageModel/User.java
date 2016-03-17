package jb.pageModel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import jb.util.PathUtil;

import java.util.Date;
import java.util.List;
@ApiModel("User")
@SuppressWarnings("unused")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = -3278884807711490319L;
	@ApiModelProperty("用户id")
	private String id;
	@ApiModelProperty("用户名")
	private String name;
	@ApiModelProperty("密码")
	private String pwd;
	@ApiModelProperty("创建时间")
	private Date createdatetime;
	@ApiModelProperty("修改时间")
	private Date modifydatetime;
	@ApiModelProperty("创建时间开始")
	private Date createdatetimeStart;
	@ApiModelProperty("创建时间结束")
	private Date createdatetimeEnd;
	@ApiModelProperty("修改时间开始")
	private Date modifydatetimeStart;
	@ApiModelProperty("修改时间结束")
	private Date modifydatetimeEnd;
	@ApiModelProperty("角色id")
	private String roleIds;
	@ApiModelProperty("角色名称")
	private String roleNames;
	@ApiModelProperty("用户类型")
	private String utype;
	@ApiModelProperty("第三方用户名")
	private String thirdUser;
	@ApiModelProperty("头像地址")
	private String headImage;
	@ApiModelProperty("用户昵称")
	private String nickname;
	@ApiModelProperty("区域")
	private String area;
	@ApiModelProperty("生日")
	private String birthday;
	@ApiModelProperty("性别")
	private String sex;
	@ApiModelProperty("个性签名")
	private String bardian;
	@ApiModelProperty("大/小v")
	private String memberV;
	@ApiModelProperty("邮箱")
	private String email;
	private String recommend;

	private String attred; //是否被关注过
	@ApiModelProperty("是否明星")
	private Boolean isStar;
	@ApiModelProperty("是否达人")
	private Boolean isTarento;
	private String validateCode;
	@ApiModelProperty("手机号")
	private String mobile;
	@ApiModelProperty("登录经度")
	private String lgX;//登录经度
	@ApiModelProperty("登录纬度")
	private String lgY;//登录维度
	@ApiModelProperty("登录城市")
	private String loginArea;//登录城市
	@ApiModelProperty("头像地址绝对路径")
	private String headImageAbsolute;
	private String tokenId;
	@ApiModelProperty("容联子账号")
	private String subAccountSid;//容联子账号
	@ApiModelProperty("子账号密码")
	private String subToken;//子账号密码
	@ApiModelProperty("voIp账号")
	private String voipAccount;//VoIP账号
	@ApiModelProperty("voIp密码")
	private String voipPwd;//VoIP账号密码

	private UserProfile userProfile; //用户画像数据

	private UserHobby userHobby;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}

	public Date getModifydatetimeStart() {
		return modifydatetimeStart;
	}

	public void setModifydatetimeStart(Date modifydatetimeStart) {
		this.modifydatetimeStart = modifydatetimeStart;
	}

	public Date getModifydatetimeEnd() {
		return modifydatetimeEnd;
	}

	public void setModifydatetimeEnd(Date modifydatetimeEnd) {
		this.modifydatetimeEnd = modifydatetimeEnd;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Date getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Date getModifydatetime() {
		return modifydatetime;
	}

	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getThirdUser() {
		return thirdUser;
	}

	public void setThirdUser(String thirdUser) {
		this.thirdUser = thirdUser;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBardian() {
		return bardian;
	}

	public void setBardian(String bardian) {
		this.bardian = bardian;
	}

	public String getMemberV() {
		return memberV;
	}

	public void setMemberV(String memberV) {
		this.memberV = memberV;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getAttred() {
		return attred;
	}

	public void setAttred(String attred) {
		this.attred = attred;
	}

	public Boolean getIsStar() {
		return isStar;
	}

	public void setIsStar(Boolean isStar) {
		this.isStar = isStar;
	}

	public Boolean getIsTarento() {
		return isTarento;
	}

	public void setIsTarento(Boolean isTarento) {
		this.isTarento = isTarento;
	}

	public String getHeadImageAbsolute() {
		return PathUtil.getHeadImagePath(headImage);
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setHeadImageAbsolute(String headImageAbsolute) {
		this.headImageAbsolute = headImageAbsolute;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getLgX() {
		return lgX;
	}

	public void setLgX(String lgX) {
		this.lgX = lgX;
	}

	public String getLgY() {
		return lgY;
	}

	public void setLgY(String lgY) {
		this.lgY = lgY;
	}

	public String getLoginArea() {
		return loginArea;
	}

	public void setLoginArea(String loginArea) {
		this.loginArea = loginArea;
	}

	public String getSubAccountSid() {
		return subAccountSid;
	}

	public void setSubAccountSid(String subAccountSid) {
		this.subAccountSid = subAccountSid;
	}

	public String getSubToken() {
		return subToken;
	}

	public void setSubToken(String subToken) {
		this.subToken = subToken;
	}

	public String getVoipAccount() {
		return voipAccount;
	}

	public void setVoipAccount(String voipAccount) {
		this.voipAccount = voipAccount;
	}

	public String getVoipPwd() {
		return voipPwd;
	}

	public void setVoipPwd(String voipPwd) {
		this.voipPwd = voipPwd;
	}

	public UserHobby getUserHobby() {
		return userHobby;
	}

	public void setUserHobby(UserHobby userHobby) {
		this.userHobby = userHobby;
	}
}

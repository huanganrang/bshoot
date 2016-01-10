
/*
 * @author John
 * @date - 2016-01-08
 */

package jb.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "api_test")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TapiTest implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ApiTest";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "接口名称";
	public static final String ALIAS_URL = "接口url地址";
	public static final String ALIAS_PARAM = "参数";
	public static final String ALIAS_RESULT = "返回结果";
	public static final String ALIAS_IS_SUCCESS = "0接口调试成功1失败";
	public static final String ALIAS_COMPLETE_URL = "口接url加上参数的完整地址";
	public static final String ALIAS_INFO = "接口信息说明";
	public static final String ALIAS_PARAM_DES = "参数说明";
	public static final String ALIAS_RESULT_DES = "返回结果说明";
	public static final String ALIAS_REMARK = "备注";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private String id;
	//@NotBlank @Length(max=255)
	private String name;
	//@NotBlank @Length(max=255)
	private String url;
	//@Length(max=255)
	private String param;
	//@Length(max=2147483647)
	private String result;
	//
	private Integer isSuccess;
	//@Length(max=500)
	private String completeUrl;
	//@Length(max=255)
	private String info;
	//@Length(max=255)
	private String paramDes;
	//@Length(max=255)
	private String resultDes;
	//@Length(max=255)
	private String remark;
	//columns END


		public TapiTest(){
		}
		public TapiTest(String id) {
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
	
	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "url", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "param", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getParam() {
		return this.param;
	}
	
	public void setParam(String param) {
		this.param = param;
	}
	
	@Column(name = "result", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public String getResult() {
		return this.result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	@Column(name = "is_success", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Integer getIsSuccess() {
		return this.isSuccess;
	}
	
	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	@Column(name = "complete_url", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getCompleteUrl() {
		return this.completeUrl;
	}
	
	public void setCompleteUrl(String completeUrl) {
		this.completeUrl = completeUrl;
	}
	
	@Column(name = "info", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getInfo() {
		return this.info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Column(name = "param_des", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getParamDes() {
		return this.paramDes;
	}
	
	public void setParamDes(String paramDes) {
		this.paramDes = paramDes;
	}
	
	@Column(name = "result_des", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getResultDes() {
		return this.resultDes;
	}
	
	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Url",getUrl())
			.append("Param",getParam())
			.append("Result",getResult())
			.append("IsSuccess",getIsSuccess())
			.append("CompleteUrl",getCompleteUrl())
			.append("Info",getInfo())
			.append("ParamDes",getParamDes())
			.append("ResultDes",getResultDes())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ApiTest == false) return false;
		if(this == obj) return true;
		ApiTest other = (ApiTest)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}


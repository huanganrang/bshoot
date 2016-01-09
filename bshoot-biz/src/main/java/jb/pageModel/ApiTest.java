package jb.pageModel;

@SuppressWarnings("serial")
public class ApiTest implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String name;
	private String url;
	private String param;
	private String result;
	private Integer isSuccess;
	private String completeUrl;
	private String info;
	private String paramDes;
	private String resultDes;
	private String remark;

	

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
	public String getParam() {
		return this.param;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResult() {
		return this.result;
	}
	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public Integer getIsSuccess() {
		return this.isSuccess;
	}
	public void setCompleteUrl(String completeUrl) {
		this.completeUrl = completeUrl;
	}
	
	public String getCompleteUrl() {
		return this.completeUrl;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getInfo() {
		return this.info;
	}
	public void setParamDes(String paramDes) {
		this.paramDes = paramDes;
	}
	
	public String getParamDes() {
		return this.paramDes;
	}
	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}
	
	public String getResultDes() {
		return this.resultDes;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return this.remark;
	}

}

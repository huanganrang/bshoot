package jb.pageModel;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import jb.listener.Application;

/**
 * 
 * JSON模型
 * 
 * 用户后台向前台返回的JSON对象
 * 
 * @author John
 * 
 */
@SuppressWarnings("serial")
@ApiModel("Json")
public class Json implements java.io.Serializable {

	@ApiModelProperty("是否成功 true/false")
	private boolean success = false;
    @ApiModelProperty("消息")
	private String msg = "";
	@ApiModelProperty("错误代码")
	private String errorCode;
	@ApiModelProperty("错误消息")
	private String errorMsg;
	@ApiModelProperty("返回对象")
	private Object obj = null;
	
	public void success(){
		this.success = true;
	}
	public void fail(){
		this.success = false;
	}
	public void fail(String errorCode){
		this.success = false;
		setErrorCode(errorCode);
		setErrorMsg(Application.getString(errorCode));
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}

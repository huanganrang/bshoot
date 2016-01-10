package jb.pageModel;

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
public class Json implements java.io.Serializable {

	private boolean success = false;

	private String msg = "";
	private String errorCode;
	private String errorMsg;
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

package jb.util;

import jb.absx.F;
import jb.listener.Application;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author John
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("config");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

	/**
	 * 获取配置中心的数据
	 * @param code
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String code,String defaultValue){
		String value = Application.getString(code);
		if(F.empty(value)){
			value = defaultValue;
		}
		return value;
	}

	public static String getValue(String code) {
		return getValue(code,"");
	}

}

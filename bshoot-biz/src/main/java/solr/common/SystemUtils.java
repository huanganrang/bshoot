package solr.common;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 *
 */
public class SystemUtils {
	/**
	 * 打印异常的堆栈信息
	 *
	 * @param exception
	 *            异常
	 * @return 异常的堆栈信息
	 * @author phoenix
	 */
	public static String printErrorTrace(Throwable exception) {
		StringBuffer errorString = new StringBuffer();
		errorString.append(exception.toString()).append("\n");
		StackTraceElement[] trace = exception.getStackTrace();
		for (int i = 0; i < trace.length; i++) {
			errorString.append(trace[i]).append("\n");
		}
		return errorString.toString();
	}

	/**
	 * 根据毫秒获得时间
	 **/
	public static String getItemTotalTime(long time) {
		String ret = "0";
		time = time / 1000;
		if (time >= 3600) {
			long h = time / 3600;
			long m = (time - h * 3600) / 60;
			long s = time - h * 3600 - m * 60;

			ret = h + ":" + (m > 9 ? m : ("0" + m)) + ":" + (s > 9 ? s : ("0" + s));
		} else if (time >= 60 && time < 3600) {
			long m = time / 60;
			long s = time - m * 60;

			ret = (m > 9 ? m : ("0" + m)) + ":" + (s > 9 ? s : ("0" + s));
		} else {
			ret = "00:" + (time > 9 ? time : ("0" + time));
		}

		return ret;
	}

	/**
	 * solr值规范化
	 *
	 * @param value
	 * @return
	 */
	public static String solrStringTrasfer(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return "\"" + value + "\"";
	}

	/**
	 * solr or条件组合
	 * @param str
	 * @param matchFactor
	 * @param out
	 */
	public static void combineStr(String[] str,int matchFactor,List<String> out ,String connStr) {
		if(null==connStr) connStr=" ";
		int nCnt = str.length;
		int nBit = 1<<nCnt;
		int counter = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= nBit; i++) {
			for (int j = 0; j < nCnt; j++) {
				if ((1<<j & i ) != 0) {
					counter++;
					sb.append(" \"").append(str[j]).append("\" ").append(connStr);
				}
			}
			if(counter==matchFactor){
				sb.delete(sb.lastIndexOf(connStr),sb.length());
				out.add(sb.toString());
			}
			counter=0;
			sb.delete(0,sb.length());
		}
	}

	/**
	 *
	 * @param list
	 * @param pageSize
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
		int listSize = list.size(); // list的大小
		int page = (listSize + (pageSize - 1)) / pageSize; // 页数

		List<List<T>> listArray = new LinkedList<List<T>>(); // 创建list数组
		// ,用来保存分割后的list
		for (int i = 0; i < page; i++) { // 按照数组大小遍历
			List<T> subList = new LinkedList<T>(); // 数组每一位放入一个分割后的list
			for (int j = 0; j < listSize; j++) { // 遍历待分割的list
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize; // 当前记录的页码(第几页)
				if (pageIndex == (i + 1)) { // 当前记录的页码等于要放入的页码时
					subList.add(list.get(j)); // 放入list中的元素到分割后的list(subList)
				}

				if ((j + 1) == ((j + 1) * pageSize)) { // 当放满一页时退出当前循环
					break;
				}
			}
			listArray.add(subList); // 将分割后的list放入对应的数组的位中
		}
		return listArray;
	}

	public static InetAddress getLocalHostAddress() {
		try {
			for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements();) {
				NetworkInterface ni = nis.nextElement();
				if (ni.isLoopback() || ni.isVirtual() || !ni.isUp())
					continue;
				for (Enumeration<InetAddress> ias = ni.getInetAddresses(); ias.hasMoreElements();) {
					InetAddress ia = ias.nextElement();
					if (ia instanceof Inet6Address)
						continue;
					return ia;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是用户index的还是search的中间件
	 *
	 * @return
	 */
	public static boolean forQuery(String forQuery) {
		if (StringUtils.equalsIgnoreCase(forQuery, "true")) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param json
	 */
	public static String wrapSolrClusterJson(String json) {
		String[] arr = StringUtils.substringsBetween(json, "\"state\":\"", "\"");
		Set<String> set = new HashSet<String>();
		for (String string : arr) {
			if (!StringUtils.equalsIgnoreCase(string, "active")) {
				set.add("\"state\":\"" + string + "\"");
			}
		}

		if (set.size() > 0) {
			for (String string : set) {
				json = StringUtils.replace(json, string, "<font color='red'><b>" + string + "</b></font>");
			}
		}
		return json;
	}
	private static String[] chineseNum = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	/**
	 * 去除特殊字符、阿拉伯数字转中文、小写
	 *
	 * @param s
	 * @return
	 */
	public static String escapeToSolr(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '，' || c == ',' || c == '.' || c == '·' || c == '(' || c == ')' || c == '-' || c == '^'
					|| c == '\"' || c == '（' || c == '）' || c == '~' || c == '|' || c == '&' || c == '；' || c == '’'
					|| c == ';' || c == '\'' || c == '、' || Character.isWhitespace(c)
					|| c=='?' || c=='？') {
				continue;
			}
			if (Character.isDigit((int) c)) {
				sb.append(chineseNum[NumberUtils.toInt(String.valueOf(c))]);
				continue;
			}
			sb.append(c);
		}
		return sb.toString().toLowerCase();
	}
}

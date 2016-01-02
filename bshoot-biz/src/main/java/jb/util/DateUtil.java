package jb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	/** 日期格式yyyy-MM-dd字符串常量 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/** 日期格式yyyy-MM-dd HH:mm:ss字符串常量 */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** 日期格式yyyy-MM字符串常量 */
	public static final String MONTH_FORMAT = "yyyy-MM";

	/** 一个月时间大约的long型数字 */
	public static final long MONTH_LONG = 2651224907l;
	
	 
	public static final long nd = 1000 * 24 * 60 * 60;
	
	public static final long nh = 1000 * 60 * 60;
	
	public static final long nm = 1000 * 60;
	

	/**
	 * 得到当前日期的前/后　beforeDays　天的日期数
	 * 
	 * @param beforeDays
	 * @return
	 */
	public static String getDate(int beforeDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		String a = dateToString(c.getTime(), DATE_FORMAT);
		return a;
	}

	/**
	 * 得到当前日期的前/后　beforeDays　天的日期数,格式自定
	 * 
	 * @param beforeDays
	 * @param dateFormat
	 * @return
	 */
	public static String getDate(int beforeDays, String dateFormat) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, beforeDays);
		String a = dateToString(c.getTime(), dateFormat);
		return a;
	}

	/**
	 * 返回当前时间的前/后几个小时日期
	 * @param beforeHours
	 * @param dateFormat
	 * @return
	 */
	public static String getDateBeforeHours(int beforeHours,String dateFormat){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.HOUR_OF_DAY, beforeHours);
		String a = dateToString(c.getTime(), dateFormat);
		return a;
	}

	/**
	 * 返回当前时间的前/后几个小时日期
	 * @param beforeHours
	 * @return
	 */
	public static Date getDateBeforeHours(int beforeHours){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.HOUR_OF_DAY, beforeHours);
		return c.getTime();
	}

	/**
	 * 将日期字符串转换为solr格式的日期字符串
	 * @param date
	 * @return
	 */
	public static String convert2SolrDate(String date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		try {
			dateFormat.parse(date);
			date.replace(" ","T");
			return date+"Z";
		}catch (ParseException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将日期类型转换为yyyy-MM-dd字符串
	 * 
	 * @param dateValue
	 * @return String
	 */
	public static String dateToString(Date dateValue) {
		return dateToString(dateValue, DATETIME_FORMAT);
	}

	/**
	 * 将日期类型转换为指定格式的字符串
	 * 
	 * @param dateValue
	 * @param format
	 * @return String
	 */
	public static String dateToString(Date dateValue, String format) {
		if (dateValue == null || format == null) {
			return null;
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(dateValue);
		}
	}
	
	
	/**
	 * 将日期类型转换为指定格式的字符串
	 * 
	 * @param dateValue
	 * @param format
	 * @return String
	 */
	public static String dateToString(Date dateValue, String format, Locale locale) {
		if (dateValue == null || format == null) {
			return null;
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
			return dateFormat.format(dateValue);
		}
	}

	/**
	 * 将日期yyyy-MM-dd字符串转为日期类型，如果转换失败返回null
	 * 
	 * @param stringValue
	 * @return Date
	 */
	public static Date stringToDate(String stringValue) {
		return stringToDate(stringValue, DATE_FORMAT);
	}

	/**
	 * 将指定日期格式的字符串转为日期类型，如果转换失败返回null
	 * 
	 * @param stringValue
	 * @param format
	 * @return Date
	 */
	public static Date stringToDate(String stringValue, String format) {
		Date dateValue = null;
		if (stringValue != null && format != null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				dateValue = dateFormat.parse(stringValue);

			} catch (ParseException ex) {
				dateValue = null;
			}
		}
		return dateValue;
	}

	/**
	 * 获得当前年
	 * 
	 * @return string
	 */
	public static String getNowYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return String.valueOf(year);
	}

	/**
	 * 获得当前月
	 * 
	 * @return string
	 */
	public static String getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			return "0" + month;
		} else {
			return String.valueOf(month);
		}
	}

	/**
	 * 获得当前日
	 * 
	 * @return string
	 */
	public static String getNowDay() {
		return dateToString(new Date(), "dd");

	}

	/**
	 * 昨天
	 * 
	 * @return
	 */
	public static String getYestday() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -1);
		return dateToString(date.getTime(), "dd");

	}

	/**
	 * 返回几个月前的Date类型
	 * 
	 * @param monthCount
	 *            几个月
	 * @return Date
	 */
	public static Date getDateFront(int monthCount) {
		return new Date(Calendar.getInstance().getTimeInMillis() - MONTH_LONG * monthCount);
	}

	/**
	 * 返回当前小时
	 * 
	 * @return string
	 */
	public static String getNowHour() {
		return dateToString(new Date(), "HH");
	}

	/**
	 * 返回当前分钟
	 * 
	 * @return string
	 */
	public static String getNowMinute() {
		return dateToString(new Date(), "mm");
	}

	/**
	 * 设置时间的日期值
	 * 
	 * @param stringDate
	 * @param num
	 * @return Date
	 */
	public static Date setDate(String stringDate, int num) {
		if (stringDate != null) {
			Date date = stringToDate(stringDate, "yyyy-MM-dd");
			return setDate(date, num);
		} else {
			return null;
		}
	}

	/**
	 * 设置时间的日期值
	 * 
	 * @param date
	 * @param num
	 * @return Date
	 */
	public static Date setDate(Date date, int num) {
		Date dateValue = null;
		Calendar c = null;
		if (date != null) {
			c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, num);
			dateValue = c.getTime();
		}
		return dateValue;
	}

	/**
	 * 取得两个日期的时间间隔,相差的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDayBetween(Date d1, Date d2) {
		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		if (d1.before(d2)) {
			before.setTime(d1);
			after.setTime(d2);
		} else {
			before.setTime(d2);
			after.setTime(d1);
		}
		int days = 0;

		int startDay = before.get(Calendar.DAY_OF_YEAR);
		int endDay = after.get(Calendar.DAY_OF_YEAR);

		int startYear = before.get(Calendar.YEAR);
		int endYear = after.get(Calendar.YEAR);
		before.clear();
		before.set(startYear, 0, 1);

		while (startYear != endYear) {
			before.set(startYear++, Calendar.DECEMBER, 31);
			days += before.get(Calendar.DAY_OF_YEAR);
		}
		return days + endDay - startDay;
	}

	public static Date addDay(Date myDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}
	
	public static Date addMonth(Date myDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 取得两个日期的时间间隔,相差的分钟
	 * @param endDate
	 * @param nowDate
	 * @return
	 */
	public static long getDatePoor(Date endDate,Date nowDate){
	    long diff = Math.abs(endDate.getTime() - nowDate.getTime());
//	    long day = diff / nd;
//	    long hour = diff / nh;
	    long minute = diff / nm;
	    return minute;
	}
	
}
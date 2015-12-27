package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DiveLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String logType;
	private String fileSrc;
	private String userId;
	private String diveType;
	private Date diveDate;
	private String weather;
	private Float waterTemperature;
	private Float seeing;
	private Date inTime;
	private Date outTime;
	private Float diveHeight;
	private Float diveWeith;
	private Float weatherTemperature;
	private Float windPower;
	private Float gasStart;
	private Float gasEnd;
	private Date addtime;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogType() {
		return this.logType;
	}
	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}

	public String getFileSrc() {
		return this.fileSrc;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
	public void setDiveType(String diveType) {
		this.diveType = diveType;
	}

	public String getDiveType() {
		return this.diveType;
	}
	public void setDiveDate(Date diveDate) {
		this.diveDate = diveDate;
	}

	public Date getDiveDate() {
		return this.diveDate;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWeather() {
		return this.weather;
	}
	public void setWaterTemperature(Float waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	public Float getWaterTemperature() {
		return this.waterTemperature;
	}
	public void setSeeing(Float seeing) {
		this.seeing = seeing;
	}

	public Float getSeeing() {
		return this.seeing;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getInTime() {
		return this.inTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public Date getOutTime() {
		return this.outTime;
	}
	public void setDiveHeight(Float diveHeight) {
		this.diveHeight = diveHeight;
	}

	public Float getDiveHeight() {
		return this.diveHeight;
	}
	public void setDiveWeith(Float diveWeith) {
		this.diveWeith = diveWeith;
	}

	public Float getDiveWeith() {
		return this.diveWeith;
	}
	public void setWeatherTemperature(Float weatherTemperature) {
		this.weatherTemperature = weatherTemperature;
	}

	public Float getWeatherTemperature() {
		return this.weatherTemperature;
	}
	public void setWindPower(Float windPower) {
		this.windPower = windPower;
	}

	public Float getWindPower() {
		return this.windPower;
	}
	public void setGasStart(Float gasStart) {
		this.gasStart = gasStart;
	}

	public Float getGasStart() {
		return this.gasStart;
	}
	public void setGasEnd(Float gasEnd) {
		this.gasEnd = gasEnd;
	}

	public Float getGasEnd() {
		return this.gasEnd;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}

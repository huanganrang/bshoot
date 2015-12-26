package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DiveAddress implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String name;
	private String sumary;
	private String icon;
	private String description;
	private String area;
	private String feature;
	private String status;
	private Date addtime;



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
	public void setSumary(String sumary) {
		this.sumary = sumary;
	}

	public String getSumary() {
		return this.sumary;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return this.icon;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return this.area;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getFeature() {
		return this.feature;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}

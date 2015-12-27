package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DiveEquip implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String equipIcon;
	private String equipSumary;
	private String equipName;
	private String status;
	private String equipBrand;
	private String equipDes;
	private Date addtime;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setEquipIcon(String equipIcon) {
		this.equipIcon = equipIcon;
	}

	public String getEquipIcon() {
		return this.equipIcon;
	}
	public void setEquipSumary(String equipSumary) {
		this.equipSumary = equipSumary;
	}

	public String getEquipSumary() {
		return this.equipSumary;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public String getEquipName() {
		return this.equipName;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}
	public void setEquipBrand(String equipBrand) {
		this.equipBrand = equipBrand;
	}

	public String getEquipBrand() {
		return this.equipBrand;
	}
	public void setEquipDes(String equipDes) {
		this.equipDes = equipDes;
	}

	public String getEquipDes() {
		return this.equipDes;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}

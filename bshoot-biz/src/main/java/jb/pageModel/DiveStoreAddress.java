package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class DiveStoreAddress implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String storeId;
	private String addressId;
	private Date addtime;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreId() {
		return this.storeId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getAddressId() {
		return this.addressId;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}

}

package jb.pageModel;

import java.util.Date;

@SuppressWarnings("serial")
public class BshootSquareRel implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.String id;	
	private java.lang.String bshootId;	
	private java.lang.String squareId;	

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	public java.lang.String getId() {
		return this.id;
	}

	
	public void setBshootId(java.lang.String bshootId) {
		this.bshootId = bshootId;
	}
	
	public java.lang.String getBshootId() {
		return this.bshootId;
	}
	public void setSquareId(java.lang.String squareId) {
		this.squareId = squareId;
	}
	
	public java.lang.String getSquareId() {
		return this.squareId;
	}

}

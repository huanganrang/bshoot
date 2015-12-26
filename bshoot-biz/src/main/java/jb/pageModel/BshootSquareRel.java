package jb.pageModel;

@SuppressWarnings("serial")
public class BshootSquareRel implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private String id;
	private String bshootId;
	private String squareId;



	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}


	public void setBshootId(String bshootId) {
		this.bshootId = bshootId;
	}

	public String getBshootId() {
		return this.bshootId;
	}
	public void setSquareId(String squareId) {
		this.squareId = squareId;
	}

	public String getSquareId() {
		return this.squareId;
	}

}

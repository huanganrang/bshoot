package jb.pageModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BaseData implements java.io.Serializable{
	private String id;
	private String pid;
	private String name;
	private Integer seq;
	private String basetypeCode;
	private String codeName;
	private String description;
	private List<BaseData> children = new ArrayList<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getBasetypeCode() {
		return basetypeCode;
	}
	public void setBasetypeCode(String basetypeCode) {
		this.basetypeCode = basetypeCode;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<BaseData> getChildren() {
		return children;
	}

	public void setChildren(List<BaseData> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "BaseData{" +
				"id='" + id + '\'' +
				", pid='" + pid + '\'' +
				", name='" + name + '\'' +
				", seq=" + seq +
				", basetypeCode='" + basetypeCode + '\'' +
				", codeName='" + codeName + '\'' +
				", description='" + description + '\'' +
				", children=" + children +
				'}';
	}
}

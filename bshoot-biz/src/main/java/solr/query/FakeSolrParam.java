package solr.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * solr参数包装类
 * Created by zhou on 2015/12/28.
 */
public class FakeSolrParam implements Serializable{
    public static final String SORT_ASC="asc";
    public static final String SORT_DESC="desc";

    private List<String> fq = new ArrayList<String>();//过滤查询条件
    private List<String> sortAsc = new ArrayList<String>();//升序字段
    private List<String> sortDesc = new ArrayList<String>();//降序字段
    private String format;//返回数据格式，json/xml/ruby..
    private List<String> fl;//查询返回的字段
    private String qc;//查询条件
    private String qt;//查询类型，默认stanard
    private int ot;// 默认排序条件 1:自定义排序 0:默认排序
    private int start=0;//开始位置
    private int rows = 20;//返回记录数
    private Map<String,Object> location;//地理位置
    private List<String> facetFieldList = new ArrayList<String>();//统计字段
    private List<String> facetSearchList = new ArrayList<String>();//统计查询条件
    private List<String> groupDesc = new ArrayList<String>();

    public List<String> getFq() {
        return fq;
    }

    public void setFq(List<String> fq) {
        this.fq = fq;
    }

    public List<String> getSortAsc() {
        return sortAsc;
    }

    public int getOt() {
        return ot;
    }

    public void setOt(int ot) {
        this.ot = ot;
    }

    public void setSortAsc(List<String> sortAsc) {
        this.sortAsc = sortAsc;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
    }

    public List<String> getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(List<String> sortDesc) {
        this.sortDesc = sortDesc;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<String> getFl() {
        return fl;
    }

    public void setFl(List<String> fl) {
        this.fl = fl;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getRows() {
        return rows;
    }

    public List<String> getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(List<String> groupDesc) {
        this.groupDesc = groupDesc;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Map<String, Object> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Object> location) {
        this.location = location;
    }

    public List<String> getFacetFieldList() {
        return facetFieldList;
    }

    public void setFacetFieldList(List<String> facetFieldList) {
        this.facetFieldList = facetFieldList;
    }

    public List<String> getFacetSearchList() {
        return facetSearchList;
    }

    public void setFacetSearchList(List<String> facetSearchList) {
        this.facetSearchList = facetSearchList;
    }
}

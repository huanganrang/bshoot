package solr.model.query;

import solr.model.criteria.Restrictions;

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
    private Location location;//地理位置
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Restrictions createRestrictions(){
        return new Restrictions(this);
    }

    @Override
    public String toString() {
        return "FakeSolrParam{" +
                "fq=" + fq +
                ", sortAsc=" + sortAsc +
                ", sortDesc=" + sortDesc +
                ", format='" + format + '\'' +
                ", fl=" + fl +
                ", qc='" + qc + '\'' +
                ", qt='" + qt + '\'' +
                ", ot=" + ot +
                ", start=" + start +
                ", rows=" + rows +
                ", location=" + location +
                ", facetFieldList=" + facetFieldList +
                ", facetSearchList=" + facetSearchList +
                ", groupDesc=" + groupDesc +
                '}';
    }

   public static class Location{
        private String field;//距离字段
        private String vale;//经纬度(维度-经度)，如：23.156,113.25
        private float distance=1;//该经纬度附件多少公里,默认1公里
        private String sort = SORT_ASC;//距离排序字段，默认升序

       public Location(String field, String vale, float distance, String sort) {
           this.field = field;
           this.vale = vale;
           this.distance = distance;
           this.sort = sort;
       }

       public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getVale() {
            return vale;
        }

        public void setVale(String vale) {
            this.vale = vale;
        }

       public float getDistance() {
           return distance;
       }

       public void setDistance(float distance) {
           this.distance = distance;
       }

       public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "field='" + field + '\'' +
                    ", vale='" + vale + '\'' +
                    ", distance=" + distance +
                    ", sort='" + sort + '\'' +
                    '}';
        }
    }
}

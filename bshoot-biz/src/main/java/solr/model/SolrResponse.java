package solr.model;

import java.util.List;

/**
 * solr操作响应
 * Created by Zhou Yibing on 2015/12/29.
 */
public class SolrResponse<T> {
    private List<T> docs;//返回的结果
    private int status;//返回状态
    private String msg;//返回的消息
    private String op;//操作类型，查询/更新
    private long qTime;//耗时
    private long numFound;//找到多少个文档

    public List getDocs() {
        return docs;
    }

    public void setDocs(List<T> docs) {
        this.docs = docs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public long getqTime() {
        return qTime;
    }

    public void setqTime(long qTime) {
        this.qTime = qTime;
    }

    public long getNumFound() {
        return numFound;
    }

    public void setNumFound(long numFound) {
        this.numFound = numFound;
    }

    @Override
    public String toString() {
        return "SolrResponse{" +
                "docs=" + docs +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", op='" + op + '\'' +
                ", qTime=" + qTime +
                ", numFound=" + numFound +
                '}';
    }
}

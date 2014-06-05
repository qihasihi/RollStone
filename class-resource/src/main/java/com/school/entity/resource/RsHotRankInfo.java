package com.school.entity.resource;


import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 资源排行数据
 * Created by zhengzhou on 14-6-3.
 */
@Entity
public class RsHotRankInfo implements   Serializable {
    private Long ref;
    private Long resid;
    private Long schoolid;
    private Integer type;
    private Long clicks;
    private Long commentnum;
    private Long downloadnum;
    private Long storenum;
    private Long praisenum;
    private Long recomendnum;
    private Long reportnum;

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public Long getResid() {
        return resid;
    }

    public void setResid(Long resid) {
        this.resid = resid;
    }

    public Long getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Long schoolid) {
        this.schoolid = schoolid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Long commentnum) {
        this.commentnum = commentnum;
    }

    public Long getDownloadnum() {
        return downloadnum;
    }

    public void setDownloadnum(Long downloadnum) {
        this.downloadnum = downloadnum;
    }

    public Long getStorenum() {
        return storenum;
    }

    public void setStorenum(Long storenum) {
        this.storenum = storenum;
    }

    public Long getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(Long praisenum) {
        this.praisenum = praisenum;
    }

    public Long getRecomendnum() {
        return recomendnum;
    }

    public void setRecomendnum(Long recomendnum) {
        this.recomendnum = recomendnum;
    }

    public Long getReportnum() {
        return reportnum;
    }

    public void setReportnum(Long reportnum) {
        this.reportnum = reportnum;
    }
}

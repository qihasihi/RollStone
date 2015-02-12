package com.school.entity.resource;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class ResourceInfo implements java.io.Serializable {
//    private List<ResourceFileInfo> resourceFileList;
    private String filename;
    private String ismicopiece;

//    public List<ResourceFileInfo> getResourceFileList() {
//        return (resourceFileList = (resourceFileList == null ? new ArrayList<ResourceFileInfo>() : resourceFileList));
//    }

   // public void setResourceFileList(List<ResourceFileInfo> resourceFile) {
//        this.resourceFileList = resourceFile;
//    }

    public void ResourceInfo() {
    }

    private Long resid;
    private Long resnum;

    private java.lang.String resname;
    private java.lang.String reskeyword;
    private java.lang.String resintroduce;
    private java.lang.Integer resstatus; // 0:已删除
    private java.lang.Integer sharestatus;
    private java.lang.Integer resdegree;

    private Integer currentloginsubid;
    private Integer currentlogingrdid;
    private String sharestatusvalues;

    private Float resscore;
    private UserInfo userinfo;
    private java.lang.Integer usertype;
    private BigDecimal avgscore;

    private String rcstate; //推荐状态
    private java.lang.Long accesstotal;
    private String extendvalues;

    private java.lang.Integer commentnum;
    private java.lang.Integer clicks;
    private java.lang.Integer storenum;
    private java.lang.Integer downloadnum;
    private java.lang.Integer praisenum;
    private java.lang.Integer recomendnum;
    private java.lang.Integer reportnum;

    private java.lang.Integer netcommentnum;
    private java.lang.Integer netclicks;
    private java.lang.Integer netstorenum;
    private java.lang.Integer netdownloadnum;
    private java.lang.Integer netpraisenum;
    private java.lang.Integer netrecomendnum;
    private java.lang.Integer netreportnum;
    private java.lang.Integer netsharestatus; // 0:已删除
    /**
     * 当前的一段时间内的点击数
     */
    private Object currentClicks;

    private String filesuffixname;
    private Long filesize;
    private String useobject;

    private Integer restype;
    private Integer filetype;
    private Integer grade;
    private Integer subject;
    private Integer sourceType;

    private String restypevalues;  //资源类型-多条件
    private String filetypevalues; //文件类型-多条件
    private String gradevalues;   //年级-多条件
    private String subjectvalues; //学科-多条件
    private String versionvalues;

    private String restypename;  //资源类型名称
    private String filetypename; //文件类型名称
    private String gradename;   //年级名称
    private String subjectname; //学科名称
    private Integer isunion; //关联专题教材查询资源
    private Integer convertstatus;
    private Integer difftype;   //区分类型
    private Integer materialid; //教材编号

    public Integer getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Integer materialid) {
        this.materialid = materialid;
    }

    public Integer getDcschoolid() {
        return dcschoolid;
    }

    public void setDcschoolid(Integer dcschoolid) {
        this.dcschoolid = dcschoolid;
    }

    private Integer dcschoolid;
    public Integer getDifftype() {
        return difftype;
    }

    public void setDifftype(Integer difftype) {
        this.difftype = difftype;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    private Integer isrecommend;//是否是查询推荐资源


    public Integer getConvertstatus() {
        return convertstatus;
    }

    public void setConvertstatus(Integer convertstatus) {
        this.convertstatus = convertstatus;
    }

    public Integer getIsunion() {
        return isunion;
    }

    public void setIsunion(Integer isunion) {
        this.isunion = isunion;
    }

    public String getVersionvalues() {
        return versionvalues;
    }

    public void setVersionvalues(String versionvalues) {
        this.versionvalues = versionvalues;
    }

    public Integer getIsrecommend() {
        return isrecommend;
    }

    public void setIsrecommend(Integer isrecommend) {
        this.isrecommend = isrecommend;
    }

    private String username;
    private String schoolname;

    private String userobject;

    private java.util.Date ctime;
    private java.util.Date mtime;


    private String type;     //文件后缀名过滤
    private Boolean reverse;    //文件后缀过滤是否反选
    private Long courseid;
    private Long currentcourseid;
    private Object resflag;
    private Object voteflag;
    private Integer voteuid;

    public Integer getVoteuid() {
        return voteuid;
    }

    public void setVoteuid(Integer voteuid) {
        this.voteuid = voteuid;
    }

    public Object getVoteflag() {
        return voteflag;
    }

    public void setVoteflag(Object voteflag) {
        this.voteflag = voteflag;
    }

    public Object getResflag() {
        return resflag;
    }

    public void setResflag(Object resflag) {
        this.resflag = resflag;
    }

    public Long getCurrentcourseid() {

        return currentcourseid;
    }

    public void setCurrentcourseid(Long currentcourseid) {
        this.currentcourseid = currentcourseid;
    }

    private String coursename;
    public Long getResnum() {
        return resnum;
    }

    public String getSharestatusvalues() {
        return sharestatusvalues;
    }

    public void setSharestatusvalues(String sharestatusvalues) {
        this.sharestatusvalues = sharestatusvalues;
    }

    public Integer getCurrentloginsubid() {
        return currentloginsubid;
    }

    public void setCurrentloginsubid(Integer currentloginsubid) {
        this.currentloginsubid = currentloginsubid;
    }

    public Integer getCurrentlogingrdid() {
        return currentlogingrdid;
    }

    public void setCurrentlogingrdid(Integer currentlogingrdid) {
        this.currentlogingrdid = currentlogingrdid;
    }

    public void setResnum(Long resnum) {
        this.resnum = resnum;
    }

    public String getUserobject() {
        return userobject;
    }

    public void setUserobject(String userobject) {
        this.userobject = userobject;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public String getRestypename() {
        return restypename;
    }

    public void setRestypename(String restypename) {
        this.restypename = restypename;
    }

    public String getFiletypename() {
        return filetypename;
    }

    public void setFiletypename(String filetypename) {
        this.filetypename = filetypename;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getRestypevalues() {
        return restypevalues;
    }

    public void setRestypevalues(String restypevalues) {
        this.restypevalues = restypevalues;
    }

    public String getFiletypevalues() {
        return filetypevalues;
    }

    public void setFiletypevalues(String filetypevalues) {
        this.filetypevalues = filetypevalues;
    }

    public String getGradevalues() {
        return gradevalues;
    }

    public void setGradevalues(String gradevalues) {
        this.gradevalues = gradevalues;
    }

    public String getSubjectvalues() {
        return subjectvalues;
    }

    public void setSubjectvalues(String subjectvalues) {
        this.subjectvalues = subjectvalues;
    }

    public Boolean getReverse() {
        return reverse;
    }

    public void setReverse(Boolean reverse) {
        this.reverse = reverse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getResdegree() {
        return resdegree;
    }

    public void setResdegree(Integer resdegree) {
        this.resdegree = resdegree;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public Integer getRestype() {
        return restype;
    }

    public void setRestype(Integer restype) {
        this.restype = restype;
    }

    public Integer getFiletype() {
        return filetype;
    }

    public void setFiletype(Integer filetype) {
        this.filetype = filetype;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getNetsharestatus() {
        return netsharestatus;
    }

    public void setNetsharestatus(Integer netsharestatus) {
        this.netsharestatus = netsharestatus;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public String getUseobject() {
        return useobject;
    }

    public void setUseobject(String useobject) {
        this.useobject = useobject;
    }

    public Integer getNetcommentnum() {
        return netcommentnum;
    }

    public void setNetcommentnum(Integer netcommentnum) {
        this.netcommentnum = netcommentnum;
    }

    public Integer getNetclicks() {
        return netclicks;
    }

    public void setNetclicks(Integer netclicks) {
        this.netclicks = netclicks;
    }

    public Integer getNetstorenum() {
        return netstorenum;
    }

    public void setNetstorenum(Integer netstorenum) {
        this.netstorenum = netstorenum;
    }

    public Integer getNetdownloadnum() {
        return netdownloadnum;
    }

    public void setNetdownloadnum(Integer netdownloadnum) {
        this.netdownloadnum = netdownloadnum;
    }

    public Integer getNetpraisenum() {
        return netpraisenum;
    }

    public void setNetpraisenum(Integer netpraisenum) {
        this.netpraisenum = netpraisenum;
    }

    public Integer getNetrecomendnum() {
        return netrecomendnum;
    }

    public void setNetrecomendnum(Integer netrecomendnum) {
        this.netrecomendnum = netrecomendnum;
    }

    public Integer getNetreportnum() {
        return netreportnum;
    }

    public void setNetreportnum(Integer netreportnum) {
        this.netreportnum = netreportnum;
    }

    public java.lang.Integer getReportnum() {
        return reportnum;
    }

    public void setReportnum(java.lang.Integer reportnum) {
        this.reportnum = reportnum;
    }

    public java.lang.Integer getDownloadnum() {
        return downloadnum;
    }

    public java.lang.Integer getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(java.lang.Integer praisenum) {
        this.praisenum = praisenum;
    }

    public java.lang.Integer getRecomendnum() {
        return recomendnum;
    }

    public void setRecomendnum(java.lang.Integer recomendnum) {
        this.recomendnum = recomendnum;
    }

    public void setDownloadnum(java.lang.Integer downloadnum) {
        this.downloadnum = downloadnum;
    }

    public String getFilesuffixname() {
        return filesuffixname;
    }

    public void setFilesuffixname(String filename) {
        this.filesuffixname = filename;
    }

    public String getExtendvalues() {
        return extendvalues;
    }

    public void setExtendvalues(String extendvalues) {
        this.extendvalues = extendvalues;
    }

    public java.lang.Long getAccesstotal() {
        return accesstotal;
    }

    public void setAccesstotal(java.lang.Long accesstotal) {
        this.accesstotal = accesstotal;
    }

    public String getRcstate() {
        return rcstate;
    }

    public void setRcstate(String rcstate) {
        this.rcstate = rcstate;
    }

    public java.lang.Integer getStorenum() {
        return storenum;
    }

    public void setStorenum(java.lang.Integer storenum) {
        this.storenum = storenum;
    }

    public java.lang.Integer getClicks() {
        return clicks;
    }

    public void setClicks(java.lang.Integer clicks1) {
        this.clicks = clicks1;
    }
    public String getResname25word(){
        if(this.getResname()==null)return "";
        if(this.getResname().length()>25)
            return this.getResname().substring(0,25)+"...";
        return this.getResname();
    }
    public String getResname13word(){
        if(this.getResname()==null)return "";
        if(this.getResname().length()>13)
            return this.getResname().substring(0,13)+"...";
        return this.getResname();
    }

    public String getSwfpath(){
        if(this.getResid()==null||this.getFilesuffixname()==null)
            return "";
        //String file=UtilTool.gender_MD5(this.getFilename().replaceAll("'"," ").replaceAll(","," ").trim());
        String file="001";
        // return UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD")+"uploadfile/"+UtilTool.getResourceMd5Directory(this.getResid().toString())+"/"+file+".swf";

        return UtilTool.getResourceLocation(null,this.getResid(),1)+"/"+UtilTool.getResourceMd5Directory(this.getResid().toString())+"/"+file+".swf";
    }

    /**
     * 得到文件夹名称
     * @return
     */
    public String getPath(){
        if(this.getResid()==null)return "";
        return UtilTool.getResourceMd5Directory(this.getResid()+"");
    }

    public BigDecimal getAvgscore() {
        if (this.resscore != null)
            return new BigDecimal(resscore);
        return new BigDecimal(0);
    }

    public void setAvgscore(BigDecimal avgscore) {
        this.avgscore = avgscore;
    }

    public UserInfo getUserinfo() {
        return (userinfo = (userinfo == null ? new UserInfo() : userinfo));
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public Integer getUserid() {
        return this.getUserinfo().getUserid();
    }

    public void setUserid(java.lang.Integer userid) {
        this.getUserinfo().setUserid(userid);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserref(String userref) {
        this.getUserinfo().setRef(userref);
    }

    public String getUserref() {
        return this.getUserinfo().getRef();
    }

    public java.lang.String getResintroduce() {
        if(resintroduce!=null){
            resintroduce=resintroduce.replaceAll("\\\\","/").replaceAll("\'","’");
        }
        return resintroduce;
    }

    public void setResintroduce(java.lang.String resintroduce) {
        this.resintroduce = resintroduce;
    }

    public Long getResid() {
        return resid;
    }

    public void setResid(Long resid) {
        this.resid = resid;
    }

    public java.lang.String getReskeyword() {
        return reskeyword;
    }

    public void setReskeyword(java.lang.String reskeyword) {
        this.reskeyword = reskeyword;
    }

    public java.util.Date getCtime() {
        return ctime;
    }

    public void setCtime(java.util.Date ctime) {
        this.ctime = ctime;
    }


    public Float getResscore() {
        return resscore;
    }

    public void setResscore(Float resscore) {
        this.resscore = resscore;
    }

    public Integer getSharestatus() {
        return sharestatus;
    }

    public void setSharestatus(Integer sharestatus) {
        this.sharestatus = sharestatus;
    }

    public Integer getResstatus() {
        return resstatus;
    }

    public void setResstatus(Integer resstatus) {
        this.resstatus = resstatus;
    }

    public java.lang.String getResname() {
        if(resname!=null&&resname.trim().length()>0)
            return resname.replaceAll("\'","’");
        return resname;
    }

    public void setResname(java.lang.String resname) {
        this.resname = resname;
    }

    public java.util.Date getMtime() {
        return mtime;
    }

    public void setMtime(java.util.Date mtime) {
        this.mtime = mtime;
    }

    public String getCtimeString() {
        return (ctime == null ? "" : UtilTool.DateConvertToString(ctime,
                DateType.type1));
    }

    public String getMtimeString() {
        return (mtime == null ? "" : UtilTool.DateConvertToString(mtime,
                DateType.type1));
    }

    public void setRealname(String realname) {
        this.getUserinfo().setRealname(realname);
    }

    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
        this.commentnum = commentnum;
    }

    public String getRealname() {
        return this.getUserinfo().getRealname();
    }

    /**
     * 得到文件上传离现在的时间
     *
     * @return
     */
    public String getHowLongWithTaday() {
        String returnValue = "";
        if (this.getCtime() == null)
            return returnValue;
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long days = (new Date().getTime() - this.getCtime().getTime()) / nd;
        if (days == 0)
            returnValue = "今天";
        else if (days == 1)
            returnValue = "昨天";
        else if (days == 2)
            returnValue = "前天";
        else
            returnValue = days + "天前";
        return returnValue;
    }

    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename){
        this.filename=filename;
    }

    public String getImagepath() {
		if(this.getFilesuffixname()==null)return null;
        String imagepath = "";
        if(!UtilTool.getResourseType(this.getFilesuffixname()).equals("video"))
                    return null;
        imagepath =UtilTool.getResourceLocation(null,this.getResid(),1)
                    + this.getPath()+"/001"
                    + this.getFilesuffixname() + ".pre.jpg";
            return imagepath;
	}


    public String getResourseType() {
        if (this.getFilesuffixname()== null)
            return null;
        //this.getFilename().substring(this.getFilename().lastIndexOf("."));
        String suffix=this.getFilesuffixname();
        boolean flag = UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "jpeg";
        flag = UtilTool.matchingText(UtilTool._VIEW_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "video";
        flag = UtilTool.matchingText(UtilTool._MP3_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "mp3";
        flag = UtilTool.matchingText(UtilTool._DOC_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "doc";
        flag = UtilTool.matchingText(UtilTool._SWF_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "swf";
        return "other";
    }

    /**
     * <span class="ico_txt1"></span>
     <span class="ico_ppt1"></span>
     <span class="ico_rtf1"></span>
     <span class="ico_vsd1"></span>
     <span class="ico_mp31"></span>
     <span class="ico_swf1"></span>
     <span class="ico_mp41"></span>
     <span class="ico_jpg1"></span>
     <span class="ico_wps1"></span>
     * @return
     */

    public String getSuffixtype(){
        if(this.getResourseType()=="jpeg"){
            return "ico_jpg1";
        }else if(this.getResourseType()=="video"){
            return "ico_mp41";
        }else if(this.getResourseType()=="mp3"){
            return "ico_mp31";
        }else if(this.getResourseType()=="doc"){
            return "ico_wps1";
        }else if(this.getResourseType()=="swf"){
            return "ico_swf1";
        }else{
            return "ico_txt1";
        }
    }

    public Object getCurrentClicks() {
        return currentClicks;
    }

    public void setCurrentClicks(Object currentClicks) {
        this.currentClicks = currentClicks;
    }

    public void setIsmicopiece(String ismicopiece) {
        this.ismicopiece = ismicopiece;
    }

    public String getIsmicopiece() {
        return ismicopiece;
    }
}
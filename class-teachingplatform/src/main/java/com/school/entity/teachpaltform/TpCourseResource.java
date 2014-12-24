package  com.school.entity.teachpaltform;

import com.school.entity.resource.ResourceInfo;
import com.school.util.UtilTool;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.security.util.BigInt;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TpCourseResource implements Serializable {

	public void TpCourseResource(){}
   
    private java.lang.Long ref;
    private Date ctime;
    private Integer  resourcetype;  //1：学习资源  2：教学参考
    private ResourceInfo resourceinfo;
    private String realname;
    private  Object taskflag;       //任务发布状态
    private String coursename;
    private Long referenceid;   //引用专题ID
    private Long courseid;
    private Long quoteid;
    private Object avgscore;
    private String username;
    private Integer localstatus;
    private Date operatetime;
    private String courseids;//查询多个专题下资源用的
    private Long taskCourseid;//在这个专题下是否发过任务
    private Object haspaper;//是否有关联试卷
    private Object voteflag; //是否赞
    private Integer voteuid;

    public Object getVoteflag() {
        return voteflag;
    }

    public void setVoteflag(Object voteflag) {
        this.voteflag = voteflag;
    }

    public Integer getVoteuid() {
        return voteuid;
    }

    public void setVoteuid(Integer voteuid) {
        this.voteuid = voteuid;
    }

    public Object getHaspaper() {
        return haspaper;
    }

    public void setHaspaper(Object haspaper) {
        this.haspaper = haspaper;
    }

    public Long getTaskCourseid() {
        return taskCourseid;
    }

    public void setTaskCourseid(Long taskCourseid) {
        this.taskCourseid = taskCourseid;
    }

    public String getCourseids() {
        return courseids;
    }

    public void setCourseids(String courseids) {
        this.courseids = courseids;
    }

    private Long currentcourseid;   //查询条件：通过专题资源、资源库查找资源使用
    private Object resflag;         //显示条件与currentcourseid配合使用   大于0表示已存在于当前专题
    private Integer seldatetype;    //查询日期条件

    public Integer getSeldatetype() {
        return seldatetype;
    }

    public void setSeldatetype(Integer seldatetype) {
        this.seldatetype = seldatetype;
    }

    public Long getCurrentcourseid() {
        return currentcourseid;
    }

    public void setCurrentcourseid(Long currentcourseid) {
        this.currentcourseid = currentcourseid;
    }

    public Object getResflag() {
        return resflag;
    }

    public void setResflag(Object resflag) {
        this.resflag = resflag;
    }

    public Integer getLocalstatus() {
        return localstatus;
    }

    public void setLocalstatus(Integer localstatus) {
        this.localstatus = localstatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getRestypename(){//资源类型名称
        return this.getResourceinfo().getRestypename();
    }
    public void setRestypename(String restypename){//资源类型名称
        this.getResourceinfo().setRestypename(restypename);
    }
    public String getFiletypename(){//文件类型名称
        return this.getResourceinfo().getFiletypename();
    }
    public void setFiletypename(String filtypename){//文件类型名称
        this.getResourceinfo().setFiletypename(filtypename);
    }
    public Integer getRestype(){
        return this.getResourceinfo().getRestype();
    }
    public void setRestype(Integer restype){
        this.getResourceinfo().setRestype(restype);
    }
    public void setIsmicopiece(String ismicopiece){
        this.getResourceinfo().setIsmicopiece(ismicopiece);
    }

    public String getIsmicopiece() {
        return this.getResourceinfo().getIsmicopiece();
    }

    public Object getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(Object avgscore) {
        this.avgscore = avgscore;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Long getQuoteid() {
        return quoteid;
    }

    public void setQuoteid(Long quoteid) {
        this.quoteid = quoteid;
    }

    private Integer courselevel;

    public Integer getCourselevel() {
        return courselevel;
    }

    public void setCourselevel(Integer courselevel) {
        this.courselevel = courselevel;
    }

    public Long getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(Long referenceid) {
        this.referenceid = referenceid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    private  Integer userid;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Object getTaskflag() {
        return taskflag;
    }

    public void setTaskflag(Object taskflag) {
        this.taskflag = taskflag;
    }

    public Integer getResdegree(){
        return this.getResourceinfo().getResdegree();
    }
    public void setResdegree(Integer resdegree){
        this.getResourceinfo().setResdegree(resdegree);
    }
    public String getResintroduce(){
        return this.getResourceinfo().getResintroduce();
    }
    public void setResintroduce(String resintroduce){
        this.getResourceinfo().setResintroduce(resintroduce);
    }
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSwfpath(){
        if(this.getResid()==null||this.getResourceinfo().getFilesuffixname()==null)
            return "";
        //String file=UtilTool.gender_MD5(this.getFilename().replaceAll("'"," ").replaceAll(","," ").trim());
        String file="001";
       // return UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD")+"uploadfile/"+UtilTool.getResourceMd5Directory(this.getResid().toString())+"/"+file+".swf";

        return UtilTool.getResourceLocation(this.getResid(),1)+"/"+UtilTool.getResourceMd5Directory(this.getResid().toString())+"/"+file+".swf";
    }
    public String getImgpath(){
        return UtilTool.getResourceLocation(this.getResid(),1)+"/"+UtilTool.getResourceMd5Directory(this.getResid().toString())+"/001.jpg";
    }



    public Integer getResstatus(){
        return this.getResourceinfo().getResstatus();
    }
    public void setResstatus(Integer status){
        this.getResourceinfo().setResstatus(status);
    }
    public Integer getUsertype(){
        return this.getResourceinfo().getUsertype();
    }
    public void setUsertype(Integer usertype){
        this.getResourceinfo().setUsertype(usertype);
    }
    public String getCtimeString(){
        if(ctime==null)
            return "";
        return UtilTool.DateConvertToString(ctime, UtilTool.DateType.type1);
    }
    public String getMtimeString(){
        if(operatetime==null)
            return "";
        return UtilTool.DateConvertToString(operatetime, UtilTool.DateType.type1);
    }


    public Integer getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(Integer resourcetype) {
        this.resourcetype = resourcetype;
    }



    public ResourceInfo getResourceinfo() {
        if(resourceinfo==null)
            resourceinfo=new ResourceInfo();
        return resourceinfo;
    }

    public void setResourceinfo(ResourceInfo resourceinfo) {
        this.resourceinfo = resourceinfo;
    }

    public java.lang.Long getRef(){
      return ref;
    }
    public void setRef(java.lang.Long ref){
      this.ref = ref;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }

    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public Long getResid(){
      return this.getResourceinfo().getResid();

    }
    public void setResid(Long resid){
      this.getResourceinfo().setResid(resid);
    }

    //关联字段
    public String getResname(){
        return this.getResourceinfo().getResname();
    }
    public void setResname(String resname){
        this.getResourceinfo().setResname(resname);
    }

    public Integer getClicks(){
        return this.getResourceinfo().getClicks();
    }
    public void setClicks(Integer click){
        this.getResourceinfo().setClicks(click);
    }
    public Integer getDownloadnum(){
        return this.getResourceinfo().getDownloadnum();
    }
    public void setDownloadnum(Integer downloadnum){
        this.getResourceinfo().setDownloadnum(downloadnum);
    }
    public Integer getPraisenum(){
        return this.getResourceinfo().getPraisenum();
    }
    public void setPraisenum(Integer praisenum){
        this.getResourceinfo().setPraisenum(praisenum);
    }

    public String getFilesuffixname(){
        return this.getResourceinfo().getFilesuffixname();
    }
    public void setFilesuffixname(String filesuffixname){
        this.getResourceinfo().setFilesuffixname(filesuffixname);
    }
    public Long getFilesize(){
        return this.getResourceinfo().getFilesize();
    }
    public void setFilesize(Long filesize){
        this.getResourceinfo().setFilesize(filesize);
    }
    public Integer getDifftype(){
        return this.getResourceinfo().getDifftype();
    }
    public  void setDifftype(Integer difftype){
        this.getResourceinfo().setDifftype(difftype);
    }
    public String getPath(){
        return this.getResourceinfo().getPath();
    }

    public Integer getSharestatus(){
        return this.getResourceinfo().getSharestatus();
    }
    public  void setSharestatus(Integer sharestatus){
        this.getResourceinfo().setSharestatus(sharestatus);
    }



    private List<Object> viewResdocumentSizeList = new ArrayList<Object>();
    private List<Object> mp3ResdocumentSize = new ArrayList<Object>();
    private List<Object> otherResdocumentSize = new ArrayList<Object>();
    private List<Object> imgResdocumentSizeList = new ArrayList<Object>();
    private List<Object> docResdocumentSizeList = new ArrayList<Object>();
    private List<Object> swfResdocumentSizeList = new ArrayList<Object>();



    private List<String> viewResDetailIdList = new ArrayList<String>();
    private List<String> mp3ResDetailIdList = new ArrayList<String>();
    private List<String> otherResDetailIdList = new ArrayList<String>();
    private List<String> imgResDetailIdList = new ArrayList<String>();
    private List<String> docResDetailIdList = new ArrayList<String>();
    private List<String> swfResDetailIdList = new ArrayList<String>();

    public List<Object> getSwfResdocumentSizeList() {
        return swfResdocumentSizeList;
    }
    public void setSwfResdocumentSizeList(List<Object> swfResdocumentSizeList) {
        this.swfResdocumentSizeList = swfResdocumentSizeList;
    }
    public List<String> getSwfResDetailIdList() {
        return swfResDetailIdList;
    }
    public void setSwfResDetailIdList(List<String> swfResDetailIdList) {
        this.swfResDetailIdList = swfResDetailIdList;
    }
    public List<Object> getDocResdocumentSizeList() {
        return docResdocumentSizeList;
    }
    public void setDocResdocumentSizeList(List<Object> docResdocumentSizeList) {
        this.docResdocumentSizeList = docResdocumentSizeList;
    }
    public List<String> getDocResDetailIdList() {
        return docResDetailIdList;
    }
    public void setDocResDetailIdList(List<String> docResDetailIdList) {
        this.docResDetailIdList = docResDetailIdList;
    }
    public List<String> getViewResDetailIdList() {
        return viewResDetailIdList;
    }

    public void setViewResDetailIdList(List<String> viewResDetailIdList) {
        this.viewResDetailIdList = viewResDetailIdList;
    }

    public List<String> getMp3ResDetailIdList() {
        return mp3ResDetailIdList;
    }

    public void setMp3ResDetailIdList(List<String> mp3ResDetailIdList) {
        this.mp3ResDetailIdList = mp3ResDetailIdList;
    }

    public List<String> getOtherResDetailIdList() {
        return otherResDetailIdList;
    }

    public void setOtherResDetailIdList(List<String> otherResDetailIdList) {
        this.otherResDetailIdList = otherResDetailIdList;
    }

    public List<String> getImgResDetailIdList() {
        return imgResDetailIdList;
    }

    public void setImgResDetailIdList(List<String> imgResDetailIdList) {
        this.imgResDetailIdList = imgResDetailIdList;
    }


    public List<Object> getImgResdocumentSizeList() {
        return imgResdocumentSizeList;
    }

    public void setImgResdocumentSizeList(List<Object> imgResdocumentSizeList) {
        this.imgResdocumentSizeList = imgResdocumentSizeList;
    }

    public List<Object> getViewResdocumentSizeList() {
        return viewResdocumentSizeList;
    }

    public void setViewResdocumentSizeList(List<Object> viewResdocumentSizeList) {
        this.viewResdocumentSizeList = viewResdocumentSizeList;
    }

    public List<Object> getMp3ResdocumentSize() {
        return mp3ResdocumentSize;
    }

    public void setMp3ResdocumentSize(List<Object> mp3ResdocumentSize) {
        this.mp3ResdocumentSize = mp3ResdocumentSize;
    }

    public List<Object> getOtherResdocumentSize() {
        return otherResdocumentSize;
    }

    public void setOtherResdocumentSize(List<Object> otherResdocumentSize) {
        this.otherResdocumentSize = otherResdocumentSize;
    }


    public List<String>getSwfResDocumentNameList(){
        return this.getResDocumentByType(5);
    }

    public List<String>getDocResDocumentNameList(){
        return this.getResDocumentByType(4);
    }

    public List<String>getMp3ResDocumentNameList(){
        return this.getResDocumentByType(3);
    }

    public List<String>getViewResDocumentNameList(){
        return this.getResDocumentByType(2);
    }
    public List<String>getImgResDocumentNameList(){
        return this.getResDocumentByType(1);
    }
    public List<String>getOtherResDocumentNameList(){
        return this.getResDocumentByType(0);
    }

    /**
     * 获取文件名称列表
     * @return
     */
    public List<String> getResDocumentNameList() {
        if (this.getResourceinfo().getFilesuffixname() == null)
            return null;
        List<String> strList = new ArrayList<String>();
        if (this.getResourceinfo().getFilesuffixname().indexOf(",") != -1) {
            String[] arg = this.getResourceinfo().getFilesuffixname().split(",");
            if (arg != null && arg.length > 0) {
                for (String a : arg) {
                    strList.add(a);
                }
            }
        } else
            strList.add(this.getResourceinfo().getFilesuffixname());

        return strList;
    }
    /**
     * 获取文件大小列表
     */
    public List<Object> getResDocumentSizeList() {
        if (this.getResourceinfo().getFilesize() == null)
            return null;
        List<Object> strList = new ArrayList<Object>();
        if (this.getResourceinfo().getFilesize().toString().indexOf(",") != -1) {
            String[] arg = this.getResourceinfo().getFilesize().toString().split(",");
            if (arg != null && arg.length > 0) {
                for (String a : arg) {
                    strList.add(a);
                }
            }
        } else
            strList.add(this.getResourceinfo().getFilesize());

        return strList;
    }

    /**
     * 获取资源详情ID列表
     */
    public List<String> getResIdList() {
        if (this.getResid() == null)
            return null;
        List<String> strList = new ArrayList<String>();
        if (this.getResid().toString().indexOf(",") != -1) {
            String[] arg = this.getResid().toString().split(",");
            if (arg != null && arg.length > 0) {
                for (String a : arg) {
                    strList.add(a);
                }
            }
        } else
            strList.add(this.getResid().toString());

        return strList;
    }



    /**
     * 根据类型获取
     * @param type 0其他 1图片 2视频 3音频4文档5swf
     * @return
     */
    public List<String>getResDocumentByType(int type){
        if(this.getResDocumentNameList()==null||
                this.getResDocumentNameList().size()<1){
            return null;
        }
        List<String>returnVal=new ArrayList<String>();
        String reg=null;
        if(type==1){
            reg= UtilTool._IMG_SUFFIX_TYPE_REGULAR;
        }else if(type==2){
            reg=UtilTool._VIEW_SUFFIX_TYPE_REGULAR;
        }else if(type==3){
            reg=UtilTool._MP3_SUFFIX_TYPE_REGULAR;
        }else if(type==4){
            reg=UtilTool._DOC_SUFFIX_TYPE_REGULAR;
        }else if(type==5){
            reg=UtilTool._SWF_SUFFIX_TYPE_REGULAR;
        }
        if(reg!=null){
            for(int i=0;i<this.getResDocumentNameList().size();i++){
                if(getResDocumentNameList().get(i).lastIndexOf(".")!=-1){
                    String suffix=getResDocumentNameList().get(i).substring(
                            getResDocumentNameList().get(i).lastIndexOf(".")).toLowerCase();
                    boolean flag=UtilTool.matchingText(reg, suffix);
                    if(flag){
                        returnVal.add(getResDocumentNameList().get(i));
                        if (getResDocumentSizeList() != null
                                && getResDocumentSizeList().size() >= (i + 1)) {
                            if (type == 2){
                                viewResdocumentSizeList
                                        .add(getResDocumentSizeList().get(i));
                                viewResDetailIdList.add(getResIdList().get(i));
                            }else if (type == 1){
                                imgResdocumentSizeList
                                        .add(getResDocumentSizeList().get(i));
                                imgResDetailIdList.add(getResIdList().get(i));
                            }else if (type == 3){
                                mp3ResdocumentSize.add(getResDocumentSizeList()
                                        .get(i));
                                mp3ResDetailIdList.add(getResIdList().get(i));
                            }else if (type == 4){
                                docResdocumentSizeList.add(getResDocumentSizeList()
                                        .get(i));
                                docResDetailIdList.add(getResIdList().get(i));
                            }else if (type == 5){
                                swfResdocumentSizeList.add(getResDocumentSizeList()
                                        .get(i));
                                swfResDetailIdList.add(getResIdList().get(i));
                            }
                        }
                    }
                }
            }
        }else{
            int i=0;
            for (String doc : this.getResDocumentNameList()) {
                if(doc.lastIndexOf(".")!=-1){
                    String suffix=doc.substring(doc.lastIndexOf(".")).toLowerCase();
                    reg=UtilTool._IMG_SUFFIX_TYPE_REGULAR;
                    boolean flag=UtilTool.matchingText(reg, suffix);
                    if(!flag){
                        reg=UtilTool._VIEW_SUFFIX_TYPE_REGULAR;
                        flag=UtilTool.matchingText(reg, suffix);
                        if(!flag){
                            reg=UtilTool._MP3_SUFFIX_TYPE_REGULAR;
                            flag=UtilTool.matchingText(reg, suffix);
                            if(!flag){
                                reg=UtilTool._DOC_SUFFIX_TYPE_REGULAR;
                                flag=UtilTool.matchingText(reg, suffix);
                                if(!flag){
                                    reg=UtilTool._SWF_SUFFIX_TYPE_REGULAR;
                                    flag=UtilTool.matchingText(reg, suffix);
                                    if(!flag){
                                        returnVal.add(doc);
                                        if (getResDocumentSizeList() != null
                                                && getResDocumentSizeList().size() >= (i + 1)) {
                                            otherResdocumentSize
                                                    .add(getResDocumentSizeList()
                                                            .get(i));
                                            otherResDetailIdList.add(getResIdList().get(i));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                i++;
            }
        }
        return returnVal;
    }



    public String getMd5filename(){
          if(this.getResourceinfo().getFilesuffixname()==null)
            return null;
//        return UtilTool.gender_MD5(this.getFilename());
           return "001"+this.getResourceinfo().getFilesuffixname();
    }
    public String getMd5Id(){
        if(this.getResid()==null)
            return null;
        //("zhengzhou"+this.getResourceid());
        return UtilTool.getResourceMd5Directory(this.getResid().toString());
    }
    public String getMd5mp3file(){
        if(this.getResourceinfo().getFilesuffixname()==null)
            return null;
//        String suffix=null;
//        if(this.getFilename().indexOf(".")!=-1){
//            suffix=this.getFilename().substring(this.getFilename().lastIndexOf("."));
//        }
//        return UtilTool.gender_MD5(this.getFilename())+suffix;
        return "001"+this.getResourceinfo().getFilesuffixname();
    }

    public String getResourseType() {
        if (this.getResourceinfo().getFilesuffixname()== null)
            return null;
        //this.getFilename().substring(this.getFilename().lastIndexOf("."));
        String suffix=this.getResourceinfo().getFilesuffixname();
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
        if(this.getResourseType().equals("jpeg")){
            return "ico_jpg1";
        }else if(this.getResourseType().equals("video")&&
                ((this.getDifftype()!=null&&this.getDifftype().toString().equals("0"))||
                        (this.getDifftype()!=null&&this.getDifftype()==1&&this.getResid()<1)||
                        (this.getDifftype()!=null&&this.getDifftype()==1&&this.getResid()>0&&Integer.parseInt(this.haspaper.toString())<1)
                )){
            return "ico_mp41";
        }else if(this.getResourseType().equals("mp3")){
            return "ico_mp31";
        }else if(this.getResourseType().equals("doc")){
            String suffix=this.getResourceinfo().getFilesuffixname();
            if(suffix.indexOf("xls")!=-1)
                return "ico_xls1";
            else if(suffix.indexOf("pds")!=-1)
                return "ico_pds1";
            else if(suffix.indexOf("txt")!=-1)
                return "ico_txt1";
            else if(suffix.indexOf("ppt")!=-1)
                return "ico_ppt1";
            else if(suffix.indexOf("rtf")!=-1)
                return "ico_rtf1";
            else if(suffix.indexOf("wps")!=-1)
                return "ico_wps1";
            else if(suffix.indexOf("doc")!=-1)
                return "ico_doc1";
            else if(suffix.indexOf("vsd")!=-1)
                return "ico_vsd1";
            return "ico_wps1";
        }else if(this.getResourseType().equals("swf")){
            return "ico_swf1";
        }else if(this.getDifftype()!=null&&this.getDifftype()==1
                &&this.getResid()>0
                &&Integer.parseInt(this.haspaper.toString())>0){
            return "ico_wsp1";
        }else{
            return "ico_other1";
        }
    }
}
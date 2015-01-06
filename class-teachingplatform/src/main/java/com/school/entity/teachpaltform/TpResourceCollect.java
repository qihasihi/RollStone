package com.school.entity.teachpaltform;

import java.util.Date;

import javax.persistence.Entity;

import com.school.entity.UserInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;
@Entity
public class TpResourceCollect  implements java.io.Serializable{

   
    private Date ctime;
    private Integer collectid;
    private UserInfo userinfo;
    private ResourceInfo resourceinfo;
    private Long courseid;
    private Object viewcount;
    private Object rankscore;
    private String coursename;
    private String resname;



    public String getFilesuffixname() {
        return this.getResourceinfo().getFilesuffixname();
    }
    public void setFilesuffixname(String filesuffixname) {
        this.getResourceinfo().setFilesuffixname(filesuffixname);
    }

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
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
    public String getSwfpath(){
        if(this.getResid()==null||this.getResourceinfo().getFilesuffixname()==null)
            return "";
        //String file=UtilTool.gender_MD5(this.getFilename().replaceAll("'"," ").replaceAll(","," ").trim());
        String file="001";
        return UtilTool.getResourceLocation(null,this.getResid(),1)+"/"+UtilTool.getResourceMd5Directory(this.getResid().toString())+"/"+file+".swf";
    }
    public String getImgpath(){
        return UtilTool.getResourceLocation(null,this.getResid(),1)+"/"+UtilTool.getResourceMd5Directory(this.getResid().toString())+"/001_smail.jpg";
    }
     
    public Object getViewcount() {
		return viewcount;
	}
	public void setViewcount(Object viewcount) {
		this.viewcount = viewcount;
	}
	public Object getRankscore() {
		return rankscore;
	}
	public void setRankscore(Object rankscore) {
		this.rankscore = rankscore;
	}
	public String getUserid(){
    	return this.getUserinfo().getRef();
    }
    public void setUserid(String userid){
    	this.getUserinfo().setRef(userid);
    }
    public Long getResid(){
    	return this.getResourceinfo().getResid();
    }
    public void setResid(Long  resid){
    	this.getResourceinfo().setResid(resid);
    }   
    public String getRealname(){
    	return this.getUserinfo().getRealname();
    }
    public void setRealname(String realname){
    	this.getUserinfo().setRealname(realname);
    }
    public String getFilename(){
    	return this.getResourceinfo().getFilename();
    }
    public void setFilename(String filename){
    	this.getResourceinfo().setFilename(filename);
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


    public String getSuffixtype(){
        if(this.getResourseType()=="jpeg"){
            return "ico_jpg1";
        }else if(this.getResourseType()=="video"&&(this.getDifftype()==null||this.getDifftype()!=1)){
            return "ico_mp41";
        }else if(this.getResourseType()=="mp3"){
            return "ico_mp31";
        }else if(this.getResourseType()=="doc"){
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
        }else if(this.getResourseType()=="swf"){
            return "ico_swf1";
        }else if(this.getDifftype()!=null&&this.getDifftype()==1){
            return "ico_wsp1";
        }else{
            return "ico_other1";
        }
    }
	
    
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Integer getCollectid() {
		return collectid;
	}
	public void setCollectid(Integer collectid) {
		this.collectid = collectid;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public ResourceInfo getResourceinfo() {
		if(resourceinfo==null)
            resourceinfo=new ResourceInfo();
		return resourceinfo;
	}
	public void setResourceinfo(ResourceInfo resourceinfo) {
		this.resourceinfo = resourceinfo;
	}
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1); 
	}

}
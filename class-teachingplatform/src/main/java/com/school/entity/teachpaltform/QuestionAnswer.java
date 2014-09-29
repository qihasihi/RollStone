package com.school.entity.teachpaltform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class QuestionAnswer  implements java.io.Serializable{

    private Long quesid;
    private Integer ref;
    private Date ctime;
    private Long quesparentid;
	private String answercontent;
    private Integer rightanswer;
    private UserInfo userinfo;
    private Long taskid;
    private Long courseid;
    private Long groupid;
    private String replyattach;
    private Integer replyattachtype;
    private Integer classid;

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getReplyattach() {
        return replyattach;
    }

    public void setReplyattach(String replyattach) {
        this.replyattach = replyattach;
    }

    public Integer getReplyattachtype() {
        return replyattachtype;
    }

    /**
     * 文件List (缩略图)
     * @return
     */
    public List<String> getReplyattachList(){
        List<String> returnList=new ArrayList<String>();
        if(this.getReplyattach()!=null&&this.getReplyattach().length()>0){
            if(this.getReplyattach().indexOf(",")!=-1){
                String replyAttach=this.getReplyattach().replaceAll("\"","").replaceAll("\\[","").replaceAll("]","");
                String[] annexArray=getReplyattach().split(",");
                for(String astr:annexArray){
                    if(astr!=null&&astr.trim().length()>0){
                        if(astr.indexOf("http:")==-1&&astr.indexOf("https:")==-1){
                            astr=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+astr;
                        }
                        returnList.add(astr);
                    }
                }
            }else{
                if(getReplyattach().indexOf("http:")==-1&&getReplyattach().indexOf("https:")==-1){
                    returnList.add(UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+getReplyattach());
                }else
                    returnList.add(getReplyattach());
            }
        }
        return returnList;
    }


    /**
     * 文件List (原图)
     * @return
     */
    public List<String> getOriginalReplyattachList(){
        List<String> returnList=new ArrayList<String>();
        if(this.getReplyattach()!=null&&this.getReplyattach().length()>0){
            if(this.getReplyattach().indexOf(",")!=-1){
                String replyAttach=this.getReplyattach().replaceAll("\"","").replaceAll("\\[","").replaceAll("]","");
                String[] annexArray=getReplyattach().split(",");
                for(String astr:annexArray){
                    if(astr!=null&&astr.trim().length()>0){
                        if(astr.indexOf("http:")==-1&&astr.indexOf("https:")==-1){
                            astr=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+astr;
                        }else{
                            String suffix=astr.substring(astr.lastIndexOf("."));
                            if(UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR, suffix)){
                                String fileName=astr.substring(0,astr.lastIndexOf("."));
                                astr=fileName+"_1"+suffix;
                            }
                        }
                        returnList.add(astr);
                    }
                }
            }else{
                if(getReplyattach().indexOf("http:")==-1&&getReplyattach().indexOf("https:")==-1){
                    String attachUrl=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+getReplyattach();
                    String suffix=getReplyattach().substring(getReplyattach().lastIndexOf("."));
                    //123.jpg
                    if(UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR, suffix)){
                        String[]fileNameArr=getReplyattach().split("\\.");
                        if(fileNameArr.length>0)
                            attachUrl=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+fileNameArr[0]+"_1"+suffix;
                    }
                    returnList.add(attachUrl);
                }else
                    returnList.add(getReplyattach());
            }
        }
        return returnList;
    }

    public void setReplyattachtype(Integer replyattachtype) {
        this.replyattachtype = replyattachtype;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    private Integer tasktype;

    public Long getTaskid() {
        return taskid;
    }

    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Integer getTasktype() {
        return tasktype;
    }

    public void setTasktype(Integer tasktype) {
        this.tasktype = tasktype;
    }

    public String getReplyuserid() {
        return replyuserid;
    }

    public void setReplyuserid(String replyuserid) {
        this.replyuserid = replyuserid;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    /*资源心得*/
    private String replyuserid;
    private String replycontent;

    private UserInfo cuserinfo ;
    private UserInfo ruserinfo;
    private UserInfo touserinfo;
    public UserInfo getCuserinfo() {
        if(cuserinfo==null)
            cuserinfo=new UserInfo();
        return cuserinfo;
    }
    public void setCuserinfo(UserInfo cuserinfo) {
        this.cuserinfo = cuserinfo;
    }
    public UserInfo getRuserinfo() {
        if(ruserinfo==null)
            ruserinfo=new UserInfo();
        return ruserinfo;
    }
    public void setRuserinfo(UserInfo ruserinfo) {
        this.ruserinfo = ruserinfo;
    }
    public UserInfo getTouserinfo() {
        if(touserinfo==null)
            touserinfo=new UserInfo();
        return touserinfo;
    }
    public void setTouserinfo(UserInfo touserinfo) {
        this.touserinfo = touserinfo;
    }

    public String getTouserid() {
        return this.getTouserinfo().getRef();
    }
    public void setTouserid(String touserid) {
        this.getTouserinfo().setRef(touserid);
    }
    public String getTorealname() {
        return this.getTouserinfo().getRealname();
    }
    public void setTorealname(String tousername) {
        this.getTouserinfo().setRealname(tousername) ;
    }
    public void setCrealname(String realname){
        this.getCuserinfo().setRealname(realname);
    }
    public String getCrealname(){
        return this.getCuserinfo().getRealname();
    }
    public void setRrealname(String realname){
        this.getRuserinfo().setRealname(realname);
    }
    public String  getRrealname(){
        return this.getRuserinfo().getRealname();
    }

    public String getHeadimage(){
        return this.getCuserinfo().getHeadimage();
    }
    public void setHeadimage(String headimage){
        this.getCuserinfo().setHeadimage(headimage);
    }
    /*专题资源评论*/
    
    
	public Long getQuesparentid() {
		return quesparentid;
	}
	public void setQuesparentid(Long quesparentid) {
		this.quesparentid = quesparentid;
	}

    public String getUserid(){
    	return this.getUserinfo().getRef();
    }
    public void setUserid(String userid){
    	this.getUserinfo().setRef(userid);
    }

    public String getRealname(){
        return this.getUserinfo().getRealname();
    }
    public void setRealname(String realname){
        this.getUserinfo().setRealname(realname);
    }

	public Integer getRef() {
		return ref;
	}
	public void setRef(Integer ref) {
		this.ref = ref;
	}
	public Date getCtime() {
		return ctime;
	}
    public Long getCtimeLong(){
        if(this.getCtime()==null)
            return 0L;
        return getCtime().getTime();
    }

    public String getXgctime(){
        Long dLong=(new Date().getTime()-getCtimeLong());
        dLong=dLong/60000;//分钟
        String sHtml="刚刚";
        if(dLong>1&&dLong<60)
            sHtml=dLong+"分钟前";
        else if(dLong>=60){
            dLong=dLong/60;
            if(dLong<24)
                sHtml=dLong+"小时前";
            else{
                dLong=dLong/24;
                sHtml=dLong+"天前";
            }
        }
        return sHtml;
    }

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Long getQuesid() {
		return quesid;
	}
	public void setQuesid(Long quesid) {
		this.quesid = quesid;
	}
	
	public String getAnswercontent() {
		return answercontent;
	}
	public void setAnswercontent(String answercontent) {
		this.answercontent = answercontent;
	}
	public Integer getRightanswer() {
		return rightanswer;
	}
	public void setRightanswer(Integer rightanswer) {
		this.rightanswer = rightanswer;
	}
	public UserInfo getUserinfo() {
		if(userinfo==null)
			userinfo=new UserInfo();
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
    public List<String> getImtaskattachList(){
        List<String> returnList=new ArrayList<String>();
        if(this.replyattach!=null&&this.replyattach.trim().length()>0){
            String imattach=this.replyattach.replaceAll("\"","").replaceAll("\\[","").replaceAll("]","");
            String[] attachUrlArr=imattach.split(",");
            if(attachUrlArr!=null&&attachUrlArr.length>0){
                for (String url:attachUrlArr){
                    if(url!=null&&url.trim().length()>0){
                        returnList.add(url);
                    }
                }
            }
        }
        return returnList;
    }

	
	public String getCtimeString(){
		if(ctime==null)
			return null;
		return UtilTool.DateConvertToString(ctime, DateType.type1); 
	}


}
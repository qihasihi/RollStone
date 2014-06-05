package  com.school.entity ;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class CommentInfo  implements java.io.Serializable{

    private String currentLoginRef;

    public void CommentInfo (){}
   
	public static String DICTIONARY_TYPE_OF_COMMENT ="ALL_COMMENT_TYPE";
    private java.lang.Integer reportuserid;
    private java.lang.Integer commentuserid;
    private java.lang.String commentobjectid;
    private java.lang.String commentid;
    private java.lang.String commentparentid;
    private java.lang.Integer anonymous;
    private ScoreInfo scoreinfo;
    private java.lang.Integer commenttype;
    private Date ctime;
    private java.lang.String reportcontext;
    private Date mtime;
    private java.lang.String commentcontext;
    private java.lang.String commentusername;
    private Integer support;
    private Integer oppose;
    private StudentInfo studentinfo;
    private Long isopper;

    public Long getIsopper() {
        return isopper;
    }

    public void setIsopper(Long isopper) {
        this.isopper = isopper;
    }

    /*专题资源评论*/
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




    public String getCommentparentid() {
        return commentparentid;
    }
    public void setCommentparentid(String commentparentid) {
        this.commentparentid = commentparentid;
    }
    public Integer getSupport() {
		return support;
	}
	public void setSupport(Integer support) {
		this.support = support;
	}
	public Integer getOppose() {
		return oppose;
	}
	public void setOppose(Integer oppose) {
		this.oppose = oppose;
	}
	public ScoreInfo getScoreinfo() {
    	if(scoreinfo==null)
    		scoreinfo = new ScoreInfo();
		return scoreinfo;
	}
	public void setScoreinfo(ScoreInfo scoreinfo) {
		this.scoreinfo = scoreinfo;
	}
	public StudentInfo getStudentinfo() {
    	if(studentinfo==null)
    		studentinfo = new StudentInfo();
		return studentinfo;
	}
	public void setStudentinfo(StudentInfo studentinfo) {
		this.studentinfo = studentinfo;
	}

	public void setStuno(String stuno){
		this.getStudentinfo().setStuno(stuno);
	}
	
	public String getStuno(){
		return this.getStudentinfo().getStuno();
	}
	
	public String getStuname(){
		return this.getStudentinfo().getStuname();
	}
	
	public void setStuname(String stuname){
		this.getStudentinfo().setStuname(stuname);
	}
	
	private Integer classid;
    
    
    public Integer getClassid() {
		return classid;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	public java.lang.String getCommentusername() {
		return commentusername;
	}
	public void setCommentusername(java.lang.String commentusername) {
		this.commentusername = commentusername;
	}
	public java.lang.Integer getReportuserid(){
      return reportuserid;
    }
    public void setReportuserid(java.lang.Integer reportuserid){
      this.reportuserid = reportuserid;
    }
    public java.lang.Integer getCommentuserid(){
      return commentuserid;
    }
    public void setCommentuserid(java.lang.Integer commentuserid){
      this.commentuserid = commentuserid;
    }
    public java.lang.String getCommentobjectid(){
      return commentobjectid;
    }
    public void setCommentobjectid(java.lang.String commentobjectid){
      this.commentobjectid = commentobjectid;
    }
    public java.lang.String getCommentid(){
      return commentid;
    }
    public void setCommentid(java.lang.String commentid){
      this.commentid = commentid;
    }
    public java.lang.Integer getAnonymous(){
      return anonymous;
    }
    public void setAnonymous(java.lang.Integer anonymous){
      this.anonymous = anonymous;
    }
    public java.lang.Integer getScore(){
      return this.getScoreinfo().getScore();
    }
    public void setScore(java.lang.Integer score){
      this.getScoreinfo().setScore(score);
    }
    public java.lang.Integer getCommenttype(){
      return commenttype;
    }
    public void setCommenttype(java.lang.Integer commenttype){
      this.commenttype = commenttype;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.String getReportcontext(){
      return reportcontext;
    }
    public void setReportcontext(java.lang.String reportcontext){
      this.reportcontext = reportcontext;
    }
    public Date getMtime(){
      return mtime;
    }
    public void setMtime(Date mtime){
      this.mtime = mtime;
    }
    public java.lang.String getCommentcontext(){
      return commentcontext;
    }
    public void setCommentcontext(java.lang.String commentcontext){
      this.commentcontext = commentcontext;
    }
    public String getCtimeString(){
    	return (ctime==null?"":UtilTool.DateConvertToString(ctime, DateType.type1)); 
    }
    public String getMtimeString(){
    	return (mtime==null?"":UtilTool.DateConvertToString(mtime, DateType.type1)); 
    }


    public String getCurrentLoginRef() {
        return currentLoginRef;
    }

    public void setCurrentLoginRef(String currentLoginRef) {
        this.currentLoginRef = currentLoginRef;
    }
}
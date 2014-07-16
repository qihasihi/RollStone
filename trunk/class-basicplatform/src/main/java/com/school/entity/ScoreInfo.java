package  com.school.entity ;

import javax.persistence.Entity;

@Entity
public class ScoreInfo  implements java.io.Serializable{

	public void ScoreInfo (){}
	public static String DICTIONARY_TYPE_OF_SCORE ="ALL_SCORE_TYPE";
    private java.lang.String commentid;
    private java.lang.String scoreid;
    private java.lang.Integer scoreuserid;
    private java.lang.Integer scoretype;
    private java.lang.Integer score;
    private java.lang.String scoreobjectid;
    private java.util.Date ctime;
    private java.util.Date mtime;

    public java.lang.String getCommentid(){
      return commentid;
    }
    public void setCommentid(java.lang.String commentid){
      this.commentid = commentid;
    }
    public java.lang.String getScoreid(){
      return scoreid;
    }
    public void setScoreid(java.lang.String scoreid){
      this.scoreid = scoreid;
    }
    public java.lang.Integer getScoreuserid(){
      return scoreuserid;
    }
    public void setScoreuserid(java.lang.Integer scoreuserid){
      this.scoreuserid = scoreuserid;
    }
    public java.lang.Integer getScoretype(){
      return scoretype;
    }
    public void setScoretype(java.lang.Integer scoretype){
      this.scoretype = scoretype;
    }
    public java.lang.Integer getScore(){
      return score;
    }
    public void setScore(java.lang.Integer score){
      this.score = score;
    }
    public java.lang.String getScoreobjectid(){
      return scoreobjectid;
    }
    public void setScoreobjectid(java.lang.String scoreobjectid){
      this.scoreobjectid = scoreobjectid;
    }
    public java.util.Date getCtime(){
      return ctime;
    }
    public void setCtime(java.util.Date ctime){
      this.ctime = ctime;
    }
    public java.util.Date getMtime(){
      return mtime;
    }
    public void setMtime(java.util.Date mtime){
      this.mtime = mtime;
    }
  

}
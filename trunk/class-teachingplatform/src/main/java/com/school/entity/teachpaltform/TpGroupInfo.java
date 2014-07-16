package  com.school.entity.teachpaltform;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class TpGroupInfo  implements java.io.Serializable{

	public void TpGroupInfo (){}
    private java.lang.Integer classid;
    private java.lang.Integer classtype;
    private java.lang.Integer cuserid;
    private java.lang.String groupname;
    private java.lang.Long groupid;
    private String termid;
    private java.util.Date ctime;
    private java.util.Date mtime;
    private Object teachername;
    private Integer userid;
    private Integer subjectid;
    private Object groupnum;
    private int completenum;
    private int totalnum;

    public int getCompletenum() {
        return completenum;
    }

    public void setCompletenum(int completenum) {
        this.completenum = completenum;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public Object getGroupnum() {
        return groupnum;
    }

    public void setGroupnum(Object groupnum) {
        this.groupnum = groupnum;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Object getTeachername() {
        return teachername;
    }

    public void setTeachername(Object teachername) {
        this.teachername = teachername;
    }

    private String classname;

    private List<TpGroupStudent> tpgroupstudent;

    public List<TpGroupStudent> getTpgroupstudent() {
        if(tpgroupstudent==null)
            tpgroupstudent = new ArrayList<TpGroupStudent>();
        return tpgroupstudent;
    }
    public void setTpgroupstudent(List<TpGroupStudent> tpgroupstudent) {
        this.getTpgroupstudent().addAll(tpgroupstudent);
    }
    private List<TpGroupStudent> tpgroupstudent2;
    public List<TpGroupStudent> getTpgroupstudent2(){
        if(tpgroupstudent2==null)
            tpgroupstudent2 = new ArrayList<TpGroupStudent>();
        return tpgroupstudent2;
    }
    public void setTpgroupstudent2(TpGroupStudent tpgroustudent2){
        this.getTpgroupstudent2().add(tpgroustudent2);
    }



    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Integer getClasstype() {
        return classtype;
    }

    public void setClasstype(Integer classtype) {
        this.classtype = classtype;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public java.lang.Integer getClassid(){
      return classid;
    }
    public void setClassid(java.lang.Integer classid){
      this.classid = classid;
    }

    public Integer getCuserid() {
        return cuserid;
    }

    public void setCuserid(Integer cuserid) {
        this.cuserid = cuserid;
    }

    public java.lang.String getGroupname(){
      return groupname;
    }
    public void setGroupname(java.lang.String groupname){
      this.groupname = groupname;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}
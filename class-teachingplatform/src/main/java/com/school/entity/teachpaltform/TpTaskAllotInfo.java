package  com.school.entity.teachpaltform;

import com.school.entity.UserInfo;
import com.school.util.UtilTool;

import java.io.Serializable;
import java.util.Date;

public class TpTaskAllotInfo implements Serializable {

	public void TpTaskAllotInfo (){}
   
    private java.lang.Long usertypeid; //用户类型编号
    private Date btime;
    private java.lang.Integer usertype;   //用户群类型  0：班级 1：虚拟班级 2：小组
    private java.lang.Integer ref;
    private Date ctime;
    private java.lang.String criteria;
    private Date etime;

    private UserInfo userinfo;
    private Long taskid;
    private String allotobj; //任务对象
    private Object allotid;
    private Object allotname;
    private Long courseid;

    public Object getAllotid() {
        return allotid;
    }

    public void setAllotid(Object allotid) {
        this.allotid = allotid;
    }

    public Object getAllotname() {
        return allotname;
    }

    public void setAllotname(Object allotname) {
        this.allotname = allotname;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Long getTaskid() {
        return taskid;
    }
    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }
    public String getAllotobj() {
        return allotobj;
    }

    public void setAllotobj(String allotobj) {
        this.allotobj = allotobj;
    }

    public UserInfo getUserinfo() {
        if(userinfo==null)
            userinfo=new UserInfo();
        return userinfo;
    }
    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }


    public java.lang.Long getUsertypeid(){
      return usertypeid;
    }
    public void setUsertypeid(java.lang.Long usertypeid){
      this.usertypeid = usertypeid;
    }
    public Date getBtime(){
      return btime;
    }
    public void setBtime(Date btime){
      this.btime = btime;
    }
    public java.lang.Integer getUsertype(){
      return usertype;
    }
    public void setUsertype(java.lang.Integer usertype){
      this.usertype = usertype;
    }
    public java.lang.Integer getRef(){
      return ref;
    }
    public void setRef(java.lang.Integer ref){
      this.ref = ref;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.String getCuserid(){
      return this.getUserinfo().getRef();
    }
    public void setCuserid(java.lang.String cuserid){
      this.getUserinfo().setRef(cuserid);
    }



    public java.lang.String getCriteria(){
      return criteria;
    }
    public void setCriteria(java.lang.String criteria){
      this.criteria = criteria;
    }
    public Date getEtime(){
      return etime;
    }
    public void setEtime(Date etime){
      this.etime = etime;
    }

    public String getBtimeString(){
        if(btime==null)
            return null;
        return UtilTool.DateConvertToString(btime, UtilTool.DateType.type1);
    }

    public String getEtimeString(){
        if(etime==null)
            return null;
        return UtilTool.DateConvertToString(etime, UtilTool.DateType.type1);
    }
  

}
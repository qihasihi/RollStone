package  com.school.entity.teachpaltform;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
public class QuesTeamRela implements  Serializable{

	public void QuesTeamRela(){}
   
    private Date ctime;
    private java.lang.Integer orderid;
    private java.lang.Long quesid;
    private java.lang.Long teamid;
    private java.lang.Long ref;

    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Integer getOrderid(){
      return orderid;
    }
    public void setOrderid(java.lang.Integer orderid){
      this.orderid = orderid;
    }
    public java.lang.Long getQuesid(){
      return quesid;
    }
    public void setQuesid(java.lang.Long quesid){
      this.quesid = quesid;
    }
    public java.lang.Long getTeamid(){
      return teamid;
    }
    public void setTeamid(java.lang.Long teamid){
      this.teamid = teamid;
    }
    public java.lang.Long getRef(){
      return ref;
    }
    public void setRef(java.lang.Long ref){
      this.ref = ref;
    }
  

}
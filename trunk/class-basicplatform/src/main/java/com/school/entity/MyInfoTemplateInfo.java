package  com.school.entity ;

import javax.persistence.Entity;
import java.util.Date;
@Entity
public class MyInfoTemplateInfo  implements java.io.Serializable{

	public void MyInfoTemplateInfo (){}
   
    private java.lang.String templateurl;
    private Integer templateid;
    private java.lang.String templatesearator;
    private java.lang.Integer enable;
    private java.lang.String templatename;
    private java.lang.String templatecontent;
    private Date ctime;
    private Date mtime;

    public java.lang.String getTemplateurl(){
      return templateurl;
    }
    public void setTemplateurl(java.lang.String templateurl){
      this.templateurl = templateurl;
    }
    public Integer getTemplateid(){
      return templateid;
    }
    public void setTemplateid(Integer templateid){
      this.templateid = templateid;
    }
    public java.lang.String getTemplatesearator(){
      return templatesearator;
    }
    public void setTemplatesearator(java.lang.String templatesearator){
      this.templatesearator = templatesearator;
    }
    public java.lang.Integer getEnable(){
      return enable;
    }
    public void setEnable(java.lang.Integer enable){
      this.enable = enable;
    }
    public java.lang.String getTemplatename(){
      return templatename;
    }
    public void setTemplatename(java.lang.String templatename){
      this.templatename = templatename;
    }
    public java.lang.String getTemplatecontent(){
      return templatecontent;
    }
    public void setTemplatecontent(java.lang.String templatecontent){
      this.templatecontent = templatecontent;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public Date getMtime(){
      return mtime;
    }
    public void setMtime(Date mtime){
      this.mtime = mtime;
    }
  

}
package com.school.entity;

import java.util.Date;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class MyInfoUserInfo implements java.io.Serializable {

    public void MyInfoUserInfo() {}

    private MyInfoTemplateInfo myinfotemplateinfo;
    private Integer ref;
    private java.lang.String mydata;
    private java.lang.Integer msgid;
    private UserInfo userinfo;
    private java.lang.String operateid;
    private java.lang.String msgname;
    private Date ctime;
    private Integer classid;
    private Integer ettuserid;
    private Integer userType;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getEttuserid() {
        return ettuserid;
    }

    public void setEttuserid(Integer ettuserid) {
        this.ettuserid = ettuserid;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public UserInfo getUserinfo() {
        return userinfo=(userinfo==null?new UserInfo():userinfo);
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public MyInfoTemplateInfo getMyinfotemplateinfo() {
        return (myinfotemplateinfo=(myinfotemplateinfo==null?new MyInfoTemplateInfo():myinfotemplateinfo));
    }

    public void setMyinfotemplateinfo(MyInfoTemplateInfo myinfotemplateinfo) {
        this.myinfotemplateinfo = myinfotemplateinfo;
    }

    public Integer getTemplateid() {
        return this.getMyinfotemplateinfo().getTemplateid();
    }

    public void setTemplateid(Integer templateid) {
        this.getMyinfotemplateinfo().setTemplateid(templateid);
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public java.lang.String getMydata() {
        return mydata;
    }

    public void setMydata(java.lang.String mydata) {
        this.mydata = mydata;
    }

    public java.lang.Integer getMsgid() {
        return msgid;
    }

    public void setMsgid(java.lang.Integer msgid) {
        this.msgid = msgid;
    }

    public java.lang.String getUserref() {
        return this.getUserinfo().getRef();
    }

    public void setUserref(java.lang.String userref) {
        this.getUserinfo().setRef(userref);
    }
    public void setRealname(String realname){
        this.getUserinfo().setRealname(realname);
    }
    public String getRealname(){
        return this.getUserinfo().getRealname();
    }
    public void setUserid(Integer userid){
        this.getUserinfo().setUserid(userid);
    }
    public Integer getUserid(){
        return this.getUserinfo().getUserid();
    }
    public java.lang.String getOperateid() {
        return operateid;
    }

    public void setOperateid(java.lang.String operateid) {
        this.operateid = operateid;
    }

    public java.lang.String getMsgname() {
        return msgname;
    }

    public void setMsgname(java.lang.String msgname) {
        this.msgname = msgname;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public java.lang.String getTemplatesearator() {
        return this.getMyinfotemplateinfo().getTemplatesearator();
    }

    public void setTemplatesearator(java.lang.String templatesearator) {
        this.getMyinfotemplateinfo().setTemplatesearator(templatesearator);
    }


    public java.lang.Integer getEnable() {
        return this.getMyinfotemplateinfo().getEnable();
    }

    public void setEnable(java.lang.Integer enable) {
        this.getMyinfotemplateinfo().setEnable(enable);
    }

    public java.lang.String getTemplatename() {
        return this.getMyinfotemplateinfo().getTemplatename();
    }

    public void setTemplatename(java.lang.String templatename) {
        this.getMyinfotemplateinfo().setTemplatename(templatename);
    }

    public java.lang.String getTemplatecontent() {
        return this.getMyinfotemplateinfo().getTemplatecontent();
    }

    public void setTemplatecontent(java.lang.String templatecontent) {
        this.getMyinfotemplateinfo().setTemplatecontent(templatecontent);
    }

    public java.lang.String getTemplateurl() {
        return this.getMyinfotemplateinfo().getTemplateurl();
    }
    public String getCtimeChinaString(){
        if(this.getCtime()==null)
            return null;
        return UtilTool.DateConvertToString(this.getCtime(), DateType.YearForChina);
    }
    public String getCtimeString(){
        if(this.getCtime()==null)
            return null;
        return UtilTool.DateConvertToString(this.getCtime(), DateType.type1);
    }
    public String getCtimeImString(){
        if(this.getCtime()==null)
            return null;
        return UtilTool.DateConvertToString(this.getCtime(), DateType.MonthAndDay);
    }

    public void setTemplateurl(java.lang.String templateurl) {
        this.getMyinfotemplateinfo().setTemplateurl(templateurl);
    }
    /**
     * 将数据库存储数据，解释至HTML语言
     * @return
     */
    public String getDynamicString(){
        if(this.getTemplatecontent()==null||this.getTemplatecontent().length()<1
                ||this.getTemplatesearator()==null||this.getTemplatesearator().length()<1)
            return null;
        String paraSplitCode="#ETIANTIAN_SPLIT#";
        String returnVal=this.getTemplatecontent();
        String replaceCode=this.getTemplatesearator();
        if(this.getMydata()==null)
            return null;
        String[] dArray=this.getMydata().split("\\|");//得到数据
        String[] dUrlArray=null;
        if(this.getTemplateurl()!=null)
            dUrlArray=this.getTemplateurl().split("\\|");
        int i=0;
        while(returnVal.indexOf(replaceCode)!=-1){
            String data=null;
            if(dArray!=null&&dArray.length>i)
                data=dArray[i];
            if(dUrlArray!=null&&dUrlArray.length>i&&dUrlArray[i].trim().length()>0){
                if(data!=null&&data.length()>0&&data.indexOf(paraSplitCode)!=-1){
                    dUrlArray[i]+=data.split(paraSplitCode)[1];
                    data="<a style='color:#2682E7;' href='"+dUrlArray[i];
                    String tmpData=dArray[i];
                    if(dArray[i]!=null&&dArray[i].trim().length()>0&&dArray[i].trim().indexOf(paraSplitCode)>=0)
                        tmpData=dArray[i].split(paraSplitCode)[0];
                    data+="' target='_BLANK'>"+tmpData+"</a>";
                    returnVal=returnVal.replaceFirst((replaceCode.equals("|")?"\\|":replaceCode),data);
                }else{//有templateurl并且data中无分隔符
                    returnVal=returnVal.replaceFirst((replaceCode.equals("|")?"\\|":replaceCode), data);
                    returnVal="<a href='"+dUrlArray[i]+"' target='_BLANK'>"+returnVal+"</a>";
                }
            }else
                returnVal=returnVal.replaceFirst((replaceCode.equals("|")?"\\|":replaceCode), data);
            i++;
        }
        return returnVal;
    }
}
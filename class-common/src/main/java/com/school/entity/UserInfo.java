package com.school.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class UserInfo implements java.io.Serializable {
	private String ref;
	private Integer userid;
	private String username;
	private String password;
	private Integer stateid;
	private String identitynumber;
	private Date birthdate;
	private String address;
	private String realcardid;

    private Integer ismodify;   //是否修改过用户名

	private String usersex;
	
	private String rolename;
	private String identityname;
	private String deptname;
	private String classname;
    private Object taskobjname;
    private Object classid;
    private Integer qq;
    private String hobbies;
    private String country;
    private String nation;
    private String lzxuserid;   //乐知行用户ID
    private String schoolid;
    private Integer ettuserid;//网校用户id

    /**
     * 用户类型:  2=任课教师   3=学生
     * @return
     */
    public Integer getUserType() {
        if(this.stuname!=null&&this.stuname.trim().length()>0){
            return 3;
        }
        return 2;
    }


    public Integer getEttuserid() {
        return ettuserid;
    }

    public void setEttuserid(Integer ettuserid) {
        this.ettuserid = ettuserid;
    }

    public Integer getDcschoolid() {
        return dcschoolid;
    }
    //
    private String dcschoolname;
    public void setDcschoolname(String dcschoolname){
        this.dcschoolname=dcschoolname;
    }
    //存入
    public String getDcschoolname(){
        return dcschoolname;
    }

    public void setDcschoolid(Integer dcschoolid) {
        this.dcschoolid = dcschoolid;
    }

    private Integer dcschoolid;//数校分校id
    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getLzxuserid() {
        return lzxuserid;
    }

    public void setLzxuserid(String lzxuserid) {
        this.lzxuserid = lzxuserid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public Integer getIsmodify() {
        return ismodify;
    }

    public void setIsmodify(Integer ismodify) {
        this.ismodify = ismodify;
    }

    public Object getClassid() {
        return classid;
    }

    public void setClassid(Object classid) {
        this.classid = classid;
    }

    public Object getTaskobjname() {
        return taskobjname;
    }

    public void setTaskobjname(Object taskobjname) {
        this.taskobjname = taskobjname;
    }

    public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getIdentityname() {
		return identityname;
	}
	public void setIdentityname(String identityname) {
		this.identityname = identityname;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	//发布资源数
	private Long resnum;
	
	public Long getResnum() {
		return resnum;
	}
	public void setResnum(Long resnum) {
		this.resnum = resnum;
	}
	/**
	 * 路径与功能权限
	 */
	private List<RightUser> pathRightList;
	private List<RightUser> functionRightList;
	
	private List cjJRoleUsers = new ArrayList();
	private List ClassUsers = new ArrayList();
	private List DeptUsers = new ArrayList();
	
	
	public List getDeptUsers() {
		return DeptUsers;
	}
	public void setDeptUsers(List deptUsers) {
		DeptUsers = deptUsers;
	}
	public String getUsersex() {
		return usersex;
	}
	public void setUsersex(String usersex) {
		this.usersex = usersex;
	}
	public List getClassUsers() {
		return ClassUsers;
	}
	public void setClassUsers(List classUsers) {
		ClassUsers = classUsers;
	}
	public List getCjJRoleUsers() {
		return cjJRoleUsers;
	}
	public void setCjJRoleUsers(List cjJRoleUsers) {
		this.cjJRoleUsers = cjJRoleUsers;
	}
	public List<RightUser> getPathRightList() {
		return pathRightList;
	}
	public void setPathRightList(List<RightUser> pathRightList) {
		this.pathRightList = pathRightList;
	}
	public List<RightUser> getFunctionRightList() {
		return functionRightList;
	}
	public void setFunctionRightList(List<RightUser> functionRightList) {
		this.functionRightList = functionRightList;
	}
	public String getIdentitynumber() {
		return identitynumber;
	}
	public void setIdentitynumber(String identitynumber) {
		this.identitynumber = identitynumber;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private String mailaddress;
	private String realname;
	private Integer gender;
	private String headimage;
	public String getHeadimage() {
		return headimage;
	}
	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}
	public String getMailaddress() {
		return mailaddress;
	}
	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	private String passquestion;
	private String questionanswer;
	private Date ctime;
	private Date mtime;
	private String sex;
	private String stuno;
	 
	public String getStuno() {
		return stuno;
	}
	public void setStuno(String stuno) {
		this.stuno = stuno;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	private String stuname;
	private String stuNo;
	
	private String teacherName;
	
	
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStateid() {
		return stateid;
	}
	public void setStateid(Integer stateid) {
		this.stateid = stateid;
	}
	public String getPassquestion() {
		return passquestion;
	}
	public void setPassquestion(String passquestion) {
		this.passquestion = passquestion;
	}
	public String getQuestionanswer() {
		return questionanswer;
	}
	public void setQuestionanswer(String questionanswer) {
		this.questionanswer = questionanswer;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	/**
	 * YYYY-MM-DD HH24:MI:SS
	 * @return
	 */
	public String getgetBirthdateString(){
		if(this.birthdate==null)
			return null;
		return UtilTool.DateConvertToString(this.birthdate, DateType.type1);
	}
	
	public String getCtimeString(){
		if(this.ctime==null)
			return null;
		return UtilTool.DateConvertToString(this.ctime, DateType.type1);
	}
	public void setCtimeString(String ctime){
		if(ctime!=null){
			this.ctime=UtilTool.StringConvertToDate(ctime);
		}
	}
	
	public Date getMtime() {
		return mtime;
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	/**
	 * YYYY-MM-DD HH24:MI:SS
	 * @return
	 */
	public String getMtimeString(){
		if(this.mtime==null)
			return null;
		return UtilTool.DateConvertToString(this.mtime, DateType.type1);
	}
	public void setMtimeString(String mtime){
		if(mtime!=null){
			this.ctime=UtilTool.StringConvertToDate(mtime);
		}
	}
	
	public String getRealcardid() {
		return realcardid;
	}
	public void setRealcardid(String realcardid) {
		this.realcardid = realcardid;
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getStuNo() { 
		return stuNo;
	} 
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo; 
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public enum UserConditionEnum implements Serializable {
		user_Id, user_Name, password, state_id, pass_question, question_answer, c_time, m_time, nll

	}
}

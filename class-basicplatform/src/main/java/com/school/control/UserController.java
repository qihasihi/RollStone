package com.school.control;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.entity.*;
import com.school.manager.*;
import com.school.manager.impl.activity.ActivityManager;
import com.school.manager.inter.*;
import com.school.manager.inter.activity.IActivityManager;
import com.school.manager.inter.notice.INoticeManager;
import com.school.manager.notice.NoticeManager;

import com.school.utils.*;
import com.school.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.activity.ActivityInfo;
import com.school.entity.notice.NoticeInfo;
import com.school.util.UtilTool.DateType;
import com.school.util.sendfile.SendFile;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController<UserInfo> {
    protected IUserManager userManager;
    protected IClassYearManager classYearManager;
    protected IIdentityManager identityManager;
    protected IDictionaryManager dictionaryManager;
    protected IRoleManager roleManager;
    protected ISubjectManager subjectManager;
    protected IGradeManager gradeManager;
    protected IClassManager classManager;
    protected ISubjectUserManager subjectUserManager;
    protected IDeptUserManager deptUserManager;
    protected ITeacherManager teacherManager;
    protected IStudentManager studentManager;
    protected IClassUserManager classUserManager;
    protected IJobUserManager jobUserManager;
    protected IRoleUserManager roleUserManager;
    protected IParentManager parentManager;
    protected IUserColumnRightManager userColumnRightManager;
    protected IRoleColumnRightManager roleColumnRightManager;
    protected IDeptManager deptManager;
    protected ITermManager termManager;
    protected IUserIdentityManager userIdentityManager;
    protected INoticeManager noticeManager;
    protected IActivityManager activityManager;
    protected IMyInfoUserManager myInfoUserManager;
    protected IInitWizardManager initWizardManager;

    public UserController(){
        this.userManager=this.getManager(UserManager.class);
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.identityManager=this.getManager(IdentityManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
        this.roleManager=this.getManager(RoleManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.classManager=this.getManager(ClassManager.class);
        this.subjectUserManager=this.getManager(SubjectUserManager.class);
        this.deptUserManager=this.getManager(DeptUserManager.class);
        this.teacherManager=this.getManager(TeacherManager.class);
        this.studentManager=this.getManager(StudentManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.jobUserManager=this.getManager(JobUserManager.class);
        this.roleUserManager=this.getManager(RoleUserManager.class);
        this.parentManager=this.getManager(ParentManager.class);
        this.userColumnRightManager=this.getManager(UserColumnRightManager.class);
        this.roleColumnRightManager=this.getManager(RoleColumnRightManager.class);
        this.deptManager=this.getManager(DeptManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.userIdentityManager=this.getManager(UserIdentityManager.class);
        this.noticeManager=this.getManager(NoticeManager.class);
        this.activityManager=this.getManager(ActivityManager.class);
        this.myInfoUserManager=this.getManager(MyInfoUserManager.class);
        this.initWizardManager=this.getManager(InitWizardManager.class);

    }
//
//    /**
//     * 测试，同步版本
//     *sdfafda
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=SynchroTeachVersion",method=RequestMethod.GET)
//    public void synchroTeachVersion(HttpServletRequest request,HttpServletResponse response){
//        new ShareTeachVersion(request.getSession().getServletContext()).run();
//    }
//    /**
//     * 测试，同步教材
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=SynchroTeachingMaterial",method=RequestMethod.GET)
//    public void synchroTeachingMaterial(HttpServletRequest request,HttpServletResponse response){
//        new ShareTeachingMaterial(request.getSession().getServletContext()).run();
//    }
//    @RequestMapping(params="m=SynchroResource",method=RequestMethod.GET)
//    public void synchroResource(HttpServletRequest request,HttpServletResponse response){
//        new ShareResource(request.getSession().getServletContext()).run();
//    }
//    @RequestMapping(params="m=SynchroEttColumn",method=RequestMethod.GET)
//    public void SynchroEttColumn(HttpServletRequest request,HttpServletResponse response){
//        new SynchroEttColumns().run();
//    }

    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
//    @RequestMapping(params="m=updateCloudMyInfo",method={RequestMethod.GET,RequestMethod.POST})
//    public void updateCloudMyInfo(HttpServletRequest request,HttpServletResponse response){
//        new UpdateRsMyInfoData(request.getSession().getServletContext()).run();
//    }
//
//    /**
//     * 测试,最热资源同步
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=updateRsHotRank",method={RequestMethod.GET,RequestMethod.POST})
//    public void UpdateRsHotRank(HttpServletRequest request,HttpServletResponse response){
//        new UpdateHotResData(request.getSession().getServletContext()).run();
//    }
//
//
//    /**
//     * 测试,分校信息同步
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=updateSchool",method={RequestMethod.GET,RequestMethod.POST})
//    public void UpdateSchool(HttpServletRequest request,HttpServletResponse response){
//        new UpdateSchool(request.getSession().getServletContext()).run();
//    }
//    /**
//     * 测试,资源数量上行
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=updateResNum",method={RequestMethod.GET,RequestMethod.POST})
//    public void updateResNum(HttpServletRequest request,HttpServletResponse response){
//        new UpdateResNum(request.getSession().getServletContext()).run();
//    }
//    /**
//     * 测试,资源数量上行
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=synchroResNum",method={RequestMethod.GET,RequestMethod.POST})
//    public void synchroResNum(HttpServletRequest request,HttpServletResponse response){
//        new ShareResNum(request.getSession().getServletContext()).run();
//    }
//
//
//    /**
//     * 测试,分校tkw wv 排行
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=SynchroSchoolScoreRank",method={RequestMethod.GET,RequestMethod.POST})
//    public void SynchroSchoolScoreRank(HttpServletRequest request,HttpServletResponse response){
//        new UpdateSchoolScoreRank(request.getSession().getServletContext()).run();
//    }
//    /**
//     * 测试,分校 排行
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=SynchroUserModelTotalScore",method={RequestMethod.GET,RequestMethod.POST})
//    public void SynchroUserModelTotalScore(HttpServletRequest request,HttpServletResponse response){
//        new UpdateUserModelTotalScore(request.getSession().getServletContext()).run();
//    }
//    /**
//     * 测试,同步专题
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=SynchroCourse",method={RequestMethod.GET,RequestMethod.POST})
//    public void SynchroCourse(HttpServletRequest request,HttpServletResponse response){
//        new ShareCourse(request.getSession().getServletContext()).run();
//
//    }
//    /**
//     * 测试,同步资源
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=ShareResource",method={RequestMethod.GET,RequestMethod.POST})
//    public void SynchroResource(HttpServletRequest request,HttpServletResponse response){
//        new ShareResource(request.getSession().getServletContext()).run();
//
//    }
//    /**
//     * 测试,同步专题
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=updateCourse",method=RequestMethod.GET)
//    public void updateCourse(HttpServletRequest request,HttpServletResponse response){
//        new UpdateCourse(request.getSession().getServletContext()).run();
//
//    }
//
//    /**
//     * 测试，同步版本
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=SynchroTeachVersion",method=RequestMethod.GET)
//    public void synchroTeachVersion(HttpServletRequest request,HttpServletResponse response){
//        new ShareTeachVersion(request.getSession().getServletContext()).run();
//    }
//    /**
//     * 测试，同步教材
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=SynchroTeachingMaterial",method=RequestMethod.GET)
//    public void synchroTeachingMaterial(HttpServletRequest request,HttpServletResponse response){
//        new ShareTeachingMaterial(request.getSession().getServletContext()).run();
//    }
//    @RequestMapping(params="m=SynchroResource",method=RequestMethod.GET)
//        public void synchroResource(HttpServletRequest request,HttpServletResponse response){
//        new ShareResource(request.getSession().getServletContext()).run();
//    }
//
//    /**
//     * 测试,同步专题
//     * @param request
//     * @param response
//     */
//    @RequestMapping(params="m=SynchroOperate",method=RequestMethod.GET)
//    public void SynchroOperate(HttpServletRequest request,HttpServletResponse response){
//        new ShareTpOperate(request.getSession().getServletContext()).run();
//    }

    @RequestMapping(params = "m=list", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView toUserList(HttpServletRequest request,
                                   HttpServletResponse response, ModelAndView mp) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = new PageResult();
        presult.setPageNo(0);
        presult.setPageSize(0);
        presult.setOrderBy("c_time desc");
        List<ClassYearInfo> yearList = this.classYearManager.getList(null,
                presult);
        if (yearList == null || yearList.size() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("CLASS_YEAR_NOT_EXISTS"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        //获取身份
        List<DictionaryInfo>identityList=this.dictionaryManager.getDictionaryByType("IDENTITY_TYPE");
        RoleInfo r = new RoleInfo();
        r.setFlag(0);
        List<RoleInfo> roleList = this.roleManager.getList(r, null);
        r.setFlag(1);
        List<RoleInfo> jobList = this.roleManager.getList(r, null);
        DeptInfo d = new DeptInfo();
        d.setParentdeptid(0);
        //List<DeptInfo> deptList = this.deptManager.getList(null, null);

        request.setAttribute("CURRENT_YEAR", yearList.get(0)
                .getClassyearvalue());
        request.setAttribute("roleList", roleList);
        request.setAttribute("jobList", jobList);
        //request.setAttribute("deptList", deptList);
        request.setAttribute("identityList", identityList);

        if(request.getParameter("identityname")!=null){
            request.setAttribute("identityname", request.getParameter("identityname"));
            request.setAttribute("rolestr", request.getParameter("rolestr"));
            request.setAttribute("username", request.getParameter("username"));
        }

        return new ModelAndView("/user/list");
    }

    @RequestMapping(params = "m=testupload", method = {RequestMethod. GET,RequestMethod.POST})
    public ModelAndView testupload(HttpServletRequest request,
                                   HttpServletResponse response, ModelMap mp) throws Exception {
        return new ModelAndView("/test-upload",mp);
    }

    @RequestMapping(params = "m=loadrole", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView loadRole(HttpServletRequest request,
                                 HttpServletResponse response, ModelMap mp) throws Exception {
        String iname=request.getParameter("identityname");
        IdentityInfo i=new IdentityInfo();
        i.setIdentityname(iname);
        List<IdentityInfo>identityList=this.identityManager.getList(i, null);
        mp.put("identityList", identityList);
        return new ModelAndView("/user/admin/role",mp);
    }
    /**
     * 教务添加用户
     *
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toAdd", method = RequestMethod.GET)
    public ModelAndView toAddUser(HttpServletRequest request,
                                  HttpServletResponse response, ModelAndView mp) throws Exception {
        PageResult clspage=new PageResult();
        clspage.setPageNo(0);
        clspage.setPageSize(0);
        clspage.setOrderBy("YEAR,CASE class_grade	WHEN '高一' THEN 1 WHEN '高二' THEN 2 WHEN '高三' THEN 3 END,CLASS_NAME ");
        List<ClassInfo> classList = this.classManager.getList(null, clspage);
        RoleInfo job = new RoleInfo();
        job.setFlag(0);
        List<RoleInfo> roleList = this.roleManager.getList(job, null);
        List<SubjectInfo> subjectList = this.subjectManager.getList(null, null);
        job.setFlag(1);
        List<RoleInfo> jobList = this.roleManager.getList(job, null);
        List<ClassYearInfo>clsyearList=this.classYearManager.getCurrentYearList("more");

        clspage.setOrderBy("CASE grade_value	WHEN '高一' THEN 1 WHEN '高二' THEN 2 WHEN '高三' THEN 3 END");
        List<GradeInfo>gradeList=this.gradeManager.getList(null,clspage);

        request.setAttribute("clsList", classList);
        request.setAttribute("clsyearList", clsyearList);
        request.setAttribute("gradeList", gradeList);
        request.setAttribute("rolelist", roleList);
        request.setAttribute("subjectlist", subjectList);
        request.setAttribute("jobList", jobList);
        return new ModelAndView("/user/add/add");
    }

    /**
     * 用户管理 跳入用户修改页
     *
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=tomodify", method = RequestMethod.GET)
    public ModelAndView toModifyUser(HttpServletRequest request,
                                     HttpServletResponse response, ModelAndView mp) throws Exception {
        JsonEntity je = new JsonEntity();
        String userid = request.getParameter("ref");
        if (userid == null || userid.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        UserInfo u = new UserInfo();
        u.setRef(userid);
        List<UserInfo> userList = this.userManager.getList(u, null);
        if (userList == null || userList.size() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("NOT_EXISTS"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TeacherInfo t = new TeacherInfo();
        t.getUserinfo().setRef(userid);
        List<TeacherInfo> teacherList = this.teacherManager.getList(t, null);
        if (teacherList != null && teacherList.size() > 0)
            request.setAttribute("teacherinfo", teacherList.get(0));

        StudentInfo s = new StudentInfo();
        s.getUserinfo().setRef(userid);
        List<StudentInfo> studentList = this.studentManager.getList(s, null);
        if (studentList != null && studentList.size() > 0)
            request.setAttribute("studentinfo", studentList.get(0));

        // 用户角色
        RoleUser ru = new RoleUser();
        ru.getUserinfo().setRef(userid);
        List<RoleUser> ruList = this.roleUserManager.getList(ru, null);
        // 用户职务
        JobUser ju = new JobUser();
        ju.getUserinfo().setRef(userid);
        List<JobUser> juList = this.jobUserManager.getList(ju, null);
        // 教师学科
        SubjectUser su = new SubjectUser();
        su.getUserinfo().setRef(userid);
        List<SubjectUser> suList = this.subjectUserManager.getList(su, null);
        //部门职务
        DeptUser du=new DeptUser();
        du.getUserinfo().setRef(userid);
        List<DeptUser>deptUserList=this.deptUserManager.getList(du, null);

        // 用户与班级关系
        ClassUser cu = new ClassUser();
        cu.getUserinfo().setRef(userid);
        cu.setRelationtype("班主任");
        List<ClassUser> bzrcuList = this.classUserManager.getList(cu, null);
        cu.setRelationtype("任课老师");
        List<ClassUser> teacuList = this.classUserManager.getList(cu, null);
        cu.setRelationtype("学生");
        List<ClassUser> stucuList = this.classUserManager.getList(cu, null);

        PageResult clspage=new PageResult();
        clspage.setPageNo(0);
        clspage.setPageSize(0);
        clspage.setOrderBy("YEAR,CASE class_grade	WHEN '高一' THEN 1 WHEN '高二' THEN 2 WHEN '高三' THEN 3 END,CLASS_NAME ");
        List<ClassInfo> classList = this.classManager.getList(null, clspage);
        RoleInfo r = new RoleInfo();
        r.setFlag(0);
        List<RoleInfo> roleList = this.roleManager.getList(r, null);
        r.setFlag(1);
        List<RoleInfo> jobList = this.roleManager.getList(r, null);
        List<SubjectInfo> subjectList = this.subjectManager.getList(null, null);

        request.setAttribute("userinfo", userList.get(0));
        request.setAttribute("userinfo", userList.get(0));
        request.setAttribute("roleUserList", ruList);
        request.setAttribute("jobUserList", juList);
        request.setAttribute("subjectUserList", suList);
        request.setAttribute("bzrcuList", bzrcuList);
        request.setAttribute("teacuList", teacuList);
        request.setAttribute("stucuList", stucuList);

        request.setAttribute("clslist", classList);
        request.setAttribute("rolelist", roleList);
        request.setAttribute("subjectlist", subjectList);
        request.setAttribute("jobList", jobList);
        request.setAttribute("deptUserList", deptUserList);
        return new ModelAndView("/user/update/update");
    }

    @RequestMapping(params = "m=ajaxlist", method = RequestMethod.POST)
    public void GetAjaxUserList(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        List<UserInfo> userList = userManager.getList(userinfo, presult);
        je.setObjList(userList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 验证用户名
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=ajaxuser", method = RequestMethod.POST)
    public void validateUserName(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        List<UserInfo> userList = userManager.getList(userinfo, presult);
        je.setObjList(userList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }



    @RequestMapping(params = "m=updateUserInfo", method = RequestMethod.POST)
    public void updateUserInfo(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        List<UserInfo> userList = userManager.getList(userinfo, presult);
        if(userList!=null&&userList.size()>0&&
                !userList.get(0).getUserid().equals(this.logined(request).getUserid())){
            je.getObjList().add("err");
            je.setMsg("已存在!");
        }else{
            String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
            String schoolid=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
            totalSchoolUrl+="/user";
            String params="m=validateEttUserInfo&flag=1&schoolid="+schoolid+"&userid="+this.logined(request).getUserid()+"";
            if(userinfo.getUsername()!=null)
                params+="&username="+java.net.URLEncoder.encode(userinfo.getUsername(),"UTF-8");
            else if(userinfo.getMailaddress()!=null)
                params+="&email="+userinfo.getMailaddress();
            boolean b = sendValidateUserInfoTotalSchool(totalSchoolUrl, params);
            if(b){
                userinfo.setUserid(this.logined(request).getUserid());
                if(userinfo.getMailaddress()!=null)
                    userinfo.setIsmodify(1);
                b=this.userManager.doUpdate(userinfo);
                if(b)
                    je.setType("success");
            }
        }
        response.getWriter().print(je.toJSON());
    }

    private List<String> getRecommondUserName(HttpServletRequest request,UserInfo userinfo)throws  Exception{
        if(userinfo==null||userinfo.getUsername()==null||userinfo.getUsername().trim().length()<1)
            return null;
        int i=1;
        String originalName=userinfo.getUsername();
        List<String>userNameList=new ArrayList<String>();
        while (true){
            if(userNameList.size()>1)
                break;
            userinfo.setUsername(originalName+i);
            List<UserInfo>userList = userManager.getList(userinfo, null);
            if(userList==null||userList.size()<1){
                String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
                String schoolid=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
                totalSchoolUrl+="/user";
                String params="m=validateEttUserInfo&schoolid="+schoolid+"&userid="+this.logined(request).getUserid()+"";
                if(userinfo.getUsername()!=null)
                    params+="&username="+java.net.URLEncoder.encode(userinfo.getUsername(),"UTF-8");
                boolean b = sendValidateUserInfoTotalSchool(totalSchoolUrl, params);
                if(b){
                    userNameList.add(userinfo.getUsername());
                }
            }
            i+=1;
        }


        return userNameList;
    }


    /**
     * 验证用户名、邮箱
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=validateUserInfo", method = RequestMethod.POST)
    public void validateUsername(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        List<String>recommendUName=new ArrayList<String>();
        PageResult presult = this.getPageResultParameter(request);
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        List<UserInfo> userList = userManager.getList(userinfo, presult);

        if(userList!=null&&userList.size()>0&&
                !userList.get(0).getUserid().equals(this.logined(request).getUserid())){
            je.getObjList().add("err");
            je.setMsg("已存在!");
            recommendUName=this.getRecommondUserName(request,userinfo);
        }else{
            String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
            String schoolid=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
            totalSchoolUrl+="/user";
            String params="m=validateEttUserInfo&schoolid="+schoolid+"&userid="+this.logined(request).getUserid()+"";
            if(userinfo.getUsername()!=null)
                params+="&username="+java.net.URLEncoder.encode(userinfo.getUsername(),"UTF-8");
            else if(userinfo.getMailaddress()!=null)
                params+="&email="+userinfo.getMailaddress();
            boolean b = sendValidateUserInfoTotalSchool(totalSchoolUrl, params);
            if(!b){
                je.getObjList().add("err");
                je.setMsg("已存在!");
                recommendUName=this.getRecommondUserName(request,userinfo);
            }else{
                je.setMsg("可以使用!");
            }
        }
        je.setType("success");
        je.getObjList().add(recommendUName);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 验证邮箱、用户名总校唯一。
     * @param totalSchoolUrl
     * @param params
     * @return
     */
    public  boolean sendValidateUserInfoTotalSchool(String totalSchoolUrl,String params){
        if(totalSchoolUrl==null)return false;

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(totalSchoolUrl.toString());

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
            if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.length()));
            httpConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
			/*
			 * PrintWriter printWriter = new
			 * PrintWriter(httpConnection.getOutputStream());
			 * printWriter.print(parameters); printWriter.close();
			 */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    httpConnection.getOutputStream(), "8859_1");
            if(params!=null)
                outputStreamWriter.write(params);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("异常错误!TOTALSCHOOL未响应!");
            return false;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine).append("\n");
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("异常错误!");
                return false;
            }
        }else if(code==404){
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return false;
        }else if(code==500){
            System.out.println("异常错误!500错误，请联系管理人员!");
            return false;
        }
        String returnContent=null;
        try {
            returnContent=new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //转换成JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        String type=jb.containsKey("type")?jb.getString("type"):"";
        String msg=jb.containsKey("msg")?jb.getString("msg"):"";
        if(type!=null&&type.trim().toLowerCase().equals("success")){
            System.out.println(msg);
            return true;
        }else{
            System.out.println(msg);return false;
        }
    }



    @RequestMapping(params = "m=userregister", method = RequestMethod.POST)
    public void userRegister(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        if (userinfo == null || userinfo.getUsername() == null
                || userinfo.getPassword() == null
                || userinfo.getMailaddress() == null
                || userinfo.getGender() == null
                || userinfo.getRealname() == null) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String roleid = request.getParameter("role");
        if (roleid == null || !UtilTool.isNumber(roleid)) {
            je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLEID_EMPTY"));
            response.getWriter().print(je.toJSON());
            return;
        }
        // 验证用户名是否可用
        if (userManager.checkUsername(userinfo.getUsername()) > 0) {
            je.setMsg(UtilTool.msgproperty.getProperty("USER_USERNAME_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        StringBuilder sql = null;
        List<Object> objList = null;
        String sex = "1".equals(userinfo.getGender()) ? "男" : "女";
        // 添加user_info
        String userref = UUID.randomUUID().toString();
        userinfo.setRef(userref);
        sql = new StringBuilder();
        objList = userManager.getSaveSql(userinfo, sql);
        if (sql.length() > 0 && objList != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        // 根据身份添加（教师、学生、家长）
        sql = new StringBuilder();
        if (roleid.equals(UtilTool._ROLE_STU_ID.toString())) {
            StudentInfo stu = new StudentInfo();
            stu.setStuname(userinfo.getRealname());
            stu.setStusex(sex);
            stu.getUserinfo().setRef(userref);
            stu.setRef(UUID.randomUUID().toString());
            objList = this.studentManager.getSaveSql(stu, sql);

        } else if (roleid.equals(UtilTool._ROLE_TEACHER_ID.toString())) {
            TeacherInfo t = new TeacherInfo();
            t.setTeachername(userinfo.getRealname());
            t.setUserid(userref);
            t.setTeachersex(sex);
            objList = this.teacherManager.getSaveSql(t, sql);

        } else if (roleid.equals(UtilTool._ROLE_STU_PARENT_ID.toString())) {
            ParentInfo p = new ParentInfo();
            p.setParentname(userinfo.getRealname());
            p.setUserid(userref);
            p.setRef(UUID.randomUUID().toString());
            p.setParentsex(sex);
            objList = this.parentManager.getSaveSql(p, sql);
        }
        if (sql.length() > 0 && objList != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        // 增加用户与角色关联

        RoleUser roleuser = new RoleUser();
        roleuser.getUserinfo().setRef(userref);
        roleuser.setRoleidstr(roleid);
        roleuser.getRoleinfo().setRoleid(Integer.parseInt(roleid));
        List<RoleUser> ruList = this.roleUserManager.getList(roleuser, null);
        // 存在角色信息 则删除
        if (ruList != null && ruList.size() > 0) {
            sql = new StringBuilder();
            objList = this.roleUserManager.getDeleteSql(roleuser, sql);
            if (sql.length() > 0 && objList != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }
        sql = new StringBuilder();
        objList = this.roleUserManager.getSaveSql(roleuser, sql);
        if (sql.length() > 0 && objList != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean flag = this.userManager.doExcetueArrayProc(sqlListArray,
                    objListArray);
            if (flag) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=checkusername", method = RequestMethod.POST)
    public void checkUsername(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String username = request.getParameter("username");
        if (userManager.checkUsername(username) == 0) {
            je.setType("success");
        } else {
            je.setMsg(UtilTool.msgproperty.getProperty("USER_USERNAME_EXISTS"));
            je.setType("error");
        }
        response.getWriter().print(je.toJSON());
    }



    // 此方法由于检查用户是否在本地记录了账号和密码
    /**
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=logincheck", method = RequestMethod.GET)
    public void loginCheck(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String userData = "";
        Cookie[] cookie = request.getCookies();
        boolean auto = false;
        Map<String ,String> um = null;
        if (cookie != null) {
            for (int i = 0; i < cookie.length; i++) {
                String username = "";
                if (cookie[i].getName().equals("SZ_SCHOOL_USER_REC")) {
                    username = cookie[i].getValue();
                    if (username.split("_").length > 1){
                        username=username.split("_")[0];
                        auto = true;
                    }
                    UserInfo userinfo = new UserInfo();
                    userinfo.setRef(username);
                    userinfo = userManager.getUserInfo(userinfo);
                    if (userinfo != null) {
                        userData += userinfo.getUsername() + "|"
                                + MD5.getJDKMD5(userinfo.getPassword());
                        um = new HashMap<String, String>();
                        um.put("u_n",userinfo.getUsername());

                        Date date = new Date();
                        String p_safetime=(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
                        String hour=p_safetime.substring(p_safetime.indexOf(" ")+1,p_safetime.indexOf(":"));
                        um.put("login_hour",hour);
                        um.put("u_p", MD5.getJDKMD5(userinfo.getPassword()
                                + p_safetime.substring(0, p_safetime.indexOf(":"))));
                        um.put("u_a","no_auto");
                        if(auto)
                            um.put("u_a","auto");
                    }
                    break;
                }
            }
            if (um != null) {
                List l = new ArrayList();
                l.add(um);
                je.setObjList(l);
                je.setType("success");
            } else {
                je.setType("error");
            }
        } else {
            je.setType("error");
        }
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=getpassword", method = RequestMethod.GET)
    public void getPassword(HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        if (userinfo != null && userinfo.getUsername() != null) {
            userinfo = userManager.getUserInfo(userinfo);
            if (userinfo != null && userinfo.getPassword() != null) {
                request.getSession().setAttribute("userpassword",
                        userinfo.getPassword());
                je.setMsg("201212181540");
                je.setType("success");
            }
        } else {
            je.setType("error");
        }

        response.getWriter().print(je.toJSON());
    }
    /**
     * etiantian进入
     * @param request
     * @param response
     */
    @RequestMapping(params="m=etttologin",method=RequestMethod.GET)
    public ModelAndView ettToPage(HttpServletRequest request,HttpServletRequest response){
        return new ModelAndView("/outsidepage/ettyanshirole");
    }

    /**
     * etiantian进入
     * @param request
     * @param response
     */
    @RequestMapping(params="m=ettlogin",method=RequestMethod.GET)
    public void ettToLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String roletypeStr=request.getParameter("roletype");
        String callback=request.getParameter("jsoncallback");
        JsonEntity jeEntity=new JsonEntity();
        if(roletypeStr==null||roletypeStr.trim().length()<1){
            jeEntity.setMsg("异常错误，没有发现必要角色类型相关参数! code: roletype is empty!");
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return;
        }

//		String password=UtilTool.utilproperty.getProperty("ETT_COME_PASS");
//		if(password==null||password.trim().length()<1){
//			jeEntity.setMsg("异常错误，项目配置失败! code: ETT_COME_PASS property is empty!");
//			response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return;
//		}
        String userName=null,password=null;

        String ettComeUName=UtilTool.utilproperty.getProperty("ETT_COME_USER");
        if(ettComeUName!=null&&ettComeUName.trim().length()>0){
            String[] ettUName_ARR=ettComeUName.split(",");
            if(ettUName_ARR!=null&&ettUName_ARR.length>0){
                for (String uname : ettUName_ARR) {
                    if(uname!=null&&uname.trim().length()>0){
                        if(uname.indexOf(roletypeStr)==-1)continue;
                        String[] utmp=uname.trim().split("\\|");
                        String roletytmp=utmp[1];
                        String usernametmp=utmp[2];
                        String passtmp=utmp[3];
                        if(roletytmp.trim().equals(roletypeStr.trim())){
                            userName=usernametmp.trim();
                            password=passtmp.trim();
                            break;
                        }
                    }
                }
            }
        }
        /**
         * 四中帐号
         */
        if(userName==null||userName.trim().length()<1){
            jeEntity.setMsg("异常错误，用户名配置失败! code: ETT_COME_USER property is empty!");
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return;
        }
        request.setAttribute("username", userName);
        request.setAttribute("password", password);
        request.setAttribute("isajax", 1);
        request.setAttribute("jsoncallback",callback);
        this.doLogin(request, response);  //进入登陆
    }

    /**
     * 精简版资源系统登陆
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=simpleResLogin",method=RequestMethod.GET)
    public String simpleResLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String userName=UtilTool.simpleResUtilProperty.get("_SIMPLE_RES_LOGIN_USRNAME").toString();
        String pass=UtilTool.simpleResUtilProperty.get("_SIMPLE_RES_LOGIN_PASS").toString();
        JsonEntity jeEntity=new JsonEntity();
        /**
         * 精简版帐号
         */
        if(userName==null||userName.trim().length()<1){
            jeEntity.setMsg("异常错误，用户名配置失败! code: _SIMPLE_RES_LOGIN_USRNAME property is empty!");
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return null;
        }
        if(pass==null||pass.trim().length()<1){
            jeEntity.setMsg("异常错误，密码配置失败! code: _SIMPLE_RES_LOGIN_PASS property is empty!");
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return null;
        }
        request.setAttribute("isajax",1);
        //检测是否拥有
        request.setAttribute("username",userName);
        request.setAttribute("password",pass);
        this.doLogin(request,response);//登陆成功，并登陆至精简版资源首页
        return null;
    }

    /**
     * 登陆核心方法
     * @param userinfo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected JsonEntity loginBase(UserInfo userinfo,HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity je=new JsonEntity();
        je.setMsg("异常错误，原因：参数异常!");
        if(userinfo==null)return je;
        if (userinfo.getUsername() == null || userinfo.getPassword() == null) {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("USER_LOGIN_INFO_UNCOMPLETE"));
            je.setType("error");
        } else {
            if (userManager.checkUsername(userinfo.getUsername()) == 0) {
                je.setMsg(UtilTool.msgproperty
                        .getProperty("USER_LOGIN_USERNAME_UNEXISTS"));
                je.setType("error");
            } else {
                userinfo= userManager.doLogin(userinfo);
                if (userinfo == null) {
                    userinfo = new UserInfo();
                    userinfo.setUsername(userinfo.getUsername());
                    userinfo = userManager.getUserInfo(userinfo);
                    Date date = new Date();
                    String p_safetime=(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
                    if(userinfo == null || !MD5.getJDKMD5(userinfo.getPassword() + p_safetime.substring(0, p_safetime.indexOf(":")))
                            .equals(userinfo.getPassword())){
                        userinfo = null;
                    }
                    je.setMsg(UtilTool.msgproperty.getProperty("USER_LOGIN_PASSWORD_ERROR"));
                }
                if (userinfo != null) {
                    if (userinfo.getPassword().trim().equals("201212181540")
                            && request.getSession()
                            .getAttribute("userpassword") != null) {
                        userinfo.setPassword((String) request.getSession()
                                .getAttribute("userpassword"));
                        userinfo = userManager.doLogin(userinfo);
                    }
                }
                if (userinfo != null) {
                    if(userinfo.getStateid()!=null&&userinfo.getStateid()>0){
                        je.setMsg("该账号已被禁用，有问题请联系本校的系统管理员!");
                        return je;
                    }

                    try {
                        // 获取用户路径权限
						/*RightUser path = new RightUser();
						path.getUserinfo().setUserid(user.getUserid());
						path.getPagerightinfo().setPagerighttype(1);
						List<RightUser> pathRightList = rightUserManager
								.getList(path, null);
						System.out.println("pathRightList.size"
								+ pathRightList.size());
						if (pathRightList != null && pathRightList.size() > 0)
							user.setPathRightList(pathRightList);
						// 获取用户功能权限
						RightUser function = new RightUser();
						function.getUserinfo().setUserid(user.getUserid());
						function.getPagerightinfo().setPagerighttype(2);
						List<RightUser> functionRightList = rightUserManager
								.getList(function, null);
						System.out.println("functionRightList.size"
								+ functionRightList.size());
						if (functionRightList != null
								&& functionRightList.size() > 0)
							user.setFunctionRightList(functionRightList);
*/
                        // 获取角色
                        RoleUser ru = new RoleUser();
                        ru.getUserinfo().setRef(userinfo.getRef());
                        List<RoleUser> ruList = this.roleUserManager.getList(
                                ru, null);
                        userinfo.setCjJRoleUsers(ruList);

                        // 获取班级关系
                        ClassUser cu = new ClassUser();
                        cu.setUserid(userinfo.getRef());
                        List<ClassUser> cuList = this.classUserManager.getList(
                                cu, null);
                        userinfo.setClassUsers(cuList);
                        //获取部门信息
                        DeptUser du = new DeptUser();
                        du.setUserref(userinfo.getRef());
                        List<DeptUser> deptList = this.deptUserManager.getList(du, null);

                        userinfo.setDeptUsers(deptList);

                        UserIdentityInfo uitmp=new UserIdentityInfo();
                        uitmp.setUserid(userinfo.getRef());
                        List<UserIdentityInfo> uidtttList=this.userIdentityManager.getList(uitmp,null);

                        request.getSession().setAttribute("cut_uidentity", uidtttList);//存入Session中
                        request.getSession().setAttribute("CURRENT_USER", userinfo);//存入Session中


                        if(UtilTool._IS_SIMPLE_RESOURCE!=2){
                            //栏目
                            IColumnManager columnManager=(ColumnManager)this.getManager(ColumnManager.class);
                            EttColumnInfo ec=new EttColumnInfo();
                            if(this.validateRole(request,UtilTool._ROLE_STU_ID))
                                ec.setRoletype(2);//学生
                            else
                                ec.setRoletype(1);
                            ec.setSchoolid(userinfo.getDcschoolid());
                            request.getSession().setAttribute("ettColumnList", columnManager.getEttColumnSplit(ec, null));
                        }
                        //查询分校的logo放入session
                        if(userinfo.getDcschoolid()!=null){
                            ISchoolLogoManager schoolLogoManager = (SchoolLogoManager)this.getManager(SchoolLogoManager.class);
                            SchoolLogoInfo sl = new SchoolLogoInfo();
                            sl.setSchoolid(userinfo.getDcschoolid());
                            List<SchoolLogoInfo> logoList = schoolLogoManager.getList(sl,null);
                            if(logoList!=null&&logoList.size()>0){
                                request.getSession().setAttribute("logoObj",logoList.get(0));
                            }
                        }

                        //    userinfo=userinfo;
                        je.setType("success");
                        je.setMsg("登陆成功并记录成功!");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }

                }
            }
        }
        return je;
    }

    @RequestMapping(params = "m=dologin", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        if(request.getAttribute("username")!=null)
            userinfo.setUsername(request.getAttribute("username").toString());
        if(request.getAttribute("password")!=null)
            userinfo.setPassword(request.getAttribute("password").toString());
        String rem=request.getParameter("remember");

        boolean remember =false;
        if(rem!=null&& rem.equals("1"))remember=true;
        String autolo=request.getParameter("autoLogin");
        boolean autoLogin = false;
        if(autolo!=null&&autolo.equals("1"))autoLogin=true;
        je=loginBase(userinfo,request,response);
        if(je.getType().trim().equals("success")){
//            if(UtilTool._IS_SIMPLE_RESOURCE!=2){
//                //栏目
//                IColumnManager columnManager=(ColumnManager)this.getManager(ColumnManager.class);
//                EttColumnInfo ec=new EttColumnInfo();
//                if(this.validateRole(request,UtilTool._ROLE_STU_ID))
//                    ec.setRoletype(2);//学生
//                else
//                    ec.setRoletype(1);
//                request.getSession().setAttribute("ettColumnList", columnManager.getEttColumnSplit(ec, null));
//            }
            request.getSession().setAttribute("fromType","szschool");  //szschool
//                    System.out.println("loginUser:"+(UserInfo) request.getSession().getAttribute("CURRENT_USER"));
            if (remember) {
                Cookie ck = new Cookie("SZ_SCHOOL_USER_REC",userinfo.getRef());
                ck.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(ck);
            }
            if (autoLogin) {

                Cookie ck = new Cookie("SZ_SCHOOL_USER_REC",userinfo.getRef()+"_AUTO");
                ck.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(ck);
						/*Cookie[] cookie = request.getCookies();
						int i = 0;
						if (cookie != null) {
							for (; i < cookie.length; i++) {
								if (cookie[i].getValue().split("\\|")[0]
										.equals("szs_username")) {
									if (cookie[i].getValue().split("\\|").length > 1)
										break;
								}
							}
						}
						if (i < cookie.length) {
							cookie[i].setValue("szs_username");
							response.addCookie(cookie[i]);
						}
						Cookie ck = new Cookie(user.getUsername(),
								"szs_username|auto");
						ck.setMaxAge(7 * 24 * 60 * 60);
						response.addCookie(ck);*/
            }
            if (!remember && !autoLogin) {
                Cookie[] cookie = request.getCookies();
                int i = 0;
                if (cookie != null) {
                    for (; i < cookie.length; i++) {
                        //if (cookie[i].getName().equals(userinfo.getUsername())) {								
                        if (cookie[i].getName().equals("SZ_SCHOOL_USER_REC")) {
                            cookie[i].setMaxAge(0);
                            response.addCookie(cookie[i]);
                            break;
                        }
                    }
                }
            }
            je.setType("success");
        } else {
            //je.setMsg(UtilTool.msgproperty
            //		.getProperty("USER_LOGIN_PASSWORD_ERROR"));
            //je.setType("error");
        }


        Object isajax=request.getAttribute("isajax");
        if(isajax==null||isajax.toString().trim().length()<1||!UtilTool.isNumber(isajax.toString().trim())||Integer.parseInt(isajax.toString().trim())!=1)
            response.getWriter().write(je.toJSON());
        else{

            if(je.getType().equals("success")){
                if(UtilTool._IS_SIMPLE_RESOURCE!=2)
                    response.sendRedirect("user?m=toIndex");
                else
                    response.sendRedirect("simpleRes?m=toindex");
            }else
                response.getWriter().print(je.getAlertMsgAndCloseWin());
        }
    }

    // 当在拦截器中发现自动登录，将调用此方法将 用户信息 写入session
    @RequestMapping(params = "m=autologin", method = RequestMethod.GET)
    public void autoLogin(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        String forwardPath = "/"+UtilTool.utilproperty.getProperty("PROC_NAME");
        UserInfo userinfo = new UserInfo();
        userinfo.setUsername(request.getAttribute("username").toString());
        if (userManager.checkUsername(userinfo.getUsername()) != 0) {
            UserInfo user = userManager.doLogin(userinfo);
            if (user != null) {
                request.getSession().setAttribute("CURRENT_USER", user);
                forwardPath = request.getAttribute("path").toString();
            }
        }
        response.sendRedirect(forwardPath);
    }

    @RequestMapping(params = "m=userquit", method = RequestMethod.GET)
    public void userQuit(HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        request.getSession().removeAttribute("CURRENT_USER");
        request.getSession().setMaxInactiveInterval(0);
        request.setAttribute("state","user_quit");
        if(request.getSession().getAttribute("fromType")!=null)
            request.getSession().removeAttribute("fromType");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @RequestMapping(params = "m=userview", method = RequestMethod.GET)
    public ModelAndView userView(HttpServletRequest request,
                                 HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute(
                "CURRENT_USER");
        if (user == null)
            return new ModelAndView("/user/userView", mp);
        mp.put("user", user);
        RoleUser ru = new RoleUser();
        ru.getUserinfo().setRef(user.getRef());
        List<RoleUser> ruList = roleUserManager.getList(ru, null);
        mp.put("roleUser", ruList);
        return new ModelAndView("/user/userView", mp);
    }


    @RequestMapping(params = "m=offuser", method = RequestMethod.POST)
    public void unUseUser(HttpServletRequest request,
                          HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        String state=request.getParameter("stateid");
        if(ref==null||ref.trim().length()<1||
                state==null||state.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        UserInfo u=new UserInfo();
        u.setRef(ref);
        u.setStateid(Integer.parseInt(state));
        if(this.userManager.doUpdate(u)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * 个人基础信息编辑
     * @param request
     * @param response
     * @param mp
     * @throws Exception
     */
    @RequestMapping(params = "m=edit_base", method = RequestMethod.POST)
    public void edit_base(HttpServletRequest request,
                          HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        String password=request.getParameter("password");
        String realname=request.getParameter("realname");
        String sex=request.getParameter("sex");
        String isTea=request.getParameter("isTea");
        String isStu=request.getParameter("isStu");

        if(ref==null||ref.trim().length()<1
                ||password==null||password.trim().length()<1
                ||realname==null||realname.trim().length()<1
                ||sex==null||sex.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            return;
        }
        List<String>sqllist=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<Object>objList=null;
        StringBuilder sql=null;

        //基本信息
        UserInfo u=new UserInfo();
        u.setPassword(password);
        u.setRef(ref);
        sql=new StringBuilder();
        objList=this.userManager.getUpdateSql(u, sql);
        if(objList!=null&&objList.size()>0){
            objListArray.add(objList);
            sqllist.add(sql.toString());
        }


        if(isTea!=null&&isTea.equals("true")){
            TeacherInfo t=new TeacherInfo();
            t.setTeachername(realname);
            t.setTeachersex(sex);
            t.setUserid(ref);

            sql=new StringBuilder();
            objList=this.teacherManager.getUpdateSql(t, sql);
            if(objList!=null&&objList.size()>0){
                objListArray.add(objList);
                sqllist.add(sql.toString());
            }
        }else if(isStu!=null&&isStu.equals("true")){
            StudentInfo s=new StudentInfo();
            s.setStuname(realname);
            s.setStusex(sex);
            s.setUserref(ref);
            sql=new StringBuilder();
            objList=this.studentManager.getUpdateSql(s, sql);
            if(objList!=null&&objList.size()>0){
                objListArray.add(objList);
                sqllist.add(sql.toString());
            }
        }

        if(objListArray.size()>0&&sqllist.size()>0){
            boolean flag=this.userManager.doExcetueArrayProc(sqllist, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                u=new UserInfo();
                u.setRef(ref);
                UserInfo user=this.userManager.getUserInfo(u);
                if(user!=null)
                    je.getObjList().add(user);

                List<UserInfo> userInfoList=new ArrayList<UserInfo>();
                userInfoList.add(user);
                //调用用户接口
                if(EttInterfaceUserUtil.updateUserBase(userInfoList)){
                    System.out.println("用户信息同步至网校成功!更新");
                }else{
                    System.out.println("用户信息同步至网校失败!更新");
                }
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 用户角色信息编辑
     * @param request
     * @param response
     * @param mp
     * @throws Exception
     */

    @RequestMapping(params = "m=edit_role", method = RequestMethod.POST)
    public void edit_role(HttpServletRequest request,
                          HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        String isTea=request.getParameter("isTea");
        String isStu=request.getParameter("isStu");
        String clsstr=request.getParameter("clsstr");
        String rolestr=request.getParameter("rolestr");
        String adminstr=request.getParameter("adminstr");
        String gradeid=request.getParameter("gradeid");
        String tchid=request.getParameter("tchid");
        String deptid=request.getParameter("deptid");
        String prepareid=request.getParameter("prepareid");
        String deptfzrid=request.getParameter("deptfzrid");

        if(ref==null||ref.trim().length()<1
                ||isTea==null||isTea.trim().length()<1
                ||isStu==null||isStu.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            return;
        }
        //用来记录操作过的classid
        List<Integer> operateCls=new ArrayList<Integer>();

        List<String>sqllist=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<Object>objList=null;
        StringBuilder sql=null;
        //获取当前学年
        List<ClassYearInfo>clsyearList=this.classYearManager.getCurrentYearList(null);
        if(clsyearList==null||clsyearList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("YEAR_EMPTY"));
            return;
        }

        //删除角色
        RoleUser rdelete=new RoleUser();
        rdelete.getUserinfo().setRef(ref);
        sql=new StringBuilder();
        objList=this.roleUserManager.getDeleteSql(rdelete, sql);
        if(objList!=null&&objList.size()>0){
            objListArray.add(objList);
            sqllist.add(sql.toString());
        }

        //删除用户权限
        UserColumnRightInfo urdelete=new UserColumnRightInfo();
        urdelete.setUserid(ref);
        sql=new StringBuilder();
        objList=this.userColumnRightManager.getDeleteSql(urdelete, sql);
        if(objList!=null&&sql!=null){
            objListArray.add(objList);
            sqllist.add(sql.toString());
        }

        if(isTea!=null&&isTea.equals("true")){
            //删除管理班级
            ClassUser cdelete=new ClassUser();
            cdelete.setRelationtype("班主任");
            cdelete.setYear(clsyearList.get(0).getClassyearvalue());
            cdelete.setUserid(ref);
            //查询删除的班级
            List<ClassUser> cuList=this.classUserManager.getList(cdelete,null);
            if(cuList!=null&&cuList.size()>0){
                for (ClassUser tmpCu:cuList){
                    if(tmpCu!=null&&tmpCu.getClassid()!=null&&!operateCls.contains(tmpCu.getClassid())){
                        operateCls.add(tmpCu.getClassid());
                    }
                }
            }
            sql=new StringBuilder();
            objList=this.classUserManager.getDeleteSql(cdelete, sql);
            if(objList!=null&&objList.size()>0){
                objListArray.add(objList);
                sqllist.add(sql.toString());
            }

            //删除部门职务
            DeptUser dudelete=new DeptUser();
            dudelete.setUserref(ref);
            sql=new StringBuilder();
            objList=this.deptUserManager.getDeleteSql(dudelete, sql);
            if(objList!=null&&objList.size()>0){
                objListArray.add(objList);
                sqllist.add(sql.toString());
            }
			
			/*
			//删除部门主任
			DeptUser dudelete=new DeptUser();
			dudelete.setRoleid(UtilTool._ROLE_DEPT_LEADER_ID);
			dudelete.setUserref(ref);
			sql=new StringBuilder();
			objList=this.deptUserManager.getDeleteSql(dudelete, sql);
			if(objList!=null&&objList.size()>0){
				objListArray.add(objList);
				sqllist.add(sql.toString());
			}
			
			//删除年级组长
			DeptUser gradedelete=new DeptUser();
			gradedelete.setRoleid(UtilTool._ROLE_GRADE_LEADER_ID);
			gradedelete.setUserref(ref);
			sql=new StringBuilder();
			objList=this.deptUserManager.getDeleteSql(gradedelete, sql);
			if(objList!=null&&objList.size()>0){
				objListArray.add(objList);
				sqllist.add(sql.toString());
			}
			
			//删除教研组长
			DeptUser tchdelete=new DeptUser();
			tchdelete.setRoleid(UtilTool._ROLE_TEACH_LEADER_ID);
			tchdelete.setUserref(ref);
			sql=new StringBuilder();
			objList=this.deptUserManager.getDeleteSql(tchdelete, sql);
			if(objList!=null&&objList.size()>0){
				objListArray.add(objList);
				sqllist.add(sql.toString());
			}*/


            //添加默认角色
            RoleUser tea=new RoleUser();
            tea.getUserinfo().setRef(ref);
            tea.setRoleid(UtilTool._ROLE_TEACHER_ID);
            tea.setRef(this.roleUserManager.getNextId());
            sql=new StringBuilder();
            objList=this.roleUserManager.getSaveSql(tea, sql);
            if(objList!=null&&objList.size()>0){
                objListArray.add(objList);
                sqllist.add(sql.toString());
            }

            //添加默认角色权限
            RoleColumnRightInfo rc=new RoleColumnRightInfo();
            rc.setRoleid(UtilTool._ROLE_TEACHER_ID);
            List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);
            if(rcList!=null&&rcList.size()>0){
                for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                    UserColumnRightInfo ucr=new UserColumnRightInfo();
                    ucr.setColumnid(roleColumnRightInfo.getColumnid());
                    ucr.setUserid(ref);
                    ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                    ucr.setRef(this.userColumnRightManager.getNextId());
                    sql=new StringBuilder();
                    objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                    if(objList!=null&&sql!=null){
                        sqllist.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }

            if(rolestr!=null&&rolestr.length()>0){
                //角色
                String[]roleArray=rolestr.split(",");
                for (String roleid : roleArray) {
                    RoleUser radd=new RoleUser();
                    radd.getUserinfo().setRef(ref);
                    radd.setRoleid(Integer.parseInt(roleid));
                    radd.setRef(this.roleUserManager.getNextId());

                    sql=new StringBuilder();
                    objList=this.roleUserManager.getSaveSql(radd, sql);
                    if(objList!=null&&objList.size()>0){
                        objListArray.add(objList);
                        sqllist.add(sql.toString());
                    }

                    //添加角色权限
                    RoleColumnRightInfo rcr=new RoleColumnRightInfo();
                    rcr.setRoleid(Integer.parseInt(roleid));
                    List<RoleColumnRightInfo>rcrList=this.roleColumnRightManager.getList(rcr, null);
                    if(rcrList!=null&&rcrList.size()>0){
                        for (RoleColumnRightInfo roleColumnRightInfo : rcrList) {
                            UserColumnRightInfo ucr=new UserColumnRightInfo();
                            ucr.setColumnid(roleColumnRightInfo.getColumnid());
                            ucr.setUserid(ref);
                            ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                            ucr.setRef(this.userColumnRightManager.getNextId());
                            sql=new StringBuilder();
                            objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                            if(objList!=null&&sql!=null){
                                sqllist.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }


                    //班主任管理班级
                    if(roleid.equals(UtilTool._ROLE_CLASSADVISE_ID.toString())){
                        if(clsstr!=null&&clsstr.length()>0){
                            String[]clsArray=clsstr.split(",");
                            for (String clsid : clsArray) {
                                //删除该班级班主任角色信息
                                ClassUser selBzr=new ClassUser();
                                selBzr.setRelationtype("班主任");
                                selBzr.setClassid(Integer.parseInt(clsid));
                                List<ClassUser>clsBzrList=this.classUserManager.getList(selBzr,null);
                                if(clsBzrList!=null&&clsBzrList.size()>0){
                                    for(ClassUser classUser:clsBzrList){
                                        RoleUser roleUser=new RoleUser();
                                        roleUser.setRoleid(UtilTool._ROLE_CLASSADVISE_ID);
                                        roleUser.setUserid(classUser.getUserid());
                                        sql=new StringBuilder();
                                        objList=this.roleUserManager.getDeleteSql(roleUser,sql);
                                        if (objList != null && sql != null) {
                                            sqllist.add(sql.toString());
                                            objListArray.add(objList);
                                        }
                                        //记录相关班级，同步网校
                                        if(!operateCls.contains(Integer.parseInt(clsid.toString())))
                                            operateCls.add(Integer.parseInt(clsid.toString()));
                                    }
                                }
                                //删除该班级班主任
                                ClassUser deleteBzr=new ClassUser();
                                deleteBzr.setRelationtype("班主任");
                                deleteBzr.setClassid(Integer.parseInt(clsid));
                                sql = new StringBuilder();
                                objList = this.classUserManager.getDeleteSql(deleteBzr,sql);
                                if (objList != null && sql != null) {
                                    sqllist.add(sql.toString());
                                    objListArray.add(objList);
                                }

                                ClassUser clsu=new ClassUser();
                                clsu.setRef(this.classUserManager.getNextId());
                                clsu.setClassid(Integer.parseInt(clsid));
                                clsu.setRelationtype("班主任");
                                clsu.getUserinfo().setRef(ref);

                                sql=new StringBuilder();
                                objList=this.classUserManager.getSaveSql(clsu, sql);
                                if(objList!=null&&objList.size()>0){
                                    objListArray.add(objList);
                                    sqllist.add(sql.toString());
                                }
                                //记录相关班级，同步网校
                                if(!operateCls.contains(Integer.parseInt(clsid)))
                                    operateCls.add(Integer.parseInt(clsid));

                            }
                            //添加班主任角色
                            RoleUser roleUser=new RoleUser();
                            roleUser.setRoleid(UtilTool._ROLE_CLASSADVISE_ID);
                            roleUser.setUserid(ref);
                            sql=new StringBuilder();
                            objList=this.roleUserManager.getSaveSql(roleUser,sql);
                            if (objList != null && sql != null) {
                                sqllist.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }else if(roleid.equals(UtilTool._ROLE_DEPT_LEADER_ID.toString())||roleid.equals(UtilTool._ROLE_DEPT_FU_LEADER_ID.toString())){
                        if(deptid!=null&&deptid.trim().length()>0){
                            DeptUser deptleader=new DeptUser();
                            deptleader.setDeptid(Integer.parseInt(deptid));
                            deptleader.setUserref(ref);
                            deptleader.setRoleid(Integer.parseInt(roleid));
                            deptleader.setRef(this.deptUserManager.getNextId());
                            sql=new StringBuilder();
                            objList=this.deptUserManager.getSaveSql(deptleader, sql);
                            if(objList!=null&&objList.size()>0){
                                objListArray.add(objList);
                                sqllist.add(sql.toString());
                            }
                        }
                    }else if(roleid.equals(UtilTool._ROLE_GRADE_LEADER_ID.toString())||roleid.equals(UtilTool._ROLE_GRADE_FU_LEADER_ID.toString())){
                        if(gradeid!=null&&gradeid.trim().length()>0){
                            DeptUser gradeleader=new DeptUser();
                            gradeleader.setDeptid(Integer.parseInt(gradeid));
                            gradeleader.setUserref(ref);
                            gradeleader.setRoleid(Integer.parseInt(roleid));
                            gradeleader.setRef(this.deptUserManager.getNextId());
                            sql=new StringBuilder();
                            objList=this.deptUserManager.getSaveSql(gradeleader, sql);
                            if(objList!=null&&objList.size()>0){
                                objListArray.add(objList);
                                sqllist.add(sql.toString());
                            }
                        }
                    }else if(roleid.equals(UtilTool._ROLE_TEACH_LEADER_ID.toString())||roleid.equals(UtilTool._ROLE_TEACH_FU_LEADER_ID.toString())){
                        if(tchid!=null&&tchid.trim().length()>0){
                            DeptUser tchleader=new DeptUser();
                            tchleader.setDeptid(Integer.parseInt(tchid));
                            tchleader.setUserref(ref);
                            tchleader.setRoleid(Integer.parseInt(roleid));
                            tchleader.setRef(this.deptUserManager.getNextId());
                            sql=new StringBuilder();
                            objList=this.deptUserManager.getSaveSql(tchleader, sql);
                            if(objList!=null&&objList.size()>0){
                                objListArray.add(objList);
                                sqllist.add(sql.toString());
                            }
                        }
                    }else if(roleid.equals(UtilTool._ROLE_PREPARE_LEADER_ID.toString())){
                        if(prepareid!=null&&prepareid.trim().length()>0){
                            DeptUser tchleader=new DeptUser();
                            tchleader.setDeptid(Integer.parseInt(prepareid));
                            tchleader.setUserref(ref);
                            tchleader.setRoleid(Integer.parseInt(roleid));
                            tchleader.setRef(this.deptUserManager.getNextId());
                            sql=new StringBuilder();
                            objList=this.deptUserManager.getSaveSql(tchleader, sql);
                            if(objList!=null&&objList.size()>0){
                                objListArray.add(objList);
                                sqllist.add(sql.toString());
                            }
                        }
                    }else if(roleid.equals(UtilTool._ROLE_FREE_DEPT_LEADER_ID.toString())){
                        if(deptfzrid!=null&&deptfzrid.trim().length()>0){
                            DeptUser tchleader=new DeptUser();
                            tchleader.setDeptid(Integer.parseInt(deptfzrid));
                            tchleader.setUserref(ref);
                            tchleader.setRoleid(Integer.parseInt(roleid));
                            tchleader.setRef(this.deptUserManager.getNextId());
                            sql=new StringBuilder();
                            objList=this.deptUserManager.getSaveSql(tchleader, sql);
                            if(objList!=null&&objList.size()>0){
                                objListArray.add(objList);
                                sqllist.add(sql.toString());
                            }
                        }
                    }
                }
            }

            //添加管理员角色
            if(adminstr!=null&&adminstr.trim().length()>0){
                String[]adminArray=adminstr.split(",");
                for (String adminid : adminArray) {
                    RoleUser radd=new RoleUser();
                    radd.getUserinfo().setRef(ref);
                    radd.setRoleid(Integer.parseInt(adminid));
                    sql=new StringBuilder();
                    objList=this.roleUserManager.getSaveSql(radd, sql);
                    if(objList!=null&&objList.size()>0){
                        objListArray.add(objList);
                        sqllist.add(sql.toString());
                    }


                    //添加管理员角色权限
                    RoleColumnRightInfo rcr=new RoleColumnRightInfo();
                    rcr.setRoleid(Integer.parseInt(adminid));
                    List<RoleColumnRightInfo>rcrList=this.roleColumnRightManager.getList(rcr, null);
                    if(rcrList!=null&&rcrList.size()>0){
                        for (RoleColumnRightInfo roleColumnRightInfo : rcrList) {
                            UserColumnRightInfo ucr=new UserColumnRightInfo();
                            ucr.setColumnid(roleColumnRightInfo.getColumnid());
                            ucr.setUserid(ref);
                            ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                            ucr.setRef(this.userColumnRightManager.getNextId());
                            sql=new StringBuilder();
                            objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                            if(objList!=null&&sql!=null){
                                sqllist.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }
                }
            }
        }else if(isStu!=null&&isStu.equals("true")){
            //添加默认角色
            RoleUser stu=new RoleUser();
            stu.getUserinfo().setRef(ref);
            stu.setRoleid(UtilTool._ROLE_STU_ID);
            sql=new StringBuilder();
            objList=this.roleUserManager.getSaveSql(stu, sql);
            if(objList!=null&&objList.size()>0){
                objListArray.add(objList);
                sqllist.add(sql.toString());
            }

            //添加默认角色权限
            RoleColumnRightInfo rcr=new RoleColumnRightInfo();
            rcr.setRoleid(UtilTool._ROLE_STU_ID);
            List<RoleColumnRightInfo>rcrList=this.roleColumnRightManager.getList(rcr, null);
            if(rcrList!=null&&rcrList.size()>0){
                for (RoleColumnRightInfo roleColumnRightInfo : rcrList) {
                    UserColumnRightInfo ucr=new UserColumnRightInfo();
                    ucr.setColumnid(roleColumnRightInfo.getColumnid());
                    ucr.setUserid(ref);
                    ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                    ucr.setRef(this.userColumnRightManager.getNextId());
                    sql=new StringBuilder();
                    objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                    if(objList!=null&&sql!=null){
                        sqllist.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }


            if(rolestr!=null&&rolestr.length()>0){
                //角色
                String[]roleArray=rolestr.split(",");
                for (String roleid : roleArray) {
                    RoleUser radd=new RoleUser();
                    radd.getUserinfo().setRef(ref);
                    radd.setRoleid(Integer.parseInt(roleid));
                    sql=new StringBuilder();
                    objList=this.roleUserManager.getSaveSql(radd, sql);
                    if(objList!=null&&objList.size()>0){
                        objListArray.add(objList);
                        sqllist.add(sql.toString());
                    }


                    //添加默认角色权限
                    RoleColumnRightInfo rcrstu=new RoleColumnRightInfo();
                    rcrstu.setRoleid(Integer.parseInt(roleid));
                    List<RoleColumnRightInfo>rcrstuList=this.roleColumnRightManager.getList(rcrstu, null);
                    if(rcrstuList!=null&&rcrstuList.size()>0){
                        for (RoleColumnRightInfo roleColumnRightInfo : rcrstuList) {
                            UserColumnRightInfo ucr=new UserColumnRightInfo();
                            ucr.setColumnid(roleColumnRightInfo.getColumnid());
                            ucr.setUserid(ref);
                            ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                            ucr.setRef(this.userColumnRightManager.getNextId());
                            sql=new StringBuilder();
                            objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                            if(objList!=null&&sql!=null){
                                sqllist.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }
                }
            }

        }

        if(objListArray.size()>0&&sqllist.size()>0){
            boolean flag=this.userManager.doExcetueArrayProc(sqllist, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");

                //重置edit_role信息
                RoleUser ru=new RoleUser();
                ru.getUserinfo().setRef(ref);
                List<RoleUser> ruList=this.roleUserManager.getList(ru, null);
                je.getObjList().add(ruList);

                if(isTea!=null&&isTea.equals("true")){
                    ClassUser teacu=new ClassUser();
                    teacu.setYear(clsyearList.get(0).getClassyearvalue());
                    teacu.setRelationtype("班主任");
                    teacu.setUserid(ref);

                    List<ClassUser>teaClsList=this.classUserManager.getList(teacu, null);
                    je.getObjList().add(teaClsList);

                    List<DeptUser>duList=null;
                    DeptUser du=new DeptUser();
                    du.setUserref(ref);
                    du.setRoleflag(1);
                    du.setTypeid(1);
                    duList=this.deptUserManager.getList(du, null);
                    je.getObjList().add(duList);

                    du.setTypeid(2);
                    duList=this.deptUserManager.getList(du, null);
                    je.getObjList().add(duList);

                    du.setTypeid(3);
                    duList=this.deptUserManager.getList(du, null);
                    je.getObjList().add(duList);

                    du.setTypeid(5);
                    duList=this.deptUserManager.getList(du, null);
                    je.getObjList().add(duList);

                    du.setTypeid(4);
                    duList=this.deptUserManager.getList(du, null);
                    je.getObjList().add(duList);
                }else if(isStu!=null&&isStu.equals("true")){


                }
                //同步班级信息
                if(operateCls.size()>0){
                    if(updateToEttClassUser(operateCls,this.logined(request).getDcschoolid())){
                        System.out.println("班主任提交网校成功!");
                    }else
                        System.out.println("班主任提交网校失败!");
                }

            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 教职工、学生信息编辑
     * @param request
     * @param response
     * @param mp
     * @throws Exception
     */

    @RequestMapping(params = "m=edit_info", method = RequestMethod.POST)
    public void edit_info(HttpServletRequest request,
                          HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        String isTea=request.getParameter("isTea");
        String isStu=request.getParameter("isStu");
        String clsstr=request.getParameter("clsstr");

        String linkman=request.getParameter("linkman");
        String linkphone=request.getParameter("linkphone");
        String stuaddress=request.getParameter("stuaddress");

        String mail=request.getParameter("mail");
        String phone=request.getParameter("phone");
        String address=request.getParameter("address");
        String majorstr=request.getParameter("majorstr");
        String subjectstr=request.getParameter("subjectstr");


        if(ref==null||ref.trim().length()<1
                ||isTea==null||isTea.trim().length()<1
                ||isStu==null||isStu.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<String>sqllist=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<Object>objList=null;
        StringBuilder sql=null;
        //获取当前学年
        List<ClassYearInfo>clsyearList=this.classYearManager.getCurrentYearList(null);
        if(clsyearList==null||clsyearList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("YEAR_EMPTY"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Integer> operateCls=new ArrayList<Integer>();
        if(isTea!=null&&isTea.equals("true")){
            TeacherInfo t=new TeacherInfo();
            t.setUserid(ref);
            if(mail!=null&&mail.trim().length()>0)
                t.setTeacherpost(mail);
            if(phone!=null&&phone.trim().length()>0)
                t.setTeacherphone(phone);
            if(address!=null&&address.trim().length()>0)
                t.setTeacheraddress(address);
            sql=new StringBuilder();
            objList=this.teacherManager.getUpdateSql(t, sql);
            if(objList!=null&&objList.size()>0){
                objListArray.add(objList);
                sqllist.add(sql.toString());
            }
            //主授学科
            if(majorstr!=null&&majorstr.length()>0){
                SubjectUser sdelete = new SubjectUser();
                sdelete.getUserinfo().setRef(ref);
                sql = new StringBuilder();
                objList = this.subjectUserManager.getDeleteSql(sdelete,
                        sql);
                if (objList != null && sql != null) {
                    sqllist.add(sql.toString());
                    objListArray.add(objList);
                }

                SubjectUser su = new SubjectUser();
                su.getSubjectinfo().setSubjectid(Integer.parseInt(majorstr));
                su.getUserinfo().setRef(ref);
                su.setIsmajor(1);
                sql = new StringBuilder();
                objList = this.subjectUserManager.getSaveSql(su, sql);
                if (objList != null && sql != null) {
                    sqllist.add(sql.toString());
                    objListArray.add(objList);
                }
            }
            //辅授学科
            if (subjectstr != null && subjectstr.trim().length() > 0) {
                String[] subjectArray = subjectstr.split(",");
                if (subjectArray.length > 0) {
                    for (int i = 0; i < subjectArray.length; i++) {
                        SubjectUser su = new SubjectUser();
                        su.getSubjectinfo().setSubjectid(
                                Integer.parseInt(subjectArray[i]));
                        su.getUserinfo().setRef(ref);
                        su.setIsmajor(0);

                        sql = new StringBuilder();
                        objList = this.subjectUserManager.getSaveSql(su, sql);
                        if (objList != null && sql != null) {
                            sqllist.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }

            //删除当前学年及以后的班级
            ClassUser cudelete=new ClassUser();
            cudelete.setAfteryear(clsyearList.get(0).getClassyearvalue());
            cudelete.getUserinfo().setRef(ref);
            cudelete.setRelationtype("任课老师");
            List<ClassUser> cuList=this.classUserManager.getList(cudelete,null);
            if(cuList!=null&&cuList.size()>0){
                for (ClassUser tmpCu:cuList){
                    if(tmpCu!=null&&tmpCu.getClassid()!=null){
                        if(!operateCls.contains(tmpCu.getClassid()))
                            operateCls.add(tmpCu.getClassid());
                    }
                }
            }

            sql=new StringBuilder();
            objList=this.classUserManager.getDeleteSql(cudelete, sql);
            if (objList != null && sql != null) {
                sqllist.add(sql.toString());
                objListArray.add(objList);
            }

            //班级
            if(clsstr!=null&&clsstr.length()>0){
                //添加任课班级
                String[]clsArray=clsstr.split(",");
                for (int i = 0; i < clsArray.length; i++) {
                    String[] csidArray = clsArray[i].toString().split("\\|");

                    ClassUser cuadd=new ClassUser();
                    cuadd.setRef(this.classUserManager.getNextId());
                    cuadd.getUserinfo().setRef(ref);
                    cuadd.setRelationtype("任课老师");
                    cuadd.setSubjectid(Integer.parseInt(csidArray[1]));
                    cuadd.setClassid(Integer.parseInt(csidArray[0]));

                    if(!operateCls.contains(Integer.parseInt(csidArray[0])))
                        operateCls.add(Integer.parseInt(csidArray[0]));


                    sql=new StringBuilder();
                    objList=this.classUserManager.getSaveSql(cuadd, sql);
                    if (objList != null && sql != null) {
                        sqllist.add(sql.toString());
                        objListArray.add(objList);
                    }
                }

            }


        }else if(isStu!=null&&isStu.equals("true")){
            StudentInfo s=new StudentInfo();
            s.setUserref(ref);
            if(linkman!=null&&linkman.trim().length()>0)
                s.setLinkman(linkman);
            if(linkphone!=null&&linkphone.trim().length()>0)
                s.setLinkmanphone(linkphone);
            if(stuaddress!=null&&stuaddress.trim().length()>0)
                s.setStuaddress(stuaddress);

            sql=new StringBuilder();
            objList=this.studentManager.getUpdateSql(s, sql);
            if(objList!=null&&objList.size()>0){
                objListArray.add(objList);
                sqllist.add(sql.toString());
            }

            //删除当前学年及以后的班级
            ClassUser cudelete=new ClassUser();
            cudelete.setAfteryear(clsyearList.get(0).getClassyearvalue());
            cudelete.getUserinfo().setRef(ref);
            cudelete.setRelationtype("学生");


            List<ClassUser> cuList=this.classUserManager.getList(cudelete,null);
            if(cuList!=null&&cuList.size()>0){
                for (ClassUser tmpCu:cuList){
                    if(tmpCu!=null&&tmpCu.getClassid()!=null){
                        if(!operateCls.contains(tmpCu.getClassid()))
                            operateCls.add(tmpCu.getClassid());
                    }
                }
            }

            sql=new StringBuilder();
            objList=this.classUserManager.getDeleteSql(cudelete, sql);
            if (objList != null && sql != null) {
                sqllist.add(sql.toString());
                objListArray.add(objList);
            }

            //班级
            Set<String>gradeValueSet=new HashSet<String>();
            if(clsstr!=null&&clsstr.length()>0){

                //添加班级
                String[]clsArray=clsstr.split(",");
                for (int i = 0; i < clsArray.length; i++) {
                    ClassInfo classInfo=new ClassInfo();
                    classInfo.setClassid(Integer.parseInt(clsArray[i]));
                    List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
                    if(clsList!=null&&clsList.size()>0)
                        gradeValueSet.add(clsList.get(0).getClassgrade());

                    ClassUser cuadd=new ClassUser();
                    cuadd.setRef(this.classUserManager.getNextId());
                    cuadd.getUserinfo().setRef(ref);
                    cuadd.setRelationtype("学生");
                    cuadd.setClassid(Integer.parseInt(clsArray[i]));


                    if(!operateCls.contains(Integer.parseInt(clsArray[i])))
                        operateCls.add(Integer.parseInt(clsArray[i]));

                    sql=new StringBuilder();
                    objList=this.classUserManager.getSaveSql(cuadd, sql);
                    if (objList != null && sql != null) {
                        sqllist.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }

            if(gradeValueSet.size()>1){
                je.setMsg("提示：同一学年学生不可存在多个年级中，请修改!");
                response.getWriter().print(je.toJSON());
                return;
            }


        }

        if(objListArray.size()>0&&sqllist.size()>0){
            boolean flag=this.userManager.doExcetueArrayProc(sqllist, objListArray);
            if(flag){
                UserInfo utmp=new UserInfo();
                utmp.setRef(ref);
                UserInfo u=userManager.getUserInfo(utmp);
                if(u!=null){
                    if(updateToEttClassUser(operateCls,u.getDcschoolid())){
                        System.out.println("更新classuser到网校成功！");
                    }else{
                        System.out.println("更新classuser到网校失败！");
                    }
                }

                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");

                if(isTea!=null&&isTea.equals("true")){
                    TeacherInfo t=new TeacherInfo();
                    t.setUserid(ref);
                    List<TeacherInfo>teaList=this.teacherManager.getList(t, null);

                    ClassUser teacu=new ClassUser();
                    teacu.setYear(clsyearList.get(0).getClassyearvalue());
                    teacu.setRelationtype("任课老师");
                    teacu.setUserid(ref);
                    List<ClassUser>teaClsList=this.classUserManager.getList(teacu, null);

                    teacu.setHistoryyear(clsyearList.get(0).getClassyearvalue());
                    teacu.setYear(null);
                    List<ClassUser>teaHistoryClsList=this.classUserManager.getList(teacu, null);

                    SubjectUser su=new SubjectUser();
                    su.setUserid(ref);
                    List<SubjectUser>subjectUserList=this.subjectUserManager.getList(su, null);

                    //重置edit_info信息
                    je.getObjList().add(teaList.get(0));
                    je.getObjList().add(teaClsList);
                    je.getObjList().add(teaHistoryClsList);
                    je.getObjList().add(subjectUserList);
                }else if(isStu!=null&&isStu.equals("true")){
                    StudentInfo s=new StudentInfo();
                    s.setUserref(ref);
                    List<StudentInfo>stuList=this.studentManager.getList(s, null);

                    ClassUser stucu=new ClassUser();
                    stucu.setRelationtype("学生");
                    stucu.setYear(clsyearList.get(0).getClassyearvalue());
                    stucu.setUserid(ref);
                    List<ClassUser>stuClsList=this.classUserManager.getList(stucu, null);

                    stucu.setHistoryyear(clsyearList.get(0).getClassyearvalue());
                    stucu.setYear(null);
                    List<ClassUser>stuHistoryClsList=this.classUserManager.getList(stucu, null);

                    //重置edit_info信息
                    je.getObjList().add(stuList.get(0));
                    je.getObjList().add(stuClsList);
                    je.getObjList().add(stuHistoryClsList);
                }

            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }



    @RequestMapping(params = "m=adminview", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView adminView(HttpServletRequest request,
                                  HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        if(ref==null||ref.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        String identityname=request.getParameter("identityname");
        request.setAttribute("identityname", identityname);
        request.setAttribute("rolestr", request.getParameter("rolestr"));
        request.setAttribute("username", request.getParameter("username"));

        UserInfo u=new UserInfo();
        u.setRef(ref);
        List<UserInfo>userList=userManager.getList(u, null);
        UserInfo user=null;
        if(userList!=null&&userList.size()>0)
            user=userList.get(0);
        if (user == null)
            return new ModelAndView("/user/userView", mp);
        mp.put("user", user);
        RoleUser ru = new RoleUser();
        ru.getUserinfo().setRef(user.getRef());
        List<RoleUser> ruList = roleUserManager.getList(ru, null);
        if (ruList != null && ruList.size() > 0) {
            mp.put("roleUser", ruList);
            mp.put("role", ruList.get(0).getRoleid());
            request.getSession().setAttribute("USER_ROLE", ruList.get(0));
        }
        mp.put("ref", ref);
        //当前及以后学年
        List<ClassYearInfo>clsyearList=this.classYearManager.getCurrentYearList("more");
        //当前学年
        List<ClassYearInfo>classyearList=this.classYearManager.getCurrentYearList(null);
        if(classyearList==null||classyearList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("YEAR_EMPTY"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //身份角色
        IdentityInfo i=new IdentityInfo();
        i.setIdentityname(user.getIdentityname());
        List<IdentityInfo>identityList=this.identityManager.getList(i, null);

        StudentInfo stuInfo =new StudentInfo();
        stuInfo.setUserref(ref);
        List<StudentInfo>stuList=this.studentManager.getList(stuInfo, null);
        if(stuList!=null&&stuList.size()>0)
            mp.put("stuinfo", stuList.get(0));
        //学生班级
        ClassUser stucu=new ClassUser();
        stucu.setRelationtype("学生");
        stucu.setYear(classyearList.get(0).getClassyearvalue());
        stucu.setUserid(ref);
        List<ClassUser>stuClsList=this.classUserManager.getList(stucu, null);

        stucu.setHistoryyear(classyearList.get(0).getClassyearvalue());
        stucu.setYear(null);
        List<ClassUser>stuHistoryClsList=this.classUserManager.getList(stucu, null);

        //教师班级
        TeacherInfo teaInfo =new TeacherInfo();
        teaInfo.setUserid(ref);
        List<TeacherInfo>teaList=this.teacherManager.getList(teaInfo, null);
        if(teaList!=null&&teaList.size()>0)
            mp.put("teainfo", teaList.get(0));
        ClassUser teacu=new ClassUser();
        teacu.setYear(classyearList.get(0).getClassyearvalue());
        teacu.setRelationtype("任课老师");
        teacu.setUserid(ref);
        List<ClassUser>teaClsList=this.classUserManager.getList(teacu, null);

        teacu.setHistoryyear(classyearList.get(0).getClassyearvalue());
        teacu.setYear(null);
        List<ClassUser>teaHistoryClsList=this.classUserManager.getList(teacu, null);

        //年级
        PageResult gradepage=new PageResult();
        gradepage.setPageNo(0);
        gradepage.setPageSize(0);
        gradepage.setOrderBy("CASE grade_value	WHEN '高一' THEN 1 WHEN '高二' THEN 2 WHEN '高三' THEN 3 END");
        List<GradeInfo>gradeList=this.gradeManager.getList(null,gradepage);

        //学科
        List<SubjectInfo> subjectList = this.subjectManager.getList(null, null);
        SubjectUser su=new SubjectUser();
        su.setUserid(ref);
        List<SubjectUser>subjectUserList=this.subjectUserManager.getList(su, null);

        //班主任
        ClassUser clsbzr=new ClassUser();
        clsbzr.setYear(classyearList.get(0).getClassyearvalue());
        clsbzr.setRelationtype("班主任");
        clsbzr.setUserid(ref);
        List<ClassUser>clsBzrList=this.classUserManager.getList(clsbzr, null);
        //部门 主任
        DeptUser du=new DeptUser();
        du.setUserref(ref);
        du.setTypeid(1);
        du.setRoleflag(1);
        List<DeptUser>duList=this.deptUserManager.getList(du, null);
        //教研组长
        du.setTypeid(2);
        List<DeptUser>tchList=this.deptUserManager.getList(du, null);
        //年级组长
        du.setTypeid(3);
        List<DeptUser>gradeLeaderList=this.deptUserManager.getList(du, null);
        //部门负责人
        du.setTypeid(4);
        List<DeptUser>deptfuzerenList=this.deptUserManager.getList(du, null);
        //备课组长
        du.setTypeid(5);
        List<DeptUser>prepareLeaderList=this.deptUserManager.getList(du, null);



        //部门 主任
        DeptInfo d=new DeptInfo();
        d.setTypeid(1);
        List<DeptInfo>dList=this.deptManager.getList(d, null);
        //教研组长
        d.setTypeid(2);
        List<DeptInfo>tList=this.deptManager.getList(d, null);
        //年级组长  
        d.setTypeid(3);
        List<DeptInfo>gList=this.deptManager.getList(d, null);
        //自定义部门
        d.setTypeid(4);
        List<DeptInfo>dFreeList=this.deptManager.getList(d, null);
        //备课组长
        d.setTypeid(5);
        List<DeptInfo>pList=this.deptManager.getList(d, null);


        mp.put("stuClsList", stuClsList);
        mp.put("stuHistoryClsList", stuHistoryClsList);
        mp.put("teaClsList", teaClsList);
        mp.put("teaHistoryClsList", teaHistoryClsList);
        mp.put("gradeList", gradeList);
        mp.put("subjectUserList", subjectUserList);
        mp.put("subjectlist", subjectList);
        mp.put("clsyearList", clsyearList);
        mp.put("roleList", identityList);
        mp.put("duList", duList);
        mp.put("tchList", tchList);
        mp.put("gradeLeaderList", gradeLeaderList);
        mp.put("deptfuzerenList", deptfuzerenList);
        mp.put("prepareLeaderList", prepareLeaderList);
        mp.put("bzrList", clsBzrList);
        mp.put("dList", dList);
        mp.put("tList", tList);
        mp.put("gList", gList);
        mp.put("pList", pList);
        mp.put("dFreeList", dFreeList);


        return new ModelAndView("/user/admin/adminView", mp);
    }

    @RequestMapping(params = "m=studentinfoview", method = RequestMethod.GET)
    public ModelAndView studentInfoView(HttpServletRequest request,
                                        HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute(
                "CURRENT_USER");
        StudentInfo stu = new StudentInfo();
        String ref= request.getParameter("ref");
        if(ref!=null&&ref.length()>0)
            stu.getUserinfo().setRef(ref);
        else
            stu.getUserinfo().setRef(user.getRef());
        List<StudentInfo> stuList = studentManager.getList(stu, null);

        if (stuList != null && stuList.size() > 0)
            mp.put("student", stuList.get(0));

        TermInfo term = this.termManager.getMaxIdTerm();
        ClassUser cu = new ClassUser();
        cu.getUserinfo().setRef(user.getRef());
        cu.setYear(term.getYear());
        List<ClassUser> claList = classUserManager.getList(cu, null);
        mp.put("classes", claList);

        return new ModelAndView("/user/studentInfoView", mp);
    }

    @RequestMapping(params = "m=teacherinfoview", method = RequestMethod.GET)
    public ModelAndView teacherInfoView(HttpServletRequest request,
                                        HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = this.logined(request);
        String ref=request.getParameter("ref");
        TeacherInfo tch = new TeacherInfo();
        if(ref!=null&&ref.trim().length()>0)
            tch.getUserinfo().setRef(ref);
        else
            tch.getUserinfo().setRef(user.getRef());
        List<TeacherInfo> tchList = teacherManager.getList(tch, null);

        if (tchList != null && tchList.size() > 0)
            mp.put("teacher", tchList.get(0));

        SubjectUser s = new SubjectUser();
        s.setUserid(user.getRef());
        List<SubjectUser> sublist = this.subjectUserManager.getList(s, null);
        mp.put("sublist", sublist);

        String year = this.classYearManager.getCurrentYearList(null).get(0).getClassyearvalue();
        ClassUser cu = new ClassUser();
        cu.getClassinfo().setYear(year);
        cu.getUserinfo().setRef(user.getRef());
        List<ClassUser> claList = classUserManager.getList(cu, null);
        mp.put("class", claList);

        return new ModelAndView("/user/teacherInfoView", mp);
    }

    @RequestMapping(params = "m=parentinfoview", method = RequestMethod.GET)
    public ModelAndView parentInfoView(HttpServletRequest request,
                                       HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = (UserInfo) request.getSession().getAttribute(
                "CURRENT_USER");
        ParentInfo pat = new ParentInfo();
        pat.getUserinfo().setUserid(user.getUserid());
        List<ParentInfo> patList = parentManager.getList(pat, null);
        if (patList != null && patList.size() > 0) {
            mp.put("pat", patList.get(0));
            UserInfo stu = new UserInfo();
            stu.setUsername(patList.get(0).getCusername());
            stu = userManager.getUserInfo(stu);
            mp.put("stu", stu);

            ClassUser cu = new ClassUser();
            cu.getUserinfo().setRef(stu.getRef());
            List<ClassUser> claList = classUserManager.getList(cu, null);
            mp.put("claList", claList);
        }
        return new ModelAndView("/user/parentInfoView", mp);
    }

    @RequestMapping(params = "m=selfinfoview", method = RequestMethod.GET)
    public ModelAndView userInfoView(HttpServletRequest request,
                                     HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = this.logined(request);
        String ref=request.getParameter("ref");
        if(ref!=null&&ref.length()>0){
            UserInfo u=new UserInfo();
            u.setRef(ref);
            user=userManager.getUserInfo(u);
        }
        System.out.println(user.getUsername());

        mp.put("user", userManager.getUserInfo(user));

        RoleUser r =new RoleUser();
        r.getUserinfo().setRef(user.getRef());
        List<RoleUser> rl = new ArrayList<RoleUser>();
        List<RoleUser> jl = new ArrayList<RoleUser>();
        List<RoleUser> ruList = this.roleUserManager.getList(r, null);

        for(RoleUser ru : ruList){
            if(ru.getRoleinfo().getFlag()==0)
                rl.add(ru);
            else
                jl.add(ru);
        }
        mp.put("roList",rl);
        System.out.println(rl.size());
        mp.put("juList", jl!=null&&jl.size()==0?null:jl);
        System.out.println(jl.size());
        return new ModelAndView("/user/selfInfoView", mp);
    }

    @RequestMapping(params = "m=edituserinfo", method = RequestMethod.GET)
    public ModelAndView editUserInfo(HttpServletRequest request,
                                     HttpServletResponse response, ModelMap mp) throws Exception {
        UserInfo user = this.logined(request);
        mp.put("user", userManager.getUserInfo(user));
        return new ModelAndView("/user/editUserInfo", mp);
    }

    @RequestMapping(params = "m=updateuserinfo", method = RequestMethod.POST)
    public void updateUserInfo(HttpServletRequest request,
                               HttpServletResponse response, ModelMap mp) throws Exception {

        String roleType = request.getParameter("role");
        JsonEntity je = new JsonEntity();
        UserInfo u = this.logined(request);
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        if (u == null) {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if (userinfo != null) {
            userinfo.setUserid(u.getUserid());
            if (userManager.doUpdate(userinfo)) {
                userinfo = userManager.getUserInfo(userinfo);
                UserInfo cuInfo=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
                userinfo.setCjJRoleUsers(cuInfo.getCjJRoleUsers());
                userinfo.setClassUsers(cuInfo.getClassUsers());
                userinfo.setFunctionRightList(cuInfo.getFunctionRightList());
                userinfo.setPathRightList(cuInfo.getPathRightList());
                request.getSession().setAttribute("CURRENT_USER", userinfo);

                je.setType("success");
            } else {
                je.setType("error");
            }
        } else
            je.setType("success");

        /*******************
         * 注册之后用户不能修改自己的角色属性
         *
         * if (!roleType.equals("0")) { List<String> sqlListArray = new
         * ArrayList<String>(); List<List<Object>> objListArray = new
         * ArrayList<List<Object>>(); // 根据身份添加（教师、学生、家长） StringBuilder sql =
         * new StringBuilder(); List<Object> objList = null; if
         * (roleType.equals(UtilTool._ROLE_STU_ID.toString())) { StudentInfo stu
         * = new StudentInfo(); stu.setStuname(userinfo.getRealname());
         * stu.setStusex(userinfo.getSex());
         * stu.getUserinfo().setRef(userinfo.getRef());
         * stu.setRef(UUID.randomUUID().toString()); objList =
         * this.studentManager.getSaveSql(stu, sql);
         *
         * } else if (roleType.equals(UtilTool._ROLE_TEACHER_ID.toString())) {
         * TeacherInfo t = new TeacherInfo();
         * t.setTeachername(userinfo.getRealname());
         * t.setUserid(userinfo.getRef()); t.setTeachersex(userinfo.getSex());
         * objList = this.teacherManager.getSaveSql(t, sql);
         *
         * } else if (roleType.equals(UtilTool._ROLE_STU_PARENT_ID.toString()))
         * { ParentInfo p = new ParentInfo();
         * p.setParentname(userinfo.getRealname());
         * p.setUserid(userinfo.getRef());
         * p.setRef(UUID.randomUUID().toString());
         * p.setParentsex(userinfo.getSex()); objList =
         * this.parentManager.getSaveSql(p, sql); } if (sql.length() > 0 &&
         * objList != null) { sqlListArray.add(sql.toString());
         * objListArray.add(objList); } // 增加用户与角色关联
         *
         * RoleUser roleuser = new RoleUser();
         * roleuser.getUserinfo().setRef(userinfo.getRef());
         * roleuser.setRoleidstr(roleType);
         * roleuser.getRoleinfo().setRoleid(Integer.parseInt(roleType));
         * List<RoleUser> ruList = this.roleUserManager .getList(roleuser,
         * null); // 存在角色信息 则删除 if (ruList != null && ruList.size() > 0) { sql =
         * new StringBuilder(); objList =
         * this.roleUserManager.getDeleteSql(roleuser, sql); if (sql.length() >
         * 0 && objList != null) { sqlListArray.add(sql.toString());
         * objListArray.add(objList); } } sql = new StringBuilder(); objList =
         * this.roleUserManager.getSaveSql(roleuser, sql); if (sql.length() > 0
         * && objList != null) { sqlListArray.add(sql.toString());
         * objListArray.add(objList); }
         *
         * if (sqlListArray.size() > 0 && objListArray.size() > 0) { boolean
         * flag = this.userManager.doExcetueArrayProc( sqlListArray,
         * objListArray); if (flag) { je.setMsg(UtilTool.msgproperty
         * .getProperty("OPERATE_SUCCESS")); je.setType("success"); } else {
         * je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR")); } }
         * else { je.setMsg(UtilTool.msgproperty
         * .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL")); } }
         */
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=changepassword", method = RequestMethod.POST)
    public void changePassword(HttpServletRequest request,
                               HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo u = this.logined(request);
        if (u == null) {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        UserInfo userinfo = this.getParameter(request, UserInfo.class);
        if (userinfo != null) {
            userinfo.setUserid(u.getUserid());
            String newPassword = request.getParameter("new_password");
            if (userManager.doLogin(userinfo) != null) {
                userinfo.setPassword(newPassword);
                if (userManager.doUpdate(userinfo)) {
                    userinfo = userManager.getUserInfo(userinfo);
                    je.setType("success");
                } else {
                    je.setMsg("系统出错，无法修改！");
                    je.setType("error");
                }
            } else {
                je.setMsg(UtilTool.msgproperty
                        .getProperty("USER_LOGIN_PASSWORD_ERROR"));
                je.setType("error");
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("USER_LOGIN_PASSWORD_ERROR"));
            je.setType("error");
        }

        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=saveheadsrcfile", method = RequestMethod.POST)
    public void saveHeadSrcFile(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String msg = "";
        String returnVal = "";
        if (this.getUpload(request) == null) {// file==null||file.isEmpty()){
            msg = "未发现您要裁剪的图片，请重试!";
            returnVal = "{error:'" + msg + "'}";
            response.getWriter().print(returnVal);
            return;
        }
        List<String> filenameString = getFileArrayName(1);
        this.setFname(null);
        if (!this.fileupLoad(request)) {
            msg = "上传失败，请重试!";
            returnVal = "{error:'" + msg + "'}";
            response.getWriter().print(returnVal);
            return;
        }
        String filename = request.getRealPath("/") + "userUploadFile/"
                + filenameString.get(0);
        String returnname = filename;
        File f = new File(filename);
        if (!f.exists()) {
            msg = "未发现您要裁剪的图片，请重试!";
            returnVal = "{error:'" + msg + "'}";
            response.getWriter().print(returnVal);
            return;
        }
        Map<String, Long> propertyMap = UtilTool.getImageProperty(f);
        // 得到系统默认大小
        Long default_w = Long.parseLong("300");// UtilTool.utilprop.prop.get("修改头像设置默认宽度").toString());
        Long default_h = Long.parseLong("300");// UtilTool.utilprop.prop.getProperty("修改头像设置默认高度").toString());
        Long w = propertyMap.get("w");
        Long h = propertyMap.get("h");
        float cos;

        if (propertyMap.get("w") > default_w
                || propertyMap.get("h") > default_h) {
            // 该图片进行缩放
            // 得到默认比便
            if(w>h){
                if(w>default_w){
                    cos=default_w*1.0f/w*1.0f;
                    w=Math.round((w * default_w * 1.0 / w));
                    h=Math.round(h*1.0*cos);
                }
            }
            if(w<=h){
                if(h>default_h){
                    cos=default_h*1.0f/h*1.0f;
                    h=Math.round((h * default_h * 1.0 / h));
                    w=Math.round(w*1.0*cos);
                }
            }
            // 得到比例
           /* if(propertyMap.get("w") > default_w&&propertyMap.get("h") < default_h){
                w = Math.round((h * default_w * 1.0 / h));
            }
            if(propertyMap.get("h") > default_h && propertyMap.get("w") < default_w){
                h = Math.round((w * default_h * 1.0 / w));
            }
            if(propertyMap.get("w") > default_w
                    && propertyMap.get("h") > default_h){
                w = Math.round((h * default_w * 1.0 / h));
                h = Math.round((w * default_h * 1.0 / w));
            }*/
            // 开始缩放

        }
        returnname = new Date().getTime() + "zz"
                + returnname.substring(returnname.lastIndexOf("."));
        UtilTool.Redraw(f, request.getRealPath("/") + "userUploadFile/"
                + returnname, w.intValue(), h.intValue());
        if (f.exists())
            f.delete();
//		UserInfo user = new UserInfo();
//		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
//				"CURRENT_USER");
//		user.setUserid(userinfo.getUserid());
//		user.setUsername(userinfo.getUsername());
//		user.setHeadimage(userManager.getUserInfo(user).getHeadimage());
//		if (user.getHeadimage() != null
//				&& !user.getHeadimage().trim().equals("")) {
//			File temf = new File(request.getRealPath("/") + user.getHeadimage());
//			if (temf.exists())
//				temf.delete();
//		}
//		returnname = returnname.substring(returnname.lastIndexOf("/") + 1);
//		user.setHeadimage("userUploadFile//" + returnname);
        if (true) {
            msg = "userUploadFile/" + returnname + "|" + w + "|" + h + "|"
                    + propertyMap.get("s");
            returnVal = "{success:'" + msg + "'}";

            response.getWriter().print(returnVal);
        } else {
            msg = "图片上传出错!";
            returnVal = "{error:'" + msg + "'}";
            response.getWriter().print(returnVal);
        }
    }

    @RequestMapping(params = "m=docuthead", method = RequestMethod.POST)
    public void doCutHead(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        UserInfo user = new UserInfo();
        UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
                "CURRENT_USER");
        user.setUserid(userinfo.getUserid());
        user.setUsername(userinfo.getUsername());
        user.setHeadimage(userManager.getUserInfo(user).getHeadimage());
        String src = request.getParameter("src");
        String x1 = request.getParameter("x1");
        String y1 = request.getParameter("y1");
        String x2 = request.getParameter("x2");
        String y2 = request.getParameter("y2");
        int w = 300;
        int h = 300;

        JsonEntity je = new JsonEntity();
        if (src == null || src.trim().length() < 1 ) {
            je.setMsg("异常错误，原因：参数不齐!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String src1 = request.getRealPath("/") + src;
        File f = new File(src1);
        if (!f.exists()) {
            je.setMsg("异常错误，该图片已经不存在!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String lastname = src.substring(src.lastIndexOf("."));
        String firstname = src.substring(0, src.lastIndexOf("."));
        String newname = firstname + "-header" + lastname;
        String newnamerealpath = request.getRealPath("/") + newname;
        if(x1 == null
                || x1.trim().length() < 1 || !UtilTool.isDouble(x1)
                || y1 == null || y1.trim().length() < 1
                || !UtilTool.isDouble(y1) || x2 == null
                || x2.trim().length() < 1 || !UtilTool.isDouble(x2)
                || y2 == null || y2.trim().length() < 1
                || !UtilTool.isDouble(y2)){
            try {
                int bytesum = 0;
                int byteread = 0;
                File oldfile = new File(src1);
                if (oldfile.exists()) { 				//文件存在时
                    InputStream inStream = new FileInputStream(src1); 				//读入原文件
                    FileOutputStream fs = new FileOutputStream(newnamerealpath);
                    byte[] buffer = new byte[1444];
                    int length;
                    while ( (byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread; //字节数 文件大小
                        fs.write(buffer, 0, byteread);
                    }
                    inStream.close();
                }
            }
            catch (Exception e) {
                System.out.println("复制单个文件操作出错");
                e.printStackTrace();
            }
        }else{
            w = Integer.parseInt(x2) - Integer.parseInt(x1);
            h = Integer.parseInt(y2) - Integer.parseInt(y1);
            if (w < 0)
                w = -w;
            if (h < 0)
                h = -h;
            UtilTool.cutImg(src1, newnamerealpath, w, h, Integer.parseInt(x1),
                    Integer.parseInt(y1));
        }

        if (user.getHeadimage() != null && user.getHeadimage().trim() != "") {
            File temf = new File(request.getRealPath("/") + user.getHeadimage());
            if (temf.exists())
                temf.delete();
        }
        user.setHeadimage(newname);
        if (userManager.doUpdate(user)) {
            Integer tempid=user.getUserid();
            user = new UserInfo();
            user.setUserid(tempid);
            user = this.userManager.getUserInfo(user);
            UserInfo cuInfo=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
            userinfo.setCjJRoleUsers(cuInfo.getCjJRoleUsers());
            userinfo.setClassUsers(cuInfo.getClassUsers());
            userinfo.setFunctionRightList(cuInfo.getFunctionRightList());
            userinfo.setPathRightList(cuInfo.getPathRightList());
            userinfo.setHeadimage(newname);
            request.getSession().setAttribute("CURRENT_USER", userinfo);
            je.getObjList().add(newname);
            je.setType("success");


            //查询当前用户的信息
            UserInfo userInfo=new UserInfo();
            userInfo.setUserid(userinfo.getUserid());
            userInfo.setDcschoolid(this.logined(request).getDcschoolid());
            List<UserInfo> uList=this.userManager.getList(userInfo,null);
            if(uList!=null&&uList.size()>0&&uList.get(0)!=null&&uList.get(0).getHeadimage()!=null&&uList.get(0).getHeadimage().trim().length()>0){
                int returnType=HeadImgToEtt(uList.get(0));
                if(returnType==1)
                    System.out.println("同步用户头像成功!");
                else
                    System.out.println("同步用户头像失败!");
            }
        } else {
            je.getObjList().add(newname);
            je.setType("error");
        }
        response.getWriter().print(je.toJSON());
    }

    public boolean fileupLoad(HttpServletRequest request) throws Exception {
        try {
            this.setFname(this.getFileNameList().get(0));
            String filename = this.getFname();
            File imageFile = new File(request.getRealPath("/")
                    + "userUploadFile/" + filename);
            if (myFile == null)
                myFile = this.getUpload(request)[0];
            copy(myFile.getInputStream(), imageFile);
            myFile = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 批量设置用户状态
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=dosetUserState", method = RequestMethod.POST)
    public void doSetUserState(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo user = this.getParameter(request, UserInfo.class);
        String useridstr = request.getParameter("useridstr");
        if (user.getStateid() == null
                || !UtilTool.isNumber(user.getStateid().toString())
                || useridstr == null || useridstr.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;
        String[] useridArray = useridstr.split(",");
        if (useridArray.length > 0) {
            for (String userid : useridArray) {
                if (userid != null) {
                    UserInfo u = new UserInfo();
                    u.setUserid(Integer.parseInt(userid));
                    u.setStateid(user.getStateid());
                    sql = new StringBuilder();
                    objList = this.userManager.getUpdateSql(u, sql);
                    if (sql.length() > 0 && objList != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }
        }

        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean flag = this.userManager.doExcetueArrayProc(sqlListArray,
                    objListArray);
            if (flag) {
                je.setType("success");
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 教务添加用户
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=doadd", method = RequestMethod.POST)
    public void doAddUser(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        // 公用
        String identityname=request.getParameter("identityname");
        boolean isstu = false;
        String issstu = request.getParameter("isstu");
        String roles = request.getParameter("roles");
        String clsstr = request.getParameter("clsstr");
        if (issstu != null && issstu.equals("true"))
            isstu = true;
        // 学生
        String stusex = request.getParameter("sex");
        String address = request.getParameter("address");
        String linkman = request.getParameter("linkman");
        String linkmanphone = request.getParameter("linkmanphone");

        // 教师
        String teachersex = request.getParameter("sex");
        String subject = request.getParameter("subject");
        String teacherphone = request.getParameter("teacherphone");
        String teacherpost = request.getParameter("teacherpost");
        String teacheraddress = request.getParameter("teacheraddress");
        String subjectmajor = request.getParameter("subjectmajor");
        String gradeid=request.getParameter("gradeid");
        String deptid=request.getParameter("deptid");
        String teachleaderid=request.getParameter("teachleaderid");
        String prepareid=request.getParameter("prepareid");
        String deptfzrid=request.getParameter("deptfzrid");
        // 班主任
        String clsbzrstr = request.getParameter("clsbzrstr");

        String strDcSchoolID= request.getParameter("dcschoolid");
        int dcSchoolID=0;
        if(strDcSchoolID!=null&&strDcSchoolID.length()>0)
        {
            dcSchoolID=Integer.parseInt( strDcSchoolID);
        }

        UserInfo user = this.getParameter(request, UserInfo.class);
        if (user.getUsername() == null
                || user.getUsername().trim().length() < 1
                || user.getPassword() == null
                || user.getPassword().trim().length() < 1
                || user.getStateid() == null
                ||identityname==null||identityname.trim().length()<1
                ) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        // 添加用户信息
        UserInfo u = new UserInfo();
        String userNextRef = UUID.randomUUID().toString();
        u.setRef(userNextRef);
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setStateid(user.getStateid());
        if (user.getPassquestion() != null)
            u.setPassquestion(user.getPassquestion());
        if (user.getQuestionanswer() != null)
            u.setQuestionanswer(user.getQuestionanswer());
        if (user.getMailaddress() != null)
            u.setMailaddress(user.getMailaddress());
        if (user.getBirthdate() != null)
            u.setBirthdate(user.getBirthdate());


        u.setDcschoolid(dcSchoolID);
        sql = new StringBuilder();
        objList = this.userManager.getSaveSql(u, sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        //添加用户与身份关联信息
        String identityNextRef = UUID.randomUUID().toString();
        UserIdentityInfo ui = new UserIdentityInfo();
        ui.setRef(identityNextRef);
        ui.getUserinfo().setRef(userNextRef);
        ui.setIdentityname(identityname);

        sql = new StringBuilder();
        objList = this.userIdentityManager.getSaveSql(ui, sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        // 学生
        if (isstu) {
            if (user.getStuname() == null
                    || user.getStuname().trim().length() < 1
                    || user.getStuno() == null
                    || user.getStuno().trim().length() < 1 || stusex == null
                    || clsstr==null
                    ) {
                je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(je.toJSON());
                return;
            }
            // 添加学生
            StudentInfo s = new StudentInfo();
            s.getUserinfo().setRef(userNextRef);
            s.setStuname(user.getStuname().trim());
            s.setStusex(stusex);
            s.setStuno(user.getStuno());
            if (linkman != null && linkman.trim().length() > 0)
                s.setLinkman(linkman);
            if (linkmanphone != null && linkmanphone.trim().length() > 0)
                s.setLinkmanphone(linkmanphone);
            if (address != null && address.trim().length() > 0)
                s.setStuaddress(address);
            sql = new StringBuilder();
            objList = this.studentManager.getSaveSql(s, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加默认学生角色
            String ruNextRef = UUID.randomUUID().toString();
            RoleUser ru = new RoleUser();
            ru.setRef(ruNextRef);
            ru.getUserinfo().setRef(userNextRef);
            ru.getRoleinfo().setRoleid(UtilTool._ROLE_STU_ID);

            sql = new StringBuilder();
            objList = this.roleUserManager.getSaveSql(ru, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加学生角色默认权限
            RoleColumnRightInfo rc=new RoleColumnRightInfo();
            rc.setRoleid(UtilTool._ROLE_STU_ID);
            List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);
            if(rcList!=null&&rcList.size()>0){
                for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                    UserColumnRightInfo ucr=new UserColumnRightInfo();
                    ucr.setColumnid(roleColumnRightInfo.getColumnid());
                    ucr.setUserid(userNextRef);
                    ucr.setRef(this.userColumnRightManager.getNextId());
                    ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                    sql=new StringBuilder();
                    objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                    if(objList!=null&&sql!=null){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }

        } else {
            if (user.getRealname() == null
                    || user.getRealname().trim().length() < 1
                    || teachersex == null
                    ) {
                je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(je.toJSON());
                return;
            }
            TeacherInfo t = new TeacherInfo();
            t.setUserid(userNextRef);
            t.setTeachername(user.getRealname());
            t.setTeachersex(teachersex);
            if (teacherphone != null)
                t.setTeacherphone(teacherphone);
            if (teacherpost != null)
                t.setTeacherpost(teacherpost);
            if (teacheraddress != null)
                t.setTeacheraddress(teacheraddress);
            if (user.getBirthdate() != null)
                t.setTeacherbirth(user.getBirthdate());
            if (user.getMailaddress() != null)
                t.setTeacherpost(user.getMailaddress());

            sql = new StringBuilder();
            objList = this.teacherManager.getSaveSql(t, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加默认教师角色
            String ruNextRef = UUID.randomUUID().toString();
            RoleUser ru = new RoleUser();
            ru.setRef(ruNextRef);
            ru.getUserinfo().setRef(userNextRef);
            ru.getRoleinfo().setRoleid(UtilTool._ROLE_TEACHER_ID);

            sql = new StringBuilder();
            objList = this.roleUserManager.getSaveSql(ru, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加教师角色默认权限
            RoleColumnRightInfo rc=new RoleColumnRightInfo();
            rc.setRoleid(UtilTool._ROLE_TEACHER_ID);
            List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);

            if(rcList!=null&&rcList.size()>0)
            {

            }
            if(rcList!=null&&rcList.size()>0){
                for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                    UserColumnRightInfo ucr=new UserColumnRightInfo();
                    ucr.setColumnid(roleColumnRightInfo.getColumnid());
                    ucr.setUserid(userNextRef);
                    ucr.setRef(this.userColumnRightManager.getNextId());
                    ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                    sql=new StringBuilder();
                    objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                    if(objList!=null&&sql!=null){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }

            // 教师科目
            if (subject != null && subject.trim().length() > 0) {
                String[] subjectArray = subject.split(",");
                if (subjectArray.length > 0) {
                    for (int i = 0; i < subjectArray.length; i++) {
                        SubjectUser su = new SubjectUser();
                        su.getSubjectinfo().setSubjectid(
                                Integer.parseInt(subjectArray[i]));
                        su.getUserinfo().setRef(userNextRef);
                        su.setIsmajor(0);

                        sql = new StringBuilder();
                        objList = this.subjectUserManager.getSaveSql(su, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }

            // 主授科目
            if (subjectmajor != null && subjectmajor.trim().length() > 0) {
                String[] majorArray = subjectmajor.split(",");
                if (majorArray.length > 0) {
                    for (int i = 0; i < majorArray.length; i++) {
                        SubjectUser sdelete = new SubjectUser();
                        sdelete.getSubjectinfo().setSubjectid(
                                Integer.parseInt(majorArray[i]));
                        sdelete.getUserinfo().setRef(userNextRef);
                        sql = new StringBuilder();
                        objList = this.subjectUserManager.getDeleteSql(sdelete,
                                sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        SubjectUser su = new SubjectUser();
                        su.getSubjectinfo().setSubjectid(
                                Integer.parseInt(majorArray[i]));
                        su.getUserinfo().setRef(userNextRef);
                        su.setIsmajor(1);

                        sql = new StringBuilder();
                        objList = this.subjectUserManager.getSaveSql(su, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }
        }

        // 添加班级关系
        Set<String>gradeValueSet=new HashSet<String>();
        if(clsstr!=null&&clsstr.length()>0){
            String[] classArray = clsstr.split(",");
            if (classArray.length > 0) {
                for (String classid : classArray) {
                    String cuNextRef = UUID.randomUUID().toString();
                    ClassUser cu = new ClassUser();
                    cu.getUserinfo().setRef(userNextRef);
                    cu.setRef(cuNextRef);
                    if (isstu) {
                        //验证年级
                        ClassInfo classInfo=new ClassInfo();
                        classInfo.setClassid(Integer.parseInt(classid));
                        List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
                        if(clsList!=null&&clsList.size()>0)
                            gradeValueSet.add(clsList.get(0).getClassgrade());

                        cu.setRelationtype("学生");
                        cu.getClassinfo().setClassid(Integer.parseInt(classid));
                    } else {
                        cu.setRelationtype("任课老师");
                        String[] csidArray = classid.split("\\|");
                        cu.getClassinfo()
                                .setClassid(Integer.parseInt(csidArray[0]));
                        cu.setSubjectid(Integer.parseInt(csidArray[1]));
                    }

                    sql = new StringBuilder();
                    objList = this.classUserManager.getSaveSql(cu, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }
        }

        if(isstu&&gradeValueSet.size()>1){
            je.setMsg("提示：同一学年学生不可存在多个年级中，请修改!");
            response.getWriter().print(je.toJSON());
            return;
        }


        // 添加角色关系
        if(roles!=null&&roles.length()>0){
            String[] roleArray = roles.split(",");
            if (roleArray.length > 0) {
                for (String roleid : roleArray) {
                    String ruNextRef = UUID.randomUUID().toString();
                    RoleUser ru = new RoleUser();
                    ru.setRef(ruNextRef);
                    ru.getUserinfo().setRef(userNextRef);
                    ru.getRoleinfo().setRoleid(Integer.parseInt(roleid));

                    sql = new StringBuilder();
                    objList = this.roleUserManager.getSaveSql(ru, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }

                    //添加用户角色权限
                    RoleColumnRightInfo rc=new RoleColumnRightInfo();
                    rc.setRoleid(Integer.parseInt(roleid));
                    List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);
                    if(rcList!=null&&rcList.size()>0){
                        for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                            UserColumnRightInfo ucr=new UserColumnRightInfo();
                            ucr.setColumnid(roleColumnRightInfo.getColumnid());
                            ucr.setUserid(userNextRef);
                            ucr.setRef(this.userColumnRightManager.getNextId());
                            ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                            sql=new StringBuilder();
                            objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                            if(objList!=null&&sql!=null){
                                sqlListArray.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }

                    /************************/
                    Integer rid=Integer.parseInt(roleid);
                    /**
                     * 年级组长
                     */
                    if((rid==UtilTool._ROLE_GRADE_LEADER_ID||rid==UtilTool._ROLE_GRADE_FU_LEADER_ID)
                            &&gradeid!=null&&gradeid.trim().length()>0){
                        DeptUser du = new DeptUser();
                        du.setRef(UUID.randomUUID().toString());
                        du.getUserinfo().setRef(userNextRef);
                        du.setDeptid(Integer.parseInt(gradeid));
                        du.setRoleid(rid);

                        sql = new StringBuilder();
                        objList = this.deptUserManager.getSaveSql(du, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                    /**
                     * 部门主任
                     */

                    if((rid==UtilTool._ROLE_DEPT_LEADER_ID||rid==UtilTool._ROLE_DEPT_FU_LEADER_ID)
                            &&deptid!=null&&deptid.trim().length()>0){
                        DeptUser du = new DeptUser();
                        du.setRef(UUID.randomUUID().toString());
                        du.getUserinfo().setRef(userNextRef);
                        du.setDeptid(Integer.parseInt(deptid));
                        du.setRoleid(rid);

                        sql = new StringBuilder();
                        objList = this.deptUserManager.getSaveSql(du, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }

                    /**
                     * 教研组长
                     */

                    if((rid==UtilTool._ROLE_TEACH_LEADER_ID||rid==UtilTool._ROLE_TEACH_FU_LEADER_ID)
                            &&teachleaderid!=null&&teachleaderid.trim().length()>0){
                        DeptUser du = new DeptUser();
                        du.setRef(UUID.randomUUID().toString());
                        du.getUserinfo().setRef(userNextRef);
                        du.setDeptid(Integer.parseInt(teachleaderid));
                        du.setRoleid(rid);

                        sql = new StringBuilder();
                        objList = this.deptUserManager.getSaveSql(du, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }

                    /**
                     * 备课组长
                     */
                    if(rid==UtilTool._ROLE_PREPARE_LEADER_ID
                            &&prepareid!=null&&prepareid.trim().length()>0){
                        DeptUser du = new DeptUser();
                        du.setRef(UUID.randomUUID().toString());
                        du.getUserinfo().setRef(userNextRef);
                        du.setDeptid(Integer.parseInt(prepareid));
                        du.setRoleid(rid);

                        sql = new StringBuilder();
                        objList = this.deptUserManager.getSaveSql(du, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }

                    /**
                     * 部门负责人
                     */
                    if(rid==UtilTool._ROLE_FREE_DEPT_LEADER_ID
                            &&deptfzrid!=null&&deptfzrid.trim().length()>0){
                        DeptUser du = new DeptUser();
                        du.setRef(UUID.randomUUID().toString());
                        du.getUserinfo().setRef(userNextRef);
                        du.setDeptid(Integer.parseInt(deptfzrid));
                        du.setRoleid(rid);

                        sql = new StringBuilder();
                        objList = this.deptUserManager.getSaveSql(du, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }
        }


        if (!isstu && clsbzrstr != null && clsbzrstr.trim().length() > 0) {
            String[] clsbzrArray = clsbzrstr.split(",");
            if (clsbzrArray.length > 0) {
                for (String clsid : clsbzrArray) {
                    ClassUser selBzr=new ClassUser();
                    selBzr.setRelationtype("班主任");
                    selBzr.setClassid(Integer.parseInt(clsid));
                    List<ClassUser>clsBzrList=this.classUserManager.getList(selBzr,null);
                    if(clsBzrList!=null&&clsBzrList.size()>0){
                        for(ClassUser classUser:clsBzrList){
                            RoleUser roleUser=new RoleUser();
                            roleUser.setRoleid(UtilTool._ROLE_CLASSADVISE_ID);
                            roleUser.setUserid(classUser.getUserid());
                            sql=new StringBuilder();
                            objList=this.roleUserManager.getDeleteSql(roleUser,sql);
                            if (objList != null && sql != null) {
                                sqlListArray.add(sql.toString());
                                objListArray.add(objList);
                            }
                        }
                    }

                    ClassUser deleteBzr=new ClassUser();
                    deleteBzr.setRelationtype("班主任");
                    deleteBzr.setClassid(Integer.parseInt(clsid));
                    sql = new StringBuilder();
                    objList = this.classUserManager.getDeleteSql(deleteBzr,sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                    //添加班主任
                    String cuNextRef = UUID.randomUUID().toString();
                    ClassUser cu = new ClassUser();
                    cu.getUserinfo().setRef(userNextRef);
                    cu.getClassinfo().setClassid(Integer.parseInt(clsid));
                    cu.setRef(cuNextRef);
                    cu.setRelationtype("班主任");
                    sql = new StringBuilder();
                    objList = this.classUserManager.getSaveSql(cu, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }
        }

        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.userManager.doExcetueArrayProc(sqlListArray,
                    objListArray);
            if (bo) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                je.getObjList().add(userNextRef);
                if(!addToEttUser(userNextRef)){
                    System.out.println("同步网校失败！原因：未知");
                }
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }





    /**
     * 教务修改用户
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=domodify", method = RequestMethod.POST)
    public void doUpdateUser(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String userNextRef = request.getParameter("ref");
        if (userNextRef == null || userNextRef.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        UserInfo us = new UserInfo();
        us.setRef(userNextRef);
        List<UserInfo> uList = this.userManager.getList(us, null);
        if (uList == null || uList.size() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        // 公用
        boolean isstu = false;
        String issstu = request.getParameter("isstu");
        String roles = request.getParameter("roles");
        String jobs = request.getParameter("jobs");
        String clsstr = request.getParameter("clsstr");
        if (issstu != null && issstu.equals("true"))
            isstu = true;
        // 学生
        String stusex = request.getParameter("stusex");
        String address = request.getParameter("address");
        String linkman = request.getParameter("linkman");
        String linkmanphone = request.getParameter("linkmanphone");

        // 教师
        String teachersex = request.getParameter("teachersex");
        String subject = request.getParameter("subject");
        String teachercardid = request.getParameter("teachercardid");
        String teacherphone = request.getParameter("teacherphone");
        String teacherpost = request.getParameter("teacherpost");
        String teacheraddress = request.getParameter("teacheraddress");
        String subjectmajor = request.getParameter("subjectmajor");
        // 班主任
        String clsbzrstr = request.getParameter("clsbzrstr");

        UserInfo user = this.getParameter(request, UserInfo.class);
        if (user.getUsername() == null
                || user.getUsername().trim().length() < 1
                || user.getPassword() == null
                || user.getPassword().trim().length() < 1
                // ||user.getPassquestion()==null||user.getPassquestion().trim().length()<1
                // ||user.getQuestionanswer()==null||user.getQuestionanswer().trim().length()<1
                || user.getStateid() == null
                // ||user.getMailaddress()==null
                // ||user.getBirthdate()==null
                //|| clsstr == null || clsstr.trim().length() < 1
                || roles == null || roles.trim().length() < 1) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;

        // 修改用户信息
        UserInfo u = new UserInfo();
        u.setRef(userNextRef);
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setStateid(user.getStateid());
        if (user.getPassquestion() != null)
            u.setPassquestion(user.getPassquestion());
        if (user.getQuestionanswer() != null)
            u.setQuestionanswer(user.getQuestionanswer());
        if (user.getMailaddress() != null)
            u.setMailaddress(user.getMailaddress());
        if (user.getBirthdate() != null)
            u.setBirthdate(user.getBirthdate());

        sql = new StringBuilder();
        objList = this.userManager.getUpdateSql(u, sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        // 学生
        if (isstu) {
            if (user.getStuname() == null
                    || user.getStuname().trim().length() < 1
                    || user.getStuno() == null
                    || user.getStuno().trim().length() < 1 || stusex == null
                    || clsstr == null || clsstr.trim().length() < 1
                // ||linkman==null||linkman.trim().length()<1
                // ||linkmanphone==null||linkmanphone.trim().length()<1
                    ) {
                je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(je.toJSON());
                return;
            }
            // 修改学生
            StudentInfo s = new StudentInfo();
            s.getUserinfo().setRef(userNextRef);
            // 验证数据
            List<StudentInfo> stuList = this.studentManager.getList(s, null);
            if (stuList == null || stuList.size() < 1) {
                je.setMsg(UtilTool.msgproperty.getProperty("NOT_EXISTS"));
                response.getWriter().print(je.toJSON());
                return;
            }
            s.setStuname(user.getStuname().trim());
            s.setStusex(stusex);
            s.setStuno(user.getStuno());
            if (linkman != null)
                s.setLinkman(linkman);
            if (linkmanphone != null)
                s.setLinkmanphone(linkmanphone);
            if (address != null && address.trim().length() > 0)
                s.setStuaddress(address);
            sql = new StringBuilder();
            objList = this.studentManager.getUpdateSql(s, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        } else {
            if (user.getRealname() == null
                    || user.getRealname().trim().length() < 1
                    || teachersex == null
                // ||teachercardid==null
                // ||teacherphone==null
                // ||teacherpost==null
                // ||subject==null
                    ) {
                je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(je.toJSON());
                return;
            }
            // 教师
            TeacherInfo t = new TeacherInfo();
            t.setUserid(userNextRef);
            List<TeacherInfo> teaList = this.teacherManager.getList(t, null);
            if (teaList == null || teaList.size() < 1) {
                je.setMsg(UtilTool.msgproperty.getProperty("NOT_EXISTS"));
                response.getWriter().print(je.toJSON());
                return;
            }
            t.setTeachername(user.getRealname());
            t.setTeachersex(teachersex);
            if (teachercardid != null)
                t.setTeachercardid(teachercardid);
            if (teacherphone != null)
                t.setTeacherphone(teacherphone);
            if (teacherpost != null)
                t.setTeacherpost(teacherpost);
            if (teacheraddress != null)
                t.setTeacheraddress(teacheraddress);
            if (user.getBirthdate() != null)
                t.setTeacherbirth(user.getBirthdate());

            sql = new StringBuilder();
            objList = this.teacherManager.getUpdateSql(t, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            // 教师科目
            if (subject != null && subject.trim().length() > 0) {
                String[] subjectArray = subject.split(",");

                if (subjectArray.length > 0) {
                    SubjectUser sudelete = new SubjectUser();
                    sudelete.getUserinfo().setRef(userNextRef);
                    sql = new StringBuilder();
                    objList = this.subjectUserManager.getDeleteSql(sudelete,
                            sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }

                    for (int i = 0; i < subjectArray.length; i++) {
                        SubjectUser su = new SubjectUser();
                        su.getSubjectinfo().setSubjectid(
                                Integer.parseInt(subjectArray[i]));
                        su.getUserinfo().setRef(userNextRef);
                        su.setIsmajor(0);
                        sql = new StringBuilder();
                        objList = this.subjectUserManager.getSaveSql(su, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }

            // 主授科目
            if (subjectmajor != null && subjectmajor.trim().length() > 0) {
                String[] majorArray = subjectmajor.split(",");
                if (majorArray.length > 0) {
                    for (int i = 0; i < majorArray.length; i++) {
                        SubjectUser sudelete = new SubjectUser();
                        sudelete.getUserinfo().setRef(userNextRef);
                        sudelete.setSubjectid(Integer.parseInt(majorArray[i]));
                        sql = new StringBuilder();
                        objList = this.subjectUserManager.getDeleteSql(
                                sudelete, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        SubjectUser su = new SubjectUser();
                        su.getSubjectinfo().setSubjectid(
                                Integer.parseInt(majorArray[i]));
                        su.getUserinfo().setRef(userNextRef);
                        su.setIsmajor(1);
                        sql = new StringBuilder();
                        objList = this.subjectUserManager.getSaveSql(su, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }

        }

        // 添加班级关系
        if(clsstr!=null&&clsstr.length()>0){
            String[] classArray = clsstr.split(",");
            if (classArray.length > 0) {
                ClassUser cudelete = new ClassUser();
                cudelete.getUserinfo().setRef(userNextRef);
                if (isstu)
                    cudelete.setRelationtype("学生");
                else
                    cudelete.setRelationtype("任课老师");
                sql = new StringBuilder();
                objList = this.classUserManager.getDeleteSql(cudelete, sql);
                if (objList != null && sql != null) {
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                for (String classid : classArray) {
                    String cuNextRef = UUID.randomUUID().toString();
                    ClassUser cu = new ClassUser();
                    cu.getUserinfo().setRef(userNextRef);
                    cu.setRef(cuNextRef);
                    if (isstu) {
                        cu.setRelationtype("学生");
                        cu.getClassinfo().setClassid(Integer.parseInt(classid));
                    } else {
                        cu.setRelationtype("任课老师");
                        String[] csidArray = classid.split("\\|");
                        cu.getClassinfo()
                                .setClassid(Integer.parseInt(csidArray[0]));
                        cu.setSubjectid(Integer.parseInt(csidArray[1]));
                    }


                    sql = new StringBuilder();
                    objList = this.classUserManager.getSaveSql(cu, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }
        }


        // 添加角色关系
        String[] roleArray = roles.split(",");
        if (roleArray.length > 0) {
            RoleUser rudelete = new RoleUser();
            rudelete.getUserinfo().setRef(userNextRef);
            sql = new StringBuilder();
            objList = this.roleUserManager.getDeleteSql(rudelete, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            for (String roleid : roleArray) {
                String ruNextRef = UUID.randomUUID().toString();
                RoleUser ru = new RoleUser();
                ru.setRef(ruNextRef);
                ru.getUserinfo().setRef(userNextRef);
                ru.getRoleinfo().setRoleid(Integer.parseInt(roleid));

                sql = new StringBuilder();
                objList = this.roleUserManager.getSaveSql(ru, sql);
                if (objList != null && sql != null) {
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }

        // 添加职务关系
        if (jobs != null && jobs.trim().length() > 0) {
            String[] jobArray = jobs.split(",");
            if (jobArray.length > 0) {
                JobUser judelete = new JobUser();
                judelete.getUserinfo().setRef(userNextRef);
                sql = new StringBuilder();
                objList = this.jobUserManager.getDeleteSql(judelete, sql);
                if (objList != null && sql != null) {
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                for (String jobid : jobArray) {
                    JobUser ju = new JobUser();
                    ju.setJobid(Integer.parseInt(jobid));
                    ju.getUserinfo().setRef(userNextRef);
                    sql = new StringBuilder();
                    objList = this.jobUserManager.getSaveSql(ju, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }
        }

        if (!isstu && clsbzrstr != null && clsbzrstr.trim().length() > 0) {
            String[] clsbzrArray = clsbzrstr.split(",");
            if (clsbzrArray.length > 0) {
                ClassUser cudelete = new ClassUser();
                cudelete.getUserinfo().setRef(userNextRef);
                cudelete.setRelationtype("班主任");
                sql = new StringBuilder();
                objList = this.classUserManager.getDeleteSql(cudelete, sql);
                if (objList != null && sql != null) {
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                for (String clsid : clsbzrArray) {
                    String cuNextRef = UUID.randomUUID().toString();
                    ClassUser cu = new ClassUser();
                    cu.getUserinfo().setRef(userNextRef);
                    cu.getClassinfo().setClassid(Integer.parseInt(clsid));
                    cu.setRef(cuNextRef);
                    cu.setRelationtype("班主任");

                    sql = new StringBuilder();
                    objList = this.classUserManager.getSaveSql(cu, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }
        }

        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.userManager.doExcetueArrayProc(sqlListArray,
                    objListArray);
            if (bo) {
                //如果是学生,删除错误的学生小组关联数据

                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }
    /**
     * 进入首页
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=toIndex", method = RequestMethod.GET)
    public ModelAndView toIndex(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception {
        // System.out.println("123456");
        //得到当前学期的授课班级
        //得到当前学期
        TermInfo term=this.termManager.getAutoTerm();
        JsonEntity jeEntity=new JsonEntity();
        if(term==null){
            if(!this.termManager.InitTerm()){
                jeEntity.setMsg("异常错误，网站设置错误，请联系管理人员设置学期!");
                response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return null;
            }
            term=this.termManager.getAutoTerm();
        }
        //得到教授班级
        String year=term.getYear();
        ClassUser cu=new ClassUser();
        cu.setYear(year);
        if(this.validateRole(request, UtilTool._ROLE_TEACHER_ID))
            cu.setRelationtype("任课老师");
        else if(this.validateRole(request, UtilTool._ROLE_STU_ID))
            cu.setRelationtype("学生");
        else if(this.validateRole(request, UtilTool._ROLE_CLASSADVISE_ID))
            cu.setRelationtype("班主任");
        cu.setUserid(this.logined(request).getRef());
        cu.getClassinfo().setIsflag(1);
        List<ClassUser> cuList=this.classUserManager.getList(cu, null);
        if(cuList!=null&&cuList.size()>0)
            mp.put("teachClass", cuList);
        //得到通知公告,网上公示
        NoticeInfo notic=new NoticeInfo();
        notic.setCuserid(this.logined(request).getRef());
        notic.setNoticetype(UtilTool._NOTICE_TYPE[0]);  //通知公告
        notic.setDcschoolid(this.logined(request).getDcschoolid());
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(5);
        presult.setOrderBy("n.IS_TOP ASC,C_TIME DESC");
        List<NoticeInfo> noticeList=this.noticeManager.getUserList(notic, presult);
        mp.put("notices", noticeList);

        notic.setNoticetype(UtilTool._NOTICE_TYPE[1]); //网上公示
        List<NoticeInfo> wsgsList=this.noticeManager.getUserList(notic, presult);
        mp.put("internetNotices", wsgsList);

//        presult=new PageResult();
//        presult.setPageNo(1);
//        presult.setPageSize(5);
//        presult.setOrderBy("n.IS_TOP ASC,C_TIME DESC");
//        notic.setNoticetype(UtilTool._NOTICE_TYPE[2]); //校内参考
//        List<NoticeInfo> xnckNotice=this.noticeManager.getUserList(notic, presult);
//        mp.put("xnckNotices", xnckNotice);
        //得到最近的关于我的活动信息
        ActivityInfo activity=new ActivityInfo();
        activity.setUserid(this.logined(request).getRef());
        presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(5);
        presult.setOrderBy("ac.C_TIME DESC");//,ac.M_TIME DESC
        List<ActivityInfo> activityList=this.activityManager.getList(activity, presult);
        mp.put("activityList", activityList);
        //得到部门类别
        mp.put("deptType", UtilTool._GetDeptType());
        //得到消息

        //得到全部的数据
        List<MyInfoUserInfo> mu=this.myInfoUserManager.getSYMsgData(this.logined(request).getRef());
        //公告消息
        List<MyInfoUserInfo> msgNoticeList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> shenqingMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> shenheMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> baomingMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> luquMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> fatieMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> renmingMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> renwuMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> chengjiMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> huodongMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> userUpdateMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> tiaobanMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> xftxMSGList=new ArrayList<MyInfoUserInfo>();
        List<MyInfoUserInfo> tzMSGList=new ArrayList<MyInfoUserInfo>();
        if(mu!=null&&mu.size()>0){
            for (MyInfoUserInfo muTmp:mu){
                if(muTmp!=null&&muTmp.getMsgid()!=null){
                    Integer msgid= muTmp.getMsgid();
                    switch(msgid){
                        case 1: //1:公告(ggtd)
                            msgNoticeList.add(muTmp);
                            break;
                        case 2: //2:申请(sqtd)
                            shenqingMSGList.add(muTmp);
                            break;
                        case 3://   3:审核(shtd)
                            shenheMSGList.add(muTmp);
                            break;
                        case 4://4:报名(bmtd)
                            baomingMSGList.add(muTmp);
                            break;
                        case 5://5:录取(lqtd)
                            luquMSGList.add(muTmp);
                            break;
                        case 6://6:发帖(fttd)
                            fatieMSGList.add(muTmp);
                            break;
                        case 7://7:任命(rmtd)
                            renmingMSGList.add(muTmp);
                            break;
                        case 8://8:任务(rwtd)
                            renwuMSGList.add(muTmp);
                            break;
                        case 9://9:成绩(cjtd)
                            chengjiMSGList.add(muTmp);
                            break;
                        case 10://10:活动(hdtd)
                            huodongMSGList.add(muTmp);
                            break;
                        case 11://11:用户修改(yhxgtd)
                            userUpdateMSGList.add(muTmp);
                            break;
                        case 12://12:调班(tbtd)
                            tiaobanMSGList.add(muTmp);
                            break;
                        case 13://13:校风提醒(xftd)
                            xftxMSGList.add(muTmp);
                            break;
                        case 14://14:通知
                            tzMSGList.add(muTmp);
                            break;
                    }
                }
            }
        }
        //公告
        mp.put("msgNoticeList", msgNoticeList);
        //申请
        mp.put("shenqingMSGList", shenqingMSGList);
        //审核消息
        mp.put("shenheMSGList", shenheMSGList);
        //报名消息
        mp.put("baomingMSGList", baomingMSGList);
        //录取消息
        mp.put("luquMSGList", luquMSGList);
        //帖消息
        mp.put("fatieMSGList", fatieMSGList);
        //任命消息
        mp.put("renmingMSGList", renmingMSGList);
        //任务消息
        mp.put("renwuMSGList", renwuMSGList);
        //成绩消息
        mp.put("chengjiMSGList", chengjiMSGList);
        //活动消息
        mp.put("huodongMSGList", huodongMSGList);
        //用户修改
        mp.put("userUpdateMSGList", userUpdateMSGList);
        //调班
        mp.put("tiaobanMSGList", tiaobanMSGList);
        //校风提醒
        mp.put("xftxMSGList", xftxMSGList);
        //通知
        mp.put("tzMSGList", tzMSGList);
        List<Map<String,Object>> mapList=this.myInfoUserManager.getSYMsgDataCount(this.logined(request).getRef());
        // 记录数量
        Integer[] msgNumArray={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        if(mapList!=null&&mapList.size()>0){
            for (Map<String,Object> tmpmp:mapList){
                if(tmpmp!=null&&tmpmp.containsKey("MSG_ID")&&tmpmp.get("MSG_ID")!=null){
                        Integer msgid= Integer.parseInt(tmpmp.get("MSG_ID").toString().trim());
                    msgNumArray[msgid-1]=Integer.parseInt(tmpmp.get("MSGCOUNT").toString());
                }
            }
        }
        /**
         * 资源审核通过
         *   1:公告(ggtd)
         *2:申请(sqtd)
         *3:审核(shtd)
         *4:报名(bmtd)
         *5:录取(lqtd)
         *6:发帖(fttd)
         *7:任命(rmtd)
         *8:任务(rwtd)
         *9:成绩(cjtd)
         *10:活动(hdtd)
         *11:用户修改(yhxgtd)
         *12:调班(tbtd)
         *13:校风提醒(xftd)
         *14:通知(tztd)
         */
        mp.put("msgNoticeCount", msgNumArray[0]);
        mp.put("shenqingMSGCount", msgNumArray[1]);
        mp.put("shenheMSGCount", msgNumArray[2]);
        mp.put("baomingMSGCount",msgNumArray[3]);
        mp.put("luquMSGCount",msgNumArray[4]);
        mp.put("fatieMSGCount",msgNumArray[5]);
        mp.put("renmingMSGCount",msgNumArray[6]);
        mp.put("renwuMSGCount",msgNumArray[7]);
        mp.put("chengjiMSGCount",msgNumArray[8]);
        mp.put("huodongMSGCount",msgNumArray[9]);
        mp.put("userUpdateMSGCount",msgNumArray[10]);
        mp.put("tiaobanMSGCount",msgNumArray[11]);
        mp.put("xftxMSGCount",msgNumArray[12]);
        mp.put("tzMSGCount",msgNumArray[13]);

        //如果是学生，则得到该学生的当前任务信息
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            List<Map<String,Object>> tkMapList=this.userManager.getCourseTaskCount(this.logined(request).getUserid());
            mp.put("stuTkList",tkMapList);
        }
        //初始化向导状态
        List<InitWizardInfo> initList = this.initWizardManager.getList(null,null);
        if(initList.size()>0){
            request.setAttribute("initObj",initList.get(0));
        }else{
            request.setAttribute("initObj",null);
        }

        //初始化用户名状态
        UserInfo sel=new UserInfo();
        sel.setUserid(this.logined(request).getUserid());
        List<UserInfo>userList=this.userManager.getList(sel,null);
        if(this.logined(request)!=null&&!this.logined(request).getUsername().toLowerCase().equals("admin"))
            request.setAttribute("ismodify",userList.get(0).getIsmodify());
        if(userList!=null&&userList.size()>0)
            request.setAttribute("pass",userList.get(0).getPassword());
//	    request.setAttribute("isfirst", UtilTool.utilproperty.getProperty("ISFIRSTLOGIN"));
//        System.out.println(UtilTool.utilproperty.getProperty("ISFIRSTLOGIN")+"----isfrist");
        List<SubjectInfo> subList = this.subjectManager.getList(null, null);
        request.setAttribute("subList", subList);
        List<Map<String, Object>> termList = this.termManager.getYearTerm();
        request.setAttribute("termList", termList.get(0));

        return new ModelAndView("index");
    }


    @RequestMapping(params="m=testPageIndex",method=RequestMethod.GET)
    public void testPageIndex(HttpServletRequest request,HttpServletResponse response)throws Exception{
//		System.out.println("1");
//		request.setAttribute("username", "20151132");
//		request.setAttribute("password", "111111");
        //在100人中，随机获取用户。
        ClassUser cutmp=new ClassUser();
        cutmp.setRelationtype("学生");
        cutmp.getClassinfo().setYear("2013~2014");
        PageResult presult=new PageResult();
        presult.setPageNo(new Random().nextInt(101));
        presult.setPageSize(1);
        Map<String,Object> objMap=userManager.getTestUser("2013~2014","学生", presult);
        request.setAttribute("username",objMap.get("USER_NAME"));
        request.setAttribute("password", objMap.get("PASSWORD"));


        //登陆验证
        this.doLogin(request, response);
        //首页
        //得到当前学期的授课班级
        //得到当前学期
        TermInfo term=this.termManager.getAutoTerm();
        JsonEntity jeEntity=new JsonEntity();
        if(term==null){
            jeEntity.setMsg("异常错误，网站设置错误，请联系管理人员设置学期!");
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return;
        }
        //得到教授班级
        String year=term.getYear();
        ClassUser cu=new ClassUser();
        cu.setYear(year);
        if(this.validateRole(request, UtilTool._ROLE_TEACHER_ID))
            cu.setRelationtype("任课老师");
        else if(this.validateRole(request, UtilTool._ROLE_STU_ID))
            cu.setRelationtype("学生");
        else if(this.validateRole(request, UtilTool._ROLE_CLASSADVISE_ID))
            cu.setRelationtype("班主任");
        cu.setUserid(this.logined(request).getRef());
        List<ClassUser> cuList=this.classUserManager.getList(cu, null);
        //	if(cuList!=null&&cuList.size()>0)
        //		mp.put("teachClass", cuList);
        //得到通知公告,网上公示
        NoticeInfo notic=new NoticeInfo();
        notic.setCuserid(this.logined(request).getRef());
        notic.setNoticetype(UtilTool._NOTICE_TYPE[0]);  //通知公告
        presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(5);
        presult.setOrderBy("n.IS_TOP ASC,C_TIME DESC");
        List<NoticeInfo> noticeList=this.noticeManager.getUserList(notic, presult);
        //	mp.put("notices", noticeList);

        notic.setNoticetype(UtilTool._NOTICE_TYPE[1]); //网上公示
        List<NoticeInfo> wsgsList=this.noticeManager.getUserList(notic, presult);
        //mp.put("internetNotices", wsgsList);

        presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(5);
        presult.setOrderBy("n.IS_TOP ASC,C_TIME DESC");
        notic.setNoticetype(UtilTool._NOTICE_TYPE[2]); //校内参考
        List<NoticeInfo> xnckNotice=this.noticeManager.getUserList(notic, presult);
        //mp.put("xnckNotices", xnckNotice);
        //得到最近的关于我的活动信息
        ActivityInfo activity=new ActivityInfo();
        activity.setUserid(this.logined(request).getRef());
        presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(5);
        presult.setOrderBy("ac.C_TIME DESC");//,ac.M_TIME DESC
        List<ActivityInfo> activityList=this.activityManager.getList(activity, presult);
        //mp.put("activityList", activityList);
        //得到部门类别
        UtilTool._GetDeptType();
        //得到消息
        /**
         * 	/** 
         关于p_msg_id的相关
         1:公告(ggtd)
         2:申请(sqtd)
         3:审核(shtd)
         4:报名(bmtd)
         5:录取(lqtd)
         6:发帖(fttd)
         7:任命(rmtd)
         8:任务(rwtd)
         9:成绩(cjtd)
         10:活动(hdtd)
         11:用户修改(yhxgtd)
         12:调班(tbtd)
         13:校风提醒(xftd)	  
         14:通知
         */
        PageResult prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        MyInfoUserInfo mu=new MyInfoUserInfo();
        //公告消息
        mu.setUserref(this.logined(request).getRef());
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.NOTICE.getValue()); //公告
        List<MyInfoUserInfo> msgNoticeList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("msgNoticeList", msgNoticeList);
        Date d=new Date();
        d.setDate(new Date().getDate()-2);
        String btime=UtilTool.DateConvertToString(d, DateType.type1);

        String etime=UtilTool.DateConvertToString(new Date(), DateType.type1);
        //System.out.println(etime);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //	mp.put("msgNoticeCount", this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime));

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //申请消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.SHENQING.getValue());
        List<MyInfoUserInfo> shenqingMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //	mp.put("shenqingMSGList", shenqingMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("shenqingMSGCount", this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime));

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //审核消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.SHENHE.getValue());
        List<MyInfoUserInfo> shenheMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("shenheMSGList", shenheMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("shenheMSGCount", );

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //报名消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.BAOMING.getValue());
        List<MyInfoUserInfo> baomingMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("baomingMSGList", baomingMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("baomingMSGCount", );

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //录取消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.LUQU.getValue());
        List<MyInfoUserInfo> luquMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("luquMSGList", luquMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("luquMSGCount", );

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");


        //帖消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.FATIE.getValue());
        List<MyInfoUserInfo> fatieMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("fatieMSGList", fatieMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("fatieMSGCount", );

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //任命消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.RENMING.getValue());
        List<MyInfoUserInfo> renmingMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("renmingMSGList", renmingMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("renmingMSGCount", );

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //任务消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.RENWU.getValue());
        List<MyInfoUserInfo> renwuMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("renwuMSGList", renwuMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("renwuMSGCount", );		

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //成绩消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.CHENGJI.getValue());
        List<MyInfoUserInfo> chengjiMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("chengjiMSGList", chengjiMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("chengjiMSGCount", this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime));

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //活动消息
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.HUODONG.getValue());
        List<MyInfoUserInfo> huodongMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("huodongMSGList", huodongMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("huodongMSGCount", );

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //用户修改
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.YONGHUXIUGAI.getValue());
        List<MyInfoUserInfo> userUpdateMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("userUpdateMSGList", userUpdateMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("userUpdateMSGCount", this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime));

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //调班
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.TIAOBAN.getValue());
        List<MyInfoUserInfo> tiaobanMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("tiaobanMSGList", tiaobanMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("tiaobanMSGCount", );

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //校风提醒
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.XFTX.getValue());
        List<MyInfoUserInfo> xftxMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("xftxMSGList", xftxMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("xftxMSGCount", );

        prmsg=new PageResult();
        prmsg.setPageNo(1);
        prmsg.setPageSize(5);
        prmsg.setOrderBy("mu.C_TIME DESC");
        //通知
        mu.setMsgid(UtilTool.MYINFO_MSG_TYPE.TONGZHI.getValue());
        List<MyInfoUserInfo> tzMSGList=this.myInfoUserManager.getList(mu,prmsg);
        //mp.put("tzMSGList", tzMSGList);
        this.myInfoUserManager.getMyInfoUserInfoCountFirstPage(mu.getMsgid(), mu.getUserref(), btime, etime);
        //mp.put("tzMSGCount", );

        //加载各个部门
        PageResult presult1=new PageResult();
        List<Map<String,Object>> deptTypeMap=UtilTool._GetDeptType();
        for (Map<String, Object> map : deptTypeMap) {
            //加载DeptUser
            String depttypeid=map.get("deptid").toString();
            if(depttypeid==null||depttypeid.trim().length()<1||!UtilTool.isNumber(depttypeid)){
                jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jeEntity.toJSON());return;
            }
            DeptUser dpTmp=new DeptUser();
            dpTmp.getDeptinfo().setTypeid(Integer.parseInt(depttypeid.trim()));
            //		dpTmp.setUserref(this.logined(request).getRef());	
            dpTmp.setOtheruserref(this.logined(request).getRef());
            List<DeptUser> dpList=this.deptUserManager.getList(dpTmp, presult1);

        }

        //得到当前用户有权限的栏目
        //List<ColumnInfo> columnList=columnManager.getUserColumnList(usr.getRef());

        response.getWriter().print("所有首页数据load over!");

    }
    @RequestMapping(params="m=fwj",method=RequestMethod.GET)
    public void fwj(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jeEntity=new JsonEntity();

        //发送文件		
        List<String>	sendFilepath = new ArrayList<String>();
        sendFilepath.add("C:/Users/qihaishi.ETIANTIAN/Desktop/1123一五六数学课堂（王秋嬛）.mp4");
        sendFilepath.add("E:/工具/LOL_V3.0.8.5_FULL.exe");
        SendFile s = new SendFile();
        if (s.sendFileOperate(sendFilepath)){
            System.out.println("success");
            jeEntity.setType("success");
            jeEntity.setMsg("发送文件成功!");
        }else{
            System.out.println("error");
            jeEntity.setMsg("发送文件失败!");
        }
        response.getWriter().print(jeEntity.toJSON());
    }

    /**
     * 乐知行进入Ett
     * @param request
     * @param response
     * @param mp
     * @throws Exception
     */
    @RequestMapping(params="m=lzxToEtt",method=RequestMethod.GET)
    public void lzxToEttUrl(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String lzxUserid=request.getParameter("lzx_user_id");
        JsonEntity jsonEntity=new JsonEntity();
        //查询用户
        if(lzxUserid==null||lzxUserid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        //查询
        UserInfo userInfo=new UserInfo();
        userInfo.setLzxuserid(lzxUserid);
        List<UserInfo> uList=this.userManager.getList(userInfo,null);
        if(uList==null||uList.size()<1){
            jsonEntity.setMsg("异常错误，原因：没有帐号信息!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        userInfo=uList.get(0);
        UserInfo tmpUser=new UserInfo();
        tmpUser.setUsername(userInfo.getUsername());
        tmpUser.setPassword(userInfo.getPassword());
        jsonEntity=this.loginBase(tmpUser,request,response);
        tmpUser=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
        if(tmpUser!=null&&jsonEntity.getType().trim().equals("success")){
            request.setAttribute("tmpUser",tmpUser);
            this.getEttUrl(request, response, mp);
        }else{
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());
        }
    }

    /**
     * 恶心的四中免费进入。。
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=mfToEttUrl",method=RequestMethod.GET)
    public void toEttURL(HttpServletRequest request,HttpServletResponse response) throws Exception{

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // 获得用户信息
        UserInfo u = this.logined(request);
        if (u == null) {
            String msg = "您尚未登录，请登录后重试!";
            response.getWriter().print(
                    "<script type='text/javascript'>alert('" + msg
                            + "');history.go(-1);</script>");
            return;
        }
        // ////////////////////////////变量存储
        // 时间变量
        Long time = new Date().getTime();
        Integer uid = u.getUserid(); // 当前登录的ID
        String uidref=u.getRef();
        List<Integer> gradeidList = new ArrayList<Integer>(); // 年级列表
        String realname = null;
        Long usertype = null;
        List<Long> subjectid = new ArrayList<Long>();
        int sex = 3;
        String email = null;
        String key = "beijing0705sizhong2011jiekou";
        // 连接地址"http://web.etiantian.com/ett20/study/common/szreg.jsp

        StringBuilder http_1 = new StringBuilder(
                "http://langyilin.etiantian.com:8080/ett20/study/common/szreg.jsp");

        int isLearnGuide = 2; // 判断是否为学案导学用户 1：是 2:否

        // 加载老师的细节角色部分。
        boolean isflag = false, ishasflag = false, ishasgrade = false, isstuflag = false;
        ;
        if (u.getCjJRoleUsers() != null && u.getCjJRoleUsers().size() > 0) {
            for (Object obj : u.getCjJRoleUsers()) {
                RoleUser ru = (RoleUser) obj;
                if (ru != null) {
                    // 为班级，管理员，班主任为全部
                    if (ru.getRoleid()==UtilTool._ROLE_CLASSADVISE_ID
                            || ru.getRoleid()==UtilTool._ROLE_ADMIN_ID) {
                        // 全部学科
                        usertype = Long.valueOf(1);
                        subjectid.add(Long.valueOf(0));

                        if (ru.getRoleid()==UtilTool._ROLE_ADMIN_ID) {
                            // 全部年纪
                            gradeidList.add(0);
                            realname = ru.getRolename();
                            ishasgrade = true;
                        }
                        isflag = true;
                    }
                    if (ru.getRoleid()==UtilTool._ROLE_STU_ID)
                        isstuflag = true;

                }
            }
        }
        boolean isteacher=false;
        // 判断是否为教师
        // usertype
        RoleUser ru=new RoleUser();
        ru.getUserinfo().setRef(uidref);
        ru.setRoleidstr(UtilTool._ROLE_TEACHER_ID.toString());
        List<RoleUser> ruList=this.roleUserManager.getList(ru, null);
        if (ruList!=null&&ruList.size()>0) {
            isteacher=true;
            // 教师显示全部年纪 2012-02-03 11:06
            gradeidList.clear();
            gradeidList.add(0);
            ishasgrade = true;// 已经存在年级的处理

            // email
            usertype = Long.valueOf(1);
            TeacherInfo tea=new TeacherInfo();
            tea.setUserid(uidref);
            String ml=null;
            List<TeacherInfo> teaList= this.teacherManager.getList(tea,null);
            if(teaList!=null&&teaList.size()>0)
                ml=teaList.get(0).getTeacherpost();
            if (ml != null && ml.indexOf("@") != -1)
                email = ml;

            if (teaList != null && teaList.size() > 0) {
                if (teaList.get(0).getTeachername() != null
                        && teaList.get(0).getTeachername().trim().length() > 0)
                    realname = teaList.get(0).getTeachername();
                if (teaList.get(0).getTeachersex().trim().equals("女"))
                    sex = 2;
                else
                    sex = 1;
            } else {
                String msg = "异常错误!您的信息中没有详细信息，请联系管理人员!!";
                response.getWriter().print(
                        "<script type='text/javascript'>alert('" + msg
                                + "');history.go(-1);</script>");
                return;
            }

            // 如果不存在教务，班主任角色 则按课程显示
            if (!isflag) {
                Object[] courseArray = teaList.get(0).getSubjects().split(
                        "\\,");
                if (courseArray != null && courseArray.length > 0 && !ishasflag) {
                    // 存储学科临时参数
                    for (Object obj : courseArray) {
                        if (obj != null && obj.toString().trim().length() > 0) {
                            if (obj.toString().trim().indexOf("语文") != -1) {
                                subjectid.add(Long.valueOf(1));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("数学") != -1) {
                                subjectid.add(Long.valueOf(2));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("英语") != -1
                                    || obj.toString().trim().indexOf("外语") != -1) {
                                subjectid.add(Long.valueOf(3));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("物理") != -1) {
                                subjectid.add(Long.valueOf(4));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("化学") != -1) {
                                subjectid.add(Long.valueOf(5));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("历史") != -1) {
                                subjectid.add(Long.valueOf(6));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("生物") != -1) {
                                subjectid.add(Long.valueOf(7));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("地理") != -1) {
                                subjectid.add(Long.valueOf(8));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("政治") != -1) {
                                subjectid.add(Long.valueOf(9));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("自然科学") != -1) {
                                subjectid.add(Long.valueOf(10));
                                ishasflag = true;
                            } else if (obj.toString().trim().indexOf("学生处") != -1
                                    || obj.toString().trim().indexOf("电教组") != -1
                                    || obj.toString().trim().indexOf("校办") != -1
                                    || obj.toString().trim().indexOf("教学处") != -1
                                    || obj.toString().trim().indexOf("总务处") != -1
                                    || obj.toString().trim().indexOf("网络") != -1) {
                                if (!gradeidList.contains(0)) {
                                    gradeidList.add(0);
                                }
                                subjectid.add(Long.valueOf(0));
                                ishasflag = true;
                                ishasgrade = true;
                            }
                        }
                    }
                    // 不显示学科信息
                    if (!ishasflag) {
                        subjectid.add(Long.valueOf(-1));
                    }
                }
            }

        } else {
            StudentInfo stu=new StudentInfo();
            stu.setUserref(uidref);
            //StudentInfo stu
            List<StudentInfo> stuList= this.studentManager.getList(stu,null);
            if(stuList!=null&&stuList.size()>0)
                stu=stuList.get(0);
            else
                stu=null;
            if (stu != null) {
                usertype = Long.valueOf(3);
                realname = stu.getStuname();
                if (stu.getStusex().trim().equals("0"))
                    sex = 2;
                else
                    sex = 1;
                // 处理是否是学案导学学生。
//				if (stu.getIslearnGuide() != null
//						&& stu.getIslearnGuide().trim().equals("是")) {
//					isLearnGuide = 1;
//				}

            } else if (!isflag) {
                String msg = "异常错误!您的信息中存在异常，请联系管理人员!!";
                response.getWriter().print(
                        "<script type='text/javascript'>alert('" + msg
                                + "');history.go(-1);</script>");
                return;
            }
        }
        TermInfo t = this.termManager.getAutoTerm();
        // 加载年级
        if (!ishasgrade) {
            ClassUser cu = new ClassUser();
            cu.setUserid(uidref);
            // 查询当前的年级  如果当前学期没有数据，则不存入学期
            String tmYear = t.getYear().trim();
            cu.setYear(tmYear);
            List<ClassUser> cuList = this.classUserManager.getList(cu, null);
            // 加载老师教授最新年级GradeId
            // 标识是否在7.15 - 1.1号之间
            boolean isshengji = false;
            if(cuList==null||cuList.size()<1){
                cu.setYear(null);
                cuList =  this.classUserManager.getList(cu, null);
                // 现在的
                Date nowd = new Date();
                Date d = UtilTool.StringConvertToDate(nowd.getYear() + "-07-15");

                if (nowd.getTime() >= d.getTime() && usertype == 3)
                    isshengji = true;
            }

            if (cuList != null && cuList.size() > 0) {
                boolean isbiye = false;
                for (ClassUser cuObj : cuList) {
                    String clsGrade = cuObj.getClassgrade();
                    if (clsGrade != null) {
                        if (clsGrade.trim().equals("高三")
                                || clsGrade.trim().equals("高中三年级")) {
                            // 说明是毕业学生
                            if (!cuObj.getYear().trim()
                                    .equals(tmYear)) {
                                isbiye = true;
                                break;
                            }
                            if (!gradeidList.contains(1))
                                gradeidList.add(1);
                        } else if (clsGrade.trim().equals("高二")
                                || clsGrade.trim().equals("高中二年级")) {
                            int currentClsCode = 2;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else

                        if (clsGrade.trim().equals("高一")
                                || clsGrade.trim().equals("高中一年级")) {
                            int currentClsCode = 3;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("初三")
                                || clsGrade.trim().equals("初中三年级")) {
                            int currentClsCode = 4;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("初二")
                                || clsGrade.trim().equals("初中二年级")) {
                            int currentClsCode = 5;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("初一")
                                || clsGrade.trim().equals("初中一年级")) {
                            int currentClsCode = 6;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("小学六年级")
                                || clsGrade.trim().equals("小六")) {
                            int currentClsCode = 7;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("小学五年级")
                                || clsGrade.trim().equals("小五")) {
                            int currentClsCode = 8;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("小学四年级")
                                || clsGrade.trim().equals("小四")) {
                            int currentClsCode = 9;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("小学三年级")
                                || clsGrade.trim().equals("小三")) {
                            int currentClsCode = 10;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        }

                        if (gradeidList != null && gradeidList.size() > 0) {
                            if (isstuflag)
                                break;
                        }
                    }
                }
                if (isbiye && !isteacher && !isflag) {
                    String msg = "抱歉!您已经毕业，将无法使用网校平台!";
                    response.getWriter().print(
                            "<script type='text/javascript'>alert('" + msg
                                    + "');history.go(-1);</script>");
                    return;
                }
            } else {
                String msg = "抱歉!您在本学期中没有新的班级信息!无法进入!详情请联系管理人员!";
                response.getWriter().print(
                        "<script type='text/javascript'>alert('" + msg
                                + "');history.go(-1);</script>");
                return;
            }
        }

        // 加密
        StringBuilder md5Builder = new StringBuilder(time.toString());
        if (gradeidList != null && gradeidList.size() > 0) {
            StringBuilder gradeidBuilder = new StringBuilder();
            for (Integer gl : gradeidList) {
                gradeidBuilder.append(gl);
                if (gl != gradeidList.get(gradeidList.size() - 1))
                    gradeidBuilder.append(",");
            }
            if (gradeidBuilder.toString().trim().length() > 0)
                md5Builder.append(gradeidBuilder);
        }
        md5Builder.append(uid);
        md5Builder.append(realname);
        md5Builder.append(usertype);
        if (subjectid != null && subjectid.size() > 0) {
            for (Long sid : subjectid) {
                md5Builder.append(sid);
            }
        }
        md5Builder.append(sex);
        if (email != null) {
            md5Builder.append(email);
        }
        // 为了应用性，可以测试以及兼容性，不带此参数
        // if(usertype==3){
        // md5Builder.append(isLearnGuide);
        // }
        md5Builder.append(time);
        md5Builder.append(key);

        String md5Str = Encrypt.byte2hex(Encrypt.encryptString(md5Builder.toString(), ""));
        // 加载整个连接
        http_1.append("?uid=" + uid); // a
        http_1.append("&realname="
                + java.net.URLEncoder.encode(realname, "UTF-8")); // b
        http_1.append("&usertype=" + usertype); // c
        http_1.append("&sex=" + sex); // d
        http_1.append("&time=" + time); // e
        if (email != null && email.trim().indexOf("@") != -1)
            http_1.append("&email="
                    + java.net.URLEncoder.encode(email.trim(), "UTF-8")); // f
        if (gradeidList != null && gradeidList.size() > 0) {
            StringBuilder gradeidBuilder = new StringBuilder();
            for (Integer gl : gradeidList) {
                gradeidBuilder.append(gl);
                if (gl != gradeidList.get(gradeidList.size() - 1))
                    gradeidBuilder.append(",");
                // if(gl!=gradeidList.get(gradeidList.size()-1))
                // gradeidBuilder.append(",");
            }
            if (gradeidBuilder.toString().trim().length() > 0)
                http_1.append("&gradeid=" + gradeidBuilder.toString());
        }
        if (subjectid != null && subjectid.size() > 0) {
            for (Long sl : subjectid) {
                http_1.append("&subjectid=" + sl); // h
            }
        }
        if (usertype == 3) {

            http_1.append("&islearnguide=" + isLearnGuide);
        }
        http_1.append("&s=" + md5Str); // i
        http_1.append("&srcId="+50006);
        // 执行链接(为了安全性)
        //  String responseHTML=UserTool.getOutputHTML(http_1.toString(),null);
        String responseHTML=UserTool.sendPostURL(http_1.toString(),null);

        if (responseHTML == null) {
            // 提示 返回
            response
                    .getWriter()
                    .print(
                            "<script type='text/javascript'>alert('异常错误!您的信息不完整并且验证失败!请重新登陆后重试!');window.close();</script>");
            return;
        }
        JSONObject jo=JSONObject.fromObject(responseHTML);
        if(jo==null||jo.get("result")==null||jo.get("result").toString().trim().equals("-1")
                ||!jo.containsKey("code")||jo.get("code")==null||jo.get("code").toString().trim().length()<1){
            response
                    .getWriter()
                    .print(
                            "<script type='text/javascript'>alert('异常错误!您的信息不完整并且验证失败!请重新登陆后重试!');window.close();</script>");
            return;
        }
//        String urlStr = "http://web.etiantian.com/ett20/study/common/szlogin.jsp?code="
//                + responseHTML;
        String urlStr = "http://langyilin.etiantian.com:8080/ett20/study/common/szlogin.jsp?code="
                + jo.get("code");
        System.out.println(responseHTML);
        response.sendRedirect(urlStr);
        return;

        // response.getWriter().
        // print("{\"type\":\"success\",\"msg\":\""+http_1.toString()+"\"}");


    }

    /**
     * 进入四中网校平台
     *
     * @throws Exception
     */
    @RequestMapping(params="m=toEttUrl", method = RequestMethod.GET)
    public void getEttUrl(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");



        //功能ID
        String mid=request.getParameter("mid");
        String modelName=request.getParameter("modelName");
//        if(isEttNoRegSchool()&&mid==null&&modelName==null){ //如果是免费版本，则进入该系统
//            response.sendRedirect("user?m=mfToEttUrl");return;
//        }

        // 获得用户信息
        UserInfo u = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
        // ////////////////////////////变量存储
        // 时间变量
        Long time = new Date().getTime();
//		BigDecimal uid = u.getUserId(); // 当前登录的ID
        String uidRef=u.getRef();
        Integer uid=u.getUserid();
        String username=u.getUsername();
        List<Integer> gradeidList = new ArrayList<Integer>(); // 年级列表
        String realname = null;
        Long usertype = null;
        List<Long> subjectid = new ArrayList<Long>();
        int sex = 3;
        String email = null;
        String key = UtilTool.utilproperty.getProperty("TO_ETT_KEY").toString();
        // 连接地址"http://web.etiantian.com/ett20/study/common/szreg.jsp

        int isLearnGuide = 2; // 判断是否为学案导学用户 1：是 2:否

        // 加载老师的细节角色部分。
        boolean isflag = false, ishasflag = false, ishasgrade = false, isstuflag = false;
        ;
        if (u.getCjJRoleUsers() != null && u.getCjJRoleUsers().size() > 0) {
            for (Object obj : u.getCjJRoleUsers()) {
                RoleUser ru = (RoleUser) obj;
                if (ru != null) {
                    // 为班级，管理员，班主任为全部
                    if (ru.getRoleid().intValue()==UtilTool._ROLE_ADMIN_ID.intValue()
                            || ru.getRoleid().intValue()==UtilTool._ROLE_CLASSADVISE_ID.intValue()
                            ) {
                        // 全部学科
                        usertype = Long.valueOf(1);
                        subjectid.add(Long.valueOf(0));
                        //管理员,教务 查看全部年级
                        if (ru.getRoleid().intValue()==UtilTool._ROLE_ADMIN_ID.intValue()){
                            // 全部年纪
                            gradeidList.add(0);
                            realname = ru.getRolename().trim();
                            ishasgrade = true;
                        }
                        isflag = true;
                        break;
                    }
                    if (ru.getRoleid().intValue()==UtilTool._ROLE_STU_ID.intValue())
                        isstuflag = true;

                }
            }
        }


        // 判断是否为教师
        // usertype
        //boolean isteacher = this.getRolemanager().isTeacher(u.getUserId());
        boolean  isteacher=this.validateRole(request, UtilTool._ROLE_TEACHER_ID);
//        if(isteacher&&Integer.parseInt(isVip)==0){
//            response.sendRedirect(UtilTool.utilproperty.getProperty("TEA_TO_ETT_REQUEST_FREE_URL").toString()); //教师进入ett入口
//            return;
//        }



        if (isteacher||!isstuflag) {

            // 教师显示全部年纪 2012-02-03 11:06
            gradeidList.clear();
            gradeidList.add(0);
           // ishasgrade = true;// 已经存在年级的处理

            // email
            usertype = Long.valueOf(1);
            String ml =u.getMailaddress();
            if (ml != null && ml.indexOf("@") != -1)
                email = ml;
            TeacherInfo t = new TeacherInfo();
            t.setUserid(uidRef);
            List<TeacherInfo> teaList = this.teacherManager.getList(t, null);
            if (teaList != null && teaList.size() > 0) {
                if (teaList.get(0).getTeachername()!= null
                        && teaList.get(0).getTeachername().trim().length() > 0)
                    realname = teaList.get(0).getTeachername();
                if (teaList.get(0).getTeachersex().trim().equals("女"))
                    sex = 2;
                else
                    sex = 1;
            } else {
                String msg = "异常错误，没有找到您的教师信息数据，请联系管理人员!";
                response.getWriter().print(
                        "<script type='text/javascript'>alert('" + msg
                                + "');this.close();</script>");
                return;
            }



            // 如果不存在教务，班主任角色 则按课程显示
            if (!isflag) {
                if(teaList.size()<1||teaList.get(0)==null||teaList.get(0).getSubjects()==null){
                    gradeidList.add(0);
                    subjectid.add(Long.valueOf(0));
                }else{
                    Object[] courseArray = teaList.get(0).getSubjects().split(
                            ",");
                    if (courseArray != null && courseArray.length > 0 && !ishasflag) {
                        // 存储学科临时参数
                        for (Object obj : courseArray) {
                            if (obj != null && obj.toString().trim().length() > 0) {
                                if (obj.toString().trim().indexOf("语文") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(1));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("数学") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(2));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("英语") != -1
                                        || obj.toString().trim().indexOf("外语") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(3));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("物理") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(4));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("化学") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(5));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("历史") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(6));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("生物") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(7));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("地理") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(8));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("政治") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(9));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("自然科学") != -1) {
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(10));
                                    ishasflag = true;
                                } else if (obj.toString().trim().indexOf("学生处") != -1

                                        || obj.toString().trim().indexOf("电教组") != -1
                                        || obj.toString().trim().indexOf("校办") != -1
                                        || obj.toString().trim().indexOf("教学处") != -1
                                        || obj.toString().trim().indexOf("总务处") != -1
                                        || obj.toString().trim().indexOf("网络") != -1) {
                                    if (!gradeidList.contains(0)) {
                                        gradeidList.add(0);
                                    }
                                    if(subjectid.size()<1)
                                        subjectid.add(Long.valueOf(0));
                                    ishasflag = true;
                                    ishasgrade = true;
                                }
                            }
                        }
                        // 不显示学科信息
                        if (!ishasflag) {
                            subjectid.add(Long.valueOf(-1));
                        }
                    }
                }
            }
        } else {
            StudentInfo stu=new StudentInfo();
            stu.setUserref(uidRef);
            List<StudentInfo> stuList = this.studentManager.getList(stu,null);
            if(stuList==null||stuList.size()<1){
                String msg = "异常错误，没有找到您的学生信息数据，请联系管理人员!";

                response.getWriter().print(
                        "<script type='text/javascript'>alert('" + msg
                                + "');this.close();</script>");
                return;
            }
            stu=stuList.get(0);

            if (stu != null) {
                usertype = Long.valueOf(3);
                realname = stu.getStuname();
                if (stu.getStusex()!=null&&stu.getStusex().trim().equals("女"))
                    sex = 2;
                else
                    sex = 1;
                // 处理是否是学案导学学生。
                if (stu.getIslearnguide() != null
                        && stu.getIslearnguide().trim().equals("是")) {
                    isLearnGuide = 1;
                }

            } else if (!isflag) {
                String msg = "异常错误，没有找到您的学生信息数据，请联系管理人员!";
                response.getWriter().print(
                        "<script type='text/javascript'>alert('" + msg
                                + "');this.close();</script>");
                return;
            }
        }
        TermInfo t = this.termManager.getAutoTerm();
        String tmYear = t.getYear().trim();
        // 加载年级
        if (!ishasgrade) {
            gradeidList.clear();
            ClassUser cu = new ClassUser();
            cu.setUserid(uidRef);
            // 查询当前的年级  如果当前学期没有数据，则不存入学期
            cu.setYear(t.getYear().trim());
            List<ClassUser> cuList = this.classUserManager.getList(cu, null);
            // 加载老师教授最新年级GradeId
            // 标识是否在7.15 - 1.1号之间
            boolean isshengji = false;
            if(cuList==null||cuList.size()<1){
                cu.setYear(null);
                cuList = this.classUserManager
                        .getList(cu,null);
                // 现在的
                Date nowd = new Date();
                Date d = UtilTool.StringConvertToDate(UtilTool.DateConvertToString(nowd,DateType.year) + "-07-15 00:00:00");
                if (nowd.getTime() >= d.getTime() && usertype == 3)
                    isshengji = true;
            }

            if (cuList != null && cuList.size() > 0) {
                boolean isbiye = false;
                for (ClassUser cuObj : cuList) {
                    tmYear=cuObj.getYear();
                    String clsGrade = cuObj.getClassgrade();
                    if (clsGrade != null) {
                        if (clsGrade.trim().equals("高三")
                                || clsGrade.trim().equals("高中三年级")) {
                            // 说明是毕业学生
                            if (!cuObj.getYear().trim()
                                    .equals(tmYear)) {
                                isbiye = true;
                                break;
                            }
                            if (!gradeidList.contains(1))
                                gradeidList.add(1);
                        } else if (clsGrade.trim().equals("高二")
                                || clsGrade.trim().equals("高中二年级")) {
                            int currentClsCode = 2;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else

                        if (clsGrade.trim().equals("高一")
                                || clsGrade.trim().equals("高中一年级")) {
                            int currentClsCode = 3;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("初三")
                                || clsGrade.trim().equals("初中三年级")) {
                            int currentClsCode = 4;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("初二")
                                || clsGrade.trim().equals("初中二年级")) {
                            int currentClsCode = 5;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("初一")
                                || clsGrade.trim().equals("初中一年级")) {
                            int currentClsCode = 6;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("小学六年级")
                                || clsGrade.trim().equals("小六")) {
                            int currentClsCode = 7;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("小学五年级")
                                || clsGrade.trim().equals("小五")) {
                            int currentClsCode = 8;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("小学四年级")
                                || clsGrade.trim().equals("小四")) {
                            int currentClsCode = 9;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        } else if (clsGrade.trim().equals("小学三年级")
                                || clsGrade.trim().equals("小三")) {
                            int currentClsCode = 10;
                            // 如果但前年级不等于班级年级，说明未升级操作。则
                            if (isshengji
                                    && !cuObj.getYear().trim()
                                    .equals(tmYear.trim()))
                                currentClsCode -= 1;
                            if (!gradeidList.contains(currentClsCode))
                                gradeidList.add(currentClsCode);
                        }

                        if (gradeidList != null && gradeidList.size() > 0) {
                            if (isstuflag)
                                break;
                        }
                    }
                }
                if (isbiye && !isteacher && !isflag) {
                    String msg = "抱歉，您已经毕业！已无法使用该功能!";
                    response.getWriter().print(
                            "<script type='text/javascript'>alert('" + msg
                                    + "');this.close();</script>");
                    return;
                }
            } else {
                String msg = "您当前学期中没有班级信息，请联系相关老师进行修改!";
                response.getWriter().print(
                        "<script type='text/javascript'>alert('" + msg
                                + "');this.close();</script>");
                return;
            }
        }
        if(gradeidList == null ||gradeidList.size() <1){
            String msg = "您当前学期中没有班级信息，请联系相关老师进行修改!";
            response.getWriter().print(
                    "<script type='text/javascript'>alert('" + msg
                            + "');this.close();</script>");
            return;
        }

        String requestUrl=null;
        Map<String,Object> paramMap=new HashMap<String, Object>();
        // 参数
        String schoolid=this.logined(request).getDcschoolid()+"";
        String schoolname="schoolId"+this.logined(request).getDcschoolid();
        if(isteacher){
            //教师。
            String isVip=request.getParameter("isVip");
            if(isVip==null||isVip.length()<1){
                String msg = "异常错误，没有权限相关信息。";
                response.getWriter().print(
                        "<script type='text/javascript'>alert('" + msg
                                + "');this.close();</script>");
                return;
            }


            //得到Servertime
            String serverTtime=null;
            List<DictionaryInfo> dicList= this.dictionaryManager.getDictionaryByType("SERVER_TIME_END");
            if(dicList!=null&&dicList.size()>0){
                serverTtime =dicList.get(0).getDictionaryvalue();
            }
            paramMap.put("uid",uid);
            Long servertime=UtilTool.StringConvertToDate(serverTtime).getTime();

            paramMap.put("realname", java.net.URLEncoder.encode(realname, "UTF-8"));
            paramMap.put("usertype", usertype);
            paramMap.put("sex",sex);
            if(isVip!=null)
                paramMap.put("isVip",isVip);
            paramMap.put("time", time);
            if (email != null && email.trim().indexOf("@") != -1)
                paramMap.put("email",email.trim());
            if (gradeidList != null && gradeidList.size() > 0) {
                StringBuilder gradeidBuilder = new StringBuilder();
                for (Integer gl : gradeidList) {
                    gradeidBuilder.append(gl);
                    if (gl != gradeidList.get(gradeidList.size() - 1))
                        gradeidBuilder.append(",");
                    // if(gl!=gradeidList.get(gradeidList.size()-1))
                    // gradeidBuilder.append(",");
                }
                if (gradeidBuilder.toString().trim().length() > 0)
                    paramMap.put("gradeid", gradeidBuilder.toString());
            }
            if (subjectid != null && subjectid.size() > 0) {
                String subjectidStr=null;
                for (Long sl : subjectid) {
                    subjectidStr=(subjectidStr==null?sl+"":(subjectidStr+","+sl));
                }
                paramMap.put("subjectid",subjectidStr);
            }




            // http_1.append("&s=" + md5Str); // i


            paramMap.put("serverendtime",servertime);
            paramMap.put("s",UserTool.zuzhiTeacherSParameter(uid.toString(),gradeidList,realname,usertype.toString(),sex+"",email,schoolid,servertime,isVip,time.toString(),key));
            if(modelName==null)
                modelName="";
            //paramMap.put("modelName",modelName);
            paramMap.put("modelName",modelName);
            paramMap.put("username",java.net.URLEncoder.encode(username,"UTF-8"));
            requestUrl=UtilTool.utilproperty.getProperty("TEA_TO_ETT_REQUEST_URL").toString(); //教师进入ett入口
        }else if(isstuflag){
            //组织学生参数
            //就否免费   0:免 费   1:收费   只有学生才有该参数
            String isVipJson="";
            List<EttColumnInfo> ettColumnInfos1 =(List<EttColumnInfo>)request.getSession().getAttribute("ettColumnList");
            if(ettColumnInfos1!=null&& ettColumnInfos1.size()>0){
                for (EttColumnInfo ettc:ettColumnInfos1){
                    if(ettc!=null&&ettc.getEttcolumnurl()!=null&&
                            ettc.getRoletype()!=null&&ettc.getRoletype()==2){
                        String[] ecArray=ettc.getEttcolumnurl().split("mid=");
                        if(ecArray!=null&&ecArray.length>0){
                            if(isVipJson.trim().length()>0)
                                isVipJson+=",";
                            isVipJson+="\""+ecArray[ecArray.length-1]+"\":{\"isVip\":"+ettc.getStatus()+"}";
                        }
                    }
                }
            }
            if(isVipJson.trim().length()>0){
                isVipJson="{"+isVipJson+"}";
            }else{
                isVipJson=null;
            }
            paramMap.put("uid",uid);

            if(mid!=null)
               paramMap.put("mid",mid);
            if(isVipJson!=null)
                paramMap.put("isVip",isVipJson);
            paramMap.put("realname",  java.net.URLEncoder.encode(realname,"UTF-8"));
            paramMap.put("username",java.net.URLEncoder.encode(username,"UTF-8"));
            paramMap.put("usertype", usertype);
            paramMap.put("sex",sex);
            paramMap.put("time", time);
            if (email != null && email.trim().indexOf("@") != -1)
                paramMap.put("email",email.trim());
            if (gradeidList != null && gradeidList.size() > 0) {
                StringBuilder gradeidBuilder = new StringBuilder();
                for (Integer gl : gradeidList) {
                    gradeidBuilder.append(gl);
                    if (gl != gradeidList.get(gradeidList.size() - 1))
                        gradeidBuilder.append(",");
                    // if(gl!=gradeidList.get(gradeidList.size()-1))
                    // gradeidBuilder.append(",");
                }
                if (gradeidBuilder.toString().trim().length() > 0)
                    paramMap.put("gradeid", gradeidBuilder.toString());
            }

            //学校名称
            paramMap.put("s",UserTool.zuzhiStudentSParameter(uid.toString(), gradeidList, realname, usertype.toString(), sex+"", email, schoolid,isVipJson, time.toString(), key));
            String year=tmYear;
            if(year!=null&&year.trim().length()>0){
                year=year.split("~")[0];
            }
            paramMap.put("stuYear",year);
            requestUrl=UtilTool.utilproperty.getProperty("STU_TO_ETT_REQUEST_URL").toString(); //学生进入ett入口
        }
        paramMap.put("srcId",this.logined(request).getDcschoolid());//UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID").toString());
        paramMap.put("schoolid",schoolid);
        //得到学生名称
        paramMap.put("schoolname", java.net.URLEncoder.encode(schoolname,"UTF-8"));
        // 执行链接(为了安全性)
        System.out.println(requestUrl);

        //组织html准备输出页面并跳转
//        if(paramMap!=null&&paramMap.size()>0){
//            Set<String> keySetArray=paramMap.keySet();
//            for (String pkey:keySetArray){
//                System.out.print(pkey+":"+paramMap.get(pkey)+"\t");  //参数输出看看
        //组织页面并输出
//            }
//        }
        String responseHTML=UserTool.getOutputHTML(requestUrl, paramMap);
        //   System.out.println(responseHTML);
        response.getWriter().print(responseHTML);

        // response.getWriter().
        // print("{\"type\":\"success\",\"msg\":\""+http_1.toString()+"\"}");
    }

    /**
     * 是否是进网校不用注册
     * @return
     */
    private boolean isEttNoRegSchool(){
        String noRegSchoolId=UtilTool.utilproperty.getProperty("ETT_NO_REG_SCHOOL_ID");
        if(noRegSchoolId==null||noRegSchoolId.trim().length()<1)
            return false;
        String currentSchoolId=UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID").trim();
        if(noRegSchoolId.indexOf(",")>-1){
            String[] noRegSchoolArray=noRegSchoolId.split(",");
            if(noRegSchoolArray!=null&&noRegSchoolArray.length>0){
                for (int i=0;i<noRegSchoolArray.length;i++){
                    if(noRegSchoolArray[i].trim().equals(currentSchoolId)){
                        return true;
                    }
                }
            }
        }else{
            if(noRegSchoolId.trim().equals(currentSchoolId))
                return true;
        }
        return false;
    }



    /**
     * 乐知行用户添加接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=receiveLZXUser", method = RequestMethod.POST)
    public void receiveLZXUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");

        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON

        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }


        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                boolean isTea=false,isStu=false;
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";
                String username=obj.containsKey("user_name")?obj.getString("user_name"):"";
                String identityname=obj.containsKey("identity_name")?obj.getString("identity_name"):"";
                String stuname="",stusex="",stuno="";
                String teachername="",teachersex="";

                if(lzxuserid.length()<1){
                    je.setMsg("lzxuserid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(username.length()<1){
                    je.setMsg("username is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(identityname.length()<1){
                    je.setMsg("identityname is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(identityname.length()>0&&identityname.equals("教师")){
                    isTea=true;
                    teachername=obj.containsKey("teacher_name")?obj.getString("teacher_name"):"";
                    teachersex=obj.containsKey("teacher_sex")?obj.getString("teacher_sex"):"";
                    if(teachername.length()<1){
                        je.setMsg("teachername is empty!");
                        response.getWriter().print(je.toJSON());return;
                    }
                  /*  if(teachersex.length()<1){
                        je.setMsg("teachersex is empty!");
                        response.getWriter().print(je.toJSON());return;
                    } */
                }else if(identityname.length()>0&&identityname.equals("学生")){
                    isStu=true;
                    stuname=obj.containsKey("stu_name")?obj.getString("stu_name"):"";
                    stusex=obj.containsKey("stu_sex")?obj.getString("stu_sex"):"";
                    stuno=obj.containsKey("stu_no")?obj.getString("stu_no"):"";
                    if(stuname.length()<1){
                        je.setMsg("stuname is empty!");
                        response.getWriter().print(je.toJSON());return;
                    }
                 /*   if(stusex.length()<1){
                        je.setMsg("stusex is empty!");
                        response.getWriter().print(je.toJSON());return;
                    }*/
                }



                UserInfo userInfo=new UserInfo();
                userInfo.setUsername(username);
                userInfo.setLzxuserid(lzxuserid);
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                //已存在
                if(userInfoList!=null&&userInfoList.size()>0){
                    if(userInfoList.get(0).getStateid().toString().equals("1")){
                        UserInfo upd=new UserInfo();
                        upd.setUserid(userInfoList.get(0).getUserid());
                        upd.setStateid(0);
                        sql=new StringBuilder();
                        objList=this.userManager.getUpdateSql(upd,sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }else{
                        je.setMsg("username:"+username+" lzxuserid:"+lzxuserid+" already exists!");
                        response.getWriter().print(je.toJSON());return;
                    }
                }else{
                    //添加用户
                    String userNextRef = UUID.randomUUID().toString();
                    userInfo.setRef(userNextRef);
                    userInfo.setPassword("111111");
                    userInfo.setStateid(0);
                    userInfo.setSchoolid(schoolid);
                    sql = new StringBuilder();
                    objList = this.userManager.getSaveSql(userInfo, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }

                    //添加用户与身份关联信息
                    String identityNextRef = UUID.randomUUID().toString();
                    UserIdentityInfo ui = new UserIdentityInfo();
                    ui.setRef(identityNextRef);
                    ui.getUserinfo().setRef(userNextRef);
                    if(isTea)
                        ui.setIdentityname("教职工");
                    else if(isStu)
                        ui.setIdentityname("学生");
                    sql = new StringBuilder();
                    objList = this.userIdentityManager.getSaveSql(ui, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                    if(!isStu&&!isTea){
                        je.setMsg("Invalid identity!");
                        response.getWriter().print(je.toJSON());
                        return;
                    }


                    if(isStu){
                        // 添加学生
                        StudentInfo s = new StudentInfo();
                        s.getUserinfo().setRef(userNextRef);
                        s.setStuname(stuname);
                        s.setStusex(stusex);
                        s.setStuno(stuno);
                        sql = new StringBuilder();
                        objList = this.studentManager.getSaveSql(s, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        //添加默认学生角色
                        String ruNextRef = UUID.randomUUID().toString();
                        RoleUser ru = new RoleUser();
                        ru.setRef(ruNextRef);
                        ru.getUserinfo().setRef(userNextRef);
                        ru.getRoleinfo().setRoleid(UtilTool._ROLE_STU_ID);

                        sql = new StringBuilder();
                        objList = this.roleUserManager.getSaveSql(ru, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        //添加学生角色默认权限
                        RoleColumnRightInfo rc=new RoleColumnRightInfo();
                        rc.setRoleid(UtilTool._ROLE_STU_ID);
                        List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);
                        if(rcList!=null&&rcList.size()>0){
                            for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                                UserColumnRightInfo ucr=new UserColumnRightInfo();
                                ucr.setColumnid(roleColumnRightInfo.getColumnid());
                                ucr.setUserid(userNextRef);
                                ucr.setRef(this.userColumnRightManager.getNextId());
                                ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                                sql=new StringBuilder();
                                objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                                if(objList!=null&&sql!=null){
                                    sqlListArray.add(sql.toString());
                                    objListArray.add(objList);
                                }
                            }
                        }
                    }else if(isTea){
                        TeacherInfo t = new TeacherInfo();
                        t.setUserid(userNextRef);
                        t.setTeachername(teachername);
                        t.setTeachersex(teachersex);

                        sql = new StringBuilder();
                        objList = this.teacherManager.getSaveSql(t, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        //添加默认教师角色
                        String ruNextRef = UUID.randomUUID().toString();
                        RoleUser ru = new RoleUser();
                        ru.setRef(ruNextRef);
                        ru.getUserinfo().setRef(userNextRef);
                        ru.getRoleinfo().setRoleid(UtilTool._ROLE_TEACHER_ID);

                        sql = new StringBuilder();
                        objList = this.roleUserManager.getSaveSql(ru, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        //添加教师角色默认权限
                        RoleColumnRightInfo rc=new RoleColumnRightInfo();
                        rc.setRoleid(UtilTool._ROLE_TEACHER_ID);
                        List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);

                        if(rcList!=null&&rcList.size()>0){
                            for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                                UserColumnRightInfo ucr=new UserColumnRightInfo();
                                ucr.setColumnid(roleColumnRightInfo.getColumnid());
                                ucr.setUserid(userNextRef);
                                ucr.setRef(this.userColumnRightManager.getNextId());
                                ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                                sql=new StringBuilder();
                                objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                                if(objList!=null&&sql!=null){
                                    sqlListArray.add(sql.toString());
                                    objListArray.add(objList);
                                }
                            }
                        }
                    }

                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }





    /**
     * 乐知行用户修改接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=modifyLZXUser", method = RequestMethod.POST)
    public void modifyLZXUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");
        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON
        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                boolean isTea=false,isStu=false;
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";
                String stuname="",stusex="",stuno="";
                String teachername="",teachersex="";
                stuname=obj.containsKey("stu_name")?obj.getString("stu_name"):"";
                stusex=obj.containsKey("stu_sex")?obj.getString("stu_sex"):"";
                stuno=obj.containsKey("stu_no")?obj.getString("stu_no"):"";
                teachername=obj.containsKey("teacher_name")?obj.getString("teacher_name"):"";
                teachersex=obj.containsKey("teacher_sex")?obj.getString("teacher_sex"):"";

                if(stuname.length()>0||stusex.length()>0){
                    isStu=true;
                }else if(teachername.length()>0||teachersex.length()>0){
                    isTea=true;
                }


                UserInfo userInfo=new UserInfo();
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setSchoolid(schoolid);
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                //用户不存在
                if(userInfoList==null||userInfoList.size()<1){
                    je.setMsg("lzxuserid:"+lzxuserid+" not exists!");
                    response.getWriter().print(je.toJSON());return;
                }else{
                    if(isTea){
                        TeacherInfo t=new TeacherInfo();
                        t.setUserid(userInfoList.get(0).getRef());
                        if(teachername.length()>0)
                            t.setTeachername(teachername);
                        if(teachersex.length()>0)
                            t.setTeachersex(teachersex);
                        sql=new StringBuilder();
                        objList=this.teacherManager.getUpdateSql(t,sql);
                        if(objList!=null&&sql!=null){
                            objListArray.add(objList);
                            sqlListArray.add(sql.toString());
                        }

                    }else if(isStu){
                        StudentInfo s=new StudentInfo();
                        s.setUserref(userInfoList.get(0).getRef());
                        if(stuname.length()>0)
                            s.setStuname(stuname);
                        if(stusex.length()>0)
                            s.setStusex(stusex);
                        if(stuno.length()>0)
                            s.setStuno(stuno);
                        sql=new StringBuilder();
                        objList=this.studentManager.getUpdateSql(s,sql);
                        if(objList!=null&&sql!=null){
                            objListArray.add(objList);
                            sqlListArray.add(sql.toString());
                        }
                    }
                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * 乐知行用户添加接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=deleteLZXUser", method = RequestMethod.POST)
    public void deleteLZXUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");
        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON
        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";


                UserInfo userInfo=new UserInfo();
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setSchoolid(schoolid);
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                //用户不存在
                if(userInfoList==null||userInfoList.size()<1){
                    je.setMsg("lzxuserid:"+lzxuserid+" not exists!");
                    response.getWriter().print(je.toJSON());return;
                }else{
                    userInfo.setStateid(1);
                    userInfo.setSchoolid(schoolid);
                    sql=new StringBuilder();
                    objList=this.userManager.getUpdateSql(userInfo,sql);
                    if(sql!=null&&objList!=null){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * 乐知行用户与班级添加接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=receiveLZXClsUser", method = RequestMethod.POST)
    public void receiveLZXClsUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");
        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON
        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";
                String classid=obj.containsKey("class_id")?obj.getString("class_id"):"";
                String relationtype=obj.containsKey("relation_type")?obj.getString("relation_type"):"";
                String subjectid=obj.containsKey("subject_id")?obj.getString("subject_id"):"";
                List<SubjectInfo>subjectList=null;

                if(lzxuserid.length()<1){
                    je.setMsg("lzxuserid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(classid.length()<1){
                    je.setMsg("classid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(relationtype.length()<1){
                    je.setMsg("relationtype is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(relationtype.equals("任课老师")){
                    if(subjectid.length()<1){
                        je.setMsg("subjectid is empty!");
                        response.getWriter().print(je.toJSON());return;
                    }
                    SubjectInfo subjectInfo=new SubjectInfo();
                    subjectInfo.setLzxsubjectid(Integer.parseInt(subjectid));
                    subjectList=this.subjectManager.getList(subjectInfo,null);
                    if(subjectList==null||subjectList.size()<1){
                        je.setMsg("lzxsubject:"+subjectid+" subject not exists!");
                        response.getWriter().print(je.toJSON());return;
                    }

                }

                UserInfo userInfo=new UserInfo();
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setSchoolid(schoolid);
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                if(userInfoList==null||userInfoList.size()<1){
                    je.setMsg("lzxuserid:"+lzxuserid+" user not exists!");
                    response.getWriter().print(je.toJSON());return;
                }
                ClassInfo c=new ClassInfo();
                c.setLzxclassid(Integer.parseInt(classid));
                List<ClassInfo>clsList=this.classManager.getList(c,null);
                if(clsList==null||clsList.size()<1){
                    je.setMsg("classid:"+classid+" class not exists!");
                    response.getWriter().print(je.toJSON());return;
                }
                ClassUser sel=new ClassUser();
                sel.setUserid(userInfoList.get(0).getRef());
                sel.setClassid(clsList.get(0).getClassid());
                sel.setRelationtype(relationtype);
                if(subjectid.length()>0&&subjectList!=null&&subjectList.size()>0)
                    sel.setSubjectid(subjectList.get(0).getSubjectid());
                List<ClassUser>clsUserList=this.classUserManager.getList(sel,null);
                if(clsUserList!=null&&clsUserList.size()>0){
                    je.setMsg("clsid:"+classid+",lzxuserid:"+lzxuserid+" already exist!");
                    response.getWriter().print(je.toJSON());return;
                }

                ClassUser cu=new ClassUser();
                cu.setRef(this.classUserManager.getNextId());
                cu.setUserid(userInfoList.get(0).getRef());
                cu.setClassid(clsList.get(0).getClassid());
                cu.setRelationtype(relationtype);
                if(subjectid.length()>0&&subjectList!=null&&subjectList.size()>0)
                    cu.setSubjectid(subjectList.get(0).getSubjectid());
                sql=new StringBuilder();
                objList=this.classUserManager.getSaveSql(cu,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * 乐知行用户与班级删除接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=deleteLZXClsUser", method = RequestMethod.POST)
    public void deleteLZXClsUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");
        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON
        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";
                String classid=obj.containsKey("class_id")?obj.getString("class_id"):"";


                if(lzxuserid.length()<1){
                    je.setMsg("lzxuserid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(classid.length()<1){
                    je.setMsg("classid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }

                UserInfo userInfo=new UserInfo();
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setSchoolid(schoolid);
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                if(userInfoList==null||userInfoList.size()<1){
                    je.setMsg("lzxuserid:"+lzxuserid+" user not exists!");
                    response.getWriter().print(je.toJSON());return;
                }
                ClassInfo c=new ClassInfo();
                c.setLzxclassid(Integer.parseInt(classid));
                List<ClassInfo>clsList=this.classManager.getList(c,null);
                if(clsList==null||clsList.size()<1){
                    je.setMsg("classid:"+classid+" class not exists!");
                    response.getWriter().print(je.toJSON());return;
                }

                ClassUser cu=new ClassUser();
                cu.setUserid(userInfoList.get(0).getRef());
                cu.setClassid(clsList.get(0).getClassid());
                sql=new StringBuilder();
                objList=this.classUserManager.getDeleteSql(cu,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }





    /**
     * 乐知行用户添加接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=receiveLZXUserM", method = RequestMethod.POST)
    public void m_receiveLZXUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String dcschoolid=request.getParameter("dcschoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");

        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(dcschoolid==null||dcschoolid.trim().length()<1){
            je.setMsg("Dcschoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid+dcschoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON

        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }


        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                boolean isTea=false,isStu=false;
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";
                String username=obj.containsKey("user_name")?obj.getString("user_name"):"";
                String identityname=obj.containsKey("identity_name")?obj.getString("identity_name"):"";
                String headimage=obj.containsKey("headimgurl")?obj.getString("headimgurl"):"";
                String stuname="",stusex="",stuno="";
                String teachername="",teachersex="";

                if(lzxuserid.length()<1){
                    je.setMsg("lzxuserid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(username.length()<1){
                    je.setMsg("username is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(identityname.length()<1){
                    je.setMsg("identityname is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(identityname.length()>0&&identityname.equals("教师")){
                    isTea=true;
                    teachername=obj.containsKey("teacher_name")?obj.getString("teacher_name"):"";
                    teachersex=obj.containsKey("teacher_sex")?obj.getString("teacher_sex"):"";
                    if(teachername.length()<1){
                        je.setMsg("teachername is empty!");
                        response.getWriter().print(je.toJSON());return;
                    }
                  /*  if(teachersex.length()<1){
                        je.setMsg("teachersex is empty!");
                        response.getWriter().print(je.toJSON());return;
                    } */
                }else if(identityname.length()>0&&identityname.equals("学生")){
                    isStu=true;
                    stuname=obj.containsKey("stu_name")?obj.getString("stu_name"):"";
                    stusex=obj.containsKey("stu_sex")?obj.getString("stu_sex"):"";
                    stuno=obj.containsKey("stu_no")?obj.getString("stu_no"):"";
                    if(stuname.length()<1){
                        je.setMsg("stuname is empty!");
                        response.getWriter().print(je.toJSON());return;
                    }
                 /*   if(stusex.length()<1){
                        je.setMsg("stusex is empty!");
                        response.getWriter().print(je.toJSON());return;
                    }*/
                }



                UserInfo userInfo=new UserInfo();
                userInfo.setUsername(username);
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setDcschoolid(Integer.parseInt(dcschoolid));
                userInfo.setSchoolid(schoolid);
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                //已存在
                if(userInfoList!=null&&userInfoList.size()>0){
                    if(userInfoList.get(0).getStateid().toString().equals("1")){
                        UserInfo upd=new UserInfo();
                        upd.setUserid(userInfoList.get(0).getUserid());
                        upd.setStateid(0);
                        sql=new StringBuilder();
                        objList=this.userManager.getUpdateSql(upd,sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }else{
                        je.setMsg("SchoolId:"+schoolid+" username:"+username+" lzxuserid:"+lzxuserid+" already exists!");
                        response.getWriter().print(je.toJSON());return;
                    }
                }else{
                    //添加用户
                    String userNextRef = UUID.randomUUID().toString();
                    userInfo.setRef(userNextRef);
                    userInfo.setPassword("111111");
                    userInfo.setStateid(0);
                    userInfo.setSchoolid(schoolid);
                    userInfo.setDcschoolid(Integer.parseInt(dcschoolid));
                    if(headimage!=null&&headimage.length()>0)
                        userInfo.setHeadimage(headimage);
                    sql = new StringBuilder();
                    objList = this.userManager.getSaveSql(userInfo, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }

                    //添加用户与身份关联信息
                    String identityNextRef = UUID.randomUUID().toString();
                    UserIdentityInfo ui = new UserIdentityInfo();
                    ui.setRef(identityNextRef);
                    ui.getUserinfo().setRef(userNextRef);
                    if(isTea)
                        ui.setIdentityname("教职工");
                    else if(isStu)
                        ui.setIdentityname("学生");
                    sql = new StringBuilder();
                    objList = this.userIdentityManager.getSaveSql(ui, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                    if(!isStu&&!isTea){
                        je.setMsg("Invalid identity!");
                        response.getWriter().print(je.toJSON());
                        return;
                    }


                    if(isStu){
                        // 添加学生
                        StudentInfo s = new StudentInfo();
                        s.getUserinfo().setRef(userNextRef);
                        s.setStuname(stuname);
                        s.setStusex(stusex);
                        s.setStuno(stuno);
                        sql = new StringBuilder();
                        objList = this.studentManager.getSaveSql(s, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        //添加默认学生角色
                        String ruNextRef = UUID.randomUUID().toString();
                        RoleUser ru = new RoleUser();
                        ru.setRef(ruNextRef);
                        ru.getUserinfo().setRef(userNextRef);
                        ru.getRoleinfo().setRoleid(UtilTool._ROLE_STU_ID);

                        sql = new StringBuilder();
                        objList = this.roleUserManager.getSaveSql(ru, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        //添加学生角色默认权限
                        RoleColumnRightInfo rc=new RoleColumnRightInfo();
                        rc.setRoleid(UtilTool._ROLE_STU_ID);
                        List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);
                        if(rcList!=null&&rcList.size()>0){
                            for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                                UserColumnRightInfo ucr=new UserColumnRightInfo();
                                ucr.setColumnid(roleColumnRightInfo.getColumnid());
                                ucr.setUserid(userNextRef);
                                ucr.setRef(this.userColumnRightManager.getNextId());
                                ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                                sql=new StringBuilder();
                                objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                                if(objList!=null&&sql!=null){
                                    sqlListArray.add(sql.toString());
                                    objListArray.add(objList);
                                }
                            }
                        }
                    }else if(isTea){
                        TeacherInfo t = new TeacherInfo();
                        t.setUserid(userNextRef);
                        t.setTeachername(teachername);
                        t.setTeachersex(teachersex);

                        sql = new StringBuilder();
                        objList = this.teacherManager.getSaveSql(t, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        //添加默认教师角色
                        String ruNextRef = UUID.randomUUID().toString();
                        RoleUser ru = new RoleUser();
                        ru.setRef(ruNextRef);
                        ru.getUserinfo().setRef(userNextRef);
                        ru.getRoleinfo().setRoleid(UtilTool._ROLE_TEACHER_ID);

                        sql = new StringBuilder();
                        objList = this.roleUserManager.getSaveSql(ru, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                        //添加教师角色默认权限
                        RoleColumnRightInfo rc=new RoleColumnRightInfo();
                        rc.setRoleid(UtilTool._ROLE_TEACHER_ID);
                        List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);

                        if(rcList!=null&&rcList.size()>0){
                            for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                                UserColumnRightInfo ucr=new UserColumnRightInfo();
                                ucr.setColumnid(roleColumnRightInfo.getColumnid());
                                ucr.setUserid(userNextRef);
                                ucr.setRef(this.userColumnRightManager.getNextId());
                                ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                                sql=new StringBuilder();
                                objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                                if(objList!=null&&sql!=null){
                                    sqlListArray.add(sql.toString());
                                    objListArray.add(objList);
                                }
                            }
                        }
                    }

                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }



    /**
     * 乐知行用户修改接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=modifyLZXUserM", method = RequestMethod.POST)
    public void m_modifyLZXUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String dcschoolid=request.getParameter("dcschoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");
        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(dcschoolid==null||dcschoolid.trim().length()<1){
            je.setMsg("Dcschoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid+dcschoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON
        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                boolean isTea=false,isStu=false;
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";
                String headimage=obj.containsKey("headimgurl")?obj.getString("headimgurl"):"";
                String stuname="",stusex="",stuno="";
                String teachername="",teachersex="";
                stuname=obj.containsKey("stu_name")?obj.getString("stu_name"):"";
                stusex=obj.containsKey("stu_sex")?obj.getString("stu_sex"):"";
                stuno=obj.containsKey("stu_no")?obj.getString("stu_no"):"";
                teachername=obj.containsKey("teacher_name")?obj.getString("teacher_name"):"";
                teachersex=obj.containsKey("teacher_sex")?obj.getString("teacher_sex"):"";

                if(stuname.length()>0||stusex.length()>0){
                    isStu=true;
                }else if(teachername.length()>0||teachersex.length()>0){
                    isTea=true;
                }


                UserInfo userInfo=new UserInfo();
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setSchoolid(schoolid);
                userInfo.setDcschoolid(Integer.parseInt(dcschoolid));
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                //用户不存在
                if(userInfoList==null||userInfoList.size()<1){
                    je.setMsg("LzxSchoolId:"+schoolid+" LzxUserId:"+lzxuserid+" not exists!");
                    response.getWriter().print(je.toJSON());return;
                }else{
                    //修改用户头像
                    if(headimage!=null&&headimage.trim().length()>0){
                        UserInfo update=new UserInfo();
                        update.setHeadimage(headimage);
                        update.setUserid(userInfoList.get(0).getUserid());
                        sql=new StringBuilder();
                        objList=this.userManager.getUpdateSql(update,sql);
                        if(objList!=null&&sql!=null){
                            objListArray.add(objList);
                            sqlListArray.add(sql.toString());
                        }
                    }


                    if(isTea){
                        TeacherInfo t=new TeacherInfo();
                        t.setUserid(userInfoList.get(0).getRef());
                        if(teachername.length()>0)
                            t.setTeachername(teachername);
                        if(teachersex.length()>0)
                            t.setTeachersex(teachersex);
                        sql=new StringBuilder();
                        objList=this.teacherManager.getUpdateSql(t,sql);
                        if(objList!=null&&sql!=null){
                            objListArray.add(objList);
                            sqlListArray.add(sql.toString());
                        }

                    }else if(isStu){
                        StudentInfo s=new StudentInfo();
                        s.setUserref(userInfoList.get(0).getRef());
                        if(stuname.length()>0)
                            s.setStuname(stuname);
                        if(stusex.length()>0)
                            s.setStusex(stusex);
                        if(stuno.length()>0)
                            s.setStuno(stuno);
                        sql=new StringBuilder();
                        objList=this.studentManager.getUpdateSql(s,sql);
                        if(objList!=null&&sql!=null){
                            objListArray.add(objList);
                            sqlListArray.add(sql.toString());
                        }
                    }
                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * 乐知行用户添加接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=deleteLZXUserM", method = RequestMethod.POST)
    public void m_deleteLZXUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String dcschoolid=request.getParameter("dcschoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");
        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(dcschoolid==null||dcschoolid.trim().length()<1){
            je.setMsg("Dcschoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid+dcschoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON
        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";


                UserInfo userInfo=new UserInfo();
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setSchoolid(schoolid);
                userInfo.setDcschoolid(Integer.parseInt(dcschoolid));
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                //用户不存在
                if(userInfoList==null||userInfoList.size()<1){
                    je.setMsg("LzxSchoolId:"+schoolid+" LzxUserId:"+lzxuserid+" not exists!");
                    response.getWriter().print(je.toJSON());return;
                }else{
                    userInfo.setStateid(1);
                    userInfo.setSchoolid(schoolid);
                    sql=new StringBuilder();
                    objList=this.userManager.getUpdateSql(userInfo,sql);
                    if(sql!=null&&objList!=null){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * 乐知行用户与班级添加接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=receiveLZXClsUserM", method = RequestMethod.POST)
    public void m_receiveLZXClsUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String dcschoolid=request.getParameter("dcschoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");
        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(dcschoolid==null||dcschoolid.trim().length()<1){
            je.setMsg("Dcschoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid+dcschoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON
        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";
                String classid=obj.containsKey("class_id")?obj.getString("class_id"):"";
                String relationtype=obj.containsKey("relation_type")?obj.getString("relation_type"):"";
                String subjectid=obj.containsKey("subject_id")?obj.getString("subject_id"):"";
                List<SubjectInfo>subjectList=null;

                if(lzxuserid.length()<1){
                    je.setMsg("lzxuserid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(classid.length()<1){
                    je.setMsg("classid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(relationtype.length()<1){
                    je.setMsg("relationtype is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(relationtype.equals("任课老师")){
                    if(subjectid.length()<1){
                        je.setMsg("subjectid is empty!");
                        response.getWriter().print(je.toJSON());return;
                    }
                    SubjectInfo subjectInfo=new SubjectInfo();
                    subjectInfo.setLzxsubjectid(Integer.parseInt(subjectid));
                    subjectList=this.subjectManager.getList(subjectInfo,null);
                    if(subjectList==null||subjectList.size()<1){
                        je.setMsg("lzxsubject:"+subjectid+" subject not exists!");
                        response.getWriter().print(je.toJSON());return;
                    }

                }

                UserInfo userInfo=new UserInfo();
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setSchoolid(schoolid);
                userInfo.setDcschoolid(Integer.parseInt(dcschoolid));
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                if(userInfoList==null||userInfoList.size()<1){
                    je.setMsg("lzxuserid:"+lzxuserid+" user not exists!");
                    response.getWriter().print(je.toJSON());return;
                }
                ClassInfo c=new ClassInfo();
                c.setLzxclassid(Integer.parseInt(classid));
                c.setDcschoolid(Integer.parseInt(dcschoolid));
                List<ClassInfo>clsList=this.classManager.getList(c,null);
                if(clsList==null||clsList.size()<1){
                    je.setMsg("classid:"+classid+" class not exists!");
                    response.getWriter().print(je.toJSON());return;
                }
                ClassUser sel=new ClassUser();
                sel.setUserid(userInfoList.get(0).getRef());
                sel.setClassid(clsList.get(0).getClassid());
                sel.setRelationtype(relationtype);
                if(subjectid.length()>0&&subjectList!=null&&subjectList.size()>0)
                    sel.setSubjectid(subjectList.get(0).getSubjectid());
                List<ClassUser>clsUserList=this.classUserManager.getList(sel,null);
                if(clsUserList!=null&&clsUserList.size()>0){
                    je.setMsg("clsid:"+classid+",lzxuserid:"+lzxuserid+" already exist!");
                    response.getWriter().print(je.toJSON());return;
                }

                ClassUser cu=new ClassUser();
                cu.setRef(this.classUserManager.getNextId());
                cu.setUserid(userInfoList.get(0).getRef());
                cu.setClassid(clsList.get(0).getClassid());
                cu.setRelationtype(relationtype);
                if(subjectid.length()>0&&subjectList!=null&&subjectList.size()>0)
                    cu.setSubjectid(subjectList.get(0).getSubjectid());
                sql=new StringBuilder();
                objList=this.classUserManager.getSaveSql(cu,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * 乐知行用户与班级删除接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=deleteLZXClsUserM", method = RequestMethod.POST)
    public void m_deleteLZXClsUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String dcschoolid=request.getParameter("dcschoolid");
        String timestamp=request.getParameter("timestamp");
        String signature=request.getParameter("signature");
        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(dcschoolid==null||dcschoolid.trim().length()<1){
            je.setMsg("Dcschoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(timestamp==null||timestamp.trim().length()<1){
            je.setMsg("Timestamp is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(signature==null||signature.trim().length()<1){
            je.setMsg("Signature is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        String validateStr=MD5_NEW.getMD5ResultCode(timestamp+schoolid+dcschoolid);
        if(!signature.equals(validateStr)){
            je.setMsg("Signature invalid!");
            response.getWriter().print(je.toJSON());
            return;
        }
        String message=request.getParameter("message");
        if(message==null||message.trim().length()<1){
            je.setMsg("message is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        //转换成JSON
        System.out.println(message);
        JSONArray jsonArray;
        try{
            jsonArray= JSONArray.fromObject(java.net.URLDecoder.decode(message,"UTF-8"));
        }catch (Exception e){
            je.setMsg(e.getMessage());
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        if(jsonArray!=null&&jsonArray.size()>0){
            Iterator iterator=jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject obj=(JSONObject)iterator.next();
                String lzxuserid=obj.containsKey("lzx_user_id")?obj.getString("lzx_user_id"):"";
                String classid=obj.containsKey("class_id")?obj.getString("class_id"):"";


                if(lzxuserid.length()<1){
                    je.setMsg("lzxuserid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }
                if(classid.length()<1){
                    je.setMsg("classid is empty!");
                    response.getWriter().print(je.toJSON());return;
                }

                UserInfo userInfo=new UserInfo();
                userInfo.setLzxuserid(lzxuserid);
                userInfo.setSchoolid(schoolid);
                userInfo.setDcschoolid(Integer.parseInt(dcschoolid));
                List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
                if(userInfoList==null||userInfoList.size()<1){
                    je.setMsg("lzxuserid:"+lzxuserid+" user not exists!");
                    response.getWriter().print(je.toJSON());return;
                }
                ClassInfo c=new ClassInfo();
                c.setLzxclassid(Integer.parseInt(classid));
                c.setDcschoolid(Integer.parseInt(dcschoolid));
                List<ClassInfo>clsList=this.classManager.getList(c,null);
                if(clsList==null||clsList.size()<1){
                    je.setMsg("classid:"+classid+" class not exists!");
                    response.getWriter().print(je.toJSON());return;
                }

                ClassUser cu=new ClassUser();
                cu.setUserid(userInfoList.get(0).getRef());
                cu.setClassid(clsList.get(0).getClassid());
                sql=new StringBuilder();
                objList=this.classUserManager.getDeleteSql(cu,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }else {
            je.setMsg("Jsondata empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag)
                je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(je.toJSON());
    }






    /**
     * 外部接口登陆(必要参数 user_name,password)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=foreighLoginM",method={RequestMethod.GET,RequestMethod.POST})
    public void foreighLoginM(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String logintime=request.getParameter("login_time");
        JsonEntity jsonEntity=new JsonEntity();
        if(logintime==null||logintime.trim().length()<1||!UtilTool.isNumber(logintime)){
            jsonEntity.setMsg("异常错误，登陆时间戳参数缺少!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        String dcschoolid=request.getParameter("dcschoolid");
        if(dcschoolid==null||dcschoolid.trim().length()<1){
            jsonEntity.setMsg("异常错误，网校分校ID为空!!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        UserInfo loginUsr=new UserInfo();
        String schoolid=request.getParameter("lzx_school_id");
        if(schoolid==null||schoolid.trim().length()<1){//||!UtilTool.isNumber(schoolid)
            jsonEntity.setMsg("异常错误，分校ID为空!!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        String lzx_userid=request.getParameter("lzx_userid");
        String username=request.getParameter("username");
        String pass=request.getParameter("password");
        String loginCode=request.getParameter("login_code");
        if(loginCode==null||loginCode.trim().length()<1){
            jsonEntity.setMsg("异常错误，登陆码参数缺少!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        if(username==null||username.trim().length()<1){
            if(lzx_userid==null||lzx_userid.trim().length()<1){
                jsonEntity.setMsg("异常错误，登陆用户ID戳参数缺少!");
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
            }
            loginUsr.setLzxuserid(lzx_userid);
        }else{
            if(pass==null||pass.trim().length()<1){
                jsonEntity.setMsg("异常错误，登陆密码为空!");
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
            }
            loginUsr.setUsername(username);
            loginUsr.setPassword(pass);
        }
        loginUsr.setDcschoolid(Integer.parseInt(dcschoolid));
        String flag_id=request.getParameter("flag_id");
        if(flag_id==null||flag_id.trim().length()<1||!UtilTool.isNumber(flag_id.trim())){
            jsonEntity.setMsg("异常错误，功能ID为空，无法进行跳转!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        //验证是否在三分钟内
        Long logint=Long.parseLong(logintime);
        Long nt=new Date().getTime();
        double d=(nt-logint)/(1000*60);
        if(d>30){//大于三分钟
            //jsonEntity.setMsg("异常错误，响应超时!接口三分钟内有效!");
            //response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        String loginKey=request.getParameter("login_key");
        //验证key
        String md5key=logintime+schoolid;
        if(loginUsr.getLzxuserid()!=null&&loginUsr.getLzxuserid().trim().length()>0){
            md5key+=loginUsr.getLzxuserid();
        }else{
            md5key+=loginUsr.getUsername()+loginUsr.getPassword();
        }
        md5key+=dcschoolid; //添加dc_school_id
        md5key+=flag_id+loginCode+logintime;
        md5key=MD5_NEW.getMD5ResultCode(md5key);//生成md5加密
        if(!md5key.trim().equals(loginKey.trim())){//如果不一致，则说明非法登陆
            jsonEntity.setMsg("异常错误，非法登陆!!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        List<UserInfo> usrList=this.userManager.getList(loginUsr,null);
        if(usrList==null||usrList.size()<1){
            jsonEntity.setMsg("没有发现该用户!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        loginUsr.setUsername(usrList.get(0).getUsername());
        loginUsr.setPassword(usrList.get(0).getPassword());
        jsonEntity=this.loginBase(loginUsr,request,response);
        if(!jsonEntity.getType().trim().equals("success")){
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }

        String targetUrl=null;
        String foreignFlagStr=UtilTool.utilproperty.getProperty("FOREIGN_FLAG_ID");//flag_id|url,flag_id|url
        String[] foreighFlagArray=foreignFlagStr.split(",");
        if(foreighFlagArray.length>0){
            for (String ft:foreighFlagArray){
                String[] fttmp=ft.split("\\|");
                if(fttmp!=null&&fttmp.length>1){
                    if(fttmp[0].trim().equals(flag_id.trim())){
                        targetUrl=fttmp[1];break;
                    }
                }
            }
        }
        if(targetUrl==null){
            jsonEntity.setMsg("异常错误，网校端配置错误!请联系管理人员");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        request.getSession().setAttribute("fromType","lzx");
        response.sendRedirect(targetUrl);
    }

    /**
     * 外部接口登陆(必要参数 user_name,password)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=foreighLogin",method={RequestMethod.GET,RequestMethod.POST})
    public void foreighLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String logintime=request.getParameter("login_time");
        JsonEntity jsonEntity=new JsonEntity();
        if(logintime==null||logintime.trim().length()<1||!UtilTool.isNumber(logintime)){
            jsonEntity.setMsg("异常错误，登陆时间戳参数缺少!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        UserInfo loginUsr=new UserInfo();
        String schoolid=request.getParameter("lzx_school_id");
        if(schoolid==null||schoolid.trim().length()<1){//||!UtilTool.isNumber(schoolid)
            jsonEntity.setMsg("异常错误，分校ID为空!!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        String lzx_userid=request.getParameter("lzx_userid");
        String username=request.getParameter("username");
        String pass=request.getParameter("password");
        String loginCode=request.getParameter("login_code");
        if(loginCode==null||loginCode.trim().length()<1){
            jsonEntity.setMsg("异常错误，登陆码参数缺少!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        if(username==null||username.trim().length()<1){
            if(lzx_userid==null||lzx_userid.trim().length()<1){
                jsonEntity.setMsg("异常错误，登陆用户ID戳参数缺少!");
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
            }
            loginUsr.setLzxuserid(lzx_userid);
        }else{
            if(pass==null||pass.trim().length()<1){
                jsonEntity.setMsg("异常错误，登陆密码为空!");
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
            }
            loginUsr.setUsername(username);
            loginUsr.setPassword(pass);
        }
        String flag_id=request.getParameter("flag_id");
        if(flag_id==null||flag_id.trim().length()<1||!UtilTool.isNumber(flag_id.trim())){
            jsonEntity.setMsg("异常错误，功能ID为空，无法进行跳转!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        //验证是否在三分钟内
        Long logint=Long.parseLong(logintime);
        Long nt=new Date().getTime();
        double d=(nt-logint)/(1000*60);
        if(d>30){//大于三分钟
            //jsonEntity.setMsg("异常错误，响应超时!接口三分钟内有效!");
            //response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        String loginKey=request.getParameter("login_key");
        //验证key
        String md5key=logintime+schoolid;
        if(loginUsr.getLzxuserid()!=null&&loginUsr.getLzxuserid().trim().length()>0){
            md5key+=loginUsr.getLzxuserid();
        }else{
            md5key+=loginUsr.getUsername()+loginUsr.getPassword();
        }
        md5key+=flag_id+loginCode+logintime;
        md5key=MD5_NEW.getMD5ResultCode(md5key);//生成md5加密
        if(!md5key.trim().equals(loginKey.trim())){//如果不一致，则说明非法登陆
            jsonEntity.setMsg("异常错误，非法登陆!!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        List<UserInfo> usrList=this.userManager.getList(loginUsr,null);
        if(usrList==null||usrList.size()<1){
            jsonEntity.setMsg("没有发现该用户!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        loginUsr.setUsername(usrList.get(0).getUsername());
        loginUsr.setPassword(usrList.get(0).getPassword());
        jsonEntity=this.loginBase(loginUsr,request,response);
        if(!jsonEntity.getType().trim().equals("success")){
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }

        String targetUrl=null;
        String foreignFlagStr=UtilTool.utilproperty.getProperty("FOREIGN_FLAG_ID");//flag_id|url,flag_id|url
        String[] foreighFlagArray=foreignFlagStr.split(",");
        if(foreighFlagArray.length>0){
            for (String ft:foreighFlagArray){
                String[] fttmp=ft.split("\\|");
                if(fttmp!=null&&fttmp.length>1){
                    if(fttmp[0].trim().equals(flag_id.trim())){
                        targetUrl=fttmp[1];break;
                    }
                }
            }
        }
        if(targetUrl==null){
            jsonEntity.setMsg("异常错误，网校端配置错误!请联系管理人员");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return;
        }
        request.getSession().setAttribute("fromType","lzx");
        response.sendRedirect(targetUrl);
    }





    /**
     * 添加用户时，传入Ett
     * @param userNextRef
     * @return
     */
    private boolean addToEttUser(String userNextRef){
        //向网校添加用户信息
        UserInfo tmpUser=new UserInfo();
        tmpUser.setRef(userNextRef);
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        List<UserInfo> tmpUList=this.userManager.getList(tmpUser,presult);
        if(tmpUList!=null&&tmpUList.size()>0){
            //调用用户接口
            if(EttInterfaceUserUtil.addUserBase(tmpUList)){
                System.out.println("用户信息同步至网校成功!");
            }else{
                System.out.println("用户信息同步至网校失败!");
                return false;
            }
            if(updateToEttClassUser(userNextRef,tmpUList.get(0).getDcschoolid()))
                System.out.println("classUser同步至网校成功!");
            else
                System.out.println("classUser同步至网校失败!");

        }
        return true;
    }
    /**
     * 同步ettClassUser
     * @param List<String>
     * @param dcschoolId
     * @return
     */
    private boolean updateToEttClassUser(List<Integer> xgClsId,Integer dcschoolId){
        if(xgClsId==null||xgClsId.size()<1)return false;
        //向网校添加班级数据
        for (Integer clsStr:xgClsId){
            if(clsStr==null)continue;
            ClassUser cu=new ClassUser();
            cu.setClassid(clsStr);
            List<ClassUser> cuTmpList=this.classUserManager.getList(cu,null);
            if(cuTmpList!=null&&cuTmpList.size()>0){
                // 必带 userId，userType,subjectId 的三个key
                List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
                for (ClassUser cuTmpe:cuTmpList){
                    if(cuTmpe!=null){
                        Map<String,Object> tmpMap=new HashMap<String, Object>();
                        tmpMap.put("userId",cuTmpe.getUid());
                        Integer userType=3;
                        if(cuTmpe.getRelationtype()!=null){
                            if(cuTmpe.getRelationtype().trim().equals("任课老师"))
                                userType=2;
                            else if(cuTmpe.getRelationtype().trim().equals("班主任"))
                                userType=1;
                        }
                        tmpMap.put("userType",userType);
                        tmpMap.put("subjectId",cuTmpe.getSubjectid()==null?-1:cuTmpe.getSubjectid());
                        mapList.add(tmpMap);
                    }
                }
                if(!EttInterfaceUserUtil.OperateClassUser(mapList, cu.getClassid(),dcschoolId)){
                    System.out.println("classUser同步至网校失败!");
                    return false;
                } else
                    System.out.println("classUser同步至网校成功!");
            }
        }

        return true;
    }
    /**
     * 同步ettClassUser
     * @param userNextRef
     * @return
     */
    private boolean updateToEttClassUser(String userNextRef,Integer dcschoolId){
        //向网校添加班级数据
        ClassUser cu=new ClassUser();
        cu.setUserid(userNextRef);
        List<ClassUser> cuList=this.classUserManager.getList(cu,null);
        if(cuList!=null&&cuList.size()>0){
            for (ClassUser cuTmp:cuList){
                cu=new ClassUser();
                cu.setClassid(cuTmp.getClassid());
                List<ClassUser> cuTmpList=this.classUserManager.getList(cu,null);
                if(cuTmpList!=null&&cuTmpList.size()>0){
                    // 必带 userId，userType,subjectId 的三个key
                    List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
                    for (ClassUser cuTmpe:cuTmpList){
                        if(cuTmpe!=null){
                            Map<String,Object> tmpMap=new HashMap<String, Object>();
                            tmpMap.put("userId",cuTmpe.getUid());
                            Integer userType=3;
                            if(cuTmpe.getRelationtype()!=null){
                                if(cuTmpe.getRelationtype().trim().equals("任课老师"))
                                    userType=2;
                                else if(cuTmpe.getRelationtype().trim().equals("班主任"))
                                    userType=1;
                            }
                            tmpMap.put("userType",userType);
                            tmpMap.put("subjectId",cuTmpe.getSubjectid()==null?-1:cuTmpe.getSubjectid());
                            mapList.add(tmpMap);
                        }
                    }
                    if(!EttInterfaceUserUtil.OperateClassUser(mapList, cuTmp.getClassid(),dcschoolId)){
                        System.out.println("classUser同步至网校失败!");
                        return false;
                    } else
                        System.out.println("classUser同步至网校成功!");
                }
            }
        }
        return true;
    }

    /**
     * 云端分校管理员添加接口
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(params = "m=addSchoolAdmin", method = RequestMethod.POST)
    public void addSchoolAdmin(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        JSONObject jo = new JSONObject();
        String schoolid=request.getParameter("schoolid");
        String adminname=request.getParameter("adminname");
        String pwd=request.getParameter("pwd");

        if(schoolid==null||schoolid.trim().length()<1){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        UserInfo userInfo=new UserInfo();
        userInfo.setUsername(adminname);
        userInfo.setDcschoolid(Integer.parseInt(schoolid));
        List<UserInfo>userInfoList=this.userManager.getList(userInfo,null);
        //已存在
        if(userInfoList!=null&&userInfoList.size()>0){
            UserInfo upd=new UserInfo();
            upd.setUserid(userInfoList.get(0).getUserid());
            upd.setPassword(pwd);
            sql=new StringBuilder();
            objList=this.userManager.getUpdateSql(upd,sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }else{
            //添加用户
            String userNextRef = UUID.randomUUID().toString();
            userInfo.setRef(userNextRef);
            userInfo.setPassword(pwd);
            userInfo.setStateid(0);
            userInfo.setSchoolid(schoolid);
            userInfo.setUsername(adminname);
            sql = new StringBuilder();
            objList = this.userManager.getSaveSql(userInfo, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            //添加用户与角色关系
            RoleUser ru = new RoleUser();
            ru.setUserid(userNextRef);
            ru.setRoleid(4);
            ru.setRef(UUID.randomUUID().toString());
            sql = new StringBuilder();
            objList = this.roleUserManager.getSaveSql(ru,sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            //添加用户与身份关联信息
            String identityNextRef = UUID.randomUUID().toString();
            UserIdentityInfo ui = new UserIdentityInfo();
            ui.setRef(identityNextRef);
            ui.getUserinfo().setRef(userNextRef);
            ui.setIdentityname("教职工");
            sql = new StringBuilder();
            objList = this.userIdentityManager.getSaveSql(ui, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

        }
        if(sqlListArray.size()>0&&objListArray.size()>0&&sqlListArray.size()==objListArray.size()){
            boolean flag=this.userManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag){
                jo.put("result",1);
            }
        }else
            jo.put("result",0);
        response.getWriter().print(jo.toString());
    }

    /**
     * 验证WebIm权限
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=loadWebImRight",method=RequestMethod.POST)
    public void loadWebImRight(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        //查询用户
        UserInfo user=new UserInfo();
        user.setUserid(this.logined(request).getUserid());
        List<UserInfo> uList=this.userManager.getList(user,null);
        if(uList!=null&&uList.size()>0&&uList.get(0).getEttuserid()!=null){
            this.logined(request).setEttuserid(uList.get(0).getEttuserid());
            jsonEntity.getObjList().add(0);
        }else
            jsonEntity.getObjList().add(1);
        jsonEntity.setType("success");
        response.getWriter().println(jsonEntity.toJSON());
    }

}


/**
 * 用户Control的工具类
 */
class UserTool{
    /**
     *后台调用接口
     * @param urlstr
     * @return
     */
    public static String sendPostURL(String urlstr,String params) throws UnsupportedEncodingException {
        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(urlstr);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
            if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.length()));
            httpConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
			/*
			 * PrintWriter printWriter = new
			 * PrintWriter(httpConnection.getOutputStream());
			 * printWriter.print(parameters); printWriter.close();
			 */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    httpConnection.getOutputStream(), "8859_1");
            if(params!=null)
                outputStreamWriter.write(params);

            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("异常错误!TOTALSCHOOL未响应!");
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine);
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("异常错误!");
                e.printStackTrace();;
                return null;
            }
        }else if(code==404){
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return null;
        }else if(code==500){
            System.out.println("异常错误!500错误，请联系管理人员!");
            return null;
        }
        String returnVal= new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");//new String(stringBuffer.toString().getBytes("GBK"),"UTF-8");
        return returnVal;

    }

    /**
     *
     * 组织分校MD5参数
     * @param uid   产品分校用户ID
     * @param gradeidList  年级标识(List<Integer>)
     * @param realname 真实姓名
     * @param usertype 用户身份（1=教师，3=学生） 在此处传3
     * @param sex       性别 （1=男，2=女）
     * @param  email 邮箱(没有可以不传)
     * @param schoolid 分校ID
     * @param time  当前时间。Java当前时间，长整型。
     * @param key   组织码(一般为固定码)
     * @return  s
     */
    public static String zuzhiStudentSParameter(String uid,List<Integer> gradeidList,String realname,String usertype,String sex,String email,String schoolid,String isvip,String time,String key) throws Exception{
        // 加密
        StringBuilder md5Builder = new StringBuilder(time.toString());
        if (gradeidList != null && gradeidList.size() > 0) {
            StringBuilder gradeidBuilder = new StringBuilder();
            for (Integer gl : gradeidList) {
                gradeidBuilder.append(gl);
                if (gl != gradeidList.get(gradeidList.size() - 1))
                    gradeidBuilder.append(",");
            }
            if (gradeidBuilder.toString().trim().length() > 0)
                md5Builder.append(gradeidBuilder);
        }
        md5Builder.append(uid);
        md5Builder.append(realname);
        md5Builder.append(usertype);

        md5Builder.append(sex);
        if(email==null)
            email="";
        md5Builder.append(email);
        // 为了应用性，可以测试以及兼容性，不带此参数
        // if(usertype==3){
        // md5Builder.append(isLearnGuide);
        // }
        md5Builder.append(schoolid);
        md5Builder.append(time);
        if(isvip!=null&&isvip.length()>0)
            md5Builder.append(isvip);

        md5Builder.append(key);

        return MD5_NEW.getMD5Result(md5Builder.toString());
    }

    /**
     * 组织分校MD5参数
     * @param uid   产品分校用户ID
     * @param gradeidList  年级标识(List<Integer>)
     * @param realname 真实姓名
     * @param usertype 用户身份（1=教师，3=学生） 在此处传3
     * @param sex       性别 （1=男，2=女）
     * @param  email 邮箱(没有可以不传)
     * @param schoolid 分校ID
     * @param servertime 服务截止时间的Long类型 Date.getTime()获取
     * @param time  当前时间。Java当前时间，长整型。
     * @param key   组织码(一般为固定码)
     * @return  s
     */
    public static String zuzhiTeacherSParameter(String uid,List<Integer> gradeidList,String realname,String usertype,String sex,String email,String schoolid,Long servertime,String isVip,String time,String key) throws Exception{
        // 加密
        StringBuilder md5Builder = new StringBuilder(time.toString());
        if (gradeidList != null && gradeidList.size() > 0) {
            StringBuilder gradeidBuilder = new StringBuilder();
            for (Integer gl : gradeidList) {
                gradeidBuilder.append(gl);
                if (gl != gradeidList.get(gradeidList.size() - 1))
                    gradeidBuilder.append(",");
            }
            if (gradeidBuilder.toString().trim().length() > 0)
                md5Builder.append(gradeidBuilder);
        }
        md5Builder.append(uid);
        md5Builder.append(realname);
        md5Builder.append(usertype);

        md5Builder.append(sex);
        if(email==null)
            email="";
        md5Builder.append(email);

        md5Builder.append(schoolid);
        md5Builder.append(servertime.toString());
        if(isVip!=null)
            md5Builder.append(isVip);
        md5Builder.append(time);
        md5Builder.append(key);
        return MD5_NEW.getMD5Result(md5Builder.toString());
    }

    /**
     * 组织输出的html
     * @param postUrl 输出后页面提交的地址
     * @param paramMap  提交的参数;
     * @return
     */
    public static String getOutputHTML(String postUrl,Map<String,Object> paramMap){
        StringBuilder outputBuilder=new StringBuilder();
        outputBuilder.append("<html>");
        outputBuilder.append("<head>");
        outputBuilder.append("<script type='text/javascript'>");
        outputBuilder.append("window.onload=function(){");
        outputBuilder.append("document.forms[0].action='"+postUrl+"';");
        outputBuilder.append("document.forms[0].method='POST';");
        outputBuilder.append("document.forms[0].submit();");
        outputBuilder.append("}");
        outputBuilder.append("</script>");
        outputBuilder.append("</head>");
        outputBuilder.append("<body>");
        outputBuilder.append("<form>");
        if(paramMap!=null&&paramMap.size()>0){
            Set<String> keySetArray=paramMap.keySet();
            for (String pkey:keySetArray){
                //组织页面并输出
                outputBuilder.append("<input type='hidden' value='"+paramMap.get(pkey)+"' name='"+pkey+"'/>");
            }
        }
        outputBuilder.append("</form></body>");
        outputBuilder.append("</html>");
        return outputBuilder.toString();
    }


}


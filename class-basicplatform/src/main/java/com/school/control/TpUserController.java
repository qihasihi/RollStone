package com.school.control;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.*;
import com.school.manager.SchoolManager;
import com.school.manager.inter.ISchoolManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Controller
@RequestMapping(value = "/tpuser")
public class TpUserController extends UserController {

    private ISchoolManager schoolManager;

    public TpUserController(){
        this.schoolManager=this.getManager(SchoolManager.class);
    }
    /**
     * 网校教务进入班级管理
     * @param request
     * @param response
     * @param mp
     * @return
     * @userType 1:教师 2:教务 3:学生
     * @throws Exception
     */
    @RequestMapping(params = "m=toJWIndex", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView toUserList(HttpServletRequest request,
                                   HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolId");
        String schoolname=request.getParameter("schoolName");
        String userid=request.getParameter("jId");
        if(schoolid==null||schoolid.trim().length()<1||!UtilTool.isNumber(schoolid)){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return null;
        }
        if(schoolname==null||schoolname.trim().length()<1){
            je.setMsg("Schoolname is empty!");
            response.getWriter().print(je.toJSON());
            return null;
        }
        if(userid==null||userid.trim().length()<1||!UtilTool.isNumber(userid)){
            je.setMsg("Userid is empty!");
            response.getWriter().print(je.toJSON());
            return null;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        //验证添加分校信息
        String url="http://localhost:8080/totalSchool/";//UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
        String totalSchoolUrl=url+"franchisedSchool?jwValidateSchool";
        String totalParams="schoolid="+schoolid+"&schoolname="+java.net.URLEncoder.encode(schoolname,"UTF-8");
        if(!sendValidateUserInfoTotalSchool(totalSchoolUrl,totalParams)){
            je.setMsg("Operate SchoolInfo Error...");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }



        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(schoolid));
        u.setEttuserid(Integer.parseInt(userid));
        List<UserInfo>userList=this.userManager.getList(u, null);
        if(userList==null||userList.size()<1){
            //添加用户
            String userNextRef = UUID.randomUUID().toString();
            u.setRef(userNextRef);
            u.setPassword(schoolid+userid);
            u.setUsername("教务"+schoolid+userid);
            u.setStateid(0);
            u.setDcschoolid(Integer.parseInt(schoolid));
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
            ui.setIdentityname("教职工");
            sql = new StringBuilder();
            objList = this.userIdentityManager.getSaveSql(ui, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }


            TeacherInfo t = new TeacherInfo();
            t.setUserid(userNextRef);
            t.setTeachername("教务"+schoolid+userid);
            t.setTeachersex("男");

            sql = new StringBuilder();
            objList = this.teacherManager.getSaveSql(t, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加网校教务角色
            RoleUser jwru = new RoleUser();
            jwru.setRef(UUID.randomUUID().toString());
            jwru.getUserinfo().setRef(userNextRef);
            jwru.getRoleinfo().setRoleid(UtilTool._ROLE_WXJW_ID);

            sql = new StringBuilder();
            objList = this.roleUserManager.getSaveSql(jwru, sql);
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

        if(sqlListArray.size()>0&&sqlListArray.size()>0){
            if(this.userManager.doExcetueArrayProc(sqlListArray,objListArray)){
                //绑定教务帐号
                if(!BindEttUser(userid,schoolid,"2")){
                    je.setMsg("Bind EttUser Error!");
                    response.getWriter().print(je.toJSON());
                };

            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return null;
            }

        }
        UserInfo login=new UserInfo();
        login.setEttuserid(Integer.parseInt(userid));
        login.setDcschoolid(Integer.parseInt(schoolid));
        List<UserInfo>userLoginList=this.userManager.getList(login,null);
        if(userLoginList==null||userLoginList.size()<1){
            je.setMsg("获取绑定用户失败!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        je=this.loginBase(userLoginList.get(0),request,response);
        if(!je.getType().trim().equals("success")){
            response.getWriter().print(je.getAlertMsgAndCloseWin());return null;
        }
        //增加分校ID
        this.logined(request).setDcschoolid(Integer.parseInt(schoolid));

        //获取学年
        List<ClassYearInfo>classYearInfoList=this.classYearManager.getCurrentYearList(null);
        if(classYearInfoList==null||classYearInfoList.size()<1){
            je.setMsg("系统未获取到学年信息，请联系分校管理员!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //获取年级
        PageResult p=new PageResult();
        p.setOrderBy("g.grade_id desc");
        p.setPageNo(0);
        p.setPageSize(0);
        List<GradeInfo>gradeList=this.gradeManager.getList(null,p);
        //获取学科
        List<SubjectInfo>subjectList=this.subjectManager.getList(null,null);
        //班级类型
        List<DictionaryInfo>classType=this.dictionaryManager.getDictionaryByType("WX_CLASS_TYPE");
        //获取班主任
        String timestamp=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("schoolId",schoolid);
        map.put("userType","1");
        map.put("timestamp",timestamp);
        String sign=UrlSigUtil.makeSigSimple("user.do",map);
        String ettUrl=UtilTool.utilproperty.get("GET_ETT_USER_LOCATION").toString();
        String ettParams="schoolId="+schoolid+"&userType=1&timestamp="+timestamp+"&sign="+sign;
        List<UserInfo>ettUserList=getETTUserList(ettUrl,ettParams);
        if(ettUserList==null||ettUserList.size()<1){
            je.setMsg("未获取到网校班主任信息!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        mp.put("gradeList",gradeList);
        mp.put("subjectList",subjectList);
        mp.put("classType",classType);
        mp.put("bzrList",ettUserList);
        mp.put("classYearList",classYearInfoList);
        request.getSession().setAttribute("dcschoolid",userList.get(0).getDcschoolid());
        return new ModelAndView("/teachpaltform/ettClass/class-admin",mp);
    }


    @RequestMapping(params = "m=doAddCls", method = {RequestMethod.POST})
    public void doAddCls(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String dcschoolid=request.getParameter("dcschoolid");
        String clsname=request.getParameter("clsname");
        String type=request.getParameter("type");
        String jid=request.getParameter("jid");
        String gradeid=request.getParameter("gradeid");
        String year=request.getParameter("year");
        String verifyTime=request.getParameter("verifyTime");
        String allowJoin=request.getParameter("allowJoin");
        String num=request.getParameter("num");
        String realname=request.getParameter("realname");

        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        List<Object>objList=null;
        StringBuilder sql=null;
        List<UserInfo>userList=null;
        String userNextRef = UUID.randomUUID().toString();

        ClassInfo c=new ClassInfo();
        c.setClassname(clsname);
        c.setDctype(Integer.parseInt(type));
        c.setDcschoolid(Integer.parseInt(dcschoolid));
        c.setClassgrade(gradeid);
        c.setYear(year);
        c.setPattern("行政班");
        c.setType("NORMAL");
        if(verifyTime!=null&&verifyTime.length()>0)
            c.setVerifytime(UtilTool.StringConvertToDate(verifyTime));
        if(allowJoin!=null&&allowJoin.length()>0)
            c.setAllowjoin(Integer.parseInt(allowJoin));
        if(num!=null&&num.length()>0)
            c.setClsnum(Integer.parseInt(num));
        if(this.classManager.doSave(c)){
            List<ClassInfo>clsList=this.classManager.getList(c,null);
            if(clsList==null||clsList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().print(je.toJSON());
                return;
            }
            c=clsList.get(0);
            //添加班主任帐号


            UserInfo u=new UserInfo();
            u.setDcschoolid(Integer.parseInt(dcschoolid));
            u.setEttuserid(Integer.parseInt(jid));
            userList=this.userManager.getList(u, null);
            if(userList==null||userList.size()<1){
                //添加用户
                u.setRef(userNextRef);
                u.setPassword(dcschoolid+jid);
                u.setUsername("班主任"+dcschoolid+jid);
                u.setStateid(0);
                u.setDcschoolid(Integer.parseInt(dcschoolid));
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
                ui.setIdentityname("教职工");
                sql = new StringBuilder();
                objList = this.userIdentityManager.getSaveSql(ui, sql);
                if (objList != null && sql != null) {
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }


                TeacherInfo t = new TeacherInfo();
                t.setUserid(userNextRef);
                t.setTeachername(realname);
                t.setTeachersex("男");

                sql = new StringBuilder();
                objList = this.teacherManager.getSaveSql(t, sql);
                if (objList != null && sql != null) {
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                //添加班主任角色
                RoleUser jwru = new RoleUser();
                jwru.setRef(UUID.randomUUID().toString());
                jwru.getUserinfo().setRef(userNextRef);
                jwru.getRoleinfo().setRoleid(UtilTool._ROLE_CLASSADVISE_ID);

                sql = new StringBuilder();
                objList = this.roleUserManager.getSaveSql(jwru, sql);
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
            }else
                userNextRef=userList.get(0).getRef();

            //添加班级关联数据
            ClassUser cu=new ClassUser();
            cu.setClassid(c.getClassid());
            cu.setRelationtype("班主任");
            cu.setRef(this.classUserManager.getNextId());
            cu.setUserid(userNextRef);
            cu.setSportsex(0);
            sql=new StringBuilder();
            objList=this.classUserManager.getSaveSql(cu,sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            if(this.classManager.doExcetueArrayProc(sqlListArray,objListArray)){
                je.setType("success");
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                //回绑网校
                UserInfo usel=new UserInfo();
                usel.setRef(userNextRef);
                userList=this.userManager.getList(usel,null);
                if(userList==null||userList.size()<1){
                    je.setMsg("获取绑定用户失败!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                if(!BindEttUser(userList.get(0).getUserid().toString(),dcschoolid,"1")){
                    je.setMsg("绑定用户失败!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
     }

    @RequestMapping(params = "m=doUpdCls", method = {RequestMethod.POST})
        public void doUpdCls(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

    }


    /**
     * 删除班级人员
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "delClassUser", method = {RequestMethod.POST})
    public void delClassUser(HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String ref=request.getParameter("ref");
        ClassUser cu=new ClassUser();
        cu.setRef(ref);
        List<ClassUser>cuList=this.classUserManager.getList(cu,null);
        if(cuList==null||cuList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(this.classUserManager.doDelete(cu)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * 获取班级详情
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=ajaxClsDetail", method = {RequestMethod.POST})
    public void ajaxClsDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String clsid=request.getParameter("clsid");
        ClassInfo c=new ClassInfo();
        c.setClassid(Integer.parseInt(clsid));
        List<ClassInfo>clsList=this.classManager.getList(c,null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }

        ClassUser bzr=new ClassUser();
        bzr.setRelationtype("班主任");
        bzr.setClassid(c.getClassid());
        List<ClassUser>bzrList=this.classUserManager.getList(bzr,null);

        ClassUser tea=new ClassUser();
        tea.setRelationtype("任课老师");
        tea.setClassid(c.getClassid());
        List<ClassUser>teaList=this.classUserManager.getList(tea,null);

        ClassUser stu=new ClassUser();
        stu.setRelationtype("学生");
        stu.setClassid(c.getClassid());
        List<ClassUser>stuList=this.classUserManager.getList(stu,null);

        je.getObjList().add(clsList.get(0));
        je.getObjList().add(bzrList.get(0));
        je.getObjList().add(teaList);
        je.getObjList().add(stuList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=ajaxClsList", method = {RequestMethod.POST})
    public void ajaxClsList(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String grade=request.getParameter("grade");
        String year=request.getParameter("year");
        String type=request.getParameter("type");
        PageResult pageResult=this.getPageResultParameter(request);

        ClassInfo c=new ClassInfo();
        c.setDcschoolid(this.logined(request).getDcschoolid());
        if(grade!=null&&grade.length()>0)
                c.setClassgrade(grade);
        if(year!=null&&year.length()>0)
            c.setYear(year);
        if(type!=null&&type.length()>0)
            c.setDctype(Integer.parseInt(type));
        else
            c.setDctype(9); //获取网校班级与爱学课堂类型班级
        List<ClassInfo>clsList=this.classManager.getList(c,pageResult);
        pageResult.setList(clsList);
        je.setPresult(pageResult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取网校教师
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "loadWXTeacher", method = {RequestMethod.POST})
    public void loadWXTeacher(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String clsid=request.getParameter("clsid");
        ClassInfo c=new ClassInfo();
        c.setClassid(Integer.parseInt(clsid));
        List<ClassInfo>clsList=this.classManager.getList(c,null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }

        /*ClassUser tea=new ClassUser();
        tea.setNorelationtype("学生");
        tea.setClassid(c.getClassid());
        List<ClassUser>teaList=this.classUserManager.getList(tea,null); */
        List<ClassUser>teaList=null;

        List<UserInfo>tmpUserList=new ArrayList<UserInfo>();
        //获取教师
        String timestamp=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("schoolId",this.logined(request).getDcschoolid().toString());
        map.put("userType","1");
        map.put("timestamp",timestamp);
        String sign=UrlSigUtil.makeSigSimple("user.do",map);
        String ettUrl=UtilTool.utilproperty.get("GET_ETT_USER_LOCATION").toString();
        String ettParams="schoolId="+this.logined(request).getDcschoolid().toString()+"&userType=1&timestamp="+timestamp+"&sign="+sign;
        List<UserInfo>ettTeacherList=getETTUserList(ettUrl,ettParams);

        //根据JID过滤已存在的教师
        if(teaList!=null&&teaList.size()>0){
            if(ettTeacherList!=null&&ettTeacherList.size()>0){
                for(UserInfo child:ettTeacherList){
                    boolean isExist=false;
                    for(ClassUser parent:teaList){
                        if(parent.getEttuserid()!=null&&parent.getEttuserid()==child.getEttuserid()){
                            isExist=true;
                        }
                    }
                    if(!isExist)
                        tmpUserList.add(child);
                }
            }
        }else
            tmpUserList=ettTeacherList;

        je.setObjList(tmpUserList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 设置班级授课教师
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "doSetClassTeacher", method = {RequestMethod.POST})
    public void doSetClassTeacher(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String clsid=request.getParameter("clsid");
        String subjectid=request.getParameter("subjectid");
        String jid=request.getParameter("jid");
        String realname=request.getParameter("realname");

        //验证班级
        ClassInfo c=new ClassInfo();
        c.setClassid(Integer.parseInt(clsid));
        List<ClassInfo>clsList=this.classManager.getList(c,null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();


        //检测当前教师是否已经创建帐号
        String userNextRef = UUID.randomUUID().toString();
        String schoolid=this.logined(request).getDcschoolid().toString();
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(this.logined(request).getDcschoolid());
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            //添加用户
            u.setRef(userNextRef);
            u.setPassword(schoolid+jid);
            u.setUsername("任课教师"+schoolid+jid);
            u.setStateid(0);
            u.setDcschoolid(Integer.parseInt(schoolid));
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
            ui.setIdentityname("教职工");
            sql = new StringBuilder();
            objList = this.userIdentityManager.getSaveSql(ui, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }


            TeacherInfo t = new TeacherInfo();
            t.setUserid(userNextRef);
            t.setTeachername(realname);
            t.setTeachersex("男");

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
        }else
            userNextRef=userList.get(0).getRef();


        ClassUser tea=new ClassUser();
        tea.setUserid(userNextRef);
        tea.setClassid(c.getClassid());
        tea.setSubjectid(Integer.parseInt(subjectid));
        List<ClassUser>teaList=this.classUserManager.getList(tea,null);
        if(teaList!=null&&teaList.size()>0){
            je.setMsg("当前教师已存在!\n\n班级："+teaList.get(0).getClassname()+"，学科："+teaList.get(0).getSubjectname()+"");
            response.getWriter().print(je.toJSON());
            return;
        }

        ClassUser save=new ClassUser();
        save.setClassid(Integer.parseInt(clsid));
        save.setSubjectid(Integer.parseInt(subjectid));
        save.setUserid(userNextRef);
        save.setRelationtype("任课老师");
        save.setRef(this.classUserManager.getNextId());
        save.setSportsex(0);
        sql=new StringBuilder();
        objList=this.classUserManager.getSaveSql(save,sql);
        if(objList!=null&&sql!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        if(this.classUserManager.doExcetueArrayProc(sqlListArray,objListArray)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));

            //回绑网校JID
            UserInfo usel=new UserInfo();
            usel.setRef(userNextRef);
            userList=this.userManager.getList(usel,null);
            if(userList==null||userList.size()<1){
                je.setMsg("获取绑定用户失败!");
                response.getWriter().print(je.toJSON());
                return;
            }
            if(!BindEttUser(userList.get(0).getUserid().toString(),schoolid,"1")){
                je.setMsg("绑定用户失败!");
                response.getWriter().print(je.toJSON());
                return;
            }

        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }




    /**
     * 获取网校学生
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "loadWXStudent", method = {RequestMethod.POST})
    public void loadWXStudent(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String clsid=request.getParameter("clsid");
        ClassInfo c=new ClassInfo();
        c.setClassid(Integer.parseInt(clsid));
        List<ClassInfo>clsList=this.classManager.getList(c,null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }



        ClassUser bzr=new ClassUser();
        bzr.setRelationtype("班主任");
        bzr.setClassid(c.getClassid());
        List<ClassUser>bzrList=this.classUserManager.getList(bzr,null);

        ClassUser tea=new ClassUser();
        tea.setRelationtype("任课老师");
        tea.setClassid(c.getClassid());
        List<ClassUser>teaList=this.classUserManager.getList(tea,null);

        ClassUser stu=new ClassUser();
        stu.setRelationtype("学生");
        stu.setClassid(c.getClassid());
        List<ClassUser>stuList=this.classUserManager.getList(stu,null);

        je.getObjList().add(clsList.get(0));
        je.getObjList().add(bzrList.get(0));
        je.getObjList().add(teaList);
        je.getObjList().add(stuList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    public  boolean sendValidateUserInfoTotalSchool(String sendUrl,String params){
        if(sendUrl==null)return false;

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(sendUrl.toString());

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
           /* if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.length()));*/
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
            if(params!=null){
                outputStreamWriter.write(params);
                System.out.println("params:"+params);
            }
            /*if(jsondata!=null)
                outputStreamWriter.write(java.net.URLEncoder.encode(jsondata,"UTF-8"));*/

            outputStreamWriter.flush();
            outputStreamWriter.close();
            // objOutputStrm.flush();
            //objOutputStrm.close();
            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("异常错误!未响应!");
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
        }else if(code==HttpURLConnection.HTTP_NOT_FOUND){
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return false;
        }else if(code==HttpURLConnection.HTTP_SERVER_ERROR){
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
        String result=jb.containsKey("result")?jb.getString("result"):"";
        if((type!=null&&type.trim().toLowerCase().equals("success")) || result!=null&&result.equals("1")){
            System.out.println(msg);
            return true;
        }else{
            System.out.println(msg);return false;
        }
    }

    public  List<UserInfo> getETTUserList(String sendUrl,String params){
        if(sendUrl==null)return null;

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(sendUrl.toString());

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
           /* if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.length()));*/
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
            if(params!=null){
                outputStreamWriter.write(params);
                System.out.println("params:"+params);
            }
            /*if(jsondata!=null)
                outputStreamWriter.write(java.net.URLEncoder.encode(jsondata,"UTF-8"));*/

            outputStreamWriter.flush();
            outputStreamWriter.close();
            // objOutputStrm.flush();
            //objOutputStrm.close();
            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("异常错误!未响应!");
            return null;
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
                return null;
            }
        }else if(code==HttpURLConnection.HTTP_NOT_FOUND){
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return null;
        }else if(code==HttpURLConnection.HTTP_SERVER_ERROR){
            System.out.println("异常错误!500错误，请联系管理人员!");
            return null;
        }
        String returnContent=null;
        try {
            returnContent=java.net.URLDecoder.decode(stringBuffer.toString(),"UTF-8");  ///aa
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //转换成JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        String type=jb.containsKey("type")?jb.getString("type"):"";
        String msg=jb.containsKey("msg")?jb.getString("msg"):"";
        String result=jb.containsKey("result")?jb.getString("result"):"";
        List<UserInfo>userList=null;
        if((type!=null&&type.trim().toLowerCase().equals("success")) || result!=null&&result.equals("1")){
            System.out.println(msg);
            if(jb.containsKey("data")){
                JSONObject userObj=JSONObject.fromObject(jb.getString("data"));
                if(!userObj.containsKey("user"))return null;
                JSONArray array;
                try{
                    array=JSONArray.fromObject(userObj.getString("user"));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return null;
                }

                if(array!=null&&array.size()>0){
                    userList=new ArrayList<UserInfo>();
                    Iterator iterator=array.iterator();
                    while(iterator.hasNext()){
                        JSONObject obj=(JSONObject)iterator.next();
                        String jid=obj.containsKey("JID")?obj.getString("JID"):"";
                        String userName =obj.containsKey("USERNAME")?obj.getString("USERNAME"):"";
                        String realName =obj.containsKey("REALNAME")?obj.getString("REALNAME"):"";
                        String sex =obj.containsKey("SEX")?obj.getString("SEX"):"";
                        UserInfo u=new UserInfo();
                        if(jid.length()>0)
                            u.setEttuserid(Integer.parseInt(jid));
                        if(userName.length()>0)
                            u.setUsername(userName);
                        if(realName.length()>0)
                            u.setRealname(realName);
                        if(sex.length()>0)
                            u.setSex(sex);
                        userList.add(u);
                    }
                }
            }

        }else{
            System.out.println(msg);return null;
        }
        return userList;
    }


    /**
     * userType 1:教师 2:教务 3:学生
     * @param userid
     * @param schoolid
     * @param userType
     * @return
     */
    public boolean BindEttUser(String userid,String schoolid,String userType){
        UserInfo sel=new UserInfo();
        sel.setUserid(Integer.parseInt(userid));
        sel.setDcschoolid(Integer.parseInt(schoolid));
        List<UserInfo>ettUserList=this.userManager.getList(sel,null);
        if(ettUserList!=null&&ettUserList.size()>0){
            JSONObject obj = new JSONObject();
            obj.element("jid", ettUserList.get(0).getEttuserid());
            obj.element("userId", ettUserList.get(0).getUserid());
            obj.element("schoolId",ettUserList.get(0).getDcschoolid());
            obj.element("userType",userType);
            JSONArray array=new JSONArray();
            array.add(obj);

            String timestamp=System.currentTimeMillis()+"";
            HashMap<String,String>map=new HashMap<String, String>();
            map.put("schoolId", ettUserList.get(0).getDcschoolid().toString());
            map.put("userType",userType);
            map.put("timestamp",timestamp);
            String sign=UrlSigUtil.makeSigSimple("backbind.do",map);
            String ettUrl=UtilTool.utilproperty.get("BIND_ETT_USER_LOCATION").toString();
            String ettParams="schoolId="+schoolid+"&userType="+userType+"&timestamp="+timestamp+"&sign="+sign+"&userList="+array.toString();
            System.out.println(ettUrl + "?" + ettParams);
            return sendValidateUserInfoTotalSchool(ettUrl, ettParams);
        }
        return false;
    }
}

class TpUserUtilTool extends TpUserController{
    /**
     * 验证请求参数
     * @param request
     * @return
     * @throws Exception
     */
    public static Boolean ValidateRequestParam(HttpServletRequest request) throws Exception{
        Enumeration eObj=request.getParameterNames();
        boolean returnBo=true;
        if(eObj!=null){
            while(eObj.hasMoreElements()){
                Object obj=eObj.nextElement();
                if(obj==null||obj.toString().trim().length()<1||request.getQueryString().toString().equals(obj))
                    continue;

                Object val=request.getParameter(obj.toString());
                if(val==null||val.toString().trim().length()<1){
                    returnBo=!returnBo;
                    break;
                }
            }
        }

        return returnBo;
    }

}
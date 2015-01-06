package com.school.control;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.*;
import com.school.entity.teachpaltform.TpGroupInfo;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.manager.SchoolManager;
import com.school.manager.inter.ISchoolManager;
import com.school.manager.inter.teachpaltform.ITpGroupManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.manager.teachpaltform.TpGroupManager;
import com.school.manager.teachpaltform.TpGroupStudentManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.utils.EttInterfaceUserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping(value = "/tpuser")
public class TpUserController extends UserController {

    @Autowired
    private ISchoolManager schoolManager;
    @Autowired
    private ITpGroupManager groupManager;
    @Autowired
    private ITpGroupStudentManager groupStudentManager;


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
       // String url="http://localhost:8080/totalSchool/";//UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");

        String url=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
        String totalSchoolUrl=url+"franchisedSchool?jwValidateSchool";
        String totalParams="schoolid="+schoolid+"&schoolname="+schoolname;//java.net.URLEncoder.encode(schoolname,"UTF-8");
        if(!sendValidateUserInfoTotalSchool(totalSchoolUrl,totalParams)){
            je.setMsg("Operate SchoolInfo Error...");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }



        boolean isNewUser=false;
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(schoolid));
        u.setEttuserid(Integer.parseInt(userid));
        List<UserInfo>userList=this.userManager.getList(u, null);
        if(userList==null||userList.size()<1){
            //添加用户
            isNewUser=true;
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

                if(isNewUser){
                    if(!BindEttUser(userid,schoolid,"2")){
                        je.setMsg("Bind EttUser Error!");
                        response.getWriter().print(je.toJSON());
                        return null;
                    };

                    //向ETT更新用户
                    UserInfo baseUser=new UserInfo();
                    baseUser.setDcschoolid(Integer.parseInt(schoolid));
                    baseUser.setEttuserid(Integer.parseInt(userid));
                    List<UserInfo>baseUserList=this.userManager.getList(baseUser,null);
                    if(!EttInterfaceUserUtil.addUserBase(baseUserList))
                        System.out.println("Add ETT USER Error!");
                    else
                        System.out.println("Add ETT USER Success!");
                }


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
        String sign=UrlSigUtil.makeSigSimple("user.do", map);
        String ettParams="schoolId="+schoolid+"&userType=1&timestamp="+timestamp+"&sign="+sign;
        List<UserInfo>ettUserList=getETTUserList(ettParams);
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
        //重新获取用户
        userList=this.userManager.getList(u,null);
        request.getSession().setAttribute("dcschoolid",userList.get(0).getDcschoolid());
        return new ModelAndView("/teachpaltform/ettClass/class-admin",mp);
    }


    /**
     * 分校教师、学生进入教学平台
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toCourseIndex", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView toCourseIndex(HttpServletRequest request,
                                   HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolId");
        String jid=request.getParameter("jId");
        if(schoolid==null||schoolid.trim().length()<1||!UtilTool.isNumber(schoolid)){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        if(jid==null||jid.trim().length()<1||!UtilTool.isNumber(jid)){
            je.setMsg("jid is empty!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(schoolid));
        u.setEttuserid(Integer.parseInt(jid));
        List<UserInfo>userList=this.userManager.getList(u, null);
        if(userList==null||userList.size()<1){
            je.setMsg("您尚未被分配到任何班级!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        je=this.loginBase(userList.get(0),request,response);
        if(!je.getType().trim().equals("success")){
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //增加分校ID
        this.logined(request).setDcschoolid(Integer.parseInt(schoolid));


        String url=null;
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            url="teachercourse?m=toStudentCourseList";
        }else if (this.validateRole(request,UtilTool._ROLE_TEACHER_ID)){
            url="teachercourse?toTeacherCourseList";
            Integer isLessionBzn=this.classUserManager.isTeachingBanZhuRen(this.logined(request).getRef(),null,null,null);
            if(isLessionBzn!=null&&isLessionBzn==3)
                url="teachercourse?toTeacherCourseList";
            else if(this.validateRole(request,UtilTool._ROLE_CLASSADVISE_ID))
                url="group?m=toGroupManager";
        }
        request.getSession().setAttribute("fromType","ett");
        String baseUrl=request.getSession().getAttribute("IP_PROC_NAME")==null?"":request.getSession().getAttribute("IP_PROC_NAME").toString();
        response.sendRedirect(baseUrl+url);
        return null;
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
        //String invitecode=request.getParameter("invitecode");

        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        List<Object>objList=null;
        StringBuilder sql=null;
        List<UserInfo>userList=null;
        String userNextRef = UUID.randomUUID().toString();
        boolean isNewUser=false;

        Integer classid=null;
        ClassInfo c=new ClassInfo();
        c.setClassname(clsname);
        c.setDctype(Integer.parseInt(type));
        c.setDcschoolid(Integer.parseInt(dcschoolid));
        c.setClassgrade(gradeid);
        c.setYear(year);
        c.setPattern("行政班");
        c.setType("NORMAL");
        c.setCuserid(this.logined(request).getUserid());
        if(verifyTime!=null&&verifyTime.length()>0)
            c.setVerifytime(UtilTool.StringConvertToDate(verifyTime));
        if(allowJoin!=null&&allowJoin.length()>0)
            c.setAllowjoin(Integer.parseInt(allowJoin));
        if(num!=null&&num.length()>0)
            c.setClsnum(Integer.parseInt(num));
        //公共家长邀请码
        if(c.getDctype()==2){
            String invitecode=this.genderInviteCode();
            c.setImvaldatecode(invitecode);
        }
        if(this.classManager.doSave(c)){
            List<ClassInfo>clsList=this.classManager.getList(c,null);
            if(clsList==null||clsList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().print(je.toJSON());
                return;
            }
            c=clsList.get(0);
            classid=c.getClassid();
            //向ETT更新班级
            if(!EttInterfaceUserUtil.addClassBase(c))
                System.out.println("Add ETT USER Error!");
            else
                System.out.println("Add ETT USER Success!");


            //添加班主任帐号
            UserInfo u=new UserInfo();
            u.setDcschoolid(Integer.parseInt(dcschoolid));
            u.setEttuserid(Integer.parseInt(jid));
            userList=this.userManager.getList(u, null);
            if(userList==null||userList.size()<1){
                //添加用户
                isNewUser=true;
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
                if(!BindEttUser(userList.get(0).getEttuserid().toString(),dcschoolid,"1")){
                    je.setMsg("绑定用户失败!");
                    response.getWriter().print(je.toJSON());
                    return;
                }

                if(objListArray.size()>0){
                    //向网校更新用户
                    if(isNewUser){
                        if(!EttInterfaceUserUtil.addUserBase(userList))
                            System.out.println("update ett user error!");
                        else
                            System.out.println("update ett user success!");
                    }

                    List<Map<String,Object>>mapList=this.getClassUserMap("班主任",c.getClassid());
                    if(!EttInterfaceUserUtil.OperateClassUser(mapList,c.getClassid(),this.logined(request).getDcschoolid()))
                        System.out.println("update ett classdriver error!");
                    else
                        System.out.println("update ett classdriver success!");
                }
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        if(classid!=null&&!UtilTool.isNumber(classid.toString()))
            je.getObjList().add(c.getClassid());
        response.getWriter().print(je.toJSON());
     }

    /**
     * 删除班级
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=doDelCls", method =RequestMethod.POST)
    public void doDelCls(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String clsid=request.getParameter("clsid");
        ClassInfo classInfo=new ClassInfo();
        classInfo.setClassid(Integer.parseInt(clsid));
        List<ClassInfo>classInfoList=this.classManager.getList(classInfo,null);
        if(classInfoList==null||classInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERROR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(this.classManager.doDelete(classInfo)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));

            //向ETT更新班级
            if(!EttInterfaceUserUtil.delClassBase(classInfoList.get(0)))
                System.out.println("Delete ETT Cls Error!");
            else
                System.out.println("Delete ETT Cls Success!");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=doUpdCls", method = {RequestMethod.POST})
        public void doUpdCls(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String clsid=request.getParameter("clsid");
        String dcschoolid=request.getParameter("dcschoolid");
        String clsname=request.getParameter("clsname");
        String type=request.getParameter("type");
        String jid=request.getParameter("jid");
        String gradeid=request.getParameter("gradeid");
        String year=request.getParameter("year");
        String verifyTime=request.getParameter("verifyTime");
        String allowJoin=request.getParameter("allowJoin");
        String num=request.getParameter("num");
       // String invitecode=request.getParameter("invitecode");
        String realname=request.getParameter("realname");

        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        List<Object>objList=null;
        StringBuilder sql=null;
        List<UserInfo>userList=null;
        String userNextRef = UUID.randomUUID().toString();
        boolean isNewUser=false;


        ClassInfo classInfo=new ClassInfo();
        classInfo.setClassid(Integer.parseInt(clsid));
        List<ClassInfo>classInfoList=this.classManager.getList(classInfo,null);
        if(classInfoList==null||classInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERROR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }

        ClassInfo c=new ClassInfo();
        c.setClassid(Integer.parseInt(clsid));
        c.setClassname(clsname);
        c.setDctype(Integer.parseInt(type));
        c.setDcschoolid(Integer.parseInt(dcschoolid));
        c.setClassgrade(gradeid);
        c.setYear(year);
        if(verifyTime!=null&&verifyTime.length()>0)
            c.setVerifytime(UtilTool.StringConvertToDate(verifyTime));
        if(allowJoin!=null&&allowJoin.length()>0)
            c.setAllowjoin(Integer.parseInt(allowJoin));
        if(num!=null&&num.length()>0)
            c.setClsnum(Integer.parseInt(num));
        //公共家长邀请码
        /*if(this.logined(request).getDcschoolid().intValue()==1&&invitecode!=null&&invitecode.trim().length()>0&&
                c.getDctype()==2)
            c.setInvitecode(invitecode);*/
        sql=new StringBuilder();
        objList=this.classManager.getUpdateSql(c,sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        c=classInfoList.get(0);
        //添加班主任帐号

        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(dcschoolid));
        u.setEttuserid(Integer.parseInt(jid));
        userList=this.userManager.getList(u, null);
        if(userList==null||userList.size()<1){
            //添加用户
            isNewUser=true;
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

        //删除班级关联数据
        ClassUser delete=new ClassUser();
        delete.setClassid(c.getClassid());
        delete.setRelationtype("班主任");
        List<ClassUser>classUsers=this.classUserManager.getList(delete,null);
        if(classUsers!=null&&classUsers.size()>0){
            sql=new StringBuilder();
            objList=this.classUserManager.getDeleteSql(delete, sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            //删除班主任角色
            for(ClassUser cu:classUsers){
                RoleUser ru = new RoleUser();
                ru.getUserinfo().setRef(cu.getUserid());
                ru.getRoleinfo().setRoleid(UtilTool._ROLE_CLASSADVISE_ID);


                ClassUser bzrRight=new ClassUser();
                bzrRight.setRelationtype("班主任");
                bzrRight.setUserid(cu.getUserid());
                List<ClassUser>bzrRightList=this.classUserManager.getList(bzrRight,null);
                if(bzrRightList!=null&&bzrRightList.size()==1){//为多个班的班主任，不删除角色
                    sql = new StringBuilder();
                    objList = this.roleUserManager.getDeleteSql(ru, sql);
                    if (objList != null && sql != null) {
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }
        }



        //添加班主任角色
        RoleUser jwru = new RoleUser();
        jwru.getUserinfo().setRef(userNextRef);
        jwru.getRoleinfo().setRoleid(UtilTool._ROLE_CLASSADVISE_ID);
        List<RoleUser>ruList=this.roleUserManager.getList(jwru,null);
        if(ruList==null||ruList.size()<1){
            jwru.setRef(UUID.randomUUID().toString());
            sql = new StringBuilder();
            objList = this.roleUserManager.getSaveSql(jwru, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }

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
                if(!BindEttUser(userList.get(0).getEttuserid().toString(),dcschoolid,"1")){
                    je.setMsg("绑定用户失败!");
                    response.getWriter().print(je.toJSON());
                    return;
                }


                if(objListArray.size()>0){
                    //向ETT更新班级
                    ClassInfo clsupdate=new ClassInfo();
                    clsupdate.setClassid(Integer.parseInt(clsid));
                    List<ClassInfo>classInfos=this.classManager.getList(clsupdate,null);
                    if(classInfos!=null&&classInfos.size()>0){
                        if(!EttInterfaceUserUtil.updateClassBase(classInfos.get(0)))
                            System.out.println("Update ETT Cls Error!");
                        else
                            System.out.println("Update ETT Cls Success!");
                    }


                    if(isNewUser){
                        //向ETT更新用户
                        UserInfo baseUser=new UserInfo();
                        baseUser.setDcschoolid(this.logined(request).getDcschoolid());
                        baseUser.setEttuserid(Integer.parseInt(jid));
                        List<UserInfo>baseUserList=this.userManager.getList(baseUser,null);
                        if(!EttInterfaceUserUtil.addUserBase(baseUserList))
                            System.out.println("Add ETT USER Error!");
                        else
                            System.out.println("Add ETT USER Success!");
                    }

                    List<Map<String,Object>>mapList=this.getClassUserMap("班主任",c.getClassid());
                    if(!EttInterfaceUserUtil.OperateClassUser(mapList,c.getClassid(),this.logined(request).getDcschoolid()))
                        System.out.println("update ett classdriver error!");
                    else
                        System.out.println("update ett classdriver success!");
                }
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
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
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        sql=new StringBuilder();
        objList=this.classUserManager.getDeleteSql(cu,sql);
        if(sql!=null&&objList!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }


        if(cuList.get(0).getRelationtype().equals("学生")){
            UserInfo userInfo=new UserInfo();
            userInfo.setRef(cuList.get(0).getUserid());
            List<UserInfo>userList=this.userManager.getList(userInfo,null);
            if(userList!=null&&userList.size()>0){
                TpGroupStudent groupStudent=new TpGroupStudent();
                groupStudent.setClassid(cuList.get(0).getClassid());
                groupStudent.setUserid(userList.get(0).getUserid());
                sql=new StringBuilder();
                objList=this.groupStudentManager.getDeleteSql(groupStudent,sql);
                if(sql!=null&&objList!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }


        if(this.classUserManager.doExcetueArrayProc(sqlListArray,objListArray)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");


            //删除ETT User
            List<Map<String,Object>>mapList=this.getClassUserMap(cuList.get(0).getRelationtype(),cuList.get(0).getClassid());
            if(!EttInterfaceUserUtil.OperateClassUser(mapList, cuList.get(0).getClassid(), this.logined(request).getDcschoolid()))
                System.out.println("delete ett classdriver error!");
            else
                System.out.println("delete ett classdriver success!");

            if(cuList.get(0).getRelationtype().equals("学生")){
                this.OperateGroupUser(cuList.get(0).getClassid(),this.logined(request).getDcschoolid());
            }
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
        for(ClassUser bzrUser:bzrList){
            bzrUser.setHeadimage("http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
        }
        if(bzrList==null||bzrList.size()<1){
            je.setMsg("未获取到当前班级班主任信息!");
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult pageResult=new PageResult();
        pageResult.setPageSize(0);
        pageResult.setPageNo(0);
        pageResult.setOrderBy(" sub.subject_id ");
        ClassUser tea=new ClassUser();
        tea.setRelationtype("任课老师");
        tea.setClassid(c.getClassid());
        List<ClassUser>teaList=this.classUserManager.getList(tea,pageResult);
        for(ClassUser teaUser:teaList){
            teaUser.setHeadimage("http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
        }


        ClassUser stu=new ClassUser();
        stu.setRelationtype("学生");
        stu.setClassid(c.getClassid());
        pageResult.setOrderBy(" stu.stu_name ");
        List<ClassUser>stuList=this.classUserManager.getList(stu,pageResult);
        if(stuList!=null&&stuList.size()>0){
            StringBuilder jids = new StringBuilder();
            jids.append("[");
            for(ClassUser tmpUser:stuList){
                jids.append("{\"jid\":"+tmpUser.getEttuserid()+"},");
            }

            if(jids.length()>0){
                String jidstr = jids.toString().substring(0,jids.toString().lastIndexOf(","))+"]";
                System.out.println("jidStr:" + jidstr);
                String url=UtilTool.utilproperty.getProperty("GET_HEAD_IMG_URL");
                HashMap<String,String> signMap = new HashMap();
                signMap.put("userList",jidstr);
                signMap.put("schoolId",this.logined(request).getDcschoolid().toString());
                signMap.put("srcJid",this.logined(request).getEttuserid().toString());
                signMap.put("userType","3");
                signMap.put("timestamp",""+System.currentTimeMillis());
                String signture = UrlSigUtil.makeSigSimple("queryEttHeadPhoto.do",signMap,"*ETT#HONER#2014*");
                signMap.put("sign",signture);
                JSONObject jsonObject = this.sendPostUrl(url,signMap,"utf-8");
                int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                if(type==1){
                    Object jsonObj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
                    jsonObj = URLDecoder.decode(jsonObj.toString(), "utf-8");
                    JSONArray jr = JSONArray.fromObject(jsonObj);
                    System.out.println(jr.toString());
                    if(jr!=null&&jr.size()>0){
                        for(int i = 0;i<jr.size();i++){
                            JSONObject jsono = jr.getJSONObject(i);
                            for(int j = 0;j<stuList.size();j++){
                                if(stuList.get(j).getEttuserid()!=null&&stuList.get(j).getEttuserid().toString().length()>0&&
                                        jsono.getInt("jid")==Integer.parseInt(stuList.get(j).getEttuserid().toString())){
                                    stuList.get(j).setHeadimage(jsono.getString("headUrl"));
                                }
                            }
                        }
                    }
                }
            }

        }

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
        String sign=UrlSigUtil.makeSigSimple("user.do", map);
        String ettParams="schoolId="+this.logined(request).getDcschoolid().toString()+"&userType=1&timestamp="+timestamp+"&sign="+sign;
        List<UserInfo>ettTeacherList=getETTUserList(ettParams);

        //根据JID过滤已存在的教师
        if(teaList!=null&&teaList.size()>0){
            if(ettTeacherList!=null&&ettTeacherList.size()>0){
                for(UserInfo child:ettTeacherList){
                    boolean isExist=false;
                    for(ClassUser parent:teaList){
                        if(parent.getEttuserid()!=null&&parent.getEttuserid().toString().equals(child.getEttuserid().toString())){
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
        boolean isNewUser=false,isUpdTeacher=false;

        if(userList==null||userList.size()<1){
            //添加用户
            isNewUser=true;
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
        }else{
            userNextRef=userList.get(0).getRef();
            TeacherInfo t = new TeacherInfo();
            t.setUserid(userNextRef);
            t.setTeachername(realname);
            sql = new StringBuilder();
            objList = this.teacherManager.getUpdateSql(t, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            isUpdTeacher=true;
        }


        ClassUser tea=new ClassUser();
        tea.setUserid(userNextRef);
        tea.setClassid(c.getClassid());
        if(subjectid!=null&&subjectid.trim().length()>0){
            tea.setSubjectid(Integer.parseInt(subjectid));
            tea.setRelationtype("任课老师");
        }
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
            if(!BindEttUser(userList.get(0).getEttuserid().toString(),schoolid,"1")){
                je.setMsg("绑定用户失败!");
                response.getWriter().print(je.toJSON());
                return;
            }

            if(isNewUser){
                //向ETT更新用户
                UserInfo baseUser=new UserInfo();
                baseUser.setDcschoolid(Integer.parseInt(schoolid));
                baseUser.setEttuserid(Integer.parseInt(jid));
                List<UserInfo>baseUserList=this.userManager.getList(baseUser,null);
                if(!EttInterfaceUserUtil.addUserBase(baseUserList))
                    System.out.println("Add ETT USER Error!");
                else
                    System.out.println("Add ETT USER Success!");
            }
            if(isUpdTeacher){
                //向ETT更新用户
                UserInfo baseUser=new UserInfo();
                baseUser.setDcschoolid(Integer.parseInt(schoolid));
                baseUser.setEttuserid(Integer.parseInt(jid));
                List<UserInfo>baseUserList=this.userManager.getList(baseUser,null);
                if(!EttInterfaceUserUtil.updateUserBase(baseUserList))
                    System.out.println("Upd ETT Teacher Error!");
                else
                    System.out.println("Upd ETT Teacher Success!");
            }

            List<Map<String,Object>>mapList=this.getClassUserMap("任课老师",Integer.parseInt(clsid));
            if(!EttInterfaceUserUtil.OperateClassUser(mapList,Integer.parseInt(clsid),Integer.parseInt(schoolid)))
                System.out.println("Add ett cls teacher error!");
            else
                System.out.println("Add ett cls teacher success!");

        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }



    /**
     * 设置班级学生
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "doAddClsStudent", method = {RequestMethod.POST})
    public void doAddClsStudent(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        if(!TpUserUtilTool.ValidateRequestParam(request)){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String clsid=request.getParameter("clsid");
        String jidStr=request.getParameter("jidStr");
        String nameStr=request.getParameter("nameStr");
        String flag=request.getParameter("flag");
        String groupFlag=request.getParameter("groupflag");
        //验证班级
        ClassInfo c=new ClassInfo();
        c.setClassid(Integer.parseInt(clsid));
        List<ClassInfo>clsList=this.classManager.getList(c,null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }

        ClassInfo classInfo = clsList.get(0);
        boolean isDelGroup=false;
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();


        //学生UserId列表
        List<String>userRefList=new ArrayList<String>();
        String schoolid=this.logined(request).getDcschoolid().toString();

        //清空班级学生
        if(flag!=null&&flag.equals("1")){
            ClassUser cdelete=new ClassUser();
            cdelete.setClassid(clsList.get(0).getClassid());
            cdelete.setRelationtype("学生");
            sql=new StringBuilder();
            objList=this.classUserManager.getDeleteSql(cdelete,sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            TpGroupStudent groupStudent=new TpGroupStudent();
            groupStudent.setClassid(Integer.parseInt(clsid));
            sql=new StringBuilder();
            objList=this.groupStudentManager.getDeleteSql(groupStudent,sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            isDelGroup=true;
        }

         //清空小组
        if(groupFlag!=null&&groupFlag.length()>0&&flag!=null&&flag.length()>0){

        }

        if(jidStr!=null&&nameStr!=null&&jidStr.length()>0&&nameStr.length()>0){
            String[]jidArray=jidStr.split(",");
            String[]nameArray=nameStr.split(",");

            if(jidArray.length>0&&nameArray.length>0){

                if(classInfo.getClsnum()!=null&&classInfo.getClsnum()<jidArray.length){
                    je.setMsg("学生人数超出限额!\n\n班级限额："+classInfo.getClsnum()+"人!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                //删除班级小组人员
                List<TpGroupStudent>tgsList=null;
                if(groupFlag!=null&&groupFlag.length()>0){
                    TpGroupStudent tgs=new TpGroupStudent();
                    tgs.setClassid(Integer.parseInt(clsid));
                    tgsList=this.groupStudentManager.getList(tgs,null);
                }

                //删除当前班级学生,jid列表中没有的
                ClassUser selStu=new ClassUser();
                selStu.setClassid(Integer.parseInt(clsid));
                selStu.setRelationtype("学生");
                List<ClassUser>stuAllList=this.classUserManager.getList(selStu,null);
                if(stuAllList!=null&&stuAllList.size()>0){
                    for(ClassUser stu:stuAllList){
                        boolean isExists=false;
                        for(String tmpJid:jidArray){
                            if(tmpJid.equals(stu.getEttuserid().toString())){
                                isExists=true;
                                break;
                            }
                        }
                        if(!isExists){
                            ClassUser delete=new ClassUser();
                            delete.setRef(stu.getRef());
                            sql=new StringBuilder();
                            List<Object>objectList=this.classUserManager.getDeleteSql(delete,sql);
                            if(sql!=null&&objectList!=null){
                                sqlListArray.add(sql.toString());
                                objListArray.add(objectList);
                            }

                            //删除班级小组人员
                            if(tgsList!=null&&tgsList.size()>0){
                                for(TpGroupStudent tgs:tgsList){
                                    if(tgs.getUserid().equals(stu.getUid())){
                                        TpGroupStudent gsdelete=new TpGroupStudent();
                                        gsdelete.setRef(tgs.getRef());
                                        sql=new StringBuilder();
                                        objList=this.groupStudentManager.getDeleteSql(gsdelete,sql);
                                        if(sql!=null&&objList!=null){
                                            sqlListArray.add(sql.toString());
                                            objListArray.add(objList);
                                        }
                                        isDelGroup=true;
                                    }
                                }
                            }
                        }
                    }
                }
                //检测当前学生是否已经创建帐号
                for(int i=0;i<jidArray.length;i++){
                    String jid=jidArray[i].toString();
                    String realname=nameArray[i].toString();
                    String userNextRef = UUID.randomUUID().toString();

                    UserInfo u=new UserInfo();
                    u.setEttuserid(Integer.parseInt(jid));
                    u.setDcschoolid(this.logined(request).getDcschoolid());
                    List<UserInfo>userList=this.userManager.getList(u,null);
                    if(userList==null||userList.size()<1){

                        //添加用户
                        u.setRef(userNextRef);
                        u.setPassword(schoolid+jid);
                        u.setUsername("学生"+schoolid+jid);
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
                        ui.setIdentityname("学生");
                        sql = new StringBuilder();
                        objList = this.userIdentityManager.getSaveSql(ui, sql);
                        if (objList != null && sql != null) {
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }


                        StudentInfo s = new StudentInfo();
                        s.setUserref(userNextRef);
                        s.setStuname(realname);
                        s.setStusex("男");
                        //s.setStuno();

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
                        userRefList.add(userNextRef);
                    }else
                        userNextRef=userList.get(0).getRef();

                    //添加
                    ClassUser sel=new ClassUser();
                    sel.setClassid(Integer.parseInt(clsid));
                    sel.setUserid(userNextRef);
                    sel.setRelationtype("学生");
                    List<ClassUser>stuList=this.classUserManager.getList(sel,null);
                    if(stuList==null||stuList.size()<1){
                        ClassUser save=new ClassUser();
                        save.setClassid(Integer.parseInt(clsid));
                        save.setUserid(userNextRef);
                        save.setRelationtype("学生");
                        save.setRef(this.classUserManager.getNextId());
                        sql=new StringBuilder();
                        objList=this.classUserManager.getSaveSql(save,sql);
                        if(objList!=null&&sql!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }
        }



        if(this.classUserManager.doExcetueArrayProc(sqlListArray,objListArray)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));

            //回绑网校JID
            List<UserInfo>bindUserList=new ArrayList<UserInfo>();
            if(userRefList.size()>0){
                for (String userid:userRefList){
                    UserInfo usel=new UserInfo();
                    usel.setRef(userid);
                    List<UserInfo>uList=this.userManager.getList(usel,null);
                    if(uList!=null&&uList.size()>0)
                        bindUserList.add(uList.get(0));
                }

                if(bindUserList.size()>0){

                    if(!BindEttUser(bindUserList,schoolid,"3")){
                        System.out.println("-----------------------------------------!!BIND STUDENT SUCCESS!!-----------------------------------------");
                        response.getWriter().print(je.toJSON());
                        return;
                    }else
                        System.out.println("------------------------------------------!!BIND STUDENT ERROR!!-----------------------------------------");
                }



                if(!EttInterfaceUserUtil.addUserBase(bindUserList))
                    System.out.println("Add ett stu error!");
                else
                    System.out.println("Add ett stu success!");
            }

            List<Map<String,Object>>mapList=this.getClassUserMap("学生",Integer.parseInt(clsid));
            if(!EttInterfaceUserUtil.OperateClassUser(mapList,Integer.parseInt(clsid),Integer.parseInt(schoolid)))
                System.out.println("Add ett cls stu error!");
            else
                System.out.println("Add ett cls stu success!");
            if(isDelGroup){
                this.OperateGroupUser(Integer.parseInt(clsid),this.logined(request).getDcschoolid());
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
        String stuName=request.getParameter("stuName");
        ClassInfo c=new ClassInfo();
        c.setClassid(Integer.parseInt(clsid));
        List<ClassInfo>clsList=this.classManager.getList(c,null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        GradeInfo gradeInfo=new GradeInfo();
        gradeInfo.setGradevalue(clsList.get(0).getClassgrade());
        List<GradeInfo>gradeInfoList=this.gradeManager.getList(gradeInfo,null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("获取班级年级异常!"));
            response.getWriter().print(je.toJSON());
            return;
        }

        Integer gradeid=gradeInfoList.get(0).getGradeid();

        ClassUser stu=new ClassUser();
        stu.setRelationtype("学生");
        stu.setClassid(c.getClassid());
        List<ClassUser>stuList=this.classUserManager.getList(stu,null);


        List<UserInfo>tmpUserList=new ArrayList<UserInfo>();
        //获取分校学生
        String timestamp=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("schoolId", this.logined(request).getDcschoolid().toString());
        map.put("userType","3");
        map.put("timestamp",timestamp);
        String sign=UrlSigUtil.makeSigSimple("user.do",map);
        //
        Integer classType=1;
        if(clsList.get(0).getDctype()==2)
            classType=1;
        else if(clsList.get(0).getDctype()==3)
            classType=2;
        String ettParams="schoolId="+this.logined(request).getDcschoolid().toString()+"&classType="+classType+"&userType=3&grade="+gradeid+"&timestamp="+timestamp+"&sign="+sign;
        if(stuName!=null&&stuName.trim().length()>0)
            ettParams+="&stuName="+java.net.URLEncoder.encode(stuName,"UTF-8");
        List<UserInfo>ettStudentList=getETTUserList(ettParams);

        //根据JID过滤已存在当前班级中的学生
        if(stuList!=null&&stuList.size()>0){
            if(ettStudentList!=null&&ettStudentList.size()>0){
                for(UserInfo child:ettStudentList){
                    boolean isExist=false;
                    for(ClassUser parent:stuList){
                        if(parent.getEttuserid()!=null&&parent.getEttuserid().toString().equals(child.getEttuserid().toString())){
                            isExist=true;
                        }
                    }
                    if(!isExist)
                        tmpUserList.add(child);
                }
            }
        }else
            tmpUserList=ettStudentList;

        je.getObjList().add(tmpUserList);
        je.getObjList().add(stuList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }





    /**
     * 获取网校云账号
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "loadEttUserName", method = {RequestMethod.POST})
    public void loadEttUserName(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        UserInfo userInfo=new UserInfo();
        userInfo.setRef(this.logined(request).getRef());
        userInfo.setDcschoolid(this.logined(request).getDcschoolid());
        List<UserInfo>uList=this.userManager.getList(userInfo,null);
        if(uList==null||uList.size()<1||uList.get(0).getEttuserid()==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());return;
        }
        UserInfo tmpUser=uList.get(0);

        String timestamp=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("jid", tmpUser.getEttuserid().toString());
        map.put("timestamp",timestamp);
        String sign=UrlSigUtil.makeSigSimple("queryEttUserName.do",map);
        map.put("sign",sign);
        String ettUrl=UtilTool.utilproperty.get("ETT_INTER_IP").toString()+"queryEttUserName.do";
        JSONObject jsonObject=sendPostUrl(ettUrl,map,"utf-8");
        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
        if(type==1){
            Object jsonObj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
            jsonObj = URLDecoder.decode(jsonObj.toString(), "utf-8");
            JSONObject dataObj=JSONObject.fromObject(jsonObj);
            String userName=dataObj.containsKey("userName")?dataObj.get("userName").toString():null;
            je.getObjList().add(userName);
        }
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }



    /**
     * 验证网校云账号
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "checkEttUserName", method = {RequestMethod.POST})
    public void checkEttUserName(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String userName=request.getParameter("userName");
        if(userName==null||userName.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        UserInfo userInfo=new UserInfo();
        userInfo.setRef(this.logined(request).getRef());
        userInfo.setDcschoolid(this.logined(request).getDcschoolid());
        List<UserInfo>uList=this.userManager.getList(userInfo,null);
        if(uList==null||uList.size()<1||uList.get(0).getEttuserid()==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());return;
        }
        UserInfo tmpUser=uList.get(0);

        String timestamp=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("jid", tmpUser.getEttuserid().toString());
        map.put("userName", userName);
        map.put("timestamp",timestamp);
        String sign=UrlSigUtil.makeSigSimple("checkEttUserName.do",map);
        map.put("sign",sign);
        String ettUrl=UtilTool.utilproperty.get("ETT_INTER_IP").toString()+"checkEttUserName.do";
        JSONObject jsonObject=sendPostUrl(ettUrl,map,"utf-8");
        Integer type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
        if(type==0){
            Object jsonObj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
            jsonObj = URLDecoder.decode(jsonObj.toString(), "utf-8");
            je.getObjList().add(jsonObj);
        }else
            je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     *修改网校云账号
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "modifyEttUser", method = {RequestMethod.POST})
    public void modifyEttUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String oldUserName=request.getParameter("oldUserName");
        String newUserName=request.getParameter("newUserName");
        String oldPwd=request.getParameter("oldPwd");
        String newPwd=request.getParameter("newPwd");

        if(oldUserName==null||oldUserName.trim().length()<1||
                newUserName==null||newUserName.trim().length()<1||
                oldPwd==null||oldPwd.trim().length()<1||
                newPwd==null||newPwd.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }


        UserInfo userInfo=new UserInfo();
        userInfo.setRef(this.logined(request).getRef());
        userInfo.setDcschoolid(this.logined(request).getDcschoolid());
        List<UserInfo>uList=this.userManager.getList(userInfo,null);
        if(uList==null||uList.size()<1||uList.get(0).getEttuserid()==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());return;
        }
        UserInfo tmpUser=uList.get(0);
        Integer userType=1;
        if(this.validateRole(request,UtilTool._ROLE_STU_ID))
            userType=2;
        else if(this.validateRole(request,UtilTool._ROLE_TEACHER_ID))
            userType=1;


        String timestamp=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("jid", tmpUser.getEttuserid().toString());
        map.put("oldUserName", oldUserName);
        map.put("newUserName", newUserName);
        map.put("oldPassword", oldPwd);
        map.put("newPassword", newPwd);
        map.put("type",userType.toString());

        map.put("timestamp",timestamp);
        String sign=UrlSigUtil.makeSigSimple("updateEttUserNameAndPassword.do",map);
        map.put("sign",sign);
        String ettUrl=UtilTool.utilproperty.get("ETT_INTER_IP").toString()+"updateEttUserNameAndPassword.do";
        JSONObject jsonObject=sendPostUrl(ettUrl,map,"utf-8");
        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
        if(type==0){
            Object jsonObj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
            jsonObj = URLDecoder.decode(jsonObj.toString(), "utf-8");
            je.getObjList().add(jsonObj);
        }else
            je.setType("success");

        response.getWriter().print(je.toJSON());
    }

    /**
     *生成公共家长邀请码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "genderInviteCode", method = {RequestMethod.POST})
    public void genderInviteCode(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        je.setType("success");
        je.getObjList().add(this.genderInviteCode());
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

    public  List<UserInfo> getETTUserList(String params){
        String ettUrl=UtilTool.utilproperty.get("GET_ETT_USER_LOCATION").toString();
        if(ettUrl==null)return null;

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(ettUrl.toString());

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
       // System.out.println(returnContent);
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

    public static JSONObject sendPostUrl(String urlstr,Map<String,String> paramMap,String requestEncoding){
        HttpURLConnection httpConnection=null;
        URL url;
        int code;
        try {
            //组织参数
            StringBuffer params = new StringBuffer();
            if(paramMap!=null&&paramMap.size()>0){
                for (Iterator iter = paramMap.entrySet().iterator(); iter
                        .hasNext();)
                {
                    Map.Entry element = (Map.Entry) iter.next();
                    params.append(element.getKey().toString());
                    params.append("=");
                    params.append(URLEncoder.encode(element.getValue().toString(), requestEncoding));
                    params.append("&");
                }

                if (params.length() > 0)
                {
                    params = params.deleteCharAt(params.length() - 1);
                }
            }

            url = new URL(urlstr);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
            if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.toString().length()));
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
                outputStreamWriter.write(params.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("异常错误!TOTALSCHOOL未响应!");
            if(httpConnection!=null)httpConnection.disconnect();
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
                if(httpConnection!=null)httpConnection.disconnect();
            } catch (IOException e) {
                System.out.println("异常错误!");
                if(httpConnection!=null)httpConnection.disconnect();
                return null;
            }
        }else if(code==404){
            if(httpConnection!=null)httpConnection.disconnect();
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return null;
        }else if(code==500){
            if(httpConnection!=null)httpConnection.disconnect();
            System.out.println("异常错误!500错误，请联系管理人员!");
            return null;
        }
        String returnContent=null;
        try {
            //returnContent=java.net.URLDecoder.decode(stringBuffer.toString(),"UTF-8");  ///aa
            returnContent=new String(stringBuffer.toString().getBytes("gbk"),requestEncoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //转换成JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        System.out.println("tpuser JSONObject:"+jb.toString());
        return jb;
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
        sel.setEttuserid(Integer.parseInt(userid));
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
        System.out.println("BindEttUser ettUserList.size()"+ettUserList.size());
        return false;
    }


    /**
     * userType 1:教师 2:教务 3:学生
     * @param userList
     * @param schoolid
     * @param userType
     * @return
     */
    public boolean BindEttUser(List<UserInfo>userList,String schoolid,String userType){
        if(userList==null||userList.size()<1)return false;

        JSONArray array=new JSONArray();
        for(UserInfo u:userList){
            JSONObject obj = new JSONObject();
            obj.element("jid", u.getEttuserid());
            obj.element("userId", u.getUserid());
            obj.element("schoolId", u.getDcschoolid());
            obj.element("userType",userType);
            array.add(obj);
        }
        String timestamp=System.currentTimeMillis()+"";
        HashMap<String,String>map=new HashMap<String, String>();
        map.put("schoolId", schoolid);
        map.put("userType",userType);
        map.put("timestamp",timestamp);
        String sign=UrlSigUtil.makeSigSimple("backbind.do",map);
        String ettUrl=UtilTool.utilproperty.get("BIND_ETT_USER_LOCATION").toString();
        String ettParams="schoolId="+schoolid+"&userType="+userType+"&timestamp="+timestamp+"&sign="+sign+"&userList="+array.toString();
        System.out.println(ettUrl + "?" + ettParams);
        return sendValidateUserInfoTotalSchool(ettUrl, ettParams);
    }


    public  List<Map<String,Object>> getClassUserMap(String relationtype,Integer classid){
        List<Map<String,Object>>mapList=null;
        ClassUser cu=new ClassUser();
        cu.setClassid(classid);
        //cu.setRelationtype(relationtype);
        List<ClassUser> cuBanList=this.classUserManager.getList(cu,null);
        if(cuBanList!=null&&cuBanList.size()>0){
            mapList=new ArrayList<Map<String, Object>>();
            for(ClassUser tmp:cuBanList){
                // 必带 userId，userType,subjectId 的三个key
                Map<String,Object> reMap=new HashMap<String, Object>();
                reMap.put("userId",tmp.getUid());
                reMap.put("subjectId",tmp.getSubjectid()==null?-1:tmp.getSubjectid());
                reMap.put("userType",tmp.getRelationtype().equals("学生")?3:tmp.getRelationtype().equals("任课老师")?2:tmp.getRelationtype().equals("班主任")?1:null);
                mapList.add(reMap);
            }
        }
        return mapList;
    }




    public  boolean OperateGroupUser(Integer classid,Integer schoolid){
        String addToEtt_URL=UtilTool.utilproperty.getProperty("TO_ETT_GROUPUSER_INTERFACE").toString();
        if(schoolid==null||classid==null)return false;
        TpGroupInfo groupInfo=new TpGroupInfo();
        groupInfo.setClassid(classid);
        List<TpGroupInfo> groupInfoList=this.groupManager.getList(groupInfo,null);
        if(groupInfoList!=null&&groupInfoList.size()>0){
            for(TpGroupInfo group:groupInfoList){
                HashMap<String,String> paramMap=new HashMap<String,String>();
                paramMap.put("time",new Date().getTime()+"");
                Map<String,Object> tmpMap=new HashMap<String, Object>();
                tmpMap.put("groupId",group.getGroupid());
                tmpMap.put("classId",classid);
                tmpMap.put("schoolId",schoolid);

                TpGroupStudent groupStudent=new TpGroupStudent();
                groupStudent.setGroupid(group.getGroupid());
                List<TpGroupStudent>gsList=this.groupStudentManager.getList(groupStudent,null);
                List<Map<String,Object>> userList=null;
                if(gsList!=null&&gsList.size()>0){
                    userList=new ArrayList<Map<String, Object>>();
                    for(TpGroupStudent tgs:gsList){
                        if(tgs!=null){
                            //必带 userId，userType 的key
                            Map<String,Object> map=new HashMap<String, Object>();
                            map.put("userId",tgs.getUserid());
                            map.put("userType",3);   //3:学生
                            userList.add(map);
                        }
                    }

                }
                if(userList!=null&&userList.size()>0)
                    tmpMap.put("userList",JSONArray.fromObject(userList).toString());
                paramMap.put("data",JSONObject.fromObject(tmpMap).toString());
                String val = UrlSigUtil.makeSigSimple("jionGroupUserInterFace.do", paramMap);
                paramMap.put("sign",val);
                JSONObject jo=UtilTool.sendPostUrl(addToEtt_URL,paramMap,"UTF-8");
                if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1")){
                    System.out.println("Operate Ett GroupStudent Success--------------------------");
                }else
                    System.out.println("Operate Ett GroupStudent Error----------------------------");
                if(jo!=null)
                    System.out.println(jo.get("msg"));
            }
        }
        return true;
    }

    /**
     * 生成公共班级家长邀请码
     * @return
     */
    private  String genderInviteCode(){
        while (true){
            String code=UtilTool.getFixLenthString(6);
            ClassInfo cls=new ClassInfo();
            cls.setImvaldatecode(code);
            List<ClassInfo>clsList=this.classManager.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                return code;
            }
        }
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
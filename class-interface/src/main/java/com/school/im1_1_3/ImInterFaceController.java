package com.school.im1_1_3;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.im1_1.manager._interface.ImInterfaceManager;
import com.school.manager.inter.*;
import com.school.util.UtilTool;
import com.school.utils.EttInterfaceUserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by zhengzhou on 14-12-1.
 */
@Controller
@RequestMapping(value="/im1.1.3")
public class ImInterFaceController extends BaseController {
    @Resource(name="classManager")
    protected IClassManager classManager;
    @Resource(name="userManager")
    protected IUserManager userManager;
    @Resource(name="userIdentityManager")
    protected IUserIdentityManager userIdentityManager;
    @Resource(name="teacherManager")
    protected ITeacherManager teacherManager;
    @Resource(name="roleUserManager")
    protected IRoleUserManager roleUserManager;
    @Resource(name="roleColumnRightManager")
    protected  IRoleColumnRightManager roleColumnRightManager;
    @Resource(name="userColumnRightManager")
    protected  IUserColumnRightManager userColumnRightManager;
    @Resource(name="gradeManager")
    protected IGradeManager gradeManager;
    @Resource(name="termManager")
    protected ITermManager termManager;
    @Resource(name="classUserManager")
    protected IClassUserManager classUserManager;
    @Resource(name="studentManager")
    protected  IStudentManager studentManager;
    @Resource(name="imInterfaceManager")
    protected ImInterfaceManager imInterfaceManager;
    @Resource(name="myInfoUserManager")
    protected IMyInfoUserManager myInfoUserManager;

    public static void main(String[] args){
        Map<String,Object> a=new HashMap<String, Object>();
        a.put("name","基本原则栽工");
        a.put("sex",1);
        a.put("son","[]");
        System.out.println(JSONObject.fromObject(a).toString());

    }
    /**
     * 修改教师身份信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=updateTeacherClassSubject.do",method=RequestMethod.POST)
    @Transactional
    public void updateTeacherClassSubject(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        returnJo.put("msg","");
        //是否有参数是空传入
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //验证必填参数
        if(!paramMap.containsKey("jid")||paramMap.get("jid")==null||paramMap.get("jid").trim().length()<1
                ||!paramMap.containsKey("targetJid")||paramMap.get("targetJid")==null||paramMap.get("targetJid").trim().length()<1
                ||!paramMap.containsKey("schoolId")||paramMap.get("schoolId")==null||paramMap.get("schoolId").trim().length()<1
                ||!paramMap.containsKey("classId")||paramMap.get("classId")==null||paramMap.get("classId").trim().length()<1
                ||(!paramMap.containsKey("subjectId")&&!paramMap.containsKey("isAdmin")
                ||(
                (paramMap.get("subjectId")==null||paramMap.get("subjectId").trim().length()<1)
                        &&
                        (paramMap.get("isAdmin")==null||paramMap.get("isAdmin").trim().length()<1
                                ||Integer.parseInt(paramMap.get("isAdmin"))==0)
        )
        )
                ||(
                paramMap.get("subjectId")==null||(
                        paramMap.get("subjectId").trim().length()<1
                                &&Integer.parseInt(paramMap.get("isAdmin").trim())==0
                )
        )
                ||!paramMap.containsKey("time")
                ||!paramMap.containsKey("sign")){
            returnJo.put("msg","参数异常，参数有空值!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","访问超时! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //删除sign
        paramMap.remove("sign");
        //根据参数生前sign，与传入的sign进行对比
        if(!UrlSigUtil.verifySigSimple("updateTeacherClassSubject.do", paramMap, sign)){
            returnJo.put("msg","验证失败!");//md5比较异常
            response.getWriter().print(returnJo.toString());
            return;
        }
        //验证该JID是否有用户
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","不存在该用户!");
            response.getWriter().println(returnJo.toString());return;
        }
        UserInfo activeUser=uList.get(0);
        //验证班级是否存在
        ClassInfo cls=new ClassInfo();
        cls.setClassid(Integer.parseInt(paramMap.get("classId").trim()));
        cls.setDcschoolid(u.getDcschoolid());
        List<ClassInfo> clsList=this.classManager.getIm113List(cls,null);
        if(clsList==null||clsList.size()<1){
            returnJo.put("msg","不存在该班级!");
            response.getWriter().println(returnJo.toString());return;
        }
        cls=clsList.get(0);
        //验证 targetJid是否有用户
        UserInfo targetu=new UserInfo();
        targetu.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        targetu.setEttuserid(Integer.parseInt(paramMap.get("targetJid").trim()));
        List<UserInfo> targetUList=this.userManager.getList(targetu,null);
        if(targetUList==null||targetUList.size()<1){
            returnJo.put("msg","不存在该用户!");
            response.getWriter().println(returnJo.toString());return;
        }
        targetu=targetUList.get(0);
        //得到目标用户是否存在于该班级
        ClassUser cu=new ClassUser();
        cu.setClassid(cls.getClassid());
        cu.setUserid(targetu.getRef());
        List<ClassUser> cuList=this.classUserManager.getList(cu,null);
        if(cuList==null||cuList.size()<1){
            returnJo.put("msg","该班级不存在目标用户!");
            response.getWriter().println(returnJo.toString());return;
        }
        //如果存在，则先删除，再添加
        if(!this.classUserManager.doDelete(cu)){
            returnJo.put("msg","删除目标用户在该班级的信息失败!");
            response.getWriter().println(returnJo.toString());return;
        }
        boolean isAdminTrue=true;
        if(Integer.parseInt(paramMap.get("isAdmin").trim())==1){
            //验证是否存在班主任
            ClassUser searchBan=new ClassUser();
            searchBan.setClassid(cls.getClassid());
            searchBan.setRelationtype("班主任");
            List<ClassUser> bancuList=this.classUserManager.getList(searchBan,null);
            if(bancuList!=null&&bancuList.size()>0){
                returnJo.put("msg","本班已存在班主任，不能担任班主任!");
                isAdminTrue=false;
            }else{
                ClassUser saveCu=new ClassUser();
                //UUID,生成
                saveCu.setRef(UUID.randomUUID().toString());
                saveCu.setClassid(cls.getClassid());
//                saveCu.setUid(activeUser.getUserid());
                saveCu.setUserid(targetu.getRef());
                saveCu.setRelationtype("班主任");
                //  saveCu.setSubjectid(Integer.parseInt(sub.trim()));
                if(!this.classUserManager.doSave(saveCu)){
                    transactionRollback();
                    returnJo.put("msg","修改班主任失败!");
                    response.getWriter().println(returnJo.toString());
                    return;
                }
//              returnJo.put("msg","添加班主任成功!");
            }
        }


        //添加
        //如果存在subjectid,说明是老师

        if(paramMap.get("subjectId")!=null&&paramMap.get("subjectId").trim().length()>0){
            String[] subArray=paramMap.get("subjectId").split(",");
            for(String sub:subArray){
                if(sub!=null&&sub.trim().length()>0){
                    ClassUser saveCu=new ClassUser();
                    //UUID,生成
                    saveCu.setRef(UUID.randomUUID().toString());
                    saveCu.setClassid(cls.getClassid());
                    saveCu.setUserid(targetu.getRef());
                    saveCu.setUid(activeUser.getUserid());
                    saveCu.setRelationtype("任课老师");
                    saveCu.setSubjectid(Integer.parseInt(sub.trim()));
                    if(!this.classUserManager.doSave(saveCu)){
                        transactionRollback();
                        returnJo.put("msg","修改班级人员失败!");
                        response.getWriter().println(returnJo.toString());
                        return;
                    }
                }
            }
        }else{
            if(!isAdminTrue){
                returnJo.put("msg","修改信息失败!"+returnJo.get("msg"));
                returnJo.put("result",0);
                transactionRollback();
                response.getWriter().println(returnJo.toString());
                return;
            }
        }
        //查询该班级人员，向ETT进行同步

        ClassUser searchCu=new ClassUser();
        searchCu.setClassid(cls.getClassid());
        List<ClassUser> searchCuList=this.classUserManager.getList(cu,null);
        if(!updateEttClassUser(searchCuList,cls.getClassid(),u.getDcschoolid())){
            //回滚事物
            transactionRollback();
            returnJo.put("msg","向网校添加班级人员信息失败!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        returnJo.put("msg","信息修改成功!"+returnJo.get("msg"));
        returnJo.put("result",1);

        response.getWriter().println(returnJo.toString());
    }

    /**
     * 测试类
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=createClass.do",method=RequestMethod.POST)
    @Transactional
    public void createClass(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        //是否有参数是空传入
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //验证必填参数
        if(!paramMap.containsKey("jid")
                ||!paramMap.containsKey("schoolId")
                ||!paramMap.containsKey("gradeId")
                ||!paramMap.containsKey("className")
                ||!paramMap.containsKey("subjectId")
                ||!paramMap.containsKey("isAdmin")
                ||paramMap.get("isAdmin").trim().length()<1
                ||(
                (paramMap.get("subjectId")==null||paramMap.get("subjectId").trim().length()<1)
                        &&Integer.parseInt(paramMap.get("isAdmin").trim())==0
        )
                ||!paramMap.containsKey("time")
                ||!paramMap.containsKey("sign")){
            returnJo.put("msg","参数异常，参数有空值!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","访问超时! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //删除sign
        paramMap.remove("sign");
        //根据参数生前sign，与传入的sign进行对比
        if(!UrlSigUtil.verifySigSimple("createClass.do", paramMap, sign)){
            returnJo.put("msg","验证失败!");//md5比较异常
            response.getWriter().print(returnJo.toString());
            return;
        }
        //验证该JID是否有用户
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","不存在该用户! jid:"+u.getEttuserid()+" ");
            response.getWriter().println(returnJo.toString());return;
        }
        UserInfo activeUser=uList.get(0);
        //年级得到
        GradeInfo g=new GradeInfo();
        g.setGradeid(Integer.parseInt(paramMap.get("gradeId").toString()));
        List<GradeInfo> gList=gradeManager.getList(g,null);
        if(gList==null||gList.size()<1){
            returnJo.put("msg","不存在该年级!");
            response.getWriter().println(returnJo.toString());return;
        }
        String gradeName=gList.get(0).getGradevalue();
        String termYear=termManager.getAutoTerm().getYear();

        //验证该班级的数量是否已达上限
        if (activeUser.getDcschoolid() >= 50000) {
            int  existClassCount = this.classManager.getTotalClass(activeUser.getDcschoolid(), termYear, 1);
            HashMap<String,String> inteMap=new HashMap<String,String>();
            inteMap.put("time",System.currentTimeMillis()+"");
            inteMap.put("schoolId",activeUser.getDcschoolid().toString());
            inteMap.put("year",termYear);
            String val = UrlSigUtil.makeSigSimple("groupInterFace.do", inteMap);
            paramMap.put("sign",val);
            // http\://int.etiantian.com\:34180/totalSchool/ cls?m=getClsNum&schoolid=&year=
            String url=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
            //http://localhost:8080/totalSchool/franchisedSchool?m=getTC
            url +="franchisedSchool?m=getTC";
            net.sf.json.JSONObject jo=sendPostUrl(url, inteMap, "UTF-8");
            if(jo!=null&&jo.containsKey("result")){
                int  maxClass = Integer.valueOf(jo.get("result").toString());
                if(existClassCount>=maxClass){
                    returnJo.put("msg","该分校允许的班级数量已达上限!无法再创建班级!");
                    response.getWriter().println(returnJo.toString());return;
                }
            }else{
                returnJo.put("msg","异常，访问总校得到该班级总数失败!");
                response.getWriter().println(returnJo.toString());return;
            }
        }
        //组织数据，准备添加
        int newClsId=genderIdCode(6);
        //难道当前学年的当前年级是否存在该班级名称
        ClassInfo cls=new ClassInfo();
        cls.setDcschoolid(u.getDcschoolid());
        cls.setClassname(paramMap.get("className").trim());
        cls.setClassgrade(gradeName);
        List<ClassInfo> clsList=this.classManager.getList(cls,null);
        if(clsList!=null&&clsList.size()>0){
            returnJo.put("msg",gradeName+cls.getClassname()+" 已经存在!");
            response.getWriter().println(returnJo.toString());return;
        }
        cls.setClassid(newClsId);
        cls.setPattern("行政班");
        cls.setClassid(newClsId);
        cls.setType("NORMAL");
        cls.setYear(termYear);
        cls.setAllowjoin(1);//允许学生加入
        cls.setCuserid(activeUser.getUserid());
        cls.setIsflag(1);
        cls.setImvaldatecode(genderInviteCode(6));//生成随机六位数，保证不重复
        int dcType=1;
        Object ettschoolid=UtilTool.utilproperty.get("PUBLIC_SCHOOL_ID").toString();
        if(ettschoolid!=null&&ettschoolid.toString().trim().length()>0){
            String[] ettSchoolIdArray=ettschoolid.toString().trim().split(",");
            for(String etsid:ettSchoolIdArray){
                if(etsid!=null&&etsid.trim().length()>0){
                    if(Integer.parseInt(etsid.trim())==u.getDcschoolid().intValue()){
                        dcType=2;break;
                    }
                }
            }
        }
        cls.setDctype(dcType);
        if(!this.classManager.doSave(cls)){
            returnJo.put("msg","创建班级失败!");
            response.getWriter().println(returnJo.toString());
            transactionRollback();
            return;
        }
        //添加教师记录
        //查询已经在的人员
        ClassUser classUser=new ClassUser();
        classUser.setClassid(newClsId);
        List<ClassUser> tmpCuList=this.classUserManager.getList(classUser,null);
        if(tmpCuList==null||tmpCuList.size()<1){
            tmpCuList=new ArrayList<ClassUser>();
        }
        //如果存在subjectid,说明是老师
        if(Integer.parseInt(paramMap.get("isAdmin").trim())==1){
            ClassUser saveCu=new ClassUser();
            //UUID,生成
            saveCu.setRef(UUID.randomUUID().toString());
            saveCu.setClassid(newClsId);
            saveCu.setUid(activeUser.getUserid());
            saveCu.setUserid(activeUser.getRef());
            saveCu.setRelationtype("班主任");
            //  saveCu.setSubjectid(Integer.parseInt(sub.trim()));
            if(!this.classUserManager.doSave(saveCu)){
                returnJo.put("msg","添加班级人员失败!");
                response.getWriter().println(returnJo.toString());
                transactionRollback();
                return;
            }
            tmpCuList.add(saveCu);
        }

        if(paramMap.get("subjectId")!=null&&paramMap.get("subjectId").trim().length()>0){
            String[] subArray=paramMap.get("subjectId").split(",");
            for(String sub:subArray){
                if(sub!=null&&sub.trim().length()>0){
                    ClassUser saveCu=new ClassUser();
                    //UUID,生成
                    saveCu.setRef(UUID.randomUUID().toString());
                    saveCu.setClassid(newClsId);
                    saveCu.setUserid(activeUser.getRef());
                    saveCu.setUid(activeUser.getUserid());
                    saveCu.setRelationtype("任课老师");
                    saveCu.setSubjectid(Integer.parseInt(sub.trim()));
                    if(!this.classUserManager.doSave(saveCu)){
                        returnJo.put("msg","添加班级人员失败!");
                        response.getWriter().println(returnJo.toString());
                        transactionRollback();
                        return;
                    }
                    tmpCuList.add(saveCu);
                }
            }
        }

        //学生加入班级动态
        MyInfoUserInfo info=new MyInfoUserInfo();
        info.setUserref(activeUser.getRef());
        info.setMsgid(-1);
        info.setMsgname("IM通知");
        info.setMydata(activeUser.getRealname()+"|"+cls.getClassgrade()+cls.getClassname());
        info.setTemplateid(18);
        info.setClassid(cls.getClassid());
        if(!this.myInfoUserManager.doSave(info)){
            returnJo.put("msg","添加动态失败!");
            response.getWriter().println(returnJo.toString());
            transactionRollback();
            return;
        }
        //如果都执行成功，则
        if(!addClassBaseToEtt(cls)){
            //回滚事物
            transactionRollback();
            returnJo.put("msg","向网校添加班级信息失败!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        //如果成功，则传入人员数据
        if(tmpCuList!=null&&tmpCuList.size()>0){
            if(!updateEttClassUser(tmpCuList,cls.getClassid(),u.getDcschoolid())){
                //回滚事物
                transactionRollback();
                returnJo.put("msg","添加班级人员信息失败!");
                response.getWriter().println(returnJo.toString());
                return;
            }
        }
        returnJo.put("result",1);
        returnJo.put("msg","添加班级相关信息成功!");
        //返回的信息
        JSONObject dataJSON=new JSONObject();
        dataJSON.put("classId",cls.getClassid());
        dataJSON.put("inviteCode",cls.getImvaldatecode());
        returnJo.put("data",dataJSON.toString());
        response.getWriter().println(returnJo.toString());
    }


    /**
     * M-SCH61.查询教师管理班级列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=manageClassList.do")
    public void manageClassList(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        //是否有参数是空传入
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //验证必填参数
        if(!paramMap.containsKey("jid")
                ||!paramMap.containsKey("schoolId")
                ||!paramMap.containsKey("time")
                ||!paramMap.containsKey("sign")){
            returnJo.put("msg","参数异常，参数有空值!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","访问超时! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //删除sign
        paramMap.remove("sign");
        //根据参数生前sign，与传入的sign进行对比
        if(!UrlSigUtil.verifySigSimple("manageClassList.do", paramMap, sign)){
            returnJo.put("msg","验证失败!");//md5比较异常
            response.getWriter().print(returnJo.toString());
            return;
        }
        //验证该JID是否有用户
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","不存在该用户!");
            response.getWriter().println(returnJo.toString());return;
        }
        UserInfo activeUser=uList.get(0);
        //
        ClassInfo cls=new ClassInfo();
        cls.setDcschoolid(activeUser.getDcschoolid());
        cls.setCuserid(activeUser.getUserid());  //查询动态的时候用到
        cls.setSearchUid(activeUser.getUserid());//c_user_id根据userid查询
        List<ClassInfo> clsList=this.classManager.getIm113List(cls, null);
        JSONArray jsonArray=new JSONArray();
        if(clsList!=null&&clsList.size()>0){
            for(ClassInfo tmpCls:clsList){
                if(tmpCls!=null){
                    JSONObject jo=new JSONObject();
                    jo.put("classId",tmpCls.getClassid());
                    jo.put("className",tmpCls.getClassname()==null?"":(tmpCls.getClassgrade()+tmpCls.getClassname()));
                    jo.put("schoolId",tmpCls.getDcschoolid());
                    jo.put("classType",tmpCls.getDctype());
                    //查询动态数量
                    jo.put("newDynamic",tmpCls.getDynamicCount()==null?0:tmpCls.getDynamicCount());
                    jsonArray.add(jo);
                }
            }
        }
        returnJo.put("result",1);
        returnJo.put("msg","查询成功!");
        returnJo.put("data",jsonArray.toString());
        response.getWriter().println(returnJo.toString());

    }


    /**
     * 学习目录接口
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=StudyModule")
    public void getStudyModule(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String userid = request.getParameter("jid");
        String usertype=request.getParameter("userType");
        String schoolid = request.getParameter("schoolId");
        String timestamp = request.getParameter("time");
        String sig = request.getParameter("sign");
        String lastAccessTime = request.getParameter("lastAccessTime");

        if(!ImInterFaceUtil.ValidateRequestParam(request)){
            JSONObject jo=new JSONObject();
            jo.put("result","0");
            jo.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            jo.put("data","");
            response.getWriter().print(jo.toString());
            return;
        }
        HashMap<String,String> map = new HashMap();
        map.put("jid",userid);
        map.put("userType",usertype);
        map.put("schoolId",schoolid);
        map.put("time",timestamp);
        map.put("lastAccessTime",lastAccessTime);
        String sign = UrlSigUtil.makeSigSimple("StudyModule",map,"*ETT#HONER#2014*");
        Boolean b = UrlSigUtil.verifySigSimple("StudyModule",map,sig);
        if(!b){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"验证失败，非法登录\"}");
            return;
        }
        int utype=ImInterFaceUtil.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"当前用户未绑定，请联系管理员\"}");
            return;
        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setUserid(userList.get(0).getUserid());
        obj.setUsertype(utype);
        List<Map<String,Object>> list = this.imInterfaceManager.getStudyModule(obj);
        if(list!=null&&list.size()>0){
            for(Map map1:list){
                map1.put("SCHOOLID" ,schoolid);
            }
        }
        Map m = new HashMap();
        Map m2 = new HashMap();
        m2.put("showManageClass",validateHasSchool(Integer.parseInt(schoolid.trim())));
        TermInfo tm=this.termManager.getAutoTerm();
        m2.put("newDynamicAll",this.myInfoUserManager.getImMsgData(null,userList.get(0).getUserid(),tm.getYear()));//总的数量
        if(list!=null&&list.size()>0){
            m2.put("classes",list);
            //String etturl = "http://192.168.10.59/study-im-service-1.0/activityNotifyNum.do";
            String etturl = UtilTool.utilproperty.getProperty("ETT_GET_NOTIFYNUM_IP");
            HashMap<String,String> ettMap = new HashMap();
            ettMap.put("jid",userid);
            ettMap.put("userType",usertype);
            ettMap.put("schoolId",schoolid);
            ettMap.put("time",timestamp);
            if(lastAccessTime==null||lastAccessTime.length()<1){
                lastAccessTime="0";
            }
            ettMap.put("lastAccessTime",lastAccessTime);
            String ettSig = UrlSigUtil.makeSigSimple("activityNotifyNum.do",ettMap,"*ETT#HONER#2014*");
            ettMap.put("sign",ettSig);
            JSONObject ettJo = UtilTool.sendPostUrl(etturl,ettMap,"utf-8");
            if(ettJo!=null&&ettJo.toString().length()>0){
                int result = ettJo.containsKey("result")?ettJo.getInt("result"):0;
                if(result==1){
                    int notifynum = 0;
                    JSONObject data = ettJo.containsKey("data")?ettJo.getJSONObject("data"):null;
                    if(data!=null){
                        notifynum = data.containsKey("activityNotifyNum")?data.getInt("activityNotifyNum"):0;
                    }
                    m2.put("activityNotifyNum",notifynum);
                }else{
                    m2.put("activityNotifyNum",-1);
                }
            }else{
                m2.put("activityNotifyNum",-1);
            }
        }else{
            m.put("result","0");
            m.put("msg","当前用户没有学习目录，请联系管理员");
        }
        m.put("result","1");
        m.put("msg","成功");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.setContentType("text/json");
        response.getWriter().print(object.toString());
    }

    /**
     * IM-SCH56.学生加入班级接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=studentJoinClass.do",method=RequestMethod.POST)
    @Transactional
    public void studentJoinClass(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //userType   1:学生  2：老师  3：家长
        response.getWriter().println(joinClass(request,1,"studentJoinClass.do"));
    }
    /**
     * IM-SCH55.教师加入班级接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=teacherJoinClass.do",method=RequestMethod.POST)
    @Transactional
    public void teacherJoinClass(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //userType   1:学生  2：老师  3：家长
        response.getWriter().println(joinClass(request,2,"teacherJoinClass.do"));
    }
    /**
     * IM-SCH57.删除班级成员
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=removeClassMember.do")
    @Transactional
    public void removeClassMember(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        if(!ImInterFaceUtil.ValidateRequestParam(request)){
            returnJo.put("msg","参数异常，参数有空值!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        //是否有参数是空传入
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //验证必填参数
        if(!paramMap.containsKey("jid")
                ||!paramMap.containsKey("schoolId")
                ||!paramMap.containsKey("classId")
                ||!paramMap.containsKey("noJoin")
                ||!paramMap.containsKey("targetJid")
                ||!paramMap.containsKey("time")
                ||!paramMap.containsKey("sign")){
            returnJo.put("msg","参数异常，参数有空值!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","访问超时! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //删除sign
        paramMap.remove("sign");
        //根据参数生前sign，与传入的sign进行对比
        if(!UrlSigUtil.verifySigSimple("removeClassMember.do", paramMap, sign)){
            returnJo.put("msg","验证失败!");//md5比较异常
            response.getWriter().print(returnJo.toString());
            return;
        }
        //验证该JID是否有用户
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","不存在该用户! jid:"+u.getEttuserid()+" ");
            response.getWriter().println(returnJo.toString());return;
        }
        //得到的访问用户
        UserInfo activeUser=uList.get(0);
        //得到班级
        ClassInfo cls=new ClassInfo();
        cls.setClassid(Integer.parseInt(paramMap.get("classId")));
        cls.setDcschoolid(u.getDcschoolid());
        //cls.setSearchUid(activeUser.getUserid());
        cls.setCuserid(activeUser.getUserid());
        List<ClassInfo> clsList=this.classManager.getIm113List(cls,null);
        if(clsList==null||clsList.size()<1){
            returnJo.put("msg","不存在班级，可能班级与分校不匹配!");
            response.getWriter().println(returnJo.toString());return;
        }
        cls=clsList.get(0);
        //验证不能删除自已
        if(Integer.parseInt(paramMap.get("targetJid"))==Integer.parseInt(paramMap.get("jid").trim())){
            returnJo.put("msg","不能删除自己!");
            response.getWriter().println(returnJo.toString());return;
        }


        //检测targetJid
        UserInfo targetUInfo=new UserInfo();
        targetUInfo.setEttuserid(Integer.parseInt(paramMap.get("targetJid")));
        targetUInfo.setDcschoolid(u.getDcschoolid());
        List<UserInfo> targetUList=this.userManager.getList(targetUInfo,null);
        if(targetUList==null||targetUList.size()<1){
            returnJo.put("msg","目标用户不存在!");
            response.getWriter().println(returnJo.toString());return;
        }
        targetUInfo=targetUList.get(0);

        //检测targetJid是否存在于该班级下
        ClassUser targetCu=new ClassUser();
        targetCu.setClassid(cls.getClassid());
        targetCu.setUserid(targetUInfo.getRef());
        List<ClassUser> targetCuList=this.classUserManager.getList(targetCu,null);
        if(targetCuList==null||targetCuList.size()<1){
            returnJo.put("msg","目标用户不存在于目标班级中!");
            response.getWriter().println(returnJo.toString());return;
        }


        //将该用户删除
        if(!this.classUserManager.doDelete(targetCu)){
            transactionRollback();
            returnJo.put("msg","操作失败,原因：删除班级关联数据失败!");
            response.getWriter().println(returnJo.toString());return;
        }
        //添加记录
        if(Integer.parseInt(paramMap.get("noJoin").trim())==1){
            boolean isSave=this.myInfoUserManager.doSaveUserClassInJoinLog
                    (targetUInfo.getUserid(),cls.getClassid(),cls.getDcschoolid(),Integer.parseInt(paramMap.get("noJoin").trim()),activeUser.getUserid());
            if(!isSave){
                transactionRollback();
                returnJo.put("msg","添加失败!");
                response.getWriter().println(returnJo.toString());return;
            }
        }
        //发送数据至Ett
        ClassUser cuTmp=new ClassUser();
        cuTmp.setClassid(cls.getClassid());
        List<ClassUser> cuTmpList=this.classUserManager.getList(cuTmp,null);
        if(cuTmpList==null)
            cuTmpList=new ArrayList<ClassUser>();
        if(!updateEttClassUser(cuTmpList,cls.getClassid(),cls.getDcschoolid())){
            transactionRollback();
            returnJo.put("msg","操作失败!");
            response.getWriter().println(returnJo.toString());return;
        }
        returnJo.put("result",1);
        returnJo.put("msg","操作成功!");
        response.getWriter().println(returnJo.toString());
    }
    /**
     * IM-SCH57.班级详情列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=classDetailInfo.do")
    public void classDetailInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        //是否有参数是空传入
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //验证必填参数
        if(!paramMap.containsKey("jid")
                ||!paramMap.containsKey("schoolId")
                ||!paramMap.containsKey("classId")
                ||!paramMap.containsKey("time")
                ||!paramMap.containsKey("sign")){
            returnJo.put("msg","参数异常，参数有空值!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg","访问超时! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //删除sign
        paramMap.remove("sign");
        //根据参数生前sign，与传入的sign进行对比
        System.out.println(UrlSigUtil.makeSigSimple("classDetailInfo.do", paramMap));
        if(!UrlSigUtil.verifySigSimple("classDetailInfo.do", paramMap, sign)){
            returnJo.put("msg","验证失败!");//md5比较异常
            response.getWriter().print(returnJo.toString());
            return;
        }
        //验证该JID是否有用户
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","不存在该用户!");
            response.getWriter().println(returnJo.toString());return;
        }
        //得到的访问用户
        UserInfo activeUser=uList.get(0);
        //得到班级
        ClassInfo cls=new ClassInfo();
        cls.setClassid(Integer.parseInt(paramMap.get("classId")));
        cls.setDcschoolid(u.getDcschoolid());
        cls.setCuserid(activeUser.getUserid());
        List<ClassInfo> clsList=this.classManager.getIm113List(cls,null);
        if(clsList==null||clsList.size()<1){
            returnJo.put("msg","不存在班级，可能班级与分校不匹配!");
            response.getWriter().println(returnJo.toString());return;
        }
        cls=clsList.get(0);
        JSONObject clsDetailJo=new JSONObject();
        clsDetailJo.put("className",(cls.getClassname()));
        clsDetailJo.put("classCode",cls.getImvaldatecode());
        System.out.println(cls.getDynamicCount());
        clsDetailJo.put("newDynamic",cls.getDynamicCount()==null?0:cls.getDynamicCount());//新消息动态
        //该班级的所有老师(班主任，任课老师)
        ClassUser cu=new ClassUser();
        cu.setClassid(cls.getClassid());
        //班主任
        cu.setRelationtype("班主任");
        List<ClassUser> banCuList=this.classUserManager.getList(cu, null);

        List<Map<String,Object>> classUserMapList=new ArrayList<Map<String, Object>>();
        if(banCuList!=null&&banCuList.size()>0){
            //userType  1:学生  2：教师
            List<Map<String,Object>> tmpList=ImInterFaceUtil
                    .getClassUserEttPhoneAndName(banCuList, 3, activeUser.getDcschoolid(), activeUser.getEttuserid());
            classUserMapList.addAll(tmpList);
        }
        //班主任
        cu.setRelationtype("任课老师");
        List<ClassUser> teaCuList=this.classUserManager.getList(cu,null);
        if(teaCuList!=null&&teaCuList.size()>0){
            //userType  1:学生  2：教师
            List<Map<String,Object>> tmpList=ImInterFaceUtil
                    .getClassUserEttPhoneAndName(teaCuList, 2, activeUser.getDcschoolid(), activeUser.getEttuserid());
            classUserMapList.addAll(tmpList);
        }
        JSONArray userListArray=new JSONArray();
        JSONObject tmpJo=new JSONObject();
        tmpJo.put("title",("老师"));


        //合并
        String jidHas="";
        List<Map<String,Object>> teaClassMapList=new ArrayList<Map<String, Object>>();;
        if(classUserMapList!=null&&classUserMapList.size()>0){

            for(int i=0;i<classUserMapList.size();i++){
                Map<String,Object> tmpMap=classUserMapList.get(i);
                if(tmpMap==null
                        ||!tmpMap.containsKey("jid")
                        ||tmpMap.get("jid")==null||tmpMap.get("jid").toString().trim().length()<1){
                    continue;
                }
                String sub=null;
                if(tmpMap.get("subject")!=null&&tmpMap.get("subject").toString().trim().length()>0)
                    sub=tmpMap.get("subject").toString().trim();
                for(int j=i+1;j<classUserMapList.size();j++){
                    Map<String,Object> mp=classUserMapList.get(j);
                    if(mp==null||!mp.containsKey("subject")
                            ||!mp.containsKey("jid")
                            ||mp.get("jid")==null||mp.get("jid").toString().trim().length()<1){
                        continue;
                    }
                    String jmp=mp.get("jid").toString();
                    String imp=tmpMap.get("jid").toString();
                    if(Integer.parseInt(imp.trim())==Integer.parseInt(jmp)){
                        if(mp.get("subject")!=null&&mp.get("subject").toString().trim().length()>0){
                            if(sub==null)
                                sub=mp.get("subject").toString().trim();
                            else
                                sub+="、"+mp.get("subject").toString().trim();
                        }
                    }
                }
                if(sub!=null&&sub.trim().length()>0){
                    tmpMap.put("subject","("+sub+")");
                }
                if(jidHas==null||jidHas.toString().trim().length()<1||
                        (tmpMap.get("jid")!=null&&(","+jidHas).indexOf(","+ tmpMap.get("jid").toString()+",")<0)){
                    teaClassMapList.add(tmpMap);
                    jidHas+=tmpMap.get("jid")+",";
                }
            }
        }
        String classUserJson=null;
        if(teaClassMapList!=null&&teaClassMapList.size()>0){
            classUserJson=JSONArray.fromObject(teaClassMapList).toString();
        }
        tmpJo.put("count", teaClassMapList == null ? 0 : teaClassMapList.size());
        tmpJo.put("list",classUserJson);
        userListArray.add(tmpJo);
        //得到学生
        JSONObject tmpStuJo=new JSONObject();
        tmpStuJo.put("title",("学生"));
        cu.setRelationtype("学生");
        List<ClassUser> stuCuList=this.classUserManager.getList(cu,null);
        tmpStuJo.put("count",stuCuList==null?0:stuCuList.size());
        List<Map<String,Object>> tmpList=null;
        //userType  1:学生  2：教师
        if(stuCuList!=null&&stuCuList.size()>0){
            tmpList=ImInterFaceUtil
                    .getClassUserEttPhoneAndName(stuCuList, 1, activeUser.getDcschoolid(), activeUser.getEttuserid());
        }
        classUserJson=null;
        if(tmpList!=null&&tmpList.size()>0){
            classUserJson=JSONArray.fromObject(tmpList).toString();
        }
        tmpStuJo.put("list",classUserJson);
        userListArray.add(tmpStuJo);
        clsDetailJo.put("userList",userListArray.toString());
        //得到班级动态()
        String dymStr=null;
        MyInfoUserInfo mu=new MyInfoUserInfo();
        mu.setClassid(cls.getClassid());
        mu.setTemplateid(18);  //18:表示Im端动态

        List<MyInfoUserInfo> myInfoUserList=this.myInfoUserManager.getImMsgData(cls.getClassid(),activeUser.getUserid());
        if(myInfoUserList!=null&&myInfoUserList.size()>0){
            StringBuilder jids=new StringBuilder();
            List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
            StringBuilder stuJid=new StringBuilder("["),teaJid=new StringBuilder("[");
            for(MyInfoUserInfo tmpMyInfo:myInfoUserList){
                if(tmpMyInfo!=null){
                    Map<String,Object> returnMap=new HashMap<String, Object>();
                    if(tmpMyInfo.getEttuserid()!=null){
                        if(tmpMyInfo.getUserType()==1)
                            stuJid.append("{\"jid\":"+tmpMyInfo.getEttuserid()+"},");
                        if(tmpMyInfo.getUserType()==2)
                            teaJid.append("{\"jid\":"+tmpMyInfo.getEttuserid()+"},");
                        returnMap.put("jid",tmpMyInfo.getEttuserid());
                    }
                    returnMap.put("time",tmpMyInfo.getCtimeImString());
                    returnMap.put("message",
                            //         (tmpMyInfo.getRealname() + " 加入了 " + cls.getClassname() + "!", "UTF-8")
                            (tmpMyInfo.getDynamicString())
                    );
                    returnMap.put("photo", "http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
                    returnUserRecord.add(returnMap);
                }
            }
            //(ett)userType  3:学生  2：教师
            String jidstr ="";
            if(stuJid.toString().trim().length()>1){
                jidstr=stuJid.toString().substring(0,stuJid.toString().lastIndexOf(","))+"]";
                JSONArray jr = ImInterFaceUtil.getEttPhoneAndRealNmae
                        (jidstr, activeUser.getDcschoolid().toString(), activeUser.getEttuserid().toString(),3);
                if(jr!=null&&jr.size()>0){
                    for(int i = 0;i<jr.size();i++){
                        JSONObject jo = jr.getJSONObject(i);
                        for(int j = 0;j<returnUserRecord.size();j++){
                            if(jo.getInt("jid")==Integer.parseInt(returnUserRecord.get(j).get("jid").toString())){
                                returnUserRecord.get(j).put("message",
                                        (jo.getString("realName")+" 加入了 "+cls.getClassname()+"!"));
                                returnUserRecord.get(j).put("photo", jo.getString("headUrl"));
                            }
                        }
                    }
                }
            }
            jidstr ="";
            if(teaJid.toString().trim().length()>1){
                jidstr=teaJid.toString().substring(0,teaJid.toString().lastIndexOf(","))+"]";
                JSONArray jr = ImInterFaceUtil.getEttPhoneAndRealNmae
                        (jidstr, activeUser.getDcschoolid().toString(), activeUser.getEttuserid().toString(),2);
                if(jr!=null&&jr.size()>0){
                    for(int i = 0;i<jr.size();i++){
                        JSONObject jo = jr.getJSONObject(i);
                        for(int j = 0;j<returnUserRecord.size();j++){
                            if(jo.getInt("jid")==Integer.parseInt(returnUserRecord.get(j).get("jid").toString())){
                                returnUserRecord.get(j).put("message",
                                        (jo.getString("realName")+"加入了"+cls.getClassname()+"!"));
                                returnUserRecord.get(j).put("photo", jo.getString("headUrl"));
                            }
                        }
                    }
                }
            }
            dymStr=JSONArray.fromObject(returnUserRecord).toString();
        }
        clsDetailJo.put("classDynamic",dymStr);
        returnJo.put("result",1);
        returnJo.put("msg","操作成功!");
        returnJo.put("data",clsDetailJo.toString());
        response.getWriter().println(returnJo.toString());
    }


    /**
     * 学生，教师加入班级
     * @param request
     * @param userType   1:学生  2：老师  3：家长
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String joinClass(HttpServletRequest request,Integer userType,String managerKeyCode)throws Exception{
        HashMap<String,String> paraMap=ImInterFaceUtil.getRequestParam(request);
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        returnJo.put("msg","");
        if(paraMap==null||userType==null||(userType!=1&&userType!=2)){
            returnJo.put("msg","参数获取异常!");
            return returnJo.toString();
        }
        if(!paraMap.containsKey("jid")||paraMap.get("jid")==null||paraMap.get("jid").trim().length()<1
                ||!paraMap.containsKey("classCode")||paraMap.get("classCode")==null
                ||paraMap.get("classCode").trim().length()<1
                ||!paraMap.containsKey("time")||paraMap.get("time")==null||paraMap.get("time").trim().length()<1
                ||!paraMap.containsKey("sign")||paraMap.get("sign")==null||paraMap.get("sign").trim().length()<1){
            returnJo.put("msg","参数异常，请检测参数是否存在空值!");
            return returnJo.toString();
        }
        String isAdmin=paraMap.get("isAdmin");
        String subjectId=paraMap.get("subjectId");
        //如果是教师，则还有subjectId或isAdmin的判断
        if(userType==2){
            if(isAdmin==null&&subjectId==null
                    ||Integer.parseInt(isAdmin)==0&&(subjectId==null||subjectId.trim().length()<1)){
                returnJo.put("msg","参数异常，请检测参数是否存在空值!");
                return returnJo.toString();
            }
        }
        //相关验证
        String time=paraMap.get("time");
        //验证时间
        //验证是否在三分钟内
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            returnJo.put("msg", "访问超时! >3 min!");
            return returnJo.toString();
        }
        String sign=paraMap.get("sign");
        //删除sign
        paraMap.remove("sign");
        //根据参数生前sign，与传入的sign进行对比
        if(!UrlSigUtil.verifySigSimple(managerKeyCode, paraMap, sign)){
            returnJo.put("msg", "验证失败!");//md5比较异常
            return returnJo.toString();
        }
        //验证该JID是否有用户
        UserInfo u=new UserInfo();
//        u.setDcschoolid(Integer.parseInt(paraMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paraMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","不存在该用户!");
            return returnJo.toString();
        }
        UserInfo activeUser=uList.get(0);
        //验证班级
        ClassInfo cls=new ClassInfo();
        cls.setImvaldatecode(paraMap.get("classCode").trim());
        List<ClassInfo> clsList=this.classManager.getList(cls,null);
        if(clsList==null||clsList.size()<1){
            returnJo.put("msg","该邀请码无效!");
            return returnJo.toString();
        }

        cls=clsList.get(0);


        //验证该班级的创建人
        if(cls.getCuserid()!=null&&activeUser.getUserid()!=null&&cls.getDcschoolid()!=null&&activeUser.getDcschoolid()!=null){
            System.out.println("cls.cuserid:"+cls.getCuserid().intValue());
            System.out.println("activeuser.cuserid:"+cls.getCuserid().intValue());
            System.out.println("cls.dcschoolid:"+cls.getCuserid().intValue());
            System.out.println("activeuser.dcschoolid:"+cls.getCuserid().intValue());
            if(cls.getCuserid().intValue()==activeUser.getUserid().intValue()
                    &&cls.getDcschoolid().intValue()== activeUser.getDcschoolid().intValue()){
                //说明操作对象是自已。提示不能加入到自已创建的班级
                returnJo.put("msg","不能加入到自己创建的班级!");
                return  returnJo.toString();
            }
        }

        //判断该用户是否存在于该班级中
        ClassUser tmpCu=new ClassUser();
        tmpCu.setClassid(cls.getClassid());
        tmpCu.setUserid(activeUser.getRef());
        //  tmpCu.setRelationtype("班主任");
        List<ClassUser> tmpCuList=this.classUserManager.getList(tmpCu,null);
        if(tmpCuList!=null&&tmpCuList.size()>0){
            returnJo.put("msg","该用户已经存在于该班级中，无效操作!");
            return returnJo.toString();
        }
        //查询该用是否允许加入
        List<Map<String,Object>> mapList=this.myInfoUserManager.getUserClassInJoinLog(activeUser.getUserid(),cls.getClassid(),cls.getDcschoolid());
        if(mapList!=null&&mapList.size()>0
                &&mapList.get(0).get("ALLOW_INJOIN")!=null
                &&Integer.parseInt(mapList.get(0).get("ALLOW_INJOIN").toString().trim())==1){
            //说明不允许加入班级了
            returnJo.put("msg","该用户已经被禁止加入当前班级中!");
            return returnJo.toString();
        }

        if(userType==2){
            //验证是否是班主任
            if(isAdmin!=null&&Integer.parseInt(isAdmin)==1){
                //验证该班级是否存在班主任
                ClassUser cu=new ClassUser();
                cu.setClassid(cls.getClassid());
                cu.setRelationtype("班主任");
                List<ClassUser> cuList=this.classUserManager.getList(cu,null);
                if(cuList!=null&&cuList.size()>0){
                    returnJo.put("msg","本班已存在班主任，不能担任班主任!");
                    //return returnJo.toString();
                }else{
                    cu.setUserid(activeUser.getRef());
                    cu.setRef(this.classUserManager.getNextId());
                    if(!this.classUserManager.doSave(cu)){
                        returnJo.put("msg","加入班级失败!");
                        transactionRollback();
                        return returnJo.toString();
                    }
                    returnJo.put("result",1);
                }
            }
            //说明是教师，
            boolean isbreak=false;
            if(subjectId!=null&&subjectId.trim().length()>0){
                String[] subjectArrayId=subjectId.split(",");
                for(String sub:subjectArrayId){
                    if(sub!=null&&sub.trim().length()>0){
                        ClassUser cu=new ClassUser();
                        cu.setClassid(cls.getClassid());
                        cu.setUserid(activeUser.getRef());
                        cu.setSubjectid(Integer.parseInt(sub.trim()));
                        cu.setRelationtype("任课老师");
                        List<ClassUser> cuList=this.classUserManager.getList(cu,null);
                        if(cuList==null||cuList.size()<1){
                            cu.setRef(this.classUserManager.getNextId());
                            if(!this.classUserManager.doSave(cu)){
                                isbreak=true;
                                returnJo.put("msg","加入班级失败!");
                                break;
                            }
                        }
                    }
                }
            }
            if(isbreak){
                transactionRollback();
                return returnJo.toString();
            }else{
                if(subjectId!=null&&subjectId.trim().length()>0){
                    returnJo.put("msg","加入班级成功!"+returnJo.get("msg"));
                    returnJo.put("result",1);
                }else{
                    if(returnJo.get("result").toString().trim().equals("1")){
                        returnJo.put("msg","加入班级成功!");
                    }else{
                        returnJo.put("msg","加入班级失败!"+returnJo.get("msg"));
                        transactionRollback();
                    }
                }
            }

        }else if(userType==1){//如果是学生，则直接判断是否存在，添加
            ClassUser cu=new ClassUser();
            cu.setClassid(cls.getClassid());
            cu.setUserid(activeUser.getRef());
            cu.setRelationtype("学生");
            List<ClassUser> cuList=this.classUserManager.getList(cu,null);
            if(cuList==null||cuList.size()<1){
                cu.setRef(this.classUserManager.getNextId());
                if(!this.classUserManager.doSave(cu)){
                    returnJo.put("msg","加入班级失败!");
                    transactionRollback();
                }else{
                    returnJo.put("msg","加入班级成功!");
                    returnJo.put("result",1);
                }
            }else{
                returnJo.put("msg","已经存在于该班中!");
            }
        }else{
            returnJo.put("msg","身份错误!");
            transactionRollback();
        }
        if(Integer.parseInt(returnJo.get("result").toString())==1){
            //学生加入班级动态
            MyInfoUserInfo info=new MyInfoUserInfo();
            info.setUserref(activeUser.getRef());
            info.setMsgid(-1);
            info.setMsgname("IM通知");
            info.setMydata(activeUser.getRealname()+"|"+cls.getClassgrade()+cls.getClassname());
            info.setTemplateid(18);
            info.setClassid(cls.getClassid());
            if(!this.myInfoUserManager.doSave(info)){
                returnJo.put("msg", "添加动态失败!");
                transactionRollback();
                return returnJo.toString();
            }
            //同步数据到网校
            ClassUser cutmp=new ClassUser();
            cutmp.setClassid(cls.getClassid());
            List<ClassUser> cuTmpList=this.classUserManager.getList(cutmp,null);
            if(cuTmpList==null||cuTmpList.size()<1){
                returnJo.put("result",0);
                returnJo.put("msg","失败，同步失败!");
                transactionRollback();
            }else{
                if(!updateEttClassUser(cuTmpList,cls.getClassid(),activeUser.getDcschoolid())){
                    returnJo.put("result",0);
                    returnJo.put("msg","失败，同步失败 !");
                    transactionRollback();
                }
            }
        }
        return returnJo.toString();
    }
    /**
     * 添加班级信息到Ett
     * @param clsEntity
     * @return
     */
    private boolean addClassBaseToEtt(ClassInfo clsEntity){
        if(clsEntity==null)
            return false;
        //发送ETT班级信息
        return EttInterfaceUserUtil.addClassBase(clsEntity);
    }
    /**
     * 更新班级信息
     * @param cuTmpList 班级人员信息ClassUser  注意给对象赋ClassUser.Uid
     * @param dcschoolid  分校ID
     * @return
     */
    @Transactional
    private boolean updateEttClassUser(List<ClassUser> cuTmpList,Integer classid,Integer dcschoolid){
        List<Map<String,Object>> mapList=null;
        if(cuTmpList!=null){
            // 必带 userId，userType,subjectId 的三个key
            mapList=new ArrayList<Map<String, Object>>();
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
                    tmpMap.put("jid",(cuTmpe.getEttuserid()==null?-1:cuTmpe.getEttuserid()));
                    tmpMap.put("subjectId",cuTmpe.getSubjectid()==null?-1:cuTmpe.getSubjectid());
                    mapList.add(tmpMap);
                }
            }
        }
        if(!EttInterfaceUserUtil.OperateClassUser(mapList,classid,dcschoolid)){
            System.out.println("classUser同步至网校失败!");
            return false;
        } else
            System.out.println("classUser同步至网校成功!");
        return true;

    }

    /**
     * IM TEACHER REG
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=teacherRegister.do")
    @Transactional
    public void teacherRegister(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!ImInterFaceUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", "参数错误!");
            jsonObj.put("result",0);
            response.getWriter().print(jsonObj.toString());return;
        }
        HashMap<String,String>paramMap=ImInterFaceUtil.getRequestParam(request);
        String userName=paramMap.get("userName");
        String password=paramMap.get("password");
        String realName=paramMap.get("realName");
        String schoolName=paramMap.get("schoolName");
        String sign=paramMap.get("sign");
        String time=paramMap.get("time");
        if(userName==null||password==null||realName==null||sign==null||time==null){
            jsonObj.put("msg","参数验证失败!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        //验证参数格式
        String msg=validateRegisterParam(request);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonObj.put("msg",msg);
                jsonObj.put("result",0);
                response.getWriter().println(jsonObj.toString());return;
            }
        }
        //验证用户邮箱唯一性
        List<UserInfo>userList=null;
        UserInfo u=new UserInfo();
        u.setUsername(userName);
        userList=this.userManager.getList(u,null);
        if(userList!=null&&userList.size()>0){
            jsonObj.put("msg","用户名已存在!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        u=new UserInfo();
        u.setMailaddress(userName);
        if(userList!=null&&userList.size()>0){
            jsonObj.put("msg","用户名已存在!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }

        //获取学校信息
        String schoolid=UtilTool.utilproperty.getProperty("PUBLIC_SCHOOL_ID");
        if(schoolName!=null&&schoolName.length()>0){
            String timestamp=System.currentTimeMillis()+"";
            HashMap<String,String>map=new HashMap<String, String>();
            map.put("time",timestamp);
            map.put("sign",UrlSigUtil.makeSigSimple("schoolList.do", map));
            map.put("schoolName",schoolName);
            List<SchoolInfo>schoolList=getSchoolList(map);
            if(schoolList!=null&&schoolList.size()>0){
                schoolid=schoolList.get(0).getSchoolid().toString();
            }
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        String userNextRef=this.userManager.getNextId();

        //添加用户
        u.setRef(userNextRef);
        u.setPassword(password);
        u.setUsername(userName);
        u.setStateid(0);
        u.setDcschoolid(Integer.parseInt(schoolid));
        u.setIsactivity(1);
        u.setMailaddress(userName);
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
        t.setTeachername(realName);
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

        if(sqlListArray.size()>0&&objListArray.size()>0){
            if(this.userManager.doExcetueArrayNoTranProc(sqlListArray, objListArray)){
                UserInfo tmpUser=new UserInfo();
                tmpUser.setRef(userNextRef);
                List<UserInfo>tmpUserList=this.userManager.getList(tmpUser,null);
                if(tmpUserList==null||tmpUserList.size()<1){
                    jsonObj.put("result",0);
                    jsonObj.put("msg","当前用户不存在!");
                    response.getWriter().print(jsonObj.toString());return;
                }
                tmpUser=tmpUserList.get(0);

                //网校注册
                String timestamp=System.currentTimeMillis()+"";
                HashMap<String,String>map=new HashMap<String, String>();
                map.put("dcSchoolId", schoolid);
                map.put("dcUserId", tmpUser.getUserid()+"");
                map.put("userName", userName);
                map.put("realName", realName);
                map.put("activityType", "1");
                map.put("password", tmpUser.getPassword());
                map.put("email", userName);
                map.put("identity", "2");
                map.put("gradeId", "0");
                map.put("timestamp",timestamp);
                String signature=UrlSigUtil.makeSigSimple("registerUser.do",map);
                map.put("sign",signature);
                String ettUrl=UtilTool.utilproperty.get("ETT_INTER_IP").toString()+"registerUser.do";
                JSONObject jsonObject=sendPostUrl(ettUrl,map,"utf-8");
                int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                if(type==1){
                    Object jsonData = jsonObject.containsKey("data")?jsonObject.get("data"):null;
                    JSONObject dataObj=JSONObject.fromObject(jsonData);
                    Integer jid=dataObj.containsKey("jid")?Integer.parseInt(dataObj.get("jid").toString()):null;
                    UserInfo upd=new UserInfo();
                    upd.setRef(userNextRef);
                    upd.setEttuserid(jid);
                    if(!this.userManager.doUpdate(upd)){
                        transactionRollback();
                        jsonObj.put("result",0);
                        jsonObj.put("msg", "注册失败!");
                        response.getWriter().print(jsonObj.toString());
                        return;
                    }
                }else{
                    transactionRollback();
                    jsonObj.put("result",0);
                    Object jsonData = jsonObject.containsKey("msg")?jsonObject.get("msg"):"";
                    jsonData=UtilTool.decode(jsonData.toString());
                    jsonObj.put("msg", jsonData);
                    response.getWriter().print(jsonObj.toString());
                    return;
                }
            }
        }
        jsonObj.put("result",1);
        jsonObj.put("msg", "注册成功!");
        response.getWriter().print(jsonObj.toString());
    }


    /**
     * IM TEACHER REG
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=studentRegister.do")
    @Transactional
    public void studentRegister(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!ImInterFaceUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", "参数错误!");
            jsonObj.put("result",0);
            response.getWriter().print(jsonObj.toString());return;
        }
        HashMap<String,String>paramMap=ImInterFaceUtil.getRequestParam(request);
        String userName=paramMap.get("userName");
        String password=paramMap.get("password");
        String realName=paramMap.get("realName");
        String classCode=paramMap.get("classCode");
        String sign=paramMap.get("sign");
        String time=paramMap.get("time");
        if(userName==null||password==null||realName==null||sign==null||time==null||classCode==null){
            jsonObj.put("msg","参数错误!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        //验证参数格式
        String msg=validateRegisterParam(request);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonObj.put("msg",msg);
                jsonObj.put("result",0);
                response.getWriter().println(jsonObj.toString());return;
            }
        }
        //验证用户邮箱唯一性
        List<UserInfo>userList=null;
        UserInfo u=new UserInfo();
        u.setUsername(userName);
        userList=this.userManager.getList(u,null);
        if(userList!=null&&userList.size()>0){
            jsonObj.put("msg","用户名已存在!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        u=new UserInfo();
        u.setMailaddress(userName);
        if(userList!=null&&userList.size()>0){
            jsonObj.put("msg","用户名已存在!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }

        //获取班级信息
        ClassInfo classInfo=new ClassInfo();
        classInfo.setImvaldatecode(classCode);
        List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
        if(clsList==null||clsList.size()<1){
            jsonObj.put("msg","当前班级不存在!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        ClassInfo tmpCls=clsList.get(0);
        String schoolid=tmpCls.getDcschoolid().toString();

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        String userNextRef=this.userManager.getNextId();

        //添加用户
        u.setRef(userNextRef);
        u.setPassword(password);
        u.setUsername(userName);
        u.setStateid(0);
        u.setDcschoolid(Integer.parseInt(schoolid));
        u.setIsactivity(1);
        u.setMailaddress(userName);
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
        s.setStuname(realName);
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
        //添加
        ClassUser sel=new ClassUser();
        sel.setClassid(tmpCls.getClassid());
        sel.setUserid(userNextRef);
        sel.setRelationtype("学生");
        List<ClassUser>stuList=this.classUserManager.getList(sel,null);
        if(stuList==null||stuList.size()<1){
            ClassUser save=new ClassUser();
            save.setClassid(tmpCls.getClassid());
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

        //学生加入班级动态
        MyInfoUserInfo info=new MyInfoUserInfo();
        info.setUserref(userNextRef);
        info.setMsgid(-1);
        info.setMsgname("IM通知");
        info.setMydata(realName+"|"+tmpCls.getClassgrade()+tmpCls.getClassname());
        info.setTemplateid(18);
        info.setClassid(tmpCls.getClassid());
        sql=new StringBuilder();
        objList=this.myInfoUserManager.getSaveSql(info,sql);
        if(objList!=null&&sql!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }


        if(sqlListArray.size()>0&&objListArray.size()>0){
            if(this.userManager.doExcetueArrayNoTranProc(sqlListArray, objListArray)){
                UserInfo tmpUser=new UserInfo();
                tmpUser.setRef(userNextRef);
                List<UserInfo>tmpUserList=this.userManager.getList(tmpUser,null);
                if(tmpUserList==null||tmpUserList.size()<1){
                    jsonObj.put("result",0);
                    jsonObj.put("msg","当前用户不存在!");
                    response.getWriter().print(jsonObj.toString());return;
                }
                tmpUser=tmpUserList.get(0);

                //网校注册
                String timestamp=System.currentTimeMillis()+"";
                HashMap<String,String>map=new HashMap<String, String>();
                map.put("dcSchoolId", schoolid);
                map.put("dcUserId", tmpUser.getUserid()+"");
                map.put("userName", userName);
                map.put("realName", realName);
                map.put("activityType", "1");
                map.put("password", tmpUser.getPassword());
                map.put("email", userName);
                map.put("identity", "1");
                map.put("gradeId", tmpCls.getGradeid()+"");
                map.put("classId", tmpCls.getClassid()+"");
                map.put("timestamp",timestamp);
                String signature=UrlSigUtil.makeSigSimple("registerUser.do",map);
                map.put("sign",signature);
                String ettUrl=UtilTool.utilproperty.get("ETT_INTER_IP").toString()+"registerUser.do";
                JSONObject jsonObject=sendPostUrl(ettUrl,map,"utf-8");
                int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                if(type==1){
                    Object jsonData = jsonObject.containsKey("data")?jsonObject.get("data"):null;
                    JSONObject dataObj=JSONObject.fromObject(jsonData);
                    Integer jid=dataObj.containsKey("jid")?Integer.parseInt(dataObj.get("jid").toString()):null;
                    UserInfo upd=new UserInfo();
                    upd.setRef(userNextRef);
                    upd.setEttuserid(jid);
                    if(!this.userManager.doUpdate(upd)){
                        transactionRollback();
                        jsonObj.put("result",0);
                        jsonObj.put("msg", "注册失败!");
                        response.getWriter().print(jsonObj.toString());return;
                    }
                }else{
                    transactionRollback();
                    jsonObj.put("result",0);
                    Object jsonData = jsonObject.containsKey("msg")?jsonObject.get("msg"):"";
                    jsonData=UtilTool.decode(jsonData.toString());
                    jsonObj.put("msg", jsonData.toString());
                    response.getWriter().print(jsonObj.toString());return;
                }
            }
        }
        jsonObj.put("result",1);
        jsonObj.put("msg", "注册成功!");
        response.getWriter().print(jsonObj.toString());
    }


    /**
     * 学生获取班级信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=searchClass.do")
    @Transactional
    public void stuSearchClass(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!ImInterFaceUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", "参数错误!");
            jsonObj.put("result",0);
            response.getWriter().print(jsonObj.toString());return;
        }
        HashMap<String,String>paramMap=ImInterFaceUtil.getRequestParam(request);
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classCode=paramMap.get("classCode");
        String sign=paramMap.get("sign");
        String time=paramMap.get("time");
        if(jid==null||schoolId==null||classCode==null||sign==null||time==null){
            jsonObj.put("msg","参数验证失败!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        paramMap.remove("sign");
        if(!UrlSigUtil.verifySigSimple("searchClass.do", paramMap, sign)){
            jsonObj.put("msg","参数验证失败!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());
            return;
        }



        //获取教师班级信息
        ClassInfo classInfo=new ClassInfo();
        classInfo.setImvaldatecode(classCode);
        classInfo.setDcschoolid(Integer.parseInt(schoolId));
        // classInfo.setSearchUid(tea.getUserid());
        List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
        if(clsList==null||clsList.size()<1){
            jsonObj.put("msg","当前班级不存在!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        ClassInfo tmpCls=clsList.get(0);
        String teacherName="";
        UserInfo tea=new UserInfo();
        tea.setUserid(tmpCls.getCuserid());
        List<UserInfo>uList=this.userManager.getList(tea,null);
        if(uList!=null&&uList.size()>0)
            teacherName=uList.get(0).getRealname();

        String msg="确认加入";
        if(teacherName!=null&&teacherName.length()>0)
            msg+=teacherName+"老师创建的";
        msg+=tmpCls.getClassgrade()+tmpCls.getClassname()+"吗?";
        jsonObj.put("result",1);
        jsonObj.put("msg", "操作成功!");
        jsonObj.put("data",msg);
        response.getWriter().print(jsonObj.toString());
    }


    /**
     * 记录班级动态访问时间点
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=saveClassTimePoint.do")
    @Transactional
    public void saveClassTimePoint(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!ImInterFaceUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", "参数异常!");
            jsonObj.put("result",0);
            response.getWriter().print(jsonObj.toString());return;
        }
        HashMap<String,String>paramMap=ImInterFaceUtil.getRequestParam(request);
        String jid=paramMap.get("jid");
        String schoolId=paramMap.get("schoolId");
        String classId=paramMap.get("classId");
        String sign=paramMap.get("sign");
        String time=paramMap.get("time");
        if(jid==null||schoolId==null||classId==null||sign==null||time==null){
            jsonObj.put("msg","参数验证失败!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        paramMap.remove("sign");
        if(!UrlSigUtil.verifySigSimple("saveClassTimePoint.do", paramMap, sign)){
            jsonObj.put("msg","参数验证失败!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());
            return;
        }

        //获取班级信息
        ClassInfo classInfo=new ClassInfo();
        classInfo.setClassid(Integer.parseInt(classId));
        classInfo.setDcschoolid(Integer.parseInt(schoolId));
        List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
        if(clsList==null||clsList.size()<1){
            jsonObj.put("msg","当前班级不存在!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }

        //获取用户
        UserInfo user=new UserInfo();
        user.setEttuserid(Integer.parseInt(jid));
        user.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(user,null);
        if(userList==null||userList.size()<1){
            jsonObj.put("msg","当前用户不存在!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        MyInfoUserInfo info=new MyInfoUserInfo();
        info.setClassid(Integer.parseInt(classId));
        info.setOperateid(userList.get(0).getRef());
        info.setUserref(userList.get(0).getRef());
        info.setMsgid(-1);
        info.setMsgname("IM通知");
        info.setMydata(UtilTool.DateConvertToString(new Date(), UtilTool.DateType.type1));
        info.setTemplateid(19);
        if(!this.myInfoUserManager.doSave(info)){
            jsonObj.put("msg","添加班级访问动态失败!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        jsonObj.put("msg","添加班级访问动态成功!");
        jsonObj.put("result", 1);
        response.getWriter().print(jsonObj.toString());return;

    }
    /**
     * 验证参数信息。
     * @param request
     * @return
     * @throws Exception
     */
    public static String validateRegisterParam(HttpServletRequest request) throws Exception{
        HashMap<String,String> paramMap=getRequestParam(request);
        if(!paramMap.containsKey("userName"))
            return "没有检测到用户名!";
        if(!paramMap.containsKey("password"))
            return "没有检测到密码!";
        ///////////////////验证用户名
        //位数6--12字符（1个汉字算2个字符）
        int uNameSize=checkWordSize(paramMap.get("userName").toString().trim());
        //用户名少于6个字或多于12个字
//        if(uNameSize<6||uNameSize>12)
//            return "用户名不能少于6个字或多于12个字!";
//        //用户名只含数字、字母（大小写）、下划线、汉字字符!
//        // 不能有空格
//        if(!UtilTool.matchingText("[a-zA-Z0-9_\\u4e00-\\u9fa5]+", paramMap.get("userName")))
//            return "用户名只含数字、字母（大小写）、下划线、汉字字符!";

        String emailPattern="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        if(!Pattern.matches(emailPattern, paramMap.get("userName").toString().trim()))
            return "邮箱格式不正确!";
        //////////////////验证密码
        //位数6--12字符
        int passSize=paramMap.get("password").trim().length();
        if(passSize<6||passSize>12)
            return "密码不能少于6个字或多于12个字!";
        //只含数字、字母（大小写）、下划线，且必须同时有数字和字母
        //if(!UtilTool.matchingText("[a-zA-Z0-9_]+",paramMap.get("password")))
        //            return "密码只含数字、字母（大小写）、下划线，且必须同时有数字和字母!";
        //验证是否存在数字
        if(!UtilTool.matchingText("[\\w[0-9]]+",paramMap.get("password")))
            return "密码必须同时有数字和字母!";
        //验证是否存在字符
        if(!UtilTool.matchingText("[\\w[a-zA-Z_]]+",paramMap.get("password")))
            return "密码必须同时有数字和字母!";
        return "TRUE";
    }

    /**
     * 验证字符长度
     * @param str
     * @return
     */
    private static int checkWordSize(String str) {
        if(str == null || str.length() == 0) {
            return 0;
        }
        int count = 0;
        char[] chs = str.toCharArray();
        for(int i = 0; i < chs.length; i++) {
            count += (chs[i] > 0xff) ? 2 : 1;
        }
        return count;
    }



    public List<SchoolInfo> getSchoolList(Map<String,String> paramMap){
        String ettUrl=UtilTool.utilproperty.get("TOTAL_SCHOOL_LOCATION").toString();
        if(ettUrl==null)return null;
        ettUrl+="franchisedSchool?m=szschoolList.do";




        HttpURLConnection httpConnection;
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
                    params.append(URLEncoder.encode(element.getValue().toString(), "utf-8"));
                    params.append("&");
                }

                if (params.length() > 0)
                {
                    params = params.deleteCharAt(params.length() - 1);
                }
            }

            url = new URL(ettUrl.toString());

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
        List<SchoolInfo>schoolList=null;
        if((type!=null&&type.trim().toLowerCase().equals("success")) || result!=null&&result.equals("1")){
            System.out.println(msg);
            if(jb.containsKey("data")){
//                JSONObject userObj=JSONObject.fromObject(jb.getString("data"));
//                if(!userObj.containsKey("user"))return null;
                JSONArray array;
                try{
                    array= JSONArray.fromObject(jb.getString("data"));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return null;
                }

                if(array!=null&&array.size()>0){
                    schoolList=new ArrayList<SchoolInfo>();
                    Iterator iterator=array.iterator();
                    while(iterator.hasNext()){
                        JSONObject obj=(JSONObject)iterator.next();
                        String id=obj.containsKey("schoolId")?obj.getString("schoolId"):"";
                        String name =obj.containsKey("schoolName")?obj.getString("schoolName"):"";
                        SchoolInfo u=new SchoolInfo();
                        if(id.length()>0)
                            u.setSchoolid(Long.parseLong(id.toString()));
                        if(name.length()>0)
                            u.setName(name);
                        schoolList.add(u);
                    }
                }
            }

        }else{
            System.out.println(msg);return null;
        }
        return schoolList;
    }

    /**
     * 验证分校是否存在
     * @param schoolid 分校ID
     * @return 0:没有  1：有
     */
    public static int validateHasSchool(Integer schoolid){
        HashMap<String,String> paraMap=new HashMap<String, String>();
        paraMap.put("schoolId",schoolid+"");
        paraMap.put("time",System.currentTimeMillis()+"isActiveSchool.do");
        paraMap.put("sign",UrlSigUtil.makeSigSimple("isActiveSchool.do",paraMap));
        String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
        // String totalSchoolUrl="http://192.168.10.41/totalSchool/";
        String url=totalSchoolUrl+"/franchisedSchool?m=isActiveSchool.do";
        JSONObject jo=sendPostUrl(url, paraMap, "UTF-8");
        Integer returnVal=0;
        if(jo!=null&&jo.containsKey("result")&&jo.get("result")!=null){
            returnVal=jo.getInt("result");
        }
        return returnVal;
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
            System.out.println("异常错误!接口连接："+urlstr+"未响应!");
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
            System.out.println("异常错误!500错误，请联系管理人员!连接："+urlstr+"");
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
        System.out.println("ImInterface 1.1.3 JSONObject:"+jb.toString());
        return jb;
    }


    /**
     * 生成IM端班级邀请码
     * @return
     */
    private  String genderInviteCode(int size){
        while (true){
            String code= UtilTool.getFixLenthString(size);
            ClassInfo cls=new ClassInfo();
            cls.setImvaldatecode(code);
            List<ClassInfo> clsList=this.classManager.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                return code;
            }
        }
    }
    /**
     * 生成公共班级家长邀请码
     * @return
     */
    private  Integer genderIdCode(int size){
        while (true){
            String code= UtilTool.getFixLenthString(size);
            ClassInfo cls=new ClassInfo();
            cls.setClassid(Integer.parseInt(code));
            List<ClassInfo>clsList=this.classManager.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                return Integer.parseInt(code);
            }
        }
    }
}

/**
 * 工具类方法
 */
class  ImInterFaceUtil{
    /**
     * 验证RequestParams相关参数
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
                String qString=request.getQueryString();
                //m=task
                if(qString==null){
                    qString=request.getParameter("m");
                }
                if(obj==null||obj.toString().trim().length()<1||(qString!=null&&qString.equals(obj)))
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
    /**
     * 验证RequestParams相关参数
     * @param request
     * @return
     * @throws Exception
     */
    public static HashMap<String,String> getRequestParam(HttpServletRequest request) throws Exception{
        Enumeration eObj=request.getParameterNames();
        HashMap<String,String> returnMap=null;
        if(eObj!=null){
            returnMap=new HashMap<String, String>();
            while(eObj.hasMoreElements()){
                Object obj=eObj.nextElement();
                if(obj==null||obj.toString().trim().length()<1||obj.toString().trim().equals("m")||obj.toString().equals(request.getQueryString()))
                    continue;
                Object val=request.getParameter(obj.toString());
                returnMap.put(obj.toString(),val.toString());
            }
        }
        return returnMap;
    }

    /**
     * 转换usertype
     * @param usertype 1:学生  2：老师  3：家长
     * return usertype
     * */
    public static Integer getUserType(String usertype){
        if(usertype==null||usertype.length()<1){
            return 0;
        }
        int type = 0;
        if(usertype.equals("1")||usertype.equals("2")){
            type=2;
        }else if(usertype.equals("3")||usertype.equals("4")){
            type=1;
        }else if(usertype.equals("6")){
            type=3;
        }
        return type;
    }

    public static JSONArray getEttPhoneAndRealNmae(String jidstr,String schoolid,String userid,int utype) throws UnsupportedEncodingException {
        String ettip = UtilTool.utilproperty.getProperty("ETT_INTER_IP");
        System.out.println("ettip------------------------------"+ettip);
        String url=ettip+"queryPhotoAndRealName.do";
        //String url = "http://wangjie.etiantian.com:8080/queryPhotoAndRealName.do";
        HashMap<String,String> signMap = new HashMap();
        signMap.put("userList",jidstr);
        signMap.put("schoolId",schoolid);
        signMap.put("srcJid",userid);
        signMap.put("userType",utype+"");
        signMap.put("timestamp",""+System.currentTimeMillis());
        String signture = UrlSigUtil.makeSigSimple("queryPhotoAndRealName.do",signMap,"*ETT#HONER#2014*");
        signMap.put("sign",signture);
        JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
        System.out.println("jsonObject---------------"+jsonObject);
        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
        if(type==1){
            Object obj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
            obj = URLDecoder.decode(obj.toString(),"utf-8");
            JSONArray jr = JSONArray.fromObject(obj);
            if(jr!=null)
                return jr;
            else
                return null;
        }else{
            return null;
        }
    }

    /**
     * 根据集合得到网校的头像及姓名
     * @param banCuList
     * @param userType 1:学生  2:老师  3:班主任
     * @param dcschoolid
     * @param ettuserid
     * @return
     * @throws Exception
     */
    public static List<Map<String,Object>> getClassUserEttPhoneAndName(List<ClassUser> banCuList,int userType,Integer dcschoolid,Integer ettuserid) throws Exception{
        StringBuilder jids=new StringBuilder();
        List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
        jids.append("[");
        for(ClassUser tmpCu:banCuList){
            if(tmpCu!=null){
                Map<String,Object> returnMap=new HashMap<String, Object>();
                if(tmpCu.getEttuserid()!=null){
                    //                    JSONObject tmpJo=new JSONObject();
                    //                    tmpJo.put("jid",tmpCu.getEttuserid()==null?"":tmpCu.getEttuserid());
                    //                    tmpJo.put("")
                    jids.append("{\"jid\":"+tmpCu.getEttuserid()+"},");
                    returnMap.put("jid",tmpCu.getEttuserid());
                }else{
                    returnMap.put("jid",null);
                }
                String subjName="";
                if(userType==3)
                    subjName="班主任";
                else if(userType==2)
                    subjName=tmpCu.getSubjectname();

                returnMap.put("subject",subjName);
                returnMap.put("userType",userType==1?3:1);
                returnMap.put("showName",tmpCu.getRealname());
                returnMap.put("photo","http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
                returnUserRecord.add(returnMap);
            }
//                else{
//                    Map<String,Object> returnMap=new HashMap<String, Object>();
//                    returnMap.put("jid","");
//                    returnMap.put("showName",)
//                }
        }

        String jidstr ="";
        if(jids.toString().trim().length()>0){
            jidstr=jids.toString().substring(0,jids.toString().lastIndexOf(","))+"]";
            JSONArray jr = ImInterFaceUtil.getEttPhoneAndRealNmae
                    (jidstr,dcschoolid.toString(),ettuserid.toString(),userType==1?3:2); //userType==1则是学生，传3 否则是教师
            if(jr!=null&&jr.size()>0){
                for(int i = 0;i<jr.size();i++){
                    JSONObject jo = jr.getJSONObject(i);
                    for(int j = 0;j<returnUserRecord.size();j++){
                        if(jo.getInt("jid")==Integer.parseInt(returnUserRecord.get(j).get("jid").toString())){
                            returnUserRecord.get(j).put("showName", jo.getString("realName"));
                            returnUserRecord.get(j).put("photo", jo.getString("headUrl"));
                        }
                    }
                }
            }
        }
        return returnUserRecord;
    }
}

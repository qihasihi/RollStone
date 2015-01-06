package com.school.ett;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.ClassUser;
import com.school.entity.TermInfo;
import com.school.entity.UserInfo;
import com.school.manager.ClassUserManager;
import com.school.manager.TermManager;
import com.school.manager.UserManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.IUserManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作ETT相关数据的存储过程。
 * Created by zhengzhou on 14-10-21.
 */
@Controller
@RequestMapping(value="/operateEtt")
public class OperateEttController extends BaseController<String>{
    @Autowired
    private IUserManager userManager;
    @Autowired
    private IClassUserManager classUserManager;
    @Autowired
    private ITermManager termManager;

    /**begin:*******************************网校WebIM使用方法***************************************************/
    /**
     * 验证用户名(1、非空验证  2、是否重复)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=validateUserName")
    public void validateUserName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.getWriter().println(OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID)).toJSON());
    }


    /**
     * 注册网校帐号
     * @param request
     * @param response
     */
    @RequestMapping(params="m=doRegisterEttAccount")
    public void doRegisterEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //验证参数
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        JsonEntity jsonEntity=new JsonEntity();
        String msg=OperateEttControllerUtil.validateRegisterParam(request,true);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonEntity.setMsg(msg);
                response.getWriter().println(jsonEntity.toJSON());return;
            }
            //重新检测用户名是否可用
            JsonEntity je=OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID));
            if(!je.getType().trim().equals("success")){
                response.getWriter().println(je.toJSON());return;
            }
            //得到该用户的年级
            UserInfo u = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
            // ////////////////////////////变量存储
            int currentClsCode=0;
            // 如果是学生，则传入年级的ID 老师不传
            if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
                TermInfo tm=this.termManager.getAutoTerm();
                String uidRef=u.getRef();
                ClassUser cu = new ClassUser();
                cu.setUserid(uidRef);
                // 查询当前的年级  如果当前学期没有数据，则不存入学期
                cu.setYear(tm.getYear().trim());
                List<ClassUser> cuList = this.classUserManager.getList(cu, null);
                if(cuList!=null&&cuList.size()>0){
                    String clsGrade=cuList.get(0).getClassgrade();
                    if (clsGrade != null) {
                        if (clsGrade.trim().equals("高三")
                                || clsGrade.trim().equals("高中三年级")) {
                            currentClsCode=1;
                        } else if (clsGrade.trim().equals("高二")
                                || clsGrade.trim().equals("高中二年级")) {
                            currentClsCode = 2;
                        } else if (clsGrade.trim().equals("高一")
                                || clsGrade.trim().equals("高中一年级")) {
                            currentClsCode = 3;
                        } else if (clsGrade.trim().equals("初三")
                                || clsGrade.trim().equals("初中三年级")) {
                            currentClsCode= 4;
                        } else if (clsGrade.trim().equals("初二")
                                || clsGrade.trim().equals("初中二年级")) {
                            currentClsCode= 5;
                        } else if (clsGrade.trim().equals("初一")
                                || clsGrade.trim().equals("初中一年级")) {
                            currentClsCode= 6;
                        } else if (clsGrade.trim().equals("小学六年级")
                                || clsGrade.trim().equals("小六")) {
                            currentClsCode= 7;
                        } else if (clsGrade.trim().equals("小学五年级")
                                || clsGrade.trim().equals("小五")) {
                            currentClsCode= 8;
                        } else if (clsGrade.trim().equals("小学四年级")
                                || clsGrade.trim().equals("小四")) {
                            currentClsCode= 9;
                        } else if (clsGrade.trim().equals("小学三年级")
                                || clsGrade.trim().equals("小三")) {
                            currentClsCode= 10;
                        }
                    }
                }
            }
            //******************************注册帐号-------------------------/
            //如果可用，则调接口添加相关数据
            HashMap<String,String> paraMap=new HashMap<String, String>();
            paraMap.put("dcSchoolId",this.logined(request).getDcschoolid()+"");
            paraMap.put("dcUserId",this.logined(request).getUserid()+"");
            paraMap.put("userName",userName);
            paraMap.put("password",password);
            paraMap.put("email",email);
//            if(this.validateRole(request,UtilTool._ROLE_STU_ID))
            paraMap.put("gradeId",currentClsCode+"");
            paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?"1":"2");
            paraMap.put("timestamp",new Date().getTime()+"");
            // paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?1:2);
            paraMap.put("sign", UrlSigUtil.makeSigSimple("registerax.do",paraMap));
            String urlstr=UtilTool.utilproperty.getProperty("REGISTER_ETT_ACCOUNT");
            JSONObject jsonObject=UtilTool.sendPostUrl(urlstr,paraMap,"UTF-8");
            //1:成功
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                if(jsonObject.containsKey("data")&&jsonObject.get("data")!=null){
                    int jid=jsonObject.getJSONObject("data").getInt("jid");
                    UserInfo updateU=new UserInfo();
                    updateU.setEttuserid(jid);
                    updateU.setUserid(this.logined(request).getUserid());
                    updateU.setRef(this.logined(request).getRef());
                    if(userManager.doUpdate(updateU)){
                        jsonEntity.setType("success");
                        jsonEntity.setMsg("注册成功!");
                        //重新将EttUserId写入session中
                        UserInfo tmpU=this.logined(request);
                        tmpU.setEttuserid(jid);
                        request.getSession().setAttribute("CURRENT_USER",tmpU);

                        response.getWriter().println(jsonEntity.toJSON());return;
                    }
                }
                jsonEntity.setType("error");
                jsonEntity.setMsg("注册成功!但绑定失败!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("错误，原因：未知!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }



    /**
     * 注册网校帐号
     * @param request
     * @param response
     */
    @RequestMapping(params="m=registerEttAccount")
    public void registerEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //验证参数
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        JsonEntity jsonEntity=new JsonEntity();
        String msg=OperateEttControllerUtil.validateRegisterParam(request,true);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonEntity.setMsg(msg);
                response.getWriter().println(jsonEntity.toJSON());return;
            }
            //重新检测用户名是否可用
            JsonEntity je=OperateEttControllerUtil.ettColumnCheckUName(request, this.validateRole(request, UtilTool._ROLE_STU_ID));
            if(!je.getType().trim().equals("success")){
                response.getWriter().println(je.toJSON());return;
            }
            //得到该用户的年级
            UserInfo u = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
            // ////////////////////////////变量存储
            int currentClsCode=0;
            // 如果是学生，则传入年级的ID 老师不传
            TermInfo tm=this.termManager.getAutoTerm();
            String uidRef=u.getRef();
            ClassUser cu = new ClassUser();
            cu.setUserid(uidRef);
            // 查询当前的年级  如果当前学期没有数据，则不存入学期
            cu.setYear(tm.getYear().trim());
            List<ClassUser> cuList = this.classUserManager.getList(cu, null);
            if(cuList!=null&&cuList.size()>0){
                String clsGrade=cuList.get(0).getClassgrade();
                if (clsGrade != null) {
                    if (clsGrade.trim().equals("高三")
                            || clsGrade.trim().equals("高中三年级")) {
                        currentClsCode=1;
                    } else if (clsGrade.trim().equals("高二")
                            || clsGrade.trim().equals("高中二年级")) {
                        currentClsCode = 2;
                    } else if (clsGrade.trim().equals("高一")
                            || clsGrade.trim().equals("高中一年级")) {
                        currentClsCode = 3;
                    } else if (clsGrade.trim().equals("初三")
                            || clsGrade.trim().equals("初中三年级")) {
                        currentClsCode= 4;
                    } else if (clsGrade.trim().equals("初二")
                            || clsGrade.trim().equals("初中二年级")) {
                        currentClsCode= 5;
                    } else if (clsGrade.trim().equals("初一")
                            || clsGrade.trim().equals("初中一年级")) {
                        currentClsCode= 6;
                    } else if (clsGrade.trim().equals("小学六年级")
                            || clsGrade.trim().equals("小六")) {
                        currentClsCode= 7;
                    } else if (clsGrade.trim().equals("小学五年级")
                            || clsGrade.trim().equals("小五")) {
                        currentClsCode= 8;
                    } else if (clsGrade.trim().equals("小学四年级")
                            || clsGrade.trim().equals("小四")) {
                        currentClsCode= 9;
                    } else if (clsGrade.trim().equals("小学三年级")
                            || clsGrade.trim().equals("小三")) {
                        currentClsCode= 10;
                    }
                }
            }else
                currentClsCode=0;

            int sex;
            if (this.logined(request).getUsersex().trim().equals("女"))
                sex = 2;
            else
                sex = 1;
            //******************************注册帐号-------------------------/
            //如果可用，则调接口添加相关数据
            HashMap<String,String> paraMap=new HashMap<String, String>();
            paraMap.put("schoolId",this.logined(request).getDcschoolid()+"");
            paraMap.put("uid",this.logined(request).getUserid()+"");
            paraMap.put("userName",userName);
            paraMap.put("password",password);
            paraMap.put("realName",this.logined(request).getRealname());
            paraMap.put("sex",sex+"");
            paraMap.put("email",email);
//            if(this.validateRole(request,UtilTool._ROLE_STU_ID))
            paraMap.put("gradeId",currentClsCode+"");
            paraMap.put("userType",this.validateRole(request,UtilTool._ROLE_STU_ID)?"1":"2");
            paraMap.put("time",new Date().getTime()+"");
            // paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?1:2);

            String urlstr=UtilTool.utilproperty.getProperty("REGISTER_ETTCOLUMN_ACCOUNT");
            String md5Key="registerNewUser.do";
            if(!this.validateRole(request,UtilTool._ROLE_STU_ID)){
                urlstr=UtilTool.utilproperty.getProperty("REGISTER_ETTCOLUMN_TEACHER_ACCOUNT");
                md5Key="registerNewTeacherUser.do";
            }

            paraMap.put("sign", UrlSigUtil.makeSigSimple(md5Key,paraMap));

            JSONObject jsonObject=UtilTool.sendPostUrlNotEncoding(urlstr, paraMap, "UTF-8");
            //1:成功
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                if(jsonObject.containsKey("data")&&jsonObject.get("data")!=null){
                    int jid=jsonObject.getInt("data");
                    UserInfo updateU=new UserInfo();
                    updateU.setEttuserid(jid);
                    updateU.setUserid(this.logined(request).getUserid());
                    updateU.setRef(this.logined(request).getRef());
                    if(userManager.doUpdate(updateU)){
                        jsonEntity.setType("success");
                        jsonEntity.setMsg("注册成功!");
                        //重新将EttUserId写入session中
                        UserInfo tmpU=this.logined(request);
                        tmpU.setEttuserid(jid);
                        request.getSession().setAttribute("CURRENT_USER",tmpU);
                        //输出
                        response.getWriter().println(jsonEntity.toJSON());return;
                    }
                }
                jsonEntity.setType("error");
                jsonEntity.setMsg("注册成功!但绑定失败!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("错误，原因：未知!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }

    /**
     * 注册网校帐号
     * @param request
     * @param response
     */
    @RequestMapping(params="m=bindEttAccount")
    public void bindEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //验证参数
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        JsonEntity jsonEntity=new JsonEntity();
        String msg=OperateEttControllerUtil.validateRegisterParam(request,false);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonEntity.setMsg(msg);
                response.getWriter().println(jsonEntity.toJSON());return;
            }

            //得到该用户的年级,学年等信息
            UserInfo u = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
            // ////////////////////////////变量存储
            //得到当前学年
            String tmYear = null;
            int currentClsCode=0;
            // 如果是学生，则传入年级的ID 老师不传
            //如果是学生，要相关参数
            if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
                TermInfo tm=this.termManager.getAutoTerm();
                tmYear=tm.getYear();
                String uidRef=u.getRef();
                ClassUser cu = new ClassUser();
                cu.setUserid(uidRef);
                // 查询当前的年级  如果当前学期没有数据，则不存入学期
                cu.setYear(tm.getYear().trim());
                List<ClassUser> cuList = this.classUserManager.getList(cu, null);
                if(cuList!=null&&cuList.size()>0){
                    String clsGrade=cuList.get(0).getClassgrade();
                    if (clsGrade != null) {
                        if (clsGrade.trim().equals("高三")
                                || clsGrade.trim().equals("高中三年级")) {
                            currentClsCode=1;
                        } else if (clsGrade.trim().equals("高二")
                                || clsGrade.trim().equals("高中二年级")) {
                            currentClsCode = 2;
                        } else if (clsGrade.trim().equals("高一")
                                || clsGrade.trim().equals("高中一年级")) {
                            currentClsCode = 3;
                        } else if (clsGrade.trim().equals("初三")
                                || clsGrade.trim().equals("初中三年级")) {
                            currentClsCode= 4;
                        } else if (clsGrade.trim().equals("初二")
                                || clsGrade.trim().equals("初中二年级")) {
                            currentClsCode= 5;
                        } else if (clsGrade.trim().equals("初一")
                                || clsGrade.trim().equals("初中一年级")) {
                            currentClsCode= 6;
                        } else if (clsGrade.trim().equals("小学六年级")
                                || clsGrade.trim().equals("小六")) {
                            currentClsCode= 7;
                        } else if (clsGrade.trim().equals("小学五年级")
                                || clsGrade.trim().equals("小五")) {
                            currentClsCode= 8;
                        } else if (clsGrade.trim().equals("小学四年级")
                                || clsGrade.trim().equals("小四")) {
                            currentClsCode= 9;
                        } else if (clsGrade.trim().equals("小学三年级")
                                || clsGrade.trim().equals("小三")) {
                            currentClsCode= 10;
                        }
                    }
                }
            }
//            int sex;
//            if (this.logined(request).getUsersex().trim().equals("女"))
//                sex = 2;
//            else
//                sex = 1;
            String year=tmYear;
            if(year!=null&&year.trim().length()>0){
                year=year.split("~")[0];
            }
            //******************************注册帐号-------------------------/
            //如果可用，则调接口添加相关数据
            //如果是学生，要相关参数
            String urlstr=UtilTool.utilproperty.getProperty("BIND_ETTCOLUMN_ACCOUNT");
            String md5Code="bindExistsUser.do";
            HashMap<String,String> paraMap=new HashMap<String, String>();
            //如果是学生，则有stuYear和gradeId参数
            if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
                paraMap.put("stuYear",year);
                paraMap.put("gradeId",currentClsCode+"");
            }else{  //如果是不是学生，则是老师，则修改接口链接
                urlstr=UtilTool.utilproperty.getProperty("BIND_ETTCOLUMN_TEACHER_ACCOUNT");
                md5Code="bindExistsTeacher.do";
            }

            paraMap.put("schoolId",this.logined(request).getDcschoolid()+"");
            paraMap.put("uid",this.logined(request).getUserid()+"");
            paraMap.put("userName",userName);
            paraMap.put("password",password);
            paraMap.put("realName",this.logined(request).getRealname());
//            if(this.validateRole(request,UtilTool._ROLE_STU_ID))
            paraMap.put("time",new Date().getTime()+"");
            // paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?1:2);
            paraMap.put("sign", UrlSigUtil.makeSigSimple(md5Code,paraMap));

            JSONObject jsonObject=UtilTool.sendPostUrlNotEncoding(urlstr, paraMap, "UTF-8");
            //1:成功
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                if(jsonObject.containsKey("data")&&jsonObject.get("data")!=null){
                    int jid=jsonObject.getInt("data");
                    UserInfo updateU=new UserInfo();
                    updateU.setEttuserid(jid);
                    updateU.setUserid(this.logined(request).getUserid());
                    updateU.setRef(this.logined(request).getRef());
                    if(userManager.doUpdate(updateU)){
                        jsonEntity.setType("success");
                        jsonEntity.setMsg("绑定成功!");
                        //重新将EttUserId写入session中
                        UserInfo tmpU=this.logined(request);
                        tmpU.setEttuserid(jid);
                        request.getSession().setAttribute("CURRENT_USER",tmpU);
                        //输出
                        response.getWriter().println(jsonEntity.toJSON());return;
                    }
                }
                jsonEntity.setType("error");
                jsonEntity.setMsg("注册成功!但绑定失败!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("错误，原因：未知!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }

    /**end:*******************************网校WebIM使用方法***************************************************/
    /**begin:***********************************网校栏目使用方法*********************************/
    /**
     * 验证用户名(1、非空验证  2、是否重复)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=checkEttUserName")
    public void checkEttUserName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.getWriter().println(OperateEttControllerUtil.ettColumnCheckUName(request, this.validateRole(request, UtilTool._ROLE_STU_ID)).toJSON());
    }



    /**
     * ETT 检查用户名是否存在
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=checkUserName.do")
    @Transactional
    public void checkUserName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!OperateEttControllerUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", UtilTool.ecode("参数数据有空值!"));
            jsonObj.put("result",0);
            response.getWriter().print(jsonObj.toString());return;
        }
        HashMap<String,String>paramMap=OperateEttControllerUtil.getRequestParam(request);
        String userName=paramMap.get("userName");
        String jid=paramMap.get("jid");
        String sign=paramMap.get("sign");
        String time=paramMap.get("timestamp");
        if(userName==null||jid==null||sign==null||time==null){
            jsonObj.put("msg",UtilTool.ecode("参数不全!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        userName= URLDecoder.decode(userName, "utf-8");
        paramMap.remove("sign");
        if(!UrlSigUtil.verifySigSimple("checkUserName.do",paramMap,sign)){
            jsonObj.put("msg",UtilTool.ecode("参数验证失败!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }

        //验证用户唯一性
        List<UserInfo>userList=null;
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            jsonObj.put("msg",UtilTool.ecode("用户不存在!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        UserInfo before=userList.get(0);

        u=new UserInfo();
        //u.setDcschoolid(userList.get(0).getDcschoolid());
        u.setUsername(userName);
        userList=this.userManager.getList(u,null);
        if(userList!=null&&userList.size()>1){
            jsonObj.put("msg",UtilTool.ecode("用户名已存在!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;

        }else if(userList!=null&&userList.size()==1){
            UserInfo after=userList.get(0);
            if(after.getEttuserid()==null||!after.getEttuserid().toString().equals(before.getEttuserid().toString())){
                jsonObj.put("msg",UtilTool.ecode("用户名已存在!"));
                jsonObj.put("result", 0);
                response.getWriter().print(jsonObj.toString());return;
            }
        }
        jsonObj.put("result",1);
        jsonObj.put("msg", "success!");
        response.getWriter().print(jsonObj.toString());
    }

    /**
     * ETT 修改用户信息
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=updateUser.do")
    @Transactional
    public void updateEttUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!OperateEttControllerUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", UtilTool.ecode("参数错误!"));
            jsonObj.put("result",0);
            response.getWriter().print(jsonObj.toString());return;
        }
        HashMap<String,String>paramMap=OperateEttControllerUtil.getRequestParam(request);
        String dcSchoolId=paramMap.get("dcSchoolId");
        String jid=paramMap.get("jid");
        String userName=paramMap.get("userName");
        String password=paramMap.get("password");
        String email=paramMap.get("email");
        String identity=paramMap.get("identity");
        String sign=paramMap.get("sign");
        String time=paramMap.get("timestamp");
        if(userName==null||password==null||sign==null||time==null||dcSchoolId==null||jid==null||identity==null||email==null){
            jsonObj.put("msg",UtilTool.ecode("参数错误!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        userName=URLDecoder.decode(userName,"utf-8");
        if(!UtilTool.matchingText("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$",email)){
            jsonObj.put("msg",UtilTool.ecode("邮箱格式不正确!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        List<UserInfo>userList=null;
        UserInfo sel=new UserInfo();
        sel.setEttuserid(Integer.parseInt(jid));
        userList=this.userManager.getList(sel,null);
        if(userList==null||userList.size()<1){
            jsonObj.put("msg",UtilTool.ecode("用户不存在!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        sel=userList.get(0);
        //验证参数格式
        //验证用户邮箱唯一性

        UserInfo u=new UserInfo();
        u.setUsername(userName);
        // u.setDcschoolid(Integer.parseInt(dcSchoolId));
        userList=this.userManager.getList(u,null);
        if(userList!=null&&userList.size()>1){
            jsonObj.put("msg",UtilTool.ecode("用户名已存在!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }else  if(userList!=null&&userList.size()==1){
            UserInfo after=userList.get(0);
            if(after.getEttuserid()==null||!after.getEttuserid().toString().equals(sel.getEttuserid().toString())){
                jsonObj.put("msg",UtilTool.ecode("用户名已存在!"));
                jsonObj.put("result", 0);
                response.getWriter().print(jsonObj.toString());return;
            }
        }
        u=new UserInfo();
        u.setMailaddress(email);
        if(userList!=null&&userList.size()>1){
            jsonObj.put("msg",UtilTool.ecode("邮箱已存在!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }else if(userList!=null&&userList.size()==1){
            UserInfo after=userList.get(0);
            if(after.getEttuserid()==null||!after.getEttuserid().toString().equals(sel.getEttuserid().toString())){
                jsonObj.put("msg",UtilTool.ecode("邮箱已存在!"));
                jsonObj.put("result", 0);
                response.getWriter().print(jsonObj.toString());return;
            }
        }

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();


        u.setPassword(password);
        u.setUsername(userName);
        u.setEttuserid(Integer.parseInt(jid));
        u.setDcschoolid(Integer.parseInt(dcSchoolId));
        u.setMailaddress(email);
        u.setRef(sel.getRef());
        sql = new StringBuilder();
        objList = this.userManager.getUpdateSql(u, sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        if(sqlListArray.size()>0&&objListArray.size()>0){
            if(!this.userManager.doExcetueArrayNoTranProc(sqlListArray, objListArray)){
                jsonObj.put("result",0);
                jsonObj.put("msg", UtilTool.ecode("操作失败!"));
                response.getWriter().print(jsonObj.toString());return;
            }
        }
        jsonObj.put("result",1);
        jsonObj.put("msg",UtilTool.ecode("操作成功!"));
        response.getWriter().print(jsonObj.toString());
    }



    /**
     * 正则验证 (在UtilTool类中)
     *
     * @author 郑舟(2010-06-31 下午02:20:16)
     * @param expression
     * @param text
     * @return boolean
     */

    public static boolean matchingText(String expression, String text) {
        boolean bool = false;
        if (expression != null && !"".equals(expression) && text != null
                && !"".equals(text.toLowerCase())) {
            Pattern p = Pattern.compile(expression); // 正则表达式
            Matcher m = p.matcher(text.toLowerCase()); // 操作的字符串
            bool = m.matches();
        }
        return bool;
    }


}


/**
 * 工具类
 */
class OperateEttControllerUtil{
    /**begin:*******************************网校WebIM使用方法***************************************************/
    /***********************---------------验证参数信息------------------*************************/
    /**
     * 验证用户名是否存在
     * @param request
     * @return
     */
    public static JsonEntity validateEttUserNameHas(HttpServletRequest request,Boolean isstu) throws Exception{
        String userName=request.getParameter("userName");
        String oldUserName=request.getParameter("oldUserName");
        JsonEntity jsonEntity=new JsonEntity();
        if(userName==null||userName.trim().length()<1){
            jsonEntity.setMsg("用户名不能为空，请输入后重试!");
          return jsonEntity;
        }
        if(oldUserName!=null&&userName.trim().equals(oldUserName.trim())){
            jsonEntity.setMsg("验证成功!");
            jsonEntity.setType("success");
            return jsonEntity;
        }
        HashMap<String,String> paraMap=new HashMap<String, String>();
        String encoding="UTF-8";
        String urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETT_TEA_USERNAME_HAS_URL");
        if(isstu){
            urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETT_STU_USERNAME_HAS_URL");
            paraMap.put("userName",userName);
        }else
            paraMap.put("username",java.net.URLEncoder.encode(userName,"UTF-8"));
        JSONObject jsonObject=UtilTool.sendPostUrlNotEncoding(urlstr,paraMap,encoding);
        //1:成功
        if(jsonObject!=null&&jsonObject.containsKey("code")&& jsonObject.getInt("code")==1){
            jsonEntity.setType("success");
            jsonEntity.setMsg("验证成功!");
        }else{
            jsonEntity.setType("error");
            jsonEntity.setMsg(java.net.URLDecoder.decode(jsonObject.getString("msg"),"UTF-8"));
        }
        return jsonEntity;
    }

/**end:*******************************网校WebIM使用方法***************************************************/
/**begin:*******************************网校栏目使用方法***************************************************/
    /**
     * 网校栏目绑定时验证用户名
     * @param request
     * @return
     */
    public static JsonEntity ettColumnCheckUName(HttpServletRequest request,Boolean isstu) throws Exception{
        String userName=request.getParameter("userName");
        JsonEntity jsonEntity=new JsonEntity();
        if(userName==null||userName.trim().length()<1){
            jsonEntity.setMsg("用户名不能为空，请输入后重试!");
            return jsonEntity;
        }
        HashMap<String,String> paraMap=new HashMap<String, String>();
        String encoding="UTF-8";
        String urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETTCOLUMN_TEA_USERNAME_HAS_URL");
        Long time=new Date().getTime();
        paraMap.put("time",time.toString());
        String key="checkUserIsExists.do";//学生版
        if(isstu){
            urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETTCOLUMN_STU_USERNAME_HAS_URL");
            paraMap.put("userName",java.net.URLEncoder.encode(userName,"UTF-8"));
        }else{
            paraMap.put("userName",java.net.URLEncoder.encode(userName,"UTF-8"));
            key="checkUserIsExists.do";//老师版
        }
        paraMap.put("sign",UrlSigUtil.makeSigSimple(key,paraMap));
        JSONObject jsonObject=UtilTool.sendPostUrlNotEncoding(urlstr,paraMap,encoding);
        //1:成功
        if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
            jsonEntity.setType("success");
            jsonEntity.setMsg("验证成功!");
        }else{
            jsonEntity.setType("error");
            jsonEntity.setMsg(java.net.URLDecoder.decode(jsonObject.getString("msg"),"UTF-8"));
        }
        return jsonEntity;
    }


    /////////////工具方法

    /**
     * 验证参数信息。
     * @param request
     * @return
     * @throws Exception
     */
    public static String validateRegisterParam(HttpServletRequest request,boolean isvalidateUnameFormat) throws Exception{
        if(!ValidateRequestParam(request)){
            return "参数不能为空!请输入数据后，重新点击!";
        }
        HashMap<String,String> paramMap=getRequestParam(request);
        if(!paramMap.containsKey("userName"))
            return "没有检测到用户名!请刷新后重试";
        if(!paramMap.containsKey("password"))
            return "没有检测到密码!请刷新后重试";
        ///////////////////验证用户名
        //位数6--12字符（1个汉字算2个字符）
        int uNameSize=checkWordSize(paramMap.get("userName").toString().trim());

        if(isvalidateUnameFormat){
            //用户名少于6个字或多于12个字
            if(uNameSize<6||uNameSize>12)
                return "用户名不能少于6个字或多于12个字!请更改";
            //用户名只含数字、字母（大小写）、下划线、汉字字符!
            // 不能有空格
            if(!UtilTool.matchingText("[a-zA-Z0-9_\\u4e00-\\u9fa5]+",paramMap.get("userName")))
                return "用户名只含数字、字母（大小写）、下划线、汉字字符!请更改";
        }
        //////////////////验证密码
        //位数6--12字符
        if(isvalidateUnameFormat){
            int passSize=paramMap.get("password").trim().length();
            if(passSize<6||passSize>12)
                return "密码不能少于6个字或多于12个字!请更改";
            //只含数字、字母（大小写）、下划线，且必须同时有数字和字母
            if(!UtilTool.matchingText("[a-zA-Z0-9_]+",paramMap.get("password")))
                return "密码只含数字、字母（大小写）、下划线，且必须同时有数字和字母!请更改";
            //验证是否存在数字
            if(!UtilTool.matchingText("[\\w[0-9]]+",paramMap.get("password")))
                return "密码必须同时有数字和字母!请更改";
            //验证是否存在字符
            if(!UtilTool.matchingText("[\\w[a-zA-Z_]]+",paramMap.get("password")))
                return "密码必须同时有数字和字母!请更改";
        }
        if(paramMap.containsKey("email")){
            //验证邮箱
            String email=paramMap.get("email");
            if(!UtilTool.matchingText("^([\\w-\\.]{4,18})@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",email))
                return "邮箱格式不正确，请更改";
        }
        return "TRUE";
    }

    /**
     * 统计字符串的字符数量(中文2个字符)
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



}

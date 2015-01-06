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
 * ����ETT������ݵĴ洢���̡�
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

    /**begin:*******************************��УWebIMʹ�÷���***************************************************/
    /**
     * ��֤�û���(1���ǿ���֤  2���Ƿ��ظ�)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=validateUserName")
    public void validateUserName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.getWriter().println(OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID)).toJSON());
    }


    /**
     * ע����У�ʺ�
     * @param request
     * @param response
     */
    @RequestMapping(params="m=doRegisterEttAccount")
    public void doRegisterEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //��֤����
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
            //���¼���û����Ƿ����
            JsonEntity je=OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID));
            if(!je.getType().trim().equals("success")){
                response.getWriter().println(je.toJSON());return;
            }
            //�õ����û����꼶
            UserInfo u = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
            // ////////////////////////////�����洢
            int currentClsCode=0;
            // �����ѧ���������꼶��ID ��ʦ����
            if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
                TermInfo tm=this.termManager.getAutoTerm();
                String uidRef=u.getRef();
                ClassUser cu = new ClassUser();
                cu.setUserid(uidRef);
                // ��ѯ��ǰ���꼶  �����ǰѧ��û�����ݣ��򲻴���ѧ��
                cu.setYear(tm.getYear().trim());
                List<ClassUser> cuList = this.classUserManager.getList(cu, null);
                if(cuList!=null&&cuList.size()>0){
                    String clsGrade=cuList.get(0).getClassgrade();
                    if (clsGrade != null) {
                        if (clsGrade.trim().equals("����")
                                || clsGrade.trim().equals("�������꼶")) {
                            currentClsCode=1;
                        } else if (clsGrade.trim().equals("�߶�")
                                || clsGrade.trim().equals("���ж��꼶")) {
                            currentClsCode = 2;
                        } else if (clsGrade.trim().equals("��һ")
                                || clsGrade.trim().equals("����һ�꼶")) {
                            currentClsCode = 3;
                        } else if (clsGrade.trim().equals("����")
                                || clsGrade.trim().equals("�������꼶")) {
                            currentClsCode= 4;
                        } else if (clsGrade.trim().equals("����")
                                || clsGrade.trim().equals("���ж��꼶")) {
                            currentClsCode= 5;
                        } else if (clsGrade.trim().equals("��һ")
                                || clsGrade.trim().equals("����һ�꼶")) {
                            currentClsCode= 6;
                        } else if (clsGrade.trim().equals("Сѧ���꼶")
                                || clsGrade.trim().equals("С��")) {
                            currentClsCode= 7;
                        } else if (clsGrade.trim().equals("Сѧ���꼶")
                                || clsGrade.trim().equals("С��")) {
                            currentClsCode= 8;
                        } else if (clsGrade.trim().equals("Сѧ���꼶")
                                || clsGrade.trim().equals("С��")) {
                            currentClsCode= 9;
                        } else if (clsGrade.trim().equals("Сѧ���꼶")
                                || clsGrade.trim().equals("С��")) {
                            currentClsCode= 10;
                        }
                    }
                }
            }
            //******************************ע���ʺ�-------------------------/
            //������ã�����ӿ�����������
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
            //1:�ɹ�
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                if(jsonObject.containsKey("data")&&jsonObject.get("data")!=null){
                    int jid=jsonObject.getJSONObject("data").getInt("jid");
                    UserInfo updateU=new UserInfo();
                    updateU.setEttuserid(jid);
                    updateU.setUserid(this.logined(request).getUserid());
                    updateU.setRef(this.logined(request).getRef());
                    if(userManager.doUpdate(updateU)){
                        jsonEntity.setType("success");
                        jsonEntity.setMsg("ע��ɹ�!");
                        //���½�EttUserIdд��session��
                        UserInfo tmpU=this.logined(request);
                        tmpU.setEttuserid(jid);
                        request.getSession().setAttribute("CURRENT_USER",tmpU);

                        response.getWriter().println(jsonEntity.toJSON());return;
                    }
                }
                jsonEntity.setType("error");
                jsonEntity.setMsg("ע��ɹ�!����ʧ��!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("����ԭ��δ֪!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }



    /**
     * ע����У�ʺ�
     * @param request
     * @param response
     */
    @RequestMapping(params="m=registerEttAccount")
    public void registerEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //��֤����
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
            //���¼���û����Ƿ����
            JsonEntity je=OperateEttControllerUtil.ettColumnCheckUName(request, this.validateRole(request, UtilTool._ROLE_STU_ID));
            if(!je.getType().trim().equals("success")){
                response.getWriter().println(je.toJSON());return;
            }
            //�õ����û����꼶
            UserInfo u = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
            // ////////////////////////////�����洢
            int currentClsCode=0;
            // �����ѧ���������꼶��ID ��ʦ����
            TermInfo tm=this.termManager.getAutoTerm();
            String uidRef=u.getRef();
            ClassUser cu = new ClassUser();
            cu.setUserid(uidRef);
            // ��ѯ��ǰ���꼶  �����ǰѧ��û�����ݣ��򲻴���ѧ��
            cu.setYear(tm.getYear().trim());
            List<ClassUser> cuList = this.classUserManager.getList(cu, null);
            if(cuList!=null&&cuList.size()>0){
                String clsGrade=cuList.get(0).getClassgrade();
                if (clsGrade != null) {
                    if (clsGrade.trim().equals("����")
                            || clsGrade.trim().equals("�������꼶")) {
                        currentClsCode=1;
                    } else if (clsGrade.trim().equals("�߶�")
                            || clsGrade.trim().equals("���ж��꼶")) {
                        currentClsCode = 2;
                    } else if (clsGrade.trim().equals("��һ")
                            || clsGrade.trim().equals("����һ�꼶")) {
                        currentClsCode = 3;
                    } else if (clsGrade.trim().equals("����")
                            || clsGrade.trim().equals("�������꼶")) {
                        currentClsCode= 4;
                    } else if (clsGrade.trim().equals("����")
                            || clsGrade.trim().equals("���ж��꼶")) {
                        currentClsCode= 5;
                    } else if (clsGrade.trim().equals("��һ")
                            || clsGrade.trim().equals("����һ�꼶")) {
                        currentClsCode= 6;
                    } else if (clsGrade.trim().equals("Сѧ���꼶")
                            || clsGrade.trim().equals("С��")) {
                        currentClsCode= 7;
                    } else if (clsGrade.trim().equals("Сѧ���꼶")
                            || clsGrade.trim().equals("С��")) {
                        currentClsCode= 8;
                    } else if (clsGrade.trim().equals("Сѧ���꼶")
                            || clsGrade.trim().equals("С��")) {
                        currentClsCode= 9;
                    } else if (clsGrade.trim().equals("Сѧ���꼶")
                            || clsGrade.trim().equals("С��")) {
                        currentClsCode= 10;
                    }
                }
            }else
                currentClsCode=0;

            int sex;
            if (this.logined(request).getUsersex().trim().equals("Ů"))
                sex = 2;
            else
                sex = 1;
            //******************************ע���ʺ�-------------------------/
            //������ã�����ӿ�����������
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
            //1:�ɹ�
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                if(jsonObject.containsKey("data")&&jsonObject.get("data")!=null){
                    int jid=jsonObject.getInt("data");
                    UserInfo updateU=new UserInfo();
                    updateU.setEttuserid(jid);
                    updateU.setUserid(this.logined(request).getUserid());
                    updateU.setRef(this.logined(request).getRef());
                    if(userManager.doUpdate(updateU)){
                        jsonEntity.setType("success");
                        jsonEntity.setMsg("ע��ɹ�!");
                        //���½�EttUserIdд��session��
                        UserInfo tmpU=this.logined(request);
                        tmpU.setEttuserid(jid);
                        request.getSession().setAttribute("CURRENT_USER",tmpU);
                        //���
                        response.getWriter().println(jsonEntity.toJSON());return;
                    }
                }
                jsonEntity.setType("error");
                jsonEntity.setMsg("ע��ɹ�!����ʧ��!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("����ԭ��δ֪!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }

    /**
     * ע����У�ʺ�
     * @param request
     * @param response
     */
    @RequestMapping(params="m=bindEttAccount")
    public void bindEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //��֤����
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        JsonEntity jsonEntity=new JsonEntity();
        String msg=OperateEttControllerUtil.validateRegisterParam(request,false);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonEntity.setMsg(msg);
                response.getWriter().println(jsonEntity.toJSON());return;
            }

            //�õ����û����꼶,ѧ�����Ϣ
            UserInfo u = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
            // ////////////////////////////�����洢
            //�õ���ǰѧ��
            String tmYear = null;
            int currentClsCode=0;
            // �����ѧ���������꼶��ID ��ʦ����
            //�����ѧ����Ҫ��ز���
            if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
                TermInfo tm=this.termManager.getAutoTerm();
                tmYear=tm.getYear();
                String uidRef=u.getRef();
                ClassUser cu = new ClassUser();
                cu.setUserid(uidRef);
                // ��ѯ��ǰ���꼶  �����ǰѧ��û�����ݣ��򲻴���ѧ��
                cu.setYear(tm.getYear().trim());
                List<ClassUser> cuList = this.classUserManager.getList(cu, null);
                if(cuList!=null&&cuList.size()>0){
                    String clsGrade=cuList.get(0).getClassgrade();
                    if (clsGrade != null) {
                        if (clsGrade.trim().equals("����")
                                || clsGrade.trim().equals("�������꼶")) {
                            currentClsCode=1;
                        } else if (clsGrade.trim().equals("�߶�")
                                || clsGrade.trim().equals("���ж��꼶")) {
                            currentClsCode = 2;
                        } else if (clsGrade.trim().equals("��һ")
                                || clsGrade.trim().equals("����һ�꼶")) {
                            currentClsCode = 3;
                        } else if (clsGrade.trim().equals("����")
                                || clsGrade.trim().equals("�������꼶")) {
                            currentClsCode= 4;
                        } else if (clsGrade.trim().equals("����")
                                || clsGrade.trim().equals("���ж��꼶")) {
                            currentClsCode= 5;
                        } else if (clsGrade.trim().equals("��һ")
                                || clsGrade.trim().equals("����һ�꼶")) {
                            currentClsCode= 6;
                        } else if (clsGrade.trim().equals("Сѧ���꼶")
                                || clsGrade.trim().equals("С��")) {
                            currentClsCode= 7;
                        } else if (clsGrade.trim().equals("Сѧ���꼶")
                                || clsGrade.trim().equals("С��")) {
                            currentClsCode= 8;
                        } else if (clsGrade.trim().equals("Сѧ���꼶")
                                || clsGrade.trim().equals("С��")) {
                            currentClsCode= 9;
                        } else if (clsGrade.trim().equals("Сѧ���꼶")
                                || clsGrade.trim().equals("С��")) {
                            currentClsCode= 10;
                        }
                    }
                }
            }
//            int sex;
//            if (this.logined(request).getUsersex().trim().equals("Ů"))
//                sex = 2;
//            else
//                sex = 1;
            String year=tmYear;
            if(year!=null&&year.trim().length()>0){
                year=year.split("~")[0];
            }
            //******************************ע���ʺ�-------------------------/
            //������ã�����ӿ�����������
            //�����ѧ����Ҫ��ز���
            String urlstr=UtilTool.utilproperty.getProperty("BIND_ETTCOLUMN_ACCOUNT");
            String md5Code="bindExistsUser.do";
            HashMap<String,String> paraMap=new HashMap<String, String>();
            //�����ѧ��������stuYear��gradeId����
            if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
                paraMap.put("stuYear",year);
                paraMap.put("gradeId",currentClsCode+"");
            }else{  //����ǲ���ѧ����������ʦ�����޸Ľӿ�����
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
            //1:�ɹ�
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                if(jsonObject.containsKey("data")&&jsonObject.get("data")!=null){
                    int jid=jsonObject.getInt("data");
                    UserInfo updateU=new UserInfo();
                    updateU.setEttuserid(jid);
                    updateU.setUserid(this.logined(request).getUserid());
                    updateU.setRef(this.logined(request).getRef());
                    if(userManager.doUpdate(updateU)){
                        jsonEntity.setType("success");
                        jsonEntity.setMsg("�󶨳ɹ�!");
                        //���½�EttUserIdд��session��
                        UserInfo tmpU=this.logined(request);
                        tmpU.setEttuserid(jid);
                        request.getSession().setAttribute("CURRENT_USER",tmpU);
                        //���
                        response.getWriter().println(jsonEntity.toJSON());return;
                    }
                }
                jsonEntity.setType("error");
                jsonEntity.setMsg("ע��ɹ�!����ʧ��!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("����ԭ��δ֪!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }

    /**end:*******************************��УWebIMʹ�÷���***************************************************/
    /**begin:***********************************��У��Ŀʹ�÷���*********************************/
    /**
     * ��֤�û���(1���ǿ���֤  2���Ƿ��ظ�)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=checkEttUserName")
    public void checkEttUserName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.getWriter().println(OperateEttControllerUtil.ettColumnCheckUName(request, this.validateRole(request, UtilTool._ROLE_STU_ID)).toJSON());
    }



    /**
     * ETT ����û����Ƿ����
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=checkUserName.do")
    @Transactional
    public void checkUserName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!OperateEttControllerUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", UtilTool.ecode("���������п�ֵ!"));
            jsonObj.put("result",0);
            response.getWriter().print(jsonObj.toString());return;
        }
        HashMap<String,String>paramMap=OperateEttControllerUtil.getRequestParam(request);
        String userName=paramMap.get("userName");
        String jid=paramMap.get("jid");
        String sign=paramMap.get("sign");
        String time=paramMap.get("timestamp");
        if(userName==null||jid==null||sign==null||time==null){
            jsonObj.put("msg",UtilTool.ecode("������ȫ!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        userName= URLDecoder.decode(userName, "utf-8");
        paramMap.remove("sign");
        if(!UrlSigUtil.verifySigSimple("checkUserName.do",paramMap,sign)){
            jsonObj.put("msg",UtilTool.ecode("������֤ʧ��!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }

        //��֤�û�Ψһ��
        List<UserInfo>userList=null;
        UserInfo u=new UserInfo();
        u.setEttuserid(Integer.parseInt(jid));
        userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            jsonObj.put("msg",UtilTool.ecode("�û�������!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        UserInfo before=userList.get(0);

        u=new UserInfo();
        //u.setDcschoolid(userList.get(0).getDcschoolid());
        u.setUsername(userName);
        userList=this.userManager.getList(u,null);
        if(userList!=null&&userList.size()>1){
            jsonObj.put("msg",UtilTool.ecode("�û����Ѵ���!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;

        }else if(userList!=null&&userList.size()==1){
            UserInfo after=userList.get(0);
            if(after.getEttuserid()==null||!after.getEttuserid().toString().equals(before.getEttuserid().toString())){
                jsonObj.put("msg",UtilTool.ecode("�û����Ѵ���!"));
                jsonObj.put("result", 0);
                response.getWriter().print(jsonObj.toString());return;
            }
        }
        jsonObj.put("result",1);
        jsonObj.put("msg", "success!");
        response.getWriter().print(jsonObj.toString());
    }

    /**
     * ETT �޸��û���Ϣ
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=updateUser.do")
    @Transactional
    public void updateEttUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!OperateEttControllerUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", UtilTool.ecode("��������!"));
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
            jsonObj.put("msg",UtilTool.ecode("��������!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        userName=URLDecoder.decode(userName,"utf-8");
        if(!UtilTool.matchingText("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$",email)){
            jsonObj.put("msg",UtilTool.ecode("�����ʽ����ȷ!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        List<UserInfo>userList=null;
        UserInfo sel=new UserInfo();
        sel.setEttuserid(Integer.parseInt(jid));
        userList=this.userManager.getList(sel,null);
        if(userList==null||userList.size()<1){
            jsonObj.put("msg",UtilTool.ecode("�û�������!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        sel=userList.get(0);
        //��֤������ʽ
        //��֤�û�����Ψһ��

        UserInfo u=new UserInfo();
        u.setUsername(userName);
        // u.setDcschoolid(Integer.parseInt(dcSchoolId));
        userList=this.userManager.getList(u,null);
        if(userList!=null&&userList.size()>1){
            jsonObj.put("msg",UtilTool.ecode("�û����Ѵ���!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }else  if(userList!=null&&userList.size()==1){
            UserInfo after=userList.get(0);
            if(after.getEttuserid()==null||!after.getEttuserid().toString().equals(sel.getEttuserid().toString())){
                jsonObj.put("msg",UtilTool.ecode("�û����Ѵ���!"));
                jsonObj.put("result", 0);
                response.getWriter().print(jsonObj.toString());return;
            }
        }
        u=new UserInfo();
        u.setMailaddress(email);
        if(userList!=null&&userList.size()>1){
            jsonObj.put("msg",UtilTool.ecode("�����Ѵ���!"));
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }else if(userList!=null&&userList.size()==1){
            UserInfo after=userList.get(0);
            if(after.getEttuserid()==null||!after.getEttuserid().toString().equals(sel.getEttuserid().toString())){
                jsonObj.put("msg",UtilTool.ecode("�����Ѵ���!"));
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
                jsonObj.put("msg", UtilTool.ecode("����ʧ��!"));
                response.getWriter().print(jsonObj.toString());return;
            }
        }
        jsonObj.put("result",1);
        jsonObj.put("msg",UtilTool.ecode("�����ɹ�!"));
        response.getWriter().print(jsonObj.toString());
    }



    /**
     * ������֤ (��UtilTool����)
     *
     * @author ֣��(2010-06-31 ����02:20:16)
     * @param expression
     * @param text
     * @return boolean
     */

    public static boolean matchingText(String expression, String text) {
        boolean bool = false;
        if (expression != null && !"".equals(expression) && text != null
                && !"".equals(text.toLowerCase())) {
            Pattern p = Pattern.compile(expression); // ������ʽ
            Matcher m = p.matcher(text.toLowerCase()); // �������ַ���
            bool = m.matches();
        }
        return bool;
    }


}


/**
 * ������
 */
class OperateEttControllerUtil{
    /**begin:*******************************��УWebIMʹ�÷���***************************************************/
    /***********************---------------��֤������Ϣ------------------*************************/
    /**
     * ��֤�û����Ƿ����
     * @param request
     * @return
     */
    public static JsonEntity validateEttUserNameHas(HttpServletRequest request,Boolean isstu) throws Exception{
        String userName=request.getParameter("userName");
        String oldUserName=request.getParameter("oldUserName");
        JsonEntity jsonEntity=new JsonEntity();
        if(userName==null||userName.trim().length()<1){
            jsonEntity.setMsg("�û�������Ϊ�գ������������!");
          return jsonEntity;
        }
        if(oldUserName!=null&&userName.trim().equals(oldUserName.trim())){
            jsonEntity.setMsg("��֤�ɹ�!");
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
        //1:�ɹ�
        if(jsonObject!=null&&jsonObject.containsKey("code")&& jsonObject.getInt("code")==1){
            jsonEntity.setType("success");
            jsonEntity.setMsg("��֤�ɹ�!");
        }else{
            jsonEntity.setType("error");
            jsonEntity.setMsg(java.net.URLDecoder.decode(jsonObject.getString("msg"),"UTF-8"));
        }
        return jsonEntity;
    }

/**end:*******************************��УWebIMʹ�÷���***************************************************/
/**begin:*******************************��У��Ŀʹ�÷���***************************************************/
    /**
     * ��У��Ŀ��ʱ��֤�û���
     * @param request
     * @return
     */
    public static JsonEntity ettColumnCheckUName(HttpServletRequest request,Boolean isstu) throws Exception{
        String userName=request.getParameter("userName");
        JsonEntity jsonEntity=new JsonEntity();
        if(userName==null||userName.trim().length()<1){
            jsonEntity.setMsg("�û�������Ϊ�գ������������!");
            return jsonEntity;
        }
        HashMap<String,String> paraMap=new HashMap<String, String>();
        String encoding="UTF-8";
        String urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETTCOLUMN_TEA_USERNAME_HAS_URL");
        Long time=new Date().getTime();
        paraMap.put("time",time.toString());
        String key="checkUserIsExists.do";//ѧ����
        if(isstu){
            urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETTCOLUMN_STU_USERNAME_HAS_URL");
            paraMap.put("userName",java.net.URLEncoder.encode(userName,"UTF-8"));
        }else{
            paraMap.put("userName",java.net.URLEncoder.encode(userName,"UTF-8"));
            key="checkUserIsExists.do";//��ʦ��
        }
        paraMap.put("sign",UrlSigUtil.makeSigSimple(key,paraMap));
        JSONObject jsonObject=UtilTool.sendPostUrlNotEncoding(urlstr,paraMap,encoding);
        //1:�ɹ�
        if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
            jsonEntity.setType("success");
            jsonEntity.setMsg("��֤�ɹ�!");
        }else{
            jsonEntity.setType("error");
            jsonEntity.setMsg(java.net.URLDecoder.decode(jsonObject.getString("msg"),"UTF-8"));
        }
        return jsonEntity;
    }


    /////////////���߷���

    /**
     * ��֤������Ϣ��
     * @param request
     * @return
     * @throws Exception
     */
    public static String validateRegisterParam(HttpServletRequest request,boolean isvalidateUnameFormat) throws Exception{
        if(!ValidateRequestParam(request)){
            return "��������Ϊ��!���������ݺ����µ��!";
        }
        HashMap<String,String> paramMap=getRequestParam(request);
        if(!paramMap.containsKey("userName"))
            return "û�м�⵽�û���!��ˢ�º�����";
        if(!paramMap.containsKey("password"))
            return "û�м�⵽����!��ˢ�º�����";
        ///////////////////��֤�û���
        //λ��6--12�ַ���1��������2���ַ���
        int uNameSize=checkWordSize(paramMap.get("userName").toString().trim());

        if(isvalidateUnameFormat){
            //�û�������6���ֻ����12����
            if(uNameSize<6||uNameSize>12)
                return "�û�����������6���ֻ����12����!�����";
            //�û���ֻ�����֡���ĸ����Сд�����»��ߡ������ַ�!
            // �����пո�
            if(!UtilTool.matchingText("[a-zA-Z0-9_\\u4e00-\\u9fa5]+",paramMap.get("userName")))
                return "�û���ֻ�����֡���ĸ����Сд�����»��ߡ������ַ�!�����";
        }
        //////////////////��֤����
        //λ��6--12�ַ�
        if(isvalidateUnameFormat){
            int passSize=paramMap.get("password").trim().length();
            if(passSize<6||passSize>12)
                return "���벻������6���ֻ����12����!�����";
            //ֻ�����֡���ĸ����Сд�����»��ߣ��ұ���ͬʱ�����ֺ���ĸ
            if(!UtilTool.matchingText("[a-zA-Z0-9_]+",paramMap.get("password")))
                return "����ֻ�����֡���ĸ����Сд�����»��ߣ��ұ���ͬʱ�����ֺ���ĸ!�����";
            //��֤�Ƿ��������
            if(!UtilTool.matchingText("[\\w[0-9]]+",paramMap.get("password")))
                return "�������ͬʱ�����ֺ���ĸ!�����";
            //��֤�Ƿ�����ַ�
            if(!UtilTool.matchingText("[\\w[a-zA-Z_]]+",paramMap.get("password")))
                return "�������ͬʱ�����ֺ���ĸ!�����";
        }
        if(paramMap.containsKey("email")){
            //��֤����
            String email=paramMap.get("email");
            if(!UtilTool.matchingText("^([\\w-\\.]{4,18})@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",email))
                return "�����ʽ����ȷ�������";
        }
        return "TRUE";
    }

    /**
     * ͳ���ַ������ַ�����(����2���ַ�)
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
     * ��֤RequestParams��ز���
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
     * ��֤RequestParams��ز���
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

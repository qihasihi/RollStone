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
        a.put("name","����ԭ���Թ�");
        a.put("sex",1);
        a.put("son","[]");
        System.out.println(JSONObject.fromObject(a).toString());

    }
    /**
     * �޸Ľ�ʦ�����Ϣ
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
        //�Ƿ��в����ǿմ���
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //��֤�������
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
            returnJo.put("msg","�����쳣�������п�ֵ!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg","���ʳ�ʱ! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //ɾ��sign
        paramMap.remove("sign");
        //���ݲ�����ǰsign���봫���sign���жԱ�
        if(!UrlSigUtil.verifySigSimple("updateTeacherClassSubject.do", paramMap, sign)){
            returnJo.put("msg","��֤ʧ��!");//md5�Ƚ��쳣
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤��JID�Ƿ����û�
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","�����ڸ��û�!");
            response.getWriter().println(returnJo.toString());return;
        }
        UserInfo activeUser=uList.get(0);
        //��֤�༶�Ƿ����
        ClassInfo cls=new ClassInfo();
        cls.setClassid(Integer.parseInt(paramMap.get("classId").trim()));
        cls.setDcschoolid(u.getDcschoolid());
        List<ClassInfo> clsList=this.classManager.getIm113List(cls,null);
        if(clsList==null||clsList.size()<1){
            returnJo.put("msg","�����ڸð༶!");
            response.getWriter().println(returnJo.toString());return;
        }
        cls=clsList.get(0);
        //��֤ targetJid�Ƿ����û�
        UserInfo targetu=new UserInfo();
        targetu.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        targetu.setEttuserid(Integer.parseInt(paramMap.get("targetJid").trim()));
        List<UserInfo> targetUList=this.userManager.getList(targetu,null);
        if(targetUList==null||targetUList.size()<1){
            returnJo.put("msg","�����ڸ��û�!");
            response.getWriter().println(returnJo.toString());return;
        }
        targetu=targetUList.get(0);
        //�õ�Ŀ���û��Ƿ�����ڸð༶
        ClassUser cu=new ClassUser();
        cu.setClassid(cls.getClassid());
        cu.setUserid(targetu.getRef());
        List<ClassUser> cuList=this.classUserManager.getList(cu,null);
        if(cuList==null||cuList.size()<1){
            returnJo.put("msg","�ð༶������Ŀ���û�!");
            response.getWriter().println(returnJo.toString());return;
        }
        //������ڣ�����ɾ���������
        if(!this.classUserManager.doDelete(cu)){
            returnJo.put("msg","ɾ��Ŀ���û��ڸð༶����Ϣʧ��!");
            response.getWriter().println(returnJo.toString());return;
        }
        boolean isAdminTrue=true;
        if(Integer.parseInt(paramMap.get("isAdmin").trim())==1){
            //��֤�Ƿ���ڰ�����
            ClassUser searchBan=new ClassUser();
            searchBan.setClassid(cls.getClassid());
            searchBan.setRelationtype("������");
            List<ClassUser> bancuList=this.classUserManager.getList(searchBan,null);
            if(bancuList!=null&&bancuList.size()>0){
                returnJo.put("msg","�����Ѵ��ڰ����Σ����ܵ��ΰ�����!");
                isAdminTrue=false;
            }else{
                ClassUser saveCu=new ClassUser();
                //UUID,����
                saveCu.setRef(UUID.randomUUID().toString());
                saveCu.setClassid(cls.getClassid());
//                saveCu.setUid(activeUser.getUserid());
                saveCu.setUserid(targetu.getRef());
                saveCu.setRelationtype("������");
                //  saveCu.setSubjectid(Integer.parseInt(sub.trim()));
                if(!this.classUserManager.doSave(saveCu)){
                    transactionRollback();
                    returnJo.put("msg","�޸İ�����ʧ��!");
                    response.getWriter().println(returnJo.toString());
                    return;
                }
//              returnJo.put("msg","��Ӱ����γɹ�!");
            }
        }


        //���
        //�������subjectid,˵������ʦ

        if(paramMap.get("subjectId")!=null&&paramMap.get("subjectId").trim().length()>0){
            String[] subArray=paramMap.get("subjectId").split(",");
            for(String sub:subArray){
                if(sub!=null&&sub.trim().length()>0){
                    ClassUser saveCu=new ClassUser();
                    //UUID,����
                    saveCu.setRef(UUID.randomUUID().toString());
                    saveCu.setClassid(cls.getClassid());
                    saveCu.setUserid(targetu.getRef());
                    saveCu.setUid(activeUser.getUserid());
                    saveCu.setRelationtype("�ο���ʦ");
                    saveCu.setSubjectid(Integer.parseInt(sub.trim()));
                    if(!this.classUserManager.doSave(saveCu)){
                        transactionRollback();
                        returnJo.put("msg","�޸İ༶��Աʧ��!");
                        response.getWriter().println(returnJo.toString());
                        return;
                    }
                }
            }
        }else{
            if(!isAdminTrue){
                returnJo.put("msg","�޸���Ϣʧ��!"+returnJo.get("msg"));
                returnJo.put("result",0);
                transactionRollback();
                response.getWriter().println(returnJo.toString());
                return;
            }
        }
        //��ѯ�ð༶��Ա����ETT����ͬ��

        ClassUser searchCu=new ClassUser();
        searchCu.setClassid(cls.getClassid());
        List<ClassUser> searchCuList=this.classUserManager.getList(cu,null);
        if(!updateEttClassUser(searchCuList,cls.getClassid(),u.getDcschoolid())){
            //�ع�����
            transactionRollback();
            returnJo.put("msg","����У��Ӱ༶��Ա��Ϣʧ��!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        returnJo.put("msg","��Ϣ�޸ĳɹ�!"+returnJo.get("msg"));
        returnJo.put("result",1);

        response.getWriter().println(returnJo.toString());
    }

    /**
     * ������
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=createClass.do",method=RequestMethod.POST)
    @Transactional
    public void createClass(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        //�Ƿ��в����ǿմ���
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //��֤�������
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
            returnJo.put("msg","�����쳣�������п�ֵ!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg","���ʳ�ʱ! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //ɾ��sign
        paramMap.remove("sign");
        //���ݲ�����ǰsign���봫���sign���жԱ�
        if(!UrlSigUtil.verifySigSimple("createClass.do", paramMap, sign)){
            returnJo.put("msg","��֤ʧ��!");//md5�Ƚ��쳣
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤��JID�Ƿ����û�
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","�����ڸ��û�! jid:"+u.getEttuserid()+" ");
            response.getWriter().println(returnJo.toString());return;
        }
        UserInfo activeUser=uList.get(0);
        //�꼶�õ�
        GradeInfo g=new GradeInfo();
        g.setGradeid(Integer.parseInt(paramMap.get("gradeId").toString()));
        List<GradeInfo> gList=gradeManager.getList(g,null);
        if(gList==null||gList.size()<1){
            returnJo.put("msg","�����ڸ��꼶!");
            response.getWriter().println(returnJo.toString());return;
        }
        String gradeName=gList.get(0).getGradevalue();
        String termYear=termManager.getAutoTerm().getYear();

        //��֤�ð༶�������Ƿ��Ѵ�����
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
                    returnJo.put("msg","�÷�У����İ༶�����Ѵ�����!�޷��ٴ����༶!");
                    response.getWriter().println(returnJo.toString());return;
                }
            }else{
                returnJo.put("msg","�쳣��������У�õ��ð༶����ʧ��!");
                response.getWriter().println(returnJo.toString());return;
            }
        }
        //��֯���ݣ�׼�����
        int newClsId=genderIdCode(6);
        //�ѵ���ǰѧ��ĵ�ǰ�꼶�Ƿ���ڸð༶����
        ClassInfo cls=new ClassInfo();
        cls.setDcschoolid(u.getDcschoolid());
        cls.setClassname(paramMap.get("className").trim());
        cls.setClassgrade(gradeName);
        List<ClassInfo> clsList=this.classManager.getList(cls,null);
        if(clsList!=null&&clsList.size()>0){
            returnJo.put("msg",gradeName+cls.getClassname()+" �Ѿ�����!");
            response.getWriter().println(returnJo.toString());return;
        }
        cls.setClassid(newClsId);
        cls.setPattern("������");
        cls.setClassid(newClsId);
        cls.setType("NORMAL");
        cls.setYear(termYear);
        cls.setAllowjoin(1);//����ѧ������
        cls.setCuserid(activeUser.getUserid());
        cls.setIsflag(1);
        cls.setImvaldatecode(genderInviteCode(6));//���������λ������֤���ظ�
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
            returnJo.put("msg","�����༶ʧ��!");
            response.getWriter().println(returnJo.toString());
            transactionRollback();
            return;
        }
        //��ӽ�ʦ��¼
        //��ѯ�Ѿ��ڵ���Ա
        ClassUser classUser=new ClassUser();
        classUser.setClassid(newClsId);
        List<ClassUser> tmpCuList=this.classUserManager.getList(classUser,null);
        if(tmpCuList==null||tmpCuList.size()<1){
            tmpCuList=new ArrayList<ClassUser>();
        }
        //�������subjectid,˵������ʦ
        if(Integer.parseInt(paramMap.get("isAdmin").trim())==1){
            ClassUser saveCu=new ClassUser();
            //UUID,����
            saveCu.setRef(UUID.randomUUID().toString());
            saveCu.setClassid(newClsId);
            saveCu.setUid(activeUser.getUserid());
            saveCu.setUserid(activeUser.getRef());
            saveCu.setRelationtype("������");
            //  saveCu.setSubjectid(Integer.parseInt(sub.trim()));
            if(!this.classUserManager.doSave(saveCu)){
                returnJo.put("msg","��Ӱ༶��Աʧ��!");
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
                    //UUID,����
                    saveCu.setRef(UUID.randomUUID().toString());
                    saveCu.setClassid(newClsId);
                    saveCu.setUserid(activeUser.getRef());
                    saveCu.setUid(activeUser.getUserid());
                    saveCu.setRelationtype("�ο���ʦ");
                    saveCu.setSubjectid(Integer.parseInt(sub.trim()));
                    if(!this.classUserManager.doSave(saveCu)){
                        returnJo.put("msg","��Ӱ༶��Աʧ��!");
                        response.getWriter().println(returnJo.toString());
                        transactionRollback();
                        return;
                    }
                    tmpCuList.add(saveCu);
                }
            }
        }

        //ѧ������༶��̬
        MyInfoUserInfo info=new MyInfoUserInfo();
        info.setUserref(activeUser.getRef());
        info.setMsgid(-1);
        info.setMsgname("IM֪ͨ");
        info.setMydata(activeUser.getRealname()+"|"+cls.getClassgrade()+cls.getClassname());
        info.setTemplateid(18);
        info.setClassid(cls.getClassid());
        if(!this.myInfoUserManager.doSave(info)){
            returnJo.put("msg","��Ӷ�̬ʧ��!");
            response.getWriter().println(returnJo.toString());
            transactionRollback();
            return;
        }
        //�����ִ�гɹ�����
        if(!addClassBaseToEtt(cls)){
            //�ع�����
            transactionRollback();
            returnJo.put("msg","����У��Ӱ༶��Ϣʧ��!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        //����ɹ���������Ա����
        if(tmpCuList!=null&&tmpCuList.size()>0){
            if(!updateEttClassUser(tmpCuList,cls.getClassid(),u.getDcschoolid())){
                //�ع�����
                transactionRollback();
                returnJo.put("msg","��Ӱ༶��Ա��Ϣʧ��!");
                response.getWriter().println(returnJo.toString());
                return;
            }
        }
        returnJo.put("result",1);
        returnJo.put("msg","��Ӱ༶�����Ϣ�ɹ�!");
        //���ص���Ϣ
        JSONObject dataJSON=new JSONObject();
        dataJSON.put("classId",cls.getClassid());
        dataJSON.put("inviteCode",cls.getImvaldatecode());
        returnJo.put("data",dataJSON.toString());
        response.getWriter().println(returnJo.toString());
    }


    /**
     * M-SCH61.��ѯ��ʦ����༶�б�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=manageClassList.do")
    public void manageClassList(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        //�Ƿ��в����ǿմ���
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //��֤�������
        if(!paramMap.containsKey("jid")
                ||!paramMap.containsKey("schoolId")
                ||!paramMap.containsKey("time")
                ||!paramMap.containsKey("sign")){
            returnJo.put("msg","�����쳣�������п�ֵ!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg","���ʳ�ʱ! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //ɾ��sign
        paramMap.remove("sign");
        //���ݲ�����ǰsign���봫���sign���жԱ�
        if(!UrlSigUtil.verifySigSimple("manageClassList.do", paramMap, sign)){
            returnJo.put("msg","��֤ʧ��!");//md5�Ƚ��쳣
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤��JID�Ƿ����û�
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","�����ڸ��û�!");
            response.getWriter().println(returnJo.toString());return;
        }
        UserInfo activeUser=uList.get(0);
        //
        ClassInfo cls=new ClassInfo();
        cls.setDcschoolid(activeUser.getDcschoolid());
        cls.setCuserid(activeUser.getUserid());  //��ѯ��̬��ʱ���õ�
        cls.setSearchUid(activeUser.getUserid());//c_user_id����userid��ѯ
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
                    //��ѯ��̬����
                    jo.put("newDynamic",tmpCls.getDynamicCount()==null?0:tmpCls.getDynamicCount());
                    jsonArray.add(jo);
                }
            }
        }
        returnJo.put("result",1);
        returnJo.put("msg","��ѯ�ɹ�!");
        returnJo.put("data",jsonArray.toString());
        response.getWriter().println(returnJo.toString());

    }


    /**
     * ѧϰĿ¼�ӿ�
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
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"��֤ʧ�ܣ��Ƿ���¼\"}");
            return;
        }
        int utype=ImInterFaceUtil.getUserType(usertype);
        UserInfo ui = new UserInfo();
        ui.setEttuserid(Integer.parseInt(userid));
        List<UserInfo> userList = this.userManager.getList(ui,null);
        if(userList==null||userList.size()<1){
            response.getWriter().print("{\"result\":\"0\",\"msg\":\"��ǰ�û�δ�󶨣�����ϵ����Ա\"}");
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
        m2.put("newDynamicAll",this.myInfoUserManager.getImMsgData(null,userList.get(0).getUserid(),tm.getYear()));//�ܵ�����
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
            m.put("msg","��ǰ�û�û��ѧϰĿ¼������ϵ����Ա");
        }
        m.put("result","1");
        m.put("msg","�ɹ�");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.setContentType("text/json");
        response.getWriter().print(object.toString());
    }

    /**
     * IM-SCH56.ѧ������༶�ӿ�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=studentJoinClass.do",method=RequestMethod.POST)
    @Transactional
    public void studentJoinClass(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //userType   1:ѧ��  2����ʦ  3���ҳ�
        response.getWriter().println(joinClass(request,1,"studentJoinClass.do"));
    }
    /**
     * IM-SCH55.��ʦ����༶�ӿ�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=teacherJoinClass.do",method=RequestMethod.POST)
    @Transactional
    public void teacherJoinClass(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //userType   1:ѧ��  2����ʦ  3���ҳ�
        response.getWriter().println(joinClass(request,2,"teacherJoinClass.do"));
    }
    /**
     * IM-SCH57.ɾ���༶��Ա
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
            returnJo.put("msg","�����쳣�������п�ֵ!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        //�Ƿ��в����ǿմ���
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //��֤�������
        if(!paramMap.containsKey("jid")
                ||!paramMap.containsKey("schoolId")
                ||!paramMap.containsKey("classId")
                ||!paramMap.containsKey("noJoin")
                ||!paramMap.containsKey("targetJid")
                ||!paramMap.containsKey("time")
                ||!paramMap.containsKey("sign")){
            returnJo.put("msg","�����쳣�������п�ֵ!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg","���ʳ�ʱ! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //ɾ��sign
        paramMap.remove("sign");
        //���ݲ�����ǰsign���봫���sign���жԱ�
        if(!UrlSigUtil.verifySigSimple("removeClassMember.do", paramMap, sign)){
            returnJo.put("msg","��֤ʧ��!");//md5�Ƚ��쳣
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤��JID�Ƿ����û�
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","�����ڸ��û�! jid:"+u.getEttuserid()+" ");
            response.getWriter().println(returnJo.toString());return;
        }
        //�õ��ķ����û�
        UserInfo activeUser=uList.get(0);
        //�õ��༶
        ClassInfo cls=new ClassInfo();
        cls.setClassid(Integer.parseInt(paramMap.get("classId")));
        cls.setDcschoolid(u.getDcschoolid());
        //cls.setSearchUid(activeUser.getUserid());
        cls.setCuserid(activeUser.getUserid());
        List<ClassInfo> clsList=this.classManager.getIm113List(cls,null);
        if(clsList==null||clsList.size()<1){
            returnJo.put("msg","�����ڰ༶�����ܰ༶���У��ƥ��!");
            response.getWriter().println(returnJo.toString());return;
        }
        cls=clsList.get(0);
        //��֤����ɾ������
        if(Integer.parseInt(paramMap.get("targetJid"))==Integer.parseInt(paramMap.get("jid").trim())){
            returnJo.put("msg","����ɾ���Լ�!");
            response.getWriter().println(returnJo.toString());return;
        }


        //���targetJid
        UserInfo targetUInfo=new UserInfo();
        targetUInfo.setEttuserid(Integer.parseInt(paramMap.get("targetJid")));
        targetUInfo.setDcschoolid(u.getDcschoolid());
        List<UserInfo> targetUList=this.userManager.getList(targetUInfo,null);
        if(targetUList==null||targetUList.size()<1){
            returnJo.put("msg","Ŀ���û�������!");
            response.getWriter().println(returnJo.toString());return;
        }
        targetUInfo=targetUList.get(0);

        //���targetJid�Ƿ�����ڸð༶��
        ClassUser targetCu=new ClassUser();
        targetCu.setClassid(cls.getClassid());
        targetCu.setUserid(targetUInfo.getRef());
        List<ClassUser> targetCuList=this.classUserManager.getList(targetCu,null);
        if(targetCuList==null||targetCuList.size()<1){
            returnJo.put("msg","Ŀ���û���������Ŀ��༶��!");
            response.getWriter().println(returnJo.toString());return;
        }


        //�����û�ɾ��
        if(!this.classUserManager.doDelete(targetCu)){
            transactionRollback();
            returnJo.put("msg","����ʧ��,ԭ��ɾ���༶��������ʧ��!");
            response.getWriter().println(returnJo.toString());return;
        }
        //��Ӽ�¼
        if(Integer.parseInt(paramMap.get("noJoin").trim())==1){
            boolean isSave=this.myInfoUserManager.doSaveUserClassInJoinLog
                    (targetUInfo.getUserid(),cls.getClassid(),cls.getDcschoolid(),Integer.parseInt(paramMap.get("noJoin").trim()),activeUser.getUserid());
            if(!isSave){
                transactionRollback();
                returnJo.put("msg","���ʧ��!");
                response.getWriter().println(returnJo.toString());return;
            }
        }
        //����������Ett
        ClassUser cuTmp=new ClassUser();
        cuTmp.setClassid(cls.getClassid());
        List<ClassUser> cuTmpList=this.classUserManager.getList(cuTmp,null);
        if(cuTmpList==null)
            cuTmpList=new ArrayList<ClassUser>();
        if(!updateEttClassUser(cuTmpList,cls.getClassid(),cls.getDcschoolid())){
            transactionRollback();
            returnJo.put("msg","����ʧ��!");
            response.getWriter().println(returnJo.toString());return;
        }
        returnJo.put("result",1);
        returnJo.put("msg","�����ɹ�!");
        response.getWriter().println(returnJo.toString());
    }
    /**
     * IM-SCH57.�༶�����б�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=classDetailInfo.do")
    public void classDetailInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        //�Ƿ��в����ǿմ���
        HashMap<String,String> paramMap=ImInterFaceUtil.getRequestParam(request);
        //��֤�������
        if(!paramMap.containsKey("jid")
                ||!paramMap.containsKey("schoolId")
                ||!paramMap.containsKey("classId")
                ||!paramMap.containsKey("time")
                ||!paramMap.containsKey("sign")){
            returnJo.put("msg","�����쳣�������п�ֵ!");
            response.getWriter().println(returnJo.toString());
            return;
        }
        String time=paramMap.get("time");
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg","���ʳ�ʱ! >3 min!");
            response.getWriter().print(returnJo.toString());
            return;
        }
        String sign=paramMap.get("sign");
        //ɾ��sign
        paramMap.remove("sign");
        //���ݲ�����ǰsign���봫���sign���жԱ�
        System.out.println(UrlSigUtil.makeSigSimple("classDetailInfo.do", paramMap));
        if(!UrlSigUtil.verifySigSimple("classDetailInfo.do", paramMap, sign)){
            returnJo.put("msg","��֤ʧ��!");//md5�Ƚ��쳣
            response.getWriter().print(returnJo.toString());
            return;
        }
        //��֤��JID�Ƿ����û�
        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(paramMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paramMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","�����ڸ��û�!");
            response.getWriter().println(returnJo.toString());return;
        }
        //�õ��ķ����û�
        UserInfo activeUser=uList.get(0);
        //�õ��༶
        ClassInfo cls=new ClassInfo();
        cls.setClassid(Integer.parseInt(paramMap.get("classId")));
        cls.setDcschoolid(u.getDcschoolid());
        cls.setCuserid(activeUser.getUserid());
        List<ClassInfo> clsList=this.classManager.getIm113List(cls,null);
        if(clsList==null||clsList.size()<1){
            returnJo.put("msg","�����ڰ༶�����ܰ༶���У��ƥ��!");
            response.getWriter().println(returnJo.toString());return;
        }
        cls=clsList.get(0);
        JSONObject clsDetailJo=new JSONObject();
        clsDetailJo.put("className",(cls.getClassname()));
        clsDetailJo.put("classCode",cls.getImvaldatecode());
        System.out.println(cls.getDynamicCount());
        clsDetailJo.put("newDynamic",cls.getDynamicCount()==null?0:cls.getDynamicCount());//����Ϣ��̬
        //�ð༶��������ʦ(�����Σ��ο���ʦ)
        ClassUser cu=new ClassUser();
        cu.setClassid(cls.getClassid());
        //������
        cu.setRelationtype("������");
        List<ClassUser> banCuList=this.classUserManager.getList(cu, null);

        List<Map<String,Object>> classUserMapList=new ArrayList<Map<String, Object>>();
        if(banCuList!=null&&banCuList.size()>0){
            //userType  1:ѧ��  2����ʦ
            List<Map<String,Object>> tmpList=ImInterFaceUtil
                    .getClassUserEttPhoneAndName(banCuList, 3, activeUser.getDcschoolid(), activeUser.getEttuserid());
            classUserMapList.addAll(tmpList);
        }
        //������
        cu.setRelationtype("�ο���ʦ");
        List<ClassUser> teaCuList=this.classUserManager.getList(cu,null);
        if(teaCuList!=null&&teaCuList.size()>0){
            //userType  1:ѧ��  2����ʦ
            List<Map<String,Object>> tmpList=ImInterFaceUtil
                    .getClassUserEttPhoneAndName(teaCuList, 2, activeUser.getDcschoolid(), activeUser.getEttuserid());
            classUserMapList.addAll(tmpList);
        }
        JSONArray userListArray=new JSONArray();
        JSONObject tmpJo=new JSONObject();
        tmpJo.put("title",("��ʦ"));


        //�ϲ�
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
                                sub+="��"+mp.get("subject").toString().trim();
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
        //�õ�ѧ��
        JSONObject tmpStuJo=new JSONObject();
        tmpStuJo.put("title",("ѧ��"));
        cu.setRelationtype("ѧ��");
        List<ClassUser> stuCuList=this.classUserManager.getList(cu,null);
        tmpStuJo.put("count",stuCuList==null?0:stuCuList.size());
        List<Map<String,Object>> tmpList=null;
        //userType  1:ѧ��  2����ʦ
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
        //�õ��༶��̬()
        String dymStr=null;
        MyInfoUserInfo mu=new MyInfoUserInfo();
        mu.setClassid(cls.getClassid());
        mu.setTemplateid(18);  //18:��ʾIm�˶�̬

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
                            //         (tmpMyInfo.getRealname() + " ������ " + cls.getClassname() + "!", "UTF-8")
                            (tmpMyInfo.getDynamicString())
                    );
                    returnMap.put("photo", "http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
                    returnUserRecord.add(returnMap);
                }
            }
            //(ett)userType  3:ѧ��  2����ʦ
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
                                        (jo.getString("realName")+" ������ "+cls.getClassname()+"!"));
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
                                        (jo.getString("realName")+"������"+cls.getClassname()+"!"));
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
        returnJo.put("msg","�����ɹ�!");
        returnJo.put("data",clsDetailJo.toString());
        response.getWriter().println(returnJo.toString());
    }


    /**
     * ѧ������ʦ����༶
     * @param request
     * @param userType   1:ѧ��  2����ʦ  3���ҳ�
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String joinClass(HttpServletRequest request,Integer userType,String managerKeyCode)throws Exception{
        HashMap<String,String> paraMap=ImInterFaceUtil.getRequestParam(request);
        JSONObject returnJo=new JSONObject();
        returnJo.put("result",0);
        returnJo.put("msg","");
        if(paraMap==null||userType==null||(userType!=1&&userType!=2)){
            returnJo.put("msg","������ȡ�쳣!");
            return returnJo.toString();
        }
        if(!paraMap.containsKey("jid")||paraMap.get("jid")==null||paraMap.get("jid").trim().length()<1
                ||!paraMap.containsKey("classCode")||paraMap.get("classCode")==null
                ||paraMap.get("classCode").trim().length()<1
                ||!paraMap.containsKey("time")||paraMap.get("time")==null||paraMap.get("time").trim().length()<1
                ||!paraMap.containsKey("sign")||paraMap.get("sign")==null||paraMap.get("sign").trim().length()<1){
            returnJo.put("msg","�����쳣����������Ƿ���ڿ�ֵ!");
            return returnJo.toString();
        }
        String isAdmin=paraMap.get("isAdmin");
        String subjectId=paraMap.get("subjectId");
        //����ǽ�ʦ������subjectId��isAdmin���ж�
        if(userType==2){
            if(isAdmin==null&&subjectId==null
                    ||Integer.parseInt(isAdmin)==0&&(subjectId==null||subjectId.trim().length()<1)){
                returnJo.put("msg","�����쳣����������Ƿ���ڿ�ֵ!");
                return returnJo.toString();
            }
        }
        //�����֤
        String time=paraMap.get("time");
        //��֤ʱ��
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(time.trim());
        Long nt=System.currentTimeMillis();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
            returnJo.put("msg", "���ʳ�ʱ! >3 min!");
            return returnJo.toString();
        }
        String sign=paraMap.get("sign");
        //ɾ��sign
        paraMap.remove("sign");
        //���ݲ�����ǰsign���봫���sign���жԱ�
        if(!UrlSigUtil.verifySigSimple(managerKeyCode, paraMap, sign)){
            returnJo.put("msg", "��֤ʧ��!");//md5�Ƚ��쳣
            return returnJo.toString();
        }
        //��֤��JID�Ƿ����û�
        UserInfo u=new UserInfo();
//        u.setDcschoolid(Integer.parseInt(paraMap.get("schoolId").trim()));
        u.setEttuserid(Integer.parseInt(paraMap.get("jid").trim()));
        List<UserInfo> uList=this.userManager.getList(u,null);
        if(uList==null||uList.size()<1){
            returnJo.put("msg","�����ڸ��û�!");
            return returnJo.toString();
        }
        UserInfo activeUser=uList.get(0);
        //��֤�༶
        ClassInfo cls=new ClassInfo();
        cls.setImvaldatecode(paraMap.get("classCode").trim());
        List<ClassInfo> clsList=this.classManager.getList(cls,null);
        if(clsList==null||clsList.size()<1){
            returnJo.put("msg","����������Ч!");
            return returnJo.toString();
        }

        cls=clsList.get(0);


        //��֤�ð༶�Ĵ�����
        if(cls.getCuserid()!=null&&activeUser.getUserid()!=null&&cls.getDcschoolid()!=null&&activeUser.getDcschoolid()!=null){
            System.out.println("cls.cuserid:"+cls.getCuserid().intValue());
            System.out.println("activeuser.cuserid:"+cls.getCuserid().intValue());
            System.out.println("cls.dcschoolid:"+cls.getCuserid().intValue());
            System.out.println("activeuser.dcschoolid:"+cls.getCuserid().intValue());
            if(cls.getCuserid().intValue()==activeUser.getUserid().intValue()
                    &&cls.getDcschoolid().intValue()== activeUser.getDcschoolid().intValue()){
                //˵���������������ѡ���ʾ���ܼ��뵽���Ѵ����İ༶
                returnJo.put("msg","���ܼ��뵽�Լ������İ༶!");
                return  returnJo.toString();
            }
        }

        //�жϸ��û��Ƿ�����ڸð༶��
        ClassUser tmpCu=new ClassUser();
        tmpCu.setClassid(cls.getClassid());
        tmpCu.setUserid(activeUser.getRef());
        //  tmpCu.setRelationtype("������");
        List<ClassUser> tmpCuList=this.classUserManager.getList(tmpCu,null);
        if(tmpCuList!=null&&tmpCuList.size()>0){
            returnJo.put("msg","���û��Ѿ������ڸð༶�У���Ч����!");
            return returnJo.toString();
        }
        //��ѯ�����Ƿ��������
        List<Map<String,Object>> mapList=this.myInfoUserManager.getUserClassInJoinLog(activeUser.getUserid(),cls.getClassid(),cls.getDcschoolid());
        if(mapList!=null&&mapList.size()>0
                &&mapList.get(0).get("ALLOW_INJOIN")!=null
                &&Integer.parseInt(mapList.get(0).get("ALLOW_INJOIN").toString().trim())==1){
            //˵�����������༶��
            returnJo.put("msg","���û��Ѿ�����ֹ���뵱ǰ�༶��!");
            return returnJo.toString();
        }

        if(userType==2){
            //��֤�Ƿ��ǰ�����
            if(isAdmin!=null&&Integer.parseInt(isAdmin)==1){
                //��֤�ð༶�Ƿ���ڰ�����
                ClassUser cu=new ClassUser();
                cu.setClassid(cls.getClassid());
                cu.setRelationtype("������");
                List<ClassUser> cuList=this.classUserManager.getList(cu,null);
                if(cuList!=null&&cuList.size()>0){
                    returnJo.put("msg","�����Ѵ��ڰ����Σ����ܵ��ΰ�����!");
                    //return returnJo.toString();
                }else{
                    cu.setUserid(activeUser.getRef());
                    cu.setRef(this.classUserManager.getNextId());
                    if(!this.classUserManager.doSave(cu)){
                        returnJo.put("msg","����༶ʧ��!");
                        transactionRollback();
                        return returnJo.toString();
                    }
                    returnJo.put("result",1);
                }
            }
            //˵���ǽ�ʦ��
            boolean isbreak=false;
            if(subjectId!=null&&subjectId.trim().length()>0){
                String[] subjectArrayId=subjectId.split(",");
                for(String sub:subjectArrayId){
                    if(sub!=null&&sub.trim().length()>0){
                        ClassUser cu=new ClassUser();
                        cu.setClassid(cls.getClassid());
                        cu.setUserid(activeUser.getRef());
                        cu.setSubjectid(Integer.parseInt(sub.trim()));
                        cu.setRelationtype("�ο���ʦ");
                        List<ClassUser> cuList=this.classUserManager.getList(cu,null);
                        if(cuList==null||cuList.size()<1){
                            cu.setRef(this.classUserManager.getNextId());
                            if(!this.classUserManager.doSave(cu)){
                                isbreak=true;
                                returnJo.put("msg","����༶ʧ��!");
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
                    returnJo.put("msg","����༶�ɹ�!"+returnJo.get("msg"));
                    returnJo.put("result",1);
                }else{
                    if(returnJo.get("result").toString().trim().equals("1")){
                        returnJo.put("msg","����༶�ɹ�!");
                    }else{
                        returnJo.put("msg","����༶ʧ��!"+returnJo.get("msg"));
                        transactionRollback();
                    }
                }
            }

        }else if(userType==1){//�����ѧ������ֱ���ж��Ƿ���ڣ����
            ClassUser cu=new ClassUser();
            cu.setClassid(cls.getClassid());
            cu.setUserid(activeUser.getRef());
            cu.setRelationtype("ѧ��");
            List<ClassUser> cuList=this.classUserManager.getList(cu,null);
            if(cuList==null||cuList.size()<1){
                cu.setRef(this.classUserManager.getNextId());
                if(!this.classUserManager.doSave(cu)){
                    returnJo.put("msg","����༶ʧ��!");
                    transactionRollback();
                }else{
                    returnJo.put("msg","����༶�ɹ�!");
                    returnJo.put("result",1);
                }
            }else{
                returnJo.put("msg","�Ѿ������ڸð���!");
            }
        }else{
            returnJo.put("msg","��ݴ���!");
            transactionRollback();
        }
        if(Integer.parseInt(returnJo.get("result").toString())==1){
            //ѧ������༶��̬
            MyInfoUserInfo info=new MyInfoUserInfo();
            info.setUserref(activeUser.getRef());
            info.setMsgid(-1);
            info.setMsgname("IM֪ͨ");
            info.setMydata(activeUser.getRealname()+"|"+cls.getClassgrade()+cls.getClassname());
            info.setTemplateid(18);
            info.setClassid(cls.getClassid());
            if(!this.myInfoUserManager.doSave(info)){
                returnJo.put("msg", "��Ӷ�̬ʧ��!");
                transactionRollback();
                return returnJo.toString();
            }
            //ͬ�����ݵ���У
            ClassUser cutmp=new ClassUser();
            cutmp.setClassid(cls.getClassid());
            List<ClassUser> cuTmpList=this.classUserManager.getList(cutmp,null);
            if(cuTmpList==null||cuTmpList.size()<1){
                returnJo.put("result",0);
                returnJo.put("msg","ʧ�ܣ�ͬ��ʧ��!");
                transactionRollback();
            }else{
                if(!updateEttClassUser(cuTmpList,cls.getClassid(),activeUser.getDcschoolid())){
                    returnJo.put("result",0);
                    returnJo.put("msg","ʧ�ܣ�ͬ��ʧ�� !");
                    transactionRollback();
                }
            }
        }
        return returnJo.toString();
    }
    /**
     * ��Ӱ༶��Ϣ��Ett
     * @param clsEntity
     * @return
     */
    private boolean addClassBaseToEtt(ClassInfo clsEntity){
        if(clsEntity==null)
            return false;
        //����ETT�༶��Ϣ
        return EttInterfaceUserUtil.addClassBase(clsEntity);
    }
    /**
     * ���°༶��Ϣ
     * @param cuTmpList �༶��Ա��ϢClassUser  ע�������ClassUser.Uid
     * @param dcschoolid  ��УID
     * @return
     */
    @Transactional
    private boolean updateEttClassUser(List<ClassUser> cuTmpList,Integer classid,Integer dcschoolid){
        List<Map<String,Object>> mapList=null;
        if(cuTmpList!=null){
            // �ش� userId��userType,subjectId ������key
            mapList=new ArrayList<Map<String, Object>>();
            for (ClassUser cuTmpe:cuTmpList){
                if(cuTmpe!=null){
                    Map<String,Object> tmpMap=new HashMap<String, Object>();
                    tmpMap.put("userId",cuTmpe.getUid());
                    Integer userType=3;
                    if(cuTmpe.getRelationtype()!=null){
                        if(cuTmpe.getRelationtype().trim().equals("�ο���ʦ"))
                            userType=2;
                        else if(cuTmpe.getRelationtype().trim().equals("������"))
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
            System.out.println("classUserͬ������Уʧ��!");
            return false;
        } else
            System.out.println("classUserͬ������У�ɹ�!");
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
            jsonObj.put("msg", "��������!");
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
            jsonObj.put("msg","������֤ʧ��!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        //��֤������ʽ
        String msg=validateRegisterParam(request);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonObj.put("msg",msg);
                jsonObj.put("result",0);
                response.getWriter().println(jsonObj.toString());return;
            }
        }
        //��֤�û�����Ψһ��
        List<UserInfo>userList=null;
        UserInfo u=new UserInfo();
        u.setUsername(userName);
        userList=this.userManager.getList(u,null);
        if(userList!=null&&userList.size()>0){
            jsonObj.put("msg","�û����Ѵ���!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        u=new UserInfo();
        u.setMailaddress(userName);
        if(userList!=null&&userList.size()>0){
            jsonObj.put("msg","�û����Ѵ���!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }

        //��ȡѧУ��Ϣ
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

        //����û�
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

        //����û�����ݹ�����Ϣ
        String identityNextRef = UUID.randomUUID().toString();
        UserIdentityInfo ui = new UserIdentityInfo();
        ui.setRef(identityNextRef);
        ui.getUserinfo().setRef(userNextRef);
        ui.setIdentityname("��ְ��");
        sql = new StringBuilder();
        objList = this.userIdentityManager.getSaveSql(ui, sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }


        TeacherInfo t = new TeacherInfo();
        t.setUserid(userNextRef);
        t.setTeachername(realName);
        t.setTeachersex("��");

        sql = new StringBuilder();
        objList = this.teacherManager.getSaveSql(t, sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        //���Ĭ�Ͻ�ʦ��ɫ
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

        //��ӽ�ʦ��ɫĬ��Ȩ��
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
                    jsonObj.put("msg","��ǰ�û�������!");
                    response.getWriter().print(jsonObj.toString());return;
                }
                tmpUser=tmpUserList.get(0);

                //��Уע��
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
                        jsonObj.put("msg", "ע��ʧ��!");
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
        jsonObj.put("msg", "ע��ɹ�!");
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
            jsonObj.put("msg", "��������!");
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
            jsonObj.put("msg","��������!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        //��֤������ʽ
        String msg=validateRegisterParam(request);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonObj.put("msg",msg);
                jsonObj.put("result",0);
                response.getWriter().println(jsonObj.toString());return;
            }
        }
        //��֤�û�����Ψһ��
        List<UserInfo>userList=null;
        UserInfo u=new UserInfo();
        u.setUsername(userName);
        userList=this.userManager.getList(u,null);
        if(userList!=null&&userList.size()>0){
            jsonObj.put("msg","�û����Ѵ���!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        u=new UserInfo();
        u.setMailaddress(userName);
        if(userList!=null&&userList.size()>0){
            jsonObj.put("msg","�û����Ѵ���!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }

        //��ȡ�༶��Ϣ
        ClassInfo classInfo=new ClassInfo();
        classInfo.setImvaldatecode(classCode);
        List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
        if(clsList==null||clsList.size()<1){
            jsonObj.put("msg","��ǰ�༶������!");
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

        //����û�
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

        //����û�����ݹ�����Ϣ
        String identityNextRef = UUID.randomUUID().toString();
        UserIdentityInfo ui = new UserIdentityInfo();
        ui.setRef(identityNextRef);
        ui.getUserinfo().setRef(userNextRef);
        ui.setIdentityname("ѧ��");
        sql = new StringBuilder();
        objList = this.userIdentityManager.getSaveSql(ui, sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }


        StudentInfo s = new StudentInfo();
        s.setUserref(userNextRef);
        s.setStuname(realName);
        s.setStusex("��");
        //s.setStuno();

        sql = new StringBuilder();
        objList = this.studentManager.getSaveSql(s, sql);
        if (objList != null && sql != null) {
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }

        //���Ĭ��ѧ����ɫ
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

        //���ѧ����ɫĬ��Ȩ��
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
        //���
        ClassUser sel=new ClassUser();
        sel.setClassid(tmpCls.getClassid());
        sel.setUserid(userNextRef);
        sel.setRelationtype("ѧ��");
        List<ClassUser>stuList=this.classUserManager.getList(sel,null);
        if(stuList==null||stuList.size()<1){
            ClassUser save=new ClassUser();
            save.setClassid(tmpCls.getClassid());
            save.setUserid(userNextRef);
            save.setRelationtype("ѧ��");
            save.setRef(this.classUserManager.getNextId());
            sql=new StringBuilder();
            objList=this.classUserManager.getSaveSql(save,sql);
            if(objList!=null&&sql!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }

        //ѧ������༶��̬
        MyInfoUserInfo info=new MyInfoUserInfo();
        info.setUserref(userNextRef);
        info.setMsgid(-1);
        info.setMsgname("IM֪ͨ");
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
                    jsonObj.put("msg","��ǰ�û�������!");
                    response.getWriter().print(jsonObj.toString());return;
                }
                tmpUser=tmpUserList.get(0);

                //��Уע��
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
                        jsonObj.put("msg", "ע��ʧ��!");
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
        jsonObj.put("msg", "ע��ɹ�!");
        response.getWriter().print(jsonObj.toString());
    }


    /**
     * ѧ����ȡ�༶��Ϣ
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=searchClass.do")
    @Transactional
    public void stuSearchClass(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!ImInterFaceUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", "��������!");
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
            jsonObj.put("msg","������֤ʧ��!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        paramMap.remove("sign");
        if(!UrlSigUtil.verifySigSimple("searchClass.do", paramMap, sign)){
            jsonObj.put("msg","������֤ʧ��!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());
            return;
        }



        //��ȡ��ʦ�༶��Ϣ
        ClassInfo classInfo=new ClassInfo();
        classInfo.setImvaldatecode(classCode);
        classInfo.setDcschoolid(Integer.parseInt(schoolId));
        // classInfo.setSearchUid(tea.getUserid());
        List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
        if(clsList==null||clsList.size()<1){
            jsonObj.put("msg","��ǰ�༶������!");
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

        String msg="ȷ�ϼ���";
        if(teacherName!=null&&teacherName.length()>0)
            msg+=teacherName+"��ʦ������";
        msg+=tmpCls.getClassgrade()+tmpCls.getClassname()+"��?";
        jsonObj.put("result",1);
        jsonObj.put("msg", "�����ɹ�!");
        jsonObj.put("data",msg);
        response.getWriter().print(jsonObj.toString());
    }


    /**
     * ��¼�༶��̬����ʱ���
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=saveClassTimePoint.do")
    @Transactional
    public void saveClassTimePoint(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JSONObject jsonObj=new JSONObject();
        if(!ImInterFaceUtil.ValidateRequestParam(request)){
            jsonObj.put("msg", "�����쳣!");
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
            jsonObj.put("msg","������֤ʧ��!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        paramMap.remove("sign");
        if(!UrlSigUtil.verifySigSimple("saveClassTimePoint.do", paramMap, sign)){
            jsonObj.put("msg","������֤ʧ��!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());
            return;
        }

        //��ȡ�༶��Ϣ
        ClassInfo classInfo=new ClassInfo();
        classInfo.setClassid(Integer.parseInt(classId));
        classInfo.setDcschoolid(Integer.parseInt(schoolId));
        List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
        if(clsList==null||clsList.size()<1){
            jsonObj.put("msg","��ǰ�༶������!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }

        //��ȡ�û�
        UserInfo user=new UserInfo();
        user.setEttuserid(Integer.parseInt(jid));
        user.setDcschoolid(Integer.parseInt(schoolId));
        List<UserInfo>userList=this.userManager.getList(user,null);
        if(userList==null||userList.size()<1){
            jsonObj.put("msg","��ǰ�û�������!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        MyInfoUserInfo info=new MyInfoUserInfo();
        info.setClassid(Integer.parseInt(classId));
        info.setOperateid(userList.get(0).getRef());
        info.setUserref(userList.get(0).getRef());
        info.setMsgid(-1);
        info.setMsgname("IM֪ͨ");
        info.setMydata(UtilTool.DateConvertToString(new Date(), UtilTool.DateType.type1));
        info.setTemplateid(19);
        if(!this.myInfoUserManager.doSave(info)){
            jsonObj.put("msg","��Ӱ༶���ʶ�̬ʧ��!");
            jsonObj.put("result", 0);
            response.getWriter().print(jsonObj.toString());return;
        }
        jsonObj.put("msg","��Ӱ༶���ʶ�̬�ɹ�!");
        jsonObj.put("result", 1);
        response.getWriter().print(jsonObj.toString());return;

    }
    /**
     * ��֤������Ϣ��
     * @param request
     * @return
     * @throws Exception
     */
    public static String validateRegisterParam(HttpServletRequest request) throws Exception{
        HashMap<String,String> paramMap=getRequestParam(request);
        if(!paramMap.containsKey("userName"))
            return "û�м�⵽�û���!";
        if(!paramMap.containsKey("password"))
            return "û�м�⵽����!";
        ///////////////////��֤�û���
        //λ��6--12�ַ���1��������2���ַ���
        int uNameSize=checkWordSize(paramMap.get("userName").toString().trim());
        //�û�������6���ֻ����12����
//        if(uNameSize<6||uNameSize>12)
//            return "�û�����������6���ֻ����12����!";
//        //�û���ֻ�����֡���ĸ����Сд�����»��ߡ������ַ�!
//        // �����пո�
//        if(!UtilTool.matchingText("[a-zA-Z0-9_\\u4e00-\\u9fa5]+", paramMap.get("userName")))
//            return "�û���ֻ�����֡���ĸ����Сд�����»��ߡ������ַ�!";

        String emailPattern="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        if(!Pattern.matches(emailPattern, paramMap.get("userName").toString().trim()))
            return "�����ʽ����ȷ!";
        //////////////////��֤����
        //λ��6--12�ַ�
        int passSize=paramMap.get("password").trim().length();
        if(passSize<6||passSize>12)
            return "���벻������6���ֻ����12����!";
        //ֻ�����֡���ĸ����Сд�����»��ߣ��ұ���ͬʱ�����ֺ���ĸ
        //if(!UtilTool.matchingText("[a-zA-Z0-9_]+",paramMap.get("password")))
        //            return "����ֻ�����֡���ĸ����Сд�����»��ߣ��ұ���ͬʱ�����ֺ���ĸ!";
        //��֤�Ƿ��������
        if(!UtilTool.matchingText("[\\w[0-9]]+",paramMap.get("password")))
            return "�������ͬʱ�����ֺ���ĸ!";
        //��֤�Ƿ�����ַ�
        if(!UtilTool.matchingText("[\\w[a-zA-Z_]]+",paramMap.get("password")))
            return "�������ͬʱ�����ֺ���ĸ!";
        return "TRUE";
    }

    /**
     * ��֤�ַ�����
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
            //��֯����
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
        } catch (Exception e) {			// �쳣��ʾ
            System.out.println("�쳣����!δ��Ӧ!");
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
                System.out.println("�쳣����!");
                return null;
            }
        }else if(code==HttpURLConnection.HTTP_NOT_FOUND){
            // ��ʾ ����
            System.out.println("�쳣����!404��������ϵ������Ա!");
            return null;
        }else if(code==HttpURLConnection.HTTP_SERVER_ERROR){
            System.out.println("�쳣����!500��������ϵ������Ա!");
            return null;
        }
        String returnContent=null;
        try {
            returnContent=java.net.URLDecoder.decode(stringBuffer.toString(),"UTF-8");  ///aa
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //ת����JSON
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
     * ��֤��У�Ƿ����
     * @param schoolid ��УID
     * @return 0:û��  1����
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
            //��֯����
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
        } catch (Exception e) {			// �쳣��ʾ
            System.out.println("�쳣����!�ӿ����ӣ�"+urlstr+"δ��Ӧ!");
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
                System.out.println("�쳣����!");
                if(httpConnection!=null)httpConnection.disconnect();
                return null;
            }
        }else if(code==404){
            if(httpConnection!=null)httpConnection.disconnect();
            // ��ʾ ����
            System.out.println("�쳣����!404��������ϵ������Ա!");
            return null;
        }else if(code==500){
            if(httpConnection!=null)httpConnection.disconnect();
            System.out.println("�쳣����!500��������ϵ������Ա!���ӣ�"+urlstr+"");
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
        //ת����JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        System.out.println("ImInterface 1.1.3 JSONObject:"+jb.toString());
        return jb;
    }


    /**
     * ����IM�˰༶������
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
     * ���ɹ����༶�ҳ�������
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
 * �����෽��
 */
class  ImInterFaceUtil{
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

    /**
     * ת��usertype
     * @param usertype 1:ѧ��  2����ʦ  3���ҳ�
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
     * ���ݼ��ϵõ���У��ͷ������
     * @param banCuList
     * @param userType 1:ѧ��  2:��ʦ  3:������
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
                    subjName="������";
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
                    (jidstr,dcschoolid.toString(),ettuserid.toString(),userType==1?3:2); //userType==1����ѧ������3 �����ǽ�ʦ
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

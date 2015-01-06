package com.school.control.resource;

import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.entity.resource.*;
import com.school.entity.teachpaltform.TeachingMaterialInfo;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.entity.teachpaltform.TpCourseResource;
import com.school.manager.*;
import com.school.manager.inter.ICommentManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.IGradeManager;
import com.school.manager.inter.ISubjectManager;
import com.school.manager.inter.resource.*;
import com.school.manager.inter.teachpaltform.ITeachingMaterialManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.inter.teachpaltform.ITpCourseResourceManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.notice.NoticeManager;
import com.school.manager.resource.*;
import com.school.manager.teachpaltform.TeachingMaterialManager;
import com.school.manager.teachpaltform.TpCourseManager;
import com.school.manager.teachpaltform.TpCourseResourceManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * �������Դϵͳ����
 * Created by zhengzhou on 14-4-21.
 */
@Controller
@RequestMapping(value = "/simpleRes")
public class SimpleResController extends BaseController<ResourceInfo> {
    @Autowired
    private ISubjectManager subjectManager;
    @Autowired
    private IGradeManager gradeManager;
    @Autowired
    private IDictionaryManager dictionaryManager;
    @Autowired
    private IResourceManager resourceManager;
    @Autowired
    private IStoreManager storeManager;
    @Autowired
    private IOperateRecordManager operateRecordManager;
    @Autowired
    private IResourceReportManager resourceReportManager;
    @Autowired
    private ICommentManager commentManager;
    @Autowired
    private IAccessManager accessManager;
    @Autowired
    private ITpTopicThemeManager tpTopicThemeManager;
    @Autowired
    private ITpCourseResourceManager tpCourseResourceManager;
    @Autowired
    private ITpCourseManager tpCourseManager;
    @Autowired
    private ITeachingMaterialManager teachingMaterialManager;
    /**
     * ���뾫�����Դҳ����ҳ
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toAllResPage", method = RequestMethod.GET)
    public ModelAndView toSubjectPage(HttpServletRequest request,HttpServletResponse response, ModelMap mp)
            throws Exception {
        JsonEntity jeEntity = new JsonEntity();
        String type = request.getParameter("type");

        List<SubjectInfo> subjectEVList = this.subjectManager.getList(new SubjectInfo(), null);
        List<DictionaryInfo> resTypeEVList=this.dictionaryManager.getDictionaryByType("RES_TYPE");

        if(subjectEVList==null || subjectEVList.size()==0
                || resTypeEVList==null || resTypeEVList.size()==0) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("PARAM_ERROR"));// �쳣���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        mp.put("subjectEVList", subjectEVList);
        mp.put("resTypeEVList", resTypeEVList);

        mp.put("type", type);
        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        List<TeachingMaterialInfo> totalTmList=new ArrayList<TeachingMaterialInfo>();
        TeachingMaterialInfo tm=new TeachingMaterialInfo();

        String selVersionId=UtilTool.simpleResUtilProperty.getProperty("SELECT_VERSION_ID");
        if(selVersionId!=null&&selVersionId.length()>0){
           mp.put("selVersionId",selVersionId);
        }

        return new ModelAndView("/resource/simple/allResPage", mp);
    }

    /**
     * ���뾫�����ҳ
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toindex",method=RequestMethod.GET)
    public ModelAndView toIndex(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
            return new ModelAndView("/resource/simple/index",mp);
    }
    /**
     * �����޸���Դ��ҳ�档
     *
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=todetail", method = RequestMethod.GET)
    public ModelAndView toResourseDetail(HttpServletRequest request,
                                         HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity jeEntity = new JsonEntity();
        // �õ���ز���
        ResourceInfo entity=this.getParameter(request,ResourceInfo.class);
        //��֤����
        if(entity.getResid()==null){    //��Դ��������
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
        }
        //�õ������¼
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        entity.setResstatus(-3);//rs_status<>3�������ж�
        List<ResourceInfo> resourceList=this.resourceManager.getList(entity,presult);
        if(resourceList.size()<1){  //��Դ�Ѿ�������
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
        }
        entity=resourceList.get(0); //�õ���Դ�����Ϣ

        //�õ��ļ�������
        String fileType=UtilTool.getResourseType(entity.getFilesuffixname());

        mp.put("fileType",fileType);
        //�õ���Դ����
        List<DictionaryInfo> resTypeDicList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        mp.put("resTypeDicList",resTypeDicList);

        //�õ��ļ�����
        List<DictionaryInfo> fileTypeList=this.dictionaryManager.getDictionaryByType("RES_FILE_TYPE");
        mp.put("fileTypeList",fileTypeList);
        //��ѯ���ڿ�Ŀ
//        SubjectUser sbu=new SubjectUser();
//        sbu.setUserid(this.logined(request).getRef());
//        List<SubjectUser> sbUserList=this.subjectUserManager.getList(sbu,null);
        //�õ��꼶
        List<GradeInfo> grdList=this.gradeManager.getList(null,null);
        mp.put("gradeList",grdList);
        //�õ�ѧ��
        List<SubjectInfo> subList=this.subjectManager.getList(null,null);
        mp.put("subList",subList);


        //----------------------------------------------------
        int ismine = 2; // 1:�Ǳ��� 2:���Ǳ���
        // ����Ǹ���Դ�Ƿ����ڱ���
        if(this.logined(request).getUserid() == entity.getUserid())
            ismine=1;
        request.setAttribute("ismine", ismine);
        //----------------------------------------------------
        int isstore = 2; // 1:���ղ� 2:δ�ղ�
        // ����Ǹ���Դ�Ƿ񱻱����ղ�
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setResid(entity.getResid());
        storeInfo.setUserid(this.logined(request).getUserid());
        List<StoreInfo> storeList = this.storeManager.getList(storeInfo, null);

        if (storeList != null && storeList.size() > 0) {
            isstore = 1;
        }
        request.setAttribute("isstore", isstore);
        //-----------------------------------------------------
        int isrecomend = 2; // 1:���Ƽ� 2:δ�Ƽ�
        // ����Ǹ���Դ�Ƿ񱻱����Ƽ�
        OperateRecord or = new OperateRecord();
        or.setResid(entity.getResid());
        or.setUserid(this.logined(request).getUserid());
        or.setOperatetype(1);
        List<OperateRecord> orList = this.operateRecordManager.getList(or, null);

        if (orList != null && orList.size() > 0) {
            isrecomend = 1;
        }
        request.setAttribute("isrecomend", isrecomend);
        //-----------------------------------------------------
        int ispraise= 2; // 1:�ѵ��� 2:δ����
        // ����Ǹ���Դ�Ƿ񱻱��˵���
        or.setOperatetype(2);
        orList = this.operateRecordManager.getList(or, null);

        if (orList != null && orList.size() > 0) {
            ispraise = 1;
        }
        request.setAttribute("ispraise", ispraise);

        //-----------------------------------------------------
        int isreport= 2; // 1:�Ѿٱ� 2:δ�ٱ�
        // ����Ǹ���Դ�Ƿ񱻱��˾ٱ�
        ResourceReport rr = new ResourceReport();
        rr.setResid(entity.getResid());
        rr.setUserid(this.logined(request).getUserid());
        List<ResourceReport> rrList = this.resourceReportManager.getList(rr, null);

        if (rrList != null && rrList.size() > 0) {
            isreport = 1;
        }
        request.setAttribute("isreport", isreport);

        DictionaryInfo dic = new DictionaryInfo();
        dic.setDictionaryname("��Դ����");
        dic.setDictionarytype(CommentInfo.DICTIONARY_TYPE_OF_COMMENT);

        List<DictionaryInfo> diclist = this.dictionaryManager
                .getList(dic, null);
        if (diclist != null && diclist.size() > 0) {
            request.setAttribute("commenttype", diclist.get(0)
                    .getDictionaryvalue());
            CommentInfo ci = new CommentInfo();
            ci.setCommenttype(Integer.parseInt(diclist.get(0)
                    .getDictionaryvalue()));
            ci.setCommentobjectid(entity.getResid().toString());
            ci.setCommentuserid(this.logined(request).getUserid());
            List<CommentInfo> comlist = this.commentManager.getList(ci, null);
            if (comlist != null && comlist.size() > 0)
                mp.put("comment_state", 1);
        }
        // �����¼��
        AccessInfo ai = new AccessInfo();
        ai.setResid(entity.getResid());
        PageResult pg = new PageResult();
        pg.setPageSize(10);
        List<AccessInfo> accesslist = this.accessManager.getList(ai, pg);
        mp.put("accesslist", accesslist);
        ai.setRef(this.accessManager.getNextId());
        ai.setUserid(this.logined(request).getUserid());
        this.accessManager.doSaveOrUpdate(ai);

        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        List<String> sqlArrayList=new ArrayList<String>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.resourceManager.getUpdateNumAdd("rs_resource_info", "res_id", "CLICKS", entity.getResid().toString(), 1, sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //����
        if(sqlArrayList.size()!=0&&sqlArrayList.size()== objArrayList.size()){
            if(!this.tpTopicThemeManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
            }
        }
        entity.setClicks((entity.getClicks()==null?0:entity.getClicks())+1);
        String courseid=request.getParameter("courseid");
        if((courseid==null||courseid.trim().length()<1)&&(entity.getGrade()==null||entity.getSubject()==null)){
            //��ѯ��Ӧ��¼
            TpCourseResource cr=new TpCourseResource();
            cr.setResid(entity.getResid());
            PageResult npresult=new PageResult();
            npresult.setPageNo(1);
            npresult.setPageSize(1);
            List<TpCourseResource> tpCr=this.tpCourseResourceManager.getList(cr,npresult);
            if(tpCr!=null&&tpCr.size()>0){
                courseid=tpCr.get(0).getCourseid().toString();
            }
        }
        if(courseid!=null&&courseid.trim().length()>0){
            TpCourseInfo tc = new TpCourseInfo();
            tc.setCourseid(Long.parseLong(courseid));
            List tcList = this.tpCourseManager.getList(tc,null);
            if(tcList!=null && tcList.size()>0){
//
                tc = (TpCourseInfo)tcList.get(0);
                mp.put("tc",tc);
                if(tc.getMaterialids()!=null&&tc.getMaterialids().length()>0){
                    String maid=tc.getMaterialids();
                    if(maid.indexOf(",")!=-1)
                        maid=maid.split(",")[0];
                    //��ѯ�̲���Ϣ
                    TeachingMaterialInfo tm=new TeachingMaterialInfo();
                    tm.setMaterialid(Integer.parseInt(maid.trim()));
                    List<TeachingMaterialInfo> tmList=this.teachingMaterialManager.getList(tm,null);
                    if(tmList!=null&&tmList.size()>0&&tmList.get(0)!=null){
                        entity.setGrade(tmList.get(0).getGradeid());
                        entity.setGradename(tmList.get(0).getGradename());
                        entity.setSubjectname(tmList.get(0).getSubjectname());
                        entity.setSubject(tmList.get(0).getSubjectid());
                    }
                }
            }
        }
        //�洢��ʾ ����Դ��Ϣ
        mp.put("resObj", entity);
        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));

        String selVersionId=UtilTool.simpleResUtilProperty.getProperty("SELECT_VERSION_ID");
        if(selVersionId!=null&&selVersionId.length()>0){
            mp.put("selVersionId",selVersionId);
        }
        return new ModelAndView("/resource/simple/detail", mp);
    }
    @RequestMapping(params = "m=resList", method = RequestMethod.GET)
    public ModelAndView toResourseList(HttpServletRequest request, ModelMap mp)
            throws Exception {
        boolean adminFlag = false;
        if(this.logined(request)!=null && this.logined(request).getCjJRoleUsers()!=null)
            for(RoleUser ru : (List<RoleUser>)this.logined(request).getCjJRoleUsers()){
                if(ru.getRoleid()==4)
                    adminFlag=true;
            }
        mp.put("adminFlag", adminFlag);
        String resname = request.getParameter("srhValue");
        if (resname != null) {
            resname = URLDecoder.decode(request.getParameter("srhValue"),
                    "utf-8");
            mp.put("srhValue", resname);
        } else
            mp.put("srhValue", "");

        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));

        //�õ���ǰ��ʦ������ѧ�ƣ����û����õ���ǰһ��
        //�õ��ý�ʦ������ѧ��
//        SubjectUser sbu=new SubjectUser();
//        // sbu.setIsmajor(1);
//        sbu.setUserid(this.logined(request).getRef());
//        List<SubjectUser> sbList=this.subjectUserManager.getList(sbu,null);
//        if(sbList!=null&&sbList.size()>0){
//            mp.put("subList",sbList);
//            if(sbList.size()>1){
//                for (SubjectUser sbUser:sbList){
//                    if(sbUser!=null&&sbUser.getIsmajor()==1){
//                        mp.put("subjectid",sbUser.getSubjectid());
//                        break;
//                    }
//                }
//            }else
//                mp.put("subjectid",sbList.get(0).getSubjectid());
//        }else{
            List<SubjectInfo> sb1List=this.subjectManager.getList(null,null);
            mp.put("subList",sb1List);
            mp.put("subjectid",(sb1List==null||sb1List.size()<1)?0:sb1List.get(0).getSubjectid());
//        }

        String selVersionId=UtilTool.simpleResUtilProperty.getProperty("SELECT_VERSION_ID");
        if(selVersionId!=null&&selVersionId.length()>0){
            mp.put("selVersionId",selVersionId);
        }
        return new ModelAndView("/resource/simple/resList",mp);
    }
    @RequestMapping(params = "m=list", method = RequestMethod.GET)
    public ModelAndView toList(HttpServletRequest request, ModelMap mp)
            throws Exception {
        boolean adminFlag = false;
        if(this.logined(request)!=null && this.logined(request).getCjJRoleUsers()!=null)
            for(RoleUser ru : (List<RoleUser>)this.logined(request).getCjJRoleUsers()){
                if(ru.getRoleid()==4)
                    adminFlag=true;
            }
        mp.put("adminFlag", adminFlag);
        String resname = request.getParameter("srhValue");
        if (resname != null) {
            resname = URLDecoder.decode(request.getParameter("srhValue"),
                    "utf-8");
            mp.put("srhValue", resname);
        } else
            mp.put("srhValue", "");

        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));


        //�õ��ý�ʦ������ѧ��

        List<SubjectInfo> sb1List=this.subjectManager.getList(null,null);
        mp.put("subjectid",(sb1List==null||sb1List.size()<1)?0:sb1List.get(0).getSubjectid());


        String selVersionId=UtilTool.simpleResUtilProperty.getProperty("SELECT_VERSION_ID");
        if(selVersionId!=null&&selVersionId.length()>0){
            mp.put("selVersionId",selVersionId);
        }

        return new ModelAndView("/resource/simple/list",mp);
    }
    @RequestMapping(params = "m=courseList", method = RequestMethod.GET)
    public ModelAndView toCourseList(HttpServletRequest request, ModelMap mp)
            throws Exception {
        boolean adminFlag = false;
        if(this.logined(request)!=null && this.logined(request).getCjJRoleUsers()!=null)
            for(RoleUser ru : (List<RoleUser>)this.logined(request).getCjJRoleUsers()){
                if(ru.getRoleid()==4)
                    adminFlag=true;
            }
        List<GradeInfo> gradeList = this.gradeManager.getList(null,null);

        request.setAttribute("adminFlag", adminFlag);
        String resname = request.getParameter("srhValue");
        if (resname != null) {
            resname = URLDecoder.decode(request.getParameter("srhValue"),
                    "utf-8");
            request.setAttribute("srhValue", resname);
        } else
            request.setAttribute("srhValue", "");

        mp.put("gradeList", gradeList);
        //�õ��ý�ʦ��ѧ����Ϣ
//        SubjectUser sbu=new SubjectUser();
//        sbu.setUserid(this.logined(request).getRef());
//        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
//        if(sbuList==null||sbuList.size()<1){
            List<SubjectInfo> sbList=this.subjectManager.getList(null,null);
            mp.put("subList",sbList);
//        }else
//            mp.put("subList",sbuList);
        String selVersionId=UtilTool.simpleResUtilProperty.getProperty("SELECT_VERSION_ID");
        if(selVersionId!=null&&selVersionId.length()>0){
            mp.put("selVersionId",selVersionId);
        }
        return new ModelAndView("/resource/simple/courseList");
    }
    @RequestMapping(params = "m=courseResList", method = RequestMethod.GET)
    public ModelAndView toCourseResList(HttpServletRequest request,HttpServletResponse response, ModelMap mp)
            throws Exception {
        JsonEntity jeEntity = new JsonEntity();
        String courseid = request.getParameter("courseid");
        if(courseid==null && courseid.trim().length()==0) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("PARAM_ERROR"));// �쳣���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        TpCourseInfo tc = new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List tcList = this.tpCourseManager.getList(tc,null);
        if(tcList==null || tcList.size()==0){
            jeEntity.setMsg("�Ҳ���ר�����ݣ�");// �쳣���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        tc = (TpCourseInfo)tcList.get(0);
        mp.put("tc",tc);
        if(tc.getMaterialids()!=null&&tc.getMaterialids().length()>0){
            String maid=tc.getMaterialids();
            if(maid.indexOf(",")!=-1)
                maid=maid.split(",")[0];
            //��ѯ�̲���Ϣ
            TeachingMaterialInfo tm=new TeachingMaterialInfo();
            tm.setMaterialid(Integer.parseInt(maid.trim()));
            List<TeachingMaterialInfo> tmList=this.teachingMaterialManager.getList(tm,null);
            if(tmList!=null&&tmList.size()>0&&tmList.get(0)!=null){
                mp.put("gradename",tmList.get(0).getGradename());
                mp.put("subjectname",tmList.get(0).getSubjectname());
            }
        }
        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));

        String selVersionId=UtilTool.simpleResUtilProperty.getProperty("SELECT_VERSION_ID");
        if(selVersionId!=null&&selVersionId.length()>0){
            mp.put("selVersionId",selVersionId);
        }
        return new ModelAndView("/resource/simple/courseResList",mp);
    }
}

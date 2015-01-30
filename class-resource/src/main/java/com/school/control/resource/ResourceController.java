package com.school.control.resource;

import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.entity.notice.NoticeInfo;
import com.school.entity.resource.*;
import com.school.entity.resource.score.SchoolScoreRank;
import com.school.entity.resource.score.UserModelTotalScore;
import com.school.entity.resource.score.UserScoreRank;
import com.school.entity.teachpaltform.TeachingMaterialInfo;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.entity.teachpaltform.TpCourseResource;
import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.manager.inter.notice.INoticeManager;
import com.school.manager.inter.resource.*;
import com.school.manager.inter.resource.score.ISchoolScoreRankManager;
import com.school.manager.inter.resource.score.IUserModelTotalScoreManager;
import com.school.manager.inter.resource.score.IUserScoreRankManager;
import com.school.manager.inter.teachpaltform.ITeachingMaterialManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.notice.NoticeManager;
import com.school.manager.resource.*;
import com.school.manager.resource.score.SchoolScoreRankManager;
import com.school.manager.resource.score.UserModelTotalScoreManager;
import com.school.manager.resource.score.UserScoreRankManager;
import com.school.manager.teachpaltform.TeachingMaterialManager;
import com.school.manager.teachpaltform.TpCourseManager;
import com.school.manager.teachpaltform.TpCourseResourceManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends BaseController<ResourceInfo> {
    @Autowired
    private IResourceFileManager resourceFileManager;
    @Autowired
    private IExtendManager extendManager;
    @Autowired
    private IExtendResourceManager extendResourceManager;
    @Autowired
    private IResourceRightManager resourceRightManager;
    @Autowired
    private TpCourseResourceManager tpCourseResourceManager;
    @Autowired
    private ICommentManager commentManager;
    @Autowired
    private IAccessManager accessManager;
    @Autowired
    private ITpTopicThemeManager tpTopicThemeManager;
    @Autowired
    private IOperateRecordManager operateRecordManager;
    @Autowired
    private IResourceReportManager resourceReportManager;
    @Autowired
    private IStoreManager storeManager;
    @Autowired
    private ISubjectUserManager subjectUserManager;
    @Autowired
    private ISubjectManager subjectManager;
    @Autowired
    private IGradeManager gradeManager;
    @Autowired
    private IResourceManager resourceManager;
    @Autowired
    private ITpCourseManager tpCourseManager;
    @Autowired
    private ITeachingMaterialManager teachingMaterialManager;
    @Autowired
    private IDictionaryManager dictionaryManager;
    @Autowired
    private IRoleManager roleManager;
    @Autowired
    private INoticeManager noticeManager;
    @Autowired
    private IGuideManager guideManager;
    @Autowired
    private IClassUserManager classUserManager;
    @Autowired
    private ISchoolManager schoolManager;
    @Autowired
    private IDeptManager deptManager;
    @Autowired
    private IUserModelTotalScoreManager userModelTotalScoreManager;
    @Autowired
    private IUserScoreRankManager userScoreRankManager;
    @Autowired
    private ISchoolScoreRankManager schoolScoreRankManager;



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
        SubjectUser sbu=new SubjectUser();
        // sbu.setIsmajor(1);
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbList=this.subjectUserManager.getList(sbu,null);
        if(sbList!=null&&sbList.size()>0){
            if(sbList.size()>1){
                for (SubjectUser sbUser:sbList){
                    if(sbUser!=null&&sbUser.getIsmajor()==1){
                        mp.put("subjectid",sbUser.getSubjectid());
                        break;
                    }
                }
            }else
                mp.put("subjectid",sbList.get(0).getSubjectid());
        }else{
            List<SubjectInfo> sb1List=this.subjectManager.getList(null,null);
            mp.put("subjectid",(sb1List==null||sb1List.size()<1)?0:sb1List.get(0).getSubjectid());
        }

        return new ModelAndView("/resource/base/list",mp);
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
        SubjectUser sbu=new SubjectUser();
        // sbu.setIsmajor(1);
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbList=this.subjectUserManager.getList(sbu,null);
        if(sbList!=null&&sbList.size()>0){
            mp.put("subList",sbList);
            if(sbList.size()>1){
                for (SubjectUser sbUser:sbList){
                    if(sbUser!=null&&sbUser.getIsmajor()==1){
                        mp.put("subjectid",sbUser.getSubjectid());
                        break;
                    }
                }
            }else
                mp.put("subjectid",sbList.get(0).getSubjectid());
        }else{
            List<SubjectInfo> sb1List=this.subjectManager.getList(null,null);
            mp.put("subList",sb1List);
            mp.put("subjectid",(sb1List==null||sb1List.size()<1)?0:sb1List.get(0).getSubjectid());
        }
        return new ModelAndView("/resource/base/resList",mp);
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
        SubjectUser sbu=new SubjectUser();
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
        if(sbuList==null||sbuList.size()<1){
            List<SubjectInfo> sbList=this.subjectManager.getList(null,null);
            mp.put("subList",sbList);
        }else
            mp.put("subList",sbuList);
        return new ModelAndView("/resource/base/courseList");
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
        return new ModelAndView("/resource/base/courseResList",mp);
    }

    @RequestMapping(params = "m=toindex", method = RequestMethod.GET)
    public ModelAndView toPageIndex(HttpServletRequest request,HttpServletResponse response, ModelMap mp)
            throws Exception {
        List<SubjectInfo> subjectEVList = this.subjectManager.getList(new SubjectInfo(), null);
        List<GradeInfo> gradeEVList = this.gradeManager.getList(new GradeInfo(), null);
        List<DictionaryInfo> resTypeEVList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        List<DictionaryInfo> resFileTypeEVList=this.dictionaryManager.getDictionaryByType("RES_FILE_TYPE");

        JsonEntity jeEntity = new JsonEntity();

        if(subjectEVList==null || subjectEVList.size()==0
                || gradeEVList==null || gradeEVList.size()==0
                || resTypeEVList==null || resTypeEVList.size()==0
                || resFileTypeEVList==null || resFileTypeEVList.size()==0) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("PARAM_ERROR"));// �쳣���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        mp.put("subjectEVList", subjectEVList);
        mp.put("gradeEVList", gradeEVList);
        mp.put("resTypeEVList", resTypeEVList);
        mp.put("resFileTypeEVList", resFileTypeEVList);

        // ��ȡ��Դ������
        PageResult pr = new PageResult();
        ResourceInfo ri = new ResourceInfo();
        ri.setResstatus(1);
        this.resourceManager.getList(ri,pr);
        mp.put("resTotalNum", pr.getRecTotal());

        // ��ȡ������Դ��������
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        ri.setCtime((Date) currentDate.getTime().clone());
        pr = new PageResult();
        this.resourceManager.getList(ri, pr);
        mp.put("dayResNum", pr.getRecTotal());

        // ��ȡ������Դ��������
        currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ri.setCtime((Date) currentDate.getTime().clone());
        pr = new PageResult();
        this.resourceManager.getList(ri, pr);
        mp.put("weekResNum", pr.getRecTotal());

        // ��ȡ������Դ��������
        currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -15);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        ri.setCtime((Date) currentDate.getTime().clone());
        pr = new PageResult();
        this.resourceManager.getList(ri, pr);
        mp.put("monthResNum", pr.getRecTotal());
        //�õ���Դ����
        NoticeInfo notic=new NoticeInfo();
        notic.setCuserid(this.logined(request).getRef());
        notic.setNoticetype("3");  //֪ͨ����
        notic.setDcschoolid(this.logined(request).getDcschoolid());
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(5);
        presult.setOrderBy("n.IS_TOP ASC,C_TIME DESC");
        List<NoticeInfo> noticeList=this.noticeManager.getUserList(notic, presult);
        mp.put("resourceNotice", noticeList);
        //��ɫ
        List<RoleInfo> roleList = this.roleManager.getList(null, null);
        mp.put("role", roleList);
        //�꼶
        List gradeList = this.gradeManager.getList(null, null);
        mp.put("gradeList", gradeList);

        //��ʦѧ��
        int def_sub=subjectEVList.get(0).getSubjectid();
        int subvalue=0;
        SubjectUser su = new SubjectUser();
        su.setUserid(this.logined(request).getRef());
        List<SubjectUser> subjectList = this.subjectUserManager.getList(su, null);
        if(subjectList!=null)
            for(SubjectUser tmp_su:subjectList){
                subvalue=tmp_su.getSubjectid();
                if(tmp_su.getIsmajor()==1)
                    break;
            }
        if(subvalue>0)
            for(SubjectInfo tmp_ev:subjectEVList){
                if(tmp_ev.getSubjectid().equals(subvalue)){
                    def_sub=tmp_ev.getSubjectid();
                    break;
                }
            }

        mp.put("def_sub", def_sub);
        mp.put("isadminFlag", this.validateRole(request, UtilTool._ROLE_ADMIN_ID) ? 0 : 1);
        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        PageResult myResRkPage=new PageResult();
        myResRkPage.setPageNo(1);
        myResRkPage.setPageSize(7);
        //�õ����а�  1���ҵ���Դ���а�
        ResourceInfo rankRes=new ResourceInfo();
        rankRes.setUserid(this.logined(request).getUserid());
        List<ResourceInfo> resRankList=this.resourceManager.getMyResStaticesRank(rankRes,myResRkPage);
        mp.put("myResRank",resRankList);
        //�õ�����У�ڹ������а�
        UserModelTotalScoreInfo userModelTotalScoreInfo=new UserModelTotalScoreInfo();
        userModelTotalScoreInfo.setModelId(3);//��Դϵͳ
        myResRkPage.setOrderBy("total_score desc");
        List<UserModelTotalScoreInfo> umtsList=this.userModelTotalScoreManager.getList(userModelTotalScoreInfo,myResRkPage);
        mp.put("umtsList",umtsList);
        //�õ��ƶ˹������а�
        UserScoreRank usRank=new UserScoreRank();
        usRank.setOperatetype(1);
        usRank.setModelid(3L);//3����Դϵͳ
        myResRkPage.setOrderBy("SCORE desc");
        List<UserScoreRank> usRankList=userScoreRankManager.getList(usRank,myResRkPage);
        mp.put("usRankList",usRankList);
        //�õ���У���а�
        SchoolScoreRank ssRank=new SchoolScoreRank();
        ssRank.setModelid(3L);//3����Դϵͳ
        ssRank.setOperatetype(1);
        myResRkPage.setOrderBy("score desc");
        List<SchoolScoreRank> ssRankList=schoolScoreRankManager.getList(ssRank,myResRkPage);
        mp.put("ssRankList",ssRankList);
        //�õ��ܻ㱨
        Integer loginGrdid=null;

        //�õ���ǰ��ʦ���ڵ����ѧ�ƣ�����꼶
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,null);
        if(mapList==null||mapList.size()<1){
            //�õ����ѧУ������꼶
            loginGrdid=this.gradeManager.getList(null, null).get(0).getGradeid();
        }else
            loginGrdid=Integer.parseInt(mapList.get(0).get("GRADE_ID").toString());

        ResourceInfo rs=new ResourceInfo();
        rs.setSubjectvalues(subvalue+"");
        rs.setGradevalues(loginGrdid + "");
        rs.setType("1");  //type:������
        List<Map<String,Object>> weekRsList=this.resourceManager.getResourceIdxViewRank(rs,myResRkPage.getPageSize());
        mp.put("weekRsList",weekRsList);


        // ��ȡ�����������ߵ���Դ
        rs.setType("2");  //type:������
        List<Map<String,Object>> monthRsList=this.resourceManager.getResourceIdxViewRank(rs, myResRkPage.getPageSize());
        mp.put("monthRsList",monthRsList);

        //�õ���������
        rs.setCtime(null);
        List<Map<String,Object>> downResRankList=this.resourceManager.getResourceIdxDownRank(rs, myResRkPage.getPageSize());
        mp.put("downRsList",downResRankList);

        //��ѯ�Ƿ��һ�ν�����Դϵͳ
        GuideInfo selGuide=new GuideInfo();
        selGuide.setOpuser(this.logined(request).getUserid());//�û�
        selGuide.setOptype(1);                                  //����  1:���ν���
        selGuide.setOptable("rs_resource_info");                //���ٱ���
        PageResult guidePresult=new PageResult();
        guidePresult.setPageSize(1);
        guidePresult.setPageNo(1);
        List<GuideInfo> guideList=this.guideManager.getList(selGuide,guidePresult);
        if(guideList==null||guideList.size()<1){        //���û��ǳ��ν���
            mp.put("isFirstInto",1);
            //��Ӽ�¼
            if(!this.guideManager.doSave(selGuide)){
                jeEntity.setMsg(UtilTool.msgproperty
                        .getProperty("OPERATE_ERROR"));// ����ʧ�ܣ�ԭ��δ֪��\n\n��ʾ�������ʾ�������֣���������ϵ����Ա��
                response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
                return null;
            }
        }else
            mp.put("isFirstInto",2);//�������һ�ν���
        return new ModelAndView("/resource/pageIndex", mp);
    }

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
        //�õ��ý�ʦ������ѧ��
        SubjectUser sbu=new SubjectUser();
        sbu.setIsmajor(1);
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbList=this.subjectUserManager.getList(sbu,null);
        if(sbList!=null&&sbList.size()>0)
            mp.put("subjectid",sbList.get(0).getSubjectid());
        mp.put("subjectEVList", subjectEVList);
        mp.put("resTypeEVList", resTypeEVList);

        mp.put("type", type);
        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));

        //�õ���ǰ��ʦ���ڵ����ѧ�ƣ�����꼶
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,null);
        /*
         ��Ʒ_��Դϵͳ
         PZYXT-68

         ��Դϵͳ������ʦ�ϴ���Դ��Ĭ��ѧ������

          �� ���� ����˱�ע - 08/����/14 1:48 ����
             ��Դϵͳ�ϴ���Դʱ�޷�Ϊ��Դ���ѧ�ơ��꼶���ݶ��������£�
             ��1��ѧ�ƣ��Խ�ʦ������ѧ��Ϊ��
             ��2���꼶����ʦ���ڰ༶������꼶
         *
         * ѧ��
         * �жϸ���ʦ���ڵ�ѧ�ƣ����û�����ã�����ȡ��ѧУ��ĳ��ѧ��
         */

        Integer loginSuid=null,loginGrdid=null;
        sbu=new SubjectUser();
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
        if(sbuList==null||sbuList.size()<1){
            if(mapList==null||mapList.size()<1){
                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
                if(subList!=null&&subList.size()>0){
                    loginSuid=subList.get(0).getSubjectid();

                }

            }else  //���û�����ڣ�����ѧ�ƣ����հ༶����
                mp.put("currentloginsubid", mapList.get(0).get("SUBJECT_ID"));
        }else{
            for(SubjectUser sb:sbuList){
                if(sb!=null){
                    if(sb.getIsmajor()==1){
                        loginSuid=sb.getSubjectid();break;
                    }
                }
            }
            if(loginSuid==null)
                loginSuid=sbuList.get(0).getSubjectid();
        }

        if(mapList==null||mapList.size()<1){
            //�õ����ѧУ������꼶
            loginGrdid=this.gradeManager.getList(null, null).get(0).getGradeid();
        }else
            loginGrdid=Integer.parseInt(mapList.get(0).get("GRADE_ID").toString());
        mp.put("currentloginSub",(loginSuid==null?0:loginSuid));
        mp.put("currentloginGrd",(loginGrdid==null?0:loginGrdid));
        return new ModelAndView("/resource/allResPage", mp);
    }

    @RequestMapping(params = "m=toMyResList", method = RequestMethod.GET)
    public ModelAndView toMyResourseList(HttpServletRequest request,HttpServletResponse response, ModelMap mp)
            throws Exception {
        Integer userid = this.logined(request).getUserid();
        List<SubjectInfo> subjectEVList = this.subjectManager.getList(new SubjectInfo(), null);
        List<GradeInfo> gradeEVList = this.gradeManager.getList(new GradeInfo(), null);
        List<DictionaryInfo> resTypeEVList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        List<DictionaryInfo> resFileTypeEVList=this.dictionaryManager.getDictionaryByType("RES_FILE_TYPE");

        JsonEntity jeEntity = new JsonEntity();

        if(subjectEVList==null || subjectEVList.size()==0
                || gradeEVList==null || gradeEVList.size()==0
                || resTypeEVList==null || resTypeEVList.size()==0
                || resFileTypeEVList==null || resFileTypeEVList.size()==0) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("PARAM_ERROR"));// �쳣���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("subjectEVList", subjectEVList);
        mp.put("gradeEVList", gradeEVList);
        mp.put("resTypeEVList", resTypeEVList);
        mp.put("resFileTypeEVList", resFileTypeEVList);
        mp.put("userid", userid);
        mp.put("isadmin", this.validateRole(request, UtilTool._ROLE_ADMIN_ID)?1:2);
        return new ModelAndView("/resource/base/myResList", mp);
    }

    @RequestMapping(params = "m=toStoreList", method = RequestMethod.GET)
    public ModelAndView toStoreList(HttpServletRequest request,HttpServletResponse response, ModelMap mp)
            throws Exception {
        Integer userid = this.logined(request).getUserid();
        List<SubjectInfo> subjectEVList = this.subjectManager.getList(new SubjectInfo(), null);
        List<GradeInfo> gradeEVList = this.gradeManager.getList(new GradeInfo(), null);
        List<DictionaryInfo> resTypeEVList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        List<DictionaryInfo> resFileTypeEVList=this.dictionaryManager.getDictionaryByType("RES_FILE_TYPE");

        JsonEntity jeEntity = new JsonEntity();

        if(subjectEVList==null || subjectEVList.size()==0
                || gradeEVList==null || gradeEVList.size()==0
                || resTypeEVList==null || resTypeEVList.size()==0
                || resFileTypeEVList==null || resFileTypeEVList.size()==0) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("PARAM_ERROR"));// �쳣���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        //�õ�ѧУ
        List<SchoolInfo> schoolList=this.schoolManager.getList(null,null);
        mp.put("scList",schoolList);
        mp.put("subjectEVList", subjectEVList);
        mp.put("gradeEVList", gradeEVList);
        mp.put("resTypeEVList", resTypeEVList);
        mp.put("resFileTypeEVList", resFileTypeEVList);
        mp.put("userid", userid);
        mp.put("isadmin", this.validateRole(request, UtilTool._ROLE_ADMIN_ID)?1:2);

        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        return new ModelAndView("/resource/base/storeList", mp);
    }

    @RequestMapping(params = "m=toCheckList", method = RequestMethod.GET)
    public ModelAndView toCheckList(HttpServletRequest request,HttpServletResponse response, ModelMap mp)
            throws Exception {
        Integer userid = this.logined(request).getUserid();
        List<SubjectInfo> subjectEVList = this.subjectManager.getList(new SubjectInfo(), null);
        List<GradeInfo> gradeEVList = this.gradeManager.getList(new GradeInfo(), null);
        List<DictionaryInfo> resTypeEVList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        List<DictionaryInfo> resFileTypeEVList=this.dictionaryManager.getDictionaryByType("RES_FILE_TYPE");

        JsonEntity jeEntity = new JsonEntity();

        if(subjectEVList==null || subjectEVList.size()==0
                || gradeEVList==null || gradeEVList.size()==0
                || resTypeEVList==null || resTypeEVList.size()==0
                || resFileTypeEVList==null || resFileTypeEVList.size()==0) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("PARAM_ERROR"));// ���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("subjectEVList", subjectEVList);
        mp.put("gradeEVList", gradeEVList);
        mp.put("resTypeEVList", resTypeEVList);
        mp.put("resFileTypeEVList", resFileTypeEVList);
        mp.put("userid", userid);

        //�õ���ǰ��ʦ���ڵ����ѧ�ƣ�����꼶
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,null);
        /*
         ��Ʒ_��Դϵͳ
         PZYXT-68

         ��Դϵͳ������ʦ�ϴ���Դ��Ĭ��ѧ������

          �� ���� ����˱�ע - 08/����/14 1:48 ����
             ��Դϵͳ�ϴ���Դʱ�޷�Ϊ��Դ���ѧ�ơ��꼶���ݶ��������£�
             ��1��ѧ�ƣ��Խ�ʦ������ѧ��Ϊ��courseList
             ��2���꼶����ʦ���ڰ༶������꼶
         *
         * ѧ��
         * �жϸ���ʦ���ڵ�ѧ�ƣ����û�����ã�����ȡ��ѧУ��ĳ��ѧ��
         */
        SubjectUser sbu=new SubjectUser();
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
        if(sbuList==null||sbuList.size()<1){
            if(mapList==null||mapList.size()<1){
                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
                if(subList!=null&&subList.size()>0)
                    mp.put("subjectid", subList.get(0).getSubjectid());
            }else  //���û�����ڣ�����ѧ�ƣ����հ༶����
                mp.put("subjectid", Integer.parseInt(mapList.get(0).get("SUBJECT_ID").toString()));
        }else{
            for(SubjectUser sb:sbuList){
                if(sb!=null){
                    if(sb.getIsmajor()==1){
                        mp.put("subjectid", sb.getSubjectid());break;
                    }
                }
            }
            if(mp.get("subjectid")==null)
                mp.put("subjectid", sbuList.get(0).getSubjectid());
        }
        /**
         * �꼶
         */
        if(mapList==null||mapList.size()<1){
            //�õ����ѧУ������꼶
            mp.put("grdeid", this.gradeManager.getList(null, null).get(0).getGradeid());
        }else
            mp.put("gradeid", mapList.get(0).get("GRADE_ID").toString());



        return new ModelAndView("/resource/base/checkList", mp);
    }

    /**
     * �����ʦ������ҳ�档
     *
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toteacherreslist", method = RequestMethod.GET)
    public ModelAndView toTeacherResourseList(HttpServletRequest request,
                                              ModelMap mp) throws Exception {

        boolean adminFlag = false;
        if(this.logined(request)!=null && this.logined(request).getCjJRoleUsers()!=null)
            for(RoleUser ru : (List<RoleUser>)this.logined(request).getCjJRoleUsers()){
                if(ru.getRoleid()==4){
                    adminFlag=true;
                    break;
                }
            }
        mp.put("adminFlag", adminFlag);
        String tchname = request.getParameter("srhValue");
        String userid = request.getParameter("userid");
        if(userid != null){
            if(!userid.trim().equals("0")){
//            UserInfo user = new UserInfo();
//            user.setUserid(Integer.parseInt(userid));
//            user = this.userManager.getUserInfo(user);
//            if(user != null)
                mp.put("userid", userid);
            }
            else{
                tchname="����������У";
            }
        }
        if (tchname != null) {
            if(userid==null||userid!=null&&!userid.trim().equals("0")){
                tchname = URLDecoder.decode(request.getParameter("srhValue"),
                        "utf-8");
            }
            mp.put("srhValue", tchname);
        } else
            mp.put("srhValue", "");
        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        return new ModelAndView("/resource/base/teacherResList", mp);
    }

    /**
     * �����ϴ��û����а�
     *
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=topublicusertop", method = RequestMethod.GET)
    public ModelAndView toPublicUserTopPage(HttpServletRequest request,
                                            ModelMap mp) throws Exception {
        boolean adminFlag = false;
        if(this.logined(request)!=null && this.logined(request).getCjJRoleUsers()!=null)
            for(RoleUser ru : (List<RoleUser>)this.logined(request).getCjJRoleUsers()){
                if(ru.getRoleid()==4)
                    adminFlag=true;
            }
        request.setAttribute("adminFlag", adminFlag);
        return new ModelAndView("/resource/base/publicUserTop", mp);
    }

    /**
     * ������Դ���а�
     *
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toresourcetop", method = RequestMethod.GET)
    public ModelAndView toResourceTopPage(HttpServletRequest request,
                                          ModelMap mp) throws Exception {
        boolean adminFlag = false;
        if(this.logined(request)!=null && this.logined(request).getCjJRoleUsers()!=null)
            for(RoleUser ru : (List<RoleUser>)this.logined(request).getCjJRoleUsers()){
                if(ru.getRoleid()==4)
                    adminFlag=true;
            }
        request.setAttribute("adminFlag", adminFlag);
        String topType = request.getParameter("topType");
        String timeType = request.getParameter("timeType");
        mp.put("topType", topType);
        mp.put("timeType", timeType);

        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        return new ModelAndView("/resource/base/resourceTop", mp);
    }

    /**
     * �����޸���Դ��ҳ�档
     *
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toupdate", method = RequestMethod.GET)
    public ModelAndView toUpdateResourse(HttpServletRequest request,
                                         HttpServletResponse response, ModelMap mp) throws Exception {
        // �õ�RES_ID
        JsonEntity jeEntity = new JsonEntity();
        String resid = request.getParameter("resid");
        if (resid == null||resid.trim().length()<1) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_RESID"));// �쳣���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        ResourceInfo restmp = new ResourceInfo();
        restmp.setResid(Long.parseLong(resid));
        List<ResourceInfo> resList = this.resourceManager.getList(restmp, null);
        if (resList == null || resList.size() < 1) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_DATE"));// �쳣����ϵͳδ������Ҫ��ѯ����Ϣ�������Ѳ�����!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        mp.put("resinfo", resList.get(0));
        // �õ���Ӧ����չ����
        restmp = resList.get(0);
        if (restmp == null) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_DATE"));// �쳣����ϵͳδ������Ҫ��ѯ����Ϣ�������Ѳ�����!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        // �õ��ļ�
        ResourceFileInfo resourceFileInfo = new ResourceFileInfo();
        resourceFileInfo.setResid(Long.parseLong(resid));
        List<ResourceFileInfo> reFileInfoList = this.resourceFileManager
                .getList(resourceFileInfo, null);
        if (reFileInfoList != null && reFileInfoList.size() > 0) {
            request.setAttribute("reFileInfoList", reFileInfoList);
        }
        ExtendResource eResource = new ExtendResource();
        eResource.setResid(restmp.getResid());
        List<ExtendResource> eResourceList = extendResourceManager.getList(
                eResource, null);
        mp.put("erList", eResourceList);
        // �õ�����������
        ExtendInfo extendInfo = new ExtendInfo();
        extendInfo.setIsquery(1);
        extendInfo.setDependextendid("0");
        PageResult presult = new PageResult();
        presult.setPageSize(300);
        List<ExtendInfo> extendList = this.extendManager.getList(extendInfo,
                presult);
        if (extendList != null && extendList.size() > 0) {
            mp.put("extendcount", extendList.size());
        }
        // �õ����Ȩ������
        DictionaryInfo dicInfo = new DictionaryInfo();
        dicInfo.setDictionarytype("RS_RESOURCE_VIEW_RIGHT_TYPE");
        List<DictionaryInfo> dicList = this.dictionaryManager.getList(dicInfo,
                null);
        if (dicList != null && dicList.size() > 0)
            mp.put("resourceViewRightType", dicList);
        // �õ�����Ȩ������
        dicInfo = new DictionaryInfo();
        dicInfo.setDictionarytype("RS_RESOURCE_DOWN_RIGHT_TYPE");
        dicList = this.dictionaryManager.getList(dicInfo, null);
        if (dicList != null && dicList.size() > 0)
            mp.put("resourceDownRightType", dicList);
        // �õ�ѧ��
        SubjectUser sbu = new SubjectUser();
        sbu.getUserinfo().setRef(this.logined(request).getRef());
        List<SubjectUser> sbUserList = this.subjectUserManager.getList(sbu,
                null);
        if (sbUserList != null && sbUserList.size() > 0)
            mp.put("sublist", sbUserList);
        // �õ�����
        PageResult p = new PageResult();
        p.setOrderBy("d.dept_id");
        p.setPageNo(0);
        p.setPageSize(0);
        List<DeptInfo> deptList = this.deptManager.getList(null, p);
        if (deptList != null && deptList.size() > 0)
            mp.put("deptList", deptList);
        // �õ����Ȩ��
        ResourceRightInfo viewRightInfo = new ResourceRightInfo();
        viewRightInfo.setResid(Long.parseLong(resid));
        viewRightInfo.setRighttype(1);
        List<ResourceRightInfo> rrList = this.resourceRightManager.getList(
                viewRightInfo, null);
        if (rrList != null && rrList.size() > 0)
            mp.put("reviewright", rrList);

        // �õ�����Ȩ��
        ResourceRightInfo downRightInfo = new ResourceRightInfo();
        downRightInfo.setResid(Long.parseLong(resid));
        downRightInfo.setRighttype(2);
        List<ResourceRightInfo> rdList = this.resourceRightManager.getList(downRightInfo, null);
        if (rdList != null && rdList.size() > 0)
            mp.put("redownright", rdList);
        //�õ��Ƽ��ؼ���
		/*DictionaryInfo dicTmp=new DictionaryInfo();
		dicTmp.setDictionarytype("RESOURCE_STORE_RECOMMEND");
		List<DictionaryInfo> recommendStoreDic=this.dictionaryManager.getList(dicTmp,null);
		mp.put("keyWordRecommend", recommendStoreDic);*/


        return new ModelAndView("/resource/base/update", mp);
    }

    /**
     * ���������Դ��ҳ�档
     *
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toadd", method = RequestMethod.GET)
    public ModelAndView toAddResourse(HttpServletRequest request, ModelMap mp)
            throws Exception {

        //�����µ�ID
        mp.put("nextId", this.resourceManager.getNextId(true));
        //�õ���Դ����
        List<DictionaryInfo> resTypeDicList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        mp.put("resTypeDicList",resTypeDicList);


        // �õ���������  RES_LEVEL
        resTypeDicList=this.dictionaryManager.getDictionaryByType("RES_LEVEL");
        mp.put("resLevelDicList",resTypeDicList);

        return new ModelAndView("/resource/base/add", mp);
    }

    /**
     * �����YԴ��B
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=changeresstate", method = RequestMethod.POST)
    public void changeResourceState(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        // �õ�����
        ResourceInfo resInfo = this.getParameter(request, ResourceInfo.class);
        JsonEntity jeEntity = new JsonEntity();
        if (resInfo == null || resInfo.getResid() == null
                || resInfo.getResstatus() == null) {
            jeEntity.setMsg("�������󣡣�");
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        List<String> sqlArrayList=new ArrayList<String>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.resourceManager.getUpdateSql(resInfo,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }

        ResourceInfo rs=new ResourceInfo();
        rs.setResid(resInfo.getResid());
        List<ResourceInfo> rsList=this.resourceManager.getList(rs,null);
        if(rsList!=null&&rsList.size()>0&&resInfo.getResstatus()!=null&&resInfo.getResstatus()==3){
            ResourceInfo res=rsList.get(0);

            sqlbuilder=new StringBuilder();
            //�����Դϵͳ֪ͨ��¼
            MyInfoCloudInfo mc=new MyInfoCloudInfo();
            mc.setTargetid(res.getResid());
            mc.setUserid(Long.parseLong(res.getUserid().toString()));
            //                if(commentinfo.getAnonymous()==null&&commentinfo.getAnonymous()!=1)
            //                    mc.setData(this.logined(request).getRealname() + " �����������Դ <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+commentinfo.getCommentobjectid()+"\">#ETIANTIAN_SPLIT#</a>");
            //                else
            mc.setData("�ҵ���Դ <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+res.getResid()+"\">#ETIANTIAN_SPLIT#</a> ������Աɾ��!");
            mc.setType(1);
            objList=this.resourceManager.getMyInfoCloudSaveSql(mc,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        // ִ��
        if (sqlArrayList!=null&&sqlArrayList.size()>0&&this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
            jeEntity.setType("success");
        }else
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(jeEntity.toJSON());
    }

    /**
     * ִ�����
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=doupdate", method = RequestMethod.POST)
    public void doUpdateResource(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        // �õ�����
        ResourceInfo resInfo = this.getParameter(request, ResourceInfo.class);
        JsonEntity jeEntity = new JsonEntity();
        if (resInfo.getResid() == null) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_RESID"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        if (resInfo.getResname() == null
                || resInfo.getResname().trim().length() < 1) {
            // jeEntity.setMsg("������Դ���Ʋ���Ϊ�գ���ˢ����!");
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_RESNAME"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        if (resInfo.getReskeyword() == null
                || resInfo.getReskeyword().trim().length() < 1) {
            // ���󣬹ؼ��ֲ���Ϊ�գ�������!
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_KEYWORD"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        if(this.resourceManager.doUpdate(resInfo))
            jeEntity.setType("success");
        response.getWriter().print(jeEntity.toJSON());
    }

    /**
     * ִ�����
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=doadd", method = RequestMethod.POST)
    public void doAddResource(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        // �õ�����
        ResourceInfo resInfo = this.getParameter(request, ResourceInfo.class);
        resInfo.setDcschoolid(this.logined(request).getDcschoolid());
        JsonEntity jeEntity = new JsonEntity();
        if (resInfo.getResid() == null) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_RESID"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }

        if (resInfo.getResname() == null
                || resInfo.getResname().trim().length() < 1) {
            // jeEntity.setMsg("������Դ���Ʋ���Ϊ�գ���ˢ����!");
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_RESNAME"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        if(resInfo.getRestype()==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        if(resInfo.getSharestatus()==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
//		if (resInfo.getReskeyword() == null
//				|| resInfo.getReskeyword().trim().length() < 1) {
//			// ���󣬹ؼ��ֲ���Ϊ�գ�������!
//			jeEntity.setMsg(UtilTool.msgproperty
//					.getProperty("RESOURCE_NO_KEYWORD"));
//			response.getWriter().print(jeEntity.toJSON());
//			return;
//		}


        // �õ���Դ�ļ�
        // ----------------------------------------------------------
        String filename = request.getParameter("filename");
        if (filename == null || filename.trim().length() < 1) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_FILE"));// ����û�з����ļ�!��ˢ��ҳ�������!
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        resInfo.setFilename(null);

        String suffix=filename.substring(filename.lastIndexOf("."));
        if(suffix!=null&&suffix.trim().length()>0){
            if(UtilTool._VIEW_SUFFIX_TYPE_REGULAR.indexOf(suffix)!=-1){
                if(!suffix.toLowerCase().equals(".mp4")){
                    jeEntity.setMsg("����MP4��ʽ����Ƶ����ת�������ϴ�!");
                    response.getWriter().print(jeEntity.toJSON());
                    return;
                }
            }

        }
        String uptype=request.getParameter("uptype"); //1:
        if(Integer.parseInt(uptype)==1||resInfo.getFilesize()==null){//С�ļ�
            String fileurl=UtilTool.getResourceLocation(request,resInfo.getResid(),2)+"/"+UtilTool.getResourceUrl(resInfo.getResid().toString() ,suffix);
            File tmp=new File(fileurl);
            if(!tmp.exists()){
                jeEntity.setMsg("�ļ�������!");
                response.getWriter().print(jeEntity.toJSON());
                return;
            }
            resInfo.setFilesize(tmp.length());
        }
        resInfo.setFilesuffixname(suffix);

        resInfo.setUsername(this.logined(request).getRealname());


        resInfo.setSchoolname(this.logined(request).getDcschoolname());

        resInfo.setDcschoolid(this.logined(request).getDcschoolid());
        // ----------------------------------------------------------
        // Ĭ��ֵ
        resInfo.setUserid(this.logined(request).getUserid());
        resInfo.setResstatus(1);//�ϴ��ɹ���

        //�ļ����ʹ���
        List<DictionaryInfo> dicList=this.dictionaryManager.getDictionaryByType("FILE_SUFFIX_TYPE");

        if(dicList!=null&&dicList.size()>0){
            for (DictionaryInfo dic:dicList){
                if(dic!=null){
                    if(dic.getDictionaryname()!=null){
                        String[] typeArr=dic.getDictionaryname().split(",");
                        if(typeArr.length>0){
                            for(String str:typeArr){
                                if(resInfo.getFilesuffixname().trim().toLowerCase().equals(str.trim())){
                                    resInfo.setFiletype(Integer.parseInt(dic.getDictionaryvalue().trim()));
                                    break;
                                }
                            }
                            if(resInfo.getFiletype()!=null)
                                break;
                        }
                    }
                }
            }
        }
        if(resInfo.getFiletype()==null){  //�������ƥ�䣬��Ϊ�ı�����
            resInfo.setFiletype(1);
        }

        //�õ���ǰ��ʦ���ڵ����ѧ�ƣ�����꼶
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,null);
        /*
         ��Ʒ_��Դϵͳ
         PZYXT-68

         ��Դϵͳ������ʦ�ϴ���Դ��Ĭ��ѧ������

          �� ���� ����˱�ע - 08/����/14 1:48 ����
             ��Դϵͳ�ϴ���Դʱ�޷�Ϊ��Դ���ѧ�ơ��꼶���ݶ��������£�
             ��1��ѧ�ƣ��Խ�ʦ������ѧ��Ϊ��
             ��2���꼶����ʦ���ڰ༶������꼶
         *
         * ѧ��
         * �жϸ���ʦ���ڵ�ѧ�ƣ����û�����ã�����ȡ��ѧУ��ĳ��ѧ��
         */
        SubjectUser sbu=new SubjectUser();
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
        if(sbuList==null||sbuList.size()<1){
            if(mapList==null||mapList.size()<1){
                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
                if(subList==null||subList.size()<1){
                    jeEntity.setMsg("����û�з���ѧУ��ѧ�����ݣ�����ϵ�����Ա�����������!");
                    response.getWriter().print(jeEntity.toJSON());return;
                }
                resInfo.setSubject(subList.get(0).getSubjectid());
            }else  //���û�����ڣ�����ѧ�ƣ����հ༶����
                resInfo.setSubject(Integer.parseInt(mapList.get(0).get("SUBJECT_ID").toString()));
        }else{
            for(SubjectUser sb:sbuList){
                if(sb!=null){
                    if(sb.getIsmajor()==1){
                        resInfo.setSubject(sb.getSubjectid());break;
                    }
                }
            }
            if(resInfo.getSubject()==null)
                resInfo.setSubject(sbuList.get(0).getSubjectid());
        }
        /**
         * �꼶
         */
        if(mapList==null||mapList.size()<1){
            //�õ����ѧУ������꼶
            resInfo.setGrade(this.gradeManager.getList(null, null).get(0).getGradeid());
        }else
            resInfo.setGrade(Integer.parseInt(mapList.get(0).get("GRADE_ID").toString()));


/**
 * �����ǰû�н��ڵİ༶�����жϸ���ʦ���ڵ�ѧ�ƣ����û�����ã�����ȡ��ѧУ��ĳ��ѧ��
 * �����ǰû�н��ڵİ༶������ȡѧУ������꼶
 */

//        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,null);
//        if(mapList==null||mapList.size()<1){
//            resInfo.setGrade(this.gradeManager.getList(null, null).get(0).getGradeid());
//            //�õ�����ʦ���ڵ�ѧ��
//            SubjectUser sbu=new SubjectUser();
//            sbu.setUserid(this.logined(request).getRef());
//            List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
//            if(sbuList==null||sbuList.size()<1){
//                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
//                if(subList==null||subList.size()<1){
//                    jeEntity.setMsg("����û�з���ѧУ��ѧ�����ݣ�����ϵ�����Ա�����������!");
//                    response.getWriter().print(jeEntity.toJSON());return;
//                }
//                resInfo.setSubject(subList.get(0).getSubjectid());
//            }else{
//                for(SubjectUser sb:sbuList){
//                    if(sb!=null){
//                        if(sb.getIsmajor()==1){
//                            resInfo.setSubject(sb.getSubjectid());break;
//                        }
//                    }
//                }
//                if(resInfo.getSubject()==null)
//                    resInfo.setSubject(sbuList.get(0).getSubjectid());
//            }
//        }else{
//            resInfo.setSubject(Integer.parseInt(mapList.get(0).get("SUBJECT_ID").toString()));
//            resInfo.setGrade(Integer.parseInt(mapList.get(0).get("GRADE_ID").toString()));
//        }

        //��Դ�ȼ� ��Դ�ȼ���1:��׼ 2:���� 3:���أ�
        resInfo.setResdegree(3);
        //�õ��ļ�����
        String filetype=UtilTool.getConvertResourseType(resInfo.getFilesuffixname());
//        if(filetype.equals("jpeg"))
//            resInfo.setFiletype(4);//�ļ����� 5:����  4:ͼƬ 3:��Ƶ  2:��Ƶ   1:�ı�
//        else if(filetype.equals("video"))
//            resInfo.setFiletype(2);
//        else if(filetype.equals("mp3"))
//            resInfo.setFiletype(3);
//        else if(filetype.equals("swf"))
//            resInfo.setFiletype(5);
//        else if(filetype.equals("doc"))
//            resInfo.setFiletype(1);
//        else
//            resInfo.setFiletype(0);

        List<String>sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArraylist=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.resourceManager.getSaveSql(resInfo,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
            sqlArrayList.add(sqlbuilder.toString());
            objArraylist.add(objList);
        }
        if(resInfo.getSharestatus()!=null&&resInfo.getSharestatus()==1){//У�ڹ���
            sqlbuilder=new StringBuilder();
            MyInfoCloudInfo mc=new MyInfoCloudInfo();
            mc.setTargetid(Long.parseLong(resInfo.getResid().toString()));
            mc.setUserid(Long.parseLong(this.logined(request).getUserid().toString()));
            mc.setData("��������Դ<a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+resInfo.getResid()+"\">#ETIANTIAN_SPLIT#</a>!");
            mc.setType(1);
            objList=this.resourceManager.getMyInfoCloudSaveSql(mc,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
                sqlArrayList.add(sqlbuilder.toString());
                objArraylist.add(objList);
            }

        }

        // ִ��
        if(this.resourceManager.doExcetueArrayProc(sqlArrayList,objArraylist)){
            System.out.println("�ϴ���Դ�������Ϣ�ɹ�!");

            jeEntity.setType("success");
            // ����nextid
            jeEntity.getObjList().add(this.resourceManager.getNextId());
            // �ļ�ת��
            if (resInfo.getFilesuffixname() != null && resInfo.getFilesuffixname().trim().length() > 0) {
                if (filetype != null && filetype.equals("doc")) {
                    UtilTool.Office2Swf(request, resInfo.getResid().toString(),resInfo.getFilesuffixname());
                }
            }
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        } else
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(jeEntity.toJSON());
    }

    @RequestMapping(params = "m=ajaxMyResourceList", method = RequestMethod.POST)
    public void getMyResourceList(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        ResourceInfo resourceinfo = this.getParameter(request,
                ResourceInfo.class);
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        if(resourceinfo==null)
            resourceinfo = new ResourceInfo();
        resourceinfo.setUserid(this.logined(request).getUserid());
        resourceinfo.setResstatus(-3);
        presult.setOrderBy(" C_TIME DESC");
        List<ResourceInfo> extList = this.resourceManager.getMyResList(resourceinfo, presult);
        presult.setList(extList);
        je.setPresult(presult);
        response.getWriter().print(je.toJSON());
    }
    @RequestMapping(params = "m=ajaxUser", method = RequestMethod.POST)
    public void getResUserName(HttpServletRequest request,
                               HttpServletResponse response) throws Exception{
        String userid=request.getParameter("userid");
        String username=request.getParameter("username");
        PageResult presult=this.getPageResultParameter(request);
        Integer uid=null;
        if(userid!=null&&userid.trim().length()>0)
            uid=Integer.parseInt(userid.trim());
        //�õ���ʦ
        Integer dcSchoolID=this.logined(request).getDcschoolid();
        List<ResourceInfo> rsList=this.resourceManager.getListByUser(username,uid,presult,dcSchoolID);
        presult.getList().add(rsList);
        //�õ���У
        if(username!=null&&username.trim().length()>0){
            List<ResourceInfo> schoolList=this.resourceManager.getListBySchoolName(username,2,null);
            presult.getList().add(schoolList);
            List<ResourceInfo> uArrayList=new ArrayList<ResourceInfo>();
            //�õ���Ա
            if(schoolList!=null&&schoolList.size()>0){
                for (ResourceInfo schooltmp:schoolList){
                    PageResult tmpresult=new PageResult();
                    tmpresult.setPageNo(1);
                    tmpresult.setPageSize(10);

                    List<ResourceInfo> uList=this.resourceManager.getListBySchoolName(schooltmp.getSchoolname(),1,tmpresult);
                    if(uList!=null&&uList.size()>0)
                        uArrayList.addAll(uList);
                }
                presult.getList().add(uArrayList);
            }
        }
        JsonEntity je=new JsonEntity();
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=ajaxList", method = RequestMethod.POST)
    public void getResList(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        ResourceInfo resourceinfo = this.getParameter(request,
                ResourceInfo.class);
        resourceinfo.setResstatus(-3);
        String timeRange = request.getParameter("timerange");
        if (timeRange != null && timeRange.trim().length() > 0) {
            if (resourceinfo == null)
                resourceinfo = new ResourceInfo();
            Calendar currentDate = new GregorianCalendar();
            if (timeRange.trim().equals("day")) {
                // ��ȡ������Դ��������
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("week")) {
                // ��ȡ������Դ��������
                currentDate.setFirstDayOfWeek(Calendar.MONDAY);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("halfMonth")) {
                // ��ȡ������Դ��������
                currentDate.add(Calendar.DATE, -15);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);

                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("month")) {
                // ��ȡ������Դ��������
                currentDate = new GregorianCalendar();
                currentDate.set(Calendar.DATE, 1);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            }
        }
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        if(presult.getOrderBy()==null||presult.getOrderBy().trim().length()<1)
            presult.setOrderBy(" diff_type desc,is_mic_copiece asc,res_id ");
        List<ResourceInfo> extList = this.resourceManager.getList(
                resourceinfo, presult);
        StringBuilder resStrBuilder=new StringBuilder();
        if(extList!=null&&extList.size()>0){
            for(ResourceInfo entity:extList){
                if(entity!=null){
                    if(resStrBuilder.toString().trim().length()>0)
                        resStrBuilder.append(",");
                    resStrBuilder.append(entity.getResid());
                }
            }
            if(resStrBuilder.toString().trim().length()>0){
                List<Map<String,Object>> mapList=this.teachingMaterialManager.getTeachingMaterialGradeSubByResId(resStrBuilder.toString());
                if(mapList!=null&&mapList.size()>0){
                    for (Map<String,Object> mp:mapList){
                        for(ResourceInfo entity:extList){
                            if(entity!=null&&mp.get("RES_ID")!=null&&entity.getGrade()==null&&entity.getSubject()==null){
                                if(entity.getResid().toString().equals(mp.get("RES_ID").toString().trim())){
                                    if(mp.get("GRADE_ID")!=null)
                                        entity.setGrade(Integer.parseInt(mp.get("GRADE_ID").toString()));
                                    if(mp.get("GRADE_NAME")!=null)
                                        entity.setGradename(mp.get("GRADE_NAME").toString());
                                    if(mp.get("SUBJECT_ID")!=null)
                                        entity.setSubject(Integer.parseInt(mp.get("SUBJECT_ID").toString()));
                                    if(mp.get("SUBJECT_NAME")!=null)
                                        entity.setSubjectname(mp.get("SUBJECT_NAME").toString());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        presult.setList(extList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=ajaxListByValues", method = RequestMethod.POST)
    public void getResListByValues(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        ResourceInfo resourceinfo = this.getParameter(request,
                ResourceInfo.class);
        resourceinfo.setDcschoolid(this.logined(request).getDcschoolid());
        resourceinfo.setResstatus(1);
        String timeRange = request.getParameter("timerange");
        if (timeRange != null && timeRange.trim().length() > 0) {
            if (resourceinfo == null)
                resourceinfo = new ResourceInfo();
            Calendar currentDate = new GregorianCalendar();
            if (timeRange.trim().equals("day")) {
                // ��ȡ������Դ��������
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("week")) {
                // ��ȡ������Դ��������
                currentDate.setFirstDayOfWeek(Calendar.MONDAY);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("halfMonth")) {
                // ��ȡ������Դ��������
                currentDate.add(Calendar.DATE, -15);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("month")) {
                // ��ȡ������Դ��������
                currentDate = new GregorianCalendar();
                currentDate.set(Calendar.DATE, 1);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            }
        }
        resourceinfo.setResstatus(1);//���ͨ����
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        if(presult.getOrderBy()==null||presult.getOrderBy().trim().length()<1)
            presult.setOrderBy(" diff_type desc,is_mic_copiece asc,res_id ");
        if(resourceinfo.getIsunion()==null)
            resourceinfo.setIsunion(1);
        else if(resourceinfo.getIsunion()==0)
            resourceinfo.setIsunion(null);
        //����δ��
        resourceinfo.setVoteuid(this.logined(request).getUserid());
        StringBuilder resStrBuilder=new StringBuilder();
        List<ResourceInfo> extList = this.resourceManager.getListByExtendValue(resourceinfo , presult);
        if(extList!=null&&extList.size()>0){
            for(ResourceInfo entity:extList){
                if(entity!=null){
                    if(resStrBuilder.toString().trim().length()>0)
                        resStrBuilder.append(",");
                    resStrBuilder.append(entity.getResid());
                }
            }
            if(resStrBuilder.toString().trim().length()>0){
                List<Map<String,Object>> mapList=this.teachingMaterialManager.getTeachingMaterialGradeSubByResId(resStrBuilder.toString());
                if(mapList!=null&&mapList.size()>0){
                    for (Map<String,Object> mp:mapList){
                        for(ResourceInfo entity:extList){
                            if(entity!=null&&mp.get("RES_ID")!=null&&entity.getGrade()==null&&entity.getSubject()==null){
                                if(entity.getResid().toString().equals(mp.get("RES_ID").toString().trim())){
                                    if(mp.get("GRADE_ID")!=null)
                                        entity.setGrade(Integer.parseInt(mp.get("GRADE_ID").toString()));
                                    if(mp.get("GRADE_NAME")!=null)
                                        entity.setGradename(mp.get("GRADE_NAME").toString());
                                    if(mp.get("SUBJECT_ID")!=null)
                                        entity.setSubject(Integer.parseInt(mp.get("SUBJECT_ID").toString()));
                                    if(mp.get("SUBJECT_NAME")!=null)
                                        entity.setSubjectname(mp.get("SUBJECT_NAME").toString());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        presult.setList(extList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=ajaxUserList", method = RequestMethod.POST)
    public void getUserSortResultList(HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        ResourceInfo resourceinfo = this.getParameter(request,
                ResourceInfo.class);
        //ר����Դ�������ӵĲ���
        String courseid = request.getParameter("courseid");
        String timeRange = request.getParameter("timerange");
        if (timeRange != null && timeRange.trim().length() > 0) {
            if (resourceinfo == null)
                resourceinfo = new ResourceInfo();
            Calendar currentDate = new GregorianCalendar();
            if (timeRange.trim().equals("day")) {
                // ��ȡ������Դ��������
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("week")) {
                // ��ȡ������Դ��������
                currentDate.setFirstDayOfWeek(Calendar.MONDAY);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("month")) {
                // ��ȡ������Դ��������
                currentDate = new GregorianCalendar();
                currentDate.set(Calendar.DATE, 1);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            }
        }

        if(courseid!=null&&courseid.length()>0)
            resourceinfo.setCourseid(Long.parseLong(courseid));

        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        resourceinfo.setResstatus(1);
        List<UserInfo> extList = this.resourceManager.getListByUserSort(resourceinfo, presult);

        presult.setList(extList);
        je.setPresult(presult);
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=ajaxExcellentList", method = RequestMethod.POST)
    public void getExcellentResource(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        boolean reverse=false;
        JsonEntity je = new JsonEntity();
        ResourceInfo resourceinfo = this.getParameter(request,
                ResourceInfo.class);
        resourceinfo.setResstatus(1);
        resourceinfo.setDcschoolid(this.logined(request).getDcschoolid());
        PageResult presult = this.getPageResultParameter(request);
        String type = request.getParameter("type");
        if(type!=null&&type.equals("video")){           //��Ƶ������Ƶ�붯��
//            type=UtilTool._VIEW_SUFFIX_TYPE_REGULAR;
//            type+="|"+UtilTool._SWF_SUFFIX_TYPE_REGULAR;
//            type=type.replaceAll("\\(|\\)|\\$","");
            type="1";
        }else if(type!=null&&type.equals("doc")){       //�ĵ���������Ƶ�붯��������ļ�
//            type=UtilTool._DOC_SUFFIX_TYPE_REGULAR;
//            type+="|"+UtilTool._IMG_SUFFIX_TYPE_REGULAR;
//            type+="|"+UtilTool._MP3_SUFFIX_TYPE_REGULAR;
//            type+="|"+UtilTool._PDF_SUFFIX_TYPE_REGULAR;
//            type=type.replaceAll("\\(|\\)|\\$","");
            type="2";
        }
//        else{
//            type=UtilTool._VIEW_SUFFIX_TYPE_REGULAR+"|"+UtilTool._DOC_SUFFIX_TYPE_REGULAR;
//            type=type.replaceAll("\\(|\\)|\\$","");
//            reverse=true;
//        }
        resourceinfo.setType(type);
        resourceinfo.setReverse(reverse);
        presult.setOrderBy(" r.RECOMENDNUM desc,r.praisenum desc ");

        //�õ���ǰ��ʦ���ڵ����ѧ�ƣ�����꼶
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,null);
        /*
         ��Ʒ_��Դϵͳ
         PZYXT-68

         ��Դϵͳ������ʦ�ϴ���Դ��Ĭ��ѧ������

          �� ���� ����˱�ע - 08/����/14 1:48 ����
             ��Դϵͳ�ϴ���Դʱ�޷�Ϊ��Դ���ѧ�ơ��꼶���ݶ��������£�
             ��1��ѧ�ƣ��Խ�ʦ������ѧ��Ϊ��
             ��2���꼶����ʦ���ڰ༶������꼶
         *
         * ѧ��
         * �жϸ���ʦ���ڵ�ѧ�ƣ����û�����ã�����ȡ��ѧУ��ĳ��ѧ��
         */
        SubjectUser sbu=new SubjectUser();
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
        if(sbuList==null||sbuList.size()<1){
            if(mapList==null||mapList.size()<1){
                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
                if(subList!=null&&subList.size()>0)
                    resourceinfo.setSubject(subList.get(0).getSubjectid());
            }else{  //���û�����ڣ�����ѧ�ƣ����հ༶����
                for (Map<String,Object> objMap:mapList){
                    if(objMap!=null&&objMap.get("SUBJECT_ID")!=null){
                        if(resourceinfo.getSubjectvalues()!=null&&resourceinfo.getSubjectvalues().trim().length()>0
                                &&resourceinfo.getSubjectvalues().indexOf(objMap.get("SUBJECT_ID").toString())<0)
                            resourceinfo.setSubjectvalues(resourceinfo.getSubjectvalues() + ",");
                        if(resourceinfo.getSubjectvalues()==null)
                            resourceinfo.setSubjectvalues(objMap.get("SUBJECT_ID").toString());
                        else
                            resourceinfo.setSubjectvalues( resourceinfo.getSubjectvalues()+objMap.get("SUBJECT_ID").toString());
                    }
                }
            }
        }else{
            for(SubjectUser sb:sbuList){
                if(sb!=null){
                    if(resourceinfo.getSubjectvalues()!=null&&resourceinfo.getSubjectvalues().trim().length()>0
                            &&resourceinfo.getSubjectvalues().indexOf(sb.getSubjectid().toString())<0){
                        resourceinfo.setSubjectvalues(resourceinfo.getSubjectvalues()+","+sb.getSubjectid());
                    }else
                        resourceinfo.setSubjectvalues(sb.getSubjectid().toString());
                }
            }
        }
        /**
         * �꼶
         */
        if(mapList!=null&&mapList.size()>0){
            //�õ����ѧУ������꼶
            //  resourceinfo.setGrade(this.gradeManager.getList(null, null).get(0).getGradeid());
            for (Map<String,Object> mp:mapList){
                if(mp!=null&&mp.containsKey("GRADE_ID")&&mp.get("GRADE_ID")!=null){
                    if( resourceinfo.getGradevalues()==null||resourceinfo.getGradevalues().indexOf(mp.get("GRADE_ID").toString())<0)
                        if(resourceinfo.getGradevalues()!=null&&resourceinfo.getGradevalues().trim().length()>0)
                            resourceinfo.setGradevalues(resourceinfo.getGradevalues() + ","+mp.get("GRADE_ID").toString());
                        else
                            resourceinfo.setGradevalues( mp.get("GRADE_ID").toString());
                }
            }
        }
        resourceinfo.setSharestatusvalues("1,2");
        List<ResourceInfo> rsList = this.resourceManager.getListByExtendValue(resourceinfo, presult);
        presult.setList(rsList);
        if(presult.getPageTotal()>3){   //ֻ��ʾ��ҳ
            presult.setRecTotal(presult.getPageSize()*3);
        }
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=ajaxCheckList", method = RequestMethod.POST)
    public void getCheckResultList(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        ResourceInfo resourceinfo = this.getParameter(request,ResourceInfo.class);
        resourceinfo.setDcschoolid(this.logined(request).getDcschoolid());
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        presult.setOrderBy(" r.RES_STATUS ASC,r.REPORTNUM DESC,r.C_time DESC ");
        resourceinfo.setResstatus(-100);//(r.RES_STATUS=1 OR r.res_status=3)   ֻ��ѯ�Ѿ�ͨ���� ����ɾ����
        resourceinfo.setSharestatusvalues("1,2");//У�ڣ��ƶ˶���
         //resourceinfo.setIsunion(1);
        List<ResourceInfo> extList = this.resourceManager.getCheckListByExtendValue(resourceinfo, presult);
        if(extList!=null&&extList.size()>0){
            StringBuilder resStrBuilder=new StringBuilder();
            for(ResourceInfo entity:extList){
                if(entity!=null){
                    if(resStrBuilder.toString().trim().length()>0)
                        resStrBuilder.append(",");
                    resStrBuilder.append(entity.getResid());
                }
            }
            if(resStrBuilder.toString().trim().length()>0){
                List<Map<String,Object>> mapList=this.teachingMaterialManager.getTeachingMaterialGradeSubByResId(resStrBuilder.toString());
                if(mapList!=null&&mapList.size()>0){
                    for (Map<String,Object> mp:mapList){
                        for(ResourceInfo entity:extList){
                            if(entity!=null&&mp.get("RES_ID")!=null&&entity.getGrade()==null&&entity.getSubject()==null){
                                if(entity.getResid().toString().equals(mp.get("RES_ID").toString().trim())){
                                    if(mp.get("GRADE_ID")!=null)
                                        entity.setGrade(Integer.parseInt(mp.get("GRADE_ID").toString()));
                                    if(mp.get("GRADE_NAME")!=null)
                                        entity.setGradename(mp.get("GRADE_NAME").toString());
                                    if(mp.get("SUBJECT_ID")!=null)
                                        entity.setSubject(Integer.parseInt(mp.get("SUBJECT_ID").toString()));
                                    if(mp.get("SUBJECT_NAME")!=null)
                                        entity.setSubjectname(mp.get("SUBJECT_NAME").toString());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        presult.setList(extList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
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
            jeEntity.setMsg("����Դ�ѱ�ɾ��!");
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
        String isfromrank=request.getParameter("isfromrank");
        if(isfromrank==null||Integer.parseInt(isfromrank.toString().trim())==0){
            AccessInfo ai = new AccessInfo();
            ai.setResid(entity.getResid());
            PageResult pg = new PageResult();
            pg.setPageSize(10);

            if(isfromrank!=null)
                ai.setIsFromRank(Integer.parseInt(isfromrank.trim()));
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
                if(!this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                    jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                    response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
                }
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
            if(tcList!=null&& tcList.size()!=0){
//                jeEntity.setMsg("�Ҳ���ר�����ݣ�");// �쳣���󣬲������룬�޷���������!
//                response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
//                return null;
//            }
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


        return new ModelAndView("/resource/base/detail", mp);
    }

    /**
     * ����ĳ���ֶ�
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdateColumn",method = RequestMethod.POST)
    public void doUpdateColumn(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ResourceInfo resEntity=this.getParameter(request,ResourceInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        //��֤
        if(resEntity==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        if(resEntity.getResid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //��֤�Ƿ����resid   ֻ��һ��
        PageResult presult=new PageResult();
        presult.setPageNo(1);presult.setPageSize(1);

        ResourceInfo validateRes=new ResourceInfo();
        validateRes.setResid(resEntity.getResid());
        List<ResourceInfo> resList=this.resourceManager.getList(validateRes,presult);
        if(resList==null||resList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //��֤��ǰ��½���û�Ȩ��
        if(this.logined(request).getUserid().intValue()!=resList.get(0).getUserid().intValue()){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_SERVICE_RIGHT"));    //û�в���Ȩ��
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        List<String>sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();

        List<Object> objList=this.resourceManager.getUpdateSql(resEntity,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        if(resEntity.getSharestatus()!=null&&resEntity.getSharestatus()==1){
            sqlbuilder=new StringBuilder();
            MyInfoCloudInfo mc=new MyInfoCloudInfo();
            mc.setTargetid(Long.parseLong(resEntity.getResid().toString()));
            mc.setUserid(Long.parseLong(this.logined(request).getUserid().toString()));
            mc.setData("��������Դ <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+resEntity.getResid()+"\">#ETIANTIAN_SPLIT#</a>! ");
            mc.setType(1);
            objList=this.resourceManager.getMyInfoCloudSaveSql(mc,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }
        //ִ���޸�
        if(this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
            jsonEntity.setType("success");
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else{
            jsonEntity.setType("error");
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * �����꼶����½�ˣ��õ����ڵ�ѧ��
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getteachersub",method = RequestMethod.POST)
    public void getTeacherSub(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        String grade=request.getParameter("gradeid");
        if(grade==null||grade.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        List<SubjectInfo> subjectList=new ArrayList<SubjectInfo>();
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,Integer.parseInt(grade));
        if(mapList!=null&&mapList.size()>0){
            for(int i=0;i<mapList.size();i++){
                if(subjectList.size()<1){
                    SubjectInfo sb=new SubjectInfo();
                    sb.setSubjectid(Integer.parseInt(mapList.get(i).get("SUBJECT_ID").toString()));
                    sb.setSubjectname(mapList.get(i).get("SUBJECT_NAME").toString());
                    subjectList.add(sb);
                }else{
                    boolean ishas=false;
                    for(int j=0;i<subjectList.size();j++){
                        if(subjectList.get(0)!=null&&subjectList.size()>0
                                &&subjectList.get(i).getSubjectid().intValue()==Integer.parseInt(mapList.get(i).get("SUBJECT_ID").toString())){
                            ishas=true;
                            break;
                        }
                    }
                    if(!ishas){
                        SubjectInfo sb=new SubjectInfo();
                        sb.setSubjectid(Integer.parseInt(mapList.get(i).get("SUBJECT_ID").toString()));
                        sb.setSubjectname(mapList.get(i).get("SUBJECT_NAME").toString());
                        subjectList.add(sb);
                    }
                }
            }
        }
        jsonEntity.setType("success");
        jsonEntity.setObjList(subjectList);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ɾ����Դ(�߼�ɾ��)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doDeleResource",method=RequestMethod.POST)
    public void doDeleResource(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ResourceInfo entity=this.getParameter(request,ResourceInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        if(entity.getResid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //��֤�Ƿ���ڸ���Դ
        List<ResourceInfo> resList=this.resourceManager.getList(entity,null);
        if(resList==null||resList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        ResourceInfo res=resList.get(0);
        //ֻ��ɾ���Խ�����Դ
        if(res.getUserid().intValue()!=this.logined(request).getUserid()){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_SERVICE_RIGHT"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        if(res.getResdegree()!=null){  //������׼�� �ܾ�����
            if(res.getResdegree().intValue()==1||res.getResdegree().intValue()==2){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("JU_JUE_OPERATE"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
        }
        //�޸�״̬,ͬʱд�������¼
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        StringBuilder sqlbuilder=new StringBuilder();
        entity.setResstatus(3);//��ɾ��
        List<Object> objList=this.resourceManager.getUpdateSql(entity,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //������¼
        sqlbuilder=new StringBuilder();
        objList=this.resourceManager.getAddOperateLog(this.logined(request).getRef(),"rs_resource_info"
                ,entity.getResid().toString(),"","","UPDATE",this.logined(request).getRealname()+"(�߼�)ɾ������Դ"+entity.getResid(),sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }

        //�����Դϵͳ֪ͨ��¼
        MyInfoCloudInfo mc=new MyInfoCloudInfo();
        mc.setTargetid(res.getResid());
        mc.setUserid(Long.parseLong(res.getUserid().toString()));
//                if(commentinfo.getAnonymous()==null&&commentinfo.getAnonymous()!=1)
//                    mc.setData(this.logined(request).getRealname() + " �����������Դ <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+commentinfo.getCommentobjectid()+"\">#ETIANTIAN_SPLIT#</a>");
//                else
        mc.setData("�ҵ���Դ <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+res.getResid()+"\">#ETIANTIAN_SPLIT#</a> ������Աɾ��!");
        mc.setType(1);
        sqlbuilder=new StringBuilder();
        objList=this.resourceManager.getMyInfoCloudSaveSql(mc,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }

        if(sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            if(this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                jsonEntity.setType("success");
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));


            }else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else{
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        }
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ����ResId�õ�֪ʶ��
     * @param request
     * @param response
     */
    @RequestMapping(params="m=ajaxResCourse",method=RequestMethod.POST)
    public void ajaxResCourse(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String resid=request.getParameter("resid");
        JsonEntity jsonEntity=new JsonEntity();
        if(resid==null||resid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }

        List<Map<String,Object>> mapList=this.resourceManager.getResourceCourseList(resid);
        jsonEntity.setType("success");
        jsonEntity.setObjList(mapList);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ������Դ���а�
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=getResourceImage", method = RequestMethod.GET)
    public void getResourceImage(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String imgw=request.getParameter("w");
        String imgh=request.getParameter("h");
        String resid=request.getParameter("resid");
        if(resid==null||resid.length()<1){
            return;
        }
        String path=null;
        if(Long.parseLong(resid.toString())<0)
            path=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH");//����
        else
            path=UtilTool.utilproperty.getProperty("RESOURCE_CLOUD_SERVER_PATH");//�ƶ�
        //��֤����Դ�Ƿ����
        ResourceInfo rsInfo=new ResourceInfo();
        rsInfo.setResid(Long.parseLong(resid.toString()));
        List<ResourceInfo> rsList=resourceManager.getList(rsInfo,null);
        if(rsList!=null&&rsList.size()>0){
            path+="/"+UtilTool.getResourceFileUrl(rsList.get(0).getResid().toString(),rsList.get(0).getFilesuffixname());//����·��
            if(imgw!=null&&imgh!=null&&imgw.trim().length()>0&&imgh.trim().length()>0){
                //������ͼ
                writeImage(response, ImgResize(path, Integer.parseInt(imgw), Integer.parseInt(imgh)));
            }
        }
    }

    /**
     * �����ҵ���Դ���а�����ҳ��
     * @param mp
     * @return
     */
    @RequestMapping(params="m=toMyResRankMore",method = RequestMethod.GET)
    public ModelAndView myResRankToMore(ModelMap mp){
        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        return new ModelAndView("/resource/more/myResRankToMore");
    }

    /**
     * �õ��ҵ���Դ���а�����
     * @param request
     * @param response
     */
    @RequestMapping(params="m=ajx_myResRankByPage",method=RequestMethod.POST)
    public void getMyResRankByPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        PageResult presult=this.getPageResultParameter(request);
        ResourceInfo resObj=new ResourceInfo();
        resObj.setUserid(this.logined(request).getUserid());
        List<ResourceInfo> resultList=this.resourceManager.getMyResStaticesRank(resObj,presult);
        jsonEntity.setType("success");
        presult.setList(resultList);
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * �õ�У�ڹ��������
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getXNShareByPage",method=RequestMethod.POST)
    public void getXNShareByPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        PageResult presult=this.getPageResultParameter(request);
        UserModelTotalScoreInfo userMTScore=new UserModelTotalScoreInfo();
        userMTScore.setModelId(3);//3��ʾ����Դϵͳ�Ļ���
        List<UserModelTotalScoreInfo> mapList=this.userModelTotalScoreManager.getList(userMTScore,presult);
        jsonEntity.setType("success");
        presult.setList(mapList);
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * �����������ҳ��
     * @param request
     * @param mp
     * @throws Exception
     */
    @RequestMapping(params="m=toHotClicksResTypeToMore",method=RequestMethod.GET)
    public ModelAndView getClicksHotResType(HttpServletRequest request,ModelMap mp) throws Exception{
        return new ModelAndView("/resource/more/hotClicksResRankToMore");
    }

    /**
     * ajax��ѯ���������͵õ�������а�  type=week:����Ϊ��λ����ǰ��7�죬type=month���Ա��µ�һ��Ϊ��λ������
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajx_hotClicksRes",method=RequestMethod.POST)
    public void getHotCLicksResByType(HttpServletRequest request,HttpServletResponse response) throws Exception{
        PageResult presult=this.getPageResultParameter(request);
        String type=request.getParameter("type");
        JsonEntity jsonEntity=new JsonEntity();
        if(type==null||type.trim().length()<1){
            jsonEntity.setMsg(UtilTool.utilproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //�õ���ǰ��½�û�������ѧ��,���û����õ�����ѧ�ƣ����û��ѧ����Ϣ����õ���ѧУ�ĵ�һ��ѧ��
        Integer subvalue=null;
        SubjectUser su = new SubjectUser();
        su.setUserid(this.logined(request).getRef());
        List<SubjectUser> subjectList = this.subjectUserManager.getList(su, null);
        if(subjectList!=null)
            for(SubjectUser tmp_su:subjectList){
                subvalue=tmp_su.getSubjectid();
                if(tmp_su.getIsmajor()==1)
                    break;
            }
        if(subvalue==null||subvalue<1){
            List<SubjectInfo> subjectEVList = this.subjectManager.getList(new SubjectInfo(), null);
//            for(SubjectInfo tmp_ev:subjectEVList){
//                if(tmp_ev.getSubjectid().equals(subvalue)){
//                    subvalue=tmp_ev.getSubjectid();
//                    break;
//                }
//            }
            subvalue=subjectEVList.get(0).getSubjectid();
        }
        Integer loginGrdid=null;
        //�õ���ǰ��ʦ���ڵ����ѧ�ƣ�����꼶
        List<Map<String,Object>> mapGrdList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,null);
        if(mapGrdList==null||mapGrdList.size()<1){
            //�õ����ѧУ������꼶
            loginGrdid=this.gradeManager.getList(null, null).get(0).getGradeid();
        }else
            loginGrdid=Integer.parseInt(mapGrdList.get(0).get("GRADE_ID").toString());

        ResourceInfo rs=new ResourceInfo();
        rs.setSubjectvalues(subvalue+"");
        rs.setGradevalues(loginGrdid+"");

        // ��ȡ������Դ��������
        Calendar currentDate = new GregorianCalendar();
        if(type.trim().toLowerCase().equals("week")){
            rs.setType("1");
        }else if(type.trim().toLowerCase().equals("month")){
            // ��ȡ������Դ��������
            rs.setType("2");
        }else{
            jsonEntity.setMsg(UtilTool.utilproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }

        //��ʼ��ѯ
        List<ResourceInfo> resList=this.resourceManager.getResourceIdxViewRankPage(rs,presult);
        //�õ��󣬿�ʼ�õ�������Ϣ
        StringBuilder resStrBuilder=new StringBuilder();
        if(resList!=null&&resList.size()>0){
            for(ResourceInfo entity:resList){
                if(entity!=null){
                    if(resStrBuilder.toString().trim().length()>0)
                        resStrBuilder.append(",");
                    resStrBuilder.append(entity.getResid());
                }
            }
            if(resStrBuilder.toString().trim().length()>0){
                List<Map<String,Object>> mapList=this.teachingMaterialManager.getTeachingMaterialGradeSubByResId(resStrBuilder.toString());
                if(mapList!=null&&mapList.size()>0){
                    for (Map<String,Object> mp:mapList){
                        for(ResourceInfo entity:resList){
                            if(entity!=null&&mp.get("RES_ID")!=null&&entity.getGrade()==null&&entity.getSubject()==null){
                                if(entity.getResid().toString().equals(mp.get("RES_ID").toString().trim())){
                                    if(mp.get("GRADE_ID")!=null)
                                        entity.setGrade(Integer.parseInt(mp.get("GRADE_ID").toString()));
                                    if(mp.get("GRADE_NAME")!=null)
                                        entity.setGradename(mp.get("GRADE_NAME").toString());
                                    if(mp.get("SUBJECT_ID")!=null)
                                        entity.setSubject(Integer.parseInt(mp.get("SUBJECT_ID").toString()));
                                    if(mp.get("SUBJECT_NAME")!=null)
                                        entity.setSubjectname(mp.get("SUBJECT_NAME").toString());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        presult.setList(resList);
        jsonEntity.setType("success");
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ������������ҳ��
     * @param request
     * @param mp
     * @throws Exception
     */
    @RequestMapping(params="m=toHotDownResTypeToMore",method=RequestMethod.GET)
    public ModelAndView getDownHotResType(HttpServletRequest request,ModelMap mp) throws Exception{
        return new ModelAndView("/resource/more/hotDownResRankToMore");
    }
    /**
     * ajax��ѯ���������͵õ�������а�  type=week:����Ϊ��λ����ǰ��7�죬type=month���Ա��µ�һ��Ϊ��λ������
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajx_hotDownRes",method=RequestMethod.POST)
    public void getHotDownResByType(HttpServletRequest request,HttpServletResponse response) throws Exception{
        PageResult presult=this.getPageResultParameter(request);
        // String type=request.getParameter("type");
        JsonEntity jsonEntity=new JsonEntity()  ;
        //�õ���ǰ��½�û�������ѧ��,���û����õ�����ѧ�ƣ����û��ѧ����Ϣ����õ���ѧУ�ĵ�һ��ѧ��
        Integer subvalue=null;
        SubjectUser su = new SubjectUser();
        su.setUserid(this.logined(request).getRef());
        List<SubjectUser> subjectList = this.subjectUserManager.getList(su, null);
        if(subjectList!=null)
            for(SubjectUser tmp_su:subjectList){
                subvalue=tmp_su.getSubjectid();
                if(tmp_su.getIsmajor()==1)
                    break;
            }
        if(subvalue==null||subvalue<1){
            List<SubjectInfo> subjectEVList = this.subjectManager.getList(new SubjectInfo(), null);
//            for(SubjectInfo tmp_ev:subjectEVList){
//                if(tmp_ev.getSubjectid().equals(subvalue)){
//                    subvalue=tmp_ev.getSubjectid();
//                    break;
//                }
//            }
            subvalue=subjectEVList.get(0).getSubjectid();
        }
        Integer loginGrdid=null;
        //�õ���ǰ��ʦ���ڵ����ѧ�ƣ�����꼶
        List<Map<String,Object>> mapGrdList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"�ο���ʦ",null,null);
        if(mapGrdList==null||mapGrdList.size()<1){
            //�õ����ѧУ������꼶
            loginGrdid=this.gradeManager.getList(null, null).get(0).getGradeid();
        }else
            loginGrdid=Integer.parseInt(mapGrdList.get(0).get("GRADE_ID").toString());

        ResourceInfo rs=new ResourceInfo();
        rs.setSubjectvalues(subvalue+"");
        rs.setGradevalues(loginGrdid+"");
        rs.setType("3");//1����  2����  3����������
        //��ʼ��ѯ
        List<ResourceInfo> resList=this.resourceManager.getResourceIdxDownRankPage(rs, presult);
        //�õ��󣬿�ʼ�õ�������Ϣ
        StringBuilder resStrBuilder=new StringBuilder();
        if(resList!=null&&resList.size()>0){
            for(ResourceInfo entity:resList){
                if(entity!=null){
                    if(resStrBuilder.toString().trim().length()>0)
                        resStrBuilder.append(",");
                    resStrBuilder.append(entity.getResid());
                }
            }
            if(resStrBuilder.toString().trim().length()>0){
                List<Map<String,Object>> mapList=this.teachingMaterialManager.getTeachingMaterialGradeSubByResId(resStrBuilder.toString());
                if(mapList!=null&&mapList.size()>0){
                    for (Map<String,Object> mp:mapList){
                        for(ResourceInfo entity:resList){
                            if(entity!=null&&mp.get("RES_ID")!=null&&entity.getGrade()==null&&entity.getSubject()==null){
                                if(entity.getResid().toString().equals(mp.get("RES_ID").toString().trim())){
                                    if(mp.get("GRADE_ID")!=null)
                                        entity.setGrade(Integer.parseInt(mp.get("GRADE_ID").toString()));
                                    if(mp.get("GRADE_NAME")!=null)
                                        entity.setGradename(mp.get("GRADE_NAME").toString());
                                    if(mp.get("SUBJECT_ID")!=null)
                                        entity.setSubject(Integer.parseInt(mp.get("SUBJECT_ID").toString()));
                                    if(mp.get("SUBJECT_NAME")!=null)
                                        entity.setSubjectname(mp.get("SUBJECT_NAME").toString());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        presult.setList(resList);
        jsonEntity.setType("success");
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     *�õ������ҵĵ�ǰ��̬
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajx_RsMyInfoCloud",method = RequestMethod.POST)
    public void ajx_RsMyInfoCloud(HttpServletRequest request,HttpServletResponse response) throws Exception{
        PageResult presult=this.getPageResultParameter(request);
        MyInfoCloudInfo myInfoCloudInfo=new MyInfoCloudInfo();
        myInfoCloudInfo.setUserid(Long.parseLong(this.logined(request).getUserid()+""));
        myInfoCloudInfo.setType(1);
        List<MyInfoCloudInfo> myList=this.resourceManager.getMyInfoCloudList(myInfoCloudInfo,presult);
        presult.setList(myList);
        JsonEntity jsonEntity=new JsonEntity();
        jsonEntity.setType("success");
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     *�õ����˶�̬AJAX
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajx_RsMyInfoCloudOther",method = RequestMethod.POST)
    public void ajx_RsMyInfoCloudOther(HttpServletRequest request,HttpServletResponse response) throws Exception{
        PageResult presult=this.getPageResultParameter(request);
        MyInfoCloudInfo myInfoCloudInfo=new MyInfoCloudInfo();
        myInfoCloudInfo.setUserid(Long.parseLong(this.logined(request).getUserid()+""));
        myInfoCloudInfo.setType(1);
        List<MyInfoCloudInfo> myList=this.resourceManager.getMyInfoCloudOtherList(myInfoCloudInfo,presult);
        presult.setList(myList);
        JsonEntity jsonEntity=new JsonEntity();
        jsonEntity.setType("success");
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ��֤��Դ�ļ��Ƿ����
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajx_mkDocFile",method=RequestMethod.POST)
    public void ajx_validateResFile(HttpServletRequest request,HttpServletResponse response)throws Exception{
       String resid=request.getParameter("resid");
        JsonEntity jsonEntity=new JsonEntity();
        if(resid==null||resid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.toJSON());
            return;
        }
        //�õ���Դ��Ϣ
        ResourceInfo resObj=new ResourceInfo();
        resObj.setResid(Long.parseLong(resid.trim()));
        List<ResourceInfo> rsList=this.resourceManager.getList(resObj,null);
        if(rsList==null||rsList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.toJSON());
            return;
        }
        resObj=rsList.get(0);
        if(resObj.getFilesuffixname()==null||resObj.getFilesuffixname().trim().length()<1){
            jsonEntity.setMsg("���ݴ���û�к�׺��!");
            response.getWriter().println(jsonEntity.toJSON());
            return;
        }
        String filepath=UtilTool.getResourceFileUrl(resObj.getResid().toString(),resObj.getFilesuffixname());
        String filetype=UtilTool.getConvertResourseType(filepath);
        if(filetype!=null&&filetype.equals("doc")){
            //�����
            String destPath=UtilTool.getResourceLocation(request,Long.parseLong(resid),2)+"/"+UtilTool.getConvertPath(resid, resObj.getFilesuffixname());
            String sourcepath=UtilTool.getResourceLocation(request,Long.parseLong(resid),2)+"/"+UtilTool.getResourceFileUrl(resid,resObj.getFilesuffixname());
            //����ļ����ڻ���ת�����Ĵ���
            if(new File(sourcepath).exists()){
                if(!new File(destPath).exists()){
                    boolean issuccess=UtilTool.Office2Swf(request,resid, resObj.getFilesuffixname());
                    if(issuccess)jsonEntity.setType("success");
                    else jsonEntity.setMsg(filetype+"ת��ʧ��!");
                }else jsonEntity.setType("success");
            }else jsonEntity.setMsg("û��Դ�ļ�!");
        }else
            jsonEntity.setMsg("����Դ���Ͳ����ĵ����ͣ��޷�����ת��!");
        response.getWriter().println(jsonEntity.toJSON());
    }

}

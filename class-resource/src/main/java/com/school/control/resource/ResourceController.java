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


        //得到该教师的主授学科
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

        //得到当前老师的所有学科，如果没有则得到当前一个
        //得到该教师的主授学科
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
        //得到该教师的学科信息
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
                    .getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        TpCourseInfo tc = new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List tcList = this.tpCourseManager.getList(tc,null);
        if(tcList==null || tcList.size()==0){
            jeEntity.setMsg("找不到专题数据！");// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        tc = (TpCourseInfo)tcList.get(0);
        mp.put("tc",tc);
        if(tc.getMaterialids()!=null&&tc.getMaterialids().length()>0){
            String maid=tc.getMaterialids();
            if(maid.indexOf(",")!=-1)
                maid=maid.split(",")[0];
            //查询教材信息
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
                    .getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        mp.put("subjectEVList", subjectEVList);
        mp.put("gradeEVList", gradeEVList);
        mp.put("resTypeEVList", resTypeEVList);
        mp.put("resFileTypeEVList", resFileTypeEVList);

        // 获取资源总条数
        PageResult pr = new PageResult();
        ResourceInfo ri = new ResourceInfo();
        ri.setResstatus(1);
        this.resourceManager.getList(ri,pr);
        mp.put("resTotalNum", pr.getRecTotal());

        // 获取本日资源更新条数
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        ri.setCtime((Date) currentDate.getTime().clone());
        pr = new PageResult();
        this.resourceManager.getList(ri, pr);
        mp.put("dayResNum", pr.getRecTotal());

        // 获取本周资源更新条数
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

        // 获取本月资源更新条数
        currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DATE, -15);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        ri.setCtime((Date) currentDate.getTime().clone());
        pr = new PageResult();
        this.resourceManager.getList(ri, pr);
        mp.put("monthResNum", pr.getRecTotal());
        //得到资源公告
        NoticeInfo notic=new NoticeInfo();
        notic.setCuserid(this.logined(request).getRef());
        notic.setNoticetype("3");  //通知公告
        notic.setDcschoolid(this.logined(request).getDcschoolid());
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(5);
        presult.setOrderBy("n.IS_TOP ASC,C_TIME DESC");
        List<NoticeInfo> noticeList=this.noticeManager.getUserList(notic, presult);
        mp.put("resourceNotice", noticeList);
        //角色
        List<RoleInfo> roleList = this.roleManager.getList(null, null);
        mp.put("role", roleList);
        //年级
        List gradeList = this.gradeManager.getList(null, null);
        mp.put("gradeList", gradeList);

        //教师学科
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
        //得到排行版  1、我的资源排行榜
        ResourceInfo rankRes=new ResourceInfo();
        rankRes.setUserid(this.logined(request).getUserid());
        List<ResourceInfo> resRankList=this.resourceManager.getMyResStaticesRank(rankRes,myResRkPage);
        mp.put("myResRank",resRankList);
        //得到个人校内共享排行版
        UserModelTotalScoreInfo userModelTotalScoreInfo=new UserModelTotalScoreInfo();
        userModelTotalScoreInfo.setModelId(3);//资源系统
        myResRkPage.setOrderBy("total_score desc");
        List<UserModelTotalScoreInfo> umtsList=this.userModelTotalScoreManager.getList(userModelTotalScoreInfo,myResRkPage);
        mp.put("umtsList",umtsList);
        //得到云端共享排行版
        UserScoreRank usRank=new UserScoreRank();
        usRank.setOperatetype(1);
        usRank.setModelid(3L);//3：资源系统
        myResRkPage.setOrderBy("SCORE desc");
        List<UserScoreRank> usRankList=userScoreRankManager.getList(usRank,myResRkPage);
        mp.put("usRankList",usRankList);
        //得到分校排行榜
        SchoolScoreRank ssRank=new SchoolScoreRank();
        ssRank.setModelid(3L);//3：资源系统
        ssRank.setOperatetype(1);
        myResRkPage.setOrderBy("score desc");
        List<SchoolScoreRank> ssRankList=schoolScoreRankManager.getList(ssRank,myResRkPage);
        mp.put("ssRankList",ssRankList);
        //得到周汇报
        Integer loginGrdid=null;

        //得到当前老师教授的最大学科，最大年级
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,null);
        if(mapList==null||mapList.size()<1){
            //得到这个学校的最高年级
            loginGrdid=this.gradeManager.getList(null, null).get(0).getGradeid();
        }else
            loginGrdid=Integer.parseInt(mapList.get(0).get("GRADE_ID").toString());

        ResourceInfo rs=new ResourceInfo();
        rs.setSubjectvalues(subvalue+"");
        rs.setGradevalues(loginGrdid + "");
        rs.setType("1");  //type:周排行
        List<Map<String,Object>> weekRsList=this.resourceManager.getResourceIdxViewRank(rs,myResRkPage.getPageSize());
        mp.put("weekRsList",weekRsList);


        // 获取本月浏览量最高的资源
        rs.setType("2");  //type:月排行
        List<Map<String,Object>> monthRsList=this.resourceManager.getResourceIdxViewRank(rs, myResRkPage.getPageSize());
        mp.put("monthRsList",monthRsList);

        //得到下载排行
        rs.setCtime(null);
        List<Map<String,Object>> downResRankList=this.resourceManager.getResourceIdxDownRank(rs, myResRkPage.getPageSize());
        mp.put("downRsList",downResRankList);

        //查询是否第一次进入资源系统
        GuideInfo selGuide=new GuideInfo();
        selGuide.setOpuser(this.logined(request).getUserid());//用户
        selGuide.setOptype(1);                                  //类型  1:初次进入
        selGuide.setOptable("rs_resource_info");                //关临表名
        PageResult guidePresult=new PageResult();
        guidePresult.setPageSize(1);
        guidePresult.setPageNo(1);
        List<GuideInfo> guideList=this.guideManager.getList(selGuide,guidePresult);
        if(guideList==null||guideList.size()<1){        //该用户是初次进入
            mp.put("isFirstInto",1);
            //添加记录
            if(!this.guideManager.doSave(selGuide)){
                jeEntity.setMsg(UtilTool.msgproperty
                        .getProperty("OPERATE_ERROR"));// 操作失败！原因：未知！\n\n提示：如该提示经常出现，请主动联系管理员！
                response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
                return null;
            }
        }else
            mp.put("isFirstInto",2);//不是这第一次进入
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
                    .getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        //得到该教师的主授学科
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

        //得到当前老师教授的最大学科，最大年级
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,null);
        /*
         产品_资源系统
         PZYXT-68

         资源系统——教师上传资源的默认学科属性

          陈 艳旭 添加了备注 - 08/四月/14 1:48 下午
             资源系统上传资源时无法为资源标记学科、年级，暂定规则如下：
             （1）学科：以教师的主授学科为主
             （2）年级：教师教授班级的最高年级
         *
         * 学科
         * 判断该老师教授的学科，如果没有设置，则提取该学校的某个学科
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

            }else  //如果没有主授，辅授学科，则按照班级出发
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
            //得到这个学校的最高年级
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
                    .getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
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
                    .getProperty("PARAM_ERROR"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        //得到学校
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
                    .getProperty("PARAM_ERROR"));// 错误，参数不齐，无法正常访问!
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

        //得到当前老师教授的最大学科，最大年级
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,null);
        /*
         产品_资源系统
         PZYXT-68

         资源系统——教师上传资源的默认学科属性

          陈 艳旭 添加了备注 - 08/四月/14 1:48 下午
             资源系统上传资源时无法为资源标记学科、年级，暂定规则如下：
             （1）学科：以教师的主授学科为主courseList
             （2）年级：教师教授班级的最高年级
         *
         * 学科
         * 判断该老师教授的学科，如果没有设置，则提取该学校的某个学科
         */
        SubjectUser sbu=new SubjectUser();
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
        if(sbuList==null||sbuList.size()<1){
            if(mapList==null||mapList.size()<1){
                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
                if(subList!=null&&subList.size()>0)
                    mp.put("subjectid", subList.get(0).getSubjectid());
            }else  //如果没有主授，辅授学科，则按照班级出发
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
         * 年级
         */
        if(mapList==null||mapList.size()<1){
            //得到这个学校的最高年级
            mp.put("grdeid", this.gradeManager.getList(null, null).get(0).getGradeid());
        }else
            mp.put("gradeid", mapList.get(0).get("GRADE_ID").toString());



        return new ModelAndView("/resource/base/checkList", mp);
    }

    /**
     * 进入教师搜索的页面。
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
                tchname="北京四中网校";
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
     * 进入上传用户排行榜。
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
     * 进入资源排行榜。
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
     * 进入修改资源的页面。
     *
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toupdate", method = RequestMethod.GET)
    public ModelAndView toUpdateResourse(HttpServletRequest request,
                                         HttpServletResponse response, ModelMap mp) throws Exception {
        // 得到RES_ID
        JsonEntity jeEntity = new JsonEntity();
        String resid = request.getParameter("resid");
        if (resid == null||resid.trim().length()<1) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_RESID"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        ResourceInfo restmp = new ResourceInfo();
        restmp.setResid(Long.parseLong(resid));
        List<ResourceInfo> resList = this.resourceManager.getList(restmp, null);
        if (resList == null || resList.size() < 1) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_DATE"));// 异常错误，系统未发现您要查询的信息，可能已不存在!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        mp.put("resinfo", resList.get(0));
        // 得到对应的扩展树名
        restmp = resList.get(0);
        if (restmp == null) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_DATE"));// 异常错误，系统未发现您要查询的信息，可能已不存在!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        // 得到文件
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
        // 得到基本的属性
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
        // 得到浏览权限设置
        DictionaryInfo dicInfo = new DictionaryInfo();
        dicInfo.setDictionarytype("RS_RESOURCE_VIEW_RIGHT_TYPE");
        List<DictionaryInfo> dicList = this.dictionaryManager.getList(dicInfo,
                null);
        if (dicList != null && dicList.size() > 0)
            mp.put("resourceViewRightType", dicList);
        // 得到下载权限设置
        dicInfo = new DictionaryInfo();
        dicInfo.setDictionarytype("RS_RESOURCE_DOWN_RIGHT_TYPE");
        dicList = this.dictionaryManager.getList(dicInfo, null);
        if (dicList != null && dicList.size() > 0)
            mp.put("resourceDownRightType", dicList);
        // 得到学科
        SubjectUser sbu = new SubjectUser();
        sbu.getUserinfo().setRef(this.logined(request).getRef());
        List<SubjectUser> sbUserList = this.subjectUserManager.getList(sbu,
                null);
        if (sbUserList != null && sbUserList.size() > 0)
            mp.put("sublist", sbUserList);
        // 得到部门
        PageResult p = new PageResult();
        p.setOrderBy("d.dept_id");
        p.setPageNo(0);
        p.setPageSize(0);
        List<DeptInfo> deptList = this.deptManager.getList(null, p);
        if (deptList != null && deptList.size() > 0)
            mp.put("deptList", deptList);
        // 得到浏览权限
        ResourceRightInfo viewRightInfo = new ResourceRightInfo();
        viewRightInfo.setResid(Long.parseLong(resid));
        viewRightInfo.setRighttype(1);
        List<ResourceRightInfo> rrList = this.resourceRightManager.getList(
                viewRightInfo, null);
        if (rrList != null && rrList.size() > 0)
            mp.put("reviewright", rrList);

        // 得到下载权限
        ResourceRightInfo downRightInfo = new ResourceRightInfo();
        downRightInfo.setResid(Long.parseLong(resid));
        downRightInfo.setRighttype(2);
        List<ResourceRightInfo> rdList = this.resourceRightManager.getList(downRightInfo, null);
        if (rdList != null && rdList.size() > 0)
            mp.put("redownright", rdList);
        //得到推荐关键字
		/*DictionaryInfo dicTmp=new DictionaryInfo();
		dicTmp.setDictionarytype("RESOURCE_STORE_RECOMMEND");
		List<DictionaryInfo> recommendStoreDic=this.dictionaryManager.getList(dicTmp,null);
		mp.put("keyWordRecommend", recommendStoreDic);*/


        return new ModelAndView("/resource/base/update", mp);
    }

    /**
     * 进入添加资源的页面。
     *
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=toadd", method = RequestMethod.GET)
    public ModelAndView toAddResourse(HttpServletRequest request, ModelMap mp)
            throws Exception {

        //生成新的ID
        mp.put("nextId", this.resourceManager.getNextId(true));
        //得到资源类型
        List<DictionaryInfo> resTypeDicList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        mp.put("resTypeDicList",resTypeDicList);


        // 得到分享类型  RES_LEVEL
        resTypeDicList=this.dictionaryManager.getDictionaryByType("RES_LEVEL");
        mp.put("resLevelDicList",resTypeDicList);

        return new ModelAndView("/resource/base/add", mp);
    }

    /**
     * 更改資源狀態
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=changeresstate", method = RequestMethod.POST)
    public void changeResourceState(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        // 得到参数
        ResourceInfo resInfo = this.getParameter(request, ResourceInfo.class);
        JsonEntity jeEntity = new JsonEntity();
        if (resInfo == null || resInfo.getResid() == null
                || resInfo.getResstatus() == null) {
            jeEntity.setMsg("参数错误！！");
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
            //添加资源系统通知记录
            MyInfoCloudInfo mc=new MyInfoCloudInfo();
            mc.setTargetid(res.getResid());
            mc.setUserid(Long.parseLong(res.getUserid().toString()));
            //                if(commentinfo.getAnonymous()==null&&commentinfo.getAnonymous()!=1)
            //                    mc.setData(this.logined(request).getRealname() + " 评论了你的资源 <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+commentinfo.getCommentobjectid()+"\">#ETIANTIAN_SPLIT#</a>");
            //                else
            mc.setData("我的资源 <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+res.getResid()+"\">#ETIANTIAN_SPLIT#</a> 被管理员删除!");
            mc.setType(1);
            objList=this.resourceManager.getMyInfoCloudSaveSql(mc,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        // 执行
        if (sqlArrayList!=null&&sqlArrayList.size()>0&&this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
            jeEntity.setType("success");
        }else
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(jeEntity.toJSON());
    }

    /**
     * 执行添加
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=doupdate", method = RequestMethod.POST)
    public void doUpdateResource(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        // 得到参数
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
            // jeEntity.setMsg("错误，资源名称不能为空，请刷重试!");
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_RESNAME"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        if (resInfo.getReskeyword() == null
                || resInfo.getReskeyword().trim().length() < 1) {
            // 错误，关键字不能为空，请重试!
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
     * 执行添加
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=doadd", method = RequestMethod.POST)
    public void doAddResource(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        // 得到参数
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
            // jeEntity.setMsg("错误，资源名称不能为空，请刷重试!");
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
//			// 错误，关键字不能为空，请重试!
//			jeEntity.setMsg(UtilTool.msgproperty
//					.getProperty("RESOURCE_NO_KEYWORD"));
//			response.getWriter().print(jeEntity.toJSON());
//			return;
//		}


        // 得到资源文件
        // ----------------------------------------------------------
        String filename = request.getParameter("filename");
        if (filename == null || filename.trim().length() < 1) {
            jeEntity.setMsg(UtilTool.msgproperty
                    .getProperty("RESOURCE_NO_FILE"));// 错误，没有发现文件!请刷新页面后重试!
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        resInfo.setFilename(null);

        String suffix=filename.substring(filename.lastIndexOf("."));
        if(suffix!=null&&suffix.trim().length()>0){
            if(UtilTool._VIEW_SUFFIX_TYPE_REGULAR.indexOf(suffix)!=-1){
                if(!suffix.toLowerCase().equals(".mp4")){
                    jeEntity.setMsg("仅限MP4格式的视频，请转换后再上传!");
                    response.getWriter().print(jeEntity.toJSON());
                    return;
                }
            }

        }
        String uptype=request.getParameter("uptype"); //1:
        if(Integer.parseInt(uptype)==1||resInfo.getFilesize()==null){//小文件
            String fileurl=UtilTool.getResourceLocation(request,resInfo.getResid(),2)+"/"+UtilTool.getResourceUrl(resInfo.getResid().toString() ,suffix);
            File tmp=new File(fileurl);
            if(!tmp.exists()){
                jeEntity.setMsg("文件不存在!");
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
        // 默认值
        resInfo.setUserid(this.logined(request).getUserid());
        resInfo.setResstatus(1);//上传成功的

        //文件类型处理
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
        if(resInfo.getFiletype()==null){  //如果都不匹配，则为文本类型
            resInfo.setFiletype(1);
        }

        //得到当前老师教授的最大学科，最大年级
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,null);
        /*
         产品_资源系统
         PZYXT-68

         资源系统——教师上传资源的默认学科属性

          陈 艳旭 添加了备注 - 08/四月/14 1:48 下午
             资源系统上传资源时无法为资源标记学科、年级，暂定规则如下：
             （1）学科：以教师的主授学科为主
             （2）年级：教师教授班级的最高年级
         *
         * 学科
         * 判断该老师教授的学科，如果没有设置，则提取该学校的某个学科
         */
        SubjectUser sbu=new SubjectUser();
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
        if(sbuList==null||sbuList.size()<1){
            if(mapList==null||mapList.size()<1){
                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
                if(subList==null||subList.size()<1){
                    jeEntity.setMsg("错误，没有发现学校的学科数据，请联系相关人员进行配置填加!");
                    response.getWriter().print(jeEntity.toJSON());return;
                }
                resInfo.setSubject(subList.get(0).getSubjectid());
            }else  //如果没有主授，辅授学科，则按照班级出发
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
         * 年级
         */
        if(mapList==null||mapList.size()<1){
            //得到这个学校的最高年级
            resInfo.setGrade(this.gradeManager.getList(null, null).get(0).getGradeid());
        }else
            resInfo.setGrade(Integer.parseInt(mapList.get(0).get("GRADE_ID").toString()));


/**
 * 如果当前没有教授的班级，则判断该老师教授的学科，如果没有设置，则提取该学校的某个学科
 * 如果当前没有教授的班级，则提取学校的最高年级
 */

//        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,null);
//        if(mapList==null||mapList.size()<1){
//            resInfo.setGrade(this.gradeManager.getList(null, null).get(0).getGradeid());
//            //得到该老师教授的学科
//            SubjectUser sbu=new SubjectUser();
//            sbu.setUserid(this.logined(request).getRef());
//            List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
//            if(sbuList==null||sbuList.size()<1){
//                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
//                if(subList==null||subList.size()<1){
//                    jeEntity.setMsg("错误，没有发现学校的学科数据，请联系相关人员进行配置填加!");
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

        //资源等级 资源等级（1:标准 2:共享 3:本地）
        resInfo.setResdegree(3);
        //得到文件类型
        String filetype=UtilTool.getConvertResourseType(resInfo.getFilesuffixname());
//        if(filetype.equals("jpeg"))
//            resInfo.setFiletype(4);//文件类型 5:动画  4:图片 3:音频  2:视频   1:文本
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
        if(resInfo.getSharestatus()!=null&&resInfo.getSharestatus()==1){//校内共享
            sqlbuilder=new StringBuilder();
            MyInfoCloudInfo mc=new MyInfoCloudInfo();
            mc.setTargetid(Long.parseLong(resInfo.getResid().toString()));
            mc.setUserid(Long.parseLong(this.logined(request).getUserid().toString()));
            mc.setData("分享了资源<a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+resInfo.getResid()+"\">#ETIANTIAN_SPLIT#</a>!");
            mc.setType(1);
            objList=this.resourceManager.getMyInfoCloudSaveSql(mc,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
                sqlArrayList.add(sqlbuilder.toString());
                objArraylist.add(objList);
            }

        }

        // 执行
        if(this.resourceManager.doExcetueArrayProc(sqlArrayList,objArraylist)){
            System.out.println("上传资源，添加消息成功!");

            jeEntity.setType("success");
            // 返回nextid
            jeEntity.getObjList().add(this.resourceManager.getNextId());
            // 文件转换
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
        //得到教师
        Integer dcSchoolID=this.logined(request).getDcschoolid();
        List<ResourceInfo> rsList=this.resourceManager.getListByUser(username,uid,presult,dcSchoolID);
        presult.getList().add(rsList);
        //得到分校
        if(username!=null&&username.trim().length()>0){
            List<ResourceInfo> schoolList=this.resourceManager.getListBySchoolName(username,2,null);
            presult.getList().add(schoolList);
            List<ResourceInfo> uArrayList=new ArrayList<ResourceInfo>();
            //得到人员
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
                // 获取本日资源更新条数
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("week")) {
                // 获取本周资源更新条数
                currentDate.setFirstDayOfWeek(Calendar.MONDAY);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("halfMonth")) {
                // 获取半月资源更新条数
                currentDate.add(Calendar.DATE, -15);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);

                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("month")) {
                // 获取本月资源更新条数
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
                // 获取本日资源更新条数
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("week")) {
                // 获取本周资源更新条数
                currentDate.setFirstDayOfWeek(Calendar.MONDAY);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("halfMonth")) {
                // 获取半月资源更新条数
                currentDate.add(Calendar.DATE, -15);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("month")) {
                // 获取本月资源更新条数
                currentDate = new GregorianCalendar();
                currentDate.set(Calendar.DATE, 1);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            }
        }
        resourceinfo.setResstatus(1);//审核通过的
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        if(presult.getOrderBy()==null||presult.getOrderBy().trim().length()<1)
            presult.setOrderBy(" diff_type desc,is_mic_copiece asc,res_id ");
        if(resourceinfo.getIsunion()==null)
            resourceinfo.setIsunion(1);
        else if(resourceinfo.getIsunion()==0)
            resourceinfo.setIsunion(null);
        //已赞未赞
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
        //专题资源调用增加的参数
        String courseid = request.getParameter("courseid");
        String timeRange = request.getParameter("timerange");
        if (timeRange != null && timeRange.trim().length() > 0) {
            if (resourceinfo == null)
                resourceinfo = new ResourceInfo();
            Calendar currentDate = new GregorianCalendar();
            if (timeRange.trim().equals("day")) {
                // 获取本日资源更新条数
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("week")) {
                // 获取本周资源更新条数
                currentDate.setFirstDayOfWeek(Calendar.MONDAY);
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                resourceinfo.setCtime((Date) currentDate.getTime().clone());
            } else if (timeRange.trim().equals("month")) {
                // 获取本月资源更新条数
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
        if(type!=null&&type.equals("video")){           //视频包括视频与动画
//            type=UtilTool._VIEW_SUFFIX_TYPE_REGULAR;
//            type+="|"+UtilTool._SWF_SUFFIX_TYPE_REGULAR;
//            type=type.replaceAll("\\(|\\)|\\$","");
            type="1";
        }else if(type!=null&&type.equals("doc")){       //文档包含除视频与动画以外的文件
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

        //得到当前老师教授的最大学科，最大年级
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,null);
        /*
         产品_资源系统
         PZYXT-68

         资源系统——教师上传资源的默认学科属性

          陈 艳旭 添加了备注 - 08/四月/14 1:48 下午
             资源系统上传资源时无法为资源标记学科、年级，暂定规则如下：
             （1）学科：以教师的主授学科为主
             （2）年级：教师教授班级的最高年级
         *
         * 学科
         * 判断该老师教授的学科，如果没有设置，则提取该学校的某个学科
         */
        SubjectUser sbu=new SubjectUser();
        sbu.setUserid(this.logined(request).getRef());
        List<SubjectUser> sbuList=this.subjectUserManager.getList(sbu,null);
        if(sbuList==null||sbuList.size()<1){
            if(mapList==null||mapList.size()<1){
                List<SubjectInfo> subList=this.subjectManager.getList(null,null);
                if(subList!=null&&subList.size()>0)
                    resourceinfo.setSubject(subList.get(0).getSubjectid());
            }else{  //如果没有主授，辅授学科，则按照班级出发
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
         * 年级
         */
        if(mapList!=null&&mapList.size()>0){
            //得到这个学校的最高年级
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
        if(presult.getPageTotal()>3){   //只显示三页
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
        resourceinfo.setResstatus(-100);//(r.RES_STATUS=1 OR r.res_status=3)   只查询已经通过的 或者删除的
        resourceinfo.setSharestatusvalues("1,2");//校内，云端都查
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
     * 进入修改资源的页面。
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
        // 得到相关参数
        ResourceInfo entity=this.getParameter(request,ResourceInfo.class);
        //验证参数
        if(entity.getResid()==null){    //资源参数错误，
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
        }
        //得到详情记录
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        entity.setResstatus(-3);//rs_status<>3的条件判断
        List<ResourceInfo> resourceList=this.resourceManager.getList(entity,presult);
        if(resourceList.size()<1){  //资源已经不存在
            jeEntity.setMsg("该资源已被删除!");
            response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
        }
        entity=resourceList.get(0); //得到资源相关信息

        //得到文件的类型
        String fileType=UtilTool.getResourseType(entity.getFilesuffixname());

        mp.put("fileType",fileType);
        //得到资源类型
        List<DictionaryInfo> resTypeDicList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        mp.put("resTypeDicList",resTypeDicList);

        //得到文件类型
        List<DictionaryInfo> fileTypeList=this.dictionaryManager.getDictionaryByType("RES_FILE_TYPE");
        mp.put("fileTypeList",fileTypeList);
        //查询教授科目
//        SubjectUser sbu=new SubjectUser();
//        sbu.setUserid(this.logined(request).getRef());
//        List<SubjectUser> sbUserList=this.subjectUserManager.getList(sbu,null);
        //得到年级
        List<GradeInfo> grdList=this.gradeManager.getList(null,null);
        mp.put("gradeList",grdList);
        //得到学科
        List<SubjectInfo> subList=this.subjectManager.getList(null,null);
        mp.put("subList",subList);


        //----------------------------------------------------
        int ismine = 2; // 1:是本人 2:不是本人
        // 捡测是该资源是否属于本人
        if(this.logined(request).getUserid() == entity.getUserid())
            ismine=1;
        request.setAttribute("ismine", ismine);
        //----------------------------------------------------
        int isstore = 2; // 1:已收藏 2:未收藏
        // 捡测是该资源是否被本人收藏
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setResid(entity.getResid());
        storeInfo.setUserid(this.logined(request).getUserid());
        List<StoreInfo> storeList = this.storeManager.getList(storeInfo, null);

        if (storeList != null && storeList.size() > 0) {
            isstore = 1;
        }
        request.setAttribute("isstore", isstore);
        //-----------------------------------------------------
        int isrecomend = 2; // 1:已推荐 2:未推荐
        // 捡测是该资源是否被本人推荐
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
        int ispraise= 2; // 1:已点赞 2:未点赞
        // 捡测是该资源是否被本人点赞
        or.setOperatetype(2);
        orList = this.operateRecordManager.getList(or, null);

        if (orList != null && orList.size() > 0) {
            ispraise = 1;
        }
        request.setAttribute("ispraise", ispraise);

        //-----------------------------------------------------
        int isreport= 2; // 1:已举报 2:未举报
        // 捡测是该资源是否被本人举报
        ResourceReport rr = new ResourceReport();
        rr.setResid(entity.getResid());
        rr.setUserid(this.logined(request).getUserid());
        List<ResourceReport> rrList = this.resourceReportManager.getList(rr, null);

        if (rrList != null && rrList.size() > 0) {
            isreport = 1;
        }
        request.setAttribute("isreport", isreport);

        DictionaryInfo dic = new DictionaryInfo();
        dic.setDictionaryname("资源评论");
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
        // 浏览记录表
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

            //更新
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
            //查询对应记录
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
//                jeEntity.setMsg("找不到专题数据！");// 异常错误，参数不齐，无法正常访问!
//                response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
//                return null;
//            }
                tc = (TpCourseInfo)tcList.get(0);
                mp.put("tc",tc);
                if(tc.getMaterialids()!=null&&tc.getMaterialids().length()>0){
                    String maid=tc.getMaterialids();
                    if(maid.indexOf(",")!=-1)
                        maid=maid.split(",")[0];
                    //查询教材信息
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

        //存储显示 的资源信息
        mp.put("resObj", entity);

        mp.put("audiosuffix",UtilTool._MP3_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("videosuffix",UtilTool._VIEW_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));
        mp.put("imagesuffix",UtilTool._IMG_SUFFIX_TYPE_REGULAR.replaceAll("\\(|\\$|\\||\\)",""));


        return new ModelAndView("/resource/base/detail", mp);
    }

    /**
     * 更新某个字段
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdateColumn",method = RequestMethod.POST)
    public void doUpdateColumn(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ResourceInfo resEntity=this.getParameter(request,ResourceInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        //验证
        if(resEntity==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        if(resEntity.getResid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //验证是否存在resid   只查一条
        PageResult presult=new PageResult();
        presult.setPageNo(1);presult.setPageSize(1);

        ResourceInfo validateRes=new ResourceInfo();
        validateRes.setResid(resEntity.getResid());
        List<ResourceInfo> resList=this.resourceManager.getList(validateRes,presult);
        if(resList==null||resList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //验证当前登陆的用户权限
        if(this.logined(request).getUserid().intValue()!=resList.get(0).getUserid().intValue()){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_SERVICE_RIGHT"));    //没有操作权限
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
            mc.setData("分享了资源 <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+resEntity.getResid()+"\">#ETIANTIAN_SPLIT#</a>! ");
            mc.setType(1);
            objList=this.resourceManager.getMyInfoCloudSaveSql(mc,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }
        //执行修改
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
     * 根据年级，登陆人，得到教授的学科
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
        List<Map<String,Object>> mapList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,Integer.parseInt(grade));
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
     * 删除资源(逻辑删除)
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
        //验证是否存在该资源
        List<ResourceInfo> resList=this.resourceManager.getList(entity,null);
        if(resList==null||resList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        ResourceInfo res=resList.get(0);
        //只能删除自建的资源
        if(res.getUserid().intValue()!=this.logined(request).getUserid()){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_SERVICE_RIGHT"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        if(res.getResdegree()!=null){  //共享，标准库 拒绝操作
            if(res.getResdegree().intValue()==1||res.getResdegree().intValue()==2){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("JU_JUE_OPERATE"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
        }
        //修改状态,同时写入操作记录
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        StringBuilder sqlbuilder=new StringBuilder();
        entity.setResstatus(3);//已删除
        List<Object> objList=this.resourceManager.getUpdateSql(entity,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //操作记录
        sqlbuilder=new StringBuilder();
        objList=this.resourceManager.getAddOperateLog(this.logined(request).getRef(),"rs_resource_info"
                ,entity.getResid().toString(),"","","UPDATE",this.logined(request).getRealname()+"(逻辑)删除了资源"+entity.getResid(),sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }

        //添加资源系统通知记录
        MyInfoCloudInfo mc=new MyInfoCloudInfo();
        mc.setTargetid(res.getResid());
        mc.setUserid(Long.parseLong(res.getUserid().toString()));
//                if(commentinfo.getAnonymous()==null&&commentinfo.getAnonymous()!=1)
//                    mc.setData(this.logined(request).getRealname() + " 评论了你的资源 <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+commentinfo.getCommentobjectid()+"\">#ETIANTIAN_SPLIT#</a>");
//                else
        mc.setData("我的资源 <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+res.getResid()+"\">#ETIANTIAN_SPLIT#</a> 被管理员删除!");
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
     * 根据ResId得到知识点
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
     * 进入资源排行榜。
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
            path=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH");//本地
        else
            path=UtilTool.utilproperty.getProperty("RESOURCE_CLOUD_SERVER_PATH");//云端
        //验证该资源是否存在
        ResourceInfo rsInfo=new ResourceInfo();
        rsInfo.setResid(Long.parseLong(resid.toString()));
        List<ResourceInfo> rsList=resourceManager.getList(rsInfo,null);
        if(rsList!=null&&rsList.size()>0){
            path+="/"+UtilTool.getResourceFileUrl(rsList.get(0).getResid().toString(),rsList.get(0).getFilesuffixname());//完整路径
            if(imgw!=null&&imgh!=null&&imgw.trim().length()>0&&imgh.trim().length()>0){
                //生成新图
                writeImage(response, ImgResize(path, Integer.parseInt(imgw), Integer.parseInt(imgh)));
            }
        }
    }

    /**
     * 进入我的资源排行榜更多的页面
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
     * 得到我的资源排行榜数据
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
     * 得到校内共享的排行
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getXNShareByPage",method=RequestMethod.POST)
    public void getXNShareByPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        PageResult presult=this.getPageResultParameter(request);
        UserModelTotalScoreInfo userMTScore=new UserModelTotalScoreInfo();
        userMTScore.setModelId(3);//3表示是资源系统的积分
        List<UserModelTotalScoreInfo> mapList=this.userModelTotalScoreManager.getList(userMTScore,presult);
        jsonEntity.setType("success");
        presult.setList(mapList);
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * 进入最热浏览页面
     * @param request
     * @param mp
     * @throws Exception
     */
    @RequestMapping(params="m=toHotClicksResTypeToMore",method=RequestMethod.GET)
    public ModelAndView getClicksHotResType(HttpServletRequest request,ModelMap mp) throws Exception{
        return new ModelAndView("/resource/more/hotClicksResRankToMore");
    }

    /**
     * ajax查询，根据类型得到浏览排行榜  type=week:以周为单位，向前推7天，type=month：以本月第一天为单位到现在
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
        //得到当前登陆用户的主授学科,如果没有则得到辅授学科，如果没有学科信息，则得到该学校的第一个学科
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
        //得到当前老师教授的最大学科，最大年级
        List<Map<String,Object>> mapGrdList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,null);
        if(mapGrdList==null||mapGrdList.size()<1){
            //得到这个学校的最高年级
            loginGrdid=this.gradeManager.getList(null, null).get(0).getGradeid();
        }else
            loginGrdid=Integer.parseInt(mapGrdList.get(0).get("GRADE_ID").toString());

        ResourceInfo rs=new ResourceInfo();
        rs.setSubjectvalues(subvalue+"");
        rs.setGradevalues(loginGrdid+"");

        // 获取本日资源更新条数
        Calendar currentDate = new GregorianCalendar();
        if(type.trim().toLowerCase().equals("week")){
            rs.setType("1");
        }else if(type.trim().toLowerCase().equals("month")){
            // 获取本周资源更新条数
            rs.setType("2");
        }else{
            jsonEntity.setMsg(UtilTool.utilproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }

        //开始查询
        List<ResourceInfo> resList=this.resourceManager.getResourceIdxViewRankPage(rs,presult);
        //得到后，开始得到其它信息
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
     * 进入最热下载页面
     * @param request
     * @param mp
     * @throws Exception
     */
    @RequestMapping(params="m=toHotDownResTypeToMore",method=RequestMethod.GET)
    public ModelAndView getDownHotResType(HttpServletRequest request,ModelMap mp) throws Exception{
        return new ModelAndView("/resource/more/hotDownResRankToMore");
    }
    /**
     * ajax查询，根据类型得到浏览排行榜  type=week:以周为单位，向前推7天，type=month：以本月第一天为单位到现在
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajx_hotDownRes",method=RequestMethod.POST)
    public void getHotDownResByType(HttpServletRequest request,HttpServletResponse response) throws Exception{
        PageResult presult=this.getPageResultParameter(request);
        // String type=request.getParameter("type");
        JsonEntity jsonEntity=new JsonEntity()  ;
        //得到当前登陆用户的主授学科,如果没有则得到辅授学科，如果没有学科信息，则得到该学校的第一个学科
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
        //得到当前老师教授的最大学科，最大年级
        List<Map<String,Object>> mapGrdList=this.classUserManager.getClassUserTeacherBy(this.logined(request).getRef(),"任课老师",null,null);
        if(mapGrdList==null||mapGrdList.size()<1){
            //得到这个学校的最高年级
            loginGrdid=this.gradeManager.getList(null, null).get(0).getGradeid();
        }else
            loginGrdid=Integer.parseInt(mapGrdList.get(0).get("GRADE_ID").toString());

        ResourceInfo rs=new ResourceInfo();
        rs.setSubjectvalues(subvalue+"");
        rs.setGradevalues(loginGrdid+"");
        rs.setType("3");//1：周  2：月  3：最热下载
        //开始查询
        List<ResourceInfo> resList=this.resourceManager.getResourceIdxDownRankPage(rs, presult);
        //得到后，开始得到其它信息
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
     *得到关于我的当前动态
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
     *得到他人动态AJAX
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
     * 验证资源文件是否存在
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
        //得到资源信息
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
            jsonEntity.setMsg("数据错误！没有后缀名!");
            response.getWriter().println(jsonEntity.toJSON());
            return;
        }
        String filepath=UtilTool.getResourceFileUrl(resObj.getResid().toString(),resObj.getFilesuffixname());
        String filetype=UtilTool.getConvertResourseType(filepath);
        if(filetype!=null&&filetype.equals("doc")){
            //如果是
            String destPath=UtilTool.getResourceLocation(request,Long.parseLong(resid),2)+"/"+UtilTool.getConvertPath(resid, resObj.getFilesuffixname());
            String sourcepath=UtilTool.getResourceLocation(request,Long.parseLong(resid),2)+"/"+UtilTool.getResourceFileUrl(resid,resObj.getFilesuffixname());
            //如果文件存在或者转换过的存在
            if(new File(sourcepath).exists()){
                if(!new File(destPath).exists()){
                    boolean issuccess=UtilTool.Office2Swf(request,resid, resObj.getFilesuffixname());
                    if(issuccess)jsonEntity.setType("success");
                    else jsonEntity.setMsg(filetype+"转换失败!");
                }else jsonEntity.setType("success");
            }else jsonEntity.setMsg("没有源文件!");
        }else
            jsonEntity.setMsg("该资源类型不是文档类型，无法进行转换!");
        response.getWriter().println(jsonEntity.toJSON());
    }

}

package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.ClassUser;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.TpGroupInfo;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.manager.ClassManager;
import com.school.manager.ClassUserManager;
import com.school.manager.UserManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.IUserManager;
import com.school.manager.inter.teachpaltform.ITpGroupManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.manager.teachpaltform.TpGroupManager;
import com.school.manager.teachpaltform.TpGroupStudentManager;
import com.school.share.*;
import com.school.util.PageResult;
import com.school.utils.EttInterfaceUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专题更新测试，及用户，班级等数据更新ETT
 * Created by zhengzhou on 14-7-10.
 */
@Controller
@RequestMapping(value = "/synTest")
public class TestController extends BaseController<String>{
    /**
     * 更新专题元素
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateCourseElement",method=RequestMethod.GET)
    @Transactional
    public void updateCourseElement(HttpServletRequest request,HttpServletResponse response){
        new UpdateCourse(request.getSession().getServletContext()).updateCourseElement();
    }

    /**
     * 更新专题本身
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateCourseSelf",method=RequestMethod.GET)
    @Transactional
    public void updateCourseSelf(HttpServletRequest request,HttpServletResponse response){
        new UpdateCourse(request.getSession().getServletContext()).updateCourseSelf();
    }
    /**
     * 更新资源本身
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateResourceSelf",method=RequestMethod.GET)
    @Transactional
    public void updateResourceSelf(HttpServletRequest request,HttpServletResponse response){
        new UpdateCourse(request.getSession().getServletContext()).updateResourceSelf();
    }

    @RequestMapping(params="m=SynchroEttColumn",method=RequestMethod.GET)
    @Transactional
    public void SynchroEttColumn(HttpServletRequest request,HttpServletResponse response){
        new SynchroEttColumns().run();
    }

    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateCloudMyInfo",method={RequestMethod.GET, RequestMethod.POST})
    @Transactional
    public void updateCloudMyInfo(HttpServletRequest request,HttpServletResponse response){
        new UpdateRsMyInfoData(request.getSession().getServletContext()).run();
    }

    /**
     * 测试,最热资源同步
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateRsHotRank",method={RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public void UpdateRsHotRank(HttpServletRequest request,HttpServletResponse response){
        new UpdateHotResData(request.getSession().getServletContext()).run();
    }


    /**
     * 测试,分校信息同步
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateSchool",method={RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public void UpdateSchool(HttpServletRequest request,HttpServletResponse response){
        new UpdateSchool(request.getSession().getServletContext()).run();
    }
    /**
     * 测试,资源数量上行
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateResNum",method={RequestMethod.GET,RequestMethod.POST})
    public void updateResNum(HttpServletRequest request,HttpServletResponse response){
        new UpdateResNum(request.getSession().getServletContext()).run();
    }
    /**
     * 测试,资源数量上行
     * @param request
     * @param response
     */
    @RequestMapping(params="m=synchroResNum",method={RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public void synchroResNum(HttpServletRequest request,HttpServletResponse response){
        new ShareResNum(request.getSession().getServletContext()).run();
    }


    /**
     * 测试,分校tkw wv 排行
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroSchoolScoreRank",method={RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public void SynchroSchoolScoreRank(HttpServletRequest request,HttpServletResponse response){
        new UpdateSchoolScoreRank(request.getSession().getServletContext()).run();
    }
    /**
     * 测试,分校 排行
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroUserModelTotalScore",method={RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public void SynchroUserModelTotalScore(HttpServletRequest request,HttpServletResponse response){
        new UpdateUserModelTotalScore(request.getSession().getServletContext()).run();
    }
    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroCourse",method={RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public void SynchroCourse(HttpServletRequest request,HttpServletResponse response){
        new ShareCourse(request.getSession().getServletContext()).run();

    }
    /**
     * 测试,同步资源
     * @param request
     * @param response
     */
    @RequestMapping(params="m=ShareResource",method={RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public void SynchroResource(HttpServletRequest request,HttpServletResponse response){
        new ShareResource(request.getSession().getServletContext()).run();

    }
    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateCourse",method=RequestMethod.GET)
    @Transactional
    public void updateCourse(HttpServletRequest request,HttpServletResponse response){
        new UpdateCourse(request.getSession().getServletContext()).run();

    }

    /**
     * 测试，同步版本
     *
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroTeachVersion",method=RequestMethod.GET)
    @Transactional
    public void synchroTeachVersion(HttpServletRequest request,HttpServletResponse response){
        new ShareTeachVersion(request.getSession().getServletContext()).run();
    }
    /**
     * 测试，同步教材
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroTeachingMaterial",method=RequestMethod.GET)
    @Transactional
    public void synchroTeachingMaterial(HttpServletRequest request,HttpServletResponse response){
        new ShareTeachingMaterial(request.getSession().getServletContext()).run();
    }
    @RequestMapping(params="m=SynchroResource",method=RequestMethod.GET)
    @Transactional
        public void synchroResource(HttpServletRequest request,HttpServletResponse response){
        new ShareResource(request.getSession().getServletContext()).run();
    }

    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroOperate",method=RequestMethod.GET)
    @Transactional
    public void SynchroOperate(HttpServletRequest request,HttpServletResponse response){
        new ShareTpOperate(request.getSession().getServletContext()).run();
    }

    /**
     * 更新用户等数据入ETT表
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroDataToEtt")
    @Transactional
    public void SynchroDataToEtt(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String dcschoolid=request.getParameter("dcschoolid");
        //只处理>50000的分校
        if(dcschoolid==null||dcschoolid.trim().length()<1||Integer.parseInt(dcschoolid.trim())<50000){
            response.getWriter().println("<script type='text/javascript'>alert('参数dcschoolid不能为空!');</script>");
            return;
        }
        Integer schoolid=Integer.parseInt(dcschoolid.trim());
        //用户表同步
        UserInfo tmpUser=new UserInfo();
        tmpUser.setStateid(0);
        tmpUser.setDcschoolid(schoolid);
        IUserManager userManager=this.getManager(UserManager.class);
        PageResult presult=new PageResult();
        presult.setPageSize(100);
        presult.setPageNo(0);
        while(true){
            presult.setPageNo(presult.getPageNo()+1);
            List<UserInfo> uList=userManager.getList(tmpUser,presult);
            if(uList==null||uList.size()<1)break;
            for(UserInfo u:uList){
            }
            //开始同步
            if(EttInterfaceUserUtil.delUserBase(uList))
                EttInterfaceUserUtil.addUserBase(uList);
        }
        ///////////////////////班级，班级用户//////////////////////////
        //班级表同步
        ClassInfo clsEntity=new ClassInfo();
        clsEntity.setDcschoolid(schoolid);
        IClassManager classManager=this.getManager(ClassManager.class);
        //班级用户同步
        IClassUserManager classUserManager=this.getManager(ClassUserManager.class);
        presult.setPageNo(0);
        while(true){
            presult.setPageNo(presult.getPageNo()+1);
            List<ClassInfo> clsList=classManager.getList(clsEntity,presult);
            if(clsList==null||clsList.size()<1)break;
            for(ClassInfo cls:clsList){
                if(EttInterfaceUserUtil.delClassBase(cls))
                    if(EttInterfaceUserUtil.addClassBase(cls)){
                        //查班级人员
                        ClassUser cu=new ClassUser();
                        cu.getUserinfo().setStateid(0);
                        cu.setClassid(cls.getClassid());
                        List<ClassUser> cuList=classUserManager.getList(cu,null);

                        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
                        if(cuList!=null&&cuList.size()>0){
                            for (ClassUser cuTmpe:cuList){
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
                        }
                        EttInterfaceUserUtil.OperateClassUser(mapList, cls.getClassid(),cls.getDcschoolid());
                    }
            }
        }

        //////////更新小组学生人员信息
//
//        ITpGroupManager groupManager=this.getManager(TpGroupManager.class);
//        ITpGroupStudentManager groupStudentManager=this.getManager(TpGroupStudentManager.class);
//        //更新小组信息
//        TpGroupInfo tmpGroup=new TpGroupInfo();
//        presult.setPageNo(0);
//        while(true){
//            presult.setPageNo(presult.getPageNo()+1);
//            List<TpGroupInfo> groupList=groupManager.getList(tmpGroup,presult);
//            if(groupList==null||groupList.size()<1)break;
//            for(TpGroupInfo g:groupList){
//                if(g!=null){
//                    Integer clsid=g.getClassid();
//                    ClassInfo tmpCls=new ClassInfo();
//                    tmpCls.setClassid(clsid);
//                    List<ClassInfo> clsList=classManager.getList(tmpCls,null);
//                    if(clsList==null||clsList.size()<1)continue;
//                    Integer schoolid=clsList.get(0).getDcschoolid();
//                    if(EttInterfaceUserUtil.delGroupBase(g,schoolid))
//                        if(EttInterfaceUserUtil.addGroupBase(g,schoolid)){
//                            List<Map<String,Object>> objMapList=null;
//                            //小组人员
//                            TpGroupStudent gs=new TpGroupStudent();
//                            gs.setGroupid(g.getGroupid());
//                            List<TpGroupStudent> tgsList=groupStudentManager.getList(gs,null);
//                            if(tgsList!=null&&tgsList.size()>0){
//                                objMapList=new ArrayList<Map<String, Object>>();
//                                for(TpGroupStudent tgs:tgsList){
//                                    if(tgs!=null){
//                                        //必带 userId，userType 的key
//                                        Map<String,Object> tmpMap=new HashMap<String, Object>();
//                                        tmpMap.put("userId",tgs.getUserid());
//                                        tmpMap.put("userType",3);   //3:学生
//                                        objMapList.add(tmpMap);
//                                    }
//                                }
//                            }
//                            EttInterfaceUserUtil.OperateGroupUser(objMapList,clsid,gs.getGroupid(),schoolid);
//                    }
//                }
//            }
//        }
    }
}

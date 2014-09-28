package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.entity.teachpaltform.*;
import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.teachpaltform.*;
import com.school.utils.EttInterfaceUserUtil;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value="/group")
public class GroupController extends BaseController<TpGroupInfo>{
    private ITermManager termManager;
    private ITpGroupManager tpGroupManager;
    private IClassUserManager classUserManager;
    private ITpVirtualClassManager tpVirtualClassManager;
    private ITrusteeShipClassManager trusteeShipClassManager;
    private ITpGroupStudentManager tpGroupStudentManager;
    private ITpTaskAllotManager tpTaskAllotManager;
    private IGradeManager gradeManager;
    private IClassManager classManager;
    private ISubjectManager subjectManager;
    public GroupController(){
        this.termManager=this.getManager(TermManager.class);
        this.tpGroupManager=this.getManager(TpGroupManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.tpVirtualClassManager=this.getManager(TpVirtualClassManager.class);
        this.trusteeShipClassManager=this.getManager(TrusteeShipClassManager.class);
        this.tpGroupStudentManager=this.getManager(TpGroupStudentManager.class);
        this.tpTaskAllotManager=this.getManager(TpTaskAllotManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.classManager=this.getManager(ClassManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
    }

	/**
	 * 进入小组管理页面
	 * @param request
	 * @param mp
	 * @return
	 * @throws Exception
     */
	@RequestMapping(params="m=toGroupManager",method=RequestMethod.GET)
	public ModelAndView toGroupManager(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity jeEntity = new JsonEntity();
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        String classid=request.getParameter("classid");

        TermInfo termInfo=null;
        if(termid!=null){
            TermInfo t=new TermInfo();
            t.setRef(termid);
            List<TermInfo>termList=this.termManager.getList(t,null);
            if(termList==null||termList.size()<1){
                jeEntity.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
                response.getWriter().print(jeEntity.getAlertMsgAndBack());
                return null;
            }
            termInfo=termList.get(0);
        }else
            termInfo=this.termManager.getMaxIdTerm(false);

        if(termInfo==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
            response.getWriter().print(jeEntity.getAlertMsgAndBack());
            return null;
        }
        List<ClassUser>tmpList=new ArrayList<ClassUser>();
        List<ClassUser>clsList=null;
        String gradeValue=null;
        if(gradeid!=null&&gradeid.trim().length()>0){
            GradeInfo gradeInfo=new GradeInfo();
            gradeInfo.setGradeid(Integer.parseInt(gradeid));
            List<GradeInfo>gList=this.gradeManager.getList(gradeInfo,null);
            if(gList!=null&&gList.size()>0)
                gradeValue=gList.get(0).getGradevalue();
        }

        if(classid!=null&&classid.trim().length()>0){
            ClassUser sel=new ClassUser();
            sel.setClassid(Integer.parseInt(classid));
            sel.setNorelationtype("学生");
            if(subjectid!=null&&subjectid.trim().length()>0)
                sel.setSubjectid(Integer.parseInt(subjectid));
            if(gradeValue!=null)
                sel.setClassgrade(gradeValue);
            sel.setYear(termInfo.getYear());
            clsList=this.classUserManager.getList(sel,null);
            if(clsList!=null&&clsList.size()>0)
                tmpList.add(clsList.get(0));
        }

        //过滤只为班主任的班级
        ClassUser bzr=new ClassUser();
        bzr.setRelationtype("班主任");
        bzr.setYear(termInfo.getYear());
        bzr.setUserid(this.logined(request).getRef());
        clsList=this.classUserManager.getList(bzr,null);
        if(clsList!=null&&clsList.size()>0){
            for(ClassUser cu:clsList){
                ClassUser selTeacher=new ClassUser();
                selTeacher.setUserid(this.logined(request).getRef());
                selTeacher.setRelationtype("任课老师");
                selTeacher.setYear(termInfo.getYear());
                selTeacher.setClassid(cu.getClassid());
                List<ClassUser>teaList=this.classUserManager.getList(selTeacher,null);
                if(teaList==null||teaList.size()<1)
                    tmpList.add(cu);
            }
        }

        ClassUser c = new ClassUser();
        //当前学期、学科、年级下的授课班级
        if(subjectid!=null&&subjectid.trim().length()>0)
            c.setSubjectid(Integer.parseInt(subjectid));
        if(gradeValue!=null)
            c.setClassgrade(gradeValue);
        c.setUserid(this.logined(request).getRef());
        c.setRelationtype("任课老师");
        c.setYear(termInfo.getYear());
        clsList=this.classUserManager.getList(c,null);
        if(clsList!=null&&clsList.size()>0){
            for(ClassUser classUser:clsList){
                boolean ishas=false;
                for(ClassUser tmp:tmpList){
                    if(tmp.getClassid().toString().equals(classUser.getClassid().toString()))
                        ishas=true;
                }
                if(!ishas)
                    tmpList.add(classUser);
            }
        }
        mp.put("classes", tmpList);

        if(tmpList.size()<1){
            jeEntity.setMsg("您当前没有分配班级!");
            response.getWriter().print(jeEntity.getAlertMsgAndBack());
            return null;
        }


        //身份
        Integer uType=this.classUserManager.isTeachingBanZhuRen(this.logined(request).getRef(), tmpList.get(0).getClassid(),null,null);
        mp.put("userType",uType);



        PageResult pr = new PageResult();
        pr.setOrderBy("SEMESTER_BEGIN_DATE");
        List<TermInfo> termList = this.termManager.getList(null, pr);
        mp.put("termList", termList);
        mp.put("currtTerm", this.termManager.getMaxIdTerm(false));
        mp.put("selTerm",termInfo);

        //获取首页年级学科
        List<GradeInfo> gradeList = this.gradeManager.getTchGradeList(this.logined(request).getUserid(), termInfo.getYear());
        List<Map<String,Object>> gradeSubjectList = new ArrayList<Map<String, Object>>();

        if(gradeList!=null&&gradeList.size()>0){
            //当前学期、学科、年级下的授课班级
            for(int j=0;j<gradeList.size();j++){
                ClassUser cu = new ClassUser();
                cu.setClassgrade(gradeList.get(j).getGradevalue());
                cu.setUserid(this.logined(request).getRef());
                cu.setRelationtype("任课老师");
                //cu.setSubjectid(subuserList.get(i).getSubjectid());
                cu.setYear(termInfo.getYear());
                List<ClassUser>classList=this.classUserManager.getList(cu,null);
                List<SubjectInfo>subjectInfoList=new ArrayList<SubjectInfo>();
                if(classList!=null&&classList.size()>0){
                    for(ClassUser classUser :classList){
                        SubjectInfo s=new SubjectInfo();
                        s.setSubjectid(classUser.getSubjectid());
                        s.setSubjectname(classUser.getSubjectname());
                        if(!subjectInfoList.contains(s))
                            subjectInfoList.add(s);
                    }
                    if(subjectInfoList.size()>0){
                        for(SubjectInfo subjectInfo:subjectInfoList){
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("gradeid",gradeList.get(j).getGradeid());
                            map.put("gradevalue",gradeList.get(j).getGradevalue());
                            map.put("subjectid",subjectInfo.getSubjectid());
                            map.put("subjectname",subjectInfo.getSubjectname());
                            if(!gradeSubjectList.contains(map))
                                gradeSubjectList.add(map);
                        }
                    }
                }
            }
        }
        mp.put("gradeSubjectList",gradeSubjectList);

        Map<String,Object>objectMap=null;
        if(gradeSubjectList.size()>0)
            objectMap=gradeSubjectList.get(0);
        if(subjectid!=null&&subjectid.trim().length()>0&&gradeid!=null&&gradeid.trim().length()>0){
            SubjectInfo s=new SubjectInfo();
            s.setSubjectid(Integer.parseInt(subjectid));
            List<SubjectInfo>subList=this.subjectManager.getList(s,null);

            GradeInfo gradeInfo=new GradeInfo();
            gradeInfo.setGradeid(Integer.parseInt(gradeid));
            List<GradeInfo>gList=this.gradeManager.getList(gradeInfo,null);

            if(subList!=null&&gList!=null){
                Map<String,Object>subGradeMap=new HashMap<String, Object>();
                subGradeMap.put("subjectid",subList.get(0).getSubjectid());
                subGradeMap.put("gradeid",gList.get(0).getGradeid());
                subGradeMap.put("subjectname",subList.get(0).getSubjectname());
                subGradeMap.put("gradevalue",gList.get(0).getGradevalue());
                objectMap=subGradeMap;
            }
        }
        mp.put("subGradeInfo",objectMap);


        return new ModelAndView("/teachpaltform/classmanager/groupManager",mp);
	}


    /**
     * 验证当前教师身份
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="checkRelationType",method=RequestMethod.POST)
    public void checkRelationType(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        je.setType("success");

        String classid=request.getParameter("classid");
        if(classid==null||classid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        Integer teacher=0,bzr=0;
        ClassUser cu=new ClassUser();
        cu.setRelationtype("任课老师");
        cu.setClassid(Integer.parseInt(classid));
        cu.setUserid(this.logined(request).getRef());
        List<ClassUser>cuList=this.classUserManager.getList(cu,null);
        if(cuList!=null&&cuList.size()>0)
            teacher=1;
        cu.setRelationtype("班主任");
        List<ClassUser>bzrList=this.classUserManager.getList(cu,null);
        if(bzrList!=null&&bzrList.size()>0)
            bzr=2;
        je.getObjList().add(teacher+bzr);
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params="m=getClsStudent",method=RequestMethod.POST)
    public void getClsStudent(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        je.setType("success");

        String classid=request.getParameter("classid");
        if(classid==null||classid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        ClassUser cu=new ClassUser();
        cu.setClassid(Integer.parseInt(classid));
        cu.setRelationtype("学生");
        List<ClassUser>stuList=this.classUserManager.getList(cu,null);
        je.setObjList(stuList);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取不在小组中的学生列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getNoGroupStudents",method=RequestMethod.POST)
    public void getNoGroupStudentList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String classid=request.getParameter("classId");
        String classtype=request.getParameter("classType");
        String subjectid = request.getParameter("subjectid");
        if(classid==null||classid.trim().length()<1
                || classtype==null||classtype.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Map<String,Object>>stuList=this.tpGroupStudentManager
                .getNoGroupStudentList(Integer.parseInt(classid),
                        Integer.parseInt(classtype),
                        this.logined(request).getUserid(),
                        subjectid==null||subjectid.trim().length()<1?null:Integer.parseInt(subjectid),
                        this.termManager.getMaxIdTerm(false).getRef());
        je.getObjList().add(stuList);
        je.getObjList().add(this.classUserManager.isTeachingBanZhuRen(this.logined(request).getRef(), Integer.parseInt(classid),null,null));
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
	 * 获取班级小组
	 * @param request
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(params="m=getClassGroups",method=RequestMethod.POST)
	public void getClassGroups(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je =new JsonEntity();
        TpGroupInfo tg = this.getParameter(request, TpGroupInfo.class);
        String termid=request.getParameter("termid");

        TermInfo termInfo=null;
        if(termid!=null){
            TermInfo t=new TermInfo();
            t.setRef(termid);
            List<TermInfo>termList=this.termManager.getList(t,null);
            if(termList==null||termList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return;
            }
            termInfo=termList.get(0);
        }else
            termInfo=this.termManager.getMaxIdTerm(false);

        if(termInfo==null){
            je.setMsg(UtilTool.msgproperty.getProperty("系统未获取到学期信息!"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return;
        }


        if(tg==null
                ||tg.getSubjectid()==null
                ||tg.getClassid()==null
                ||tg.getClasstype()==null
			    ||termInfo.getRef()==null
                    ||termInfo.getRef().trim().length()<1){
            je.setType("error");
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		tg.setTermid(termInfo.getRef());
		List<TpGroupInfo>groupList=this.tpGroupManager.getGroupBySubject(tg);
        //查询小组任务完成率
        int completenum=0;
        int totalnum=0;
        if(groupList!=null&&groupList.size()>0){
            for(int i = 0;i<groupList.size();i++){
                //首先查询没个小组的所有任务
                List<TpTaskAllotInfo> taskList=this.tpTaskAllotManager.getTaskByGroup(groupList.get(i).getGroupid());
                //然后根据任务查询每个任务的完成数和接收任务数
                completenum=0;
                totalnum=0;
                if(taskList!=null&&taskList.size()>0){
                    for(int j = 0;j<taskList.size();j++){
                        //完成数
                        List<Map<String,Object>>  cnum=this.tpTaskAllotManager.getCompleteNum(groupList.get(i).getGroupid(),taskList.get(j).getTaskid());
                        completenum+=Integer.parseInt(cnum.get(0).get("NUM").toString());
                        //总数
                        List<Map<String,Object>>  tnum=this.tpTaskAllotManager.getNum(groupList.get(i).getGroupid(),taskList.get(j).getTaskid());
                        totalnum+=Integer.parseInt(tnum.get(0).get("NUM").toString());
                    }
                }
                groupList.get(i).setCompletenum(completenum);
                groupList.get(i).setTotalnum(totalnum);
            }
        }
		je.setObjList(groupList);
		je.setType("success"); 
		response.getWriter().append(je.toJSON());
	}
	
	/**
	 * 获取小组成员
	 * @param request
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(params="m=getGroupStudents",method=RequestMethod.POST)
	public void getGroupStudent(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je =new JsonEntity();
		String groupid=request.getParameter("groupid");
		if(groupid==null||groupid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
        PageResult pageResult=new PageResult();
        pageResult.setPageSize(0);
        pageResult.setPageNo(0);
        pageResult.setOrderBy(" s.stu_no,s.stu_name ");

		TpGroupStudent gs=new TpGroupStudent();
		gs.setGroupid(Long.parseLong(groupid));
        gs.setStateid(0);
		List<TpGroupStudent>groupstudentList=this.tpGroupStudentManager.getList(gs,pageResult);
		je.setObjList(groupstudentList);
		je.setType("success"); 
		response.getWriter().append(je.toJSON());
	} 
	  
	/**
	 * 创建小组并添加组员
	 * @param request
	 * @param response 
	 * @throws Exception
     */
	@RequestMapping(params="m=addNewGroup",method=RequestMethod.POST)
	public void doAddGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
        TpGroupInfo tg = this.getParameter(request, TpGroupInfo.class);
		String groupname=request.getParameter("groupname");
		String classid=request.getParameter("classid");
        String subjectid=request.getParameter("subjectid");
        if(tg==null
            ||tg.getClassid() == null
                ||subjectid==null||subjectid.trim().length()<1
                || tg.getClasstype() == null
                || tg.getGroupname()==null
                || tg.getGroupname().trim().length()==0) {
            je.setType("error");
            je.setMsg("参数错误");
            response.getWriter().print(je.toJSON());
            return ;
        }
		//添加小组
		Long groupid=this.tpGroupManager.getNextId(true);
		TermInfo term = termManager.getMaxIdTerm(false);
		if(term==null)
			term = termManager.getMaxIdTerm(true);
		tg.setTermid(term.getRef());
		tg.setCuserid(this.logined(request).getUserid());
		tg.setGroupid(groupid);
        tg.setSubjectid(Integer.parseInt(subjectid));
		
		if(this.tpGroupManager.checkGroupName(tg)){
			je.setMsg("error");
            je.setType("errorname");
			response.getWriter().print(je.toJSON());
			return;
		}
		if(this.tpGroupManager.doSave(tg)){
			je.setType("success");
            if(!addGroupToEtt(tg.getGroupid()))
                System.out.println("向ETT添加小组请求，失败!");
            else
                System.out.println("向ETT添加小组请求，成功!");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
		}
		response.getWriter().print(je.toJSON());
	}


	/**
	 * ajax查询小组信息
	 * @param request
	 * @param response
	 * @throws Exception
	@RequestMapping(params="m=getClassCondition",method=RequestMethod.POST)
	public void getAjaxClassGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String termid=request.getParameter("termid");
		if(termid==null||termid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		UserInfo u=this.logined(request);
		List<Map<String, Object>>groupclsList=this.groupManager.getClassGroupByTeacher(u.getRef(), termid);
		je.setObjList(groupclsList);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * 添加学生到小组
	 * @param request
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(params="m=addStudentsToGroup",method=RequestMethod.POST)
	public void addStudentsToGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je =new JsonEntity();                                
		String groupid=request.getParameter("groupid");
		String useridstr=request.getParameter("stusId");
		if(groupid==null||groupid.trim().length()<1
				||useridstr==null||useridstr.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>sqlListArray=new ArrayList<String>();
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		List<Object>objList=null;
		StringBuilder sql=null;
		String[]userArray=useridstr.split(",");
		if(userArray.length>0){
			for (String userid : userArray) {
				TpGroupStudent gs=new TpGroupStudent();
				gs.setUserid(Integer.parseInt(userid));
				gs.setGroupid(Long.parseLong(groupid));
                gs.setIsleader(2);
				sql=new StringBuilder();
				objList=this.tpGroupStudentManager.getSaveSql(gs, sql);
				if(sql!=null&&objList!=null){
					sqlListArray.add(sql.toString());
					objListArray.add(objList);
				}
			}
		}
		if(sqlListArray.size()>0&&objListArray.size()>0){
			boolean bo=this.tpGroupStudentManager.doExcetueArrayProc(sqlListArray, objListArray);
			if(bo){
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
                TpGroupInfo tg=new TpGroupInfo();
                tg.setGroupid(Long.parseLong(groupid.trim()));
                List<TpGroupInfo> tgList=this.tpGroupManager.getList(tg,null);
                if(tgList!=null&&tgList.size()>0){
                    Integer clsId=tgList.get(0).getClassid();
                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(clsId);
                    List<ClassInfo> clsList=this.classManager.getList(cls,null);
                    if(clsList!=null&&clsList.size()>0){
                        Integer dcschoolid=clsList.get(0).getDcschoolid();
                        if(!updateEttGroupUser(clsId,Long.parseLong(groupid.trim()),dcschoolid)){
                            System.out.println("向ETT更新小组成员请求，失败!");
                        }else
                            System.out.println("向ETT更新小组成员请求，成功!");
                    }
                }
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
		}
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * 删除小组成员
	 * @param request
	 * @throws Exception
     */
	@RequestMapping(params="m=delGroupStudent",method=RequestMethod.POST)
	public void delGroupStudents(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String ref=request.getParameter("ref");
		if(ref==null||ref.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}

        TpGroupStudent gs=new TpGroupStudent();
        gs.setRef(Integer.parseInt(ref));
        List<TpGroupStudent> tgsList=this.tpGroupStudentManager.getList(gs,null);
        if(tgsList==null||tgsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
		if(this.tpGroupStudentManager.doDelete(gs)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
            //向ETT发送同步数据请求
            TpGroupInfo tg=new TpGroupInfo();
            tg.setGroupid(tgsList.get(0).getGroupid());
            List<TpGroupInfo> tgList=this.tpGroupManager.getList(tg,null);
            if(tgList!=null&&tgList.size()>0){
                Integer clsId=tgList.get(0).getClassid();
                ClassInfo cls=new ClassInfo();
                cls.setClassid(clsId);
                List<ClassInfo> clsList=this.classManager.getList(cls,null);
                if(clsList!=null&&clsList.size()>0){
                    Integer dcschoolid=clsList.get(0).getDcschoolid();
                    if(!updateEttGroupUser(clsId,tgsList.get(0).getGroupid(),dcschoolid)){
                        System.out.println("向ETT更新小组成员请求，失败!");
                    }else
                        System.out.println("向ETT更新小组成员请求，成功!");
                }
            }
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
	}
	
	/**
	 * 编辑小组名称
     */
	@RequestMapping(params="m=editGroupName",method=RequestMethod.POST)
	public void editGroupName(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity(); 
		String groupid=request.getParameter("groupid");
		String groupname=request.getParameter("groupname");
		if(groupid==null||groupid.trim().length()<1
				||groupname==null||groupname.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		TpGroupInfo g=new TpGroupInfo();
		g.setGroupid(Long.parseLong(groupid));
		g.setGroupname(groupname);
		if(this.tpGroupManager.doUpdate(g)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * 删除小组并删除组内人员
	 * @param request
     */
	@RequestMapping(params="m=delGroup",method=RequestMethod.POST)
	public void deleteGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String groupid=request.getParameter("groupid");
		if(groupid==null||groupid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>sqlListArray=new ArrayList<String>();
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		List<Object>objList=null;
		StringBuilder sql=null;
		
		TpGroupInfo g=new TpGroupInfo();
		g.setGroupid(Long.parseLong(groupid));
        List<TpGroupInfo> tgList=this.tpGroupManager.getList(g,null);
        if(tgList==null||tgList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
		sql=new StringBuilder();
		objList=this.tpGroupManager.getDeleteSql(g, sql);
		if(sql!=null&&objList!=null){
			sqlListArray.add(sql.toString());
			objListArray.add(objList);
		}
		TpGroupStudent gs=new TpGroupStudent();
		gs.setGroupid(g.getGroupid());
		sql=new StringBuilder();
		objList=this.tpGroupStudentManager.getDeleteSql(gs, sql);
		if(sql!=null&&objList!=null){
			sqlListArray.add(sql.toString());
			objListArray.add(objList);
		}
		if(sqlListArray.size()>0&&objListArray.size()>0){
			boolean bo=this.tpGroupStudentManager.doExcetueArrayProc(sqlListArray, objListArray);
			if(bo){
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
                //得到学校ID
                Integer clsid=tgList.get(0).getClassid();
                ClassInfo c=new ClassInfo();
                c.setClassid(clsid);
                List<ClassInfo> clsList=this.classManager.getList(c,null);
                if(clsList!=null&&clsList.size()>0){
                    Integer dcschoolid=clsList.get(0).getDcschoolid();
                    if(!updateEttGroupUser(clsid,gs.getGroupid(),dcschoolid)){
                        System.out.println("发送ETT删除小组人员命令失败!");
                    }else
                        System.out.println("发送ETT删除小组人员命令成功!");

                    if(!EttInterfaceUserUtil.delGroupBase(tgList.get(0),dcschoolid)){
                        System.out.println("发送ETT删除小组命令失败!");
                    }else
                        System.out.println("发送ETT删除小组命令成功!");
                }
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
		}
		response.getWriter().print(je.toJSON());
	}

    /**
     * 更新Ett小组与人员信息
     * @param clsid 班级ID
     * @param groupid 班级内小组ID
     * @param dcschoolid 分校ID
     * @return
     */
    private boolean updateEttGroupUser(Integer clsid,Long groupid,Integer dcschoolid){
        if(groupid==null||clsid==null||dcschoolid==null)return false;
        List<Map<String,Object>> objMapList=null;
        TpGroupStudent ts=new TpGroupStudent();
        ts.setGroupid(groupid);
        List<TpGroupStudent> tgsList=this.tpGroupStudentManager.getList(ts,null);
        if(tgsList!=null&&tgsList.size()>0){
            objMapList=new ArrayList<Map<String, Object>>();
            for(TpGroupStudent tgs:tgsList){
                if(tgs!=null){
                    //必带 userId，userType 的key
                    Map<String,Object> tmpMap=new HashMap<String, Object>();
                    tmpMap.put("userId",tgs.getUserid());
                    tmpMap.put("userType",3);   //3:学生
                    objMapList.add(tmpMap);
                }
            }
        }
        return EttInterfaceUserUtil.OperateGroupUser(objMapList,clsid,groupid,dcschoolid);
    }

	
	/**
	 * 学生调组
	 * @param request
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(params="m=changeGroupStudent",method=RequestMethod.POST)
	public void changeGroupStudent(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je =new JsonEntity();
		String ref=request.getParameter("ref");
		String groupid=request.getParameter("groupid");
        String isleader=request.getParameter("isleader");
		if(ref==null||ref.trim().length()<1
				||groupid==null||groupid.trim().length()<1
                ||isleader==null||isleader.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        TpGroupStudent gsSel=new TpGroupStudent();
        gsSel.setRef(Integer.parseInt(ref));
        List<TpGroupStudent>gsList=this.tpGroupStudentManager.getList(gsSel,null);
        if(gsList==null||gsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //记录操作相关的GroupId集合
        List<Long> operateGroupIdList=new ArrayList<Long>();
        operateGroupIdList.add(gsList.get(0).getGroupid());
        if(isleader.equals("1")){
            //清空GroupId组长
            TpGroupStudent gsUpd= new TpGroupStudent();
            gsUpd.setGroupid(Long.parseLong(groupid));
            gsUpd.setIsleader(2);
            sql=new StringBuilder();
            objList=this.tpGroupStudentManager.getUpdateSql(gsUpd,sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            //记录
            if(!operateGroupIdList.contains(Long.parseLong(groupid)))
                operateGroupIdList.add(Long.parseLong(groupid));
        }

        TpGroupStudent groupStudent=gsList.get(0);
        //组ID相同
        if(groupStudent.getGroupid().toString().equals(groupid)){
            TpGroupStudent gs=new TpGroupStudent();
            gs.setRef(Integer.parseInt(ref));
            gs.setIsleader(Integer.parseInt(isleader));
            sql=new StringBuilder();
            objList=this.tpGroupStudentManager.getUpdateSql(gs,sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }else{ //组ID不同删除关联数据，重新添加，加入小组时间改变
            TpGroupStudent gsDel=new TpGroupStudent();
            gsDel.setRef(Integer.parseInt(ref));
            sql=new StringBuilder();
            objList=this.tpGroupStudentManager.getDeleteSql(gsDel, sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加TpGroupStudent数据
            TpGroupStudent gsAdd=new TpGroupStudent();
            gsAdd.setUserid(groupStudent.getUserid());
            gsAdd.setIsleader(Integer.parseInt(isleader));
            gsAdd.setGroupid(Long.parseLong(groupid));
            sql=new StringBuilder();
            objList=this.tpGroupStudentManager.getSaveSql(gsAdd, sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            //记录
            if(!operateGroupIdList.contains(Long.parseLong(groupid)))
                operateGroupIdList.add(Long.parseLong(groupid));
        }

		if(this.tpGroupStudentManager.doExcetueArrayProc(sqlListArray,objListArray)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");

            if(operateGroupIdList!=null&&operateGroupIdList.size()>0){
                for(Long gid:operateGroupIdList){
                    if(gid!=null&&gid.toString().length()>0){
                        //得到学校ID
                        TpGroupInfo gp=new TpGroupInfo();
                        gp.setGroupid(gid);
                        List<TpGroupInfo> gpList=this.tpGroupManager.getList(gp,null);
                        if(gpList!=null&&gpList.size()>0){
                            Integer clsid=gpList.get(0).getClassid();
                            ClassInfo c=new ClassInfo();
                            c.setClassid(clsid);
                            List<ClassInfo> clsList=this.classManager.getList(c,null);
                            if(clsList!=null&&clsList.size()>0){
                                Integer dcschoolid=clsList.get(0).getDcschoolid();
                                if(!updateEttGroupUser(clsid, gpList.get(0).getGroupid(), dcschoolid)){
                                    System.out.println("发送ETT修改小组人员命令失败!");
                                }else
                                    System.out.println("发送ETT修改小组人员命令成功!");
                            }
                        }
                    }
                }
            }
		}else{ 
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}

	@RequestMapping(params="m=getMyGroup",method=RequestMethod.POST)
	public void getMyGroup(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je =new JsonEntity();
        TpGroupInfo tg = this.getParameter(request, TpGroupInfo.class);
        String subjectid=request.getParameter("subjectid");

		if(tg==null||//tg.getGroupid()==null
                tg.getClassid()==null//||subjectid==null||subjectid.trim().length()<1
                ){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
        TpGroupInfo g=new TpGroupInfo();
        g.setClassid(tg.getClassid());
        g.setUserid(this.logined(request).getUserid());
        if(subjectid!=null&&subjectid.trim().length()>0)
            g.setSubjectid(Integer.parseInt(subjectid));
        List<TpGroupInfo>groupList=this.tpGroupManager.getList(g,null);
        if(groupList!=null&&groupList.size()>0){
            for (TpGroupInfo tpGroupInfo:groupList){
                if(tpGroupInfo!=null){
                    TpGroupStudent gs = new TpGroupStudent();
                    gs.setGroupid(tpGroupInfo.getGroupid());
                    List gsList=this.tpGroupStudentManager.getList(gs,null);
                    tpGroupInfo.setTpgroupstudent(gsList);
                }
            }
        }
        je.setObjList(groupList);
		response.getWriter().print(je.toJSON());
	}



    /**
     * 向ETT发送请求，添加组
     * @param groupid
     * @return
     */
    private boolean addGroupToEtt(Long groupid){
        if(groupid==null)return false;
        TpGroupInfo tg=new TpGroupInfo();
        tg.setGroupid(groupid);
        List<TpGroupInfo> tpGroupList=this.tpGroupManager.getList(tg,null);
        if(tpGroupList!=null&&tpGroupList.size()>0){
            ClassInfo cls=new ClassInfo();
            cls.setClassid(tpGroupList.get(0).getClassid());
            List<ClassInfo> clsList=this.classManager.getList(cls,null);
            if(clsList==null||clsList.size()<1)return false;
            return EttInterfaceUserUtil.addGroupBase(tpGroupList.get(0),clsList.get(0).getDcschoolid());
        }
        return false;
    }


}

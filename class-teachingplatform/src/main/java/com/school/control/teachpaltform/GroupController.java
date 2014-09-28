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
	 * ����С�����ҳ��
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
                jeEntity.setMsg(UtilTool.msgproperty.getProperty("ϵͳδ��ȡ��ѧ����Ϣ!"));
                response.getWriter().print(jeEntity.getAlertMsgAndBack());
                return null;
            }
            termInfo=termList.get(0);
        }else
            termInfo=this.termManager.getMaxIdTerm(false);

        if(termInfo==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("ϵͳδ��ȡ��ѧ����Ϣ!"));
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
            sel.setNorelationtype("ѧ��");
            if(subjectid!=null&&subjectid.trim().length()>0)
                sel.setSubjectid(Integer.parseInt(subjectid));
            if(gradeValue!=null)
                sel.setClassgrade(gradeValue);
            sel.setYear(termInfo.getYear());
            clsList=this.classUserManager.getList(sel,null);
            if(clsList!=null&&clsList.size()>0)
                tmpList.add(clsList.get(0));
        }

        //����ֻΪ�����εİ༶
        ClassUser bzr=new ClassUser();
        bzr.setRelationtype("������");
        bzr.setYear(termInfo.getYear());
        bzr.setUserid(this.logined(request).getRef());
        clsList=this.classUserManager.getList(bzr,null);
        if(clsList!=null&&clsList.size()>0){
            for(ClassUser cu:clsList){
                ClassUser selTeacher=new ClassUser();
                selTeacher.setUserid(this.logined(request).getRef());
                selTeacher.setRelationtype("�ο���ʦ");
                selTeacher.setYear(termInfo.getYear());
                selTeacher.setClassid(cu.getClassid());
                List<ClassUser>teaList=this.classUserManager.getList(selTeacher,null);
                if(teaList==null||teaList.size()<1)
                    tmpList.add(cu);
            }
        }

        ClassUser c = new ClassUser();
        //��ǰѧ�ڡ�ѧ�ơ��꼶�µ��ڿΰ༶
        if(subjectid!=null&&subjectid.trim().length()>0)
            c.setSubjectid(Integer.parseInt(subjectid));
        if(gradeValue!=null)
            c.setClassgrade(gradeValue);
        c.setUserid(this.logined(request).getRef());
        c.setRelationtype("�ο���ʦ");
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
            jeEntity.setMsg("����ǰû�з���༶!");
            response.getWriter().print(jeEntity.getAlertMsgAndBack());
            return null;
        }


        //���
        Integer uType=this.classUserManager.isTeachingBanZhuRen(this.logined(request).getRef(), tmpList.get(0).getClassid(),null,null);
        mp.put("userType",uType);



        PageResult pr = new PageResult();
        pr.setOrderBy("SEMESTER_BEGIN_DATE");
        List<TermInfo> termList = this.termManager.getList(null, pr);
        mp.put("termList", termList);
        mp.put("currtTerm", this.termManager.getMaxIdTerm(false));
        mp.put("selTerm",termInfo);

        //��ȡ��ҳ�꼶ѧ��
        List<GradeInfo> gradeList = this.gradeManager.getTchGradeList(this.logined(request).getUserid(), termInfo.getYear());
        List<Map<String,Object>> gradeSubjectList = new ArrayList<Map<String, Object>>();

        if(gradeList!=null&&gradeList.size()>0){
            //��ǰѧ�ڡ�ѧ�ơ��꼶�µ��ڿΰ༶
            for(int j=0;j<gradeList.size();j++){
                ClassUser cu = new ClassUser();
                cu.setClassgrade(gradeList.get(j).getGradevalue());
                cu.setUserid(this.logined(request).getRef());
                cu.setRelationtype("�ο���ʦ");
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
     * ��֤��ǰ��ʦ���
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
        cu.setRelationtype("�ο���ʦ");
        cu.setClassid(Integer.parseInt(classid));
        cu.setUserid(this.logined(request).getRef());
        List<ClassUser>cuList=this.classUserManager.getList(cu,null);
        if(cuList!=null&&cuList.size()>0)
            teacher=1;
        cu.setRelationtype("������");
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
        cu.setRelationtype("ѧ��");
        List<ClassUser>stuList=this.classUserManager.getList(cu,null);
        je.setObjList(stuList);
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ȡ����С���е�ѧ���б�
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
	 * ��ȡ�༶С��
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
                je.setMsg(UtilTool.msgproperty.getProperty("ϵͳδ��ȡ��ѧ����Ϣ!"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return;
            }
            termInfo=termList.get(0);
        }else
            termInfo=this.termManager.getMaxIdTerm(false);

        if(termInfo==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ϵͳδ��ȡ��ѧ����Ϣ!"));
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
        //��ѯС�����������
        int completenum=0;
        int totalnum=0;
        if(groupList!=null&&groupList.size()>0){
            for(int i = 0;i<groupList.size();i++){
                //���Ȳ�ѯû��С�����������
                List<TpTaskAllotInfo> taskList=this.tpTaskAllotManager.getTaskByGroup(groupList.get(i).getGroupid());
                //Ȼ����������ѯÿ�������������ͽ���������
                completenum=0;
                totalnum=0;
                if(taskList!=null&&taskList.size()>0){
                    for(int j = 0;j<taskList.size();j++){
                        //�����
                        List<Map<String,Object>>  cnum=this.tpTaskAllotManager.getCompleteNum(groupList.get(i).getGroupid(),taskList.get(j).getTaskid());
                        completenum+=Integer.parseInt(cnum.get(0).get("NUM").toString());
                        //����
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
	 * ��ȡС���Ա
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
	 * ����С�鲢�����Ա
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
            je.setMsg("��������");
            response.getWriter().print(je.toJSON());
            return ;
        }
		//���С��
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
                System.out.println("��ETT���С������ʧ��!");
            else
                System.out.println("��ETT���С�����󣬳ɹ�!");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
		}
		response.getWriter().print(je.toJSON());
	}


	/**
	 * ajax��ѯС����Ϣ
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
	 * ���ѧ����С��
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
                            System.out.println("��ETT����С���Ա����ʧ��!");
                        }else
                            System.out.println("��ETT����С���Ա���󣬳ɹ�!");
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
	 * ɾ��С���Ա
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
            //��ETT����ͬ����������
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
                        System.out.println("��ETT����С���Ա����ʧ��!");
                    }else
                        System.out.println("��ETT����С���Ա���󣬳ɹ�!");
                }
            }
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
	}
	
	/**
	 * �༭С������
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
	 * ɾ��С�鲢ɾ��������Ա
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
                //�õ�ѧУID
                Integer clsid=tgList.get(0).getClassid();
                ClassInfo c=new ClassInfo();
                c.setClassid(clsid);
                List<ClassInfo> clsList=this.classManager.getList(c,null);
                if(clsList!=null&&clsList.size()>0){
                    Integer dcschoolid=clsList.get(0).getDcschoolid();
                    if(!updateEttGroupUser(clsid,gs.getGroupid(),dcschoolid)){
                        System.out.println("����ETTɾ��С����Ա����ʧ��!");
                    }else
                        System.out.println("����ETTɾ��С����Ա����ɹ�!");

                    if(!EttInterfaceUserUtil.delGroupBase(tgList.get(0),dcschoolid)){
                        System.out.println("����ETTɾ��С������ʧ��!");
                    }else
                        System.out.println("����ETTɾ��С������ɹ�!");
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
     * ����EttС������Ա��Ϣ
     * @param clsid �༶ID
     * @param groupid �༶��С��ID
     * @param dcschoolid ��УID
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
                    //�ش� userId��userType ��key
                    Map<String,Object> tmpMap=new HashMap<String, Object>();
                    tmpMap.put("userId",tgs.getUserid());
                    tmpMap.put("userType",3);   //3:ѧ��
                    objMapList.add(tmpMap);
                }
            }
        }
        return EttInterfaceUserUtil.OperateGroupUser(objMapList,clsid,groupid,dcschoolid);
    }

	
	/**
	 * ѧ������
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
        //��¼������ص�GroupId����
        List<Long> operateGroupIdList=new ArrayList<Long>();
        operateGroupIdList.add(gsList.get(0).getGroupid());
        if(isleader.equals("1")){
            //���GroupId�鳤
            TpGroupStudent gsUpd= new TpGroupStudent();
            gsUpd.setGroupid(Long.parseLong(groupid));
            gsUpd.setIsleader(2);
            sql=new StringBuilder();
            objList=this.tpGroupStudentManager.getUpdateSql(gsUpd,sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
            //��¼
            if(!operateGroupIdList.contains(Long.parseLong(groupid)))
                operateGroupIdList.add(Long.parseLong(groupid));
        }

        TpGroupStudent groupStudent=gsList.get(0);
        //��ID��ͬ
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
        }else{ //��ID��ͬɾ���������ݣ�������ӣ�����С��ʱ��ı�
            TpGroupStudent gsDel=new TpGroupStudent();
            gsDel.setRef(Integer.parseInt(ref));
            sql=new StringBuilder();
            objList=this.tpGroupStudentManager.getDeleteSql(gsDel, sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //���TpGroupStudent����
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
            //��¼
            if(!operateGroupIdList.contains(Long.parseLong(groupid)))
                operateGroupIdList.add(Long.parseLong(groupid));
        }

		if(this.tpGroupStudentManager.doExcetueArrayProc(sqlListArray,objListArray)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");

            if(operateGroupIdList!=null&&operateGroupIdList.size()>0){
                for(Long gid:operateGroupIdList){
                    if(gid!=null&&gid.toString().length()>0){
                        //�õ�ѧУID
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
                                    System.out.println("����ETT�޸�С����Ա����ʧ��!");
                                }else
                                    System.out.println("����ETT�޸�С����Ա����ɹ�!");
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
     * ��ETT�������������
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

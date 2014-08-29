package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.entity.teachpaltform.*;
import com.school.manager.ClassManager;
import com.school.manager.ClassUserManager;
import com.school.manager.GradeManager;
import com.school.manager.TermManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.IGradeManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.teachpaltform.*;
import com.school.util.EttInterfaceUserUtil;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        String subjectid=request.getParameter("subjectid");
        String gradeid = request.getParameter("gradeid");
        UserInfo u=this.logined(request);
        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if (ti == null || classid==null || classtype==null) {
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("ѧ�ڲ�������,����ϵ����"));// �쳣���󣬲������룬�޷���������!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }
        //��ѯ�꼶����
        GradeInfo gi = new GradeInfo();
        gi.setGradeid(Integer.parseInt(gradeid));
        List<GradeInfo> giList = this.gradeManager.getList(gi,null);
        ClassUser c = new ClassUser();
        //��ǰѧ�ڡ�ѧ�ơ��꼶�µ��ڿΰ༶
        c.setClassgrade(giList.get(0).getGradevalue());
        c.setUserid(this.logined(request).getRef());
        c.setRelationtype("�ο���ʦ");
        c.setSubjectid(Integer.parseInt(subjectid));
        c.setYear(ti.getYear());
        List<ClassUser>clsList=this.classUserManager.getList(c,null);
       // List<ClassUser> clsList = this.classUserManager.getListByTchYear(u.getRef(), ti.getYear());
        TpVirtualClassInfo tvc= new TpVirtualClassInfo();
        tvc.setCuserid(u.getUserid());
        tvc.setStatus(1);
        List<TpVirtualClassInfo> tvcList=this.tpVirtualClassManager.getList(tvc, null);

        TrusteeShipClass trusteeShipClass=new TrusteeShipClass();
        trusteeShipClass.setTrustclassid(Integer.parseInt(classid));
        trusteeShipClass.setIsaccept(1);//����
        trusteeShipClass.setTrustteacherid(this.logined(request).getUserid());
        List<TrusteeShipClass>trusteeShipClassList=this.trusteeShipClassManager.getList(trusteeShipClass,null);

        mp.put("classid",classid);
        mp.put("classtype",classtype);
        mp.put("classes", clsList);
        mp.put("vclasses", tvcList);
        //��ǰ�༶���й�
        if(trusteeShipClassList!=null&&trusteeShipClassList.size()>0){
            mp.put("isTrust",1);
        }

		return new ModelAndView("/teachpaltform/class/groupManager",mp);
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
                        Integer.parseInt(subjectid),
                        this.termManager.getMaxIdTerm(false).getRef());
		je.setObjList(stuList);  
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
		TermInfo term=this.termManager.getMaxIdTerm(false);
		if(term==null)
			term = this.termManager.getMaxIdTerm(true);
		if(tg==null
                ||tg.getSubjectid()==null
                ||tg.getClassid()==null
                ||tg.getClasstype()==null
			    ||term.getRef()==null
                    ||term.getRef().trim().length()<1){
            je.setType("error");
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		tg.setTermid(term.getRef());
		List<TpGroupInfo>groupList=this.tpGroupManager.getGroupBySubject(tg);
        //��ѯС�����������
        int completenum=0;
        int totalnum=0;
        if(groupList!=null&&groupList.size()>0){
            for(int i = 0;i<groupList.size();i++){
                //���Ȳ�ѯû��С�����������
                List<TpTaskAllotInfo> taskList=this.tpTaskAllotManager.getTaskByGroup(groupList.get(i).getGroupid());
                //Ȼ����������ѯÿ�������������ͽ���������
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
		TpGroupStudent gs=new TpGroupStudent();
		gs.setGroupid(Long.parseLong(groupid));
        gs.setStateid(0);
		List<TpGroupStudent>groupstudentList=this.tpGroupStudentManager.getList(gs,null);
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
        }

		if(this.tpGroupStudentManager.doExcetueArrayProc(sqlListArray,objListArray)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
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

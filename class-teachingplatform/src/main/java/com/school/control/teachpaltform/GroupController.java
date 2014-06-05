package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.ClassUser;
import com.school.entity.GradeInfo;
import com.school.entity.TermInfo;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.TpGroupInfo;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.entity.teachpaltform.TpVirtualClassInfo;
import com.school.entity.teachpaltform.TrusteeShipClass;
import com.school.manager.ClassUserManager;
import com.school.manager.TermManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.teachpaltform.ITpGroupManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.manager.inter.teachpaltform.ITpVirtualClassManager;
import com.school.manager.inter.teachpaltform.ITrusteeShipClassManager;
import com.school.manager.teachpaltform.TpGroupManager;
import com.school.manager.teachpaltform.TpGroupStudentManager;
import com.school.manager.teachpaltform.TpVirtualClassManager;
import com.school.manager.teachpaltform.TrusteeShipClassManager;
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
    public GroupController(){
        this.termManager=this.getManager(TermManager.class);
        this.tpGroupManager=this.getManager(TpGroupManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.tpVirtualClassManager=this.getManager(TpVirtualClassManager.class);
        this.trusteeShipClassManager=this.getManager(TrusteeShipClassManager.class);
        this.tpGroupStudentManager=this.getManager(TpGroupStudentManager.class);
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
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        UserInfo u=this.logined(request);
        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if (ti == null || classid==null || classtype==null) {
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("学期参数错误,请联系教务。"));// 异常错误，参数不齐，无法正常访问!
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());
            return null;
        }

        List<ClassUser> clsList = this.classUserManager.getListByTchYear(u.getRef(), ti.getYear());
        TpVirtualClassInfo tvc= new TpVirtualClassInfo();
        tvc.setCuserid(u.getUserid());
        List<TpVirtualClassInfo> tvcList=this.tpVirtualClassManager.getList(tvc, null);

        TrusteeShipClass trusteeShipClass=new TrusteeShipClass();
        trusteeShipClass.setTrustclassid(Integer.parseInt(classid));
        trusteeShipClass.setIsaccept(1);//接受
        trusteeShipClass.setTrustteacherid(this.logined(request).getUserid());
        List<TrusteeShipClass>trusteeShipClassList=this.trusteeShipClassManager.getList(trusteeShipClass,null);

        mp.put("classid",classid);
        mp.put("classtype",classtype);
        mp.put("classes", clsList);
        mp.put("vclasses", tvcList);
        //当前班级已托管
        if(trusteeShipClassList!=null&&trusteeShipClassList.size()>0){
            mp.put("isTrust",1);
        }

		return new ModelAndView("/teachpaltform/class/groupManager",mp);
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
                        this.termManager.getMaxIdTerm(false).getRef());
		je.setObjList(stuList);  
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
		TermInfo term=this.termManager.getMaxIdTerm(false);
		if(term==null)
			term = this.termManager.getMaxIdTerm(true);
		if(tg==null
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
		tg.setCuserid(this.logined(request).getUserid());
		List<TpGroupInfo>groupList=this.tpGroupManager.getList(tg,null);
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
		TpGroupStudent gs=new TpGroupStudent();
		gs.setGroupid(Long.parseLong(groupid));
        gs.setStateid(0);
		List<TpGroupStudent>groupstudentList=this.tpGroupStudentManager.getList(gs,null);
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
        if(tg==null
            ||tg.getClassid() == null
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
		
		if(this.tpGroupManager.checkGroupName(tg)){
			je.setType("errorname");
			response.getWriter().print(je.toJSON());
			return;
		}
		
		if(this.tpGroupManager.doSave(tg)){
			je.setType("success");
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

		if(this.tpGroupStudentManager.doDelete(gs)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
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
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
		}
		response.getWriter().print(je.toJSON());
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
		TpGroupStudent gs=new TpGroupStudent();
		gs.setRef(Integer.parseInt(ref));
		gs.setGroupid(Long.parseLong(groupid));
        gs.setIsleader(Integer.parseInt(isleader));
		if(this.tpGroupStudentManager.doUpdate(gs)){
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

}

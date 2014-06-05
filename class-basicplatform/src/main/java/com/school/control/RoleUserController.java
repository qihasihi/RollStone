package com.school.control;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.*;
import com.school.manager.inter.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController; 
import com.school.entity.ClassYearInfo;
import com.school.entity.DeptInfo; 
import com.school.entity.GradeInfo;
import com.school.entity.JobInfo;
import com.school.entity.RoleInfo;
import com.school.entity.RoleUser; 
import com.school.entity.SubjectInfo;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@Scope("prototype")
@RequestMapping(value="/roleuser")
public class RoleUserController extends BaseController<RoleUser> {
    private IClassYearManager classYearManager;
    private IGradeManager gradeManager;
    private ISubjectManager subjectManager;
    private IDeptManager deptManager;
    private IRoleManager roleManager;
    private IUserManager userManager;
    private IRoleUserManager roleUserManager;
    public RoleUserController(){
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
        this.deptManager=this.getManager(DeptManager.class);
        this.roleManager=this.getManager(RoleManager.class);
        this.userManager=this.getManager(UserManager.class);
        this.roleUserManager=this.getManager(RoleUserManager.class);
    }

	/**
	 * 获取给角色分配用户模式窗体
	 * @param request
	 * @param response
	 * @throws Exception  
	 */
	@RequestMapping(params="m=toSetRoleUser",method=RequestMethod.GET) 
	public ModelAndView toSetRoleUser(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
		JsonEntity je = new JsonEntity(); 
		PageResult clsyearPresult=new PageResult();
		clsyearPresult.setPageNo(0);
		clsyearPresult.setPageSize(0);
		clsyearPresult.setOrderBy("class_year_value desc");
		List<ClassYearInfo>yearList=this.classYearManager.getList(null, clsyearPresult);
		if(yearList==null||yearList.size()<1){ 
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_YEAR_NOT_EXISTS"));
			response.getWriter().print(je.getAlertMsgAndCloseWin()); 
			return null;   
		} 
		List<GradeInfo>gradeList=this.gradeManager.getList(null, null);
		List<SubjectInfo>subjectList=this.subjectManager.getList(null, null);
		RoleInfo r=new RoleInfo();
		r.setFlag(0);
		List<RoleInfo>roleList=this.roleManager.getList(r, null);
		r.setFlag(1);  
		List<RoleInfo>jobList=this.roleManager.getList(r, null);
		DeptInfo d=new DeptInfo(); 
		d.setParentdeptid(0);  
		List<DeptInfo>deptList=this.deptManager.getList(null, null);
		
		request.setAttribute("yearList",yearList);  
		request.setAttribute("gradeList",gradeList);
		request.setAttribute("subList",subjectList);  
		request.setAttribute("roleList",roleList);
		request.setAttribute("jobList",jobList); 
		request.setAttribute("deptList",deptList); 
		return new ModelAndView("/roleuser/dialoglist");   
	} 

	/**
	 * 获取用户 根据 角色、部门、职务
	 * @param request
	 * @param response
	 * @param mp
	 * @throws Exception
	 */
	@RequestMapping(params="m=getUserByCondition",method=RequestMethod.POST) 
	public void getUserByCondition(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult pageresult = this.getPageResultParameter(request);
		pageresult.setOrderBy("state_id,c_time desc"); 
		RoleUser ru=this.getParameter(request, RoleUser.class);
		
		request.setAttribute("indentityname", ru.getIdentityname());
		request.setAttribute("rolestr", ru.getRoleidstr());
		request.setAttribute("username", ru.getUsername());
		
		
		String year=request.getParameter("year");
//		if(year==null||year.trim().length()<1){
//			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(je.toJSON());
//			return; 
//		} 
		boolean isselstu=false,isseljz=false;
		String[]ridArray=null;
		if(ru.getRoleidstr()!=null&&ru.getRoleidstr().length()>0){
			ridArray=ru.getRoleidstr().split(",");
			for(int i=0;i<ridArray.length;i++){
				if(ridArray[i]!=null&&ridArray.length==1){
					if(UtilTool._ROLE_STU_ID.toString().equals(ridArray[i].toString()))
						isselstu=true;
					else if(UtilTool._ROLE_STU_PARENT_ID.toString().equals(ridArray[i].toString()))
						isseljz=true;
				} 
			}	
		} 	
		List<UserInfo>userList=this.userManager.getUserByCondition(year, isselstu,isseljz, ru, pageresult);
		pageresult.setList(userList);
		je.setPresult(pageresult); 
		je.setType("success"); 
		response.getWriter().print(je.toJSON()); 
	} 
	
	/**
	 * 给角色分配用户 
	 * @param request
	 * @param response
	 * @param mp
	 * @throws Exception
	 */
	@RequestMapping(params="m=doManageRoleUser",method=RequestMethod.POST)
	public void doSetRoleUser(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
		JsonEntity je = new JsonEntity();
		String roleid=request.getParameter("roleid");
		String useridArray=request.getParameter("useridArray");
		if(roleid==null||!UtilTool.isNumber(roleid)){
			je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLEID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(useridArray==null||useridArray.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON()); 
			return;
		}
		String[]uidArray=useridArray.split(",");
		List<String>sqlArrayList=new ArrayList<String>();
		List<List<Object>>objArrayList=new ArrayList<List<Object>>();
		StringBuilder sql=null;
		List<Object>objList=null;
		if(uidArray.length>0){
			for (String userid : uidArray) {
				if(userid!=null&&UtilTool.isNumber(userid)){
					RoleUser ru=new RoleUser();
					ru.getRoleinfo().setRoleid(Integer.parseInt(roleid));
					UserInfo tu=new UserInfo();
					tu.setUserid(Integer.parseInt(userid));
					UserInfo u=this.userManager.getUserInfo(tu);
					if(u!=null&&u.getRef()!=null){
						ru.getUserinfo().setRef(u.getRef());
						//删除
						sql=new StringBuilder();
						objList=this.roleUserManager.getDeleteSql(ru, sql);
						if(objList!=null&&sql.length()>0){
							sqlArrayList.add(sql.toString());
							objArrayList.add(objList);
						} 
						 
						//添加
						sql=new StringBuilder();
						ru.setRef(UUID.randomUUID().toString()); 
						objList=this.roleUserManager.getSaveSql(ru, sql);
						if(objList!=null&&sql.length()>0){
							sqlArrayList.add(sql.toString());
							objArrayList.add(objList); 
						}
					} 
				}
			}
		}
		if(sqlArrayList.size()>0&&objArrayList.size()>0){
			boolean flag=this.roleUserManager.doExcetueArrayProc(sqlArrayList, objArrayList);
			if(flag){
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
	 * 添加年级组长 
	 * @param request
	 * @param response
	 * @param mp
	 * @throws Exception
	 */  
	@RequestMapping(params="m=doSetGradeLeader",method=RequestMethod.POST)
	public void doSetGradeLeader(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
		JsonEntity je = new JsonEntity();
		String roleid=request.getParameter("roleid");
		String useridArray=request.getParameter("useridArray");
		String gradeid=request.getParameter("gradeid");
		if(roleid==null||roleid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLEID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		if(useridArray==null||useridArray.trim().length()<1||gradeid==null){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON()); 
			return;
		}   
		String[]uidArray=useridArray.split(",");
		List<String>sqlArrayList=new ArrayList<String>();
		List<List<Object>>objArrayList=new ArrayList<List<Object>>();
		StringBuilder sql=null;
		List<Object>objList=null;
		if(uidArray.length>0){
			RoleUser ru=new RoleUser();
			ru.getRoleinfo().setRoleid(Integer.parseInt(roleid));
			ru.setGradeid(Integer.parseInt(gradeid));
			//删除
			sql=new StringBuilder();
			objList=this.roleUserManager.getDeleteSql(ru, sql);
			if(objList!=null&&sql.length()>0){
				sqlArrayList.add(sql.toString());
				objArrayList.add(objList);
			}
			 
			for (String userid : uidArray) {
				if(userid!=null&&userid.length()>0){
					//添加
					sql=new StringBuilder();
					ru.setRef(UUID.randomUUID().toString()); 
					ru.setGradeid(Integer.parseInt(gradeid));
					ru.getUserinfo().setRef(userid);
					objList=this.roleUserManager.getSaveSql(ru, sql);
					if(objList!=null&&sql.length()>0){ 
						sqlArrayList.add(sql.toString());
						objArrayList.add(objList); 
					}
				}  
			}
		}
		if(sqlArrayList.size()>0&&objArrayList.size()>0){
			boolean flag=this.roleUserManager.doExcetueArrayProc(sqlArrayList, objArrayList);
			if(flag){
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
 
}

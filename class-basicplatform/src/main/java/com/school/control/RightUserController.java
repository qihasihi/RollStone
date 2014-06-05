package com.school.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.RightUserManager;
import com.school.manager.RoleUserManager;
import com.school.manager.inter.IRightUserManager;
import com.school.manager.inter.IRoleUserManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.RightUser;
import com.school.entity.RoleUser;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/rightuser") 
@Scope("prototype") 
public class RightUserController extends BaseController<RightUser> {
	private IRoleUserManager roleUserManager;
    private IRightUserManager rightUserManager;
    public RightUserController(){
        this.roleUserManager=this.getManager(RoleUserManager.class);
        this.rightUserManager=this.getManager(RightUserManager.class);
    }
	
	
	/**
	 * 给角色用户增（删）权限 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=setRightByRole",method=RequestMethod.POST)
	public void toSetPageRightByRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String roleid=request.getParameter("rid");
		String pagerightstr=request.getParameter("prightidstr");
		
		JsonEntity je= new JsonEntity();
		if(roleid==null||!UtilTool.isNumber(roleid)){
			je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLEID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>sqlListArray=new ArrayList<String>();
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		StringBuilder sql=null;
		List<Object>objList=null;
		if(pagerightstr!=null&&pagerightstr.trim().length()>0){
			//先删除
			RoleUser ru=new RoleUser();
			ru.setRoleidstr(roleid);
			List<RoleUser>ruList=this.roleUserManager.getList(ru, null);
			if(ruList==null||ruList.size()<1){
				je.setMsg(UtilTool.msgproperty.getProperty("ROLE_USER_NOT_EXISTS"));
				response.getWriter().print(je.toJSON());
				return;
			}
			for (RoleUser rUser : ruList) {
				if(rUser!=null&&rUser.getRuserid()!=null){
					RightUser rutmp=new RightUser();
					rutmp.getUserinfo().setUserid(rUser.getRuserid());
					sql=new StringBuilder();
					objList=this.rightUserManager.getDeleteSql(rutmp, sql);
					if(objList!=null&&sql.length()>0){
						sqlListArray.add(sql.toString());
						objListArray.add(objList);
					}
				}
			}
			//添加
			String[]rightIdArray=pagerightstr.split(",");
			if(!"isRemove".equalsIgnoreCase(pagerightstr)&&rightIdArray.length>0){
				for (RoleUser newRu : ruList){
					if(newRu!=null&&newRu.getRuserid()!=null){
						RightUser newrUser=new RightUser();
						newrUser.getUserinfo().setUserid(newRu.getRuserid());
						for (String rightid : rightIdArray) {
							if(rightid!=null&&UtilTool.isNumber(rightid)){
								newrUser.setPagerightid(Integer.parseInt(rightid));
								sql=new StringBuilder(); 
								objList=this.rightUserManager.getSaveSql(newrUser, sql);
								if(objList!=null&&sql.length()>0){
									sqlListArray.add(sql.toString());
									objListArray.add(objList);
								}
							}
						}
					}
					
				}
			}
		}
		 
		if(sqlListArray.size()>0&&objListArray.size()>0){
			boolean flag=this.rightUserManager.doExcetueArrayProc(sqlListArray, objListArray);
			if(flag){
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{  
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}
		response.getWriter().print(je.toJSON());   
	}
	
	
	/**
	 * 用户管理页面     
	 * 给用户增（删）权限  
	 * @param request 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=setRightByUserid",method=RequestMethod.POST)
	public void toSetPageRightByUserid(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String useridstr=request.getParameter("useridstr");
		String pagerightstr=request.getParameter("prightidstr");
		
		JsonEntity je= new JsonEntity();
		if(useridstr==null||useridstr.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>sqlListArray=new ArrayList<String>();
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		StringBuilder sql=null;
		List<Object>objList=null;
		String[]useridArray=useridstr.split(",");
		if(pagerightstr!=null&&pagerightstr.trim().length()>0){
			//先删除
			if(useridArray.length>0){
				for (String uid : useridArray) {
					RightUser rutmp=new RightUser();
					rutmp.getUserinfo().setUserid(Integer.parseInt(uid));
					sql=new StringBuilder();
					objList=this.rightUserManager.getDeleteSql(rutmp, sql);
					if(objList!=null&&sql.length()>0){
						sqlListArray.add(sql.toString());
						objListArray.add(objList);
					}
				}
			}
			//添加
			String[]rightIdArray=pagerightstr.split(",");
			if(!"isRemove".equalsIgnoreCase(pagerightstr)&&rightIdArray.length>0){
				for (String userid : useridArray){
					if(userid!=null&&UtilTool.isNumber(userid)){
						RightUser newrUser=new RightUser();
						newrUser.getUserinfo().setUserid(Integer.parseInt(userid));
						for (String rightid : rightIdArray) {
							if(rightid!=null&&UtilTool.isNumber(rightid)){
								newrUser.setPagerightid(Integer.parseInt(rightid));
								sql=new StringBuilder(); 
								objList=this.rightUserManager.getSaveSql(newrUser, sql);
								if(objList!=null&&sql.length()>0){
									sqlListArray.add(sql.toString());
									objListArray.add(objList);
								}
							}
						}
					}
				}
			}
		}
		  
		if(sqlListArray.size()>0&&objListArray.size()>0){
			boolean flag=this.rightUserManager.doExcetueArrayProc(sqlListArray, objListArray);
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

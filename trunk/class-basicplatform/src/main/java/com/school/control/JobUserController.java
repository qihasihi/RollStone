package com.school.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.JobUserManager;
import com.school.manager.inter.IJobUserManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.school.control.base.BaseController;
import com.school.entity.JobUser;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;

@Controller
@Scope("prototype")
@RequestMapping(value="/jobuser")
public class JobUserController extends BaseController<JobUser>{
	private IJobUserManager jobUserManager;
    public JobUserController(){
        this.jobUserManager=this.getManager(JobUserManager.class);
    }
	/**
	 * 设置用户职务
	 * @param request 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=setUserJob",method=RequestMethod.POST)
	public void setUserJob(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je =new JsonEntity(); 
		String useridstr=request.getParameter("useridArray");
		String jobid=request.getParameter("jobid");
		if(useridstr==null||useridstr.trim().length()<1
			||jobid==null||jobid.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>sqlListArray=new ArrayList<String>();
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		List<Object>objList=null;
		StringBuilder sql=null;
		
		String[]useridArray=useridstr.split(",");
		if(useridArray.length>0){
			for (String userid : useridArray) {
				JobUser ju=new JobUser();
				ju.getJobinfo().setJobid(Integer.parseInt(jobid));
				ju.getUserinfo().setRef(userid);
				
				//先删除
				sql=new StringBuilder();
				objList=this.jobUserManager.getDeleteSql(ju, sql);
				if(objList!=null&&sql!=null){
					sqlListArray.add(sql.toString());
					objListArray.add(objList);
				}
				//再添加 
				sql=new StringBuilder();
				objList=this.jobUserManager.getSaveSql(ju, sql);
				if(objList!=null&&sql!=null){
					sqlListArray.add(sql.toString());
					objListArray.add(objList);
				}
				
			}
		}
		
		if(sqlListArray.size()>0&&objListArray.size()>0){
			if(this.jobUserManager.doExcetueArrayProc(sqlListArray, objListArray)){
				je.setType("success");
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
		}
		response.getWriter().print(je.toJSON());
	}
}

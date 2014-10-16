package com.school.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.JobManager;
import com.school.manager.inter.IJobManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.JobInfo;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/job")
public class JobController extends BaseController<JobInfo>{ 
    private IJobManager jobManager;
    public JobController(){
        this.jobManager=this.getManager(JobManager.class);
    }
	/**
	 * 进入List页面。
	 * @param request
	 * @param mp
	 * @return
	 */ 
	@RequestMapping(params="m=list",method=RequestMethod.GET)
	protected ModelAndView getListBy(HttpServletRequest request,ModelMap mp) throws Exception{	
		return new ModelAndView("/job/jobManage",mp);		
	}
	
	@RequestMapping(params="m=ajaxList",method=RequestMethod.POST)
	protected void getAjaxList(HttpServletRequest request,HttpServletResponse response) throws Exception{	
		JsonEntity je = new JsonEntity();
		PageResult pageresult=this.getPageResultParameter(request);	
		JobInfo jobInfo=this.getParameter(request, JobInfo.class);
		List<JobInfo> jobList=jobManager.getList(jobInfo, pageresult);
		pageresult.setList(jobList);
		je.setPresult(pageresult);
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}
	
	@RequestMapping(params="m=add",method=RequestMethod.POST)
	protected void add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JobInfo jobInfo=this.getParameter(request, JobInfo.class);//得到常规参数
		//验证参数
		JsonEntity je=new JsonEntity();
		if(jobInfo.getJobname()==null||jobInfo.getJobname().length()<1){
			je.setMsg("错误，请输入职务名称!");
			response.getWriter().print(je.toJSON());return;
		}
		boolean isflag=this.jobManager.doSave(jobInfo);		
		if(isflag){
			je.setType("success");
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
		}else
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		response.getWriter().print(je.toJSON());
	}
	
	@RequestMapping(params="m=del",method=RequestMethod.POST)
	protected void del(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JobInfo jobInfo=this.getParameter(request, JobInfo.class);//得到常规参数
		//验证参数
		JsonEntity je=new JsonEntity();
		if(jobInfo.getJobid()==null||jobInfo.getJobid()==0){
			je.setMsg("错误!");
			response.getWriter().print(je.toJSON());return;
		}
		boolean isflag=this.jobManager.doDelete(jobInfo);		
		
		if(isflag){
			je.setType("success");
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
		}else
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		response.getWriter().print(je.toJSON());
	}
	

	@RequestMapping(params="m=update",method=RequestMethod.POST)
	protected void update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JobInfo jobInfo=this.getParameter(request, JobInfo.class);//得到常规参数
		//验证参数
		JsonEntity je=new JsonEntity();
		if(jobInfo.getJobid()==null||jobInfo.getJobid()==0){
			je.setMsg("错误!");
			response.getWriter().print(je.toJSON());return;
		}
		if(jobInfo.getJobname()==null||jobInfo.getJobname().length()<1){
			je.setMsg("错误，请输入职务名称!");
			response.getWriter().print(je.toJSON());return;
		}
		boolean isflag=this.jobManager.doUpdate(jobInfo);		
		//response.sendRedirect("job?m=list"); 
		if(isflag){
			je.setType("success");
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
		}else
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		response.getWriter().print(je.toJSON());
	}
	
}

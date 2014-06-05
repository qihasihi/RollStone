package com.school.control.evalteacher;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.ClassYearInfo;
import com.school.entity.evalteacher.TimeStepInfo;
import com.school.manager.ClassYearManager;
import com.school.manager.evalteacher.TimeStepManager;
import com.school.manager.inter.IClassYearManager;
import com.school.manager.inter.evalteacher.ITimeStepManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/timestep") 
public class TimpStepInfoAction extends BaseController<TimeStepInfo> {
    private IClassYearManager classYearManager;
    private ITimeStepManager timeStepManager;
    public TimpStepInfoAction(){
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.timeStepManager=this.getManager(TimeStepManager.class);
    }

	@RequestMapping(params="m=list",method=RequestMethod.GET)  
	public ModelAndView toQueryList(HttpServletRequest request,ModelMap mp ) throws Exception{
		List<ClassYearInfo> cyl = this.classYearManager.getList(null, null);
		List<TimeStepInfo> tsi=this.timeStepManager.getPageList(null, null);
		mp.put("classYearList", cyl);
		mp.put("timeStepList", tsi);
		return new ModelAndView("/evalteacher/timesetup/list",mp);	 
	}
	
	@RequestMapping(params="m=save",method=RequestMethod.POST)  
	public void toSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		TimeStepInfo timestepinfo = this.getParameter(request, TimeStepInfo.class);
		if(timestepinfo==null||timestepinfo.getYearid()==null
				||timestepinfo.getPjstarttimeString()==null||timestepinfo.getPjendtimeString()==null){
			je.setMsg("异常错误，原因：参数错误!");
			response.getWriter().print(je.toJSON());
			return;
		}
		int msg=0;
		System.out.println(timestepinfo.getRef());
		if(timestepinfo.getRef()!=null&&timestepinfo.getRef()>0){
			msg=this.timeStepManager.update(timestepinfo);
		}else{
			List list=this.timeStepManager.getListByYear(timestepinfo);
			if(list==null||list.size()>0){
				je.setMsg("已存在该学年，无法添加，请进行修改操作!");
				response.getWriter().print(je.toJSON());
				return;
			}
			msg=this.timeStepManager.save(timestepinfo);
		}
		if(msg>0){
			je.setType("success");
			je.setMsg("保存成功！");
			
		}else{
			je.setMsg("异常错误，原因：SQL执行错误！");
		}
		response.getWriter().print(je.toJSON());
		
	}
	
	@RequestMapping(params="m=gettimeinfo",method=RequestMethod.POST)  
	public void getTimeInfo(HttpServletRequest request,HttpServletResponse response ) throws Exception{
		JsonEntity je = new JsonEntity();
		TimeStepInfo timestepinfo = this.getParameter(request, TimeStepInfo.class);
		if(timestepinfo==null||timestepinfo.getRef()==null){
			je.setMsg("没有找到数据!");
			response.getWriter().print(je.toJSON());
			return;
		}
		PageResult pr = new PageResult();
		List<TimeStepInfo> tsList=this.timeStepManager.getPageList(timestepinfo, null);
		if(tsList==null||tsList.size()>0){
			pr.setList(tsList);
			je.setType("success");
			je.setPresult(pr);
			response.getWriter().print(je.toJSON());
		}else{
			je.setMsg("没有找到数据!");
		}
		return;
	}

}

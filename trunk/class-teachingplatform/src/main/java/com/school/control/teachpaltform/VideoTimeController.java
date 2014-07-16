package com.school.control.teachpaltform;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.inter.teachpaltform.IVideoTimeManager;
import com.school.manager.teachpaltform.VideoTimeManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.teachpaltform.VideoTimeInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/videotime")
public class VideoTimeController extends BaseController<VideoTimeInfo> {
    private IVideoTimeManager videoTimeManager;
    public VideoTimeController(){
        this.videoTimeManager=this.getManager(VideoTimeManager.class);
    }

	
	@RequestMapping(params="m=toList",method=RequestMethod.GET)
	public ModelAndView toSetTime(HttpServletRequest request,HttpServletResponse response)throws Exception{
		VideoTimeInfo vt=new VideoTimeInfo();
		PageResult presult=new PageResult();
		presult.setOrderBy(" u.ref");
		List<VideoTimeInfo>vtList =this.videoTimeManager.getList(vt, presult);
		request.setAttribute("vtList", vtList);
		return new ModelAndView("/video/list");
	}
	
	
	@RequestMapping(params="m=update",method=RequestMethod.POST)
	public void doSetTime(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity(); 
		String ref=request.getParameter("ref");
		if(ref==null||ref.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return; 
		}
		VideoTimeInfo v=new VideoTimeInfo(); 
		v.setFlag(1);
		v.setRef(Integer.parseInt(ref));
		if(this.videoTimeManager.doUpdate(v)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}     
}

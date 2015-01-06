package com.school.control.resource;

import com.school.control.base.BaseController;
import com.school.entity.resource.ResourceRecommend;
import com.school.manager.inter.resource.IResourceRecommendManager;
import com.school.util.JsonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/resrecommend") 
public class RecommendController extends BaseController<ResourceRecommend>{
    @Autowired
    private IResourceRecommendManager resourceRecommendManager;

	/**
	 * 添加
	 * @param request
	 * @param response
	 * @param mp
	 * @throws Exception
	 */
	@RequestMapping(params="m=doadd",method=RequestMethod.POST)
	public void doAddRecommend(HttpServletRequest request,HttpServletResponse response,ModelAndView mp) throws Exception{
		JsonEntity jEntity=new JsonEntity();
		
		ResourceRecommend rr=this.getParameter(request, ResourceRecommend.class);
		if(rr==null||rr.getResid()==null||rr.getResid().trim().length()<1){
			jEntity.setMsg("Error!");
			response.getWriter().print(jEntity.toJSON());return;
		}
		//更新或添加推荐记录
		rr.setRef(this.resourceRecommendManager.getNextId());
		if(this.resourceRecommendManager.doSave(rr))
			jEntity.setType("success");
		response.getWriter().print(jEntity.toJSON());
	}
	
	@RequestMapping(params="m=dodel",method=RequestMethod.POST)
	public void doDelRecommend(HttpServletRequest request,HttpServletResponse response,ModelAndView mp) throws Exception{
		JsonEntity jEntity=new JsonEntity();
		
		ResourceRecommend rr=this.getParameter(request, ResourceRecommend.class);
		if(rr==null||rr.getResid()==null||rr.getResid().trim().length()<1){
			jEntity.setMsg("Error!");
			response.getWriter().print(jEntity.toJSON());return;
		}
		//删除推荐记录
		if(this.resourceRecommendManager.doDelete(rr))
			jEntity.setType("success");
		response.getWriter().print(jEntity.toJSON());
	}
	
}

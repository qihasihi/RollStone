package com.school.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.ScoreManager;
import com.school.manager.inter.IScoreManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.resource.ResourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.CommentInfo;
import com.school.entity.ScoreInfo;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value = "/score")
public class ScoreController extends BaseController<ScoreInfo> {
    @Autowired
    private IScoreManager scoreManager;
    @Autowired
    private IResourceManager resourceManager;

	/**
	 * Ìí¼Ó
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "m=ajaxsave", method = RequestMethod.POST)
	public void doSubmitAddComment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		ScoreInfo scoreinfo = this.getParameter(request, ScoreInfo.class);
		if (scoreinfo == null || scoreinfo.getScoretype() == null
				|| scoreinfo.getScoreobjectid() == null
				|| scoreinfo.getScore() == null) {
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		scoreinfo.setScoreuserid(this.logined(request).getUserid());
		scoreinfo.setScoreid(this.scoreManager.getNextId());
		if (scoreManager.doSave(scoreinfo)) {
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		} else {
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * Ìí¼Ó
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "m=ajaxResScore", method = RequestMethod.POST)
	public void doResScore(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		ScoreInfo scoreinfo = this.getParameter(request, ScoreInfo.class);
		if (scoreinfo == null || scoreinfo.getScoretype() == null
				|| scoreinfo.getScoreobjectid() == null
				|| scoreinfo.getScore() == null) {
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		scoreinfo.setScoreuserid(this.logined(request).getUserid());
		scoreinfo.setScoreid(this.scoreManager.getNextId());
		if (scoreManager.doSave(scoreinfo)) {
			System.out.println("----------------"+this.resourceManager.updateResScore(scoreinfo.getScoreobjectid()));
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		} else {
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
}

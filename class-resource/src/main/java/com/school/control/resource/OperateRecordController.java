package com.school.control.resource;

import com.school.control.base.BaseController;
import com.school.entity.resource.OperateRecord;
import com.school.manager.inter.resource.IOperateRecordManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/operaterecord")
public class OperateRecordController extends BaseController<OperateRecord> {
    @Autowired
    private IOperateRecordManager operateRecordManager;

	/**
	 * 推荐资源
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "m=recomendres", method = RequestMethod.POST)
	public void recomendRes(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OperateRecord orTmp = this.getParameter(request, OperateRecord.class);
		JsonEntity jEntity = new JsonEntity();

		if (orTmp.getResid() == null) {
			jEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jEntity.toJSON());
			return;
		}

		orTmp.setUserid(this.logined(request).getUserid().intValue());
		orTmp.setOperatetype(1);

		if (this.operateRecordManager.doSave(orTmp)) {
			jEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_SUCCESS"));
			jEntity.setType("success");
		} else
			jEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_ERROR"));
		response.getWriter().print(jEntity.toJSON());
	}
	
	/**
	 * 赞资源
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "m=praiseres", method = RequestMethod.POST)
	public void praiseRes(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OperateRecord orTmp = this.getParameter(request, OperateRecord.class);
		JsonEntity jEntity = new JsonEntity();

		if (orTmp.getResid() == null) {
			jEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jEntity.toJSON());
			return;
		}

		orTmp.setUserid(this.logined(request).getUserid().intValue());
		orTmp.setOperatetype(2);

		if (this.operateRecordManager.doSave(orTmp)) {
			jEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_SUCCESS"));
			jEntity.setType("success");
		} else
			jEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_ERROR"));
		response.getWriter().print(jEntity.toJSON());
	}
}

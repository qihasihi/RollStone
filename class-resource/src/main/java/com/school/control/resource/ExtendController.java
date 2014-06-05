package com.school.control.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.inter.resource.IExtendManager;
import com.school.manager.inter.resource.IExtendValueManager;
import com.school.manager.resource.ExtendManager;
import com.school.manager.resource.ExtendValueManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.SmsInfo;
import com.school.entity.resource.ExtendInfo;
import com.school.entity.resource.ExtendValueInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value = "/extend")
public class ExtendController extends BaseController<ExtendInfo> {
    private IExtendManager extendManager;
    private IExtendValueManager extendValueManager;
    public ExtendController(){
        this.extendManager=this.getManager(ExtendManager.class);
        this.extendValueManager=this.getManager(ExtendValueManager.class);
    }

	@RequestMapping(params = "m=list", method = RequestMethod.GET)
	public ModelAndView toExtendList(HttpServletRequest request, ModelAndView mp)
			throws Exception {
		List<ExtendInfo> extendList = this.extendManager.getList(null, null);
		request.setAttribute("extendList", extendList);
		return new ModelAndView("/resource/extend/list");
	}

	/**
	 * 去修改
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "m=update", method = RequestMethod.POST)
	public void doUpdateExtend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		ExtendInfo extendinfo = this.getParameter(request, ExtendInfo.class); 
		if (extendinfo == null || extendinfo.getExtendid() == null) {
			je.setMsg("参数不足无法修改，请刷新页面重试！！");
			response.getWriter().print(je.toJSON());
			return;
		}

		List<String> sqlListArray = new ArrayList<String>();
		List<List<Object>> objListArray = new ArrayList<List<Object>>();
		StringBuilder sql = null;
		List<Object> objList = null;

		sql = new StringBuilder();
		objList = this.extendManager.getUpdateSql(extendinfo, sql);
		sqlListArray.add(sql.toString());
		objListArray.add(objList);

		String valList = "";
		if (extendinfo.getExtendvaluelist() != null) {
			for (String value : extendinfo.getExtendvaluelist().split("\n"))
				valList += "'" + value + "',";
			valList = valList.substring(0, valList.length() - 1);
		}
		List<ExtendValueInfo> deleteValList = this.extendValueManager
				.getNotInList(extendinfo.getExtendid(), valList);

		for (ExtendValueInfo ev : deleteValList) {
			sql = new StringBuilder();
			objList = this.extendValueManager.getDeleteSql(ev, sql);
			sqlListArray.add(sql.toString());
			objListArray.add(objList);
		}
		for (String value : extendinfo.getExtendvaluelist().split("\n")) {
			ExtendValueInfo ev = new ExtendValueInfo();
			ev.setExtendid(extendinfo.getExtendid());
			ev.setValuename(value);
			List lt = this.extendValueManager.getList(ev, null);
			if (lt == null || lt.size() == 0) {
				sql = new StringBuilder();
				ev.setValueid(this.extendValueManager.getNextId());
				objList = this.extendValueManager.getSaveSql(ev, sql);
				sqlListArray.add(sql.toString());
				objListArray.add(objList);
			}

		}
		if (this.extendValueManager.doExcetueArrayProc(sqlListArray,
				objListArray))
			je.setType("success");

		sqlListArray = new ArrayList<String>();
		objListArray = new ArrayList<List<Object>>();
		for (int i = 0; i < extendinfo.getExtendvaluelist().split("\n").length; i++) {
			ExtendValueInfo ev = new ExtendValueInfo();
			ev.setExtendid(extendinfo.getExtendid());
			ev.setValuename(extendinfo.getExtendvaluelist().split("\n")[i]);
			ev.setOrdernumber(i);
			sql = new StringBuilder();
			objList = this.extendValueManager.getUpdateOrderBySql(ev, sql);
			sqlListArray.add(sql.toString());
			objListArray.add(objList);
		}
		this.extendValueManager.doExcetueArrayProc(sqlListArray, objListArray);

		response.getWriter().print(je.toJSON());
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "m=ajaxsave", method = RequestMethod.POST)
	public void doAddExtend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		ExtendInfo extendinfo = this.getParameter(request, ExtendInfo.class);
		String extendValueList = request.getParameter("extendvaluelist");
		if (extendinfo.getExtendname() == null) {
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		extendinfo.setExtendid(this.extendManager.getNextId());

		List<String> sqlListArray = new ArrayList<String>();
		List<List<Object>> objListArray = new ArrayList<List<Object>>();
		StringBuilder sql = null;
		List<Object> objList = null;
		sql = new StringBuilder();
		objList = this.extendManager.getSaveSql(extendinfo, sql);
		sqlListArray.add(sql.toString());
		objListArray.add(objList);
		int i = 1;
		for (String extendValue : extendValueList.split("\n")) {
			if (extendValue.length() > 0) {
				sql = new StringBuilder();
				ExtendValueInfo ex = new ExtendValueInfo();
				ex.setValueid(this.extendValueManager.getNextId());
				ex.setExtendid(extendinfo.getExtendid());
				ex.setValuename(extendValue);
				ex.setOrdernumber(i);
				objList = this.extendValueManager.getSaveSql(ex, sql);
				sqlListArray.add(sql.toString());
				objListArray.add(objList);
				i++;
			}
		}
		if (this.extendValueManager.doExcetueArrayProc(sqlListArray,
				objListArray))
			je.setType("success");
		response.getWriter().print(je.toJSON());
	}

	@RequestMapping(params = "m=del", method = RequestMethod.POST)
	public void doDelExtend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		ExtendInfo extendinfo = this.getParameter(request, ExtendInfo.class);
		if (this.extendManager.doDelete(extendinfo)) {
			je.setType("success");
		}
		response.getWriter().print(je.toJSON());
	}

	@RequestMapping(params = "m=getExtendList", method = RequestMethod.POST)
	public void getExtendList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();

		ExtendInfo extendinfo = this.getParameter(request, ExtendInfo.class);
		ExtendValueInfo ex = new ExtendValueInfo();
		ex.setExtendid(extendinfo.getExtendid());
		List<ExtendInfo> extList = this.extendManager.getList(extendinfo, null);
		je.setObjList(extList);
		response.getWriter().print(je.toJSON());
	}

	@RequestMapping(params = "m=getextendinfo", method = RequestMethod.POST)
	public void getExtend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		ExtendInfo extendinfo = this.getParameter(request, ExtendInfo.class);
		if (extendinfo.getExtendid() == null)
			return;
		ExtendValueInfo ex = new ExtendValueInfo();
		ex.setExtendid(extendinfo.getExtendid());
		List<ExtendInfo> extList = this.extendManager.getList(extendinfo, null);
		if (extList != null && extList.size() > 0) {
			List<ExtendValueInfo> extValList = this.extendValueManager.getList(
					ex, null);
			if (extValList != null && extValList.size() > 0) {
				String valueStr = "";
				for (ExtendValueInfo ev : extValList)
					valueStr += ev.getValuename() + "\n";
				extList.get(0).setExtendvaluelist(valueStr);
			}
			je.setObjList(extList);
			je.setType("success");
		}
		response.getWriter().print(je.toJSON());
	}

	@RequestMapping(params = "m=getextendvaluelist", method = RequestMethod.POST)
	public void getExtendValueList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		ExtendInfo extendinfo = this.getParameter(request, ExtendInfo.class);
		if (extendinfo == null || extendinfo.getExtendid() == null)
			return;
		ExtendValueInfo ex = new ExtendValueInfo();
		ex.setExtendid(extendinfo.getExtendid());
		je.setObjList(this.extendValueManager.getList(ex, null));
		response.getWriter().print(je.toJSON());
	}

}

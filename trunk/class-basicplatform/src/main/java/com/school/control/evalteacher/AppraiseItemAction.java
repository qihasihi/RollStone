package com.school.control.evalteacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.ClassYearManager;
import com.school.manager.evalteacher.AppraiseItemManager;
import com.school.manager.evalteacher.TimeStepManager;
import com.school.manager.inter.IClassYearManager;
import com.school.manager.inter.evalteacher.IAppraiseItemManager;
import com.school.manager.inter.evalteacher.ITimeStepManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassYearInfo;
import com.school.entity.evalteacher.AppraiseItemInfo;
import com.school.entity.evalteacher.TimeStepInfo;

import com.school.util.JsonEntity;
import com.school.util.PageResult;

/**
 * 评教系统(评价项)
 * 
 * @author zhusx
 * 
 */
@Controller
@RequestMapping(value = "/appraiseitem")
public class AppraiseItemAction extends BaseController<AppraiseItemInfo> {
    private IClassYearManager classYearManager;
    private IAppraiseItemManager appraiseItemManager;
    private ITimeStepManager timeStepManager;
    public AppraiseItemAction(){
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.appraiseItemManager=this.getManager(AppraiseItemManager.class);
        this.timeStepManager=this.getManager(TimeStepManager.class);
    }
	/**
	 * 进入设置评价相管理
	 * 
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(params = "m=list", method = RequestMethod.GET)
	public ModelAndView toAppraiseManager(HttpServletRequest request,
			ModelMap mp) throws Exception {
		// 加载最新学年
		ClassYearInfo cyi = null;
		List<ClassYearInfo> yearList = this.classYearManager
				.getCurrentYearList(null);
		if (yearList != null && yearList.size() > 0) {
			cyi = yearList.get(0);
			mp.put("currentYear", cyi);

			AppraiseItemInfo appiteminfo = new AppraiseItemInfo();
			appiteminfo.setYearid(cyi.getClassyearid());
			List<AppraiseItemInfo> appitemList = this.appraiseItemManager
					.getList(appiteminfo, null);
			mp.put("appitemList", appitemList);
			TimeStepInfo tsinfo = new TimeStepInfo();
			tsinfo.setYearid(cyi.getClassyearid());
			List<TimeStepInfo> tsinfoList = this.timeStepManager
					.getListByYear(tsinfo);
			Integer isstart = 0;
			if (tsinfoList != null && tsinfoList.size()>0){
				 // 1:未开始 2：已经开始 3:已经结束
				if (tsinfoList.get(0).getPjstarttime().getTime() > new Date()
						.getTime())
					isstart = 1;
				// 正在执行中
				else if (tsinfoList.get(0).getPjstarttime().getTime() < new Date()
						.getTime()
						&& tsinfoList.get(0).getPjendtime().getTime() > new Date()
								.getTime())
					isstart = 2;
				// 已经结束
				else if (tsinfoList.get(0).getPjendtime().getTime() < new Date()
						.getTime())
					isstart = 3;
				
			}
			mp.put("isstart", isstart);
		}
		mp.put("classYearList", this.classYearManager.getList(null, null));
		return new ModelAndView("/evalteacher/appraiseitem/list", mp);
	}

	/**
	 * 加载不是本学年的评教项
	 * 
	 * @throws Exception
	 * 
	 *             public void loadPreeQuestion() throws Exception{ JsonEntity
	 *             je=new JsonEntity(); List<AppraiseItemInfo>
	 *             appitemList=this.getAppitemmanager().getList(this.getModel(),
	 *             null); je.setObjList(appitemList); je.setType("success");
	 *             response.getWriter().print(je.toJSON()); }
	 * 
	 *             /** 添加项目(问题)
	 * 
	 * @throws Exception
	 */

	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(params = "m=ajaxList", method = RequestMethod.POST)
	public void getAppitemList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		AppraiseItemInfo title_appitem = this.getParameter(request,
				AppraiseItemInfo.class);
		List<AppraiseItemInfo> appitemList = this.appraiseItemManager
				.getList(title_appitem, null);
		je.setObjList(appitemList);
		response.getWriter().print(je.toJSON());
	}

	
	@RequestMapping(params = "m=save", method = RequestMethod.POST)
	public void doSaveQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		AppraiseItemInfo title_appitem = this.getParameter(request,
				AppraiseItemInfo.class);
		if (title_appitem.getName() == null
				|| title_appitem.getName().trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题信息，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		String answerA = request.getParameter("option_a");
		String answerB = request.getParameter("option_b");
		String answerC = request.getParameter("option_c");
		String answerD = request.getParameter("option_d");
		if (answerA == null || answerA.trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题 A 答案，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (answerB == null || answerB.trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题 B 答案，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (answerC == null || answerC.trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题 C 答案，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (answerD == null || answerD.trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题 D 答案，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		// 开始执行操作

		ClassYearInfo cyi = null;
		List<ClassYearInfo> yearList = this.classYearManager
				.getCurrentYearList(null);
		if (yearList != null && yearList.size() > 0) {
			cyi = yearList.get(0);
		} else {
			je.setMsg("异常错误，系统尚未获取到系统中最新的年份，请联系管理员！  \n\n代码：CURRENT_YEAR!");
			response.getWriter().print(je.toJSON());
			return;
		}
		title_appitem.setYearid(cyi.getClassyearid());
		title_appitem.setIstitle(1);
		Integer title_id = this.appraiseItemManager.save(title_appitem);
		if (title_id == null || title_id < 1) {
			je.setMsg("创建项标题失败，请重试或联系管理员...");
			response.getWriter().print(je.toJSON());
			return;
		}

		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
		List<String> sqlStrList = new ArrayList<String>();
		List<Object> objPara = new ArrayList<Object>();
		StringBuilder sqlbuilder = new StringBuilder();
		// //////////////////////////添加答案(答案添加的时候 :item_id 是所在的问题，有REF代传入)
		// A:
		AppraiseItemInfo appitem = new AppraiseItemInfo();
		appitem.setName(answerA.trim());
		appitem.setYearid(cyi.getClassyearid());
		appitem.setItemid(title_id);
		appitem.setOptionid(1);
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getSaveSql(appitem, sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());
		// B:
		appitem = new AppraiseItemInfo();
		appitem.setName(answerB.trim());
		appitem.setYearid(cyi.getClassyearid());
		appitem.setItemid(title_id);
		appitem.setOptionid(2);
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getSaveSql(appitem, sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());
		// C:
		appitem = new AppraiseItemInfo();
		appitem.setName(answerC.trim());
		appitem.setYearid(cyi.getClassyearid());
		appitem.setItemid(title_id);
		appitem.setOptionid(3);
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getSaveSql(appitem, sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());
		// D:
		appitem = new AppraiseItemInfo();
		appitem.setName(answerD.trim());
		appitem.setYearid(cyi.getClassyearid());
		appitem.setItemid(title_id);
		appitem.setOptionid(4);
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getSaveSql(appitem, sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());

		je.setMsg("异常错误，系统没有发现可以执行的语句!");
		// 开始执行
		if (sqlStrList.size() > 0 && objArrayList.size() > 0) {
			boolean flag = this.appraiseItemManager.doExcetueArrayProc(
					sqlStrList, objArrayList);
			if (flag) {
				je.setType("success");
				je.setMsg("问题添加成功!");
			} else
				je.setMsg("添加失败！原因：未知!");
		}
		response.getWriter().print(je.toJSON());
	}
	/**
	 * 删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(params = "m=del", method = RequestMethod.POST)
	public void doDeleteQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		AppraiseItemInfo appitem = this.getParameter(request,
				AppraiseItemInfo.class);
		if (appitem.getRef() == null) {
			je.setMsg("异常错误，系统尚未获取到必要的数据，导致系统无法继续运作，请稍后重试!");
			response.getWriter().print(je.toJSON());
			return;
		}
		Integer affices = this.appraiseItemManager.delete(appitem);
		if (affices == null || affices < 1) {
			je.setMsg("删除失败！原因：未知!");
			response.getWriter().print(je.toJSON());
		} else {
			je.setMsg("删除成功!");
			je.setType("success");
		}
		response.getWriter().print(je.toJSON());
	}

	/**
	 * 修改项目(问题)
	 * 
	 * @throws Exception
	 */
	@RequestMapping(params = "m=update", method = RequestMethod.POST)
	public void doUpdateQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		AppraiseItemInfo title_appitem = this.getParameter(request,
				AppraiseItemInfo.class);
		if (title_appitem.getRef() == null || title_appitem.getName() == null
				|| title_appitem.getName().trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题信息，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		String refA = request.getParameter("ref_a");
		String refB = request.getParameter("ref_b");
		String refC = request.getParameter("ref_c");
		String refD = request.getParameter("ref_d");
		String answerA = request.getParameter("option_a");
		String answerB = request.getParameter("option_b");
		String answerC = request.getParameter("option_c");
		String answerD = request.getParameter("option_d");
		if (refA == null || refA.trim().length() < 1 || answerA == null
				|| answerA.trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题 A 答案，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (refB == null || refB.trim().length() < 1 || answerB == null
				|| answerB.trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题 B 答案，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (refC == null || refC.trim().length() < 1 || answerC == null
				|| answerC.trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题 C 答案，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (refD == null || refD.trim().length() < 1 || answerD == null
				|| answerD.trim().length() < 1) {
			je.setMsg("系统尚未获取到您要添加的问题 D 答案，请录入!");
			response.getWriter().print(je.toJSON());
			return;
		}

		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
		List<String> sqlStrList = new ArrayList<String>();
		List<Object> objPara = new ArrayList<Object>();
		StringBuilder sqlbuilder = new StringBuilder();
		// //////////////////////////添加答案(答案添加的时候 :item_id 是所在的问题，有REF代传入)
		// title:
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getUpdateSql(title_appitem,
				sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());
		// A:
		AppraiseItemInfo appitem = new AppraiseItemInfo();
		appitem.setRef(Integer.parseInt(refA));
		appitem.setName(answerA.trim());
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getUpdateSql(appitem, sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());
		// B:
		appitem = new AppraiseItemInfo();
		appitem.setRef(Integer.parseInt(refB));
		appitem.setName(answerB.trim());
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getUpdateSql(appitem, sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());
		// C:
		appitem = new AppraiseItemInfo();
		appitem.setRef(Integer.parseInt(refC));
		appitem.setName(answerC.trim());
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getUpdateSql(appitem, sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());
		// D:
		appitem = new AppraiseItemInfo();
		appitem.setRef(Integer.parseInt(refD));
		appitem.setName(answerD.trim());
		sqlbuilder = new StringBuilder();
		objPara = this.appraiseItemManager.getUpdateSql(appitem, sqlbuilder);
		objArrayList.add(objPara);
		sqlStrList.add(sqlbuilder.toString());

		je.setMsg("异常错误，系统没有发现可以执行的语句!");
		// 开始执行
		if (sqlStrList.size() > 0 && objArrayList.size() > 0) {
			boolean flag = this.appraiseItemManager.doExcetueArrayProc(
					sqlStrList, objArrayList);
			if (flag) {
				je.setType("success");
				je.setMsg("问题修改成功!");
			} else
				je.setMsg("修改失败！原因：未知!");
		}
		response.getWriter().print(je.toJSON());
	}

	/**
	 * 沿用
	 * 
	 * @return
	 * @throws Exception
	 * 
	 *             public void doYyong() throws Exception{ JsonEntity je=new
	 *             JsonEntity(); if(this.getModel().getPjyear()==null){
	 *             je.setMsg("异常错误，系统尚未获取到您要引用的年份！请重试!");
	 *             response.getWriter().print(je.toJSON()); return ; }
	 *             if(this.getModel().getTargetidentitytype()==null){
	 *             je.setMsg("异常错误，系统尚未获取到您要引用的评教项针对身份！请重试!");
	 *             response.getWriter().print(je.toJSON()); return ; } String
	 *             currentYear=this.getTermmanager().getMaxIdTerm().getYear();
	 * 
	 *             //查询所有的Year List<AppraiseItemInfo>
	 *             appList=this.getAppitemmanager().getList(this.getModel(),
	 *             null); if(appList==null||appList.size()<1){
	 *             je.setMsg(this.getModel().getPjyear()+"年份中没有发现评教项目，请核对后重试!");
	 *             response.getWriter().print(je.toJSON()); return ; }
	 *             List<String> sqlList=new ArrayList<String>();
	 *             List<List<Object>> objArrayList=new
	 *             ArrayList<List<Object>>();
	 * 
	 *             BigDecimal nextId=null; for (AppraiseItemInfo tmp : appList)
	 *             { StringBuilder sqlbuilder=new StringBuilder();
	 *             if(tmp.getIstitle()!=null
	 *             &&tmp.getIstitle().intValue()==BigDecimal.ONE.intValue()){
	 *             nextId=this.getAppitemmanager().getNextSeqId(); }
	 *             tmp.setPjyear(currentYear); tmp.setRef(nextId);
	 *             tmp.setTargetidentitytype
	 *             (this.getModel().getTargetidentitytype()); List<Object>
	 *             objParaList=this.getAppitemmanager().getSaveSql(tmp,
	 *             sqlbuilder); sqlList.add(sqlbuilder.toString());
	 *             objArrayList.add(objParaList); }
	 * 
	 *             if(objArrayList.size()>0&&sqlList.size()>0){ boolean flag =
	 *             this.getAppitemmanager().doMoreProcedure(sqlList,
	 *             objArrayList); if (flag) { je.setType("success");
	 *             je.setMsg("引用'"+this.getModel().getPjyear()+"'成功!");
	 *             response.getWriter().print(je.toJSON());
	 * 
	 * 
	 *             } else{ je.setMsg("引用失败！原因：未知!");
	 *             response.getWriter().print(je.toJSON()); return ; } }else{
	 *             je.setMsg("异常错误，系统尚未获取到可以引用的数据！");
	 *             response.getWriter().print(je.toJSON()); return ; }
	 * 
	 *             }
	 */
}

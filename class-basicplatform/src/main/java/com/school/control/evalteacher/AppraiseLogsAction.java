package com.school.control.evalteacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.*;
import com.school.manager.evalteacher.AppraiseItemManager;
import com.school.manager.evalteacher.AppraiseLogsManager;
import com.school.manager.evalteacher.TimeStepManager;
import com.school.manager.inter.*;
import com.school.manager.inter.evalteacher.ITimeStepManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassUser;
import com.school.entity.ClassYearInfo;
import com.school.entity.GradeInfo;
import com.school.entity.SubjectInfo;
import com.school.entity.TeacherInfo;
import com.school.entity.UserInfo;
import com.school.entity.evalteacher.AppraiseItemInfo;
import com.school.entity.evalteacher.AppraiseLogsInfo;
import com.school.entity.evalteacher.TimeStepInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

/**
 * 评教系统(评价项)
 * 
 * @author zhusx
 * 
 */
@Controller
@RequestMapping(value = "/teacherappraise")
public class AppraiseLogsAction extends BaseController<AppraiseLogsInfo> {
    private IClassYearManager classYearManager;
    private ITimeStepManager timeStepManager;
    private ITeacherManager teacherManager;
    private AppraiseLogsManager appraiseLogsManager;
    private AppraiseItemManager appraiseItemManager;
    private IUserManager userManager;
    private IClassUserManager classUserManager;
    private IGradeManager gradeManager;
    public AppraiseLogsAction(){
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.timeStepManager=this.getManager(TimeStepManager.class);
        this.teacherManager=this.getManager(TeacherManager.class);
        this.appraiseLogsManager=this.getManager(AppraiseLogsManager.class);
        this.appraiseItemManager=this.getManager(AppraiseItemManager.class);
        this.userManager=this.getManager(UserManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
    }

	@RequestMapping(params = "m=list_stu", method = RequestMethod.GET)
	public ModelAndView teacherAppraiseItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {
		UserInfo user = this.logined(request);
		
		String tchref = request.getParameter("tchref");
		
		String displayTile = request.getParameter("displayTitle");
		
		if(displayTile!=null && displayTile.equals("false")){
			mp.put("displayTitle", false);
		}else{
			mp.put("displayTitle", true);
		}
		
		ClassYearInfo cyi = null;
		List<ClassYearInfo> yearList = this.classYearManager
				.getCurrentYearList(null);
		if (yearList != null && yearList.size() > 0) {
			cyi = yearList.get(0);
			mp.put("currentYear", cyi);
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
			
			
			List<Map<String, Object>> teachersCondition = new ArrayList<Map<String, Object>>();
			List<TeacherInfo> teaList = this.teacherManager
					.getTeacherListByUserId(user.getRef(),
							cyi.getClassyearvalue());
			String tr = null;
			for (TeacherInfo t : teaList) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				AppraiseLogsInfo aplogs = new AppraiseLogsInfo();
				aplogs.setTargetuserref(t.getUserid());
				aplogs.setUserid(user.getUserid());
				aplogs.setYearid(cyi.getClassyearid());
				int count = this.appraiseLogsManager.getListNum(aplogs);
				if(count>0?false:true&&tr==null)
					tr=t.getUserid();
				map.put("name", t.getTeachername());
				map.put("subject", t.getSubjects());
				map.put("ref", t.getUserid());
				map.put("status", count > 0 ? true : false);
				teachersCondition.add(map);
			}
			mp.put("teaList", teachersCondition);
			if(tchref!=null){
				mp.put("tchref", tchref);
			}else{
				if(tr!=null)
					mp.put("tchref", tr);
			}
			mp.put("yearid", cyi.getClassyearid());
			mp.put("year", cyi.getClassyearvalue());
			AppraiseItemInfo appiteminfo = new AppraiseItemInfo();
			appiteminfo.setYearid(cyi.getClassyearid());
			List<AppraiseItemInfo> appitemList = this.appraiseItemManager
					.getList(appiteminfo, null);
			mp.put("appitemList", appitemList);
		}
		return new ModelAndView("/evalteacher/teacherappraise/list", mp);
	}

	@RequestMapping(params = "m=getteacherclass", method = RequestMethod.POST)
	public void getTeacherClassInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		String tchid = request.getParameter("tchid");
		List<ClassYearInfo> cyi = this.classYearManager.getCurrentYearList(null);
		if (tchid == null || cyi == null || cyi.size() == 0) {
			je.setMsg("参数不足,无法评教！");
			response.getWriter().print(je.toJSON());
			return;
		}
		UserInfo user = new UserInfo();
		user.setRef(tchid);
		List l = new ArrayList();
		user = this.userManager.getUserInfo(user);
		List<ClassUser> cuList = this.classUserManager.getClassUserByTchAndStu(
				tchid, this.logined(request).getRef(), cyi.get(0)
						.getClassyearvalue(),1);	//得到启用的班级
		if (user != null)
			l.add(user.getHeadimage() != null
					&& user.getHeadimage().length() > 0 ? user.getHeadimage()
					: "#");

		if (cuList != null && cuList.size() > 0)
			l.add(cuList);

		if (l.size() == 2) {
			je.setType("success");
			je.setObjList(l);
		}
		response.getWriter().print(je.toJSON());
	}

	/**
	 * 评价(目前只有appraiselogs-stu.jsp调用)
	 * 
	 * @throws Exception
	 * */
	@RequestMapping(params = "m=addappraiselosg", method = RequestMethod.POST)
	public void doAddAppraiseLogs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		AppraiseLogsInfo ali = this.getParameter(request,
				AppraiseLogsInfo.class);
		if (ali == null) {
			je.setMsg("参数错误，无法提交！！");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (ali.getTargetclassuserref() == null) {
			je.setMsg("异常错误，系统尚未获取到您要评价教职员工的班级引用! 请刷新重试!    错误标识: Classuserref");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (ali.getTargetuserref() == null) {
			je.setMsg("异常错误，系统尚未获取到您要评价的用户标识！请刷新后重试，错误标识：User_Id");
			response.getWriter().print(je.toJSON());
			return;
		}
		String[] answerOptions = request.getParameterValues("answeroption");
		if (answerOptions == null || answerOptions.length < 1) {
			je.setMsg("异常错误，系统尚未获取到您评价的信息！请重新提交!   错误标识：answeroption");
			response.getWriter().print(je.toJSON());
			return;
		}

		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"CURRENT_USER");
		if (user == null) {
			je.setMsg("未登录无法评教！");
			response.getWriter().print(je.toJSON());
			return;
		}
		ClassYearInfo cyi = null;
		List<ClassYearInfo> yearList = this.classYearManager
				.getCurrentYearList(null);
		if (yearList != null && yearList.size() > 0) {
			cyi = yearList.get(0);
		} else {
			je.setMsg("无法获取当前学期信息！！");
			response.getWriter().print(je.toJSON());
			return;
		}
		ali.setYearid(cyi.getClassyearid());
		ali.setUserid(user.getUserid());
		// 准保存储数据
		List<String> sqlStrList = new ArrayList<String>();
		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
		for (String tmp : answerOptions) {
			if (tmp.indexOf("|") != -1) {
				String[] tmpStr = tmp.split("\\|");
				if (tmpStr.length == 3 && UtilTool.isNumber(tmpStr[0])
						&& UtilTool.isNumber(tmpStr[1])
						&& UtilTool.isDouble(tmpStr[2])) {
					ali.setItemid(Integer.parseInt(tmpStr[0].trim()));
					ali.setOptionid(Integer.parseInt(tmpStr[1].trim()));
					ali.setScore(Float.parseFloat(tmpStr[2].trim()));
					StringBuilder sqlbuilder = new StringBuilder();
					List<Object> objList = this.appraiseLogsManager.getSaveSql(
							ali, sqlbuilder);
					objArrayList.add(objList);
					sqlStrList.add(sqlbuilder.toString());
				}
			}
		}
		je.setMsg("异常错误，系统没有发现可以执行的语句!");
		// 开始执行
		if (sqlStrList.size() > 0 && objArrayList.size() > 0) {
			boolean flag = this.appraiseLogsManager.doExcetueArrayProc(
					sqlStrList, objArrayList);
			if (flag) {
				je.setType("success");
				je.setMsg("评价成功!");
			} else
				je.setMsg("评价失败！原因：未知!");
		}
		response.getWriter().print(je.toJSON());
	}

	/*************** 查看页面统计 **********************/
	/**
	 * 管理员进入统计页面
	 * */
	@RequestMapping(params = "m=admin_watch_ban", method = RequestMethod.GET)
	public ModelAndView toAdminWatchBan(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {
		ClassYearInfo cyi = null;
		List<ClassYearInfo> yearList = this.classYearManager
				.getCurrentYearList(null);
		if (yearList != null && yearList.size() > 0) {
			cyi = yearList.get(0);
			mp.put("currentYear", cyi);
		}

		yearList = this.classYearManager.getList(new ClassYearInfo(), null);
		mp.put("yearList", yearList);

		List<GradeInfo> gradeList = this.gradeManager.getList(new GradeInfo(),
				null);
		mp.put("gradeList", gradeList);

		List<SubjectInfo> subjectList = this.getManager(SubjectManager.class).getList(
                new SubjectInfo(), null);
		mp.put("subjectList", subjectList);

		return new ModelAndView(
				"/evalteacher/teacherappraise/all_applogs_count", mp);
	}

	@RequestMapping(params = "m=teacher_watch_ban", method = RequestMethod.GET)
	public ModelAndView toTeacherWatchBan(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {
		String tchId = request.getParameter("tch_id");
		mp.put("tch_id", tchId);
		ClassYearInfo cyi = null;
		List<ClassYearInfo> yearList = this.classYearManager
				.getCurrentYearList(null);
		if (yearList != null && yearList.size() > 0) {
			cyi = yearList.get(0);
		} else {
			return null;
		}
		yearList = this.classYearManager.getList(new ClassYearInfo(), null);
		mp.put("yearList", yearList);

		TeacherInfo tch = new TeacherInfo();
		tch.setUserid(tchId);
		tch = this.teacherManager.getList(tch, null).get(0);
		mp.put("tch", tch);
		List<String> sumColumnList = new ArrayList<String>();
		sumColumnList.add("姓名");
		sumColumnList.add("班级");
		sumColumnList.add("百分制得分");
		sumColumnList.add("排名");
		sumColumnList.add("得分");

		// 总分平均分列明
		mp.put("sumColumnList", sumColumnList);
		// 得到初中。高中的年级
		List<GradeInfo> gradeDictList = this.gradeManager.getList(null, null);
		// 得到统计全部的数据
		List<Map<String, Object>> dataList = null;
		dataList = this.appraiseLogsManager.getQueryBanStatice(
				cyi.getClassyearid(), tchId, new PageResult());

		mp.put("dataList", dataList);
		return new ModelAndView(
				"/evalteacher/teacherappraise/teacherclass/teacher_applogs_count", mp);
	}
	
	@RequestMapping(params = "m=self_watch_ban", method = RequestMethod.GET)
	public ModelAndView toSelfWatchBan(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {
		
		String userid = this.logined(request).getRef();
		mp.put("tch_id", userid);
		ClassYearInfo cyi = null;
		List<ClassYearInfo> yearList = this.classYearManager
				.getCurrentYearList(null);
		if (yearList != null && yearList.size() > 0) {
			cyi = yearList.get(0);
		} else {
			return null;
		}
		yearList = this.classYearManager.getList(new ClassYearInfo(), null);
		mp.put("yearList", yearList);

		TeacherInfo tch = new TeacherInfo();
		tch.setUserid(userid);
		tch = this.teacherManager.getList(tch, null).get(0);
		mp.put("tch", tch);
		List<String> sumColumnList = new ArrayList<String>();
		sumColumnList.add("姓名");
		sumColumnList.add("班级");
		sumColumnList.add("百分制得分");
		sumColumnList.add("排名");
		sumColumnList.add("得分");

		// 总分平均分列明
		mp.put("sumColumnList", sumColumnList);
		// 得到初中。高中的年级
		List<GradeInfo> gradeDictList = this.gradeManager.getList(null, null);
		// 得到统计全部的数据
		List<Map<String, Object>> dataList = null;
		dataList = this.appraiseLogsManager.getQueryBanStatice(
				cyi.getClassyearid(), userid, new PageResult());

		mp.put("dataList", dataList);
		return new ModelAndView(
				"/evalteacher/teacherappraise/teacherclass/teacher_applogs_count", mp);
	}
	
	@RequestMapping(params = "m=toClassResultDetailed", method = RequestMethod.GET)
	public ModelAndView toClassResultDetailed(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {
		
		AppraiseLogsInfo ali = this.getParameter(request,
				AppraiseLogsInfo.class);
		JsonEntity jeEntity=new JsonEntity();
		if(ali==null||ali.getTargetuserref()==null||ali.getClassid()==null||ali.getYearid()==null){
			jeEntity.setMsg("参数错误！！");
			response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return null;
		}

		List<Map<String, Object>> dataList = null;
		dataList = this.appraiseLogsManager.getQueryTeaClassStatice(ali);

		mp.put("dataList", dataList);
		return new ModelAndView(
				"/evalteacher/teacherappraise/teacheritem/tch_class_applogs_count", mp);
	}


	@RequestMapping(params = "m=refresh_pj_stat", method = RequestMethod.POST)
	public void refreshPJStat(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		this.appraiseLogsManager.refreshPJStat();
		je.setType("success");
		response.getWriter().print(je.toJSON());
	}

	@RequestMapping(params = "m=searchevalcount", method = RequestMethod.POST)
	public void searchEvalCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		Integer yearid = Integer.parseInt(request.getParameter("yearid"));
		String grades = request.getParameter("grades");
		String subjects = request.getParameter("subjects");
		List<Map<String, Object>> dataList = null;
		dataList = this.appraiseLogsManager.getQueryBanStatice(yearid, grades,
				subjects, null);
		if (dataList != null && dataList.size() > 0) {
			je.setType("success");
			je.setObjList(dataList);
		}
		response.getWriter().print(je.toJSON());
	}

	@RequestMapping(params = "m=searchteachercount", method = RequestMethod.POST)
	public void searchTeacherCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		Integer yearid = Integer.parseInt(request.getParameter("yearid"));
		String tch_id = request.getParameter("tch_id");
		List<Map<String, Object>> dataList = null;
		dataList = this.appraiseLogsManager.getQueryBanStatice(yearid, tch_id,
				null);
		if (dataList != null && dataList.size() > 0) {
			je.setType("success");
			je.setObjList(dataList);
		}
		response.getWriter().print(je.toJSON());
	}
}

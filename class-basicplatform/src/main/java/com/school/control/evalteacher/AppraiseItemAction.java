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
 * ����ϵͳ(������)
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
	 * �����������������
	 * 
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(params = "m=list", method = RequestMethod.GET)
	public ModelAndView toAppraiseManager(HttpServletRequest request,
			ModelMap mp) throws Exception {
		// ��������ѧ��
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
				 // 1:δ��ʼ 2���Ѿ���ʼ 3:�Ѿ�����
				if (tsinfoList.get(0).getPjstarttime().getTime() > new Date()
						.getTime())
					isstart = 1;
				// ����ִ����
				else if (tsinfoList.get(0).getPjstarttime().getTime() < new Date()
						.getTime()
						&& tsinfoList.get(0).getPjendtime().getTime() > new Date()
								.getTime())
					isstart = 2;
				// �Ѿ�����
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
	 * ���ز��Ǳ�ѧ���������
	 * 
	 * @throws Exception
	 * 
	 *             public void loadPreeQuestion() throws Exception{ JsonEntity
	 *             je=new JsonEntity(); List<AppraiseItemInfo>
	 *             appitemList=this.getAppitemmanager().getList(this.getModel(),
	 *             null); je.setObjList(appitemList); je.setType("success");
	 *             response.getWriter().print(je.toJSON()); }
	 * 
	 *             /** �����Ŀ(����)
	 * 
	 * @throws Exception
	 */

	/**
	 * ɾ��
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
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ�������Ϣ����¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}
		String answerA = request.getParameter("option_a");
		String answerB = request.getParameter("option_b");
		String answerC = request.getParameter("option_c");
		String answerD = request.getParameter("option_d");
		if (answerA == null || answerA.trim().length() < 1) {
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ����� A �𰸣���¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (answerB == null || answerB.trim().length() < 1) {
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ����� B �𰸣���¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (answerC == null || answerC.trim().length() < 1) {
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ����� C �𰸣���¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (answerD == null || answerD.trim().length() < 1) {
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ����� D �𰸣���¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}
		// ��ʼִ�в���

		ClassYearInfo cyi = null;
		List<ClassYearInfo> yearList = this.classYearManager
				.getCurrentYearList(null);
		if (yearList != null && yearList.size() > 0) {
			cyi = yearList.get(0);
		} else {
			je.setMsg("�쳣����ϵͳ��δ��ȡ��ϵͳ�����µ���ݣ�����ϵ����Ա��  \n\n���룺CURRENT_YEAR!");
			response.getWriter().print(je.toJSON());
			return;
		}
		title_appitem.setYearid(cyi.getClassyearid());
		title_appitem.setIstitle(1);
		Integer title_id = this.appraiseItemManager.save(title_appitem);
		if (title_id == null || title_id < 1) {
			je.setMsg("���������ʧ�ܣ������Ի���ϵ����Ա...");
			response.getWriter().print(je.toJSON());
			return;
		}

		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
		List<String> sqlStrList = new ArrayList<String>();
		List<Object> objPara = new ArrayList<Object>();
		StringBuilder sqlbuilder = new StringBuilder();
		// //////////////////////////��Ӵ�(����ӵ�ʱ�� :item_id �����ڵ����⣬��REF������)
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

		je.setMsg("�쳣����ϵͳû�з��ֿ���ִ�е����!");
		// ��ʼִ��
		if (sqlStrList.size() > 0 && objArrayList.size() > 0) {
			boolean flag = this.appraiseItemManager.doExcetueArrayProc(
					sqlStrList, objArrayList);
			if (flag) {
				je.setType("success");
				je.setMsg("������ӳɹ�!");
			} else
				je.setMsg("���ʧ�ܣ�ԭ��δ֪!");
		}
		response.getWriter().print(je.toJSON());
	}
	/**
	 * ɾ��
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
			je.setMsg("�쳣����ϵͳ��δ��ȡ����Ҫ�����ݣ�����ϵͳ�޷��������������Ժ�����!");
			response.getWriter().print(je.toJSON());
			return;
		}
		Integer affices = this.appraiseItemManager.delete(appitem);
		if (affices == null || affices < 1) {
			je.setMsg("ɾ��ʧ�ܣ�ԭ��δ֪!");
			response.getWriter().print(je.toJSON());
		} else {
			je.setMsg("ɾ���ɹ�!");
			je.setType("success");
		}
		response.getWriter().print(je.toJSON());
	}

	/**
	 * �޸���Ŀ(����)
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
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ�������Ϣ����¼��!");
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
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ����� A �𰸣���¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (refB == null || refB.trim().length() < 1 || answerB == null
				|| answerB.trim().length() < 1) {
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ����� B �𰸣���¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (refC == null || refC.trim().length() < 1 || answerC == null
				|| answerC.trim().length() < 1) {
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ����� C �𰸣���¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}
		if (refD == null || refD.trim().length() < 1 || answerD == null
				|| answerD.trim().length() < 1) {
			je.setMsg("ϵͳ��δ��ȡ����Ҫ��ӵ����� D �𰸣���¼��!");
			response.getWriter().print(je.toJSON());
			return;
		}

		List<List<Object>> objArrayList = new ArrayList<List<Object>>();
		List<String> sqlStrList = new ArrayList<String>();
		List<Object> objPara = new ArrayList<Object>();
		StringBuilder sqlbuilder = new StringBuilder();
		// //////////////////////////��Ӵ�(����ӵ�ʱ�� :item_id �����ڵ����⣬��REF������)
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

		je.setMsg("�쳣����ϵͳû�з��ֿ���ִ�е����!");
		// ��ʼִ��
		if (sqlStrList.size() > 0 && objArrayList.size() > 0) {
			boolean flag = this.appraiseItemManager.doExcetueArrayProc(
					sqlStrList, objArrayList);
			if (flag) {
				je.setType("success");
				je.setMsg("�����޸ĳɹ�!");
			} else
				je.setMsg("�޸�ʧ�ܣ�ԭ��δ֪!");
		}
		response.getWriter().print(je.toJSON());
	}

	/**
	 * ����
	 * 
	 * @return
	 * @throws Exception
	 * 
	 *             public void doYyong() throws Exception{ JsonEntity je=new
	 *             JsonEntity(); if(this.getModel().getPjyear()==null){
	 *             je.setMsg("�쳣����ϵͳ��δ��ȡ����Ҫ���õ���ݣ�������!");
	 *             response.getWriter().print(je.toJSON()); return ; }
	 *             if(this.getModel().getTargetidentitytype()==null){
	 *             je.setMsg("�쳣����ϵͳ��δ��ȡ����Ҫ���õ������������ݣ�������!");
	 *             response.getWriter().print(je.toJSON()); return ; } String
	 *             currentYear=this.getTermmanager().getMaxIdTerm().getYear();
	 * 
	 *             //��ѯ���е�Year List<AppraiseItemInfo>
	 *             appList=this.getAppitemmanager().getList(this.getModel(),
	 *             null); if(appList==null||appList.size()<1){
	 *             je.setMsg(this.getModel().getPjyear()+"�����û�з���������Ŀ����˶Ժ�����!");
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
	 *             je.setMsg("����'"+this.getModel().getPjyear()+"'�ɹ�!");
	 *             response.getWriter().print(je.toJSON());
	 * 
	 * 
	 *             } else{ je.setMsg("����ʧ�ܣ�ԭ��δ֪!");
	 *             response.getWriter().print(je.toJSON()); return ; } }else{
	 *             je.setMsg("�쳣����ϵͳ��δ��ȡ���������õ����ݣ�");
	 *             response.getWriter().print(je.toJSON()); return ; }
	 * 
	 *             }
	 */
}

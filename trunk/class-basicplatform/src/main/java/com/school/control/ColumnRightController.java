package com.school.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.ColumnManager;
import com.school.manager.ColumnRightManager;
import com.school.manager.ColumnRightPageRightManager;
import com.school.manager.inter.IColumnManager;
import com.school.manager.inter.IColumnRightManager;
import com.school.manager.inter.IColumnRightPageRightManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ColumnInfo;
import com.school.entity.ColumnRightInfo;
import com.school.entity.ColumnRightPageRightInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/columnright")
public class ColumnRightController extends BaseController<ColumnRightInfo>{
    private IColumnRightManager columnRightManager;
    private IColumnManager columnManager;
    private IColumnRightPageRightManager columnRightPageRightManager;
    public ColumnRightController(){
        this.columnRightManager=this.getManager(ColumnRightManager.class);
        this.columnManager=this.getManager(ColumnManager.class);
        this.columnRightPageRightManager=this.getManager(ColumnRightPageRightManager.class);
    }
	
	@RequestMapping(params="m=list",method=RequestMethod.GET)
	public ModelAndView getListBy(HttpServletRequest request,ModelMap mp) throws Exception{
//		List<PageRightInfo> pagerightList=this.pageRightManager.getList(null, null);
//		mp.put("prList", pagerightList);
		//�õ� ���е���Ŀ��
		ColumnInfo colInfo=new ColumnInfo();		
		List<ColumnInfo> colList=this.columnManager.getList(colInfo, null);
		mp.put("columnList", colList);
		return new ModelAndView("/columnright/columnpagelist",mp);		
	}
	
	/**
	 * AjaxList
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST) 
	public void toRightList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ColumnRightInfo pr = this.getParameter(request, ColumnRightInfo.class);
		PageResult presult=this.getPageResultParameter(request);
		presult.setOrderBy(" u.columnright_id DESC ");
		List<ColumnRightInfo> columnRightList = this.columnRightManager.getList(pr, presult);
		JsonEntity je = new JsonEntity();
		je.setType("success");
		presult.setList(columnRightList);
		je.setPresult(presult);
		response.getWriter().print(je.toJSON()); 
	}
	/**
	 * ��������ҳ
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=detail",method=RequestMethod.GET)
	public ModelAndView toDetail(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
		ColumnRightInfo crightInfo=this.getParameter(request, ColumnRightInfo.class);
		JsonEntity jEntity=new JsonEntity();
		if(crightInfo.getRef()==null||crightInfo.getRef().trim().length()<1){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jEntity.getAlertMsgAndBack());return null;
		}
		//�õ�����
		ColumnRightInfo crighttmp=new ColumnRightInfo();
		crighttmp.setRef(crightInfo.getRef());
		List<ColumnRightInfo> crightList=this.columnRightManager.getList(crighttmp, null);
		if(crightList==null||crightList.size()<1){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jEntity.getAlertMsgAndBack());return null;
		}
		mp.put("entity", crightList.get(0)); 
		//�õ����Ȩ��
//		ColumnRightPageRightInfo crprTmp=new ColumnRightPageRightInfo();
//		crprTmp.setColumnrightid(crightList.get(0).getColumnrightid());
//		List<ColumnRightPageRightInfo> crprList=this.columnRightPageRightManager.getList(crprTmp, null);
//		mp.put("crprList", crprList);
//		
		//�õ� ���е���Ŀ��
		ColumnInfo colInfo=new ColumnInfo();		
		List<ColumnInfo> colList=this.columnManager.getList(colInfo, null);
		mp.put("columnList", colList);
		return new ModelAndView("/columnright/detail",mp);
	}
	
	/**
	 * ������ĿȨ��REF�õ���ҳ��·��
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=getpageright",method=RequestMethod.POST)
	public void getPageRightColumnRight(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ColumnRightInfo crightInfo=this.getParameter(request, ColumnRightInfo.class);
		PageResult presult=this.getPageResultParameter(request);
		JsonEntity jEntity=new JsonEntity();
		if(crightInfo.getRef()==null||crightInfo.getRef().trim().length()<1){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jEntity.toJSON());return;
		}
		//�õ�����
		ColumnRightInfo crighttmp=new ColumnRightInfo();
		crighttmp.setRef(crightInfo.getRef());
		List<ColumnRightInfo> crightList=this.columnRightManager.getList(crighttmp, null);
		if(crightList==null||crightList.size()<1){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jEntity.toJSON());return;
		}
	
		//�õ����Ȩ��
		ColumnRightPageRightInfo crprTmp=new ColumnRightPageRightInfo();
		crprTmp.setColumnrightid(crightList.get(0).getColumnrightid());
		List<ColumnRightPageRightInfo> crprList=this.columnRightPageRightManager.getList(crprTmp, presult);
		jEntity.setType("success");
		//jEntity.setObjList(crprList);
		presult.setList(crprList);
		jEntity.setPresult(presult);
		response.getWriter().print(jEntity.toJSON());
	}
	
	/**
	 * ִ���޸�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doUpdate",method=RequestMethod.POST)
	public void doUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ColumnRightInfo cright=this.getParameter(request, ColumnRightInfo.class);
		JsonEntity jeEntity=new JsonEntity();
		if(cright.getRef()==null||cright.getRef().trim().length()<1
				||cright.getColumnid()==null||cright.getColumnrightname()==null){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//��֤�Ƿ񻹴��ڸ�����
		ColumnRightInfo crinfo=new ColumnRightInfo();
		crinfo.setRef(cright.getRef());
		List<ColumnRightInfo> crList=this.columnRightManager.getList(crinfo, null);
		if(crList==null||crList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//�ȶԣ����и���
		ColumnRightInfo currentCr=crList.get(0);
		if(cright.getColumnid()!=null)
			currentCr.setColumnid(cright.getColumnid());
		if(cright.getColumnrightname()!=null)
			currentCr.setColumnrightname(cright.getColumnrightname());
		currentCr.setMuserid(this.logined(request).getRef());
		//ִ�����
		if(this.columnRightManager.doUpdate(currentCr)){
			jeEntity.setType("success");
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
		}else
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		response.getWriter().print(jeEntity.toJSON());
	}

	
	/**
	 * ִ���޸�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doDel",method=RequestMethod.POST)
	public void doDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ColumnRightInfo cright=this.getParameter(request, ColumnRightInfo.class);
		JsonEntity jeEntity=new JsonEntity();
		if(cright.getRef()==null||cright.getRef().trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//��֤�Ƿ񻹴��ڸ�����
		ColumnRightInfo crinfo=new ColumnRightInfo();
		crinfo.setRef(cright.getRef());
		List<ColumnRightInfo> crList=this.columnRightManager.getList(crinfo, null);
		if(crList==null||crList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//�ȶԣ����и���
		ColumnRightInfo currentCr=new ColumnRightInfo();
		currentCr.setRef(cright.getRef());
		//ִ�����
		if(this.columnRightManager.doDelete(currentCr)){
			jeEntity.setType("success");
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
		}else
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		response.getWriter().print(jeEntity.toJSON());
	}
	/**
	 * ���
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doadd",method=RequestMethod.POST)
	public void doAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ColumnRightInfo cright=this.getParameter(request, ColumnRightInfo.class);
		JsonEntity jeEntity=new JsonEntity();
		//��֤����
		if(cright.getColumnid()==null||cright.getColumnrightname()==null||cright.getColumnrightname().trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//��֤��Ŀ�Ƿ����
		ColumnInfo colTmp=new ColumnInfo();
		colTmp.setColumnid(cright.getColumnid());
		List<ColumnInfo> colList=this.columnManager.getList(colTmp, null);
		if(colList==null||colList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		String nextRef=this.columnRightManager.getNextId();
		cright.setRef(nextRef);
		if(this.columnRightManager.doSave(cright)){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			jeEntity.setType("success");
			jeEntity.getObjList().add(nextRef);
		}else
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		
		response.getWriter().print(jeEntity.toJSON());
	}
	/**
	 * ִ���޸�ҳ��Ȩ��
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doUpdateRight",method=RequestMethod.POST)
	public void doUpdateRight(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//�õ�����
		ColumnRightInfo colRightInfo=this.getParameter(request, ColumnRightInfo.class);
		//�õ�pagerightarr
		String pagerightidArr=request.getParameter("pagerightidarr"); //���ƣ� 1,2,3,4,5,6,7,8,9
		JsonEntity jeEntity=new JsonEntity();
		//��֤����
		if(colRightInfo.getColumnrightid()==null||
				pagerightidArr==null||pagerightidArr.trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//��֤columnrightid�Ƿ����
		ColumnRightInfo crinfo=new ColumnRightInfo();
		crinfo.setColumnrightid(colRightInfo.getColumnrightid());
		List<ColumnRightInfo> crList=this.columnRightManager.getList(crinfo, null);
		if(crList==null||crList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		Integer columnid=crList.get(0).getColumnid();
		
		List<String> sqlList=new ArrayList<String>();
		List<List<Object>> listObjArr=new ArrayList<List<Object>>();
		StringBuilder sqlbuilder=null;
		List<Object> objList=null;
		
		//����pagerightid
		String[] pagerightArray=pagerightidArr.split(",");
		//��һ�Σ���ɾ�����е���ĿȨ�޹�ϵ��
		ColumnRightPageRightInfo crprtmp=new ColumnRightPageRightInfo();
		crprtmp.setColumnrightid(colRightInfo.getColumnrightid());
		sqlbuilder=new StringBuilder();
		objList=this.columnRightPageRightManager.getDeleteSql(crprtmp, sqlbuilder);
		if(sqlbuilder.toString().trim().length()>0){
			sqlList.add(sqlbuilder.toString());
			listObjArr.add(objList);
		}
		//��֯�����õ���־��¼
		sqlbuilder=new StringBuilder();
		objList=this.columnRightPageRightManager.getAddOperateLog(this.logined(request).getRef()
				,"j_columnright_pageright_info",colRightInfo.getColumnrightid()+"", null, null, "(��ĿȨ�ޱ༭ҳ��Ȩ��)ɾ��"
				, this.logined(request).getRealname()+"����columnrightid("+colRightInfo.getColumnrightid()+")ɾ��"
				, sqlbuilder);
		if(sqlbuilder.toString().trim().length()>0){
			sqlList.add(sqlbuilder.toString());
			listObjArr.add(objList);
		}
		
		for (String pridStr:pagerightArray){	
				//String pridStr : pagerightArray) {
			if(pridStr==null||pridStr.trim().length()<1||!UtilTool.isNumber(pridStr.trim()))continue;
			String nextRef=this.columnRightPageRightManager.getNextId();
			ColumnRightPageRightInfo crprInfo=new ColumnRightPageRightInfo();
			crprInfo.setColumnrightid(colRightInfo.getColumnrightid());
			crprInfo.setPagerightid(Integer.parseInt(pridStr.trim()));
			crprInfo.setRef(nextRef);
			crprInfo.setColumnid(columnid);
			crprInfo.setMuserid(this.logined(request).getRef());
			//��֯�õ�SQL��������
			sqlbuilder=new StringBuilder();
			objList=this.columnRightPageRightManager.getSaveSql(crprInfo, sqlbuilder);
			if(sqlbuilder.toString().trim().length()>0){
				sqlList.add(sqlbuilder.toString());
				listObjArr.add(objList);
			}
			//��֯�����õ���־��¼
			sqlbuilder=new StringBuilder();
			objList=this.columnRightPageRightManager.getAddOperateLog(this.logined(request).getRef()
					,"j_columnright_pageright_info", nextRef, null, null, "���", this.logined(request).getRealname()+"����û�", sqlbuilder);
			if(sqlbuilder.toString().trim().length()>0){
				sqlList.add(sqlbuilder.toString());
				listObjArr.add(objList);
			}
		}
		//ɾ��
		if(sqlList!=null&&sqlList.size()>0&&listObjArr!=null&&listObjArr.size()>0&&listObjArr.size()==sqlList.size()){
			if(this.columnRightPageRightManager.doExcetueArrayProc(sqlList, listObjArr)){
				jeEntity.setType("success");
				jeEntity.setMsg("�����ɹ�!");
			}else
				jeEntity.setMsg("����ʧ�ܣ�ԭ��δ֪!");
		}else
			jeEntity.setMsg("�쳣����û�пɲ���������!");
		response.getWriter().print(jeEntity.toJSON());
	}
}







package com.school.control.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.inter.resource.ICheckManager;
import com.school.manager.inter.resource.IExtendManager;
import com.school.manager.resource.CheckManager;
import com.school.manager.resource.ExtendManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.school.control.base.BaseController;
import com.school.entity.resource.CheckInfo;
import com.school.entity.resource.ExtendInfo;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
/**
 * 
 * @author zhengzhou
 *
 */
@Controller
@RequestMapping(value="/check")
public class CheckController extends BaseController<CheckInfo> {
    private IExtendManager extendManager;
    private ICheckManager checkManager;
    public CheckController(){
        this.extendManager=this.getManager(ExtendManager.class);
        this.checkManager=this.getManager(CheckManager.class);
    }
	/**
	 * 进入审核页面
	 * @return
	 */
	@RequestMapping(params="m=tocheckPage",method=RequestMethod.GET)
	public ModelAndView toCheckPage(HttpServletRequest request,ModelMap mp){		
		//JsonEntity jeEntity=new JsonEntity();
		ExtendInfo extendInfo=new ExtendInfo();
		extendInfo.setCheck(1);
		List<ExtendInfo> extendList=this.extendManager.getList(extendInfo,null);
		List<Map<String, Object>> objListMaps=new ArrayList<Map<String,Object>>();
		//extendList
		if(extendList!=null&&extendList.size()>0){
			for (ExtendInfo edtmp : extendList) {
				List<Map<String, Object>> objMapList=this.extendManager.getExtendCheckShu(edtmp.getExtendid());
				if(objMapList!=null&&objMapList.size()>0)
					objListMaps.addAll(objMapList);
			}
		}
		mp.put("extendList", extendList);
		mp.put("extendValList", objListMaps);
		return new ModelAndView("/resource/check/check",mp);
	}
	/**
	 * 得到现有的审核人。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=getcheckteacher",method=RequestMethod.POST)
	public void getCheckTeacher(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		CheckInfo ckInfo=this.getParameter(request, CheckInfo.class);		
		//进行查询
		List<CheckInfo> ckList=this.checkManager.getList(ckInfo,null);
		jeEntity.setObjList(ckList);
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
	}
	/**
	 * 执行AddCheck
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doAddCheck",method=RequestMethod.POST)
	public void doAddCheck(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		CheckInfo ckInfo=this.getParameter(request, CheckInfo.class);
		
		String uArrayId=request.getParameter("userArrayId");
//		if(ckInfo.getValueid()==null||ckInfo.getValueid().trim().length()<1){
//			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(jeEntity.toJSON());return;
//		}			
		List<List<Object>> objParamList=new ArrayList<List<Object>>();
		List<String> sqlList=new ArrayList<String>();
		
		StringBuilder sqlbuilder;
		List<Object> objList;

		//清除
		sqlbuilder=new StringBuilder();
		CheckInfo cktmp;
	
		if(uArrayId!=null){
			//清空
			if(uArrayId.trim().length()<1&&ckInfo.getValueid()!=null&&ckInfo.getValueid().length()>0){
				cktmp=new CheckInfo();
				cktmp.setValueid(ckInfo.getValueid());
				objList=this.checkManager.getDeleteSql(cktmp, sqlbuilder);
				if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
					sqlList.add(sqlbuilder.toString());
					objParamList.add(objList);
				}
			}else{
				
				//添加
				String[] uIdArray=uArrayId.split(",");
				if(uIdArray==null||uIdArray.length<1){
					jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
					response.getWriter().print(jeEntity.toJSON());return;
				}
				cktmp=new CheckInfo();
				cktmp.setValueid(ckInfo.getValueid());
				objList=this.checkManager.getDeleteSql(cktmp, sqlbuilder);
				if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
					sqlList.add(sqlbuilder.toString());
					objParamList.add(objList);
				}
				for (String uidtmp : uIdArray) {
					if(uidtmp.trim().length()<1)
						continue;
					cktmp=new CheckInfo();
					cktmp.setRef(this.checkManager.getNextId());
					cktmp.setValueid(ckInfo.getValueid());
					cktmp.setUserid(uidtmp);
					cktmp.setOperateuserid(this.logined(request).getRef());
					cktmp.setOperaterealname(this.logined(request).getRealname());
					sqlbuilder=new StringBuilder();
					objList=this.checkManager.getSaveSql(cktmp, sqlbuilder);
					if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
						sqlList.add(sqlbuilder.toString());
						objParamList.add(objList);
					}		
				}
			}
		}else if(ckInfo.getValueid()!=null){
			if(ckInfo.getUserid()==null||ckInfo.getUserid().trim().length()<1){
				jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
				response.getWriter().print(jeEntity.toJSON());return;
			}
			cktmp=new CheckInfo();
			cktmp.setUserid(ckInfo.getUserid());
			objList=this.checkManager.getDeleteSql(cktmp, sqlbuilder);
			if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
				sqlList.add(sqlbuilder.toString());
				objParamList.add(objList);
			}
			
			String valArr[]=ckInfo.getValueid().split(",");
			for (String valTmp : valArr) {
				if(valTmp==null||valTmp.trim().length()<1)
					continue;
				cktmp=new CheckInfo();
				cktmp.setValueid(valTmp.trim());
				cktmp.setUserid(ckInfo.getUserid());
				cktmp.setOperateuserid(this.logined(request).getRef());
				cktmp.setOperaterealname(this.logined(request).getRealname());
				cktmp.setRef(this.checkManager.getNextId());
				sqlbuilder=new StringBuilder();
				objList=this.checkManager.getSaveSql(cktmp, sqlbuilder);
				if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
					sqlList.add(sqlbuilder.toString());
					objParamList.add(objList);
				}
			}			
		}
		if(sqlList==null||sqlList.size()<1||objParamList==null||objParamList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
		}else{
			if(this.checkManager.doExcetueArrayProc(sqlList, objParamList)){
				jeEntity.setType("success");
				jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			}else
				jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));			
		}
		response.getWriter().print(jeEntity.toJSON());
	}
	@RequestMapping(params="m=getNodeByUserid",method=RequestMethod.POST)
	public void getNodeByUserId(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String userid=request.getParameter("userid");
		JsonEntity jeEntity=new JsonEntity();
		if(userid==null||userid.trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		CheckInfo ckInfo=new CheckInfo();
		ckInfo.setUserid(userid);
		List<CheckInfo> ckList=this.checkManager.getList(ckInfo,null);
		jeEntity.setType("success");
		jeEntity.setObjList(ckList);
		response.getWriter().print(jeEntity.toJSON());
	}
}

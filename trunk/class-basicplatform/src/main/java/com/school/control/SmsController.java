package com.school.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.SmsManager;
import com.school.manager.SmsReceiverManager;
import com.school.manager.UserManager;
import com.school.manager.inter.ISmsManager;
import com.school.manager.inter.ISmsReceiverManager;
import com.school.manager.inter.IUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.SmsInfo;
import com.school.entity.SmsReceiver;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/sms")
public class SmsController extends BaseController<SmsInfo> {
    private ISmsReceiverManager smsReceiverManager;
    private ISmsManager smsManager;
    private IUserManager userManager;
    public SmsController(){
        this.smsReceiverManager=this.getManager(SmsReceiverManager.class);
        this.smsManager=this.getManager(SmsManager.class);
        this.userManager=this.getManager(UserManager.class);
    }

	
	@RequestMapping(params="m=inbox",method=RequestMethod.GET)
	protected ModelAndView getListBy(HttpServletRequest request,ModelMap mp) throws Exception{	
		/*List<SmsInfo> smsList=smsManager.getList(null, null);
		mp.put("smsList", smsList);*/
		return new ModelAndView("/sms/inbox",mp);		
	}
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxGetReceiveList",method=RequestMethod.POST)
	public void AjaxGetReceiveList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		SmsInfo smsinfo = this.getParameter(request, SmsInfo.class);
		SmsReceiver smsreceiver = new SmsReceiver();
		if(smsinfo.getSmstitle()!=null&&!smsinfo.getSmstitle().trim().equals(""))
			smsreceiver.setSmstitle(smsinfo.getSmstitle());
		UserInfo usr=this.logined(request);
		if(null!=usr)
			smsreceiver.setReceiverid(usr.getUserid());
		else
			smsreceiver.setReceiverid(0);
		PageResult pageresult = this.getPageResultParameter(request);
		List<SmsReceiver> receiveSMSList =smsReceiverManager.getList(smsreceiver, pageresult);
		pageresult.setList(receiveSMSList);
		JsonEntity je = new JsonEntity();
		je.setType("success");
		je.setPresult(pageresult);
		response.getWriter().print(je.toJSON()); 
	}
	
	@RequestMapping(params="m=ajaxGetSentList",method=RequestMethod.POST)
	public void AjaxGetSentList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		SmsInfo smsinfo = this.getParameter(request, SmsInfo.class);
		UserInfo usr=this.logined(request);
		if(null!=usr)
			smsinfo.setSenderid(usr.getUserid());
		else
			smsinfo.setSenderid(0);
		smsinfo.setSmsstatus(SmsInfo.STATUS_SEND_SUCCESS);
		PageResult pageresult = this.getPageResultParameter(request);
		List<SmsInfo> smsList =smsManager.getList(smsinfo, pageresult);
		pageresult.setList(smsList);
		JsonEntity je = new JsonEntity();
		je.setType("success");
		je.setPresult(pageresult);
		response.getWriter().print(je.toJSON()); 
	}
	
	@RequestMapping(params="m=delBySmsid",method=RequestMethod.POST)
	public void toSetPageRightByUserid(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String smsidstr=request.getParameter("smsidstr");
		
		JsonEntity je= new JsonEntity();
		if(smsidstr==null||smsidstr.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		List<String>sqlListArray=new ArrayList<String>();
		List<List<Object>>objListArray=new ArrayList<List<Object>>();
		StringBuilder sql=null;
		List<Object>objList=null;
		
		String[]smsidArray=smsidstr.split(",");
		for (String smsid : smsidArray){
			SmsInfo smsInfo=new SmsInfo();
			smsInfo.setSmsid(Integer.parseInt(smsid));
			sql=new StringBuilder();
			objList=this.smsManager.getDeleteSql(smsInfo, sql);
			if(objList!=null&&objList.size()>0&&sql!=null&&sql.length()>0){
				sqlListArray.add(sql.toString());
				objListArray.add(objList);
			}
		}
		
		if(sqlListArray.size()>0&&objListArray.size()>0){
			boolean flag=this.smsManager.doExcetueArrayProc(sqlListArray, objListArray);
			if(flag){
				je.setType("success");	 
				je.setMsg("操作成功！");
			}else{
				je.setMsg("操作失败！");
			}
		}else{
			je.setMsg("您的操作没有执行！");
		}
		response.getWriter().print(je.toJSON());
	}
	
	@RequestMapping(params="m=write",method=RequestMethod.GET)
	protected ModelAndView writeSms(HttpServletRequest request,HttpServletRequest response,ModelMap mp) throws Exception{	
		String smsid=request.getParameter("smsid");
		String type=request.getParameter("type");
		if(smsid!=null&&smsid.trim().length()>0){
			SmsInfo s=new SmsInfo();
			s.setSmsid(Integer.parseInt(smsid));
			List<SmsInfo>smsList=this.smsManager.getList(s, null);
			if(smsList!=null&&smsList.size()>0){
				SmsInfo si = smsList.get(0);
				if(type!=null&&type.equals("reply")){
					si.setSmsid(0);
					si.setReceiverlist(si.getSendername());
					StringBuilder rpContent=new StringBuilder("<br/><br/>");
					rpContent.append("<br/>-----原始邮件 -----------------------------------");
					rpContent.append("<br/>发件人："+si.getSendername());
					rpContent.append("<br/>发送时间："+si.getCtime());
					rpContent.append("<br/>主题："+si.getSmstitle());
					rpContent.append("<br/>内容："+si.getSmscontent());
					if(!si.getSmstitle().substring(0, 3).equals("回复："))
						si.setSmstitle("回复："+si.getSmstitle());
					si.setSmscontent(rpContent.toString());
				}
				
				if(type!=null&&type.equals("forward")){
					si.setReceiverlist("");
				}
				mp.put("smsInfo", si);
			}
		}
		return new ModelAndView("/sms/write",mp);	
	}
	/**
	 * "------------"
	 * @param request    
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitAddRole(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
		JsonEntity je = new JsonEntity();
		SmsInfo smsinfo = this.getParameter(request, SmsInfo.class);
		String receiverstr=smsinfo.getReceiverlist();
		if(receiverstr==null||receiverstr.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}else if(smsinfo.getSmstitle()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLENAME_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return ;
		}else if(smsinfo.getSmscontent()==null){
     		je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLENAME_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return ;
		}
		String[] receiverArray=receiverstr.split(";");
		Set<String> receiverSet = new HashSet<String>();
		List<Integer> receiverList= new ArrayList<Integer>();
		List<String> errReceiverList= new ArrayList<String>();
		UserInfo receiver = null;
		for (String receiverName : receiverArray){
			if(receiverName!=null&&receiverName.trim().length()>0)
			 receiverSet.add(receiverName);
		}
		for (String receiverName : receiverSet){
			receiver = new UserInfo();
			receiver.setUsername(receiverName);
			receiver = userManager.getUserInfo(receiver);
			if(receiver!=null&&receiver.getUserid()!=null)
				receiverList.add(receiver.getUserid());
			else
				errReceiverList.add(receiverName);
		}
		if(errReceiverList.size()>0){
			je.setType("error1");
			je.setMsg("无法找到收件人!");
			je.setObjList(errReceiverList);
			response.getWriter().print(je.toJSON());
			return;
		}
		UserInfo usr=this.logined(request);
		if(null!=usr){
			smsinfo.setSenderid(usr.getUserid());
		}
		Integer smsId=null;
				
		if(smsinfo.getSmsid()<1)
			smsId=this.smsManager.doSaveGetId(smsinfo);
		else{
			smsManager.doUpdate(smsinfo);
			smsId=smsinfo.getSmsid();
		}
		if(smsId==null||smsId<1){
			je.setType("error2");
			je.setMsg("发送失败!");
			response.getWriter().print(je.toJSON());
			return;
		}
		
		List<String> sqlListArray = new ArrayList<String>();
		List<List<Object>> objListArray = new ArrayList<List<Object>>();
		StringBuilder sql = null;
		List<Object> objList = null;
		for(Integer receiverId:receiverList){
			sql=new StringBuilder();
			SmsReceiver smsreceiver = new SmsReceiver();
			smsreceiver.setReceiverid(receiverId);
			smsreceiver.setSmsid(smsId);
			objList=this.smsReceiverManager.getSaveSql(smsreceiver, sql);
			sqlListArray.add(sql.toString());
			objListArray.add(objList);
		}
		if(sqlListArray.size()>0&&objListArray.size()>0){
			boolean flag=this.smsReceiverManager.doExcetueArrayProc(sqlListArray, objListArray);
			if(flag){
				je.setMsg("发送成功！");
				je.setType("success");
				smsinfo = new SmsInfo();
				smsinfo.setSmsid(smsId);
				smsinfo.setSmsstatus(SmsInfo.STATUS_SEND_SUCCESS);
				smsManager.doUpdate(smsinfo);
			}else{
				je.setMsg("发送失败，请重试！");
				smsinfo = new SmsInfo();
				smsinfo.setSmsid(smsId);
				smsinfo.setSmsstatus(SmsInfo.STATUS_SEND_ERROR);
				smsManager.doUpdate(smsinfo);
			}
		}else{
			je.setMsg("您的操作没有执行！");
		}
		response.getWriter().print(je.toJSON());
		
	}  
	
	/**
	 * "------------"
	 * @param request    
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsavetodraft",method=RequestMethod.POST)
	public void savetoDraft(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
		JsonEntity je = new JsonEntity();
		SmsInfo smsinfo = this.getParameter(request, SmsInfo.class);
		String receiverstr=smsinfo.getReceiverlist();
		
		if(receiverstr==null||receiverstr.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}else if(smsinfo.getSmstitle()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLENAME_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return ;
		}else if(smsinfo.getSmscontent()==null){
     		je.setMsg(UtilTool.msgproperty.getProperty("ROLE_ROLENAME_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return ;
		}
				
		UserInfo usr=this.logined(request);
		if(null!=usr){
			smsinfo.setSenderid(usr.getUserid());
		}
		if(smsManager.doSave(smsinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
    	response.getWriter().print(je.toJSON()); 
    	
	}  
	@RequestMapping(params="m=ajaxupdtodraft",method=RequestMethod.POST)
	public void updtoDraft(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
		JsonEntity je = new JsonEntity();
		SmsInfo smsinfo = this.getParameter(request, SmsInfo.class);
		if(smsManager.doUpdate(smsinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
    	response.getWriter().print(je.toJSON()); 
    	
	} 
	
		@RequestMapping(params="m=draft",method=RequestMethod.GET)
		protected ModelAndView getDraftListBy(HttpServletRequest request,ModelMap mp) throws Exception{	
			return new ModelAndView("/sms/draft",mp);		
		}
		
		@RequestMapping(params="m=sent",method=RequestMethod.GET)
		protected ModelAndView getSentListBy(HttpServletRequest request,ModelMap mp) throws Exception{	
			return new ModelAndView("/sms/sent",mp);		
		}
		/**
		 * "------------"
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		@RequestMapping(params="m=ajaxdraftlist",method=RequestMethod.POST)
		public void AjaxGetDraftList(HttpServletRequest request,HttpServletResponse response)throws Exception{
			SmsInfo smsinfo = new SmsInfo();
			UserInfo usr=this.logined(request);
			if(null!=usr)
				smsinfo.setSenderid(usr.getUserid());
			else
				smsinfo.setSenderid(0);
			smsinfo.setSmsstatus(SmsInfo.STATUS_DRAFT);
			PageResult pageresult = this.getPageResultParameter(request);
			List<SmsInfo> smsList =smsManager.getList(smsinfo, pageresult);
			pageresult.setList(smsList);
			JsonEntity je = new JsonEntity();
			je.setType("success");
			je.setPresult(pageresult);
			response.getWriter().print(je.toJSON()); 
			
		}                                          
		
		@RequestMapping(params="m=writelist",method=RequestMethod.POST)
		public void getWriteList(HttpServletRequest request,HttpServletResponse response)throws Exception{
			SmsInfo smsinfo=new SmsInfo();
			String smsid=request.getParameter("smsid");
			JsonEntity je = new JsonEntity();
			if(smsid==null||smsid.trim().length()<1){
				je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
				response.getWriter().print(je.toJSON());
				return;
			}
			smsinfo.setSmsid(Integer.valueOf(smsid));
			List<SmsInfo> smsList =smsManager.getList(smsinfo, null);
			je.setObjList(smsList); 
			je.setType("success");
			response.getWriter().print(je.toJSON()); 
		}
		
	@RequestMapping(params = "m=viewReceiverSMS", method = RequestMethod.GET)
	public ModelAndView viewReceiverSMS(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {
		SmsReceiver sr = new SmsReceiver();
		sr.setRef(Integer.parseInt(request.getParameter("ref")));
		if (sr.getRef() != null && sr.getRef() > 0) {
			List<SmsReceiver> smsList = smsReceiverManager.getList(sr, null);
			if (smsList != null && smsList.size() > 0){
				mp.put("sms_ref", sr.getRef());
				mp.put("smsinfo", smsList.get(0));
				mp.put("type", 1);
				sr.setStatus(1);
				smsReceiverManager.doUpdate(sr);
				
			}
			return new ModelAndView("/sms/smsView", mp);
		} else {
			JsonEntity je = new JsonEntity();
			je.setType("success");
			je.setMsg("无法找到邮件！");
			response.getWriter().print(je.toJSON());
			return null;
		}
	}
	
	@RequestMapping(params = "m=viewSentSMS", method = RequestMethod.GET)
	public ModelAndView viewSentSMS(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {
		SmsInfo sr = new SmsInfo();
		sr.setSmsid(Integer.parseInt(request.getParameter("smsid")));
		if (sr.getSmsid() != null && sr.getSmsid() > 0) {
			List<SmsInfo> smsList = smsManager.getList(sr, null);
			if (smsList != null && smsList.size() > 0){
				mp.put("smsinfo", smsList.get(0));
				mp.put("type", 2);
			}
			return new ModelAndView("/sms/smsView", mp);
		} else {
			JsonEntity je = new JsonEntity();
			je.setType("success");
			je.setMsg("无法找到邮件！");
			response.getWriter().print(je.toJSON());
			return null;
		}
	}
	
	@RequestMapping(params="m=deleteReceiveSMS",method=RequestMethod.POST)
	public void deleteReceiveSMS(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		
		String smsids = request.getParameter("smsids");
		System.out.println(smsids);
		if(smsids!=null){
			String[] smsid=smsids.split("\\|");
			List<String> sqlListArray = new ArrayList<String>();
			List<List<Object>> objListArray = new ArrayList<List<Object>>();
			StringBuilder sql = null;
			List<Object> objList = null;
			for(String ref:smsid){
				sql=new StringBuilder();
				SmsReceiver smsreceiver = new SmsReceiver();
				smsreceiver.setRef(Integer.parseInt(ref));
				objList=this.smsReceiverManager.getDeleteSql(smsreceiver, sql);
				sqlListArray.add(sql.toString());
				objListArray.add(objList);
			}
			if(smsReceiverManager.doExcetueArrayProc(sqlListArray, objListArray))
				je.setType("success");
		}
		response.getWriter().print(je.toJSON()); 
	}
	
	@RequestMapping(params="m=deleteDraftSMS",method=RequestMethod.POST)
	public void deleteDraftSMS(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		SmsInfo smsinfo = this.getParameter(request, SmsInfo.class);
		if(smsinfo!=null&&smsinfo.getSmsid()!=null){
			if(smsManager.doDelete(smsinfo))
				je.setType("success");
		}
		response.getWriter().print(je.toJSON()); 
	}
	
	@RequestMapping(params="m=deleteSentSMS",method=RequestMethod.POST)
	public void deleteSentSMS(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String smsids = request.getParameter("smsids");
		if(smsids!=null){
			String[] smsid=smsids.split("\\|");
			List<String> sqlListArray = new ArrayList<String>();
			List<List<Object>> objListArray = new ArrayList<List<Object>>();
			StringBuilder sql = null;
			List<Object> objList = null;
			for(String sid:smsid){
				sql=new StringBuilder();
				SmsInfo smsinfo = new SmsInfo();
				smsinfo.setSmsid(Integer.parseInt(sid));
				smsinfo.setSmsstatus(SmsInfo.STATUS_DELETED);
				objList=this.smsManager.getUpdateSql(smsinfo, sql);
				sqlListArray.add(sql.toString());
				objListArray.add(objList);
			}
			if(smsReceiverManager.doExcetueArrayProc(sqlListArray, objListArray))
				je.setType("success");
		}
		response.getWriter().print(je.toJSON()); 
	}
}

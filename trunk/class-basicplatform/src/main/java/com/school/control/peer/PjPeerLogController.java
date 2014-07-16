package com.school.control.peer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.inter.peer.IPjPeerBaseManager;
import com.school.manager.inter.peer.IPjPeerItemManager;
import com.school.manager.inter.peer.IPjPeerLogManager;
import com.school.manager.inter.peer.IPjPeerUserManager;
import com.school.manager.peer.PjPeerBaseManager;
import com.school.manager.peer.PjPeerItemManager;
import com.school.manager.peer.PjPeerLogManager;
import com.school.manager.peer.PjPeerUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.DeptInfo;
import com.school.entity.DeptUser;
import com.school.entity.RoleUser;
import com.school.entity.peer.PjPeerBaseInfo;
import com.school.entity.peer.PjPeerItemInfo;
import com.school.entity.peer.PjPeerLogInfo;
import com.school.entity.peer.PjPeerUserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;
/**
 * @author ������
 * @description ͬ����������������controller
 */
@Controller
@RequestMapping(value="/peerlog")
public class PjPeerLogController extends BaseController<PjPeerLogInfo> {
    private IPjPeerBaseManager pjPeerBaseManager;
    private IPjPeerUserManager pjPeerUserManager;
    private IPjPeerItemManager pjPeerItemManager;
    private IPjPeerLogManager pjPeerLogManager;

    public PjPeerLogController() {
        this.pjPeerBaseManager = this.getManager(PjPeerBaseManager.class);
        this.pjPeerUserManager = this.getManager(PjPeerUserManager.class);
        this.pjPeerItemManager = this.getManager(PjPeerItemManager.class);
        this.pjPeerLogManager = this.getManager(PjPeerLogManager.class);
    }

    /**
	 * @author ������
	 * @description ͬ�����۽���ְ������ҳ��
	 */
	@RequestMapping(params="m=dolist",method=RequestMethod.GET) 
	public ModelAndView toPjPeerDoList(HttpServletRequest request,ModelAndView mp )throws Exception{
		return new ModelAndView("/peer/pj_peer_do");  
	}
	/**
	 * @author ������
	 * @description ͬ�����۽���ְ������ҳ��
	 */
	@RequestMapping(params="m=jslist",method=RequestMethod.GET) 
	public ModelAndView toPjPeerZgList(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		JsonEntity je = new JsonEntity();
		String peerbaseref = "";
		String ptype = "";
		String deptid = "";
		ptype=request.getParameter("ptype");
		//��ȡ����id
		PageResult presult = this.getPageResultParameter(request);
		PjPeerBaseInfo pb = new PjPeerBaseInfo();
		Date dt=new Date();//�������Ҫ��ʽ,��ֱ����dt,dt���ǵ�ǰϵͳʱ��
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//������ʾ��ʽ
        String nowTime="";
        nowTime= df.format(dt);
        Date d =UtilTool.StringConvertToDate(nowTime);
        pb.setNtime(d);
        presult.setSort("pj.c_time desc");
		List<PjPeerBaseInfo> pbList = this.pjPeerBaseManager.getList(pb, presult);
		if(pbList!=null&&pbList.size()==1){
			peerbaseref=pbList.get(0).getRef();
			request.setAttribute("year", pbList.get(0).getYear());
		}else{
			je.setMsg("��ǰδ�鵽�������û�������ϵ����Ա");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		//peerbaseref=pbList.get(2).getRef();
		//��ȡ�û��������
		PjPeerUserInfo pjuser = new PjPeerUserInfo();
		pjuser.setUserid(this.logined(request).getRef());		
		pjuser.setPeerbaseid(peerbaseref);		
		List<PjPeerUserInfo> pjuserList = this.pjPeerUserManager.getList(pjuser, null);
		if(pjuserList!=null&pjuserList.size()==1){
			deptid=pjuserList.get(0).getDeptid().toString();
			if(ptype==null||ptype.length()==0){
				request.setAttribute("ptype", pjuserList.get(0).getPtype());
				ptype = pjuserList.get(0).getPtype().toString();
			}
			String typename="";
			if(ptype.equals("1")){
				typename="��ʦ����";
			}else if(ptype.equals("2")){
				typename="ְ������";
			}
			request.setAttribute("typename", typename);
		}else{
			je.setMsg("δ�鵽��ǰ�û����������ȷ�ϵ�ǰ�û��Ѳ�������");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}		
		PjPeerItemInfo itemobj = new PjPeerItemInfo();
		itemobj.setPeerbaseref(peerbaseref);
		itemobj.setPtype(Integer.parseInt(ptype));
		List<PjPeerItemInfo> peerItemList = this.pjPeerItemManager.getList(itemobj, null);
		//��ʼ��֯����
		List<PjPeerItemInfo> parentitemList=new ArrayList<PjPeerItemInfo>();		//���ڵ�
		List<PjPeerItemInfo> childitemList=new ArrayList<PjPeerItemInfo>();			//�ӽڵ㼯��
		
		if(peerItemList==null||peerItemList.size()<1){
			je.setMsg("ϵͳ��δ��ȡ���������۵���Ŀ����������ܽ������ۣ��������ʣ�����ϵ��ؽ�ʦ! ������룺peeritem is empty!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		//��������ݷֲ�
		for (PjPeerItemInfo tmpMap : peerItemList) {
			if(tmpMap!=null)
				if(tmpMap.getParentref().toString().trim().equals("0"))
					parentitemList.add(tmpMap);
				else
					childitemList.add(tmpMap);			
		}
		if(parentitemList.size()<1){
			je.setMsg("ϵͳ��δ��ȡ�����������۴���������ʣ�����ϵ��ؽ�ʦ!������룺parentitem is empty!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		pjuser = new PjPeerUserInfo();
		pjuser.setPeerbaseid(peerbaseref);
		pjuser.setPtype(Integer.parseInt(ptype));
		pjuser.setDeptid(Integer.parseInt(deptid));
		List<PjPeerUserInfo> experimenterList = this.pjPeerUserManager.getList(pjuser, null);
//		for(PjPeerUserInfo pj :experimenterList){
//			if(pj.getUserinfo().getRef().equals(this.logined(request).getRef()))
//				experimenterList.remove(pj);
//		}
		for(int i = 0;i<experimenterList.size();i++){
			if(experimenterList.get(i).getUserinfo().getRef().equals(this.logined(request).getRef())){
				experimenterList.remove(i);
				break;
			}
		}
		PjPeerLogInfo logobj = new PjPeerLogInfo();
		logobj.setPeerbaseref(peerbaseref);
		logobj.setPtype(Integer.parseInt(ptype));
		logobj.setCuserid(this.logined(request).getRef());
		List<PjPeerLogInfo> peerlogList = this.pjPeerLogManager.getList(logobj, null);
		request.setAttribute("peerlogList", peerlogList);
		request.setAttribute("experimenterList", experimenterList);
		request.setAttribute("peerbaseref", peerbaseref);
		request.setAttribute("parentitemList", parentitemList);
		request.setAttribute("childitemList", childitemList);
		request.setAttribute("remark", parentitemList.get(0).getRemark());
        request.setAttribute("p_type",ptype);
		if(Integer.parseInt(ptype)==1){
			return new ModelAndView("/peer/pj_peer_do_js_list");  
		}else{
			return new ModelAndView("/peer/pj_peer_do_zg_list"); 
		}
	}
	
	/**
	 * @author ������
	 * @throws Exception 
	 * @description ִ���������
	 */
	@RequestMapping(params="m=dosave",method=RequestMethod.POST)
	public void doAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String param = request.getParameter("paramStr");
		String peerbaseref = request.getParameter("peerbaseref");
		String ptype = request.getParameter("ptype");
		String[] params =param.split("&");
		if(params!=null&params.length>0){
			//�����ȸ�������id������������ɾ������Ϊ�п������޸�			
			PjPeerLogInfo obj = null;
			obj = new PjPeerLogInfo();
			obj.setPeerbaseref(peerbaseref);
			obj.setPtype(Integer.parseInt(ptype));
			Boolean bl = this.pjPeerLogManager.doDelete(obj);
			//���ֵ�����ݼ�����
			List<String> sqlArrayList = new ArrayList<String>();
			//���sql�ļ���
			List<List<Object>> objArrayList = new ArrayList<List<Object>>();
		    //ƴsql�Ķ���ʹ��ֵ�ö���
			List<Object> objList;
			StringBuilder sql ;	
			Boolean b = false;
			for(int i = 0;i<params.length;i++){
				//��ȡÿһ������һ���������֡���0��������id  1����������id  2������
				String[] objStr = params[i].split("\\|");
				obj = new PjPeerLogInfo();
				obj.setPeeritemref(Integer.parseInt(objStr[0]));
				obj.setUserid(objStr[1]);
				obj.setScore(Integer.parseInt(objStr[2]));
				obj.setCuserid(this.logined(request).getRef());
				obj.setPeerbaseref(peerbaseref);
				obj.setPtype(Integer.parseInt(ptype));
				objList = new ArrayList<Object>();
				sql = new StringBuilder();
				objList = this.pjPeerLogManager.getSaveSql(obj, sql);
				sqlArrayList.add(sql.toString());
				objArrayList.add(objList);
			}
			b = this.pjPeerItemManager.doExcetueArrayProc(sqlArrayList, objArrayList);
			if(b){			
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
				je.setType("success");
			}else{
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * @author ������
	 * @description ͬ�����۽��벿���쵼��ѯҳ��
	 */
	@RequestMapping(params="m=todeptlogstat",method=RequestMethod.GET) 
	public ModelAndView toPjPeerLogDeptStat(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		PjPeerBaseInfo obj = new PjPeerBaseInfo();
		List<PjPeerBaseInfo> baseList = this.pjPeerBaseManager.getList(obj, null);
		List<DeptUser> deptuser = this.logined(request).getDeptUsers();
		String deptid = "";
		if(deptuser!=null&&deptuser.size()>0){
			for(int i = 0 ;i<deptuser.size();i++){
				if(deptuser.get(i).getRoleid()!=null){
					deptid+=deptuser.get(i).getDeptid()+"|";
				}
			}
		}
		List<DeptInfo> smalldeptList = this.pjPeerLogManager.getDeptInfo(deptid);
		List<DeptInfo> deptList = new ArrayList<DeptInfo>();
		if(smalldeptList!=null&&smalldeptList.size()>1){
			for(int i = 0;i<smalldeptList.size();i++){
				if(smalldeptList.get(i).getParentdeptid()==-1){
					deptList.add(smalldeptList.get(i));
					smalldeptList.remove(i);
				}
			}
		}
		request.setAttribute("deptList", deptList);
		request.setAttribute("smalldeptList", smalldeptList);
		request.setAttribute("baseList", baseList);
		return new ModelAndView("/peer/pj_peer_log_list_dept");  
	}
	
	/**
	 * @author ������
	 * @description ͬ�����۽��벿���쵼��ѯҳ��
	 */
	@RequestMapping(params="m=getdeptlogstat",method=RequestMethod.GET) 
	public ModelAndView getPjPeerLogDeptStat(HttpServletRequest request,HttpServletResponse response,ModelAndView mp)throws Exception{
		JsonEntity je = new JsonEntity();
		String peerbaseref = request.getParameter("peerbaseref");
		String ptype = request.getParameter("ptype");
		String deptref = request.getParameter("deptref");
		//������ѡ�Ĳ���
		request.setAttribute("olddeptref", deptref);
		//������Ϣ
		List<DeptUser> deptuser = this.logined(request).getDeptUsers();
		String deptid = "";
		if(deptuser!=null&&deptuser.size()>0){
			for(int i = 0 ;i<deptuser.size();i++){
				if(deptuser.get(i).getRoleid()!=null){
					deptid+=deptuser.get(i).getDeptid()+"|";
				}
			}
		}
		if(deptid.length()<1){
			je.setMsg("��û�е�ǰ��ѯȨ�ޣ�����ϵ����Ա");
			response.getWriter().print(je.getAlertMsgAndBack());
			return null;
		}
		if(peerbaseref==null||peerbaseref==""){
			je.setMsg("ϵͳδ��ȡ���������⣬��ˢ������");
			response.getWriter().print(je.getAlertMsgAndBack());
			return null;
		}
		if(ptype==null||ptype==""){
			je.setMsg("ϵͳδ��ȡ�����������ˢ������");
			response.getWriter().print(je.getAlertMsgAndBack());
			return null;
		}
		if(deptref==null||deptref==""){
			deptref=deptid;
		}
		//��ȡͳ���б�
		List<List<String>> logList = this.pjPeerLogManager.getLogStat(peerbaseref, Integer.parseInt(ptype), deptref);		
		request.setAttribute("logList", logList);
		//��ѯ�������б�
		PjPeerItemInfo obj = new PjPeerItemInfo();
		obj.setPeerbaseref(peerbaseref);
		obj.setPtype(Integer.parseInt(ptype));
		obj.setParentref(0);
		List<PjPeerItemInfo> item = this.pjPeerItemManager.getList(obj, null);
		if(item!=null&&item.size()>0){
			request.setAttribute("remark", item.get(0).getRemark());			
		}
		request.setAttribute("itemList", item);
		//��ѯ�����б�
		PjPeerBaseInfo baseObj = new PjPeerBaseInfo();
		List<PjPeerBaseInfo> baseList = this.pjPeerBaseManager.getList(baseObj, null);
		request.setAttribute("baseList", baseList);
		//�����б�
		List<DeptInfo> smalldeptList = this.pjPeerLogManager.getDeptInfo(deptid);
		List<DeptInfo> deptList = new ArrayList<DeptInfo>();
		if(smalldeptList!=null&&smalldeptList.size()>1){
			for(int i = 0;i<smalldeptList.size();i++){
				if(smalldeptList.get(i).getParentdeptid()==-1){
					deptList.add(smalldeptList.get(i));
					smalldeptList.remove(i);
				}
			}
		}
		request.setAttribute("deptList", deptList);
		request.setAttribute("smalldeptList", smalldeptList);
		
		je.setType("success");
		je.setMsg("�ɹ�");
		response.getWriter().print(je.toJSON());
		return new ModelAndView("/peer/pj_peer_log_list_dept");  
	}
}

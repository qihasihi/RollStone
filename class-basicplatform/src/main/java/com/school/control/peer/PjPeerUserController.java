package com.school.control.peer;

import com.school.control.base.BaseController;
import com.school.entity.DeptInfo;
import com.school.entity.peer.PjPeerUserInfo;
import com.school.manager.inter.IDeptManager;
import com.school.manager.inter.peer.IPjPeerUserManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @author ������
 * @description ͬ�����۲����û�controller
 */
@Controller
@RequestMapping(value="/peeruser") 
public class PjPeerUserController extends BaseController<PjPeerUserInfo> {
    @Autowired
    private IDeptManager deptManager;
    @Autowired
    private IPjPeerUserManager pjPeerUserManager;

	/**
	 * @author ������
	 * @description ͬ�����۲����û������б�ҳ��
	 */
	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toPjPeerUserList(HttpServletRequest request,ModelAndView mp )throws Exception{	
		//��ѯ���в���(�����飬�����飬����)		
		List<DeptInfo> deList=this.deptManager.getList(null, null);
		request.setAttribute("deList", deList);
		String peerbaseid = request.getParameter("peerbaseid");
		request.setAttribute("peerbaseid", peerbaseid);
		return new ModelAndView("/peer/pj_peer_user_list");  
	}
	
	/**
	 * @author ������
	 * @throws Exception 
	 * @description ��ȡ�����û��б�
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void getPjPeerUserList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		PageResult presult = this.getPageResultParameter(request);
		PjPeerUserInfo obj = this.getParameter(request, PjPeerUserInfo.class);
		if(obj.getPeerbaseid() ==null ||obj.getPeerbaseid()==""||obj.getDeptid()==null){
			je.setMsg("ϵͳ�쳣������������ˢ��ҳ������");
			response.getWriter().print(je.toJSON());
			return;
		}
		List<PjPeerUserInfo> peerUserList = this.pjPeerUserManager.getList(obj, presult);
		presult.setList(peerUserList);
		je.setType("success");
		je.setPresult(presult);		
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * @author ������
	 * @throws Exception 
	 * @description �޸Ĳ����û��������
	 */
	@RequestMapping(params="m=updateType",method=RequestMethod.POST)
	public void doUpdateType(HttpServletRequest request,HttpServletResponse response)throws Exception{
		PjPeerUserInfo obj = this.getParameter(request, PjPeerUserInfo.class);
		JsonEntity je = new JsonEntity();
		if(obj.getRef()==null){
			je.setMsg("ϵͳ�쳣������������ˢ��ҳ������");
			response.getWriter().print(je.toJSON());
			return;
		}
		Boolean b = this.pjPeerUserManager.doUpdate(obj);
		if(b){			
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{
			je.setType("erroe");
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
}

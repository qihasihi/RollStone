package com.school.control.ethosaraisal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.*;
import com.school.manager.ethosaraisal.StuEthosManager;
import com.school.manager.inter.*;
import com.school.manager.inter.ethosaraisal.IStuEthosManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.school.control.base.BaseController;
import com.school.entity.ClassUser;
import com.school.entity.GradeInfo;
import com.school.entity.StudentInfo;
import com.school.entity.TermInfo;
import com.school.entity.ClassInfo;
import com.school.entity.ethosaraisal.StuEthosInfo;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
/**
 * @author ������
 * @date 2013-04-28
 * @description ѧ��У��Controller
 */
@Controller
@RequestMapping(value="/stuethos")
public class StuEthosController extends BaseController<StuEthosInfo> {
    public StuEthosController(){
        this.stuEthosManager=this.getManager(StuEthosManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.classManager=this.getManager(ClassManager.class);
        this.studentManager=this.getManager(StudentManager.class);
        this.operaterexcelmanager=this.getManager(OperateExcelManager.class);
    }
    //ѧ��У�綨������
    private IGradeManager gradeManager;
    private IStuEthosManager stuEthosManager;
    private ITermManager termManager;
    private IClassManager classManager;
    private IStudentManager studentManager;
    private IOperateExcelManager operaterexcelmanager;

	/**
	 * @author ������
	 * @date 2013-04-28
	 * @description ѧ��У�����
	 * */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		StuEthosInfo obj = this.getParameter(request, StuEthosInfo.class);
		if(obj!=null){
			List<StuEthosInfo> list = this.stuEthosManager.getList(obj, null);
			String uuid = UUID.randomUUID().toString();
			String userRef = this.logined(request).getRef();
			if(list.size()>0){				
				obj.setOperateid(userRef);
				obj.setRef(list.get(0).getRef());
				Boolean b = this.stuEthosManager.doUpdate(obj);
				if(b){
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
					je.setType("success");
				}else{  
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
				}
			}else{
				
				obj.setOperateid(userRef);
				obj.setRef(uuid);
				Boolean b = this.stuEthosManager.doSave(obj);
				if(b){
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
					je.setType("success");
				}else{  
					je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
				}
			}					
		}else{
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ת��ѧ��У��ҳ��
	 * */
	@RequestMapping(params="m=tolist",method=RequestMethod.GET) 
	public ModelAndView tolist(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		return new ModelAndView("/ethosapraisal/admin_ethos_info");  
	}
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ת��ѧ��У��ҳ��
	 * */
	@RequestMapping(params="m=list",method=RequestMethod.POST) 
	public ModelAndView toStuEthosInfo(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{	
		JsonEntity je = new JsonEntity();
		
		String year = request.getParameter("year");
		String grade = request.getParameter("gradeName");
		int classId = Integer.parseInt(request.getParameter("classId").toString());
		String weekId = request.getParameter("weekId");
		
		if (classId <0) {
			je.setMsg("����ϵͳ��δ��ȡ����Ҫ�����İ༶��Ϣ���뷵�ذ༶У��ҳ��ѡ��༶��Ϣ��");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		if (year == null || year.trim().length() < 1 ) {
			je.setMsg("����ϵͳ��δ��ȡ��Ҫ������ѧ����Ϣ���뷵�ذ༶У��ҳ��ѡ��ѧ����Ϣ��");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		if(grade == null || grade.trim().length()<1){
			je.setMsg("����ϵͳ��δ��ȡ��Ҫ�������꼶��Ϣ���뷵�ذ༶У��ҳ��ѡ���꼶��Ϣ��");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		if (weekId == null || weekId.trim().length()<1) {
			je.setMsg("����ϵͳ��δ��ȡ��Ҫ������ѧ����Ϣ���뷵�ذ༶У��ҳ��ѡ��ѧ����Ϣ��");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
			return null;
		}
		
		return new ModelAndView("/ethosapraisal/stu_ethos_info");  
	} 
	/**
	 * @author ������
	 * @date 2013-04-28
	 * @description ѧ��У�����
	 * */
	@RequestMapping(params="m=loadethos",method=RequestMethod.POST)
	public void getStudentEthos(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		Integer weekid = Integer.parseInt(request.getParameter("weekid").toString());
		Integer classid = Integer.parseInt(request.getParameter("classid").toString());
		if(weekid<1||classid<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		} 
		StuEthosInfo obj = new StuEthosInfo();
		obj.setClassid(classid);
		obj.setWeekid(weekid);
		List<StuEthosInfo> objList = this.stuEthosManager.getList(obj, null);
		je.setObjList(objList);
		je.setType("success");
		response.getWriter().print(je.toJSON());	
	}
	/**
	 * @author ������
	 * @date 2013-04-28
	 * @description ѧ��У��÷ֹ���
	 * */
	@RequestMapping(params="m=getscore",method=RequestMethod.POST)
	public void getStudentEthosScore(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String type = request.getParameter("type");
		if(type.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		} 
		String score = UtilTool.scoreproperty.getProperty(type);
		List<Object> list = new ArrayList<Object>();
		list.add(score);
		if(score!=""&&score!=null){
			je.setType("success");
			je.setObjList(list);
		}else{
			je.setType("error");
			je.setMsg("δ�鵽"+type+"��Ӧ�ķ���");
		}
		
		
		response.getWriter().print(je.toJSON());
	}
	/**
	 * @author ������
	 * @date 2013-05-07
	 * @description ѧ��У��ɾ��
	 * 
	 * */
	@RequestMapping(params="m=ajaxdel",method=RequestMethod.POST)
	public void doDeleteStudentEthos(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity je = new JsonEntity();
		String delid = request.getParameter("delid");
		if(delid.length()>0){
			String[] delids = delid.split(",");			
			//���ֵ�����ݼ�����
			List<String> sqlArrayList = new ArrayList<String>();
			//���sql�ļ���
			List<List<Object>> objArrayList = new ArrayList<List<Object>>();
			StringBuilder sqlbuilder;
			List<Object> objlist;
			StuEthosInfo obj;
			for(int i = 0; i<delids.length;i++){				
				sqlbuilder = new StringBuilder();
				objlist = new ArrayList<Object>();
				obj = new StuEthosInfo();
				obj.setRef(delids[i]);
				objlist=this.stuEthosManager.getDeleteSql(obj, sqlbuilder);
				sqlArrayList.add(sqlbuilder.toString());
				objArrayList.add(objlist);
			}
			Boolean b = this.stuEthosManager.doExcetueArrayProc(sqlArrayList, objArrayList);
			if(b){
				je.setType("success");
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));				
			}else{  
				je.setType("error");
				je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}else{
			je.setType("error");
			je.setMsg(UtilTool.msgproperty.getProperty("REF_EMPTY"));
		}
		response.getWriter().print(je.toJSON());
	}
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ת��У��ͳ��
	 * */
	@RequestMapping(params="m=touserstudent",method=RequestMethod.GET) 
	public ModelAndView toUserSelectClass(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{	
		JsonEntity je = new JsonEntity();
		List<TermInfo> termList = termManager.getList(null, null);
		List<GradeInfo> gradeList = gradeManager.getList(null, null);
		request.setAttribute("termList", termList);
		request.setAttribute("gradeList", gradeList);
		
		return new ModelAndView("/ethosapraisal/user/studentview-user");  
	}
	/**
	 * @author ������
	 * @date 2013-05-07
	 * @description ��ѯͳ��ѧ��У�翼��
	 * 
	 * */
	@RequestMapping(params="m=getethoskq",method=RequestMethod.POST)
	public void getEthosKQ(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String termid = request.getParameter("termid");
	 	String classid= request.getParameter("classid");
	 	String stuno=request.getParameter("stuno");
	 	String ordercolumn=request.getParameter("ordercolumn");
	 	String dict=request.getParameter("dict");
	 	List<List<String>> objList = this.stuEthosManager.getEthosKQ(termid, Integer.parseInt(classid), stuno, ordercolumn, dict);
	 	je.setObjList(objList);
	 	je.setType("success");
	 	response.getWriter().print(je.toJSON());
	}
	/**
	 * @author ������
	 * @date 2013-05-07
	 * @description ��ѯͳ��ѧ��У��Υ��
	 * 
	 * */
	@RequestMapping(params="m=getethoswj",method=RequestMethod.POST)
	public void getEthosWJ(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String termid = request.getParameter("termid");
	 	String classid= request.getParameter("classid");
	 	String stuno=request.getParameter("stuno");
	 	String ordercolumn=request.getParameter("ordercolumn");
	 	String dict=request.getParameter("dict");
	 	List<List<String>> objList = this.stuEthosManager.getEthosWJ(termid, Integer.parseInt(classid), stuno, ordercolumn, dict);
	 	je.setObjList(objList);
	 	je.setType("success");
	 	response.getWriter().print(je.toJSON());
	}
	/**
	 * @author ������
	 * @date 2013-05-07
	 * @description ��ѯͳ��ѧ��У���ۺ�
	 * 
	 * */
	@RequestMapping(params="m=getethoszh",method=RequestMethod.POST)
	public void getEthosZH(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		String termid = request.getParameter("termid");
	 	String classid= request.getParameter("classid");
	 	String stuno=request.getParameter("stuno");
	 	String isshowrank = request.getParameter("isshowrank");
	 	List<List<String>> objList = this.stuEthosManager.getEthosZH(termid, Integer.parseInt(classid), stuno,Integer.parseInt(isshowrank));
	 	je.setObjList(objList);
	 	je.setType("success");
	 	response.getWriter().print(je.toJSON());
	}
	
	/**-------------------------�����ǵ�������-------------------------*/
	
	/**
     * @atuthor ������
     * @date 2013-05-21
	 * @description ����ѧ��У����Ϣ���ܣ���ѧ����ѯ(����)
     */
	@RequestMapping(params="m=expStukq",method=RequestMethod.POST)
    public void explortUserStudentKQ(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsonEntity je=new JsonEntity();
    	String termid=request.getParameter("termid");
    	if(termid==null||termid.trim().length()<1){
    		je.setMsg("����ϵͳ��⵽��û�����������ϵͳ�������������ϵͳ!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String classid=request.getParameter("classid");
    	if(classid==null||classid.trim().length()<1){
    		je.setMsg("����ϵͳ��⵽��û�����������ϵͳ�������������ϵͳ!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String stuno=request.getParameter("stuno");
    	if(stuno==null||stuno.trim().length()<1)
    		stuno=null;
    	String ordercolumn=request.getParameter("ordercolumn");
    	if(ordercolumn==null||ordercolumn.trim().length()<1)
    		ordercolumn=null;
    	String direct=request.getParameter("dict");
    	if(direct==null||direct.trim().length()<1)
    		direct=null;
    	//�õ�����
    	//List<List<String>> objColumn= new ArrayList<List<String>>();
    	String column = "�ܴ�,ѧ��,����,�������,���ٴ���,�¼����,�¼ٴ���,�������,���˴���,�������,���δ���,�ٵ����,�ٵ�����";
    	String [] columns = column.split(",");
    	
    	//�õ�����
    	List<List<String>> objData= this.stuEthosManager.getEthosKQ(termid, Integer.parseInt(classid), stuno, ordercolumn, direct);

    	//����������
		List<String> columnList=new ArrayList<String>();
		//if(objColumn!=null&&objColumn.size()>0){
		//	columnList=objColumn.get(0);
		//}
		for(int i = 0 ; i < columns.length; i++){
			columnList.add(columns[i]);
		}
  
    	//׼����������
		//׼����������
		String title="",filename="";
		TermInfo t=new TermInfo();
		t.setRef(termid.trim());
		List<TermInfo> tmInfo=this.termManager.getList(t, null);
		if(tmInfo!=null&&tmInfo.size()>0){
			//title=tmInfo.get(0).getYear()+""+tmInfo.get(0).getTermname() ;
		}
		
		 //--�õ��༶��Ϣ
		if(classid!=null&&classid.trim().length()>0){
			ClassInfo c=new ClassInfo();
			c.setClassid(Integer.parseInt(classid));
			List<ClassInfo> clsList=this.classManager.getList(c, null);
			if(clsList!=null&&clsList.size()>0){
				title+=""+clsList.get(0).getClassgrade()+"("+clsList.get(0).getClassname()+")";
			}
		}
		if(stuno!=null){
			StudentInfo s = new StudentInfo();
			s.setStuno(stuno);
			List<StudentInfo> stuList=this.studentManager.getList(s, null);
			if(stuList!=null&&stuList.size()>0){
				title+=""+stuList.get(0).getStuname()+"["+stuList.get(0).getStuno()+"]";
			}
		}
		
		title+="У�翼��ͳ�Ƴɼ�";
		filename=title;
		String flag=this.operaterexcelmanager.ExplortExcel(response, filename, columnList, objData, title, String.class, null);
		if(flag!=null){
			je.setMsg("�����޷�����!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
		}
    }
	/**
     * @atuthor ������
     * @date 2013-05-21
	 * @description ����ѧ��У����Ϣ���ܣ���ѧ����ѯ(Υ��)
     */
	@RequestMapping(params="m=expStuwj",method=RequestMethod.POST)
    public void explortUserStudentWJ(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsonEntity je=new JsonEntity();
    	String termid=request.getParameter("termid");
    	if(termid==null||termid.trim().length()<1){
    		je.setMsg("����ϵͳ��⵽��û�����������ϵͳ�������������ϵͳ!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String classid=request.getParameter("classid");
    	if(classid==null||classid.trim().length()<1){
    		je.setMsg("����ϵͳ��⵽��û�����������ϵͳ�������������ϵͳ!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String stuno=request.getParameter("stuno");
    	if(stuno==null||stuno.trim().length()<1)
    		stuno=null;
    	String ordercolumn=request.getParameter("ordercolumn");
    	if(ordercolumn==null||ordercolumn.trim().length()<1)
    		ordercolumn=null;
    	String direct=request.getParameter("dict");
    	if(direct==null||direct.trim().length()<1)
    		direct=null;
    	//�õ�����
    	//List<List<String>> objColumn= new ArrayList<List<String>>();
    	String column = "�ܴ�,ѧ��,����,�ؿ��ܷ�,�ؿ�δ����,У���ܷ�,У������,�й��ܷ�,�й����,�����ܷ�,δ������";
    	String [] columns = column.split(",");
    	
    	//�õ�����
    	List<List<String>> objData= this.stuEthosManager.getEthosWJ(termid, Integer.parseInt(classid), stuno, ordercolumn, direct);

    	//����������
		List<String> columnList=new ArrayList<String>();
		//if(objColumn!=null&&objColumn.size()>0){
		//	columnList=objColumn.get(0);
		//}
		for(int i = 0 ; i < columns.length; i++){
			columnList.add(columns[i]);
		}
  
    	//׼����������
		//׼����������
		String title="",filename="";
		TermInfo t=new TermInfo();
		t.setRef(termid.trim());
		List<TermInfo> tmInfo=this.termManager.getList(t, null);
		if(tmInfo!=null&&tmInfo.size()>0){
			//title=tmInfo.get(0).getYear()+""+tmInfo.get(0).getTermname() ;
		}
		
		 //--�õ��༶��Ϣ
		if(classid!=null&&classid.trim().length()>0){
			ClassInfo c=new ClassInfo();
			c.setClassid(Integer.parseInt(classid));
			List<ClassInfo> clsList=this.classManager.getList(c, null);
			if(clsList!=null&&clsList.size()>0){
				title+=""+clsList.get(0).getClassgrade()+"("+clsList.get(0).getClassname()+")";
			}
		}
		if(stuno!=null){
			StudentInfo s = new StudentInfo();
			s.setStuno(stuno);
			List<StudentInfo> stuList=this.studentManager.getList(s, null);
			if(stuList!=null&&stuList.size()>0){
				title+=""+stuList.get(0).getStuname()+"["+stuList.get(0).getStuno()+"]";
			}
		}
		
		title+="У��Υ��ͳ�Ƴɼ�";
		filename=title;
		String flag=this.operaterexcelmanager.ExplortExcel(response, filename, columnList, objData, title, String.class, null);
		if(flag!=null){
			je.setMsg("�����޷�����!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
		}
    }
	/**
     * @atuthor ������
     * @date 2013-05-21
	 * @description ����ѧ��У����Ϣ���ܣ���ѧ����ѯ(�ۺ�)
     */
	@RequestMapping(params="m=expStuzh",method=RequestMethod.POST)
    public void explortUserStudentZH(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	JsonEntity je=new JsonEntity();
    	String termid=request.getParameter("termid");
    	if(termid==null||termid.trim().length()<1){
    		je.setMsg("����ϵͳ��⵽��û�����������ϵͳ�������������ϵͳ!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String classid=request.getParameter("classid");
    	if(classid==null||classid.trim().length()<1){
    		je.setMsg("����ϵͳ��⵽��û�����������ϵͳ�������������ϵͳ!");
    		response.getWriter().print(je.getAlertMsgAndCloseWin());
    		return;
    	}
    	String stuno=request.getParameter("stuno");
    	if(stuno==null||stuno.trim().length()<1)
    		stuno=null;
    	String ordercolumn=request.getParameter("ordercolumn");
    	if(ordercolumn==null||ordercolumn.trim().length()<1)
    		ordercolumn=null;
    	String direct=request.getParameter("dict");
    	if(direct==null||direct.trim().length()<1)
    		direct=null;
    	String isshowrank = request.getParameter("isshowrank");
    	//�õ�����
    	//List<List<String>> objColumn= new ArrayList<List<String>>();
    	String column = "�ܴ�,ѧ��,����,�������,������,���ٷ���,�¼����,�¼���,�¼ٷ���,�������,������,���˷���,�������,������,���η���,�ٵ�,�ٵ�����,�ٵ�����,δ���ؿ���,�ؿ��۷�,У�����,У������,У���۷�,�й����,�й�Υ�ʹ�,�й�۷�,�ٻ������,�ٻ������,����۷�,���˺���,���˺��´�,���˺��·�,�ܷ�,����";
    	String [] columns = column.split(",");
    	
    	//�õ�����
    	List<List<String>> objData= this.stuEthosManager.getEthosZH(termid, Integer.parseInt(classid), stuno, Integer.parseInt(isshowrank));

    	//����������
		List<String> columnList=new ArrayList<String>();
		//if(objColumn!=null&&objColumn.size()>0){
		//	columnList=objColumn.get(0);
		//}
		for(int i = 0 ; i < columns.length; i++){
			columnList.add(columns[i]);
		}
  
    	//׼����������
		//׼����������
		String title="",filename="";
		TermInfo t=new TermInfo();
		t.setRef(termid.trim());
		List<TermInfo> tmInfo=this.termManager.getList(t, null);
		if(tmInfo!=null&&tmInfo.size()>0){
			//title=tmInfo.get(0).getYear()+""+tmInfo.get(0).getTermname() ;
		}
		
		 //--�õ��༶��Ϣ
		if(classid!=null&&classid.trim().length()>0){
			ClassInfo c=new ClassInfo();
			c.setClassid(Integer.parseInt(classid));
			List<ClassInfo> clsList=this.classManager.getList(c, null);
			if(clsList!=null&&clsList.size()>0){
				title+=""+clsList.get(0).getClassgrade()+"("+clsList.get(0).getClassname()+")";
			}
		}
		if(stuno!=null){
			StudentInfo s = new StudentInfo();
			s.setStuno(stuno);
			List<StudentInfo> stuList=this.studentManager.getList(s, null);
			if(stuList!=null&&stuList.size()>0){
				title+=""+stuList.get(0).getStuname()+"["+stuList.get(0).getStuno()+"]";
			}
		}
		
		title+="У���ۺ�ͳ�Ƴɼ�";
		filename=title;
		String flag=this.operaterexcelmanager.ExplortExcel(response, filename, columnList, objData, title, String.class, null);
		if(flag!=null){
			je.setMsg("�����޷�����!");
			response.getWriter().print(je.getAlertMsgAndCloseWin());
		}
    }
}

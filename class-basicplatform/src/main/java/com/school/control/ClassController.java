package com.school.control;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.util.MD5_NEW;
import jcore.jsonrpc.common.JSONArray;
import jcore.jsonrpc.common.JSONObject;
import net.sf.json.JSON;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.ClassUser;
import com.school.entity.ClassYearInfo;
import com.school.entity.GradeInfo;
import com.school.entity.SubjectInfo;
import com.school.entity.TermInfo;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/cls") 
public class ClassController extends BaseController<ClassInfo>{
    private IClassManager classManager;
    private IClassYearManager classYearManager;
    private IGradeManager gradeManager;
    private ISubjectManager subjectManager;
    private ITermManager termManager;
    private IClassUserManager classUserManager;
    private IOperateExcelManager operaterexcelmanager;
    private IUserManager userManager;
    public ClassController(){
        this.classManager=this.getManager(ClassManager.class);
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.userManager=this.getManager(UserManager.class);
        this.operaterexcelmanager=this.getManager(OperateExcelManager.class);
    }

	@RequestMapping(params="m=list",method=RequestMethod.GET) 
	public ModelAndView toClassList(HttpServletRequest request,ModelMap mp )throws Exception{
		PageResult p = new PageResult();
		p.setOrderBy("c.e_time desc"); 
		List<ClassYearInfo>classyearList=this.classYearManager.getList(null, p); 
		List<GradeInfo>gradeList=this.gradeManager.getList(null, null);
		mp.put("classyearList", classyearList);
		mp.put("gradeList", gradeList);

		//�õ�ȫ��ѧ��
		List<SubjectInfo> subList=this.subjectManager.getList(null, null);
		request.setAttribute("subList", subList);
		//�õ���ǰ���
		TermInfo currentTm=this.termManager.getAutoTerm();
		mp.put("currentYear", currentTm.getYear());
		
		//�õ�allowAutoLevel
		int allowAutoLevel=0;
		//��֤���Ƿ���������ִ��
		//�õ���һ��ѧ��
		TermInfo tm=new TermInfo();
		tm.setDYYear(currentTm.getYear());
		PageResult presult=new PageResult();
		presult.setOrderBy(" u.YEAR ASC ");
		presult.setPageSize(1);	
		
		List<TermInfo> tmList=this.termManager.getList(tm, presult);
		if(tmList!=null&&tmList.size()>0){		
			String nextyear=tmList.get(0).getYear();
			//�õ���ǰnextyear�����е�������
			presult=new PageResult();
			presult.setPageSize(1);	
			ClassInfo clsentity=new ClassInfo();
			clsentity.setYear(nextyear);
			clsentity.setPattern("������");
			List<ClassInfo> nextClsList=this.classManager.getList(clsentity, presult);
			if(nextClsList==null||nextClsList.size()<1){
				allowAutoLevel=1;
			}
		}
		request.setAttribute("allowAutoLevel", allowAutoLevel);
		return new ModelAndView("/class/list");  
	}
	
	/**
	 * �༶��Ա����  
	 * @param request
	 * @param mp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="m=detail",method=RequestMethod.GET) 
	public ModelAndView toClassUserDetail(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
		String classid=request.getParameter("classid");
		JsonEntity jeEntity=new JsonEntity();
		if(classid==null||classid.trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
		}
		//��֤�༶
		ClassInfo clsInfo=new ClassInfo();
		clsInfo.setClassid(Integer.parseInt(classid.trim()));
		List<ClassInfo> clsList=this.classManager.getList(clsInfo, null);
		if(clsList==null||clsList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
		}
		//PageResult p = new PageResult();
		ClassUser cu=new ClassUser();
		cu.setRelationtype("ѧ��");
		if(classid!=null&&classid.trim().length()>0)  
			cu.setClassid(Integer.parseInt(classid));
        //ֻ��ʾ���õ�ѧ���ʺţ�����ʾ���õ�ѧ��
        cu.getUserinfo().setStateid(0);
		List<ClassUser>stuList=this.classUserManager.getList(cu,null); 
		//�õ�ȫ��ѧ��
		List<SubjectInfo> subList=this.subjectManager.getList(null, null);
		request.setAttribute("subList", subList);
		//�õ�ȫ���꼶
		List<GradeInfo> gradeList=this.gradeManager.getList(null, null);
		request.setAttribute("gdList", gradeList);
		
		request.setAttribute("stuList", stuList);		
		request.setAttribute("clsinfo", clsList.get(0));
		
		
		return new ModelAndView("/class/detail/detail");    
	}

    /**
     * ���ݰ༶ID�õ��༶��Ա
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getClsUserByClsId",method=RequestMethod.POST)
    public void getClsUserByClsId(HttpServletRequest request,HttpServletResponse response) throws Exception{
        PageResult presult=this.getPageResultParameter(request);
        String clsid=request.getParameter("classid");
       // String relationType=request.getParameter("relationtype");
        JsonEntity jsonEntity=new JsonEntity();
        if(clsid==null||clsid.trim().length()<1){//||relationType==null||relationType.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        ClassUser cu=new ClassUser();
        cu.setRelationtype("ѧ��");
        cu.setClassid(Integer.parseInt(clsid));
        //ֻ��ʾ���õ�ѧ���ʺţ�����ʾ���õ�ѧ��
        cu.getUserinfo().setStateid(0);
        List<ClassUser> stuList=this.classUserManager.getList(cu,presult);
        presult.getList().add(stuList);
        presult.getList().add(clsid);
        jsonEntity.setType("success");
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }
	
	@RequestMapping(params="m=exportClsExcel",method=RequestMethod.GET)
	public String exportClsExcel(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String classid=request.getParameter("classid");
		if(classid==null||classid.length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return null; 
		}
		String msg=exportStudent(response, classid);
		if(msg!=null&&msg.length()>0){
			je.setMsg(msg);
			response.getWriter().print(je.getAlertMsgAndBack());
			return null;
		}  
		return null;
	}
	
	public String exportStudent(HttpServletResponse response,String classid) throws Exception{
		List<List<String>> dataList = new ArrayList<List<String>>();
		List<String> columns = new ArrayList<String>();
		columns.add("ѧ��");
		columns.add("����");
		columns.add("�Ա�");
		//columns.add("���");

		String filename = null;
		String title = null;
		ClassUser cu=new ClassUser();
		cu.setRelationtype("ѧ��");
		cu.setClassid(Integer.parseInt(classid));
        cu.getUserinfo().setStateid(0);//����ѯ���õ�ѧ����Ϣ
		List<ClassUser>clsUserList=this.classUserManager.getList(cu, null);
		if(clsUserList==null||clsUserList.size()<1){
			return "����Ϊ��!";
		}
		for (ClassUser clsu : clsUserList) {
			List<String> tempList = new ArrayList<String>();
			tempList.add(clsu.getStuno());
			tempList.add(clsu.getUserinfo().getRealname());
			tempList.add(clsu.getUserinfo().getSex());
		//	tempList.add(clsu.getClassgrade()+clsu.getClassname());
		//	tempList.add(clsu.getYear());
			dataList.add(tempList);
		}
		filename=title=clsUserList.get(0).getYear()+clsUserList.get(0).getClassgrade()+clsUserList.get(0).getClassname()+"ѧ������";
		return this.operaterexcelmanager.ExplortExcel(response,filename, columns,
				dataList, title, String.class, null);
	}
	  
	/**
	 * ��ȡList
	 * @param request
	 * @param response 
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
	public void AjaxGetList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		String type=request.getParameter("dtype");
		if(type!=null&&type.trim().length()>0){ 
			classinfo.setType(type);
		}
		PageResult pageresult = this.getPageResultParameter(request);
		pageresult.setOrderBy(" u.c_time desc ");//�������ã���ǰ�������ں�
		List<ClassInfo> classList =classManager.getList(classinfo, pageresult);
		pageresult.setList(classList);
		JsonEntity je = new JsonEntity();
		je.setType("success"); 
		je.setPresult(pageresult); 
		response.getWriter().print(je.toJSON()); 
	}
	/** 
	 * �������������༶ģ�塣
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=explortClsTemplate",method=RequestMethod.POST)
	public void explortClsTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		String type=request.getParameter("dtype");
		if(type!=null&&type.trim().length()>0){
			classinfo.setType(type);
		}
		if(classinfo.getClassgrade()==null||classinfo.getClassgrade().trim().length()<1)
			classinfo.setClassgrade(null);
		
		if(classinfo.getPattern()==null||classinfo.getPattern().trim().length()<1)
			classinfo.setPattern(null);
		
		if(classinfo.getYear()==null||classinfo.getYear().trim().length()<1)
			classinfo.setYear(null);
	
		if(classinfo.getClassname()==null||classinfo.getClassname().trim().length()<1)
			classinfo.setClassname(null);
		
		PageResult pageresult = this.getPageResultParameter(request);
		pageresult.setOrderBy(" u.isflag asc ");//�������ã���ǰ�������ں�
		List<ClassInfo> classList =classManager.getList(classinfo, pageresult);
		
		JsonEntity jEntity=new JsonEntity();
		if(classList==null||classList.size()<1){
			jEntity.setMsg("�޷����������༶ģ�飬ԭ�򣺸������������δ���ҵ���Ӧ�༶!");
			response.getWriter().print(jEntity.getAlertMsgAndCloseWin());return;
		}
		//��ʼ��֯����
		List<String> sheetNameList=new ArrayList<String>();
		List<List<String>> columnsList=new ArrayList<List<String>>();
		List datalist=new ArrayList();
		List<String> titleList=new ArrayList<String>();
		List<Class<? extends Object>> entityClsList=new ArrayList<Class<? extends Object>>();
		List<String> explortObjList=new ArrayList<String>();
		for (int i=0;i<classList.size();i++) {
            if(i>=4)break;       //ֻ��ʾ4��
            ClassInfo clstmp = classList.get(i);
			if(clstmp!=null){
				//sheet
				sheetNameList.add(clstmp.getClassgrade()+clstmp.getClassname());
				//column
				List<String> coltmpList=new ArrayList<String>();
				coltmpList.add("ѧ��");
				coltmpList.add("����");
				coltmpList.add("�Ա�");
				columnsList.add(coltmpList);
				//title
				String title=clstmp.getYear()+" "+clstmp.getClassgrade()+"("+clstmp.getClassname()+")";
				titleList.add(title);
				//datalist   (��)
				List<List<String>> dataArrayList=new ArrayList<List<String>>();
				datalist.add(dataArrayList);
				//entityCls
				entityClsList.add(null);
				//explortObjList
				explortObjList.add(null);
			}
		}
		
		
		String filename=classList.get(0).getYear()+"ѧ��༶ѧ������ģ��";
		//����
		this.operaterexcelmanager.ExplortExcel(response, filename,sheetNameList,columnsList
				,datalist,titleList,entityClsList,explortObjList);
	}
	
	
	/**
	 * ȥ�޸�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=toupd",method=RequestMethod.POST) 
	public void toUpdateClassyear(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		if(classinfo.getClassid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_CLASSID_EMPTY")); 
			response.getWriter().print(je.toJSON());
			return;
		}
		List<ClassInfo> classList = classManager.getList(classinfo, null);
		je.setObjList(classList); 
		je.setType("success");
		response.getWriter().print(je.toJSON());  
	}
	
	/**
	 * ���
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
	public void doSubmitAddClassyear(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		String type=request.getParameter("dtype");
		String year=request.getParameter("dyear");
		String pattern=request.getParameter("dpattern");
		if(classinfo.getClassgrade()==null||
				classinfo.getClassgrade().trim().length()<1||
				classinfo.getClassname()==null||
				classinfo.getClassname().length()<1||type==null||year==null||pattern==null){  
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR")); 
			response.getWriter().print(je.toJSON()); 
			return;
		}
		classinfo.setType(type);
		classinfo.setYear(year);
		classinfo.setPattern(pattern);
		classinfo.setIslike(1);		
		//�Ѵ��ڵ�ǰ���� �޷����
		List<ClassInfo>classList=this.classManager.getList(classinfo, null);
		if(classList!=null&&classList.size()>0){
			je.setMsg(UtilTool.msgproperty.getProperty("DATA_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		classinfo.setIsflag(1);
		if(classManager.doSave(classinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}  
	 	
	/**
	 * �޸�
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=modify",method=RequestMethod.POST)
	public void doSubmitUpdateRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
 		JsonEntity je = new JsonEntity();
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		if(classinfo.getClassid()==null
				//||classinfo.getYear()==null
				){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		ClassInfo clsInfo=new ClassInfo();
		clsInfo.setClassid(classinfo.getClassid());
		List<ClassInfo> clsList=this.classManager.getList(clsInfo, null);
		if(clsList==null||clsList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(je.toJSON());return;
		}		
		ClassInfo clsEntity=clsList.get(0);
		if(classinfo.getClassgrade()!=null&&!classinfo.getClassgrade().equals(clsEntity.getClassgrade()))
			clsEntity.setClassgrade(classinfo.getClassgrade());
		if(classinfo.getClassname()!=null&&!classinfo.getClassname().equals(clsEntity.getClassname()))
			clsEntity.setClassname(classinfo.getClassname());
		if(classinfo.getType()!=null&&!classinfo.getType().equals(clsEntity.getType()))
			clsEntity.setType(classinfo.getType());
		if(classinfo.getPattern()!=null&&!classinfo.getPattern().equals(clsEntity.getPattern()))
			clsEntity.setPattern(classinfo.getPattern());
		if(classinfo.getPattern()!=null&&classinfo.getPattern().equals("�ֲ��")
				&&classinfo.getSubjectid()!=null&&classinfo.getSubjectid()!=clsEntity.getSubjectid())
			clsEntity.setSubjectid(classinfo.getSubjectid());
		else if(classinfo.getPattern()!=null&&classinfo.getPattern().equals("������"))
			clsEntity.setSubjectid(-999);
		if(classinfo.getYear()!=null&&!classinfo.getYear().equals(clsEntity.getYear()))
			clsEntity.setYear(classinfo.getYear());	
		if(classinfo.getIsflag()!=null&&classinfo.getIsflag()!=clsEntity.getIsflag())
			clsEntity.setIsflag(classinfo.getIsflag());
		if(classManager.doUpdate(clsEntity)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success"); 
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());  
	}  
	
	/**
	 * ɾ��
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params="m=del",method=RequestMethod.POST)
	public void doDeleteRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je = new JsonEntity();
		ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
		if(classinfo.getClassid()==null){
			je.setMsg(UtilTool.msgproperty.getProperty("CLASS_CLASSID_EMPTY"));
			response.getWriter().print(je.toJSON());
			return;
		}
		//����ID�õ�����༶���ڵ�ѧ������
		List<ClassInfo> clsList=this.classManager.getList(classinfo, null);
		if(clsList==null||clsList.size()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
			response.getWriter().print(je.toJSON());
			return;
		}
		//��֤�� ���༶����Ϊ0ʱ��ɾ�����ã�����ɾ�����༶��
//		if(clsList.get(0).getNum()!=null&&UtilTool.isNumber(clsList.get(0).getNum().toString())
//				&&Integer.parseInt(clsList.get(0).getNum().toString())>0){
//			je.setMsg("�쳣���󣬸ð༶������ѧ���������ѧ������ɾ���༶!");
//			response.getWriter().print(je.toJSON());return;
//		}
		if(classManager.doDelete(classinfo)){
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		}else{  
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());    
	}  
	
	/**
	 * ����COPY�༶ѧ��
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=toCopyClsStudent",method=RequestMethod.POST)
	public void toCopyClassStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String clsid=request.getParameter("classid");
		JsonEntity jeEntity=new JsonEntity();
		if(clsid==null||clsid.toString().trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		ClassInfo clsEntity=new ClassInfo();
		clsEntity.setClassid(Integer.parseInt(clsid.toString().trim()));
		List<ClassInfo> classList=this.classManager.getList(clsEntity, null);
		if(classList==null||classList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.toJSON());
			return;
		}
		
		//���Ŀ�İ༶�������࣬��ѡ�༶Ϊ��һѧ��������༶

		//��ɫ����Ϊ�Ѿ����䵽���롰Ŀ�İ༶��ͬ���͵������༶�У�
			//ȷ��ʱ��ʾ����������������������༶ɾ���������˰ࡣ
		 PageResult presult=new PageResult();
		 presult.setPageNo(1);
		 presult.setPageSize(10000);
		 presult.setOrderBy(" u.class_id asc ");
		
		clsEntity=classList.get(0);
		 
		 List<ClassInfo> tmpClsList=null;
		 if(clsEntity.getPattern().trim().equals("������")){
			 ClassYearInfo cyEntity=new ClassYearInfo();
			 cyEntity.setClassyearvalue(clsEntity.getYear());
			 List<Map<String,Object>> objMapList=this.classYearManager.getClassYearPree(cyEntity);
			 if(objMapList!=null&&objMapList.size()>0){				 
				 //�õ���һ���
				 String cyValue=objMapList.get(0).get("CLASS_YEAR_VALUE").toString();
				 
				 ClassInfo tmpClsEntity=new ClassInfo();
				 tmpClsEntity.setYear(cyValue);
				 tmpClsEntity.setPattern("������");
				 tmpClsEntity.setClassgrade(clsEntity.getClassgrade());
				 //tmpClsEntity.setSubjectid(clsEntity.getSubjectid());
				 
				 tmpClsList=this.classManager.getList(tmpClsEntity, presult);
			 }
		 }else if(clsEntity.getPattern().trim().equals("�ֲ��")){
				//���Ŀ�İ༶�Ƿֲ�࣬��ѡ�༶Ϊ��ѧ���������
			 ClassInfo tmpEntity=new ClassInfo();
			 tmpEntity.setYear(clsEntity.getYear());
			 tmpEntity.setPattern("������");
			 tmpEntity.setClassgrade(clsEntity.getClassgrade());
			 tmpClsList=this.classManager.getList(tmpEntity, presult);
		 }
		 jeEntity.setObjList(tmpClsList);
		 jeEntity.setType("success");
		 response.getWriter().print(jeEntity.toJSON());
	}
	/**
	 * �õ�����ѧ�������
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="m=tiaobancu",method=RequestMethod.POST)
	public void getTiaoBanClassUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		ClassInfo clsInfo=this.getParameter(request, ClassInfo.class);
//		if(clsInfo==null||clsInfo.getClassid()==null){
//			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(jeEntity.toJSON());return;
//		}
		ClassInfo tmpSearchCls=new ClassInfo();
		tmpSearchCls.setClassid(clsInfo.getClassid());
		List<ClassInfo> clsList=this.classManager.getList(tmpSearchCls, null);
		if(clsList==null||clsList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//�õ�ѧ��
		ClassInfo tmpCls=clsList.get(0);
		List<Map<String,Object>> objMapList=this.classUserManager.getClassUserWithTiaoban(clsInfo.getPattern(), clsInfo.getYear(),clsInfo.getClassid().toString());
		jeEntity.setObjList(objMapList);
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
	}
	
	/**
	 * �༶����ҳ������ִ��
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	//cls?m=doAddClsUserDetail
	@RequestMapping(params="m=doAddClsUserDetail",method=RequestMethod.POST)
	public void doAddClsUserDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		ClassInfo clsInfo=this.getParameter(request, ClassInfo.class);
		if(clsInfo==null||clsInfo.getClassid()==null){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		String uid=request.getParameter("uid");
		if(uid==null||uid.trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		
		//��֤�༶�Ƿ����
		ClassInfo clsEntity=new ClassInfo();
		clsEntity.setClassid(clsInfo.getClassid());
		List<ClassInfo> clsList=this.classManager.getList(clsEntity, null);
		if(clsList==null||clsList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		clsEntity=clsList.get(0);
		//��ʼ������� 
		String[] uidArray=uid.split(",");
		StringBuilder sqlbuilder=null;
		List<String> sqlArrayList=new ArrayList<String>();
		List<List<Object>> objArrayList=new ArrayList<List<Object>>();
		for (String tmpuid : uidArray) {
			if(tmpuid==null||tmpuid.trim().length()<1)continue;
			//��֤�û��Ƿ����
			UserInfo utmp=new UserInfo();
			utmp.setRef(tmpuid.trim());
			List<UserInfo> uList=this.userManager.getList(utmp, null);
			if(uList==null||uList.size()<1){
				jeEntity.setMsg("�쳣����ѧ��ID:"+tmpuid.trim()+" û�з��ָ�ѧ��!");
				response.getWriter().print(jeEntity.toJSON());
				return;
			}
			//UserInfo currentUsTmp=uList.get(0);
			//�����û��Ƿ������ͬ��ݣ�ͬ���ͣ�ͬѧ�ƵĴ���,��ɾ��
			ClassUser cutmp=new ClassUser();
			if(clsEntity.getPattern().trim().equals("�ֲ��"))
				if(clsEntity.getSubjectid()!=null)
					cutmp.setSubjectid(clsEntity.getSubjectid());	//subjectid
			cutmp.setClassgrade(clsEntity.getClassgrade());	//classgrade 
			cutmp.setRelationtype("ѧ��");			//relation_type
			cutmp.setPattern(clsEntity.getPattern());	//pattern
			cutmp.setUserid(tmpuid.trim());		//userid
			cutmp.setYear(clsEntity.getYear());
			List<ClassUser> cutmpList=this.classUserManager.getList(cutmp, null);
			if(cutmpList!=null&&cutmpList.size()>0){				
				sqlbuilder=new StringBuilder();
				List<Object> objList=this.classUserManager.getDeleteSql(cutmp, sqlbuilder);
				if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
					sqlArrayList.add(sqlbuilder.toString());
					objArrayList.add(objList);
				}
				String remark="����ɾ����������"+clsEntity.getPattern()+"��ѧ��:"+clsEntity.getSubjectid()+","+clsEntity.getClassgrade()+",ѧ��,"+tmpuid+",";
					   remark+=","+clsEntity.getYear();
				
				sqlbuilder=new StringBuilder();
				objList=this.classUserManager.getAddOperateLog(this.logined(request).getRef(),"j_class_user"
						,null,null, null,"DELETE", remark,sqlbuilder);
				if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
					sqlArrayList.add(sqlbuilder.toString());
					objArrayList.add(objList);
				}
			}
			
			//���
			ClassUser addcu=new ClassUser();
			addcu.setRef(this.classUserManager.getNextId());
			addcu.setClassid(clsEntity.getClassid());
			addcu.setUserid(tmpuid.trim());
			addcu.setRelationtype("ѧ��");
			addcu.setSubjectid(clsEntity.getSubjectid());
			sqlbuilder=new StringBuilder();
			List<Object> objList=this.classUserManager.getSaveSql(addcu, sqlbuilder);
			if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
				sqlArrayList.add(sqlbuilder.toString());
				objArrayList.add(objList);
			}
		}
		
		if(sqlArrayList.size()>0&&objArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
			if(this.classUserManager.doExcetueArrayProc(sqlArrayList, objArrayList)){
				jeEntity.setType("success");
				jeEntity.setMsg("�����ɹ�!");
			}else
				jeEntity.setMsg("����ʧ�ܣ�ԭ��δ֪!");
		}else{
			jeEntity.setMsg("�쳣����û�п�ִ�еĲ���!������!");
		}
		response.getWriter().print(jeEntity.toJSON());
	} 
	/**
	 * �Զ�����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=levelup",method=RequestMethod.POST)
	public void doClsLevelUp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String year=request.getParameter("year");
		
		JsonEntity jeEntity=new JsonEntity();
		if(year==null||year.trim().length()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jeEntity.toJSON());return;
		}
		//��֤���Ƿ���������ִ��
		//�õ���һ��ѧ��
		TermInfo tm=new TermInfo();
		tm.setDYYear(year);
		PageResult presult=new PageResult();
		presult.setOrderBy(" u.YEAR ASC ");
		presult.setPageSize(1);		
		List<TermInfo> tmList=this.termManager.getList(tm, presult);
		if(tmList==null||tmList.size()<1){
			jeEntity.setMsg("�쳣���󣬷��з�����һ�����!��ȷ���ǿ���!");
			response.getWriter().print(jeEntity.toJSON());return;
		}
		String nextyear=tmList.get(0).getYear();
		//�õ���ǰnextyear�����е�������
		presult=new PageResult();
		presult.setPageSize(1);	
		ClassInfo clsentity=new ClassInfo();
		clsentity.setYear(nextyear);
		clsentity.setPattern("������");
		List<ClassInfo> nextClsList=this.classManager.getList(clsentity, presult);
		if(nextClsList!=null&&nextClsList.size()>0){
			jeEntity.setMsg("�쳣������"+nextyear+"�д������µ������༶!��ȷ��!");
			response.getWriter().print(jeEntity.toJSON());return;
		}
		
		if(this.classManager.doClassLevelUp(year)){
			jeEntity.setMsg("�Զ������ɹ�!��ˢ��ҳ��!");
			jeEntity.setType("success");
		}else
			jeEntity.setMsg("����ʧ��!ԭ��δ֪!");
		response.getWriter().print(jeEntity.toJSON());
	}

    @RequestMapping(params ="m=lzxUpdate")
    public void lzxUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String clsArrayjson=request.getParameter("clsarrayjson");
        if(clsArrayjson==null||clsArrayjson.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����û�з�����Ҫ��ӻ��޸ĵİ༶����Json!\"}");
            return;
        }
        net.sf.json.JSONArray clsJr=net.sf.json.JSONArray.fromObject(clsArrayjson);
        if(clsJr==null||clsJr.isEmpty()){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����û�з�����Ҫ��ӻ��޸ĵİ༶����Json!\"}");return;
        }
        String hasClsid=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=clsJr.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object timeStr=clsJo.get("timestamp");
            Object schoolid=clsJo.get("lzx_school_id");
            Object classid=clsJo.get("classid");
            Object className=clsJo.get("class_name");
            Object pattern=clsJo.get("pattern");
            Object classGrade=clsJo.get("class_grade");
            Object year=clsJo.get("year");
            Object type= clsJo.get("type");
            Object subjectid=clsJo.get("subject_id");
            Object isflag=clsJo.get("isflag");
            Object key=clsJo.get("key");
            //��֤��ز���
            if(timeStr==null||timeStr.toString().trim().length()<1||!UtilTool.isNumber(timeStr.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬵�½ʱ�������ȱ��!\"}");return;
            }
            if(schoolid==null||schoolid.toString().trim().length()<1||!UtilTool.isNumber(schoolid.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬷�УIDΪ��!!\"}");return;
            }
            if(classid==null||classid.toString().trim().length()<1||!UtilTool.isNumber(classid.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬰༶IDΪ��!!\"}");return;
            }
            if(className==null||className.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬰༶����Ϊ��!!\"}");
                return;
            }
            if(pattern==null||pattern.toString().trim().length()<1){
                //response.getWriter().print("�쳣���󣬰༶����Ϊ��!!");return;
                pattern="������";
            }
            if(classGrade==null||classGrade.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣�����꼶Ϊ��!!\"}");
                return;
            }
            if(year==null||year.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����ѧ��Ϊ��!!\"}");
               return;
            }
            if(type==null||type.toString().trim().length()<1){
               // response.getWriter().print("�쳣���󣬰༶����Ϊ��!!");return;
                type="NORMAL";
            }
            if(pattern!=null&&pattern.toString().trim().equals("�ֲ��")){
                if(subjectid==null||subjectid.toString().trim().length()<1||!UtilTool.isNumber(subjectid.toString())){
                    response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬷ֲ��İ༶Ҫ���ѧ��ID,ѧ��IDΪ��!!\"}");
                    return;
                }
            }
            if(isflag==null||isflag.toString().trim().length()<1||!UtilTool.isNumber(isflag.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬰༶�Ƿ�����Ϊ��!!\"}");return;
            }
            if(key==null||key.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����keyΪ��!!\"}");
                return;
            }

            //��֤�Ƿ�����������
            Long ct=Long.parseLong(timeStr.toString());
            Long nt=new Date().getTime();
            double d=(nt-ct)/(1000*60);
            if(d>3){//����������
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣������Ӧ��ʱ!�ӿ�����������Ч!\"}");
               return;
            }
            //��֤key
            String md5key=timeStr.toString()+schoolid;

            md5key+=classid.toString()+className.toString()+classGrade.toString()+type.toString()+timeStr.toString();
            md5key= MD5_NEW.getMD5ResultCode(md5key);//����md5����
            if(!md5key.trim().equals(key.toString().trim())){//�����һ�£���˵���Ƿ���½
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬷Ƿ���½!!\"}");return;
            }
            //��֤ͨ��
            ClassInfo cls=new ClassInfo();
            cls.setClassname(className.toString());
            cls.setClassgrade(classGrade.toString());
            cls.setYear(year.toString());
            cls.setPattern(pattern.toString());
            List<ClassInfo> clsList=this.classManager.getList(cls,null);
            if(clsList!=null&&clsList.size()>0){
                hasClsid=(hasClsid==null?classid.toString():hasClsid+","+classid.toString());
                continue;
            }

            cls.setType(type.toString());

            cls.setClassid(Integer.parseInt(classid.toString()));
            cls.setIsflag(Integer.parseInt(isflag.toString()));
            if(pattern.toString().trim().equals("�ֲ��")){
                cls.setSubjectid(Integer.parseInt(subjectid.toString()));
            }
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.classManager.getSaveOrUpdateSql(cls,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.classManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                 if(hasClsid!=null){
                     response.getWriter().println("{\"type\":\"success\",\"msg\":\""+hasClsid+"\"}");
                 }else
                      response.getWriter().println("{\"type\":\"success\"}");
             }else{
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����ԭ��δ֪!\"}");return;
            }
        }else{
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"û�п���ӻ��޸ĵİ༶��¼���Բ���!\"}");return;
        }
    }

    /**
     * ��֪�У�ɾ������
     * @param request
     * @param response
     */
    @RequestMapping(params="m=lzxDel",method=RequestMethod.POST)
    public void lzxDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String clsArrayjson=request.getParameter("clsarrayjson");
        if(clsArrayjson==null||clsArrayjson.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����û�з�����Ҫ��ӻ��޸ĵİ༶����Json!\"}");return;
        }
        net.sf.json.JSONArray clsJr=net.sf.json.JSONArray.fromObject(clsArrayjson);
        if(clsJr==null||clsJr.isEmpty()){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����û�з�����Ҫ��ӻ��޸ĵİ༶����Json!\"}");return;
        }
        String noDelClsId=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=clsJr.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object timeStr=clsJo.get("timestamp");
            Object schoolid=clsJo.get("lzx_school_id");
            Object classid=clsJo.get("classid");
            Object key=clsJo.get("key");
            //��֤��ز���
            if(timeStr==null||timeStr.toString().trim().length()<1||!UtilTool.isNumber(timeStr.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬵�½ʱ�������ȱ��!\"}");return;
            }
            if(schoolid==null||schoolid.toString().trim().length()<1||!UtilTool.isNumber(schoolid.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬷�УIDΪ��!!\"}");return;
            }
            if(classid==null||classid.toString().trim().length()<1||!UtilTool.isNumber(classid.toString())){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬰༶IDΪ��!!\"}");return;
            }
            if(key==null||key.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����keyΪ��!!\"}");
                return;
            }

            //��֤�Ƿ�����������
            Long ct=Long.parseLong(timeStr.toString());
            Long nt=new Date().getTime();
            double d=(nt-ct)/(1000*60);
            if(d>3){//����������
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣������Ӧ��ʱ!�ӿ�����������Ч!\"}");return;
            }
            //��֤key
            String md5key=timeStr.toString()+schoolid;

            md5key+=classid.toString()+timeStr.toString();
            md5key= MD5_NEW.getMD5ResultCode(md5key);//����md5����
            if(!md5key.trim().equals(key.toString().trim())){//�����һ�£���˵���Ƿ���½
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣���󣬷Ƿ���½!!\"}");
                return;
            }
            //�鿴�Ƿ����ѧ����¼
            ClassUser cutmp=new ClassUser();
            cutmp.setClassid(Integer.parseInt(classid.toString()));
            PageResult presult=new PageResult();
            presult.setPageSize(1);
            presult.setPageNo(1);
            List<ClassUser> cuList=this.classUserManager.getList(cutmp,presult);
            if(cuList!=null&&cuList.size()>0){
                noDelClsId=(noDelClsId==null?classid.toString():noDelClsId+","+classid);
                continue;
            }
            //��֤ͨ��
            ClassInfo cls=new ClassInfo();
            cls.setClassid(Integer.parseInt(classid.toString()));
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.classManager.getDeleteSql(cls, sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.classManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                if(noDelClsId!=null&&noDelClsId.toString().trim().length()>0){
                    response.getWriter().println("{\"type\":\"success\",\"msg\":\""+noDelClsId+"\"}");
                }else
                    response.getWriter().println("{\"type\":\"success\"}");
            }else{
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"�쳣����ԭ��δ֪!\"}");
            }
        }else{
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"û�п���ӻ��޸ĵİ༶��¼���Բ���!\"}");
        }
    }
}





















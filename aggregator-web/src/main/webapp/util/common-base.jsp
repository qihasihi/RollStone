<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>   
<%@page import="com.school.entity.RoleUser"%>
<%@page import="com.school.util.UtilTool"%>
<%@page import="com.school.util.JsonEntity"%> 
<%@page import="com.school.entity.UserInfo"%>
<%@page import="java.math.BigDecimal"%>

<%
	response.setHeader("Cache-Control", "Public"); 
	response.setHeader("Pragma", "no-cache"); 
	response.setDateHeader("Expires", 0); 
%>      
  
<%
    String proc_name=UtilTool.utilproperty.getProperty("PROC_NAME");
	String basePath = request.getScheme() + "://"
            + UtilTool.utilproperty.getProperty("IP_ADDRESS")
			+"/"+proc_name + "/";
			
	String fileSystemIpPort=UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD");//"http://202.99.47.77:80/";//request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+"/";;
    UserInfo u=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
			 boolean isTeacher=false,isStudent=false,isBzr=false;
 List<RoleUser> cruList=null;
// System.out.println(request.getRequestURI().trim().replaceAll("/",""));
// System.out.println(request.getServletPath().replaceAll("/",""));

    /**
     * 模块名称
     *   1:资源平台
     *   2:教学平台
     */
    Integer modelType=0;

if(!(request.getRequestURI().trim().replaceAll("/","").equals(proc_name)
		||!request.getServletPath().replaceAll("/","").equals(proc_name+"login.jsp")
		||!request.getServletPath().replaceAll("/","").equals(proc_name+"userRegister.jsp")
		)){
	if(u==null){
		response.getWriter().print("<script type='text/javascript'>alert('"
				+UtilTool.msgproperty.getProperty("NO_LOGINED")+"');location.href='"
				+UtilTool.getCurrentLocation(request)+"';</script>");
		return;  
	}else{
		cruList=u.getCjJRoleUsers();
	
		for(RoleUser ru : cruList){
			if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_TEACHER_ID)){
				isTeacher=true;

			}else if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_STU_ID)){
				isStudent=true;
			}
            if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_TEACHER_ID))
                isBzr=true;
		} 
	}   
	session.setAttribute("currentUserRoleList",cruList);		
}else{
	if(u!=null){		
		cruList=u.getCjJRoleUsers();
	
		for(RoleUser ru : cruList){
			if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_TEACHER_ID)){
				isTeacher=true;
			}else if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_STU_ID)){
				isStudent=true;
			}
		} 
	}
	session.setAttribute("currentUserRoleList",cruList);	
}
%> 
<%!/**
页面权限验证
*/
boolean validateFunctionRight(HttpServletResponse response,UserInfo u,BigDecimal serviceid,boolean isshow){
		/*if(!UtilTool.functionRight(u,serviceid)){
			if(isshow){
				JsonEntity je=new JsonEntity();
				je.setMsg(UtilTool.msgproperty.getProperty("NO_SERVICE_RIGHT")); 
				try{
					response.getWriter().println(je.getAlertMsg());
					response.getWriter().print("<script type='text/javascript'>history.go(-1);</script>");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return false;
		}*/
		return true;
} %>

<link rel="stylesheet" type="text/css"	href="<%=basePath %>css/dtree.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WdatePicker.css" />
<!-- <link rel="stylesheet" type="text/css"	href="<%=basePath %>util/progressupload-1/css/progressupload.css" /> -->
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/jquery.autocomplete.css"/>
<!-- <script src="<%=basePath %>js/common/jquery-1.9.1.js"></script> -->
<script src="<%=basePath %>util/xheditor/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=basePath %>js/common/common.js"></script>   
<script src="<%=basePath %>js/common/date-extend-property.js"></script> 
      
		
		<script type="text/javascript"	src="<%=basePath %>js/common/pageControl-0.1.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common/dtree.js"></script> 
		
		

		<link rel="stylesheet" type="text/css"	href="<%=basePath %>css/common.css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath %>jcpt/css/master.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>jcpt/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>jcpt/css/jcpt.css"/>
  
		<link rel="stylesheet" type="text/css"	href="<%=basePath %>css/jquery-ui-1.10.2.custom.css" />

		<script src="<%=basePath %>util/My97DatePicker/WdatePicker.js" language="javascript" type="text/javascript"></script>
		<!-- <script src="<%=basePath %>util/xheditor/xheditor-1.1.6-zh-cn.js"type="text/javascript"></script>-->

		<!-- xheditor 1.2.1 -->
		<script src="<%=basePath %>util/xheditor/xheditor-1.2.1.min.js"type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath %>util/xheditor/xheditor_lang/zh-cn.js"></script>
		
		<!-- 文件上传
		<script src="<%=basePath %>js/common/ajaxUpload.js"	type="text/javascript"></script> -->
			<script src="<%=basePath %>js/common/notes-zhengzhou-0.1-zh-cn.js"	type="text/javascript"></script>
		<script src="<%=basePath %>js/common/ajaxfileupload.js" type="text/javascript"></script>

		<script type="text/javascript" src="<%=basePath %>js/common/jquery.json-2.4.js"></script> 

<script type="text/javascript" src="<%=basePath %>js/common/UUID.js"></script>  
<script type="text/javascript" src="<%=basePath %>js/common/jquery.autocomplete.js"></script>  
			<!-- 上传控件
	<script type="text/javascript" src="<%=basePath %>js/common/jquery-migrate-1.1.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common/knockout-2.2.1.js"></script>
-->
		<!-- ckeditor -->
		<script type="text/javascript" src="<%=basePath %>ckeditor/ckeditor.js"></script>
		<script type="text/javascript" src="<%=basePath %>ckfinder/ckfinder/ckfinder.js"></script> 



<!-- <script type="text/javascript" src="<%=basePath %>js/common/JsonRpcClient.js"></script> -->
<% 

	Integer grade_role_id=UtilTool._ROLE_GRADE_LEADER_ID; //年级组长
	Integer grade_fu_role_id=UtilTool._ROLE_GRADE_FU_LEADER_ID; //副年级组长
	Integer dept_role_id=UtilTool._ROLE_DEPT_LEADER_ID; //部门主任
	Integer dept_fu_role_id=UtilTool._ROLE_DEPT_FU_LEADER_ID; //副部门主任
	Integer teach_role_id=UtilTool._ROLE_TEACH_LEADER_ID; //教研组长
	Integer teach_fu_role_id=UtilTool._ROLE_TEACH_FU_LEADER_ID; //副教研组长
	Integer prepare_leader_id=UtilTool._ROLE_PREPARE_LEADER_ID; //备课组长
	Integer dept_manage_role=UtilTool._ROLE_FREE_DEPT_LEADER_ID;//部门负责人
	Integer stu_role_id=UtilTool._ROLE_STU_ID;
	Integer tea_role_id=UtilTool._ROLE_TEACHER_ID;
	Integer admin_role_id=UtilTool._ROLE_ADMIN_ID;
	Integer bzr_role_id=UtilTool._ROLE_CLASSADVISE_ID;
   /*Object title=request.getSession().getAttribute("_TITLE");
    if(title==null)
        request.getSession().setAttribute("_TITLE",UtilTool.utilproperty.getProperty("CURRENT_PAGE_TITLE"));
    title=request.getSession().getAttribute("_TITLE");*/
    String webTitle="北京四中网校--数字化校园";//java.net.URLDecoder.decode(title.toString());
	

%>        
      
<script type="text/javascript"> 
	var $STU_ID=<%=stu_role_id%>,$TEA_ID=<%=tea_role_id%>,$PARENT_ID="3",$ADMIN_ID=<%=admin_role_id%>,$BANZR_ID=<%=bzr_role_id%>;
	var $GRADE_ID=<%=grade_role_id%>;
	var $GRADE_FU_ID=<%=grade_fu_role_id%>;
	var $DEPT_ID=<%=dept_role_id%>;
	var $DEPT_FU_ID=<%=dept_fu_role_id%>;
	var $TEACH_ID=<%=teach_role_id%>;
	var $TEACH_FU_ID=<%=teach_fu_role_id%>; 
	var $PREPARE_ID=<%=prepare_leader_id%>;
    var $DEPT_FZR_ID=<%=dept_manage_role%>;
 
	var isStudent=<%=isStudent%>;
	var isTeacher=<%=isTeacher%>;    
	var fileSystemIpPort='<%=fileSystemIpPort%>'; 
</script>
<title><%=webTitle%></title>
	
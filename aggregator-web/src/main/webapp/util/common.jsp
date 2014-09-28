<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.school.entity.RoleUser"%>
<%@page import="com.school.util.UtilTool"%>
<%@page import="com.school.util.JsonEntity"%>
<%@page import="com.school.entity.UserInfo"%>
<%@page import="com.school.entity.UserIdentityInfo"%>
<%@page import="java.math.BigDecimal"%>
<%
    /**
     * 数据库连接公用文件
     * 功能：
     * 刷新时间间隔检查和限制
     * 数据库连接
     * mod：20031029
     */

/**
 * 模块名称
 *   1:资源平台
 *   2:教学平台
 */
Integer modelType=0;
%>
<jsp:useBean id="htLastURI" class="java.util.Hashtable" scope="session"/>
<%
    //防刷新
    try{
        String uri = (String)htLastURI.get("uri");
        String time = (String)htLastURI.get("accesstime");
        int timelength = 2;
        if(uri==null)  {
            htLastURI.put("uri",request.getRequestURI()+(request.getQueryString()==null?"":"?"+request.getQueryString()));
            htLastURI.put("accesstime",System.currentTimeMillis()+"");
        }  else  {
            if(uri.equals(request.getRequestURI()+(request.getQueryString()==null?"":"?"+request.getQueryString())))    {
                if((System.currentTimeMillis()-Long.parseLong(time))/1000<=timelength)      {
%>
<html>
<head>
    <title>数据查询</title>
    <meta http-equiv='Content-Type' content='text/html; charset=GBK'>
    <script language="javascript">
        function toClose()
        {
            window.location.reload();
            return
        }
        var timmer=window.setTimeout('toClose()',5*1000);
    </script>
</head>
<body> 
请勿频繁刷新！ 
</body>
</html>
<%
                    return;
                }  else  {
                    htLastURI.put("uri",request.getRequestURI()+(request.getQueryString()==null?"":"?"+request.getQueryString()));
                    htLastURI.put("accesstime",System.currentTimeMillis()+"");
                }
            }else{
                htLastURI.put("uri",request.getRequestURI()+(request.getQueryString()==null?"":"?"+request.getQueryString()));
                htLastURI.put("accesstime",System.currentTimeMillis()+"");
            }
        }
    }catch(Exception e){
    }
%>




<%
//	response.setHeader("Cache-Control", "Public");
//	response.setHeader("Pragma", "no-cache");
//	response.setDateHeader("Expires", 0);

    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
%>      
  
<%
    String proc_name=UtilTool.utilproperty.getProperty("PROC_NAME");
	String basePath = request.getScheme() + "://"
			+ UtilTool.utilproperty.getProperty("IP_ADDRESS")
            +"/"+proc_name + "/";
			
	String fileSystemIpPort=UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD");//"http://202.99.47.77:80/";//request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+"/";;
    UserInfo u=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
			 boolean isTeacher=false,isStudent=false,isBzr=false,isWxJw=false,isteaidentity=false,isstuidentity=false;
 List<RoleUser> cruList=null;
if(!(request.getRequestURI().trim().replaceAll("/","").equals(proc_name)
		||!request.getServletPath().replaceAll("/","").equals(proc_name+"login.jsp")
		||!request.getServletPath().replaceAll("/","").equals(proc_name+"userRegister.jsp")
		)){
	if(u==null){
		response.getWriter().print("<script type='text/javascript'>alert('"
				+UtilTool.msgproperty.getProperty("NO_LOGINED")+"');location.href='"
				+UtilTool.getCurrentLocation()+"';</script>");
		return;
	}else{
		cruList=u.getCjJRoleUsers();
	
		for(RoleUser ru : cruList){
			if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_TEACHER_ID)){
                if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_CLASSADVISE_ID))
                    isBzr=true;
				isTeacher=true;
			}else if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_STU_ID)){
				isStudent=true;
			}else if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_WXJW_ID))
                isWxJw=true;
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
            if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_CLASSADVISE_ID))
                isBzr=true;
            if(ru!=null&&ru.getRoleid().equals(UtilTool._ROLE_WXJW_ID))
                isWxJw=true;
		}


	}
    Object cutUidentity=request.getSession().getAttribute("cut_uidentity");
    if(cutUidentity!=null){
        List<UserIdentityInfo> uittList=(List<UserIdentityInfo>)cutUidentity;
        if(uittList!=null&&uittList.size()>0){
            for (UserIdentityInfo uita:uittList){
                if(uita!=null&&uita.getIdentityname()!=null&&uita.getIdentityname().trim().equals("教职工")){
                    isteaidentity=true;
                }
                if(uita!=null&&uita.getIdentityname()!=null&&uita.getIdentityname().trim().equals("学生")){
                    isstuidentity=true;
                }
            }
        }
        session.setAttribute("currentUserRoleList",cruList);
    }
}
    String testhost = "http://web.etiantian.com";
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
<link rel="stylesheet" type="text/css"	href="<%=basePath %>css/common.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/master.css"/> 
		<link rel="stylesheet" type="text/css" href="<%=basePath %>css/style.css"/>



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

	
        <%--<link rel="stylesheet" type="text/css" href="<%=basePath %>css/jcpt.css"/>--%>
		<!-- <link rel="stylesheet" type="text/css"	href="<%=basePath %>css/jquery-ui-1.10.2.custom.css" /> -->

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
<%--<script type="text/javascript" src="<%=basePath %>js/common/jquery.rotate.1-1.js"></script>--%>
<!--
<script type="text/javascript" src="<%=basePath %>js/common/UUID.js"></script>
-->
<script type="text/javascript" src="<%=basePath %>js/common/jquery.autocomplete.js"></script>  
			<!-- 上传控件
	<script type="text/javascript" src="<%=basePath %>js/common/jquery-migrate-1.1.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common/knockout-2.2.1.js"></script>
-->
		<!-- ckeditor -->
		<script type="text/javascript" src="<%=basePath %>ckeditor/ckeditor.js"></script>
		<script type="text/javascript" src="<%=basePath %>ckfinder/ckfinder/ckfinder.js"></script>

        <!--ueditor-->
        <script  type="text/javascript" src="<%=basePath %>ueditor/ueditor.config.js"></script>
        <script   type="text/javascript" src="<%=basePath %>ueditor/ueditor.all.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>ueditor/themes/default/css/ueditor.css">
<% 

	Integer grade_role_id=UtilTool._ROLE_GRADE_LEADER_ID; //年级组长
	Integer grade_fu_role_id=UtilTool._ROLE_GRADE_FU_LEADER_ID; //副年级组长
	Integer dept_role_id=UtilTool._ROLE_DEPT_LEADER_ID; //部门主任
	Integer teach_role_id=UtilTool._ROLE_TEACH_LEADER_ID; //教研组长
	Integer dept_manage_role=UtilTool._ROLE_FREE_DEPT_LEADER_ID;
	Integer stu_role_id=UtilTool._ROLE_STU_ID;
	Integer tea_role_id=UtilTool._ROLE_TEACHER_ID;
	Integer admin_role_id=UtilTool._ROLE_ADMIN_ID;
	Integer bzr_role_id=UtilTool._ROLE_CLASSADVISE_ID;
    /*Object title=request.getSession().getAttribute("_TITLE");
    if(title==null)
        request.getSession().setAttribute("_TITLE",UtilTool.utilproperty.getProperty("CURRENT_PAGE_TITLE"));
    title=request.getSession().getAttribute("_TITLE");*/
    String webTitle="北京四中网校--数字化校园";//java.net.URLDecoder.decode(title.toString());
    if(request.getSession().getAttribute("fromType")!=null&&request.getSession().getAttribute("fromType").toString().trim().equals("lzx")){
        webTitle="北京四中教育云";
    }
  //  System.out.println(webTitle);

%>
      
<script type="text/javascript"> 
	var $STU_ID=<%=stu_role_id%>,$TEA_ID=<%=tea_role_id%>,$PARENT_ID="3",$ADMIN_ID=<%=admin_role_id%>,$BANZR_ID=<%=bzr_role_id%>;
	var $GRADE_ID=<%=grade_role_id%>;
	var $DEPT_ID=<%=dept_role_id%>;
	var $TEACH_ID=<%=teach_role_id%>;
 
	var isStudent=<%=isStudent%>;
	var isTeacher=<%=isTeacher%>;
    var isBzr=<%=isBzr%>;
	var fileSystemIpPort='<%=fileSystemIpPort%>';

</script>  
<title><%=webTitle%></title>


<script src="http://web.etiantian.com/js/o/jplugins/ui/1.8.16/js/jquery-ui-1.8.16.custom.min.js"></script>
<script src="http://web.etiantian.com/js/o/jplugins/cross-domian/1.0.3/jquery.xdomainrequest.min.js"></script>
<script src="http://web.etiantian.com/js/o/jplugins/ba-postmessage/0.5/jquery.ba-postmessage.min.js"></script>
<script src="http://web.etiantian.com/js/o/jplugins/ba-bbq/1.2.1/jquery.ba-bbq.min.js"></script>

<script src="<%=testhost%>/js/ett/frame-bridge/jquery.top-dialog.js" charset="utf-8"></script>
<script src="<%=testhost%>/js/ett/frame-bridge/jquery.top-bridge.js" charset="utf-8"></script>
	
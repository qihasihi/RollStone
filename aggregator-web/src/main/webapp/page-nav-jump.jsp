<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.school.entity.UserInfo"%>
<%@page import="com.school.entity.RoleUser"%>
<%
 String url1=request.getParameter("url");
 String role=request.getParameter("roleid");
 if(url1==null||role==null){
 	%>
 	<script type="text/javascript">
		alert('异常错误!原因：参数异常!');
	</script>
 	<%
 	return;
 }
 String[] urlArray=url1.split("\\|");
 String[] roleArray=role.split("\\|");
 if(urlArray==null||roleArray==null
 	||urlArray.length!=roleArray.length){
 	%>
 	<script type="text/javascript">
		alert('异常错误!原因：参数异常!');
	</script>
 	<%
 	return;
 	}
 UserInfo u=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
 if(u==null){
 	%>
 	<script type="text/javascript">
		alert('您尚未登陆，请登陆后重试!!');
		window.close();
	</script>
 	<%
 	return;
 }
 List<RoleUser> ruList=u.getCjJRoleUsers();
 if(ruList==null||ruList.size()<1){
 	%>
 	<script type="text/javascript">
		alert('当前登陆的用户，信息不完整!!');
		window.close();
	</script>
 	<%
 	return;
 }
 String url=null;
 //进行比较
 for(RoleUser ru:ruList){
 	if(ru==null||ru.getRoleid()==null){
 		continue;
 	}
 	boolean isok=false;
 	for(int i=0;i<roleArray.length;i++){
 		if(roleArray[i].toString().equals(ru.getRoleid().toString())){
 			isok=true;
 			url=urlArray[i];
 			break;
 		}
 	}
 	if(isok&&url!=null)
 		break;
 }
 if(url!=null){
 	%>
<script type="text/javascript">
	location.href="<%=url%>";
</script>
 	<%
 }

%>
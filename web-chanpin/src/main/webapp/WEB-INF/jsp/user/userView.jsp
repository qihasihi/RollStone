<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.school.entity.RoleInfo"%>
<%@ include file="/util/common-jsp/common-yhqx.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">   
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数字化校园</title>
<script type="text/javascript">
$(function(){
	var myDate = new Date();
    var num = new Number(myDate.getTime());
	$("#selfinfo").load("user?m=selfinfoview&t="+num);
});
function selfDiv(n){
	if(n==1){
		$("#editBtn").show();
		$("#selfinfo").load("user?m=selfinfoview");
	}else{
		$("#editBtn").hide();
		$("#selfinfo").load("user?m=edituserinfo");
	}
}

function closeDiv(div){  
	imgareaselectObj.setOptions({remove:true,hide:true});
	closeModel(div);   
}
</script>
</head>

<body>
<%
request.setAttribute("pageType","userview" );
%>
<%@include file="/util/head.jsp" %>

<%@include file="/util/nav-base.jsp" %>

<div class="jxpt_layout">
<p class="jxpt_icon01">用户信息</p>
<div class="yhqx_grxx_content">
    <p class="public_title">基础信息<a id="editBtn" href="javascript:selfDiv(2);" class="an_blue_small" style="float:right">修改资料</a></p>
	<div id="selfinfo">
	</div>
 <c:forEach items="${roleUser}" var="role">
 
    <c:if test="${role.roleinfo.roleid==1}">
	<p class="public_title">学生信息</p>
	<div id="otherinfo">
	<script>$("#otherinfo").load("user?m=studentinfoview");</script>
	</div>
	</c:if>
	
	<c:if test="${role.roleinfo.roleid==2}">
	<p class="public_title">教师信息</p>
	<div id="otherinfo">
	<script>$("#otherinfo").load("user?m=teacherinfoview");</script>
	</div>
	</c:if>
	
</c:forEach>

</div>
</div>

<div class="foot">主办方：*****分校&nbsp;&nbsp;&nbsp;&nbsp;协办方：北京四中龙门网络教育技术有限公司</div>
</body>
</html>

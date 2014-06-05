<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common-jsp/common-hdxt.jsp"%>
<%
UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
List<RoleUser> roleList = user.getCjJRoleUsers();
Integer roleid = roleList.get(0).getRoleid();
String userid=user.getRef();
%>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'activity_list.jsp' starting page</title>
     <script type="text/javascript" src="<%=basePath %>js/activity/activity_list.js"></script>
	<script type="text/javascript">
			var p1;
			var userid="<%=userid%>";
			$(function(){
					p1 = new PageControl({
						post_url:'activity?m=ajaxlist',
						page_id:'page1',
						page_control_name:'p1',
						post_form:document.page1form,
						gender_address_id:'page1address',
						http_operate_handler:activityListReturn,
						new_page_html_mode:true,
						return_type:'json',
						page_no:1, 
						page_size:20,
						rectotal:0,
						pagetotal:1,
						operate_id:"mainTbl"
					});

					pageGo("p1");
			});
</script>

  </head>
  
  <body>
  <%@include file="/util/head.jsp"  %>   
	  <div class="jxpt_layout">
	   <%@include file="/util/nav.jsp" %>
	 	 <p class="t_r p_20_10"><a href="javascript:toAdd()" class="an_blue_small">新建活动</a><%if(roleid==4){ %><a href="javascript:toCheck()" class="an_blue_small">审核活动</a><%} %><a href="activitysite?m=list" class="an_blue_small">场地管理</a></p>
	    <table  border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_a">
	    	 <colgroup span="2" class="w150"></colgroup>
		    <colgroup span="2" class="w130"></colgroup>
		    <colgroup class="w130"></colgroup>
		    <colgroup span="2" class="w90"></colgroup>
		    <colgroup class="w100"></colgroup>
		    <tbody id="mainTbl"></tbody>
		</table>
		<p >
		    <form id="page1form" name="page1form" method="post">
		    	<div align="right" id="page1address"></div>
		    </form>
		</p>
	</div>
	<%@include file="/util/foot.jsp"  %>
<div id="siteDetail" class="public_windows_layout w710 h490" style="display:none">
		<%--<table cellpadding="0px" cellspacing="0px" border="0px"
				bgcolor="white">				
				<tr>
					<td ></td>
					<td>					
						    <div  style="background: url(css/images/bg01_100618.gif) no-repeat 0 -62px;height:19px;	padding-top:4px;">
						    <span  id="ct_title">场地冲突情况</span>
						 	<span style="float:right;padding-right: 5px"> 
						 	   	<a href="javascript:closeModel('siteDetail')">关闭
							</a> 
						 	</span>
						 	<span  style="float:left;">提示：红色字体代表冲突活动!</span>
						    </div>
						    <div style="height:550px;overflow-y:auto">
							<table style="background-color:white" cellspacing="0" cellpadding="3" border="0" class="Tc Table Tab" 
								id="conflictSiteTbl"  style="width:100%;"> 
							</table>
							</div>
					</td>
					<td ></td>
				</tr>
				<tr>
					<td ></td>
					<td ></td>
					<td ></td>
				</tr>
			</table> 
		--%>
			<p class="f_right"><a href="javascript:closeModel('siteDetail','hide');pageGo('p1')" title="关闭"><span class="public_windows_close"></span></a></p>
			  <p>提示：红色字体代表时间有冲突的活动！</p>
			  <table style="background-color:white" cellspacing="0" cellpadding="3" border="0" class="public_tab2" 
											id="conflictSiteTbl"  style="width:100%;"> 
										</table>
		</div>
  </body>

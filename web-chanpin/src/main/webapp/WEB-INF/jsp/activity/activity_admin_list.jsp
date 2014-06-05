<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common-jsp/common-hdxt.jsp"%>
<%
UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
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
						post_url:'activity?m=ajaxadminlist',
						page_id:'page1',
						page_control_name:'p1',
						post_form:document.page1form,
						gender_address_id:'page1address',
						http_free_operate_handler:validateAdminParam,
						http_operate_handler:activityAdminReturn,
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
  
  <body><%@include file="/util/head.jsp"  %>   
	  <div class="jxpt_layout">
	   <%@include file="/util/nav.jsp" %>
  <p class="jxpt_icon06"><span><a href="activity?m=list">返回</a></span>审核活动</p>
      <p class="p_20"><span class="font14">活动名称：</span>
    <input name="at_name" id="at_name" type="text" class="public_input w250" />
      <span class="font14 p_l_20">    审核状态：</span>
		<select id="shState" name="shState">
			<option value="">==选择查询==</option>
			<option value="0">已公布</option>
			<option value="1">未公布</option>
			<option value="2">审核中</option>
		</select>
    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getListByParam();" class="an_blue_small">查询</a></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_a" >
	     <colgroup span="2" class="w180"></colgroup>
	    <colgroup span="2" class="w140"></colgroup>
	    <colgroup span="2" class="w80"></colgroup>
	    <colgroup class="w160"></colgroup>
	    <tbody id="mainTbl"></tbody>
	 </table>
    	<form name="page1form" id="page1form" action="" method="post">
		    <p class="Tr Mt10">
		    		<div align="right" id="page1address"></div> 
		    </p>
    	</form>
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

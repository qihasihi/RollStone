<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common.jsp"%>
<%
UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
String userid=user.getRef();
%>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'classview-user.jsp' starting page</title>
     <script type="text/javascript" src="<%=basePath %>js/ethosaraisal/class_ethos_info.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
    	$(function(){
    		//可以允许使用回车键
       		document.onkeypress=function(e){
       			return true;
       		};      	  
       		$("select").val('');
       	})      
    </script>   
  </head>
  
  <body>
    <div class="content">
    <div class="xfpb_layoutR">
      <p class="Pt10">请选择查询条件：</p>
      <p class="Ptb10"> 
       <input type="hidden" name="year" id="year"/>
  	           学期：<select id="termId" name="termName" onchange="getTermWeeks()">  				
  			     <option value="">==请选择==</option>
  						<c:if test="${!empty termList}">
  							<c:forEach items="${termList}" var="tms">
  								<option value="${tms.ref }">${tms.year} ${tms.termname }</option>
  							</c:forEach>
  						</c:if>						
  		     </select>  
        周次：
       <select id="weekId" name="weekName">  				
  			     <option value="">==请选择==</option>
  						<option value="1">==第一周==</option> 				 			
  		     </select>  
        年级：
          <select  id="grade" name="grade" onchange="getClasssByGrade()">  				
  		         <option value="">==请选择==</option>
						<c:if test="${!empty gradeList}">
							<c:forEach items="${gradeList}" var="tms">
								<option value="${tms.gradevalue }">${tms.gradename}</option>
							</c:forEach>
						</c:if>
              </select>
         班次：
          <select id="classId" name="className"> 
		 			<option value="">==请选择==</option>
		      </select>
          <span class="Pl20"><a href="javascript:void('');" onclick="showClassStatices('dataTbl');"><img src="images/an42.gif" width="57" height="23" border="0" title="查询"/>cha</a></span></p><br />
        <table border="0" cellpadding="0" cellspacing="0" class="Tab Tc">
		<col class="W130" />
		<col class="W500" />
		<col class="W130" />
		<col class="W65" />
		<tbody id="dataTbl" >		
			<tr><td colspan=4>请依次选择‘学期’-> '周次' -> '年级' -> '班次'，点击查询!</td></tr>
          </tbody>
        </table>
	  <p class="Pt20" id="explortExl"></p>
    </div>
	<div class="xfpb_layoutL">	 
	 <p class="Pt10"><a href="classethos?m=tousergrade" >按年级查看</a></p>
	  <p class="Pt10"><span class="F_blue">按班级查看</span></p>
	  <p class="Pt10"><a href="stuethos?m=touserstudent"">按学生查看</a></p>
    </div>
	<div class="clear"></div>
    </div>
  <div id="footer">2010   copyright   北京四中</div>
  
  <div class="W546 white_content" id="showDetailDiv">
  <div class="windowT01"><a href="javascript:void('');" onclick="closeModel('showDetailDiv');"><img src="images/an14.gif" alt="关闭" width="15" height="15" border="0" /></a>
  	<div class="Tc F_b" align="center">详情查看</div>
  </div>
  <div class="windowC01">
    <div class="H200 Plr20" style="overflow:auto">
      <p class="Pt10" id="detailText">编辑场地信息,编辑场地信息,编辑场地信息.</p>       
    </div>
  </div>
  <div class="windowB01"></div>
</div>

<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
  </body>


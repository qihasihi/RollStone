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
    
    <title>My JSP 'gradeview-user.jsp' starting page</title>
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
      	    //初始化
      		$("#wk_one").attr("checked",true);
      	
       		$("select").val('');
       		//禁止多周，单周选择 
       		$("select").filter(function(){return this.id.indexOf('weekid_')!=-1}).attr("disabled",true);
       	})
       
    </script>
  </head>
  
  <body>
   <div class="content">
    <div class="xfpb_layoutR">
      <p class="Pt10">请选择查询条件：</p>
      <p class="Pt10"> 学期：
      <select id="termId" name="termName" onchange="getTermWeeks()">  				
  			<option value="">==请选择==</option>
  						<c:if test="${!empty termList}">
  							<c:forEach items="${termList}" var="tms">
  								<option value="${tms.ref }">${tms.year} ${tms.termname }</option>
  							</c:forEach>
  						</c:if>					
  		</select> 
        年级：
         <select  id="grade" name="grade">  				
  		<option value="">==请选择==</option>
						<c:if test="${!empty gradeList}">
							<c:forEach items="${gradeList}" var="tms">
								<option value="${tms.gradevalue }">${tms.gradename}</option>
							</c:forEach>
						</c:if>
  </select>
         排序条件：
         <select id="orderColumn"> 
 			<option value="">==请选择==</option> 		
 			<option value="class_Name">班级</option> 			
 			<!--<c:if test="${!empty orderColumn}">
 				<s:iterator value="#request.orderColumn" var="t">
 					<option value="${t.dictionaryValue }">${t.dictionaryName}</option>
 				</s:iterator>
 			</c:if>-->
 			<option value="sumScore">总分</option>
 			<option value="rank">排名</option> 
		 </select>
      </p>
      <p class="Pt10">
        <input type="radio" onchange="radioClicked(this)" id="wk_one" name="weekselect"/>
        单周&nbsp;&nbsp;&nbsp;&nbsp;周次：
        <select id="weekId" name="weekId">  				
  			<option value="">==请选择==</option>
  						<option value="1">==第一周==</option>
  		</select>
      </p>
      <p class="Pt10">        
        <input onchange="radioClicked(this)"  type="radio"  name="weekselect"/>
        多周&nbsp;&nbsp;&nbsp;&nbsp;起始周：
        <select id="weekid_begin"><option value="">==请选择==</option>
  						<option value="1">==第一周==</option></select>
      结束周：
      <select id="weekid_end"><option value="">==请选择==</option>
  						<option value="1">==第一周==</option></select>
      </p>
      <p class="Pb10 Tc"><a href="javascript:void('');" onclick="showGradeStatices('dataTbl')"><img src="images/an42.gif" width="57" height="23" border="0" title="查询"/></a></p>
       <!--  <div class="913.2">
        <table width="913.2" border="0" cellpadding="0" cellspacing="0" class="Tab W80 Tc">
        <tr><th>班级</th><th>集会分数</th><th>卫生分数</th><th>财产分数</th><th>宿舍集体分</th><th>其他分数</th><th>考勤汇总分</th>
					<th>违纪汇总分</th><th>好人好事</th><th>奖励加分</th><th>总分</th><th>排名</th></tr>
        </table>
        </div>-->
        <div class="913.2" style="overflow-x:auto">
        <table width="913.2" border="1" cellpadding="0" id="dataTbl" cellspacing="0" class="Tab W80 Tc">
          <tr  class="Trbg1">
            <td  colspan="12"> 请依次选择学期，年级，单周或多周，进行查询！</td>               
          </tr>
          </table>       
      </div>
	  <p class="Pt10" id="explortExl" style="display:none"></p>
    </div>
	<div class="xfpb_layoutL">
	  <p class="Pt10"><span class="F_blue">按年级查看</span></p>
	  <p class="Pt10"><a href="classethos?m=touserclass">按班级查看</a></p>
	  <p class="Pt10"><a href="stuethos?m=touserstudent"">按学生查看</a></p>
    </div>
	<div class="clear"></div>
    </div>
  <div id="footer">2010   copyright   北京四中</div>
  </body>
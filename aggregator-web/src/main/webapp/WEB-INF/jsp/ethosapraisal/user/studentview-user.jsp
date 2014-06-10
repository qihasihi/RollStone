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
    
    <title>My JSP 'studentview-user.jsp' starting page</title>
    <script type="text/javascript" src="<%=basePath %>js/ethosaraisal/stu_ethos_info.js"></script>
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
     		 // rdoCheckedRetrive();
       	})
       	
       	/**
       	*
       	*查看的班级类型。
       	*/
       	function rdoCheckedRetrive() {
       		var classIdObj=$("#classId");
       		if(classIdObj.val().Trim().length<1){
       			alert("您尚未选择班级，请先选择班级后操作！\n\n提示：必须先依次选择学期，年级，才能选择班级!");
       			classIdObj.focus();
       			return;
       		}
       		if($("#selStuNoOrStuName").attr("checked")==true){
       			$("#txtSelStuNo").removeAttr("disabled");
       		}else{
       			$("#txtSelStuNo").attr("disabled",true);
       		}
       	}
      
      function termChange() {
      	$("#grade").val('');
      	$("#classId").html("<option value=''>==请选择==</option>");
      }
      
    </script>
  </head>
  
  <body>
    <div class="content">
    <div class="xfpb_layoutR">
      <p class="Pt10">请选择查询条件：</p>
      <p class="Pt10"> 学年：
        <select id="termId" name="termName" onchange="termChange()">  				
  						<option value="">==请选择==</option>
  						<c:if test="${!empty termList}">
  							<c:forEach items="${termList}" var="tms">
  								<option value="${tms.ref }">${tms.year} ${tms.termname }</option>
  							</c:forEach>
  						</c:if>						
  					</select>

        年级：
  <select  id="grade" name="grade" onchange="loadClass()">  				
			  		<option value="">==请选择==</option>
					<c:if test="${!empty gradeList}">
							<c:forEach items="${gradeList}" var="tms">
								<option value="${tms.gradevalue }">${tms.gradename}</option>
							</c:forEach>
						</c:if>
			  </select>
        班级：<select id="classId" onchange="loadStuName()">
  				<option value="">==请选择==</option>
  				
  			</select>
  </p>
      <p class="Pt10">
        <input type="radio" onchange="rdoCheckedRetrive()" name="selType" id="selStuNoOrStuName"/>学号/姓名
        <input type="text"  disabled="disabled" id="txtSelStuNo" class="InputBorder W160" />
      <input type="hidden" id="selStuId"/> 
        </p>
      <p class="Pt10">              
       <input type="radio" onchange="rdoCheckedRetrive()" checked name="selType" id="selClass"/>全班
        全班</p>
      <p class="Pb10 Tc"><a href="javascript:void();" onclick="loadUserStudent_KQ()"><img src="images/an42.gif" width="57" height="23" border="0" title="查询"/></a></p>
      <div> 
       <div class="TabLayout1Column W826"> 
		      <ul class="XxkLabel">  
		       <li id="liKQ" class="up"><a href="javascript:void();" onclick="loadUserStudent_KQ()">考勤</a></li>
		       <li id="liHG"><a href="javascript:void('');" onclick="loadUserStudent_WJ()">行规</a></li> 
		       <li id="liZH"><a href="javascript:void('');" onclick="loadUserStudent_ZH()">综合</a></li>	       
		      </ul>
		      </div>
		      <br/>  
     	 <div class="TabLayout1Column W826" style="overflow: hidden" >  
        <table border="0" cellpadding="0" cellspacing="0"  id="tdcolumn" class="Tab W80 Tc clearit">
          
        </table>
        </div>
        <div class="TabLayout1 W826" style=" margin: 0;">
        <table border="0" cellpadding="0" cellspacing="0" class="Tab W80 Tc"  id="dataTbl">
           <tr  class="Trbg1">
            <td colspan="10"> 请依次选择学期，年级，班级，[或选择学号]进行查询！</td>              
          </tr>
         </table>
      </div>
      </div>
	  <p class="Pt10" id="explortExl"></p>
    </div> 
	<div class="xfpb_layoutL">
	  <p class="Pt10"><a href="classethos?m=tousergrade">按年级查看</a></p>
	  <p class="Pt10"><a href="classethos?m=touserclass">按班级查看</a></p>
	  <p class="Pt10"><span class="F_blue">按学生查看</span></p>
    </div>
	<div class="clear"></div>
    </div>
  <div id="footer">2010   copyright   北京四中</div>
  <div class="W546 white_content" id="showDetailDiv">
  <div class="windowT01"><a href="javascript:void('');" onclick="closeModel('showDetailDiv')"><img src="images/an14.gif" alt="关闭" width="15" height="15" border="0" /></a>
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


<script type="text/javascript">
  $("#dataTbl td ").css("width",770);   
</script>
  </body>

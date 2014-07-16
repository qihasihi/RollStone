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
    <script type="text/javascript" src="<%=basePath %>js/ethosaraisal/class_ethos_info.js"></script>
    <title>My JSP 'admin_ethos_info.jsp' starting page</title>
    
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
       			$("#datatbl input[type='text']").unbind("keyup");
       			$("#datatbl input[type='text']").unbind("blur");
       		//初始化
       		$("textarea").css("font-size","12px");
       		
       		$("select").val('');
       		$("textarea").val('');
       		$("input[type='text']").val('');
       		$("#doadd").attr("disabled",true);
       		$("#doupdate").attr("disabled",true);
       		$("#dodelete").attr("disabled",true);
       		$("input[type='text']").unbind("keydown");
       		$("input[type='text']").unbind("keyup");
       		//设置文本框，不能输入
       		$("input[type='text']").css("ime-mode","disabled");
       		document.getElementById("termId").options[1].selected=true;
       		document.getElementById("weekId").options[1].selected=true;
       		document.getElementById("grade").options[1].selected=true;
       		getClasssByGrade();
       		adminSearchClassDate('dataTbl',false,false);
       		//getTermWeeks(true);
       		
       			/**
		       	*按键 释放的时候
		       	*/
		    $("input[type='text']").attr("maxlength",5);
       		$("input[type='text']").bind("keypress",function(event){
       			var keywhich=event.which==0?event.keyCode:event.which ; 
       			if(keywhich==8||keywhich==46||keywhich==43||keywhich==45||keywhich>=48&&keywhich<=57){
       				if(keywhich==43||keywhich==45)
       					if(this.value.Trim().indexOf("+")!=-1||this.value.Trim().indexOf("-")!=-1)
       						return false;  
       				return true; 
       			}
       			 return false;   		
       		})
       	})
       
       
    </script>
  </head>
  
  <body>
    <div class="content">
 <form action="" id="adminform" method="post">
 

    <table border="0" id="datatbl" cellpadding="0" cellspacing="0" class="Table border_Tab W974">
	<col class="W200" />
	<col class="W770" />
      <tr>
        <td colspan="2" align="center"><p class="Ptb10">
          学期：
              <select id="termId" name="termName" onchange="getTermWeeks()">  				
  						<option value="">==请选择==</option>
  						<c:if test="${!empty termList}">
  							<c:forEach items="${termList}" var="tms">
  								<option value="${tms.ref }">${tms.year} ${tms.termname }</option>
  							</c:forEach>
  						</c:if>						
  					</select>  	
  					<input type="hidden" name="year" id="year"/> 
           <!--   <span class="Pl20">学期：</span>
                <select name="select4">
                <option>第一学期</option>
                <option>第二学期</option>
                  </select>-->
              <span class="Pl20">周次：</span>
            	 <select id="weekId" name="weekId" onchange="getClasssByGrade()">  				
  						<option value="">==请选择==</option>
  						<option value="1">==第一周==</option>
  					</select>
              <span class="Pl20">年级：</span>
              <select  id="grade" name="gradeName" onchange="getClasssByGrade()">  				
  						<option value="">==请选择==</option>
						<c:if test="${!empty gradeList}">
							<c:forEach items="${gradeList}" var="tms">
								<option value="${tms.gradevalue }">${tms.gradename}</option>
							</c:forEach>
						</c:if>
  					</select>
              <span class="Pl20">班级：</span>
              <select id="classId" name ="gradeNamegradeNamegradeName" onchange="clearClassOption(false)">			
  						<option value="">==请选择==</option> 
  					</select>
              <span class="Pl20"><a href="javascript:void('')" onclick="getClassXFDetail()"><img src="images/an42.gif" width="57" height="23" border="0" title="查询"/></a></span></p>
        </td>
      </tr> 
      <tr>
        <td align="center"><p class="Pt10"> 
        <a href="javascript:doAddOrUpdateClassXF();" action="doAddOrUpdateClassXF()">添加/修改</a></p>
        <p class="Pt10"><a href="javascript:void('');" id="delA" action="doArraydelete()">删除</a></p>
        <p class="Pt10"><a href="javascript:void('');" action="doclearforclass()">清空</a></p>
        <p class="Pt10"><a href="javascript:toStuEthosList();" action="toInputStuEthos()">输入学生校风</a></p>
        <p class="Pt10"><a href="javascript:adminSearchClassDate('dataTbl',false,true);"  id="gradeView" action="adminSearchClassDate('dataTbl',false,true)">年级汇总情况</a></p>
        <p class="Ptb10"><a href="javascript:void('');" onclick="window.close();">退出</a></p></td>
        <td><table border="0" cellpadding="0" cellspacing="0" class="M0 Table noborder_Tab">
		<col class="W120" />
	    <col class="W403" />
	    <col class="W200" />
          <tr>
            <th>项目</th>
            <th>情况</th>
            <th>分数</th>
          </tr>
          <tr>
            <td align="center"><br />
            集会情况</td>
            <td>
            <textarea class="border_box H30 W380" readonly="readonly" id="assemblyremark"></textarea>
            </td>
            <td>
            <input type="text"  readonly="readonly" class="border_box W200 H30" id="assemblyscore"/>
            </td>
          </tr>  
          <tr>
            <td align="center"><br />
            卫生情况</td>
            <td><textarea class="border_box H30 W380" readonly="readonly" id="hygieneremark"></textarea>
            </td>
            <td><input  id="hygienescore" readonly="readonly"  type="text" class="border_box W200 H30" /></td>
          </tr>
          <tr>
            <td align="center"><br />
            宿舍集体情况</td>
            <td><textarea readonly="readonly" id="dormitoryremark" class="border_box H30 W380"></textarea></td>
            <td><input id="dormitoryscore" readonly="readonly" type="text" class="border_box W200 H30" /></td>
          </tr>
           <tr>
            <td align="center"><br />
            财产情况</td>
            <td><textarea readonly="readonly" id="moneyremark" class="border_box H30 W380"></textarea></td>
            <td><input id="moneyscore" readonly="readonly" type="text" class="border_box W200 H30" /></td>
          </tr>
          <tr>
            <td align="center"><br />
            其他情况</td>
            <td><textarea readonly="readonly" id="otherremark" class="border_box H30 W380"></textarea></td>
     	   <td><input id="otherscore" readonly="readonly" type="text" class="border_box W200 H30" /></td>
          </tr>
          <tr>
            <td align="center"><br />
            奖励加分</td>
            <td><textarea readonly="readonly" id="awardremark" class="border_box H30 W380"></textarea></td>
            <td><input readonly="readonly" id="awardscore" type="text" class="border_box W200 H30" /></td>
          </tr>
          <tr>
            <td align="center"><br />
            个人考勤汇总</td>
            <td><textarea readonly="readonly" id="kqremark" class="border_box H30 W380"></textarea></td>
            <td><input readonly="readonly" id="kqscore" type="text" class="border_box W200 H30" /></td>
          </tr>
          <tr>
            <td align="center"><br />
            个人违纪汇总</td>
            <td><textarea readonly="readonly" id="wjremark" class="border_box H30 W380"></textarea></td>
            <td><input readonly="readonly"  type="text" id="wjscore" class="border_box W200 H30" /></td>
          </tr>
          
           <tr>
            <td align="center"><br />
            好人好事汇总</td>
            <td><textarea readonly="readonly" id="gdremark" class="border_box H30 W380"></textarea></td>
            <td><input readonly="readonly" id="gdscore"  type="text" class="border_box W200 H30" /></td>
          </tr>
        </table>
          <p>&nbsp;</p></td>
      </tr>
    </table> 
    </form>
     <p class="F_b Pt15">班级数据汇总</p>
    <div class="TabLayout" style="padding-top: 0px; margin: 0;">
      <table  id="dataTbl" border="0" cellpadding="0" cellspacing="0" class="Tab W80 Tc">
        
      </table>
    </div>
</div>
</div>
  
    </div>
    <div id="footer">2010   copyright   北京四中</div>
  </body>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/util/common.jsp" %> 
<%@ page import="com.school.entity.TeacherInfo"%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数字化校园</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/pj.css"/>
<script type="text/javascript">
	$(function(){
		$("#yearid").change(function(){
			searchEvalCount();
		});
	}); 

	function searchEvalCount(){
		var yearid = $("#yearid").val();
		$.ajax({
			url:'teacherappraise?m=searchteachercount',
			type:'POST',
			data:{yearid:yearid,tch_id:'${tch_id}'},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				var html="<colgroup span='5' class='w160'></colgroup>";
				html+="<tr><th>教师姓名</th><th>班级</th><th>评教得分</th><th>百分制得分</th><th>排名</th></tr>";
				if(rps.type=="success"){
					$.each(rps.objList,function(idx,itm){
						html+="<tr>";
						html+="<td align='center'>"+itm.TEACHER_NAME+"</td>";
						html+="<td align='center'><a target='_blank' class='font-lightblue' href='teacherappraise?m=toClassResultDetailed&targetuserref="
								+itm.TARGET_USER_REF+"&classid="+itm.CLASS_ID+"&yearid="+itm.YEAR_ID+"'>"
								+itm.CLASS_GRADE+itm.CLASS_NAME+"</a></td>";
						html+="<td align='center'>"+itm.TOTAL_AVG+"</td>";
						html+="<td align='center'>"+(itm.SCORE100.length>5?itm.SCORE100.substring(0,5):itm.SCORE100)+"</td>";
						html+="<td align='center'>"+(idx+1)+"</td>";
						html+="</tr>";
					});
				}else{
					
					html+="<tr>";
					html+="<td colspan='5' align='center'>没有数据！</td>";
					html+="</tr>";
				}
				$("#dataTable").html(html);
			}
		});
	}
	</script>
</head>

<body>
<%@include file="/util/head.jsp" %>

<%@include file="/util/nav.jsp" %>

<div class="jxpt_layout">
  <p class="jxpt_icon06">评教结果</p>

 <div class="p_20">
 <p class="font14 p_b_10"><b>${tch.teachername }</b></p>
  <p class="font14 p_b_10">学年：
 <select id="yearid" name="yearid">
         <c:forEach items="${yearList}" var="yearItem">
      	 <option value="${yearItem.classyearid}">${yearItem.classyearname}</option>
     	 </c:forEach>
        </select>
 </p>
    <table id="dataTable" border="0" cellpadding="0" cellspacing="0" class="public_tab2">
    <colgroup span="5" class="w160"></colgroup>
      <tr>
        <th>姓名</th>
        <th>班级</th>
        <th>得分</th>
        <th>百分制得分</th>
        <th>排名</th>
      </tr>
      <c:forEach items="${dataList}" var="item" varStatus="stauts">
      <tr class="${status.index%2==1?'trbg2':''}">
       <td align="center">${item['TEACHER_NAME']}</td>
       <td align="center"><a target="_blank" class="font-lightblue" href="teacherappraise?m=toClassResultDetailed&targetuserref=${item['TARGET_USER_REF']}&classid=${item['CLASS_ID']}&yearid=${item['YEAR_ID']}">
     		  ${item['CLASS_GRADE']}${item['CLASS_NAME']}
       </td>
       <td align="center">${item['TOTAL_AVG']}</td>
       <td align="center">${fn:substring(item['SCORE100'], 0,5)}</td>
       <td align="center">${stauts.index+1}</td>
     </tr>
      </c:forEach>
  </table>
</div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

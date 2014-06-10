<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript">
$(function(){
	$("input[name='classid']").bind("change",function(){  
		getClassCommentList();
	});
	$("input[name='classid']").attr("checked",true);
	getClassCommentList();
}); 

function getClassCommentList(){
	var classid = $("#classid:checked").val();
	var commentobjectid =$("#commentobjectid").val();
	var commenttype = $("#commenttype").val();
	$.ajax({
		url:'commoncomment?m=getCourseCommentListAjax',
		data:{classid:classid,commentobjectid:commentobjectid,commenttype:commenttype},
		type:'post',
		dataType:'json',
		error:function(){
			alert('异常错误,系统未响应！');
		},success:function(rps){
			var tableHtml=" <colgroup><col class='w140' /><col class='w140' /><col class='w520' /><col class='w170' /></colgroup>"
			tableHtml+="<tr><th>学号</th><th>姓名</th><th>评语</th><th>参评时间</th></tr>";
			if(rps.type=="success"){
				var html="<strong>综合级别：</strong>";
				for(var i=1;i<=5;i++)
					if(i<=rps.msg)
						html+="<img src='images/star01_130423.png' width='17' height='17' align='absmiddle' />";
					else
						html+="<img src='images/star03_130423.png' width='17' height='17' align='absmiddle' />";
						
				$("#avg_socre").html(html+"&nbsp;"+rps.msg);
				$.each(rps.objList,function(idx,itm){
					tableHtml+="<tr>";
					tableHtml+="<td>"+itm.stuno +"</td>";
					tableHtml+="<td>"+itm.stuname +"</td>";
					tableHtml+="<td>"+itm.commentcontext +"</td>";
					tableHtml+="<td>"+itm.ctimeString+"</td>";
					tableHtml+="</tr>";					
				});
			}else{
				tableHtml+="<tr><td colspan='5'>没有评论信息！</td></tr>";
				$("#avg_socre").html("");
			}
			$("#commentlist").html(tableHtml);
		}
	});
}

function showCourseList(){
	var ulobj=$("#ul_courselist");
	if(ulobj.css("display")=="none")
		ulobj.show();
	else
		ulobj.hide();	  
}
</script>
</head>
<body>
<%@include file="/util/head.jsp" %>
<div class="jxpt_layout">
<%@include file="/util/nav.jsp" %>
  <div class="jxpt_keti_title">
    <p>${course.coursename }<a href="javascript:showCourseList();"><img src="images/an01_130423.png" width="27" height="28" align="absbottom" class="p_l_20" /></a></p>
    <ul class="font-white" style="display:none;" id="ul_courselist">
    <c:if test="${!empty courseList}">
   		<c:forEach items="${courseList}" var="c">
   			<c:if test="${c.courseid!=commentobjectid}">
	   			<li>
	   				<a href="tpres?toTeacherIdx&courseid=${c.courseid }">${c.coursename }</a>
	   			</li>
   			</c:if>	
   		</c:forEach>
   	</c:if> 
    </ul>
  </div>
  <div class="public_ejlable_layout">
    <ul>
      <li >   
		<a href="task?toTaskList&courseid=${commentobjectid }">学习任务</a> 
	  </li>
	  <li> 
		<a href="tpres?toTeacherIdx&courseid=${commentobjectid }">专题资源</a> 
	  </li>    
	  <li>    
		<a href="activecolumns/toStuIdx?teachercourseid=${commentobjectid }">互动空间</a>
	 </li>   
	 <li class="crumb">    
		<a href="commoncomment?m=toCourseCommentList&objectid=${commentobjectid }">专题评价</a>
	 </li>
	 
    </ul>
  </div>
  <div class="jxpt_content_layout">
    <p class="p_t_10 font14"><strong>选择班级：</strong>
    <c:forEach items="${course.classEntity}" var="cls">
   			<input id="classid" name="classid" type="radio" id="classid" value="${cls.CLASS_ID }" />
   			${cls.CLASS_NAME}&nbsp;&nbsp;&nbsp;&nbsp;
   		</c:forEach>
    </p>
    <p id="avg_socre" class="p_t_10 font14"><strong>综合级别：</strong></p>
    <input type="hidden"  id="commentobjectid" value="${commentobjectid}" />
    <input type="hidden"  id="commenttype" value="${commenttype}" />
    <table id="commentlist" border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
    </table>
  </div>
</div>
<div class="foot">主办方：*****分校&nbsp;&nbsp;&nbsp;&nbsp;协办方：北京四中龙门网络教育技术有限公司</div>
</body>
</html>



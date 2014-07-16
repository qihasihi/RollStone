<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/util/common.jsp" %> 
<%@ page import="com.school.entity.TeacherInfo"%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>评教</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pj.css"/>
    <script type="text/javascript">
    var hideColNum=0;
    
    $(function(){
		<c:if test="${currentYear!=null}">
	    	$("#yearid").val(${currentYear.classyearid});
	    </c:if>
	    
	    searchEvalCount();
	}); 
    
	function searchEvalCount(){
		var yearid = $("#yearid").val();
		var grades = new Array();
		$("input[type='checkbox'][name='grades']:checked").each(function() {grades.push($(this).val());});
		var subjects = new Array();
		$("input[type='checkbox'][name='subjects']:checked").each(function() {subjects.push($(this).val());});
		$.ajax({
			url:'teacherappraise?m=searchevalcount',
			type:'POST',
			data:{yearid:yearid,grades:grades.join(','),subjects:subjects.join(',')},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				var html="<caption class='t_r font-purple'><a id='h_col' style='display:none;' href='javascript:showOrHideCol(false);'>"
					+"隐藏</a><a id='s_col' href='javascript:showOrHideCol(true);'>"
					+"得分明细<img width='14' height='12' src='images/an36_130705.png'></a></caption>";
				html+="<tr><th>教师姓名</th><th>年级</th><th>学科</th><th>评教得分</th><th>百分制得分</th><th>排名</th>";
				if(rps.type=="success"){
					if(rps.objList!=null && rps.objList[0].COL_AVG!=null && rps.objList[0].COL_AVG.length>0){
						hideColNum=rps.objList[0].COL_AVG.split(',').length;
						$.each(rps.objList[0].COL_AVG.split(','),function(idx,itm){
							html+="<th>第"+(idx+1)+"题</th>";
						});
					}
						
					html+="</tr>";
					$.each(rps.objList,function(idx,itm){
						html+="<tr>";
						html+="<td align='center'><a href='teacherappraise?m=teacher_watch_ban&tch_id="
								+itm.TARGET_USER_REF+"' target='_blank' class='font-lightblue'>"+itm.TEACHER_NAME+"</a></td>";
						html+="<td align='center'>"+itm.CLASS_GRADE+"</td>";
						html+="<td align='center'>"+itm.SUBJECT_NAME+"</td>";
						html+="<td align='center'>"+itm.TOTAL_AVG+"</td>";
						html+="<td align='center'>"+(itm.SCORE100.length>5?itm.SCORE100.substring(0,5):itm.SCORE100)+"</td>";
						html+="<td align='center'>"+(idx+1)+"</td>";
						if(itm.COL_AVG!=null && itm.COL_AVG.length>0)
							$.each(itm.COL_AVG.split(','),function(cidx,citm){
								html+="<td align='center'>"+citm+"</td>";
							});
						html+="</tr>";
					});
				}else{
					html+="</tr><tr>";
					html+="<td colspan='4' align='center'>没有数据！</td>";
					html+="</tr>";
				}
				$("#dataTable").html(html);
				showOrHideCol(false);
			}
		});
	}
	
	function showOrHideCol(flag){
		for(var i=0;i<hideColNum;i++){
			if(flag){
				$("#s_col").hide();
				$("#h_col").show();
				$("th:eq("+(6+i)+")",$("tr")).show();
				$("td:eq("+(6+i)+")",$("tr")).show();
			}else{
				$("#h_col").hide();
				$("#s_col").show();
				$("th:eq("+(6+i)+")",$("tr")).hide();
				$("td:eq("+(6+i)+")",$("tr")).hide();
				
			}
		}
	}
	
	function refreshPJStat(){
		$.ajax({
			url:'teacherappraise?m=refresh_pj_stat',
			type:'POST',
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="success"){
					$("#r_p").html("已经执行数据更新操作....");
					alert("后台已经开始整理数据，请刷新查看！！");
				}else{
					alert("数据更新失败！");
				}
			}
		});
	}
	</script>
</head>

<body>
<%@include file="/util/head.jsp" %>

<%@include file="/util/nav.jsp" %>

<div class="jxpt_layout">
  <div class="public_lable_layout">
    <ul class="public_lable_list">
      <li><a href="timestep?m=list">评价时间</a></li>
      <li><a href="appraiseitem?m=list">编辑评价</a></li>
      <li class="crumb">评教结果</li>
    </ul>
  </div>
  
  <div class="p_20">
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 zt14">
    <col class="w100"/>
    <col class="w760"/>
    <tr>
      <th>学&nbsp;&nbsp;&nbsp;&nbsp;年：</th>
      <td><select id="yearid" name="yearid">
         <c:forEach items="${yearList}" var="yearItem">
      	 <option value="${yearItem.classyearid}">${yearItem.classyearname}</option>
     	 </c:forEach>
        </select></td>
    </tr>
    <tr>
      <th>年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
      <td><ul class="public_list1">
	      <c:forEach items="${gradeList}" var="gradeItem">
	        <li><input id="grades" name="grades" type="checkbox" value="'${gradeItem.gradevalue}'">${gradeItem.gradename}</li>
	      </c:forEach>
        </ul>
        </td>
    </tr>
    <tr>
      <th>学&nbsp;&nbsp;&nbsp;&nbsp;科：</th>
      <td><ul class="public_list1">
	      <c:forEach items="${subjectList}" var="subjectItem">
	      <li>
	      <input id="subjects" name="subjects" type="checkbox" value="${subjectItem.subjectid}"/>${subjectItem.subjectname}
	      </li>
	     </c:forEach>
      </ul></td>
    </tr>
    <tr>
      <th>&nbsp;</th>
      <td><a href="javascript:searchEvalCount();" class="an_blue">确&nbsp;定</a></td>
    </tr>
  </table>
 
	<input type="button" value="统计评教数据" onclick="refreshPJStat();"/>
	<div style="width:950px;overflow-x:auto;white-space:nowrap;">
   <table id="dataTable" border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_25 td85">
    </table>
    </div>
</div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

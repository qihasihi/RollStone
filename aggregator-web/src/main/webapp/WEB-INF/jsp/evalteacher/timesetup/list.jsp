<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common.jsp" %> 
<%@page import="com.school.entity.evalteacher.TimeStepInfo"%>  
<%  
	request.setAttribute("isSelect",true);	 //查询功能权限 
    request.setAttribute("isAdd",true);	 //添加功能权限
    request.setAttribute("isUpdate",true);	 //修改功能权限
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
    <title>评教时间管理</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pj.css"/>
	<script type="text/javascript">

	function saveTimestep(){
		var ref=$("#ref").val();
		var yearid=$("#yearid").val();
		var pjstarttime = $("#pjstarttime").val();
		var pjendtime = $("#pjendtime").val();
		var pjdesc = $("#pjdesc").val();
		if(yearid==0){
			alert("请选择学年！");
			return;
		}
		if(pjstarttime.Trim().length<1){
			alert("请选择起始时间！");
			return;
		}
		if(pjendtime.Trim().length<1){
			alert("请选择结束时间！");
			return;
		}
		if(ref.Trim().length<1){
			ref=0;
		}
		$.ajax({
			url:'timestep?m=save',
			type:'POST',
			data:{ 
				ref:ref,
				yearid:yearid,
				pjstarttime:pjstarttime,
				pjendtime:pjendtime,
				pjdesc:pjdesc,
			},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type!="success"){
					alert(rps.msg);
					return;
				}
				location.href='timestep?m=list';
			}
		});
	}
	
	function updateInfo(ref){
		$("#doSaveOrUpdate").html('修改');
		$.ajax({
			url:'timestep?m=gettimeinfo',
			type:'POST',
			data:{ref:ref},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type!="success"){
					alert(rps.msg);
				}else{
					$("#yearid").attr("disabled",true);
					$("#yearid").val(rps.objList[0].yearid);
					$("#ref").val(ref);
					$("#pjstarttime").val(rps.objList[0].pjstarttimeString);
					$("#pjendtime").val(rps.objList[0].pjendtimeString);
					$("#pjdesc").val(rps.objList[0].pjdesc);
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
      <li class="crumb">评价时间</li>
      <li><a href="appraiseitem?m=list">编辑评价</a></li>
      <li><a href="teacherappraise?m=admin_watch_ban">评教结果</a></li>
    </ul>
  </div>

<div class="p_20">
<form id="form1" name="form1" method="post" action="">
<input type="hidden" id="ref" name="ref" value=""/>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_tb_15 zt14">
    <col class="w100"/>
    <col class="w760"/>
    <tr>
      <th>学&nbsp;&nbsp;&nbsp;&nbsp;年：</th>
      <td><select id="yearid" name="yearid">
      <option value="0">-- 请选择  --</option>
      <c:forEach items="${classYearList}" var="cyi">
      <option value="${cyi.classyearid}">${cyi.classyearname}</option>
      </c:forEach>
      </select></td>
    </tr>
    <tr>
      <th>评价时间：</th>
      <td><input id="pjstarttime" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" type="text" name="pjstarttime">
    ~
    <input id="pjendtime" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" type="text" name="pjendtime">
    </td>
    </tr>
    <tr>
      <th>相关描述：</th>
      <td><textarea id="pjdesc" name="pjdesc" cols="45" rows="3"></textarea></td>
    </tr>
    <tr>
      <th>&nbsp;</th>
      <td><a id="doSaveOrUpdate" href="javascript:saveTimestep();" class="an_blue">添&nbsp;加</a>
      <a href="javascript:$('#from1').reset();;" class="an_blue">重&nbsp;置</a></td>
    </tr>
  </table>
  </form>
 </div>
  <p class="pj_title">评价时间管理</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_a">
      <col class="w160"/>
      <col class="w190"/>
      <col class="w190"/>
      <col class="w300"/>
      <col class="w120"/>
      <tr>
        <th>学年</th>
        <th>评价开始时间</th>
        <th>评价结束时间</th>
        <th>相关描述 </th>
        <th>操作</th>
      </tr>
      <c:forEach items="${timeStepList}" var="tsi" varStatus="status">
	  <tr class="${status.index%2==1?'trbg2':''}">
		<td>${tsi.classyearname }</td> 
		<td>${tsi.pjstarttime }</td> 
		<td>${tsi.pjendtime }</td> 
		<td>${tsi.pjdesc }</td> 
		<td>
		<c:if test="${tsi.status==1 }">
			<a class="font-lightblue" href="javascript:updateInfo(${tsi.ref });">修改</a></td>
		</c:if>
		<c:if test="${tsi.status==0 }">
			<font color="gary">修改</font></td>
		</c:if>  
	  </tr>
	  </c:forEach>
    </table>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>

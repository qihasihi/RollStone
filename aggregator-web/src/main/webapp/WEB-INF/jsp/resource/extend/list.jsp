<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common.jsp" %> 
<%@page import="com.school.entity.resource.ExtendInfo"%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>资源属性管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/resource/resbase.js"></script>
	<link rel="stylesheet" type="text/css"
			href="js/resource/resbase.css" />
	<script type="text/javascript">
	$(function(){
		document.onkeypress=function(e){}
		$("#updateButton").hide();
		$("#dependextendid").bind("change",function(){  
			getExtendValueOptions($(this).val());
		});
	})
	
	function getUpdateInfo(extendid){
		$("#resetButton").click();
		$("#editExtend").show();
		$.ajax({
			url:'extend?m=getextendinfo',
			type:'POST',
			data:{extendid:extendid},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="success"){
					$("#extendid").val(extendid);
					$("#updateButton").show();
					$("#addButton").hide();
					$("#extendname").val(rps.objList[0].extendname);
					$("#extendvaluelist").val(rps.objList[0].extendvaluelist);
					$("#dependextendid").val(rps.objList[0].dependextendid);
					if(rps.objList[0].dependextendid!=0)
						getExtendValueOptions(rps.objList[0].dependextendid,rps.objList[0].dependextendvalueid);
					else
						$("#devl").html("");
					$("input[name='isquery'][value='"+rps.objList[0].isquery+"']").attr("checked",true);
				}else{
					alert("无法获取该属性信息！");
				}
			}
		});
	}
	
	function addNewExtend(){
		var extendname = $("#extendname").val();
		var extendvaluelist = $("#extendvaluelist").val();
		var dependextendid = $("#dependextendid").val();
		var dependextendvalueid = $("#dependextendvalueid").val();
		var isquery = $("input:radio[name='isquery']:checked").val();
		if(extendname.Trim().length<1){
			alert("请填写属性名！");
			return;
		}
		if(extendvaluelist.Trim().length<1){
			alert("请填写属性值！");
			return;
		}
		
		$.ajax({
			url:'extend?m=ajaxsave',
			type:'POST',
			data:{ 
				extendname:extendname,
				extendvaluelist:extendvaluelist,
				dependextendid:dependextendid,
				dependextendvalueid:dependextendvalueid,
				isquery:isquery
			},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type!="success"){
					alert("添加失敗！");
					return;
				}
				location.href='extend?m=list';
			}
		});
	}
	
	function getExtendValueOptions(extendid,selected){
		$.ajax({
			url:'extend?m=getextendvaluelist',
			type:'POST',
			data:{extendid:extendid},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				var trHtml="";
				if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
					trHtml+="<td align='right'>关联节点：</td>"
					trHtml+="<td colspan='2'><select id='dependextendvalueid' name='dependextendvalueid'>"
					+"<option value='0' seclected >-- 无关联 --</option>";
					$.each(rps.objList,function(idx,itm) {
						trHtml+="<option value='"+itm.valueid+"'>"+itm.valuename+"</option>";
					});
					trHtml+="</select></td>";
				}
				$("#devl").html(trHtml);
				$("#dependextendvalueid").val(selected);
			}
		});
	}
	
	function doUpdateExtend(){
		var extendid=$("#extendid").val();
		var extendname = $("#extendname").val();
		var extendvaluelist = $("#extendvaluelist").val();
		var dependextendid = $("#dependextendid").val();
		var dependextendvalueid = $("#dependextendvalueid").val();
		var isquery = $("input:radio[name='isquery']:checked").val();
		if(extendid.Trim().length<1){
			alert("参数错误，无法进行修改！");
			return;
		}
		if(extendname.Trim().length<1){
			alert("请填写属性名！");
			return;
		}
		if(extendvaluelist.Trim().length<1){
			alert("请填写属性值！");
			return;
		}
		if(dependextendid==extendid){
			alert("资源属性不能关联自身节点！！");
			return;
		}
		$.ajax({
			url:'extend?m=update',
			type:'POST',
			data:{ 
				extendid:extendid,
				extendname:extendname,
				extendvaluelist:extendvaluelist,
				dependextendid:dependextendid,
				dependextendvalueid:dependextendvalueid,
				isquery:isquery
			},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type!="success"){
					alert("修改失败！"+rps.msg);
					return;
				}
				alert(rps.msg);
				location.href='extend?m=list';
			}
		});
	}
	
	function deleteExtend(ref){
		if(!confirm("确认删除？"))
			return;
		if(ref==null||ref<1){
			alert("参数错误！！");
			return;
		}
		$.ajax({
			url:'extend?m=del',
			type:'POST',
			data:{extendid:ref},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="success"){
					location.href='extend?m=list';
				}else{
					alert("删除失败！");
				}
			}
		});
	}
	
	function showAddDiv(){
		$("#editExtend").show();
		$("#resetButton").click();
		$("#updateButton").hide();
		$("#addButton").show();
	}
    </script>
  </head>  
   <body>
   <%List<ExtendInfo> extendList = (List<ExtendInfo>)request.getAttribute("extendList");  %>
  <a href="<%=basePath %>rs_index.jsp">返回首页</a><br/><br/>
  <a href="javascript:showAddDiv();">创建新属性类</a>
  <div  style="position:absolute; left:190px; top: 81px;">
  <%if(extendList!=null&&extendList.size()>0) {%>
  <table width="750">
  <tr>
      <th>属性名</th>
      <th>关联节点</th>
      <th>查询</th>
      <th>类型</th>
      <th>操作</th>
	</tr>
  <c:forEach items="${extendList}" var="extend">
  <tr>
      <td align="center">${extend.extendname}</td>
      <td align="center">${extend.dependname==null?"无":extend.dependname}</td>
      <td align="center">${extend.isquery gt 0?"参与":"不参与"}</td>
      <td align="center">${extend.enable gt 0?"自建属性":"默认属性"}</td>
      <td align="center"><a href="javascript:getUpdateInfo('${extend.extendid}')">修改</a>&nbsp;|&nbsp;<a href="javascript:deleteExtend('${extend.extendid}')">删除</a></td>
	</tr>
	</c:forEach>
	</table>
  <%}else{ %>
  	还没有扩张属性，请点击左上按钮进行添加。
  <%} %>
  <br/><br/>
  
  
  
  <div id="editExtend" style="position:absolute; left:18px;display:none">
  <form name="form1" method="post" action="">
  <input type="hidden" id="extendid" name="extendid" value=""/>
  <table width="500" border="1">
  <tr>
    <td width="96" align="right">属性名：</td>
    <td colspan="2"><label>
      <input id="extendname" name="extendname" type="text" size="20">
    </label></td>
  </tr>
  <tr>
    <td align="right">属性值：</td>
    <td colspan="2"><label>
      <textarea id="extendvaluelist" name="extendvaluelist" cols="30" rows="6"></textarea>
    </label></td>
  </tr>
  <tr>
    <td align="right">关联属性：</td>
    <td colspan="2">
      <select id="dependextendid" name="dependextendid">
        <option value="0" seclected >-- 无关联 --</option>
        <c:forEach items="${extendList}" var="e">
        <option value="${e.extendid}" seclected >${e.extendname}</option>
        </c:forEach>
      </select></td>
  </tr>
  <tr id="devl">
    <td align="right"></td>
    <td colspan="2"></td>
  </tr>
  <tr>
    <td align="right">参与查询：</td>
    <td colspan="2">
        <input type="radio" name="isquery" value="1">是&nbsp;&nbsp;
        <input type="radio" name="isquery" value="0">否</td>
  </tr>
   <tr>
    <td colspan="2" align="center">
      <input id="addButton" type="button" onClick="addNewExtend();" value="添加">
       <input id="updateButton" type="button" onClick="doUpdateExtend();" value="修改">
      </td>
    <td width="250" align="center"><input id="resetButton" type="reset" value="重置"></td>
  </tr>
</table>

  </form>
  </div>
  </div>
   </body>
</html>

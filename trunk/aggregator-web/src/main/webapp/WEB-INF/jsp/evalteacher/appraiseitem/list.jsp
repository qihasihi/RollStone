<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common.jsp" %> 
<%@page import="com.school.entity.evalteacher.AppraiseItemInfo"%>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>评教选项管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/pj.css"/>
<script type="text/javascript">

	$(function(){
		<c:if test="${currentYear==null}">
	    	alert("当前时间不属于任何学年范围内，无法编辑评教选项！");
	    	window.location.href="timestep?m=list";
	    </c:if>
	    <c:if test="${isstart==2}">
    		alert("评教已经开始，请不要修改评价题目！");
    	</c:if>
    	<c:if test="${isstart==3}">
			alert("评教已经结束，无法修改评价题目！");
			$.each($("input[name='current_input']"),function(idx,itm){
				$(itm).attr("disabled","disabled");	
			});
		</c:if>
		$("#displayRadio").attr("checked",true);
		$("#classYear").change(function(){
			getAppItem();
		});
		$("input[name='displayType']").bind("click",function(){  
			switch ($(this).val()){
			case '1': $("#currentYear").show();$("#oldYear").hide();break;
			case '2': $("#currentYear").hide();$("#oldYear").show();break;
			}
		});
		$("#oldYear").hide();
		getAppItem();
	}); 
	
	function doSave(){
		var ref = $("#ref").val();
		var name = $("#name").val();
		var option_a = $("#option_a").val();
		var option_b = $("#option_b").val();
		var option_c = $("#option_c").val();
		var option_d = $("#option_d").val();

		if(name.Trim().length<1){
			alert("请填写评教项标题！");
			return;
		}
		if(option_a.Trim().length<1){
			alert("请填写选项A！");
			return;
		}
		if(option_b.Trim().length<1){
			alert("请填写选项B！");
			return;
		}
		if(option_c.Trim().length<1){
			alert("请填写选项C！");
			return;
		}
		if(option_d.Trim().length<1){
			alert("请填写选项D！");
			return;
		}
		
		$.ajax({
			url:'appraiseitem?m=save',
			type:'POST',
			data:{ 
				name:name,
				option_a:option_a,
				option_b:option_b,
				option_c:option_c,
				option_d:option_d,
			},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type!="success"){
					alert(rps.msg);
					return;
				}
				location.href='appraiseitem?m=list';
			}
		});
	}
	
	function doUpdate(ref){
		var name = $("#"+ref+"_name").val();
		var option_a = $("#"+ref+"_option_1").val();
		var option_b = $("#"+ref+"_option_2").val();
		var option_c = $("#"+ref+"_option_3").val();
		var option_d = $("#"+ref+"_option_4").val();
		
		var ref_a = $("#"+ref+"_ref_1").val();
		var ref_b = $("#"+ref+"_ref_2").val();
		var ref_c = $("#"+ref+"_ref_3").val();
		var ref_d = $("#"+ref+"_ref_4").val();

		if(name.Trim().length<1){
			alert("请填写评教项标题！");
			return;
		}
		if(option_a.Trim().length<1){
			alert("请填写选项A！");
			return;
		}
		if(option_b.Trim().length<1){
			alert("请填写选项B！");
			return;
		}
		if(option_c.Trim().length<1){
			alert("请填写选项C！");
			return;
		}
		if(option_d.Trim().length<1){
			alert("请填写选项D！");
			return;
		}
		
		$.ajax({
			url:'appraiseitem?m=update',
			type:'POST',
			data:{ 
				ref:ref,
				name:name,
				ref_a:ref_a,
				ref_b:ref_b,
				ref_c:ref_c,
				ref_d:ref_d,
				option_a:option_a,
				option_b:option_b,
				option_c:option_c,
				option_d:option_d,
			},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type!="success"){
					alert(rps.msg);
					return;
				}
				location.href='appraiseitem?m=list';
			}
		});
	}
	
	function deleteAppItem(ref){
		if(ref==null||ref<1){
			alert("参数错误！！");
			return;
		}
		$.ajax({
			url:'appraiseitem?m=del',
			type:'POST',
			data:{ref:ref},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				if(rps.type=="success"){
					location.href='appraiseitem?m=list';
				}else{
					alert(rps.msg);
				}
			}
		});
	}
	
	function getAppItem(){
		$.ajax({
			url:'appraiseitem?m=ajaxList',
			type:'POST',
			data:{yearid:$("#classYear").val()},
			dataType:'json',
			error:function(){alert("网络异常")},
			success:function(rps){
				var html='<col class="w60" /><col class="w870" />';
				if(rps!=null&&rps.objList!=null&&rps.objList.length>0){
					var n=1;
					$.each(rps.objList,function(idx,itm){
						if(itm.istitle==1){
							html+='<tr><th>题目'+n+'：</th>';
							html+='<td><p><input type="text" class="public_input w650" value="'+itm.name+'" disabled/>&nbsp;&nbsp;</p>';
							html+='<span id="itm_'+itm.ref+'_answer_1"><strong>A：</strong>';
							html+='<input id="'+itm.ref+'_option_1" type="text" class="public_input w110" disabled/></span>&nbsp;&nbsp;&nbsp;';
							html+='<span id="itm_'+itm.ref+'_answer_2"><strong>B：</strong>';
							html+='<input id="'+itm.ref+'_option_2" type="text" class="public_input w110" disabled/></span>&nbsp;&nbsp;&nbsp;';
							html+='<span id="itm_'+itm.ref+'_answer_3"><strong>C：</strong>';
							html+='<input id="'+itm.ref+'_option_3" type="text" class="public_input w110" disabled/></span>&nbsp;&nbsp;&nbsp;';
							html+='<span id="itm_'+itm.ref+'_answer_4"><strong>D：</strong>';
							html+='<input id="'+itm.ref+'_option_4" type="text" class="public_input w110" disabled/></span>&nbsp;&nbsp;&nbsp;';
							html+='</p></td>';
							html+='</tr>';
							n++;
						}
					});
					$("#old_pj_tab").html(html);
					$.each(rps.objList,function(idx,itm){
						if(itm.itemid>0&&itm.istitle==0){
			  		  		$("#item_"+itm.itemid+"_answer_"+itm.optionid)
			  		  		.append("<input id='"+itm.itemid+"_ref_"+itm.optionid+"' type='hidden' value='"+itm.ref+"'/>");
			  		  		$("#"+itm.itemid+"_option_"+itm.optionid).val(itm.name);
						}
					});
				}else{
					html+="<tr><td colspan='2'>本学年没有编辑评价项！</td></tr>";
					$("#old_pj_tab").html(html);
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
      <li class="crumb">编辑评价</li>
      <li><a href="teacherappraise?m=admin_watch_ban">评教结果</a></li>
    </ul>
  </div>


  <p class="font14 p_20">
    <input type="radio" name="displayType" id="displayRadio" value="1" />
   编辑当前评价项&nbsp;&nbsp;&nbsp;&nbsp;
   <input type="radio" name="displayType" id="displayRadio" value="2" />
   查看历史评价项</p>
   <div id="currentYear">
 <c:if test="${currentYear!=null}">
 <p class="pj_title">${currentYear.classyearvalue }年评价项</p>
 <% int i=1; %>
 <table border="0" cellpadding="0" cellspacing="0" class="pj_tab">
 <col class="w60" />
 <col class="w870" />
 <c:forEach items="${appitemList}" var="item" varStatus="status">
  <c:if test="${item.istitle==1}">
   <tr>
     <th>题目<%=i++ %>：</th>
     <td><p>
       <input id="${item.ref}_name" name="current_input" type="text" class="public_input w650" value="${item.name}"/>
       &nbsp;&nbsp;
	   <c:if test="${isstart!=3 && isstart!=2}">
       		<a class="an_blue_small" href="javascript:doUpdate(${item.ref});">修改</a>
	  	 	<a class="an_blue_small" href="javascript:deleteAppItem(${item.ref});">删除</a>
	   </c:if>
	   </p>
       <p>
         <span id="item_${item.ref}_answer_1"><strong>A：</strong>
         <input  id="${item.ref}_option_1"  name="current_input" type="text" class="public_input w110"  value=""/></span>
         &nbsp;&nbsp;&nbsp;
         <span id="item_${item.ref}_answer_2"><strong>B：</strong>
         <input  id="${item.ref}_option_2"  name="current_input" type="text" class="public_input w110"  value=""/></span>
         &nbsp;&nbsp;&nbsp;
         <span id="item_${item.ref}_answer_3"><strong>C：</strong>
         <input  id="${item.ref}_option_3"  name="current_input" type="text" class="public_input w110"  value=""/></span>
         &nbsp;&nbsp;&nbsp;
         <span id="item_${item.ref}_answer_4"><strong>D：</strong>
         <input  id="${item.ref}_option_4"  name="current_input" type="text" class="public_input w110"  value=""/></span>
       </p></td>
   </tr>
   </c:if>   		
   </c:forEach>
   <c:if test="${isstart!=3 && isstart!=2}">
   <tr class="trbg2">
   <th>题目<%=i++ %>：</th>
	   <td><p>
	       <input id="name" name="name" type="text" class="public_input w650"/>
	       &nbsp;&nbsp;
	       </p>
	       <p>
	         <span><strong>A：</strong>
	         <input id="option_a" name="option_a" type="text" class="public_input w110"/></span>
	         &nbsp;&nbsp;&nbsp;
	         <span><strong>B：</strong>
	         <input id="option_b" name="option_b" type="text" class="public_input w110"></span>
	         &nbsp;&nbsp;&nbsp;
	         <span><strong>C：</strong>
	         <input id="option_c" name="option_c" type="text" class="public_input w110"></span>
	         &nbsp;&nbsp;&nbsp;
	         <span><strong>D：</strong>
	         <input id="option_d" name="option_d" type="text" class="public_input w110"></span>
	       </p>
	    </td>
    </tr>
    </c:if>
   <script type="text/javascript">
  		<c:if test="${!empty appitemList}">	
  		<c:forEach items="${appitemList}" var="item">
  		  	<c:if test="${item.itemid>0&&item.istitle==0}">
	  		  $("#item_${item.itemid}_answer_${item.optionid}")
	  		  		.append("<input id='${item.itemid}_ref_${item.optionid}' type='hidden' value='${item.ref}'/>");
	  		  $("#${item.itemid}_option_${item.optionid}").val("${item.name}");
  		  	</c:if>
  		  </c:forEach>
  		</c:if>  
  	</script>  
 </table>
  <c:if test="${isstart!=3 && isstart!=2}">
  	<p class="p_20 m_l_20"><a href="javascript:javascript:doSave();" class="an_blue_small">添加题目</a></p>
  </c:if>
 </c:if>
 </div>
 <div id="oldYear">
 <p class="pj_title">学年：
 <select id="classYear">
	 <c:forEach items="${classYearList}" var="classYear">
	 	<option value="${classYear.classyearid}">${classYear.classyearname}</option>
	 </c:forEach>
 </select>
 </p>
 <table border="0" cellpadding="0" cellspacing="0" class="pj_tab" id="old_pj_tab">
 </table>
 </div>
<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>
<div id="addNewQuestion" class="public_windows_layout w740 h90" style="display:none;">
<p class="f_right"><a href="javascript:closeModel('addNewQuestion');" title="关闭"><span class="public_windows_close"></span></a></p>
  <form name="form1" method="post" action="">
  <input type="hidden" id="ref" name="ref" value=""/>
  <table width="750" align="center">
    <tr>
      <td width="70" align="right">题目：</td>
      <td colspan="4"><input id="name" name="name" type="text" size="82"></td>
    </tr>
    <tr>
      <td align="right">选项：</td>
      <td width="150">&nbsp;A.<input id="option_a" name="option_a" type="text" size="15"></td>
      <td width="150">&nbsp;B.<input id="option_b" name="option_b" type="text" size="15"></td>
      <td width="150">&nbsp;C.<input id="option_c" name="option_c" type="text" size="15"></td>
      <td width="150">&nbsp;D.<input id="option_d" name="option_d" type="text" size="15"></td>
	  </tr>
	  <tr>
      <td colspan="5" align="center"><a class="an_blue_small" href="javascript:doSave();">保存</a></td>
	  </tr>
  </table>
   
  </form>
  </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

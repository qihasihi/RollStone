<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<script type="text/javascript" src="../js/interactive/theme.js"></script> 
		<script type="text/javascript">
			var clsid="${clsid}";		
			var columnsid="${columnsid}";
				var subjectid="${subjectid}";
			var p1;
			$(function(){
			$(".header_ico1 a").attr("href","../"+$(".header_ico1 a").attr("href"));
				$("input[type='text']").bind("keyup",function(){});
				$("input[type='text']").bind("keydown",function(){}); 
				if(columnsid==null){
					alert('异常错误，非法访问!参数异常!');return;
				}
				p1=new PageControl({
						post_url:'getThemeList',
						page_id:'page1',
						page_control_name:"p1",		//分页变量空间的对象名称
						post_form:document.page1form,		//form
						gender_address_id:'page1address',		//显示的区域
						http_free_operate_handler:validateParam,		//执行查询前操作的内容
						http_operate_handler:themePageReturn,	//执行成功后返回方法
						return_type:'json',								//放回的值类型
						page_no:1,					//当前的页数
						page_size:200000,				//当前页面显示的数量
						rectotal:0,				//一共多少
						pagetotal:1,
						operate_id:"tbl_body_data" 
					});
				//	$("#a_sel_columnsid").val(columnsid);	 
				//	$("#_sel_columnsid").val(columnsid);				
					$("input[name='a_state']:first").attr("checked",true);
					$("input[name='a_display']:first").attr("checked",true);					
				//$("#sel_search_clsid").val("${clsid}");	
				//classChangeSearchColumns(document.getElementById('sel_search_clsid'),'');//${columnsid}
				setTimeout(function(){pageGo('p1');},800);
			});	
			function checkedStuAll(type,id){			
				$('#studiv input[type="checkbox"]').attr('checked',(type==1?true:false));
				$("a[id='"+id+"']").attr("href","javascript:checkedStuAll("+(type==1?2:1)+",'"+id+"')");   
				$("a[id='"+id+"']").html((type==1?'反选':'全选')); 
			}
			/**
			*班级改变时，查询Columns
			*/
			function classChangeSearchColumns(obj,hddivid,toselOptsid){		
				var clsid=obj.value;
				var h='<option value="">==全部==</option>';
				var selObjHtml="#"+hddivid;
				if(clsid.Trim().length>0) 
					selObjHtml+=" input[value='"+clsid+"']";
				else
					selObjHtml+=" input[type='hidden']";
				$(selObjHtml).each(function(idx,itm){
					var itmParent=$(this).parent(); 
					h+="<option value='"+itmParent.val().Trim()+"'>"+itmParent.attr("text")+"</option>";
				});
				$("#"+toselOptsid).html(h); 
			}		 
		</script>
	</head>
  
  <body>   
  <%@include file="/util/head.jsp" %>
  <div  id="studiv" style="display:none" class="public_windows_layout w395 h450">
  <p class="f_right"><a href="javascript:;" onclick="closeModel('studiv')" title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_tb_10">用户查询</p>
  <p class="t_c m_b_10 p_b_10">用户名称：
    <input name="txtSearchVal" id="txtSearchVal" type="text" class="public_input w160" /><a class="an_gray" href="javascript:;" onclick="searchBeadCheck('txtSearchVal')">查询</a></p>
  <div class="h320 p_l_20 overflow"> 
  <ul class="public_list1">
  <c:forEach items="${classuserlist}" var="culist">
    <li>
        <input type="checkbox" value="${culist.userinfo.ref }|${culist.userinfo.realname}" name="ckx_stuname" id="stuname_${culist.userinfo.ref}"/>
  			<label for="stuname_${culist.userinfo.ref}">${culist.userinfo.realname}</label>
  	</li> 
   </c:forEach>
  </ul>
</div>
<p class="t_c p_t_20 clearit"><a class="an_gray"  href="javascript:checkedStuAll(1,'a_xuanze');" id="a_xuanze">全选</a>
    <!-- <a class="an_gray" href="1">重置</a> --><a class="an_gray" href="javascript:;" onclick="btnStuClick()" id="btnStuClick">确定</a>
	<input type="hidden" value="" name="stutype" id="stutype"/>  
</p>
</div>  
   
<div class="jxpt_layout">
<%@include file="/util/nav.jsp" %>
  <p class="jxpt_icon01">论题管理</p>  

  <div class="jxpt_content_layout">    
    <p class="font_strong p_l_10 font-blue font14"><span id="op_type">新建论题</span></p>
    <form action="" id="form1" name="form1">
      <input type="hidden" value="" id="u_theme_id" name="u_theme_id"/>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 zt14">
      <col class="w100"/>
      <col class="w780"/>
       <tr>
        <th><span style="color:red">&nbsp;</span>当前专题：</th>
        <td>${currentTCourse }</td>
      </tr>
      <tr>
        <th><span style="color:red">*</span>论题标题：</th>
        <td><input type="text" name="a_themetitle" maxlength="30" id="a_themetitle" class="public_input w450" />
        <span class="font-gray1">(最多30字)</span></td>
      </tr>
      <tr>
        <th><span style="color:red">&nbsp;</span>论题说明：</th>
        <td><textarea  id="a_themecontent" name="a_themecontent" class="public_input h90 w450"></textarea>
        <span class="font-gray1">(最多500字)</span></td>
      </tr>
      <tr>
        <th><span style="color:red">*</span>关&nbsp;键&nbsp;字：</th>
        <td><input id="a_keyword" type="text" class="public_input w450" />
        <span class="font-gray1">(各个关键字之间用逗号隔开)</span></td>
      </tr>
      <tr>
        <th><span style="color:red">*</span>所属栏目：</th>
        <td id="td_add_selCol">
        	<script type="text/javascript">
        		var h='';
        		<c:if test="${!empty columnslist}">
        			<c:forEach items="${columnslist}" var="coltmp">
        				var selObjArray=$("span[id='sp_col_${coltmp.classInfo.classgrade}${coltmp.classname}'] select");
	   					if(selObjArray.length<1){
	   						document.write("<span id='sp_col_${coltmp.classInfo.classgrade}${coltmp.classname}'><select id='a_sel_columnsid' name='a_sel_columnsid'><option value=''>==无==</option></select>&nbsp;&nbsp;&nbsp;</span>");
	   					}
	   				
	   					h='<option value="${coltmp.columnsid }">${coltmp.classInfo.classgrade}${coltmp.classname} ${coltmp.columnsname }</option>'; 
	   					$("span[id='sp_col_${coltmp.classInfo.classgrade}${coltmp.classname}'] select").append(h);
        			</c:forEach>	   				
   				</c:if>
        	</script>
   		</td>
        <td id="td_update_selCol"  style="display:none"> 
   		<select id='a_sel_columnsid' name='a_sel_columnsid'>
   			<c:if test="${!empty columnslist}">   			
	   				<c:forEach items="${columnslist}" var="coltmp">
	   					<option value="${coltmp.columnsid }">${coltmp.classInfo.classgrade}${coltmp.classname} ${coltmp.columnsname }</option>
	   				</c:forEach>
   				</c:if>
   				</select>
   		</td>        
      </tr>
      <tr>
        <th><span style="color:red">*</span>论题状态：</th>
        <td><c:if test="${!empty dicstate}">
   			<c:forEach items="${dicstate}" var="statetmp">
   				<input type="radio" name="a_state" id="a_state_${statetmp.dictionaryvalue }" value="${statetmp.dictionaryvalue }"/>
   				<label for="a_state_${statetmp.dictionaryvalue }">${statetmp.dictionaryname }</label>
   			</c:forEach>
   		</c:if></td>
      </tr>
      <tr>
        <th><span style="color:red">*</span>可见方式：</th>
        <td><c:if test="${!empty dicopen}">
   			<c:forEach items="${dicopen}" var="tmp">
   				<input type="radio" name="a_display" id="a_display_${tmp.dictionaryvalue }" onclick="changeGroupTr()" value="${tmp.dictionaryvalue }"/>
   				<label for="a_display_${tmp.dictionaryvalue }">${tmp.dictionaryname }</label> 
   			</c:forEach>
   		</c:if> </td> 
      </tr>
      <tr id="tr_group_display" style="display:none">
      <th>小&nbsp;&nbsp;&nbsp;&nbsp;组：</th>
      <td>
      	<p id="groupdiv"> <c:forEach items="${groupList}" var="grouptmp">
  		<input type="checkbox" value="${grouptmp.groupid }|${grouptmp.groupname}" name="ckx_group" id="group_${grouptmp.groupid }"/>
  			<label for="group_${grouptmp.groupid }">${grouptmp.groupname }</label>&nbsp;  		
  	</c:forEach>
  	</p>
  	</td>
      </tr>
      <tr>
        <th><span style="color:red">&nbsp;</span>版&nbsp;&nbsp;&nbsp;&nbsp;主：</th>
        <td><div id="add_banzhu"></div>
          <a  href="javascript:;" onclick="showStuDiv(1)" class="font-lightblue">选择</a>&nbsp;&nbsp;<span class="font-gray1">（空默认为老师）</span></td>
      </tr>
     <!-- <tr>
         <th>浏览方式：</th>
        <td><select name="select">
          <option>默认</option>
        </select>
        <span class="font-gray1">（默认方式为按主题的最后回复时间排序）</span></td>
      </tr>
      <tr>
        <th>&nbsp;相关专题：</th>
        <td><input name="textfield3" type="text" class="public_input w450" /></td>
      </tr>-->
      <tr> 
        <th>&nbsp;</th>
        <td><a onclick="operateColumns()" href="javascript:;" class="an_blue">提&nbsp;交</a>
        <!--  <span id="doadd"><a href="javascript:;" onclick="changeAddOperateType()" class="an_blue">添加论题</a></span> -->
         <a href="../activecolumns/toStuIdx?teachercourseid=${teachercourseid}" class="an_blue">返&nbsp;回</a>
         </td>
      </tr>
    </table> 
    </form>   
    <input type="hidden" value="${teachercourseid }" name="teachercourseid" id="teachercourseid"/>
       <p class="font_strong p_l_10 p_t_20 font-blue font14">论题管理</p>
    <p class="p_l_10 p_b_10">班级选择：
      <select name="sel_search_clsid" id="sel_search_clsid" onchange="classChangeSearchColumns(this,'dv_opts_columns_hd','sel_columns')">
      	<option value="">==全部==</option>
        <c:if test="${!empty columnslist}">     
        	<script type="text/javascript">
        		<c:forEach items="${columnslist}" var="clstmp">
        			if($("#sel_search_clsid option[value='${clstmp.classid}']").length<1)
        				document.write("<option value='${clstmp.classid}'>${clstmp.classgrade}${clstmp.classname}</option>");
        		</c:forEach>
        	</script>
        </c:if>
      </select>
      &nbsp;&nbsp;栏目选择：
  <select name="sel_columns" id="sel_columns">    
  	<option value="">==全部==</option>
  	 <c:if test="${!empty columnslist}">     
        	<script type="text/javascript">
        		<c:forEach items="${columnslist}" var="clstmp1">        			
        				document.write("<option value='${clstmp1.columnsid}'>${clstmp1.classgrade}${clstmp1.classname} ${clstmp1.columnsname}</option>");
        		</c:forEach>
        	</script>
        </c:if>
  </select> 
   <a href="javascript:;" onclick="pageGo('p1')" class="an_blue_small">确定</a></p>
 <div id="dv_opts_columns_hd" style="display:none">
  	<c:if test="${!empty columnslist}">   			
	   				<c:forEach items="${columnslist}" var="coltmp1">
	   					<option value="${coltmp1.columnsid }">
	   					${coltmp1.classInfo.classgrade}${coltmp1.classname} ${coltmp1.columnsname }
	   					<input type="hidden" value="${coltmp1.classid }" id="clsid_hd"/>
	   					</option>
	   				</c:forEach>
   				</c:if>
  </div>
<!-- <input type="text" name="themename" id="sel_val" maxlength="12"/>
   					<select name="sel_type" id="sel_type">
   						<c:if test="${!empty themeseltype}">   						
   							<s:iterator value="#request.themeseltype" var="typetmp">
   								<option value="${typetmp.dictionaryValue }">${typetmp.dictionaryName }</option>
   							</s:iterator>
   						</c:if>
   					</select>
   					 -->   		  	
   		

    <table id="tbl_data" border="0" cellpadding="0" cellspacing="0" class="public_tab2">
      <col class="w290" />
      <col class="w50" />
      <col class="w50" />
      <col class="w90" />
      <col class="w90" />
      <col class="w50" />
      <col class="w310" />
      <tr>
      	<th>论题名称</th>   				
     	<th>贴子数</th>
   		<th>回帖数</th>
   		<th>创建人</th>
   		<th>创建时间</th>
   		<th>状态</th> 	
   		<th>操作</th> 	
      </tr>
      <tbody id="tbl_body_data">
   	 </tbody>
    </table>
    <form name="page1form" id="page1form" action="" method="post">
		  	<div id="page1address" style="display:none"></div>
    </form>
  </div>
</div>
  <%@include file="/util/foot.jsp" %>
</body>
</html>

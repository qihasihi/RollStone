<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-base.jsp" %>
<%
	request.setAttribute("isSelect",true);	 //查询功能权限
    request.setAttribute("isAdd",true);	 //添加功能权限 
    request.setAttribute("isUpdate",true);	 //修改功能权限  
 %>
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/columnpage.js"></script>
  	<script type="text/javascript">
  	var p1;
	$(function(){
	 
		//翻页控件
			p1=new PageControl({
				post_url:'columnright?m=ajaxlist',
				page_id:'page1',
				page_control_name:"p1",		//分页变量空间的对象名称
				post_form:document.page1form,		//form
				http_free_operate_handler:beforajaxList,
				gender_address_id:'page1address',		//显示的区域						
				http_operate_handler:afterajaxList,	//执行成功后返回方法
				return_type:'json',								//放回的值类型
				page_no:1,					//当前的页数
				page_size:20,				//当前页面显示的数量
				rectotal:0,				//一共多少
				pagetotal:1,
				operate_id:"datatbl"
			});
			pageGo('p1');
			
	}); 
	
  	</script>
  </head>
  
   <body> 
  	<%@include file="/util/head.jsp" %>
	<%@include file="/util/nav-base.jsp" %>
  
  <div class="jxpt_layout">
<div class="public_lable_layout">
    <ul class="public_lable_list">
      <li><a href="classyear?m=list">基础设置</a></li>
      <li><a href="dictionary?m=list">字典管理</a></li>
      <li><a href="user?m=list">用户管理</a></li>
      <li><a href="role?m=list">角色管理</a></li>
      <li><a href="job?m=list">职务管理</a></li>
      <li><a href="dept?m=list">部门管理</a></li>
      <li  class="crumb"><a href="columnright?m=list">栏目权限管理</a></li>
      <li><a href="pageright?m=list">页面权限管理</a></li>
    </ul>
  </div>
  
  
<div class="p_20">
    <p>
    栏目：<select name="sel_column_id" id="sel_column_id" >
    	<option value="">=请选择=</option>
    	<c:if test="${!empty columnList}">
    		<c:forEach items="${columnList}" var="coltmp">
    			<option value="${coltmp. columnid}">${coltmp.columnname}<span style="color:gray">[${coltmp.path}]</span></option>
    		</c:forEach>
    	</c:if>
    </select>
      <input placeholder="栏目权限名称 进行查询!" value="" id="sel_columnrightname"
       name="sel_columnrightname"  type="text" class="public_input w200" />
      &nbsp;&nbsp;<a href="javascript:pageGo('p1');" id="sel_btn"  class="an_blue_small">查询</a><a href="javascript:showModel('div_add');" class="an_blue_small">新建</a></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_25">
      <colgroup span="1" class="w140"></colgroup>      
       <colgroup span="1" class="w160"></colgroup>
       <colgroup span="2" class="w250"></colgroup>      
      <tr>
      	<td>栏目权限ID</td>
        <th>栏目权限名称</th>
        <th>所属栏目</th>        
        <th>操作</th>
      </tr>
      <tbody  id="datatbl">     
      </tbody>
    </table> 
    <form action="/dictionary/ajaxlist" id="page1form" name="page1form" method="post">
  			<p align="center" id="page1address"></p>
  	</form>
</div>
</div>


<div class="public_windows_layout w395 h250" id="div_add" style="display:none;">
  <p class="f_right"><a href="javascript:closeModel('div_add')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">新建栏目权限</p>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_10">
    <col class="w80"/>
    <col class="w260"/>
    <tr>
      <td>权限名称：</td>
      <td><input name="add_columnright_name" id="add_columnright_name" type="text" class="public_input w200" /></td>
    </tr> 
    <tr>
      <td>所属栏目： </td>
      <td>
      	<select id="add_columns_id" name="add_columns_id">
      		<c:if test="${!empty columnList}">
    		<c:forEach items="${columnList}" var="coltmp">
    			<option value="${coltmp. columnid}">${coltmp.columnname}<span style="color:gray">[${coltmp.path}]</span></option>
    		</c:forEach>
    	</c:if>
      	</select>
      </td>
    </tr>  
  </table>
  <p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doAdd();">添加</a></p>
</div>
<%@include file="/util/foot.jsp" %>
</body>

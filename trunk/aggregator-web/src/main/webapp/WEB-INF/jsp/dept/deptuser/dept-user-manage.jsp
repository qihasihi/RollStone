<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %>  

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
  	<script type="text/javascript" src="<%=basePath %>js/dept.js"></script>
<script type="text/javascript">  
	var deptid="${deObj.deptid}";
	var ptype="${deObj.typeid}";  
	$(function() { 
		$("a").filter(function(){return this.href.indexOf('ref='+deptid)!=-1}).css("color",'red');
		
		loadAutoComplete();  
	}); 	
	/**
	*进入设置形政干部页面
	*/
	function toSetLeader(){
		if(deptid.Trim().length<1){
			alert('异常错误，系统尚未获取到部门标识，请刷新页面后重试!');
			return;    
		}     
		window.open("dept?m=toSetLeader&ref="+deptid+"&ptype="+ptype);
	}  

</script>
</head> 
  <body onselectstart="return false" ondragstart="return false"> 
  <%@include file="/util/head.jsp" %>
   
  	<%@include file="/util/nav.jsp" %>

<div class="jxpt_layout">
 <div class="public_lable_layout">
    <ul class="public_lable_list">
      <li><a href="classyear?m=list">基础设置</a></li>
      <!-- <li><a href="dictionary?m=list">字典管理</a></li> -->
      <li><a href="user?m=list">用户管理</a></li>
      <li><a href="role?m=list">角色管理</a></li>
      <li><a href="job?m=list">职务管理</a></li>
      <li   class="crumb"><a href="dept?m=list">部门管理</a></li>
    </ul>
  </div>  

  <div class="yhqx_bmgl">
    <div class="left">
      <p class="t_c p_tb_10"><a href="javascript: d.openAll();"><img src="images/an34_130705.png" title="展开树" width="76" height="27" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript: d.closeAll();"><img src="images/an35_130705.png" title="关闭树" width="76" height="27" /></a></p>
      <div class="auto">
      	<!-- dtree -->  
  		<div class="dtree" style="">
  			<input id="ref" type="hidden"/> 
  			<p style="color:red;">鼠标左键点击节点弹出菜单</p>  
  			<div id="_dtree">
  			<script type="text/javascript">
				var d = new dTree('d');			
				//d.add("0","-1","部门信息");  
				<c:if test="${!empty deList}">
					<c:forEach items="${deList}" var="dpt">  
						d.add(${dpt.deptid},  
							  		  ${dpt.parentdeptid},
							  		  "${dpt.deptname}", 
							  		  "dept?m=toassignDeptUser&ref=${dpt.deptid}&ptype=${dpt.typeid}"); 
					</c:forEach>
				</c:if>	 
				document.write(d);	 	
			</script>
  			</div>  
  		</div>
  		<!-- dtree -->
   
    </div>
    </div>
    
     <div class="right">  
    <div class="two">
    <p><strong>备选用户：</strong></p>
    <p>   
        <input onclick="this.value='';this.style.color='black';"  id="txt_search" name="textfield2" type="text" class="public_input w160" />
      <!--<a href="1" target="_blank"><img src="images/an30_130705.png" width="22" height="20" align="middle" /></a> <a href="1" target="_blank">全选</a> --></p>  
      <select id="allow_user_assert" multiple="multiple" style="padding: 3px 5px 0;width:228px;height: 268px;border: 1px solid #7F9DB9;">     
          <c:if test="${!empty notUdList }">      
          	<c:forEach items="${notUdList}" var="ud">        
          		<option value="${ud.userref }|${ud.userinfo.userid}">${ud.userinfo.userid} / ${ud.realname }</option>
          	</c:forEach>            
          </c:if>     
      </select>   
    </div>  
    <div class="two">
    <p><strong>已选用户：</strong></p>  
    <p class="t_r"><!-- <a href="1" target="_blank">情况</a> --></p>  
    <select id="now_user_assert" multiple="multiple" style="padding: 3px 5px 0;width:228px;height: 268px;border: 1px solid #7F9DB9;">
    	 <c:if test="${!empty currentUser }">      
          	<c:forEach items="${currentUser}" var="ud">        
          		<option value="${ud.userref }|${ud.userinfo.userid}">${ud.userinfo.userid} / ${ud.realname }</option>
          	</c:forEach>            
          </c:if>   
    </select>  
    </div>            
    <p class="clearit">选中用户操作：<a class="an_blue_small" href="javascript:void(0);" onclick="toAddInput('allow_user_assert','now_user_assert')">添加至已选</a><a href="javascript:void(0);" onclick="toAddInput('now_user_assert','allow_user_assert')" class="an_blue_small">从列表删除</a><a id="btn_zhiding" name="btn_zhiding" href="javascript:toSetLeader();"  class="an_blue_small">指定干部</a></p>
    <p class="t_c clearit p_t_20"><a class="an_blue" onclick="doSubmitUser('now_user_assert')" href="javascript:void(0);">确定</a><a class="an_blue"  href="javascript:window.reload();">取消</a></p>
    </div>
    
    <div class="clear"></div>
  </div>
  
</div> 
  		
  
  		
	    <div class="zyfx_htWindows1" style="position: absolute;display:none;" id="for_root">
	        <ul class="zyfx_htList"> 
	          <li  onmouseover="mouseoverLi(this);"  onclick="javascript:to_createDeptname('for_root','createnode');">添加</li> 
	        </ul> 
    	</div> 
    	
    	<div class="zyfx_htWindows1"   style="position: absolute;display:none;" id="sub_menu">
        <ul class="zyfx_htList">
          <li onmouseover="mouseoverLi(this);" onclick="javascript: to_editDeptname('sub_menu','editnode');">编辑部门</li>             
          <li onmouseover="mouseoverLi(this);" onclick="javascript: to_createDeptname('sub_menu','createnode');">添加部门</li>
          <li onmouseover="mouseoverLi(this);" onclick="javascript: to_assertPeople('sub_menu');">指定人员</li> 
  		  <li onmouseover="mouseoverLi(this);" onclick="javascript: dodelete();">删除</li>  
        </ul> 
         <input  type="hidden" id="ref" name="ref"/>
         <input type="hidden" id="deptname" name="deptname"/>
         <input type="hidden" id="depttype" name="typeid"/>
         <input type="hidden" id="coursename" id="coursename"/>
          <input type="hidden" id="studyperiods" id="studyperiods"/>
           <input type="hidden" id="grade" id="grade"/> 
    	</div>
    	
    	<c:if test="${isAdd}">
    	<!-- 添加 --> 
		 <div id="createnode" class="IndexWindows2" style="position: absolute;display:none;" id="createnode">
    <div style="float:right"><a href="javascript: close_Div('createnode');"><img src="images/an14.gif" alt="关闭" width="15" height="15" border="0" /></a></div>
         <p align="center">添加部门</p>        
         <p>部门名称：<input id="c_deptname" name="deptname" type="text" class="input-border W140 H20"/></p>
          <p class="Pt2">部门类型：
        	<select id="depttype">
        		<option value="">==请选择==</option>
        		<c:if test="${!empty typeList}">
        			<c:forEach items="${typeList}" var="dtype">
        				<option value="${dtype.dictionaryvalue }">${dtype.dictionaryname }</option>
        			</c:forEach> 
        		</c:if>         		
        	</select></p>  
        		 <p class="Pt2">科 &nbsp; &nbsp;目：
        	<select id="coursename">
        		<option value="">==请选择==</option>  
        		<c:if test="${!empty subjectList}">
        				<c:forEach items="${subjectList}" var="s"> 
        					<option value="${s.subjectid }">${s.subjectname}</option>
        				</c:forEach>   
        		</c:if>        		
        	</select></p>
           <p class="Pt2">学 &nbsp; &nbsp;段：        	
        		<select id="studyperiods">
        	    	<option value="">==请选择==</option>
        			<option value="初中">初中</option>
        			<option value="高中">高中</option>    
        		</select></p>        		
        <p class="Pt2" id="grade">年 &nbsp; &nbsp;级：         
	        <select id="grade">
	         	 <option value="">==请选择==</option> 
	       	 	<c:if test="${!empty gradeList}">
        			<c:forEach items="${gradeList}" var="g">
        				<option value="${g.gradevalue}">${g.gradename }</option>  
        			</c:forEach> 
        		</c:if>  
	        </select>
	     </p>
         <p class="Pt2" align="center"><a href="javascript: createDept('c_deptname','createnode')"><img src="images/an28.gif" alt="确认" title="确认" width="61" height="21" border="0" /></a></p>   
    </div>
		</c:if>
		
		
		<c:if test="${isUpdate}"> 
		  <div id="editnode" class="IndexWindows2" style="position: absolute;display:none;" id="editnode">
    <div style="float:right"><a href="javascript: close_Div('editnode');"><img src="images/an14.gif" alt="关闭" width="15" height="15" border="0" /></a></div> 
         <p align="center">修改部门</p>
         <p>部门名称：<input id="e_deptname" name="deptname" type="text" class="input-border W140 H20"/></p>
         <p class="Pt2">部门类型：
        	<select id="depttype"> 
        		<option value="">==请选择==</option> 
        		<c:if test="${!empty typeList}">  
        			<c:forEach items="${typeList}" var="dtype">
        				<option value="${dtype.dictionaryvalue }">${dtype.dictionaryname }</option>
        			</c:forEach>
        		</c:if>        		  
        	</select></p> 
        	 <p class="Pt2">科 &nbsp; &nbsp;目：
        	<select id="coursename">  
        		<option value="">==请选择==</option> 
        		<c:if test="${!empty subjectList}">
        				<c:forEach items="${subjectList}" var="s"> 
        					<option value="${s.subjectid }">${s.subjectname}</option>
        				</c:forEach>     
        		</c:if>        	  	 	      
        	</select></p>    
        <p class="Pt2">学 &nbsp; &nbsp;段：        	
        		<select id="studyperiods">
        	    	<option value="">==请选择==</option>
        			<option value="初中">初中</option>
        			<option value="高中">高中</option>    
        		</select></p>
        <p class="Pt2" id="grade">年 &nbsp; &nbsp;级：        
	        <select id="grade">
	         	 <option value="">==请选择==</option>
	       	 	<c:if test="${!empty gradeList}">
        			<c:forEach items="${gradeList}" var="g">
        				<option value="${g.gradevalue }">${g.gradename }</option> 
        			</c:forEach>
        		</c:if>   
	        </select>
	     </p>
         <p class="Pt2" align="center"> <a href="javascript: doUpdateDeptname('e_deptname','editnode')"><img src="images/an28.gif" alt="确认" title="确认" width="61" height="21" border="0" /></a></p>   
    </div>
	</c:if> 
    
    
    <!-- ----遮蔽层--- -->
    <%@include file="/util/foot.jsp" %>
  </body>
</html>

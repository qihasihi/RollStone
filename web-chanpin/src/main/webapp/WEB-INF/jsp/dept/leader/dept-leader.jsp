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
	$(function() { 
		$("a").filter(function(){return this.href.indexOf('ref='+deptid)!=-1}).css("color",'red');
	}); 	 
	/**
	*进入设置形政干部页面
	*/
	function toSetLeader(){
		if(deptid.Trim().length<1){
			alert('异常错误，系统尚未获取到部门标识，请刷新页面后重试!');
			return;  
		} 
		window.open("dept!intoSetDeptPost.action?ref="+deptid);
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
							  		  "dept?m=toSetLeader&ref=${dpt.deptid}&ptype=${dpt.typeid}"); 
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
    <div>   
    <p><strong>指定人员职位</strong></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
       <c:if test="${!empty udlist}">
	      <colgroup span="3" class="w300"></colgroup>    
	      <tr>      
	      	<th>姓名</th>   
	      	<th>职位</th>
	      	<th>操作</th> 
	      </tr>
	      <c:forEach items="${udlist}" var="ud">
	      	<tr>
	      		<td>${ud.realname}</td>
	      		<td>
	      			<select id="se_role_${ud.userinfo.ref }">
	      				<option value="">---</option>
	      				<c:if test="${!empty roleList}">
	      					<c:forEach items="${roleList}" var="r">  
		      					<c:if test="${r.roleid eq ud.roleid}">
	  								<option value="${r.roleid }" selected="selected">${r.rolename }</option>
	  							</c:if>  
	  							    
	  							<c:if test="${r.roleid != ud.roleid}">
	  								<option value="${r.roleid }">${r.rolename }</option>
	  							</c:if>
	      					</c:forEach>
	      				</c:if>
	      			</select>  
	      		</td>   
	      		<td>    
	      			<a style="color:blue;" href="javascript:doSetPostName('${ud.ref }','${ud.userinfo.ref}')">确定</a>    
	      		</td> 
	      	</tr>
	      </c:forEach>
	   </c:if> 
        
       <c:if test="${empty udlist}">  
        	<tr><td>暂无人员信息!请先指定部门人员!</td></tr>        
        </c:if>  
     </table> 
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
    	
    
    
    
    <!-- ----遮蔽层--- -->
    <%@include file="/util/foot.jsp" %>
  </body>
</html>

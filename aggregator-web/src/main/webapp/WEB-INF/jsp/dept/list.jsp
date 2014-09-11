<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %>
<%
	request.setAttribute("isSelect",true);	 //查询功能权限
    request.setAttribute("isAdd",true);	 //添加功能权限 
    request.setAttribute("isUpdate",true);	 //修改功能权限  
 %>
  <head>
  	<script type="text/javascript" src="<%=basePath %>js/dept.js"></script>
  	<script type="text/javascript">
  		var isSelect=true;	 //查询功能权限
    	var isAdd=true;	 //添加功能权限
    	var isDelete=true;	 //删除功能权限
    	var isUpdate=true;	 //修改功能权限
  	
 
    	var icon = {
    			root: '<%=basePath%>img/base-node.png',
    			node: '<%=basePath%>img/base-node.png', 
    			folder			: '<%=basePath%>img/base-node.png',  
    			folderOpen	: '<%=basePath%>img/base-node.png',  
    			empty				: '<%=basePath%>images/empty.gif',
    			line				: '<%=basePath%>images/line.gif', 
    			join				: '<%=basePath%>images/join.gif', 
    			joinBottom	: '<%=basePath%>images/joinbottom.gif',
    			plus				: '<%=basePath%>images/plus.gif',
    			plusBottom	: '<%=basePath%>images/plusbottom.gif',
    			minus				: '<%=basePath%>images/minus.gif',
    			minusBottom	: '<%=basePath%>images/minusbottom.gif',
    			nlPlus			: '<%=basePath%>images/nolines_plus.gif',
    			nlMinus			: '<%=basePath%>images/nolines_minus.gif'
    		};

	var p1;
	$(function(){ 

		//翻页控件
		p1=new PageControl({
			post_url:'dept?m=toassignDeptUser',
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
			operate_id:"maintbl"
		});  
		pageGo('p1'); 
		
	
		
		/************功能权限************/
		if(isSelect){ 
			$(".dtree").show();
		}
		$get.InitHerf(document.getElementById('_dtree'),"moveReturnFun"); 
		$(document).bind("click",bodyClick);
	});
	
	function to_loadDeptUser(ref,dtype){
		$("#sub_menu input[id='ref']").val(ref);
		$("#sub_menu input[id='depttype']").val(dtype);
		pageGo('p1');
	}

	
	
  	</script>
  	<style type="text/css">
  		
  	</style>
  </head>  
  
  <body onselectstart="return false" ondragstart="return false"> 
  <%@include file="/util/head.jsp" %>
 <%@include file="/util/nav-base.jsp" %> 
  
 <div id="nav">
    <ul>
      <li ><a href="user?m=list">用户管理</a></li>
      <li class="crumb"><a href="cls?m=list">组织管理</a></li>
        <li ><a href="sysm?m=logoconfig">系统设置</a></li>
    </ul>   
</div>
 
<div class="content">
 <div class="contentT">
    <div class="contentR public_input">
    <div class="jcpt_zzgl_bmR">
    <p><input id="txt_search" name="textfield28" type="text"  class="w200">&nbsp;&nbsp;<a href="javascript:doSubmitUser()" class="an_public3">添加</a></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
      <col class="w120"/>
      <col class="w240"/>
      <col class="w120"/>
      <tbody id="maintbl">
	     
      </tbody> 
    </table>
    <form id="page1form" name="page1form"  method="post">
				<p align="center" id="page1address"></p>   
	</form>  
	
    </div>

    <div class="jcpt_zzgl_bmL">
      <p class="title">部门结构设置</p>
      <div class="layout">
      	<!-- dtree -->
  		<div class="dtree" style="display:none;">
  			<input id="hd_ref" type="hidden"/>  
  			<!-- <p style="color:red;">鼠标左键点击节点弹出菜单</p> -->  
  			<div id="_dtree">  
  				<script type="text/javascript"> 
  					var d = new dTree('d');  
  					d.changeIcon(icon);    
  					/***********权限操作************/
  				/*	d.add("4","-1","树根节点") ;   
  					if(isAdd){  
  	  					d.add("0","-1","行政部门","javascript:dTreeBindClick(0,'for_root',1)");
  	  					d.add("1","-1","教研组","javascript:dTreeBindClick(-1,'for_root',2)");
  	  					d.add("2","-1","年级组","javascript:dTreeBindClick(-2,'for_root',3)");
  	  					d.add("3","-1","自定义部门","javascript:dTreeBindClick(-3,'for_root',4)");  
  					}else{                 
  	  					d.add("0","-1","部门信息");          
  	  				}*/    
  					     
  					<c:if test="${!empty deptList}"> 
  						<c:forEach items="${deptList}" var="dpt">
  							<c:if test="${dpt.parentdeptid=='-1'}">
  							d.add(${dpt.deptid}, 
							  		  ${dpt.parentdeptid}, 
							  		  "${dpt.deptname}", //"javascript:dTreeBindClick(${dpt.deptid},'for_root','${dpt.typeid}')"    
							  		'javascript:void(0);','','','','','',"dTreeBindClick(${dpt.deptid},'for_root','${dpt.typeid}')");	
  							</c:if>     
      
  							<c:if test="${dpt.parentdeptid!='-1'}">
  							d.add(${dpt.deptid}, 
							  		  ${dpt.parentdeptid},    
							  		  "${dpt.deptname}",   
							  		"javascript:to_loadDeptUser(${dpt.deptid},'${dpt.typeid}')",'','','','','',"dTreeBindClick(${dpt.deptid},'sub_menu','${dpt.typeid}','${dpt.grade}','${dpt.subjectid}','${dpt.studyperiods}');");
  							</c:if>				 
  						</c:forEach> 
  					</c:if>
  					document.write(d); 
  				</script>
  			</div>
  		</div>
  		<!-- dtree -->
  		
  		<div class="public_windows font-blue" style="position: absolute;display:none;" id="for_root"> 
          <p><a onclick="javascript:to_createDeptname('for_root','createnode');" href="javascript:void(0);">添加部门</a></p>
          <input type="hidden" id="hd_typeid">
        </div>  
         
         
        <div class="public_windows font-blue" style="position: absolute;display:none;" id="sub_menu"> 
          <p><a href="javascript:void(0);" onclick="javascript: to_createDeptname('sub_menu','createnode');">添加部门</a></p>
          <p><a  onclick="javascript: to_editDeptname('sub_menu','editnode');" href="javascript:void(0);">编辑部门</a></p> 
          <p><a href="javascript:void(0);"  onclick="javascript: dodelete();">删除部门</a></p>
           <input  type="hidden" id="ref" name="ref"/>
         <input type="hidden" id="deptname" name="deptname"/>
         <input type="hidden" id="depttype" name="typeid"/>
         <input type="hidden" id="coursename" id="coursename"/>
          <input type="hidden" id="studyperiods" id="studyperiods"/>
           <input type="hidden" id="grade" id="grade"/> 
        </div> 
       
      </div>
    </div>
    </div>
    
    <div class="contentL">
      <ul>
        <li><a href="cls?m=list">班级管理</a></li> 
        <li class="crumb"><a href="dept?m=list">部门管理</a></li>
      </ul>
    </div>
    <div class="clear"></div>
</div> 
<div class="contentB"></div>
</div>
  		
  		
  		
    	<div>
    		<select id="sel_role"  style="display:none;"> 
             
           </select>   
    	</div>
    	
    	<c:if test="${isAdd}">
    	<!-- 添加 --> 
		 <div id="createnode" style="position: absolute;display:none;" class="public_windows">
<h3><a  href="javascript: close_Div('createnode');" title="关闭"></a>添加部门</h3>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
  <col class="w100"/>
  <col class="w240"/> 
  <tr id="tr_type"> 
    <th><span class="ico06"></span>类&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
    <td><input name="rdo_type" type="radio" id="radio" value="2" checked />
      教研组&nbsp;&nbsp;&nbsp;&nbsp; 
      <input type="radio" name="rdo_type"  value="5" />
      备课组</td>
  </tr>
  <tr>
    <th><span class="ico06"></span> 部门名称：</th>
    <td><input id="c_deptname" name="deptname" type="text" class="w150"></td>
  </tr>
  <tr>
    <th>&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;科：</th>
    <td><select id="coursename">
        		<option value="">==请选择==</option>  
        		<c:if test="${!empty subjectList}">
        				<c:forEach items="${subjectList}" var="s"> 
        					<option value="${s.subjectid }">${s.subjectname}</option>
        				</c:forEach>   
        		</c:if>        		
        	</select></td>
  </tr>
  <tr> 
    <th>&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;段：</th>
    <td><select id="studyperiods">
        	    	<option value="">==请选择==</option>
        		    <c:if test="${!empty periodList}">
                        <c:forEach items="${periodList}" var="s">
                            <option value="${s.dictionaryvalue }">${s.dictionaryname}</option>
                        </c:forEach>
                    </c:if>
        		</select></td>
  </tr>
  <tr>
    <th>&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
    <td><select id="grade">
	         	 <option value="">==请选择==</option> 
	       	 	<c:if test="${!empty gradeList}">
        			<c:forEach items="${gradeList}" var="g">
        				<option value="${g.gradevalue}">${g.gradename }</option>  
        			</c:forEach> 
        		</c:if>  
	        </select></td>
  </tr>
  <tr>
    <th>&nbsp;</th>
    <td><a href="javascript: createDept('c_deptname','createnode')" class="an_public1">确&nbsp;定</a></td>
  </tr>
</table> 
</div>
		</c:if>
		
		
		<c:if test="${isUpdate}"> 
		   <div id="editnode" style="position: absolute;display:none;" class="public_windows">
<h3><a  href="javascript: close_Div('editnode');" title="关闭"></a>编辑部门</h3>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
  <col class="w100"/>
  <col class="w240"/>  
  <!-- <tr>  
    <th>* 类&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
    <td><input name="radio" type="radio" id="radio" value="radio" checked />
      年级组&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="radio" id="radio4" value="radio" />
      备课组</td>
  </tr> -->
  <tr>
    <th><span class="ico06"></span> 部门名称：</th>
    <td><input id="e_deptname" name="deptname" type="text" class="w150"></td>
  </tr> 
  <tr>
    <th>&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;科：</th>
    <td><select id="coursename">
        		<option value="">==请选择==</option>  
        		<c:if test="${!empty subjectList}">
        				<c:forEach items="${subjectList}" var="s"> 
        					<option value="${s.subjectid }">${s.subjectname}</option>
        				</c:forEach>   
        		</c:if>        		
        	</select></td>
  </tr>
  <tr>
    <th>&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;段：</th>
    <td><select id="studyperiods">
        	    	<option value="">==请选择==</option>
                    <c:if test="${!empty periodList}">
                        <c:forEach items="${periodList}" var="s">
                            <option value="${s.dictionaryvalue }">${s.dictionaryname}</option>
                        </c:forEach>
                    </c:if>
        		</select></td>
  </tr>
  <tr>
    <th>&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
    <td><select id="grade">
	         	 <option value="">==请选择==</option> 
	       	 	<c:if test="${!empty gradeList}">
        			<c:forEach items="${gradeList}" var="g">
        				<option value="${g.gradevalue}">${g.gradename }</option>  
        			</c:forEach> 
        		</c:if>  
	        </select></td>
  </tr>
  <tr>
    <th>&nbsp;</th>
    <td><a href="javascript: doUpdateDeptname('e_deptname','editnode')" class="an_public1">确&nbsp;定</a></td>
  </tr>
</table>  
</div>
	</c:if> 
    
    
    <!-- ----遮蔽层--- -->
    <%@include file="/util/foot.jsp" %>
  </body>


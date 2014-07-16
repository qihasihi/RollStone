<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common.jsp"%>

  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'activity_add_selectuser.jsp' starting page</title>
    <script type="text/javascript" src="<%=basePath %>js/activity/selectuser.js"></script>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		var p1;
		$(function(){
			p1 = new PageControl({
				post_url:'activity?m=ajaxuserlist',
				page_id:'page1',
				page_control_name:'p1',
				post_form:document.pagef1,
				gender_address_id:'page1address',
				http_free_operate_handler:freedopagemethod,
				http_operate_handler:findpostReturnMethod,
				return_type:'json',
				page_no:1,
				page_size:20,
				rectotal:0,
				pagetotal:1,
				operate_id:"datatbl"
			});		
			//查询全部学生
	    	 doSel();
	    	//掩藏年级，班级选项
	    		$("tr").filter(function(){
					return this.id.Trim().indexOf('tr_student')!=-1;
				}).hide('fast');
				
				var testTeaLen=$("input[type='checkbox']").filter(function(){return this.value==18&&this.checked==true});
				if(testTeaLen.length>0)
					$("#tr_tea_course").show();
				else
					$("#tr_tea_course").hide();
		});
		/**
    	*执行查询
    	*/
    	function doSel(){
    		p1.setPageNo(1);
    		p1.Refresh();
    		pageGo('p1');
    	}
    	/**
    	*选择完毕
    	*/
    	function selComplate(){
    		var seuserid=$("input[type='hidden']").filter(
									function(){
										return (this.name=='seluserid');
									}
							);
			var returnVal=null;
			$.each(seuserid,function(idx,itm){
				if(returnVal==null)
					returnVal=itm.value.Trim();
				else
					returnVal+=","+itm.value.Trim();
			});			
			if(returnVal==null){
				alert('您尚未选择用户!无法提交!请重新选择!');return;
			}
			window.returnValue=returnVal;
			window.close();
    	}
	</script>
  </head>
  
  <body>
  		<div>
   			<table  style="font-size:12px" class="Tab" cellpadding="0" cellspacing="0">
   				<tr>
   					<th width="90px" align="right">角色：</th>
   					<td id="td_role" valign="top">
   						<c:if test="${empty roleList}">
   							<span>没有发现角色信息!</span>	
   						</c:if>
   						<c:if test="${!empty roleList}">
   							<c:forEach  var="r" items="${roleList }">
   								<b><input type="checkbox" onclick="rolechecked(${r.roleid },'${r.rolename }')" value="${r.roleid }" name="rck"/>${r.rolename }</b>
   							</c:forEach>
   						</c:if>
   					</td>   					
   				</tr>
   				
   			</table>
   			<table id="td_ht" style="font-size:12px;width:100%" class="Tab" cellpadding="0" cellspacing="0">
   				<tr id="tr_student_grade">
   					<th  align="right"  width="90px">年级：</th>
   					<td>
   						<c:if test="${empty gradeList }">
   							没有发现年级信息!
   						</c:if>
   						<c:if test="${!empty gradeList }">
   							<c:forEach var="g" items="${gradeList }" varStatus="status">
   								<b><input type="radio" onclick="gradeChecked(this.value.Trim(),null)" value="${g.gradevalue }" name="ck_grade"/>${g.gradename }</b>
   								<c:if test="${(status.index+1)%3==0}"><br/></c:if>
   							</c:forEach>
   						</c:if>
   					</td>
   				</tr>
   				<tr  id="tr_student_cls">
   					<th  align="right"  width="90px">班级：</th>
   					<td id="td_cls">
   						<b><input type="checkbox" value="11" name="ck_cls"/>1班</b>
   						<b><input type="checkbox" value="11" name="ck_cls"/>2班</b>
   						<b><input type="checkbox" value="11" name="ck_cls"/>3班</b>
   						<b><input type="checkbox" value="11" name="ck_cls"/>4班</b>
   						<b><input type="checkbox" value="11" name="ck_cls"/>5班</b>
   						<b><input type="checkbox" value="11" name="ck_cls"/>6班</b>
   						<b><input type="checkbox" value="11" name="ck_cls"/>7班</b> 
   					</td> 
   				</tr>
   				<tr  id="tr_tea_course">
   					<th  align="right"  width="90px">教授科目：</th>
   					<td id="td_tea_course">
   						 <!--  -->
   						 <c:if test="${!empty subList}">
   						 	<c:forEach var="tmpS" items="${subList }">
   						 		<b><input type="checkbox" name="ck_course" id="ck_course_${tmpS.subjectid }" value="${tmpS.subjectid }"/>
   						 		${tmpS.subjectname }&nbsp;</b>
   						 	</c:forEach>
   						 </c:if>
   					</td> 
   				</tr>
   				<tr id="tr_0">
   					<th  align="right"  width="90px">用户名/姓名：</th>
   					<td><b><input type="text" maxlength="30" name="selInput" id="selInput"/></b></td>
   				</tr>
   				<tr>
   					<td></td>
   					<td><b><input type="button" onclick="doSel()" class="btn" value="查询"/></b>
   						<b><input type="button" onclick="selComplate()" class="btn" value="选择完毕"/></b>
   					</td>  
   				</tr>
   			</table>
   		<table id="datatbl"  style="font-size:12px;width:100%;text-align: center;" class="Tab" cellpadding="0" cellspacing="0">
   		</table>
   		<form action="" name="pagef1" id="pagef1" method="post">
   			<div id="page1address" align="right"></div>
   		</form>
   		</div>
   		<br/>
   		<div style="display: none" id="seldiv">
   			
   		</div>
  </body>

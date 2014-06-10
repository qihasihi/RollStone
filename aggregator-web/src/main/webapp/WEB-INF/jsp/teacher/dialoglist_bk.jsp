<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common.jsp" %> 
  <head>
  	<script type="text/javascript">
  	var pu; 
	$(function(){
	 
		//翻页控件
			pu=new PageControl({
				post_url:'teacher?m=ajaxlist',
				page_id:'page1',
				page_control_name:"pu",		//分页变量空间的对象名称
				post_form:document.page1form,		//form
				http_free_operate_handler:beforajaxListUser,
				gender_address_id:'page1address',		//显示的区域						
				http_operate_handler:afterajaxListUser,	//执行成功后返回方法
				return_type:'json',								//放回的值类型
				page_no:1,					//当前的页数
				page_size:20,				//当前页面显示的数量
				rectotal:0,				//一共多少
				pagetotal:1,
				operate_id:"datatbl"
			});
			pageGo('pu');
	});    
	var gradeid="${gradeid}";

	function beforajaxListUser(p){ 
		var param = {};
		  
		//教师姓名/用户名
		var realname = $("#selInput").val();
		if(realname.Trim().length>0) 
			param.realname = realname;
		param.gradeid=gradeid;
		p.setPostParams(param);
	}
  
	//执行成功后返回方法 
	function afterajaxListUser(rps){
		var html = '';
		if(rps.type=='error'){
			alert(rsp.msg);
			return;
		}else{
			if(rps.objList.length>0){
				html+='<tr><th><input type="checkbox" id="ck_all" onclick="selAll(this)"/>全选</th><th>教师姓名</th><th>登录帐号</th><th>主授学科</th>';
				html+='<th>教师性别</th><th>教师电话</th><th>邮箱</th>';
				html+='<th>身份证号</th></tr>';
				$.each(rps.objList,function(idx,itm){
					html+='<tr>';
					html+='<td><input type="checkbox" name="ck_userid" value="'+itm.userid+'"/></td>';
					if(!(typeof(itm.teachername)=="undefined")){ 
						html+='<td>'+ itm.teachername +'</td>'; 
					}else 
						html+='<td></td>'; 
					if(!(typeof(itm.username)=="undefined")){  
						html+='<td>'+ itm.username +'</td>';
					}else 
						html+='<td></td>';
					if(!(typeof(itm.coursename)=="undefined")){
						html+='<td>'+ itm.coursename +'</td>';
					}else
						html+='<td></td>';
					if(!!(itm.teachersex)){
						html+='<td>'+ itm.teachersex +'</td>';
					}else
						html+='<td></td>';
					if(!(typeof(itm.teacherphone)=="undefined")){
						html+='<td>'+ itm.teacherphone +'</td>';
					}else
						html+='<td></td>';
					if(!(typeof(itm.teacherpost)=="undefined")){
						html+='<td>'+ itm.teacherpost +'</td>';
					}else
						html+='<td></td>';
					if(!!(itm.teachercardid)){
						html+='<td>'+ itm.teachercardid +'</td>';
					}else
						html+='<td></td>';
					html+='<tr>'
				});
			}else{
				html+="<tr><th>暂无数据!</th></tr>";
			}
			pu.setPageSize(rps.presult.pageSize);
			pu.setPageNo(rps.presult.pageNo);
			pu.setRectotal(rps.presult.recTotal);
			pu.setPagetotal(rps.presult.pageTotal);
			pu.Refresh();
		} 
		$("#datatbl").html(html); 
	} 
	 
	function selComplete(){
		var uidarr=$("input[name='ck_userid']").filter(function(){
			return this.value.length>0&&this.checked==true;
		});
		
		var returnVal ="";
		$.each(uidarr,function(idx,itm){
			if(returnVal.length>0)
				returnVal+=",";
			returnVal += itm.value.Trim();
		});
		if(returnVal.length<1){
			alert("您尚未选择用户无法提交，请先选择用户!");
			return;
		}
		window.returnValue=returnVal;
		window.close(); 
	}
	function selAll(ckobj){ 
		$("input[name='ck_userid']").attr("checked",$(ckobj).is(":checked"));  
	} 

	
	
  	</script>
  </head>
  
  <body>
  	<div >
   		<table id="td_ht" style="font-size:12px;width:100%" class="Tab" cellpadding="0" cellspacing="0">
   				<!-- <tr id="tr_student_grade">
   					<th  align="right"  width="90px">年级：</th>
   					<td>
   						<c:if test="${empty gradeList }">
   							没有发现年级信息!
   						</c:if>
   						<c:if test="${!empty gradeList }">
   							<c:forEach items="${gradeList}" var="g" varStatus="gindex" >
   								<b><input type="radio" onclick="getClassByGrade(this.value.Trim(),year)" value="${g.gradevalue }" name="ck_grade"/>${g.gradename }</b>  
   								<c:if test="${(gindex.index+1)%3==0}"><br/></c:if>
   							</c:forEach>
   						</c:if>
   					</td> 
   				</tr> -->
   				
   				<tr id="tr_0">
   					<th  align="right"  width="90px">用户名/姓名：</th>
   					<td><b><input type="text" maxlength="30" name="selInput" id="selInput"/></b></td>
   				</tr>
   				<tr>
   					<td></td>
   					<td><b><input type="button" onclick="pageGo('pu')" class="btn" value="查询"/></b>
   						<b><input type="button" onclick="selComplete()" class="btn" value="选择完毕"/></b>
   					</td>  
   				</tr>
   			</table>
  		<table id="datatbl" align="center" style="text-align:center;font-size:13;width:100%">
  			
  		</table>
  		<form action="/role/ajaxlistuser" id="page1form" name="page1form" method="post">
  			<p align="center" id="page1address"></p>
  		</form>
  		<div id="seldiv">
  		</div>
  		
  	</div>
  </body>


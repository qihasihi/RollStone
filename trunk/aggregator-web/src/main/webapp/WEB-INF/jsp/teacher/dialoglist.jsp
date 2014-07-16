<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %> 
  <head>
  	<script type="text/javascript">
  	var pu; 
	$(function(){
		$("#sel_grade").val("${gradeid}");
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
	      
	var max_leader_num="${max_leader_num}";
   
	function beforajaxListUser(p){  
		var param = {}; 
		  
		//教师姓名/用户名
		var realname = $("#selInput").val();
		if(realname.Trim().length>0) 
			param.realname = realname;
		var gradesel = $("#sel_grade").val();
		if(gradesel.Trim().length>0) 
			param.gradeid = gradesel;
		p.setPostParams(param);
	}
    
	//执行成功后返回方法 
	function afterajaxListUser(rps){
		var html = '';
		if(rps.type=='error'){
			alert(rps.msg);
			return;   
		}else{  
			if(rps.objList.length>0){
				html+='<tr><th><th>教师姓名</th><th>班级</th><th>科目</th>';//<input type="checkbox" id="ck_all" onclick="selAll(this)"/>全选</th>
				html+='<th>创建时间</th></tr>';    
				$.each(rps.objList,function(idx,itm){    
					html+='<tr>';         
					html+='<td><input type="checkbox" onclick="selOne(this)" name="ck_userid" value="'+itm.userid+'"/></td>';
					if(!(typeof(itm.userinfo.realname)=="undefined")){  
						html+='<td>'+ itm.userinfo.realname +'</td>';  
					}else    
						html+='<td></td>';   
					if(!(typeof(itm.classgrade)=="undefined"&&typeof(itm.classname)=="undefined")){  
						html+='<td>'+ itm.classgrade+itm.classname  +'</td>';
					}else  
						html+='<td></td>';
				//	if(!(typeof(itm.coursename)=="undefined")){
				//		html+='<td>'+ itm.coursename +'</td>';
				//	}else
				//		html+='<td></td>';
					if(!!(itm.subjectname)){
						html+='<td>'+ itm.subjectname +'</td>';
					}else
						html+='<td></td>';
				/*	if(!(typeof(itm.teacherphone)=="undefined")){
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
						html+='<td></td>';*/
					html+='<td>'+itm.ctimeString+'</td>';
					html+='</tr>'; 
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
		if(!confirm("确认操作?")){return;}
		window.returnValue=returnVal; 
		window.close();  
	}
	function selAll(ckobj){ 
		$("input[name='ck_userid']").attr("checked",$(ckobj).is(":checked"));  
	}

	function selOne(ckobj){
		//$("input[name='ck_userid']").attr("checked",$(ckobj).is(":checked"));
		var len=$("input[name='ck_userid']").filter(function(){return this.checked==true}).length;
		var flag=len<=parseFloat(max_leader_num)?true:false;
		if(len>max_leader_num){
			alert('每个年级最多设置'+max_leader_num+'个年级组长!');
			$(ckobj).attr("checked",flag);
			return;
		}   
	}     

	 
	
  	</script>  
  </head>
  
  <body>
	<div class="jxpt_nav">
		<p class="f_left">
			当前位置：首页 &gt; 基础平台
		</p>  
		<p>
			<strong><%=u.getRealname()%></strong>您好!
		</p>
	</div>  



	<div class="jxpt_layout">
		<p class="jxpt_icon01">
			<!-- <span><a href="1">返回</a></span> -->
			基础设置>年级管理>指定组长
		</p>    
		<div class="p_20">  
			<table border="0" cellpadding="0" cellspacing="0"
				class="public_tab1 zt14">
				<col class="w120" />
				<col class="w780" />
				
				<tr>
   					<th>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
   					<td>       
   						<select id="sel_grade">
	   						<option value="">==请选择==</option>
	   						<c:if test="${!empty gradeList }">
	   							<c:forEach items="${gradeList}" var="g" varStatus="gindex" >
	   								<option value="${g.gradeid }">${g.gradename }</option>  
	   							</c:forEach>  
	   						</c:if>     
   						</select>
   					</td>  
   				</tr>  
				 
				<tr>  
					<th>
						用户名/姓名：
					</th>
					<td>
						<input name="selInput" id="selInput" type="text" class="public_input w200" />
					</td>
				</tr>
				<tr>
					<th> 
						&nbsp;
					</th>
					<td>
						<a href="javascript:pageGo('pu')"  class="an_blue">查&nbsp;询</a>
					</td>
				</tr>
			</table>
			<table border="0" cellpadding="0" cellspacing="0"
				class="public_tab2 m_t_25">
				<colgroup class="w110"></colgroup>
				<colgroup span="4" class="w150"></colgroup>
				<tbody id="datatbl" >
				
				
				<tr>
					<th>
						<input type="checkbox" name="checkbox13" id="checkbox31" />
						全选/不选
					</th>
					<th>
						编号（学号）
					</th>
					<th>
						姓名
					</th>
					<th>
						性别
					</th>
					<th>
						用户名
					</th>
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="checkbox14" id="checkbox32" />
					</td>
					<td>
						123456
					</td>
					<td>
						好孩子
					</td>
					<td>
						女
					</td>
					<td>
						美丽时间
					</td>
				</tr>
				<tr class="trbg2">
					<td>
						<input type="checkbox" name="checkbox15" id="checkbox33" />
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<input type="checkbox" name="checkbox16" id="checkbox34" />
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr class="trbg2">
					<td>
						<input type="checkbox" name="checkbox17" id="checkbox35" />
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				</tbody>
			</table>
		
			<form action="/role/ajaxlistuser" id="page1form" name="page1form" method="post">
  				<p align="center" id="page1address"></p>
  			</form>
  			  
  			<div>
  				<ul class="public_list2" id="seldiv">
  				
  				</ul>
  			</div> 
  		  
			<p class="w370 t_c">
				<a href="javascript:selComplete()"  class="an_blue">保存选择</a>
			</p>
		</div>
	</div> 


	<%@include file="/util/foot.jsp"%>
</body>


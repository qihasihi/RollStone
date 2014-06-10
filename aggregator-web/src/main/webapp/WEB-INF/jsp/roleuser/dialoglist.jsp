<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<head>
	<script type="text/javascript" src="<%=basePath%>js/roleuser.js"></script>
	<script type="text/javascript">
  	var ptype="${param.type}";
  	var psign = "${param.sign}"
  	var pu;   
	$(function(){ 
	 
		//翻页控件
			pu=new PageControl({
				post_url:'roleuser?m=getUserByCondition',
				page_id:'page1',
				page_control_name:"pu",		//分页变量空间的对象名称
				post_form:document.page1form,		//form
				http_free_operate_handler:beforajaxListUser,
				gender_address_id:'page1address',		//显示的区域						
				http_operate_handler:afterajaxListUser,	//执行成功后返回方法
				new_page_html_mode:true,
				return_type:'json',								//放回的值类型
				page_no:1,					//当前的页数
				page_size:20,				//当前页面显示的数量
				rectotal:0,				//一共多少
				pagetotal:1,
				operate_id:"datatbl"
			});
			
			pageGo('pu');
			
			//隐藏年级、班级
			$("tr").filter(function(){
				return this.id.indexOf("tr_student") != -1;
			}).hide();
			//科目
			$("input").attr("checked",false);
			var trcourseLen=$("input[type='checkbox']").filter(function(){
				return this.value==2 && this.checked;
			});
			if(trcourseLen.length>0)
				$("#tr_tea_course").show();
			else
				$("#tr_tea_course").hide();
	});
	
	function selComplete(){
		var uidarr=$("input[name='hd_userid']").filter(function(){
			return this.value.length>0;
		});
		
		var returnVal ="";
		$.each(uidarr,function(idx,itm){
			if(returnVal.length>0)
				returnVal+=",";
			returnVal += itm.value.Trim();
		});
		if(returnVal.length<1){
			alert("您尚未选择用户无法提交，请先选择用户！");
			return;
		}  
		if(!confirm('确认操作?')){return;}
		window.returnValue=returnVal;
		window.close(); 
	}
	
  	</script>
</head>

<body>



	<div class="jxpt_layout">
		<p class="jxpt_icon01">
			<!-- <span><a href="1">返回</a></span> -->
			指定用户
		</p>
		<div class="p_20">
			<table border="0" cellpadding="0" cellspacing="0"
				class="public_tab1 zt14">
				<col class="w120" />
				<col class="w780" />
				<tr>
					<th>
						年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：
					</th>
					<td>
						<select id="year" onchange="onYearChange()">  
							<c:if test="${!empty yearList}">
								<c:forEach var="j" items="${yearList}">
									<option value="${j.classyearvalue }">${j.classyearname }</option>
								</c:forEach>
							</c:if>  
						</select>
					</td>
				</tr>
				<tr>
					<th>
						角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：
					</th>
					<td>
						<ul class="public_list2">
							<c:if test="${empty roleList}">
								<li>
									没有发现角色信息!
								</li>
							</c:if>
							<c:if test="${!empty roleList}">
								<c:forEach var="r" items="${roleList}">
									<li>
										<input type="checkbox"
											onclick="rolechecked(${r.roleid },'${r.rolename }')"
											value="${r.roleid }" name="rck" />
										${r.rolename }
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</td>
				</tr>
				<!-- <tr>
      <th>部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：</th>
      <td><ul class="public_list2">
        <li>
          <input type="checkbox" name="checkbox11" id="checkbox11" />
          校内信息发布者</li>
      
      </ul></td>
    </tr>-->
				<tr>
					<th>
						职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：
					</th>
					<td>
						<ul class="public_list2">
							<c:if test="${empty jobList}">
								<li>没有发现职务信息!</li>  
							</c:if>
							<c:if test="${!empty jobList}">
								<c:forEach var="j" items="${jobList}">
									<li><input type="checkbox" value="${j.roleid }" name="rck" />${j.rolename}</li>
								</c:forEach>
							</c:if> 
						</ul>
					</td>
				</tr>
				
				<tr id="tr_student_grade">
   					<th  align="right"  width="90px">年级：</th>
   					<td>
   						<ul class="public_list2">
	   						<c:if test="${empty gradeList }">
	   							<li>没有发现年级信息!</li>
	   						</c:if>
	   						<c:if test="${!empty gradeList }">
	   							<c:forEach items="${gradeList}" var="g" varStatus="gindex" >
	   								<li><input type="radio" onclick="getClassByGrade(this.value.Trim(),year)" value="${g.gradevalue }" name="ck_grade"/>${g.gradename }</li>  
	   							</c:forEach>
	   						</c:if>
   						</ul>
   					</td>   
   				</tr>    
				
				<tr  id="tr_student_cls">
   					<th  align="right"  width="90px">班级：</th>
   					<td >
   						<ul id="td_cls" class="public_list2">
	   						<!-- <input type="checkbox" value="11" name="ck_cls"/>1班</li>
	   						<b><input type="checkbox" value="11" name="ck_cls"/>2班</b>
	   						<b><input type="checkbox" value="11" name="ck_cls"/>3班</b>
	   						<b><input type="checkbox" value="11" name="ck_cls"/>4班</b>
	   						<b><input type="checkbox" value="11" name="ck_cls"/>5班</b>
	   						<b><input type="checkbox" value="11" name="ck_cls"/>6班</b>
	   						<b><input type="checkbox" value="11" name="ck_cls"/>7班</b> -->
   						</ul> 
   					</td>   
   				</tr>
   				<tr  id="tr_tea_course">
   					<th  align="right"  width="90px">教授科目：</th>
   					<td id="td_tea_course">
   						<ul class="public_list2">
	   						 <c:if test="${!empty subList}">
	   						 	<c:forEach items="${subList}" var="s">
	   						 		<li><input type="checkbox" name="ck_course" id="ck_course_${s.subjectid }" value="${s.subjectid }"/>
	   						 		${s.subjectname }&nbsp;</li>
	   						 	</c:forEach>
	   						 </c:if>
   						 </ul>
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
			</table><%--
		
			<form action="/role/ajaxlistuser" id="page1form" name="page1form" method="post">
  				<p align="center" id="page1address"></p>
  			</form>
  			  --%><p >
		    <form id="page1form" name="page1form" method="post">
		    	<div align="right" id="page1address"></div>  
		    </form>  
		</p>  
  			<div>
  				<ul class="public_list2" id="seldiv">
  				
  				</ul>
  			</div>
  		
			<p class="w370 t_c">
				<a href="javascript:selComplete()"  class="an_blue">保存选择</a>
			</p>
		</div>
	</div> 

</body>


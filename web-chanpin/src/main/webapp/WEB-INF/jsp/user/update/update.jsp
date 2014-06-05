<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@include file="/util/common-jsp/common-yhqx.jsp" %> 
<%
	
%>
  <head>
  	<style type="text/css">span{color:red;font-size:12px}</style>
  	<script type="text/javascript" src="<%=basePath %>js/user.js"></script>
  	<script type="text/javascript">  		
  		$(function(){
  			//$("input[name='user_state']:first").attr("checked",true);
  			//
  			//$("#userinfo").html($("#addteacher").html());   			
  			$("input[name='role']").filter(function(){return this.value=="3"}).attr("disabled",true);
  			$("input[name='role']").filter(function(){return this.value=="<%=grade_role_id%>"}).css("display","none");
  			$("input[name='role']").filter(function(){return this.value=="<%=grade_fu_role_id%>"}).css("display","none");
  			$("input[name='role']").filter(function(){return this.value=="<%=dept_role_id%>"}).css("display","none");
  			$("input[name='role']").filter(function(){return this.value=="<%=teach_role_id%>"}).css("display","none");
  			$("input[name='role']").filter(function(){return this.value=="<%=dept_manage_role%>"}).css("display","none");

			  
  			$("label[for='role_<%=grade_role_id%>']").hide();
  			$("label[for='role_<%=grade_fu_role_id%>']").hide();
  			$("label[for='role_<%=dept_role_id%>']").hide();
  			$("label[for='role_<%=teach_role_id%>']").hide();
  			$("label[for='role_<%=dept_manage_role%>']").hide();
  			
  			  
  			//处理角色 显示不同的层  
  			<c:if test="${!empty roleUserList}">
  				$("input[name='role']").each(function(idx,itm){
	  				<c:forEach items="${roleUserList}" var="ru">
	  					if(itm.value.Trim()==${ru.roleinfo.roleid}){
	  						this.checked=true;
	  						roleChangeUpd(this,'add');	  						
	  					}
	  				</c:forEach> 
	  			});  	 			
  			</c:if> 
 
			//职务
  			<c:if test="${!empty jobUserList}">
				$("input[name='job']").each(function(idx,itm){
  				<c:forEach items="${jobUserList}" var="ju">
  					if(itm.value.Trim()==${ju.jobinfo.jobid}){
  						this.checked=true;
  					}  
  				</c:forEach>  
  				});  	 			
			</c:if> 

			
			
  			<c:if test="${!empty userinfo}">
	  			$("#user_state_${userinfo.stateid}").attr("checked",true);	
  			</c:if> 
  			 
  			  
  			
  			//判读是否是学生
  			<c:if test="${!empty studentinfo}">
  					//stuname
  					$("#userinfo input[id='stuname']").val("${studentinfo.stuname}");
  					//stuno
  					$("#userinfo input[id='stuno']").val("${studentinfo.stuno}");
  					//stusex
  					$("#addstudent input[name='stusex']").each(function(idx,itm){
  						if(this.value.Trim()=="${studentinfo.stusex}")
  							this.checked=true;
  						else
  							this.checked=false;
  					});  					
  					//stuclass
  					var opshtml='';
  					<c:if test="${!empty stucuList}">
  						<c:forEach items="${stucuList}" var="clsuser">
  							opshtml+='<option value="${clsuser.classinfo.classid}">${clsuser.classinfo.year} ';
  							opshtml+='| ${clsuser.classinfo.classgrade} | ${clsuser.classinfo.classname}</option>';
  						</c:forEach> 
  					</c:if>  
  					$("#addstudent select[id='stucls_result']").html(opshtml);
  					//linkman
  					$("#userinfo input[id='linkman']").val("${studentinfo.linkman}");
  					//linkmanPhone
  					$("#userinfo input[id='linkmanPhone']").val("${studentinfo.linkmanphone}");
  					//stuAddress
  					$("#userinfo input[id='stuAddress']").val("${studentinfo.stuaddress}");
  			</c:if>
  			//判断是否是教师 
  			<c:if test="${!empty teacherinfo}">  
  					//teachername  
  					$("#addteacher input[id='realname']").val('${teacherinfo.teachername}');
  					//sex
  					var sex=${teacherinfo.teachersex=="男"?1:2};
  					$("#addteacher input[id='sex"+sex+"']").attr("checked",true);
  					//subjectteacherinfos
  					<c:forEach items="${subjectUserList}" var="sub">
  					    $("#addteacher input[id='subject${sub.subjectinfo.subjectid}']").attr("checked",true);
  					    <c:if test="${sub.ismajor=='1'}">  
  					  		$("#addteacher input[id='subject_major_${sub.subjectinfo.subjectid}']").attr("checked",true);
  					    </c:if>       
  					</c:forEach>       
  					    
  					//cls
  					var opshtml='';
  					<c:if test="${!empty teacuList}">
  						<c:forEach items="${teacuList}" var="clsu">
  							opshtml+='<option value="${clsu.classinfo.classid}|${clsu.subjectid}">${clsu.classinfo.year} ';
  							opshtml+='| ${clsu.classinfo.classgrade} | ${clsu.classinfo.classname} | ${clsu.subjectname}</option>';
  						</c:forEach>   	 					 
  					</c:if>  
   
  					var bzropshtml='';  
  					//班主任cls  
  					<c:if test="${!empty bzrcuList}">
  						<c:forEach items="${bzrcuList}" var="clsu">
  							bzropshtml+='<option value="${clsu.classinfo.classid}">${clsu.classinfo.year} ';
  							bzropshtml+='| ${clsu.classinfo.classgrade} | ${clsu.classinfo.classname}</option>';
  						</c:forEach>    						
  					</c:if>   
  
  					
  					$("#addteacher select[id='cls_result']").html(opshtml);

  					$("#addclassmaster select[id='cls_bzr_result']").html(bzropshtml);
  					
  					$("#addteacher input[id='teachercardid']").val('${teacherinfo.teachercardid}');
  					
  					$("#addteacher input[id='teacherphone']").val('${teacherinfo.teacherphone}');
  					
  					$("#addteacher input[id='teacherpost']").val('${teacherinfo.teacherpost}');
  					
  					$("#addteacher input[id='teacheraddress']").val('${teacherinfo.teacheraddress}');
  					<c:if test="${!empty teacherinfo.teacherbirth}">
  						$("input[id='birth']").val('${teacherinfo.teacherbirth}'.split(" ")[0]);  
  					</c:if>  
  			</c:if>

  			
  			 
  			 
  		
  		});
  	</script> 
  </head> 
  <body>
  	<%@include file="/util/head.jsp" %>

<%@include file="/util/nav-base.jsp" %>

<div class="jxpt_layout">
<p class="jxpt_icon01">用户信息</p>
<div class="yhqx_grxx_content">
    <p class="public_title">基础信息</p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1  m_a_20 zt14">
      <col class="w100"/>
      <col class="w290"/>
      <col class="w100"/> 
      <col class="w370"/>
      <tr>
        <th>用&nbsp;户&nbsp;名：</th>
        <td><span class="m_lr_5 font-red">*</span><input name="username" id="username" value="${userinfo.username }" disabled="disabled" maxlength="24" type="text" class="public_input w200" />
        	<span id="username_err"></span> 
        	<input type="hidden" id="username_flag" value="1"/>
        </td>  
        <th>用户状态：</th>  
        <td>   
        	<input type="radio" name="user_state" checked id="user_state_0" value="0"/>
	  		<label for="user_state_0">启用</label> 
	  		<input type="radio" name="user_state" id="user_state_1" value="1"/>
	  		<label for="user_state_1">禁用</label>  
        </td>
      </tr> 
      <tr>
        <th>密&nbsp;&nbsp;&nbsp;&nbsp;码：</th>
        <td><span class="m_lr_5 font-red">*</span><input name="pass" id="pass" value="${userinfo.password }" maxlength="16" type="password" class="public_input w200" /></td>
        <th>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</th>
        <td><input id="mail_address" name="mail_address" type="text" value="${userinfo.mailaddress }" class="public_input w300" /></td>
      </tr>  
      <tr>  
        <th>确认密码：</th>
        <td colspan="3"><span class="m_lr_5 font-red">*</span><input  name="repass" id="repass" value="${userinfo.password }" maxlength="16" type="password" class="public_input w200" /></td>
      </tr> 
      <tr>
        <th>用户角色：  
</th>  
        <td colspan="3"><ul class="public_list1">
         <c:if test="${!empty rolelist}">
  				<c:forEach items="${rolelist}" var="r">
  					<li><input type="checkbox" onclick="roleChangeUpd(this,'add')" name="role" id="role_${r.roleid }" value="${r.roleid }"/>
  						<label for="role_${r.roleid }">${r.rolename }</label>
  					</li>
  				</c:forEach>
  			</c:if>
        </ul></td>
      </tr>
      <tr>  
        <th>用户职务：</th>  
        <td colspan="3"><ul class="public_list1">
          <c:if test="${!empty jobList}">  
  				<c:forEach items="${jobList}" var="r">
  					<li><input type="checkbox" onclick="roleChangeUpd(this,'add')" name="role" id="role_${r.roleid }" value="${r.roleid }"/>
  						<label for="role_${r.roleid }">${r.rolename }</label>
  					</li>
  				</c:forEach>
  			</c:if>   
        </ul></td>  
      </tr>  
    </table>
  		  
<div id="addstudent"  style="display:none" >
<p class="public_title">学生信息</p>  
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
  <col class="w100"/>
  <col class="w760"/>
  <tr>
    <th>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</th>
    <td><span class="m_lr_5 font-red">*</span><input name="stuname" id="stuname" value="${studentinfo.stuname}" maxlength="30" type="text" class="public_input w200" /></td>
  </tr>
  <tr>
    <th>学&nbsp;&nbsp;&nbsp;&nbsp;号：</th>
    <td><span class="m_lr_5 font-red">*</span><input name="stuno" id="stuno" value="${studentinfo.stuno }" maxlength="20" type="text" readonly="readonly" class="public_input w200" /><!-- onblur="validateStuno('stuno')" -->
    	<span id="stuno_err"></span>        
        <input type="hidden" id="stuno_flag" value="1"/>  
   		<!--  <a href="javascript:validateStuno('stuno')" class="an_blue_small">检测学号</a>-->
   </td>  
  </tr> 
  <tr>  
    <th>性&nbsp;&nbsp;&nbsp;&nbsp;别： </th>
    <td><input type="radio" name="stusex" id="stusex1" checked="checked" value="男"/><label for="stusex1">男</label>
  			<input type="radio" name="stusex" id="stusex2" value="女"/><label for="stusex2">女</label>
  	</td>
  </tr>
  <tr>
    <th>班&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
    <td>
    	<select id="stucls" name="stucls">
  			<option value="">==请选择==</option>
  				<c:if test="${!empty clslist}">
  					<c:forEach items="${clslist}" var="c">
  						<option value="${c.classid}">${c.year} | ${c.classgrade} | ${c.classname }</option>
  					</c:forEach>
  				</c:if>  
  			</select>  	
    <a href="javascript:;" onclick="clsChange('stucls','','stucls_result');" class="an_blue_small">确定</a><a href="javascript:void(0);" onclick="removeCls('stucls','stucls_result');" class="an_blue_small">删除</a><br />
         
       <select class="public_input h90 w300 m_t_5" id="stucls_result" name="stucls_result" multiple="multiple">
		</select>
    </td>  
       
  </tr>
  <tr>
    <th>监&nbsp;护&nbsp;人：</th>
    <td><input name="linkman" id="linkman" value="${studentinfo.linkman}" maxlength="30" type="text" class="public_input w200" /></td>
  </tr>
  <tr>
    <th>监护电话：</th>
    <td><input  name="linkmanPhone" id="linkmanPhone" value="${studentinfo.linkmanphone }" maxlength="11" type="text" class="public_input w200" /></td>
  </tr>
  <tr>
    <th>家庭住址：</th>
    <td><input name="stuAddress" id="stuAddress" value="${studentinfo.stuaddress}" maxlength="50" type="text" class="public_input w450" /></td>
  </tr>  
</table>   
</div>

<div id="addteacher"  style="display:none"   >
<p class="public_title">教师信息</p>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
  <col class="w100"/>
  <col class="w760"/>
  <tr>
    <th>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</th>
    <td><span class="m_lr_5 font-red">*</span><input name="realname" id="realname" maxlength="30" type="text" class="public_input w200" /></td>
  </tr>
  <tr>
    <th>性&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
    <td><input type="radio" name="sex" id="sex1" checked="checked" value="男"/><label for="sex1">男</label>
  			<input type="radio" name="sex" id="sex2" value="女"/><label for="sex2">女</label></td>
  </tr>
  <tr>
    <th>主授科目：</th>
    <td><ul class="public_list1">
        <c:if test="${!empty subjectlist}">
  			<c:forEach items="${subjectlist}" var="sj">
  				<li>
  					<input type="radio"  value="${sj.subjectid}" name="subject_major" id="subject_major_${sj.subjectid }"/>
  					<label for="subject_major_${sj.subjectid }">${sj.subjectname }</label>  					 
  				</li>   
  			</c:forEach>  
  		</c:if>
      </ul></td>
  </tr>
  
  <tr>
    <th>辅授科目：</th>
    <td><ul class="public_list1">
        <c:if test="${!empty subjectlist}"> 
  			<c:forEach items="${subjectlist}" var="sj">
  				<li>  
  					<input type="checkbox"  value="${sj.subjectid}" name="subject" id="subject${sj.subjectid }"/>
  					<label for="subject${sj.subjectid }">${sj.subjectname }</label>
  				</li>     
  			</c:forEach>  
  		</c:if>
      </ul></td>
  </tr> 
  
  <tr>
    <th>班&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
    <td>
    	<select id="cls" name="cls">  
  				<option value="">==请选择==</option>
  				<c:if test="${!empty clslist}">
  					<c:forEach items="${clslist}" var="c">
  						<option value="${c.classid}">${c.year} | ${c.classgrade} | ${c.classname }</option>
  					</c:forEach>
  				</c:if>     
  		</select> 
  		<select id="cls_subject">
  			 <c:if test="${!empty subjectlist}">
  				<c:forEach items="${subjectlist}" var="sj">
  					<option value="${sj.subjectid}">
  						${sj.subjectname}
  					</option>  
  				</c:forEach>    
  			</c:if>
  		</select>
  			
      <a href="javascript:;" onclick="clsChange('cls','cls_sex','cls_result');" class="an_blue_small">确定</a><a href="javascript:;" onclick="removeCls('cls','cls_result')" class="an_blue_small">删除</a><br />
       <select class="public_input h90 w300 m_t_5" id="cls_result" name="cls_result" multiple="multiple">
	   </select>   
      </td>
  </tr>
  <tr>
    <th>身份证号：</th>
    <td><input name="teachercardid" id="teachercardid" maxlength="19" type="text" class="public_input w200" /></td>
  </tr>
  <tr>
    <th>电&nbsp;&nbsp;&nbsp;&nbsp;话：</th>
    <td><input name="teacherphone" id="teacherphone" maxlength="11" type="text" class="public_input w200" /></td>
  </tr>
  <tr>
    <th>家庭住址：</th>
    <td><input name="teacheraddress" id="teacheraddress" maxlength="50" type="text" class="public_input w450" /></td>
  </tr> 
</table>
</div>
  
 <!-- 班主任 -->
 <div id="addclassmaster" style="display:none;">
<p class="public_title">班主任信息</p>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
  <col class="w100"/>
  <col class="w760"/>
  <tr>
    <th>管理班级：</th>
    <td><select id="cls_bzr" name="cls_bzr"> 
  				<option value="">==请选择==</option>
  				<c:if test="${!empty clslist}">
  					<c:forEach items="${clslist}" var="c">
  						<option value="${c.classid}">${c.year} | ${c.classgrade} | ${c.classname }</option>
  					</c:forEach>
  				</c:if>  
  		</select>  
      <a href="javascript:void(0);" onclick="clsChange('cls_bzr','','cls_bzr_result');" class="an_blue_small">确定</a><a href="javascript:void(0);" onclick="removeCls('cls_bzr','cls_bzr_result');" class="an_blue_small">删除</a><br />
      <select id="cls_bzr_result"  class="public_input h90 w300 m_t_5" multiple="multiple">
  	  </select>
  	  </td>
  </tr>
</table>     
</div>   

<!-- <p class="public_title">年级组长信息
</p>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
  <col class="w100"/>
  <col class="w760"/>
  <tr>
    <th>学&nbsp;&nbsp;&nbsp;&nbsp;年：</th>  
    <td><select name="select6" id="select6">
      <option>请选择</option>
      <option>2012-2013 高二/12班</option>
      <option>教师</option>
    </select></td>
  </tr>
  <tr>
    <th>年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
    <td><ul class="public_list1">
      <li>
        <input type="checkbox" name="checkbox3" id="checkbox3" />
        初一</li>
      <li>
        <input type="checkbox" name="checkbox3" id="checkbox3" />
        初二
      </li>
      <li>
        <input type="checkbox" name="checkbox3" id="checkbox3" />
        初三</li>
      <li>
        <input type="checkbox" name="checkbox3" id="checkbox3" />
        高一</li>
      <li>
        <input type="checkbox" name="checkbox3" id="checkbox3" />
        高二</li>
      <li>
        <input type="checkbox" name="checkbox3" id="checkbox3" />
        高三</li>
    </ul></td> 
  </tr>
</table>
 -->
  
<p class="t_c p_b_10"><a href="javascript:doOperateUserUpd('${userinfo.ref}');" class="an_blue">提&nbsp;交</a><a href="javascript:window.location.reload();" class="an_blue">重&nbsp;置</a><a href="javascript:history.go(-1);" class="an_blue">返&nbsp;回</a></p>
</div>
</div>  

<%@include file="/util/foot.jsp" %>
  
  </body>
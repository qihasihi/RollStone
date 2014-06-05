<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@include file="/util/common-jsp/common-yhqx.jsp" %>

  <head>
  	<style type="text/css">span{color:red;font-size:12px} p{padding: 3px;}</style>
  	<script type="text/javascript" src="<%=basePath %>js/user.js"></script>
  	<script type="text/javascript">  		
  		$(function(){
  			$('input[name="identity"]').each(function(idx,itm){
  				$(itm).bind("click",function(){
  	  				identityChange(itm.value);
  	  				role4add(itm.value);
  	  	  		});       
  	  		});  
  	  		         
  			identityChange($('input[name="identity"]:checked').val());
  			role4add($('input[name="identity"]:checked').val());

  			//$("input[name='role']").filter(function(){return this.value=="<%=dept_manage_role%>"}).css("display","none");    
  			//$("label[for='role_<%=grade_role_id%>']").hide();
  			    
  		}); 

  		   
  	</script>       
  </head>    
  <body>  
  	<%@include file="/util/head.jsp" %>

<%@include file="/util/nav.jsp" %>

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
        <th><span class="m_lr_5 font-red">*</span>用&nbsp;户&nbsp;名：</th>
        <td><input name="username" id="username" value="" maxlength="24" type="text" class="public_input w200" onblur="validateUsername('username')"/>
        	<span id="username_err"></span>
        	<input type="hidden" id="username_flag" value="0"/>
        </td>
      </tr> 
      
      <tbody style="display:none;">
        <tr>  
	       <th>用户状态：</th>
	        <td> 
	        	<input type="radio" name="user_state" checked id="user_state_0" value="0"/>
		  		<label for="user_state_0">启用</label> 
		  		<input type="radio" name="user_state" id="user_state_1" value="1"/>
		  		<label for="user_state_1">禁用</label>  
	        </td>
	    </tr>
	   </tbody>
      
      <tr>
        <th><span class="m_lr_5 font-red">*</span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</th>
        <td><input name="pass" id="pass" value="" maxlength="16" type="password" class="public_input w200" /></td>
        <!--<th>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</th>
        <td><input id="mail_address" name="mail_address" type="text" class="public_input w300" /></td>-->
      </tr>  
      <!-- <tr> 
        <th>确认密码：</th>
        <td colspan="3"><span class="m_lr_5 font-red">*</span><input  name="repass" id="repass" value="" maxlength="16" type="password" class="public_input w200" /></td>
      </tr> -->
       
      <tr>  
    <th><span class="m_lr_5 font-red">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</th>  
    <td><input name="realname" id="realname" maxlength="30" type="text" class="public_input w200" /></td>
  	</tr>
  	
  	 <tr>  
    <th><span class="m_lr_5 font-red">*</span>性&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
    <td><input type="radio" name="sex" id="sex1" checked="checked" value="男"/> <label for="sex1">男</label>  
  			<input type="radio" name="sex" id="sex2" value="女"/> <label for="sex2">女</label>
  	</td>
  </tr>
  	
     <!--  <tr>
        <th>用户角色：
</th>
        <td colspan="3"><ul class="public_list1">
         <c:if test="${!empty rolelist}">
  				<c:forEach items="${rolelist}" var="r">
  					<li><input type="checkbox" onclick="roleChange(this,'add')" name="role" id="role_${r.roleid }" value="${r.roleid }"/>
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
  					<li><input type="checkbox" onclick="roleChange(this,'add')" name="role" id="role_${r.roleid }" value="${r.roleid }"/>
  						<label for="role_${r.roleid }">${r.rolename }</label>
  					</li>
  				</c:forEach>
  			</c:if>   
        </ul></td>
      </tr> -->
    </table>
    
    <p class="public_title">身份信息</p>	
  	  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
	  <col class="w100"/> 
	  <col class="w760"/>  
	  <tr>  
	    <th><span class="m_lr_5 font-red">*</span>身&nbsp;&nbsp;&nbsp;&nbsp;份：</th>
	    <td> <input type="radio" name="identity" id="identity1" checked="checked" value="学生"/> <label for="identity1">学生</label>  
	  			<input type="radio" name="identity" id="identity2" value="教职工"/> <label for="identity2">教职工</label>
	  	</td>
	  </tr>     
  	</table>
<div id="addstudent" style="display:none" >
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
  <col class="w100"/> 
  <col class="w760"/>  

  <tr>
    <th><span class="m_lr_5 font-red">*</span>学&nbsp;&nbsp;&nbsp;&nbsp;号：</th>
    <td><input name="stuno" id="stuno" maxlength="20" type="text" class="public_input w200" onblur="validateStuno('stuno')"/>
    	<span id="stuno_err"></span>  
        <input type="hidden" id="stuno_flag" value="0"/>
   		<!--  <a href="javascript:validateStuno('stuno')" class="an_blue_small">检测学号</a>-->
   </td> 
  </tr>
  <tr>
    <th><span class="m_lr_5 font-red">*</span>班&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
    <td>
    		<select id="sel_year" onchange="getClsByYear('addstudent')">
    		<c:if test="${!empty clsyearList}">
  				<c:forEach items="${clsyearList}" var="y">
  					<option value="${y.classyearvalue}">
  						${y.classyearname}
  					</option>  
  				</c:forEach>    
  			</c:if>
    	</select>
    	
    	<select id="sel_grade" onchange="getClsByYear('addstudent')"> 
    		<c:if test="${!empty gradeList}" >
  				<c:forEach items="${gradeList}" var="g">
  					<option value="${g.gradevalue}">
  						${g.gradename}  
  					</option>  
  				</c:forEach>    
  			</c:if>
    	</select>
    	
    	<select id="sel_cls"> 
    		<option value="">==请选择==</option>
    	</select>  
    <!-- 	<select id="stucls" name="stucls">  
  			<option value="">==请选择==</option>
  				<c:if test="${!empty clslist}">
  					<c:forEach items="${clslist}" var="c">
  						<option value="${c.classid}">${c.year} | ${c.classgrade} | ${c.classname }</option>
  					</c:forEach>
  				</c:if>  
  			</select> -->    	
    <a href="javascript:;" onclick="clsChange('sel_cls','','stucls_result','addstudent');" class="an_blue_small">确定</a><a href="javascript:void(0);" onclick="removeCls('sel_cls','stucls_result','addstudent');" class="an_blue_small">删除</a><br />
         
       <select class="public_input h90 w300 m_t_5" id="stucls_result" name="stucls_result" multiple="multiple">
		</select>
    </td>  
       
  </tr>
  <tr>
    <th>监&nbsp;护&nbsp;人：</th>
    <td><input name="linkman" id="linkman" maxlength="30" type="text" class="public_input w200" /></td>
  </tr>
  <tr>
    <th>监护电话：</th>
    <td><input  name="linkmanPhone" id="linkmanPhone" maxlength="11" type="text" class="public_input w200" /></td>
  </tr>
  <tr>
    <th>家庭住址：</th>
    <td><input name="stuAddress" id="stuAddress" maxlength="50" type="text" class="public_input w450" /></td>
  </tr>
</table> 
</div>

<div id="addteacher"  style="display:none" >

<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14">
  <col class="w100"/>
  <col class="w760"/>
 
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
    	<select id="sel_year" onchange="getClsByYear('addteacher')">
    		<c:if test="${!empty clsyearList}">
  				<c:forEach items="${clsyearList}" var="y">
  					<option value="${y.classyearvalue}">
  						${y.classyearname}
  					</option>  
  				</c:forEach>    
  			</c:if>
    	</select>
    	
    	<select id="sel_grade" onchange="getClsByYear('addteacher')"> 
    		<c:if test="${!empty gradeList}">
  				<c:forEach items="${gradeList}" var="g">
  					<option value="${g.gradevalue}">
  						${g.gradename}
  					</option>  
  				</c:forEach>    
  			</c:if>
    	</select>
    	
    	<select id="sel_cls" onchange="setClsSubject('addteacher')"> 
    		<option value="">==请选择==</option>
    	</select>  
    	  
    	
    	<!-- <select id="cls" name="cls">     
  				<option value="">==请选择==</option>
  				<c:if test="${!empty clslist}">
  					<c:forEach items="${clslist}" var="c">
  						<option value="${c.classid}">${c.year} | ${c.classgrade} | ${c.classname }</option>
  					</c:forEach>
  				</c:if>     
  		</select> --> 
  		<select id="cls_subject" >  
  			<!--  <c:if test="${!empty subjectlist}">
  				<c:forEach items="${subjectlist}" var="sj">
  					<option value="${sj.subjectid}">
  						${sj.subjectname}
  					</option>  
  				</c:forEach>    
  			</c:if>-->
  		</select>
  			  
      <a href="javascript:;" onclick="clsChange('sel_cls','cls_sex','cls_result','addteacher');" class="an_blue_small">确定</a><a href="javascript:;" onclick="removeCls('sel_cls','cls_result','addteacher')" class="an_blue_small">删除</a><br />
       <select class="public_input h90 w300 m_t_5" id="cls_result" name="cls_result" multiple="multiple">  
	   </select>   
      </td>
  </tr>  
  
  <tr>
   <th>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</th>
        <td><input id="mail_address" name="mail_address" type="text" class="public_input w300" /></td>
  </tr>      
  <!--  <tr>
    <th>身份证号：</th>
    <td><input name="teachercardid" id="teachercardid" maxlength="19" type="text" class="public_input w200" /></td>
  </tr>-->
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
 
 	
    <p class="public_title">角色信息</p>	
    <div id="div_role">
  	  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a_20 zt14"> 
	  <col class="w100"/> 
	  <col class="w760"/>  
	  <tr>  
	    <th>角&nbsp;&nbsp;&nbsp;&nbsp;色：</th>
	    <td id="td_role">
	  	</td>
	  </tr>  
	  <tbody id="tb_teach_role">
	  <!-- 班主任 -->
	   <tr id="tr_bzr" style="display:none;">
	    <th>管理班级：</th>
	    <td>  
	    
	    <select id="sel_year" onchange="getClsByYear('div_role')">
    		<c:if test="${!empty clsyearList}">
  				<c:forEach items="${clsyearList}" var="y">
  					<option value="${y.classyearvalue}">
  						${y.classyearname}
  					</option>  
  				</c:forEach>    
  			</c:if>
    	</select>
    	
    	<select id="sel_grade" onchange="getClsByYear('div_role')"> 
    		<c:if test="${!empty gradeList}">
  				<c:forEach items="${gradeList}" var="g">
  					<option value="${g.gradevalue}">
  						${g.gradename}
  					</option>  
  				</c:forEach>    
  			</c:if>
    	</select>
    	
    	<select id="sel_cls" onchange=""> 
    		<option value="">==请选择==</option>
    	</select>  
    	
	   
	      <a href="javascript:void(0);" onclick="clsChange('sel_cls','','cls_bzr_result','div_role');" class="an_blue_small">确定</a><a href="javascript:void(0);" onclick="removeCls('sel_cls','cls_bzr_result','div_role');" class="an_blue_small">删除</a><br />
	      <select id="cls_bzr_result"  class="public_input h90 w300 m_t_5" multiple="multiple">
	  	  </select>
	  	  </td>
	  </tr>
	  
	  <tr  id="tr_gradeleader" style="display:none;">
	  	<th>年级组长：</th>
	  	<td>
	  		<select id="sel_gradeleader">
	  		</select>
	  	</td>
	  </tr>  
	  
	  <tr id="tr_teachleader" style="display:none;">
	  	<th>教研组长：</th>
	  	<td>
	  		<select id="sel_teachleader">
	  		</select>
	  	</td>
	  </tr>
	  
	  <tr id="tr_dept" style="display:none;">
	  	<th>部门主任：</th>
	  	<td>
	  		<select id="sel_dept">
	  		</select>
	  	</td>
	  </tr>
	    
	  <tr>  
	    <th>管理员&nbsp;<input type="checkbox" onclick="ckAdminRole(this)" /></th>
	    <td id="td_adminrole">
	    	
	  	</td>
	  </tr>  
	  </tbody>   
  	</table>
  	  
  	</div> 
   
<p class="t_c p_b_10"><a href="javascript:doOperateUser();" class="an_blue">提&nbsp;交</a></p>  
<!-- <a href="javascript:window.location.reload();" class="an_blue">重&nbsp;置</a><a href="javascript:history.go(-1);" class="an_blue">返&nbsp;回</a> -->
</div>
</div>  

<%@include file="/util/foot.jsp" %>
  </body>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<%@page import="com.school.entity.UserInfo"%>
<%
    UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
    int dcSchoolID=user.getDcschoolid().intValue();
%>
<head>
	<style type="text/css">
span {
	color: red;
	font-size: 12px
}   

p { 
	padding: 3px;
}
</style>
	<script type="text/javascript" src="<%=basePath %>js/user.js"></script>
	<script type="text/javascript">  		
  		$(function(){
            getClsByYear('addteacher');
            getClsByYear('addstudent');
  			$('input[name="identity"]').each(function(idx,itm){
  				$(itm).bind("click",function(){
  	  				identityChange(itm.value);
  	  				role4add(itm.value);
                });
            });


  			identityChange($('input[name="identity"]:checked').val());
  			role4add($('input[name="identity"]:checked').val());


            /**
            *控制学科
             */
            $('#addteacher input[name="subject"]').attr("checked",false);
            $("#addteacher input[name='subject_major']").each(function(idx,itm){
                $(itm).bind("click",function(){
                    $('#addteacher input[name="subject"][value="'+itm.value+'"]').attr({"checked":false,"disabled":true});
                    $('#addteacher input[name="subject"][value!="'+itm.value+'"]').attr({"disabled":false});
                });
            });
            $('#addteacher input[name="subject"]').each(function(idx,itm){
                $(itm).bind("click",function(){
                    if(itm.value==$("#addteacher input[name='subject_major']:checked").val())
                        $(itm).attr({"checked":false,"disabled":true});
                });
            });
  			    
  		}); 

  		   
  	</script> 
</head>
<body>
	<%@include file="/util/head.jsp"%>

	<%@include file="/util/nav-base.jsp"%>
	
	<div id="nav">
    <ul>
      <li class="crumb"><a href="user?m=list">用户管理</a></li>
      <li><a href="cls?m=list">组织管理</a></li>

        <li ><a href="sysm?m=logoconfig">系统设置</a></li>
    </ul>  
</div>

	<div class="content">
		<div class="contentT">
			<div class="contentR public_input">
            <input type="hidden" id="dcSchoolID" name="dcSchoolID" value="<%=dcSchoolID%>">
				<h4 class="m_t_15">
					基本信息
				</h4>
				<table border="0" cellpadding="0" cellspacing="0"
					class="public_tab1">
					<col class="w100" />
					<col class="w500" />
					<tbody style="display: none;">
						<tr>
							<th>
								用户状态：
							</th>
							<td>
								<input type="radio" name="user_state" checked id="user_state_0"
									value="0" />
								<label for="user_state_0">
									启用
								</label>
								<input type="radio" name="user_state" id="user_state_1"
									value="1" />
								<label for="user_state_1">
									禁用
								</label>
							</td>
						</tr>
					</tbody>

					<tr>
						<th>
                            <span class="ico06"></span>用&nbsp;户&nbsp;名：
						</th>
						<td>
							<input name="username" id="username" type="text" class="w160"
								value="" onblur="validateUsername('username')"/>
							<span id="username_err"></span> 
							<input type="hidden" id="username_flag" value="0" />
						</td>
					</tr>
					<tr>
						<th>
                            <span class="ico06"></span>密&nbsp;&nbsp;&nbsp;&nbsp;码：
						</th>
						<td>
							<input name="pass" id="pass" value="" maxlength="16"
								type="password" class="w160" />
						</td>
					</tr>
					<tr>
						<th>
                            <span class="ico06"></span>姓&nbsp;&nbsp;&nbsp;&nbsp;名：
						</th>
						<td>
							<input name="realname" id="realname" maxlength="30" type="text"
								class="w160" />
						</td>
					</tr>
					<tr>
						<th>
                            <span class="ico06"></span>姓&nbsp;&nbsp;&nbsp;&nbsp;别：
						</th>
						<td>
							<input type="radio" name="sex" id="sex1" checked="checked"
								value="男" />
							<label for="sex1">
								男
							</label>
							<input type="radio" name="sex" id="sex2" value="女" />
							<label for="sex2">
								女
							</label>
						</td>
					</tr>
					<tr class="public_tab1">
						<th>
                            <span class="ico06"></span>身&nbsp;&nbsp;&nbsp;&nbsp;份：
						</th>
						<td>
							<input type="radio" name="identity" id="identity1"  value="学生" />
							<label for="identity1">
								学生
							</label>
							<input type="radio" name="identity" id="identity2" value="教职工" checked="checked" />
							<label for="identity2">
								教职工
							</label>
						</td>
					</tr>
				</table>
				<h6></h6>

				<div id="addstudent" style="display: none">
					<h4>
						学生信息
					</h4>
					<table border="0" cellpadding="0" cellspacing="0"
						class="public_tab1 m_b_20">
						<col class="w100" />
						<col class="w500" />
						<tr>
							<th>
                                <span class="ico06"></span>学&nbsp;&nbsp;&nbsp;&nbsp;号：
							</th>
							<td>
								<input name="stuno" id="stuno" maxlength="20" type="text"
									class="w160" onblur="validateStuno('stuno')" />
								<span id="stuno_err"></span>
								<input type="hidden" id="stuno_flag" value="0" />
							</td>
						</tr>
						<tr>
							<th>
                                <span class="ico06"></span>所在班级：
							</th>
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
									<c:if test="${!empty gradeList}">
										<c:forEach items="${gradeList}" var="g">
											<option value="${g.gradevalue}">
												${g.gradename}
											</option>
										</c:forEach>
									</c:if>
								</select>

								<select id="sel_cls">
									<option value="">
										==请选择==
									</option>
								</select>
 
								&nbsp;&nbsp; 
								<a href="javascript:;" onclick="clsLiChange('sel_cls','','stucls_result','addstudent');" class="an_public3">添&nbsp;加</a>
								<div  class="jcpt_yfgl_info">
									<ol id="stucls_result" name="stucls_result">
										<!-- <li>
											2013-2014学年 高三 实验班-数学 计算机
											<a href="1"><span title="删除"></span>
											</a>
										</li> -->
										
									</ol>
								</div>
							</td>
						</tr> 
						<tr>
							<th>
								&nbsp;&nbsp;监&nbsp;护&nbsp;人：
							</th>
							<td>
								<input name="linkman" id="linkman" maxlength="30" type="text"
									class="w300" />
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;&nbsp;监护电话：
							</th>
							<td>
								<input name="linkmanPhone" id="linkmanPhone" maxlength="11"
									type="text" class="w300" />
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;&nbsp;家庭住址：
							</th>
							<td>
								<input name="stuAddress" id="stuAddress" maxlength="50"
									type="text" class="w300" />
							</td>
						</tr>
					</table>
				</div>



				<div id="addteacher" style="display: none;">
					<h4>
						教职工信息
					</h4>
					<table border="0" cellpadding="0" cellspacing="0"
						class="public_tab1 m_b_20">
						<col class="w100" />
						<col class="w500" />
						<tr>
							<th>
								&nbsp;&nbsp;主教学科：
							</th>
							<td>
								<ul class="public_list1">
									<c:if test="${!empty subjectlist}">
										<c:forEach items="${subjectlist}" var="sj">
											<li>
												<input type="radio" value="${sj.subjectid}"
													name="subject_major" id="subject_major_${sj.subjectid }" />
												<label for="subject_major_${sj.subjectid }">
													${sj.subjectname }
												</label>
											</li>
										</c:forEach>
									</c:if>
								</ul>
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;&nbsp;辅授学科：
							</th>
							<td>
                                <p class="font-darkblue"><a id="a_for_show" href="javascript:void (0);" onclick="ul_subject.style.display='block';a_for_hide.style.display='block';this.style.display='none'">展开选择<span class="ico49a"></span></a><a href="javascript:void (0);" onclick="ul_subject.style.display='none';a_for_show.style.display='block';this.style.display='none'" id="a_for_hide" style="display: none;">收起<span class="ico49c"></span></a></p>
								<ul class="public_list1" id="ul_subject" style="display: none;">
									<c:if test="${!empty subjectlist}">
										<c:forEach items="${subjectlist}" var="sj">
											<li>
												<input type="checkbox" value="${sj.subjectid}"
													name="subject" id="subject${sj.subjectid }" />
												<label for="subject${sj.subjectid }">
													${sj.subjectname }
												</label>
											</li>
										</c:forEach>
									</c:if>
								</ul>
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;&nbsp;授课班级：
							</th>
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
									<option value="">
										==请选择==
									</option>
								</select>
								<select id="cls_subject" style="display:none;">
								</select> 

								<!-- <a href="javascript:;" onclick="clsChange('sel_cls','cls_sex','cls_result','addteacher');" class="an_blue_small">确定</a><a href="javascript:;" onclick="removeCls('sel_cls','cls_result','addteacher')" class="an_blue_small">删除</a><br />
       <select class="public_input h90 w300 m_t_5" id="cls_result" name="cls_result" multiple="multiple">  
	   </select> -->

								&nbsp;&nbsp;
								<a href="javascript:;" onclick="clsLiChange('sel_cls','cls_sex','cls_result','addteacher')" class="an_public3">添&nbsp;加</a>
								<div class="jcpt_yfgl_info">
									<ol id="cls_result">
										<!-- <li>
											2013-2014学年 高三 实验班-数学 计算机
											<a href="1"><span title="删除"></span>
											</a>
										</li> --> 
									</ol>
								</div>
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;&nbsp;邮&nbsp;&nbsp;&nbsp;&nbsp;箱：
							</th>
							<td>
								<input id="mail_address" name="mail_address" maxlength="50"
									type="text" class="w300" />
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;&nbsp;电&nbsp;&nbsp;&nbsp;&nbsp;话：
							</th>
							<td>
								<input name="teacherphone" id="teacherphone" type="text"
									maxlength="20" class="w300" />
							</td>
						</tr>
						<tr>
							<th>
								&nbsp;&nbsp;家庭住址：
							</th>
							<td>
								<input name="teacheraddress" id="teacheraddress" maxlength="50"
									type="text" class="w300" />
							</td>
						</tr>
					</table>
				</div>






				<h6></h6>
				<h4>
					角色信息
				</h4>
				<div id="div_role">
					<table border="0" cellpadding="0" cellspacing="0"
						class="public_tab1">
						<col class="w100" />
						<col class="w500" />

						<tr>
							<th>
								&nbsp;&nbsp;角&nbsp;&nbsp;&nbsp;&nbsp;色：
							</th>
							<td>
								<ul id="td_role" class="public_list1">
								</ul>

								<div class="jcpt_yfgl_juese" id="model_admin_1"> 
									<div id="tr_bzr"  style="display: none;">
									<p>
										<strong>管理班级：</strong>
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
											<option value="">
												==请选择==
											</option>
										</select>

										&nbsp;&nbsp;
										<a href="javascript:void(0);" 
											onclick="clsLiChange('sel_cls','','cls_bzr_result','div_role');"
											class="an_public3">添&nbsp;加</a>
										<div class="jcpt_yfgl_info" >
											<ol id="cls_bzr_result"> 
												<!-- <li> 
													2013-2014学年 高三 实验班-数学 计算机
													<a href="1"><span title="删除">
													</a>
												</li> -->
											</ol>
										</div> 
									</p>
									</div>


 

									<p id="tr_gradeleader" style="display: none;">
										<strong>年级组：</strong>
										<select id="sel_gradeleader">
										</select>
									</p>

									<p id="tr_teachleader" style="display: none;">
										<strong>教研组：</strong>
										<select id="sel_teachleader">
										</select>
									</p>


									<p id="tr_dept" style="display: none;">
										<strong>行政部门：</strong>
										<select id="sel_dept">
										</select>
									</p>
									
									<p id="tr_prepareleader" style="display: none;">
										<strong>备课组：</strong>
										<select id="sel_prepareleader">
										</select>
									</p>

                                    <p id="tr_dept_fzr" style="display: none;">
                                        <strong>自定义部门：</strong>
                                        <select id="sel_dept_fzr">
                                        </select>
                                    </p>
								</div>
							</td>
						</tr> 

						<tr id="model_admin_2">
							<th>   
								<input  type="checkbox" onclick="ckAdminRole(this)" />
								设为管理员
							</th> 
							<td style="display:none;">  
								<select id="td_admin_role" onchange="getRoleRemark(this)">
								</select> 
								&nbsp;&nbsp;
								<a href="javascript:void(0);" onclick="setAdminRole('td_admin_role','ol_admin','model_admin_2')" class="an_public3">添&nbsp;加</a><span id="sp_role_remark"></span>
								<div class="jcpt_yfgl_info">
									<ol id="ol_admin">
										 
									</ol> 
								</div>
							</td>
						</tr>
					</table>
				</div>
				<h6></h6>
				<p class="t_c">
					<a href="javascript:doOperateUser();" class="an_small_long">创&nbsp;建</a>
				</p>
			</div>

			<div class="contentL">
				<ul>
					<li>
						<a href="user?m=list">查询</a>
					</li>
					<li class="crumb">
						<a href="user?m=toAdd">添加</a>
					</li>
                    <%if(visible){%>
					<li> 
						<a href="role?m=list">角色管理</a>
					</li>
                    <%}%>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
		<div class="contentB"></div>
	</div>

	<%@include file="/util/foot.jsp"%>
</body>
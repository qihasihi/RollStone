<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.school.entity.RoleInfo"%>
<%@ include file="/util/common-jsp/common-yhqx.jsp"%>

<%
	UserInfo user = (UserInfo) request.getAttribute("user");
%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>数字化校园</title>
		<script type="text/javascript" src="<%=basePath %>js/user.js"></script>
		<script type="text/javascript">
	var identityname="${identityname}",rolestr="${rolestr}",username="${username}";  
	var isTea=false,isStu=false;
	$(function() {
		<c:forEach items="${roleUser}" var="role">
			<c:if test="${role.roleinfo.roleid==1}">
				isStu=true;
			</c:if>  

			<c:if test="${role.roleinfo.roleid==2}">
				isTea=true; 
			</c:if>
        </c:forEach>		
	});
  
	function go_back(){    
		toPostURL('user?m=list',{ref:'${ref}',identityname:identityname,rolestr:rolestr,username:username},false,null);
	}    
</script>  
	</head>

	<body>
		<%@include file="/util/head.jsp"%>

		<%@include file="/util/nav.jsp"%>

		<div class="jxpt_layout">
			<div class="public_lable_layout">
				<ul class="public_lable_list">
					<li>
						<a href="classyear?m=list">基础设置</a>
					</li>
					<%
						//  <li><a href="dictionary?m=list">字典管理</a></li>
					%>
					<li class="crumb">
						<a href="user?m=list">用户管理</a>
					</li>
					<li>
						<a href="role?m=list">角色管理</a>
					</li>
					<li>
						<a href="job?m=list">职务管理</a>
					</li>
					<li>
						<a href="dept?m=list">部门管理</a>
					</li>
				</ul>
			</div>

			<div class="yhqx_grxx_content">
				<p class="public_title">
					基础信息
					<a id="edit_base" style="color: blue" href="javascript:edit_base()">[修改]</a>
					<a href="javascript:void(0);" onclick="go_back()">返回</a>
				</p>
				<div id="selfinfo">
					<table border="0" cellpadding="0" cellspacing="0"
						class="public_tab1 m_a_20 zt14">
						<col class="w100" />
						<col class="w200" />
						<col class="w450" />
						<tr>
							<th>
								用&nbsp;户&nbsp;名：
							</th>
							<td>
								<span id="sp_username"> <%=user.getUsername()%> </span>
								<%if(user.getStateid()==0){ %>
									<input id="btn_offuser" type="button" value="禁用" onclick="unUse('${ref}',1)"/>
								<%}else{ %>
									<input id="btn_offuser" type="button" value="启用" onclick="unUse('${ref}',0)"/>
								<%} %>     
								  
							</td>
							<th rowspan="5" align="left">
								<!-- <span class="font_strong p_tb_10">用户头像：</span>
      <div class="yhqx_touxiang">
      <img src='<%=user.getHeadimage() != "images/defaultheadsrc.png" ? user
					.getHeadimage() : ""%>' onerror="this.src='images/defaultheadsrc.png'" alt="用户头像" width="135" height="135" /></div>      -->
							</th>
						</tr>


						<tr>
							<th>
								密&nbsp;&nbsp;&nbsp;&nbsp;码：
							</th>
							<td>
								<span id="sp_password"> <%=user.getPassword() != null
					&& !user.getPassword().trim().equals("") ? user
					.getPassword() : "无"%> </span>
							</td>
						</tr>

						<tr>
							<th>
								姓&nbsp;&nbsp;&nbsp;&nbsp;名：
							</th>
							<td>
								<span id="sp_realname"> <%=user.getRealname() != null
					&& !user.getRealname().trim().equals("") ? user
					.getRealname() : "无"%> </span>
							</td>
						</tr>
						<tr>
							<th>
								性&nbsp;&nbsp;&nbsp;&nbsp;别：
							</th>
							<td>
								<span id="sp_sex"> <%=user.getUsersex()%> </span>
							</td>
						</tr>

						<tr id="btn_base" style="display: none;">
							<th>

							</th>
							<td>
								<input type="button" value="保存基础信息"
									onclick="save_base('${ref}')" />
							</td>
						</tr>
					</table>
				</div>
				<c:forEach items="${roleUser}" var="role">
					<c:if test="${role.roleinfo.roleid==1}">
						<c:if test="${!empty stuinfo}">
							<p class="public_title">
								学生信息
								<a id="edit_info" style="color: blue"
									href="javascript:edit_info()">[修改]</a>
							</p>
							<div id="otherinfo">
								<table border="0" cellpadding="0" cellspacing="0"
									class="public_tab1 m_a_20 zt14">
									<col class="w100" />
									<col class="w200" />
									<col class="w450" />
									
									<tbody id="view_body">
									<tr>
										<th>
											学&nbsp;&nbsp;&nbsp;&nbsp;号：
										</th>
										<td>
											<span id="sp_stuno"> ${stuinfo.stuno } </span>
										</td>
									</tr>

									<tr>
										<th>
											所在班级：
										</th>
										<td>
											<span id="sp_stucls"> 
												<c:if test="${!empty stuClsList}">
													<c:forEach items="${stuClsList}" var="cls">
											    		${cls.year }${cls.classgrade }${cls.classname }&nbsp;
													</c:forEach>
												</c:if> 
											 </span>	
											 
											 <select id="hd_cls" style="display:none;">
												<c:if test="${!empty stuClsList }"> 
													<c:forEach items="${stuClsList}" var="cls">
														<option value="${cls.classid }">${cls.year}${cls.classgrade}${cls.classname }</option>
													</c:forEach>
												</c:if>
											 </select>
											<c:if test="${!empty stuHistoryClsList}">
											<a href="javascript:showModel('div_stu_history_cls')">＋</a>查看历史班级
											<div id="div_history_cls" style="display: none;">
												<table border="0" cellpadding="0" cellspacing="0"
													style="border: 1px solid black">
													<tr>
														<th>
															学年
														</th>
														<th>
															年级
														</th>
														<th>
															班级
														</th>
													</tr>
													
														<c:forEach items="${stuHistoryClsList}" var="cls">
															<tr>
																<td>
																	${cls.year }
																</td>
																<td>
																	${cls.classgrade }
																</td>
																<td>
																	${cls.classname }
																</td>
															</tr>
														</c:forEach>

													<c:if test="${empty stuHistoryClsList}">
													暂无!
												</c:if>
												</table>
											</div>
											</c:if>
										</td>
									</tr>

									<tr>
										<th>
											监护人：
										</th>
										<td>
											<span id="sp_linkman"> ${stuinfo.linkman } </span>
										</td>
									</tr>

									<tr>
										<th>
											监护人电话：
										</th>
										<td>
											<span id="sp_linkphone"> ${stuinfo.linkmanphone } </span>
										</td>
									</tr>

									<tr>
										<th>
											家庭住址：
										</th>
										<td>
											<span id="sp_stuaddress"> ${stuinfo.stuaddress } </span>
										</td>
									</tr>
									
										<tr id="btn_info" style="display:none;">
											<th></th>
											<td>
												<input type="button" value="保存学生信息"
													onclick="save_info('${ref}')" />
											</td>
										</tr> 
									</tbody>
									
									
									
									<tbody id="edit_body" style="display:none;">
										<tr> 
										<th>
											班&nbsp;&nbsp;&nbsp;&nbsp;级： 
										</th>
										<td id="edit_cls">
											<select id="sel_year" onchange="getClsByYear('view_body')">
												<c:if test="${!empty clsyearList}">
													<c:forEach items="${clsyearList}" var="y">
														<option value="${y.classyearvalue}">
															${y.classyearname}
														</option>
													</c:forEach>
												</c:if>
											</select>

											<select id="sel_grade" onchange="getClsByYear('view_body')">
												<c:if test="${!empty gradeList}">
													<c:forEach items="${gradeList}" var="g">
														<option value="${g.gradevalue}">
															${g.gradename}
														</option>
													</c:forEach>
												</c:if>
											</select>

											<select id="sel_cls" onchange="setClsSubject('view_body')">
												<option value="">
													==请选择==
												</option>
											</select>
											<a href="javascript:;"
												onclick="clsChange('sel_cls','cls_sex','stucls_result','view_body');"
												class="an_blue_small">确定</a><a href="javascript:;"
												onclick="removeCls('sel_cls','stucls_result','view_body')"
												class="an_blue_small">删除</a>   
											<br />
											<select class="public_input h90 w300 m_t_5" id="stucls_result"
												name="stucls_result" multiple="multiple">
											</select>
										</td>
									</tr>
									</tbody>
								</table>
							</div>
						</c:if>



						<p class="public_title">
							角色信息
							<c:if test="${!empty roleList and fn:length(roleList)>1}">
								<a id="btn_edit_role" style="color: blue" 	href="javascript:edit_role()">[修改]</a>
							</c:if>
						</p>

						<div id="roleinfo">  

							<table border="0" cellpadding="0" cellspacing="0"
								class="public_tab1 m_a_20 zt14">
								<col class="w100" />
								<col class="w200" />
								<col class="w450" />
								<tbody id="view_role">
									<tr>
										<th>
											角&nbsp;&nbsp;&nbsp;&nbsp;色：
										</th>
										<td id="sp_role">
											<c:forEach items="${roleUser}" var="r">
												${r.rolename } &nbsp;
												<input type="hidden" name="hd_role" value="${r.roleid }"/>
											</c:forEach>
										</td>
									</tr>
								</tbody>

								<tbody id="edit_role" style="display: none;">

									<tr>
										<th>
											角&nbsp;&nbsp;&nbsp;&nbsp;色：
										</th>  
										<td>
											<c:if test="${!empty roleList and fn:length(roleList)>1}">
												<c:forEach items="${roleList}" var="r"> 
													<c:if test="${r.isadmin==0&&r.roleid!=1}">
														<input id="role_${r.roleid }" name="role" type="checkbox"
															value="${r.roleid }" />  
														<label for="role_${r.roleid }">
															${r.rolename }
														</label>
													</c:if>  
												</c:forEach>
											</c:if> 
										</td>
									</tr>

									<tr id="btn_role">
										<th>

										</th>
										<td>
											<input type="button" value="保存角色信息"
												onclick="save_role('${ref}')" />
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</c:if>

					<!-- 教师信息 -->
					<c:if test="${role.roleinfo.roleid==2}">
						<c:if test="${!empty teainfo}">
							<p class="public_title">
								教职工信息  
								<a id="edit_info" style="color: blue"
									href="javascript:edit_info()">[修改]</a>
							</p>
							<div id="otherinfo">
								<table border="0" cellpadding="0" cellspacing="0"
									class="public_tab1 m_a_20 zt14">
									<col class="w100" />
									<col class="w200" />
									<col class="w450" />
									<tbody id="view_body">
									<tr>
										<th>
											主授学科：
										</th>
										<td>
											<span id="sp_major"> <c:if
													test="${!empty subjectUserList }">
													<c:forEach items="${subjectUserList}" var="s">
														<c:if test="${s.ismajor==1}">
															${s.subjectname }
															<input type="hidden" name="hd_major" value="${s.subjectid }"/>
														</c:if>
													</c:forEach>
												</c:if> </span>
										</td>
									</tr>

									<tr>
										<th>
											辅授学科：
										</th>
										<td>
											<span id="sp_subject"> <c:if
													test="${!empty subjectUserList }">
													<c:forEach items="${subjectUserList}" var="s">
														<c:if test="${s.ismajor==0}">
														${s.subjectname }&nbsp;
														<input type="hidden" name="hd_subject" value="${s.subjectid }"/> 
													</c:if>
													</c:forEach>
												</c:if> </span>
										</td>
									</tr>

									<tr>
										<th>
											授课班级：
										</th>
										<td>
											<span id="sp_teacls"> 
												<c:if test="${!empty teaClsList }">
													<c:forEach items="${teaClsList}" var="cls">
														${cls.year }${cls.classgrade}${cls.classname }&nbsp;
													</c:forEach>
												</c:if> 
											</span>
												<select id="hd_cls" style="display:none;">
													<c:if test="${!empty teaClsList }">
														<c:forEach items="${teaClsList}" var="cls">
															<option value="${cls.classid }|${cls.subjectid}">${cls.year}${cls.classgrade}${cls.classname }|${cls.subjectname}</option>
														</c:forEach>
													</c:if>
												</select>
											<c:if test="${!empty teaHistoryClsList }">
										<div id="div_history_cls">
											<a>+</a>查看历史授课班级
													<table>
														<tr>
															<th>
																学年
															</th>
															<th>
																行政班
															</th>
															<th>
																分册班
															</th>
														</tr>

														<c:forEach items="${teaHistoryClsList}" var="cls">
															<tr>
																<td>
																	${cls.year }
																</td>
																<td>
																	${cls.classgrade}
																</td>
																<td>
																	${cls.classname }
																</td>
															</tr>
														</c:forEach>
													</table>
												</div>
											</c:if>
										</td>
									</tr>

									<tr>
										<th>
											邮&nbsp;&nbsp;&nbsp;&nbsp;箱：
										</th>
										<td>
											<span id="sp_mail"> ${teainfo.teacherpost } </span>
										</td>
									</tr>

									<tr>
										<th>
											电&nbsp;&nbsp;&nbsp;&nbsp;话：
										</th>
										<td>
											<span id="sp_phone"> ${teainfo.teacherphone } </span>
										</td>
									</tr>

									<tr>
										<th>
											家庭住址：
										</th>
										<td>
											<span id="sp_teaaddress"> ${teainfo.teacheraddress } </span>
										</td>
									</tr>
									
									<tr id="btn_info" style="display:none;">
										<th>

										</th>
										<td>
											<input type="button" value="保存教职工信息"
												onclick="save_info('${ref}')" />
										</td>
									</tr>
									</tbody>
									
								<tbody id="edit_body" style="display:none;">
										<tr>
											<td id="edit_major">
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
											辅授科目：
										</th>
										<td id="edit_subject">
											<ul class="public_list1">
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
									<tr >
										<th>
											班&nbsp;&nbsp;&nbsp;&nbsp;级：
										</th>
										<td id="edit_cls">
											<select id="sel_year" onchange="getClsByYear('view_body')">
												<c:if test="${!empty clsyearList}">
													<c:forEach items="${clsyearList}" var="y">
														<option value="${y.classyearvalue}">
															${y.classyearname}
														</option>
													</c:forEach>
												</c:if>
											</select>  

											<select id="sel_grade" onchange="getClsByYear('view_body')">
												<c:if test="${!empty gradeList}">
													<c:forEach items="${gradeList}" var="g">
														<option value="${g.gradevalue}">
															${g.gradename}
														</option>
													</c:forEach>
												</c:if>
											</select>

											<select id="sel_cls" onchange="setClsSubject('view_body')">
												<option value="">
													==请选择==
												</option>
											</select>

											<select id="cls_subject">

											</select>

											<a href="javascript:;"
												onclick="clsChange('sel_cls','cls_sex','cls_result','view_body');"
												class="an_blue_small">确定</a><a href="javascript:;"
												onclick="removeCls('sel_cls','cls_result','view_body')"
												class="an_blue_small">删除</a> 
											<br />
											<select class="public_input h90 w300 m_t_5" id="cls_result"
												name="cls_result" multiple="multiple">
											</select>
										</td>
									</tr>
									</tbody>
								</table>
							</div>



							


							<p class="public_title">
								角色信息
								<a id="btn_edit_role" style="color: blue"
									href="javascript:edit_role()">[修改]</a>
							</p>


							<div id="roleinfo">

								<table border="0" cellpadding="0" cellspacing="0"
									class="public_tab1 m_a_20 zt14">
									<col class="w100" />
									<col class="w200" />
									<col class="w450" />
									<tbody id="view_role">
										<tr>
											<th>
												角&nbsp;&nbsp;&nbsp;&nbsp;色：
											</th>
											<td>
												<span id="sp_role">
												<c:forEach items="${roleUser}" var="r">
													<c:if test="${r.isadmin==0}">
														${r.rolename } &nbsp;
														<input type="hidden" name="hd_role" value="${r.roleid }"/>
													</c:if>
												</c:forEach>
												</span>
											</td>
										</tr>
										
										
											<tr>
												<td></td>
												<td>
													管理班级:
													<span id="sp_bzr">
														<c:if test="${!empty bzrList }">
															<c:forEach items="${bzrList}" var="cls">
																${cls.classgrade }${cls.classname }
															</c:forEach>	
														</c:if>	
													</span>
													<select id="hd_bzr" style="display:none;">
														<c:if test="${!empty bzrList }">
															<c:forEach items="${bzrList}" var="cls">
																${cls.classgrade }${cls.classname }
																<option value="${cls.classid }">${cls.year }${cls.classgrade }${cls.classname }</option>
															</c:forEach>  
														</c:if>
													</select>
												</td>
											</tr>
										
										
										
											<tr>
												<td></td>
												<td>
													年级组长:
													<span id="sp_grade">
														<c:if test="${!empty gradeLeaderList }">
															<c:forEach items="${gradeLeaderList}" var="g">
																${g.deptname }
																<input type="hidden" name="hd_grade" value="${g.deptid }"/>
															</c:forEach>
														</c:if>
													</span>
												</td>
											</tr>
										
										
											<tr>
												<td></td>
												<td>
													部门主任:
													<span id="sp_dept">
														<c:if test="${!empty duList }">
															<c:forEach items="${duList}" var="d">
																${d.deptname }
																<input type="hidden" name="hd_dept" value="${d.deptid }"/>
															</c:forEach>
														</c:if>
													</span>
												</td>
											</tr>
										
										
											<tr>
												<td></td>
												<td>
													教研组长:
													<span id="sp_tchdept">
														<c:if test="${!empty tchList }">
															<c:forEach items="${tchList}" var="t">
																${t.deptname }
																<input type="hidden" name="hd_tchdept" value="${t.deptid }"/>
															</c:forEach>
														</c:if>
													</span>
												</td>
											</tr>
										
										<tr>
											<th>
												管&nbsp;理&nbsp;员&nbsp;：
											</th>
											<td>
												<span id="sp_admin_role">
													<c:forEach items="${roleUser}" var="r">
														<c:if test="${r.isadmin==1}">
															${r.rolename } &nbsp;
														</c:if>
													</c:forEach>
												</span>
												
												<select id="hd_admin_role" style="display:none;">
												   <c:if test="${!empty roleUser}">  
														<c:forEach items="${roleUser}" var="r">
															<c:if test="${r.isadmin==1}">
																<option value="${r.roleid }">${r.rolename }</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>
											</td>
										</tr>
									</tbody>

									<tbody id="edit_role"  style="display: none;">
										<tr>
											<th>
												角&nbsp;&nbsp;&nbsp;&nbsp;色：
											</th>
											<td>
												<span id="sp_role">
													<c:if test="${!empty roleList}">
														<c:forEach items="${roleList}" var="r">  
															<c:if test="${r.isadmin==0&&r.roleid!=2}">
																<input id="role_${r.roleid }" onclick="roleChange(this,'')" name="role" type="checkbox"
																	value="${r.roleid }" />
																<label for="role_${r.roleid }">  
																	${r.rolename }
																</label>
															</c:if>
														</c:forEach>
													</c:if>
												</span>
											</td>
										</tr>
										
										<tr id="tr_bzr" style="display:none;">
										<th>  
											管理班级：
										</th>
										<td>
											<select id="sel_year" onchange="getClsByYear('edit_role')">
												<c:if test="${!empty clsyearList}">
													<c:forEach items="${clsyearList}" var="y">
														<option value="${y.classyearvalue}">
															${y.classyearname}
														</option>
													</c:forEach>
												</c:if>
											</select>  

											<select id="sel_grade" onchange="getClsByYear('edit_role')">
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

											<a href="javascript:;"
												onclick="clsChange('sel_cls','cls_sex','cls_bzr_result','edit_role');"
												class="an_blue_small">确定</a><a href="javascript:;"
												onclick="removeCls('sel_cls','cls_bzr_result','edit_role')"
												class="an_blue_small">删除</a>   
											<br />  
											<select class="public_input h90 w300 m_t_5" id="cls_bzr_result"
												name="cls_bzr_result" multiple="multiple">
											</select>
										</td>
									</tr>
										

										<tr id="tr_gradeleader" style="display: none;">
											<th>
												年级组长：
											</th>
											<td>
												<select id="sel_gradeleader">
													<c:if test="${!empty gList}">
														<c:forEach items="${gList}" var="g">
															<c:if test="${g.parentdeptid!=-1}">
																<option value="${g.deptid }">${g.deptname }</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>
											</td>
										</tr>

										<tr id="tr_teachleader" style="display: none;">
											<th>
												教研组长：
											</th>
											<td>
												<select id="sel_teachleader">
													<c:if test="${!empty tList}">
														<c:forEach items="${tList}" var="t">
															<c:if test="${t.parentdeptid!=-1}">
																<option value="${t.deptid }">${t.deptname }</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>
											</td>
										</tr>

										<tr id="tr_dept" style="display: none;">
											<th>
												部门主任：
											</th>
											<td>
												<select id="sel_dept">
													<c:if test="${!empty dList}">
														<c:forEach items="${dList}" var="d">
															<c:if test="${d.parentdeptid!=-1}">
																<option value="${d.deptid }">${d.deptname }</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>
											</td>
										</tr>

										<tr>
											<th>
												管理员&nbsp;
												<input id="ck_adminrole" type="checkbox" onclick="ckAdminRole(this)" />
											</th>
											<td id="td_admin_role" style="display:none;">
												<select id="sel_admin_role" >
													<c:if test="${!empty roleList}">
														<c:forEach items="${roleList}" var="r">
															<c:if test="${r.isadmin==1}">
																<option value="${r.roleid }">
																	${r.rolename }
																</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>  

												<a href="javascript:;"
													onclick="setAdminRole('sel_admin_role','admin_result','edit_role')";
													class="an_blue_small">确定</a><a href="javascript:;" onclick="removeCls('sel_admin_role','admin_result','edit_role')" class="an_blue_small">删除</a>
												<br />
  

												<select class="public_input h90 w300 m_t_5"
													id="admin_result" name="admin_result" multiple="multiple">
												</select>
											</td>
										</tr>  

										<tr id="btn_role">
											<th>

											</th>
											<td>
												<input type="button" value="保存角色信息"
													onclick="save_role('${ref}')" />
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</c:if>
					</c:if>
				</c:forEach>



			</div>
		</div>

		<%@include file="/util/foot.jsp"%>
	</body>
</html>

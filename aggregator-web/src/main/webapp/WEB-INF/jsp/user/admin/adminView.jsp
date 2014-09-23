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
		<script type="text/javascript" src="<%=basePath%>js/user.js"></script>
		<script type="text/javascript">
	var identityname="${identityname}",rolestr="${rolestr}",username="${username}";  
	var isTea=false,isStu=false;
	$(function() {
        getClsByYear('edit_role');

		<c:forEach items="${roleUser}" var="role">
			<c:if test="${role.roleinfo.roleid==1}">
				isStu=true;
			</c:if>  

			<c:if test="${role.roleinfo.roleid==2}">
				isTea=true; 
			</c:if>
        </c:forEach>		

		<c:if test="${!empty bzrList}">
			$("#sp_bzr").parent().show();	
		</c:if>

		<c:if test="${!empty gradeLeaderList}">
			$("#sp_grade").parent().show();	
		</c:if>

		<c:if test="${!empty duList}">
			$("#sp_dept").parent().show();	 
		</c:if>

		<c:if test="${!empty tchList}">
			$("#sp_tchdept").parent().show();	
		</c:if>

		<c:if test="${!empty prepareLeaderList}">
			$("#sp_prepareleader").parent().show();	
		</c:if>

        <c:if test="${!empty deptfuzerenList}">
             $("#sp_dept_fzr").parent().show();
        </c:if>


	});
  
	function go_back(){    
		toPostURL('user?m=list',{ref:'${ref}',identityname:identityname,rolestr:rolestr,username:username},false,null);
	}    
</script>
	</head>

	<body>
		<%@include file="/util/head.jsp"%>

		<%@include file="/util/nav-base.jsp"%>

		<div id="nav">
			<ul>
				<li class="crumb">
					<a href="user?m=list">用户管理</a>
				</li>
				<li>
					<a href="cls?m=list">组织管理</a>
				</li>
				<li>
					<a href="term?m=list">系统设置</a>
				</li>
			</ul>
		</div>

		<div class="content">
			<div class="contentT">
				<div class="contentR public_input">
					<p class="back">
						<a href="javascript:void(0);" onclick="go_back();">返回</a>
					</p>
					<h4>
						基本信息
						<span><a id="edit_base" href="javascript:edit_base()">【修改】</a></span>
					</h4>
					<div id="selfinfo">
						<table border="0" cellpadding="0" cellspacing="0"
							class="public_tab1">
							<col class="w100" />
							<col class="w500" />
							<tr>
								<th>
                                    <span class="ico06"></span>用&nbsp;户&nbsp;名：
								</th>
								<td>
									<span id="sp_username"> <%=user.getUsername()%>&nbsp;&nbsp;
									</span>
									<%if (user.getStateid() == 0) {%>
										<a href="javascript:void(0);" id="btn_offuser" onclick="unUse('${ref}',1)" class="an_lightblue">禁&nbsp;用</a>
									<%} else {%>   
										<a class="an_lightblue" href="javascript:void(0);" id="btn_offuser"    
										onclick="unUse('${ref}',0)" >启&nbsp;用</a>
									<%}%>
 
								</td>  
							</tr>
							<tr>
								<th>
                                    <span class="ico06"></span>密&nbsp;&nbsp;&nbsp;&nbsp;码：
								</th>
								<td>
									<span id="sp_password">******</span>
                                    <input type="hidden" id="hd_password" value="<%=user
					.getPassword()%>">
								</td>
							</tr> 
							<tr>
								<th>
                                    <span class="ico06"></span>姓&nbsp;&nbsp;&nbsp;&nbsp;名：
								</th>
								<td>
									<span id="sp_realname"> <%=user.getRealname() != null
					&& !user.getRealname().trim().equals("") ? user
					.getRealname() : "无"%> </span>
								</td>
							</tr>
							<tr>
								<th>
                                    <span class="ico06"></span>姓&nbsp;&nbsp;&nbsp;&nbsp;别：
								</th>
								<td>
									<span id="sp_sex"> <%=user.getUsersex()%> </span>
								</td>
							</tr>

							<tr id="btn_base" style="display: none;">
								</td>
								<th>
									&nbsp;
								</th>
								<td>
									<a href="javascript:void(0);" onclick="save_base('${ref}')"
										class="an_small_long">保存基本信息</a>
								</td>
							</tr>

							<!-- <tr>
							<th>
								＊身&nbsp;&nbsp;&nbsp;&nbsp;份：
							</th>
							<td>
								学生
							</td>
						</tr> -->
						</table>
					</div>
					<h6></h6>



					<c:forEach items="${roleUser}" var="role">
						<c:if test="${role.roleinfo.roleid==1}">
							<c:if test="${!empty stuinfo}">
								<h4>
									学生信息
									<span><a id="edit_info" href="javascript:edit_info()">【修改】</a></span>
								</h4>
								<div id="otherinfo">
									<table border="0" cellpadding="0" cellspacing="0"
										class="public_tab1 m_b_20">
										<col class="w100" />
										<col class="w500" />
										<tbody id="view_body">
											<tr>
												<th>
                                                    <span class="ico06"></span>学&nbsp;&nbsp;&nbsp;&nbsp;号：
												</th>
												<td>
													<span id="sp_stuno"> ${stuinfo.stuno } </span>
												</td>
											</tr>
											<tr>
												<th>
                                                    <span class="ico06"></span>所在班级：
												</th>
												<td>
													<ul id="sp_stucls" class="public_list1">
														<c:if test="${!empty stuClsList}">
															<c:forEach items="${stuClsList}" var="cls">
																<li>
																	${cls.year }${cls.classgrade }${cls.classname }
																</li>
															</c:forEach>
														</c:if>
													</ul>

													<select id="hd_cls" style="display: none;">
														<c:if test="${!empty stuClsList }">
															<c:forEach items="${stuClsList}" var="cls">
																<option value="${cls.classid }">
																	${cls.year}${cls.classgrade}${cls.classname }
																</option>
															</c:forEach>
														</c:if>
													</select>

													<p class="font-blue1" id="p_history">
														<a href="javascript:showModel('div_history_cls')">查看以往所在班级</a>
													</p>
													<!-- 学生历史所在班级 -->
													<div class="public_windows" id="div_history_cls" 
														style="display: none;">
														<h3>
															<a href="javascript:closeModel('div_history_cls')"
																title="关闭"></a>以往所在班级
														</h3> 
														<div class="jcpt_yfgl_floatt02">
															<table border="0" cellpadding="0" cellspacing="0"
																class="public_tab3">
																<col class="w100" />
																<col class="w320" />
																<tr>
																	<th>
																		学年
																	</th>
																	<th>
																		所在班级
																	</th>
																</tr> 
																<tbody id="tb_his_cls">
																	<c:if test="${!empty stuHistoryClsList}">
																		<c:forEach items="${stuHistoryClsList}" var="cls">
																			<tr>
																				<td>
																					${cls.year }
																				</td>
																				<td>
																					${cls.classgrade }${cls.classname }
																				</td>
																			</tr>
																		</c:forEach>
																	</c:if>
	
																	<c:if test="${empty stuHistoryClsList}">
																		<tr><td colspan="2">暂无!</td></tr>
																	</c:if> 
																</tbody>
															</table>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<th>
													&nbsp;&nbsp;监&nbsp;护&nbsp;人：
												</th>
												<td>
													<span id="sp_linkman"> ${stuinfo.linkman } </span>
												</td>
											</tr>
											<tr>
												<th>
													&nbsp;&nbsp;监护电话：
												</th>
												<td>
													<span id="sp_linkphone"> ${stuinfo.linkmanphone } </span>
												</td>
											</tr>
											<tr>
												<th>
													&nbsp;&nbsp;家庭住址：
												</th>
												<td>
													<span id="sp_stuaddress"> ${stuinfo.stuaddress } </span>
												</td>
											</tr>

											<tr id="btn_info" style="display: none;">
												<th>
													&nbsp;
												</th>
												<td>
													<a href="javascript:void(0);" onclick="save_info('${ref}')"
														class="an_small_long">保存学生信息</a>
												</td>
											</tr>
										</tbody>


										<tbody id="edit_body" style="display: none;">
											<tr>
												<th>
                                                    <span class="ico06"></span>所在班级：
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

													&nbsp;&nbsp;
													<a href="javascript:void(0);" onclick="clsLiChange('sel_cls', '', 'stucls_result', 'view_body');" class="an_public3">添&nbsp;加</a>
													<div class="jcpt_yfgl_info">  
														<ol id="stucls_result" name="stucls_result">

														</ol>
													</div>
												</td>
											</tr>
										</tbody>

									</table>
								</div>
							</c:if>

							<h6></h6>
							<h4>
								角色信息
								<c:if test="${!empty roleList and fn:length(roleList)>1}">
									<span><a id="btn_edit_role"
										href="javascript:edit_role()">【修改】</a></span>
								</c:if>
							</h4>
							<div id="roleinfo">
								<table border="0" cellpadding="0" cellspacing="0"
									class="public_tab1">
									<col class="w100" />
									<col class="w500" />

									<tbody id="view_role">
										<tr>
											<th>
												&nbsp;&nbsp;角&nbsp;&nbsp;&nbsp;&nbsp;色：
											</th>
											<td id="sp_role">
												<c:forEach items="${roleUser}" var="r">
													<c:if test="${r.roleid!=1}">
														${r.rolename } &nbsp;
														<input type="hidden" name="hd_role" value="${r.roleid }" />
													</c:if>
												</c:forEach>
											</td> 
										</tr> 
									</tbody>

									<tbody id="edit_role" style="display: none;">

										<tr>
											<th>
												&nbsp;&nbsp;角&nbsp;&nbsp;&nbsp;&nbsp;色：
											</th>
											<td>
												<ul class="public_list1">
													<c:if test="${!empty roleList and fn:length(roleList)>1}">
														<c:forEach items="${roleList}" var="r">
															<c:if test="${r.isadmin==0&&r.roleid!=1}">
																<li>
																	<input id="role_${r.roleid }" name="role"
																		type="checkbox" value="${r.roleid }" />
																	<label for="role_${r.roleid }">
																		${r.rolename }
																	</label>
																</li>
															</c:if>
														</c:forEach>
													</c:if>
												</ul>
											</td>
										</tr>

										<tr id="btn_role">
											<th>
												&nbsp;
											</th>
											<td>
												<a href="javascript:void(0);" onclick="save_role('${ref}')"
													class="an_small_long">保存角色信息</a>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</c:if>


						<!-- 教师信息 -->
						<c:if test="${role.roleinfo.roleid==2}">
							<c:if test="${!empty teainfo}">
								<h4>
									教职工信息
									<span><a id="edit_info" href="javascript:edit_info()">【修改】</a>
									</span>
								</h4>

								<div id="otherinfo">
									<table border="0" cellpadding="0" cellspacing="0"
										class="public_tab1 m_b_20">
										<col class="w100" />
										<col class="w500" />

										<tbody id="view_body">
											<tr>
												<th>
													&nbsp;&nbsp;主教学科：
												</th>
												<td>
													<span id="sp_major"> <c:if
															test="${!empty subjectUserList }">
															<c:forEach items="${subjectUserList}" var="s">
																<c:if test="${s.ismajor==1}">
															${s.subjectname }
															<input type="hidden" name="hd_major"
																		value="${s.subjectid }" />
																</c:if>
															</c:forEach>
														</c:if> </span>
												</td>
											</tr>
											<tr>
												<th>
													&nbsp;&nbsp;辅授学科：
												</th>
												<td>
													<span id="sp_subject"> <c:if
															test="${!empty subjectUserList }">
															<c:forEach items="${subjectUserList}" var="s">
																<c:if test="${s.ismajor==0}">
														${s.subjectname }&nbsp;
														<input type="hidden" name="hd_subject"
																		value="${s.subjectid }" />
																</c:if>
															</c:forEach>
														</c:if> </span>
												</td>
											</tr>
											<tr>
												<th>
													&nbsp;&nbsp;授课班级：
												</th>
												
													
													
														<select id="hd_cls" style="display:none;">
														<c:if test="${!empty teaClsList }">
															<c:forEach items="${teaClsList}" var="cls">
																<option value="${cls.classid }|${cls.subjectid}">${cls.year}${cls.classgrade}${cls.classname }|${cls.subjectname}</option>
															</c:forEach>
														</c:if>
														</select> 
													 
													
												<td>
													<ul id="sp_teacls" class="public_list1">
														<c:if test="${!empty teaClsList }">
															<c:forEach items="${teaClsList}" var="cls">
																<li>
																	${cls.year }${cls.classgrade}${cls.classname }
																</li>
															</c:forEach>
														</c:if>
													</ul>  
													 
													<p class="font-blue1" id="p_history"> 
														<a href="javascript:showModel('div_history_cls')">查看以往授课班级</a>
													</p>

													<div class="public_windows" id="div_history_cls"
														style="display: none;">
														<h3> 
															<a href="javascript:closeModel('div_history_cls')" title="关闭"></a>以往授课班级
														</h3>
														<div class="jcpt_yfgl_float02">
															<table border="0" cellpadding="0" cellspacing="0"
																class="public_tab3">
																<col class="w100" />
																<col class="w320" />
																<tr>
																	<th>
																		学年 
																	</th>
																	<th>
																		授课班级
																	</th>  
																</tr>  
																<tbody id="tb_his_cls">
																<c:if test="${!empty teaHistoryClsList }">
																	<c:forEach items="${teaHistoryClsList}" var="cls">
																		<tr>
																			<td>
																				${cls.year }
																			</td>
																			<td>
																				${cls.classgrade}${cls.classname }
																			</td>
																		</tr>
																	</c:forEach>
																</c:if>

																<c:if test="${empty teaHistoryClsList }">
																	<tr><td colspan="2">暂无!</td></tr>
																</c:if> 
																</tbody>

															</table>
														</div>
													</div>
												</td>
											</tr>
											<tr>
												<th>
													&nbsp;&nbsp;邮&nbsp;&nbsp;&nbsp;&nbsp;箱：
												</th>
												<td>
													<span id="sp_mail"> ${teainfo.teacherpost } </span>
												</td>
											</tr>
											<tr>
												<th>
													&nbsp;&nbsp;电&nbsp;&nbsp;&nbsp;&nbsp;话：
												</th>
												<td>
													<span id="sp_phone"> ${teainfo.teacherphone } </span>
												</td>
											</tr>
											<tr>
												<th>
													&nbsp;&nbsp;家庭住址：
												</th>
												<td>
													<span id="sp_teaaddress"> ${teainfo.teacheraddress }
													</span>
												</td>
											</tr>

											<tr id="btn_info" style="display: none;">
												<th>
													&nbsp;
												</th>
												<td>
													<a href="javascript:void(0);" onclick="save_info('${ref}')"
														class="an_small_long">保存职工信息</a>
												</td>
											</tr>
										</tbody>

										<tbody id="edit_body" style="display: none;">
											<tr>
												<th>
													&nbsp;&nbsp;主教学科：
												</th>
												<td id="edit_major">
													<ul class="public_list1">
														<c:if test="${!empty subjectlist}">
															<c:forEach items="${subjectlist}" var="sj">
																<li>
																	<input type="radio" value="${sj.subjectid}"
																		name="subject_major"
																		id="subject_major_${sj.subjectid }" />
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
												<td id="edit_subject">
                                                    <p class="font-darkblue"><a id="a_for_show" href="javascript:void (0);" onclick="var ul_subject=document.getElementById('ul_subject'),a_for_hide=document.getElementById('a_for_hide'); ul_subject.style.display='block';a_for_hide.style.display='block';this.style.display='none'">展开选择<span class="ico49a"></span></a><a href="javascript:void (0);" onclick="var ul_subject=document.getElementById('ul_subject'),a_for_show=document.getElementById('a_for_show'); ul_subject.style.display='none';a_for_show.style.display='block';this.style.display='none'" id="a_for_hide" style="display: none;">收起<span class="ico49c"></span></a></p>
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

													<select id="cls_subject" style="display:none;">
 
													</select>

													&nbsp;&nbsp;
													<a href="javascript:;"
														onclick="clsLiChange('sel_cls','cls_sex','cls_result','view_body');"
														class="an_public3">添&nbsp;加</a>
													<div class="jcpt_yfgl_info">
														<ol id="cls_result" name="cls_result">

														</ol>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<h6></h6>
								<h4>
									角色信息
									<span><a id="btn_edit_role"
										href="javascript:edit_role()">【修改】</a>
									</span>
								</h4>
								<div id="roleinfo">
									<table border="0" cellpadding="0" cellspacing="0"
										class="public_tab1">
										<col class="w100" />
										<col class="w500" />
										<tbody id="view_role">
											<tr>
												<th>
													&nbsp;&nbsp;角&nbsp;&nbsp;&nbsp;&nbsp;色：
												</th>
												<td>
													<span id="sp_role">
													 <c:forEach items="${roleUser}" var="r">
															<c:if test="${r.isadmin==0}">
                                                                <c:if test="${r.roleid!=2}">
                                                                    ${r.rolename } &nbsp;
                                                                </c:if>
														    <input type="hidden" name="hd_role" value="${r.roleid }" />
															</c:if>
														</c:forEach> </span>
														
													<span style="display:none">
														<br/> 
														<strong>管理班级：</strong>
														<span id="sp_bzr"> <c:if test="${!empty bzrList }">
																<c:forEach items="${bzrList}" var="cls">
																	${cls.classgrade }${cls.classname }&nbsp;
																</c:forEach>
															</c:if> </span>
													</span> 
														
													<select id="hd_bzr" style="display: none;">
														<c:if test="${!empty bzrList }">
															<c:forEach items="${bzrList}" var="cls">
																${cls.classgrade }${cls.classname }
																<option value="${cls.classid }">
																	${cls.year }${cls.classgrade }${cls.classname }
																</option>
															</c:forEach>
														</c:if>
													</select>
													
													<span style="display:none">
													<br/>
													<strong>年级组:</strong>
													<span id="sp_grade"> <c:if
															test="${!empty gradeLeaderList }">
															<c:forEach items="${gradeLeaderList}" var="g">
																${g.deptname }
																<input type="hidden" name="hd_grade"
																	value="${g.deptid }" />
															</c:forEach>
														</c:if> </span>
													</span>

													<span style="display:none">
													<br/>
													<strong>教研组:</strong>
													<span id="sp_tchdept"> <c:if
															test="${!empty tchList }">
															<c:forEach items="${tchList}" var="t">
																${t.deptname }
																<input type="hidden" name="hd_tchdept"
																	value="${t.deptid }" />
															</c:forEach>
														</c:if> </span>
													
													</span>
													
													<span style="display:none">
													<br/>
													<strong>行政部门:</strong>
													<span id="sp_dept"> <c:if test="${!empty duList }">
															<c:forEach items="${duList}" var="d">
																${d.deptname }
																<input type="hidden" name="hd_dept" value="${d.deptid }" />
															</c:forEach>
														</c:if> </span>
													</span>
													
													<span style="display:none">
													<br/>
													<strong>备课组:</strong>
													<span id="sp_prepareleader"> <c:if test="${!empty prepareLeaderList }">
															<c:forEach items="${prepareLeaderList}" var="d">
																${d.deptname }
																<input type="hidden" name="hd_prepare" value="${d.deptid }" />
															</c:forEach>
														</c:if> </span>	
													</span>


                                                    <span style="display:none">
                                                    <br/>
													<strong>自定义部门:</strong>
													<span id="sp_dept_fzr"> <c:if test="${!empty deptfuzerenList }">
                                                        <c:forEach items="${deptfuzerenList}" var="d">
                                                            ${d.deptname }
                                                            <input type="hidden" name="hd_dept_fzr" value="${d.deptid }" />
                                                        </c:forEach>
                                                    </c:if> </span>
													</span>
												</td>
											</tr>

											<tr>
												<th>
													&nbsp;&nbsp;管&nbsp;理&nbsp;员：
												</th>
												<td>
													<span id="sp_admin_role"> 
													 	<c:forEach items="${roleUser}" var="r">
															<c:if test="${r.isadmin==1}">
																${r.rolename } &nbsp;
															</c:if>
														</c:forEach>
													 </span>

													<select id="hd_admin_role" style="display: none;">
														<c:if test="${!empty roleUser}">
															<c:forEach items="${roleUser}" var="r">
																<c:if test="${r.isadmin==1}">
																	<option value="${r.roleid }">
																		${r.rolename }
																	</option>
																</c:if>
															</c:forEach>
														</c:if>
													</select>
												</td>
											</tr>
										</tbody>

										<tbody id="edit_role" style="display: none;">
											<tr>
												<th>
													&nbsp;&nbsp;角&nbsp;&nbsp;&nbsp;&nbsp;色：
												</th>
												<td>
													<ul id="sp_role" class="public_list1">
															<c:if test="${!empty roleList}">
														<c:forEach items="${roleList}" var="r">  
															<c:if test="${r.isadmin==0&&r.roleid!=2}">
																<li><input id="role_${r.roleid }" onclick="roleChange(this,'')" name="role" type="checkbox"
																	value="${r.roleid }" />
																<label for="role_${r.roleid }">  
																	${r.rolename }
																</label>
																</li>
															</c:if>
														</c:forEach>
													</c:if> 
													</ul>
													
													<div  class="jcpt_yfgl_juese">
														<div id="tr_bzr" style="display:none;">
														<p>
															<strong>管理班级：</strong>
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

															&nbsp;&nbsp;
															<a  href="javascript:;" onclick="clsLiChange('sel_cls','cls_sex','cls_bzr_result','edit_role');"class="an_public3">添&nbsp;加</a>
														</p>  
														<div class="jcpt_yfgl_info">
															<ol id="cls_bzr_result" name="cls_bzr_result">
																 
															</ol>
														</div>
														</div> 
														 
														
										<p id="tr_gradeleader" style="display: none;">
											<strong>
												年级组：
											</strong>
												<select id="sel_gradeleader">
													<c:if test="${!empty gList}">
														<c:forEach items="${gList}" var="g">
															<c:if test="${g.parentdeptid!=-1}">
																<option value="${g.deptid }">${g.deptname }</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>
										</p>

										<p id="tr_teachleader" style="display: none;">
											<strong>
												教研组：
											</strong>
												<select id="sel_teachleader">
													<c:if test="${!empty tList}">
														<c:forEach items="${tList}" var="t">
															<c:if test="${t.parentdeptid!=-1}">
																<option value="${t.deptid }">${t.deptname }</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>
										</p>

										<p id="tr_dept" style="display: none;">
											<strong>
												行政部门：
											</strong>
												<select id="sel_dept">
													<c:if test="${!empty dList}">
														<c:forEach items="${dList}" var="d">
															<c:if test="${d.parentdeptid!=-1}">
																<option value="${d.deptid }">${d.deptname }</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>
										</p>
										
										<p id="tr_prepareleader" style="display: none;">
											<strong> 
												备课组：
											</strong> 
												<select id="sel_prepareleader">
													<c:if test="${!empty pList}">
														<c:forEach items="${pList}" var="d">
															<c:if test="${d.parentdeptid!=-1}">
																<option value="${d.deptid }">${d.deptname }</option>
															</c:if>
														</c:forEach>
													</c:if>
												</select>
										</p>

                                        <p id="tr_dept_fzr" style="display: none;">
                                            <strong>
                                                自定义部门：
                                            </strong>
                                            <select id="sel_dept_fzr">
                                                <c:if test="${!empty dFreeList}">
                                                    <c:forEach items="${dFreeList}" var="d">
                                                        <c:if test="${d.parentdeptid!=-1}">
                                                            <option value="${d.deptid }">${d.deptname }</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                            </select>
                                        </p>
												</td>
											</tr>
											
											<tr>
												<th>
													<input id="ck_adminrole" type="checkbox" onclick="ckAdminRole(this)" />
													设为管理员
												</th>
												<td id="td_admin_role" style="display:none;"> 
													<select id="sel_admin_role" onchange="getRoleRemark(this);" >
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
													
													&nbsp;&nbsp;
													<a href="javascript:void(0);" onclick="setAdminRole('sel_admin_role','admin_result','td_admin_role')" class="an_public3">添&nbsp;加</a><span id="sp_role_remark"></span>
													</p>
													<div class="jcpt_yfgl_info">
														<ol id="admin_result" name="admin_result">
															
														</ol>
													</div>
												</td>
											</tr>
											<tr  id="btn_role">
												<th>
													&nbsp;
												</th>
												<td> 
													<a href="javascript:void(0);" onclick="save_role('${ref}')" class="an_small_long">保存角色信息</a>
												</td>  
											</tr>
										</tbody>
									</table>
								</div>
							</c:if> 
						</c:if>
					</c:forEach>
				</div>  

				<div class="contentL">
					<ul>
						<li  class="crumb">
							<a href="user?m=list">查询</a>
						</li>
						<li>
							<a href="user?m=toAdd">添加</a>
						</li>
						<li>
							<a href="role?m=list">角色管理</a>
						</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<div class="contentB"></div>
		</div>


		<%@include file="/util/foot.jsp"%>
	</body>
</html>

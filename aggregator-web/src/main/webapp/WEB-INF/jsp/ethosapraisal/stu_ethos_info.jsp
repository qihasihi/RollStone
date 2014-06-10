<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common.jsp"%>
<%
UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
String userid=user.getRef();
%>
  <head>
    <base href="<%=basePath%>">
    
    <title>title</title>    
	<script type="text/javascript" src="<%=basePath %>js/ethosaraisal/stu_ethos_info.js"></script>
	<script type="text/javascript">
		var cls_id="${param.classId}";
		var year="${param.year}";
		var weekId = "${param.weekId}";
		var grade = "${param.gradeName}";
		$(function(){loadStudentByClass(cls_id,year,'行政班');getStuEthosDetail();})
	</script>
  </head>
  
  <body>
  
  <div class="content">

			<form action="" name="stuform" method="post">

				<input type="hidden" name="gradeName" id="grade" />
				<input type="hidden" name="weekName" id="weekid" />
				<input type="hidden" name="className" id="classid" />
				<input type="hidden" name="yearName" id="year" />
				<input type="hidden" name="ishave" id="ishave" value="1" />
				<c:if test="${!empty wkinfo &&!empty clsinfo}">
					<p style="padding-bottom: 10px;color:blue">${wkinfo.terminfo.year }${wkinfo.terminfo.termName } ${clsinfo.classGrade }${clsinfo.className } ${wkinfo.weekname } 学生校风情况!</p>
				</c:if>
				<table border="1" cellpadding="0" cellspacing="0"
					class="Table border_Tab W974">
					<col class="W200" />
					<tr>
						<td align="center" valign="top">
							<p class="F_b Pt5 Tl Pl20">
								学号/姓名
							</p>
							<p class="F_b Pt5">
								<input type="text" value="" name="stuinfo" id="stuinfo"/>
							</p>
							<p class="Pt35">
								<a href="javascript:doAddOrUpdateStuEthos();">添加/修改 </a>
							</p>
						
							<p class="Pt10">
								<a href="javascript:doArrayDelete();">删除 </a>
							</p>
							<p class="Pt10">
								<a href="javascript:toClear();">清空</a>
							</p>
							<p class="Pt10">
								<a href="javascript:void('');">级联更新 </a>
							</p>
							<p class="Ptb10">
								<a href="javascript:window.close();">退出</a>
							</p>
						</td>
						<td>
							<table class="M0 Table noborder_Tab" cellspacing="0"
								cellpadding="0" border="0" id="stuethTab">
								<col class="W120" />
								<col class="W403" />
								<col class="W220" />
								<tr>
									<th>
										项目
									</th>
									<th>
										情况
									</th>
									<th>
										数量/分数
									</th>
								</tr>
								<tr>
									<td align="center">
										<br />
										病假
									</td>
									<td>
										<textarea name="sickleave" id="sickleave"
											class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="sickleavenum" id="sickleavenum" type="text"
											maxlength="4" class="border_box W81 H30"
											onblur="Minuspoint('sickleave','sickleavenum','sickleavescore','病假');" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="sickleavescore" id="sickleavescore" maxlength="4"
											readonly="readonly" type="text" class="border_box W81 H30" />
									</td>
								</tr>
								<tr>
									<td align="center">
										<br />
										事假
									</td>
									<td>
										<textarea name="thingleave" id="thingleave"
											class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="thingleavenum" id="thingleavenum" maxlength="4"
										onblur="Minuspoint('thingleave','thingleavenum','thingleavescore','事假');"
											type="text" class="border_box W81 H30" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="thingleavescore" id="thingleavescore"
										
											maxlength="4" readonly="readonly" type="text"
											class="border_box W81 H30" />
									</td>
								</tr>
								<tr>
									<td align="center">
										<br />
										早退
									</td>
									<td>
										<textarea name="leaveearly" id="leaveearly"
											class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="leaveearlynum" id="leaveearlynum" maxlength="4"
											
											onblur="Minuspoint('leaveearly','leaveearlynum','leaveearlyscore','早退');"										
											type="text" class="border_box W81 H30" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="leaveearlyscore" id="leaveearlyscore"
											maxlength="4" readonly="readonly" type="text"
											class="border_box W81 H30" />
									</td>
								</tr>
								<tr>
									<td align="center">
										<br />
										旷课
									</td>
									<td>
										<textarea name="absenteeism" id="absenteeism"
											class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="absennum" id="absennum" maxlength="4"
											onblur="Minuspoint('absenteeism','absennum','absenscore','旷课');" type="text"
											class="border_box W81 H30" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="absenscore" readonly="readonly" id="absenscore" maxlength="4"
											 type="text" class="border_box W81 H30" />
									</td>
								</tr>
								<tr> 
									<td align="center">
										<br />
										迟到
									</td>
									<td>
										<textarea name="late" id="late" class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="latenum" id="latenum" maxlength="4"
											onblur="Minuspoint('late','latenum','latescore','迟到');" 											
											type="text" class="border_box W81 H30" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="latescore" id="latescore" maxlength="4"
											readonly="readonly" type="text" class="border_box W81 H30" />
									</td>
								</tr>
								<tr>
									<td align="center">
										<br />
										胸卡
									</td>
									<td>
										<textarea name="badge" id="badge" class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
									<!-- onblur="Minuspoint('badge','badgenum','badgescore','胸卡');"  -->
										<input name="badgenum" id="badgenum" type="text"										
											maxlength="4" class="border_box W81 H30" 
											onblur="Minuspoint('badge','badgenum','badgescore','胸卡');" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="badgescore" id="badgescore" maxlength="4"
											readonly="readonly" type="text" class="border_box W81 H30" />
									</td>
								</tr>
								<tr>
									<td align="center">
										<br />
										校服
									</td>
									<td>
										<textarea name="uniforms" id="uniforms"
											class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="uniformsnum" id="uniformsnum" type="text"
											onblur="Minuspoint('uniforms','uniformsnum','uniformsscore','校服');" 											 
											maxlength="4" class="border_box W81 H30" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="uniformsscore" id="uniformsscore" type="text"
											maxlength="4" readonly="readonly" class="border_box W81 H30" />
									</td>
								</tr>
								<tr>
									<td align="center">
										<br />
										行规
									</td>
									<td>
										<textarea name="linere" id="linere"
											class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="linerenum" id="linerenum" type="text"
											maxlength="4" class="border_box W81 H30" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="linerescore" id="linerescore" type="text"
											maxlength="4" class="border_box W81 H30" />
									</td>
								</tr>
								<tr>
									<td align="center">
										<br />
										还书记录
									</td>
									<td>
										<textarea name="rebook" id="rebook"
											class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="rebooknum" id="rebooknum" type="text"
										onblur="Minuspoint('rebook','rebooknum','rebookscore','还书');"
											maxlength="4" class="border_box W81 H30" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="rebookscore" id="rebookscore" type="text"
										
											maxlength="4" readonly="readonly" class="border_box W81 H30" />
									</td>
								</tr>
								<tr>
									<td align="center">
										<br />
										好人好事（加分）
									</td>
									<td>
										<textarea name="goodthing" id="goodthing"
											class="border_box H30 W380"></textarea>
									</td>
									<td valign="bottom">
										<input name="goodthingnum" id="goodthingnum" maxlength="4"
											type="text" class="border_box W81 H30" />
										<span class="Plr5 display Mt10"> /</span>
										<input name="goodthingscore" id="goodthingscore" maxlength="4"
											type="text" class="border_box W81 H30" />
									</td>
								</tr>
							</table>
							<p>
								&nbsp;
							</p>
						</td>
					</tr>
				</table>
			</form>
			<form action="">
				<!-- 数据加载的时候 显示列明 -->
				<p class="F_b Pt15">
					班级数据汇总
				</p>
				<div class="TabLayout" style="overflow-x: auto; overflow-y: hidden; margin: 0;">
					<table border="0" cellpadding="0" cellspacing="0"
						class="Tab W80 Tc" id="dataTbl">
					</table>
				</div>
			</form>
		</div>
		<div class="W546 white_content" id="showDetailDiv">
	  <div class="windowT01"><a href="javascript:void('');" onclick="closeModel('showDetailDiv');"><img src="images/an14.gif" alt="关闭" width="15" height="15" border="0" /></a>
	  	<div class="Tc F_b" align="center">详情查看</div>
	  </div>
	  <div class="windowC01">
	    <div class="H200 Plr20" style="overflow:auto">
	      <p class="Pt10" id="detailText">编辑场地信息,编辑场地信息,编辑场地信息.</p>       
	    </div>
	  </div>
	  <div class="windowB01"></div>
	</div>
  </body>

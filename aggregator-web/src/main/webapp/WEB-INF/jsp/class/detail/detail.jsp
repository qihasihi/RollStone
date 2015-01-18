<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>

<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
	 <style type="text/css">	 
		.h170{height:170px;}
	 </style>
		<script type="text/javascript" src="<%=basePath %>js/class.js"></script>
		<script type="text/javascript">
            var isupdateclsstu=false;
		$(function(){			
		});
		var clsid="${classid}";
		function explortExl(){
			document.forms[0].action="cls?m=exportClsExcel";
			document.forms[0].submit();     
		}
		</script>      
     
    <script type="text/javascript">
    $(function() {
        if(${clsinfo.dcschoolid} == <%=UtilTool.utilproperty.getProperty("ELITE_SCHOOL_ID")%>) {
            $("#add_sel_pattern").attr("disabled", true);
            $("#activity_type_row").show();
            $("#term_id_row").show();
            $("#is_elite").val("1");
            $("#class_attr").hide();
            $("#class_type").hide();
            $("#exportStudent").hide();
            $("#copyFromOtherClass").hide()
            var options = document.getElementById("add_sel_pattern").options;
            options[1].selected=true;
            $("#tr_add_subject").show();
        }
    });
    </script>
	</head> 
	<body>
	   <%@include file="/util/head.jsp" %>
 <%@include file="/util/nav-base.jsp" %>  
<div id="nav">
    <ul>
      <li><a href="user?m=list">用户管理</a></li>
      <li class="crumb"><a href="cls?m=list">组织管理</a></li>
      <li><a href="term?m=list">系统设置</a></li>
    </ul>
</div>
			<!-- <div class="public_lable_layout">
				<ul class="public_lable_list font-white">
					<li class="crumb">
						<a href="tptask!toTaskList.action?courseid=${param.courseid }">学习任务</a>
					</li>
					<li>
						<a href="res!toTeacherIdx.action?courseid=${param.courseid }">课程资源</a>
					</li>
					<li>
						<a
							href="activecolumns!toColumnsAdmin.action?courseteacher=${param.courseid }">互动空间</a>
					</li>
				</ul>
			</div> -->  
		 
<div class="content">
 <div class="contentT">
    <div class="contentR public_input">
    <p class="back"><a href="cls?m=list" >返回</a></p>
    <h4>班级信息<span id="sp_update_cls">【<a href="javascript:readlyToUpdate(${clsinfo.classid })">修改</a>】</span></h4>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1">
      <col class="w100"/>
      <col class="w500"/>
      <tr>
        <th><span class="ico06"></span>学&nbsp;&nbsp;&nbsp;&nbsp;年：</th>
        <td><span id="sp_year">${clsinfo.classyearname} <input type="hidden" value="${clsinfo.year}"/></span></td>
      </tr>
        <tr id="term_id_row" style="display:none">
            <th><span class="ico06"></span>活动期次：</th>
            <td>
                <span id="termid1">第&nbsp;${clsinfo.termid}&nbsp;期</span>
                <span id="termid_row" style="display:none">第&nbsp;<input id="term_id" type="number" class="w80" min="1" value="${clsinfo.termid}"/>&nbsp;期</span>
            </td>
        </tr>

        <tr id="activity_type_row" style="display:none">
            <input type="hidden" id="is_elite" name="is_elite" value="0">
            <th><span class="ico06"></span>活动类型：</th>
            <td><span id="activitytype1"><c:if test="${clsinfo.activitytype==1}">菁英班</c:if>
                <c:if test="${clsinfo.activitytype==2}">普通班</c:if></span>

                <span id="activitytype_row" style="display:none">
                    <select class="w160" name="activity_type" id="activity_type">
                        <c:if test="${clsinfo.activitytype==1}">
                            <option value="1" selected="selected">菁英班</option>
                        </c:if>
                        <c:if test="${clsinfo.activitytype!=1}">
                            <option value="1">菁英班</option>
                        </c:if>
                        <c:if test="${clsinfo.activitytype==2}">
                            <option value="2" selected="selected">普通班</option>
                        </c:if>
                        <c:if test="${clsinfo.activitytype!=2}">
                            <option value="2">普通班</option>
                        </c:if>
                    </select>
                </span>

            </td>
        </tr>
      <tr>
        <th><span class="ico06"></span>年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
        <td><span id="sp_classgrade">${clsinfo.classgrade}</span>
        <span style="display:none"><select id="sel_grade">
					<c:if test="${!empty gdList}">
						<c:forEach items="${gdList}" var="gd">
							<option value="${gd.gradevalue}">${gd.gradename }</option>
						</c:forEach>
					</c:if>
				</select></span>
			<!-- <span id="sp_cannelUpdate" style="float:right;display:none"><a href="javascript:quxiaoClsUpdate(${clsinfo.classid })"><img width="15px" height="15px" src="css/images/icon03_130108.gif"/></a>&nbsp;&nbsp;&nbsp;</span> -->
        </td>
      </tr>
      <tr>
        <th><span class="ico06"></span>班级名称：</th>
        <td><span id="sp_classgrade_copy">${clsinfo.classgrade}</span>&middot;
          <span id="sp_classname">${clsinfo.classname}</span></td>
      </tr>
      <tr id="class_attr">
        <th><span class="ico06"></span>文 /  理：</th>
        <td><span id="sp_type">
					<c:if test="${empty clsinfo.type}">
						未知<input type="hidden" value=""/>
					</c:if>
					<c:if test="${!empty clsinfo.type}">
						<c:if test="${clsinfo.type=='W'}">
							文科
						</c:if>
						<c:if test="${clsinfo.type=='L'}">
							理科
						</c:if>
						<c:if test="${clsinfo.type=='NORMAL'}">
							普通
						</c:if>
					</c:if>
				</span></td>
      </tr>
      <tr id="class_type">
        <th><span class="ico06"></span>类&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
        <td><span id="sp_pattern">
						<c:if test="${clsinfo.pattern=='行政班'}">
							行政班
						</c:if>
						<c:if test="${clsinfo.pattern=='分层班'}">
							分层班
						</c:if>
					</span></td>
      </tr>
      <c:if test="${clsinfo.pattern=='行政班'}">		
					<tr id="up_subject" style="padding-bottom:5px;display:none">
				</c:if>
				<c:if test="${clsinfo.pattern=='分层班'}">
					<tr id="up_subject"  style="padding-bottom:5px;">
				</c:if>
				<th><span class="ico06"></span>所属学科：</th>
				<td><span id="sp_subject">${!empty clsinfo.subjectname?clsinfo.subjectname:"无" }</span>
					<select name="sel_subject" id="sel_subject" style="display:none">
                        <c:if test="${!empty subList}">
                            <c:forEach items="${subList}" var="sb">
                                <option value="${sb.subjectid }">${sb.subjectname }</option>
                            </c:forEach>
                        </c:if>
                    </select>
				</td>
      </tr>
      <tr id="up_bottom_li" style="display:none">
        <th>&nbsp;</th>
      	<td>
     	 <a  class="an_small_long" href="javascript:doSubUpdate(${clsinfo.classid });">保存班级信息</a>
		</td>
      </tr>
    </table>
    <h6></h6>
    <form action="cls" method="get">  
					 <input type="hidden" name="classid" value="${clsinfo.classid }"/>
				  <input type="hidden" name="m" value="exportClsExcel"/>
				</form>
    <h4>班级学生列表</h4>
    <p class="t_c"><a id="copyFromOtherClass" href="javascript:toCopyClassStudent(${clsinfo.classid })"  class="an_big_long">从其他班级复制学生</a>
        <%if(u!=null&&(u.getIsactivity()==null||u.getIsactivity()==0)){%>
            &nbsp;&nbsp;&nbsp;&nbsp;<a id="exportStudent" href="javascript:showModel('loadExcel')" class="an_big_long">从模版导入学生</a>
        <%}%>
    </p>
    <table id="initItemList" border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
      <colgroup span="3" class="w200"></colgroup>
      <tr>
        <th>学号</th>
        <th>姓名</th>
        <th>性别</th>
        </tr>
      <c:if test="${!empty stuList}">
			<c:forEach items="${stuList}" var="stu" varStatus="stuIdx">
					<c:if test="${stuIdx.index%2!=0}">
							<tr class="trbg1">
					</c:if>
					<c:if test="${stuIdx.index%2==0}">
							<tr>
					</c:if>
						<td>${stu.stuno }</td>
						<td>${stu.userinfo.realname }</td>
						<td>${stu.userinfo.sex }</td>
				</tr>
			</c:forEach>
		</c:if>
    </table>
    <p class="font-blue1 jcpt_zzgl_dc"><a href="javascript:document.forms[0].submit();">导出本班学生名单</a></p>
    </div>
    <div class="contentL">
      <ul>
        <li class="crumb"><a href="cls?m=list">班级管理</a></li>
          <%if(visible){%>
            <li><a href="dept?m=list">部门管理</a></li>
          <%}%>

      </ul>
    </div>
    <div class="clear"></div>
</div>
<div class="contentB"></div>
</div>


		 <%@include file="/util/foot.jsp" %>
<div class="public_windows" id="loadExcel" style="display:none;">
  <h3><a href="javascript:;" onclick="loadExcel.style.display='none';fade.style.display='none';if(isupdateclsstu){location.reload();}" title="关闭"></a>从模版导入学生</h3>
  <div class="jcpt_zzgl_float">
    <p>
      <input type="file" name="upload" id="upload" class="w220">
      &nbsp;<a id="aload" href="javascript:uploadStudentExcel(${clsinfo.classid});" class="an_public2">上传</a>&nbsp;&nbsp;<a href="javascript:explortClassTemplate(${clsinfo.classid})" class="font-blue">下载学生名册导入模版</a></p>
      <p style="display:none" id="showLoadHtml"></p>
    <p class="p_t_10"><strong class="font-blue1">注意：</strong>      此操作会自动清空本班已有学生名册，再导入模版中学生。</p>
  </div>
</div>

<div style="display:none" >
<form id="explortClsTemplateFRM" name="explortClsTemplate" method="post" target="_blank">
  	<input name="classname" value="" id="classname"/>
  	<input name="year" value="" id="year"/>
  	<input name="classgrade" value="" id="classgrade"/>
  	<input name="pattern" value="" id="pattern"/>
  	<input name="dtype" value="" id="type"/> 
  	<input name="classid" value="" id="classid"/>
  </form>
  </div>
<div class="public_windows"  id="dv_tiaoban" style="display: none">

  <h3><a href="javascript:;" onclick="dv_tiaoban.style.display='none';fade.style.display='none';if(isupdateclsstu){location.reload();}" title="关闭"></a>从其它班级复制学生</h3>
  <div class="jcpt_zzgl_float public_input">
  <div class="one">
    <p><select  id="sel_class" onchange="getClassStudent(this)" id="select">
    </select></p>
    <p class="p_t_10"><input name="txt_search_1" id="txt_search_1" type="text" class="public_input w140" placeholder="模糊查询（学号/姓名）" /> </p>
    <div class="p_t_30"><select id="sel_op_1" name="sel_op_1" class="w160" style="height:170px"  multiple="multiple">（已调）色值#38bab7</select></div>
  </div>

  <div class="two">
    <p><a href="javascript:;" onclick="clsAddToRight()"><img src="images/an03_131126.png" title="添加" width="68" height="20"></a></p>
    <p class="p_t_10"><a href="javascript:doDelSelected();"><img src="images/an04_131126.png" title="删除" width="68" height="20"></a></p>
    <p class="p_t_30"><a href="javascript:;" onclick="doAddClsUsrToDetail(${clsinfo.classid})"   class="an_public1">确&nbsp;定</a></p>
  </div> 
  <div class="three">
      <select name="sel_op_2" id="sel_op_2" class="w160" style="height:170px"  multiple="multiple">（已调）色值#38bab7</select>
  </div>
  <div class="clear"></div>
 </div>
</div>

	</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<%@ page import="com.school.manager.ClassManager" %>
<%@ page import="com.school.entity.*" %>
<%@ page import="net.sf.json.JSON" %>
<%@ page import="jcore.jsonrpc.common.JSONObject" %>
<%@ page import="jcore.jsonrpc.common.JSONArray" %>
<%  
	request.setAttribute("isSelect",true);	 //查询功能权限
    request.setAttribute("isAdd",true);	 //添加功能权限
    request.setAttribute("isUpdate",true);	 //修改功能权限
 %>

<%
    UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
    int dcSchoolID=user.getDcschoolid().intValue();
    boolean isElite = false;
    if(dcSchoolID == Integer.valueOf(UtilTool.utilproperty.getProperty("ELITE_SCHOOL_ID"))) {
        isElite = true;
    }
    List<SubjectInfo> subList = (List<SubjectInfo>)request.getAttribute("subList");
%>

 <%
jcore.jsonrpc.common.JsonRpcRegister.registerObject(request,"PageUtilTool",com.school.util.PageUtil.PageUtilTool.class);
%>
<head>
	<script type="text/javascript" src="<%=basePath %>js/class.js"></script>

	<script type="text/javascript">
	var isSelect=true;	 //查询功能权限
    var isAdd=true;	 //添加功能权限
    var isDelete=true;	 //删除功能权限
    var isUpdate=true;	 //修改功能权限
    //菁英学校ID
    var isElite="<%=isElite%>";
    var eliteSchoolId='<%=UtilTool.utilproperty.getProperty("ELITE_SCHOOL_ID")%>';
    var currentSchoolId=<%=dcSchoolID%>;
    var subList = <%=net.sf.json.JSONArray.fromObject(subList).toString()%>;
    /*********功能权限控制**********/
    $(function(){
    	$("#add_btn").hide();
    	$("#sel_btn").hide();
    	//当前年份
    	$("#sel_year").val("${currentYear}");
    	$("#add_sel_year").val("${currentYear}");
    	if(isSelect){ $("#sel_btn").show();}
    	if(isAdd){ $("#add_btn").show();}

        $("#add_sel_grade option:first").attr("selected",true);
        document.getElementById('add_showclsgrade').innerHTML=$("#add_sel_grade").val();
    }); 
    
	var p1;
	$(function(){
		//翻页控件
			if(isSelect){
				p1=new PageControl({
					post_url:'cls?m=ajaxlist',
					page_id:'page1',
					page_control_name:"p1",		//分页变量空间的对象名称
					post_form:document.page1form,		//form
					http_free_operate_handler:beforajaxList,
					gender_address_id:'page1address',		//显示的区域						
					http_operate_handler:afterajaxList,	//执行成功后返回方法
					return_type:'json',								//放回的值类型
					page_no:1,					//当前的页数
					page_size:10,				//当前页面显示的数量
					rectotal:0,				//一共多少
					pagetotal:1,
					operate_id:"maintbl"
				});
				//setTimeout(function(){pageGo('p1');},1000);
			}
	});
	/**
	*添加，班级类型进行改变时触发
	*/
	function addPatternChange(obj,dvid){
		if(obj.value.Trim()=="分层班"){
			$("#"+dvid).show();
		}else
			$("#"+dvid).hide();
	}

	$(function() {
        if(isElite=="true") {
            $("#add_sel_pattern").attr("disabled", true);
            $("#activity_type_row").show();
            $("#term_id_row").show();
            $("#is_elite").val("1");
            $("#sel_year_col").hide();
            $("#class_attr").hide();
            $("#class_type").hide();
            $("#sel_type_col").hide();
            $("#sel_pattern_col").hide();
            $("#importStd").hide();
            $("#autoLvUp").hide();
            var options = document.getElementById("add_sel_pattern").options;
            options[1].selected=true;
            $("#tr_add_subject").show();
        }
    });
</script>
</head>

<body >
   <%@include file="/util/head.jsp" %>
 <%@include file="/util/nav-base.jsp" %>
<div id="nav">
    <ul>
      <li><a href="user?m=list">用户管理</a></li>
            <li class="crumb"><a href="cls?m=list">组织管理</a></li>
        <li ><a href="sysm?m=logoconfig">系统设置</a></li>
    </ul>
</div>
  
<div class="content">
 <div class="contentT">
    <div class="contentR">
    <div class="jcpt_zzgl">
      <table border="0" cellspacing="0" cellpadding="0" class="public_tab1 public_input">
    <tr>
    <th>
        <span id="sel_year_col">
      学年：
      <select id="sel_year" name="sel_year">
       <c:if test="${!empty classyearList}">
				<c:forEach items="${classyearList}" var="y">
					<option value="${y.classyearvalue }">${y.classyearname }</option> 
				</c:forEach>	
			</c:if>
      </select></span>
        <%if(isElite) {%>
        开班期次：
        <select id="sel_term" name="sel_term">
            <c:if test="${!empty clsTmList}">
                <c:forEach items="${clsTmList}" var="clsT">
                    <option value="${clsT.termid}">第${clsT.termid}期</option>
                </c:forEach>
            </c:if>
        </select>
        <%}%>
        &nbsp;
      年级：<select name="sel_grade" id="sel_grade">
	      	<option value="">全部</option> 
	     	 <c:if test="${!empty gradeList}">
				<c:forEach items="${gradeList}" var="g" varStatus="idx">
					<option value="${g.gradevalue }">${g.gradename }</option>				
				</c:forEach>		
			</c:if> 
		</select>&nbsp;
        <span id="sel_type_col">
		文/理：<select name="sel_type" id="sel_type">
				<option value="">全部</option>
				<option value="NORMAL">普通</option>
				<option value="W">文科</option>
				<option value="L">理科</option>
			   </select>
            </span>
            &nbsp;
        <span id="sel_pattern_col">
		类别：<select name="sel_pattern" id="sel_pattern">
				<option value="">全部</option>
				<option value="行政班">行政班</option>
				<option value="分层班">分层班</option>
			  </select>
            </span>
        <%if(isElite) {%>
        学科：
        <select name="query_subject" id="query_subject" class="w80">
            <option value="0">全部</option>
            <c:if test="${!empty subList}">
                <c:forEach items="${subList}" var="sb">
                    <option value="${sb.subjectid }">${sb.subjectname }</option>
                </c:forEach>
            </c:if>
        </select>

        &nbsp;
        类型：<select class="w100" name="sel_activity_type" id="sel_activity_type">
        <option value="">全部</option>
        <option value="1">菁英班</option>
        <option value="2">普通班</option>
    </select>
        <%}%>
      </th>
    </tr> 
      <tr>
        <td>
        <input type="text" name="sel_clsname" placeholder="按班级名称进行模糊查询" class="w200" id="sel_clsname"/>
	    	&nbsp;<a href="javascript:pageGo('p1')"  class="an_search" title="搜索"></a>
            <input type="hidden" id="dcSchoolID" name="dcSchoolID" value="<%=dcSchoolID%>">
        </td>
      </tr>
      </table>
    </div>
    <h6></h6>
    <p class="t_c"><a href="javascript:checkClass('addClass')" class="an_big">新建班级</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <a id="importStd" href="javascript:showModel('loadExcel')" class="an_big">导入学生</a>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <c:if test="${allowAutoLevel==1}">
   	 <a id="autoLvUp" href="javascript:checkClass('levelup') "  class="an_big">自动升级</a></p>
    </c:if>
     <c:if test="${allowAutoLevel!=1}">
   	 <a id="autoLvUp" href="javascript:showModel('dv_autoShenj')  "  class="an_gray">自动升级</a></p>
    </c:if>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
       <colgroup span="6" class="w120"></colgroup>
        <tbody id="maintbl">
        </tbody>
      
     </table>
      <form action="/grade/ajaxlist" id="page1form" name="page1form" method="post">
  			<div  id="page1address" class="nextpage" align="center" style="display:none"></div>
  			<!-- class="nextpage" -->
  		</form>
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

<div id="loading" style='display:none;position: absolute;z-index:1005;background-color: white;'><img src="images/loading.gif"/>&nbsp;&nbsp;正在操作...请耐心等候!</div>
  

<div class="public_windows"  id="loadExcel" style="display:none;">
<h3><a href="javascript:closeModel('loadExcel');" title="关闭"></a>导入学生</h3>
  <div class="jcpt_zzgl_float">
      <p><input type="file" name="upload" id="upload" class="w220">
      &nbsp;<a  id="aload" href="javascript:uploadStudentExcel();" class="an_public2">上传</a>&nbsp;&nbsp;<a href="javascript:explortClassTemplate()" class="font-blue">下载学生名册导入模版</a></p>
     <p class="p_l_20 p_t_10" style="display:none" id="showLoadHtml">
      <p class="p_t_10"><strong class="font-blue1">注意：</strong><br/>
        1．需要先建好目标班级，再导入班级学生。<br/>
        2．会自动清空目标班级已有学生名册，再导入对应学生。<br/>
        3．若是新用户，导入并新建用户信息：用户名为学号，密码默认：111111。<br/>
      	4．可以批量导入多个班级名册：在模版中建多个工作表，一个工作表填写一个班级<br/>&nbsp;&nbsp;&nbsp;的学生信息。 </p>

  </div>
</div>

<div class="public_windows" id="dv_autoShenj"  style="display:none; ">
  <h3><a href="javascript:closeModel('dv_autoShenj');" title="关闭"></a>自动升级</h3>
  <div class="jcpt_zzgl_float">
    <p class="p_t_10"><strong class="font-blue1">旧学年的行政班自动升到新学年：</strong><br>
     &nbsp;&nbsp;&nbsp;&nbsp;1．启用条件：旧学年有班级，新学年没有班级。<br>
     &nbsp;&nbsp;&nbsp;&nbsp;2．系统自动创建班级，自动升级学生名册。无需手动建立，手动导入。<br>
     &nbsp;&nbsp;&nbsp;&nbsp;3．只自动升级行政班，分层班不升级。<br>
     &nbsp;&nbsp;&nbsp;&nbsp;4．毕业班级不会升级：例如高三的班级、初三的班级。    </p>
     <c:if test="${allowAutoLevel==1}">
   	 <p class="p_t_10 t_c" id="p_auto_uplevel"><a href="javascript:;" onclick="autoLeaveUp(sel_year.value)" class="an_public1">确定</a></p>
    </c:if>
    <c:if test="${allowAutoLevel!=1}">
    	 <p class="p_t_10 t_c" style="color: red">检测到当前选择的学年已有班级，可手动删除这些班级再自动升级!</p>
    </c:if>
  </div>
</div>




<div class="public_windows" id="div_add" style="display:none;">
<h3><a href="javascript:closeModel('div_add');" title="关闭"></a>新建班级</h3>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
  <col class="w100"/>
  <col class="w240"/>
  <tr>
    <th>* 学&nbsp;&nbsp;&nbsp;&nbsp;年：</th>
    <td><select name="add_sel_year" id="add_sel_year" class="w160">
        <c:if test="${!empty classyearList}">
			<c:forEach items="${classyearList}" var="y">
				<option value="${y.classyearvalue }">${y.classyearname }</option> 
			</c:forEach>	
		</c:if>
      </select></td>
  </tr>
    <tr id="term_id_row" style="display:none">
        <th>* 活动期次：</th>
        <td>第&nbsp;<input id="term_id" type="number" class="w80" min="1" value="1"/>&nbsp;期</td>
    </tr>

    <tr id="activity_type_row" style="display:none">
        <th>* 活动类型：</th>
        <td>
            <input type="hidden" id="is_elite" name="is_elite" value="0">
            <select class="w160" name="activity_type" id="activity_type">
                <option value="1" selected="selected">菁英班</option>
                <option value="2">普通班</option>
            </select></td>
    </tr>

    <tr>
    <th>* 年&nbsp;&nbsp;&nbsp;&nbsp;级：</th>
    <td><select name="add_sel_grade"  class="w160" id="add_sel_grade" onchange="document.getElementById('add_showclsgrade').innerHTML=this.value;">
      		<c:if test="${!empty gradeList}">
				<c:forEach items="${gradeList}" var="g" varStatus="idx">
					<option value="${g.gradevalue }">${g.gradename }</option>
				</c:forEach>
			</c:if>
      	</select></td>
  </tr>
  <tr>
    <th>* 班级名称：</th>
    <td><span id="add_showclsgrade"></span> <input id="add_sel_clsname" type="text" class="w80" />&nbsp;班</td>
  </tr>
  <tr id="class_attr">
    <th>* 文 /  理：</th>
    <td> <select class="w160" name="add_sel_type" id="add_sel_type">
				<option value="NORMAL">普通</option>
				<option value="W">文科</option>
				<option value="L">理科</option>
			</select></td>
  </tr>
  <tr id="class_type">
    <th>* 类&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
    <td><select name="add_sel_pattern" id="add_sel_pattern" class="w160" onchange="addPatternChange(this,'tr_add_subject')">
      	<option value="行政班" selected="selected">行政班</option>
		<option value="分层班">分层班</option>
    </select></td>
  </tr>
  <tr id="tr_add_subject" style="display:none">
    <th>* 所属学科：</th>
    <td><select name="sel_subject" id="sel_subject" class="w160">
						<c:if test="${!empty subList}">
							<c:forEach items="${subList}" var="sb">
								<option value="${sb.subjectid }">${sb.subjectname }</option>
							</c:forEach>
						</c:if>
					</select></td>
  </tr>
  <tr>
    <th>&nbsp;</th>
    <td><a href="javascript:doAdd()" class="an_public1">创&nbsp;建</a></td>
  </tr>
</table>
</div>
<div style="display:none">
  <form id="explortClsTemplateFRM" name="explortClsTemplate" method="post" target="_blank">
  	<input name="classname" value="" id="classname"/>
  	<input name="year" value="" id="year"/>
  	<input name="classgrade" value="" id="classgrade"/>
  	<input name="pattern" value="" id="pattern"/>
  	<input name="dtype" value="" id="type"/>
  </form>
  </div>
<%@include file="/util/foot.jsp" %> 
</body>

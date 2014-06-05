<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-hdxt.jsp"%>

  <head>
  <script type="text/javascript" src="<%=basePath %>js/activity/activity_add.js"></script>
    <base href="<%=basePath%>">
    
    <title>My JSP 'activity_add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
			var intype="${param.intype}";	
			
			function issign_click(ck){
				if(ck.checked==true){
					$("#addUser").hide();
					$("#user_span").html('');
				}else{
					$("#addUser").show();
				}
			}		
		</script>
  </head>
  
  <body><%@include file="/util/head.jsp"  %>  
  <div class="jxpt_layout"><%@include file="/util/nav.jsp" %>
	<p class="jxpt_icon04"><span><a href="activity?m=list">返回</a></span>新建活动</p>
	<div class="p_20">
	<!-- 记录场地选择 -->
	<span id="save_address_span" style="display: none;"></span>
	<!-- 记录选择邀请人 -->
	<span id="save_user_span" style="display: none;"></span>
  <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_a zt14 ">
   <col class="w100"/>
    <col class="w760"/>
  <input name="ref" id = "ref" type="hidden" value="${activity.ref }"/>
  <col class="W81"/>
  <col class="W590"/>
  <tr>
    <th>名&nbsp;&nbsp;&nbsp;&nbsp;称：</th>
    <td><input name="atName" value="${activity.atname }"  maxlength="15"  id="atName" type="text" class="class="public_input w250"" />&nbsp;&nbsp;<span  style="color: gray;">请限制在15字以内</span></td>
    </tr>
  <tr>
    <th valign="top">地&nbsp;&nbsp;&nbsp;&nbsp;点：</th>
    <td>
	    <span><a href="javascript:showSiteAddressModel('select_siteAddress',false)" class="an_blue_small">添&nbsp;加</a></span>
	    <div id="address_span">
	    	<c:if test="${!empty activity.siteinfo}">
	    		<c:forEach  var="sa" items="${activity.siteinfo}">
	    			<b id="selSiteAddress_${sa.ref }">${sa.sitename }[<a href="javascript:delDemo(${sa.ref },'selSiteAddress_')" class="F_blue">X</a>]<input type="hidden" name="selSiteId" value="${sa.ref }"/></b>
	    		</c:forEach>
	    	</c:if>
	    </div>
    </td>
    </tr>
  <tr>
    <th>开始时间：</th>
    <td><input value="${activity.begintimestring }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="beginTime"  name="beginTime" type="text" class="public_input w250" /></td>
    </tr>
  <tr>
    <th>结束时间：</th>
    <td><input value="${activity.endtimestring }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="endTime" id="endTime"  type="text" class="public_input w250"  /></td>
    </tr>
    <tr>
    <th>会场需求：</th>
    <td> <textarea onkeyup="ckLength(this,300)" name="audiovisual" id="audiovisual" class="public_input h90 w450 m_t_5">${activity.audiovisual }</textarea>
    <br/><span style="color: gray" >例如：茶水，电教器材 等等!</span>
    </td>
    </tr>
  <tr>  
    <th>估计人数：</th>
    <td><input value="${activity.estimationnum }" maxlength="5" onkeyup="this.value=this.value.replace(/\D/g,'')" name="estimationNum"  id="estimationNum" type="text" class="public_input w100" /></td>
    </tr>
  <tr>
    <th valign="top">参与人员：</th>
    <td> 
    <span id="addUser" style="display:none"><a href="javascript:add_addUser()" class="an_blue_small">添&nbsp;加</a>
    </span>
    	<input ${activity.issign==0?'checked':'' } type="checkbox" onclick="issign_click(this)" name="issign" id="issign" value="0"/>不选择人员    
    	<div id="user_span">
		    <c:if test="${activity.issign==1}">   	
		    	 	<c:if test="${!empty activity.activityuserinfo}">
			    		<c:forEach items="${activity.activityuserinfo}" var="au">
			    			<b id="selUserId_${au.userid }">${au.realname }[<a href="javascript:delDemo('${au.userid }','selUserId_')" class="F_blue">X</a>]<input type="hidden" name="selUserId" value="${au.userid }"/></b>
			    		</c:forEach>
			    	</c:if>    	
		    </c:if>
    	</div>
    </td>
    </tr>
  <tr>
    <th>活动介绍：</th>
    <td><span class="Pt10">
      <textarea onkeyup="ckLength(this,1300)" name="content" id="content" class="public_input h90 w450 m_t_5">${activity.content }</textarea>
    </span>
    	<div style="color: gray;">请限制在1300字以内!</div> 
    </td>
    </tr>
     <tr>
      <th>&nbsp;</th>
      <td><a href="javascript:doUpd()"  class="an_blue">确&nbsp;定</a><a href="javascript:clearForm(document.addform)" class="an_blue">重&nbsp;置</a></td>
    </tr>
</table>
    </div>
   </div><%@include file="/util/foot.jsp"  %>
  
   		<div id = "select_siteAddress" style="display:none" class="public_windows_layout w710 h490">
  <p class="f_right"><a href="javascript:selectedSite('select_siteAddress')"  title="关闭"><span class="public_windows_close"></span></a></p>
  <p class="t_c font14 font_strong p_t_10">场地情况</p>
  <table id = "mainTbl" border="0" cellspacing="0" cellpadding="0" class="m_a_10 public_tab3">
   <col class="w60" />
    <col class="w160" />
    <col class="w70" />
    <col class="w160" />
    <col class="w220" />
   			<tr>
				<th class="noborder"><input type='checkbox' name="ckAll"  id="ckAll" onclick="ckAllCheckChange(this,'ck_child')"/></th>
				<th>场地名称</th>
				<th>容纳人数</th>
				<th>最近活动</th>									
			</tr>
			<c:if test="${!empty siteList}">
				<c:forEach var="s" items="${siteList}">
					<tr>
						<td class="noborder">
							<input type="checkbox" value="${s[0] }þ|ñ${s[1]}þ|ñ${s[2]}" name="ck_child" id="ck_site_${s[0]}"/>
						</td>
						<td>
							<script type="text/javascript">
								var name = "${s[1]}";
								if(name.Trim().length>14){
									name=name.Trim().substring(0,14)+"...";
								}
								document.write(name);
							</script>
						</td>
						<td>
							${s[2]}
						</td>
						<td>
							<c:if test="${empty s[3]||empty s[4]}">
								暂无
							</c:if>
							<c:if test="${!empty s[3]&&!empty s[4]}">
								${s[3] }--${s[4] }
							</c:if> 
						</td> 
						 
					</tr>
				</c:forEach>
			</c:if>
   		</table>
   </div>
  </body>


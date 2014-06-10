<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp" %> 
<%

	UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
	String username = user.getUsername();
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'setupwizard.jsp' starting page</title>
    <script type="text/javascript" src="<%=basePath %>js/Popup.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/setupwizard.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath %>js/common/ajaxfileupload.js"></script>
<script src="<%=basePath %>js/common/jquery.form.pack.2.24.js"></script>
<script src="<%=basePath %>js/common/jquery.imgareaselect.js"></script>
	<link  rel="stylesheet" type="text/css" href="css/imgareaselect-default.css"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	//设置logo设置用的三个变量
		var imgwidth = 0;
		var imgheight = 0;
		var imgareaselectObj;
	
	</script>
  </head>
  
  <body>
  <div style="height: 20px"></div>
    	<div id="mainDiv">
    		<!--<div id="div1" style="display:blok;">&nbsp; 
    		<input type="button" value="点击出出窗口" onclick="ShowIframe()"/>
    			<span style="font-size: 24;font-weight: bold">修改密码</span></br>
    			当前用户名：<%=username %></br>
    			请输入密码：<input id="newPass" name="newPass" type="password" value=""/></br>
    			再次&nbsp;输入：<input id="morePass" name="morePass" type="password" value=""/></br>
    			<input type="button" value="下一步" onclick="next('div1','div2')"/>
    		</div>
    		
    		<div id="div2" style="display:none;">
    		<span style="font-size: 24;font-weight: bold">学科设置</span></br>
    			<ul id = "subject">
    				<c:if test="${!empty subList}">
    					<c:forEach var="sub" items="${subList}" varStatus="idx">
    						<c:if test="${sub.subjecttype==1}">
    							<li id="${idx.index+1}">${sub.subjectname }</li>
    						</c:if>
    						<c:if test="${sub.subjecttype==2}">
    							<li onclick="clk('${idx.index+1}','${sub.subjectname }','${sub.subjectid }')" id="li${idx.index+1}">${sub.subjectname }</li>
    						</c:if>
    					</c:forEach>
    				</c:if>
    			</ul><a href="javascript:addli();">添加学科</a>
    			<input type="button" value="下一步" onclick="next('div2','div3')"/>
    		</div>
    		
    		<div id="div3" style="display:none;">
    			<span style="font-size: 24;font-weight: bold">学年设置</span></br>
    			<table>
    				<tr>
	    				<td>
	    					学年：
	    				</td>
	    				<td>
	    					<span></span>
	    				</td>
    				</tr>
    				<tr>
	    				<td>
	    					第一学期：
	    				</td>
	    				<td>
	    					<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="beginTime"  name="beginTime" type="text" class="public_input w250" />~<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="endTime" id="endTime"  type="text" class="public_input w250" />
	    				</td>
    				</tr>
    				<tr>
	    				<td>
	    					第二学期：
	    				</td>
	    				<td>
	    					<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="beginTime"  name="beginTime" type="text" class="public_input w250" />~<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="endTime" id="endTime"  type="text" class="public_input w250" />
	    				</td>
    				</tr>
    				<tr>
	    				<td colspan="2">
	    					<input type="button" value="下一步" onclick="next('div3','div4')"/>
	    				</td>
    				</tr>
    			</table>
    		</div>
    		
    		<div id="div4">
    			
    		</div>
    		
    		
    		--><div class="jcpt_initial" id="div1">
				  <div class="next"><a href="javascript:submitUpdatePwd('div1','div2')" title="下一步"></a></div>
				  <div class="jcpt_initialT"></div>
				  <div class="jcpt_initialC public_input">
				  <h3>*修改密码</h3>
				  <p><strong>用&nbsp;户&nbsp;名：</strong> <%=username %></p>
				  <p><strong>重设密码：</strong>
				    <input class="w160" id="newPass" name="newPass" type="password" value=""/>
				  </p>
				  <div class="tishi" id="passDiv1" style="display: none">密码长度不能小于6个字符</div>
				  <p><strong>再次确认：</strong>
				    <input id="morePass" name="morePass" type="password" value="" class="w160"/>
				  </p>
				  <div class="tishi" id="passDiv2" style="display: none">两次输入的密码不一致</div>
				  </div>
				  <div class="jcpt_initialB"></div>
				</div>
				
				<div class="jcpt_initial" style="display:none" id="div2">
				  <div class="next"><a href="javascript:submitUpdateTerm('div2','div3')" title="下一步"></a></div>
				  <div class="jcpt_initialT"></div>
				  <div class="jcpt_initialC public_input">
				  <h3>*学年设置</h3>
				  <p><strong>学&nbsp;&nbsp;&nbsp;&nbsp;年：</strong> ${termList.CLASS_YEAR_NAME }</p>
				  <p><strong>第一学期：</strong>
				     <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" readonly="readonly"   id="beginTime1"  name="beginTime1" type="text" value="${termList.BTIME1 }" />
				     -<input type="hidden" id="termref1" value="${termList.REF1 }"/>
				     <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" readonly="readonly"   id="endTime1" id="endTime1"  type="text" class="w80" value="${termList.ETIME1 }"/>
				  </p>
				  <div class="tishi" id="termDiv1" style="display: none">时间不在当前学年</div>
				  <p><strong>第二学期：</strong>
				     <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" readonly="readonly"   id="beginTime2"  name="beginTime2" type="text"  value="${termList.BTIME2 }"/>
				     -<input type="hidden" id="termref2" value="${termList.REF2 }"/>
				     <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" readonly="readonly"   id="endTime2" id="endTime2"  type="text" class="w80" value="${termList.ETIME2 }"/>
				  </p>
				  <div class="tishi" id="termDiv2" style="display: none">时间不能早于上一学期的时间<br>
				  </div>
				  </div>
				  <div class="jcpt_initialB"></div>
				</div>
				
				<div class="jcpt_initial" style="display:none" id="div3">
				  <div class="next"><a href="javascript:submitSubject('div3','div4')" title="下一步"></a></div>
				  <div class="jcpt_initialT"></div>
				  <div class="jcpt_initialC public_input">
				  <h3>学科设置</h3>
				    <ul id="subject">
				      
				      
				      <c:if test="${!empty subList}">
    					<c:forEach var="sub" items="${subList}" varStatus="idx">
    						<c:if test="${sub.subjecttype==1}">
    							<li id="${idx.index+1}"><input name="textfield12" type="text" disabled value="${sub.subjectname }" /></li>
    						</c:if>
    						<c:if test="${sub.subjecttype==2}">
    							<li id="li${idx.index+1}"><input name="textfield12" type="text" value="${sub.subjectname }" /><a href="subject?m=del&subjectid=${sub.subjectid };javascript:delSubject('${sub.subjectid }')"><span title="删除"></span></a></li>
    						</c:if>
    					</c:forEach>
    				</c:if>
				    </ul>
				    <div class="t_c"><a href="javascript:addli();"  class="an_blue_small">新建学科</a></div>
				  </div>
				  <div class="jcpt_initialB"></div>
				</div>
				
				<div class="jcpt_initial" style="display:none" id="div4">
				  <div class="next"><a href="javascript:next('div4','div5')" title="下一步"></a></div>
				  <div class="jcpt_initialT"></div>
				  <div class="jcpt_initialC public_input">
				  <h3>设置本校数字校园logo</h3>
				     <div class="jcpt_initial_logo" >
				      <p><input type="file" id="upload" name="upload" class="w320">&nbsp;<a href="javascript:uploadImg();"  class="an_lightblue">上传</a></p>
					      <div id="cuthear_div" style="display:none">
						      
						      <div class="top"  width="320" height="96" id="myPreview" ><img id="myimage"  ></div>
						      
						     
						     <div class="bottom"   id="preview" style="width: 320px; height: 96px; overflow: hidden;">
					        	<img id="previewimg" name="myimage"  width="320" height="96">
					          </div>
					          <p class="t_c"><a href="javascript:subheadcut();"  class="an_blue_small">保 存</a></p>
					     </div>
				     </div>
				  </div>
				  <div class="jcpt_initialB"></div>
				</div>
				
				<div class="jcpt_initial" style="display:none" id="div5">
				  <div class="jcpt_initialT"></div>
				  <div class="jcpt_initialC public_input">
				  <h3 class="p_t_30">恭喜您已完成数字化校园平台的初始化设置！</h3>
				  <h3 class="m_t_15">开始使用吧！</h3>
				  <div class="t_c p_t_30"><a href="javascript:shutdownpop()" class="jcpt_initial_an">进入数字化校园平台</a></div>
				</div>
				  <div class="jcpt_initialB"></div>
				</div>
    	</div>
  </body>
</html>

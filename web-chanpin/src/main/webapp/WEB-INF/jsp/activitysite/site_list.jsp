<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-hdxt.jsp"%>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'site_list.jsp' starting page</title>
    
    <script type="text/javascript" src="<%=basePath %>js/activity/site.js"></script>
	<script type="text/javascript">
			var p1;
			$(function(){
					p1 = new PageControl({
						post_url:'activitysite?m=ajaxlist',
						page_id:'page1',
						page_control_name:'p1',
						post_form:document.page1form,
						gender_address_id:'page1address',
						http_free_operate_handler:validatePagePoatParam,
						http_operate_handler:siteListReturn,
						return_type:'json',
						page_no:1, 
						page_size:20,
						rectotal:0,
						pagetotal:1,
						operate_id:"mainTbl"
					});

					pageGo("p1");
			});
</script>
  </head>
  
  <body>
    	<%--<div style="width:800px;height:1000px">
    		<table>
    			<tr>
    				<td>
    					场地名称：<input name="sitename" id="sitename" type="text">&nbsp;&nbsp;&nbsp;&nbsp;
    					容纳人数：<input name="sitecontain" id="sitecontain" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
    					----<input name = "sitecontain2" id="sitecontain2" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
        					<br/><button onclick="pageGo(p1)">查询</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        					<button onclick="showAddDiv('aSiteDiv')">添加场地</button>
    				</td>
                </tr>
                <tr>
                	<td>
                		<b>场地列表</b>
                	</td>
                </tr>
                <tr>
                	<td>
                		<table border="1" cellpadding="0" cellspacing="0"  id="mainTbl">
	</table>
	 <p >
				    <form id="page1form" name="page1form" method="post">
				    	<div align="right" id="page1address"></div>
				    </form>
</p>
                	</td>
                </tr>
    		</table>
    	</div>
    	
    	--%>
    	    	 <%@include file="/util/head.jsp"  %>   
	  <div class="jxpt_layout">
	   <%@include file="/util/nav.jsp" %>
  <p class="jxpt_icon01"><span><a href="activity?m=list">返回</a></span>场地管理</p>
  <p class="p_20"><span class="font14">场地名称：</span>
    <input name="sitename" id="sitename" type="text" class="public_input w250" />
  <span class="font14 p_l_20">容纳人数：</span>
  <input name="sitecontain" id="sitecontain" type="text" class="public_input w80" />
  &mdash;&mdash;
  <input name="sitecontain2" id="sitecontain2" type="text" class="public_input w80" />
  &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:pageGo(p1)" class="an_blue_small">查询</a><a href="javascript:showAddDiv('aSiteDiv')" class="an_blue_small">添加场地</a></p>
  <table  border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_a">
    <colgroup span="2" class="w250"></colgroup>
     <colgroup class="w80"></colgroup>
    <colgroup span="2" class="w140"></colgroup>
    <colgroup class="w100"></colgroup>
    <tbody id="mainTbl"></tbody>
  </table>
  <p >
				    <form id="page1form" name="page1form" method="post">
				    	<div align="right" id="page1address"></div>
				    </form>
</p>
 </div>
 <%@include file="/util/foot.jsp"  %>
    	<div id="aSiteDiv" style="display:none" class="public_windows_layout w450 h320">
    	<p class="f_right"><a href="javascript:closeModel('aSiteDiv','hide')" title="关闭"><span class="public_windows_close"></span></a></p>
  		<p class="t_c font14 font_strong p_t_10">添加场地信息</p>
    	 <form action="" name="addform" id="addform" method="post">
    		<table class="public_tab1 m_a_10">
    		 <col class="w90"/>
    <col class="w310"/>
    			<tr>
	    			<th>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</th>
	    			<td>
	    				<input name="asitename" id="asitename" type="text" />
	    			</td>
    			</tr>
    			 <tr>
            <th>位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置：</th>
            <td><input  name="siteaddress"  id="siteaddress" type="text"></td>
          </tr>
          <tr>
            <th>容&nbsp;纳&nbsp;人&nbsp;数：</th>
            <td><input name="asitecontain"  maxlength="5"  id="asitecontain" type="text"  
            onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
          </tr>
           <tr>
            <th>基本设施描述：</th>
            <td>
             	 <textarea name="baseinfo" id="baseinfo" ></textarea> 
              </td>
          </tr></table>
    		<p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doSubmitAdd(document.addform)">确定</a><a class="an_gray"  href="javascript:clearForm(document.addform)">重置</a></p>
    		</form>
    	</div>
    	<div id="uSiteDiv" style="display:none" class="public_windows_layout w450 h320">
    	 <p class="f_right"><a href="javascript:closeModel('uSiteDiv','hide')"  title="关闭"><span class="public_windows_close"></span></a></p>
  		<p class="t_c font14 font_strong p_t_10">修改场地信息</p>
    	 <form action="" name="updateform" id="updateform" method="post">
    		<input type="hidden" name="ref" id="ref" value=""/>
    		<table class="public_tab1 m_a_10">
    		 <col class="w90"/>
    <col class="w310"/>
    			<tr>
	    			<th>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</th>
	    			<td>
	    				<input name="usitename" id="usitename" type="text" />
	    			</td>
    			</tr>
    			 <tr>
            <th>位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置：</th>
            <td><input  name="uiteaddress"  id="usiteaddress" type="text"></td>
          </tr>
          <tr>
            <th>容&nbsp;纳&nbsp;人&nbsp;数：</th>
            <td><input name="usitecontain"  maxlength="5"  id="usitecontain" type="text"  
            onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
          </tr>
           <tr>
            <th>基本设施描述：</th>
            <td>
             	 <textarea name="ubaseinfo" id="ubaseinfo" ></textarea> 
              </td>
          </tr></table>
    		<p class="p_t_10 t_c"><a class="an_gray"  href="javascript:doSubmitUpdate(document.updateform)">确定</a><a class="an_gray"  href="javascript:clearForm(document.updateform)">重置</a></p>
    		</form>
    	</div>
    	

  </body>

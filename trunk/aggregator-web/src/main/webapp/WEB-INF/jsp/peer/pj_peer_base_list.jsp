<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-thpj.jsp"%>

<html>
  <script type="text/javascript" src="<%=basePath %>js/peer/pj_peer_base.js"></script>
  <script>
  			var isupddept = 1;
		  var p1;
			$(function(){
				p1 = new PageControl({
					post_url:'peerbase?m=ajaxlist', 
					page_id:'page1',
					page_control_name:'p1',
					post_form:document.listform,
					gender_address_id:'listaddress',
					http_operate_handler:peerBaseListReturn,
					new_page_html_mode:true,
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
  <body>
		<%@include file="/util/head.jsp"  %>		 
				 
	<div class="thpj_content">
	 <%@include file="/util/nav.jsp" %>
  <div class="thpj_contentR">
  <form id="mainForm" name="mainForm" action="" method="">
  	<input type="hidden" id="ref" name="ref"/>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 m_b_25 zt14">
      <col class="w100"/>
      <col class="w650"/>
      <tr>
        <th>学&nbsp;&nbsp;&nbsp;&nbsp;年：</th>
        <td>
        <select id="year" name="year">
	    				<option value="-1">
	    					请选择
	    				</option>
	    				<c:if test="${!empty term}">
	    					<c:forEach items="${term}" var="itm">
	    						<option value="${ itm.year}年${itm.termname }">${ itm.year}年${itm.termname }</option>
	    					</c:forEach>
	    				</c:if>
	    			</select>
        </td>
      </tr>
      <tr>
        <th>时&nbsp;&nbsp;&nbsp;&nbsp;间：</th>
        <td>
          <input type="text" class="public_input w160" id="btime" name="btime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})"  readonly="readonly" style="cursor:text" />
	    			 &mdash;&mdash;
	    			<input type="text" class="public_input w160" id="etime" name="etime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})"  readonly="readonly" style="cursor:text"/>
          </td>
      </tr>
      <tr>
        <th>评价主题：</th>
        <td><input id="name" name="name" type="text" class="public_input w370" /></td>
      </tr>
      <tr  id="isupd_tr" style="display:none">
        <th>修改参评部门：</th>
        <td><input type="checkbox" id="is_upd"  name="is_upd" onclick="isupd(this)"/>是否修改 &nbsp;&nbsp;<span class="font-red">修改后，将重置您对部门参评人员做的问卷调整操作</span></td>
      </tr>
      <tr>
        <th>参评部门：</th>
        <td><c:if test="${!empty dept}">
	    				<c:forEach items="${dept}" var="itm">
	    					<input type="checkbox" id="dept_ck" name="dept_ck" value="${itm.deptid }"/>${itm.deptname }&nbsp;&nbsp;&nbsp;&nbsp;
	    				</c:forEach>
	    			</c:if><span class="font-gray1">（需要判断已选的部门完成了分组）</span>
          
          </td>
      </tr>
      <tr>
        <th>评价描述：</th>
        <td><textarea id="remark" name="remark" class="public_input h90 w450 m_t_5"></textarea></td>
      </tr>
      <tr>
        <th>&nbsp;</th>
        <td id="dotd">
        	<a href="javascript:doSubmit()" class="an_blue">添加</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:clearForm(document.mainForm)" class="an_blue">重置</a>
        </td>
      </tr>
    </table>
    </form>
    <p class="public_title">时间列表</p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
      <col class="w160" />
      <col class="w250" />
      <col class="w160" />
      <col class="w160" />
      <col class="w150" />
      <tbody id="mainTbl">
	  </tbody>   
	</table>
	 <div class="t_r p_10_20">	
	    <form id="listform" name="listform" method="post">
	    	<div align="right" id="listaddress"></div>
	    </form>
	 </div> 
</div>

<div class="thpj_contentL">
  <ul>
    <li class="crumb"><a href="peerbase?m=list">时间管理</a></li>
    <li><a href="peeritem?m=list">编辑评价</a></li>
    <li><a href="1">问卷设置</a></li>
    <li><a href="peerlog?m=todeptlogstat">评价结果</a></li>
  </ul>
</div>
<div class="clear"></div>
</div>
<%@include file="/util/foot.jsp"  %> 
  </body>
</html>

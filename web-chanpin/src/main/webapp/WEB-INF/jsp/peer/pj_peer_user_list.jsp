<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-thpj.jsp"%>

<html>
  <head>
    <script type="text/javascript" src="<%=basePath %>js/peer/pj_peer_user.js"></script>
    <script type="text/javascript">
<%--    var p1;--%>
<%--	$(function(){--%>
<%--		p1 = new PageControl({--%>
<%--			post_url:'peeruser?m=ajaxlist', --%>
<%--			page_id:'page1',--%>
<%--			data:{deptid:1,peerbaseid:${peerbaseid }},--%>
<%--			page_control_name:'p1',--%>
<%--			post_form:document.listform,--%>
<%--			gender_address_id:'listaddress',--%>
<%--			http_operate_handler:peerUserListReturn,--%>
<%--			new_page_html_mode:true,--%>
<%--			return_type:'json',--%>
<%--			page_no:1, --%>
<%--			page_size:20,--%>
<%--			rectotal:0,--%>
<%--			pagetotal:1,--%>
<%--			operate_id:"mainTbl"--%>
<%--		});--%>
<%----%>
<%--		pageGo("p1");--%>
<%--	});--%>
var peerbaseid = "${peerbaseid}";
$.ajax({
	url:'peeruser?m=ajaxlist',
	dataType:'json',
	type:'post',
	data:{deptid:1,
		  peerbaseid:peerbaseid
    },
	cache: false,
	error:function(){
		alert('异常错误!系统未响应!');
	},success:function(rps){
		//alert(rps.msg);
		peerUserListReturn(rps);
	}
});
    </script>
  </head>
  
  <body>
  	<input type="hidden" id="peerid" value="${peerbaseid }"/>
    	
  
  
  
  
  <div class="jxpt_layout">
  <p class="jxpt_icon06">调整部门问卷</p>
  <div class="thpj_wjszL">
     <p class="t_c p_tb_10"><a href="javascript: d.openAll();"><img src="images/an34_130705.png" title="展开树" width="76" height="27" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript: d.closeAll();"><img src="images/an35_130705.png" title="关闭树" width="76" height="27" /></a></p>
    <div class="auto">
      <div class="dtree" style="">
  			<input id="ref" type="hidden"/> 
  			<p style="color:red;">鼠标左键点击节点弹出菜单</p>  
  			<div id="_dtree">
  			<script type="text/javascript">
				var d = new dTree('d');			
				//d.add("0","-1","部门信息");  
				<c:if test="${!empty deList}">
					<c:forEach items="${deList}" var="dpt">  
						d.add(${dpt.deptid},  
							  		  ${dpt.parentdeptid},
							  		  "${dpt.deptname}",   
							  		  "javascript:getUserListByDept(${dpt.deptid})"); 
					</c:forEach>
				</c:if>	 
				document.write(d);	 	
			</script>
  			</div>  
  		</div>
    </div>
  </div>
  
  <div class="thpj_wjszR">
    <p>系统默认“行政部门”人员参与职工互评，可通过查询对对个别人员的问卷进行微调！</p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2 m_t_10">
      <colgroup span="3" class="w180"></colgroup>
      <tbody id="mainTbl">
			    </tbody>   
			  </table>
			 
			 
				 <div class="t_r p_10_20">	
								    <form id="listform" name="listform" method="post">
								    	<div align="right" id="listaddress"></div>
								    </form>
				 </div> 
  </div>
  <div class="clear"></div>
</div>
  </body>
</html>

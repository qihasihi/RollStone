<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String commenttype = request.getParameter("commenttype");
	String commentobjectid = request.getParameter("commentobjectid");
%>
<script type="text/javascript">
  		var pList;
  		$(function(){
  			pList = new PageControl({
  				post_url:"comment!getCommentList.action?commenttype=<%=commenttype %>&commentobjectid=<%=commentobjectid %>", 
  				page_id:'page1',
  				page_control_name:"pList",  
  				post_form:document.pListForm,
  				gender_address_id:'pListaddress',
				http_operate_handler:getCommentReturnMethod,	//执行成功后返回方法
				return_type:'json',								//放回的值类型
				page_no:1,					//当前的页数
				page_size:20,				//当前页面显示的数量
				rectotal:0,				//一共多少 
				pagetotal:1,
				operate_id:"tbl_commentList"
  			});
  			pageGo("pList");
  		});
  	</script>
<div style="margin: 5px;background-color: white" class="white_content" id="commentList">
 <p>
    <a style="float:right;" href="javascript:closeDiv('commentList');" ><img src="images/icon04.gif" title="关闭"  width="20" height="20" /></a>
    </p>
 <br><p id="commentListTitle" align="center" style="font-weight:bold;font-size:24;" ></p><br>
<table id="tbl_commentList" width="800" border="0" align="center">
</table>
<form id="pListForm" name="pListForm">
	     <p class="Mt20" id="pListaddress" align="center"></p>
	   </form>
</div>
<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>

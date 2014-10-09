<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path=request.getHeader("x-etturl");
    if(path==null){
        path=request.getContextPath();
    }else{
        path=path.substring(0,path.substring(1).indexOf("/"));
    }
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style type="text/css">
.star_hover {
	float:left;
	height:15px;
	width:20px;
	overflow:hidden;
	display:inline-block;
	background:url(images/star01.gif)
}

.star_out {
	float:left;
	height:15px;
	width:20px;
	overflow:hidden;
	display:inline-block;
	background:url(images/star02.gif)
}

</style>
<div style="margin: 5px;background-color: white" class="white_content" id="commentDiv">
<form action="" method="get">
<table width="600" border="1" align="center" cellspacing="0">
 <tr>
    <td height="40" colspan="3" align="center">我要评论</td>
  </tr>
  <tr>
    <td width="80" align="right">评分：</td>
    <td colspan="2" valign="middle">
	<ul id="p_rate" style="float:left;">
		<li title="1分" class="star_out"></li>
		<li title="2分" class="star_out"></li>
		<li title="3分" class="star_out"></li>
		<li title="4分" class="star_out"></li>
		<li title="5分" class="star_out"></li>
	</ul>    <span id="scoreResult">未评</span>分	</td>
  </tr>
  <tr>
    <td align="right" valign="top">评论：</td>
    <td colspan="2"><textarea id="commentcontext" name="commentcontext" cols="50" rows="5"></textarea>  </tr>
   <tr>
    <td height="20" colspan="3">&nbsp;</td>
    </tr>
  <tr>
    <td height="40" valign="top">&nbsp;</td>
    <td width="253" height="40" align="center" valign="top"><input type="button" onclick="comment_start.addComment(pRate);" name="ok" value="提交">    </td>
    <td width="253" height="40" align="center" valign="top"><input type="button" onclick="comment_start.closeCommentDiv(pRate);" name="canle" value="取消"></td>
  </tr>
</table>
</form>
</div>
<div id="fade" class="black_overlay" style="background:black; filter: alpha(opacity=50); opacity: 0.5; -moz-opacity:0.5;"></div>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<% Object msg = request.getAttribute("result");
	if(msg!=null){
		if(msg.toString().equals("commented"))
			out.println("<script>alert('您已对本课题进行过评价');window.close();</script>");
		if(msg.toString().equals("errors"))
			out.println("<script>alert('参数错误，无法评价！请联系管理员...');window.close();</script>");
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/comment_star.css"/>
    <script type="text/javascript" src="js/comment/commentScorePlug.js"></script>
<script type="text/javascript">
function addcomment(){
	var commenttype=$("#commenttype").val();
	var commentobjectid=$("#commentobjectid").val();
	var score=comment_star.value;
	if(commentobjectid==0||commenttype==0){
		alert("缺失参数，无法评论！");
		return;
	}
	if(score<1){
		alert("请打分！");
		return;
	}
	if($("#commentcontext").val()==null||$("#commentcontext").val().length<1){
		alert("请输入评论内容！");
		return;
	}
	var dat={
			commenttype:commenttype,
			commentobjectid:commentobjectid,
			score:score,
			commentcontext:$("#commentcontext").val()} ;
	$.ajax({
		url:'commoncomment?m=ajaxTCCommentSave',
		data:dat,
		type:'post',
		dataType:'json',
		error:function(){
			alert('异常错误,系统未响应！');
		},success:function(rps){
			if(rps.type=="success"){
				alert("评论成功!");
				window.close();
			}else{
				alert("提交失败!");
			}		
		}
	});
}

</script>
</head>
</head>

<body>
<div class="subpage_head"><span class="ico19"></span><strong>专题评价</strong></div>
<div class="content1">
    <div class="jxxt_student_pingjia">
        <p><strong>${tcc.coursename }</strong></p>
        <p>学科：${tcc.subjectname }&nbsp;&nbsp;&nbsp;&nbsp;教师：${tcc.teachername }&nbsp;&nbsp;&nbsp;&nbsp;开课时间：${tcc.begintimeString }</p>
    </div>
    <input type="hidden" id="commenttype" value="${commenttype }"/>
    <input type="hidden" id="commentobjectid" value="${commentobjectid }"/>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
        <col class="w100"/>
        <col class="w600"/>
        <tr>
            <th>专题评分：</th>
            <td><div id="commentScore"></div>
                <script type="text/javascript">
                    comment_star = creatCommentForm('commentScore', 5, false, true,'a',null);
                </script>
            </td>
        </tr>
        <tr>
            <th>想法及建议：</th>
            <td><textarea id="commentcontext" name="commentcontext" class="h90 w500"></textarea></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="javascript:addcomment();" class="an_small">提&nbsp;交</a><a href="javascript:window.close();" class="an_small">取&nbsp;消</a></td>
        </tr>
    </table>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>




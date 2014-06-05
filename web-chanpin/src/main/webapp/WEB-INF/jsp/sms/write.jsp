<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common.jsp" %> 
<html>
<head>
<title>数字化校园</title>
<link rel="stylesheet" type="text/css" href="css/dxzx.css"/>
<script type="text/javascript" src="js/sms.js"></script>
<script type="text/javascript">
   	
	var icon = {
		root				: '<%=basePath%>images/base.gif',
		folder			: '<%=basePath%>images/check0.gif',
		folderOpen	: '<%=basePath%>images/check0.gif',
		node				: '<%=basePath%>images/check0.gif', 
		empty				: '<%=basePath%>images/empty.gif',
		line				: '<%=basePath%>images/line.gif',
		join				: '<%=basePath%>images/join.gif',
		joinBottom	: '<%=basePath%>images/joinbottom.gif',
		plus				: '<%=basePath%>images/plus.gif',
		plusBottom	: '<%=basePath%>images/plusbottom.gif',
		minus				: '<%=basePath%>images/minus.gif',
		minusBottom	: '<%=basePath%>images/minusbottom.gif',
		nlPlus			: '<%=basePath%>images/nolines_plus.gif',
		nlMinus			: '<%=basePath%>images/nolines_minus.gif'
	};
  	var d = new dTree('d');
   	d.changeIcon(icon);
   	var p1;
   		$(function(){   		
   		//xheditor富文本框使用事例 	
		$("#sel_smscontent").xheditor( 
					{
						tool : "mfull",
						html5Upload:false,		//是否应用 html5上传，如果应用，则不允许上传大文件
						upLinkUrl : "{editorRoot}upload.jsp", 
						upLinkExt : "zip,rar,txt,doc,ppt,pptx,docx,xls,xlsx,ZIP,RAR,TXT,DOC,PPT,PPTX,DOCX,XLS,XLSX",
						upImgUrl : "{editorRoot}upload.jsp",
						upImgExt : "jpg,jpeg,gif,png,JPG,JPEG,GIF,PNG",
						upFlashUrl : "{editorRoot}upload.jsp",
						upFlashExt : "swf,SWF",
						upMediaUrl : "{editorRoot}upload.jsp",
						upMediaExt : "avi,mp3,wma,wmv,mp4,mov,mpeg,mpg,flv,AVI,MP4,MOV,FLV,MPEG,MPG,MP3,WMA,WMV"// ,
					});
		 
			$("#addMessDiv").show('fast'); 
   		});
   		
   	</script>
</head>

<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>

<div class="jxpt_layout">
  <div class="public_lable_layout">
    <ul class="public_lable_list">
      <li><a href="sms?m=inbox">收件箱</a></li>
      <li><a href="sms?m=sent">发件箱</a></li>
      <li class="crumb">写信息</li>
      <li><a href="sms?m=draft">草稿箱</a></li>
    </ul>
  </div>
  <div class="p_20">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 zt14">
      <col class="w120" />
      <col class="w560" />
      <tr>
        <th>收件人：<br />
          <span class="font12">(用户名或用户ID)</span></th>
        <td><input id="sel_reciever" type="text" class="public_input w540" value="${smsInfo.receiverlist }" /></td>
      </tr>
      <tr>
        <th>提醒：</th>
        <td class=" font-gray">可以输入多个用户ID或用户名进行群发，用户ID及用户名之间用&ldquo;;&rdquo;隔开。建议使用用户ID， 因为用户名是可以更改的，用户ID固定不变。</td>
      </tr>
      <tr>
        <th>主题：</th>
        <td><input id="sel_smstitle" type="text" class="public_input w540" value="${smsInfo.smstitle }"/></td>
      </tr>
      <tr>
        <th>详情：</th>
        <td><textarea style="" rows="15" cols="80" id="sel_smscontent" >${smsInfo.smscontent }</textarea>
        </td>
      
      
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
        <a href="javascript:toDraft('${smsInfo.smsid}')" class="an_blue">存稿</a>
        <a href="javascript:toSend()" class="an_blue">发送</a>
        <a href="javascript:reset('sel_smscontent');" class="an_blue">重置</a></td>
      </tr>
    </table>
  </div>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>


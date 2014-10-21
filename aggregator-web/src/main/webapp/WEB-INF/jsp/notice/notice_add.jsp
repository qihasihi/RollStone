<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common.jsp"%>
<%
UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
String userid=user.getRef();
int idx=0;
%>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="<%=basePath %>js/notice/notice.js"></script>
	<meta http-equiv="pragma" content="no-cache">
      <meta http-equiv="cache-control" content="no-cache">
      <meta http-equiv="expires" content="0">
      <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
      <meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		$(function(){
			$('#content').xheditor(
					{
						tool : "mfull",
						html5Upload:false,		//是否应用 html5上传，如果应用，则不允许上传大文件
						upLinkUrl : "{editorRoot}upload.jsp",
						upLinkExt : "zip,rar,txt,doc,ppt,pptx,docx,xls,xlsx,ZIP,RAR,TXT,DOC,PPT,PPTX,DOCX,XLS,XLSX",
						upImgUrl : "{editorRoot}upload.jsp",
						upImgExt : "jpg,jpeg,gif,png,JPG,JPEG,GIF,PNG",
						upFlashUrl : "{editorRoot}upload.jsp",
						upFlashExt : "swf,SWF"
					//	,upMediaUrl : "{editorRoot}upload.jsp",
					//	upMediaExt : "avi,mp3,wma,wmv,mp4,mov,mpeg,mpg,flv,AVI,MP4,MOV,FLV,MPEG,MPG,MP3,WMA,WMV"// ,
					// onUpload:insertUpload //指定回调涵数
					});
			$("tr").filter(function(){
				return this.id.Trim().indexOf('tr_student')!=-1;
			}).hide('fast');
			
		});
	</script>
  </head>
  
  <body>
    <div align="center">
			<br />
			<a href="notice?m=list">返回列表</a>
			<a href="loginJumpFile.jsp">返回首页</a>
				<table style="font-size: 13px">
					<tr>
						<td colspan=3 align="center">
							<h3>
								发布信息
							</h3>
						</td>
					</tr>
					<tr>
						<th>
							类 &nbsp;&nbsp;型:
						</th>
						<td colspan="2">
							<select id="type" name="type"
								onchange="typeSelect('tdNoticeUsefullife')">
								<option value="-1">
									==请选择==
								</option>
								<c:forEach items="${typeList}" var="itm">
									<option value="${itm.dictionaryvalue }">
									${itm.dictionaryname }
								</option>
								</c:forEach>								
								
								
							</select>
						</td>
						<th>						
							<input type="checkbox" id="isTop" name="isTop" value="0" />
							是否置顶&nbsp;&nbsp;						
						</th>
					</tr>
					<tr id="tdNoticeUsefullife">
					</tr>
					<tr>
						<th>
							标 &nbsp;&nbsp;题:
						</th>
						<td colspan="3">
							<input type="text" name="title"  id="title" style="width: 650px"
								maxlength="50" />
						</td>
					</tr>
					<tr>
						<th>标题链接:</th>
						<td colspan="3" align="left"><input type="checkbox" onclick="isTitleLinkEditor(this)" id="titleLink" name="titleLink" value="0"/></td>
					</tr>
					<tr>
						<th valign="top">
							内 &nbsp;&nbsp;容:
						</th>
						<td colspan="3" id="tdContent">
							 <textarea rows="15" name="content" style="height:300px" id="content" cols="70"></textarea>
					</tr>
					<tr>
						<th>
							发送对象:
						</th>
						<td colspan="3">
							<c:if test="${!empty role}">
								<c:forEach items="${role}" var="itm">
									<input onclick="rolechecked('${itm.roleid}','${itm.rolename}')" type="checkbox" value="${itm.roleid }" id="roleId"
									name="roleId" />${itm.rolename }
    					<%idx++;
    					if(idx%9==0){
    					 %>
								<br />
								<%} %>
								</c:forEach>
							</c:if>
						</td>
					</tr>
				</table>
				<table id="td_ht" style="font-size: 13px" >
   				<tr id="tr_student_grade">
   					<th  >年级：</th>
   					<td colspan="3">
   						<c:if test="${empty gradeList }">
   							没有发现年级信息!
   						</c:if>
   						<c:if test="${!empty gradeList }">
   							<c:forEach var="g" items="${gradeList }" varStatus="status">
   								<input type="checkbox"  value="${g.gradevalue }" name="ck_grade"/>${g.gradename }
   								<c:if test="${(status.index+1)%3==0}"><br/></c:if>
   							</c:forEach>
   						</c:if>
   					</td>
   				</tr>
   				
   				
   			</table>
   			<input type="button" onclick="onSubmitdoAdd()" value="发布" />
							&nbsp;&nbsp;&nbsp;
							<input type="reset" value="重置" />
		</div>
  </body>


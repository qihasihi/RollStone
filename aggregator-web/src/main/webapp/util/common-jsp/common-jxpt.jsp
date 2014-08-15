<%@ page language="java" import="java.util.*,java.math.BigDecimal" pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<%
    //教学平台
    modelType=2;%>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/jxxt.css"/>
<script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/videoPlayer/swfobject.js"></script>
<script type="text/javascript">
    var ques_mp3_path='<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>';
</script>
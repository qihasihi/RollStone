<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
<script type="text/javascript" src="js/interactive/topic.js"></script>
<script type="text/javascript">
	var rt="${roleStr}";
	var courseid=${courseid};
	var p1;
	$(function(){
        if($("#ul_courselist li").length>1)
            $("#a_courselist").show();
        else
            $("#a_courselist").hide();

		p1 = new PageControl({
			post_url:'tptopic?m=ajaxtopiclist',
			page_id:'page1',
			page_control_name:'p1',
			post_form:document.f1,
			gender_address_id:'pageAddress1',
			http_free_operate_handler:validateParam,
			http_operate_handler:listReturn,
			return_type:'json',
			page_no:1, 
			page_size:10,
			rectotal:0,
			pagetotal:1,
			operate_id:"mainUL"
		});	
		pageGo('p1');
	});
	//参数赋值
	function validateParam(tobj){		
		var param=new Object();
		param.selectType=1;
		param.courseid=courseid;
		tobj.setPostParams(param);		
	}

	
</script>
</head>

<body>
<%@include  file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>
<div class="zhuanti">
  <p>
  	<c:if test="${!empty courseList}">
  		<c:forEach items="${courseList}" var="t">
  			<c:if test="${t.courseid==courseid}">
  				${t.coursename}<input type="hidden" value="${t.courseid }" id="hd_course_id"/>
  			</c:if>
  		</c:forEach>
  	</c:if>
  <a class="ico13" href="javascript:;" onclick="operateUI('ul_courselist')" id="a_courselist"></a></p>
  <ul style="display:none" id="ul_courselist">
    <c:if test="${!empty courseList}">
  		<c:forEach items="${courseList}" var="ct">
  			<c:if test="${ct.courseid==courseid}">
  			<li style="display:none"><a href="tptopic?m=index&courseid=${ct.courseid}">${ct.coursename}</a></li>
  			</c:if>
  			<c:if test="${ct.courseid!=courseid}">
  			 <li><a href="tptopic?m=index&courseid=${ct.courseid}">${ct.coursename}</a></li>
  			 </c:if>  			
  		</c:forEach>
  	</c:if>
  </ul>
</div>

<div class="subpage_nav">
  <ul>

      <c:if test="${!empty roleStr&&roleStr eq 'TEACHER'}">
          <li><a href="task?toTaskList&courseid=${courseid}">学习任务</a></li>
          <li><a href="tpres?toTeacherIdx&courseid=${courseid}">专题资源</a></li>
          <li class="crumb"><a>互动空间</a></li>
          <li><a  href="question?m=toQuestionList&courseid=${courseid}">试&nbsp;&nbsp;题</a></li>
          <li><a href="paper?toPaperList&courseid=${courseid}">试&nbsp;&nbsp;卷</a></li>
      </c:if>
      <c:if test="${!empty roleStr&&roleStr eq 'STUDENT'}">
          <li><a href="task?toStuTaskIndex&courseid=${courseid }">学习任务</a></li>
          <li><a href="tpres?toStudentIdx&courseid=${courseid }">专题资源</a></li>
          <li class="crumb"><a>互动空间</a></li>
       </c:if>
  </ul>
</div>
<div class="content2">
 <c:if test="${!empty roleStr&&roleStr eq 'TEACHER'}">
  <div class="jxxt_zhuanti_hdkj_add">
  <%--<p class="f_right"><a class="ico15" href="tpres?m=toRecycleIdx&type=4&courseid=${courseid}" title="回收站"></a></p>--%>
  <p class="font-darkblue"><a href="tptopic?m=toAdmin&courseid=${courseid }"><span class="ico26"></span>新建论题</a></p>
  </div>
</c:if>


  <div class="jxxt_zhuanti_hdkj_lunti">
  <div id="mainUL"></div> 
  <form name="f1" id="f1" method="post">
	<div class="nextpage" id="pageAddress1"> <span><b class="before"></b></span><span><a href="1">1</a></span><span><a href="1">2</a></span><span class="crumb"><a href="1">3</a></span><span><a href="1">4</a></span><span><a href="1">5</a></span>&hellip;<span><a href="1">9</a></span><span><a href="1">10</a></span><span><a href="1">11</a></span><span><a href="1"><b class="after"></b></a></span>&nbsp;&nbsp;共20页&nbsp;&nbsp;去第
  <input name="textfield2" type="text" id="textfield" value="3000" />
  页&nbsp;&nbsp;<span><a href="1">Go</a></span></div>
</div>
</form>
<c:if test="${!empty staticObj}">
	<div class="jxxt_zhuanti_hdkj_info">
		<strong>今日发帖：</strong>${staticObj.JRFT}
		<strong>昨日发帖：</strong>${staticObj.ZRFT}
		<strong>论题总数：</strong>${staticObj.LTZS}
		<strong>主帖总数：</strong>${staticObj.ZTZS}
		<strong>回帖总数：</strong>${staticObj.HTZS}       
		<strong>统计时间：</strong>${staticObj.TJSJ}
	</div>
</c:if>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>

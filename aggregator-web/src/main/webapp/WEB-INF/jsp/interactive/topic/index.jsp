<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
<script type="text/javascript" src="js/interactive/topic.js"></script>
<script type="text/javascript" src="fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript"  src="fancybox/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-1.3.4.css"/>

<script type="text/javascript">
	var rt="${roleStr}";
	var courseid=${courseid};
	var p1,fancyboxObj;
	$(function(){
        if($("#ul_courselist li").length>1)
            $("#a_courselist").show();
        else
            $("#a_courselist").hide();

        fancyboxObj=$("#a_click").fancybox({
            'onClosed':function(){
                pageGo('p1');
                $("#dv_optpc").hide();
            }
        });

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
			page_size:5,
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

    function loadTopicAdmin(tpcid){
        if(typeof(tpcid)=="undefined"||tpcid==null){
            //新建
            $("#dv_optpc").load("tptopic?m=toAdmin&courseid=${courseid}",function(){
               //加载完毕后，显示
                $("#dv_optpc .foot").remove();
                $("#dv_optpc").show();
                $("#a_click").click();
            });
        }else{
            //新建
            $("#dv_optpc").load("tptopic?m=toAdmin&courseid=${courseid}&topicid="+tpcid,function(){
                //加载完毕后，显示
                $("#dv_optpc .foot").remove();
                $("#dv_optpc").show();
                $("#a_click").click();
            });
        }
    }

	
</script>
</head>

<body>
<%@include  file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>
<div class="zhuanti">
  <p>
  	<c:if test="${!empty coruseName}">
  				${coruseName}<input type="hidden" value="${courseid }" id="hd_course_id"/>
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
  <p class="font-darkblue"><a href="javascript:;" onclick="loadTopicAdmin()"><span class="ico26"></span>新建论题</a></p>
  </div>
</c:if>


  <div class="jxxt_zhuanti_hdkj_lunti">
  <div id="mainUL"></div> 
  <form name="f1" id="f1" method="post">
	<div class="nextpage" id="pageAddress1"></div>
</div>
</form>
<%--<c:if test="${!empty staticObj}">--%>
	<%--<div class="jxxt_zhuanti_hdkj_info">--%>
		<%--<strong>今日发帖：</strong>${staticObj.JRFT}--%>
		<%--<strong>昨日发帖：</strong>${staticObj.ZRFT}--%>
		<%--<strong>论题总数：</strong>${staticObj.LTZS}--%>
		<%--<strong>主帖总数：</strong>${staticObj.ZTZS}--%>
		<%--<strong>回帖总数：</strong>${staticObj.HTZS}       --%>
		<%--<strong>统计时间：</strong>${staticObj.TJSJ}--%>
	<%--</div>--%>
<%--</c:if>--%>
</div>
<%@include file="/util/foot.jsp"%>
<div id="dv_optpc" style="display:none" class="public_float jxxt_zhuanti_hdkj_float">

</div>
<a id="a_click" href="#dv_optpc">
</a>
</body>
</html>

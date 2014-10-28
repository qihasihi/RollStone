<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-2-17
  Time: 下午1:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@ page import="com.school.entity.teachpaltform.TpCourseInfo" %>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/ques-add-bycourse.js"></script>
<head>
    <title></title>
    <script type="text/javascript">
        var operate_type="${param.operate_type}";
        var questype="${param.questype}";
        var questionids=[];
        var p1;
        var addCourseId = "${param.addCourseId}";
        $(function(){
            p1 = new PageControl({
                post_url:'question?m=getQuestionByCourse',
                page_id:'page1',
                page_control_name:'p1',
                post_form:document.page1form,
                gender_address_id:'page1address',
                http_operate_handler:questionListReturn,
                http_free_operate_handler:function(p){
                    var param={};
                    param.selectCourseid = "${param.selectCourseid}";
                    param.addCourseid=addCourseId;
                    if(questype.length>0)
                        param.questype=questype;
                    p.setPostParams(param);

                },
                new_page_html_mode:false,
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
</head>
<body>
<div class="subpage_head">
    <span class="ico55"></span><strong>添加试题</strong>
</div>
<div class="subpage_nav">
    <ul>
        <li><a  href="question?m=toAddQuestion&courseid=${param.addCourseId}">新建试题</a></li>
        <li class="crumb"><a href="teachercourse?m=toCourseQuestionList&addCourseId=${param.addCourseId}">引用试题</a></li>
    </ul>
</div>
<div class="content1">
    <div class="jxxt_zhuanti_shiti_add_info">
        <p class="back"><a href="javascript:window.history.go(-1);" class="one">返回</a></p>
        <p class="font-black"><strong>${courseObj.coursename}　（${courseObj.materialname}）</strong></p>
        <p>类型：${courseObj.courselevel eq 3?"自建专题":"标准/共享专题"}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作者：${!empty courseObj.teachername and fn:length(courseObj.teachername)>0?courseObj.teachername:"北京四中网校"}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年级：${courseObj.gradename}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <%
                TpCourseInfo c=(TpCourseInfo)request.getAttribute("courseObj");
                if(c!=null){
                    for(int i=1;i<6;i++){
                        if(Float.parseFloat(c.getAvgscore().toString())>=i){
                            %>
                                <span class='ico_star1'></span>
                            <%}else if(Float.parseFloat(c.getAvgscore().toString())<i&&Float.parseFloat(c.getAvgscore().toString())>i-1){%>
                                <span class='ico_star2'></span>
                            <%}else{ %>
                                <span class='ico_star3'></span>
                            <%}%>
                    <%}%>
            <%}%>
            <!--<span class="ico_star1"></span><span class="ico_star1"></span><span class="ico_star1"></span><span class="ico_star2"></span><span class="ico_star3"></span>&nbsp;3.5-->
        </p>
        <p class="font-darkblue">搜到的试题&nbsp;<span class="font-red">${courseObj.questionnum}</span></p>
    </div>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
        <col class="w30"/>
        <col class="w880"/>
        <tbody id="mainTbl">
        </tbody>
    </table>
    <form id="page1form" name="page1form"  method="post" >
        <p class="nextpage" align="center" id="page1address"></p>
    </form>
    <%--<div id="hideDiv" style="display: none">--%>
        <%--<c:if test="${!empty selectedQuestion}">--%>
            <%--<c:forEach items="${selectedQuestion}" var="itm">--%>
                <%--<input type="text" id="${itm.questionid}" value="${itm.questionid}"/>--%>
            <%--</c:forEach>--%>
        <%--</c:if>--%>
    <%--</div>--%>
    <p class="t_c p_tb_10"><a href="javascript:questionSubmit();" id="btn_addQues" class="an_small">添&nbsp;加</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:history.go(-1)" class="an_small">取&nbsp;消</a></p>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

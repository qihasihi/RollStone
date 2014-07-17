
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>


<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
<!--    <script type="text/javascript"
            src="<%=basePath %>js/teachpaltform/ques.js"></script> -->
    <script type="text/javascript"  src="<%=basePath %>js/teachpaltform/resource.js"></script>　
    <script type="text/javascript">
        var courseid = "${param.courseid}";
        $(function () {
            //加载任务
            loadDetial(1);
        });

        function loadDetial(type){
            var url='';
            switch (type){
                case  1: url='teachercourse?toTaskList&courseid='+courseid;break;
                case  2: url='teachercourse?toTeacherIdx&courseid='+courseid;break;
                case  3: url='teachercourse?m=toQuestionList&courseid='+courseid;break;
                case  4: url='tptopic?m=courseDetailIdx&courseid='+courseid;break;
                case  5: url='teachercourse?toPaperList&courseid='+courseid;break;
            }
            $("li[name='li_nav']").each(function(idx,itm){
                $(itm).bind("click",function(){
                    $(itm).siblings().removeClass("crumb").end().addClass("crumb");
                })
            })
            $("#dv_course_element").load(url);
        }
    </script>


</head>
<body>



<div class="subpage_head"><span class="ico55"></span><strong>专题详情&mdash;&mdash;${courseInfo.coursename}</strong></div>
<div class="jxxt_zhuanti_hdkj_nr">
    <p class="font-darkgray">${materialList[0].materialname}${!empty materialList[0].versionname?materialList[0].versionname:""}&nbsp;&nbsp;&nbsp;&nbsp;发布者：${!empty courseInfo.realname?courseInfo.realname:"北京四中网校"}</p>
    <p>${courseInfo.introduction==null?"":courseInfo.introduction}</p>
</div>
<div class="subpage_nav">
    <ul>
        <li name="li_nav" class="crumb"><a href="javascript:loadDetial(1)">学习任务</a></li>
        <li name="li_nav"><a href="javascript:loadDetial(2)">专题资源</a></li>
        <li name="li_nav"><a href="javascript:loadDetial(4)">互动空间</a></li>
        <li name="li_nav"><a href="javascript:loadDetial(3)">试&nbsp;&nbsp;题</a></li>
        <li name="li_nav"><a href="javascript:loadDetial(5)">试&nbsp;&nbsp;卷</a></li>
    </ul>
</div>
    <div id="dv_course_element">

    </div>
<%@include file="/util/foot.jsp" %>

</body>
</html>

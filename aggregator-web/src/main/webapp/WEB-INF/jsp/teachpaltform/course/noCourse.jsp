<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="js/teachpaltform/stucourselist.js"></script>
    <script type="text/javascript">
        var currtenTab=1; //当前学科导航栏页数
        var tabSize=10; //学科导航栏每页显示数量
        $(function(){
//
//            $("#p_stuClasses").change(function(){
//                getMyGroup();
//            });


//            pList = new PageControl( {
//                post_url : 'teachercourse?m=getStuCourselistAjax',
//                page_id : 'page1',
//                page_control_name : "pList",
//                post_form : document.pListForm,
//                gender_address_id : 'pListaddress',
//                http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
//                http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
//                return_type : 'json', //放回的值类型
//                page_no : 1, //当前的页数
//                page_size : 20, //当前页面显示的数量
//                rectotal : 0, //一共多少
//                pagetotal : 1,
//                operate_id : "initItemList"
//            });

            if($("#sub_0")!=null)
                $("#sub_0").click();
            changeTab('back');
            changeTab('front');
        });
        function changeTab2(direct){
            if(direct=="back"){
                var crumbNextLen=$("ul[id='ul_grade'] .crumb").next().length;

                if(crumbNextLen<1)
                    changeTab('front');
                else if($("ul[id='ul_grade'] .crumb").next().filter(function(){return this.style.display!='none'}).length<1){
                    // eval($("ul[id='ul_grade'] li:first").children("a").attr("href").replace("javascript:",""));
                    changeTab('back');
                }
                if($("ul[id='ul_grade'] .crumb").next().length>0)
                    eval($("ul[id='ul_grade'] .crumb").next().children("a").attr("href").replace("javascript:",""));
                else
                    eval($("ul[id='ul_grade'] li:first").children("a").attr("href").replace("javascript:",""));
            }
            if(direct=="front"){
                var crumbNextLen=$("ul[id='ul_grade'] .crumb").prev().length;

                if(crumbNextLen<1)
                    changeTab('back');
                else if($("ul[id='ul_grade'] .crumb").prev().filter(function(){return this.style.display!='none'}).length<1){
                    // eval($("ul[id='ul_grade'] li:first").children("a").attr("href").replace("javascript:",""));
                    changeTab('front');
                }

                if($("ul[id='ul_grade'] .crumb").prev().length>0)
                    eval($("ul[id='ul_grade'] .crumb").prev().children("a").attr("href").replace("javascript:",""));
                else
                    eval($("ul[id='ul_grade'] li:last").children("a").attr("href").replace("javascript:",""));
            }
        }
    </script>
</head>
<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>

<div id="nav">

</div>

<div class="content1">
    <p class="tishi">该学期，还没有老师发布专题，请耐心等待&hellip;</p>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>  

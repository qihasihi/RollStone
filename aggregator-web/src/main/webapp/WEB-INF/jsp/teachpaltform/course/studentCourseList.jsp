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

        $("#p_stuClasses").change(function(){
            getMyGroup();
        });


        pList = new PageControl( {
            post_url : 'teachercourse?m=getStuCourselistAjax',
            page_id : 'page1',
            page_control_name : "pList",
            post_form : document.pListForm,
            gender_address_id : 'pListaddress',
            http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
            http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
            return_type : 'json', //放回的值类型
            page_no : 1, //当前的页数
            page_size : 20, //当前页面显示的数量
            rectotal : 0, //一共多少
            pagetotal : 1,
            operate_id : "initItemList"
        });

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
<input type="hidden" id="termid" value="${currtTerm.ref}">
<input type="hidden" id="subjectid" value="">
<div id="nav">
    <div class="arr"><a href="javascript:changeTab2('front');"><span class="up"></span></a><a href="javascript:changeTab2('back');"><span class="next"></span></a></div>
    <ul id="ul_grade">
        <c:forEach var="sl" items="${subjectList}" varStatus="status">
            <li id="sub_${sl.subjectid}"><a href="javascript:searchBySubject(${sl.subjectid})"><span id="sub_${status.index}">${sl.subjectname}</span></a></li>
        </c:forEach>
    </ul>
</div>

<div class="content2">

    <div class="jxxt_studentR font-black" id="my_term">
        <p><span class="ico22"></span>我的小组&nbsp;&nbsp;
            <select id="p_stuClasses" class="w120" >
            </select>
        </p>
        <ul id="my_group">
        </ul>
    </div>

    <div class="jxxt_studentL">
        <table border="0" cellspacing="0" cellpadding="0" class="public_tab1">
            <col class="w110" />
            <col class="w630" />

            <tr>
                <td><span class="ico28"></span><strong class="font-darkblue">我的班级：</strong></td>
                <td>
                    <ul id="stuClasses" class="public_list2">
                    </ul></td>
            </tr>
        </table>

        <p class="font-darkblue font_strong"><a href="tpres?toStuCollectList" target="_blank" ><span class="ico60"></span>我的资源管理</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="" id="task_performance" target="_blank"><span class="ico37"></span>任务完成统计</a></p>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w150"/>
            <col class="w270"/>
            <col class="w80"/>
            <col class="w80"/>
            <col class="w80"/>
            <col class="w80"/>
            <tr>
                <th>开课时间</th>
                <th>专题名称</th>
                <th>授课教师</th>
                <th>未完成任务</th>
                <th>课堂表现</th>
                <th>专题评价</th>
            </tr>
            <tbody id="courseTable">
            </tbody>
        </table>
        <form id="pListForm" name="pListForm"><p class="nextpage" id="pListaddress" align="center"></p></form>
    </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>  

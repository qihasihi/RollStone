<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
        <script type="text/javascript" src="js/teachpaltform/stucourselist.js"></script>
        <script type="text/javascript" src="js/teachpaltform/course_calendar.js"></script>
<script type="text/javascript">
    var currtenTab=1; //当前学科导航栏页数
    var tabWord=35; //学科导航栏每页显示数量
    var _clsid="";
    var grade="";
    var gradeid="";

    function prev(){
        var year=$("#year").html().Trim();
        var month=$("#month").html().Trim();
        month=parseInt(month)-1;
        if(month<1){
            year=parseInt(year)-1;
            month=12;
        }

        showCalendar(year,month);
    }

    function next(){
        var year=$("#year").html().Trim();
        var month=$("#month").html().Trim();
        month=parseInt(month)+1;
        if(month>12){
            year=parseInt(year)+1;
            month=1;
        }
        showCalendar(year,month);
    }



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
            page_no:1, //当前的页数
            page_size:10, //当前页面显示的数量
            rectotal:0, //一共多少
            pagetotal:1,
            operate_id: "initItemList"
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
<input type="hidden" id="termid" value="${selTerm.ref}">
<input type="hidden" id="subjectid" value="">
    <div class="jxxt_student_nav">
        <div id="nav">
            <div class="arr" id="dv_sub_operate" style="display: none;width:55px;">
                <a href="javascript:changeTab2('front');"><span class="up"></span></a>
                <a href="javascript:changeTab2('back');"><span class="next"></span></a></div>
            <ul id="ul_grade">
                <c:forEach var="sl" items="${subjectList}" varStatus="status">
                    <li id="sub_${sl.subjectid}"><a href="javascript:searchBySubject(${sl.subjectid})"><span id="sub_${status.index}">${sl.subjectname}</span></a></li>
                </c:forEach>
            </ul>

            <div class="ico"><a href="tpres?toStuCollectList" target="_blank" ><span class="ico60"></span>资源管理</a>&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="showRiLi('dv_rili')"><span class="ico88"></span>课程日历</a></div>
            <div class="jxxt_student_rili" style="display:none" id="dv_rili">
                <div class="jxxt_rili">
                    <h1><a href="javascript:;" onclick="prev()" class="left"></a>
                    <span id="year">
                    </span>
                        年
                    <span id="month">
                    </span>
                        月<a  href="javascript:;" onclick="next()" class="right"></a></h1>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tbody id="calendar"></tbody>
                    </table>
                </div>
                <h2>我的课表</h2>
                <div class="kebiao" id="ul_kebiao">

                </div>
            </div>
        </div>
    </div>

    <div class="content2">
    <div class="jxxt_studentR font-black" id="my_term">
        <p><span class="ico22"></span><font id="term_name"></font></p>
        <ul id="my_group">
        </ul>
    </div>

    <div class="jxxt_studentL">
        <p class="font-darkblue f_right"><a href="javascript: ;" onclick="window.open('task?m=tostuSelfPerformance&termid='+termid.value+'&classid='+_clsid+'&subjectid='+subjectid.value)" id="task_performance"><span class="ico37"></span>任务完成统计</a>&nbsp;&nbsp;<a href="javascript:;" onclick="window.open('clsperformance?m=toAwardStaticesScore&subjectid='+subjectid.value+'&classid='+_clsid);" ><span class="ico66"></span>课程积分</a></p>
        <div class="banji" id="dv_banji">
            <p class="font-black"><span id="current_banji"></span><a id="a_nextBj" href="javascript:;" onclick="autoShowObjById('stuClasses')" class="ico49a"></a></p>
            <ul class="banji hide" id="stuClasses">
            </ul></div>

        <%--<table border="0" cellspacing="0" cellpadding="0" class="public_tab1">--%>
            <%--<col class="w110" />--%>
            <%--<col class="w630" />--%>
            <%--<tr>--%>
                <%--<td><span class="ico28"></span><strong class="font-darkblue">我的班级：</strong></td>--%>
                <%--<td>--%>
                    <%--<ul id="stuClasses" class="public_list2">--%>
                    <%--</ul></td>--%>
            <%--</tr>--%>
        <%--</table>--%>

        <%--<p class="font-darkblue font_strong"><a href="tpres?toStuCollectList" target="_blank" ><span class="ico60"></span>我的资源管理</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="" id="task_performance" target="_blank"><span class="ico37"></span>任务完成统计</a></p>--%>
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
                <th>课堂积分</th>
                <th>专题评价</th>
            </tr>
            <tbody id="courseTable">
            </tbody>
        </table>
        <form id="pListForm" name="pListForm"><p class="nextpage" id="pListaddress" align="center"></p></form>
    </div>
    </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>  

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
    var _clsid="";
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
<input type="hidden" id="termid" value="${selTerm.ref}">
<input type="hidden" id="subjectid" value="">
    <div class="jxxt_student_nav">
        <div id="nav">
            <div class="arr"><a href="javascript:changeTab2('front');"><span class="up"></span></a><a href="javascript:changeTab2('back');"><span class="next"></span></a></div>
            <ul id="ul_grade">
                <c:forEach var="sl" items="${subjectList}" varStatus="status">
                    <li id="sub_${sl.subjectid}"><a href="javascript:searchBySubject(${sl.subjectid})"><span id="sub_${status.index}">${sl.subjectname}</span></a></li>
                </c:forEach>
            </ul>

            <div class="ico"><a href="tpres?toStuCollectList" target="_blank" ><span class="ico60"></span>资源管理</a>&nbsp;&nbsp;&nbsp;<a href="1" target="_blank"><span class="ico88"></span>课程日历</a></div>
            <div class="jxxt_student_rili" style="display:none">
                <div class="jxxt_rili">
                    <h1><a href="1" class="left"></a>2014年8月<a href="1" class="right"></a></h1>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><a href="1">1</a></td>
                            <td><a href="1">2</a></td>
                            <td><a href="1">3</a></td>
                            <td><a href="1">4</a></td>
                            <td><a href="1">5</a></td>
                            <td><a href="1">6</a></td>
                            <td><a href="1">7</a></td>
                        </tr>
                        <tr>
                            <td><a href="1">8</a></td>
                            <td class="border"><a href="1">9<span class="ico89b"></span></a></td>
                            <td class="crumb"><a href="1">10<span class="ico89a"></span></a></td>
                            <td><a href="1">11<span class="ico89a"></span></a></td>
                            <td><a href="1">12</a></td>
                            <td><a href="1">13</a></td>
                            <td><a href="1">14</a></td>
                        </tr>
                        <tr>
                            <td><a href="1">15</a></td>
                            <td><a href="1">16</a></td>
                            <td><a href="1">17</a></td>
                            <td><a href="1">18</a></td>
                            <td><a href="1">19</a></td>
                            <td><a href="1">20</a></td>
                            <td><a href="1">21</a></td>
                        </tr>
                        <tr>
                            <td><a href="1">22</a></td>
                            <td><a href="1">23</a></td>
                            <td><a href="1">24</a></td>
                            <td><a href="1">25</a></td>
                            <td><a href="1">26</a></td>
                            <td><a href="1">27</a></td>
                            <td><a href="1">28</a></td>
                        </tr>
                        <tr>
                            <td><a href="1">29</a></td>
                            <td><a href="1">30</a></td>
                            <td><a href="1">31</a></td>
                            <td><a href="1"></a></td>
                            <td><a href="1"></a></td>
                            <td><a href="1"></a></td>
                            <td><a href="1"></a></td>
                        </tr>
                    </table>
                </div>
                <h2>我的课表</h2>
                <div class="kebiao">
                    <p><span class="font-black">高二（1）班</span>&nbsp;&nbsp;<a href="1" target="_blank">三角函数的基础最长20个汉字</a><a class="ico33"></a><br>
                        10：15&mdash;&mdash;11：50</p>
                    <p><span class="font-black">高二提高班</span>&nbsp;&nbsp;<a href="1" target="_blank">三角函数的基础应用</a><a class="ico33"></a><br>
                        10：15&mdash;&mdash;11：50</p>
                    <p><span class="font-black">好好学习天天向上班</span>&nbsp;&nbsp;<a href="1" target="_blank">三角基础应用角函基好...</a><a class="ico33"></a><br>
                        10：15&mdash;&mdash;11：50</p>
                    <p><span class="font-black">高二（1）班</span>&nbsp;&nbsp;<a href="1" target="_blank">三角函数的基础应用</a><a class="ico33"></a><br>
                        10：15&mdash;&mdash;11：50</p>
                </div>
            </div>
        </div>
    </div>

    <div class="content2">
    <div class="jxxt_studentR font-black" id="my_term">
        <p><span class="ico22"></span><font id="term_name">好好学习天天向上组</font></p>
        <ul id="my_group">
        </ul>
    </div>

    <div class="jxxt_studentL">
        <p class="font-darkblue f_right"><a href="" target="_blank" id="task_performance"><span class="ico37"></span>任务完成统计</a>&nbsp;&nbsp;<a href="javascript:;" onclick="location.href='clsperformance?m=toAwardStaticesScore&subjectid='+subjectid.value+'&classid='+_clsid;" target="_blank" ><span class="ico66"></span>课程积分</a></p>
        <div class="banji" id="dv_banji">
            <p class="font-black"><span id="current_banji">高一天天向上好好学班</span><a href="javascript:;" onclick="autoShowObjById('stuClasses')" class="ico49a"></a></p>
            <ul class="banji hide" id="stuClasses">
                <li><a href="1">高一语文学习班高一语文学习班</a></li>
                <li><a href="1">高一英语</a></li>
                <li><a href="1">高二数学</a></li>
                <li><a href="1">高一通用技术</a></li>
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
                <th>课堂表现</th>
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

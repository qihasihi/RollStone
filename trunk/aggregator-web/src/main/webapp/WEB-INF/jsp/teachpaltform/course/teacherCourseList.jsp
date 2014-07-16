<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <script type="text/javascript" src="js/teachpaltform/tchcourselist.js"></script>
    <script type="text/javascript">
        var currtenTab=1; //当前学科导航栏页数
        var tabSize=6; //学科导航栏每页显示数量
        var termid="${currtTerm.ref}";
        var currtterm="${currtTerm.ref}";
        var teacherid="${teacherid}";
        var global_gradeid=0;
        var global_subjectid=0;
        var userid=${userid};
        var materialid="${param.materialid}";
        var currentSubjectid = "${param.currentSubjectid}";
        var currentGradeid="${param.gradeid}";
        $(function(){
            if(materialid.length>0){
                initMaterial();
            }

            $("#fTermid").change(function(){
                getFTermCondition();
            });

            $("#fGradeSub").change(function(){
                fChangeGrade();
            });

            pList = new PageControl( {
                post_url : 'teachercourse?m=getTchCourListAjax',
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

            <c:if test="${isTrust==1}">
//            pTsList = new PageControl( {
//                post_url : 'teachercourse?m=getTrusteeshipCourListAjax',
//                page_id : 'pageTs',
//                page_control_name : "pTsList",
//                post_form : document.pListForm,
//                gender_address_id : 'pTsListaddress',
//                http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
//                http_operate_handler : getTsInvestReturnMethod, //执行成功后返回方法
//                return_type : 'json', //放回的值类型
//                page_no : 1, //当前的页数
//                page_size : 20, //当前页面显示的数量
//                rectotal : 0, //一共多少
//                pagetotal : 1,
//                operate_id : "initItemList"
//            });
            </c:if>
            getTermCondition('${currtTerm.ref}','${currtTerm.year } ${currtTerm.termname}');


            // 导航栏调整为正确显示


        });
        function initMaterial(){
//            $.ajax({
//                url:'teachingmaterial?m=getTchingMaterialList',
//                data:{
//                    materialid:materialid
//                },
//                type:'POST',
//                dataType:'json',
//                error:function(){
//                    alert('异常错误,系统未响应！');
//                },success:function(rps){
//                    $.each(rps.objList,function(idx,itm) {
//                        $("#material_name").html(itm.materialname);
//                        $("#material_id").val(itm.materialid);
//                    });
//                }
//            });
            $("#material_name").html("${materialinfo.materialname}");
            $("#material_id").val("${materialinfo.materialid}");
        }
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
        function showYYCourse(){
            getFTermCondition();
            showModel('followCourse_div');
        }
    </script>
</head>
<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>
<!--当前学期-->
<input type="hidden" id="hd_term_flag"/>z
<div class="jxxt_xueqi">
    <div class="menu"><span id="checkedTerm"></span><a class="ico13" href="javascript:void(0);" onclick="displayObj('termList');"></a></div>
    <ul id="termList" style="display:none;">
        <c:forEach var="tl" items="${termList }">
            <li><a href="javascript:void(0);" onclick="getTermCondition('${tl.ref }','${tl.year } ${tl.termname }');">${tl.year } ${tl.termname }</a></li>
        </c:forEach>
    </ul>
</div>

<div id="nav">
    <div class="arr"><a href="javascript:changeTab2('front');"><span class="up"></span></a><a href="javascript:changeTab2('back');"><span class="next"></span></a></div>
    <ul id="ul_grade">
    </ul>
</div>

<div class="content1">
    <ul id="claList" class="public_list2">
    </ul>
    <div class="jxxt_title m_t_15">

        <p class="f_right">
            <c:if test="${tscSize>0}">
                <a id="a_tsc" href="javascript:getTrusteeShipNotices();">【<span id="tscNum">${tscSize}</span>】条托管消息</a>&nbsp;&nbsp;
            </c:if>
            <a name="a_hide" href="javascript:getTchingMaterial();"><span id="material_name">点击教材版本</span></a>
            <a name="a_hide" href="teachercourse?m=toTeacherCourseRecycle" class="ico15" title="回收站"></a></p>
        <p><span class="ico14"></span><strong>我的课表</strong>
            <a name="a_hide" href="javascript:toSaveCoursePage();" class="font-darkblue"><span class="ico26"></span>添加专题</a>
            <a name="a_hide" href="javascript:showYYCourse();" class="font-darkblue"><span class="ico27"></span>沿用专题</a>
            <a name="a_hide" onclick="window.open('virtualclass?m=toClassManager&gradeid='+global_gradeid+'&subjectid='+global_subjectid+'')"  href="javascript:void(0)"  class="font-darkblue"><span class="ico28"></span>班级管理</a></p>
    </div>

    <div class="jxxt_tab_layout">
        <input type="hidden" id="material_id" value="0" />
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w350"/>
            <col class="w350"/>
            <col class="w120"/>
            <col class="w120"/>
            <tr>
                <th>专题名称</th>
                <th>班级&mdash;开始时间</th>
                <th>专题评价</th>
                <th>操作</th>
            </tr>
            <tbody id="courseTable">
            </tbody>
        </table>
        <form id="pListForm" name="pListForm"><p class="nextpage" id="pListaddress" align="center"></p></form>
    </div>

    <%--<h6></h6>--%>

    <c:if test="${isTrust==1}">
    <%--<div class="jxxt_title">--%>
        <%--<!-- <p class="f_right">已托管班级：高一1班&nbsp;&nbsp;高一2班&nbsp;&nbsp;高一3班</p> -->--%>
        <%--<p><span class="ico14"></span><strong>托管班级课表</strong></p>--%>
    <%--</div>--%>

    <%--<div id="ts_course" class="jxxt_tab_layout">--%>
        <%--<table border="0" cellpadding="0" cellspacing="0" class="public_tab2">--%>
            <%--<col class="w120"/>--%>
            <%--<col class="w350"/>--%>
            <%--<col class="w350"/>--%>
            <%--<col class="w120"/>--%>
            <%--<tr>--%>
                <%--<th>受托老师</th>--%>
                <%--<th>专题名称</th>--%>
                <%--<th>班级&mdash;开始时间</th>--%>
                <%--<th>专题评价</th>--%>
            <%--</tr>--%>
            <%--<tbody id="tsCourseTable">--%>
            <%--</tbody>--%>
        <%--</table>--%>
        <%--<form id="pTsListForm" name="pTsListForm"><p class="nextpage" id="pTsListaddress" align="center"></p></form>--%>
    <%--</div>--%>
    </c:if>

</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

<div style="display:none;" id="trusteeShip_div" class="public_windows">
    <p class="f_right"><a href="javascript:closeModel('trusteeShip_div');" title="关闭"><span class="public_windows_close"></span></a></p>
    <br/>
    <h3 align="center">托管通知</h3><hr/>
    <table border="0" cellpadding="0" cellspacing="0" >
        <col class="w50" />
        <col class="w250" />
        <col class="w100" />
        <tbody id="trusteeShip_notices">
        </tbody>
    </table>
    <br/>
</div>

<div id="followCourse_div" class="public_windows" style="display:none;">
    <h3><a href="javascript:closeModel('followCourse_div');" title="关闭"></a>沿用专题</h3>
    <div class="jxxt_float_yyzt public_input">
        <p class="font-black">学期：
            <select id="fTermid">
                <c:forEach var="tl" items="${termList }">
                    <c:if test="${tl.ref ne currtTerm.ref}">
                        <option value="${tl.ref }">${tl.year }年 ${tl.termname }</option>
                    </c:if>
                </c:forEach>
            </select>
            &nbsp;&nbsp;年级学科：
            <select id="fGradeSub">
            </select>
        </p>
        <ul id="fCourseList">
        </ul>
        <p class="t_c"><a href="javascript:addTeacherCourse(1);" class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;
            <a href="javascript:closeModel('followCourse_div');" class="an_public1">取&nbsp;消</a></p>
    </div>
</div>

<div id="teaching_materia_div" class="public_windows" style="display:none;">
    <h3><a href="javascript:closeModel('teaching_materia_div');" title="关闭"></a>选教材版本</h3>
    <div class="jxxt_float_jcbb">
        <p class="font-black">请选择本学科授课的教材版本，选择后搜索资源时会优先推荐该版本。</p>
        <ul id="teaching_materia">
        </ul>

        <p class="t_c ">
            <a id="addReportBtn" class="an_public1"  href="javascript:void(0);" onclick="selectMaterial();">确定</a>
        </p>
    </div>
</div>

<div class="public_windows" id="shareWindow" style="display: none;">
    <input type="hidden" id="courseid"/>
    <h3><a href="javascript:closeModel('shareWindow')" title="关闭"></a>分享专题</h3>
    <div class="jxxt_float_fxzt public_input">
        <p class="font-black"><input type="radio" name="share" id="cloudShare" value="2"> 云端教师</p>
        <p class="font-black"><input type="radio" name="share" id="selfShare" value="1"> 校内教师</p>
        <p class="t_c p_t_10"><a href="javascript:doShareCourse()" class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;<a href="javascript:closeModel('shareWindow')"  class="an_public1">取&nbsp;消</a></p>
    </div>
</div>
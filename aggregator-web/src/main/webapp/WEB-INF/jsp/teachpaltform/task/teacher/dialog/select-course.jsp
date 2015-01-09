<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript" src="js/resource/res_task.js"></script>
    <script type="text/javascript">
        var termid="${currtTerm.ref}";
        var global_gradeid;
        var global_subjectid;
        var courseid = "${param.courseid}";
        var pCourseList;
        $(function () {

            pCourseList = new PageControl( {
                post_url : 'teachercourse?m=getTchCourListAjax',
                page_id : 'page1',
                page_control_name : "pCourseList",
                post_form : document.pListForm,
                gender_address_id : 'pListaddress',
                http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 10, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "initItemList"
            });

            changeGrade('sel_subgrade');
        });


        function preeDoPageSub(pObj){
            var param={};
            param.termid=termid;
            param.gradeid=global_gradeid;
            param.subjectid=global_subjectid;
            param.atermid=termid;
            param.resid="${param.resid}";
            pObj.setPostParams(param);
        }
        function getInvestReturnMethod(rps){
            var html="";
            var classhtml="";
            if(rps!=null
                    &&rps.presult.list[0]!=null
                    &&rps.presult.list[0].length>0){
                $.each(rps.presult.list[0],function(idx,itm){
                    html+="<tr "+(idx%2==1?"class='trbg1'":"")+">";
                    html+="<td>";
                    html+=itm.coursename;
                    html+="</td>";
                    html+="<td>";
                    html+='<input type="radio" name="rdo_course" value="'+itm.courseid+'"/>';
                    html+="</td>";
                    html+="</tr>";
                });

                $("#a_sub_task").show();


                if(rps.presult.list[0].length>0){
                    pCourseList.setPagetotal(rps.presult.pageTotal);
                    pCourseList.setRectotal(rps.presult.recTotal);
                    pCourseList.setPageSize(rps.presult.pageSize);
                    pCourseList.setPageNo(rps.presult.pageNo);
                }else{
                    pCourseList.setPagetotal(0);
                    pCourseList.setRectotal(0);
                    pCourseList.setPageNo(1);
                }
                pCourseList.Refresh();
            }else{
                html+="<tr><td colspan='2'>没有数据!</td></tr>";
                $("#a_sub_task").hide();
            }

            $("#courseTable").html(html);
        }


        function sub_data(paperid){
            if(typeof paperid=='undefined'||isNaN(paperid)){
                alert('试卷标识错误!请刷新页面重试!');
                return;
            }
            if (typeof(courseid) == 'undefined' || courseid.length < 1) {
                alert('异常错误，系统未获取到课题标识!');
                return;
            }
            /*var resid=$("#hd_resdetailid").val();
            if(resid.length<1){
                alert('系统未获取到资源标识!');
                return;
            }*/
            if(!confirm("确认操作?"))return;
            $("#hd_pid").val(paperid);
            //显示相关数据
            returnValue =res_id;
            showTaskElementTopic(6);
            //关闭弹出层
            $.fancybox.close();

           /* $.ajax({
                url: 'tpres?doAddVideoPaper',
                type: 'post',
                data: {courseid: courseid, paperid: paperid,resid:resid},
                dataType: 'json',
                error: function () {
                    alert('网络异常!');
                },
                success: function (json) {
                   if(json.type=='success'){
                       load_resource(1,1,true);
                       $.fancybox.close();
                   }else{
                       alert(json.msg);
                   }
                }
            });*/
        }



        function loadDiv(type){
            $("#dv_paperDetail").hide();
            $("#dv_selectPaper").show();
            if(type==1){
                $("#li_1").addClass("crumb");
                $("#li_2").removeClass("crumb");
                $("#dv_addpaperchild").hide();
                $("#dv_selpaperchild").show();
            }else if(type==2){
                $("#li_2").addClass("crumb");
                $("#li_1").removeClass("crumb");
                $("#dv_selectPaper").hide();
                $("#dv_addpaperchild").show();
                changeGrade('add_subgrade');

                //toSaveCoursePage();
            }else if(type==3){
                $("#dv_selectPaper").hide();
                $("#dv_addpaperchild").hide();
                $("#dv_model").hide();
                $("#dv_paperDetail").hide();
                $("#dv_paperDetail").load(
                        "paper?m=editPaperQuestionModel&courseid=${param.courseid}&paperid="+tpid+"&isrelate=1"
                ,function(){
                            $("#dv_selectPaper").hide();
                            $("#dv_paperDetail").show();
                        });
            }else if(type==4){
                $("#dv_selectPaper").hide();
                $("#dv_course_parent").show();
                $("#dv_addCourse").hide();
                $("#teaching_materia_div").hide();
            }
        }
    /**
    * 新建试卷
    */
    function doSaveRelatePaper(){
        /**
         * 添加试卷
         */
            var papername=$("#papername");
            if(papername.val().Trim().length<1){
                alert('请输入试卷名称!');
                papername.focus();
                return;
            }
            $.ajax({
                url:"paper?doSubAddPaper",
                type:"post",
                data:{courseid:courseid,papername:papername.val()},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        paperid=tpid=rmsg.objList[0];
                        $("#dv_paper_content_child").html('');
                        $("#dv_paperDetail").load("paper?m=editPaperQuestionModel&courseid="+courseid+"&paperid="+paperid+"&isrelate=1",
                                function(){
                                    $("#paper_detail .float_title").remove();
                                    $("#paper_detail #dv_paperdetail").removeClass("jxxt_float_h600");
                                    $(".an_small").removeClass("an_small").addClass("an_public1");
                                    var len=$('div').filter(function(){return this.id.indexOf('dv_table')!=-1&&$(this).html().Trim().length>0}).length
                                    if(len<1){
                                        $("#a_sb_taskpaper").hide();
                                    }else
                                        $("#a_sb_taskpaper").show();
                                    $("#paper_detail").show();
                                    $("#dv_selectPaper").hide();
                                    $("#dv_paperDetail").show();

                        });

                    }
                }
            });

    }
    </script>


</head>
<body>
<%--试卷三级目录--%>
<input type="hidden" id="material_id"/>
<div class="public_float public_float960" id="dv_model" style="display:none">
    <div class="public_float public_float960"  id="dv_model_child">

    </div>
</div>
<div class="public_float public_float960" id="dv_course_parent">
    <p class="float_title"><strong>发布任务</strong></p>
    <div class="subpage_lm">
        <ul>
            <li class="crumb" id="li_1"><a href="javascript:;" onclick="loadDiv(1)">选择专题</a></li>
            <li  id="li_2"><a href="javascript:;" onclick="loadDiv(2)">新建专题</a></li>
        </ul>
    </div>

    <%--选择专题--%>
    <div  style="" id="dv_selectPaper">

        <div class="jxxt_float_w920 font-black">
            <c:if test="${!empty subGradeList}">
                <select id="sel_subgrade" onchange="changeGrade('sel_subgrade')">
                    <c:forEach items="${subGradeList}" var="c">
                        <option value="${c.gradeid}_${c.subjectid}">${c.gradevalue}${c.subjectname}</option>
                    </c:forEach>
                </select>
            </c:if>
        </div>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w350"/>
            <col class="w270"/>
            <col class="w90"/>
            <col class="w120"/>
            <col class="w120"/>
            <tr>
                <th>专题名称</th>
                <th>操作</th>
            </tr>
            <tr>
                <tbody id="courseTable">
                </tbody>
        </table>
        <form id="pListForm" name="pListForm"><p class="nextpage" id="pListaddress" align="center"></p></form>
        <p class="t_c"><a href="javascript:doAddPaperTask();" id="a_sub_task" class="an_small">提&nbsp;交</a></p>
    </div>

    <%--新建专题--%>
    <div id="dv_addpaperchild" style="display:none">

        <div class="jxxt_float_w920 font-black">
            <c:if test="${!empty gradeSubList}">
                <select id="add_subgrade" onchange="changeGrade('add_subgrade')">
                    <c:forEach items="${gradeSubList}" var="c">
                        <option value="${c.gradeid}_${c.subjectid}">${c.gradevalue}${c.subjectname}</option>
                    </c:forEach>
                </select>
            </c:if>
        </div>

        <div id="dv_addCourse">

        </div>
    </div>
</div>
<!--试卷详情-->
<div class="public_float public_float960" id="dv_paperDetail" style="display:none">

</div>
<!--专题教材-->
<div id="teaching_materia_div" class="public_float jxxt_float_jcbb" style="display:none;">
    <%--<p class="float_title"><strong>选教材版本</strong></p>--%>
    <p class="float_title"><a href="javascript:;" class="ico93" onclick="loadDiv(4)" title="返回"></a><span id="dv_model_mdname">新建专题</span></p>
    <p class="font-black">请选择本学科授课的教材版本，选择后搜索资源时会优先推荐该版本。</p>
    <ul id="teaching_materia">
    </ul>

    <p class="t_c"><a id="addReportBtn" class="an_public1"  href="javascript:selectMaterial();" onclick="">确定</a></p>
</div>
</body>
</html>

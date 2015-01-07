<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
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

            changeGrade();
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
                    html+="<td><p class='one'>";

                    html+='<a  href="javascript:void(0);" onclick="genderClick(\''+itm.courseid+'\')">'+itm.coursename+'</a>';
                    if(itm.islive>0)
                        html+='<a  target="_blank" href="'+itm.liveaddress+'"><b class="lm_ico08" title="直播课"></b></a>'; //<b class="lm_ico08" title="直播课"></b>
                    html+='</p></td>';
                    html+="<td>";
                    if(typeof(itm.classEntity)!='undefined'&&itm.classEntity.length>0){
                        if(itm.classEntity[0].CLASS_NAME!="0"){
                            $.each(itm.classEntity,function(n,classTemp){
                                html+="<p><span class='f_right'>"+classTemp.CLASS_TIME.substring(0,16)+"</span>"+classTemp.CLASS_NAME+"</p>";
                            });
                        }else{
                            html+="<p><a href='javascript:toUpdCoursePage("+itm.courseid+")' class='font-blue'>" +
                                    "<img width='15' height='15' src='images/an02_131126.png'/>设置</a></p>";
                        }
                    }else{
                        html+="<p><span class='f_right'>----</span>----</p>";
                    }
                    html+="</td>";
                    html+='<td><a  target="_blank" href="clsperformance?m=toIndex&courseid='+itm.courseid+'&classtype=1&subjectid='+global_subjectid+'&termid='+termid+'" class="font-blue">查看' ;
                    if(typeof(itm.courseScoreIsOverStr)!="undefined"&&itm.courseScoreIsOverStr.indexOf("1")!=-1){
                        html+='<span class="ico33"></span>';
                    }
                    html+='</a></td>';
                    html+="<td><a href='teachercourse?m=toClassCommentList&courseid="+itm.courseid+"&type=1' target='_blank'>"+itm.avgscore+"（"+itm.commentnum+"人）</a></td>";
                    html+="<td>";
                    if(itm.sharetype==2){
                        html+='<a class="ico21" title="已共享"></a>';
                    }else if(typeof itm.quoteid=='undefined'||itm.quoteid==0){
                        html+="<a href='javascript:shareCourse(\""+itm.courseid+"\","+itm.sharetype+")' class='ico20' title='共享'></a>";
                    }

                    html+="</td></tr>";
                });


                if(rps.presult.pageTotal<=1)
                    $('#pListaddress').hide();
                else
                    $('#pListaddress').show();


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
                html+="<tr><td colspan='5'>没有数据！</td></tr>";

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
                $("#dv_selpaperchild").hide();
                $("#dv_addpaperchild").show();
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
                $("#dv_addpaperchild").hide();
                $("#dv_model").hide();
                $("#dv_paperDetail").hide();
                $("#dv_content").load(
                        "task?m=toSelMicForTask&tasktype=6&courseid=${param.courseid}"
                        ,function(){
                            //$("#dv_selectPaper").hide();
                            //$("#dv_paperDetail").show();
                        });
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
<div class="public_float public_float960" id="dv_model" style="display:none">
    <div class="public_float public_float960"  id="dv_model_child">

    </div>
</div>
<div class="public_float public_float960" id="dv_selectPaper">
    <p class="float_title"><strong>发布任务</strong></p>
    <div class="subpage_lm">
        <ul>
            <li class="crumb" id="li_1"><a href="javascript:;" onclick="loadDiv(1)">选择专题</a></li>
            <li  id="li_2"><a href="javascript:;" onclick="loadDiv(2)">新建专题</a></li>
        </ul>
    </div>
    <%--选择试卷--%>
    <div  style="" id="dv_selCourse">
        <div class="jxxt_float_w920 font-black">
            <c:if test="${!empty subGradeList}">
            <select id="sel_subgrade" onchange="changeGrade()">
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
                <th>班级&mdash;开始时间</th>
                <th>课堂积分</th>
                <th>专题评价</th>
                <th>操作</th>
            </tr>
            <tr>
                <tbody id="courseTable">
                </tbody>
        </table>
        <form id="pListForm" name="pListForm"><p class="nextpage" id="pListaddress" align="center"></p></form>
    </div>
    <%--新建试卷--%>
    <div id="dv_addpaperchild" style="display:none">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input ">
            <col class="w90"/>
            <col class="w600"/>
            <tr>
                <th>试卷名称：</th>
                <td><input name="papername" id="papername" type="text" class="w350"/></td>
            </tr>
            <tr>
                <th>&nbsp;</th>
                <td><a href="javascript:;" onclick="doSaveRelatePaper()" class="an_public1">下一步</a></td>
            </tr>
        </table>
    </div>
</div>
<!--试卷详情-->
<div class="public_float public_float960" id="dv_paperDetail" style="display:none">

</div>
</body>
</html>

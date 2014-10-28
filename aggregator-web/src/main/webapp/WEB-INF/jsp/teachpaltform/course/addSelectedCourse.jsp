<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
        <script type="text/javascript">
            var srhType=1;
            var teacherid="${teacherid}";
            var global_gradeid=0;
            var global_subjectid=0;
            var materialid="${materialid}";
            var schoolname="${schoolname}";
            var subjectid="${subject.subjectid}";
            var gradeid="${grade.gradeid}";
            $(function(){
                //定位materialid 选中
                if(materialid.Trim().length>0){
                    $("#materialid option[value='"+materialid+"']").attr("selected",true);
                }

                srhMode(1);
                <c:if test="${!empty grade}">
                    $("#gradeid").val('${grade.gradeid}');
                    //$("#gradeid").attr("disabled",true);
                </c:if>
                $("#schoolname").val(schoolname);
                $("#gradeid").change(function(){
                  // pageGo('pList');
                });

                $("#subjectid").change(function(){
                  //  pageGo('pList');
                });

                $("#materialid").change(function(){
                    //pageGo('pList');
                });

                pList = new PageControl( {
                    post_url : 'teachercourse?m=getCourseLibraryListAjax',
                    page_id : 'page1',
                    page_control_name : "pList",
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
                pageGo('pList');
            });

            function preeDoPageSub(pObj){
                var param={};
                param.searchType=1;
                if(srhType==1){
                    if($("#materialid").val()!=0){
                        param.gradeid=$("#gradeid").val();
                        param.materialidvalues=$("#materialid").val();
                    }else{
                        param.gradeid=$("#gradeid").val();
                    }
                    if($("#coursename").val().Trim().length>0)
                        param.coursename=$("#coursename").val();
                    param.subjectid=subjectid;
                }
                if(srhType==2){
                    param.gradeid=$("#tgradeid").val();
                    var sname=$("#schoolname").val();
//                    if(schoolname!=sname){
//                        param.schoolname=sname;
//                    }
                    if($("#teachername").val().Trim().length>0)
                        param.teachername=$("#teachername").val();
                    param.subjectid=subjectid;
                }
                pObj.setPostParams(param);
            }

            function getInvestReturnMethod(rps){
                var html="";
                var materialid=$("#materialid").val();
                if (!(rps != null
                        && rps.presult.list != null
                        && rps.presult.list.length > 0)) {
                    html += "<tr><td colspan='5'>沒有数据！</td></tr>";
                    $("#srhTotal").html(0);
                } else {
                    $.each(rps.presult.list, function (idx, itm) {
                        html += "<tr>";
                        html += "<td><input id='courseid' name='courseid' type='radio' value='" + itm.courseid + "' />";

                        if(itm.cuserid==${userid})
                            html += "<span class='ico16' title='自建'></span>";
                        else if(itm.sourceType==1)
                            html += "<span class='ico18' title='标准'></span>";
                        else if(itm.sourceType==2||itm.sourceType==3)
                            html += "<span class='ico17' title='共享'></span>";
                        //else if(itm.sourceType==3)
                            //html += "<span class='ico44' title='参考'></span>";
                        html += "</td><td><p><a href='teachercourse?m=toCourseDetial&courseid=" + itm.courseid + "&materialid="+materialid+"' target='_self'>" + itm.coursename + "</a></p>";
                        html += "<p class='font-darkgray'>作者：" + (itm.schoolname==null?"北京四中网校":itm.schoolname) + "&nbsp;"+(itm.teachername==null?"":itm.teachername)+"</p>";
                        html += "<p>简介：" + (itm.introduction==null?"无":itm.introduction) + "</p>";
                        html += "<p class='t_r'>资源：<b>"+itm.rescount+"</b> 试题：<b>"+itm.quescount+"</b>试卷：<b>"+itm.papercount+"</b>论题：<b>"+itm.topiccount+"</b>任务：<b>"+itm.taskcount+"</b>";
                        for(var i=1;i<6;i++)
                            if(itm.avgscore>=i)
                                html += "<span class='ico_star1'></span>";
                            else if(itm.avgscore<i&&itm.avgscore>i-1)
                                html += "<span class='ico_star2'></span>";
                            else
                                html += "<span class='ico_star3'></span>";
                        html += itm.avgscore + "</p>";
                        html += "</p></td></tr>";
                    });
                    if (rps.presult.list.length > 0) {
                        pList.setPagetotal(rps.presult.pageTotal);
                        pList.setRectotal(rps.presult.recTotal);
                        pList.setPageSize(rps.presult.pageSize);
                        pList.setPageNo(rps.presult.pageNo);
                    } else {
                        pList.setPagetotal(0);
                        pList.setRectotal(0);
                        pList.setPageNo(1);
                    }
                    pList.Refresh();
                    $("#srhTotal").html(rps.presult.recTotal);
                }
                $("#courseTable").html(html);
            }

            //添加引用专题前的检查
            function checkCourse(){
                var courseid;
                $("input[name='courseid']:checked").each(function() {courseid=$(this).val();});
                $.ajax({
                    url:'teachercourse?m=checkQuoteCourse',
                    data:{
                        courseid:courseid
                    },
                    type:'POST',
                    dataType:'json',
                    error:function(){
                        alert('异常错误,系统未响应！');
                    },success:function(rps){
                        if(rps.type=="success"){
                            if(rps.objList!=null&&rps.objList.length>0){
                                var htm='';
                                htm+= '<h3><a href="javascript:closeModel(\'confirmCourseDiv\')"  title="关闭"></a>引用专题</h3>';
                                var courseid;
                                $.each(rps.objList,function(idx,itm){
                                   courseid=itm.courseid;
                                });
                                htm+='<p class="p_t_20 t_c"><input type="radio" name="checkQuote" value="1">&nbsp;继续使用';
                                htm+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="2" name="checkQuote">&nbsp;重新引用</p>';
                                htm+='<p class="p_t_20 t_c"><a href="javascript:subCheckQuote('+courseid+')"  class="an_public1">确&nbsp;定</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:closeModel(\'confirmCourseDiv\')"  class="an_public1">取&nbsp;消</a></p>';
                                $("#confirmCourseDiv").html(htm);
                                showModel('confirmCourseDiv');
                            }
                        }else{
                            addTeacherCourse();
                        }
                    }
                });
            }

            //提交选择后的引用
            function subCheckQuote(courseid){
                var radiovalue;
                $("input[name='checkQuote']:checked").each(function() {radiovalue=$(this).val();});
                if(radiovalue==1){
                    revertCourse(courseid);
                }else if(radiovalue==2){
                    deleteQuoteCourse(courseid);
                }
            }

            //执行恢复引用
            function revertCourse(courseid){
                $.ajax({
                    url:'teachercourse?m=revertCourse',//cls!??.action
                    dataType:'json',
                    type:'POST',
                    data:{courseid:courseid,
                        localstatus:1
                    },
                    cache: false,
                    error:function(){
                        alert('异常错误!系统未响应!');
                    },success:function(rps){
                        alert("添加成功!");
                        window.location.href="teachercourse?toTeacherCourseList&subjectid=${subject.subjectid}&gradeid=${grade.gradeid}";
                    }
                });
            }
            //重新引用，先删除回收站中的专题
            function deleteQuoteCourse(courseid){
                $.ajax({
                    url:'teachercourse?m=delQuoteCourse',//cls!??.action
                    dataType:'json',
                    type:'POST',
                    data:{courseid:courseid
                    },
                    cache: false,
                    error:function(){
                        alert('异常错误!系统未响应!');
                    },success:function(rps){
                       if(rps.type=="error"){
                           alert("删除引用专题出错，请刷新重试")
                       }else{
                           addTeacherCourse();
                       }
                    }
                });
            }

            //添加引用专题
            function addTeacherCourse(){
                $("#addP").html('<a class="an_small">添&nbsp;加</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:closeAddorUpdateWindow();" class="an_small">取&nbsp;消</a>');
                var term=$("#termid");
                var courseids=new Array();
                var subjectid=$("#subjectid");
                var gradeid=$("#gradeid");
                $("input[name='courseid']:checked").each(function() {courseids.push($(this).val());});
                if(term.val().Trim().length<1){
                    alert('学期参数错误!');
                    window.close();
                }
                if(gradeid.val().Trim().length<1){
                    alert('年级参数错误!');
                    window.close();
                }
                if(courseids.length<1 && courseids.length<1){
                    alert('请选择专题!');
                    return;
                }
//                $("#addButton").attr("href","");
//                $("#addButton").removeClass("an_small");
//                $("#addButton").addClass("an_gray_small");
                $.ajax({
                    url:'teachercourse?m=doAddQuoteCourse',
                    data:{
                        termid:term.val(),
                        subjectid:subjectid.val(),
                        gradeid:gradeid.val(),
                        courseids:courseids.join(','),
                        materialid:materialid
                    },
                    type:'POST',
                    dataType:'json',
                    error:function(){
                        alert('异常错误,系统未响应！');
                    },success:function(rps){
                        if(rps.type=="success"){
                            alert("添加成功!");
                            window.location.href="teachercourse?toTeacherCourseList&subjectid=${subject.subjectid}&gradeid=${grade.gradeid}";
                        }else{
                            alert("无法添加!"+rps.msg);
                        }
                    }
                });
            }

            function srhMode(n){
                $("#srhTotal").html('');
                $("#courseTable").html('');
                if(n==1){
                    srhType=1;
                    $('#courSrhType').show();
                    if(gradeid==1||gradeid==2||gradeid==3){
                        if(subjectid==2){
                            $("#wenli").show();
                        }
                    }
                    $('#tchSrhType').hide();
                    $("#li_c").addClass("crumb");
                    $("#li_t").removeClass("crumb");
                }
                if(n==2){
                    srhType=2;
                    $("#wenli").hide();
                    $('#courSrhType').hide();
                    $('#tchSrhType').show();
                    $("#li_t").addClass("crumb");
                    $("#li_c").removeClass("crumb");
                }
            }

            function closeAddorUpdateWindow(){
                if(typeof subjectid!='undefined'&&subjectid.length>0&&typeof gradeid!='undefined'&&gradeid.length>0){
                    window.location.href="teachercourse?toTeacherCourseList&subjectid="+subjectid+"&gradeid="+gradeid;
                }else{
                    window.location.href="teachercourse?toTeacherCourseList";
                }
            }

            function changeWenLi(){
                var type=$("#wenli").val();
                $.ajax({
                    url:'teachercourse?m=getMaterialByWenli',
                    data:{
                       gradeid:gradeid,
                        subjectid:subjectid,
                        wenli:type
                    },
                    type:'POST',
                    dataType:'json',
                    error:function(){
                        alert('异常错误,系统未响应！');
                    },success:function(rps){
                        var htm='';
                        if(rps.type=="success"){
                            $.each(rps.objList,function(idx,itm){
                                htm+="<option value='"+itm.materialid+"'> ";
                                htm+=itm.materialname;
                                if(itm.versionname!=null){
                                    htm+=itm.versionname;
                                }
                                htm+="</option>";
                            })
                            $("#materialid").html(htm);
                        }else{
                            alert("未知错误，请刷新页面重试");
                        }
                    }
                });
            }
            function getMaterial(){
                var gid = $("#gradeid").val();
                if(gid==1||gid==2||gid==3){
                    if(subjectid==2){
                        $("#wenli").show();
                    }else{
                        $("#wenli").hide();
                    }
                }else{
                    $("#wenli").hide();
                }
                $.ajax({
                    url:'teachercourse?m=getMaterial',
                    data:{
                        gradeid:gid,
                        subjectid:subjectid
                    },
                    type:'POST',
                    dataType:'json',
                    error:function(){
                        alert('异常错误,系统未响应！');
                    },success:function(rps){
                        var htm='';
                        if(rps.type=="success"){
                            $.each(rps.objList,function(idx,itm){
                                htm+="<option value='"+itm.materialid+"'> ";
                                htm+=itm.materialname;
                                if(itm.versionname!=null){
                                    if(itm.versionname!='其它')
                                        htm+=itm.versionname;
                                }
                                htm+="</option>";
                            })
                            $("#materialid").html(htm);
                        }else{
                            alert("未知错误，请刷新页面重试");
                        }
                    }
                });
                pageGo('pList');
            }
        </script>
</head>
    <body>
    <div id="confirmCourseDiv"  class="public_windows" style="display: none"></div>
    <input id="subjectid" type="hidden" value="${subject.subjectid}">
    <div class="subpage_head">
        <span class="back"><a href="javascript:closeAddorUpdateWindow();">返回</a></span>
        <span class="ico55"></span><strong>添加专题</strong>
    </div>
    <div class="subpage_nav">
        <ul>
            <li><a href="teachercourse?m=toSaveOrUpdate&subjectid=${subject.subjectid}&gradeid=${grade.gradeid}">新建专题</a></li>
            <li class="crumb"><a href="javascript:void(0);">引用专题</a></li>
        </ul>
    </div>
    <div class="content2">
        <div class="subpage_lm">
            <ul>
                <li id="li_c" class="crumb"><a href="javascript:srhMode(1);">按专题查</a></li>
                <li id="li_t"><a href="javascript:srhMode(2);">按教师查</a></li>
            </ul>
        </div>
        <div class="jxxt_add_zhuanti_info">
            <!-- 按专题搜索 -->
            <p id="courSrhType" class="public_input">
                <input id="termid" type="hidden" value="${termid}" />
                <select id="gradeid" name="gradeid" onchange="getMaterial();">
                    <c:if test="${!empty gradeList}">
                        <c:forEach var="grade" items="${ gradeList}">
                            <option value="${grade.gradeid}">${grade.gradename}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <select id="wenli" name="wenli" onchange="changeWenLi()" style="display: none">
                    <option value="0">全部</option>
                    <option value="1">文科</option>
                    <option value="2">理科</option>
                </select>
                <select id="materialid" name="materialid" onchange="pageGo('pList')">
                    <!--<option value="0">全部</option>-->
                    <c:forEach var="tm" items="${tmList}">
                        <option value="${tm.materialid}">${tm.materialname}
                            <c:if test="${!empty tm.versionname&&tm.versionname!='其它'}">
                                (${tm.versionname})
                            </c:if>
                        </option>
                    </c:forEach>
                </select>
                <%--文理：<input type="radio" name="radio" value="radio">文&nbsp;&nbsp;--%>
                <%--<input type="radio" name="radio" value="radio">理--%>
                <input id="coursename" name="coursename" type="text" class="w300" />
                <a href="javascript:pageGo('pList',1);" class="an_search" title="查询"></a></p>
            <!-- 按教师搜索 -->
            <p id="tchSrhType" class="public_input" style="display:none;">
                <%--<select name="schoolname" id="schoolname">--%>
                    <%--<c:if test="${!empty schoolList}">--%>
                        <%--<c:forEach var="itm" items="${schoolList}">--%>
                            <%--<option value="${itm.name}">${itm.name}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</c:if>--%>
                <%--</select>--%>
                <select id="tgradeid" name="tgradeid">
                    <c:if test="${!empty gradeList}">
                        <c:forEach var="grade" items="${ gradeList}">
                            <option value="${grade.gradeid}">${grade.gradename}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <input id="teachername" name="teachername" type="text" class="w300" />
                <a href="javascript:pageGo('pList',1);" class="an_search" title="查询"></a></p>
            <p class="font-darkblue">搜到的专题&nbsp;<span id="srhTotal" class="font-red">0</span></p>
        </div>
        <div class="jxxt_add_zhuanti_nr">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                <col class="w50"/>
                <col class="w860"/>
                <tbody id="courseTable">
                </tbody>
            </table>
            <form id="pListForm" name="pListForm"><p class="nextpage" id="pListaddress" align="center"></p></form>
        </div>
        <p class="p_tb_10 t_c" id="addP"><a id="addButton" href="javascript:checkCourse();" class="an_small">添&nbsp;加</a>&nbsp;&nbsp;&nbsp;&nbsp;</p>
            </div>
    <%@include file="/util/foot.jsp" %>
    </body>
</html>

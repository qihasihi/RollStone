<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-3-27
  Time: 下午4:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<%
    UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
    String userid=user.getRef();
%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var p1;
        var userid='<%=userid%>';
        $(function(){
            p1 = new PageControl( {
                post_url : 'teachercourse?m=getCourseRecycleList',
                page_id:'page1',
                page_control_name:'p1',
                post_form:document.page1form,
                gender_address_id:'page1address',
                http_operate_handler:questionListReturn,
                return_type:'json',
                page_no:1,
                page_size:20,
                rectotal:0,
                pagetotal:1,
                operate_id:"mainTbl"
            });
            pageGo("p1");
        });
    function revertcourse(courseid){
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
                alert(rps.msg)
                pageGo(p1);
            }
        });
    }
    function questionListReturn(rps){
        if(rps.type=='error'){
            alert(rps.msg);
            if (typeof (p1) != "undefined" && typeof (p1) == "object") {
                // 设置空间不可用
                p1.setPagetotal(1);
                p1.setRectotal(0);
                p1.Refresh();
                // 设置显示值
                var shtml = '<tr><td align="center" colspan="3">暂时没有被删除专题!';
                shtml += '</td></tr>';
                $("#mainTbl").html(shtml);

            }
        }else{
            var shtml = '';
            shtml+=	'<tr> <th>专题名称</th><th>删除时间</th><th>操作</th></tr>';
            if(rps.objList.length<1){
                shtml+="<tr><th colspan='8' style='height:65px' align='center'>暂无信息!</th></tr>";
            }else{
                $.each(rps.objList,function(idx,itm){
                    shtml+="<tr "+(idx%2==1?"class='trbg1'":"")+">";
                    shtml+="<td><p class='one'>";
                    if(itm.quoteid==0)
                        shtml+="<span class='ico16' title='自建'></span>";
                    else if(itm.quoteid!=0&&itm.qcuserid==userid)
                        shtml+="<span class='ico16' title='自建'></span>";
                    else if(itm.quoteid!=0&&itm.qcourselevel==1)
                        shtml+="<span class='ico18' title='标准'></span>";
                    else
                        shtml+="<span class='ico17' title='共享'></span>";

                    shtml+=itm.coursename+'</p></td>';
                    shtml+='<td>'+itm.mtimestring+'</td>';

                    if(itm.quoteid==0)
                        shtml+='<td><a href="javascript:revertcourse(\''+itm.courseid+'\')" class="ico25" title="恢复"></a></td>';
                    else if(itm.quoteid!=0&&itm.qcuserid==userid)
                        shtml+='<td><a href="javascript:revertcourse(\''+itm.courseid+'\')" class="ico25" title="恢复"></a></td>';
                    else if(itm.quoteid!=0&&itm.qcourselevel==1)
                        shtml+='<td><a href="javascript:checkCourse(\''+itm.courseid+'\','+itm.quoteid+')" class="ico25" title="恢复"></a></td>';
                    else
                        shtml+='<td><a href="javascript:checkCourse(\''+itm.courseid+'\','+itm.quoteid+')" class="ico25" title="恢复"></a></td>';
                    //shtml+='<td><a href="javascript:revertcourse(\''+itm.courseid+'\')" class="ico25" title="恢复"></a></td>';
                });
            }
            $("#mainTbl").hide();
            $("#mainTbl").html(shtml);
            $("#mainTbl").show('fast');
            //翻页信息
            if (typeof (p1) != "undefined" && typeof (p1) == "object") {
                p1.setPagetotal(rps.presult.pageTotal);
                p1.setRectotal(rps.presult.recTotal);
                p1.setPageSize(rps.presult.pageSize);
                p1.setPageNo(rps.presult.pageNo);
                p1.Refresh();
            }
        }
    }
        //标准专题选择恢复方式
        function checkCourse(courseid,quoteid){
            var htm = '';
            htm+='<input type="button" value="继续使用" onclick="revertcourse('+courseid+')"/>';
            htm+='<br/>';
            htm+='<input type="button" value="重新引用" onclick="deleteQuoteCourse('+courseid+','+quoteid+')"/>';
            $("#checkQuoteCourse").html(htm);
            showModel("checkQuoteCourse");
        }

        //重新引用，先删除回收站中的专题
        function deleteQuoteCourse(courseid,quoteid){
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
                        addQuoteCourse(quoteid);
                    }
                }
            });
        }

        //添加引用专题
        function addQuoteCourse(courseid){
           var termid = "${param.termid}";
            var subjectid ="${param.subjectid}";
            var gradeid = "${param.gradeid}";
            $.ajax({
                url:'teachercourse?m=doAddQuoteCourse',
                data:{
                    termid:termid,
                    subjectid:subjectid,
                    gradeid:gradeid,
                    courseids:courseid
                },
                type:'POST',
                dataType:'json',
                error:function(){
                    alert('异常错误,系统未响应！');
                },success:function(rps){
                    if(rps.type=="success"){
                        alert("添加成功!");
                        //window.location.href="teachercourse?toTeacherCourseList&subjectid=${param.subjectid}&gradeid=${param.gradeid}";
                        pageGo(p1);
                    }else{
                        alert("无法添加!"+rps.msg);
                    }
                }
            });
        }
    </script>
</head>
<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>
<div id="checkQuoteCourse" style="display: none"></div>
<div class="subpage_head"><span class="ico15"></span><strong>回收站</strong></div>
<div class="content2">
    <div class="jxxt_tab_layout">

<table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
    <col class="w700"/>
    <col class="w140"/>
    <col class="w100"/>

    <tbody id="mainTbl">
    </tbody>
</table>
    </div>

    <form id="page1form" name="page1form" method="post">
    <div class="nextpage" align="right" id="page1address"></div>
</form>

<%@include file="/util/foot.jsp" %>
</body>
</html>

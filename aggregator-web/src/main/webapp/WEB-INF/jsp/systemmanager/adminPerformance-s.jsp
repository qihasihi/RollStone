<!DOCTYPE HTML>
<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 15-1-11
  Time: 上午9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        function ajaxPerformance(){
            var gradeid = $("#selgrade").val();
            var subjectid = $("#selsubject").val();
            var classid = $("#selclass").val();
            var param={};
            if(gradeid.length>0&&gradeid!=-1){
                param.gradeid=gradeid;
            }else{
                alert("请选择年级");
                return;
            }
            if(subjectid.length>0&&subjectid!=-1){
                param.subjectid = subjectid;
            }else{
                alert("请选择学科");
                return;
            }
            if(classid.length>0&&classid!=-1){
                param.classid=classid;
            }else{
                alert("请选择班级");
                return;
            }
            $.ajax({
                url:'sysm?m=getAdminPerformanceStu',
                data:param,
                type:'POST',
                dataType:'json',
                error:function(){
                    alert('异常错误,系统未响应！');
                },success:function(rps){
                    if(rps.objList[0]!=null&&rps.objList[0].length>0){
                        var htmTop = '';
                        var htmMain = '';
                        var col = '';
                        var span = 0;
                        var w = 0;
                        $.each(rps.objList[0],function(idx,itm){
                            span++;
                            col+='<th title="'+itm.COLNAME+'">'+(idx+1)+'</th>';
                        });
                        htmTop+='<colgroup class="w100"></colgroup><colgroup span="'+span+'" class="w80"></colgroup>';
                        htmTop+='<tr>';
                        htmTop+='<th>姓名</th>';
                        htmTop+=col;
                        htmTop+='<th>总分</th>';
                        htmTop+='</tr>';
                        if(rps.objList[1]!=null&&rps.objList[1].length>0){

                            $.each(rps.objList[1],function(idx,itm){
                                htmMain+='<tr>';
                                $.each(itm,function(i,x){
                                    htmMain+='<td>'+x+'</td>';
                                });
                                htmMain+='</tr>';
                            });

                        }
                        htmMain=htmTop+htmMain;
                        w=100+(1+span)*80;
                        $("#mainTbl").css("width",w);
                        $("#mainTbl").html(htmMain);
                    }else{
                        $("#mainTbl").html("没有数据");
                    }
                }
            });
        }
        function getSubject(){
            var gradeid = $("#selgrade").val();
            $.ajax({
                url:'sysm?m=getAdminPerformanceSubject',
                data:{gradeid:gradeid},
                type:'POST',
                dataType:'json',
                error:function(){
                    alert('异常错误,系统未响应！');
                },success:function(rps){
                    if(rps.type=="success"){
                        var htm = '<option value="-1">请选择</option>';
                        if(rps.objList.length>0){
                            $.each(rps.objList,function(idx,itm){
                                htm+='<option value="'+itm.subjectid+'">'+itm.subjectname+'</option>';
                            })
                        }else{
                            htm+='<option value="-1">请选择</option>';
                        }
                        $("#selsubject").html(htm);
                    }else{
                        alert("操作失败，"+rps.msg);
                    }
                }
            });
        }

        function getSelClass(){
            var gradeid = $("#selgrade").val();
            var subjectid=$("#selsubject").val();
            $.ajax({
                url:'sysm?m=getAdminPerformanceClass',
                data:{gradeid:gradeid,subjectid:subjectid},
                type:'POST',
                dataType:'json',
                error:function(){
                    alert('异常错误,系统未响应！');
                },success:function(rps){
                    if(rps.type=="success"){
                        var htm = '<option value="-1">请选择</option>';
                        if(rps.objList.length>0){
                            $.each(rps.objList,function(idx,itm){
                                htm+='<option value="'+itm.classid+'">'+itm.classname+'</option>';
                            })
                        }else{
                            htm+='<option value="-1">请选择</option>';
                        }
                        $("#selclass").html(htm);
                    }else{
                        alert("操作失败，"+rps.msg);
                    }
                }
            });
        }
        function exprotStuPerformance(){
            var gradeid = $("#selgrade").val();
            var subjectid = $("#selsubject").val();
            var classid = $("#selclass").val();
            var param={};
            if(gradeid.length>0&&gradeid!=-1){
                param.gradeid=gradeid;
            }else{
                alert("请选择年级");
                return;
            }
            if(subjectid.length>0&&subjectid!=-1){
                param.subjectid = subjectid;
            }else{
                alert("请选择学科");
                return;
            }
            if(classid.length>0&&classid!=-1){
                param.classid=classid;
            }else{
                alert("请选择班级");
                return;
            }
            $("#gradeid").val(gradeid);
            $("#subjectid").val(subjectid);
            $("#classid").val(classid);
            $("#exportExcelForm").attr("action","sysm?m=exportAdminPerformanceStu");
            $("#exportExcelForm").submit();
        }
    </script>
</head>
<body>
<%@include file="/util/head.jsp" %>
<%@include file="/util/nav-base.jsp" %>
<div id="nav">
    <ul>
        <li><a href="user?m=list">用户管理</a></li>
        <li><a href="cls?m=list">组织管理</a></li>
        <li><a href="sysm?m=logoconfig">系统设置</a></li>
        <li class="crumb"><a href="sysm?m=toAdminPerformance">使用统计</a></li>
    </ul>
</div>

<div class="content2">
    <div class="subpage_lm">
        <ul>
            <li ><a href="sysm?m=toAdminPerformance">教师统计</a></li>
            <li class="crumb"><a href="sysm?m=toAdminPerformanceStu">学生统计</a></li>
        </ul>
    </div>

    <div class="jcpt_sytj public_input">
        <p class="font-black">
            年级：
            <select id="selgrade" onchange="getSubject()" class="w120">
                <c:if test="${!empty gradeList}">
                    <option value="-1">请选择</option>
                    <c:forEach items="${gradeList}" var="itm">
                        <option value="${itm.gradeid}">${itm.gradename}</option>
                    </c:forEach>
                </c:if>
            </select>
            &nbsp;&nbsp;学科：
            <select id="selsubject" onchange="getSelClass()" class="w100">
                <option></option>
            </select>
            &nbsp;&nbsp;班级：
            <select id="selclass" class="w100">

            </select>
            <a href="javascript:ajaxPerformance()" class="an_search" title="查询"></a>
        </p>
        <p><a href="javascript:exprotStuPerformance();" class="font-darkblue">导出</a></p>
        <div class="tab_layout2">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab2" style="width:740px" id="mainTbl">

            </table>
        </div>
    </div>
</div>
<div style="display: none">
    <form id="exportExcelForm" nam="exportExcelForm" method="post">
        <input id="gradeid" name="gradeid"/>
        <input id="subjectid" name="subjectid"/>
        <input id="classid" name="classid"/>
    </form>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

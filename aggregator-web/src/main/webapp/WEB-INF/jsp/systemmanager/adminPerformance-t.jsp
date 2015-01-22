<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 15-1-9
  Time: 上午10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-yhqx.jsp"%>
<html>
<head>
    <title>教务统计--教师</title>
    <script type="text/javascript">
        var pList;
        $(function(){
            pList = new PageControl( {
                post_url : 'sysm?m=getAdminPerformance',
                page_id : 'page1',
                page_control_name : "pList",
                post_form : document.pListForm,
                gender_address_id : 'pListaddress',
                http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
                http_operate_handler : ajaxPerformance, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 5, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "mainTbl"
            });
        });
        function preeDoPageSub(pObj){
            var btime = $("#beginTime").val();
            var etime = $("#endTime").val();
            var gradeid = $("#selgrade").val();
            var subjectid = $("#selsubject").val();
            var classid = $("#selclass").val();

            var param={};
            if(btime.length>0&&etime.length>0){
                param.btime=btime;
                param.etime=etime;
            }else{
                alert("请选择开始结束时间");
                return;
            }
            if(gradeid.length>0){
                param.gradeid=gradeid;
            }else{
                alert("请选择年级");
                return;
            }
            if(subjectid.length>0){
                param.subjectid = subjectid;
            }else{
                alert("请选择学科");
                return;
            }
            if(classid.length>0){
                param.classid=classid;
            }else{
                alert("请选择班级");
                return;
            }
            pObj.setPostParams(param);
        }
        function ajaxPerformance(rps){
                if(rps.type=="success"){
                    var thhtm='';
                    var htm='';
                    thhtm+='<tr><th>专题</th><th>任务数</th><th>已结束任务</th><th>任务完成率</th>' +
                            '<th>专题评价</th><th>资源学习</th><th>互动交流</th><th>微课程</th><th>成卷测试</th><th>自主测试</th><th>直播课</th><th>试题</th><th>一般任务</th></tr>';
                    if(rps.objList[0]!=null&&rps.objList[0].length>0){
                        $.each(rps.objList[0],function(idx,itm){
                            //查询到的数据
                            htm+='<tr>';
                            htm+='<td>'+itm.coursename+'</td>';
                            htm+='<td>'+(isNaN(itm.tasknum)?0:itm.tasknum)+'</td>';
                            htm+='<td>'+itm.endtasknum+'</td>';
                            htm+='<td>'+itm.completerate+'</td>';
                            htm+='<td>'+itm.evaluation.replace("|","<br>")+'人</td>';
                            /*htm+='<td>'+itm.resourcetask.replace("|","<br>(")+'%)</td>';
                            htm+='<td>'+itm.interactivetask.replace("|","<br>(")+'%)</td>';
                            htm+='<td>'+itm.microtask.replace("|","<br>(")+'%)</td>';
                            htm+='<td>'+itm.coilingtesttask.replace("|","<br>(")+'%)</td>';
                            htm+='<td>'+itm.selftesttask.replace("|","<br>(")+'%)</td>';
                            htm+='<td>'+itm.livetask.replace("|","<br>(")+'%)</td>';
                            htm+='<td>'+itm.questask.replace("|","<br>(")+'%)</td>';
                            htm+='<td>'+itm.generaltask.replace("|","<br>(")+'%)</td>';*/
                            htm+='<td>'+itm.resourcetask.replace("|",'<br>(')+')</td>';
                            htm+='<td>'+itm.interactivetask.replace("|",'<br>(')+')</td>';
                            htm+='<td>'+itm.microtask.replace("|",'<br>(')+')</td>';
                            htm+='<td>'+itm.coilingtesttask.replace("|",'<br>(')+')</td>';
                            htm+='<td>'+itm.selftesttask.replace("|",'<br>(')+')</td>';
                            htm+='<td>'+itm.livetask.replace("|",'<br>(')+')</td>';
                            htm+='<td>'+itm.questask.replace("|",'<br>(')+')</td>';
                            htm+='<td>'+itm.generaltask.replace("|",'<br>(')+')</td>';

                            htm+='</tr>';
                        });
                        var top = '';
                        if(rps.objList[1]!=null&&rps.objList.length>0){
                            var totalNum=0;
                            var totalCourseName='总数';
                            var totalTaskNum = 0;
                            var totalEndTaskNum=0;
                            var totalCompleteRate=0.00;
                            var totalEvaluationAvg =0;
                            var totalEvaluationPeo = 0;
                            var totalResourceTaskNum=0;
                            var totalResourceTaskRate=0.00;
                            var totalInteractiveTaskNum=0;
                            var totalInteractiveTaskRate=0.00;
                            var totalMicroTaskNum = 0;
                            var totalMicroTaskRate = 0.00;
                            var totalCoilingtestTaskNum=0;
                            var totalCoilingtestTaskRate=0.00;
                            var totalSelftestTaskNum = 0;
                            var totalSelftestTaskRate = 0.00;
                            var totalLiveTaskNum=0;
                            var totalLiveTaskRate = 0.00;
                            var totalQuesTaskNum = 0;
                            var totalQuesTaskRate = 0.00;
                            var totalGeneralTaskNum = 0;
                            var totalGeneralTaskRate=0.00;
                            $.each(rps.objList[1],function(idx,itm){
                                //组织总数的数据
                                totalNum++;
                                totalTaskNum+=parseInt(isNaN(itm.tasknum)?0:itm.tasknum);
                                totalEndTaskNum+=parseInt(isNaN(itm.endtasknum)?0:itm.endtasknum);
                                totalCompleteRate+=parseFloat((isNaN(itm.completerate.split("%")[0])?0:itm.completerate.split("%")[0]));
                                totalEvaluationAvg+=parseFloat((isNaN(itm.evaluation.split("|")[0])?0:itm.evaluation.split("|")[0]));
                                totalEvaluationPeo+=parseInt((isNaN(itm.evaluation.split("|")[1])?0:itm.evaluation.split("|")[1]));
                                totalResourceTaskNum+=parseInt((isNaN(itm.resourcetask.split("|")[0])?0:itm.resourcetask.split("|")[0]));
                                totalResourceTaskRate+=parseFloat((isNaN(itm.resourcetask.split("|")[1])?0:itm.resourcetask.split("|")[1]));
                                totalInteractiveTaskNum+=parseInt((isNaN(itm.interactivetask.split("|")[0])?0:itm.interactivetask.split("|")[0]));
                                totalInteractiveTaskRate+=parseFloat((isNaN(itm.interactivetask.split("|")[1])?0:itm.interactivetask.split("|")[1]));
                                totalMicroTaskNum+=parseInt((isNaN(itm.microtask.split("|")[0])?0:itm.microtask.split("|")[0]));
                                totalMicroTaskRate+=parseFloat((isNaN(itm.microtask.split("|")[1])?0:itm.microtask.split("|")[1]));
                                totalCoilingtestTaskNum+=parseInt((isNaN(itm.coilingtesttask.split("|")[0])?0:itm.coilingtesttask.split("|")[0]));
                                totalCoilingtestTaskRate+=parseFloat((isNaN(itm.coilingtesttask.split("|")[1])?0:itm.coilingtesttask.split("|")[1]));
                                totalSelftestTaskNum+=parseInt((isNaN(itm.selftesttask.split("|")[0])?0:itm.selftesttask.split("|")[0]));
                                totalSelftestTaskRate+=parseFloat((isNaN(itm.selftesttask.split("|")[1])?0:itm.selftesttask.split("|")[1]));
                                totalLiveTaskNum+=parseInt((isNaN(itm.livetask.split("|")[0])?0:itm.livetask.split("|")[0]));
                                totalLiveTaskRate+=parseFloat((isNaN(itm.livetask.split("|")[1])?0:itm.livetask.split("|")[1]));
                                totalQuesTaskNum+=parseInt((isNaN(itm.questask.split("|")[0])?0:itm.questask.split("|")[0]));
                                totalQuesTaskRate+=parseFloat((isNaN(itm.questask.split("|")[1])?0:itm.questask.split("|")[1]));
                                totalGeneralTaskNum+=parseInt((isNaN(itm.generaltask.split("|")[0])?0:itm.generaltask.split("|")[0]));
                                totalGeneralTaskRate+=parseFloat((isNaN(itm.generaltask.split("|")[1])?0:itm.generaltask.split("|")[1]));
                            });
                            top+='<tr>';
                            top+='<td>'+totalCourseName+'</td>';
                            top+='<td>'+totalTaskNum+'</td>';
                            top+='<td>'+totalEndTaskNum+'</td>';
                            top+='<td>'+(totalCompleteRate/totalNum).toFixed(2)+'%</td>';
                            top+='<td>'+(totalEvaluationAvg/totalNum).toFixed(2)+'<br>('+totalEvaluationPeo+')</td>';
                            top+='<td>'+totalResourceTaskNum+'<br>('+(totalResourceTaskRate/totalNum).toFixed(2)+'%)</td>';
                            top+='<td>'+totalInteractiveTaskNum+'<br>('+(totalInteractiveTaskRate/totalNum).toFixed(2)+'%)</td>';
                            top+='<td>'+totalMicroTaskNum+'<br>('+(totalMicroTaskRate/totalNum).toFixed(2)+'%)</td>';
                            top+='<td>'+totalCoilingtestTaskNum+'<br>('+(totalCoilingtestTaskRate/totalNum).toFixed(2)+'%)</td>';
                            top+='<td>'+totalSelftestTaskNum+'<br>('+(totalSelftestTaskRate/totalNum).toFixed(2)+'%)</td>';
                            top+='<td>'+totalLiveTaskNum+'<br>('+(totalLiveTaskRate/totalNum).toFixed(2)+'%)</td>';
                            top+='<td>'+totalQuesTaskNum+'<br>('+(totalQuesTaskRate/totalNum).toFixed(2)+'%)</td>';
                            top+='<td>'+totalGeneralTaskNum+'<br>('+(totalGeneralTaskRate/totalNum).toFixed(2)+'%)</td>';
                            top+='</tr>';
                        }
                    }else{
                        htm+='<tr><td colspan="15">当前班级暂无数据</td></tr>';
                    }
                    htm=thhtm+top+htm;
                    $("#mainTbl").html(htm);
                }else{
                    alert("操作失败，"+rps.msg);
                }
            if(rps.presult.pageTotal<=1)
                $('#pListaddress').hide();
            else
                $('#pListaddress').show();


            if(rps.presult.list[0]!=null&&rps.presult.list[0].length>0){
                pList.setPagetotal(rps.presult.pageTotal);
                pList.setRectotal(rps.presult.recTotal);
                pList.setPageSize(rps.presult.pageSize);
                pList.setPageNo(rps.presult.pageNo);
            }else{
                pList.setPagetotal(0);
                pList.setRectotal(0);
                pList.setPageNo(1);
            }
            pList.Refresh();
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
    function exportExcel(){
        var btime = $("#beginTime").val();
        var etime = $("#endTime").val();
        var gradeid = $("#selgrade").val();
        var subjectid = $("#selsubject").val();
        var classid = $("#selclass").val();

        var param={};
        if(btime.length>0&&etime.length>0){
            param.btime=btime;
            param.etime=etime;
        }else{
            alert("请选择开始结束时间");
            return;
        }
        if(gradeid.length>0){
            param.gradeid=gradeid;
        }else{
            alert("请选择年级");
            return;
        }
        if(subjectid.length>0){
            param.subjectid = subjectid;
        }else{
            alert("请选择学科");
            return;
        }
        if(classid.length>0){
            param.classid=classid;
        }else{
            alert("请选择班级");
            return;
        }
//
        $("#btime").val(btime);
        $("#etime").val(etime);
        $("#gradeid").val(gradeid);
        $("#subjectid").val(subjectid);
        $("#classid").val(classid);
        $("#exportExcelForm").attr("action","sysm?m=exportAdminPerformance");
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
            <li class="crumb"><a href="sysm?m=toAdminPerformance">教师统计</a></li>
            <li><a href="sysm?m=toAdminPerformanceStu">学生统计</a></li>
        </ul>
    </div>

    <div class="jcpt_sytj public_input">
        <p class="font-black">
            开始时间范围：
            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="beginTime"  name="beginTime" type="text" class="w140" />
            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',skin:'whyGreen'})" readonly="readonly"   id="endTime" name="endTime" type="text" class="w140" />
            &nbsp;&nbsp;年级：
            <select id="selgrade" onchange="getSubject()" class="w120">
                <option value="-1">请选择</option>
                <c:if test="${!empty gradeList}">
                    <c:forEach items="${gradeList}" var="itm">
                        <option value="${itm.gradeid}">${itm.gradename}</option>
                    </c:forEach>
                </c:if>
            </select>
            &nbsp;&nbsp;学科：
            <select id="selsubject" onchange="getSelClass()" clas="w100">
                <option></option>
            </select>
            &nbsp;&nbsp;班级：
            <select id="selclass" class="w100">

            </select>
            <a href="javascript:pageGo('pList');" class="an_search" title="查询"></a>
        </p>
        <p><a href="javascript:exportExcel();" class="font-darkblue">导出</a></p>
        <div class="tab_layout1">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
                <colgroup class="w140"></colgroup>
                <colgroup class="w50"></colgroup>
                <colgroup span="11" class="w70"></colgroup>
                <tbody id="mainTbl"></tbody>
            </table>
            <form id="pListForm" name="pListForm"><p class="nextpage" id="pListaddress" align="center"></p></form>
        </div>
    </div>
        </div>

<div style="display: none">
    <form id="exportExcelForm" nam="exportExcelForm" method="post">
        <input id="btime" name="btime"/>
        <input id="etime" name="etime"/>
        <input id="gradeid" name="gradeid"/>
        <input id="subjectid" name="subjectid"/>
        <input id="classid" name="classid"/>
    </form>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

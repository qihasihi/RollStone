<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-3-28
  Time: 下午3:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<%
    UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
    String username=user.getRealname();
%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var term_id="${param.termid}";
        var subject_id="${param.subjectid}";
        $(function(){
            getPerformance();
        });
        function getPerformance(){
            var course_id = $("#courseSel").val();
            $.ajax({
                url:'task?m=stuSelfPerformance',//cls!??.action
                dataType:'json',
                type:'POST',
                data:{courseid:course_id,termid:term_id,subjectid:subject_id
                },
                cache: false,
                error:function(){
                    alert('异常错误!系统未响应!');
                },success:function(rps){
                    $.each(rps.objList[1],function(step,obj){
                        var totalnum=obj.TOTALNUM;
                        var rightnum=obj.RIGHTNUM;
                        var fn =parseFloat(parseInt(rightnum))/parseInt(totalnum)*100;
                        var ufn=parseFloat(parseInt(totalnum-rightnum))/parseInt(totalnum)*100;
                        var finishhtml= fn.toFixed(2);
                        finishhtml+="%";
                        var ufinishhtml=ufn.toFixed(2);
                        ufinishhtml+="%";
                        $("#complete").html(finishhtml);
                        $("#uncomplete").html(parseInt(totalnum-rightnum));
                    });

                    var arrObj={};
                    var shtml='';
                    $("#mainTbl").html(shtml);
                    $.each(rps.objList[0],function(idx,itm){
                        if($("tr[id='tr_"+itm.COURSE_ID+"']").length<1){
                            shtml='<tr id="tr_'+itm.COURSE_ID+'">';
                            shtml+='<td id="td_'+itm.COURSE_ID+'">'+itm.COURSE_NAME+'</td>';
                            shtml+='</tr>';
                            $("#mainTbl").append(shtml);
                        }
                    });


                    $.each(rps.objList[0],function(ix,im){
                        var typename="";
                        var timestring = im.C_TIME;
                        timestring = timestring.substring(0,timestring.lastIndexOf("."));
                        if(im.TASK_TYPE==1){
                            typename="资源学习";
                        }else if(im.TASK_TYPE==2){
                            typename="互动交流";
                        }else if(im.TASK_TYPE==3){
                            typename="课后作业";
                        }
                        var status="";
                        if(im.STATUS>0){
                            status='<span class="ico12" title="完成"></span>';
                        }else{
                            status='<span class="ico24" title="未完成"></span>';
                        }
                        if($("#tr_"+im.COURSE_ID+" td").length<2){
                            var content="";
                            content=im.ANSWERCONTENT;
                            if(content==null||typeof(content)=="undefined"||typeof(content)==undefined){
                                content="";
                            }
                            var h='<td>任务'+''+(ix+1)+''+typename+'</td>';
                            h+='<td>'+timestring+'</td>';
                            h+='<td>'+content+'</td>';
                            h+='<td>'+status+'</td>';
                            $("#tr_"+im.COURSE_ID).append(h);
                        }else{
                            var content="";
                            content=im.ANSWERCONTENT;
                            if(content==null||typeof(content)=="undefined"||typeof(content)==undefined){
                                content="";
                            }
                            var h='<tr>';
                            h+='<td>任务'+''+(ix+1)+''+typename+'</td>';
                            h+='<td>'+timestring+'</td>';
                            h+='<td>'+content+'</td>';
                            h+='<td>'+status+'</td>';
                            h+='</tr>';

                            var rowspan=$("#td_"+im.COURSE_ID).attr("rowspan");
                            $("#td_"+im.COURSE_ID).attr("rowspan",(typeof(rowspan)=="undefined"?0:parseInt(rowspan))+1);
                            $("#tr_"+im.COURSE_ID).after(h);
                        }
                    });

                }
            });
        }
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico19"></span><strong>个人任务统计—— <%=username%></strong></div>
<div class="content1">
    <p class="public_input font-black">按专题：
        <select  onchange="getPerformance()" id="courseSel">
            <option selected value="0">全部</option>
            <c:if test="${!empty course}">
                <c:forEach items="${course}" var="itm">
                    <option value="${itm.courseid}">${itm.coursename}</option>
                </c:forEach>
            </c:if>

        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;任务完成率：<span id="complete"></span>&nbsp;&nbsp;&nbsp;&nbsp;未完成任务数：<span id="uncomplete"></span> </p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <col class="w240"/>
        <col class="w150"/>
        <col class="w140"/>
        <col class="w350"/>
        <col class="w60"/>
        <tr>
            <th>专题</th>
            <th>任务序号_任务类型</th>
            <th>学习时间</th>
            <th>作答内容</th>
            <th>完成情况</th>
        </tr>
        <tbody id="mainTbl">
        <tr>
            <td rowspan="2" class="v_c"><p>好好学习天天向上专题</p></td>
            <td><p>任务1 删除主帖的评论</p></td>
            <td>2013-11-11 10:36 </td>
            <td><p>我风纪扣大家立刻假大空连脚裤垃圾开发就可怜见阿拉科代开发就近积分捐款捐巨款就就近口令句打法克拉夫卡风口浪尖房打开富克拉风景点考虑萨反复拉锯法拉就风</p></td>
            <td class="v_c"><span class="ico12" title="完成"></span></td>
        </tr>
        <tr class="trbg1">
            <td><p>任务2 删除主帖</p></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td class="v_c"><span class="ico24" title="进行中"></span></td>
        </tr>
        <tr>
            <td rowspan="2">&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr class="trbg1">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        </tbody>
    </table>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

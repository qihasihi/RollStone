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
//    UserInfo user=(UserInfo)request.getSession().getAttribute("CURRENT_USER");
//    String username=user.getRealname();
%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var term_id="${param.termid}";
        var subject_id="${param.subjectid}";
        var userid="${userid}";
        $(function(){
            getPerformance();
        });
        function nowTime()
        {
            var now = new Date();

            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日

            var hh = now.getHours();            //时
            var mm = now.getMinutes();          //分
            var ss = now.getSeconds();          //秒

            var clock = year + "-";

            if(month < 10)
                clock += "0";

            clock += month + "-";

            if(day < 10)
                clock += "0";

            clock += day + " ";

            if(hh < 10)
                clock += "0";

            clock += hh + ":";
            if (mm < 10)
                clock += '0';

            clock += mm+":";
            if(ss<10)
                clock+='0';
            clock+=ss;
            return(clock);
        }


        function getPerformance(){
            var course_id = $("#courseSel").val();
            var p={courseid:course_id,termid:term_id,subjectid:subject_id};
            <c:if test="${!empty param.userid}">
                p.userid="${param.userid}";
            </c:if>
            $.ajax({
                url:'task?m=stuSelfPerformance',//cls!??.action
                dataType:'json',
                type:'POST',
                data:p,
                cache: false,
                error:function(){
                    alert('异常错误!系统未响应!');
                },success:function(rps){
                    $.each(rps.objList[1],function(step,obj){
//                        var totalnum=obj.TOTALNUM;
//                        var rightnum=obj.RIGHTNUM;
//                        var fn =parseFloat(parseInt(rightnum))/parseInt(totalnum)*100;
//                        var ufn=parseFloat(parseInt(totalnum-rightnum))/parseInt(totalnum)*100;
//                        var finishhtml= fn.toFixed(2);
//                        finishhtml+="%";
//                        var ufinishhtml=ufn.toFixed(2);
//                        ufinishhtml+="%";
                        $("#totalNum").html(obj.totalNum);
                        $("#completeNum").html(obj.completeNum);
                        $("#unBeginNum").html(obj.unBeginNum);
                        $("#endUnCompleteNum").html(obj.endUnCompleteNum);
                        $("#unCompleteNum").html(obj.unCompleteNum);
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

                    var zhongjianshu =0;
                    $.each(rps.objList[0],function(ix,im){
                        var typename="";
                        var details="";
                        var timestring = im.C_TIME || "";
                        var now=nowTime();
                        timestring = timestring.substring(0,timestring.lastIndexOf("."));
                        if(im.TASK_TYPE==1){
                            typename="资源学习";
                            if(im.RESOURCE_TYPE==1){
                                 details="tpres?toTeacherIdx&courseid="+im.COURSE_ID+"&tpresdetailid="+im.TASK_VALUE_ID+"&taskid="+im.TASK_ID;
                            }else{
                                if(im.REMOTE_TYPE==1){
                                    details='tpres?m=toRemoteResourcesDetail&hd_res_id='+im.TASK_VALUE_ID;
                                }else{
                                    details='tpres?m=toRemoteResourcesDetail&res_id='+im.TASK_VALUE_ID;
                                }
                            }
                        }else if(im.TASK_TYPE==2){
                            typename="互动交流";
                            details="tptopic?m=toDetailTopic&topicid="+im.TASK_VALUE_ID;
                        }else if(im.TASK_TYPE==3){
                            typename="试题";
                            details="question?m=todetail&id="+im.TASK_VALUE_ID+"&courseid="+im.COURSE_ID;
                        }else if(im.TASK_TYPE==4){
                            typename="成卷测试";
                            details="paperques?m=toTestPaper&courseid="+im.COURSE_ID+"&taskid="+im.TASK_ID+"&paperid="+im.TASK_VALUE_ID+"";
                        }else if(im.TASK_TYPE==5){
                            typename="自主测试";
                            details="paper?m=genderZiZhuPaper&taskid="+im.TASK_ID;
                        }else if(im.TASK_TYPE==6){
                            typename="微课程学习";
                            details="paperques?m=toTestPaper&courseid="+im.COURSE_ID+"&taskid="+im.TASK_ID+"&paperid="+im.TASK_VALUE_ID+"";
                        }


                        var status="";
                        if(im.STATUS>0){
                            status='<span class="ico12" title="完成"></span>';
                        }else{
                            if(validateTwoDate(now,im.E_TIME)){
                                status='<span class="ico24" title="进行中"></span>';
                            }else{
                                status='<span class="ico24b" title="已结束"></span>';
                            }
                        }

                        var  content=im.ANSWERCONTENT || "";
                        if(content==null||typeof(content)=="undefined"||typeof(content)==undefined){
                            if(im.STATUS>0){
                                if(parseInt(im.TASK_TYPE)==1||parseInt(im.TASK_TYPE)==2){
                                    content="已查看";
                                }else if(parseInt(im.TASK_TYPE)==4||parseInt(im.TASK_TYPE)==5){
                                    content="已提交";
                                }
                            }else{
                                content="";
                            }
                        }
                       if($("#tr_"+im.COURSE_ID+" td").length<2){
                           zhongjianshu=ix;
                           var h='<td>任务'+''+1+''+typename+'</td>';
                           h+='<td>'+timestring+'</td>';
                           h+='<td>'+content+'</td>';
                           h+='<td class="v_c"><a href="'+details+'" class="ico46" title="浏览详情"></a></td> ';
                           h+='<td>'+status+'</td>';
                           $("#tr_"+im.COURSE_ID).append(h);
                       }else{
                          // alert("中间数"+zhongjianshu+"---专题"+im.COURSE_ID+"---标示"+ix);

                           var h='<tr id="tr_'+im.COURSE_ID+'">';
                           h+='<td>任务'+(ix+1-zhongjianshu)+typename+'</td>';
                           h+='<td>'+timestring+'</td>';
                           h+='<td>'+content+'</td>';
                           h+='<td class="v_c"><a href="'+details+'" class="ico46" title="浏览详情"></a></td> ';
                           h+='<td>'+status+'</td>';
                           h+='</tr>';

                           var rowspan=$("#td_"+im.COURSE_ID).attr("rowspan");
                           $("#td_"+im.COURSE_ID).attr("rowspan",(typeof(rowspan)=="undefined"?0:parseInt(rowspan))+1);
                           $("tr[id='tr_"+im.COURSE_ID+"']:last").after(h);

                       }
                    });

                }
            });
        }

        function tabCutover(n){
            $("#tabs").children().attr("class","");
            $("#content").children().hide();
            if(n==3){
                $("#li_class_tab").attr("class","crumb");
                $("#classstu").show();
            }else if(n==2){
                $("#li_group_tab").attr("class","crumb");
                $("#groupstu").show();
            }else{
                $("#li_self_tab").attr("class","crumb");
                $("#selfdiv").show();
            }
        }
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico19"></span><strong>个人任务统计—— ${username}</strong></div>


        <c:if test="${param.userid==null}">
<div class="subpage_nav">
            <ul id="tabs">
            <li id="li_self_tab" class="crumb"><a href="javascript:tabCutover(1)">个人</a></li>
            <li id="li_group_tab"><a href="javascript:tabCutover(2)">小组</a></li>
            <li id = "li_class_tab"><a href="javascript:tabCutover(3)">班级</a></li>
            </ul>
</div>
        </c:if>


<div class="content1" id="content">
    <div id="selfdiv">
    <p class="public_input font-black">按专题：
        <select  onchange="getPerformance()" id="courseSel">
            <option selected value="0">全部</option>
            <c:if test="${!empty course}">
                <c:forEach items="${course}" var="itm">
                    <option value="${itm.courseid}">${itm.coursename}</option>
                </c:forEach>
            </c:if>

        </select><br/>
        总数：<span id="totalNum" style="color: red"></span>&nbsp;&nbsp;完成任务数：<span id="completeNum" style="color: red"></span>  &nbsp;&nbsp;未开始任务数：<span id="unBeginNum" style="color: red"></span>&nbsp;&nbsp;已结束未完成任务数：<span id="endUnCompleteNum" style="color: red"></span>&nbsp;&nbsp;已开始未做任务数：<span id="unCompleteNum" style="color: red"></span></p>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <col class="w240"/>
        <col class="w140"/>
        <col class="w140"/>
        <col class="w300"/>
        <col class="w60"/>
        <col class="w60"/>

        <tr>
            <th>专题</th>
            <th>任务序号_任务类型</th>
            <th>学习时间</th>
            <th>作答内容</th>
            <th>浏览</th>
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
        <div id="classstu" style="display:none;">
            <%--
            <p class="font-black"><strong>科代表</strong>：----&nbsp;&nbsp;----&nbsp;&nbsp;----</p>
            <p>（科代表权限： 1. 删除恶意资源评论   2. 删除不符合要求的主帖  3. 删除主帖中的恶意评论  4. 主帖加精、置顶）</p>
            --%>

            <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
                <colgroup span="2" class="w470">
                <tr>
                    <th>姓名</th>
                    <th>任务完成率</th>
                </tr>
                <c:forEach var="stu" items="${students }" varStatus="idx">
                <tr ${(idx.index%2==1)?'class="trbg1"':''}>
                    <td>${stu.realname}</td>
                    <td><a href="javascript:void(0);" class="font-blue">${stu.completenum}%</a></td>
                <tr>
                    </c:forEach>

            </table>
        </div>
        <div id="groupstu" style="display:none;">
            <c:if test="${empty gsList}">
                <p class="font-black"><strong>我的小组</strong>：没有找到小组</p>
            </c:if>
            <c:if test="${!empty gsList}">
                <c:forEach var="gs" items="${gsList }"  varStatus="idx">
                    <p class="font-black"><strong>我的小组</strong>：${(empty gs.groupname)?"没有找到小组":gs.groupname }</p>
                    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
                        <colgroup span="2" class="w470">
                        <tr>
                            <th>姓名</th>
                            <th>任务完成率</th>
                        </tr>
                        <c:if test="${!empty gs.tpgroupstudent}">
                        <c:forEach var="cgs" items="${gs.tpgroupstudent }"  varStatus="ix">
                        <tr ${(ix.index%2==1)?'class="trbg1"':''}>
                            <td>${cgs.stuname}</td>
                            <td><a href="javascript:void(0);" class="font-blue">${cgs.completenum}%</a></td>
                        <tr>
                            </c:forEach>
                            </c:if>
                    </table>
                </c:forEach>
            </c:if>
        </div>
</div>
<%@include file="/util/foot.jsp" %>
</body>
</html>

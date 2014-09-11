<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-6-30
  Time: 下午2:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
    <script type="text/javascript" src="flexpaper/swfobject/swfobject.js"></script>
    <script type="text/javascript" src="js/teachpaltform/testpaper.js"></script>
    <script type="text/javascript">
        //文件地址:在msg.property中进行编辑
        var resourcepathHead="<%=fileSystemIpPort%>/uploadfile/";
        <c:if test="${resObj.resid>0}">
        resourcepathHead="<%=fileSystemIpPort%>clouduploadfile/";
        </c:if>
        var resid="${resObj.resid}";
        var courseid="${courseid}";
        var taskid="${taskid}";
        var paperid="${paperid}";
        var subQuesId=",";
        var quesSize=0;
        $(function(){
            $("li").filter(function(){return this.id.indexOf('c_')!=-1}).each(function(idx,itm){
                $(itm).bind("click",function(){
                    $(itm).siblings().removeClass('crumb');
                    $(itm).addClass('crumb');
                })
            })

            jwplayer('div_show0').play();
            jwplayer('div_show0').onTime(function(){
                var t=jwplayer('div_show0').getDuration();
                $('#videoTime').html(parseInt(t/60)+'分'+parseInt(t%60)+ '秒');
            })

        });


        /**
         * 加载试卷中的试题
         */
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>

<div class="subpage_head"><span class="ico55"></span><strong>微课程</strong></div>
<div class="content1">
    <ul class="jxxt_zhuanti_rw_wkc">
        <li id="c_1" class="crumb"><a href="javascript:;" onclick="$('#dv_view').show();$('#dv_paper').hide();"><strong>微视频</strong></a></li>
        <li id="c_2"><a href="javascript:;" onclick="$('#dv_view').hide();$('#dv_paper').show();"><strong>试卷</strong></a></li>
    </ul>
    <div id="dv_view">

        <p class="font-black t_c"><strong>${resObj.resname}</strong><br>${resObj.realname}&nbsp;&nbsp;&nbsp;&nbsp;时长：<span id="videoTime"></span>
        </p>
        <div class="jxxt_zhuanti_rw_wkc_sp">
            <div id="div_show0">
                <img src="images/video_gszh.jpg" width="769px" height="432px" alt="正在排列,转换"/>
                <script type="text/javascript">
                    //是否显示进度条的判断
                    var isShowBar=true;
                    loadSWFPlayer(resourcepathHead+"${resObj.path}/001${resObj.filesuffixname}",'div_show0'
                            ,resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}.pre.jpg'
                            ,${resObj.resid},769,432,isShowBar);
                </script>
            </div>
        </div>
    </div>

    <div id="dv_paper" class="jxxt_zhuanti_rw_wkc_sj font-black public_input bg" style="display: none">
        <c:if test="${!empty pqList}">
            <c:forEach items="${pqList}" var="pq" varStatus="idx">
                <table border="0" cellpadding="0" cellspacing="0" class="public_tab1" id="dv_ques_${pq.questionid}" data-bind="${pq.questionid}">
                    <col class="w30"/>
                    <col class="w880"/>
                    <caption>
                        <c:if test="${!empty taskid}">
                            <c:if test="${pq.questiontype==1||pq.questiontype==2}">
                                <a href="task?loadStuMicQuesPerformance&courseid=${courseid}&taskid=${taskid}&questionid=${pq.questionid}&type=1&paperid=${paperid}" class="font-blue f_right">查看回答</a>
                            </c:if>
                            <c:if test="${pq.questiontype==3||pq.questiontype==4}">
                                <a href="task?loadStuMicQuesPerformance&courseid=${courseid}&taskid=${taskid}&questionid=${pq.questionid}&type=2&paperid=${paperid}" class="font-blue f_right">查看回答</a>
                            </c:if>
                        </c:if>
                        <span class="font-blue">${idx.index+1}</span>/${fn:length(pqList)}
                    </caption>

                    <tr>
                        <td>
                            <c:if test="${pq.questionid>0}">
                                <span class="ico44"></span>
                            </c:if>
                        </td>
                        <td>
                            <span class="bg">${pq.questiontypename}</span>${fn:replace(pq.content,'<span name="fillbank"></span>' ,"_____" )}
                            <c:if test="${pq.extension eq 4}">
                                <div  class="p_t_10" id="sp_mp3_${pq.questionid}" ></div>
                                <script type="text/javascript">
                                    playSound('play','<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>/${pq.questionid}/001.mp3',270,22,'sp_mp3_${pq.questionid}',false);
                                </script>
                            </c:if>
                            <c:if test="${!empty pq.questionOption}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <col class="w30"/>
                                    <col class="w850"/>
                                    <c:forEach items="${pq.questionOption}" var="option">
                                        <tr>
                                            <th>
                                                <c:if test="${pq.questiontype eq 3 or pq.questiontype eq 7 }">
                                                    <input disabled type="radio">
                                                </c:if>
                                                <c:if test="${pq.questiontype eq 4 or pq.questiontype eq 8 }">
                                                    <input disabled type="checkbox">
                                                </c:if>
                                            </th>
                                            <td>
                                                    ${option.optiontype}&nbsp;${option.content}
                                                <c:if test="${option.isright eq 1}">
                                                    <span class="ico12"></span>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>


                        </td>
                    </tr>

                    <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0 and pq.extension ne 5}">
                        <c:forEach items="${pq.questionTeam}" var="c" varStatus="cidx">
                            <tr>
                                <td>&nbsp;</td>
                                <td><p><span data-bind="${c.questionid}"  class="font-blue">${(cidx.index+1)+(pq.orderidx-1)}</span>. ${c.content}</p>
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <col class="w30"/>
                                        <col class="w850"/>
                                        <caption>
                                            <c:if test="${!empty taskid}">
                                                <c:if test="${c.questiontype<3}">
                                                    <a href="task?loadStuMicQuesPerformance&courseid=${courseid}&taskid=${taskid}&questionid=${c.questionid}&type=1&paperid=${paperid}" class="font-blue f_right">查看回答</a>
                                                </c:if>
                                                <c:if test="${c.questiontype==3||c.questiontype==4||c.questiontype==7||c.questiontype==8}">
                                                    <a href="task?loadStuMicQuesPerformance&courseid=${courseid}&taskid=${taskid}&questionid=${c.questionid}&type=2&paperid=${paperid}" class="font-blue f_right">查看回答</a>
                                                </c:if>
                                            </c:if>
                                        </caption>
                                        <c:forEach items="${c.questionOption}" var="option">
                                            <tr>
                                                <th>
                                                    <c:if test="${c.questiontype eq 3 or c.questiontype eq 7}">
                                                        <input disabled type="radio">
                                                    </c:if>
                                                    <c:if test="${c.questiontype eq 4 or c.questiontype eq 8 }">
                                                        <input disabled type="checkbox">
                                                    </c:if>
                                                </th>
                                                <td>
                                                        ${option.optiontype}&nbsp;${option.content}
                                                    <c:if test="${option.isright eq 1}">
                                                        <span class="ico12"></span>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>

                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <c:if test="${c.questiontype<3 }">
                                        <p>
                                            <strong>正确答案：</strong>${c.correctanswer}
                                        </p>
                                    </c:if>
                                    <p><strong>答案解析：</strong>${c.analysis}</p>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>


                    <c:if test="${pq.questiontype<6}">
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <p>
                                    <strong>正确答案：</strong>
                                    <c:if test="${pq.questiontype eq 1 or  pq.questiontype eq 2 }">
                                        ${pq.correctanswer}
                                    </c:if>
                                    <c:if test="${pq.questiontype eq 3 or  pq.questiontype eq 4 }">
                                        <c:if test="${!empty pq.questionOption}">
                                            <c:forEach items="${pq.questionOption}" var="option">
                                                <c:if test="${option.isright eq 1}">
                                                    ${option.optiontype}&nbsp;
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:if>
                                </p>
                                <p>
                                    <strong>答案解析：</strong>${pq.analysis}
                                </p>
                            </td>
                        </tr>
                    </c:if>


                    <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0 and pq.extension eq 5}">
                        <tr>
                            <td>&nbsp;</td>
                            <td><p><strong>正确答案及答案解析：</strong></p>
                                <c:forEach items="${pq.questionTeam}" var="c" varStatus="cidx">
                                    <p><span data-bind="${c.questionid}"  class="font-blue">${(cidx.index+1)+(pq.orderidx-1)}</span>.
                                        <c:forEach items="${c.questionOption}" var="option">
                                            <c:if test="${option.isright eq 1}">
                                                ${option.optiontype}
                                            </c:if>
                                        </c:forEach>
                                        &nbsp;&nbsp;${c.analysis}
                                    </p>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </c:forEach>
        </c:if>

    </div>

</div>





</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
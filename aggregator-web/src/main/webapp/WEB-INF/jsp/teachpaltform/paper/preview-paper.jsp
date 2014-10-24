<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.school.entity.teachpaltform.paper.PaperQuestion" %>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>


<html>
<head>
<title>${sessionScope.CURRENT_TITLE}</title>
<script type="text/javascript" src="<%=basePath %>js/teachpaltform/paper.js"></script>
<script type="text/javascript">
var courseid="${courseid}";
var paperid="${paper.paperid}";
var pList,pBankList;
var total;
var allquesid="${ALLQUESID}";
var paperSumScore="${paper.score}" || 0;
var papertype=${paper.papertype};
var quesOrderIdx=1;childOrderIdx=1;
$(function(){
    pList = new PageControl( {
        post_url : 'paperques?m=ajaxPaperQuestionList',
        page_id : 'page1',
        page_control_name : "pList",
        post_form : document.pListForm,
        gender_address_id : 'pListaddress',
        http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
        http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
        return_type : 'json', //放回的值类型
        page_no : 1, //当前的页数
        page_size : 9999, //当前页面显示的数量
        rectotal : 0, //一共多少
        pagetotal : 1,
        operate_id : "initItemList"
    });
   // pageGo('pList');


    $("li[name='li_nav']").each(function(idx,itm){
        $(itm).bind("click",function(){
            $(itm).siblings().removeClass("crumb").end().addClass("crumb");
        })
    })



    if(papertype==1||papertype==2){
        var allqueslength=allquesid.split(",").length;
        var avgScore=parseInt(paperSumScore/allqueslength);
        $("span[name='avg_score']").html(avgScore);
        if(paperSumScore%allqueslength>0){
            //// 使用数组翻转函数
            var yuScore=paperSumScore%allqueslength;
            var s=jQuery.makeArray($("span[name='avg_score'][id^='score_']")).reverse();
            $.each(s,function(idx,itm){
                if(yuScore<1)return;
                $(itm).html(parseFloat($(itm).html())+1);
                yuScore-=1;
            });
        }
        var quesArray=$("table").filter(function(){return this.id.indexOf('dv_ques_')!=-1});
        $.each(quesArray,function(ix,im){
            var score=$("#sum_"+$(im).data().bind+"").html();
            var childArray=$(im).find("span[name='avg_score'][id^='score_']");
            if(childArray.length>0){
                score=0;
                $.each(childArray,function(i,m){
                    score+=parseFloat($(m).html());
                })
            }
            $("#sum_"+$(im).data().bind+"").html(score);
        });
    }
});


function showOrhide(aobj, taskid) {
    var status=$("#div_task_"+taskid);
    if(status.css("display")=="none"){
        load_task_detial(taskid);
        $(status).show();
        $(aobj).removeClass().addClass("ico49a");
    }else{
        $(status).hide();
        $(aobj).removeClass().addClass("ico49b");
    }
}



function getInvestReturnMethod(rps){
    var html='';
    if(rps.objList!=null&&rps.objList.length>0){
        $.each(rps.objList,function(idx,itm){

        });
    }
    $("#initItemList").html(html);



    if(rps.objList.length>0){
        pList.setPagetotal(rps.presult.pageTotal);
        pList.setRectotal(rps.presult.recTotal);
        pList.setPageSize(rps.presult.pageSize);
        pList.setPageNo(rps.presult.pageNo);
    }else
    {
        pList.setPagetotal(0);
        pList.setRectotal(0);
        pList.setPageNo(1);
    }
    pList.Refresh();
}
function preeDoPageSub(pObj){
    if(typeof(pObj)!='object'){
        alert("异常错误，请刷新页面重试!");
        return;
    }
    if(courseid.length<1){
        alert('异常错误，系统未获取到专题标识!');
        return;
    }
    var param={courseid:courseid,paperid:paperid};
    pObj.setPostParams(param);
}



</script>
</head>
<body>

<div class="subpage_head"><span class="ico55"></span><strong>查看试卷</strong></div>
<div class="content2">
    <div class="jxxt_zhuanti_shijuan_add font-black public_input">
        <p class="p_b_10 font-gray">${coursename}</p>
        <c:if test="${!empty paper.subjectivenum and !empty paper.objectivenum}">
            <p class="title">
                <span class="f_right">
                    <strong>主观题：<span class="font-blue">${paper.subjectivenum}</span></strong>
                    &nbsp;&nbsp;&nbsp;&nbsp;<strong>客观题：<span class="font-blue">${paper.objectivenum}</span></strong>
                    &nbsp;&nbsp;&nbsp;&nbsp;<strong>总分值：<span id="" class="font-blue">${paper.score}&nbsp;分</span></strong>
                </span><strong>${paper.papername }</strong>
            </p>
        </c:if>
        <c:if test="${!empty pqList}">

            <c:forEach items="${pqList}" var="pq" varStatus="pqIdx">
                <c:set var="teamSize" value="${fn:length(pq.questionTeam)}"/>
                <table border="0" cellpadding="0" cellspacing="0" class="public_tab1" id="dv_ques_${pq.questionid}" data-bind="${pq.questionid}">
                    <col class="w30"/>
                    <col class="w910"/>
                    <caption>
                        <c:if test="${empty param.mic}">
                            <span class="f_right" name="avg_score" id="sum_${pq.questionid}"> ${pq.score}</span>
                        </c:if>

                    </span>
                        <span  class="font-blue" id="sp_sortIdx">
                        <c:if test="${!empty pq.questionTeam}">
                            <script type="text/javascript">
                                var qteamSize="${fn:length(pq.questionTeam)}";
                                var qoIdx=quesOrderIdx;
                                quesOrderIdx=quesOrderIdx+parseInt(qteamSize);
                                document.write(qoIdx+"-"+(quesOrderIdx-1));
                            </script>
                        </c:if>
                          <c:if test="${empty pq.questionTeam}">
                                 <script type="text/javascript">
                                     quesOrderIdx++;
                                     document.write(quesOrderIdx-1);
                                 </script>
                         </c:if>
                        </span>
                    </caption>

                    <tr>
                        <td>

                            <c:if test="${pq.questionid>0}">
                                <span class="ico44"></span>
                            </c:if>
                        </td>
                        <td>
                            <span class="bg">${pq.questiontypename}</span>
                             ${fn:replace(pq.content,'<span name="fillbank"></span>' ,"_____" )}

                            <c:if test="${pq.extension eq 4}">
                                <div  class="p_t_10" id="sp_mp3_${pq.questionid}" ></div>
                                <script type="text/javascript">
                                    playSound('play','<%=basePath+UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>/${pq.questionid}/001.mp3',270,22,'sp_mp3_${pq.questionid}',false);
                                </script>
                                ${pq.analysis}
                            </c:if>

                            <c:if test="${!empty pq.questionOption and pq.questiontype ne 1}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <col class="w30"/>
                                    <col class="w880"/>
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
                                <td><p><span class="width font-blue"><span class="font-blue"  style="cursor: pointer" data-bind="${c.questionid}|${c.ref}" name="avg_score" id="score_${c.questionid}">
                                        ${c.score}</span></span>
                                    <span data-bind="${c.questionid}" >
                                          <script type="text/javascript">
                                              var qteamSize="${fn:length(pq.questionTeam)}";
                                              var tmpIdx=quesOrderIdx-qteamSize;
                                              document.write(tmpIdx+${cidx.index});
                                          </script>
                                        </span>. ${c.content}</p>
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <col class="w30"/>
                                        <col class="w880"/>
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
                                    <c:if test="${pq.questiontype eq 1 and pq.questionid>0}">
                                        <c:if test="${!empty pq.questionOption}">
                                            ${pq.questionOption[0].content}
                                        </c:if>
                                    </c:if>

                                    <c:if test="${pq.questiontype eq 1 and pq.questionid<0}">
                                        ${pq.correctanswer}
                                    </c:if>

                                    <c:if test="${pq.questiontype eq 2 }">
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
                                    <p><span class="width font-blue"><span class="font-blue" name="avg_score"  style="cursor: pointer" data-bind="${c.questionid}|${c.ref}">${c.score}</span>
                                        </span><span data-bind="${c.questionid}"  class="font-blue">
                                          <script type="text/javascript">
                                              var qteamSize="${fn:length(pq.questionTeam)}";
                                              var qoIdx=quesOrderIdx;
                                              var tmpIdx=quesOrderIdx-qteamSize;
                                              document.write(tmpIdx+${cidx.index});
                                          </script>
                                        </span>.
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




<%@include file="/util/foot.jsp" %>

</body>
</html>

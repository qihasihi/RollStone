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
    <script type="text/javascript">
        var quesSize="${quesSize}";
        $(function(){
            loadQuesNumberTool();
            next(1);
        });
        function loadQuesNumberTool(){
            var h='';
            for(i=1;i<=quesSize;i++){
                h+='<a href="javascript:;" onclick="next('+i+')">['+i+']</a>&nbsp;&nbsp;'
            }
            $("#dv_ques_number").html(h);
        }

        function next(no){
            var pnoDiv=$("#dv_question div").filter(function(){return this.style.display!='none';}).next();
            if(typeof(no)!="undefined"&&!isNaN(no)&&no>0&&parseInt(quesSize)>=no){
                pnoDiv=$("#dv_question div").eq(no-1);
            }
            if(pnoDiv.length<1){
                alert('最后一题了!')
            }else{
                $("#dv_question div").css("display","none");
                pnoDiv.show();
            }
        //ques=pnoDiv.children().filter(function(){return this.name=='hd_quesid'}).val()
        }
    </script>
</head>
<body>
    <div id="dv_ques_number">
    </div>
   <div id="dv_question">
        <c:if test="${!empty quesList}">
            <c:forEach items="${quesList}" var="q" varStatus="qidx">
                <div style="display:none">
                    <input type="hidden" value="${q.questionid}" id="hd_quesid" name="hd_quesid"/>
                    <input type="hidden" value="" name="hd_answer"/>
                    <span id="quesName" style="font-size:16px;">${qidx.index+1}、${q.content}
                    </span>
                    <span id="quesOption_${q.questionid}">
                        <ul>
                            <c:if test="${!empty q.questionInfo.questionOption}">
                                <c:forEach items="${q.questionInfo.questionOption}" var="qo">
                                 <li><input type="radio" value="${qo.optiontype}" name="rdo_answer${qo.questionid}">${qo.optiontype}.${qo.content}</li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </span>

               </div>
            </c:forEach>
        </c:if>
   </div>
    <div id="dv_ques_tool">
        <a href="javascript:;" onclick="next()">下一题</a>
    </div>
</body>
</html>

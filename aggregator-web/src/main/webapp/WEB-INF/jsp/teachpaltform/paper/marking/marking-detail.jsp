<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-4
  Time: 上午10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        $(function(){
            var num1=0;
            var num2=0.5;
            var htm = "";
            htm+='<tr>';
            for(var i = 0;i<${num.SCORE+1};i++){
                htm+='<td width="100"><a href="javascript:submitScore(${detail.REF},'+num1+')">'+num1;
                num1++;
                htm+='</a><br><hr><a href="javascript:submitScore(${detail.REF},'+num2+')">'+num2;
                if(i<${num.SCORE-1}){
                    num2++;
                }else{
                    num2="";
                }
                htm+='</a></td>';
                if(i==5){
                    htm+='</tr><tr>';
                }
            }
            htm+='</tr>';
            $("#score").html(htm);
        });
        function submitScore(ref,score){

        }
    </script>
</head>
<body>
    <c:if test="${!empty num}">
        <span>试题分数：${num.SCORE}分</span>&nbsp;&nbsp;&nbsp;<span>平均分：${num.AVGSCORE}分</span>&nbsp;&nbsp;&nbsp;<span>已批改：${num.MARKINGNUM}/${num.SUBMITNUM}</span>
    </c:if>
   <hr>
    <c:if test="${!empty detail}">
        <span>${detail.CONTENT}</span><br>
        <span>正确答案：${detail.CURRENTANSWER}</span><br>
        <span>答案解析：${detail.ANALYSIS}</span>
        <hr>
        <span>学生：${detail.STUNAME}</span><br>
        <span>答案：${detail.ANSWER}</span>
        <span>评分：请选择该学生的得分。</span><br>
        <table id="score">

        </table>
    </c:if>

</body>
</html>

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
            var totalnum= "${num.SCORE}";
            var n = Math.ceil((parseFloat(totalnum)+0.5)/10);
            var divhtm='';
            for(var i = 0;i<n;i++){
               divhtm+='<div class="layout">';
                var ulhtm='<ul>';
                var lihtm='';
                for(var j =0;j<10;j++){
                    lihtm+='<li';
                    if(j==0){
                       lihtm+=' class="crumb"';
                    }
                    lihtm+=' ><a href="1">'+(j+i*10)+'</a></li>';
                    if(parseFloat((j+i*10))>=parseFloat(totalnum-0.5)){
                        break;
                    }
                }
                ulhtm+=lihtm+'</ul>';

                var ulhtm2='<ul>';
                var lihtm2='';
                for(var k =0.5;k<10.5;k++){
                    lihtm2+='<li><a href="1">'+(k+i*10)+'</a></li>';
                    if(parseFloat((k+i*10))>=parseFloat(totalnum-0.5))
                        break;
                }
                ulhtm2+=lihtm2+'</ul>';
                divhtm+=ulhtm+ulhtm2+'</div>';

            }
            $("#score").append(divhtm);
            <%--for(var i = 0;i<${num.SCORE+1};i++){--%>
                <%--htm+='<li class="crumb"><a href="javascript:submitScore(${detail.REF},'+num1+')">'+num1;--%>
                <%--num1++;--%>
                <%--htm+='</a><br><hr><a href="javascript:submitScore(${detail.REF},'+num2+')">'+num2;--%>
                <%--if(i<${num.SCORE-1}){--%>
                    <%--num2++;--%>
                <%--}else{--%>
                    <%--num2="";--%>
                <%--}--%>
                <%--htm+='</a></td>';--%>
                <%--if(i==5){--%>
                    <%--htm+='</tr><tr>';--%>
                <%--}--%>
            <%--}--%>
            <%--htm+='</tr>';--%>
            <%--$("#score").html(htm);--%>
        });
        function submitScore(ref,score){

        }
    </script>
</head>
<body>
    <div class="subpage_head"><span class="ico55"></span><strong>批阅试卷</strong></div>
    <div class="content2">
        <p class="jxxt_zhuanti_rw_piyue_info"><a href="1" class="an_public3">下一题</a>
            <a href="1" class="an_public3">上一题</a><strong class="font-blue">${param.idx}</strong>&nbsp;&nbsp;试题分数：<span class="font-blue">${num.SCORE}</span>&nbsp;分</p>
        <div class="jxxt_zhuanti_shijuan_add public_input font-black">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 w940">
                <tr>
                    <td><span class="bg">填空题：</span>${detail.CONTENT}</td>
                </tr>
                <tr>
                    <td><p><strong>正确答案：</strong>${detail.CURRENTANSWER}</p>
                        <p><strong>答案解析：</strong>${detail.ANALYSIS}</p></td>
                </tr>
            </table>
        </div>
        <div class="jxxt_zhuanti_rw_piyue_defen" id="score">
            <h5></h5>
            <p class="p_b_20"><strong>平均分：</strong><span class="font-blue">${num.AVGSCORE}</span>分
                &nbsp;&nbsp;&nbsp;&nbsp;<strong>已批改：</strong><span class="font-blue">${num.MARKINGNUM}/${num.SUBMITNUM}</span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" class="font-blue">查看统计</a></p>
            <p><strong>学生：</strong>${detail.STUNAME} </p>
            <p><strong>答案：</strong>${detail.ANSWER}</p>
            <p><strong>附件：</strong><a href="1" target="_blank" class="font-blue">我的答案.jpg</a></p>
            <p><strong>评分：</strong>请选择该学生的得分。</p>
        </div>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>
</html>

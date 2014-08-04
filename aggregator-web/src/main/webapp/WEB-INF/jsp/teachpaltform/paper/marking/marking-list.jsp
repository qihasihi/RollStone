<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-3
  Time: 上午10:00
  desctiption: 批阅试卷 主页
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <title>批阅试卷主页</title>
    <script type="text/javascript">
        var quesidStr = "${quesidStr}";
        var paperid = "${param.paperid}";
        $(function(){
            var quesids = quesidStr.split(",");
            if(quesids.length>0){
                var htm = '';
                for(var i = 0;i<quesids.length;i++){
                    var orderidx = i+1;
                    var id = quesids[i];
                    var sign = false;
                    var questionid;
                    if(quesids[i].split("|").length>1){
                        questionid = quesids[i].split("|")[0];
                        sign = true;
                        id=quesids[i].split("|")[1];
                    }
                    htm+='<li';
                    <c:forEach items="${questionList}" var="itm" varStatus="idx">
                       if(id==${itm.questionid}){
                           if(${itm.markingnum==itm.submitnum}){
                               htm+=' class="over"'
                           }
                           htm+='><a href="paper?m=toMarkingDetail';
                           if(sign){
                               htm+='&questionid='+questionid;
                           }
                           htm+='&paperid=${param.paperid}&quesid='+id+'&idx='+orderidx+'">'+orderidx+'<b>${itm.markingnum}/${itm.submitnum}</b></a>';
                       }
                    </c:forEach>
                    htm+='</li>';
                }
                $("#question").html(htm);
            }
        });
        function getPercentScoreByType(type){
            $.ajax({
                url:"paper?m=getpercentscore",
                type:"post",
                data:{type:type,paperid:paperid},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        if(type==1){
                            if(rmsg.objList!=null&&rmsg.objList.length>0){
                                $.each(rmsg.objList,function(idx,itm){
                                    $("#percentScore").html(itm.htm);
                                });
                            }
                        }else{
                            if(rmsg.objList!=null&&rmsg.objList.length>0){
                                $.each(rmsg.objList,function(idx,itm){
                                    var htm = '';
                                    $("#percentScore").html(itm.htm);
                                });
                            }
                        }
                    }
                }
            });
        }
    </script>
</head>
<body>
    <div class="subpage_head"><span class="ico55"></span><strong>${papername}</strong></div>
    <div class="content1">
        <div class="jxxt_zhuanti_rw_piyue">
            <h2><select name="select3" id="select3">
                <option selected>高一</option>
                <option>教职工</option>
                <option>学生</option>
            </select></h2>
            <h3>注：客观题系统已自动完成批改</h3>
            <ul class="shiti" id="question">

            </ul>
        <div class="jxxt_zhuanti_rw_tongji">
            <p><strong>分数段分布统计：</strong><a href="1" target="_blank" class="font-darkblue">按百分制统计</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="1" target="_blank" class="font-darkblue">按实际分数统计</a></p>
            <div class="right"><img src="images/paperScorePie.png" width="193" height="140"></div>
            <div class="left">
                <table border="0" cellspacing="0" cellpadding="0" class="public_tab3">
                    <colgroup span="2" class="w140"></colgroup>
                    <tr>
                        <td>分数段</td>
                        <td>学生人数</td>
                    </tr>
                    <tbody id="percentScore">
                        ${htm}
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>
</html>

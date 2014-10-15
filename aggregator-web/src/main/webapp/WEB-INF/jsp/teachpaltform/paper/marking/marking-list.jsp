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
        var sign = ${ismark};
        var paperScore="${paperScore}";
        $(function(){
            var currentclsid = ${classid};
            var currenttype = ${classtype};
            if(currentclsid>0){
               // $("#classSelect").val(currentclsid);
                $("#classSelect").find("option[value='"+currentclsid+"|"+currenttype+"']").attr("selected",true);
            }
            if($("#classSelect option").length<2)
                $("#a_cls").hide();
           queryPaperques();

            if($("#question li").filter(function(){return this.className!='over'}).length<1){
                //验证是否没有作答
                var isStuAnswer=true;
//                $("#question li b").each(function(idx,itm){
//                    var h=$(itm).html().Trim().split("/");
//                    if(h[0]==0||h[1]==0){
//                        isStuAnswer=false;return;
//                    }
//                })
                if(isStuAnswer){
                    getPercentScoreByType(2);
                    $("#dv_tj_fsd").show();
                }
            }

        });
        function queryPaperques(){
            var classvalue =$("#classSelect").val();
            var classid = classvalue.split("|")[0];
            var classtype = classvalue.split("|")[1];
            var quesids = quesidStr.split(",");
            if(quesids.length>0){
                var htm = '';
                for(var i = 0;i<quesids.length;i++){
                    var orderidx = i+1;
                    var id = quesids[i];
                    var tmpsign = false;
                    var questionid;
                    if(quesids[i].split("|").length>1){
                        questionid = quesids[i].split("|")[0];
                        tmpsign = true;
                        id=quesids[i].split("|")[1];
                    }

                    <c:forEach items="${questionList}" var="itm" varStatus="idx">
                    var curid="${itm.questionid}";
                    if(id==curid){
                        htm+='<li';
                        if(${itm.markingnum==itm.submitnum}){
                            htm+=' class="over"'
                        }
                        var u="javascript:alert('试卷中该问题还没有学生进行答题，无法批阅试卷!');";
                        <c:if test="${!empty itm.submitnum&&itm.submitnum>0}">
                        u='paper?m=toMarkingDetail';
                        if(tmpsign)
                            u+='&questionid='+questionid;
                        u+='&sign='+sign+'&tabidx='+i+'&paperid=${param.paperid}&quesid='+id+'&idx='+orderidx+'&classid='+classid+'&classtype='+classtype+'&taskid=${param.taskid}';
                        </c:if>
                        htm+='><a href="'+u+'">'+orderidx+'<b>${itm.markingnum}/${itm.submitnum}</b></a>';
                    }
                    </c:forEach>
                    htm+='</li>';
                }
                $("#question").html(htm);
            }
        }
        function getPercentScoreByType(type){
            $.ajax({
                url:"paper?m=getpercentscore",
                type:"post",
                data:{type:type,paperid:paperid,classid:${classid}},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        if(type==1){
                            if(rmsg.objList[0]!=null){
                               // $.each(rmsg.objList[0],function(idx,itm){
                                    $("#percentScore").html(rmsg.objList[0].htm);
                              //  });
                            }
                            var h='<img src="'+rmsg.objList[1]+'" name="'+new Date().getTime()+'a" id="percentImg"/>';
                            $("#percentImg").parent().html(h);
                        }else{
                            if(rmsg.objList[0]!=null){
                                itm=rmsg.objList[0];
                               // $.each(rmsg.objList[0],function(idx,itm){
                                    var htm = '';
                                    htm+='<tr><td>90~100</td><td>'+itm["90~100"]+'</td></tr>';
                                    htm+='<tr><td>80~90</td><td>'+itm["80~90"]+'</td></tr>';
                                    htm+='<tr><td>70~80</td><td>'+itm["70~80"]+'</td></tr>';
                                    htm+='<tr><td>60~70</td><td>'+itm["60~70"]+'</td></tr>';
                                    htm+='<tr><td>0~60</td><td>'+itm["0~60"]+'</td></tr>';
                                    $("#percentScore").html(htm);
                               // });
                            }
                            var h='<img src="'+rmsg.objList[1]+'" name="'+new Date().getTime()+'a" id="percentImg"/>';
                            $("#percentImg").parent().html(h);
                        }

                    }
                }
            });
        }
        function changeClass(){
            var clsid = $("#classSelect").val();
            window.location.href="paper?m=toMarking&paperid=${param.paperid}&taskid=${param.taskid}&classid="+clsid.split("|")[0]+"&classtype="+clsid.split("|")[1];
        }
    </script>
</head>
<body>
    <div class="subpage_head"><span class="ico55"></span><strong>
        <c:if test="${papertype==1}">标准A--</c:if>
        <c:if test="${papertype==2}">标准B--</c:if>
        <c:if test="${papertype==3}">成卷测试--</c:if>
        ${papername}</strong></div>
    <div class="content1">
        <div class="jxxt_zhuanti_rw_piyue">
            <h2><select name="select3" id="classSelect" onchange="changeClass()" style="display:none">
                <c:forEach items="${classes}" var="itm">
                    <c:if test="${!empty classid&&classid==itm.classid}">
                        <option value="${itm.classid}|${itm.classtype}" selected="true">${itm.classname}</option>
                    </c:if>
                    <c:if test="${empty classid||classid!=itm.classid}">
                        <option value="${itm.classid}|${itm.classtype}">${itm.classname}</option>
                    </c:if>
                </c:forEach>
            </select>
            <span id="sp_showCls"><script type="text/javascript">document.write($("#classSelect option[selected='true']").text());</script>
              <a id="a_cls" href="javascript:;" onclick="sp_showCls.style.display='none';$('#classSelect').show()" class="ico49a"></a>
            </span>
            </h2>
            <h3>注：客观题系统已自动完成批改</h3>
            <ul class="shiti" id="question">

            </ul>
        <div class="jxxt_zhuanti_rw_tongji" id="dv_tj_fsd" style="display:none">
            <p><strong>分数段分布统计：</strong><a href="javascript:getPercentScoreByType(2)" class="font-darkblue">按百分制统计</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <c:if test="${empty paperScore||paperScore!=100}">
                    <a href="javascript:getPercentScoreByType(1)"  class="font-darkblue">按实际分数统计</a>
                </c:if></p>
            <div class="right"><img id="percentImg" src="images/paperScorePie.png" width="193" height="140"></div>
            <div class="left">
                <table border="0" cellspacing="0" cellpadding="0" class="public_tab3">
                    <colgroup span="2" class="w140"></colgroup>
                    <tr>
                        <td>分数段</td>
                        <td>学生人数</td>
                    </tr>
                    <tbody id="percentScore">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    </div>
    <%@include file="/util/foot.jsp" %>
</body>
</html>

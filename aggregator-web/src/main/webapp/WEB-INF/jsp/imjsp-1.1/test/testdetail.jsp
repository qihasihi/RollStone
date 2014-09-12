<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-8-26
  Time: 下午2:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <script type="text/javascript" src="js/common/image-show.js"></script>
    <script type="text/javascript" src="js/im1.1/testpaper.js"></script>
    <title>试卷详情</title>
    <script type="text/javascript">
        var quesSize="${quesSize}";
        var courseid="${courseid}";
        var taskid="${taskid}";
        var classid="${classid}";
        var paperid="${paperid}";
        var allquesidObj="${allquesidObj}";
        var sumScore=${!empty paperObj.score?paperObj.score:100},avgScore=parseInt(sumScore/quesSize);
        var scoreArray=[];
        var papertype="${paperObj.papertype}";
        var _QUES_IMG_URL="<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>";
        var tqControler=null;
        $(function(){
            //分数
            for(i=0;i<quesSize;i++){
                scoreArray[scoreArray.length]=avgScore;
            }
            //如果分数有余数。则从最后分数开始算
            var yuScore=sumScore%quesSize;
            if(yuScore>0){
                scoreArray=scoreArray.reverse();
                $.each(scoreArray,function(idx,itm){
                    if(yuScore<1)return;
                    scoreArray[idx]=parseFloat(itm)+1;
                    yuScore-=1;
                });
                scoreArray=scoreArray.reverse();
            }
            //  loadQuesNumberTool(quesSize);
            tqControler=new TestPaperDetail({
                taskid:taskid,
                courseid:courseid,
                paperid:paperid,
                quesidstr:allquesidObj,
                scoreArray:scoreArray,
                papertype:papertype,
                subQuesId:subQuesId,
                classid:classid,
                userType:"${userType}",
                userid:"${userid}"
            });
            <c:if test="${!empty quesid}">
                 tqControler.nextQues(${quesid});
            </c:if>
            <c:if test="${empty quesid}">
                   tqControler.nextNum(1);
            </c:if>
            //重写相关FORM submit事件,不让其真正提交
            document.getElementById("fm_free").submit=function(){
                if(allowNext){
                     nextNum(-1,tqControler);
                }
            }
            document.getElementById("fm_next").submit=function(){
                if(allowNext){
                     nextNum(1,tqControler);
                }
            }
            document.getElementById("fm_subQues").submit=function(){
                if(!allowNext){
                    beginJs1();
                    alert('4||请勿过度频繁点击!');
                    return(false);
                }
                alert('1||作答成功!');
                return false;
            }
        });
    </script>
</head>
<body>
<!--试卷里的试题-->
<div class="zxcs_test" id="dv_question">
</div>
<a href="javascript:;" onclick="fm_subQues.submit();fm_free.submit();">上一题</a>
<a  href="javascript:;" onclick="fm_subQues.submit();fm_next.submit();">下一题</a>
<form action="#"  id="fm_free" method="post">
</form>
<form  action="#"  id="fm_next" method="post">
</form>
<!--提交试题的FRM-->
<form action="#"  id="fm_subQues" method="post">
</form>
</body>
</html>

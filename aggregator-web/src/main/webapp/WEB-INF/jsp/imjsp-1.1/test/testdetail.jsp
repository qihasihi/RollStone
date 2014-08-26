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
    <script type="text/javascript" src="js/im1.1/testpaper.js"></script>
    <title>试卷详情</title>
    <script type="text/javascript">
        var quesSize="${quesSize}";
        var courseid="${courseid}";
        var taskid="${taskid}";
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
                userid:"${userid}"
            });
        });
    </script>
</head>
<body>
<!--试卷里的试题-->
<div class="zxcs_test" id="dv_question">
</div>
<a href="javascript:tqControler.nextNum(-1)">上一题</a>
<a href="javascript:tqControler.nextNum(1)">下一题</a>
</body>
</html>

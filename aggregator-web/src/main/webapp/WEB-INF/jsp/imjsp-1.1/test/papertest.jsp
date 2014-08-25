<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-8-23
  Time: 下午5:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <script type="text/javascript">
        var quesSize="${quesSize}";
        var courseid="${courseid}";
        var taskid="${taskid}";
        var subQuesId="${!empty answerQuesId?answerQuesId:","}";
        var paperid="${paperid}";
        var allquesidObj="${allquesidObj}";
        var sumScore=${!empty paperObj.score?paperObj.score:100},avgScore=parseInt(sumScore/quesSize);
        var scoreArray=[];
        var papertype="${paperObj.papertype}";
        var _QUES_IMG_URL="<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>";
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
          // nextNum(0);
        });
    </script>
</head>
<body>
<div class="zxcs_test" id="dv_question">
    <h1><span class="f_right">得分：10分</span>单选题（10分）</h1>
    <div class="yuyin"><img src="images/pic02_140811.png" width="99" height="22"></div>
    <div class="title">我是问题的题干噢~~~问题啊问题，问题啊问题，问题的题干~~~我是问题的题干噢~~~问题啊问题，问题啊问题，问题的题干~~~我是问题的题干噢~~~下列有关文学常识的表述，有误的一项是你猜我猜不猜（    ）</div>
    <ul class="test">
        <li class="crumb"><a href="1"><span class="blue">A、</span>近体诗是对唐代形成的律诗和绝句的通称。与古体诗相对而言，句数、字数和平仄、用韵等都有一定的格律。</a></li>
        <li><a href="1"><span class="blue">B、</span>桐城派是清代散文流派，代表作家有方苞、归有光、刘大櫆、姚鼐等。</a></li>
        <li><a href="1"><span class="blue">C、</span>从文是我国现代文学中有风格、有艺术个性的作家。他的作品题材广泛，文笔清丽，语言清新活泼。《边城》、《湘行散记》最具代表性。</a></li>
        <li><a href="1"><span class="blue">D、</span>高尔基的《母亲》是世界文学史上第一部描写无产阶级革命斗争的著作，列宁称它是&ldquo;一部非常及时的书&rdquo;。</a></li>
    </ul>
</div>
</body>
</html>

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
        var quesSize="${quesSize}";
        var allquesidObj="${allquesidObj}";
        var papertype="${paperObj.papertype}";
        var isanswer="${isAnswer}";
        var _QUES_IMG_URL="<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>";
        var sumScore=${!empty paperObj.score?paperObj.score:100},avgScore=parseInt(sumScore/quesSize);
        var scoreArray=[];
        var t=1;
        $(function(){
        <%--教师进入查看试卷--%>
        <c:if test="${!empty param.flag&&param.flag==1}">
            loadExam();
        </c:if>
        <c:if test="${empty param.flag||param.flag!=1}">
            <c:if test="${!empty param.isMidView&&param.isMidView==1}">
                isViewVideo();
                 loadExam();
            </c:if>
            <c:if test="${!empty isViewVideo&&isViewVideo!=0}">
                //如果已经预览过。则显示。
                isViewVideo();
            </c:if>

            jwplayer('div_show0').onTime(function(){
                if(t==1){
                    var videoTime=jwplayer('div_show0').getDuration();
                    $("#sp_video_time").html(parseInt(videoTime/60)+'分' + parseInt(videoTime%60)+'秒');
                    t=2;
                }
            });
        </c:if>
        });

        function isViewVideo(isMid){
            $("#li_exam").bind("click",function(){
                jwplayer('div_show0').stop();
                loadExam();
            });
         <c:if test="${empty param.isMidView||param.isMidView!=1}">
            $("#li_exam").html("<a><strong>试卷</strong></a>");
            </c:if>

            $("#li_view").bind("click",function(){
                div_exam.style.display='none';
                div_video.style.display='block';
                li_view.className='crumb';
                li_exam.className='';
                jwplayer('div_show0').play();
            });
        }



        /**
         *微视频播放完毕执行，数据添加
         */
        function jwplayEnd(){
            jwplayer('div_show0').stop();
            <c:if test="${empty isViewVideo||isViewVideo==0}">
                $.ajax({
                    url:"paperques?m=saveStuMic",
                    type:"post",
                    data:{resid:resid,courseid:courseid,taskid:taskid},
                    dataType:'json',
                    cache: false,
                    error:function(){
                        alert('系统未响应，请稍候重试!');
                    },success:function(rmsg){
                        if(rmsg.type=="error"){
                            alert(rmsg.msg);
                        }else{
                            jwplayerSetup.controls=true;
                            var returnPobj= jwplayer('div_show0').setup(jwplayerSetup);
                            loadExam();
                        }
                    }
                });
                isViewVideo();
            </c:if>
            <c:if test="${!empty isViewVideo&&isViewVideo!=0}">
             loadExam();
            </c:if>
        }
        /**
         * 加载试卷中的试题
         */
        function loadExam(){
            //jwplayer('div_show0').pause();
            //如果存在，则不加载
            if($("#div_exam").length<1||$("#div_exam").html().Trim().length<1){
                var url="paperques?m=testPaper&courseid="+courseid+"&taskid="+taskid+"&paperid="+paperid+"&isVlidateAnswer=1";
                <c:if test="${!empty param.flag&&param.flag==1}">
                    url+="&flag=1&userid=${param.userid}";
                </c:if>
                //加载页面
                <c:if test="${!empty isAnswer&&isAnswer==1||taskstatus eq '3'||(!empty param.flag&&param.flag=='1')}">

                    $.get(url,function(data){
                        $("#div_exam").html(data);
                                $("#div_exam").html(
                                        '  <div class="jxxt_zhuanti_rw_wkc_sj font-black public_input bg">'+
                                        $("#div_exam #dv_question").html()+'</div>');
                                $("#div_exam strong[id*='you_score']").parent().hide();
                                $("#div_exam>div>table").removeClass("w940");
                                $("#div_exam>div>table").addClass("w910");
                            }
                    );
                </c:if>
                <c:if test="${!empty isAnswer&&isAnswer==0&&taskstatus!='3'&&(empty param.flag||param.flag!='1')}">
                    $("#div_exam").load(url+" #dv_test",function(){
                        if(papertype!=3){
                            //分数
                            for(i=0;i<quesSize;i++)
                                scoreArray[scoreArray.length]=avgScore;

                            //如果分数有余数。则从最后分数开始算
                            var yuScore=sumScore%quesSize;
                            if(yuScore>0){
                                scoreArray=scoreArray.reverse();
                                $.each(scoreArray,function(idx,itm){
                                    scoreArray[idx]=parseFloat(itm)+1;
                                    yuScore-=1;
                                });
                                scoreArray=scoreArray.reverse();
                            }
                        }
                        //加载
                        loadQuesNumberTool(quesSize);
                        nextNum(0);
                    });
                </c:if>
           }
            <c:if test="${empty param.flag||param.flag!=1}">
                $("#li_exam").unbind("click");
               // $("#li_exam").bind("click",
                isViewVideo();
                $("#li_view").html("<a><strong>微视频</strong></a>");
                $("#li_exam").html("<a><strong>试卷</strong></a>");
                $("#li_exam").attr("class","crumb");
                $("#li_view").attr("class","");
            </c:if>
            $("#div_video").hide();
            $("#div_exam").show();
        }
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>
<div class="subpage_head">
    <p class="back"><span class="ico_time"></span>
    <c:if test="${empty param.flag||param.flag!=1}">
        <span id="sp_howlongt">
            <c:if test="${taskstatus=='3'}">已结束</c:if>
            <c:if test="${taskstatus!='3'}">${taskstatus}</c:if>
        </span>
    </c:if>
    </p>
    <span class="ico55"></span><strong>微课程</strong>
</div>
<div class="content1">
    <c:if test="${empty param.flag||param.flag!=1}">
        <%--<p class="t_r"><span class="ico_time"></span><strong><span id="sp_howlongt">--%>
            <%--<c:if test="${taskstatus=='3'}">已结束</c:if>--%>
            <%--<c:if test="${taskstatus!='3'}">${taskstatus}</c:if>--%>
        <%--</span></strong></p>--%>


        <ul class="jxxt_zhuanti_rw_wkc">
            <c:if test="${empty param.isMidView||param.isMidView!=1}">
            <li class="crumb" id="li_view"><strong>微视频</strong></li>
                <li id="li_exam"><strong>试卷</strong></li>
            </c:if>
            <c:if test="${!empty param.isMidView&&param.isMidView==1}">
                <li id="li_view"><strong>微视频</strong></li>
                <li class="crumb"  id="li_exam"><strong>试卷</strong></li>
            </c:if>
        </ul>
  </c:if>
   <div id="div_video">
    <p class="font-black t_c"><strong>${resObj.resname}</strong><br>${resObj.realname}&nbsp;&nbsp;&nbsp;&nbsp;时长：<span id="sp_video_time"></span></p>
    <div class="jxxt_zhuanti_rw_wkc_sp">
        <div id="div_show0">
            <img src="images/video_gszh.jpg" width="578px" height="400px" alt="正在排列,转换"/>

        </div>
     </div>
       <%//<a href="javascript:;" onclick="jwplayEnd()">看完了（测试）</a>%>
   </div>
    <div id="div_exam" style="display:none">
    </div>
</div>
<%@include file="/util/foot.jsp"%>
<%--如果是教师查看，则不显示视频，--%>
<c:if test="${empty param.flag||param.flag!=1}">
<script type="text/javascript">
        //是否显示进度条的判断
        var isShowBar=false;
        <c:if test="${!empty isViewVideo&&isViewVideo!=0||taskstatus=='3'}">
        isShowBar=true;
        </c:if>
        loadSWFPlayer(resourcepathHead+"${resObj.path}/001${resObj.filesuffixname}",'div_show0'
                ,resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}.pre.jpg'
                ,${resObj.resid},769,432,isShowBar,jwplayEnd);
</script>
</c:if>
</body>
</html>

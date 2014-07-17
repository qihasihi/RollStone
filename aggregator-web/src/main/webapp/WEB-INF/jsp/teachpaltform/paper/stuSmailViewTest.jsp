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
        var quesSize=0;

        var t=1;
        $(function(){

            <c:if test="${!empty isViewVideo&&isViewVideo!=0}">
                $("#li_exam").bind("click",loadExam);
                $("#li_exam").html("<a><strong>试卷</strong></a>");
            </c:if>

            jwplayer('div_show0').onTime(function(){
                if(t==1){
                    var videoTime=jwplayer('div_show0').getDuration();
                    $("#sp_video_time").html(parseInt(videoTime/60)+'分' + parseInt(videoTime%60)+'秒');
                    t=2;
                }
            });
        });





        /**
         *微视频播放完毕执行，数据添加
         */
        function jwplayEnd(){
            jwplayer('div_show0').stop();
            <c:if test="${empty isViewVideo||isViewVideo==0}">
                $.ajax({
                    url:"paperques?m=saveStuMic",
                    type:"post",
                    data:{resid:resid},
                    dataType:'json',
                    cache: false,
                    error:function(){
                        alert('系统未响应，请稍候重试!');
                    },success:function(rmsg){
                        if(rmsg.type=="error"){
                            alert(rmsg.msg);
                        }else{
                            loadExam();
                        }
                    }
                });
            </c:if>
            <c:if test="${!empty isViewVideo&&isViewVideo!=0}">
             loadExam();
            </c:if>
        }
        /**
         * 加载试卷中的试题
         */
        function loadExam(){
            jwplayer('div_show0').pause();
            //如果存在，则不加载
            if($("#div_exam").html().Trim().length<1){
                var url="paperques?m=testPaper&courseid="+courseid+"&taskid="+taskid+"&paperid="+paperid;
                $("#div_exam").load(url+" #dv_test",function(){
                    quesSize=$("#dv_question>div").length;
                    $("span[name='fillbank']").each(function(idx,itm){
                        $(this).replaceWith('<input type="text" name="txt_tk" id="txt_tk_'+$(this).parent().parent().children("input[name='hd_quesid']").val()+'"/>');
                    });
                    //加载
                    loadQuesNumberTool(quesSize);
                    next(1);
                });//
            }

            $("#li_exam").unbind("click");
            $("#li_exam").bind("click",loadExam);

            $("#li_view").html("<a><strong>微视频</strong></a>");
            $("#li_exam").html("<a><strong>试卷</strong></a>");
            $("#li_exam").attr("class","crumb");
            $("#li_view").attr("class","");

            $("#div_video").hide();
            $("#div_exam").show();
        }
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>
<div class="subpage_head"><span class="ico55"></span><strong>微课程</strong></div>
<div class="content1">
    <p class="t_r"><span class="ico_time"></span><strong><span id="sp_howlongt">${taskstatus}</span></strong></p>

    <ul class="jxxt_zhuanti_rw_wkc">
        <li class="crumb" id="li_view"
            onclick="div_exam.style.display='none';div_video.style.display='block';li_view.className='crumb';li_exam.className='';"
                ><strong>微视频</strong></li>
        <li id="li_exam"><strong>试卷</strong></li>
    </ul>
   <div id="div_video">
    <p class="font-black t_c"><strong>${resObj.resname}</strong><br>${resObj.realname}&nbsp;&nbsp;&nbsp;&nbsp;时长：<span id="sp_video_time"></span></p>
    <div class="jxxt_zhuanti_rw_wkc_sp">
        <div id="div_show0">
            <img src="images/video_gszh.jpg" width="578px" height="400px" alt="正在排列,转换"/>
            <script type="text/javascript">
                //是否显示进度条的判断
                var isShowBar=false;
                <c:if test="${!empty isViewVideo&&isViewVideo!=0}">
                isShowBar=true;
                </c:if>
                loadSWFPlayer(resourcepathHead+"${resObj.path}/001${resObj.filesuffixname}.mp4",'div_show0'
                        ,resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}.pre.jpg'
                        ,${resObj.resid},769,432,isShowBar,jwplayEnd);
            </script>
        </div>
     </div>
       <%--<a href="javascript:;" onclick="jwplayEnd()">看完了（测试）</a>--%>
   </div>
    <div id="div_exam" style="display:none">
    </div>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>

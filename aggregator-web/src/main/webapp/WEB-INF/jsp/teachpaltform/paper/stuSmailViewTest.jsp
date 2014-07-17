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
        $(function(){
        });

        /**
         *微视频播放完毕执行，数据添加
         */
        function jwplayEnd(){
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
            var url="paperques?m=testPaper&courseid="+courseid+"&taskid="+taskid+"&paperid="+paperid;
            $("#div_exam").load(url+" .content1",function(){
                quesSize=$("#dv_question>div").length;
                $("span[name='fillbank']").each(function(idx,itm){
                    $(this).replaceWith('<input type="text" name="txt_tk" id="txt_tk_'+$(this).parent().parent().children("input[name='hd_quesid']").val()+'"/>');
                });
                //加载
                loadQuesNumberTool(quesSize);
                next(1);
            });//
            $("#div_video").hide();
            $("#div_exam").show();

        }
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>
<div class="subpage_head"><span class="ico55"></span><strong>
    <c:if test="${!empty paperObj&&paperObj.papertype==3}">成卷测试</c:if>
    <c:if test="${!empty paperObj&&paperObj.papertype==4}">自主测试</c:if>
</strong></div>
<div class="content1">
    <div class="jxxt_zhuanti_rw_ceshi" id="div_video">
       <div id="div_show0">
           <img src="images/video_gszh.jpg" width="578px" height="400px" alt="正在排列,转换"/>
           <script type="text/javascript">
                //是否显示进度条的判断
               var isShowBar=false;
              <c:if test="${!empty isViewVideo&&isViewVideo!=0}">
               isShowBar=true;
             </c:if>
                       videoConvertProgress('${resObj.resid}',"001${resObj.filesuffixname}"
                               ,"001",0,'${resObj.path}/'
                               ,'<%=fileSystemIpPort%>',resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}.pre.jpg'
                               ,578
                               ,480,isShowBar,jwplayEnd
                       );
           </script>
       </div>
    <!--是否已经看过该视频-->
    <c:if test="${empty isViewVideo||isViewVideo==0}">
        <div id="div_show0_toolbar">
            <a href="javascript:;" onclick="jwplayer('div_show0').play()">播放</a>
            <a href="javascript:;" onclick="jwplayer('div_show0').pause()">暂停</a>
        </div>
    </c:if>
        <a href="javascript:;" onclick="jwplayEnd()">看完了（测试）</a>
    </div>
    <div id="div_exam">

    </div>
 </div>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>
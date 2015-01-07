<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-6-30
  Time: 下午2:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.school.util.UtilTool" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/SchoolTabFunctions" prefix="fn"%>
<%
    String fileSystemIpPort1="",basePath1="";

    String proc_name1=request.getHeader("x-etturl");
    if(proc_name1==null){
        proc_name1=request.getContextPath().replaceAll("/","");
    }else{
        if(proc_name1.indexOf("/")==-1)
            proc_name1+="/";
        ///group1/1.jsp
        proc_name1=proc_name1.substring(0,proc_name1.substring(1).indexOf("/")+1).replaceAll("/","");
    }

    String ipStr1=request.getServerName();
    if(request.getServerPort()!=80){
        ipStr1+=":"+request.getServerPort();
    }
    //UtilTool.utilproperty.getProperty("PROC_NAME");
    basePath1 = (request.getScheme() + "://"
            + ipStr1
            +"/"+proc_name1+"/");
    if(session.getAttribute("IP_PROC_NAME")==null||!session.getAttribute("IP_PROC_NAME").toString().equals(basePath1))
        session.setAttribute("IP_PROC_NAME",basePath1);
    //公用的文件服务器项目链接
    String publicFileSystemIpPort1=new StringBuilder(basePath1).toString();
//    //项目
    fileSystemIpPort1=publicFileSystemIpPort1+ UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD")+"/";
    int idx=0;
%>

<html>
<head>
    <script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
    <script type="text/javascript" src="flexpaper/swfobject/swfobject.js"></script>
    <script type="text/javascript" src="js/teachpaltform/testpaper.js"></script>
    <script type="text/javascript">
        var playerController;
        //文件地址:在msg.property中进行编辑
        var resourcepathHead="<%=fileSystemIpPort1%>/uploadfile/";
        <c:if test="${resObj.resid>0}">
        resourcepathHead="<%=fileSystemIpPort1%>clouduploadfile/";
        </c:if>
        var resid="${resObj.resid}";
        var courseid="${courseid}";
        var taskid="${taskid}";
        var paperid="${paperid}";
        var subQuesId=",";
        var quesSize=0;
        $(function(){
            $("li").filter(function(){return this.id.indexOf('c_')!=-1}).each(function(idx,itm){
                $(itm).bind("click",function(){
                    $(itm).siblings().removeClass('crumb');
                    $(itm).addClass('crumb');
                })
            })
            showQuesNum();
//            playerController.play();

        });


        /**
         * 初始化进入
         */
        function paperDivShow(){
            $('#dv_view').hide();
            $('#dv_paper').show();
//            if(typeof(currentQuesid)=="undefined"){
//                currentQuesid=$("#dv_paper table:first").attr("data-bind");
//                currentQIdx=$("#dv_paper table:first #sp_tbl_qidx").html();
//            }
            var currentQIdx=$("#dv_paper table[id*='dv_ques_']").filter(function(){return this.style.display!='none';}).attr("data-idx");
            if(typeof(currentQIdx)=="undefined"||currentQIdx==null){
                next(1);
            }

        }
        /**
         * 加载试卷中的试题
         *
         *
         *
         */
        function showQues(idx){
            // $("#dv_paper table:not(data-idx='"+idx+"')").hide();
            //隐藏dv_ques
            $("#dv_paper table[id*='dv_ques_']").hide();
            var cuTagName=$("#dv_paper table[id*='dv_ques_']").eq((idx-1)).attr("tagName").toUpperCase();
            if(cuTagName=="TR"){//如果是tr，则显示table
                $("#dv_paper table[id*='dv_ques_']").eq((idx-1)).next("tr").fadeIn('fast');
                $("#dv_paper table[id*='dv_ques_']").eq((idx-1)).parent().parent().fadeIn('fast');
            }else{
                $("#dv_paper table[id*='questeam_']").hide();
            }
            $("#dv_paper table[id*='dv_ques_']").eq((idx-1)).fadeIn('slow');
            //序号的样式
            $("#ul_xuhao>li").removeClass("blue_big").removeClass("blue").addClass("blue");
            $("#li_"+idx).removeClass("blue").addClass("blue_big");
            //得到题号，当前的问题
            var currentQuesid=$("#dv_paper table[id*='dv_ques_']").eq((idx-1)).attr("data-bind");
            var currentQIdx=$("#dv_paper table[id*='dv_ques_']").eq((idx-1)).attr("data-idx");

            if(currentQIdx>=$("#ul_xuhao>li").length){
                $("#a_next").hide()
            }else
                $("#a_next").show();
            if(currentQIdx<=1){
                $("#a_free").hide();
            }else
                $("#a_free").show();
        }

        /**
         *下一题
         */
        function next(ty){
//            currentQuesid=$("#dv_paper table:eq("+currentQIdx+")").attr("data-bind");
            //当前的序号
            var currentQIdx=$("#dv_paper table[id*='dv_ques_']").filter(function(){return this.style.display!='none';}).attr("data-idx");
            if(typeof(currentQIdx)=="undefined"||currentQIdx==null){
                currentQIdx=0;
            }
//            currentQIdx=$("#dv_paper table:eq("+(currentQIdx-1)+") #sp_tbl_qidx").html();
            var nextQIdx=parseInt(currentQIdx)+1
            if(ty<1)
                nextQIdx=parseInt(currentQIdx)-1;
            if(nextQIdx>$("#dv_paper table[id*='dv_ques_']").length){
                nextQIdx=1;
            }else if(nextQIdx<1)
                nextQIdx=6;
            showQues(nextQIdx);
            //序号的样式
            $("#ul_xuhao>li").removeClass("blue_big").removeClass("blue").addClass("blue");
            $("#li_"+nextQIdx).removeClass("blue").addClass("blue_big");
        }
        /**
        *得到序列
         */
        function showQuesNum(){
            var h='';
            var qtlength=$("#dv_paper table[id*='dv_ques_']").length;
            $("#dv_paper table[id*='dv_ques_']").each(function(idx,itm){
                h+='<li class="blue quesNumli" id="li_'+(idx+1)+'"><a href="javascript:;" onclick="showQues('+(idx+1)+')">'+(idx+1)+'</a></li>';

            });
            $("#ul_xuhao").html(h);
        }
    </script>
</head>
<body>
<input type="hidden" value="${paperid}" name="hd_paper_id" id="hd_paper_id"/>

    <ul class="jxxt_zhuanti_rw_wkc">
        <li id="c_1" class="crumb"><a href="javascript:;" onclick="$('#dv_view').show();$('#dv_paper').hide();"><strong>微视频</strong></a></li>
        <!--<li id="c_2"><a href="javascript:;" onclick="paperDivShow()"><strong>试卷</strong></a></li>-->
    </ul>
    <div id="dv_view" class="jxxt_float_h560">

        <p class="font-black t_c"><strong>${resObj.resname}</strong><br>${resObj.realname}&nbsp;&nbsp;&nbsp;&nbsp;时长：<span id="videoTime"></span>
        </p>
        <div class="jxxt_zhuanti_rw_wkc_sp">
            <div id="div_show0">
                <img src="images/video_gszh.jpg" width="769px" height="432px" alt="正在排列,转换"/>
                <script type="text/javascript">
                    //是否显示进度条的判断
                    var isShowBar=true;
                    playerController=loadSWFPlayer(resourcepathHead+"${resObj.path}/001${resObj.filesuffixname}",'div_show0'
                            ,resourcepathHead+'${resObj.path}/001${resObj.filesuffixname}.pre.jpg'
                            ,${resObj.resid},769,432,isShowBar);
                    playerController.onTime(function(){
                        var t=playerController.getDuration();
                        $('#videoTime').html(parseInt(t/60)+'分'+parseInt(t%60)+ '秒');
                    })

                </script>
            </div>
        </div>
    </div>

    <div id="dv_paper" class="jxxt_float_h560 " style="display: none">
        <div class="jxxt_zhuanti_rw_ceshi">

            <c:if test="${!empty pqList}">
                <p class="jxxt_zhuanti_rw_ceshi_an"><a href="javascript:;" onclick="next(-1)" id="a_free" class="an_test1">上一题</a><a id="a_next" href="javascript:;" onclick="next(1)" class="an_test1">下一题</a></p>
                <ul id="ul_xuhao">
                    <%--<c:forEach items="${pqList}" var="pq" varStatus="idx">--%>
                        <%--<li class="blue" id="li_${idx.index+1}"><a href="javascript:;" onclick="showQues(${idx.index+1})">${idx.index+1}</a></li>--%>
                        <%--&lt;%&ndash;<li class="blue_big"><a href="1">4</a></li>&ndash;%&gt;--%>
                    <%--</c:forEach>--%>
                </ul>
                <div class="clear"></div>
            </c:if>
        </div>
        <c:if test="${!empty pqList}">
            <c:forEach items="${pqList}" var="pq" varStatus="idx">
                <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input font-black"
                       id="dv_ques_${pq.questionid}"
                       data-bind="${pq.questionid}" style="display:none"
                            data-idx="<%=++idx%>"
                       style="display:none">
                    <col class="w30"/>
                    <col class="w880"/>

                    <caption>
                        <c:if test="${!empty taskid}">
                            <c:if test="${pq.questiontype==1||pq.questiontype==2}">
                                <a href="task?loadStuMicQuesPerformance&courseid=${courseid}&taskid=${taskid}&questionid=${pq.questionid}&type=1&paperid=${paperid}" class="font-blue f_right">查看回答</a>
                            </c:if>
                            <c:if test="${pq.questiontype==3||pq.questiontype==4}">
                                <a href="task?loadStuMicQuesPerformance&courseid=${courseid}&taskid=${taskid}&questionid=${pq.questionid}&type=2&paperid=${paperid}" class="font-blue f_right">查看回答</a>
                            </c:if>
                        </c:if>
                        <span class="font-blue" id="sp_tbl_qidx" style="display:none">${idx.index+1}</span>
                    </caption>

                    <tr>
                        <td>
                            <c:if test="${pq.questionid>0}">
                                <span class="ico44"></span>
                            </c:if>
                        </td>
                        <td>
                            <span class="bg">${pq.questiontypename}</span>${fn:replace(pq.content,'<span name="fillbank"></span>' ,"_____" )}
                            <c:if test="${pq.extension eq 4}">
                                <div  class="p_t_10" id="sp_mp3_${pq.questionid}" ></div>
                                <script type="text/javascript">
                                    playSound('play','<%=UtilTool.utilproperty.getProperty("RESOURCE_QUESTION_IMG_PARENT_PATH")%>/${pq.questionid}/001.mp3',270,22,'sp_mp3_${pq.questionid}',false);
                                </script>
                            </c:if>
                            <c:if test="${!empty pq.questionOption}">
                                <table border="0" cellpadding="0" cellspacing="0">
                                    <col class="w30"/>
                                    <col class="w850"/>
                                    <c:forEach items="${pq.questionOption}" var="option">
                                        <tr>
                                            <th>
                                                <c:if test="${pq.questiontype eq 3 or pq.questiontype eq 7 }">
                                                    <input disabled type="radio">
                                                </c:if>
                                                <c:if test="${pq.questiontype eq 4 or pq.questiontype eq 8 }">
                                                    <input disabled type="checkbox">
                                                </c:if>
                                            </th>
                                            <td>
                                                    ${option.optiontype}&nbsp;${option.content}
                                                <c:if test="${option.isright eq 1}">
                                                    <span class="ico12"></span>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>


                        </td>
                    </tr>

                    <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0 and pq.extension ne 5}">
                        <c:forEach items="${pq.questionTeam}" var="c" varStatus="cidx">
                            <tr id="dv_ques_${c.questionid}" data-bind="${c.questionid}">
                                <td>&nbsp;</td>
                                <td><p><span data-bind="${c.questionid}"  class="font-blue">${(cidx.index+1)+(pq.orderidx-1)}</span>. ${c.content}</p>
                                    <table border="0" cellpadding="0" cellspacing="0">
                                        <col class="w30"/>
                                        <col class="w850"/>
                                        <caption>
                                            <c:if test="${!empty taskid}">
                                                <c:if test="${c.questiontype<3}">
                                                    <a href="task?loadStuMicQuesPerformance&courseid=${courseid}&taskid=${taskid}&questionid=${c.questionid}&type=1&paperid=${paperid}" class="font-blue f_right">查看回答</a>
                                                </c:if>
                                                <c:if test="${c.questiontype==3||c.questiontype==4||c.questiontype==7||c.questiontype==8}">
                                                    <a href="task?loadStuMicQuesPerformance&courseid=${courseid}&taskid=${taskid}&questionid=${c.questionid}&type=2&paperid=${paperid}" class="font-blue f_right">查看回答</a>
                                                </c:if>
                                            </c:if>
                                        </caption>
                                        <c:forEach items="${c.questionOption}" var="option">
                                            <tr>
                                                <th>
                                                    <c:if test="${c.questiontype eq 3 or c.questiontype eq 7}">
                                                        <input disabled type="radio">
                                                    </c:if>
                                                    <c:if test="${c.questiontype eq 4 or c.questiontype eq 8 }">
                                                        <input disabled type="checkbox">
                                                    </c:if>
                                                </th>
                                                <td>
                                                        ${option.optiontype}&nbsp;${option.content}
                                                    <c:if test="${option.isright eq 1}">
                                                        <span class="ico12"></span>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </td>
                            </tr>

                            <tr style="display:none" id="tr_jiexi">
                                <td>&nbsp;</td>
                                <td>
                                    <c:if test="${c.questiontype<3 }">
                                        <p>
                                            <strong>正确答案：</strong>${c.correctanswer}
                                        </p>
                                    </c:if>
                                    <p><strong>答案解析：</strong>${c.analysis}</p>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>


                    <c:if test="${pq.questiontype<6}">
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <p>
                                    <strong>正确答案：</strong>
                                    <c:if test="${pq.questiontype eq 1 or  pq.questiontype eq 2 }">
                                        ${pq.correctanswer}
                                    </c:if>
                                    <c:if test="${pq.questiontype eq 3 or  pq.questiontype eq 4 }">
                                        <c:if test="${!empty pq.questionOption}">
                                            <c:forEach items="${pq.questionOption}" var="option">
                                                <c:if test="${option.isright eq 1}">
                                                    ${option.optiontype}&nbsp;
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:if>
                                </p>
                                <p>
                                    <strong>答案解析：</strong>${pq.analysis}
                                </p>
                            </td>
                        </tr>
                    </c:if>


                    <c:if test="${!empty pq.questionTeam and fn:length(pq.questionTeam)>0 and pq.extension eq 5}">
                        <tr >
                            <td>&nbsp;</td>
                            <td><p><strong>正确答案及答案解析：</strong></p>
                                <c:forEach items="${pq.questionTeam}" var="c" varStatus="cidx">
                                    <p><span data-bind="${c.questionid}"  class="font-blue">${(cidx.index+1)+(pq.orderidx-1)}</span>.
                                        <c:forEach items="${c.questionOption}" var="option">
                                            <c:if test="${option.isright eq 1}">
                                                ${option.optiontype}
                                            </c:if>
                                        </c:forEach>
                                        &nbsp;&nbsp;${c.analysis}
                                    </p>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </c:forEach>
        </c:if>
     </div>
</div>
</body>
</html>
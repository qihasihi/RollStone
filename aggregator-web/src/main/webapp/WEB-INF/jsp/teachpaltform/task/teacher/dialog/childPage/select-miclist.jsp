<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%String optype1=request.getParameter("op_type1");
    if(optype1==null||optype1.trim().length()<1){%>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<%}%>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript">
        var courseid = "${param.courseid}";
        var pList;
        var tasktype="${param.tasktype}";
        var questiontype="${param.questype}";
        $(function () {
            var url='';
            switch (tasktype){
                case '1':url='tpres?toQueryResourceList';break;
                case '2':url='task?toQryTopicList';break;
                case '3':url='question?m=questionAjaxList';break;
                case '4':url='paper?toQueryTaskPaperList';break;
                case '5':url='question?m=questionAjaxList';break;
            }

            pList = new PageControl( {
                post_url : url,
                page_id : 'page1',
                page_control_name : "pList",
                post_form : document.pListForm,
                gender_address_id : 'pListaddress',
                http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 9999, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "initItemList"
            });
           // pageGo('pList');
        });


        function preeDoPageSub(pObj){
            if(typeof(pObj)!='object'){
                alert("异常错误，请刷新页面重试!");
                return;
            }
            if(courseid.length<1){
                alert('异常错误，系统未获取到专题标识!');
                return;
            }
            var param={courseid:courseid};
            if(tasktype=="3")
                param.questype=questiontype;
            else if(tasktype=="4")
                param.type=4;
            else if(tasktype=="5")
                param.type=5;
            param.taskflag=1;
            pObj.setPostParams(param);
        }

        function getInvestReturnMethod(rps){
            var html='',shtml='';
            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    switch (tasktype){
                        case '4':{
                            if(itm.paperid>0){
                                html+='<li><a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
                                html+='<p class="one">'+itm.papername+'</p>';
                                html+='<p class="two">';
                                if(itm.objectivenum>0&&itm.subjectivenum>0)
                                    html+='<span class="bg1" style="width:50%">'+itm.objectivenum+'</span><span class="bg2" style="width:50%">'+itm.subjectivenum+'</span>';
                                else if(itm.objectivenum>0)
                                    html+='<span class="bg2" style="width:100%">'+itm.objectivenum+'</span>';
                                else if(itm.subjectivenum>0)
                                    html+='<span class="bg1" style="width:100%">'+itm.subjectivenum+'</span>';
                                html+='</p></a>';
                                if(itm.has)
                                html+='<p class="pic">';
                                if(itm.taskflag<1)
                                    shtml+='<a href="javascript:sub_data('+itm.paperid+')"><b><span class="ico51" title="发任务"></span></b></a>';
                                else
                                    html+='<b><span class="ico52" title="已发任务"></span></b>';
                                html+='</p>';
                                html+='</li>';
                            }else{
                                shtml+='<li><a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
                                shtml+='<p class="one">'+itm.papername+'</p>';
                                shtml+='<p class="two">';
                                if(itm.objectivenum>0&&itm.subjectivenum>0)
                                    shtml+='<span class="bg1" style="width:50%">'+itm.objectivenum+'</span><span class="bg2" style="width:50%">'+itm.subjectivenum+'</span>';
                                else if(itm.objectivenum>0)
                                    shtml+='<span class="bg1" style="width:100%">'+itm.objectivenum+'</span>';
                                else if(itm.subjectivenum>0)
                                    shtml+='<span class="bg2" style="width:100%">'+itm.subjectivenum+'</span>';
                                else if(typeof itm.quesnum!='undefined')
                                    shtml+='<span class="bg1" style="width:100%">'+itm.quesnum+'</span>';
                                shtml+='</p></a>';
                                shtml+='<p class="pic">';
                                if(itm.taskflag<1)
                                    shtml+='<a href="javascript:sub_data('+itm.paperid+')"><b><span class="ico51" title="发任务"></span></b></a>';
                                else
                                    shtml+='<b><span class="ico52" title="已发任务"></span></b>';
                                shtml+='</p>';
                                shtml+='</li>';
                            }
                        }
                    }
                });
            }
            $("#ul_standard").html(html);
            $("#ul_native").html(shtml);

            if(rps.objList.length>0){
                pList.setPagetotal(rps.presult.pageTotal);
                pList.setRectotal(rps.presult.recTotal);
                pList.setPageSize(rps.presult.pageSize);
                pList.setPageNo(rps.presult.pageNo);
            }else
            {
                pList.setPagetotal(0);
                pList.setRectotal(0);
                pList.setPageNo(1);
            }
            pList.Refresh();
        }

        function sub_data(resid){
            if(typeof resid=='undefined'||isNaN(resid)){
                alert('资源标识错误!请刷新页面重试!');
                return;
            }

//            if (window.opener != undefined) {
//                //for chrome
//                window.opener.returnValue =resid;
//            }
//            else {
                returnValue =resid;
//            }
            <%if(optype1==null||optype1.trim().length()<1){%>
            window.close();
            <%}else{%>
                //显示相关数据
                showTaskElementTopic(6);
                //关闭弹出层
                $.fancybox.close();
            <%}%>

        }

        function showLi(){
            $("li").filter(function(){return this.id.indexOf('li_')!=-1}).show();
        }


    </script>


</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>添加任务—选择微视频</strong></div>
<div class="content2">
    <div class="jxxt_zhuanti_rw_add p_t_20">
        <ul class="gqkt font-black">
            <c:if test="${empty resList}">
                <li><img src="images/pic06_140722.png" width="215" height="160"></li>
            </c:if>

            <c:if test="${!empty resList}">
                <c:forEach    items="${resList}" var="r" varStatus="idx" >
                    <c:if test="${idx.index>12}">
                        <li id="li_${r.resid}" style="display: none;">
                    </c:if>

                    <c:if test="${idx.index<=12}">
                        <li>
                    </c:if>
                    <a href="javascript:loadMicDetail('${r.resid}')"  class="kapian">
                        <p>
                            <%--<img src="<%=fileSystemIpPort%>${r.resid>0?'clouduploadfile':'/uploadfile/'}${r.path}/001${r.filesuffixname}.pre.jpg" width="215" height="122">--%>
                            <img src="images/pic13_140704.jpg" width="215" height="122">
                        </p>
                        <p class="text">${r.realname}&nbsp;&nbsp;${r.resname}</p></a>
                    <c:if test="${r.haspaper eq 1}">
                        <p class="picL">
                            <span class="ico83" title="试卷"></span>
                        </p>
                    </c:if>
                    <p class="pic">
                       <%-- <a class="ico43" href="javascript:sub_data('${r.resid}')" title="发任务"></a>--%>
                           <a class="ico43" href="javascript:loadRelatePage('${r.resid}')" title="关联试卷"></a>

                    </p>
                    </li>

                </c:forEach>
            </c:if>
        </ul>
        <c:if test="${fn:length(resList)>12}">
            <p class="font-darkblue t_c clearit"><a href="javascript:showLi();" ><span class="ico49a"></span>查看更多微视频</a></p>
        </c:if>
        <div class="clear"></div>
    </div>
</div>

<div class="content1 font-black" style="display: none;">

    <ul class="jxxt_zhuanti_shijuan_list" id="ul_standard">
        <!--<li><a href="1" target="_blank">
            <p class="one">中国语文高一年级高清课堂视频讲解高清课堂名</p>
            <p class="two"><span class="bg1" style="width:100%">8</span></p></a>
            <p class="pic"><a class="ico_wsp1" title="微视频"></a><a href="1"><b><span class="ico51" title="发任务"></span></b></a></p>
        </li>-->
        <c:if test="${!empty resList}">
            <c:forEach    items="${resList}" var="r">
                <li><a href="javascript:;" >
                    <p class="one">${r.resname}</p>
                    <p class="pic"><a class="ico_wsp1" title="微视频"></a><a href="javascript:loadRelatePage('${r.resid}')"><b><span class="ico43" title="关联试卷"></span></b></a></p>
                </li>
            </c:forEach>
        </c:if>

    </ul>



    <form id="pListForm" name="pListForm" style="display: none;">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
</div>

<%@include file="/util/foot.jsp" %>
</body>
</html>

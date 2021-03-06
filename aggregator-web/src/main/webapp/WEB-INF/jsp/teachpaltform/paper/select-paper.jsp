<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
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
            pageGo('pList');
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
            var paramObj={width:1000,height:700}

            var left =findDimensions().width/2-parseFloat($("#ul_native").css("width"))/2;
            var top =100;//findDimensions().height/2-parseFloat($("#p_operate").css("height"))/2-120;
            var param = 'dialogWidth:'+paramObj.width+'px;dialogHeight:'+paramObj.height+'px;dialogLeft:'+left+'px;dialogTop:'+top+'px;status:no;location:no';


            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    switch (tasktype){
                        case '4':{
                            if(itm.paperid>0){
                                var url='paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid;
                                html+='<li><a href="javascript:window.showModalDialog(\''+url+'\',\'\',\''+param+'\');">';
                                html+='<p class="one">'+itm.papername+'</p>';
                                html+='<p class="two">';
                                if(itm.objectivenum>0&&itm.subjectivenum>0)
                                    html+='<span class="bg1" style="width:50%">'+itm.objectivenum+'</span><span class="bg2" style="width:50%">'+itm.subjectivenum+'</span>';
                                else if(itm.objectivenum>0)
                                    html+='<span class="bg1" style="width:100%">'+itm.objectivenum+'</span>';
                                else if(itm.subjectivenum>0)
                                    html+='<span class="bg2" style="width:100%">'+itm.subjectivenum+'</span>';
                                html+='</p></a>';
                                html+='<p class="pic">';
                                if(itm.taskflag<1)
                                    html+='<a href="javascript:sub_data('+itm.paperid+')"><b><span class="ico51" title="发任务"></span></b></a>';
                                else
                                    html+='<b><span class="ico52" title="已发任务"></span></b>';
                                html+='</p>';
                                html+='</li>';
                            }else{
                                var url='paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid;
                                shtml+='<li><a href="javascript:window.showModalDialog(\''+url+'\', \'\',  \''+param+'\');">';
                                //shtml+='<li><a href="paper?toPreviewPaper&courseid='+itm.courseid+'&paperid='+itm.paperid+'">';
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
            }else{
                html='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
                shtml='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
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

        function sub_data(paperid){
            if(typeof paperid=='undefined'||isNaN(paperid)){
                alert('试卷标识错误!请刷新页面重试!');
                return;
            }

            if (window.opener != undefined) {
                //for chrome
                window.opener.returnValue =paperid;
            }
            else {
                window.returnValue =paperid;
            }
            window.close();
        }
    </script>


</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>添加任务—选择试卷</strong></div>
<div class="content1 font-black">
    <p class="p_b_10">图例：<span class="ico81"></span>客观题&nbsp;&nbsp;<span class="ico80"></span>主观题</p>
    <c:if test="${!empty courselevel and courselevel ne 3}">
        <p><strong>标准试卷</strong></p>
        <ul class="jxxt_zhuanti_shijuan_list" id="ul_standard">

        </ul>
    </c:if>
    <p><strong>自建试卷</strong></p>
    <ul class="jxxt_zhuanti_shijuan_list" id="ul_native">

    </ul>
</div>

<form id="pListForm" name="pListForm" style="display: none;">
    <p class="Mt20" id="pListaddress" align="center"></p>
</form>

<%@include file="/util/foot.jsp" %>
</body>
</html>

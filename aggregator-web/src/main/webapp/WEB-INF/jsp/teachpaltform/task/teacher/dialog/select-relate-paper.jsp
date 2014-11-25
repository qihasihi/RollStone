<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>
    <script type="text/javascript">
        var courseid = "${param.courseid}";
        var pListR;
        var tpid;
        $(function () {
            var url='paper?toQueryMicRelatePaper';

            pListR = new PageControl( {
                post_url : url,
                page_id : 'pageR',
                page_control_name : "pListR",
                post_form : document.pListFormR,
                gender_address_id : 'pListaddressR',
                http_free_operate_handler : preeDoPageSubR, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethodR, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 4, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "initItemList"
            });
            pageGo('pListR');
            loadDiv(1);
        });


        function preeDoPageSubR(pObj){
            if(typeof(pObj)!='object'){
                alert("异常错误，请刷新页面重试!");
                return;
            }
            if(courseid.length<1){
                alert('异常错误，系统未获取到专题标识!');
                return;
            }
            var param={courseid:courseid};
            param.type=4;
            param.taskflag=1;
            pObj.setPostParams(param);
        }

        function getInvestReturnMethodR(rps){
            var html='',shtml='';
            if(rps.objList[0]!=null&&rps.objList[0].length>0){
                $.each(rps.objList[0],function(idx,itm){
                    shtml+='<li><a href="javascript:;" onclick="loadPaperDetail('+itm.paperid+',undefined,1)">';
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
                        shtml+='<a href="javascript:sub_data('+itm.paperid+')"><b><span class="ico43" title="关联试卷"></span></b></a>';
                    else
                        shtml+='<b><span class="ico52" title="已发任务"></span></b>';
                    shtml+='</p>';
                    shtml+='</li>';
                });
            }else{
                shtml='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
            }



            if(rps.objList[1]!=null&&rps.objList[1].length>0){
                $.each(rps.objList[1],function(idx,itm){
                    if(itm.paperid>0){
                        html+='<li><a href="javascript:;" onclick="loadPaperDetail('+itm.paperid+',undefined,1)">';
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
                            html+='<a href="javascript:sub_data('+itm.paperid+')"><b><span class="ico43" title="关联试卷"></span></b></a>';
                        else
                            html+='<b><span class="ico52" title="已发任务"></span></b>';
                        html+='</p>';
                        html+='</li>';
                    }
                });
            }else{
                html='<li><img src="images/pic06_140722.png" width="215" height="160"></li>';
            }

            $("#ul_standard").html(html);
            $("#ul_native").html(shtml);

            if(rps.objList.length>0){
                pListR.setPagetotal(rps.presult.pageTotal);
                pListR.setRectotal(rps.presult.recTotal);
                pListR.setPageSize(rps.presult.pageSize);
                pListR.setPageNo(rps.presult.pageNo);
            }else
            {
                pListR.setPagetotal(0);
                pListR.setRectotal(0);
                pListR.setPageNo(1);
            }
            pListR.Refresh();
        }

        function sub_data(paperid){
            if(typeof paperid=='undefined'||isNaN(paperid)){
                alert('试卷标识错误!请刷新页面重试!');
                return;
            }
            if (typeof(courseid) == 'undefined' || courseid.length < 1) {
                alert('异常错误，系统未获取到课题标识!');
                return;
            }
            var resid=$("#hd_resdetailid").val();
            if(resid.length<1){
                alert('系统未获取到资源标识!');
                return;
            }
            if(!confirm("确认关联该试卷?"))return;

            $.ajax({
                url: 'tpres?doAddVideoPaper',
                type: 'post',
                data: {courseid: courseid, paperid: paperid,resid:resid},
                dataType: 'json',
                error: function () {
                    alert('网络异常!');
                },
                success: function (json) {
                   if(json.type=='success'){
                       load_resource(1,1,true);
                       $.fancybox.close();
                   }else{
                       alert(json.msg);
                   }
                }
            });
        }

        /**
         *加载详情页面
         * @param resid
         */
        function loadPaperDetail(paperid,cid,isselect){
            if(typeof(paperid)=="undefined"||paperid==null){
                alert('网络异常!参数异常!');
                return;
            }
            $("#dv_model").hide();
            $("#dv_model_child").hide();
            <%//参数解析 dropQuesNum:1 表示可以拖拽调整试题顺序  其它则是不提供此功能。%>
            if(typeof(cid)=="undefined"||cid==null)
                cid=${param.courseid};
            var u="paper?toPreviewPaperModel&courseid="+cid+"&paperid="+paperid+"&op_type1=1&dropQuesNum=0";
            if(typeof(isselect)!="undefined")
                u+="&isselect=1";
            $("#dv_paperDetail").load(u
                    ,function(rps){
                        // $("dv_load_topic").html(rps);
//                        $(".content1:last").removeClass("content1");
//                        $(".subpage_head:last").remove();
//                        $(".foot:last").remove();
//                        $("#fade:last").remove();
                        $(".an_small").removeClass("an_small").addClass("an_public1").last().remove();
                        //$("#dv_selectMic_child").show();
                        $("#dv_selectPaper").hide();
                        $("#dv_paperDetail").show();
                    });
        }

        function paperDetailReturn(isselect){
            $("#dv_paperDetail").hide();
            if(typeof(isselect)!="undefined"){
                $("#dv_selectPaper").show();
            }else{
                $("#dv_model").show();
                $("#dv_model_child").show();
            }
        }


        function loadDiv(type){
            $("#dv_paperDetail").hide();
            $("#dv_selectPaper").show();
            if(type==1){
                $("#li_1").addClass("crumb");
                $("#li_2").removeClass("crumb");
                $("#dv_addpaperchild").hide();
                $("#dv_selpaperchild").show();
            }else if(type==2){
                $("#li_2").addClass("crumb");
                $("#li_1").removeClass("crumb");
                $("#dv_selpaperchild").hide();
                $("#dv_addpaperchild").show();
            }else if(type==3){
                $("#dv_selectPaper").hide();
                $("#dv_addpaperchild").hide();
                $("#dv_model").hide();
                $("#dv_paperDetail").hide();
                $("#dv_paperDetail").load(
                        "paper?m=editPaperQuestionModel&courseid=${param.courseid}&paperid="+tpid+"&isrelate=1"
                ,function(){
                            $("#dv_selectPaper").hide();
                            $("#dv_paperDetail").show();
                        });

            }
        }
    /**
    * 新建试卷
    */
    function doSaveRelatePaper(){
        /**
         * 添加试卷
         */
            var papername=$("#papername");
            if(papername.val().Trim().length<1){
                alert('请输入试卷名称!');
                papername.focus();
                return;
            }
            $.ajax({
                url:"paper?doSubAddPaper",
                type:"post",
                data:{courseid:courseid,papername:papername.val()},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        paperid=tpid=rmsg.objList[0];
                        $("#dv_paper_content_child").html('');
                        $("#dv_paperDetail").load("paper?m=editPaperQuestionModel&courseid="+courseid+"&paperid="+paperid+"&isrelate=1");

                        $("#dv_selectPaper").hide();
                        $("#dv_paperDetail").show();
                    }
                }
            });

    }

        /**
         *显示导入试卷
         */
        function showImportPaper(pid){
            $("#dv_selectPaper").hide();
            $("#dv_addpaperchild").hide();
            $("#dv_paperDetail").hide();
            $("#dv_model_mdname").html("导入试卷");
            $("#dv_model").show();
            $("#dv_paper_content_child").html('');
            $("#dv_model_child").hide();
            $("#dv_model_child").load("paper?m=dialogPaperModel&courseid="+courseid+"&paperid="+pid+"&isrelate=1",function(){
                $("#dv_model_child").show();
            });
        }
        /**
         *显示导入试题
         */
        function showImportQues(pid){
            $("#dv_selectPaper").hide();
            $("#dv_addpaperchild").hide();
            $("#dv_paperDetail").hide();
            $("#dv_model_mdname").html("导入试题");
            $("#dv_model").show();
            $("#dv_paper_content_child").html('');
            $("#dv_model_child").hide();
            $("#dv_model_child").load("paper?m=dialogQuestionModel&courseid="+courseid+"&paperid="+pid+"&isrelate=1",function(){
                $("#dv_model_child").show();
            });
        }
        /**
         *显示新建试题
         */
        function showCreateQues(pid){
            $("#dv_selectPaper").hide();
            $("#dv_addpaperchild").hide();
            $("#dv_paperDetail").hide();
            $("#dv_model_mdname").html("新建试题");
            $("#dv_model").show();
            $("#dv_model_child").hide();
            $("#dv_paper_content_child").html('');
            $("#dv_model_child").load("question?m=toDialogAddPaperQues&courseid="+courseid+"&paperid="+pid+"&isrelate=1",function(){
                $("#dv_model_child").show();
            });
        }

        /**
         *显示新建试题
         */
        function showUpdQues(pid,qid){
            $("#dv_selectPaper").hide();
            $("#dv_addpaperchild").hide();
            $("#dv_paperDetail").hide();
            $("#dv_model_mdname").html("修改试题");
            $("#dv_model").show();
            $("#dv_model_child").hide();
            $("#dv_paper_content_child").html('');
            $("#dv_model_child").load("question?m=toUpdDialogQuestion&courseid="+courseid+"&paperid="+pid+"&questionid="+qid+"&isrelate=1",function(){
                $("#dv_model_child").show();
            });
        }






    </script>


</head>
<body>
<%--试卷三级目录--%>
<div class="public_float public_float960" id="dv_model" style="display:none">
    <div class="public_float public_float960"  id="dv_model_child">

    </div>
</div>
<div class="public_float public_float960" id="dv_selectPaper">
    <p class="float_title"><strong>关联试卷</strong></p>
    <div class="subpage_lm">
        <ul>
            <li class="crumb" id="li_1"><a href="javascript:;" onclick="loadDiv(1)">选择试卷</a></li>
            <li  id="li_2"><a href="javascript:;" onclick="loadDiv(2)">新建试卷</a></li>
        </ul>
    </div>
    <%--选择试卷--%>
    <div  style="display:none" id="dv_selpaperchild">
        <div class="jxxt_float_w920 font-black">
            <p class="t_r">图例：<span class="ico81"></span>客观题&nbsp;&nbsp;<span class="ico80"></span>主观题</p>
            <!--<div style="overflow-x: hidden;overflow-y:auto;width:930px;height:530px;">-->
                    <p><strong>标准试卷</strong></p>
                    <ul class="jxxt_zhuanti_shijuan_list" id="ul_standard">

                    </ul>
                <p><strong>自建试卷</strong></p>
                <ul class="jxxt_zhuanti_shijuan_list" id="ul_native">

                </ul>
                <form id="pListFormR" name="pListFormR" style="">
                    <div class="nextpage" id="pListaddressR" align="center"></div>
                </form>
        </div>

    </div>
    <%--新建试卷--%>
    <div id="dv_addpaperchild" style="display:none">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input ">
            <col class="w90"/>
            <col class="w600"/>
            <tr>
                <th>试卷名称：</th>
                <td><input name="papername" id="papername" type="text" class="w350"/></td>
            </tr>
            <tr>
                <th>&nbsp;</th>
                <td><a href="javascript:;" onclick="doSaveRelatePaper()" class="an_public1">下一步</a></td>
            </tr>
        </table>
    </div>
</div>
<!--试卷详情-->
<div class="public_float public_float960" id="dv_paperDetail" style="display:none">

</div>
</body>
</html>

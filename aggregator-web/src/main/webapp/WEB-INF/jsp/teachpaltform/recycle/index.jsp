<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<%
    String basepath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort();

%>
<html>
<head>
    <title>${sessionScope.CURRENT_TITLE}</title>
    <style>
    </style>


    <link rel="stylesheet" type="text/css" href="<%=basePath %>comment/comment_star.css"/>
    <script type="text/javascript" src="<%=basePath %>comment/commentScorePlug.js"></script>
    <script type="text/javascript" src="js/resource/new_resbase.js"></script>
    <script type="text/javascript"
            src="<%=basePath %>js/teachpaltform/resource.js"></script>　
    <script type="text/javascript">
        var courseid="${courseid}";
        var pRecycle;
        var type="${type}";
        var url;
        $(function(){
            if(typeof type=='undefined'||type.length<1)
                return;

            $('#li_'+type+'').addClass("crumb");

            $("li").filter(function(){return this.id.indexOf('li_')!=-1}).each(function(idx,itm){
               $(itm).bind("click",function(){
                   $(itm).addClass("crumb").andSelf().siblings().removeClass("crumb");
               });
            });
            getUrlByType();

            pRecycle = new PageControl( {
                post_url : url,
                page_id : 'pRecycle',
                page_control_name : "pRecycle",
                post_form : document.pListForm,
                gender_address_id : 'pListaddress',
                http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 5, //当前页面显示的
                // 数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "initItemList"
            });

            pageGo('pRecycle');

        });
            /**
             *
             * 根据Type得到URL
             * */
            function getUrlByType(){

                if(type=="1"){
                    url="tpres?m=loadRecycleTask";
                }else if(type=="2"){
                    url="tpres?m=loadRecycleResource";
                }else if(type=="3"){
                    url="tpres?m=loadRecycleQuestion";
                }else if(type=="4"){
                    url="tptopic?m=ajaxtopiclist&status=3&selectType=1";
                }else if(type=="5"){
                    url="paper?m=loadRecyclePaper";
                }
            }


        /**
         * 资源评论
         * @param rps
         */
        function getInvestReturnMethod(rps){
            var html='';
            if(rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    var status='';
                    html+='<tr';
                    if((idx+1)%2==0)
                        html+=' class="trbg1"';
                    html+='>';

                    if(type=="1"){
                        status=typeof itm.cloudstatus!='undefined'&&itm.cloudstatus==3?'<span class="ico44" title="参考" ></span>':'<span class="ico16" title="自建"></span>';
                        var tasktype=itm.tasktype==1?"资源学习：":itm.tasktype==2?"互动交流：":"试&nbsp;&nbsp;&nbsp;&nbsp;题：";
                        html+='<td><p class="one">'+status+'<span class="left">'+tasktype+'</span><span class="right">'+(replaceAll(replaceAll(itm.taskobjname.toLowerCase(),"<p>",""),"</p>",""))+'</span></p></td>';
                        html+='<td>'+itm.mtimeString+'</td>';
                        html+='<td><a href="javascript:doRestoreTask('+itm.taskid+')" class="ico25" title="恢复"></a></td>';
                    }else if(type=="2"){
                        status=itm.resdegree==1?'<span class="ico18" title="标准" ></span>':itm.resdegree==2?'<span class="ico17" title="共享"></span>':'<span class="ico16" title="自建"></span>';
                        html+='<td><p class="one">'+status+'<span class="'+itm.suffixtype+'"></span>'+itm.resname+'</p></td>';
                        html+='<td>'+itm.mtimeString+'</td>';
                        html+='<td><a href="javascript:doRestoreResource('+itm.ref+')" class="ico25" title="恢复"></a></td>';
                    }else if(type=="3"){
                        status=itm.questionlevel==1?'<span class="ico18" title="标准" ></span>':itm.questionlevel==2?'<span class="ico17" title="共享"></span>':'<span class="ico16" title="自建"></span>';
                        var questype=itm.questiontype==1?"其&nbsp;&nbsp;&nbsp;&nbsp;他：":itm.questiontype==2?"填&nbsp;&nbsp;&nbsp;&nbsp;空：":itm.questiontype=="3"?"单&nbsp;&nbsp;&nbsp;&nbsp;选：":"多&nbsp;&nbsp;&nbsp;&nbsp;选："
                        html+='<td><p class="one">'+status+'<span class="left">'+questype+'</span><span class="right">'+(replaceAll(replaceAll(itm.content.toLowerCase(),"<p>",""),"</p>",""))+'</span></p></td>';
                        html+='<td>'+itm.mtimeString+'</td>';
                        html+='<td><a href="javascript:doRestoreQuestion('+itm.ref+')" class="ico25" title="恢复"></a></td>';
                    }else if(type==4){
                        html+='<td><p class="one">';
                        var t=(typeof(itm.cloudstatus)=="undefined"||(itm.cloudstatus!=3&&itm.cloudstatus!=4))?0:itm.cloudstatus;
                        if(t==3)        //共享
                            html+='<span class="ico17" title="共享"></span>';
                        else if(t==4)   //标准
                            html+='><span class="ico18" title="标准" ></span>';
                        else         //自建
                            html+='<span class="ico16" title="自建"></span>';
                        html+=itm.topictitle
                        html+='</p></td>';
                        html+='<td>'+itm.mtimeString+'</td>';
                        html+='<td><a href="javascript:;" onclick="doRestoreTopic('+itm.topicid+')" class="ico25" title="恢复"></a></td>';
                    }else if(type=="5"){
                        status=itm.paperid>0?'<span class="ico18" title="标准" ></span>':'<span class="ico16" title="自建"></span>';
                        html+='<td><p class="one">'+status+itm.papername+'</p></td>';
                        html+='<td>'+itm.mtimeString+'</td>';
                        html+='<td><a href="javascript:doRestorePaper('+itm.ref+')" class="ico25" title="恢复"></a></td>';
                    }
                    html+='</tr>';
                });
            }else{
                html='<tr><td colspan="3"><p>暂无数据!</p></td></tr>';
            }
            $("tbody[id='initItemList']").html(html);


            if(rps.objList.length>0){
                pRecycle.setPagetotal(rps.presult.pageTotal);
                pRecycle.setRectotal(rps.presult.recTotal);
                pRecycle.setPageSize(rps.presult.pageSize);
                pRecycle.setPageNo(rps.presult.pageNo);
            }else
            {
                pRecycle.setPagetotal(0);
                pRecycle.setRectotal(0);
                pRecycle.setPageNo(1);
            }
            pRecycle.Refresh();
        }

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

            pObj.setPostParams(param);
        }

        /**
         * 恢复任务
         * @param ref
         */
        function doRestoreTopic(tpicid){
            if(typeof tpicid=='undefined')
                return;
            if(!confirm("确认恢复?"))return;
            $.ajax({
                url:"tptopic?m=doUpdateTopic",
                type:"post",
                data:{topicid:tpicid,status:1},//默认开启
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        alert(rmsg.msg);
                        pageGo('pRecycle');
                    }
                }
            });
        }

        /**
         * 恢复任务
         * @param ref
         */
        function doRestoreTask(taskid){
            if(typeof taskid=='undefined')
                return;
            if(!confirm("确认恢复?"))return;
            $.ajax({
                url:"task?m=doRestoreTask",
                type:"post",
                data:{taskid:taskid},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        alert(rmsg.msg);
                        pageGo('pRecycle');
                    }
                }
            });
        }


        /**
         * 恢复资源
         * @param ref
         */
        function doRestoreResource(ref){
            if(typeof ref=='undefined')
                return;
            if(!confirm("确认恢复?"))return;
            $.ajax({
                url:"tpres?m=doRestoreResource",
                type:"post",
                data:{ref:ref},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        alert(rmsg.msg);
                        pageGo('pRecycle');
                    }
                }
            });
        }


        /**
         * 恢复试题
         * @param ref
         */
        function doRestoreQuestion(ref){
            if(typeof ref=='undefined')
                return;
            if(!confirm("确认恢复?"))return;
            $.ajax({
                url:"question?m=doRestoreQuestion",
                type:"post",
                data:{ref:ref},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        alert(rmsg.msg);
                        pageGo('pRecycle');
                    }
                }
            });
        }



        /**
         * 恢复试卷
         * @param ref
         */
        function doRestorePaper(ref){
            if(typeof ref=='undefined')
                return;
            if(!confirm("确认恢复?"))return;
            $.ajax({
                url:"paper?m=doOperatePaper",
                type:"post",
                data:{ref:ref,flag:1},
                dataType:'json',
                cache: false,
                error:function(){
                    alert('系统未响应，请稍候重试!');
                },success:function(rmsg){
                    if(rmsg.type=="error"){
                        alert(rmsg.msg);
                    }else{
                        alert(rmsg.msg);
                        pageGo('pRecycle');
                    }
                }
            });
        }

    function changePannel(t){
        type=t;
        getUrlByType();
        pRecycle.setPostUrl(url);
        pageGo('pRecycle');
    }


    </script>


</head>
<body>
<div class="subpage_head"><span class="ico15"></span><strong>回收站——${coursename}</strong></div>
<div class="subpage_nav">
    <ul>
        <!--<li id="li_1"><a href="javascript:;" onclick="changePannel(1)">任务</a></li>-->
        <li id="li_2"><a href="javascript:;" onclick="changePannel(2)">资源</a></li>
        <li id="li_4"><a href="javascript:;" onclick="changePannel(4)">论题</a></li>
        <li id="li_3"><a  href="javascript:;" onclick="changePannel(3)">试题</a></li>
        <li id="li_5"><a  href="javascript:;" onclick="changePannel(5)">试卷</a></li>
    </ul>
</div>
<div class="content2">
    <div class="jxxt_tab_layout">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w700"/>
            <col class="w140"/>
            <col class="w100"/>
            <tr>
                <th>内容</th>
                <th>删除时间</th>
                <th>操作</th>
            </tr>
            <tbody id="initItemList">
                <!--
                    <tr>
                    <td><p class="one"><span class="ico16" title="自建"></span><span class="left">互动交流：</span><span class="right">那些年，我与鲁迅</span></p></td>
                    <td>2013-11-03 12:30</td>
                    <td><a href="1" class="ico25" title="恢复"></a></td>
                </tr>
                <tr class="trbg1">
                    <td><p class="one"><span class="ico17" title="共享"></span><span class="left">试&nbsp;&nbsp;&nbsp;&nbsp;题：</span><span class="right">伤逝》这篇手记体小说在具体行文中通过空一行以作为手记体的标识，行文中通过空一行以作为手记体的标识因此， 它实际上是由_____节手记组成的？</span></p></td>
                    <td>2013-11-03 12:30</td>
                    <td><a href="1" class="ico25" title="恢复"></a></td>
                </tr>
                <tr>
                    <td><p class="one"><span class="ico18" title="标准" ></span><span class="left">试&nbsp;&nbsp;&nbsp;&nbsp;卷：</span><span class="right">查看试卷</span></p></td>
                    <td>2013-11-03 12:30</td>
                    <td><a href="1" class="ico25" title="恢复"></a></td>
                </tr>
                <tr class="trbg1">
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                -->
            </tbody>
        </table>
    </div>
    <form id="pListForm" name="pListForm">
        <p class="Mt20" id="pListaddress" align="center"></p>
    </form>
<%@include file="/util/foot.jsp" %>
</body>


</html>

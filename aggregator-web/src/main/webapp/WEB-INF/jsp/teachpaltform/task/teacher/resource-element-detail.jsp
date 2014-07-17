<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-14
  Time: 下午2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/util/common-jsp/common-jxpt.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var courseid = "${param.courseid}";
        var pList1;
        var pList2;
        var pList3;
        var tasktype="${param.tasktype}";
        $(function () {
            var url='tpres?toQueryResourceList';
            pList1 = new PageControl( {
                post_url : url,
                page_id : 'page1',
                page_control_name : "pList1",
                post_form : document.pListForm1,
                gender_address_id : 'pListaddress1',
                http_free_operate_handler : preeDoPageSub1, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethod1, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 5, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "mainTab1"
            });
            pList2 = new PageControl( {
                post_url : 'tpres?toQueryRelatedResourceList',
                page_id : 'page2',
                page_control_name : "pList2",
                post_form : document.pListForm2,
                gender_address_id : 'pListaddress2',
                http_free_operate_handler : preeDoPageSub2, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethod2, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 5, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "mainTab2"
            });
            pList3 = new PageControl( {
                post_url : 'tpres?toQueryLikeResourceList',
                page_id : 'page3',
                page_control_name : "pList3",
                post_form : document.pListForm3,
                gender_address_id : 'pListaddress3',
                http_free_operate_handler : preeDoPageSub3, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethod3, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 5, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "mainTab3"
            });
            pageGo('pList1');
            pageGo('pList2');
            pageGo('pList3');
        });

        function preeDoPageSub1(pObj){
            if(typeof(pObj)!='object'){
                alert("异常错误，请刷新页面重试!");
                return;
            }
            if(courseid.length<1){
                alert('异常错误，系统未获取到专题标识!');
                return;
            }
            var param={courseid:courseid};
            param.taskflag=1;
            pObj.setPostParams(param);
        }
        function preeDoPageSub2(pObj){
            if(typeof(pObj)!='object'){
                alert("异常错误，请刷新页面重试!");
                return;
            }
            if(courseid.length<1){
                alert('异常错误，系统未获取到专题标识!');
                return;
            }
            var param={courseid:courseid};
            param.taskflag=1;
            pObj.setPostParams(param);
        }
        function preeDoPageSub3(pObj){
            if(typeof(pObj)!='object'){
                alert("异常错误，请刷新页面重试!");
                return;
            }
            var param={};
            param.gradeid =$("#grade").val();
            param.subjectid=${param.subjectid};
            param.name=$("#likename").val();
            pObj.setPostParams(param);
        }
        function getInvestReturnMethod1(rps){
            var htm='';
            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    var introduce=typeof itm.resintroduce=='undefined'||itm.resintroduce.length<1?"":replaceAll(replaceAll(itm.resintroduce.toLowerCase(),"<p>",""),"</p>","");
                    var username=typeof itm.username=='undefined'||itm.username.length<1?"":itm.username;
                    var type=itm.resourcetype==1?"学习资源":"教学参考";

                    htm+='<tr>';
                    htm+='<td><a href="javascript:subData('+itm.resid+')" class="ico51" title="发任务"></a>';
                    htm+='<span class="'+itm.suffixtype+'"></span></td>';
                    htm+='<td><p><a href="javascript:void(0);" target="_blank">'+itm.resname+'</a></p>';
                    htm+='<p>'+introduce+'</p>';
                    htm+='<p class="jxxt_zhuanti_zy_add_text">'+username+'&nbsp;&nbsp;&nbsp;'+type+'&nbsp;&nbsp;&nbsp;'+itm.restypename+'</p></td>';
                    htm+='</tr>';
                });
                $("#mainTab1").html(htm);

                if(rps.objList.length>0){
                    pList1.setPagetotal(rps.presult.pageTotal);
                    pList1.setRectotal(rps.presult.recTotal);
                    pList1.setPageSize(rps.presult.pageSize);
                    pList1.setPageNo(rps.presult.pageNo);
                }else
                {
                    pList1.setPagetotal(0);
                    pList1.setRectotal(0);
                    pList1.setPageNo(1);
                }
                pList1.Refresh();
            }else{
                $("#mainTab1").html('没有数据');
            }
        }
        function getInvestReturnMethod2(rps){
            var htm='';
            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    var introduce=typeof itm.resintroduce=='undefined'||itm.resintroduce.length<1?"":replaceAll(replaceAll(itm.resintroduce.toLowerCase(),"<p>",""),"</p>","");
                    var username=typeof itm.username=='undefined'||itm.username.length<1?"":itm.username;
                    var type=itm.resourcetype==1?"学习资源":"教学参考";

                    htm+='<tr>';
                    htm+='<td><a href="javascript:subData2('+itm.resid+','+itm.courseid+')" class="ico51" title="发任务"></a>';
                    htm+='<span class="'+itm.suffixtype+'"></span></td>';
                    htm+='<td><p><a href="javascript:void(0);" target="_blank">'+itm.resname+'</a></p>';
                    htm+='<p>'+introduce+'</p>';
                    htm+='<p class="jxxt_zhuanti_zy_add_text">'+username+'&nbsp;&nbsp;&nbsp;'+type+'&nbsp;&nbsp;&nbsp;'+itm.restypename+'</p></td>';
                    htm+='</tr>';
                });
                $("#mainTab2").html(htm);

                if(rps.objList.length>0){
                    pList2.setPagetotal(rps.presult.pageTotal);
                    pList2.setRectotal(rps.presult.recTotal);
                    pList2.setPageSize(rps.presult.pageSize);
                    pList2.setPageNo(rps.presult.pageNo);
                }else
                {
                    pList2.setPagetotal(0);
                    pList2.setRectotal(0);
                    pList2.setPageNo(1);
                }
                pList2.Refresh();
            }else{
                $("#mainTab2").html('没有数据');
            }
        }
        function getInvestReturnMethod3(rps){
            var htm='';
            if(rps.objList!=null&&rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    var introduce=typeof itm.resintroduce=='undefined'||itm.resintroduce.length<1?"":replaceAll(replaceAll(itm.resintroduce.toLowerCase(),"<p>",""),"</p>","");
                    var username=typeof itm.username=='undefined'||itm.username.length<1?"":itm.username;
                    var type=itm.resourcetype==1?"学习资源":"教学参考";

                    htm+='<tr>';
                    htm+='<td><a href="javascript:subData2('+itm.resid+','+itm.courseid+')" class="ico51" title="发任务"></a>';
                    htm+='<span class="'+itm.suffixtype+'"></span></td>';
                    htm+='<td><p><a href="javascript:void(0);" target="_blank">'+itm.resname+'</a></p>';
                    htm+='<p>'+introduce+'</p>';
                    htm+='<p class="jxxt_zhuanti_zy_add_text">'+username+'&nbsp;&nbsp;&nbsp;'+type+'&nbsp;&nbsp;&nbsp;'+itm.restypename+'</p></td>';
                    htm+='</tr>';
                });
                $("#mainTab3").html(htm);

                if(rps.objList.length>0){
                    pList3.setPagetotal(rps.presult.pageTotal);
                    pList3.setRectotal(rps.presult.recTotal);
                    pList3.setPageSize(rps.presult.pageSize);
                    pList3.setPageNo(rps.presult.pageNo);
                }else
                {
                    pList3.setPagetotal(0);
                    pList3.setRectotal(0);
                    pList3.setPageNo(1);
                }
                pList3.Refresh();
            }else{
                $("#mainTab3").html('没有数据');
            }
        }
        function subData(resid){
            if (window.opener != undefined) {
                //for chrome
                window.opener.returnValue =resid+",1";
            }
            else {
                window.returnValue =resid+",1";
            }
            window.close();
        }
        function subData2(resid,cid){
            $.ajax({
                url: 'tpres?m=addResource',
                type: 'post',
                data: {
                    courseid: courseid,
                    resArray: resid,
                    resourcetype: 1
                },
                dataType: 'json',
                cache: false,
                error: function () {
                    alert('网络异常!')
                },
                success: function (rps) {
                    if (rps.type == "error") {
                        alert(rps.msg);
                    } else {
                        if (window.opener != undefined) {
                            //for chrome
                            window.opener.returnValue =resid+",1,"+cid;
                        }
                        else {
                            window.returnValue =resid+",1,"+cid;
                        }
                        window.close();
                    }
                }
            });

        }
    function showLike(){
        $("#local").hide();
        $("#like").show();
        pageGo('pList3');
    }
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico55"></span><strong>添加任务&mdash;&mdash;选择资源</strong></div>
<div class="content2">
    <div class="subpage_lm">
        <ul>
            <li class="crumb"><a href="1">本地资源</a></li>
            <li><a href="tpres?m=toRemoteResources&courseid=${courseid}">远程资源</a></li>
        </ul>
    </div>

    <div class="jxxt_zhuanti_rw_add">
        <p class="public_input f_right">
            <select name="select3" id="grade">
                <option selected value="3">高一</option>
                <option value="2">高二</option>
                <option value="1">高三</option>
            </select>
            <input name="textfield2" id="likename" type="text" class="w240" placeholder="资源名称/专题名称" />
            <a href="javascript:showLike()" class="an_search" title="查询"></a></p>
        <p class="font-darkblue p_b_20"><a href="1" target="_blank"><span class="ico26"></span>上传资源</a></p>
        <div id="local">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                <col class="w60"/>
                <col class="w880"/>
                <caption>本专题资源库</caption>
                <tbody id="mainTab1">

                </tbody>

            </table>
            <div class="nextpage">
                <form id="pListForm1" name="pListForm1">
                    <p class="Mt20" id="pListaddress1" align="center"></p>
                </form>
            </div>
            <h6></h6>

            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                <col class="w60"/>
                <col class="w880"/>
                <caption>关联专题资源库</caption>
                <tbody  id="mainTab2">

                </tbody>

            </table>
            <div class="nextpage">
                <form id="pListForm2" name="pListForm2">
                    <p class="Mt20" id="pListaddress2" align="center"></p>
                </form>
            </div>
        </div>
        <div id="like" style="display: none">
            <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 font-black">
                <col class="w60"/>
                <col class="w880"/>
                <tbody id="mainTab3">

                </tbody>

            </table>
            <div class="nextpage">
                <form id="pListForm3" name="pListForm3">
                    <p class="Mt20" id="pListaddress3" align="center"></p>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

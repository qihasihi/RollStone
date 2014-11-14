<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <script type="text/javascript">
        var isfirst=true;
        var tpcid= null;
        var p1,p2;
        $(function(){
            p2=new PageControl({
                post_url:'tptopic?m=ajaxtopiclist&courseid=${courseid}',
                page_id:'page2',
                page_control_name:"p2",		//分页变量空间的对象名称
                post_form:document.page1form,		//form
               // http_free_operate_handler:beforajaxList,
                gender_address_id:'pageaddresss1',		//显示的区域
                http_operate_handler:afterTopicAjaxList,	//执行成功后返回方法
                return_type:'json',								//放回的值类型
                page_no:1,					//当前的页数
                page_size:10,				//当前页面显示的数量
                operate_id:"topshowtb"
            });
            pageGo('p2');

            p1=new PageControl({
                post_url:'tptopictheme?m=themeListjson&courseid=${courseid}',
                page_id:'page1',
                page_control_name:"p1",		//分页变量空间的对象名称
                post_form:document.form2,		//form
                 http_free_operate_handler:beforP1ajaxList,
                gender_address_id:'pageaddress2',		//显示的区域
                http_operate_handler:afterThemeAjaxList,	//执行成功后返回方法
                return_type:'json',								//放回的值类型
                page_no:1,					//当前的页数
                page_size:15,				//当前页面显示的数量
                operate_id:"sp_datatbl"
            });
        });

        function doGetThemeList(c_tpcid){
            if(typeof(c_tpcid)=="undefined"||c_tpcid==null)return;
            tpcid=c_tpcid;  //赋值
            //开始查询
            $("#topshowtb li").removeClass("crumb");
            $("#li_"+c_tpcid).addClass("crumb");
            $("#tpctitle").html($("#li_"+c_tpcid+" input[id='txt_title']").val());
            $("#tpccontent").html($("#li_"+c_tpcid+" textarea").val());

            pageGo('p1');
        }
        /**
         *
         * 加载主题
         */
        function afterThemeAjaxList(rps){
            if(rps.type=="error"){
                alert('异常错误，原因：未知!');
            }else{
                if(rps.objList.length>0){
                    var h='';
                    $.each(rps.objList,function(idx,itm){
                      h+='<p class="title"><a class="ico49b" onclick="operateAHtml('+itm.themeid+')" id="a_'+itm.themeid+'"></a>'+itm.themetitle+'</p>';
                      h+='<div id="dv_'+itm.themeid+'" class="text" style="width:94%;overflow-x: auto;display:none">&nbsp;&nbsp;&nbsp;&nbsp;'+itm.themecontent+'</div>';
                    });
                    $("#sp_datatbl").html(h);
                }else{
                    $("#sp_datatbl").html("<li>暂无数据!</li>");
                }
                $("#themecount").html(rps.presult.recTotal);
                p1.setPageSize(rps.presult.pageSize);
                p1.setPageNo(rps.presult.pageNo);
                p1.setRectotal(rps.presult.recTotal);
                p1.setPagetotal(rps.presult.pageTotal);
                p1.Refresh();
            }
        }

        function operateAHtml(thid){
            //$("a[id='a_"+thid+"']")
            var dvShowType=$("#dv_"+thid).css("display");
            if(dvShowType=="none"){ //要显示
                $("a[id='a_"+thid+"']").attr("class","");
                $("a[id='a_"+thid+"']").addClass("ico49a");
                $("#dv_"+thid).show();
            }else{ //隐藏
                $("a[id='a_"+thid+"']").attr("class","");
                $("a[id='a_"+thid+"']").addClass("ico49b");
                $("#dv_"+thid).hide();
            }
        }


        function beforP1ajaxList(p){
            var param={selectType:2};
            if(tpcid!=null&&(tpcid+"").length>0){
                param.topicid=tpcid;
            }

            p.setPostParams(param);
        }

        /**
        *论题加载
        * @param rps
        */
        function afterTopicAjaxList(rps){
            if(rps.type=="error"){
                alert('异常错误，原因：未知!');
            }else{
                if(rps.objList.length>0){
                    var h='';
                    $.each(rps.objList,function(idx,itm){
                        if(isfirst&&idx==0){
                            tpcid=itm.topicid;
                        }
                        h+='<li id="li_'+itm.topicid+'"><a href="javascript:;" onclick="doGetThemeList('+itm.topicid+')">'+itm.topictitle+'</a>';
                        h+='<input type="hidden" id="txt_title" value="'+itm.topictitle+'"/>';
                        h+='<textarea style=\'display:none\' id=\'txt_content\'>'+itm.topiccontent+'</textarea>';
                        h+='</li>';
                    });

                    $("#topshowtb").html(h);
                }else{
                    $("#topshowtb").html("<li>暂无数据!</li>");
                }
                $("#sp_tpccount").html(rps.presult.recTotal);
                if(rps.presult.pageTotal<2)
                    $("#dv_fanye").hide();
                else
                    $("#dv_fanye").show();
                p2.setPageSize(rps.presult.pageSize);
                p2.setPageNo(rps.presult.pageNo);
                p2.setRectotal(rps.presult.recTotal);
                p2.setPagetotal(rps.presult.pageTotal);
                p2.Refresh();
                if(rps.presult.pageTotal>1)
                    $("#dv_fanye").show();
                else
                    $("#dv_fanye").hide();

                if(tpcid!=null&&isfirst){
                    <c:if test="${empty param.topicid}">
                        doGetThemeList(tpcid);
                    </c:if>
                    <c:if test="${!empty param.topicid}">
                            doGetThemeList(${param.topicid});
                    </c:if>
                    isfirst=false;
                }
            }
        }
    </script>

</head>
<body>
<div class="content2">
    <div class="jxxt_zhuanti_zy_layout">
        <div class="jxxt_trzt_hdkj">
            <p class=" f_right font-blue">主帖数：<span id="themecount">0</span></p>
            <p><strong id="tpctitle"></strong></p>
            <p id="tpccontent"></p>
            <span id="sp_datatbl">
            </span>
            <form name="form2" id="form2" method="post">
                <div class="nextpage m_t_15" id="pageaddress2"></div>
            </form>
        </div>
        <div class="jxxt_zhuanti_zy_layoutL">
            <p class="p_b_10 font-black">&nbsp;&nbsp;<strong>论题数：</strong><span id="sp_tpccount">0</span></p>
            <ul class="one">
                <li class="crumb"><a href="1">论&nbsp;&nbsp;题</a></li>
            </ul>
            <ul class="three"  id="topshowtb">
            </ul>
            <div class="nextpage" id="dv_fanye">
                <span><a href="javascript:;" onclick="p2.pageGo(1);"><b class="first"></b></a></span>
                <span><a href="javascript:;" onclick="p2.pagePre()"><b class="before"></b></a></span>
                <span><a href="javascript:;" onclick="p2.pageNext()"><b class="after"></b></a></span>
                <span><a href="javascript:;" onclick="p2.pageLast()"><b class="last"></b></a></span>
            </div>
        </div>
        <form name="page1form" id="page1form" method="post"><div id="pageaddresss1" style="display:none"></div></form>
        <div class="clear"></div>
    </div>
</div>
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
	<head>
		<title>${sessionScope.CURRENT_TITLE}</title>
	<style>  
</style>  
		<script type="text/javascript"
			src="js/teachpaltform/resource.js"></script>   
		<script type="text/javascript">
		var courseid="${param.courseid}";  
		var pList,pStuList;
        var collectorder=' u.c_time desc ';
        var stuorder=' aa.c_time desc ';
        var hdflag=1;
		$(function(){
            pList = new PageControl( {
                post_url : 'tpres?getStuCollectList',
                page_id : 'page1',
                page_control_name : "pList",
                post_form : document.pListForm,
                gender_address_id : 'pListaddress',
                http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
                http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 20, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "initItemList"
            });

            pStuList = new PageControl( {
                post_url : 'tpres?getStuResourceList',
                page_id : 'pageStuList',
                page_control_name : "pStuList",
                post_form : document.pListForm,
                gender_address_id : 'pListaddress',
                http_free_operate_handler : preeDoStuPageSub, //执行查询前操作的内容
                http_operate_handler : getStuInvestReturnMethod, //执行成功后返回方法
                return_type : 'json', //放回的值类型
                page_no : 1, //当前的页数
                page_size : 20, //当前页面显示的数量
                rectotal : 0, //一共多少
                pagetotal : 1,
                operate_id : "stuResourceItemList"
            });
            pageGo("pStuList");

		});

        function preeDoPageSub(pObj){
            var param={};
            //	if(typeof(courseid)!='undefined'&&courseid.length>0)
            //		param.courseid=courseid;
            if(typeof(collectorder)!="undefined"&&collectorder!=null&&collectorder.Trim().length>0)
                param.collectorder=collectorder;
            pObj.setPostParams(param);
        }

        function getInvestReturnMethod(rps){
            var iconclass=hdflag==1?'ico48a':'ico48b';
            var html='';
            html+='<tr>';
            html+='<th>资源名称</th>';
            html+='<th>所属专题</th>';//<a href="javascript:void(0);" class="ico48a"></a>
            html+='<th>上传人</th>';//<a href="javascript:void(0);" class="ico48a"></a>
            html+='<th>收藏时间<a href="javascript:void(0);"  onclick="DataSort(1,\'u.c_time\',this)"  class="'+iconclass+'"></a></th>';
            html+='<th>操作</th>';
            html+='</tr>';
            if(rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    var filename=itm.resname+itm.filesuffixname,lastname=itm.filesuffixname;
                    html+='<tr';
                    if((idx+1)%2==0)
                        html+=' class="trbg1"';
                    html+='>';
                    html+='<td><p class="ico"><b class="'+itm.suffixtype+'"></b>';
                    if(itm.resourseType=="jpeg"){
                        html+='<a href="javascript:previewImg(\'div_show\',\''+itm.md5Id+'\',\'001\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="video"){
                        html+='<a href="javascript:loadSWFPlayer(\''+itm.md5Id+'\',\'001\',\'div_show\',true,\''+lastname+'\',\''+lastname+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="other"){
                        html+='<a href="javascript:showModelOther(\'div_show\',\''+"001"+itm.filesuffixname+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="mp3"){
                        html+='<a href="javascript:showModeloperateSound(\'div_show\',\''+itm.md5Id+'\',\''+itm.md5mp3file+'\',\'play\',\''+fileSystemIpPort+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="doc"){
                        html+='<a href="javascript:showModelDoc(\''+itm.swfpath+'\',\'div_show\');">'+filename+'</a>';
                    }else if(itm.resourseType=="swf"){
                        html+='<a href="javascript:swfobjPlayer(\''+itm.md5Id+'\',\'001\',\'div_show\',true,\''+lastname+'\',\'swf\',\'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }
                    html+='</p></td>';
                    html+='<td><p>'+itm.coursename+'</p></td>';
                    html+='<td>'+itm.userinfo.realname+'</td>';
                    html+='<td>'+itm.ctimeString+'</td>';
                    html+='<td>';
                    html+='<a href="javascript:void(0)" class="ico58b" title="取消收藏" onclick="doDelOneResource('+itm.collectid+')"></a>';
                    html+='</td>';
                    html+='</tr>';
                });
            }else{
                html+='<tr><td>暂无数据</td></tr>';
            }
            $("tbody[id='initItemList']").html(html);


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


        function preeDoStuPageSub(pObj){
            var param={};
            if(typeof(courseid)!='undefined'&&courseid.length>0)
                param.courseid=courseid;
            if(typeof(stuorder)!="undefined"&&stuorder!=null&&stuorder.Trim().length>0)
                param.stuorder=stuorder;
            pObj.setPostParams(param);
        }

        function DataSort(type,orderby,obj){
            hdflag=hdflag==0?1:0;
            if(type==1){
                var cls=$(obj).attr("class");
                if(cls=='ico48a'){
                    collectorder=orderby;
                }else{
                    collectorder=orderby+' desc';
                }
                pageGo('pList');

            }else{
                var cls=$(obj).attr("class");
                if(cls=='ico48a'){
                    stuorder=orderby;
                }else{
                    stuorder=orderby+' desc';
                }
                pageGo('pStuList');
            }
        }

        function getStuInvestReturnMethod(rps){
            var iconclass=hdflag==1?'ico48a':'ico48b';
            var html='';
            html+='<tr>';
            html+='<th>资源名称</th>';
            html+='<th>所属专题</th>';//<a href="javascript:void(0);" class="ico48a"></a>
            html+='<th>审核状态</th>';//<a href="javascript:void(0);"  onclick="DataSort(2,\'aa.res_status\',this)" class="'+iconclass+'"></a>
            html+='<th>上传时间<a href="javascript:void(0);" onclick="DataSort(2,\'aa.c_time\',this)"  class="'+iconclass+'"></a></th>';
            html+='<th>操作</th>';
            html+='</tr>';

            if(rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+='<tr';
                    if((idx+1)%2==0)
                        html+=' class="trbg1"';
                    html+='>';
                    var status='',filename=itm.resname+itm.filesuffixname,lastname=itm.filesuffixname;;
                    if(itm.resstatus=="0"){
                        status='待审核';
                    }else if(itm.resstatus=="2"){
                        status='未通过';
                    }else if(itm.resstatus=="1"){
                        status='已通过';
                    }
                    html+='<td><p class="ico"><b class="'+itm.suffixtype+'"></b>';
                    if(itm.resourseType=="jpeg"){
                        html+='<a href="javascript:previewImg(\'div_show\',\''+itm.md5Id+'\',\'001\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="video"){
                        html+='<a href="javascript:loadSWFPlayer(\''+itm.md5Id+'\',\'001\',\'div_show\',true,\''+lastname+'\',\''+lastname+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="other"){
                        html+='<a href="javascript:showModelOther(\'div_show\',\''+"001"+itm.filesuffixname+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="mp3"){
                        html+='<a href="javascript:showModeloperateSound(\'div_show\',\''+itm.md5Id+'\',\''+itm.md5mp3file+'\',\'play\',\''+fileSystemIpPort+'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }else if(itm.resourseType=="doc"){
                        html+='<a href="javascript:showModelDoc(\''+itm.swfpath+'\',\'div_show\');">'+filename+'</a>';
                    }else if(itm.resourseType=="swf"){
                        html+='<a href="javascript:swfobjPlayer(\''+itm.md5Id+'\',\'001\',\'div_show\',true,\''+lastname+'\',\'swf\',\'\',\''+itm.resid+'\');">'+filename+'</a>';
                    }
                    html+='</p></td>';
                    html+='<td><p>'+itm.coursename+'</p></td>';
                    html+='<td>'+status+'</td>';
                    html+='<td>'+itm.ctimeString+'</td>';
                    html+='<td>';
                    if(itm.resstatus!="1"){
                        html+='<a  href="javascript:toUpdResource(\''+itm.resid+'\',\''+itm.courseid+'\')" class="ico11" title="编辑"></a>';
                        html+='<a class="ico04" title="删除" href="javascript:void(0)" onclick="doDelStuResource(\''+itm.ref+'\')"></a>';
                    }
                    html+='</td>';
                    html+='</tr>';
                });
            }else{
                html+='<tr><td>暂无数据</td></tr>';
            }
            $("tbody[id='initItemList']").html(html);

            if(rps.objList.length>0){
                pStuList.setPagetotal(rps.presult.pageTotal);
                pStuList.setRectotal(rps.presult.recTotal);
                pStuList.setPageSize(rps.presult.pageSize);
                pStuList.setPageNo(rps.presult.pageNo);
            }else
            {
                pStuList.setPagetotal(0);
                pStuList.setRectotal(0);
                pStuList.setPageNo(1);
            }
            pStuList.Refresh();
        }




 
		</script>   
		 
	     
	</head> 
	<body>
    <div class="subpage_head"><span class="ico60"></span><strong>我的资源管理</strong></div>
    <div class="subpage_nav">
        <ul>
            <li id="li_release" class="crumb"><a href="javascript:pageGo('pStuList');" onclick="var li_release=$('#li_release'); li_release.addClass('crumb');var li_collect=$('#li_collect'); li_collect.removeClass('crumb') ">我发布的</a></li>
            <li id="li_collect" ><a href="javascript:pageGo('pList')" onclick="var li_release=$('#li_release'); li_release.removeClass('crumb');var li_collect=$('#li_collect'); li_collect.addClass('crumb') ">我收藏的</a></li>
        </ul>
    </div>
    <div class="content1">
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
            <col class="w300"/>
            <col class="w300"/>
            <col class="w100"/>
            <col class="w160"/>
            <col class="w80"/>
            <tbody id="initItemList">
            </tbody>
            <!--<tr>
                <td><p class="ico"><b class="ico_txt1"></b><a href="1" target="_blank">是否可以提供更多的视频资源？是否可以提供更资源否可以提供更多的</a></p></td>
                <td><p>是否可以提供更多的视频资源？</p></td>
                <td>王小小</td>
                <td>2013-12-23 13:16:22</td>
                <td><a href="1" class="ico58b" title="取消收藏"></a></td>
            </tr>-->
        </table>
        <form id="pListForm" name="pListForm">
            <p class="nextpage" id="pListaddress" ></p>
        </form>

       <!-- <form id="pStuListForm" name="pStuListForm">
            <p class="nextpage" id="pStuListaddress" ></p>
        </form>-->
    </div>
    <div style="position: absolute; width:660px; height: 510px; z-index: 1005; display: none;" id="swfplayer"> <!--  class="white_content1" -->
        <div style="float:right"><a href="javascript:closeVideoPlayer();"><img height="15" border="0" width="15" alt="关闭" src="images/an14.gif"></a></div>
        <div id="div_show"></div>
    </div>



    <div class="public_windows" id="dv_upd_resource" style="display: none">
        <h3><a href="javascript:void(0);" onclick="closeModel('dv_upd_resource')" title="关闭"></a>修改资源</h3>
        <table border="0" cellpadding="0" cellspacing="0" class="public_tab1 public_input">
            <col class="w80"/>
            <col class="w350"/>
            <tr>
                <th>资源名称：</th>
                <td><input name="upd_res_name" id="upd_res_name"  class="w300" /></td>
            </tr>
            <tr>
                <th>资源类型：</th>
                <td class="font-black">
                    <c:if test="${!empty resType}">
                        <c:forEach items="${resType}" var="d">
                            <input type="radio" name="upd_res_type"  value="${d.dictionaryvalue}" />${d.dictionaryname}&nbsp;&nbsp;
                        </c:forEach>
                    </c:if>
                </td>
            </tr>
            <tr>
                <th>资源简介：</th>
                <td><textarea id="upd_res_remark" class="h90 w300"></textarea></td>
            </tr>
            <tr>
                <th>&nbsp;</th>
                <td><a  onclick="doUpdResource(1)" class="an_public1">确&nbsp;认</a></td>
                <input type="hidden" id="hd_resid"/>
            </tr>
        </table>
    </div>

    <%@include file="/util/foot.jsp" %>
    </body>
</html>

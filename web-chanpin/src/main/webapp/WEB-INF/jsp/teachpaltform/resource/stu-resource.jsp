<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

		<script type="text/javascript">
		var courseid="${courseid}";
		var pStuList;
        var stuorder='aa.c_time desc';
		$(function(){
            pStuList = new PageControl( {
					post_url : 'tpres?getStuResourceList',
					page_id : 'pageStuList',
					page_control_name : "pStuList",
					post_form : document.pStuListForm,
					gender_address_id : 'pStuListaddress',
					http_free_operate_handler : preeDoPageSub, //执行查询前操作的内容
					http_operate_handler : getInvestReturnMethod, //执行成功后返回方法
					return_type : 'json', //放回的值类型
					page_no : 1, //当前的页数
					page_size : 10, //当前页面显示的数量
					rectotal : 0, //一共多少 
					pagetotal : 1,
					operate_id : "stuResourceItemList"
				});
				pageGo("pStuList");
		});

        function preeDoPageSub(pObj){
            var param={};
            if(typeof(courseid)!='undefined'&&courseid.length>0)
                param.courseid=courseid;
            if(typeof(stuorder)!="undefined"&&stuorder!=null&&stuorder.Trim().length>0)
                param.stuorder=stuorder;
            pObj.setPostParams(param);
        }

        function getInvestReturnMethod(rps){
            var html='';
            if(rps.objList.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+='<tr>';
                    //html+='<td><p>'+itm.coursename+'</p></td>';
                    /*-<tr>
                            <td><p class="ico"><b class="ico_txt1"></b><a href="1" target="_blank">是否可以提供更多的视频资源？是否可以提供更资源否可以提供更多的</a></p></td>
                            <td>2013-12-23 13:16:22</td>
                            <td>未审核</td>
                            <td><a href="1" class="ico11" title="编辑"></a><a href="1" class="ico04" title="删除"></a></td>
                    </tr> */

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
                    html+='<td>'+itm.ctimeString+'</td>';
                    html+='<td>'+status+'</td>';
                    html+='<td>';
                    if(itm.resstatus!="1"){
                        html+='<a  href="javascript:toUpdResource(\''+itm.resid+'\')" class="ico11" title="编辑"></a>';
                        html+='<a class="ico04" title="删除" href="javascript:void(0)" onclick="doDelStuResource(\''+itm.ref+'\')"></a>';
                    }
                    html+='</td>';
                    html+='</tr>';
                });
            }else{
                html+='<tr><td>暂无数据</td></tr>';
            }
            $("tbody[id='stuResourceItemList']").html(html);

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

        function DataSort(type,orderby,obj){
            var cls=$(obj).attr("class");
            $(obj).removeClass();
            if(cls=='ico48a'){
                stuorder=orderby;
                $(obj).addClass("ico48b");
            }else{
                stuorder=orderby+' desc';
                $(obj).addClass("ico48a");
            }
            pageGo('pStuList');
        }

		
		</script>
<p><strong class="font-black">管理我发布的资源</strong></p>
<table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
    <col class="w430"/>
    <col class="w140"/>
    <col class="w100"/>
    <col class="w100"/>
    <tr>
        <th>资源名称</th>
        <th>上传时间<a onclick="DataSort(2,'aa.c_time',this)"  class="ico48a"></a></th>
        <th>审核状态<a onclick="DataSort(2,'aa.res_status',this)"  class="ico48a"></a></th>
        <th>操作</th>
    </tr>
    <!--<tr>
        <td><p class="ico"><b class="ico_txt1"></b><a href="1" target="_blank">是否可以提供更多的视频资源？是否可以提供更资源否可以提供更多的</a></p></td>
        <td>2013-12-23 13:16:22</td>
        <td>未审核</td>
        <td><a href="1" class="ico11" title="编辑"></a><a href="1" class="ico04" title="删除"></a></td>
    </tr> -->
    <tbody  id="stuResourceItemList">

    </tbody>
</table>
<form id="pStuListForm" name="pStuListForm">
    <p class="Mt20" id="pStuListaddress" align="center"></p>
</form>

<div style="position: absolute; width:660px; height: 510px; z-index: 1005; display: none;" id="swfplayer"> <!--  class="white_content1" -->
    <div style="float:right"><a href="javascript:closeVideoPlayer();"><img height="15" border="0" width="15" alt="关闭" src="images/an14.gif"></a></div>
    <div id="div_show"></div>
</div>
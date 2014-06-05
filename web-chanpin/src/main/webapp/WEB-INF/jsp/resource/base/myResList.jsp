<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="js/resource/resbase.js"></script>
    <script type="text/javascript">
        var pmyRes;
        var audiosuffix="${audiosuffix}";
        var videosuffix="${videosuffix}";
        var imagesuffix="${imagesuffix}";
        $(function(){
            //翻页控件
            pmyRes=new PageControl({
                post_url:'resource?m=ajaxMyResourceList',
                page_id:'page1',
                page_control_name:"pmyRes",		//分页变量空间的对象名称
                post_form:document.page1form,		//form
                http_free_operate_handler:beforeAjaxList,
                gender_address_id:'page1address',		//显示的区域
                http_operate_handler:afterAjaxList,	//执行成功后返回方法
                return_type:'json',								//放回的值类型
                page_no:1,					//当前的页数
                page_size:20,				//当前页面显示的数量
                rectotal:0,				//一共多少
                pagetotal:1,
                operate_id:"myResData"
            });

            $("#my_sub").change(function(){
                pageGo('pmyRes');
            });
            $("#my_grade").change(function(){
                pageGo('pmyRes');
            });
            $("#my_rt").change(function(){
                pageGo('pmyRes');
            });
            $("#my_ft").change(function(){
                pageGo('pmyRes');
            });

            pageGo('pmyRes');
        });

        function beforeAjaxList(p){
            var param = {};
            param.userid = ${userid};
            if($("#my_sub").val()!=0){
                param.subject = $("#my_sub").val();
            }
            if($("#my_grade").val()!=0){
                param.grade = $("#my_grade").val();
            }
            if($("#my_rt").val()!=0){
                param.restype = $("#my_rt").val();
            }
            if($("#my_ft").val()!=0){
                param.filetype = $("#my_ft").val();
            }
            if($("#resname").val().Trim().length>0){
                param.resname = $("#resname").val();
            }

            p.setPostParams(param);
        }

        /**
         * 打开共享资源的浮层
         * @param rsid 资源ID
         */
        function toShareRes(rsid,nstus){
            if(typeof(rsid)=="undefined"||rsid==null||(rsid+"").length<1){
                alert("异常错误，参数异常!");return;
            }
            $("#dv_share input[type='hidden']").val(rsid);
            if(nstus==1){
                $("#dv_share input[name='rdo_share_status'][value='"+nstus+"']").attr("checked",true);
            }
            showModel("dv_share");
        }

        function afterAjaxList(rps){
            var html = "";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<tr "+(idx%2==1?"class='trbg1'":"")+">";
                    html+="<td><p class='ico'><a target='_blank' href='resource?m=todetail&resid="+itm.resid+"'>";
                    itm.filesuffixname=itm.filesuffixname.toLowerCase();
                    if(itm.filesuffixname==".doc"||itm.filesuffixname==".docx")
                        html+="<b class='ico_doc1'></b>";
                    else if(itm.filesuffixname==".xls"||itm.filesuffixname==".xlsx")
                        html+="<b class='ico_xls1'></b>";
                    else if(itm.filesuffixname==".ppt"||itm.filesuffixname==".pptx")
                        html+="<b class='ico_ppt1'></b>";
                    else if(itm.filesuffixname==".swf")
                        html+="<b class='ico_swf1'></b>";
                    else if(itm.filesuffixname==".pdf")
                        html+="<b class='ico_pdf1'></b>";
                    else if(itm.filesuffixname==".vsd")
                        html+="<b class='ico_vsd1'></b>";
                    else if(itm.filesuffixname==".rtf")
                        html+="<b class='ico_rtf1'></b>";
                    else if(itm.filesuffixname==".txt")
                        html+="<b class='ico_txt1'></b>";
                    else if(audiosuffix.indexOf(itm.filesuffixname)!=-1)
                        html+="<b class='ico_mp31'></b>";
                    else if(videosuffix.indexOf(itm.filesuffixname)!=-1)
                        html+="<b class='ico_mp41'></b>";
                    else if(imagesuffix.indexOf(itm.filesuffixname)!=-1)
                        html+="<b class='ico_jpg1'></b>";
                    else
                        html+="<b class='ico_other1'></b>";
                    html+=itm.resname +"</a></p></td>";//PZYXT-47
                    html+="<td>"+itm.clicks+"</td>";
                    html+="<td>"+itm.praisenum+"</td>";
                    html+="<td>"+itm.recomendnum+"</td>";
                    html+="<td>"+itm.storenum+"</td>";
                    html+="<td>"+itm.downloadnum+"</td>";
                    html+="<td>校内</td>";
                    html+="<td>";
                    //网校分享类型(-1：未同步  0:待审核 1:未通过 2:已删除 3:共享 4:标准)
                    //分享类型(1:本地共享 2:云端共享 3:不共享)
                    var isshowfx=false;
                    var nstus=itm.sharestatus;
                    if(typeof(nstus)=="undefined"||nstus==null||nstus==2){
                        nstus=itm.netsharestatus;
                        switch(nstus){
                            case -1:
                                html+="未分享";break;
                            case 0:
                                html+="待审核";break;
                            case 1:
                                html+="未通过";break;
                            case 2:
                                html+="已删除";break;
                            case 3:
                            case 4:
                                html+="审核通过";break;
                        }
                    }else if(nstus==1){
                        html+="校内分享";isshowfx=true;
                    }else if(nstus==3){
                        html+="不分享";isshowfx=true;
                    }

                    html+="</td>";
                    html+="<td>";
                    if(isshowfx){
                     html+="<a href='javascript:;' onclick='toShareRes("+itm.resid+","+nstus+")' title='分享' class='ico20' id='a_share_"+itm.resid+"'></a>";
                    }
                    html+="<a href="+"javascript:doDelRes('"+itm.resid+"');"+" title='删除' class='ico04' ></a></td>";
                    html+="</tr>";
                });
            }else{
                html+="<tr><th colspan='9'>暂无数据！</th></tr>";
            }
            pmyRes.setPageSize(rps.presult.pageSize);
            pmyRes.setPageNo(rps.presult.pageNo);
            pmyRes.setRectotal(rps.presult.recTotal);
            pmyRes.setPagetotal(rps.presult.pageTotal);
            pmyRes.Refresh();
            $("#myResData").html(html);
        }

    </script>
</head>
<body>
<div class="subpage_head"><span class="lm_ico03"></span><strong>资源管理</strong></div>

<div class="subpage_nav">
    <ul>
        <li class="crumb"><a href="resource?m=toMyResList">我发布的</a></li>
        <li><a href="resource?m=toStoreList">我收藏的</a></li>
        <c:if test="${isadmin==1}">
            <li><a href="resource?m=toCheckList">我管理的</a></li>
        </c:if>
    </ul>
</div>

<div class="content1">
    <div class="zyxt_sczy public_input">
        <p class="font-darkblue"><a href="resource?m=toadd" target="_blank"><span class="ico26"></span>上传资源</a></p>
        <select name="my_sub" id="my_sub">
            <option selected="selected" value="0" >学科</option>
            <c:forEach items="${subjectEVList}" var="sev">
                <option value="${sev.subjectid }" >${sev.subjectname }</option>
            </c:forEach>
        </select>
        <select name="my_grade" id="my_grade">
            <option selected="selected" value="0" >年级</option>
            <c:forEach items="${gradeEVList}" var="gev">
                <option value="${gev.gradeid }" >${gev.gradename }</option>
            </c:forEach>
        </select>
        <select name="my_rt" id="my_rt">
            <option selected="selected" value="0" >资源类型</option>
            <c:forEach items="${resTypeEVList}" var="rtev">
                <option value="${rtev.dictionaryvalue }" >${rtev.dictionaryname }</option>
            </c:forEach>
        </select>
        <select name="my_ft" id="my_ft">
            <option selected="selected" value="0" >文件类型</option>
            <c:forEach items="${resFileTypeEVList}" var="ftev">
                <option value="${ftev.dictionaryvalue }" >${ftev.dictionaryname }</option>
            </c:forEach>
        </select>
        <input id="resname" name="resname" type="text" class="w320"/>&nbsp;<a href="javascript:pageGo('pmyRes');" class="an_search" title="搜索"></a>
    </div>
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup class="w320"></colgroup>
        <colgroup span="7" class="w80"></colgroup>
        <colgroup class="w60"></colgroup>
        <tr>
            <th>资源名称</th>
            <th>浏览</th>
            <th>赞</th>
            <th>推荐</th>
            <th>收藏</th>
            <th>下载</th>
            <th>存放位置</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        <tbody id="myResData"></tbody>
    </table>
    <form id="page1form" name="page1form" method="post">
        <p class="nextpage" align="center" id="page1address"></p>
    </form>
</div>
<%@include file="/util/foot.jsp" %>
<%//分享%>
<div class="public_windows" style="display:none" id="dv_share">
    <h3><a href="javascript:;" onclick="closeModel('dv_share')" title="关闭"></a>分享专题</h3>
    <input type="hidden" name="share_res_id" id="share_res_id"/>
    <div class="jxxt_float_fxzt public_input">
        <p class="font-black">&nbsp;&nbsp;&nbsp;<input type="radio" name="rdo_share_status" id="rdo_share_status2" value="2"><label for="rdo_share_status2">&nbsp;云端教师</label></p>
        <p class="font-black">&nbsp;&nbsp;&nbsp;<input type="radio"name="rdo_share_status" id="rdo_share_status1" value="1"><label for="rdo_share_status1">&nbsp;校内教师</label></p>
        <p class="t_c p_t_10"><a href="javascript:;" onclick="doShareRes()" class="an_public1">确&nbsp;定</a>
            &nbsp;&nbsp;<a href="javascript:;" onclick="closeModel('dv_share')" class="an_public1">取&nbsp;消</a></p>
    </div>
</div>
</body>
</html>
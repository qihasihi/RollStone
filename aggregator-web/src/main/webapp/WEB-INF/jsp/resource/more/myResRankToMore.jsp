<%--
  Created by IntelliJ IDEA.
  User: zhengzhou
  Date: 14-5-29
  Time: 下午3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<html>
<head>
    <script type="text/javascript">
        var audiosuffix="${audiosuffix}";
        var videosuffix="${videosuffix}";
        var imagesuffix="${imagesuffix}";
        var p1;
        $(function(){
            p1=new PageControl({
                post_url:'resource?m=ajx_myResRankByPage',
                page_id:'page1',
                page_control_name:"p1",		//分页变量空间的对象名称
                post_form:document.f1,		//form
                gender_address_id:'pageAddress1',		//显示的区域
             //   http_free_operate_handler:beforeVideoAjaxList,		//执行查询前操作的内容
                http_operate_handler:aftermethod,	//执行成功后返回方法
                return_type:'json',								//放回的值类型
                page_no:1,					//当前的页数
                page_size:20,				//当前页面显示的数量
                rectotal:0,				//一共多少
                pagetotal:1,
                operate_id:"tbl_data"
            });
            pageGo('p1');
        });
        /**
        *AJAX返回方法
        * @param rps
         */
        function aftermethod(rps){
            var html = "";
            if(rps!=null&&rps.presult.list.length>0){
                $.each(rps.objList,function(idx,itm){
                    html+="<tr "+(idx%2==1?"class='trbg1'":"")+"><td><p class=\"ico\">";
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
                    html+="<a  href='resource?m=todetail&resid="+itm.resid+"'>"+ itm.resname +"</a></p></td>";
                  // html+="<td  onmouseover=\"getResCourse('"+itm.resid+"')\" onmouseout=\"dv_res_course.style.display='none';ul_course.innerHTML='';\"><p>查看</p></td>";

                    html+="<td>"+itm.clicks+"</td>";
                    html+="<td>"+itm.praisenum+"</td>";
                    html+="<td>"+itm.recomendnum+"</td>";
                    html+="<td>"+itm.storenum+"</td>";
                    html+="<td>"+itm.downloadnum+"</td>";
                    html+="</tr>";
                });
            }else{
                html+="<tr><td colspan='6'>暂无数据！</tr></td>";
            }
            p1.setPageSize(rps.presult.pageSize);
            p1.setPageNo(rps.presult.pageNo);
            p1.setRectotal(rps.presult.recTotal);
            p1.setPagetotal(rps.presult.pageTotal);
            p1.Refresh();
            $("#tbl_data").html(html);
        }
    </script>
</head>
<body>
<div class="subpage_head"><span class="ico71"></span><strong>我的资源排行榜</strong></div>
<div class="content1">
    <table border="0" cellpadding="0" cellspacing="0" class="public_tab2">
        <colgroup class="w440"></colgroup>
        <colgroup span="5" class="w100"></colgroup>
        <tr>
            <th>资源名称</th>
            <th>浏览</th>
            <th>赞</th>
            <th>推荐</th>
            <th>收藏</th>
            <th>下载</th>
        </tr>
        <tbody id="tbl_data">

        </tbody>

    </table>
    <form name="f1" id="f1" method="post">
        <div class="nextpage" id="pageAddress1"></div>
    </form>
</div>
<%@include file="/util/foot.jsp"%>
</body>
</html>

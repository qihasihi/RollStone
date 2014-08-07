<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-7-8
  Time: 下午2:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/util/common-jsp/common-jxpt.jsp"%>
<html>
<head>
    <base target="_self">
    <title></title>
    <script src="js/teachpaltform/resource.js" type="text/javascript"></script>
    <script type="text/javascript">
        var pageNo=1;
        var pageNo2=1;
        var versionid="${versionid}";
        var courseid="${courseid}";
        $(function(){
            var bl1 = ${sign1};
            var bl2 = ${sign2};
            if(bl1){
                $("#gaoDiv").show();
            }else{
                $("#gaoDiv").hide();
            }
            if(bl2){
                $("#zhiDiv").show();
            }else{
                $("#zhiDiv").hide();
            }
            getRemoteResources(true);
        });
        function dosub(taskvalueid,resourcetype,remotetype,resname){
            if (window.opener != undefined) {
                //for chrome
                window.opener.returnValue =taskvalueid+","+resourcetype+","+remotetype+","+resname;
            }
            else {
                window.returnValue =taskvalueid+","+resourcetype+","+remotetype+","+resname;
            }
            window.close();
        }
    </script>
</head>
<body>
    <div class="subpage_head"><span class="ico55"></span><strong>添加任务&mdash;&mdash;选择资源</strong></div>
    <div class="content2">
        <div class="subpage_lm">
            <ul>
                <li><a href="task?m=toTaskElementDetial&operate_type=1&subjectid=${param.subjectid}&tasktype=1&courseid=${param.courseid}">本地资源</a></li>
                <li class="crumb"><a href="tpres?m=toRemoteResources&courseid=${courseid}&subjectid=${param.subjectid}">远程资源</a></li>
            </ul>
        </div>

        <div class="jxxt_zhuanti_rw_add">
            <p class="public_input t_r">
                <%--<select name="select3" id="select3">--%>
                    <%--<option selected>高一</option>--%>
                    <%--<option>教职工</option>--%>
                    <%--<option>学生</option>--%>
                <%--</select>--%>
                <input name="textfield2"  id="keyword" type="text" class="w240" placeholder="资源名称/专题名称" />
                <a href="javascript:getRemoteResources(false)" class="an_search" title="查询"></a></p>
           <div id="gaoDiv">
                <p class="font-black p_b_10"><strong>高清课堂</strong></p>
                <ul class="gqkt font-black" id="gaoqing">

                </ul>
                <p class="font-darkblue t_c clearit" id="moreGaoqing"><a href="javascript:getMoreResources()" ><span class="ico49a"></span>查看更多资源</a></p>
           </div>
            <div id="zhiDiv">
                <p class="font-black p_tb_10 clearit"><strong>知识导学</strong></p>
                <ul class="zsdx font-black" id="zhishi">

                </ul>
                <p class="font-darkblue t_c" id="moreZhishi"><a href="javascript:getMoreZhishi()" ><span class="ico49a"></span>查看更多资源</a></p>
            </div>
        </div>
    </div>
    <%@include file="/util/foot.jsp"%>
</body>
</html>

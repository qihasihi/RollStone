<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-2-24
  Time: 下午3:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.school.entity.UserInfo"%>
<%@include file="/util/common-jsp/common-zrxt.jsp" %>
<%
    List<Map<String,Object>> list = (List<Map<String,Object>>)request.getAttribute("scoreList");
   Map<String,Object> userScoreInfo = (Map<String,Object>)request.getAttribute("userScoreInfo");
    Integer upScore=0;
    Integer addScore=0;
    Integer minScore=0;
    if(list!=null&&list.size()>0){
        for(Map o:list){
            if(Integer.parseInt(o.get("SCORE").toString())>0){
                addScore=addScore+Integer.parseInt(o.get("SCORE").toString());
            }
            if(Integer.parseInt(o.get("SCORE").toString())<0){
                minScore=minScore+Integer.parseInt(o.get("SCORE").toString());
            }
        }
    }
    if(minScore<0){
        minScore=-minScore;
    }
    switch (Integer.parseInt(userScoreInfo.get("LEVEL").toString())){
        case 1:
            upScore=21-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
        case 2:
            upScore=51-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
        case 3:
            upScore=101-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
        case 4:
            upScore=201-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
        case 5:
            upScore=501-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
        case 6:
            upScore=1001-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
        case 7:
            upScore=2001-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
        case 8:
            upScore=3001-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
        case 9:
            upScore=4001-Integer.parseInt(userScoreInfo.get("TOTALSCORE").toString());
            break;
    }

%>
<html>
<head>
    <title>用户积分详情</title>
    <script type="text/javascript">
        var p1;
        $(function(){
            p1 = new PageControl({
                post_url:'userModelScoreLogs?m=userShareResourceScore',
                page_id:'page1',
                page_control_name:'p1',
                post_form:document.page1form,
                gender_address_id:'page1address',
                http_free_operate_handler:addParam,
                http_operate_handler:getResourceScore,
                return_type:'json',
                page_no:1,
                page_size:5,
                operate_id:"mainTbl"
            });


        });

        function changeMonths(idx,typeid,time){
            $("#h_month").val(time);
            var obj =  $("#ul_month").children();
            $.each(obj,function(idx,itm){
                $(itm).removeClass("font-darkblue");
            });
            $("#li_"+idx).addClass("font-darkblue");
            pageGo('p1');
        }

        function addParam(tobj){
            if(typeof(tobj)!="object"){
                alert('异常错误!请刷新页面后重试!');
                return;
            }
            var param=new Object();
            param.scoretypeid=$("#scoretypeid").val().Trim();
            param.selMonth=$("#h_month").val().Trim();

            tobj.setPostParams(param);
        }

        function showResScoreDetails(typeid,typename){
            var obj =  $("#ul_month").children();
            $.each(obj,function(idx,itm){
                $(itm).removeClass("font-darkblue");
            });
            $("#li_11").addClass("font-darkblue");
            $("#scoretypeid").val(typeid);
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth();
            var time = year+"-0"+(month+1);
            $("#h_month").val(time);
            $("#typename").html("<strong>"+typename+"</strong>");
            var oDate = new Date();
            var nYear = oDate.getFullYear();
            var nMonth = oDate.getMonth();
            for(var i = 11;i>=0;i--){
                var htm='';
                var time;
                if(nMonth>9){
                    time=nYear+'-'+(nMonth+1);
                }else{
                    time=nYear+'-0'+(nMonth+1)
                }
                htm='<a href="javascript:changeMonths('+i+',null,\''+time+'\')" >'+(nMonth+1)+'月</a>';
                $("#li_"+i).html(htm);
                //window['txt'+i].value = nYear+'年'+(nMonth+1)+'月';
                nMonth--;
                if(nMonth==-1){
                    nYear--;
                    nMonth = 11;
                }
            }
            pageGo('p1');
            showModel('resource');
        }

        function getResourceScore(rps){
            if(rps.type=='error'){
                alert(rps.msg);

                // 设置显示值
                var shtml = '<tr><td align="center">暂时没有专题!';
                shtml += '</td></tr>';
                $("#mainTbl").html(shtml);

            }else{
                var shtml = '';
                shtml+="<tr><th>资源名称</th><th>积分</th><th>时间</th></tr>"
                if(rps.objList.length<1){
                    shtml+="<tr><th colspan='8' style='height:65px' align='center'>暂无信息!</th></tr>";
                }else{
                    $.each(rps.objList,function(idx,itm){
                        shtml+="<tr>";
                        shtml+="<td>"+itm.resname+"</td>";
                        shtml+="<td>+"+itm.score+"</td>";
                        shtml+="<td>"+itm.ctimeString+"</td>";
                        shtml+="</tr>";
                    });
                }
                $("#mainTbl").hide();
                $("#mainTbl").html(shtml);
                $("#mainTbl").show('fast');
            }
        }
    </script>
<%--</head>--%>
<%--<body>--%>
    <%--<p><input type="image" src="${userScoreInfo['HEAD_IMAGE']}"/><br>--%>
        <%--${userScoreInfo["STU_NAME"]}<br>--%>
        <%--${userScoreInfo["LEVEL"]}&nbsp;${userScoreInfo["LEVEL_NAME"]}--%>
    <%--</p>--%>
    <%--<p>我的等级：${userScoreInfo["LEVEL"]}级      已获得积分：${userScoreInfo["TOTALSCORE"]}分      升级还需要：<%=upScore%>分<br>--%>
        <%--今日获得积分：${userScoreInfo["TODAYSCORE"]}分--%>
    <%--</p>--%>
    <%--<table>--%>
        <%--<tr>--%>
            <%--<td>--%>
                <%--<span style="text-decoration: underline;font-size: 14px">获得积分</span>--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--<%=addScore%>--%>
            <%--</td>--%>
        <%--</tr>--%>
        <%--<c:if test="${scoreList!=null}">--%>
            <%--<c:forEach items="${scoreList}" var="itm">--%>
                <%--<c:if test="${itm['SCORE']>0}">--%>
                    <%--<tr>--%>
                        <%--<td width="200">--%>
                                <%--${itm["SCORE_TYPE_NAME"]}--%>
                        <%--</td>--%>
                        <%--<td width="200">--%>
                                <%--+${itm["SCORE"]}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</c:if>--%>

            <%--</c:forEach>--%>
        <%--</c:if>--%>
        <%--<tr>--%>
            <%--<td>--%>
                    <%--&nbsp;--%>
            <%--</td>--%>
            <%--<td>--%>

            <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>--%>
               <%--<span style="text-decoration: underline;font-size: 14px"> 扣除积分</span>--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--<%=minScore%>--%>
            <%--</td>--%>
        <%--</tr>--%>
        <%--<c:if test="${scoreList!=null}">--%>
            <%--<c:forEach items="${scoreList}" var="itm">--%>
                <%--<c:if test="${itm['SCORE']<0}">--%>
                    <%--<tr>--%>
                        <%--<td width="200">--%>
                                <%--${itm["SCORE_TYPE_NAME"]}--%>
                        <%--</td>--%>
                        <%--<td width="200">--%>
                                <%--${itm["SCORE"]}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</c:if>--%>

            <%--</c:forEach>--%>
        <%--</c:if>--%>
    <%--</table>--%>


    <div class="subpage_head"><span class="ico66"></span><strong>我的积分</strong></div>
    <div class="content">
        <div class="zyxt_jifen">
            <div class="zyxt_jifen_nr">
                <div class="zyxt_jifen_nrR">
                    <p>总&nbsp;积&nbsp;分：${userScoreInfo["TOTALSCORE"]}分</p>
                    <p>我的等级：${userScoreInfo["LEVEL"]}级（${userScoreInfo["LEVEL_NAME"]}）</p>
                    <p>升级需要：<%=upScore%>分 </p>
                </div>
                <div class="zyxt_jifen_nrL">
                    <div class="pic"><img src="${userScoreInfo['HEAD_IMAGE']}" width="135" height="135"></div>
                    <p class="t_c">${userScoreInfo["STU_NAME"]}</p>
                </div>
            </div>
        </div>
        <div class="contentR">
            <p class="p_t_20 font-darkblue">我的资源积分</p>
            <ul class=" zyxt_jifen_info font-black">
                <li class="crumb"><span>250分</span>获得积分</li>
                <c:if test="${scoreList!=null}">
                    <c:forEach items="${scoreList}" var="itm">
                        <c:if test="${itm['SCORE']>0}">
                            <li><a href="javascript:showResScoreDetails('${itm['SCORE_TYPE_ID']}','${itm["SCORE_TYPE_NAME"]}')"><span>+${itm["SCORE"]}</span> ${itm["SCORE_TYPE_NAME"]}</a></li>
                        </c:if>

                    </c:forEach>
                </c:if>
                <li class="crumb"><span><%=minScore%>分</span>扣除积分</li>
                <c:if test="${scoreList!=null}">
                    <c:forEach items="${scoreList}" var="itm">
                        <c:if test="${itm['SCORE']<0}">
                            <li><a href="javascript:showResScoreDetails('${itm['SCORE_TYPE_ID']}','${itm["SCORE_TYPE_NAME"]}')"><span>${itm["SCORE"]}</span> ${itm["SCORE_TYPE_NAME"]}</a></li>
                        </c:if>
                    </c:forEach>
                </c:if>
            </ul>
            <br/>
        </div>
        <div class="subpage_contentL">
            <ul>
                <li><a href="1">专题积分</a></li>
                <li class="crumb"><a href="1">资源积分</a></li>
                <li><a href="1">其它积分</a></li>
            </ul>
        </div>
        <div class="clear"></div>
        <div class="zyxt_float_jifen" id="resource" style="display: none">
            <input type="hidden" id="scoretypeid"/>
            <input type="hidden" id="h_month"/>
            <div class="public_windows">
                <h3><a href="javascript:closeModel('resource','hide')"  title="关闭"></a>积分详情</h3>
                <div class="xiangqing">
                    <p class="one"><span id="typename"></span>&nbsp;&nbsp;<span id="currentTime"></span></p>
                    <ul id="ul_month">
                        <li id="li_0" ><a href="1">12月</a></li>
                        <li id="li_1"><a href="1">1月</a></li>
                        <li id="li_2"><a href="1">2月</a></li>
                        <li id="li_3"><a href="1">3月</a></li>
                        <li id="li_4"><a href="1">4月</a></li>
                        <li id="li_5"><a href="1">5月</a></li>
                        <li id="li_6"><a href="1">6月</a></li>
                        <li id="li_7"><a href="1">7月</a></li>
                        <li id="li_8"><a href="1">8月</a></li>
                        <li id="li_9"><a href="1">9月</a></li>
                        <li id="li_10"><a href="1">10月</a></li>
                        <li id="li_11" class="font-darkblue"><a href="1">11月</a></li>
                    </ul>
                    <table border="0" cellpadding="0" cellspacing="0" class="public_tab3 black">
                        <col class="w350" />
                        <col class="w60" />
                        <col class="w100" />
                        <tbody id="mainTbl"></tbody>
                    </table>
                    <%--<div class="nextpage"><span><a href="1"><b class="first"></b></a></span><span><a href="1"><b class="before"></b></a></span><span><a href="1"><b class="after"></b></a></span><span><a href="1"><b class="last"></b></a></span></div>--%>
                    <form id="page1form" name="page1form" method="post">
                        <div class="nextpage" align="right" id="page1address"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
<%@include file="/util/foot.jsp"  %>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-29
  Time: 上午9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/util/common-jsp/common-im.jsp"%>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/jxxt.css"/>
    <script type="text/javascript" src="js/common/videoPlayer/new/jwplayer.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/videoPlayer/swfobject.js"></script>
    <script type="text/javascript" src="js/1.6/jquery-ui-160.min.js"></script>
    <script type="text/javascript" src="js/common/image-show.js"></script>
    <script type="text/javascript">
        $(function(){
            var imgArrayObj=$("img").filter(function(){
                var dsrc=$(this).attr("data-src");
                return typeof(dsrc)!="undefined"&&dsrc.length>0;
            });
            if(imgArrayObj.length>0){
                EttImageShow({
                    image:imgArrayObj,
                    fadeId:'dv_imageShow1-1',
                    sw:90,
                    sh:70
                });
            }
        })

    </script>
</head>
<body>
<%
    long mTime = System.currentTimeMillis();
    int offset = Calendar.getInstance().getTimeZone().getRawOffset();
    Calendar c = Calendar.getInstance();
    c.setTime(new Date(mTime - offset));
    String currentDay =UtilTool.DateConvertToString(c.getTime(),UtilTool.DateType.type1);
    System.out.println("当前页面"+"------------"+"remote-resource-detail.jsp"+"         当前位置------------进入jsp      当前时间------"+currentDay);
%>
<div class="zsdx">
    <div class="timu">${resname} </div>
    <c:if test="${!empty replyList}">
        <c:forEach var="itm" items="${replyList}">
            <div class="over">
                <b><img src="${itm.uPhoto}" width="36" height="36"></b>
                <p class="title"><span>${itm.replyDate}</span>${itm.uName}</p>
                <p>${itm.replyDetail}</p>
                <c:if test="${!empty itm.replyAttach}">
                    <c:if test="${itm.replyAttachType eq 1}">
                        <c:forEach items="${itm.replyAttach}" var="at">
                            <script type="text/javascript">
                                var fpath="${at}";
                                fpath=fpath.replaceAll('"',"").replaceAll("”","").replaceAll("[","").replaceAll("]","");
                                var h='<img height="70" width="90" src="'+fpath+'" data-src="'+fpath+'">';
                                document.write(h);
                            </script>

                        </c:forEach>
                    </c:if>
                    <c:if test="${itm.replyAttachType eq 2}">
                        <c:forEach items="${itm.replyAttach}" var="at">
                            <p><a style="cursor: hand"><img src="images/pic02_140811.png" width="99" height="22"  onclick="ado_${atidx.index}.play()"/></a>

                                    <span style="display:none"><audio controls="controls" id="ado_${atidx.index}">
                                        <source src="${at}" type="audio/ogg">
                                        <source src="${at}" type="audio/mpeg">
                                        您的浏览器不支持 audio 标签。
                                    </audio></span></p>
                        </c:forEach>
                    </c:if>
                </c:if>
            </div>
        </c:forEach>
    </c:if>

</div>
<%
    System.out.println("当前页面"+"------------"+"remote-resource-detail.jsp"+"              当前位置------------jsp结束      当前时间------"+currentDay);
%>
</body>
</html>

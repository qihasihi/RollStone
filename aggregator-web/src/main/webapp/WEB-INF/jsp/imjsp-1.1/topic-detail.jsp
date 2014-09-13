<%--
  Created by IntelliJ IDEA.
  User: yuechunyang
  Date: 14-8-25
  Time: 上午9:47
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
<div class="hzjl">
    <h1>${topic.topictitle} </h1>
    <div class="black">${topic.topiccontent}</div>
    <c:if test="${!empty themeList}">
        <c:forEach items="${themeList}" var="itm">
            <div class="info">
                <b><img src="${itm.uPhoto}" width="36" height="36"></b>
                <p class="title"><span>${itm.C_TIME}</span>${itm.uName}</p>
                <div class="black">${itm.THEME_TITLE}</div>
                <c:if test="${itm.SOURCE_ID!=null and itm.SOURCE_ID!=1}">
                    <p>${itm.THEME_CONTENT}</p>
                </c:if>
                <c:if test="${itm.SOURCE_ID!=null and itm.SOURCE_ID==1}">
                    <c:if test="${itm.IM_ATTACH_TYPE eq 1}">
                        <c:forEach items="${itm.IM_ATTACH}" var="at">
                            <script type="text/javascript">
                                var fpath="${at}";
                                fpath=fpath.replaceAll('"',"").replaceAll("”","").replaceAll("[","").replaceAll("]","");
                                var h='<img src="imapi1_1?m=makeImImg&w=90&h=70&p='+fpath+'" data-src="'+fpath+'">';
                                document.write(h);
                            </script>

                        </c:forEach>
                    </c:if>
                    <c:if test="${itm.IM_ATTACH_TYPE eq 2}">
                        <c:forEach items="${itm.IM_ATTACH}" var="at">
                        <p>
                            <audio controls="controls">
                                <source src="${at}" type="audio/ogg">
                                <source src="${at}" type="audio/mpeg">
                                您的浏览器不支持 audio 标签。
                            </audio></p>
                        </c:forEach>
                    </c:if>
                </c:if>

            </div>
        </c:forEach>
    </c:if>
</div>
</body>
</html>

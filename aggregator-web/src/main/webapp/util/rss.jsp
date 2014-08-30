<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String filepath=request.getParameter("filepath");
    String imgurl=request.getParameter("imgurl");
%>
<rss version="2.0" xmlns:jwplayer="http://rss.jwpcdn.com/">
    <channel>
        <item>
            <title></title>
            <description>Sintel is a fantasy CGI from the Blender Open Movie Project.</description>
           <!-- <jwplayer:image>player:image><%=imgurl%></jwplayer:image>-->
                <jwplayer:source file="<%=filepath%>"></jwplayer:source>
        </item>
        </channel>
</rss>
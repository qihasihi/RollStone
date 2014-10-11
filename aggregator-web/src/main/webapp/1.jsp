<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*" %>
<%
    out.println("Your IP : "+request.getRemoteAddr()+"<br>");

    out.println("This host is : "+java.net.InetAddress.getLocalHost()+"<br>");
    out.println("Session ID is : "+session.getId()+"<br>");

    Runtime rt = Runtime.getRuntime();
    out.println("All jvm mem is "+rt.totalMemory()+"<br>");
    out.println("The free jvm mem is "+rt.freeMemory()+"<br>");
    out.println("<br>");
    out.println(request.getRequestURI()+"<br>");
    out.println(request.getServerName()+"<br/>"+request.getRequestURL()+"<br/>");


    out.println("Headers<hr/>");
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        out.print("Header Name: <em>" + headerName);
        String headerValue = request.getHeader(headerName);
        out.print("</em>, Header Value: <em>" + headerValue);
        out.println("</em><br/>");
    }

%>

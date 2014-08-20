<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.etiantian.unite.utils.UrlSigUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!  /**
 * 验证RequestParams相关参数
 * @param request
 * @return
 * @throws Exception
 */
public static HashMap<String,String> getRequestParam(HttpServletRequest request) throws Exception{
    Enumeration eObj=request.getParameterNames();
    HashMap<String,String> returnMap=null;
    if(eObj!=null){
        returnMap=new HashMap<String, String>();
        while(eObj.hasMoreElements()){
            Object obj=eObj.nextElement();
            if(obj==null||obj.toString().trim().length()<1)
                continue;
            Object val=request.getParameter(obj.toString());
            if(obj.equals("method")){
                continue;
            }
            returnMap.put(obj.toString(),val.toString());
        }
    }
    return returnMap;
}
%>

<%
    HashMap<String,String> map=getRequestParam(request);
    String method=request.getParameter("method");
    //验证Md5
    String val = UrlSigUtil.makeSigSimple(method, map);
    response.getWriter().println(val);
%>
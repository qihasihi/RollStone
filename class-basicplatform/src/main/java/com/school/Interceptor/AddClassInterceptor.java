package com.school.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by yuchunfan on 14-10-20.
 */
public class AddClassInterceptor implements HandlerInterceptor {

    //添加action为：cls?m=ajaxsave
    private String mappingURL = ".*/cls\\?m=ajaxsave.*";//利用正则映射到需要拦截的路径
    private String mappingURL2 = ".*m=levelup.*";
    private String mappingURL3 = ".*m=modify.*";

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String url = request.getRequestURL().toString()+"?"+request.getQueryString();
        if(url != null && url.matches(mappingURL)) {
            int schoolId = Integer.valueOf(request.getParameter("dcschoolid"));
            String year = request.getParameter("dyear");
            if (schoolId >= 50000) {
                URL postUrl =  new URL(request.getRequestURL().toString()+"?m=fCheck") ;
                HttpURLConnection post = (HttpURLConnection)postUrl.openConnection();
                post.setDoInput(true);
                post.setDoOutput(true);
                post.setUseCaches(false);
                post.setRequestProperty("Charsert", "UTF-8");
                post.setRequestMethod("POST");
                post.setInstanceFollowRedirects(true);
                post.connect();
                DataOutputStream out = new DataOutputStream(post.getOutputStream());
                String content = "schoolId="+ schoolId + "&&year=" + URLEncoder.encode(year, "utf-8");
                out.writeBytes(content);
                out.flush();
                out.close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        post.getInputStream()));
                int maxClass = Integer.valueOf(reader.readLine());
                reader.close();

                URL postUrl2 =  new URL(request.getRequestURL().toString()+"?m=cCheck") ;
                HttpURLConnection post2 = (HttpURLConnection)postUrl2.openConnection();
                post2.setDoInput(true);
                post2.setDoOutput(true);
                post2.setUseCaches(false);
                post2.setRequestProperty("Charsert", "UTF-8");
                post2.setRequestMethod("POST");
                post2.setInstanceFollowRedirects(true);
                post2.connect();
                DataOutputStream out2 = new DataOutputStream(post2.getOutputStream());
                out.writeBytes(content);
                out.flush();
                out.close();
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(
                        post2.getInputStream()));
                int existClass = Integer.valueOf(reader2.readLine());
                reader2.close();

                if (maxClass <= existClass) {
                    response.getWriter().println("{\"result\":\"Wrong!\"}");
                    return false;
                }
                return true;
            }
            response.getWriter().println("{\"result\":\"Wrong!\"}");
            return false;
        }


        if(url != null && url.matches(mappingURL2)) {
            int schoolId = Integer.valueOf(request.getParameter("dcschoolid"));
            String year = request.getParameter("dyear");
            if (schoolId >= 50000) {
                URL postUrl =  new URL(request.getRequestURL().toString()+"?m=fCheck") ;
                HttpURLConnection post = (HttpURLConnection)postUrl.openConnection();
                post.setDoInput(true);
                post.setDoOutput(true);
                post.setUseCaches(false);
                post.setRequestProperty("Charsert", "UTF-8");
                post.setRequestMethod("POST");
                post.setInstanceFollowRedirects(true);
                post.connect();
                DataOutputStream out = new DataOutputStream(post.getOutputStream());
                String content = "schoolId="+ schoolId + "&&year=" + URLEncoder.encode(year, "utf-8");
                out.writeBytes(content);
                out.flush();
                out.close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        post.getInputStream()));
                int maxClass = Integer.valueOf(reader.readLine());
                reader.close();

                URL postUrl2 =  new URL(request.getRequestURL().toString()+"?m=cCheck") ;
                HttpURLConnection post2 = (HttpURLConnection)postUrl2.openConnection();
                post2.setDoInput(true);
                post2.setDoOutput(true);
                post2.setUseCaches(false);
                post2.setRequestProperty("Charsert", "UTF-8");
                post2.setRequestMethod("POST");
                post2.setInstanceFollowRedirects(true);
                post2.connect();
                DataOutputStream out2 = new DataOutputStream(post2.getOutputStream());
                out.writeBytes(content);
                out.flush();
                out.close();
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(
                        post2.getInputStream()));
                int existClass = Integer.valueOf(reader2.readLine());
                reader2.close();


                if (maxClass < existClass) {
                    response.getWriter().println("{\"result\":\"Wrong!\"}");
                    return false;
                }
                return true;
            }
            response.getWriter().println("{\"result\":\"Wrong!\"}");
            return false;
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}

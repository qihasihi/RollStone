package com.school.share;

import com.school.util.LZX_MD5;
import com.school.util.UtilTool;
import net.sf.json.JSONSerializer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import java.util.*;

/**
 * Created by zhengzhou on 14-6-23.
 */
public class SendLZXMess extends TimerTask {
    private static String sendLzxMessUrl=null,sendLzxMessApiKey=null,sendLzxMessAppId=null;
    public SendLZXMess(){
        //加载相关数据
        sendLzxMessUrl= UtilTool.utilproperty.getProperty("LZX_SEND_MESS_URL");
        sendLzxMessApiKey= UtilTool.utilproperty.getProperty("LZX_SEND_MESS_apikey");
        sendLzxMessAppId= UtilTool.utilproperty.getProperty("LZX_SEND_MESS_appId");

    }
    @Override
    public void run() {

    }
    public static void main(String[] args) throws Exception{
        sendLzxMessUrl="http://123.127.240.118:10013/api/message/send";
        sendLzxMessApiKey="6230810fb942f9dbfdb9fb37448c28b6";
        sendLzxMessAppId="10788190454e4532b6dc7c0766004016";
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        Map<String,Object> objMap=new HashMap<String, Object>();
        objMap.put("userid","0634775faf414468bd07876b9b980c33");
        objMap.put("type",3);
        objMap.put("content","测试消息2313214213传递，及中文是否乱码的问题121212");
        objMap.put("url","http://www.baidu.com11");
        mapList.add(objMap);
        objMap=new HashMap<String, Object>();
        objMap.put("userid","0634775faf414468bd07876b9b980c33");
        objMap.put("type",3);
        objMap.put("content","测试消息传递，及中文是否乱码的问题!22222");
        objMap.put("url","http://www.sina.cn");
        mapList.add(objMap);
        String js=getMessageArray(mapList);
        //完整的URL
        Long dtime=new Date().getTime();
        int randomId=new Random().nextInt(10000);
        String apiId=sendLzxMessAppId;
        int nonce=randomId;
        String u=sendLzxMessUrl;
        String signature=LZX_MD5.getMD5Str(sendLzxMessApiKey+randomId+dtime);
        Map<String,Object> sendMess=new HashMap<String, Object>();
        sendMess.put("timestamp",dtime);
        sendMess.put("signature",signature);
        sendMess.put("nonce",nonce);
        sendMess.put("appId",apiId);
        sendMess.put("message",js);

        if(sendPostUrl(u,sendMess)){
            System.out.println("发送消息成功!");
        }else
            System.out.println("发送消息失败!");

    }


    /**
     * 得到消息信息
     * @param map
     * @return
     */
    private static String getMessageArray(final List<Map<String,Object>> map){
        return (map!=null&&map.size()>0)?JSONSerializer.toJSON(map).toString():null;
    }

    /**
     * fasong
     * @return
     */
    public static boolean sendPostUrl(String urlstr,Map<String,Object> params){
        if(urlstr==null)return false;

        if(StringUtils.isNotEmpty(urlstr)){
            HttpClient httpClient =new HttpClient();
            PostMethod postMethod = new PostMethod(urlstr);
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

            List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();


            if(params!=null&&params.size()>0){
                if(params!=null&&params.size()>0){
                    Set<String> keySet=params.keySet();
                        Iterator<String> keyIte=keySet.iterator();
                        NameValuePair[] data=new NameValuePair[keySet.size()];
                       int i=0;
                        while(keyIte!=null&&keyIte.hasNext()){
                            String key=keyIte.next();
                            data[i++]=new NameValuePair(key,params.get(key).toString());
                        }
                    postMethod.setRequestBody(data);
                }
            }
            String res="error";
            try {
                int statusCode=httpClient.executeMethod(postMethod);
                if(statusCode== HttpStatus.SC_OK){
                    res=postMethod.getResponseBodyAsString().trim();
                    System.out.println(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                postMethod.releaseConnection();
            }
            if(res!=null&&res.trim().length()>0){
                if(res.trim().equals("success"))
                    return true;
            }
        }
        return false;
    }

}

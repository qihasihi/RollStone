package com.school.share;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.school.entity.resource.ResourceInfo;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.resource.ResourceManager;
import com.school.util.*;
import net.sf.json.JSONObject;

import com.school.util.UtilTool.DateType;
import com.school.util.sendfile.SendFile;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

/**
 * 分享资源(定时器)
 * @author zhengzhou
 *
 */
public class ShareResource extends TimerTask {
    /**
     * 生成的模板XML
     */
    private final String template_name="ShareTemplate.xml";
    /**
     * 每次查询多少
     */
    private final Integer _pageSize=1;
    /**
     * 分校ID
     */
	private String schoolid=null;


	private ServletContext request;
	public ShareResource(ServletContext application){
		//分校ID
		this.request=application;
		 schoolid=UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
	}
	public void run() {
		// TODO Auto-generated method stub
            String firstDirectory=MD5_NEW.getMD5Result(MD5_NEW.getMD5Result(schoolid)+UtilTool.utilproperty.getProperty("TO_ETT_KEY").toString());
			//String firstDirectory=UtilTool.DateConvertToString(new Date(),DateType.smollDATE)+"_"+schoolid;
			String parentDirectory=request.getRealPath("/")+"/uploadfile/tmp/"; //根路径
			String directionPath=parentDirectory+firstDirectory+"/";
            //数据文件存放的文件夹
            String dataFileParent=directionPath+"data/";
            //记录XML保存地址
			String filename=new StringBuffer().append(firstDirectory).append(".xml").toString();

            String writeUrl=dataFileParent+"/"+filename;
            //资源文件zip序列文件  (用来记录资源包文件顺序)
            String filetxtName=new StringBuffer().append(new Date().getTime()+"")
                    .append("_").append(schoolid).append("_filename")
                    .append(".txt").toString();
            //保存template的路径
            String templatepath=parentDirectory+template_name;
             File fromF=new File(templatepath);
            if(!fromF.exists()){
                //记录错误信息
                System.out.println("分校异常!没有发现资源上传模版!");
                return;
            }
            //创建文件夹，并复制模板，进行操作
			File tmpF1=new File(writeUrl);
            try{
                if(!tmpF1.getParentFile().exists())
                    tmpF1.getParentFile().mkdirs();
                if(tmpF1.exists()&&tmpF1.isFile())
                    tmpF1.delete();
                //复制模板
                FileUtils.copyFile(fromF,tmpF1);
            }catch(Exception e){
                //记录日志
                System.out.println("分校异常!创建空文件失败，原因：未知");
                e.printStackTrace();
            }
			//得到要分享的资源
        ResourceInfo rssearch=new ResourceInfo();
        rssearch.setSharestatus(2);     //云端共享
        rssearch.setNetsharestatus(-1);//等等审核
        rssearch.setResdegree(3);   //是本地资源
        //醒询得到数据
        IResourceManager resourceManager=(ResourceManager) SpringBeanUtil.getBean("resourceManager");
        PageResult presult=new PageResult();
        presult.setPageSize(_pageSize);
        presult.setPageNo(0);
            String zipNameKey=firstDirectory;
           int x=0;
            while(true){
                presult.setPageNo(presult.getPageNo()+1);
                List<ResourceInfo> resList=resourceManager.getList(rssearch,presult);
                if(resList==null||resList.size()<1){
                    break;
                }
                x++;
              //  writeUrl=writeUrl.substring(0,writeUrl.lastIndexOf("."))
                if(!writeResourceToXml(writeUrl,resList)){
                    //记录错误信息以及时间
                    System.out.println("错误，分校错误，原因：没有可更新的资源信息!");
                }
//                 String currentDirectoryName=zipNameKey+"_"+presult.getPageNo();
//                File tmp=new File(directionPath+"/"+currentDirectoryName+"/");
//                if(tmp.exists()){
//                    //删除文件夹
//                    tmp.delete();
//                }
//                tmp.mkdirs();//创建文件夹
                //得到文件，并打包 同时进行打包
                for(ResourceInfo rstmp:resList){
                    //复制文件
                    String directory=UtilTool.getResourceMd5Directory(rstmp.getResid().toString());

                   String filePath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+directory+"/";
                    //开始复制文件
                    File dfile=new File(filePath);
                    if(dfile.exists()&&dfile.isDirectory()){
                        //打包至目录下
                        ZipUtil.genZip(filePath,directionPath,directory);
                        UtilTool.writeFile(request, parentDirectory+"/",filetxtName, directionPath+"/"+directory+".zip\r\n");
                    }
                    //更新XML zipName字段
                    OperateXMLUtil.updateXml(writeUrl,"RES_ID",rstmp.getResid()+"",new String[]{"zipName"},new String[]{directory+".zip"});
                }
            }
            if(x==0){
                System.out.println("异常错误，没有可以同步的资源。!");
            }else{  //说明存在共享的资源，继续更新。
                //将数据文件进行打包 zip  并加入到发送序列中
                ZipUtil.genZip(dataFileParent,directionPath,filename.split("\\.")[0]);
                String tmp=directionPath+"/"+filename.split("\\.")[0]+".zip";
                UtilTool.writeFile(request, parentDirectory+"/",filetxtName, tmp+"\r\n");

                //读取文件上传序列，进行上传
                UtilTool.uploadResourceToTotalSchool(parentDirectory + "/" + filetxtName);
                //调用接口，添加数据
                System.out.println("文件上传完成，调用接口，添加数据");
                //得到key
                String fpath = request.getRealPath("/") + "school.txt";
                BufferedReader br = null;
                StringBuilder content = null;
                try {
                    br = new BufferedReader(new FileReader(fpath.trim()));
                    String lineContent = null;
                    while ((lineContent = br.readLine()) != null) {
                        if (content == null)
                            content = new StringBuilder(lineContent);
                        else
                            content.append("\n" + lineContent);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return;
                } finally {
                    if (br != null)
                        try{
                        br.close();
                        }catch(Exception e){e.printStackTrace();}
                }
                String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
                totalSchoolUrl+="/resourceInter";
                String params="m=fxsczy&key="+content.toString()+"&resolveFilename="+firstDirectory+".zip";
                //调用总校接口，解析文件
                if(ShareResourceUtil.sendResourcePathToTotalSchool(totalSchoolUrl,params)){
                    //开始更新数据库，更新已经同步的资源
                    if(resourceManager.doUpdateShareNetShareStatus(0, 2, 3, -1)){
                        System.out.println("resource resolved success!");
                    }else{
                        System.out.println("resource update success!but localhost dataset is error!");
                    }
                }else{
                    System.out.println("resource resolved error!");
                }
            }
		}

    /**
     * 写入XML中
     * @param saveUrl  XML保存的位置,
     * @param resList  要写入的数据
     * @return
     */
        private boolean writeResourceToXml(String saveUrl,List<ResourceInfo> resList){
            if(resList==null||resList.size()<1)
                return false;

             boolean returnVal=true;

            // 获取学生信息
            //模拟的数据
            for (int i = 0; i <resList.size(); i++) {
                //记录录入的列与值
                List<String> columnList=new ArrayList<String>();
                List<String> valueList=new ArrayList<String>();

                ResourceInfo s = (ResourceInfo)resList.get(i);
                // 创建一个学生
                columnList.add("RES_ID");valueList.add(s.getResid().toString());
                if(s.getResname()!=null){           //资源NAME
                    columnList.add("RES_NAME");valueList.add(s.getResname());
                }
                if(s.getResintroduce()!=null){
                    columnList.add("RES_INTRODUCE");valueList.add(s.getResintroduce());//资源介绍
                }
                if(s.getUserid()!=null){
                    columnList.add("USER_ID");valueList.add(s.getUserid().toString());
                }
                if(s.getUsertype()!=null){   //本地作者类型（1:学生 2:老师）
                    columnList.add("USER_TYPE");valueList.add(s.getUsertype().toString());
                } if(s.getResstatus()!=null){//资源状态：0已删除
                    columnList.add("RES_STATUS");valueList.add(s.getResstatus().toString());
                }if(s.getResscore()!=null){       //资源评分
                    columnList.add("RES_SCORE");valueList.add(s.getResscore().toString());
                }if(s.getClicks()!=null){
                    //本地点击次数
                    columnList.add("CLICKS");valueList.add(s.getResscore().toString());
                }if(s.getCommentnum()!=null){
                 //本地评论次数
                    columnList.add("COMMENTNUM");valueList.add(s.getCommentnum().toString());
                } if(s.getDownloadnum()!=null){
                 //本地下载次数
                columnList.add("DOWNLOADNUM");valueList.add(s.getDownloadnum().toString());
                 } if(s.getStorenum()!=null){
                    columnList.add("STORENUM");valueList.add(s.getStorenum().toString());
                          //本地收藏次数
                }if(s.getPraisenum()!=null){
                     //本地攒次数
                    columnList.add("PRAISENUM");valueList.add(s.getPraisenum().toString());
                } if(s.getRecomendnum()!=null){
                   //本地推荐次数
                    columnList.add("RECOMENDNUM");valueList.add(s.getRecomendnum().toString());
                }  if(s.getReportnum()!=null){
                     //本地举报次数
                    columnList.add("REPORTNUM");valueList.add(s.getReportnum().toString());
                }if(s.getUsername()!=null){
                     //上传人姓名
                    columnList.add("USER_NAME");valueList.add(s.getUsername().toString());
                } if(s.getSchoolname()!=null){
                    //学校名称
                    columnList.add("SCHOOL_NAME");valueList.add(s.getSchoolname().toString());
                } if(s.getResdegree()!=null){
                     //资源等级（1:标准 2:共享 3:本地）
                    columnList.add("RES_DEGREE");valueList.add(s.getResdegree().toString());
                } if(s.getFilesuffixname()!=null){
                  //资源后缀名
                    columnList.add("FILE_SUFFIXNAME");valueList.add(s.getFilesuffixname().toString());
                } if(s.getFilesize()!=null){
                        //文件大小
                    columnList.add("FILE_SIZE");valueList.add(s.getFilesize().toString());
                } if(s.getUseobject()!=null){
                       //适用对象
                    columnList.add("USE_OBJECT");valueList.add(s.getUseobject().toString());
                }if(s.getRestype()!=null){
                          //资源类型
                    columnList.add("RES_TYPE");valueList.add(s.getRestype().toString());
                }if(s.getFiletype()!=null){
                         //文件类型
                    columnList.add("FILE_TYPE");valueList.add(s.getFiletype().toString());
                }if(s.getGrade()!=null){
                               //年级编号
                    columnList.add("GRADE");valueList.add(s.getGrade().toString());
                } if(s.getSubject()!=null){
                      //学科编号
                    columnList.add("SUBJECT");valueList.add(s.getSubject().toString());
                }
                columnList.add("C_TIME");valueList.add(s.getCtime().getTime()+"");
                if(!OperateXMLUtil.addXml(saveUrl,columnList,valueList.toArray())){
                    returnVal=false;
                    //记录同步日志
                    System.out.println("分校异常错误!编写XML异常!");
                    break;
                }
            }
            return returnVal;
        }

    /*
    	UtilTool.writeFile(request,directionPath, filename, "[stat=300]\r\n");
			//得到数据

			//循环读写文件
			PageResult presult=new PageResult();
			presult.setPageNo(0);
			presult.setPageSize(100);
			while(true){
				presult.setPageNo(presult.getPageNo()+1);
				List<List<String>> resourceArrayList=sharemanager.getShareResourceInfo(presult);
				if(resourceArrayList==null||resourceArrayList.size()<1)
					break;
				for (List<String> tmpList : resourceArrayList) {
					StringBuilder tmpBuilder=new StringBuilder();
					tmpBuilder.append(tmpList.get(0));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(1));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(2));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(3));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(4));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(5));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(6));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(7));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(8));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(9));
					tmpBuilder.append("\r\n");
					UtilTool.writeFile(request, directionPath, filename, tmpBuilder.toString());
				}
			}
			UtilTool.writeFile(request,directionPath, filename, "[endstat=300]\r\n");
			//[endstat=300]
			//[stat=400]
			UtilTool.writeFile(request,directionPath, filename, "[stat=400]\r\n");
			presult.setPageNo(0);
			String filetxtName=new StringBuffer().append(new Date().getTime()+"")
							.append("_").append(schoolid).append("_filename")
							.append(".txt").toString();
			//IResourceFileManager resourceFileManager=(ResourceFileManager)applicationContext.getBean("resourceFileManager");
			while(true){
				presult.setPageNo(presult.getPageNo()+1);
				List<List<String>> resourceFleList=sharemanager.getShareResourceFile(presult);
				if(resourceFleList==null||resourceFleList.size()<1)
					break;
				for (List<String> tmpList : resourceFleList) {
					StringBuilder tmpBuilder=new StringBuilder();
					tmpBuilder.append(tmpList.get(0));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(1));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(2));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(3));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(4));
					tmpBuilder.append("\r\n");
					UtilTool.writeFile(request, directionPath, filename, tmpBuilder.toString());
					//组织文件，并开始上传
					String downPath=UtilTool.getResourceUrl(tmpList.get(0).trim(), tmpList.get(1).trim(),tmpList.get(3));
					String downDirectory=downPath.split("/")[0];
					//得到resource_file并同时写入文件中，等待上传
					//String filerealpath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+downPath+"\r\n";
					String directorypath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+downDirectory+"/";
					//开始压缩文件

					ZipUtil.genZip(directorypath, directorypath+"../", downDirectory);
					UtilTool.writeFile(request, parentDirectory,filetxtName, directorypath+"../"+downDirectory+".zip\r\n");
				}
			}
			UtilTool.writeFile(request,directionPath, filename, "[endstat=400]\r\n");
			//[endstat=400]
			//[stat=500]
			UtilTool.writeFile(request, directionPath, filename, "[stat=500]\r\n");
			presult.setPageNo(0);
			while(true){
				presult.setPageNo(presult.getPageNo()+1);
				List<List<String>> extedresourceList=sharemanager.getShareExtendResourceInfo(presult);
				if(extedresourceList==null||extedresourceList.size()<1)
					break;
				for (List<String> tmpList : extedresourceList) {
					StringBuilder tmpBuilder=new StringBuilder();
					tmpBuilder.append(tmpList.get(0));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(1));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(2));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(3));
					tmpBuilder.append(split_char);
					tmpBuilder.append(tmpList.get(4));
					tmpBuilder.append("\r\n");
					UtilTool.writeFile(request, directionPath, filename, tmpBuilder.toString());
				}
			}
			UtilTool.writeFile(request, directionPath, filename, "[endstat=500]\r\n");
    * */



    // 写入ｘｍｌ文件
    public static void callWriteXmlFile(Document doc, Writer w, String encoding) {
        try {
            Source source = new DOMSource(doc);
            Result result = new StreamResult(w);
            Transformer xformer = TransformerFactory.newInstance()
                    .newTransformer();
            xformer.setOutputProperty(OutputKeys.ENCODING, encoding);
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
/**
 * 共享资源工具
 * @author zhengzhou
 *
 */
class ShareResourceUtil{



	/**
	 * fasong ziyuan
	 * @param totalSchoolUrl
	 * @return
	 */
	public static boolean sendResourcePathToTotalSchool(String totalSchoolUrl,String params){
		if(totalSchoolUrl==null)return false;

			
		HttpURLConnection httpConnection;
		URL url;
		int code;
		try {
			url = new URL(totalSchoolUrl.toString());

			httpConnection = (HttpURLConnection) url.openConnection();

			httpConnection.setRequestMethod("POST");
            if(params!=null)
			    httpConnection.setRequestProperty("Content-Length",String.valueOf(params.length()));
			httpConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			/*
			 * PrintWriter printWriter = new
			 * PrintWriter(httpConnection.getOutputStream());
			 * printWriter.print(parameters); printWriter.close();
			 */

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					httpConnection.getOutputStream(), "8859_1");
            if(params!=null)
			    outputStreamWriter.write(params);
			outputStreamWriter.flush();
			outputStreamWriter.close();

			code = httpConnection.getResponseCode();
		} catch (Exception e) {			// 异常提示
			System.out.println("异常错误!TOTALSCHOOL未响应!");
			return false;
		}
		StringBuffer stringBuffer = new StringBuffer();
		if (code == HttpURLConnection.HTTP_OK) {
			try {
				String strCurrentLine;
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpConnection.getInputStream()));
				while ((strCurrentLine = reader.readLine()) != null) {
					stringBuffer.append(strCurrentLine).append("\n");
				}
				reader.close();
			} catch (IOException e) {
				System.out.println("异常错误!");
				return false;
			}
		}else if(code==404){
			// 提示 返回
			System.out.println("异常错误!404错误，请联系管理人员!");
			return false;
		}else if(code==500){
			System.out.println("异常错误!500错误，请联系管理人员!");
			return false;
		}
		String returnContent=null;
		try {
			returnContent=new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//转换成JSON
		System.out.println(returnContent);
		JSONObject jb=JSONObject.fromObject(returnContent);
		String type=jb.containsKey("type")?jb.getString("type"):"";
		String msg=jb.containsKey("msg")?jb.getString("msg"):"";
		if(type!=null&&type.trim().toLowerCase().equals("success")){
			System.out.println(msg);
			return true;
		}else{
			System.out.println(msg);return false;
		}
	}

    /**
     * 得到ID
     * @param args
     */
    public static void mian(String[] args){
        //xml保存地址 并加上名称


    }


}

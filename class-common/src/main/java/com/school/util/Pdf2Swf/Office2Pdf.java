/**
  * 
  */
 package com.school.util.Pdf2Swf;
 
 import java.io.File;
 import java.util.Date;

import javax.servlet.http.HttpServletRequest;

 import com.school.util.UtilTool;
 import org.artofsolving.jodconverter.OfficeDocumentConverter;
 import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
 
 /**
  * 这是一个工具类，主要是为了使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx)
  * 转化为pdf文件<br>
  * Office2010的没测试<br>
  * 
  * @date 2012-11-5
  * @author xhw
  * 
  */

 public class Office2Pdf {
	 OfficeManager officeManager = null;
     // 连接OpenOffice
      OfficeDocumentConverter converter = null;
     /**
      * office中.doc格式
      */
     public static final String OFFICE_DOC = "doc";
     /**
      * office中.docx格式
      */
     public static final String OFFICE_DOCX = "docx";
     /**
      * office中.xls格式
      */
     public static final String OFFICE_XLS = "xls";
     /**
      * office中.xlsx格式
      */
     public static final String OFFICE_XLSX = "xlsx";
     /**
      * office中.ppt格式
      */
     public static final String OFFICE_PPT = "ppt";
     /**
      * office中.pptx格式
      */
     public static final String OFFICE_PPTX = "pptx";
     /**
      * pdf格式
      */
     public static final String OFFICE_TO_PDF = "pdf";

 
     /**
      * 使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx) 转化为pdf文件<br>
      * 
      * @param inputFilePath
      *            源文件路径，如："e:/test.docx"
      * @param outputFilePath
      *            目标文件路径，如："e:/test_docx.pdf"
      * @return
      */
//     public boolean openOfficeToPDF(String inputFilePath, String outputFilePath) {
//         return office2pdf(inputFilePath, outputFilePath);
//     }
 
     /**
      * 根据操作系统的名称，获取OpenOffice.org 3的安装目录<br>
      * 如我的OpenOffice.org 3安装在：C:/Program Files (x86)/OpenOffice.org 3<br>
      * 
      * @return OpenOffice.org 3的安装目录
      */
     public String getOfficeHome(HttpServletRequest request) {
         String osName = System.getProperty("os.name");
//         if (Pattern.matches("Linux.*", osName)) {
             //return request.getRealPath("/")+"util/docconverter/OpenOffice.org 3";
//         } else if (Pattern.matches("Windows.*", osName)) {
//             return "C:/Program Files/OpenOffice.org 3";
//         } else if (Pattern.matches("Mac.*", osName)) {
//             return "/Application/OpenOffice.org.app/Contents";
//         }
         String path= UtilTool.utilproperty.getProperty("OPENOFFICE_PATH");
         if(path==null||path.length()<1)
             return "C:/Program Files (x86)/OpenOffice.org 3";
         else
             return path;
//         return null;
     }
 
     /**
      * 连接OpenOffice.org 并且启动OpenOffice.org
      * 
      * @return
      */
     public OfficeManager getOfficeManager(HttpServletRequest request) {
         DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
         // 获取OpenOffice.org 3的安装目录
         String officeHome = getOfficeHome(request);
         config.setOfficeHome(officeHome);
         // 启动OpenOffice的服务
         OfficeManager officeManager = config.buildOfficeManager();
         officeManager.start();
         return officeManager;
     }
 
     /**
      * 转换文件
      * 
      * @param inputFile
      * @param outputFilePath_end
      * @param inputFilePath
      * @param outputFilePath
      * @param converter
      */
     public void converterFile(File inputFile, String outputFilePath_end, String inputFilePath, String outputFilePath, OfficeDocumentConverter converter) {
         File outputFile = new File(outputFilePath_end);
         System.out.println("inputFile:"+inputFile+"   outputFile:"+outputFile);
         // 假如目标路径不存在,则新建该路径
         if (!outputFile.getParentFile().exists()) {
             outputFile.getParentFile().mkdirs();
         }
         converter.convert(inputFile, outputFile);
         System.out.println("文件：" + inputFilePath + "\n转换为\n目标文件：" + outputFile + "\n成功！");
     }
 
     /**
      * 使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx) 转化为pdf文件<br>
      * 
      * @param inputFilePath  
      *            源文件路径，如："e:/test.docx"
      * @param outputFilePath
      *            目标文件路径，如："e:/test_docx.pdf"
      * @return
      */
     public  String  office2pdf(HttpServletRequest request,String inputFilePath, String outputFilePath) {
         if(inputFilePath==null)
        	   return "";
         officeManager=getOfficeManager(request);
         converter=new OfficeDocumentConverter(officeManager);
         String a="",outputFilePath_end="";
         synchronized (a) {
	         long begin_time = new Date().getTime();
	         if (null != inputFilePath) {
	        	 System.out.println("11111111111111111111111111");
	             File inputFile = new File(inputFilePath);
	             // 判断目标文件路径是否为空
	             if (null == outputFilePath) {
	                 // 转换后的文件路径
	                 outputFilePath_end = getOutputFilePath(inputFilePath);
	                 System.out.println("222222222222222222222222222222");
	                 if (inputFile.exists()) {// 找不到源文件, 则返回
	                     converterFile(inputFile, outputFilePath_end, inputFilePath, outputFilePath, converter);
	                 }
	             } else {
	                 if (inputFile.exists()) {// 找不到源文件, 则返回
	                     converterFile(inputFile, outputFilePath, inputFilePath, outputFilePath, converter);
	                 }
	             }
                 System.out.println("3333333333333333333333333");
	             officeManager.stop();
	         } else {
	             System.out.println("con't find the resource");
	         }
	         long end_time = new Date().getTime();
	         System.out.println("文件转换耗时：[" + (end_time - begin_time) + "]ms");
         }
         return outputFilePath_end;
     }
 
     /**
      * 获取输出文件
      * 
      * @param inputFilePath
      * @return
      */
     public String getOutputFilePath(String inputFilePath) {
         String outputFilePath = inputFilePath.replaceAll("." + getPostfix(inputFilePath), ".pdf");
         return outputFilePath;
     }
 
     /**
      * 获取inputFilePath的后缀名，如："e:/test.pptx"的后缀名为："pptx"<br>
      * 
      * @param inputFilePath
      * @return  
      */
     public String getPostfix(String inputFilePath) {
         return inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
     }
 
 }
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
  * ����һ�������࣬��Ҫ��Ϊ��ʹOffice2003-2007ȫ����ʽ���ĵ�(.doc|.docx|.xls|.xlsx|.ppt|.pptx)
  * ת��Ϊpdf�ļ�<br>
  * Office2010��û����<br>
  * 
  * @date 2012-11-5
  * @author xhw
  * 
  */

 public class Office2Pdf {
	 OfficeManager officeManager = null;
     // ����OpenOffice
      OfficeDocumentConverter converter = null;
     /**
      * office��.doc��ʽ
      */
     public static final String OFFICE_DOC = "doc";
     /**
      * office��.docx��ʽ
      */
     public static final String OFFICE_DOCX = "docx";
     /**
      * office��.xls��ʽ
      */
     public static final String OFFICE_XLS = "xls";
     /**
      * office��.xlsx��ʽ
      */
     public static final String OFFICE_XLSX = "xlsx";
     /**
      * office��.ppt��ʽ
      */
     public static final String OFFICE_PPT = "ppt";
     /**
      * office��.pptx��ʽ
      */
     public static final String OFFICE_PPTX = "pptx";
     /**
      * pdf��ʽ
      */
     public static final String OFFICE_TO_PDF = "pdf";

 
     /**
      * ʹOffice2003-2007ȫ����ʽ���ĵ�(.doc|.docx|.xls|.xlsx|.ppt|.pptx) ת��Ϊpdf�ļ�<br>
      * 
      * @param inputFilePath
      *            Դ�ļ�·�����磺"e:/test.docx"
      * @param outputFilePath
      *            Ŀ���ļ�·�����磺"e:/test_docx.pdf"
      * @return
      */
//     public boolean openOfficeToPDF(String inputFilePath, String outputFilePath) {
//         return office2pdf(inputFilePath, outputFilePath);
//     }
 
     /**
      * ���ݲ���ϵͳ�����ƣ���ȡOpenOffice.org 3�İ�װĿ¼<br>
      * ���ҵ�OpenOffice.org 3��װ�ڣ�C:/Program Files (x86)/OpenOffice.org 3<br>
      * 
      * @return OpenOffice.org 3�İ�װĿ¼
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
      * ����OpenOffice.org ��������OpenOffice.org
      * 
      * @return
      */
     public OfficeManager getOfficeManager(HttpServletRequest request) {
         DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
         // ��ȡOpenOffice.org 3�İ�װĿ¼
         String officeHome = getOfficeHome(request);
         config.setOfficeHome(officeHome);
         // ����OpenOffice�ķ���
         OfficeManager officeManager = config.buildOfficeManager();
         officeManager.start();
         return officeManager;
     }
 
     /**
      * ת���ļ�
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
         // ����Ŀ��·��������,���½���·��
         if (!outputFile.getParentFile().exists()) {
             outputFile.getParentFile().mkdirs();
         }
         converter.convert(inputFile, outputFile);
         System.out.println("�ļ���" + inputFilePath + "\nת��Ϊ\nĿ���ļ���" + outputFile + "\n�ɹ���");
     }
 
     /**
      * ʹOffice2003-2007ȫ����ʽ���ĵ�(.doc|.docx|.xls|.xlsx|.ppt|.pptx) ת��Ϊpdf�ļ�<br>
      * 
      * @param inputFilePath  
      *            Դ�ļ�·�����磺"e:/test.docx"
      * @param outputFilePath
      *            Ŀ���ļ�·�����磺"e:/test_docx.pdf"
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
	             // �ж�Ŀ���ļ�·���Ƿ�Ϊ��
	             if (null == outputFilePath) {
	                 // ת������ļ�·��
	                 outputFilePath_end = getOutputFilePath(inputFilePath);
	                 System.out.println("222222222222222222222222222222");
	                 if (inputFile.exists()) {// �Ҳ���Դ�ļ�, �򷵻�
	                     converterFile(inputFile, outputFilePath_end, inputFilePath, outputFilePath, converter);
	                 }
	             } else {
	                 if (inputFile.exists()) {// �Ҳ���Դ�ļ�, �򷵻�
	                     converterFile(inputFile, outputFilePath, inputFilePath, outputFilePath, converter);
	                 }
	             }
                 System.out.println("3333333333333333333333333");
	             officeManager.stop();
	         } else {
	             System.out.println("con't find the resource");
	         }
	         long end_time = new Date().getTime();
	         System.out.println("�ļ�ת����ʱ��[" + (end_time - begin_time) + "]ms");
         }
         return outputFilePath_end;
     }
 
     /**
      * ��ȡ����ļ�
      * 
      * @param inputFilePath
      * @return
      */
     public String getOutputFilePath(String inputFilePath) {
         String outputFilePath = inputFilePath.replaceAll("." + getPostfix(inputFilePath), ".pdf");
         return outputFilePath;
     }
 
     /**
      * ��ȡinputFilePath�ĺ�׺�����磺"e:/test.pptx"�ĺ�׺��Ϊ��"pptx"<br>
      * 
      * @param inputFilePath
      * @return  
      */
     public String getPostfix(String inputFilePath) {
         return inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
     }
 
 }
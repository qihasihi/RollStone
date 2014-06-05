package com.school.util.sendfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.school.util.UtilTool;



public class SendFile {
	public static void main(String[] args) {
	List<String>	sendFilepath = new ArrayList<String>();

	sendFilepath.add("G:/���/10201_client_win32.zip");
	sendFilepath.add("G:/���/sql2005���˰�/SQLFULL_x86_CHS.exe");

		SendFile s = new SendFile();
		if (s.sendFileOperate(sendFilepath))
			System.out.println("success");
		else
			System.out.println("error");

	}
	
	
	public boolean sendFileOperate(List<String> fpathList) {
		// ��֤fpathList
		String returnMsg = SendFileUtil.validateFile(fpathList.toArray());
		if (!returnMsg.equals("SUCCESS")) {
			System.out.println(returnMsg);
			return false;
		}
		//ѭ�������ļ�
		for (String path : fpathList) {
			SocketClient client = new SocketClient(path);				
			if(!client.sendMessage())
                return false;
		}		
		return true;
	}
}

/**
 * �ļ����͹�����
 * @author zhengzhou
 *
 */
class SendFileUtil{
	/**
	 * ��֤�ļ�
	 * 
	 * @param filepath
	 * @return
	 */
	public static String validateFile(Object[] filepath) {
		if (filepath == null || filepath.length < 1)
			return null;
		StringBuilder msgBuilder = new StringBuilder();
		for (Object fpathObj : filepath) {
			String fpath = fpathObj.toString();
			if (fpath == null || fpath.trim().length() < 1) {
				writeMsg(msgBuilder, fpath, " string is empty!");
			} else {
				File f = new File(fpath);
				if (!f.exists()) // �ļ��Ƿ����
					writeMsg(msgBuilder, fpath, " not found!");
				else if (!f.isFile()) // �ļ��Ƿ���file
					writeMsg(msgBuilder, fpath, " is not file!");
				else { // �ļ���С�Ƿ���Ҫ��Χ��
					String fileMinSize = UtilTool.utilproperty.getProperty("file_min_size"); // �ļ���С�����С
					if (fileMinSize == null || !UtilTool.isNumber(fileMinSize)) {
						writeMsg(msgBuilder, null,
								" file_min_size properties is error! ");
						continue;
					}
					String fileMaxSize =UtilTool.utilproperty.getProperty("file_max_size"); // �ļ���������С
					if (fileMaxSize == null || !UtilTool.isNumber(fileMaxSize)) {
						writeMsg(msgBuilder, null,
								" file_min_size properties is error! ");
						continue;
					}
					String allowFile =UtilTool.utilproperty.getProperty("allow_file");
					if (allowFile == null)
						allowFile = "*";
					// ��֤�ļ�����
					if (!allowFile.equals("*")) {
						String[] fileName = f.getName().split(".");
						String[] allowFileStr = allowFile.split(",");
						boolean isvalidateOk = false;
						for (String str : allowFileStr) {
							if (str == null || str.trim().length() < 1) {
								continue;
							}
							if (fileName == null || fileName.length < 2) {
								writeMsg(msgBuilder, fpath, " lastname error!");
								continue;
							}
							if (("." + fileName[1]).trim().equals(str.trim())) {
								isvalidateOk = true;
								break;
							}
						}
						if (!isvalidateOk) {
							writeMsg(msgBuilder, fpath,
									" lastname don't allow!");
						}
					}
					// ��֤�ļ���С
					Long flength = f.length();
					// ��֤��С�Ƿ�ͨ��
					if (flength < Long.parseLong(fileMinSize.trim())) {
						writeMsg(msgBuilder, fpath, " minSize> currentpath");
						continue;
					}
					// ��֤����Ƿ�ͨ��
					if (flength > Long.parseLong(fileMaxSize.trim())) {
						writeMsg(msgBuilder, fpath, " maxSize> currentpath");
						continue;
					}
				}
			}
		}
		if (msgBuilder.toString().trim().length() < 1)
			msgBuilder.append("SUCCESS");
		return msgBuilder.toString();
	}

	/**
	 * д�������Ϣ
	 * 
	 * @param msgBuilder
	 * @param fpath
	 * @param msg
	 */
	private static void writeMsg(StringBuilder msgBuilder, String fpath,
			String msg) {
		if (msgBuilder.toString().trim().length() < 1)
			msgBuilder.append("");
		else
			msgBuilder.append(",");
		msgBuilder.append(fpath + " " + msg);
	}
	
}


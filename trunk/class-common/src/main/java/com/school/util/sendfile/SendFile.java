package com.school.util.sendfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.school.util.UtilTool;



public class SendFile {
	public static void main(String[] args) {
	List<String>	sendFilepath = new ArrayList<String>();

	sendFilepath.add("G:/软件/10201_client_win32.zip");
	sendFilepath.add("G:/软件/sql2005个人版/SQLFULL_x86_CHS.exe");

		SendFile s = new SendFile();
		if (s.sendFileOperate(sendFilepath))
			System.out.println("success");
		else
			System.out.println("error");

	}
	
	
	public boolean sendFileOperate(List<String> fpathList) {
		// 验证fpathList
		String returnMsg = SendFileUtil.validateFile(fpathList.toArray());
		if (!returnMsg.equals("SUCCESS")) {
			System.out.println(returnMsg);
			return false;
		}
		//循环发送文件
		for (String path : fpathList) {
			SocketClient client = new SocketClient(path);				
			if(!client.sendMessage())
                return false;
		}		
		return true;
	}
}

/**
 * 文件发送工具类
 * @author zhengzhou
 *
 */
class SendFileUtil{
	/**
	 * 验证文件
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
				if (!f.exists()) // 文件是否存在
					writeMsg(msgBuilder, fpath, " not found!");
				else if (!f.isFile()) // 文件是否是file
					writeMsg(msgBuilder, fpath, " is not file!");
				else { // 文件大小是否在要求范围内
					String fileMinSize = UtilTool.utilproperty.getProperty("file_min_size"); // 文件最小允许大小
					if (fileMinSize == null || !UtilTool.isNumber(fileMinSize)) {
						writeMsg(msgBuilder, null,
								" file_min_size properties is error! ");
						continue;
					}
					String fileMaxSize =UtilTool.utilproperty.getProperty("file_max_size"); // 文件最大允许大小
					if (fileMaxSize == null || !UtilTool.isNumber(fileMaxSize)) {
						writeMsg(msgBuilder, null,
								" file_min_size properties is error! ");
						continue;
					}
					String allowFile =UtilTool.utilproperty.getProperty("allow_file");
					if (allowFile == null)
						allowFile = "*";
					// 验证文件类型
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
					// 验证文件大小
					Long flength = f.length();
					// 验证最小是否通过
					if (flength < Long.parseLong(fileMinSize.trim())) {
						writeMsg(msgBuilder, fpath, " minSize> currentpath");
						continue;
					}
					// 验证最大是否通过
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
	 * 写入错误信息
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


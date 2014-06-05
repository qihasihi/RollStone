package com.school.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	/**
	 * 将存放在sourceFilePath目录下的源文件,打包成fileName名称的ZIP文件,并存放到zipFilePath。
	 * 
	 * @param sourceFilePath
	 *            待压缩的文件路径
	 * @param zipFilePath
	 *            压缩后存放路径
	 * @param fileName
	 *            压缩后文件的名称
	 * @return flag
	 */
	public static boolean genZip(String sourceFilePath, String zipFilePath,
			String fileName) {
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		if (sourceFile.exists() == false) {
			System.out.println(">>>>>> 待压缩的文件目录：" + sourceFilePath
					+ " 不存在. <<<<<<");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {
					System.out.println(">>>>>> " + zipFilePath + " 目录下存在名字为："
							+ fileName + ".zip" + " 打包文件.准备删除,重新压缩 <<<<<<");
					zipFile.delete();
				}

					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println(">>>>>> 待压缩的文件目录：" + sourceFilePath
								+ " 里面不存在文件,无需压缩. <<<<<<");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// 创建ZIP实体,并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i]
									.getName());
							zos.putNextEntry(zipEntry);
							// 读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024 * 10);
							int read = 0;
							while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
								zos.write(bufs, 0, read);
							}
						}
						flag = true;
					}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != zos)
						zos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return flag;
	}

    /**
     * 解压文件
     * @param zipFilePath  zip文件路径
     * @param unzipDirectory  解压后的路径
     * @throws Exception
     */
    public static void unzip(String zipFilePath, String unzipDirectory)
            throws Exception {

        // 创建文件对象
        File file = new File(zipFilePath);
        // 创建zip文件对象
        ZipFile zipFile = new ZipFile(file);
        // 创建本zip文件解压目录

        File unzipFile = new File(unzipDirectory);
        if (unzipFile.exists())
            unzipFile.delete();
        unzipFile.mkdirs();

        // 得到zip文件条目枚举对象
        Enumeration zipEnum = zipFile.entries();
        // 定义输入输出流对象
        InputStream input = null;
        OutputStream output = null;
        // 循环读取条目
        System.out
                .println("name\t\t\tsize\t\t\tcompressedSize\t\t\tisDirectory");
        while (zipEnum.hasMoreElements()) {
            // 得到当前条目
            ZipEntry entry = (ZipEntry) zipEnum.nextElement();
            String entryName = new String(entry.getName().getBytes("ISO8859_1"));
            System.out.println(entryName + "\t\t\t" + entry.getSize()
                    + "\t\t\t" + entry.getCompressedSize() + "\t\t\t\t\t\t\t"
                    + entry.isDirectory());
            String zipEntryName = entry.getName();
            InputStream in = zipFile.getInputStream(entry);
            String outPath = (unzipFile.getPath()+"/"+zipEntryName).replaceAll("\\*", "/");
            //判断路径是否存在,不存在则创建文件路径
            File tf = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if(!tf.exists()){
                tf.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if(new File(outPath).isDirectory()){
                continue;
            }
            //输出文件路径信息
            System.out.println(outPath);

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while((len=in.read(buf1))>0){
                out.write(buf1,0,len);
            }
            in.close();
            out.close();

        }
    }



	static String getSuffixName(String name) {
		return name.substring(0, name.lastIndexOf("."));
	}
}

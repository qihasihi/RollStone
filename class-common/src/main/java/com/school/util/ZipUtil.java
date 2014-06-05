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
	 * �������sourceFilePathĿ¼�µ�Դ�ļ�,�����fileName���Ƶ�ZIP�ļ�,����ŵ�zipFilePath��
	 * 
	 * @param sourceFilePath
	 *            ��ѹ�����ļ�·��
	 * @param zipFilePath
	 *            ѹ������·��
	 * @param fileName
	 *            ѹ�����ļ�������
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
			System.out.println(">>>>>> ��ѹ�����ļ�Ŀ¼��" + sourceFilePath
					+ " ������. <<<<<<");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {
					System.out.println(">>>>>> " + zipFilePath + " Ŀ¼�´�������Ϊ��"
							+ fileName + ".zip" + " ����ļ�.׼��ɾ��,����ѹ�� <<<<<<");
					zipFile.delete();
				}

					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println(">>>>>> ��ѹ�����ļ�Ŀ¼��" + sourceFilePath
								+ " ���治�����ļ�,����ѹ��. <<<<<<");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// ����ZIPʵ��,����ӽ�ѹ����
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i]
									.getName());
							zos.putNextEntry(zipEntry);
							// ��ȡ��ѹ�����ļ���д��ѹ������
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
				// �ر���
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
     * ��ѹ�ļ�
     * @param zipFilePath  zip�ļ�·��
     * @param unzipDirectory  ��ѹ���·��
     * @throws Exception
     */
    public static void unzip(String zipFilePath, String unzipDirectory)
            throws Exception {

        // �����ļ�����
        File file = new File(zipFilePath);
        // ����zip�ļ�����
        ZipFile zipFile = new ZipFile(file);
        // ������zip�ļ���ѹĿ¼

        File unzipFile = new File(unzipDirectory);
        if (unzipFile.exists())
            unzipFile.delete();
        unzipFile.mkdirs();

        // �õ�zip�ļ���Ŀö�ٶ���
        Enumeration zipEnum = zipFile.entries();
        // �����������������
        InputStream input = null;
        OutputStream output = null;
        // ѭ����ȡ��Ŀ
        System.out
                .println("name\t\t\tsize\t\t\tcompressedSize\t\t\tisDirectory");
        while (zipEnum.hasMoreElements()) {
            // �õ���ǰ��Ŀ
            ZipEntry entry = (ZipEntry) zipEnum.nextElement();
            String entryName = new String(entry.getName().getBytes("ISO8859_1"));
            System.out.println(entryName + "\t\t\t" + entry.getSize()
                    + "\t\t\t" + entry.getCompressedSize() + "\t\t\t\t\t\t\t"
                    + entry.isDirectory());
            String zipEntryName = entry.getName();
            InputStream in = zipFile.getInputStream(entry);
            String outPath = (unzipFile.getPath()+"/"+zipEntryName).replaceAll("\\*", "/");
            //�ж�·���Ƿ����,�������򴴽��ļ�·��
            File tf = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if(!tf.exists()){
                tf.mkdirs();
            }
            //�ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
            if(new File(outPath).isDirectory()){
                continue;
            }
            //����ļ�·����Ϣ
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

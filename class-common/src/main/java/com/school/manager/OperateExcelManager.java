package com.school.manager;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.school.util.UtilTool;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.school.util.PageResult;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.base.IBaseManager;
import com.school.manager.inter.IOperateExcelManager;

@Service
public class OperateExcelManager implements IOperateExcelManager {

	/********************** ����Excel�漰���� *****************************/
	/**
	 * ��������
	 * 
	 * @param wb
	 * @param sheet
	 * @param title
	 * @param margeCount
	 */
	private static void createTitle(HSSFWorkbook wb, HSSFSheet sheet,
			String title, short margeCount,Boolean islock) {
		HSSFRow titleRow = sheet.createRow(0); // �к����������Ǵ�0��ʼ
		HSSFCell titleCell = titleRow.createCell((short) 0);
		// ������ʽ
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont f = wb.createFont();
		f.setFontName("����"); // ����
		f.setFontHeight((short) 300); // �ָ�
		style.setFont(f);
		titleCell.setCellStyle(style); // ������ʽ
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ���þ���
		titleCell.setCellValue(title); // ������
		style.setLocked(islock);
		// �ϲ���Ԫ��
		sheet.addMergedRegion(new Region(0, (short) 0, 0, margeCount));

	}

	/**
	 * ������ͨ��
	 * 
	 * @param wb
	 * @param row
	 * @param column
	 * @param cellValue
	 * @param islock
	 *            �Ƿ����
	 */
	private static void createTitleCell(HSSFWorkbook wb, HSSFSheet sheet,
			HSSFRow row, short column, String cellValue, boolean islock) {

		HSSFCell cell = row.createCell(column);
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont f = wb.createFont();
		if (row.getRowNum() == 1) {
			f.setFontName("����");
			f.setFontHeight((short) 260);
			f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // ���ô���
		} else {
			f.setFontName("����");
		}
		//		
		// cellValue.replace(" ", "").length()*5;
		style.setFont(f);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ���þ���
		style.setLocked(islock);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//cell.setCellStyle(style);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(cellValue);
	}

	/**
	 * ����������
	 * 
	 * @param wb
	 * @param row
	 * @param column
	 * @param cellValue
	 */
	private static void createMathCell(HSSFWorkbook wb, HSSFRow row,
			short column, String cellValue, boolean islocked) {
		HSSFCell cell = row.createCell(column);
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont f = wb.createFont();
		f.setFontName("����");
		style.setFont(f);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setLocked(islocked);
		cell.setCellStyle(style);
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(Double.parseDouble(cellValue));
	}

	private int createExpressTitle(HSSFWorkbook wb, HSSFSheet sheet,
			List<String> columns, int rowCount) {
		HSSFRow row = sheet.createRow(rowCount++);
		HSSFRow row2 = sheet.createRow(rowCount++);
		for (int i = 0; i < columns.size(); i++) {
			HSSFCell cel = row.createCell(i);
			HSSFCell cel1 = row2.createCell(i);
			HSSFCellStyle style = wb.createCellStyle();
			HSSFFont f = wb.createFont();
			if (row.getRowNum() == 1) {
				f.setFontName("����");
				f.setFontHeight((short) 260);
				f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // ���ô���
			} else {
				f.setFontName("����");
			}
			style.setFont(f);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ���þ���
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// style.setVerticalAlignment(HSSFCellStyle.
			style.setLocked(true);
			cel.setCellStyle(style);

			cel1.setCellStyle(style);
			cel.setCellValue(columns.get(i));
			if (columns.get(i).indexOf(">") != -1)
				cel1.setCellValue(columns.get(i).substring(
						columns.get(i).indexOf(">") + 1,
						columns.get(i).lastIndexOf(">")));

		}
		Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row
				.cellIterator();
		String eqsubName = "";
		while (cellIterator.hasNext()) {
			org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
			String va = cell.getStringCellValue().trim();
			if (va.indexOf(">") != -1) {
				String vas = va.substring(va.lastIndexOf(">") + 1);
				String col = va.substring(va.indexOf(">") + 1,
						va.lastIndexOf(">")).trim();
				String subName = va.substring(0, va.indexOf(">"));
				if (!eqsubName.equals(subName)) {
					eqsubName = subName;
					int cindex = cell.getColumnIndex()
							+ Integer.parseInt(vas.trim()) - 1;
					sheet.addMergedRegion(new org.apache.poi.ss.util.Region(1,
							(short) cell.getColumnIndex(), 1, (short) cindex));
					cell.setCellValue(eqsubName);
					continue;
				}
			} else {
				sheet.addMergedRegion(new org.apache.poi.ss.util.Region(1,
						(short) cell.getColumnIndex(), 2, (short) cell
								.getColumnIndex()));
			}
		}

		// sheet.addMergedRegion(new org.apache.poi.ss.util.Region(1, (short)1,
		// 2, (short)1));
		return rowCount;
	}

	/**
	 * �������� *
	 * 
	 * @param filename
	 *            �����ļ�������
	 * @param sheetNames
	 *            ÿ��Sheet������
	 * @param columns
	 *            ÿ��Sheet�������
	 * @param list
	 *            ����ֵ
	 * @param title
	 *            ����
	 * @param clz
	 *            ����������
	 * @param explortObject
	 *            �����ĳɼ�����
	 * @throws IOException
	 */
	private void saveExcelByPOI(HttpServletResponse response, String filename,
			List<String> sheetNames, List<List<String>> columns, List list,
			List<String> title, List<Class<? extends Object>> clz,
			List<String> explortObject,boolean ispizhu) throws IOException {
		// filename = "explortfile";
       filename= new String(filename.getBytes("gbk"), "ISO8859-1");
		response.addHeader("Content-Disposition", "attachment;filename="
                 + filename+".xls");
		OutputStream out = response.getOutputStream();
		HSSFWorkbook wb = new HSSFWorkbook(); // ����������
		// ѭ����
		for (int i = 0; i < sheetNames.size(); i++) {
			// HSSFSheet sheet = wb.createSheet();
			HSSFSheet sheet = wb.createSheet(sheetNames.get(i)); // �������̸�����
			// HSSFSheet sheet = wb.createSheet(""); // �������̸�����
			sheet.setDefaultColumnWidth((short) 15); // ����ÿ��Ĭ�Ͽ��
			int columnSize = columns.get(i).size() - 1;
			if(explortObject!=null&&explortObject.get(i)!=null){
				if (explortObject.get(i).equals("moduleOne")
						|| explortObject.get(i).equals("moduleOneDengDi")
						|| explortObject.get(i).equals("termModuleDengdi")) {
					columnSize++;
				} else if (explortObject.get(i).equals("termModule")) {
					columnSize = (columns.size() - 2) * 3 + 2;
				}
			}
			createTitle(wb, sheet, title.get(i), (short) columnSize,true); // ��������
			int rowsCount = 1;
			String subjectName = "";
			int rowBegin = 2;
			if (explortObject!=null&&explortObject.get(i)!=null&&explortObject.get(i).equals("statistics")) {
				rowsCount = this.createExpressTitle(wb, sheet, columns.get(i),
						rowsCount);
				rowBegin = 3;
			} else if (explortObject!=null&&explortObject.get(i)!=null&&(explortObject.get(i).equals("moduleOne")
					|| explortObject.get(i).equals("moduleTwo"))) {
				int moduleNum = explortObject.get(i).equals("moduleOne") ? 1
						: 1;
				rowsCount = this.createExpressForModuleTitle(wb, sheet, columns
						.get(i), rowsCount, moduleNum);
			} else if (explortObject!=null&&explortObject.get(i)!=null&&(explortObject.get(i).equals("moduleOneDengDi")
					|| explortObject.get(i).equals("termModuleDengdi")
					|| explortObject.get(i).equals("moduleTwoDengDi"))) {
				rowsCount = this.createExpressForModuleDengdiTitle(wb, sheet,
						columns.get(i), rowsCount);
			} else if (explortObject!=null&&explortObject.get(i)!=null&&explortObject.get(i).equals("termModule")) {
				rowsCount = this.createExpressFoTermTitle(wb, sheet, columns
						.get(i), rowsCount);
			} else {
				int index = 0;
				HSSFRow row1 = sheet.createRow(rowsCount++);
				for (String columnStr : columns.get(i)) {
					createTitleCell(wb, sheet, row1, (short) index, columnStr,
							true);
					index++;
				}
			}
			//sheet.protectSheet("http://www.etiantian.com");
			abstractEntityFactory(list.get(i), sheet, wb, columns.get(i), clz
					.get(i), rowBegin);

            //�̶��ļ���λ��
            if(ispizhu){
                HSSFCell cell=sheet.getRow(0).getCell(0);
                //���뵥Ԫ������
                //������ͼ����
                HSSFPatriarch p=sheet.createDrawingPatriarch();
                //��ȡ��ע����
                //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
                //ǰ�ĸ������������,���ĸ������Ǳ༭����ʾ��עʱ�Ĵ�С.
                HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)5,6,(short)8,12));
                //������ע��Ϣ
                comment.setString(new HSSFRichTextString(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI1").toString() ));
                cell.setCellComment(comment);
                /******�ڶ�����ע*********/
                cell=sheet.getRow(0).createCell(3);
                HSSFFont f = wb.createFont();
                f.setColor(HSSFColor.BLUE.index);
              //  f.setFontName("����");
                HSSFCellStyle cellStyle=wb.createCellStyle();
                cellStyle.setFont(f);
                cell.setCellStyle(cellStyle);
                //������ע��Ϣ
                cell.setCellValue(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI2").toString());
                        //new String(("����������ϵͳ�н��ð༶���ٵ���ѧ����\n��������ʱ���Զ���ոð༶ԭ�е�ѧ�����ٵ��������ѧ����").getBytes("ISO8859-1"),"GBK"));

                /*************��������ע***************/
                comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)5,6,(short)8,12));
                cell=sheet.getRow(1).getCell(0);
                //������ע��Ϣ
                comment.setString(new HSSFRichTextString(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI3").toString()));
                /*("�ٱ���\n" +
                        "�ڲ����ظ������������ֻ��ַ�\n" +
                        "���������ѧ���������ͬʱ��ѧ�������˻����û���Ϊѧ�ţ�����Ĭ��Ϊ111111��"
                )*/
                cell.setCellComment(comment);
                /*************���ĸ���ע***************/
                comment=p.createComment(new HSSFClientAnchor(0, 0, 0, 0,(short)5,6,(short)8,12));
                cell=sheet.getRow(1).getCell(1);
                //������ע��Ϣ
                comment.setString(new HSSFRichTextString(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI4").toString()));
                cell.setCellComment(comment);
                /*************�������ע***************/
                comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)5,6,(short)8,12));
                cell=sheet.getRow(1).getCell(2);
                //������ע��Ϣ
                comment.setString(new HSSFRichTextString(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI5").toString()));
                cell.setCellComment(comment);
            }

		}

		try {
			wb.write(out);		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			out.close();
			out.flush();
		}
	}
    public static String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (c >= 0 && c <= 255){sb.append(c);}
            else{
                byte[] b;
                try { b = Character.toString(c).getBytes("utf-8");}
                catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

	/**
	 * �������� *
	 * 
	 * @param columns
	 *            �����ļ���
	 * @throws IOException
	 */

	private void saveExcelByPOI(HttpServletResponse response, String filename,
			List<String> columns, List list, String title, Class clz,
			String explortObject,boolean ispizhu) throws IOException {
		// filename = "explortfile";

		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String((filename + ".xls").getBytes("GBK"), "ISO8859-1"));
		OutputStream out = response.getOutputStream();
		HSSFWorkbook wb = new HSSFWorkbook(); // ����������
		HSSFSheet sheet = wb.createSheet();
		// HSSFSheet sheet = wb.createSheet(""); // �������̸�����
		sheet.setDefaultColumnWidth((short) 15); // ����ÿ��Ĭ�Ͽ��
		int columnSize = 0;
		if (columns == null || columns.size() < 1) {
			columnSize = ((List) list.get(0)).size();
		} else {
			columnSize = columns.size() - 1;
			if (explortObject != null) {
				if (explortObject.equals("moduleOne")
						|| explortObject.equals("moduleOneDengDi")
						|| explortObject.equals("termModuleDengdi")) {
					columnSize++;
				} else if (explortObject.equals("termModule")) {
					columnSize = (columns.size() - 2) * 3 + 2;
				}
			}
		}
		createTitle(wb, sheet, title, (short) columnSize,true); // ��������
		int rowsCount = 1;
		String subjectName = "";
		int rowBegin = 2;
		if (columns != null && columns.size() > 0) {
			if (explortObject != null && explortObject.equals("statistics")) {
				rowsCount = this.createExpressTitle(wb, sheet, columns,
						rowsCount);
				rowBegin = 3;
			} else if (explortObject != null
					&& explortObject.equals("moduleOne")
					|| explortObject != null
					&& explortObject.equals("moduleTwo")) {
				int moduleNum = explortObject.equals("moduleOne") ? 1 : 1;
				rowsCount = this.createExpressForModuleTitle(wb, sheet,
						columns, rowsCount, moduleNum);
			} else if (explortObject != null
					&& explortObject.equals("moduleOneDengDi")
					|| explortObject != null
					&& explortObject.equals("termModuleDengdi")
					|| explortObject != null
					&& explortObject.equals("moduleTwoDengDi")) {
				rowsCount = this.createExpressForModuleDengdiTitle(wb, sheet,
						columns, rowsCount);
			} else if (explortObject != null
					&& explortObject.equals("termModule")) {
				rowsCount = this.createExpressFoTermTitle(wb, sheet, columns,
						rowsCount);
			} else {
				int index = 0;
				HSSFRow row1 = sheet.createRow(rowsCount++);
				for (String columnStr : columns) {
					createTitleCell(wb, sheet, row1, (short) index, columnStr,
							true);
					index++;
				}
				sheet.protectSheet(new String("http://www.etiantian.com"));
			}
		}
		abstractEntityFactory(list, sheet, wb, columns, clz, rowBegin);

        //�̶��ļ���λ��
        if(ispizhu){
            HSSFCell cell=sheet.getRow(0).getCell(0);
            //���뵥Ԫ������
            //������ͼ����
            HSSFPatriarch p=sheet.createDrawingPatriarch();
            //��ȡ��ע����
            //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
            //ǰ�ĸ������������,���ĸ������Ǳ༭����ʾ��עʱ�Ĵ�С.
            HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)5,6,(short)8,12));
            //������ע��Ϣ
            comment.setString(new HSSFRichTextString(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI1").toString() ));
            cell.setCellComment(comment);
            /******�ڶ�����ע*********/
            cell=sheet.getRow(0).createCell(3);
            HSSFFont f = wb.createFont();
            f.setColor(HSSFColor.GREEN.index);
            f.setFontName("����");
            HSSFCellStyle cellStyle=wb.createCellStyle();
            cellStyle.setFont(f);
            cell.setCellStyle(cellStyle);
            //������ע��Ϣ
            cell.setCellValue(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI2").toString());
            //new String(("����������ϵͳ�н��ð༶���ٵ���ѧ����\n��������ʱ���Զ���ոð༶ԭ�е�ѧ�����ٵ��������ѧ����").getBytes("ISO8859-1"),"GBK"));

            /*************��������ע***************/
            comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)5,6,(short)8,12));
            cell=sheet.getRow(1).getCell(0);
            //������ע��Ϣ
            comment.setString(new HSSFRichTextString(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI3").toString()));
                /*("�ٱ���\n" +
                        "�ڲ����ظ������������ֻ��ַ�\n" +
                        "���������ѧ���������ͬʱ��ѧ�������˻����û���Ϊѧ�ţ�����Ĭ��Ϊ111111��"
                )*/
            cell.setCellComment(comment);
            /*************���ĸ���ע***************/
            comment=p.createComment(new HSSFClientAnchor(0, 0, 0, 0,(short)5,6,(short)8,12));
            cell=sheet.getRow(1).getCell(1);
            //������ע��Ϣ
            comment.setString(new HSSFRichTextString(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI4").toString()));
            cell.setCellComment(comment);
            /*************�������ע***************/
            comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)5,6,(short)8,12));
            cell=sheet.getRow(1).getCell(2);
            //������ע��Ϣ
            comment.setString(new HSSFRichTextString(UtilTool.utilproperty.getProperty("EXCEL_WRITE_TISHI5").toString()));
            cell.setCellComment(comment);
        }
		try {
			wb.write(out);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			out.close();
			out.flush();
		}
	}

	private int createExpressForModuleDengdiTitle(HSSFWorkbook wb,
			HSSFSheet sheet, List<String> columns, int rowsCount) {
		// TODO Auto-generated method stub
		HSSFRow row = sheet.createRow(rowsCount++);
		HSSFCellStyle style = null;

		for (int i = 0; i < columns.size(); i++) {
			if (i == 0) {
				HSSFCell cel = row.createCell(0);
				cel.setCellValue(columns.get(i).trim());
			}
			HSSFCell cel1 = row.createCell(i + 1);
			style = wb.createCellStyle();
			HSSFFont f = wb.createFont();
			if (row.getRowNum() == 1) {
				f.setFontName("����");
				f.setFontHeight((short) 260);
				f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // ���ô���
			} else {
				f.setFontName("����");
			}
			style.setFont(f);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ���þ���
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setLocked(false);
			cel1.setCellStyle(style);
			cel1.setCellValue(columns.get(i).trim());
			if (i == 0) {
				row.getCell(i).setCellStyle(style);
				sheet.addMergedRegion(new org.apache.poi.ss.util.Region(1,
						(short) i, 1, (short) (i + 1)));
				cel1.setCellValue(columns.get(0).trim());
			}

		}
		//
		int cel = sheet.getRow(1).getLastCellNum();
		return rowsCount;
	}

	/**
	 * ����ģ��1 �꼶���༶ͷ����Ϣ
	 * 
	 * @param wb
	 * @param sheet
	 * @param columns
	 * @param rowsCount
	 * @param modelNum
	 *            1:ģ��1 2:ģ��2
	 * @return
	 */
	private int createExpressFoTermTitle(HSSFWorkbook wb, HSSFSheet sheet,
			List<String> columns, int rowsCount) {
		HSSFRow row = sheet.createRow(rowsCount++);
		HSSFRow row2 = sheet.createRow(rowsCount++);
		HSSFCellStyle style = null;
		int colsIndex = 2;
		for (int i = 0; i < columns.size(); i++) {
			HSSFCell cel = null, cel1 = null, cel2 = null;
			if (i >= 2) {
				cel = row.createCell(colsIndex);
				cel1 = row2.createCell(colsIndex + 1);
				cel2 = row2.createCell(colsIndex + 2);
			} else {
				cel = row.createCell(i);
				cel1 = row2.createCell(i + 1);
				cel2 = row2.createCell(i + 2);
			}
			style = wb.createCellStyle();
			HSSFFont f = wb.createFont();
			if (row.getRowNum() == 1) {
				f.setFontName("����");
				f.setFontHeight((short) 260);
				f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // ���ô���
			} else {
				f.setFontName("����");
			}
			style.setFont(f);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ���þ���
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// style.setVerticalAlignment(HSSFCellStyle.
			style.setLocked(false);
			cel.setCellStyle(style);
			cel1.setCellStyle(style);
			cel2.setCellStyle(style);
			if (i < 2) {
				sheet.addMergedRegion(new org.apache.poi.ss.util.Region(1,
						(short) i, 2, (short) i));
			} else {
				sheet.addMergedRegion(new org.apache.poi.ss.util.Region(1,
						(short) colsIndex, 1, (short) (colsIndex + 2)));
				colsIndex += 3;
			}
			cel.setCellValue(columns.get(i).trim());
		}

		for (int i = 0; i < colsIndex;) {
			if (i > 1) {
				if (row2.getCell(i) == null)
					row2.createCell(i);
				row2.getCell(i).setCellValue("ģ��1");
				row2.getCell(i).setCellStyle(style);
				if (row2.getCell(i + 1) == null)
					row2.createCell(i + 1);
				row2.getCell(i + 1).setCellValue("ģ��2");
				row2.getCell(i + 1).setCellStyle(style);
				if (row2.getCell(i + 2) == null)
					row2.createCell(i + 2);
				row2.getCell(i + 2).setCellValue("ѧ��");
				row2.getCell(i + 2).setCellStyle(style);
				i += 3;
			} else {
				i++;
			}
		}
		return rowsCount;
	}

	/**
	 * ����ģ���꼶���༶ͷ����Ϣ
	 * 
	 * @param wb
	 * @param sheet
	 * @param columns
	 * @param rowsCount
	 * @param modelNum
	 *            1:ģ��1 2:ģ��2
	 * @return
	 */
	private int createExpressForModuleTitle(HSSFWorkbook wb, HSSFSheet sheet,
			List<String> columns, int rowsCount, int modelNum) {
		HSSFRow row = sheet.createRow(rowsCount++);
		// HSSFRow row2 = sheet.createRow(rowsCount++);
		HSSFCellStyle style = null;
		int colsIndex = 2;
		for (int i = 0; i < columns.size(); i++) {
			HSSFCell cel = null;// , cel1 = null;
			// if (i >= 2) {
			// cel = row.createCell(colsIndex);
			// // cel1 = row2.createCell(colsIndex + 1);
			// } else {
			cel = row.createCell(i);
			// cel1 = row2.createCell(i + 1);
			// }
			style = wb.createCellStyle();
			HSSFFont f = wb.createFont();
			if (row.getRowNum() == 1) {
				f.setFontName("����");
				f.setFontHeight((short) 260);
				f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // ���ô���
			} else {
				f.setFontName("����");
			}
			style.setFont(f);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ���þ���
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// style.setVerticalAlignment(HSSFCellStyle.
			style.setLocked(false);
			cel.setCellStyle(style);
			// cel1.setCellStyle(style);

			// sheet.addMergedRegion(new org.apache.poi.ss.util.Region(1,
			// (short) 1, 1, (short) (colsIndex + 1)));
			colsIndex += 1;
			cel.setCellValue(columns.get(i).trim());
		}

		// for (int i = 0; i < colsIndex; i += 2) {
		// if (i > 1) {
		// if (row2.getCell(i) == null)
		// row2.createCell(i);
		// row2.getCell(i).setCellValue("ģ" + modelNum + "ƽʱ");
		// row2.getCell(i).setCellStyle(style);
		// if (row2.getCell(i + 1) == null)
		// row2.createCell(i + 1);
		// row2.createCell(i + 1);
		// row2.getCell(i + 1).setCellValue("ģ��" + modelNum);
		// row2.getCell(i + 1).setCellStyle(style);
		// }
		// }
		return rowsCount;
	}

	private void abstractEntityFactory(Object obj, HSSFSheet sheet,
			HSSFWorkbook wb, List<String> columns, Class clz, int rowBegin) {
		List list = (List) obj;
		createTempDate(list, sheet, wb, columns, rowBegin);
	}

	/**
	 * ��ͨ���ݼ���
	 * 
	 * @param obj
	 * @param sheet
	 * @param wb
	 * @param columns
	 * @param rowBegin
	 */
	private void createTempDate(List obj, HSSFSheet sheet, HSSFWorkbook wb,
			List<String> columns, int rowBegin) {
		int j = rowBegin;
		HSSFRow row;
		List<List<String>> emtemp = (List<List<String>>) obj;
		if (emtemp != null && emtemp.size() > 0) {
			int emtemplength = emtemp.size();
			for (int i = 0; i < emtemplength; i++) {
				if (emtemp.get(i) != null && emtemp.get(i).size() > 0) {
					row = sheet.createRow(j++); // ������
					int tmpSize = emtemp.get(i).size();
					for (int z = 0; z < tmpSize; z++) {
						createTitleCell(wb, sheet, row, (short) z, emtemp
								.get(i).get(z), false);
					}
				}
			}
		}

	}

	private void createStatisticsModuleDengdi(List list, HSSFSheet sheet,
			HSSFWorkbook wb, List<String> columns, int rowbegin) {
		// TODO Auto-generated method stub
		int j = rowbegin;
		HSSFRow row;
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(j++);
			List<String> strList = (List<String>) list.get(i);
			String parentTitle = "";
			for (int k = 0; k < strList.size(); k++) {
				if (k > 1) {
					if (!strList.get(k).toString().equals("1000000")
							&& Integer.parseInt(strList.get(k).toString()) >= 0) {
						createMathCell(wb, row, (short) k, strList.get(k),
								false);
					} else
						createTitleCell(wb, sheet, row, (short) k, "ȱ��", false);
				} else
					createTitleCell(wb, sheet, row, (short) k, strList.get(k),
							false);
			}
		}
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(2, (short) 0,
				(1 + 5), (short) 0));
		sheet.addMergedRegion(new org.apache.poi.ss.util.Region(7, (short) 0,
				(11), (short) 0));
		if (list.size() > 10) {
			sheet.addMergedRegion(new org.apache.poi.ss.util.Region(12,
					(short) 0, (16), (short) 0));
			sheet.addMergedRegion(new org.apache.poi.ss.util.Region(17,
					(short) 0, (21), (short) 0));
			sheet.addMergedRegion(new org.apache.poi.ss.util.Region(22,
					(short) 0, (26), (short) 0));
			sheet.addMergedRegion(new org.apache.poi.ss.util.Region(27,
					(short) 0, (31), (short) 0));
		}

	}

	private void createStatisticsTitle(List list, HSSFSheet sheet,
			HSSFWorkbook wb, List<String> columns, int rowBegin) {
		int j = rowBegin;
		HSSFRow row = null;
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(j);
			List lst = (List) list.get(i);
			for (int k = 0; k < lst.size(); k++) {
				if (k != 1) {
					if (k == 0) {
						createTitleCell(wb, sheet, row, (short) k, lst.get(k)
								.toString(), true);
					} else if (k < 5 && k != 0) {
						createTitleCell(wb, sheet, row, (short) (k - 1), lst
								.get(k) == null ? "" : lst.get(k).toString(),
								true);
					} else {
						if (Double.parseDouble(lst.get(k).toString()) < 0) {
							createTitleCell(wb, sheet, row, (short) (k - 1),
									"ȱ��", true);
						} else if (Double.parseDouble(lst.get(k).toString()) > 10000) {

						} else {
							createMathCell(wb, row, (short) (k - 1), lst.get(k)
									.toString(), true);
						}
					}
				}
			}
			j++;
		}
	}

	private void createStatisticsModuleOneTitle(List list, HSSFSheet sheet,
			HSSFWorkbook wb, List<String> columns, int rowBegin) {
		int j = rowBegin;
		HSSFRow row = null;
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(j);
			List lst = (List) list.get(i);
			for (int k = 0; k < lst.size(); k++) {
				if (lst.get(k) == null) {
					lst.set(k, "");
				}
				if (k < 4) {
					createTitleCell(wb, sheet, row, (short) k, lst.get(k)
							.toString(), true);
				} else {
					if (Double.parseDouble(lst.get(k).toString()) < 0) {
						createTitleCell(wb, sheet, row, (short) k, "ȱ��", true);
					} else if (Double.parseDouble(lst.get(k).toString()) > 10000) {

					} else {
						createMathCell(wb, row, (short) k, lst.get(k)
								.toString(), true);
					}
				}
			}
			j++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.school.manager.IOperateExcelManager#ExplortExcel(javax.servlet.http
	 * .HttpServletResponse, java.lang.String, java.util.List, java.util.List,
	 * java.lang.String, java.lang.Class, java.lang.String)
	 */
	public String ExplortExcel(HttpServletResponse response, String filename,
			List<String> columns, List list, String title, Class clz,
			String explortObject) throws Exception {
		if (filename == null || filename == "") {
			filename = "explortFile";
		}
		try {
			this.saveExcelByPOI(response, filename, columns, list, title, clz,
					explortObject,true);
		} catch (IOException e) {
			return "����ʧ��!";
		}
		// ServletActionContext.getResponse().sendRedirect("user!select.action");
		return null;
	}
    public String ExplortExcel(HttpServletResponse response, String filename,
                              List<String> columns, List list, String title, Class clz,
                              String explortObject,boolean ispizhu) throws Exception {
        if (filename == null || filename == "") {
            filename = "explortFile";
        }
        try {
            this.saveExcelByPOI(response, filename, columns, list, title, clz,
                    explortObject,ispizhu);
        } catch (IOException e) {
            return "����ʧ��!";
        }
        // ServletActionContext.getResponse().sendRedirect("user!select.action");
        return null;
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.school.manager.IOperateExcelManager#ExplortExcel(javax.servlet.http
	 * .HttpServletResponse, java.lang.String, java.util.List, java.util.List,
	 * java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	public String ExplortExcel(HttpServletResponse response, String filename,
			List<String> sheetNames, List<List<String>> columns, List list,
			List<String> title, List<Class<? extends Object>> clz,
			List<String> explortObject,Boolean ispizhu) throws Exception {
		if (filename == null || filename == "") {
			filename = "explortFile";
		}
		try {
			this.saveExcelByPOI(response, filename, sheetNames, columns, list,
					title, clz, explortObject,ispizhu);
		} catch (IOException e) {
			return "����ʧ��!";
		}
		// ServletActionContext.getResponse().sendRedirect("user!select.action");
		return null;
	}
    public String ExplortExcel(HttpServletResponse response, String filename,
                               List<String> sheetNames, List<List<String>> columns, List list,
                               List<String> title, List<Class<? extends Object>> clz,
                               List<String> explortObject) throws Exception {
       return  ExplortExcel(response,filename,sheetNames,columns,list,title,clz,explortObject,false);
    }

	/******************************** ����Excel �漰���� ************************************/
	/**
	 * ���� Excel���� (δ ���� ���ݿ� )
	 * 
	 * @param filename
	 * @throws Exception
	 */
	private List<Object> ReadMethod(String filepath, IBaseManager basemanager,
			String type) throws Exception {
		List list = new ArrayList();

		// ���ȹ���һ��������
 		InputStream is = new FileInputStream(filepath);
		// jxl��Workbook�õ����������
		jxl.Workbook rwb = Workbook.getWorkbook(is);

		// Workbook�õ���һ��sheet

		Sheet[] sheets = rwb.getSheets();// ��õ�ǰExcel���м���sheet
		int p = sheets.length;

		for (int w = 0; w < p; w++) { // ��ÿ��sheet�е�����ȫ����ȡ����
			List<Object> childList=new ArrayList<Object>(); 
			// �ڴ�Excel�ж�ȡ���ݵ�ʱ���� Ҫ֪��ÿ��sheet�м��У����Ƕ�����
			Sheet rs = rwb.getSheet(w);
			int rows = rs.getRows();
			int d = 1;
			if(type!=null&&type.trim().equals("comparamatriculate"))
				d+=2;		
			for (; d < rows; d++) { // ��ѭ��,Excel�������Ǵӣ�0��0����ʼ��
				if (d != 1 && rs.getRow(d) != null) {				
					Object obj = basemanager.getOfExcel(rs,
							rs.getRow(d).length, d, type);
					if (obj != null)
						childList.add(obj);
				}
			}
			if(childList.size()>0){
				//ѡ�޿αȽ� 
				if(type!=null&&type.trim().equals("comparamatriculate"))
					list.add(childList);		
				else
					list.addAll(childList);
			}
		}
		is.close();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bhsf.oa.manager.impl.IOperateExcelManager#includeExcel(java.lang.
	 * String, com.bhsf.oa.manager.iface.IBaseManager)
	 */
	public List<Object> includeExcel(String fileName, IBaseManager basemanager)
			throws Exception {
		List<Object> objList = ReadMethod(fileName, basemanager, null);
		return objList;
	}

	public List<Object> includeExcel(String fileName, IBaseManager basemanager,
			String type) throws Exception {
		List<Object> objList = ReadMethod(fileName, basemanager, type);
		return objList;
	}

	/******************************************************************************************/

    public static void main(String[] args) throws Exception{
        //��������������
        HSSFWorkbook wb=new HSSFWorkbook();
        //�������������
        HSSFSheet sheet=wb.createSheet("�ҵĹ�����");
        //������ͼ����
        HSSFPatriarch p=sheet.createDrawingPatriarch();
        //������Ԫ�����,��ע���뵽4��,1��,B5��Ԫ��
        HSSFCell cell=sheet.getRow(4).getCell(1);
        //���뵥Ԫ������
        cell.setCellValue(new HSSFRichTextString("��ע"));
        //��ȡ��ע����
        //(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2)
        //ǰ�ĸ������������,���ĸ������Ǳ༭����ʾ��עʱ�Ĵ�С.
        HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
        //������ע��Ϣ
        comment.setString(new HSSFRichTextString("�����ע�ɹ�!�����ע�ɹ�!"));
        //�������,ѡ��B5��Ԫ��,��״̬��
        comment.setAuthor("toad");
        //����ע��ӵ���Ԫ�������
        cell.setCellComment(comment);
        //���������
        FileOutputStream out=new FileOutputStream("E:/writerPostil.xls");

        wb.write(out);
        //�ر�������
        out.close();
    }
}

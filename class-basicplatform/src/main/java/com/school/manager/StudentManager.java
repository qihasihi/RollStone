
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IStudentDAO;
import com.school.util.PageResult;
import com.school.entity.StudentInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.base.IBaseManager;
import com.school.manager.inter.IStudentManager;

@Service
public class  StudentManager extends BaseManager<StudentInfo> implements IStudentManager  {
	private IStudentDAO studentdao ;
	 
	@Autowired
	@Qualifier("studentDAO")
	
	
	public void setStudentdao(IStudentDAO studentdao) {
		this.studentdao = studentdao;
	}

	@Override
	public Boolean doDelete(StudentInfo obj) {
		return this.studentdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(StudentInfo obj) {
		return this.studentdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(StudentInfo obj) {
		return this.studentdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<StudentInfo> getBaseDAO() {
		return studentdao;
	}

	@Override
	public List<StudentInfo> getList(StudentInfo obj, PageResult presult) {
		return this.studentdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.studentdao.getNextId();
	}

	public List<Object> getDeleteSql(StudentInfo obj, StringBuilder sqlbuilder) {
		return this.studentdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(StudentInfo obj, StringBuilder sqlbuilder) { 
		return this.studentdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(StudentInfo obj, StringBuilder sqlbuilder) {
		return this.studentdao.getUpdateSql(obj, sqlbuilder);  
	}

	public StudentInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		String title=rs.getRow(0)[0].getContents();
		StudentInfo stu = new StudentInfo();
		if(title!=null&&title.indexOf("(")!=-1
				&&title.indexOf(")")!=-1){
			String year=title.trim().substring(0,title.trim().indexOf(" "));
			String grade=title.trim().substring(title.trim().indexOf(" ")+1,title.trim().indexOf("("));
			String clName=title.trim().substring(title.trim().indexOf("(")+1,title.trim().indexOf(")"));
			if(year!=null&&!year.trim().equals(""))
				stu.getClassinfo().setYear(year.trim());
			else
				throw new NullPointerException(
				"导入的文件，没有班级信息：年份 年级(班级)  例：2009~2010 高一(3班)");
			if(grade!=null&&!grade.trim().equals(""))
				stu.getClassinfo().setClassgrade(grade.trim());
			else
				throw new NullPointerException(
				"导入的文件，没有班级信息：年份 年级(班级)  例：2009~2010 高一(3班)");
			if(clName!=null&&!clName.trim().equals(""))
				stu.getClassinfo().setClassname(clName.trim());
			else
				throw new NullPointerException(
				"导入的文件，没有班级信息：年份 年级(班级)  例：2009~2010 高一(3班)");
		}else{
			throw new NullPointerException(
					"导入的文件标题格式有误!例:2012~2013 高一(3班)");
		}
		for (int i = 0; i < cols; i++) { // 0:stuNo 1:stuName 2:stuSex
			if (rs.getRow(d) == null || rs.getRow(d).length - 1 < i)
				break;
			String innerText = rs.getRow(d)[i].getContents();
			if (i == 0) {
				if (innerText != null && innerText.trim().length() > 0)
					stu.setStuno(innerText.trim());
			}else if(i==1){
				if (innerText != null && innerText.trim().length() > 0)
					stu.setStuname(innerText.trim());
			} else if (i == 2) {
				if (innerText != null && innerText.trim().length() > 0)
					stu.setStusex(innerText.trim());
				break;
			}
		}
		if (stu.getStuno() == null || stu.getStuno().length() < 1)
			stu = null;
		//stu.setStuNo(stu.getStuName()+stu.getStuNo());
		return stu;
		
	}

	public List<StudentInfo> getStudentByClass(Integer classid, String year,
			String pattern) {
		// TODO Auto-generated method stub
		return this.studentdao.getStudentByClass(classid, year, pattern);
	}

	
}


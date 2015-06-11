package operators;

import java.util.List;

import beans.Student;
import cn.edu.fudan.se.dac.Condition;
import cn.edu.fudan.se.dac.DACFactory;
import cn.edu.fudan.se.dac.DataAccessInterface;

public class StudentOperator {
	private static DataAccessInterface<Student> studentDAC = DACFactory.getInstance().createDAC(Student.class);
	public static final int STUDENT_ADD_COMMIT_ERROR = -2;
	public static final int STUDENT_ADD_SUCCESS = 1;
	public static final int STUDENT_EXISTS_ERROR = -1;
	{
		System.out.println("Initializing Student Operator...");
		studentDAC.beginTransaction();
		System.out.println("OK.");
	}
	public int addStudentInfo(String studentId, String name, String gender, String schoolName){
		Student student = new Student(studentId, name, gender, schoolName);
		final String tempId = studentId;
		List<Student> checkList = studentDAC.selectByCondition(new Condition<Student>(){
			public boolean assertBean(Student arg0) {
				return arg0.getStudentId().equals(tempId);
			}	
		});
		if (!checkList.isEmpty()) return STUDENT_EXISTS_ERROR;
		studentDAC.add(student);
		studentDAC.commit();
		studentDAC.beginTransaction();
		return STUDENT_ADD_SUCCESS;
		
		
		
	}
}

package operators;

import java.util.ArrayList;
import java.util.List;

import beans.*;
import cn.edu.fudan.se.dac.*;

public class StudentCourseOperator {
	public static final int SELECT_COURSE_EXISTS_ERROR = -1;
	public static final int SELECT_COURSE_CONFLICT_ERROR = -2;
	//private static final int SELECT_COURSE_CREDIT_EXCEEDED_ERROR = -3;
	public static final int COURSE_COMMIT_ERROR = -4;
	public static final int SELECT_COURSE_SUCCESS = 1;
	public static final int NO_SCHEDULE = -5;
	public static final int NO_SUCH_CLASS_IN_SCHEDULE = -6;
	public static final int DROP_COURSE_SUCCESS = 2;
	public static final int NO_SUCH_CLASS_ERROR = -7;
	
	private static DataAccessInterface<Student> studentDAC = DACFactory.getInstance().createDAC(Student.class);
	private static DataAccessInterface<Course> courseDAC = DACFactory.getInstance().createDAC(Course.class);
	private static DataAccessInterface<StudentCourse> scDAC = DACFactory.getInstance().createDAC(StudentCourse.class);
	private static DataAccessInterface<School> schoolDAC = DACFactory.getInstance().createDAC(School.class);
	
	{
		System.out.println("Initializing StudentCourse Operator...");
		studentDAC.beginTransaction();
		courseDAC.beginTransaction();
		scDAC.beginTransaction();
		schoolDAC.beginTransaction();
		System.out.println("OK.");
	}
	public int selectCourse(String courseId,String studentId){
		final String cId = courseId,sId = studentId;
		List<StudentCourse> checkExists = scDAC.selectByCondition(new Condition<StudentCourse>(){

			@Override
			public boolean assertBean(StudentCourse arg0) {
				return arg0.getCourseId().equals(cId) && arg0.getStudentId().equals(sId);
			}
			
		});
		if (!checkExists.isEmpty()) return SELECT_COURSE_EXISTS_ERROR;
		List<Student> stu = studentDAC.selectByCondition(new Condition<Student>(){
			@Override
			public boolean assertBean(Student arg0) {
				//System.out.println(arg0.getStudentId());
				//System.out.println(sId);
				return arg0.getStudentId().equals(sId);
			}		
		});
		Student student = stu.get(0); final String stuSchool = student.getSchoolName();
		List<Course> cou = courseDAC.selectByCondition(new Condition<Course>(){

			@Override
			public boolean assertBean(Course arg0) {
				return arg0.getCourseId().equals(cId);
			}
			
		});
		if (cou.isEmpty()) return NO_SUCH_CLASS_ERROR;
		Course course = cou.get(0);
		
		// If we don't need to check credit exceeding then the following lines are not needed.
		List<School> sch = schoolDAC.selectByCondition(new Condition<School>(){

			@Override
			public boolean assertBean(School arg0) {
				return arg0.getSchoolName().equals(stuSchool);
			}
				
		});
		// School school = sch.get(0);
		
		// Ending this part.
		
		// Now we check whether there are time conflicts.
		Time courseTime = course.getTime();
		
		List<StudentCourse> checkConflicts = scDAC.selectByCondition(new Condition<StudentCourse>(){

			@Override
			public boolean assertBean(StudentCourse arg0) {
				return arg0.getStudentId().equals(sId);
			}
			
		});
		for (StudentCourse sc:checkConflicts){
			final String tempcid = sc.getCourseId();
			//System.out.println(tempcid);
			for (Course c:courseDAC.selectByCondition(new Condition<Course>(){
				@Override
				public boolean assertBean(Course arg0) {
					return arg0.getCourseId().equals(tempcid);
				}
			
			})){
				//System.out.println("C:"+c.getTime().getWeekday()+" "+c.getTime().getPeriod());
				//System.out.println("courseTime:"+courseTime.getWeekday()+" "+courseTime.getPeriod());
				if (c.getTime().same(courseTime)) {
					//System.out.println("2333333");

					return SELECT_COURSE_CONFLICT_ERROR;
				}
			}
		}
		// Checking time conflicts finished.

		scDAC.add(new StudentCourse(sId,cId));
		scDAC.commit();
		
		scDAC.beginTransaction();
		return SELECT_COURSE_SUCCESS;
	
				
	
		
		
		
	
		
	
		
	}
	
	public List<Course> querySchedule(String studentId){
		final String sId = studentId;
		List<StudentCourse> result = scDAC.selectByCondition(new Condition<StudentCourse>(){

			@Override
			public boolean assertBean(StudentCourse arg0) {
				// TODO Auto-generated method stub
				return arg0.getStudentId().equals(sId);
			}
			
		});
		if (result.isEmpty()) return null;
		else {
			List<Course> courseList = new ArrayList<Course>();
			for (StudentCourse sc:result){
				final StudentCourse tempsc = sc;
				courseList.addAll(courseDAC.selectByCondition(new Condition<Course>(){

					@Override
					public boolean assertBean(Course arg0) {
						// TODO Auto-generated method stub
						return arg0.getCourseId().equals(tempsc.getCourseId());
					}
					
				}));
			}
		    return courseList;
		}
		
		
	}
	
	public int dropCourse(String courseId, String studentId){
		final String cId = courseId,sId = studentId;
		List<StudentCourse> result = scDAC.selectByCondition(new Condition<StudentCourse>(){

			@Override
			public boolean assertBean(StudentCourse arg0) {
				return arg0.getCourseId().equals(cId) && arg0.getStudentId().equals(sId);
			}
			
		});
		if (result.isEmpty()) return NO_SUCH_CLASS_IN_SCHEDULE;
		else {
			scDAC.deleteByCondition(new Condition<StudentCourse>(){

				@Override
				public boolean assertBean(StudentCourse arg0) {
					// TODO Auto-generated method stub
					return arg0.getCourseId().equals(cId) && arg0.getStudentId().equals(sId);
				}
				
			});
			scDAC.commit();
			scDAC.beginTransaction();
			return DROP_COURSE_SUCCESS;
		}
	}

	public void getAll() {
		System.out.println("Get contents of StudentCourse:");
		for (StudentCourse sc:scDAC.selectByCondition(new Condition.True<StudentCourse>())){
			System.out.println("StudentId:"+sc.getStudentId()+" CourseId:"+sc.getCourseId());
		}
		
	}

	public void clear() {
		scDAC.deleteByCondition(new Condition.True<StudentCourse>());
		scDAC.commit();
		scDAC.beginTransaction();
		
	}
}

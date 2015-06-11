package operators;
import java.util.List;

import beans.*;
import cn.edu.fudan.se.dac.*;

public class CourseOperator {
	private static DataAccessInterface<Course> courseDAC = DACFactory.getInstance().createDAC(Course.class);
	private static DataAccessInterface<CourseTime> coursetimeDAC = DACFactory.getInstance().createDAC(CourseTime.class);
	
	public static final int COURSE_EXISTS_ERROR = -1;
	public static final int COURSE_ADD_COMMIT_ERROR = -2;
	public static final int COURSE_ADD_SUCCESS = 1;
	{
		System.out.println("Initializing Course Operator...");
		courseDAC.beginTransaction();
		coursetimeDAC.beginTransaction();
		System.out.println("OK.");
	}
	public int addCourseInfo(String courseId, String schoolName, String courseName, 
			String teacherName, int credit, String location, Time time){
		Course newCourse = new Course(courseId, schoolName, courseName, teacherName, credit, location, time);
		final String tempId = courseId;
		List<Course> checkList = courseDAC.selectByCondition(new Condition<Course>(){
			public boolean assertBean(Course arg0) {
				return arg0.getCourseId().equals(tempId);
			}	
		});
		if (!checkList.isEmpty()) return COURSE_EXISTS_ERROR;
		courseDAC.add(newCourse);
		coursetimeDAC.add(new CourseTime(courseId,time));
		courseDAC.commit();
		coursetimeDAC.commit();
		courseDAC.beginTransaction();
		coursetimeDAC.beginTransaction();
		return COURSE_ADD_SUCCESS;
		
		
		
	}
	public int addCourseInfo(Course course){
		final String tempId = course.getCourseId();
		List<Course> checkList = courseDAC.selectByCondition(new Condition<Course>(){
			public boolean assertBean(Course arg0) {
				return arg0.getCourseId().equals(tempId);
			}	
		});
		if (!checkList.isEmpty()) return COURSE_EXISTS_ERROR;
		courseDAC.add(course);
		courseDAC.commit();
		courseDAC.beginTransaction();
		return COURSE_ADD_SUCCESS;
		
	}
	
	
	public List<Course> QueryCourseByTime(Time time){
		final Time tempTime = time;
		List<Course> resultSet = courseDAC.selectByCondition(new Condition<Course>(){

			@Override
			public boolean assertBean(Course arg0) {
				// TODO Auto-generated method stub
				return arg0.getTime().equals(tempTime);
			}
			
		});
		return resultSet;
	}
	public List<Course> QueryCourseById(String courseId){
		final String tempId = courseId;
		List<Course> resultSet = courseDAC.selectByCondition(new Condition<Course>(){

			@Override
			public boolean assertBean(Course arg0) {
				// TODO Auto-generated method stub
				return arg0.getCourseId().equals(tempId);
			}
			
		});
		return resultSet;
	}
	public void getAll(){
		System.out.println("Get contents of Course:");
		for (Course c:courseDAC.selectByCondition(new Condition.True<Course>())){
			System.out.println("CourseId:"+c.getCourseId());
		}
	}
	public void clear() {
		courseDAC.deleteByCondition(new Condition.True<Course>());
		courseDAC.commit();
		courseDAC.beginTransaction();
		
	}
	
}

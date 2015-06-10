package beans;

public class StudentCourse {
	private String studentId;
	private String courseId;
	
	public StudentCourse(){
		
	}
	
	public StudentCourse(String studentId, String courseId){
		this.studentId = studentId;
		this.courseId = courseId;
	}
	
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseId() {
		return courseId;
	}
	
}

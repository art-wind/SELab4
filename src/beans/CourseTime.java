package beans;

public class CourseTime {
	private String courseId;
	private Time time;
	
	public CourseTime(){
		
	}
	
	public CourseTime(String courseId,Time time){
		this.time = time;
		this.courseId = courseId;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	public Time getTime() {
		return this.time;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseId() {
		return courseId;
	}
}

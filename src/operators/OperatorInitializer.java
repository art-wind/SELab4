package operators;

import beans.Time;

public class OperatorInitializer {

	public static void main(String[] args) {
		CourseOperator co = new CourseOperator();
		SchoolOperator so = new SchoolOperator();
		StudentCourseOperator sco = new StudentCourseOperator();
		StudentOperator sto = new StudentOperator();
		
		so.clear();
		co.clear();
		sco.clear();
		so.getAll();
		co.getAll();
		sco.getAll();
		so.addSchoolInfo("XinDongFang", 233);
		so.getAll();
		so.addSchoolInfo("LanXiang", 666);
		sto.addStudentInfo("SK001", "ASS", "Male", "XinDongFang");
		so.getAll();
		
		co.addCourseInfo("XDF236", "XinDongFang", "Fighting", "Tang", 3, "Kitchen", new Time(4,3));
		co.addCourseInfo("XDF234", "XinDongFang", "Tasting", "Guo", 2, "Kitchen", new Time(4,4));
		co.addCourseInfo("XDF235", "XinDongFang", "Eating", "Ma", 2, "Kitchen", new Time(4,4));
		co.getAll();
		//sto.addStudentInfo("12JIXIAO3001", "Dumb", "Male", "XinDongFang");
		System.out.println(sco.selectCourse("XDF235", "12JIXIAO3001"));
		System.out.println(sco.selectCourse("XDF234", "12JIXIAO3001"));
		System.out.println(sco.selectCourse("XDF236", "12JIXIAO3001"));
		System.out.println(sco.selectCourse("XDF999", "12JIXIAO3001"));
		System.out.println(sco.querySchedule("12JIXIAO3001"));
		System.out.println(sco.dropCourse("XDF235", "12JIXIAO3001"));
		System.out.println(sco.dropCourse("XDF777", "12JIXIAO3001"));
		//sco.getAll();

	}

}

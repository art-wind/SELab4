package responses;

import java.io.Serializable;
import java.util.List;

import beans.Course;
import beans.Student;

import com.alibaba.fastjson.JSON;

public class StudentCoursesResponse implements Serializable {

    public final String id;
    public final String studentId;
    public final List<String> courseIds; 

    public StudentCoursesResponse(String id, String studentId, List<String> courseIds) {
        this.id = id;
        this.studentId = studentId;
        this.courseIds = courseIds;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

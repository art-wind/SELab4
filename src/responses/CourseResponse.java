package responses;

import java.io.Serializable;
import java.util.List;

import beans.Course;

import com.alibaba.fastjson.JSON;

public class CourseResponse implements Serializable {

    public final String id;
    public final List<Course> courses;

    public CourseResponse(String id, List<Course> courses) {
        this.id = id;
        this.courses = courses;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

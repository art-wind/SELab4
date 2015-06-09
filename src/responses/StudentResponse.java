package responses;

import java.io.Serializable;

import util.Student;

import com.alibaba.fastjson.JSON;

public class StudentResponse implements Serializable {

    public final String id;
    public final Student student;

    public StudentResponse(String id, Student student) {
        this.id = id;
        this.student = student;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

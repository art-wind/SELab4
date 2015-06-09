package requests;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class CourseRequest implements Serializable {
	
    public final String id;

    public CourseRequest(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

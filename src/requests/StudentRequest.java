package requests;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class StudentRequest implements Serializable {

    public final String id;

    public StudentRequest(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

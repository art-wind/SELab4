package requests;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class StateRequest implements Serializable {

    public final String id;

    public StateRequest(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

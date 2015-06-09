package responses;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class StateResponse implements Serializable {

    public final String id;
    public final String state;

    public StateResponse(String id, String state) {
        this.id = id;
        this.state = state;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}

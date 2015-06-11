package responders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logics.SelectCourse;
import parameters.SelectCourseParameter;
import responses.StateResponse;
import util.Messager;
import beans.StudentCourse;
import com.alibaba.fastjson.JSON;

public class SelectCourseResponsor extends Messager implements Runnable {
	private int responsorId;

	public SelectCourseResponsor(int responsorId) {
		super(SelectCourseParameter.TOPIC, SelectCourseParameter.RESPONSOR_CONSUMER_GROUP, SelectCourseParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = responsorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		StudentCourse st = JSON.parseObject((String)messageBody, StudentCourse.class);
		String result = SelectCourse.selectCourse(st.getCourseId(), st.getStudentId());
		StateResponse cr = new StateResponse(messageId, result);
		sendMessage(SelectCourseParameter.RESPONSE_TAG, SelectCourseParameter.RESPONSOR_KEY, cr);
		return true;
	}

	@Override
	public void run() {
		start(SelectCourseParameter.REQUEST_TAG);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                if ("stop".equals(reader.readLine())) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stop();
	}

}

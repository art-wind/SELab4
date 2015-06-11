package responders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logics.DropCourse;
import parameters.DropCourseParameter;
import responses.StateResponse;
import util.Messager;
import beans.StudentCourse;
import com.alibaba.fastjson.JSON;

public class DropCourseResponsor extends Messager implements Runnable {
	private int responsorId;

	public DropCourseResponsor(int responsorId) {
		super(DropCourseParameter.TOPIC,
				DropCourseParameter.RESPONSOR_CONSUMER_GROUP, DropCourseParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = responsorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		StudentCourse st = JSON.parseObject((String)messageBody, StudentCourse.class);
		String result = DropCourse.dropCourse(st.getCourseId(), st.getStudentId());
		StateResponse cr = new StateResponse(messageId, result);
		sendMessage(DropCourseParameter.RESPONSE_TAG, DropCourseParameter.RESPONSOR_KEY, cr);
		return true;
	}

	@Override
	public void run() {
		start(DropCourseParameter.REQUEST_TAG);
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

package responders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSON;

import parameters.AddCourseInfoParameter;
import beans.Course;
import logics.AddCourseInfo;
import responses.StateResponse;
import util.Messager;

public class AddCourseInfoResponsor extends Messager implements Runnable {
	private int responsorId;

	public AddCourseInfoResponsor(int resposorId) {
		super(AddCourseInfoParameter.TOPIC, AddCourseInfoParameter.RESPONSOR_CONSUMER_GROUP, AddCourseInfoParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = resposorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		Course courseInfo = JSON.parseObject((String) messageBody, Course.class);
		String result = AddCourseInfo.addCourseInfo(courseInfo);
		StateResponse sr = new StateResponse(messageId, result);
		sendMessage(AddCourseInfoParameter.RESPONSE_TAG, AddCourseInfoParameter.RESPONSOR_KEY, sr);
		return true;
	}

	@Override
	public void run() {
		start(AddCourseInfoParameter.REQUEST_TAG);
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

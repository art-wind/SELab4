package dresponsors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import responses.StateResponse;
import util.Messager;
import beans.Course;
import com.alibaba.fastjson.JSON;

import dispatchers.AddCourseInfoDispatcher;
import dparameter.AddCourseInfoDispatcherParameter;

public class AddCourseInfoDResponsor extends Messager implements Runnable {

	protected AddCourseInfoDResponsor(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		Course courseInfo = JSON.parseObject((String) messageBody, Course.class);
		StateResponse result = AddCourseInfoDispatcher.addCourseInfo(courseInfo);
		sendMessage(AddCourseInfoDispatcherParameter.RESPONSE_TAG, AddCourseInfoDispatcherParameter.RESPONSOR_KEY, result);
		return true;
	}

	@Override
	public void run() {
		start(AddCourseInfoDispatcherParameter.REQUEST_TAG);
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

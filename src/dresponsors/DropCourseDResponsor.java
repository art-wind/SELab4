package dresponsors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import responses.StateResponse;
import util.Messager;
import beans.StudentCourse;
import com.alibaba.fastjson.JSON;

import dispatchers.DropCourseDispatcher;
import dparameter.DropCourseDispatcherParameter;

public class DropCourseDResponsor extends Messager implements Runnable {

	public DropCourseDResponsor(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		StudentCourse st = JSON.parseObject((String)messageBody, StudentCourse.class);
		StateResponse result = DropCourseDispatcher.dropCourse(st);
		sendMessage(DropCourseDispatcherParameter.RESPONSE_TAG, DropCourseDispatcherParameter.RESPONSOR_KEY, result);
		return true;
	}

	@Override
	public void run() {
		start(DropCourseDispatcherParameter.REQUEST_TAG);
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

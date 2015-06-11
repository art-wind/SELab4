package dresponsors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import responses.StateResponse;
import util.Messager;
import beans.StudentCourse;
import com.alibaba.fastjson.JSON;

import dispatchers.SelectCourseDispatcher;
import dparameter.SelectCourseDispatcherParameter;

public class SelectCourseDResponsor extends Messager implements Runnable {

	public SelectCourseDResponsor(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		StudentCourse st = JSON.parseObject((String)messageBody, StudentCourse.class);
		StateResponse result = SelectCourseDispatcher.selectCourse(st);
		sendMessage(SelectCourseDispatcherParameter.RESPONSE_TAG, SelectCourseDispatcherParameter.RESPONSOR_KEY, result);
		return true;
	}

	@Override
	public void run() {
		start(SelectCourseDispatcherParameter.REQUEST_TAG);
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

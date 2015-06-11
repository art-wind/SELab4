package dresponsors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import responses.CourseResponse;
import util.Messager;
import com.alibaba.fastjson.JSON;

import dispatchers.QueryScheduleDispatcher;
import dparameter.QueryCourseByIdDispatcherParameter;
import dparameter.QueryScheduleDispatcherParameter;

public class QueryScheduleDResponsor extends Messager implements Runnable {

	public QueryScheduleDResponsor(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		String studentId = JSON.parseObject((String)messageBody, String.class);
		CourseResponse result = QueryScheduleDispatcher.querySchedule(studentId);
		sendMessage(QueryScheduleDispatcherParameter.RESPONSE_TAG, QueryScheduleDispatcherParameter.RESPONSOR_KEY, result);
		return true;
	}

	@Override
	public void run() {
		start(QueryCourseByIdDispatcherParameter.REQUEST_TAG);
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

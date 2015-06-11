package dresponsors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import responses.CourseResponse;
import com.alibaba.fastjson.JSON;

import dispatchers.QueryCourseByIdDispatcher;
import dparameter.QueryCourseByIdDispatcherParameter;
import util.Messager;

public class QueryCourseByIdDResponsor extends Messager implements Runnable {

	public QueryCourseByIdDResponsor(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		String courseId = JSON.parseObject((String)messageBody, String.class);
		CourseResponse result = QueryCourseByIdDispatcher.queryCourseById(courseId);
		sendMessage(QueryCourseByIdDispatcherParameter.RESPONSE_TAG, QueryCourseByIdDispatcherParameter.RESPONSOR_KEY, result);
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

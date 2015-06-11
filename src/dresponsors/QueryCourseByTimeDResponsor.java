package dresponsors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import responses.CourseResponse;
import util.Messager;
import beans.Time;
import com.alibaba.fastjson.JSON;

import dispatchers.QueryCourseByTimeDispatcher;
import dparameter.QueryCourseByIdDispatcherParameter;
import dparameter.QueryCourseByTimeDispatcherParameter;

public class QueryCourseByTimeDResponsor extends Messager implements Runnable {

	public QueryCourseByTimeDResponsor(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		Time time = JSON.parseObject((String)messageBody, Time.class);
		CourseResponse result = QueryCourseByTimeDispatcher.queryCourseByTime(time);
		sendMessage(QueryCourseByTimeDispatcherParameter.RESPONSE_TAG, QueryCourseByTimeDispatcherParameter.RESPONSOR_KEY, result);
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

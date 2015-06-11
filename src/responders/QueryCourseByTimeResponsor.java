package responders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import logics.QueryCourseByTime;
import parameters.QueryCourseByTimeParameter;
import responses.CourseResponse;
import util.Messager;
import beans.Course;
import beans.Time;
import com.alibaba.fastjson.JSON;

public class QueryCourseByTimeResponsor extends Messager implements Runnable{
	private int responsorId;

	public QueryCourseByTimeResponsor(int responsorId) {
		super(QueryCourseByTimeParameter.TOPIC, 
				QueryCourseByTimeParameter.RESPONSOR_CONSUMER_GROUP, QueryCourseByTimeParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = responsorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		Time time = JSON.parseObject((String)messageBody, Time.class);
		List<Course> result = QueryCourseByTime.queryCourseByTime(time);
		CourseResponse cr = new CourseResponse(messageId, result);
		sendMessage(QueryCourseByTimeParameter.RESPONSE_TAG, QueryCourseByTimeParameter.RESPONSOR_KEY, cr);
		return true;
	}

	@Override
	public void run() {
		start(QueryCourseByTimeParameter.REQUEST_TAG);
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

package responders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import logics.QueryCourseById;
import parameters.QueryCourseByIdParameter;
import responses.CourseResponse;
import util.Messager;
import beans.Course;
import com.alibaba.fastjson.JSON;

public class QueryCourseByIdResponsor extends Messager implements Runnable{
	private int responsorId;

	public QueryCourseByIdResponsor(int responsorId) {
		super(QueryCourseByIdParameter.TOPIC,
				QueryCourseByIdParameter.RESPONSOR_CONSUMER_GROUP, QueryCourseByIdParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = responsorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		String courseId = JSON.parseObject((String)messageBody, String.class);
		List<Course> result = QueryCourseById.queryCourseById(courseId);
		CourseResponse cr = new CourseResponse(messageId, result);
		sendMessage(QueryCourseByIdParameter.RESPONSE_TAG, QueryCourseByIdParameter.RESPONSOR_KEY, cr);
		return true;
	}

	@Override
	public void run() {
		start(QueryCourseByIdParameter.REQUEST_TAG);
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

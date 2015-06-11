package responders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import logics.QuerySchedule;
import parameters.QueryScheduleParameter;
import responses.CourseResponse;
import util.Messager;
import beans.Course;
import com.alibaba.fastjson.JSON;

public class QueryScheduleResponsor extends Messager implements Runnable {
	private int responsorId;

	public QueryScheduleResponsor(int responsorId) {
		super(QueryScheduleParameter.RESPONSOR_CONSUMER_GROUP, 
				QueryScheduleParameter.RESPONSOR_PRODUCER_GROUP, QueryScheduleParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = responsorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		String studentId = JSON.parseObject((String)messageBody, String.class);
		List<Course> result = QuerySchedule.querySchedule(studentId);
		CourseResponse cr = new CourseResponse(messageId, result);
		sendMessage(QueryScheduleParameter.RESPONSE_TAG, QueryScheduleParameter.RESPONSOR_KEY, cr);
		return true;
	}

	@Override
	public void run() {
		start(QueryScheduleParameter.REQUEST_TAG);
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

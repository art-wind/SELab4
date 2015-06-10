package responders;

import java.util.List;

import logics.QuerySchedule;
import parameters.QueryScheduleParameter;
import responses.CourseResponse;
import beans.Course;

import com.alibaba.fastjson.JSON;

import cn.edu.fudan.se.messager.Messager;

public class QueryScheduleResponsor extends Messager {
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

}

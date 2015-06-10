package responders;

import java.util.List;

import logics.QueryCourseByTime;
import parameters.QueryCourseByTimeParameter;
import responses.CourseResponse;
import beans.Course;
import beans.Time;

import com.alibaba.fastjson.JSON;

import cn.edu.fudan.se.messager.Messager;

public class QueryCourseByTimeResponsor extends Messager {
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

}

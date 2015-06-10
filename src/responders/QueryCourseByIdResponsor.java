package responders;

import java.util.List;

import logics.QueryCourseById;
import parameters.QueryCourseByIdParameter;
import responses.CourseResponse;
import beans.Course;

import com.alibaba.fastjson.JSON;

import cn.edu.fudan.se.messager.Messager;

public class QueryCourseByIdResponsor extends Messager {
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

}

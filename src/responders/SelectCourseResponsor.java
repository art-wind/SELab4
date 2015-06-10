package responders;

import logics.SelectCourse;
import parameters.SelectCourseParameter;
import responses.StateResponse;
import beans.StudentCourse;

import com.alibaba.fastjson.JSON;

import cn.edu.fudan.se.messager.Messager;

public class SelectCourseResponsor extends Messager {
	private int responsorId;

	public SelectCourseResponsor(int responsorId) {
		super(SelectCourseParameter.TOPIC, SelectCourseParameter.RESPONSOR_CONSUMER_GROUP, SelectCourseParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = responsorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		StudentCourse st = JSON.parseObject((String)messageBody, StudentCourse.class);
		String result = SelectCourse.selectCourse(st.getCourseId(), st.getStudentId());
		StateResponse cr = new StateResponse(messageId, result);
		sendMessage(SelectCourseParameter.RESPONSE_TAG, SelectCourseParameter.RESPONSOR_KEY, cr);
		return true;
	}

}

package responders;

import logics.DropCourse;
import parameters.DropCourseParameter;
import responses.StateResponse;
import beans.StudentCourse;

import com.alibaba.fastjson.JSON;

import cn.edu.fudan.se.messager.Messager;

public class DropCourseResponsor extends Messager {
	private int responsorId;

	public DropCourseResponsor(int responsorId) {
		super(DropCourseParameter.TOPIC,
				DropCourseParameter.RESPONSOR_CONSUMER_GROUP, DropCourseParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = responsorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		StudentCourse st = JSON.parseObject((String)messageBody, StudentCourse.class);
		String result = DropCourse.dropCourse(st.getCourseId(), st.getStudentId());
		StateResponse cr = new StateResponse(messageId, result);
		sendMessage(DropCourseParameter.RESPONSE_TAG, DropCourseParameter.RESPONSOR_KEY, cr);
		return true;
	}

}

package responders;

import beans.Course;
import logics.AddCourseInfo;
import responses.StateResponse;
import cn.edu.fudan.se.messager.Messager;

public class AddCourseInfoResponder extends Messager {

	public AddCourseInfoResponder(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		if (!(messageBody instanceof Course))
			return false;
		Course courseInfo = (Course) messageBody;
		String result = AddCourseInfo.addCourseInfo(courseInfo);
		StateResponse sr = new StateResponse(id, result);
		return false;
	}

}

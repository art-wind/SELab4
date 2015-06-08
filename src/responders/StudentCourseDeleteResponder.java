package responders;

import cn.edu.fudan.se.messager.Messager;

public class StudentCourseDeleteResponder extends Messager {

	public StudentCourseDeleteResponder(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		// TODO Auto-generated method stub
		return false;
	}

}

package invokers;

import cn.edu.fudan.se.messager.Messager;

public class AddCourseInfoInvoker extends Messager {

	protected AddCourseInfoInvoker(String topic, String consumerGroup,
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

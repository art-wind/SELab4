package responders;

import cn.edu.fudan.se.messager.Messager;

public class StudentInfoSelectByIdResponder extends Messager {

	public StudentInfoSelectByIdResponder(String topic, String consumerGroup,
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
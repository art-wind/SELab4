package invokers;

import cn.edu.fudan.se.messager.Messager;

public class SchoolInfoInsertInvoker extends Messager {

	public SchoolInfoInsertInvoker(String topic, String consumerGroup,
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

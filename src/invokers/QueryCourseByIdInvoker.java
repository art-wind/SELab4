package invokers;

import util.Messager;

public class QueryCourseByIdInvoker extends Messager {

	public QueryCourseByIdInvoker(String topic, String consumerGroup,
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

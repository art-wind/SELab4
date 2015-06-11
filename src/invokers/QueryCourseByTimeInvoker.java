package invokers;

import util.Messager;

public class QueryCourseByTimeInvoker extends Messager {

	public QueryCourseByTimeInvoker(String topic, String consumerGroup,
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

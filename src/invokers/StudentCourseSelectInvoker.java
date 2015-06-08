package invokers;

import cn.edu.fudan.se.messager.Messager;

public class StudentCourseSelectInvoker extends Messager {

	protected StudentCourseSelectInvoker(String topic,
			String consumerGroup, String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}
	
	
    @Override
    protected boolean onReceiveMessage(String messageId, Object messageBody) {
    	return false;
    }
    
    
}

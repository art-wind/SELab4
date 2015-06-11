package invokers;

import java.util.concurrent.ConcurrentHashMap;

import beans.Course;
import beans.Student;
import requests.StudentRequest;
import responses.StudentResponse;
import util.Messager;

public class AddStudentInfoInvoker extends Messager {
	private int responsorCount;
    private ConcurrentHashMap<String, ListResponseHandler<Student>> idHandlerMap;
    
	public AddStudentInfoInvoker(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		// TODO Auto-generated method stub
		if(!(messageBody instanceof StudentResponse)){
			 log("response type error");
	            return false;
		}
		StudentResponse response = (StudentResponse)messageBody;
		if(!(idHandlerMap.containsKey(response.id))){
			log("request id not exists");
        } else {
//            idHandlerMap.get(response.id).addResponse(response.student);
        }
        return true;
	}

}

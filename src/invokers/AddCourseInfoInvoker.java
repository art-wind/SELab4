package invokers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import requests.CourseRequest;
import responses.CourseResponse;
import util.Messager;
import beans.Course;
import cn.edu.fudan.se.Parameter;

import com.alibaba.rocketmq.client.producer.SendResult;

public class AddCourseInfoInvoker extends Messager {

    private int responsorCount;
    private ConcurrentHashMap<String, ListResponseHandler<Course>> idHandlerMap;
    
	protected AddCourseInfoInvoker(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		// TODO Auto-generated method stub
		if (!(messageBody instanceof CourseResponse)) {
            log("response type error");
            return false;
        }
		CourseResponse response = (CourseResponse) messageBody;
        if (!idHandlerMap.containsKey(response.id)) {
            log("request id not exists");
        } else {
            idHandlerMap.get(response.id).addResponse(response.courses);
        }
        return true;
	}
	public void run(){
		start(Parameter.RESPONSE_TAG);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			String line;
			try {
				if("stop".equals( line = reader.readLine() )){
					break;
				}
				else{
					CourseRequest body = new CourseRequest(line);
					SendResult sendResult = sendMessage(Parameter.REQUEST_TAG, Parameter.INVOKER_KEY, body);
					idHandlerMap.put(sendResult.getMsgId(),new Handler(responsorCount,sendResult.getMsgId()) );
					log(String.format("[%s]%s", sendResult.getMsgId(), body));
				}
			} catch (IOException e) {
                e.printStackTrace();
            }
        }
        stop();
	}
	private class Handler extends ListResponseHandler<Course> {
        Handler(int expectResponseCount, String messageId) {
            super(expectResponseCount, messageId);
        }
		@Override
		void enoughResponse(Vector<Course> result, String key) {
			// TODO Auto-generated method stub
			String print = "";
            for (Course course : result) {
                print += course + "\n";
            }
            idHandlerMap.remove(key);
            log(String.format("enough Response:\n%s", print));
		}
    }

    public static void main(String[] args) {
//        new Thread(new AddCourseInfoInvoker(3)).start();
    }
}

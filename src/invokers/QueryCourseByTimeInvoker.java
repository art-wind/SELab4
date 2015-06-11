package invokers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.sun.tools.hat.internal.parser.Reader;

import requests.CourseRequest;
import responses.CourseResponse;
import cn.edu.fudan.se.Parameter;
import beans.Course;
import util.Messager;

public class QueryCourseByTimeInvoker extends Messager {
	int responsorCount;
	private ConcurrentHashMap<String, ListResponseHandler<Course>> idHandlerMap;
    
	public QueryCourseByTimeInvoker(String topic, String consumerGroup,
			String producerGroup) {
		super(topic, consumerGroup, producerGroup);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		// TODO Auto-generated method stub
		if( !(messageBody instanceof CourseResponse) ){
			log("Not valid type.");
		}
		CourseResponse response = (CourseResponse)messageBody;
		if (!idHandlerMap.containsKey(response.id)) {
            log("request id not exists");
        } else {
            idHandlerMap.get(response.id).addResponse(response.courses);
        }
		return false;
	}
	public void run(){
		start(Parameter.RESPONSE_TAG);
		while(true){
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line;
			try {
				if("stop".equals( line = reader.readLine()) ){
					break;
				}
				else{
					CourseRequest body = new CourseRequest(line);
					SendResult sendResult = sendMessage(Parameter.RESPONSE_TAG, Parameter.INVOKER_KEY, body);
					idHandlerMap.put(line,new Handler(responsorCount, sendResult.getMsgId()));
					log(String.format("[%s]%s", sendResult.getMsgId(), body));
				}
			}
			catch(IOException o){
				
			}
			
		}
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

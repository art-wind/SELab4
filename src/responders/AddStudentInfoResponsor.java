package responders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logics.AddStudentInfo;
import parameters.AddStudentInfoParameter;
import responses.StateResponse;
import util.Messager;
import beans.Student;
import com.alibaba.fastjson.JSON;

public class AddStudentInfoResponsor extends Messager implements Runnable {
	private int responsorId;

	public AddStudentInfoResponsor(int responsorId) {
		super(AddStudentInfoParameter.TOPIC, AddStudentInfoParameter.RESPONSOR_CONSUMER_GROUP, AddStudentInfoParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = responsorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		Student studentInfo = JSON.parseObject((String)messageBody, Student.class);
		String result = AddStudentInfo.addStudentInfo(studentInfo.getStudentId(), studentInfo.getName(), 
				studentInfo.getGender(), studentInfo.getSchoolName());
		StateResponse sr = new StateResponse(messageId, result);
		sendMessage(AddStudentInfoParameter.RESPONSE_TAG, AddStudentInfoParameter.RESPONSOR_KEY, sr);
		return true;
	}

	@Override
	public void run() {
		start(AddStudentInfoParameter.REQUEST_TAG);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                if ("stop".equals(reader.readLine())) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stop();
	}

}

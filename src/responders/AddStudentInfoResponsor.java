package responders;

import logics.AddStudentInfo;
import parameters.AddStudentInfoParameter;
import responses.StateResponse;
import beans.Student;

import com.alibaba.fastjson.JSON;

import cn.edu.fudan.se.messager.Messager;

public class AddStudentInfoResponsor extends Messager {
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

}

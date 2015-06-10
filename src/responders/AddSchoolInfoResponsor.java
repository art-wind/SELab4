package responders;

import logics.AddSchoolInfo;

import com.alibaba.fastjson.JSON;

import beans.School;
import parameters.AddSchoolInfoParameter;
import responses.StateResponse;
import cn.edu.fudan.se.messager.Messager;

public class AddSchoolInfoResponsor extends Messager {
	private int responsorId;

	public AddSchoolInfoResponsor(int resposorId) {
		super(AddSchoolInfoParameter.TOPIC, AddSchoolInfoParameter.RESPONSOR_CONSUMER_GROUP, AddSchoolInfoParameter.RESPONSOR_PRODUCER_GROUP);
		this.responsorId = resposorId;
	}

	@Override
	protected boolean onReceiveMessage(String messageId, Object messageBody) {
		School schoolInfo = JSON.parseObject((String)messageBody, School.class);
		String result = AddSchoolInfo.addSchoolInfo(schoolInfo.getSchoolName(), schoolInfo.getCreditRequirement());
		StateResponse sr = new StateResponse(messageId, result);
		sendMessage(AddSchoolInfoParameter.RESPONSE_TAG, AddSchoolInfoParameter.RESPONSOR_KEY, sr);
		return true;
	}

}

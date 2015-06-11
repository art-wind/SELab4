package responders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logics.AddSchoolInfo;
import com.alibaba.fastjson.JSON;

import beans.School;
import parameters.AddSchoolInfoParameter;
import responses.StateResponse;
import util.Messager;

public class AddSchoolInfoResponsor extends Messager implements Runnable {
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

	@Override
	public void run() {
		start(AddSchoolInfoParameter.REQUEST_TAG);
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

package operators;

import java.util.List;

import beans.School;
import cn.edu.fudan.se.dac.Condition;
import cn.edu.fudan.se.dac.DACFactory;
import cn.edu.fudan.se.dac.DataAccessInterface;

public class SchoolOperator {
	private static DataAccessInterface<School> schoolDAC = DACFactory.getInstance().createDAC(School.class);
	public static final int SCHOOL_ADD_COMMIT_ERROR = -2;
	public static final int SCHOOL_ADD_SUCCESS = 1;
	public static final int SCHOOL_EXISTS_ERROR = -1;
	{
		System.out.println("Initializing School Operator...");
		schoolDAC.beginTransaction();
		System.out.println("OK");
	}
	public int addSchoolInfo(String schoolName, int creditRequirement){
		School school = new School(schoolName,creditRequirement);
		final String tempsn = schoolName;
		List<School> checkList = schoolDAC.selectByCondition(new Condition<School>(){
			public boolean assertBean(School arg0) {
				return arg0.getSchoolName().equals(tempsn);
			}	
		});
		if (!checkList.isEmpty()) return SCHOOL_EXISTS_ERROR;
		schoolDAC.add(school);
		schoolDAC.commit();
		schoolDAC.beginTransaction();
		return SCHOOL_ADD_SUCCESS;
		
		
		
	}
	public void getAll(){
		System.out.println("School List:");
		for (School s:schoolDAC.selectByCondition(new Condition.True<School>())){
			System.out.println(s.getSchoolName());
		}
	}
	public void clear() {
		schoolDAC.deleteByCondition(new Condition.True<School>());
		schoolDAC.commit();
		schoolDAC.beginTransaction();
		
	}
}

package beans;

public class School {
	private String schoolName;
	private int creditRequirement;
	public School(){
		
	}
	
	public School(String schoolName, int creditRequirement){
		this.schoolName = schoolName;
		this.creditRequirement = creditRequirement;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getCreditRequirement() {
		return creditRequirement;
	}

	public void setCreditRequirement(int creditRequirement) {
		this.creditRequirement = creditRequirement;
	}
	
	
}

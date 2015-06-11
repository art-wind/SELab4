package beans;

public class Time{
	private int weekday;
	private int period;
	public Time(){
		
	}
	public Time(int weekday, int period){
		this.weekday = weekday;
		this.period = period;
	}
	public int getWeekday() {
		return weekday;
	}
	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public boolean same(Time time2){
		return this.period == time2.period && this.weekday == time2.weekday;
	}
	
	
}
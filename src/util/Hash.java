package util;

import beans.Time;

public class Hash {
	
	int hashnum = 10000007;
	int clusternum;
	
	public Hash(int clusternum) {
		this.clusternum = clusternum;
	}

	public int hash(Time time) {
		
		return (time.getWeekday() * hashnum + time.getPeriod()) % clusternum;
		
	}
	
	public int hash(String id) {
		int result = 0;
		for (int i = 0; i < id.length(); i++)
			result = (result * hashnum + id.charAt(i)) % clusternum;
		return result;
	}
	
}

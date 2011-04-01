package il.ac.tau.team3.datastore;

import il.ac.tau.team3.common.GeneralUser;
import il.ac.tau.team3.common.SPGeoPoint;

public class UserLocation extends GeneralLocation{
	
	private String name;
	
	private String status;
	
	public UserLocation(double longitude, double latitude, GeneralUser user) {
		super(longitude, latitude);
		this.name = user.getName();
		this.status = user.getStatus();
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

}

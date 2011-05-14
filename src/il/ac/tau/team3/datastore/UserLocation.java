package il.ac.tau.team3.datastore;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;


import il.ac.tau.team3.common.GeneralUser;
import il.ac.tau.team3.common.SPGeoPoint;

@PersistenceCapable
public class UserLocation extends GeneralLocation{
	@Persistent
	private String name;
	@Persistent
	private String firstName;
	@Persistent
	private String lastName;
	@Persistent
	private String status;
	
	public UserLocation(GeneralUser user) {
		super(user.getSpGeoPoint().getLongitudeInDegrees(), user.getSpGeoPoint().getLatitudeInDegrees());
		this.name = user.getName();
		this.status = user.getStatus();
		
		this.firstName = user.getStatus();
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	

}

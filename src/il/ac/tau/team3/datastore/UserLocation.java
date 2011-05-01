package il.ac.tau.team3.datastore;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Inheritance;

import il.ac.tau.team3.common.GeneralUser;
import il.ac.tau.team3.common.SPGeoPoint;

@Entity
@Inheritance
public class UserLocation extends GeneralLocation{
	@Basic
	private String name;
	@Basic
	private String status;
	
	public UserLocation(GeneralUser user) {
		super(user.getSpGeoPoint().getLongitudeInDegrees(), user.getSpGeoPoint().getLatitudeInDegrees());
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

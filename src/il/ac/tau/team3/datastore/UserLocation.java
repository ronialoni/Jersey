package il.ac.tau.team3.datastore;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.codehaus.jackson.annotate.JsonIgnore;


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
	
	@JsonIgnore
	public static UserLocation getUserByName(String name)	{
		@SuppressWarnings("unchecked")
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(UserLocation.class, "name==paramname");
		q.declareParameters("String paramname");
		List<UserLocation> lst = (List<UserLocation>)q.execute(name);
		if (lst.size() == 0)	{
			return null;
		}
		return lst.get(0);
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

	@Override
	public boolean equals(Object o)	{
		if (o == this) {
			return true;
		}
		
		if (o == null)	{
			return false;
		}
		
		if (!(o instanceof GeneralUser))	{
			return false;
		}
		
		UserLocation other = (UserLocation)o;
		
		return this.getKey().equals(other.getKey());
		
	}

}

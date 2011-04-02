package il.ac.tau.team3.datastore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;

import java.util.List;

import il.ac.tau.team3.common.GeneralPlace;

@Entity 
@Inheritance
public class PlaceLocation extends GeneralLocation{
		
	private String name;
	private String address;
	private List<String> minyanJoiners;
	
	public PlaceLocation(GeneralPlace place ) {
		super(place.getSpGeoPoint().getLongitudeInDegrees(), place.getSpGeoPoint().getLatitudeInDegrees());
		this.name = place.getName();
		this.address = place.getAddress();
		
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void addJoiner(String name){
		this.minyanJoiners.add(name);
		return;
	}
	
	public void removeJoiner(String name){
		this.minyanJoiners.remove(name);
		return;
	}

}

package il.ac.tau.team3.datastore;
import javax.jdo.annotations.Embedded;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

import il.ac.tau.team3.common.GeneralPlace;
import il.ac.tau.team3.common.GeneralUser;

@Entity 
@Inheritance
public class PlaceLocation extends GeneralLocation {
	
	
	private String name;

	private String address;
	
	private String owner;

	private List<String> minyanJoiners;
	
	public PlaceLocation(GeneralPlace place ) {
		super(place.getSpGeoPoint().getLongitudeInDegrees(), place.getSpGeoPoint().getLatitudeInDegrees());
		this.name = place.getName();
		this.address = place.getAddress();
		this.minyanJoiners = place.getAllJoiners();
		this.owner = place.getOwner();
		// TODO Auto-generated constructor stub
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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
	
	public List<String> getAllJoiners(){
		return this.minyanJoiners;
	}
	
	public void setAllJoiners(List<String> joiners){
		this.minyanJoiners = joiners;
	}
	
	
	public boolean IsJoinerSigned(String joiner){
	    	return (this.minyanJoiners.contains(joiner));
	    }
	
	
	public int getNumberOfPrayers(){
	    	return this.minyanJoiners.size();
	    }
}

package il.ac.tau.team3.datastore;
import javax.jdo.annotations.Embedded;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
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
	
	@Basic
	private String name;
	@Basic
	private String address;
	@Basic
	private String owner;
	
	@Basic
	private List<String> minyanJoiners;
	@Basic
	private List<String> minyanJoiners2;
	@Basic
	private List<String> minyanJoiners3;
	@Basic
	private boolean prays[];
	
	public boolean[] getPrays() {
		return prays;
	}

	public void setPrays(boolean[] prays) {
		this.prays = prays;
	}

	public PlaceLocation(GeneralPlace place ) {
		super(place.getSpGeoPoint().getLongitudeInDegrees(), place.getSpGeoPoint().getLatitudeInDegrees());
		this.name = place.getName();
		this.address = place.getAddress();
		this.minyanJoiners = place.getAllJoiners();
		this.minyanJoiners2 = place.getAllJoiners2();
		this.minyanJoiners3 = place.getAllJoiners3();
		this.prays = place.getPrays();
		this.owner = place.getOwner();
		// TODO Auto-generated constructor stub
	}
	
	public List<String> getMinyanJoiners() {
		return minyanJoiners;
	}

	public void setMinyanJoiners(List<String> minyanJoiners) {
		this.minyanJoiners = minyanJoiners;
	}

	

	public void setMinyanJoiners2(List<String> minyanJoiners2) {
		this.minyanJoiners2 = minyanJoiners2;
	}

	

	public void setMinyanJoiners3(List<String> minyanJoiner3) {
		this.minyanJoiners3 = minyanJoiner3;
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
	public void addJoiner2(String name){
		this.minyanJoiners2.add(name);
		return;
	}
	
	public void removeJoiner2(String name){
		this.minyanJoiners2.remove(name);
		return;
	}
	public void addJoiner3(String name){
		this.minyanJoiners3.add(name);
		return;
	}
	
	public void removeJoiner3(String name){
		this.minyanJoiners3.remove(name);
		return;
	}
	
	public List<String> getAllJoiners(){
		return this.minyanJoiners;
	}
	
	public List<String> getAllJoiners2(){
		return this.minyanJoiners2;
	}
	
	public List<String> getAllJoiners3(){
		return this.minyanJoiners3;
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
	public boolean IsJoinerSigned2(String joiner){
    	return (this.minyanJoiners2.contains(joiner));
    }


public int getNumberOfPrayers2(){
    	return this.minyanJoiners2.size();
    }
public boolean IsJoinerSigned3(String joiner){
	return (this.minyanJoiners3.contains(joiner));
}


public int getNumberOfPrayers3(){
	return this.minyanJoiners3.size();
}
}

package il.ac.tau.team3.datastore;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Serialized;


import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import il.ac.tau.team3.common.GeneralPlace;
import il.ac.tau.team3.common.GeneralUser;
import il.ac.tau.team3.common.Pray;

@PersistenceCapable
public class PlaceLocation extends GeneralLocation {
	
	@Persistent
	private String name;
	@Persistent
	private String address;
	@Persistent (defaultFetchGroup="true",serialized="true")
	private GeneralUser owner;
	@Persistent
	private Date startDate;
	@Persistent
	private Date endDate;
	
	
	@Persistent (defaultFetchGroup="true",serialized="true", dependentElement = "true")
	private List<Pray> praysOfTheDay = new ArrayList<Pray>();
	
	
	@Persistent
	private boolean prays[];
	
	

	public PlaceLocation(GeneralPlace place ) {
		super(place.getSpGeoPoint().getLongitudeInDegrees(), place.getSpGeoPoint().getLatitudeInDegrees());
		this.name = place.getName();
		this.address = place.getAddress();
		this.prays = place.getPrays();
		this.owner = place.getOwner();
		this.startDate = place.getStartDate();
		this.endDate = place.getEndDate();
		for(Pray pray : place.getPraysOfTheDay()){
			this.praysOfTheDay.add(pray);
		}
		
		// TODO Auto-generated constructor stub
	}
	
	public boolean[] getPrays() {
		return prays;
	}

	public void setPrays(boolean[] prays) {
		this.prays = prays;
	}
	
	

	public List<Pray> getPraysOfTheDay() {
		return praysOfTheDay;
	}

	public void setPraysOfTheDay(List<Pray> praysOfTheDay) {
		this.praysOfTheDay = praysOfTheDay;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public GeneralUser getOwner() {
		return owner;
	}

	public void setOwner(GeneralUser owner) {
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
	
	public void addJoiner(int prayNumber, String name){
		if(prayNumber < 0 || prayNumber >= praysOfTheDay.size()){
			return ;
		}
		this.praysOfTheDay.get(prayNumber).addJoiner(name);
	}
	
	public void removeJoiner(int prayNumber, String name){
		if(prayNumber < 0 || prayNumber >= praysOfTheDay.size()){
			return ;
		}
		this.praysOfTheDay.get(prayNumber).removeJoiner(name);
	}
	

	
	
	public boolean IsJoinerSigned(int prayNum, String joiner){
	    	return (this.praysOfTheDay.get(prayNum).isJoinerSigned(joiner));
	    }
	
	
	public int getNumberOfPrayers(int prayNum){
	    	return this.praysOfTheDay.get(prayNum).numberOfJoiners();
	    }
}

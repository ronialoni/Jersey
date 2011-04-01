package il.ac.tau.team3.datastore;

import java.util.List;

import il.ac.tau.team3.common.GeneralPlace;

public class PlaceLocation extends GeneralLocation{
	private String name;
	private String address;
	private List<String> minyanJoiners;
	
	public PlaceLocation(double longitude, double latitude, GeneralPlace place ) {
		super(longitude, latitude);
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

}

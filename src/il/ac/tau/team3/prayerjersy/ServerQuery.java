package il.ac.tau.team3.prayerjersy;

import java.io.Serializable;

public class ServerQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7352718258289905443L;
	
	enum DataType	{
		USERS,
		PLACES
	};
	
	public double lat;
	public double lon;
	public long radius;
	public DataType type;
	
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public long getRadius() {
		return radius;
	}
	public void setRadius(long radius) {
		this.radius = radius;
	}
	public ServerQuery(double lat, double lon, long radius, DataType type) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.radius = radius;
		this.type = type;
	}
	
	
}

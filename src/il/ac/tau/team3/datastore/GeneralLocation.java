package il.ac.tau.team3.datastore;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.annotations.Geocells;
import com.beoui.geocell.annotations.Latitude;
import com.beoui.geocell.annotations.Longitude;
import com.beoui.geocell.model.Point;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)

public class GeneralLocation  {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	@Longitude
	double longitude;

	@Persistent
	@Latitude
	double latitude;

	
	@Persistent
	@Geocells
	private List<String> geocells = new ArrayList<String>();
	
	public GeneralLocation(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		Point p = new Point(longitude, latitude);
		geocells = GeocellManager.generateGeoCell(p);
		

	}
	
	public void setGeocells(List<String> geocellsP) {
		this.geocells = geocellsP;
	}

	public Long getKey() {
        return id;
    }


	


	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public List<String> getGeocells() {
		return geocells;
	}

	public void setGeocells(double lat,double longt) {
		Point p = new Point(longitude, latitude);
		geocells = GeocellManager.generateGeoCell(p);
	}
}

package il.ac.tau.team3.datastore;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.annotations.Geocells;
import com.beoui.geocell.annotations.Latitude;
import com.beoui.geocell.annotations.Longitude;
import com.beoui.geocell.model.Point;

@Entity
@MappedSuperclass

public class GeneralLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Longitude
	double longitude;

	@Latitude
	double latitude;

	@Geocells
	@OneToMany(fetch = FetchType.EAGER)
	private List<String> geoCellsData = new ArrayList<String>();
	
	public GeneralLocation(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		Point p = new Point(longitude, latitude);
		geoCellsData = GeocellManager.generateGeoCell(p);
		

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

	public List<String> getGeoCellsData() {
		return geoCellsData;
	}

	public void setGeoCellsData(double lat,double longt) {
		Point p = new Point(longitude, latitude);
		geoCellsData = GeocellManager.generateGeoCell(p);
	}
}

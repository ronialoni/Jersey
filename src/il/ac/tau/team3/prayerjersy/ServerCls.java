package il.ac.tau.team3.prayerjersy;

import il.ac.tau.team3.common.GeneralPlace;
import il.ac.tau.team3.common.GeneralUser;
import il.ac.tau.team3.common.SPGeoPoint;
import il.ac.tau.team3.datastore.EMF;
import il.ac.tau.team3.datastore.GeneralLocation;
import il.ac.tau.team3.datastore.PlaceLocation;
import il.ac.tau.team3.datastore.UserLocation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Application;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.model.GeocellQuery;
import com.beoui.geocell.model.Point;

public class ServerCls extends Application {
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(prayerjersy.class);

		return s;
	}
	
}

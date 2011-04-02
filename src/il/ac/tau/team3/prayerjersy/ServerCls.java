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
//		EntityManager entity = EMF.get().createEntityManager();
////		GeneralLocation loc = new GeneralLocation(23, 25);
////		entity.getTransaction().begin();
////		entity.persist(loc);
////		entity.getTransaction().commit();
//		
//		GeneralPlace p = new GeneralPlace("My minyan122444","12 bla bla st",new SPGeoPoint(23, 25));
//		
//		PlaceLocation ploc = new PlaceLocation(p);
//		entity.getTransaction().begin();
//		entity.persist(ploc);
//		entity.getTransaction().commit();
//		entity.getTransaction().begin();
//		PlaceLocation userx = entity.find(PlaceLocation.class, 119);
//		userx.setAddress("neeee");
//	    //userx.setName(ploc.getName());
//	   // userx.setLatitude(ploc.getLatitude());
//	    //userx.setLongitude(ploc.getLongitude());
//	    //userx.setGeoCellsData(ploc.getLatitude(), ploc.getLongitude());
//		entity.getTransaction().commit();
//		
//		GeneralUser u = new GeneralUser("Roni",new SPGeoPoint(23, 25),"Looking for friends");
//		
//		UserLocation uloc = new UserLocation(u);
//		entity.getTransaction().begin();
//		entity.persist(uloc);
//		entity.getTransaction().commit();
//		
//		GeocellQuery baseQuery = new GeocellQuery("SELECT x FROM UserLocation x");
//		
//
//		Point center = new Point (25.0, 24.0);
//		
//        List<UserLocation> results = GeocellManager.proximitySearch(center, 10, 1000000, UserLocation.class, baseQuery, entity, 13);
//        System.out.println(results);
		return s;
	}
	
}

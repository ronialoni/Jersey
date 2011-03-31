package il.ac.tau.team3.prayerjersy;

import il.ac.tau.team3.datastore.EMF;
import il.ac.tau.team3.datastore.GeneralLocation;

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
		EntityManager entity = EMF.get().createEntityManager();
		GeneralLocation loc = new GeneralLocation(23, 25);
		entity.getTransaction().begin();
		entity.persist(loc);
		entity.getTransaction().commit();
		
		GeocellQuery baseQuery = new GeocellQuery("SELECT x FROM GeneralLocation x");
		

		Point center = new Point (25.0, 24.0);
		
        List<GeneralLocation> results = GeocellManager.proximitySearch(center, 10, 1000000, GeneralLocation.class, baseQuery, entity, 13);
        System.out.println(results);
		return s;
	}
	
}

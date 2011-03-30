package il.ac.tau.team3.prayerjersy;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ServerCls extends Application {
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(prayerjersy.class);
		return s;
	}
}

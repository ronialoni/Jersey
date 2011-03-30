package il.ac.tau.team3.prayerjersy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import il.ac.tau.team3.common.GeneralUser;
import il.ac.tau.team3.common.SPGeoPoint;
import il.ac.tau.team3.common.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//The Java class will be hosted at the URI path "/helloworld"

@Path("/prayerjersy")
public class prayerjersy {

	private volatile static List<GeneralUser> users;
	private static final Logger log = Logger.getLogger(ServerCls.class
			.getName());

	public prayerjersy() {
		users = new ArrayList<GeneralUser>();
		User user = new User("Server Tomer", new SPGeoPoint(3600, 4100),
				"An orthodax extremest");
		GeneralUser otheruser1 = new GeneralUser("Server Aviad",
				new SPGeoPoint(3600, 4200), "Looking for Minyan");
		GeneralUser otheruser2 = new GeneralUser("Server Roni", new SPGeoPoint(
				3600, 4300), "Looking for something to cook...");
		GeneralUser otheruser3 = new GeneralUser("Server Matan",
				new SPGeoPoint(3600, 4400), "Looking for Minyan");
		users.add(user);
		users.add(otheruser1);
		users.add(otheruser2);
		users.add(otheruser3);
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralUser retrieve(@PathParam("id") int id) {

		return users.get(id);
	}

	@GET
	@Produces("application/json")
	public Integer getNumUsers() {
		return users.size();
	}

	@PUT
	@Consumes("application/json")
	public void store(GeneralUser user) {

	}

	@DELETE
	@Consumes("application/json")
	public void remove(GeneralUser user) {

	}

}

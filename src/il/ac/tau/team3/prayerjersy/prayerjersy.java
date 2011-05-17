package il.ac.tau.team3.prayerjersy;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import il.ac.tau.team3.common.GeneralPlace;
import il.ac.tau.team3.common.GeneralUser;
import il.ac.tau.team3.common.PlaceAndUser;
import il.ac.tau.team3.common.Pray;
import il.ac.tau.team3.datastore.PMF;


import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;

import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.JDOGeocellQueryEngine;
import com.beoui.geocell.model.GeocellQuery;
import com.beoui.geocell.model.Point;

//The Java class will be hosted at the URI path "/helloworld"

@Path("/prayerjersy")
public class prayerjersy {

	private static final Logger log = Logger.getLogger(ServerCls.class
			.getName());
	private static final int DEFAULT_SEARCH_RESOlUTION = 1;
	private PersistenceManager pm;


	public prayerjersy() {
		//entity = EMF.get().createEntityManager();
		this.pm = PMF.get().getPersistenceManager();
	}

	/*@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralUser retrieveUser(@PathParam("id") int id) {
		if(id < users.size()){
			return users.get(id);
		}
		else return null;
	}
	 */
	@GET
	@Path("/updateuserbyid")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean UpdateUserLocationById(@QueryParam("longitude") double longitude, @QueryParam("latitude")double latitude, @QueryParam("id") long id){
		try{
			GeneralUser userx = pm.getObjectById(GeneralUser.class, id);
			if(userx!=null){

				userx.setLatitude(latitude);
				userx.setLongitude(longitude);
				userx.setGeocells(latitude, longitude);

				CacheCls.getUserCache().clear();
				return true;
			}}
		finally{
			pm.close();
		}
		return false;
	}



	@POST
	@Path("/updateuserbyname")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Long UpdateUserLocationByName(GeneralUser user){
		Query q = pm.newQuery(GeneralUser.class, "name==nameParam");
		q.declareParameters("String nameParam");
		CacheCls.getUserCache().clear(); 
		CacheCls.getPlaceCache().clear();
		Long id;
		GeneralUser userx;
		try{
			
			Collection<GeneralUser> list = (Collection<GeneralUser>) q.execute(user.getName());
			if(list.isEmpty()){

				userx = new GeneralUser(user);
				userx.setId(null);
				pm.makePersistent(userx);
				

			} else	{
				userx = list.iterator().next();
				userx.cloneUserData(user);
			}
			
			id = userx.getId();
		}
		finally{
			q.closeAll();
			pm.close();
			
		}

		return id;
		
	}

	@POST
	@Path("/updateplacebylocation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Long UpdatePlaceLocationByLocation(GeneralPlace place){

		//Query q = entity.createQuery("SELECT x FROM PlaceLocation x WHERE " +
		//		"x.latitude="+place.getSpGeoPoint().getLatitudeInDegrees()+" AND x.longitude="+place.getSpGeoPoint().getLongitudeInDegrees());
		CacheCls.getPlaceCache().clear();

		GeneralPlace placex = null;
		Long id;
		try{
			if (place.getId() != null)	{
				placex = (GeneralPlace)pm.getObjectById(GeneralPlace.class, place.getId());
			}
			if (placex != null)	{
				placex.cloneUserData(place);
			} else	{
				placex = new GeneralPlace(place);
				pm.makePersistent(place);
			}
			id = placex.getId();
		}
		finally{
			pm.close();
		}
		return id;
	}

	@POST
	@Path("/addjoiner")
	@Consumes(MediaType.APPLICATION_JSON)
	public void UpdatePlaceJoinersByName_Add(PlaceAndUser pau){
		GeneralUser user = pau.getUser();
		GeneralPlace place = pau.getPlace();
		boolean praysWishes[] = pau.getPraysWishes();
		final String[] praysNames = new String[]{"Shaharit", "Minha", "Arvit"};

		if(place == null || user == null || place.getId() == null || user.getId() == null){
			return;
		}

		GeneralPlace placex = pm.getObjectById(GeneralPlace.class, place.getId());
		try{
			if(placex!=null){
				//URI u = UriBuilder.fromResource(PlaceLocation.class).build(placex);
				CacheCls.getPlaceCache().clear();
				for (int i = 0; i < praysWishes.length; i++)	{
					if(praysWishes[i]){
						Pray p = placex.getPrayByName(praysNames[i]); 
						if(!p.isJoinerSigned(user)){
							p.addJoiner(user);
						}
					}
				}
			}
		}finally{
			pm.close();
		}
	}		


	@POST
	@Path("/removejoiner")
	@Consumes(MediaType.APPLICATION_JSON)
	public void UpdatePlaceJoinersByName_Remove(PlaceAndUser pau){
		GeneralUser user = pau.getUser();
		GeneralPlace place = pau.getPlace();
		boolean praysWishes[] = pau.getPraysWishes();
		final String[] praysNames = new String[]{"Shaharit", "Minha", "Arvit"};
		if(place == null || user == null || place.getId() == null || user.getId() == null){
			return;
		}

		GeneralPlace placex = pm.getObjectById(GeneralPlace.class, place.getId());
		try{
			if(placex!=null){
				//URI u = UriBuilder.fromResource(PlaceLocation.class).build(placex);
				CacheCls.getPlaceCache().clear();
				for (int i = 0; i < praysWishes.length; i++)	{
					if(praysWishes[i]){
						Pray p = placex.getPrayByName(praysNames[i]); 
						if(p.isJoinerSigned(user)){
							p.removeJoiner(user);
						}
					}
				}
			}
		}finally{
			pm.close();
		}

	}


	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralUser[] retrieveAllUsers(@QueryParam("longitude") double longitude, @QueryParam("latitude")double latitude, @QueryParam("radius")long radius) {
		ServerQuery query  = new ServerQuery(latitude, longitude, radius, ServerQuery.DataType.USERS);
		if (CacheCls.getUserCache().containsKey(query))	{
			return (GeneralUser[])CacheCls.getUserCache().get(query);
		} else	{
			GeneralUser[] users =requestDatastoreForUsers(longitude, latitude, radius).toArray(new GeneralUser[0]); 
			CacheCls.getUserCache().put(query, users);
			return users;
		}

	}

	@GET
	@Path("/places")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralPlace[] retrieveAllPlaces(@QueryParam("longitude") double longitude, @QueryParam("latitude")double latitude, @QueryParam("radius")long radius) {
		ServerQuery query  = new ServerQuery(latitude, longitude, radius, ServerQuery.DataType.PLACES);
		if (CacheCls.getPlaceCache().containsKey(query))	{
			return (GeneralPlace[])CacheCls.getPlaceCache().get(query);
		} else	{
			GeneralPlace[] places =requestDatastoreForPlaces(longitude, latitude, radius).toArray(new GeneralPlace[0]); 
			CacheCls.getPlaceCache().put(query, places);
			return places;
		}
	}
	
	@GET
	@Path("/placesbyowner")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralPlace[] retrieveAllOwnerPlaces(@QueryParam("owner") String owner) {
		/* REWRITE */
		return null;
		
	}
	
	

	@POST
	@Path("/deleteplace")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removePlace(GeneralPlace place) {
		if(place == null || place.getId()==null){
			return;
		}

		GeneralPlace placex = pm.getObjectById(GeneralPlace.class, place.getId());
		try{
		if(placex!=null){
			CacheCls.getPlaceCache().clear();
			pm.deletePersistent(placex);
			return;
		}
		}finally{
			pm.close();
		}

	}

	private List<GeneralUser> requestDatastoreForUsers(double longitude,double latitude,long radius){
		//GeocellQuery baseQuery = new GeocellQuery("select from UserLocation");
		GeocellQuery baseQuery = new GeocellQuery();
		Point center = new Point (latitude, longitude);
		JDOGeocellQueryEngine qe = new JDOGeocellQueryEngine();
		qe.setPersistenceManager(pm);
		List<GeneralUser> results = GeocellManager.proximitySearch(center, 100000000, radius, GeneralUser.class, baseQuery, qe, DEFAULT_SEARCH_RESOlUTION);
		return results;
	}
	
	private List<GeneralPlace> requestDatastoreForPlaces(double longitude,double latitude,long radius){
		GeocellQuery baseQuery = new GeocellQuery();
		Point center = new Point (latitude, longitude);
		JDOGeocellQueryEngine qe = new JDOGeocellQueryEngine();
		qe.setPersistenceManager(pm);
		List<GeneralPlace> results = GeocellManager.proximitySearch(center, 100000000, radius, GeneralPlace.class, baseQuery, qe, DEFAULT_SEARCH_RESOlUTION);
		return results;
	}
}



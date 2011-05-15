package il.ac.tau.team3.prayerjersy;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import il.ac.tau.team3.common.GeneralPlace;
import il.ac.tau.team3.common.GeneralUser;
import il.ac.tau.team3.common.PlaceAndUser;
import il.ac.tau.team3.common.Pray;
import il.ac.tau.team3.common.SPGeoPoint;
import il.ac.tau.team3.datastore.PMF;
import il.ac.tau.team3.datastore.PlaceLocation;
import il.ac.tau.team3.datastore.UserLocation;


import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.GeocellQueryEngine;
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
			UserLocation userx = pm.getObjectById(UserLocation.class, id);
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
	public Long UpdateUserLocationByName(GeneralUser user){
		Query q = pm.newQuery(UserLocation.class, "this.name='"+user.getName()+"'");
		CacheCls.getUserCache().clear(); 
		CacheCls.getPlaceCache().clear();
		Long id;
		try{
			
			Collection<UserLocation> list = (Collection<UserLocation>) q.execute();
			if(list.isEmpty()){

				return this.createUserInDS(user.getSpGeoPoint().getLongitudeInDegrees(), user.getSpGeoPoint().getLatitudeInDegrees(), user.getName(), user.getStatus() , user.getFirstName(),user.getLastName());
				

			}

			UserLocation userx = list.iterator().next();

			userx.setLatitude(user.getSpGeoPoint().getLatitudeInDegrees());
			userx.setLongitude(user.getSpGeoPoint().getLongitudeInDegrees());
			userx.setGeocells(user.getSpGeoPoint().getLatitudeInDegrees(), user.getSpGeoPoint().getLongitudeInDegrees());
			userx.setStatus(user.getStatus());
			userx.setStatus(user.getFirstName());
			userx.setStatus(user.getLastName());
			id = userx.getKey();
		}
		finally{
			q.closeAll();
			pm.close();
			
		}

		return id;
		
	}

	//	@GET
	//	@Path("/getuserbymail")
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public Long  GetUserByMailAccount(@QueryParam("account") String account){
	//		//Query q = entity.createQuery("SELECT x FROM UserLocation x WHERE x.name='"+account+"'");
	//		Query q = entity.createQuery("SELECT x FROM UserLocation x WHERE x.name=?1");
	//		q.setParameter (1, account);
	//		List<UserLocation> list = q.getResultList();
	//		if(!list.isEmpty()){
	//			return list.get(0).getKey();
	//			 
	//		}
	//		return (long) -1;
	//	}

	@POST
	@Path("/updateplacebylocation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Long UpdatePlaceLocationByLocation(GeneralPlace place){

	
		CacheCls.getPlaceCache().clear();
		return createPlaceInDS(place);
	
	}

	@POST
	@Path("/addjoiner")
	@Consumes(MediaType.APPLICATION_JSON)
	public void UpdatePlaceJoinersByName_Add(PlaceAndUser pau){
		GeneralUser user = pau.getUser();
		GeneralPlace place = pau.getPlace();
		boolean praysWishes[] = pau.getPraysWishes();

		if(place == null || user == null){
			return;
		}

		PlaceLocation placex = pm.getObjectById(PlaceLocation.class, place.getId());
		try{
			if(placex!=null){
				//URI u = UriBuilder.fromResource(PlaceLocation.class).build(placex);
				CacheCls.getPlaceCache().clear();
				if(praysWishes[0]){
					if(!placex.getPraysOfTheDay().get(0).isJoinerSigned(user)){
						placex.getPraysOfTheDay().get(0).addJoiner(user);
						JDOHelper.makeDirty(placex, "praysOfTheDay");
					}
				}
				if(praysWishes[1]){
					if(!placex.getPraysOfTheDay().get(1).isJoinerSigned(user)){
						placex.getPraysOfTheDay().get(1).addJoiner(user);
						JDOHelper.makeDirty(placex, "praysOfTheDay");
					}
				}
				if(praysWishes[2]){
					if(!placex.getPraysOfTheDay().get(2).isJoinerSigned(user)){
						placex.getPraysOfTheDay().get(2).addJoiner(user);
						JDOHelper.makeDirty(placex, "praysOfTheDay");
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
		if(place == null || user == null){
			return;
		}

		PlaceLocation placex = pm.getObjectById(PlaceLocation.class, place.getId());
		try{
			if(placex!=null){
				CacheCls.getPlaceCache().clear();

				if(praysWishes[0]){
					if(placex.getPraysOfTheDay().get(0).isJoinerSigned(user)){
						placex.getPraysOfTheDay().get(0).removeJoiner(user);
						JDOHelper.makeDirty(placex, "praysOfTheDay");
					}
				}
				if(praysWishes[1]){
					if(placex.getPraysOfTheDay().get(1).isJoinerSigned(user)){
						placex.getPraysOfTheDay().get(1).removeJoiner(user);
						JDOHelper.makeDirty(placex, "praysOfTheDay");
					}
				}
				if(praysWishes[2]){
					if(placex.getPraysOfTheDay().get(2).isJoinerSigned(user)){
						placex.getPraysOfTheDay().get(2).removeJoiner(user);
						JDOHelper.makeDirty(placex, "praysOfTheDay");
											
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
			GeneralUser[] users =convertServerUserObjToClientUserObj(requestDatastoreForUsers(longitude, latitude, radius)).toArray(new GeneralUser[0]); 
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
			GeneralPlace[] places =convertServerPlaceObjToClientPlaceObj(requestDatastoreForPlaces(longitude, latitude, radius)).toArray(new GeneralPlace[0]); 
			CacheCls.getPlaceCache().put(query, places);
			return places;
		}
	}
	
	@GET
	@Path("/placesbyowner")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralPlace[] retrieveAllOwnerPlaces(@QueryParam("owner") String owner) {
		Query q = pm.newQuery(PlaceLocation.class);
		Collection<PlaceLocation> list = (Collection<PlaceLocation>) q.execute();
		List<GeneralPlace> tmp =  new ArrayList<GeneralPlace>();
		for ( PlaceLocation placex : list){
			if(placex.getOwner().getName().equals(owner)){
				tmp.add(new GeneralPlace(placex));
			}
		}
		GeneralPlace[] returnVal = new GeneralPlace[tmp.size()];
		if(null != tmp){
		for(int i = 0 ; i < tmp.size(); ++i){
			returnVal[i] = tmp.get(i);
		}
		}
		return returnVal;
		
	}
	/*
	@GET
	@Path("/newuser")
	@Produces(MediaType.APPLICATION_JSON)
	public Long CreateNewUser(@QueryParam("longitude") double longitude, @QueryParam("latitude")double latitude, @QueryParam("name")String name,@QueryParam("status")String status) {
		CacheCls.getUserCache().clear();
		return this.createUserInDS(longitude, latitude, name, status);
	}
	 */
	/*@GET
	@Path("/newplace")
	@Produces(MediaType.APPLICATION_JSON)
	public Long CreateNewPlace(@QueryParam("longitude") double longitude, @QueryParam("latitude")double latitude, @QueryParam("name")String name,@QueryParam("status")String address) {
		GeneralPlace u = new GeneralPlace(name,address,new SPGeoPoint((int)latitude*1000000, (int)longitude*1000000));

		PlaceLocation uloc = new PlaceLocation(u);
		entity.getTransaction().begin();
		entity.persist(uloc);
		entity.getTransaction().commit();

		return uloc.getKey();
	}*/

	//	@GET
	//	@Path("/signtoplace")
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public boolean SignToPlace(@QueryParam("id") long id,  @QueryParam("name")String name) {
	//		entity.getTransaction().begin();
	//		PlaceLocation placex = entity.find(PlaceLocation.class, id);
	//		if(placex!=null){
	//			CacheCls.getPlaceCache().clear();
	//			placex.addJoiner(name);
	//			entity.getTransaction().commit();
	//			return true;
	//		}
	//		return false;
	//	}

	/*	@GET
	@Path("/place/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GeneralPlace retrievePlace(@PathParam("id") int id) {
		if (id < places.size()) return places.get(id);
		else return null;
	}
	 */
	
	/*
	@GET
	@Path("/deleteuser")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean removeUser(@QueryParam("id") long id) {
		entity.getTransaction().begin();
		UserLocation userx = entity.find(UserLocation.class, id);
		if(userx!=null){
			CacheCls.getUserCache().clear();
			entity.remove(userx); 
			entity.getTransaction().commit();
			return true;
		}
		return false;
	}
*/
	

	@POST
	@Path("/deleteplace")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removePlace(GeneralPlace place) {
		if(place == null || place.getId()==null){
			return;
		}


		PlaceLocation placex = pm.getObjectById(PlaceLocation.class, place.getId());
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

	private List<UserLocation> requestDatastoreForUsers(double longitude,double latitude,long radius){
		//GeocellQuery baseQuery = new GeocellQuery("select from UserLocation");
		GeocellQuery baseQuery = new GeocellQuery();
		Point center = new Point (latitude, longitude);
		JDOGeocellQueryEngine qe = new JDOGeocellQueryEngine();
		qe.setPersistenceManager(pm);
		List<UserLocation> results = GeocellManager.proximitySearch(center, 100000000, radius, UserLocation.class, baseQuery, qe, DEFAULT_SEARCH_RESOlUTION);
		return results;
	}
	
	private List<PlaceLocation> requestDatastoreForPlaces(double longitude,double latitude,long radius){
		GeocellQuery baseQuery = new GeocellQuery();
		Point center = new Point (latitude, longitude);
		JDOGeocellQueryEngine qe = new JDOGeocellQueryEngine();
		qe.setPersistenceManager(pm);
		List<PlaceLocation> results = GeocellManager.proximitySearch(center, 100000000, radius, PlaceLocation.class, baseQuery, qe, DEFAULT_SEARCH_RESOlUTION);
		return results;
	}


	private List<GeneralUser> convertServerUserObjToClientUserObj(List<UserLocation> serverUsers){
		List<GeneralUser> returnList = new ArrayList<GeneralUser>();
		for(UserLocation serverUser : serverUsers){
			GeneralUser tmp = new GeneralUser(serverUser.getName(),new SPGeoPoint((int)(serverUser.getLatitude()*1000000), (int)(serverUser.getLongitude()*1000000)),serverUser.getStatus());
			tmp.setId(serverUser.getKey());
			returnList.add(tmp);

		}
		return returnList;
	}

	private List<GeneralPlace> convertServerPlaceObjToClientPlaceObj(List<PlaceLocation> serverPlaces){
		List<GeneralPlace> returnList = new ArrayList<GeneralPlace>();
		if(serverPlaces.size() != 0 ){
			for(PlaceLocation serverPlace : serverPlaces){
				GeneralPlace tmp = new GeneralPlace(serverPlace.getName(),serverPlace.getAddress(),new SPGeoPoint((int)(serverPlace.getLatitude()*1000000), (int)(serverPlace.getLongitude()*1000000)));
				tmp.setId(serverPlace.getKey());
			
				tmp.setPraysOfTheDay((Pray[])serverPlace.getPraysOfTheDay().toArray(new Pray[0]));
				tmp.setPrays(serverPlace.getPrays());
				tmp.setOwner(serverPlace.getOwner());
				returnList.add(tmp);

			}
		}
		return returnList;
	}

	private Long createUserInDS(double longitude, double latitude, String name,String status, String firstName, String lastName){
		GeneralUser u = new GeneralUser(name,new SPGeoPoint((int)(latitude*1000000), (int)(longitude*1000000)),status,firstName,lastName);
		UserLocation uloc = new UserLocation(u);
		Long id;
		try{
		pm.makePersistent(uloc);
		id = uloc.getKey();
		}
		finally{
			pm.close();
		}
		return id;
	}

	private Long createPlaceInDS(GeneralPlace place){
		
		PlaceLocation uloc = new PlaceLocation(place);
		Long id;
		try{
		pm.makePersistent(uloc);
		id = uloc.getKey();
		}
		finally{
			pm.close();
		}
		return id;
	}
}

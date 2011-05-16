package il.ac.tau.team3.common;

import il.ac.tau.team3.datastore.PMF;
import il.ac.tau.team3.datastore.PlaceLocation;
import il.ac.tau.team3.datastore.UserLocation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import org.codehaus.jackson.annotate.JsonGetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSetter;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Pray  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6975030575322925440L;
	
	@JsonIgnore
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Date startTime;
	@Persistent
	private Date endTime;
	@Persistent
	String name;
	@Persistent(dependentElement = "true", defaultFetchGroup="true")
	private ArrayList<Long> joinersId;
	
	@NotPersistent
	private ArrayList<GeneralUser> joiners;
	
	@Persistent
	@JsonIgnore
	PlaceLocation place;
	
	
	@JsonIgnore
	public PlaceLocation getPlace() {
		return place;
	}

	@JsonIgnore
	public void setPlace(PlaceLocation place) {
		this.place = place;
	}

	public Pray(Date startTime, Date endTime, String name,
			ArrayList<Long> joiners) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.joinersId = joiners;
	}
	
	public Pray() {
		super();
		this.startTime = new Date();
		this.endTime =  new Date();
		this.name = "";
		this.joinersId = new ArrayList<Long>();
	}


	public Pray(Pray pray) {
		// TODO Auto-generated constructor stub
		if (null != pray.startTime)	{
			this.startTime = pray.startTime;
		} else	{
			this.startTime = new Date();
		}
		if (null != pray.endTime)	{
			this.endTime = pray.endTime;
		} else	{
			this.endTime = new Date();
		}
		if (pray.name != null)	{
			this.name = pray.name;
		} else	{
			this.name = "";
		}
		if (pray.joinersId !=null)	{
			this.joinersId = new ArrayList<Long>(pray.joinersId);
			//Collections.copy(this.joiners, pray.joiners);
			
		} else	{
			this.joinersId = new ArrayList<Long>();
		}
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public ArrayList<Long> getJoinersId() {
		return joinersId;
	}

	/**
	 * @param joiners
	 */
	public void setJoinersId(ArrayList<Long> joiners) {
		this.joinersId = joiners;
	}
	
	public boolean isJoinerSigned(GeneralUser joiner){
		return this.getJoiners().contains(joiner);
	}
	
	public int numberOfJoiners(){
		return this.joinersId.size();
	}
	
	public void addJoiner(GeneralUser user){
		this.joinersId.add(UserLocation.getUserByName(user.getName()).getKey());
	}
	
	public void removeJoiner(GeneralUser user){
		if(this.getJoiners().contains(user)){
			this.joinersId.remove(UserLocation.getUserByName(user.getName()).getKey());
		}
	}

	public void setKey(Key key) {
		this.key = key;
	}

	@JsonIgnore
	public Key getKey() {
		return key;
	}

	@JsonSetter("joiners")
	public void setJoiners(ArrayList<GeneralUser> joiners) {
		this.joiners = joiners;
		for (GeneralUser gu : joiners)	{
        	joinersId.add(UserLocation.getUserByName(gu.getName()).getKey());
        }
	}

	public ArrayList<GeneralUser> getJoiners() {
		joiners = new ArrayList<GeneralUser>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		for (long l : joinersId)	{
			joiners.add(new GeneralUser(pm.getObjectById(UserLocation.class, l)));
		}
		return joiners;
	}


	
}

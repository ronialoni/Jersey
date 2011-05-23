package il.ac.tau.team3.common;

import il.ac.tau.team3.datastore.PMF;

import java.io.Serializable;
import java.util.Date;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
	GeneralPlace place;
	
	
	@JsonIgnore
	public GeneralPlace getPlace() {
		return place;
	}

	@JsonIgnore
	public void setPlace(GeneralPlace place) {
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
		return this.getJoinersId().contains(joiner.getId());
	}
	
	public int numberOfJoiners(){
		return this.joinersId.size();
	}
	
	public void addJoiner(GeneralUser user){
		this.joinersId.add(user.getId());
	}
	
	public void removeJoiner(GeneralUser user){
		if(this.getJoinersId().contains(user.getId())){
			this.joinersId.remove(user.getId());
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
        	joinersId.add(gu.getId());
        }
	}

	public ArrayList<GeneralUser> getJoiners() {
		joiners = new ArrayList<GeneralUser>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		for (long l : joinersId)	{
			joiners.add(pm.getObjectById(GeneralUser.class, l));
		}
		return joiners;
	}


	
}

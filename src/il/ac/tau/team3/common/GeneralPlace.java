package il.ac.tau.team3.common;

import il.ac.tau.team3.datastore.PMF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
@PersistenceCapable
public class GeneralPlace extends GeneralLocation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3680632953183211194L;
	
	@Persistent
	private String address;
	
	@NotPersistent
	private GeneralUser owner;
	
	@JsonIgnore
	private long		ownerId; 

	@Persistent
	private List<Pray> praysOfTheDay;

	@Persistent
	private Date startDate;
	@Persistent
	private Date endDate;






	public GeneralPlace(){
		super();
		this.praysOfTheDay = new ArrayList<Pray>(3);
		this.startDate = new Date();
		this.endDate = new Date();
	}
	
	public GeneralPlace(GeneralPlace p){
		super();
		cloneUserData(p);
		this.setId(p.getId());
	}
	
	public void cloneUserData(GeneralPlace obj)	{
		super.closeUserData(obj);
		this.setPraysOfTheDay(obj.getPraysOfTheDay());
		this.setAddress(obj.getAddress());
		this.setEndDate(obj.getEndDate());
		this.setStartDate(obj.getStartDate());
		this.setOwnerId(obj.getOwnerId());
		this.setOwner(obj.getOwner());
		
	}

	public GeneralPlace(String name, String address , SPGeoPoint spGeoPoint){
		super(spGeoPoint,name);
		this.address = address;
		this.praysOfTheDay = new ArrayList<Pray>(3);
		this.startDate = new Date();
		this.endDate = new Date();
	}
	

	public GeneralPlace(GeneralUser owner, String name, String address , SPGeoPoint spGeoPoint, Date startDate,Date endDate){
		super(spGeoPoint,name);
		this.address = address;
		this.praysOfTheDay = new ArrayList<Pray>(3);
		this.owner = owner;
		this.startDate = startDate;
		this.endDate = endDate;
	}


	@JsonIgnore
	public void setPraysOfTheDay(int prayNumber, Pray praysOfTheDay) {
		if(prayNumber < 0 || prayNumber >= this.praysOfTheDay.size()){
			return ;
		}
		this.praysOfTheDay.set(prayNumber, praysOfTheDay);
	}
	
	public void setPraysOfTheDay(List<Pray> praysOfTheDay)	{
		this.praysOfTheDay = praysOfTheDay;
		
	}
	
	public List<Pray> getPraysOfTheDay()	{
		return this.praysOfTheDay;
		
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public GeneralUser getOwner() {
		if (null == owner)	{
			owner = PMF.get().getPersistenceManager().getObjectById(GeneralUser.class, ownerId);
		}
		return owner;
	}

	public void setOwner(GeneralUser owner) {
		this.owner = owner;
		if (owner != null)	{
			this.ownerId = owner.getId();
		}
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



	@JsonIgnore
	public boolean IsJoinerSigned(int prayNumber, GeneralUser joiner){
		if(prayNumber < 0 || prayNumber >= praysOfTheDay.size()){
			return false;
		}
		return (this.praysOfTheDay.get(prayNumber).isJoinerSigned(joiner));
	}

	@JsonIgnore  
	public int getNumberOfPrayers(int prayNumber){
		return this.praysOfTheDay.get(prayNumber).numberOfJoiners();
	}

	public void setOwnerId(Long ownerId) {
		if (null != ownerId)	{
			this.ownerId = ownerId;
		} else if (null != owner)	{
			this.ownerId = owner.getId();
		}
	}

	@JsonIgnore
	public long getOwnerId() {
		return ownerId;
	}


}





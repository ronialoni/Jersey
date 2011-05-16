
package il.ac.tau.team3.common;

import il.ac.tau.team3.datastore.UserLocation;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.codehaus.jackson.annotate.JsonIgnore;

@PersistenceCapable
public class GeneralUser extends GeneralLocation implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = -8100017600787664519L;
		@Persistent
		private String status;
		@Persistent
		private String firstName;
		@Persistent
		private String lastName;
		
		public GeneralUser()	{
			super();
		}
		
		public GeneralUser(String name, SPGeoPoint spGeoPoint, String status) {
			super(spGeoPoint,name);
			
			this.status = status;
		}
		
		public GeneralUser(UserLocation serverObj) {
			this(serverObj.getName(), new SPGeoPoint((int)(serverObj.getLatitude()*1000000), 
						(int)(serverObj.getLongitude()*1000000)), serverObj.getStatus(), serverObj.getFirstName(), serverObj.getLastName());
			

		}
		
		public GeneralUser(String name, SPGeoPoint spGeoPoint, String status, String firstName, String lastName) {
			super(spGeoPoint,name);
			this.firstName = firstName;
			this.lastName = lastName;
			this.status = status;
		}
	
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		@JsonIgnore
		public String getFullName() {
			if (this.firstName == null && this.lastName == null){
				return null;
			}
			
			return (this.firstName == null? "" : (this.firstName + " "))  + (this.lastName == null? "" : this.lastName);
		}
		
		
		@JsonIgnore
		@Override
		public boolean equals(Object o)	{
			if (o == this) {
				return true;
			}
			
			if (o == null)	{
				return false;
			}
			
			if (!(o instanceof GeneralUser))	{
				return false;
			}
			
			GeneralUser other = (GeneralUser)o;
			
			if ((null != getId()) && (null != other.getId()))	{
				return this.getId().equals(other.getId());
			}
			
			return this.getName().equals(other.getName());
			
		}
		
		
}

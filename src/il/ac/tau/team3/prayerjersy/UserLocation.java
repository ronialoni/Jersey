package il.ac.tau.team3.prayerjersy;

import com.google.appengine.api.datastore.Key;

import il.ac.tau.team3.common.GeneralUser;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

    private GeneralUser user;

	public UserLocation(GeneralUser user) {
		
		this.user = user;
	}


    // Accessors for the fields. JPA doesn't use these, but your application does.

    public GeneralUser getUser() {
		return user;
	}



	public void setUser(GeneralUser user) {
		this.user = user;
	}



	public void setKey(Key key) {
		this.key = key;
	}






	public Key getKey() {
        return key;
    }

  
}
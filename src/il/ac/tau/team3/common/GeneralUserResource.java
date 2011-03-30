package il.ac.tau.team3.common;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;



@Path("/prayerjersy")
public interface GeneralUserResource {
    @GET
    @Produces("application/json")
    public GeneralUser retrieve(@QueryParam("id") int id);
    
    @GET
    @Produces("application/json")
    public Integer getNumUsers();

    @PUT
    @Consumes("application/json")
    public void store(GeneralUser user);

    @DELETE
    @Consumes("application/json")
    public void remove(GeneralUser user);
}

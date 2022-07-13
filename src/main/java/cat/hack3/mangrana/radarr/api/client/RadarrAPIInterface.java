package cat.hack3.mangrana.radarr.api.client;

import cat.hack3.mangrana.radarr.api.client.response.RootFolder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v3")
public interface RadarrAPIInterface {

    @GET
    @Path("/rootfolder")
    @Produces({ MediaType.APPLICATION_JSON })
    List<RootFolder> getRootFolders(@QueryParam("apikey") String apikey);

    @GET
    @Path("/rootfolder/{rootFolderId}")
    @Produces({ MediaType.APPLICATION_JSON })
    RootFolder getRootFolder(@PathParam("rootFolderId") int rootFolderId, @QueryParam("apikey") String apikey);

}
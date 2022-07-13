package cat.hack3.mangrana.radarr.api.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;

public class RadarrAPIInterfaceBuilder {
    static final String API_KEY = "b2f71888fb1e4f03a9214d8df71df08c"; //this Is an Old Api Key ;-)
    static final String HOST = "https://your.host.is";

    RadarrAPIInterface buildProxy() {
        System.out.println("preparing Radarr API call at host "+ HOST + " ...");
        UriBuilder fullPath = UriBuilder.fromPath(HOST);
        ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        ResteasyWebTarget target = client.target(fullPath);
        return target.proxy(RadarrAPIInterface.class);
    }
}

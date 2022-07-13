package cat.hack3.mangrana.radarr.api.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
public class RootFolder {

    private String id;
    private String path;
    private List<MovieFolder> unmappedFolders;

    @Override
    public String toString() {
        String listOutput = Objects.nonNull(unmappedFolders) ? unmappedFolders.size() +" elements" : "-no elements-";
        return "RootFolder{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", unmappedFolders=" + listOutput +'}';
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public List<MovieFolder> getUnmappedFolders() {
        return unmappedFolders;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUnmappedFolders(List<MovieFolder> unmappedFolders) {
        this.unmappedFolders = unmappedFolders;
    }
}

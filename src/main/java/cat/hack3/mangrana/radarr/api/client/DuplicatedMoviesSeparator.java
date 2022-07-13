package cat.hack3.mangrana.radarr.api.client;

import cat.hack3.mangrana.exception.IncorrectWorkingPathException;
import cat.hack3.mangrana.radarr.api.client.response.MovieFolder;
import cat.hack3.mangrana.radarr.api.client.response.RootFolder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

import static cat.hack3.mangrana.radarr.api.client.RadarrAPIInterfaceBuilder.API_KEY;

public class DuplicatedMoviesSeparator {

    String radarrContainerMoviesPath = "/movies";
    String appMoviesPath = "/movies/radarr_Pelis_duplicades";

    RadarrAPIInterfaceBuilder apiInterfaceBuilder;

    public static void main(String[] args) {
        DuplicatedMoviesSeparator duplicatedMoviesSeparator = new DuplicatedMoviesSeparator();
        duplicatedMoviesSeparator.process();
    }

    private void process() {
        try {
            checkDirectory();
            separateDuplicatedFromAllRootFolders();
        } catch (IncorrectWorkingPathException | IOException e) {
            e.printStackTrace();
            if (e.getMessage().contains("HTTP 504")) {
                log("Found a timeout, then retrying...");
                process();
            }
        } finally {
            log("thread process finished");
        }
    }

    private DuplicatedMoviesSeparator() {
        log("Hi my friends, here the duplicated movies separator ;) enjoy");
        apiInterfaceBuilder = new RadarrAPIInterfaceBuilder();
    }

    private void checkDirectory() throws IncorrectWorkingPathException, IOException {
        Path path = Paths.get(radarrContainerMoviesPath);
        log("Checking if the movies are in the path: "+radarrContainerMoviesPath);
        if (!Files.exists(path)) throw new IncorrectWorkingPathException("The path not exist");
        if (!Files.isDirectory(path)) throw new IncorrectWorkingPathException("The path not a directory");
        if (!Files.isWritable(path)) throw new IncorrectWorkingPathException("The path not writable");
        if (FileUtils.isEmptyDirectory(new File(radarrContainerMoviesPath))) throw new IncorrectWorkingPathException("The path not contains anything");
        log("all good :D");
    }

    @SuppressWarnings("unused")
    private void separateDuplicatedFromAllRootFolders() {
        log("going to retrieve ALL root folders info");
        RadarrAPIInterface proxy = apiInterfaceBuilder.buildProxy();
        List<RootFolder> rootFolders = proxy.getRootFolders(API_KEY);
        rootFolders.forEach(this::separateDuplicatedMoviesFolder);
    }

    @SuppressWarnings("unused")
    private void testOneRootFolder() {
        log("going to retrieve only a single folder info");
        RadarrAPIInterface proxy = apiInterfaceBuilder.buildProxy();
        RootFolder rootFolder = proxy.getRootFolder(47, API_KEY);
        separateDuplicatedMoviesFolder(rootFolder);
    }

    private void separateDuplicatedMoviesFolder(RootFolder folder) {
        log("Going to work with the object: "+ folder.toString());
        folder.getUnmappedFolders().forEach(this::separateDuplicatedMovie);
    }

    private void separateDuplicatedMovie(MovieFolder movieFolder) {
        String newPath = movieFolder.getPath()
                .replaceFirst(radarrContainerMoviesPath, appMoviesPath)
                .replaceFirst("\\/[A-Z]\\/", "/");
        log(MessageFormat.format("Will hard move from < {0} > to < {1} >", movieFolder.getPath(), newPath));
        try {
            FileUtils.moveDirectoryToDirectory(
                    new File(movieFolder.getPath()),
                    new File(newPath),
                    true);
            log("moved");
        } catch (IOException e) {
            log("error found while trying to move :( --> "+ e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void log (String msg) {
        System.out.println(msg);
    }

}

package client.utils;

import com.google.gson.Gson;
import commons.Activity;
import commons.ActivityJson;
import jakarta.ws.rs.WebApplicationException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class ImportCallable implements Callable<Boolean> {

    private final ServerUtils server;
    private final String pathFieldText;

    public ImportCallable(ServerUtils server, String pathFieldText) {
        this.server = server;
        this.pathFieldText = pathFieldText;
    }

    @Override
    public Boolean call() throws Exception {
        if (!pathFieldText.endsWith(".json") && !pathFieldText.isEmpty()) {
            throw new IllegalArgumentException("File needs to be a json file!");
        }
        Reader file = Files.newBufferedReader(Paths.get(pathFieldText));
        Gson gson = new Gson();
        ActivityJson[] activityArray = gson.fromJson(file, ActivityJson[].class);
        for (ActivityJson activityJson : activityArray) {
            addActivity(activityJson);
        }
        file.close();
        return true;
    }

    /**
     * Method to act an Activity to the server and throw the correct Exception on fail.
     * @param activityJson The Activity to add.
     * @throws Exception Throws exception bases on the activity that caused it
     */
    //Wattage == 0 is not a valid fail option after bug fix in dev!
    private void addActivity(ActivityJson activityJson) throws Exception {
        Activity activity = activityJson.convertToActivity();
        try {
            server.addActivity(activity);
        } catch (WebApplicationException e) {
            if (activity == null) {
                throw new Exception("Activity is null and could not be added!");
            } else if (activity.getTitle() == null) {
                throw new Exception("Activity " + activityJson.getId() +
                        " could not be added as the title is null!");
            } else {
                throw new Exception("Activity " + activityJson.getId() +
                        " could not be added for unknown reason!");
            }
        }
    }


}

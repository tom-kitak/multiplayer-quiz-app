package client.utils;

import client.scenes.ImportActivityCtrl;
import com.google.gson.Gson;
import commons.Activity;
import commons.ActivityJson;
import jakarta.ws.rs.WebApplicationException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class ImportCallable implements Callable<String> {

    private final ServerUtils server;
    private final String pathFieldText;
    private final ImportActivityCtrl ctrl;


    public ImportCallable(ServerUtils server, String pathFieldText, ImportActivityCtrl ctrl) {
        this.server = server;
        this.pathFieldText = pathFieldText;
        this.ctrl = ctrl;
    }

    @Override
    public String call() throws Exception {
        StringBuilder exceptions = new StringBuilder();
        if (!pathFieldText.endsWith(".json") && !pathFieldText.isEmpty()) {
            ctrl.setProgress(1);
            return "File needs to be a json file! (end with .json)";
        } else if (pathFieldText.isEmpty()) {
            ctrl.setProgress(1);
            return "Path cannot be empty!";
        }
        Reader file = Files.newBufferedReader(Paths.get(pathFieldText));
        Gson gson = new Gson();
        ActivityJson[] activityArray = gson.fromJson(file, ActivityJson[].class);
        int length = activityArray.length;
        for (int i = 0; i < length; i++) {
            try {
                addActivity(activityArray[i]);
            } catch (Exception e) {
                exceptions.append(e.getMessage()).append(System.lineSeparator());
            }
            ctrl.setProgress((i + 1d) / length);
        }
        System.out.println("DONE!");
        ctrl.setProgress(1);
        file.close();
        return exceptions.toString();
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

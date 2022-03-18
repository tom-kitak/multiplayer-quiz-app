package client.utils;

import client.scenes.ImportActivityCtrl;
import com.google.gson.Gson;
import commons.Activity;
import jakarta.ws.rs.WebApplicationException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class ImportCallable implements Callable<String> {

    private final ServerUtils server;
    private final String pathFieldText;
    private final ImportActivityCtrl ctrl;


    /**
     * Constructor for ImportCallable.
     * @param server The serverUtils to use to send the activities.
     * @param pathFieldText The path of the activities.json file.
     * @param ctrl The control that called this import.
     */
    public ImportCallable(ServerUtils server, String pathFieldText, ImportActivityCtrl ctrl) {
        this.server = server;
        this.pathFieldText = pathFieldText;
        this.ctrl = ctrl;
    }

    /**
     * The callable's main method.
     * @return Returns a String with exceptions when it is done.
     * @throws Exception Throws an interruptException when the callable got interrupted.
     */
    @Override
    public String call() throws Exception {
        StringBuilder exceptions = new StringBuilder();
        // Check if the path is in the correct format.
        if (!pathFieldText.endsWith(".json") && !pathFieldText.isEmpty()) {
            ctrl.setProgress(1);
            return "File needs to be a json file! (end with .json)";
        } else if (pathFieldText.isEmpty()) {
            ctrl.setProgress(1);
            return "Path cannot be empty!";
        }
        // Prepare Reader to read the json file.
        Reader file = Files.newBufferedReader(Paths.get(pathFieldText));
        Gson gson = new Gson();
        // Will throw an JsonSyntaxException when Json is not in correct format.
        ActivityJson[] activityArray = gson.fromJson(file, ActivityJson[].class);
        int length = activityArray.length;
        // Iterate through the activities and add them to the server.

        for (int i = 0; i < length; i++) {
            try {
                addActivity(activityArray[i]);
            } catch (Exception e) {
                exceptions.append(e.getMessage()).append(System.lineSeparator());
            }
            // Let the ctrl know the progress.
            ctrl.setProgress((i + 1d) / length);
        }
        // USED FOR DEBUGGING TO KNOW WHEN THE ACTIVITY IMPORT IS DONE
        System.out.println("DONE!");
        ctrl.setProgress(1);
        file.close();
        return exceptions.toString();
    }

    /**
     * Method to act an Activity to the server and throw the correct Exception on fail.
     * @param activityJson The Activity to add.
     * @throws Exception Throws exception bases on the activity and reason that caused it.
     */
    private void addActivity(ActivityJson activityJson) throws Exception {
        // Convert activityJson to Activity class.
        Activity activity = activityJson.convertToActivity(pathFieldText);
        // Try to add activity to server, on fail handle and throw exception with correct message.
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

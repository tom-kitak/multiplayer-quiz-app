package client.utils;

import commons.Activity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

public class ActivityJson {
    private String id;
    private String image_path;
    private String title;
    private long consumption_in_wh;
    private String source;

    /**
     * Method to convert this class to Activity.
     * @param path The path of the Json file were this object was read from.
     * @return This object as Activity.
     */
    public Activity convertToActivity(String path) throws Exception {
        return new Activity(title, consumption_in_wh, readImage(path));
    }

    /**
     * Method to read the image of this activity to a byte[].
     * @param pathString Absolute path of json file were this object was read from.
     * @return The byte[] of the image that was read
     */
    private byte[] readImage(String pathString) throws Exception {
        String path = Paths.get(pathString).getParent().toString() + '\\' + image_path;
        try {
            return new FileInputStream(path).readAllBytes();
        } catch (FileNotFoundException e) {
            throw new Exception("The image for " + getId() + " could not be found!");
        } catch (IOException e) {
            throw new Exception("The activity " + getId() + " could not be added, due to a error with the image!");
        }
    }

    /**
     * Getter for the id. Used for identification when an error occurs.
     * @return The id of this object.
     */
    public String getId() {
        return id;
    }
}

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
    public Activity convertToActivity(String path) {
        return new Activity(title, consumption_in_wh, readImage(path));
    }

    /**
     * Method to read the image of this activity to a byte[].
     * @param pathString Absolute path of json file were this object was read from.
     * @return The byte[] of the image that was read
     */
    //ToDo Handle Exceptions
    private byte[] readImage(String pathString) {
        String path = Paths.get(pathString).getParent().toString() + '\\' + image_path;
        try {
            return new FileInputStream(path).readAllBytes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Getter for the id. Used for identification when an error occurs.
     * @return The id of this object.
     */
    public String getId() {
        return id;
    }
}

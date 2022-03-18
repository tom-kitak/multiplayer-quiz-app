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

    public Activity convertToActivity(String path) {
        return new Activity(title, consumption_in_wh, readImage(path));
    }

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

    public String getId() {
        return id;
    }
}

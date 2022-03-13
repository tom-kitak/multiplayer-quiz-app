package commons;

public class ActivityJson {
    private String id;
    private String image_path;
    private String title;
    private long consumption_in_wh;
    private String source;

    public Activity convertToActivity() {
        return new Activity(title, consumption_in_wh);
    }

}

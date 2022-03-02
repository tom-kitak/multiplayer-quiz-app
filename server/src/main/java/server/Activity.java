package server;
//CHECKSTYLE:OFF
import javax.persistence.*;
//CHECKSTYLE:ON
import java.util.Objects;

@Entity
public class Activity {

    @Id
    public long id;

    private String title;
    private int value;
    private String source;

    @SuppressWarnings("unused")
    private Activity() {
        // object mappers - copied from the template
        // Don't delete this because otherwise the database doesn't work
    }

    public Activity(String title, int value, String source) {
        this.title = title;
        this.value = value;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public int getValue() {
        return value;
    }

    public String getSource() {
        return source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;
        Activity activity = (Activity) o;
        return getValue() == activity.getValue() && getTitle().equals(activity.getTitle()) && getSource().equals(activity.getSource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getValue(), getSource());
    }

    @Override
    public String toString() {
        return "Activity{" +
                "title='" + title + '\'' +
                ", value=" + value +
                ", source='" + source + '\'' +
                '}';
    }
}

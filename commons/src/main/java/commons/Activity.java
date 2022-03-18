package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;
    public long wh;
    @Lob
    public byte[] image;

    @SuppressWarnings("unused")
    private Activity() {
        //for object mappers
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Activity(String title, long wh, byte[] image) {
        this.title = title;
        this.wh = wh;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Activity activity)) return false;

        return new EqualsBuilder().append(id, activity.id)
                .append(wh, activity.wh).append(title, activity.title).append(image, activity.image)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(title).append(wh).append(image)
                .toHashCode();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getWh() {
        return wh;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWh(long wh) {
        this.wh = wh;
    }
}

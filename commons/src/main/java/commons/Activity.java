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

    /**
     * a constructor that is needed for Object mappers.
     */
    @SuppressWarnings("unused")
    private Activity() {
        //for object mappers
    }

    /**
     * Creates a new Activity Object.
     * @param title the title of the Activity
     * @param wh the watt hours of the Activity
     * @param image the byte array for the Image of the Activity
     */
    public Activity(String title, long wh, byte[] image) {
        this.title = title;
        this.wh = wh;
        this.image = image;
    }

    /**
     * @return the byte array that represents the image.
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the byte array for the image field.
     * @param image the byte array we want to set
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Checks o and this Activity for equality.
     * @param o the object we check for equality with.
     * @return true iff o is an Activity with equal fields
     * to this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Activity activity)) return false;

        return new EqualsBuilder().append(id, activity.id)
                .append(wh, activity.wh).append(title, activity.title).append(image, activity.image)
                .isEquals();
    }

    /**
     * @return an integer that represents the hashcode of this Activity.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(title).append(wh).append(image)
                .toHashCode();
    }

    /**
     * @return the id of this Activity.
     */
    public long getId() {
        return id;
    }


    /**
     * @return the String representing the title field of this Activity.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the integer representing the wh field of this Activity.
     */
    public long getWh() {
        return wh;
    }

    /**
     * Sets the id field of this Activity.
     * @param id the integer we want to assign to the id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Sets the title field of this Activity.
     * @param title the String value we want to assign to the title field
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the wh field of this Activity.
     * @param wh the integer we want to assign to the wh field
     */
    public void setWh(long wh) {
        this.wh = wh;
    }
}

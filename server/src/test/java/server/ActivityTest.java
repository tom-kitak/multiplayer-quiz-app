package server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActivityTest {
    @Test
    void getTitle() {
        Activity activity = new Activity("test", 5, "source");
        Assertions.assertEquals("test", activity.getTitle());
    }

    @Test
    void getValue() {
        Activity activity = new Activity("test", 5, "source");
        Assertions.assertEquals(5, activity.getValue());
    }

    @Test
    void getSource() {
        Activity activity = new Activity("test", 5, "source");
        Assertions.assertEquals("source",activity.getSource());
    }

    @Test
    void setTitle() {
        Activity activity = new Activity("test", 5, "source");
        activity.setTitle("test2");
        Assertions.assertEquals("test2", activity.getTitle());
    }

    @Test
    void setValue() {
        Activity activity = new Activity("test", 5, "source");
        activity.setValue(7);
        Assertions.assertEquals(7, activity.getValue());
    }

    @Test
    void setSource() {
        Activity activity = new Activity("test", 5, "source");
        activity.setSource("source2");
        Assertions.assertEquals("source2", activity.getSource());
    }

    @Test
    void testEquals() {
        Activity activity = new Activity("test", 5, "source");
        Activity activity2 = new Activity("test", 5, "source");
        Assertions.assertEquals(activity, activity2);
    }

    @Test
    void testToString() {
        Activity activity = new Activity("test", 5, "source");
        Assertions.assertEquals("Activity{title='test', value=5, source='source'}", activity.toString());
    }
}

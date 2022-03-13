package server;

import commons.Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActivityTest {
    @Test
    void getTitle() {
        Activity activity = new Activity("test", 5);
        Assertions.assertEquals("test", activity.getTitle());
    }

    @Test
    void getValue() {
        Activity activity = new Activity("test", 5);
        Assertions.assertEquals(5, activity.getWh());
    }

    @Test
    void setTitle() {
        Activity activity = new Activity("test", 5);
        activity.setTitle("test2");
        Assertions.assertEquals("test2", activity.getTitle());
    }

    @Test
    void setValue() {
        Activity activity = new Activity("test", 5);
        activity.setWh(7);
        Assertions.assertEquals(7, activity.getWh());
    }


    @Test
    void testEquals() {
        Activity activity = new Activity("test", 5);
        Activity activity2 = new Activity("test", 5);
        Assertions.assertEquals(activity, activity2);
        activity2.setWh(7);
        Assertions.assertNotEquals(activity, activity2);
    }

    @Test
    void testToString() {
        Activity activity = new Activity("test", 5);
        System.out.println(activity.toString());
        Assertions.assertNotNull(activity.toString());
    }

    @Test
    void getId() {
        Activity activity = new Activity("test", 5);
        Assertions.assertEquals(0,activity.getId());
    }


    @Test
    void setId() {
        Activity activity = new Activity("test", 5);
        activity.setId(7L);
        Assertions.assertEquals(7L, activity.getId());
    }
}

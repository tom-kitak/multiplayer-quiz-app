package server.database;

import commons.Activity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
class ActivityRepositoryTest {

    @Autowired
    ActivityRepository activityRepository;
    Activity activity;
    String imageString = "18763671972912763726319376237108";
    byte[] tempImage = imageString.getBytes(StandardCharsets.UTF_8);

    @BeforeEach
    void setUp(){

        String imageString = "18763671972912763726319376237108";
        byte[] tempImage = imageString.getBytes(StandardCharsets.UTF_8);
        activity = new Activity("a", (long) 5, tempImage);
    }

    @AfterEach
    void after(){
        activityRepository.deleteAll();
    }


    @Test
    void testSave(){
        activityRepository.save(activity);
        List<Activity> activityList = activityRepository.findAll();
        assertEquals(activity, activityList.get(0));
    }

    @Test
    void testFindAll(){
        activityRepository.save(activity);
        Activity ac = new Activity("b", (long) 1000, tempImage);
        activityRepository.save(ac);
        List<Activity> activities = activityRepository.findAll();
        assertTrue(activities.get(0).equals(activity)
                &&activities.get(1).equals(ac));
    }

    @Test
    void testFindById(){
        activityRepository.save(activity);
        Optional<Activity> activityFromDB = activityRepository.findById(activity.getId());

        assertTrue(activityFromDB.isPresent());
    }

    @Test
    void testFindById2(){
        activityRepository.save(activity);
        Optional<Activity> ac = activityRepository.findById(activity.id-1545);
        assertTrue(ac.isEmpty());
    }

    @Test
    void testExistsById(){
        activityRepository.save(activity);
        assertTrue(activityRepository.existsById(activity.getId()));
    }

    @Test
    void testDeleteById(){
        activityRepository.save(activity);
        activityRepository.deleteById(activity.id);
        assertFalse(activityRepository.existsById(activity.getId()));
    }

}
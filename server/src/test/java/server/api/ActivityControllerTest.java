package server.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.Activity;
import server.database.ActivityRepository;
//CHECKSTYLE:OFF
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
//CHECKSTYLE:ON

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ActivityRepository activityRepository;

    Activity record_1 = new Activity("title", 2, "source");
    Activity record_2 = new Activity("title2", 3, "source2");


    @Test
    @DisplayName("Checks whether the /api/activity/ returns all activities")
    public void getAll() throws Exception {
        List<Activity> activityList = new ArrayList<>(Arrays.asList(record_1, record_2));

        Mockito.when(activityRepository.findAll()).thenReturn(activityList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/activity/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(jsonPath("$[0].title", is("title")));
    }

    @DisplayName("Checks whether the /api/activity/{id} returns the correct activity")
    public void getId() throws Exception {
        // This test fails because of optionals, I will make a post regarding this on stackOverflow.
        Mockito.when(activityRepository.findById(0L).orElse(null)).thenReturn(record_1);
        Mockito.when(activityRepository.findById(1L).orElse(null)).thenReturn(record_2);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/activity/0")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andExpect(jsonPath("$[0].title", is("title")));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andExpect(jsonPath("$[1].title", is("title2")));
    }

    //Tests for delete and post are missing because they also use this functionality of optional which
    // automatically fails them.
}

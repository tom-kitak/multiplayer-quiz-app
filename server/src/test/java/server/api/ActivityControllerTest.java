package server.api;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import commons.Activity;
import server.database.ActivityRepository;
//CHECKSTYLE:OFF
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
//CHECKSTYLE:ON

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {
    //Temporary string to byte[] here for testing as pipeline will other-ways fail
    String imageString = "18763671972912763726319376237108";
    byte[] tempImage = imageString.getBytes(StandardCharsets.UTF_8);

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ActivityRepository activityRepository;

    Activity record_1 = new Activity("title", 2, tempImage);
    Activity record_2 = new Activity("title2", 3, tempImage);


    @Test
    @DisplayName("Checks whether the /api/activity/ returns all activities")
    public void getAll() throws Exception {
        List<Activity> activityList = new ArrayList<>(Arrays.asList(record_1, record_2));

        Mockito.when(activityRepository.count()).thenReturn(2L);
        Mockito.when(activityRepository.findAll()).thenReturn(activityList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/activity/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(jsonPath("$[0].title", is("title")));
    }

    @Test
    @DisplayName("Checks whether the /api/activity/{id} returns the correct activity")
    public void getId() throws Exception {

        Mockito.when(activityRepository.findById(0L)).thenReturn(Optional.of(record_1));
        Mockito.when(activityRepository.findById(1L)).thenReturn(Optional.of(record_2));
        Mockito.when(activityRepository.existsById(0L)).thenReturn(true);
        Mockito.when(activityRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/activity/0")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title", is("title")))
                        .andExpect(jsonPath("$.wh", is(2)));



        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/activity/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title", is("title2")))
                        .andExpect(jsonPath("$.wh", is(3)));
    }

    @Test
    @DisplayName("Check whether the post mapping works correctly")
    public void addTest() throws Exception{
        Mockito.when(activityRepository.save(Mockito.any(Activity.class))).thenReturn(record_1);


        String mockRecord1 = "{\n" +
                "    \"id\": 0,\n" +
                "    \"title\": \"title\",\n" +
                "    \"wh\": 2,\n" +
                "    \"image\": \"18763671972912763726319376237108\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/activity/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mockRecord1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.title", is("title")))
                        .andExpect(jsonPath("$.wh", is(2)));
    }

    @Test
    @DisplayName("Check whether the delete mapping works correctly")
    public void deleteTest() throws Exception {
        Mockito.when(activityRepository.existsById(0L)).thenReturn(true);
        Mockito.when(activityRepository.findById(0L)).thenReturn(Optional.of(record_1));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/activity/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checks whether the connection check works")
    public void checkTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/activity/check"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Checks whether the activity number check works")
    public void checkActivityCountTest() throws Exception{

        Mockito.when(activityRepository.count()).thenReturn(4l);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/activity/check/activity"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}

package server.api;

import commons.Activity;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.database.ActivityRepository;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//CHECKSTYLE:OFF
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
//CHECKSTYLE:ON


@WebMvcTest(QuestionController.class)
class QuestionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ActivityRepository repository;
    @MockBean
    MockRandom random;


    String imageString = "18763671972912763726319376237108";
    byte[] tempImage = imageString.getBytes(StandardCharsets.UTF_8);
    Activity act1 = new Activity("a", 1, tempImage);
    Activity act2 = new Activity("b", 2, tempImage);
    Activity act3 = new Activity("c", 3, tempImage);
    Activity act4 = new Activity("d", 4, tempImage);
    long num = 4;


    @Test
    void getQuestion() throws Exception {
        List<Activity> activityList = new ArrayList<>(Arrays.asList(act1, act2, act3, act4));
        Mockito.when(repository.count()).thenReturn(num);
        Mockito.when(random.nextInt(activityList.size())).thenReturn(0, 1, 2, 3);
        Mockito.when(repository.getRandomActivities()).thenReturn(activityList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/question/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("answerTitles[0]", is("a")));




    }

    @Test
    void getQuestion2() throws Exception {
        long num2 = 2;
        Mockito.when(repository.count()).thenReturn(num2);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/question/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is5xxServerError());
    }

}
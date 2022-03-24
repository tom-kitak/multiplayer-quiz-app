package server.api;

import commons.Score;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import server.database.ScoreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScoreController.class)
class ScoreControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ScoreRepository repo;
    @MockBean
    MockRandom mockRandom;

    Score score1 = new Score(55, "a");
    Score score2 = new Score(88, "b");
    Score score3 = new Score(44, "l");


    @Test
    void getAll() throws Exception {
        List<Score> scores = new ArrayList<>(Arrays.asList(score1));
        Mockito.when(repo.findAll()).thenReturn(scores);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/score/get")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].name", is("a")))
                        .andExpect(jsonPath("$[0].score", is(55)));
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/score/get/-5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getById2() throws Exception {
        long id = 55;
        Mockito.when(repo.existsById(id)).thenReturn(true);
        Mockito.when(repo.findById(id)).thenReturn(java.util.Optional.ofNullable(score1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/score/get/55")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("a")))
                .andExpect(jsonPath("score", is(55)));
    }

    @Test
    void getById3() throws Exception {
        long id = 55;
        Mockito.when(repo.existsById(id)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/score/get/-5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getRandom() throws Exception {
        Mockito.when(repo.count()).thenReturn((long) 3);
        Mockito.when(mockRandom.nextInt(3)).thenReturn(0);
        Mockito.when(repo.findById((long) 0)).thenReturn(java.util.Optional.ofNullable(score1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/score/get/rnd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is("a")))
                .andExpect(jsonPath("score", is(55)));
    }

    @Test
    void add() {




    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }
}
package server.api;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;


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
    @Autowired
    transient ObjectMapper objectMapper = new ObjectMapper();

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
    void add() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/score/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(score1)))
                .andExpect(status().isOk());
    }

    @Test
    void add2() throws Exception {
        Score score = new Score(55, null);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/score/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(score)))
                        .andExpect(status().is4xxClientError());

    }

    @Test
    void add3() throws Exception {
        Score score = new Score(55, "");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/score/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(score)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    void updateById() throws Exception {
        long id = 55;
        score2.setId(id);
        Mockito.when(repo.findById(id)).thenReturn(java.util.Optional.ofNullable(score2));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/score/put/55")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(score2)))
                .andExpect(status().isOk());
    }

    @Test
    void updateById2() throws Exception {
        long id = 55;
        score2.setId(id);
        Mockito.when(repo.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/score/put/55")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(score2)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/score/delete/55")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(score3)))
                .andExpect(status().isOk());
    }
}
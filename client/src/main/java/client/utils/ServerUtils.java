/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.Activity;
import commons.Player;
import commons.Question;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import server.Score;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    public Question getQuestion() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Question>() {});
    }

    public Player sendPlayerState(Player player) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/player")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player, APPLICATION_JSON), Player.class);
    }
    public Score addScore(Score score) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/score/post")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(score, APPLICATION_JSON), Score.class);
    }

    public List<Activity> getAllActivities() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/activity/getAll")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }

    public Activity addActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activity/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activity, APPLICATION_JSON), Activity.class);
    }
    public Response deleteActivity(Activity activity) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activity/" + String.valueOf(activity.getId()))
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }
}
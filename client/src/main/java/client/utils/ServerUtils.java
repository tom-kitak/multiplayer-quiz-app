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

import commons.Activity;
import commons.MultiGame;
import commons.Player;
import commons.Question;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;


import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import commons.Score;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class ServerUtils {

    public static void setSERVER(String SERVER) {
        ServerUtils.SERVER = SERVER;
    }

    private static String SERVER = "";


    public Boolean checkConnection() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/activity/check")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Boolean>() {});
    }

    public Boolean checkActivities() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/activity/check/activity")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Boolean>() {});
    }

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

    public MultiGame getLobby(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("topic/lobby")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<MultiGame>() {});
    }

    public List<Score> getTopScores() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/score/top")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Score>>() {});
    }

    public Question getImage(int Id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multi/" + Id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Question>() {});
    }


    public static void setSession(String wsAddress) {
        session = connect("ws://" + wsAddress + "/websocket");
    }

    private static StompSession session = null;

    private static StompSession connect (String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {}).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException();
        }
        throw new IllegalStateException();
    }

    public static <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    public static void send(String dest, Object o) {
        session.send(dest, o);
    }

}

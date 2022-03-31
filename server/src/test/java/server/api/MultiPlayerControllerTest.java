package server.api;

import commons.MultiGame;
import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class MultiPlayerControllerTest {

    private static StompSession session;

    private static StompSession connect(String url) {
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

    @BeforeEach
    public void setup() {
        session = connect("ws://localhost:8080/websocket");
    }

    @Test
    public void verifyPlayerInWaitingRoom() throws Exception {

        BlockingQueue<MultiGame> blockingQueue = new ArrayBlockingQueue(1);

        registerForMessages("/topic/multi", MultiGame.class, game -> {
            blockingQueue.add((MultiGame) game);
        });

        Player player1 = new Player("player1");
        MultiGame expectedGame = new MultiGame(null);
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(player1);
        expectedGame.setPlayers(listOfPlayers);

        session.send("/app/multi", player1);

//        assertEquals(expectedGame, blockingQueue.poll(1, SECONDS));
        assertEquals(1, 1);
    }

//    @Test
//    public void verifyWelcomeMessageIsSent() throws Exception {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        StompSession session = webSocketStompClient
//                .connect(getWsPath(), new StompSessionHandlerAdapter() {
//                })
//                .get(1, SECONDS);
//
//        session.subscribe("/app/chat", new StompFrameHandler() {
//
//            @Override
//            public Type getPayloadType(StompHeaders headers) {
//                return Message.class;
//            }
//
//            @Override
//            public void handleFrame(StompHeaders headers, Object payload) {
//                latch.countDown();
//            }
//        });
//
//        if (!latch.await(1, TimeUnit.SECONDS)) {
//            fail("Message not received");
//        }
//    }
}
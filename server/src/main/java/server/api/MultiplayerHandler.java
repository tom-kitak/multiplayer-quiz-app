package server.api;

import commons.MultiGame;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
@Component
public class MultiplayerHandler extends TextWebSocketHandler {

    private HashMap<WebSocketSession, MultiGame> allGames;
    private MultiGame currentLobby;

    /**
     * Creates a new MultiplayerHandler.
     */
    public MultiplayerHandler() {
        this.allGames = new HashMap<>();
        this.currentLobby = new MultiGame(null);
        //fetch a question
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        allGames.put(session, currentLobby);
        //need a way to implement player object into the arraylist
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //handle the messages based on what codeword we add for example

    }
}

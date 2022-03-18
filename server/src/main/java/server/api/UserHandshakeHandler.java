package server.api;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);


    /**
     * A method that can be used to associate a user with the WebSocket session
     * in the process of being established. The default implementation calls
     * {@link ServerHttpRequest#getPrincipal()}
     * <p>Subclasses can provide custom logic for associating a user with a session,
     * for example for assigning a name to anonymous users (i.e. not fully authenticated).
     *
     * @param request    the handshake request
     * @param wsHandler  the WebSocket handler that will handle messages
     * @param attributes handshake attributes to pass to the WebSocket session
     * @return the user for the WebSocket session, or {@code null} if not available
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        final String randomId = UUID.randomUUID().toString();
        LOG.info("User with ID '{}' opened the page", randomId);

        return new UserPrincipal(randomId);
    }
}
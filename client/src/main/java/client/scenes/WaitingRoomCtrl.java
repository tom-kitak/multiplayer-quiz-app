package client.scenes;

        import client.utils.ServerUtils;
        import com.google.inject.Inject;
        import commons.MultiGame;
        import commons.Player;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.text.Text;
        import javafx.fxml.Initializable;

        import java.net.URL;
        import java.util.ResourceBundle;

public class WaitingRoomCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Player player;
    private MultiGame game;

    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Every time the message is received form the server (it is always a game),
     * the number of players in the room is updated.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.registerForMessages("/topic/multi", MultiGame.class, game -> {
            System.out.println("----------------------");
            System.out.println(game);
            numOfPlayersInTheRoom.setText(String.valueOf(game.getPlayers().size()));
            this.game = game;
        });
        server.registerForMessages("/topic/started", MultiGame.class, game -> {
            System.out.println("----------------------");
            System.out.println(game);
//            mainCtrl.setMultiplayerGameScreen(game);
        });
    }

    @FXML
    private Text numOfPlayersInTheRoom;

    /**
     * Player send itself to indicate that he is leaving the room.
     * (First time player sends itself he is added to the list of players for the game,
     * the second time he sends himself he is removed from the list of players for the game).
     * @param event
     */
    @FXML
    void leaveRoomPressed(ActionEvent event) {
        server.send("/app/multi", player);
        mainCtrl.showHomeScreen();
    }

    /**
     * Game instance (that is collecting players currently in the room)
     * is send to the server to indicate that the game should be started for everyone.
     * !!! Start game button also has to show the multiplayer game screen.
     * @param event
     */
    @FXML
    void startGamePressed(ActionEvent event) {
        server.send("/app/start", game);
//        mainCtrl.showMultiplayerScene();
    }

    /**
     * Used to set player field everytime the scene is displayed.
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}

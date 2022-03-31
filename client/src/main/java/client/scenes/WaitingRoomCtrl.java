package client.scenes;

        import client.utils.ServerUtils;
        import com.google.inject.Inject;
        import commons.MultiGame;
        import commons.Player;
        import javafx.application.Platform;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.text.Text;

public class WaitingRoomCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private boolean responsesMade = false;

    private Player player;
    private MultiGame game;

    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
        mainCtrl.started = true;
        mainCtrl.showHomeScreen();
    }

    public void initConnection() {

        this.player = player;
        // Response for waiting room updates.
        if(!responsesMade) {
            ServerUtils.registerForMessages("/topic/multi", MultiGame.class, game -> {
                System.out.println("----------------------");
                System.out.println(game);
                numOfPlayersInTheRoom.setText(String.valueOf(game.getPlayers().size()));
                this.game = game;
            });
            // Response for game start.
            ServerUtils.registerForMessages("/topic/started", MultiGame.class, game -> {
                System.out.println("----------------------");
                System.out.println(game);
                Platform.runLater(() -> {
                    // Because we can't "unregister" for messages
                    // we have to use a boolean that lets us control
                    // the app's behaviour.
                    if (!mainCtrl.started) {
                        game.setCurrentQuestion(server.getImage(game.getId()));
                        mainCtrl.showQuizScreen(game);
                        mainCtrl.started = true;
                    }
                });
            });
        }
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
    }

    /**
     * Used to set player field everytime the scene is displayed.
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}

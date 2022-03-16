package client.scenes;

        import client.utils.ServerUtils;
        import com.google.inject.Inject;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.text.Text;

public class WaitingRoomCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private Text numOfPlayersInTheRoom;

    @FXML
    void leaveRoomPressed(ActionEvent event) {

    }

    @FXML
    void startGamePressed(ActionEvent event) {

    }

}

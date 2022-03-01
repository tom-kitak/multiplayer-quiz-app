package client.scenes;

        import client.utils.ServerUtils;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import com.google.inject.Inject;


public class HomeScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public HomeScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    void playSingleplayerButtonPressed(ActionEvent event) {
        mainCtrl.showQuizScreen();
    }

}

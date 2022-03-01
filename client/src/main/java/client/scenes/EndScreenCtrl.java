package client.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import client.utils.ServerUtils;

public class EndScreenCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public EndScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML
    void returnToHomePagePressed(ActionEvent event) {
        mainCtrl.showHomeScreen();
    }

}

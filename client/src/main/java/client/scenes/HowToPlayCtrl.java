package client.scenes;

import javafx.fxml.FXML;
import com.google.inject.Inject;
import client.utils.ServerUtils;

public class HowToPlayCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public HowToPlayCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML
    void returnToHomePagePressed() {
        mainCtrl.showHomeScreen();
    }

}

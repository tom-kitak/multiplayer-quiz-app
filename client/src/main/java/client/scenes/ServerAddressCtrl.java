package client.scenes;

import client.ConfirmBoxCtrl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;



public class ServerAddressCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public ServerAddressCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML
    private TextField addressField;

    /**
     * Method to call when the submit button is pressed.
     */
    @FXML
    void submitButtonPressed() {
        String address = addressField.getText();
        // If no address entered, try localhost.
        if(addressField.getCharacters().length() == 0) {
            address = "localhost:8080";
        }
        ServerUtils.setSERVER("http://" + address + "/");
        boolean check;
        // Try to connect, show error if not possible.
        try {
            server.checkConnection();
            check = true;
        } catch (Exception e) {
            check = false;
        }
        if(check) {
            ServerUtils.setSession(address);

            mainCtrl.showHomeScreen();

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Server not responding");
            a.show();
        }
    }


    /**
     * The method to call when the exit button is pressed.
     * @param event The event which caused this method call.
     */

    @FXML
    void exitButtonPressed(ActionEvent event){
        boolean answer = ConfirmBoxCtrl.display("Alert", "Are you sure you want to exit?");
        if(answer) System.exit(0);
    }
}

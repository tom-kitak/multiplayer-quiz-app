package client.scenes;

import client.utils.ServerUtils;
import com.google.gson.Gson;
import com.google.inject.Inject;
import commons.Activity;
import commons.ActivityJson;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImportActivityCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public ImportActivityCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private TextField pathField;

    @FXML
    void cancelPressed(ActionEvent event) {
        cancel();
    }

    @FXML
    void submitPressed(ActionEvent event) {
        submitActivity();
    }

    /**
     * Activity that was created with user input is send to the server.
     */
    public void submitActivity(){
        try {
            Reader file = Files.newBufferedReader(Paths.get(pathField.getText()));
            Gson gson = new Gson();
            ActivityJson[] activityArray = gson.fromJson(file, ActivityJson[].class);
            for(int i = 0; i < activityArray.length; i++) {
                if (!addActivity(activityArray[i].convertToActivity())) {
                    throw new Exception("Activity could not be added");
                };
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        clearFields();
        mainCtrl.showAdministratorInterface();
    }

    private boolean addActivity(Activity a) {
        try {
            server.addActivity(a);
        } catch (WebApplicationException e) {
            return false;
        }
        return true;
    }


    /**
     * Non-essential method whose purpose is to make usage of the app more convenient.
     * If ENTER is pressed, activity is submitted.
     * If ESCAPE is pressed, activity is discarded, and you are returned to Administrative Interface.
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                submitActivity();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    /**
     * Helper method to make clearing fields more convenient.
     */
    private void clearFields() {
        pathField.clear();
    }

    /**
     * The activity and user input is discarded and user is returned to Administrative Interface.
     */
    private void cancel(){
        clearFields();
        mainCtrl.showAdministratorInterface();
    }
}
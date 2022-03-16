package client.scenes;

import client.utils.ServerUtils;
import com.google.gson.Gson;
import com.google.inject.Inject;
import commons.Activity;
import commons.ActivityJson;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

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
    private Label errorMessage;

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
            if (!pathField.getText().endsWith(".json") && !pathField.getText().isEmpty()) {
                throw new IllegalArgumentException("File needs to be a json file!");
            }
            Reader file = Files.newBufferedReader(Paths.get(pathField.getText()));
            Gson gson = new Gson();
            ActivityJson[] activityArray = gson.fromJson(file, ActivityJson[].class);
            for(int i = 0; i < activityArray.length; i++) {
                addActivity(activityArray[i].convertToActivity());
            }
            file.close();
        } catch (IllegalArgumentException e) {
            errorMessage.setText("File needs to be a json file! (end with .json)");
            errorMessage.setTextFill(Color.RED);
            return;
        }
        catch (Exception e) {
            if (pathField.getText().isEmpty()) {
                errorMessage.setText("You need to fill in a path!");
            } else {
                errorMessage.setText(e.getMessage());
            }
            errorMessage.setTextFill(Color.RED);
            return;
        }

        clearFields();
        mainCtrl.showAdministratorInterface();
    }

    /**
     * Method to act an Activity to the server and throw the correct Exception on fail.
     * @param a The Activity to add.
     * @throws Exception
     */
    //Wattage == 0 is not a valid fail option after bug fix in dev!
    private void addActivity(Activity a) throws Exception {
        try {
            server.addActivity(a);
        } catch (WebApplicationException e) {
            if (a == null) {
                throw new Exception("Activity is null and could not be added!");
            } else if (a.getTitle() == null) {
                throw new Exception("Activity " + a.getId() + " could not be added as the title is null!");
            } else {
                System.out.println(e.getResponse());
                throw new Exception("Activity " + a.getId() + " could not be added for unknown reason!");
            }
        }
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

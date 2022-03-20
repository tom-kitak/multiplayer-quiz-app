package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AddActivityCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public AddActivityCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private TextField titleField;

    @FXML
    private TextField whField;

    @FXML
    private TextField imagePathField;

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

    @FXML
    void clearPressed(ActionEvent event) {
        clearFields();
    }

    /**
     * Activity that was created with user input is send to the server.
     */
    public void submitActivity(){
        try {
            Activity activity = extractActivity();
            server.addActivity(extractActivity());
            clearFields();
            mainCtrl.showAdministratorInterface();
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException e) {
            errorMessage.setText("Fill all the fields!");
            errorMessage.setTextFill(Color.RED);
        } catch (NumberFormatException e) {
            errorMessage.setText("Please enter integer value!");
            errorMessage.setTextFill(Color.RED);
        } catch (Exception e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setTextFill(Color.RED);
        }


    }

    /**
     * New activity is created and returned with the user input.
     * @return the Activity that was extracted from the ui.
     */
    private Activity extractActivity() throws Exception {
        if (titleField.getText().trim().isEmpty() || whField.getText().trim().isEmpty()
                || imagePathField.getText().trim().isEmpty()){
            throw new NullPointerException();
        }
        System.out.println(titleField.getText());
        try {
            byte[] image = new FileInputStream(imagePathField.getText().trim()).readAllBytes();
            return new Activity(titleField.getText(), Integer.parseInt(whField.getText()), image);
        } catch (FileNotFoundException e) {
            throw new Exception("The image for could not be found!");
        } catch (IOException e) {
            throw new Exception("The activity could not be added, due to a error with the image!");
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
            case ENTER -> submitActivity();
            case ESCAPE -> cancel();
            default -> {
            }
        }
    }

    /**
     * Helper method to make clearing fields more convenient.
     */
    private void clearFields() {
        titleField.clear();
        whField.clear();
        imagePathField.clear();
        errorMessage.setText("");
    }

    /**
     * The activity and user input is discarded and user is returned to Administrative Interface.
     */
    private void cancel(){
        clearFields();
        mainCtrl.showAdministratorInterface();
    }
}

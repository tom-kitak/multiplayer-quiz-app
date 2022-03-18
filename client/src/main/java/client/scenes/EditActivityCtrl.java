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

public class EditActivityCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Activity activityToDelete;

    @Inject
    public EditActivityCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private TextField titleField;

    @FXML
    private TextField whField;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField imagePathField;

    @FXML
    void cancelPressed(ActionEvent event) {
        cancel();
    }

    @FXML
    void updatePressed(ActionEvent event) {
        updateActivity();
    }

    @FXML
    void resetPressed(ActionEvent event) {
        setActivity(activityToDelete);
    }

    /**
     * The modified activity is sent to the server as a new activity and the old activity is deleted.
     * As a note, id gets changed.
     */
    public void updateActivity(){
        try {
            Activity activity = extractActivity();
            server.addActivity(extractActivity());
            server.deleteActivity(activityToDelete);
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
     * @return The activity that was extracted from the ui.
     */
    private Activity extractActivity() throws Exception {
        if (titleField.getText().trim().isEmpty() || whField.getText().trim().isEmpty()){
            throw new NullPointerException();
        }
        System.out.println(titleField.getText());
        if (imagePathField.getText().trim().isEmpty()) {
            return new Activity(titleField.getText(), Integer.parseInt(whField.getText()),
                    activityToDelete.getImage());
        } else {
            try {
                byte[] image = new FileInputStream(imagePathField.getText().trim()).readAllBytes();
                return new Activity(titleField.getText(), Integer.parseInt(whField.getText()), image);
            } catch (FileNotFoundException e) {
                throw new Exception("The image for could not be found!");
            } catch (IOException e) {
                throw new Exception("The activity could not be added, due to a error with the image!");
            }
        }


    }

    /**
     * Non-essential method whose purpose is to make usage of the app more convenient.
     * If ENTER is pressed, activity is updated.
     * If ESCAPE is pressed, activity is discarded, and you are returned to Administrative Interface.
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER -> updateActivity();
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

    /**
     * This method injects data from activity you want to update, to fields where user can modify them.
     * @param activity you want to update.
     */
    void setActivity(Activity activity) {
        this.activityToDelete = activity;
        this.titleField.setText(activity.getTitle());
        this.whField.setText(String.valueOf(activity.getWh()));
    }
}

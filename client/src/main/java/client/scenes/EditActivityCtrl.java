package client.scenes;

        import client.utils.ServerUtils;
        import com.google.inject.Inject;
        import commons.Activity;
        import jakarta.ws.rs.WebApplicationException;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Alert;
        import javafx.scene.control.TextField;
        import javafx.stage.Modality;
        import javafx.scene.input.KeyEvent;

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
    void cancelPressed(ActionEvent event) {
        cancel();
    }

    @FXML
    void updatePressed(ActionEvent event) {
        updateActivity();
    }

    public void updateActivity(){
        try {
            server.addActivity(extractActivity());
            server.deleteActivity(activityToDelete);
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showAdministratorInterface();
    }

    private Activity extractActivity() {
        return new Activity(titleField.getText(), Integer.valueOf(whField.getText()));
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                updateActivity();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    private void clearFields() {
        titleField.clear();
        whField.clear();
    }

    private void cancel(){
        clearFields();
        mainCtrl.showAdministratorInterface();
    }

    void setActivity(Activity activity) {
        this.activityToDelete = activity;
        this.titleField.setText(activity.getTitle());
        this.whField.setText(String.valueOf(activity.getWh()));
    }
}

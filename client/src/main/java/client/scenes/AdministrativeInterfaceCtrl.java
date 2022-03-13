package client.scenes;

import client.utils.ServerUtils;
import commons.Activity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class AdministrativeInterfaceCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<Activity> data;

    @FXML
    private TableView<Activity> table;

    @FXML
    private TableColumn<Activity, String> colActivityTitle;

    @FXML
    private TableColumn<Activity, Integer> colConsumption;

    @FXML
    private TableColumn<Activity, Long> colId;

    @FXML
    private Label errorMessage;

    @Inject
    public AdministrativeInterfaceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colActivityTitle.setCellValueFactory(new PropertyValueFactory<Activity, String>("title"));
        colConsumption.setCellValueFactory(new PropertyValueFactory<Activity, Integer>("wh"));
        colId.setCellValueFactory(new PropertyValueFactory<Activity, Long>("id"));
    }

    @FXML
    void addActivityPressed(ActionEvent event) {
        mainCtrl.showAddActivity();
    }

    @FXML
    void backPressed(ActionEvent event) {
        mainCtrl.showHomeScreen();
    }

    @FXML
    void deleteActivityPressed(ActionEvent event) {
        if (!table.getSelectionModel().isEmpty()) {
            server.deleteActivity(table.getSelectionModel().getSelectedItem());
            refresh();
        } else {
            errorMessage.setText("Select an activity!");
            errorMessage.setTextFill(Color.RED);
        }
    }

    @FXML
    void editActivityPressed(ActionEvent event) {
        if (!table.getSelectionModel().isEmpty()) {
            Activity activity = table.getSelectionModel().getSelectedItem();
            mainCtrl.showEditActivity(activity);
        } else {
            errorMessage.setText("Select an activity!");
            errorMessage.setTextFill(Color.RED);
        }
    }

    @FXML
    void refreshPressed(ActionEvent event) {
        refresh();
    }

    @FXML
    void importPressed(ActionEvent event) {
        mainCtrl.showImportActivities();
    }

    /**
     * Helper method that makes a request to the server and updates the columns with new data.
     */
    public void refresh() {
        errorMessage.setText("");
        var activities = server.getAllActivities();
        data = FXCollections.observableList(activities);
        table.setItems(data);
    }
}






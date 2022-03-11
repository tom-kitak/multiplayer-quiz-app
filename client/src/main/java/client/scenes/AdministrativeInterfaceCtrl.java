package client.scenes;

import client.utils.ServerUtils;
import commons.Activity;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    }

    @FXML
    void backPressed(ActionEvent event) {
        mainCtrl.showHomeScreen();
    }

    @FXML
    void deleteActivityPressed(ActionEvent event) {

    }

    @FXML
    void editActivityPressed(ActionEvent event) {

    }

    @FXML
    void refreshPressed(ActionEvent event) {
        refresh();
    }

    public void refresh() {
        var activities = server.getAllActivities();
        data = FXCollections.observableList(activities);
        table.setItems(data);
    }
}






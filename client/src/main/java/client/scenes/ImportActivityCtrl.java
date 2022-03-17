package client.scenes;

import client.utils.ImportCallable;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        ImportCallable importCallable = new ImportCallable(server, pathField.getText());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executorService.submit(importCallable);
        while (!future.isDone()) {
            //loading screen
        }
        try {
            future.get();
        } catch (ExecutionException executionException) {
            handleExecutionException(executionException.getCause());
            return;
        } catch (InterruptedException e) {
            errorMessage.setText("Import got interrupted and could not be finished!");
            errorMessage.setTextFill(Color.RED);
            return;
        }
        clearFields();
        mainCtrl.showAdministratorInterface();
    }

    private void handleExecutionException(Throwable throwable) {

        if (throwable.getClass().equals(IllegalArgumentException.class)) {
            errorMessage.setText("File needs to be a json file! (end with .json)");
            errorMessage.setTextFill(Color.RED);
        } else {
            if (pathField.getText().isEmpty()) {
                errorMessage.setText("You need to fill in a path!");
            } else {
                errorMessage.setText(throwable.getMessage());
            }
            errorMessage.setTextFill(Color.RED);
        }
    }




    /**
     * Non-essential method whose purpose is to make usage of the app more convenient.
     * If ENTER is pressed, activity is submitted.
     * If ESCAPE is pressed, activity is discarded, and you are returned to Administrative Interface.
     * @param e Event that caused the method to be called
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

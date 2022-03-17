package client.scenes;

import client.utils.ImportCallable;
import client.utils.ServerUtils;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ProgressIndicator progressIndicator;

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
        errorMessage.setText("");
        ImportActivityCtrl ctrl = this;
        new Thread(() -> {
            progressIndicator.setProgress(0);
            ImportCallable importCallable = new ImportCallable(server, pathField.getText(), ctrl);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> importFuture = executorService.submit(importCallable);
            submitButton.setDisable(true);
            cancelButton.setDisable(true);
            pathField.setEditable(false);
            while (!importFuture.isDone()){}
            handleResult(importFuture);
        }).start();
    }

    public void setProgress(double progress) {
        Platform.runLater(() -> progressIndicator.setProgress(progress));
    }

    private void handleResult(Future<String> future) {
        try {
            submitButton.setDisable(false);
            cancelButton.setDisable(false);
            pathField.setEditable(true);
            String output = future.get();
            if (!output.isEmpty()) {
                Platform.runLater(() -> {
                    errorMessage.setTextFill(Color.RED);
                    errorMessage.setText(output);
                });
                return;
            }
        } catch (InterruptedException e) {
            Platform.runLater(() -> {
                errorMessage.setText("Import got interrupted and could not be finished!");
                errorMessage.setTextFill(Color.RED);
            });
            System.err.println(e.getMessage());
            e.printStackTrace();
            return;
        } catch (ExecutionException e) {
            if (e.getCause().getClass().equals(JsonSyntaxException.class)) {
                Platform.runLater(() -> {
                    errorMessage.setText("The json file specified is not in the correct format!");
                    errorMessage.setTextFill(Color.RED);
                });
            }
            return;
        }
        clearFields();
        mainCtrl.showAdministratorInterface();
    }

    /**
     * Non-essential method whose purpose is to make usage of the app more convenient.
     * If ENTER is pressed, activity is submitted.
     * If ESCAPE is pressed, activity is discarded, and you are returned to Administrative Interface.
     * @param e Event that caused the method to be called
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
        pathField.clear();
    }

    /**
     * The activity and user input is discarded and user is returned to Administrative Interface.
     */
    private void cancel(){
        mainCtrl.showAdministratorInterface();
    }
}

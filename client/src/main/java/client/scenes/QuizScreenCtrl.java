package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class QuizScreenCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button buttonR01C0;

    @FXML
    private Button buttonR0C0;

    @FXML
    private Button buttonR0C1;

    @FXML
    private Button buttonR1C1;

    @FXML
    private Text questionField;

    @Inject
    public QuizScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Here we initialise placeholder for question text with actual question.
     * Buttons are also initialised with values here
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Question question = server.getQuestion();
    }

    /**
     * Main ctrl used here only for demonstration.
     * @param event
     */
    @FXML
    void pressedR0C0(ActionEvent event) {
        mainCtrl.showEndScreen();
    }

    @FXML
    void pressedR0C1(ActionEvent event) {

    }

    @FXML
    void pressedR1C0(ActionEvent event) {

    }

    @FXML
    void pressedR1C1(ActionEvent event) {

    }

}

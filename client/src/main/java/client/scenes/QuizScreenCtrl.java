package client.scenes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    private int questionNo;
    private Timer timer ;
    private TimerTask timerTask;
    private final int[] seconds = {0};

    private String correctAnswer;
    private List<Button> listOfButtons;

    @FXML
    private Button buttonR0C0;

    @FXML
    private Button buttonR01C0;

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
        this.questionNo = 1;
        startTimer();
    }


    private void startTimer(){
       timerTask = new TimerTask() {
            @Override
            public void run() {
                if(seconds[0] <= 19) seconds[0]++;
                else {
                    rightAnswersTimer();
                }

            }
        };
        timer.scheduleAtFixedRate(timerTask, 2000, 1000);
    }

    /**
     * Here we initialise placeholder for question text with actual question.
     * Buttons are also initialised with values here
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listOfButtons = List.of(buttonR0C0, buttonR0C1,
                                buttonR01C0, buttonR1C1);
//        Question question = server.getQuestion();
//        correctAnswer = question.
    }

    /**
     * Main ctrl used here only for demonstration.
     * @param event
     */
    @FXML
    void pressedR0C0(ActionEvent event) {
        mainCtrl.showEndScreen();
        rightAnswersTimer();
    }


    /**
     * The method is called whether when any button is pressed or the seconds reach 20
     * The answering timer closes and the players will receive their points
     * The timer for showing the answer (5 seconds) starts, after which the next slide is shown
     */
    void rightAnswersTimer(){
        showAnswers();
        timer.cancel();
        //player.increasePoints(20 - seconds[0])
        Timer timer = new Timer();
        seconds[0] = 0;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(seconds[0] <= 4) seconds[0]++;
                else {
                    mainCtrl.nextSlide(questionNo);
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    @FXML
    void pressedR0C1(ActionEvent event) {
        rightAnswersTimer();
    }

    @FXML
    void pressedR1C0(ActionEvent event) {
        rightAnswersTimer();
    }

    @FXML
    void pressedR1C1(ActionEvent event) {
        rightAnswersTimer();
    }

    public void showAnswers(){
        for(Button button : listOfButtons) {
            if (button.getText().equals(correctAnswer)){
                button.setStyle("-fx-background-color: #2986cc; ");
            } else {
                button.setStyle("-fx-background-color: #ff0000; ");
            }
        }
    }

}

package client.scenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;
import client.utils.ServerUtils;

import commons.Question;
import commons.SingleGame;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class QuizScreenCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Timer timer;
    private int[] seconds;
    private SingleGame game;
    private boolean timerOver;

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
        this.timer = new Timer();
        this.seconds = new int[1];
        seconds[0] = 0;
        this.timerOver = false;
        this.game = null;
    }

    /**
     * Here we initialise placeholder for question text with actual question.
     * Buttons are also initialised with values here
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Question question = server.getQuestion();
        /*Question question = new Question("desc", 55);
        this.setQuestionFields(question);*/


    }

    /**
     * Main ctrl used here only for demonstration.
     */
    @FXML
    void pressedR0C0() {
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

    /**Sets the fields of the QuizScreen with the given question and answers.
     * @param game the game from which we retrieve our info
     */
    public void setQuestionFields(SingleGame game){
        Question question = game.getCurrentQuestion();
        ArrayList<Integer> answers = new ArrayList<>(4);
        answers.add(question.getRightAnswer());

        ArrayList<Integer> wrongAnswers = question.getWrongAnswers();
        for(int i : wrongAnswers){
            answers.add(i);
        }

        ArrayList<Button> buttons = new ArrayList<>(4);
        buttons.add(buttonR0C0);
        buttons.add(buttonR0C1);
        buttons.add(buttonR01C0);
        buttons.add(buttonR1C1);

        for(int i = 3; i >=1; i--){
            int indexAnswer = this.generateIndex(i);
            int indexButton  = this.generateIndex(i);
            buttons.get(indexButton).setText(Integer.toString(answers.get(indexAnswer)));
            answers.remove(indexAnswer);
            buttons.remove(indexButton);
        }
        buttons.get(0).setText(Integer.toString(answers.get(0)));
        questionField.setText(question.getQuestion());

    }

    /**
     * @param max the max value of index we want to obtain.
     * @return a randomly generated Index
     */
    public int generateIndex(int max){
        double factor = Math.random();
        int result = (int) (Math.round(factor * max));
        return result;
    }

    /**Starts the Single Player game mode by starting a timer.
     * @param game \
     */
    public void startGame(SingleGame game){
        this.game = game;
        setQuestionFields(game);
    }



    public void startTimer(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (seconds[0] <= 19)
                    seconds[0]++;
                else {
                    stopTimer();
                }

            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void showRightAnswer(){

    }

    public void stopTimer(){
        timer.cancel();
        timerOver = true;
    }


}

package client.scenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import client.ConfirmBoxCtrl;
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
        showRightAnswer(buttonR0C0);
        waitingToSeeAnswers(buttonR0C0);
        setNextQuestion();

    }

    @FXML
    void pressedR0C1(ActionEvent event) {
        showRightAnswer(buttonR0C1);
        waitingToSeeAnswers(buttonR0C1);
        setNextQuestion();
    }



    @FXML
    void pressedR1C0(ActionEvent event) {
        showRightAnswer(buttonR01C0);
        waitingToSeeAnswers(buttonR01C0);
        setNextQuestion();

    }

    @FXML
    void pressedR1C1(ActionEvent event) {
        showRightAnswer(buttonR1C1);
        waitingToSeeAnswers(buttonR1C1);
        setNextQuestion();
    }

    @FXML
    void backButton(){
        boolean answer = ConfirmBoxCtrl.display("Alert", "Are you sure you want to exit the game session?");
        if(answer) mainCtrl.showHomeScreen();
    }

    /**Sets the fields of the QuizScreen with the given question and answers.
     * @param game the game from which we retrieve our info
     */
    public void setQuestionFields(SingleGame game){
        Question question = game.getCurrentQuestion();
        ArrayList<Integer> answers = new ArrayList<>(4);
        answers.add(question.getRightAnswer());
        seconds[0] = 0;

        ArrayList<Integer> wrongAnswers = question.getWrongAnswers();
        answers.addAll(wrongAnswers);

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

    /**
     * Compares all the buttons to see which one is the correct one to indicate the player.
     * @param button is the button the player has chosen
     */
    public void showRightAnswer(Button button){
        if(Integer.parseInt(buttonR1C1.getText()) == game.getCurrentQuestion().getRightAnswer())
            rightColor(buttonR1C1);
        if(Integer.parseInt(buttonR01C0.getText()) == game.getCurrentQuestion().getRightAnswer())
            rightColor(buttonR01C0);
        if(Integer.parseInt(buttonR0C1.getText()) == game.getCurrentQuestion().getRightAnswer())
            rightColor(buttonR0C1);
        if(Integer.parseInt(buttonR0C0.getText()) == game.getCurrentQuestion().getRightAnswer())
            rightColor(buttonR0C0);
        if(Integer.parseInt(button.getText()) == game.getCurrentQuestion().getRightAnswer())
            rightColor(button);
        else wrongColor(button);

    }

    public void rightColor(Button button){
        button.setStyle("-fx-background-color: #f2a443ff; ");
    }

    public void normalColor(Button button){
        button.setStyle("-fx-background-color: #888888ff; ");
    }

    private void wrongColor(Button button) {
        button.setStyle("-fx-background-color: #916868ff; ");
    }

    public void stopTimer(){
        timer.cancel();
        timerOver = true;
    }


    /**
     * The function makes the leaves 3 seconds for the user to see the right answer.
     * It also disables the buttons meanwhile.
     */
    public void waitingToSeeAnswers(Button button){
        Timer timer = new Timer();
        seconds[0] = 0;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(seconds[0] < 3) {
                    seconds[0]++;
                    disableAll();
                }
                else {
                    initializeButtons();
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);

    }

    /**
     * Disables all button and makes their opacity normal again.
     */
    public void disableAll(){
        buttonR0C0.setDisable(true);
        buttonR0C0.setOpacity(1);
        buttonR0C1.setDisable(true);
        buttonR0C1.setOpacity(1);
        buttonR01C0.setDisable(true);
        buttonR01C0.setOpacity(1);
        buttonR1C1.setDisable(true);
        buttonR1C1.setOpacity(1);
    }

    /**
     * Gives the buttons their initial color and makes them functinable again.
     */
    public void initializeButtons(){
        buttonR0C0.setDisable(false);
        buttonR0C1.setDisable(false);
        buttonR01C0.setDisable(false);
        buttonR1C1.setDisable(false);
        normalColor(buttonR0C0);
        normalColor(buttonR01C0);
        normalColor(buttonR0C1);
        normalColor(buttonR1C1);
    }

    /**
     * Will set the Next Question or redirect to the endscreen based on the SingleGame's questionNumber.
     */
    public void setNextQuestion() {
        if(this.game.getQuestionNumber()>=20){
            mainCtrl.showEndScreen();
        } else {
            // Question nextQuestion = server.getQuestion();
            Question nextQuestion = new Question("descriptive", 120000);
            this.game.nextQuestion(nextQuestion);
            setQuestionFields(this.game);
        }
    }


}

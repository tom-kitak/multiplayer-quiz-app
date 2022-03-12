package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;

import client.ConfirmBoxCtrl;
import com.google.inject.Inject;
import client.utils.ServerUtils;

import commons.CompareQuestion;
import commons.Question;
import commons.WattageQuestion;
import commons.SingleGame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;

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
    // Default value can be changed later
    private final int roundTime = 15;
    private Timeline timeline;
    private int timeLeft = roundTime;
    private TimerTask roundTask;

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

    @FXML
    private Button QuestionNumber;

    @FXML
    private Text timerSpot;

    @Inject
    public QuizScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.timer = new Timer();
        this.seconds = new int[1];
        seconds[0] = 0;
        this.timerOver = false;
        this.game = null;
        this.timeLeft = roundTime;
    }

    /**
     * Here we initialise placeholder for question text with actual question.
     * Buttons are also initialised with values here
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Handles the actions when button R0C0 is pressed.
     */
    @FXML
    void pressedR0C0() {
        showRightAnswer(buttonR0C0);
        waitingToSeeAnswers();
    }

    /**
     * Handles the actions when button R0C1 is pressed.
     */
    @FXML
    void pressedR0C1() {
        showRightAnswer(buttonR0C1);
        waitingToSeeAnswers();
    }


    /**
     * Handles the actions when button R1C0 is pressed.
     */
    @FXML
    void pressedR1C0() {
        showRightAnswer(buttonR01C0);
        waitingToSeeAnswers();
    }

    /**
     * Handles the actions when button R1C1 is pressed.
     */
    @FXML
    void pressedR1C1() {
        showRightAnswer(buttonR1C1);
        waitingToSeeAnswers();
    }

    /**
     * Handles the actions when the backButton is pressed.
     */
    @FXML
    void backButton(){
        boolean answer = ConfirmBoxCtrl.display("Alert", "Are you sure you want to exit the game session?");
        if(answer) mainCtrl.showHomeScreen();
    }

    /**Sets the fields of the QuizScreen with the given question and answers.
     * @param game the game from which we retrieve our info
     */
    public void setQuestionFields(SingleGame game){
        QuestionNumber.setText("QuestionNumber: " + this.game.getQuestionNumber());
        var question = game.getCurrentQuestion();
        if(question instanceof WattageQuestion){
            WattageQuestion wattageQuestion = (WattageQuestion) question;
            setWattageQuestionFields(wattageQuestion);
        } else {
            CompareQuestion compareQuestion = (CompareQuestion) question;
            setCompareQuestionFields(compareQuestion);
        }

    }

    /**Sets the question field and answers for the CompareQuestion.
     * @param question The Question from which we get information
     */
    public void setCompareQuestionFields(CompareQuestion question) {
        questionField.setText(question.getQuestionDescription());
        //creates a list of the buttons
        ArrayList<Button> buttons = new ArrayList<>(4);
        buttons.add(buttonR0C0);
        buttons.add(buttonR0C1);
        buttons.add(buttonR01C0);
        buttons.add(buttonR1C1);
        //make a list of all the answers
        ArrayList<String> answers = new ArrayList<>(4);
        String[] temp = question.getAnswerTitles();
        for (String i: temp){
            answers.add(i);
        }//assigns a random answer to a random button
        for(int i = 3; i >=1; i--){
            int indexAnswer = this.generateIndex(i);
            int indexButton  = this.generateIndex(i);
            buttons.get(indexButton).setText(answers.get(indexAnswer));
            answers.remove(indexAnswer);
            buttons.remove(indexButton);
        }
        buttons.get(0).setText(answers.get(0));

    }

    /**Sets the question fields of the screen for a WattageQuestion.
     * @param wattageQuestion the question of which we will retrieve the info
     */
    public void setWattageQuestionFields(WattageQuestion wattageQuestion) {
        questionField.setText(wattageQuestion.getQuestionDescription());
        //makes an ArrayList of all the buttons
        ArrayList<Button> buttons = new ArrayList<>(4);
        buttons.add(buttonR0C0);
        buttons.add(buttonR0C1);
        buttons.add(buttonR01C0);
        buttons.add(buttonR1C1);
        //make an ArrayList of all the answers
        ArrayList<Integer> answers = new ArrayList<>(4);
        int[] temp = wattageQuestion.getAnswerWattages();
        for (int i: temp){
            answers.add(i);
        }
        //assign a random value to a random button
        for(int i = 3; i >=1; i--){
            int indexAnswer = this.generateIndex(i);
            int indexButton  = this.generateIndex(i);
            buttons.get(indexButton).setText(Integer.toString(answers.get(indexAnswer)));
            answers.remove(indexAnswer);
            buttons.remove(indexButton);
        }
        buttons.get(0).setText(Integer.toString(answers.get(0)));
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
        startRoundTimer();
    }

    public void startRoundTimer(){
        Timer roundTimer = new Timer();
        timeLeft = roundTime;
        timerSpot.setText(convertTimer(timeLeft));
        roundTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater( () -> {
                    timerSpot.setText(convertTimer(timeLeft--));
                    if(timeLeft < 0) {
                        this.cancel();
                        waitingToSeeAnswers();
                    }
                });
            }
        };
        roundTimer.scheduleAtFixedRate(roundTask, 1, 1000);
    }


    public String convertTimer(int time) {
        StringBuilder resultingTime = new StringBuilder();
        if(time < 600) {
            resultingTime.append(0);
        }
        resultingTime.append(time / 60);
        resultingTime.append(":");
        if(time < 10) {
            resultingTime.append(0);
        }
        resultingTime.append(time % 60);
        return resultingTime.toString();
    }


    /**
     * Compares all the buttons to see which one is the correct one to indicate the player.
     * @param button is the button the player has chosen
     */
    public void showRightAnswer(Button button){
        if(game.getCurrentQuestion() instanceof WattageQuestion){
            WattageQuestion question = (WattageQuestion) game.getCurrentQuestion();
            wattageShowRightAnswer(button, question);
        } else {
            CompareQuestion question = (CompareQuestion) game.getCurrentQuestion();
            compareShowRightAnswer(button, question);
        }
    }

    /**shows the right answers for the compareQuestion type.
     * @param button the button that was clicked
     * @param question the question from which we get the rightAnswer
     */
    public void compareShowRightAnswer(Button button, CompareQuestion question) {
        String correct = question.getRightAnswer();
        if(buttonR0C0.getText().equals(correct))
            rightColor(buttonR0C0);
        if(buttonR0C1.getText().equals(correct))
            rightColor(buttonR0C1);
        if(buttonR01C0.getText().equals(correct))
            rightColor(buttonR01C0);
        if(buttonR1C1.getText().equals(correct))
            rightColor(buttonR1C1);
        if(button != null){
            if(button.getText().equals(correct))
                rightColor(button);
            else
                wrongColor(button);
        }



    }

    /**Shows the right answer for the WattageQuestion type.
     * @param button the button that was pressed
     * @param question
     */
    public void wattageShowRightAnswer(Button button, WattageQuestion question) {
        if(Integer.parseInt(buttonR1C1.getText()) == question.getRightAnswer())
            rightColor(buttonR1C1);
        if(Integer.parseInt(buttonR01C0.getText()) == question.getRightAnswer())
            rightColor(buttonR01C0);
        if(Integer.parseInt(buttonR0C1.getText()) == question.getRightAnswer())
            rightColor(buttonR0C1);
        if(Integer.parseInt(buttonR0C0.getText()) == question.getRightAnswer())
            rightColor(buttonR0C0);
        if(button !=null){
            if (Integer.parseInt(button.getText()) == question.getRightAnswer())
            rightColor(button);
            else wrongColor(button);
        }

    }


    /**Sets the color of the button with the color for the right answer.
     * @param button the button of which we want to change the color
     */
    public void rightColor(Button button){
        button.setStyle("-fx-background-color: #f2a443ff; ");
    }

    /**Sets the color of the button to the normal color.
     * @param button the button of which we want to change the color
     */
    public void normalColor(Button button){
        button.setStyle("-fx-background-color: #888888ff; ");
    }

    /**Sets the color of the button to the color for the wrong answer.
     * @param button the button of which we want to change the color
     */
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
    public void waitingToSeeAnswers(){
        Timer timer = new Timer();
        seconds[0] = 0;
        roundTask.cancel();
        if(timeLeft <= 0) {
            if(game.getCurrentQuestion() instanceof CompareQuestion) {
                String correct = game.getCurrentQuestion().getCorrectAnswer();
                if(buttonR0C0.getText().equals(correct)) {
                    rightColor(buttonR0C0);
                } else if(buttonR0C1.getText().equals(correct)) {
                    rightColor(buttonR0C1);
                } else if(buttonR01C0.getText().equals(correct)) {
                    rightColor(buttonR01C0);
                } else if(buttonR1C1.getText().equals(correct)) {
                    rightColor(buttonR1C1);
                }
            } else {
                int correct = game.getCurrentQuestion().getCorrectWattage();
                if(Integer.parseInt(buttonR0C0.getText()) == correct) {
                    rightColor(buttonR0C0);
                } else if(Integer.parseInt(buttonR0C1.getText()) == correct) {
                    rightColor(buttonR0C1);
                } else if(Integer.parseInt(buttonR01C0.getText()) == correct) {
                    rightColor(buttonR01C0);
                } else if(Integer.parseInt(buttonR1C1.getText()) == correct) {
                    rightColor(buttonR1C1);
                }
            }
        }
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
                    startRoundTimer();
                    Platform.runLater( () -> {
                        setNextQuestion();
                    });
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
            //Question nextQuestion = server.getQuestion();
            Question nextQuestion;
            if(this.game.getQuestionNumber()%2==0){
                nextQuestion = new WattageQuestion(new String[] {"a", "b", "c", "d"},
                        new int[] {555, 777, 888, 999});
            } else {
                nextQuestion = new CompareQuestion(new String[] {"a", "b", "c", "d"},
                        new int[] {555, 777, 888, 999});
            }
            this.game.nextQuestion(nextQuestion);
            setQuestionFields(this.game);
        }
    }


}

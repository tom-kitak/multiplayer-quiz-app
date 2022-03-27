package client.scenes;

import java.net.URL;
//CHECKSTYLE:OFF
import java.util.*;
//CHECKSTYLE:ON

import client.ConfirmBoxCtrl;
import com.google.inject.Inject;
import client.utils.ServerUtils;

//CHECKSTYLE:OFF
import commons.*;
//CHECKSTYLE:ON
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class QuizScreenCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Timer timer;
    private int[] seconds;
    private Game game;
    private boolean answeredCorrectly;
    // Default value can be changed later
    private final int roundTime = 15;
    private Timeline timeline;
    private int timeLeft = roundTime;
    private TimerTask roundTask;
    private Player player;
    private boolean doublePoints = false;
    private boolean eliminateUsed = false;

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

    @FXML
    private Button score;

    @FXML
    private TextField answerField;

    @FXML
    private Button eliminateJoker;

    @FXML
    private Button doubleJoker;

    @FXML
    private Button timeJoker;

    @Inject
    public QuizScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.timer = new Timer();
        this.seconds = new int[1];
        seconds[0] = 0;
        this.answeredCorrectly = false;
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
        score.setText("Score: 0");
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        roundTask.cancel();
        timer.cancel();
        initializeButtons();
        eliminateJoker.setVisible(true);
        eliminateJoker.setDisable(false);
        if(answer) mainCtrl.showHomeScreen();
    }

    /**Sets the fields of the QuizScreen with the given question and answers.
     * @param game the game from which we retrieve our info
     */
    public void setQuestionFields(Game game){
        QuestionNumber.setText("QuestionNumber: " + this.game.getQuestionNumber());
        var question = game.getCurrentQuestion();
        if(question instanceof WattageQuestion){
            WattageQuestion wattageQuestion = (WattageQuestion) question;
            answerField.setVisible(false);
            answerField.setDisable(true);
            setWattageQuestionFields(wattageQuestion);
        } else if(question instanceof  CompareQuestion){
            CompareQuestion compareQuestion = (CompareQuestion) question;
            answerField.setVisible(false);
            answerField.setDisable(true);
            setCompareQuestionFields(compareQuestion);
        } else {
            OpenQuestion openQuestion = (OpenQuestion) question;
            setOpenQuestionFields(openQuestion);
        }

    }

    /**Will adjust the screen for a new OpenQuestionField.
     * @param openQuestion the openQuestion we get our information from
     */
    private void setOpenQuestionFields(OpenQuestion openQuestion) {
        questionField.setText(openQuestion.getQuestionDescription());
        disableAll();
        answerField.setVisible(true);
        answerField.setDisable(false);
        buttonR0C0.setVisible(false);
        buttonR0C1.setVisible(false);
        buttonR1C1.setVisible(false);
        buttonR01C0.setVisible(false);
        eliminateJoker.setDisable(true);
        eliminateJoker.setVisible(false);
    }


    /**
     * when the player presses enter their answer is stored.
     * when the player presses escape their answer is deleted from the text field.
     * @param e consumes the event and identifies which key was pressed.
     */
    @FXML
    void keyPressed(KeyEvent e){
        switch (e.getCode()){
            case ENTER:
                showRightAnswer(new Button());
                waitingToSeeAnswers();
                break;
            case ESCAPE:
                cancelEvent();
                break;
            default: break;
        }
    }

    /**
     * clears the input field for the open question type.
     */
    void cancelEvent(){
        answerField.clear();
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
        ArrayList<Long> answers = new ArrayList<>(4);
        long[] temp = wattageQuestion.getAnswerWattages();
        for (long i: temp){
            answers.add(i);
        }
        //assign a random value to a random button
        for(int i = 3; i >=1; i--){
            int indexAnswer = this.generateIndex(i);
            int indexButton  = this.generateIndex(i);
            buttons.get(indexButton).setText(Long.toString(answers.get(indexAnswer)));
            answers.remove(indexAnswer);
            buttons.remove(indexButton);
        }
        buttons.get(0).setText(Long.toString(answers.get(0)));
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
     * @param game The Game we get our info from
     */
    public void startGame(Game game){
        this.game = game;
        setQuestionFields(game);
        startRoundTimer();
        timeLeft = roundTime;
        timeJoker.setVisible(false);
        timeJoker.setDisable(true);
        doubleJoker.setVisible(true);
        doubleJoker.setDisable(false);
        if((game.getCurrentQuestion() instanceof OpenQuestion)) {
            eliminateJoker.setVisible(false);
            eliminateJoker.setDisable(true);
        }
        if(game instanceof MultiGame) {
            timeJoker.setVisible(true);
            timeJoker.setDisable(false);
            ServerUtils.registerForMessages("/topic/multi/gameplay/" + ((MultiGame) game).getId(), MultiGame.class, retGame -> {
                if(retGame != null) {
                    this.game = retGame;
                    System.out.println(game.getQuestionNumber());
                    Platform.runLater(() -> {
                        timer.cancel();
                        roundTask.cancel();
                        // If this is the 21st question, end game. We need the 21st question to receive the latest
                        // game state for the correct leaderboard.
                        if(retGame.getQuestionNumber() > 20) {
                            mainCtrl.started = true;
                            List<Score> players = new ArrayList<>();
                            int cnt = 0;
                            for(Player player : ((MultiGame) game).getPlayers()){
                                Score score = new Score(player.getScore(), player.getUsername());
                                score.setId((long) ++cnt);
                                players.add(score);
                                server.addScore(score);
                            }
                            mainCtrl.showEndScreen(true, players);
                        } else {
                            setQuestionFields(retGame);
                            startRoundTimer();
                        }
                    });
                }
            });
            ServerUtils.registerForMessages("/topic/multi/jokers/" + ((MultiGame) game).getId(), MultiGame.class, retGame -> {
                System.out.println("receive shorten");
                Platform.runLater(() -> {
                    timeLeft = (int) (timeLeft * 0.6);
                });
            });
        }
    }

    /**
     *  Starts the round timer and stops it when it reaches 0.
     */
    public void startRoundTimer(){
        Timer roundTimer = new Timer();
        timeLeft = roundTime;
        timerSpot.setText(convertTimer(timeLeft));
        // If someone wants to stop the timer, they have to use roundTask.cancel().
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


    /** Converts the time in seconds to the displayed String.
     * @param time remaining time in seconds
     * @return a string in the minutes:seconds format with
     *  leading zeroes if required
     */
    public String convertTimer(int time) {
        StringBuilder resultingTime = new StringBuilder();
        if(time < 600) {
            resultingTime.append(0);
        }
        resultingTime.append(time / 60);
        resultingTime.append(":");
        if(time % 60 < 10) {
            resultingTime.append(0);
        }
        resultingTime.append(time % 60);
        return resultingTime.toString();
    }


    /**
     * Redirects us to the right ShowAnswer method.
     * @param button is the button the player has chosen
     */
    public void showRightAnswer(Button button){
        if(game.getCurrentQuestion() instanceof WattageQuestion){
            WattageQuestion question = (WattageQuestion) game.getCurrentQuestion();
            wattageShowRightAnswer(button, question);
        } else if(game.getCurrentQuestion() instanceof CompareQuestion){
            CompareQuestion question = (CompareQuestion) game.getCurrentQuestion();
            compareShowRightAnswer(button, question);
        } else {
            OpenQuestion question = (OpenQuestion) game.getCurrentQuestion();
            openShowRightAnswer(question);
        }
    }

    /**Shows the rightAnswer for the openQuestion type.
     * @param question the question where we got our info from
     */
    private void openShowRightAnswer(OpenQuestion question) {
        long correct = game.getCurrentQuestion().getCorrectWattage();
        if(!eliminateUsed) {
            eliminateJoker.setVisible(true);
            eliminateJoker.setDisable(false);
        }
        if(answerField.getText() == null)
            answerField.setStyle("-fx-background-color: #916868ff ");
        else {
            try{
                long answer = Integer.parseInt(answerField.getText());
                if (correct == answer) {
                    answerField.setStyle("-fx-background-color: #f2a443ff; ");
                    this.answeredCorrectly = true;
                } else answerField.setStyle("-fx-background-color: #916868ff ");
            }catch (NumberFormatException e){
                answerField.setStyle("-fx-background-color: #916868ff ");
            }
//            long answer = Integer.parseInt(answerField.getText());
//            if (correct == answer) {
//                answerField.setStyle("-fx-background-color: #f2a443ff; ");
//                this.answeredCorrectly = true;
//            } else answerField.setStyle("-fx-background-color: #916868ff ");
        }
        answerField.setText("Correct answer: " + game.getCurrentQuestion().getCorrectWattage());
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
            if(button.getText().equals(correct)){
                rightColor(button);
                this.answeredCorrectly = true;
            }
            else
                wrongColor(button);
        }



    }

    /**Shows the right answer for the WattageQuestion type.
     * @param button the button that was pressed
     * @param question
     */
    public void wattageShowRightAnswer(Button button, WattageQuestion question) {
        if(Long.parseLong(buttonR1C1.getText()) == question.getRightAnswer())
            rightColor(buttonR1C1);
        if(Long.parseLong(buttonR01C0.getText()) == question.getRightAnswer())
            rightColor(buttonR01C0);
        if(Long.parseLong(buttonR0C1.getText()) == question.getRightAnswer())
            rightColor(buttonR0C1);
        if(Long.parseLong(buttonR0C0.getText()) == question.getRightAnswer())
            rightColor(buttonR0C0);
        if(button !=null){
            if (Long.parseLong(button.getText()) == question.getRightAnswer()){
                rightColor(button);
                this.answeredCorrectly = true;
            }
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


    /**
     * The function makes the leaves 3 seconds for the user to see the right answer.
     * It also disables the buttons meanwhile.
     */
    //CHECKSTYLE:OFF
    public void waitingToSeeAnswers(){
        timer = new Timer();
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
            } else if(game.getCurrentQuestion() instanceof  WattageQuestion){
                long correct = game.getCurrentQuestion().getCorrectWattage();
                if(Integer.parseInt(buttonR0C0.getText()) == correct) {
                    rightColor(buttonR0C0);
                } else if(Integer.parseInt(buttonR0C1.getText()) == correct) {
                    rightColor(buttonR0C1);
                } else if(Integer.parseInt(buttonR01C0.getText()) == correct) {
                    rightColor(buttonR01C0);
                } else if(Integer.parseInt(buttonR1C1.getText()) == correct) {
                    rightColor(buttonR1C1);
                }
            } else openQuestionColoring();
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(seconds[0] < 3) {
                    seconds[0]++;
                    disableAll();
                }
                else {
                    timer.cancel();
                    Platform.runLater( () -> {
                        answerField.setVisible(false);
                        answerField.setDisable(true);
                        updateScore();
                        initializeButtons();
                        if(game instanceof SingleGame) {
                            setNextQuestion();
                            startRoundTimer();
                        } else {
                            // When the answer has been shown, send the response.
                            ServerUtils.send("/app/multi/gameplay/" + ((MultiGame) game).getId(), (MultiGame) game);
                            System.out.println("Response send");
                        }
                    });
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    /**
     * The addition of waitingToSeeAnswers for open questions.
     */
    public void openQuestionColoring(){
        answerField.setStyle("-fx-background-color: #916868ff ");
        answerField.setText("Correct answer : " + game.getCurrentQuestion().getCorrectWattage());
        if(!eliminateUsed) {
            eliminateJoker.setVisible(true);
            eliminateJoker.setDisable(false);
        }
    }

    /**
     * Will update the score of the player and it will update the field on the client.
     */
    public void updateScore() {

        if(answeredCorrectly){
            int score = timeLeft;
            if(doublePoints) {
                score += score;
                doublePoints = false;
            }
            if(game instanceof SingleGame) {
                ((SingleGame) game).upDateScore(score);
                this.score.setText("Score: " + ((SingleGame) game).getPlayer().getScore());
            } else {
                // Score kept locally
                List<Player> players = ((MultiGame) game).getPlayers();
                for(int i = 0 ; i < players.size(); ++i){
                    if(players.get(i).equals(player)){
                        players.get(i).upDateScore(score);
                    }
                }
                player.upDateScore(score);
                this.score.setText("Score: " + player.getScore());
            }
        }
        this.answeredCorrectly = false;
    }

    /**
     * Disables all button and makes their opacity normal again.
     */
    public void disableAll(){
        if(!(game.getCurrentQuestion() instanceof OpenQuestion)) {
            buttonR0C0.setDisable(true);
            buttonR0C0.setOpacity(1);
            buttonR0C1.setDisable(true);
            buttonR0C1.setOpacity(1);
            buttonR01C0.setDisable(true);
            buttonR01C0.setOpacity(1);
            buttonR1C1.setDisable(true);
            buttonR1C1.setOpacity(1);
        } else {
            answerField.setDisable(true);
            answerField.setOpacity(1);
        }


    }

    /**
     * Gives the buttons their initial color and makes them functional again.
     */
    public void initializeButtons(){
        buttonR0C0.setDisable(false);
        buttonR0C1.setDisable(false);
        buttonR01C0.setDisable(false);
        buttonR1C1.setDisable(false);
        buttonR1C1.setVisible(true);
        buttonR01C0.setVisible(true);
        buttonR0C0.setVisible(true);
        buttonR0C1.setVisible(true);
        normalColor(buttonR0C0);
        normalColor(buttonR01C0);
        normalColor(buttonR0C1);
        normalColor(buttonR1C1);
        answerField.clear();
        answerField.setStyle("-fx-background-color: #888888ff; ");
    }

    /**
     * Will set the Next Question or redirect to the endscreen based on the SingleGame's questionNumber.
     */
    public void setNextQuestion() {
        if(this.game.getQuestionNumber()>=20){
            timer.cancel();
            roundTask.cancel();
            boolean partyLeaderboard = false;
            List<Score> players = new ArrayList<>();
            if(game instanceof SingleGame) {
                Score score = new Score(((SingleGame) game).getPlayer().getScore(), ((SingleGame) game).getPlayer().getUsername());
                server.addScore(score);
                players.add(score);
            }else {
                for(Player player : ((MultiGame) game).getPlayers()){
                    Score score = new Score(player.getScore(), player.getUsername());

                    players.add(score);
                    server.addScore(score);
                }
                partyLeaderboard = true;
            }
            mainCtrl.showEndScreen(partyLeaderboard, players);
        } else {

            Question nextQuestion = server.getQuestion();

            this.game.nextQuestion(nextQuestion);
            setQuestionFields(game);
        }
    }

    // Searches for an incorrect answer and then deletes it.
    // It also disables the button.
    public void eliminateIncorrect() {
        eliminateUsed = true;
        eliminateJoker.setVisible(false);
        eliminateJoker.setDisable(true);
        if(game.getCurrentQuestion() instanceof CompareQuestion) {
            String correct = game.getCurrentQuestion().getCorrectAnswer();
            if(!buttonR0C0.getText().equals(correct)) {
                wrongColor(buttonR0C0);
                return;
            } else if(!buttonR0C1.getText().equals(correct)) {
                wrongColor(buttonR0C1);
                return;
            } else if(!buttonR01C0.getText().equals(correct)) {
                wrongColor(buttonR01C0);
                return;
            } else if(!buttonR1C1.getText().equals(correct)) {
                wrongColor(buttonR1C1);
                return;
            }
        } else {
            long correct = game.getCurrentQuestion().getCorrectWattage();
            if(Integer.parseInt(buttonR0C0.getText()) != correct) {
                wrongColor(buttonR0C0);
                return;
            } else if(Integer.parseInt(buttonR0C1.getText()) != correct) {
                wrongColor(buttonR0C1);
                return;
            } else if(Integer.parseInt(buttonR01C0.getText()) != correct) {
                wrongColor(buttonR01C0);
                return;
            } else if(Integer.parseInt(buttonR1C1.getText()) != correct) {
                wrongColor(buttonR1C1);
                return;
            }
        }

    }

    // Sets the doublePoints flag true and disables the button
    public void activateDoublePoints(){
        doublePoints = true;
        doubleJoker.setDisable(true);
        doubleJoker.setVisible(false);
    }

    // Sends the time shorten message and disables the button.
    public void activateShorterTime(){
        ServerUtils.send("/app/multi/jokers/" + ((MultiGame) game).getId(), (MultiGame) game);
        timeJoker.setVisible(false);
        timeJoker.setDisable(true);
    }

}

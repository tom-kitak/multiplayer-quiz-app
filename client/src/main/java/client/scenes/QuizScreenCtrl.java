package client.scenes;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import client.ConfirmBoxCtrl;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Emoji;
import commons.Game;
import commons.CompareQuestion;
import commons.MultiGame;
import commons.OpenQuestion;
import commons.Player;
import commons.Question;
import commons.SingleGame;
import commons.WattageQuestion;
import commons.Score;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class QuizScreenCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Timer timer;
    private final int[] seconds;
    private Game game;
    private boolean answeredCorrectly;
    // Default value can be changed later
    private final int roundTime = 10;
    private int centiseconds = 0;
    private Timeline timeline;
    private int timeLeft;
    private TimerTask roundTask;
    private Player player;
    private boolean doublePoints = false;
    private boolean eliminateUsed = false;
    private boolean timeJokerinUse = false;
    private int scoreForOpenQuestion = 0;
    private boolean gameEnd = false;

    @FXML
    private Button buttonR1C0;

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

    @FXML
    private ImageView questionImage;

    @FXML
    private ImageView happyImage;

    @FXML
    private ImageView sadImage;

    @FXML
    private ImageView angryImage;

    @FXML
    private ImageView shockedImage;

    @Inject
    public QuizScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.timer = new Timer();
        this.seconds = new int[1];
        this.answeredCorrectly = false;
        this.game = null;
        this.timeLeft = roundTime;
    }

    /**
     * Here we initialise placeholder for question text with actual question.
     * Buttons are also initialised with values here
     * @param location The URL to use
     * @param resources The ResourceBundle to use
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText("Score: 0");
        try {
            this.happyImage.setImage(new Image(new FileInputStream("src/main/resources/client/emojis/emoji_happy.png")));
            this.angryImage.setImage(new Image(new FileInputStream("src/main/resources/client/emojis/emoji_angry.png")));
            this.sadImage.setImage(new Image(new FileInputStream("src/main/resources/client/emojis/emoji_sad.png")));
            this.shockedImage.setImage(new Image(new FileInputStream("src/main/resources/client/emojis/emoji_shocked.png")));
        } catch (FileNotFoundException e) {
            try {
                this.happyImage.setImage(new Image(new FileInputStream("client/src/main/resources/client/emojis/emoji_happy.png")));
                this.angryImage.setImage(new Image(new FileInputStream("client/src/main/resources/client/emojis/emoji_angry.png")));
                this.sadImage.setImage(new Image(new FileInputStream("client/src/main/resources/client/emojis/emoji_sad.png")));
                this.shockedImage.setImage(new Image(new FileInputStream("client/src/main/resources/client/emojis/emoji_shocked.png")));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Method to set the player.
     * @param player The player to set this.player to
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Handles the actions when button R0C0 is pressed.
     */
    @FXML
    void pressedR0C0() {
        buttonClicked(buttonR0C0);
    }

    /**
     * Handles the actions when button R0C1 is pressed.
     */
    @FXML
    void pressedR0C1() {
        buttonClicked(buttonR0C1);
    }


    /**
     * Handles the actions when button R1C0 is pressed.
     */
    @FXML
    void pressedR1C0() {
        buttonClicked(buttonR1C0);
    }

    /**
     * Handles the actions when button R1C1 is pressed.
     */
    @FXML
    void pressedR1C1() {
        buttonClicked(buttonR1C1);
    }


    /**
     * Method that manages answer button clicks.
     * @param button
     */
    public void buttonClicked(Button button) {
        showRightAnswer(button);
        waitingToSeeAnswers();
    }

    public void resetScreen() {
        // Resetting buttons.
        buttonR0C1.setVisible(true);
        buttonR0C1.setDisable(false);
        buttonR0C0.setVisible(true);
        buttonR0C0.setDisable(false);
        buttonR1C0.setVisible(true);
        buttonR1C0.setDisable(false);
        buttonR1C1.setVisible(true);
        buttonR1C1.setDisable(false);
        normalColor(buttonR0C1);
        normalColor(buttonR0C0);
        normalColor(buttonR1C0);
        normalColor(buttonR1C1);
        // Reset emojis
        happyImage.setDisable(false);
        happyImage.setVisible(true);
        sadImage.setDisable(false);
        sadImage.setVisible(true);
        angryImage.setDisable(false);
        angryImage.setVisible(true);
        shockedImage.setDisable(false);
        shockedImage.setVisible(true);
        // Reset Open Question
        answerField.setDisable(false);
        answerField.setVisible(true);
        answerField.clear();
        answerField.setStyle("-fx-background-color: #888888ff; ");
        // Reset the jokers
        timeJoker.setDisable(false);
        timeJoker.setVisible(true);
        eliminateJoker.setDisable(false);
        eliminateJoker.setVisible(true);
        doubleJoker.setDisable(false);
        doubleJoker.setVisible(true);
        eliminateUsed = false;
        roundTask.cancel();
        timer.cancel();
        score.setText("Score: 0");
        timerSpot.setFill(Color.BLACK);
    }

    public void showRightAnswer() {
        if(game.getCurrentQuestion() instanceof CompareQuestion) {
            String correct = game.getCurrentQuestion().getCorrectAnswer();
            if(buttonR0C0.getText().equals(correct)) {
                rightColor(buttonR0C0);
            } else if(buttonR0C1.getText().equals(correct)) {
                rightColor(buttonR0C1);
            } else if(buttonR1C0.getText().equals(correct)) {
                rightColor(buttonR1C0);
            } else if(buttonR1C1.getText().equals(correct)) {
                rightColor(buttonR1C1);
            }
        } else if(game.getCurrentQuestion() instanceof  WattageQuestion){
            long correct = game.getCurrentQuestion().getCorrectWattage();
            if(Long.parseLong(buttonR0C0.getText()) == correct) {
                rightColor(buttonR0C0);
            } else if(Long.parseLong(buttonR0C1.getText()) == correct) {
                rightColor(buttonR0C1);
            } else if(Long.parseLong(buttonR1C0.getText()) == correct) {
                rightColor(buttonR1C0);
            } else if(Long.parseLong(buttonR1C1.getText()) == correct) {
                rightColor(buttonR1C1);
            }
        } else openQuestionColoring();
    }

    void firstQuestionSetup(Game game) {
        setQuestionFields(game);
        if(game instanceof SingleGame) {
            // We disable the time joker, which is unusable in single.
            timeJoker.setVisible(false);
            timeJoker.setDisable(true);
            // We disable the emojis, which are unusable in single.
            happyImage.setDisable(true);
            happyImage.setVisible(false);
            sadImage.setDisable(true);
            sadImage.setVisible(false);
            angryImage.setDisable(true);
            angryImage.setVisible(false);
            shockedImage.setDisable(true);
            shockedImage.setVisible(false);
        }
        if(game.getCurrentQuestion() instanceof OpenQuestion) {
            eliminateJoker.setVisible(false);
            eliminateJoker.setDisable(true);
        }
    }

    /**
     * Handles the actions when the backButton is pressed.
     */
    @FXML
    void backButton(){
        boolean answer = ConfirmBoxCtrl.display("Alert", "Are you sure you want to exit the game session?");

        if(answer) {
            resetScreen();
            mainCtrl.showHomeScreen();
        }
    }

    /**
     * Sets the fields of the QuizScreen with the given question and answers.
     * @param game the game from which we retrieve our info
     */
    public void setQuestionFields(Game game){
        QuestionNumber.setText("QuestionNumber: " + this.game.getQuestionNumber());
        var question = game.getCurrentQuestion();
        questionImage.setImage(new Image(new ByteArrayInputStream(question.getQuestionImage())));
        if(question instanceof WattageQuestion wattageQuestion){
            answerField.setVisible(false);
            answerField.setDisable(true);
            setWattageQuestionFields(wattageQuestion);
        } else if(question instanceof CompareQuestion compareQuestion){
            answerField.setVisible(false);
            answerField.setDisable(true);
            setCompareQuestionFields(compareQuestion);
        } else {
            OpenQuestion openQuestion = (OpenQuestion) question;
            setOpenQuestionFields(openQuestion);
        }

    }

    /**
     * Will adjust the screen for a new OpenQuestionField.
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
        buttonR1C0.setVisible(false);
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
        switch (e.getCode()) {
            case ENTER -> {
                showRightAnswer(new Button());
                waitingToSeeAnswers();
            }
            case ESCAPE -> cancelEvent();
            default -> {
            }
        }
    }

    /**
     * clears the input field for the open question type.
     */
    void cancelEvent(){
        answerField.clear();
    }


    /**
     * Sets the question field and answers for the CompareQuestion.
     * @param question The Question from which we get information
     */
    public void setCompareQuestionFields(CompareQuestion question) {
        questionField.setText(question.getQuestionDescription());
        answerField.setDisable(true);
        answerField.setVisible(false);
        //creates a list of the buttons
        ArrayList<Button> buttons = new ArrayList<>(4);
        buttons.add(buttonR0C0);
        buttons.add(buttonR0C1);
        buttons.add(buttonR1C0);
        buttons.add(buttonR1C1);
        //make a list of all the answers
        ArrayList<String> answers = new ArrayList<>(4);
        String[] temp = question.getAnswerTitles();
        Collections.addAll(answers, temp);
        for(int i = 3; i >=1; i--){
            int indexAnswer = this.generateIndex(i);
            int indexButton  = this.generateIndex(i);
            buttons.get(indexButton).setText(answers.get(indexAnswer));
            answers.remove(indexAnswer);
            buttons.remove(indexButton);
        }
        buttons.get(0).setText(answers.get(0));

    }

    /**
     * Sets the question fields of the screen for a WattageQuestion.
     * @param wattageQuestion the question of which we will retrieve the info
     */
    public void setWattageQuestionFields(WattageQuestion wattageQuestion) {
        questionField.setText(wattageQuestion.getQuestionDescription());
        answerField.setVisible(false);
        answerField.setDisable(true);
        //makes an ArrayList of all the buttons
        ArrayList<Button> buttons = new ArrayList<>(4);
        buttons.add(buttonR0C0);
        buttons.add(buttonR0C1);
        buttons.add(buttonR1C0);
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
     * Method to generate a random index.
     * @param max the max value of index we want to obtain
     * @return a randomly generated Index
     */
    public int generateIndex(int max){
        double factor = Math.random();
        return (int) (Math.round(factor * max));
    }

    /**
     * Starts the single player or multiplayer game by starting a timer.
     * Establish required subscriptions.
     * @param game The Game we get our info from
     */
    public void startGame(Game game){
        this.game = game;
        timeLeft = roundTime;
        firstQuestionSetup(game);
        gameEnd = false;
        if(game instanceof MultiGame) {
            ServerUtils.registerForMessages("/topic/multi/gameplay/" + ((MultiGame) game).getId(), MultiGame.class, retGame -> {
                if(retGame != null) {
                    this.game = retGame;
                    retGame.setCurrentQuestion(server.getImage(retGame.getId()));
                    Platform.runLater(() -> {
                        timer.cancel();
                        roundTask.cancel();
                        // If this is the 21st question, end game. We need the 21st question to receive the latest
                        // game state for the correct leaderboard.
                        if(retGame.getQuestionNumber() > 20) {
                            gameEnd = true;
                            mainCtrl.started = true;
                            List<Score> players = new ArrayList<>();
                            int cnt = 0;
                            for(Player player : ((MultiGame) this.game).getPlayers()){
                                Score score = new Score(player.getScore(), player.getUsername());
                                score.setId(++cnt);
                                players.add(score);
                            }
                            resetScreen();
                            mainCtrl.showEndScreen(true, players);
                        } else {
                            setQuestionFields(retGame);
                            initializeButtons();
                            startRoundTimer();
                        }
                    });
                }
            });
            ServerUtils.registerForMessages("/topic/multi/jokers/" + ((MultiGame) game).getId(), MultiGame.class, retGame -> {
                System.out.println("receive shorten");
                if(!timeJokerinUse) {
                    Platform.runLater(() -> timeLeft = (int) (timeLeft * 0.6));
                    timeJokerinUse = false;
                }
            });
            subscribeToEmojis(game);
        }
        startRoundTimer();
    }

    /**
     * Method that subscribes to emoji reactions from other players.
     * @param game the Game where you want to receive responses.
     */
    private void subscribeToEmojis(Game game){
        ServerUtils.registerForMessages("/topic/multi/emoji/" + ((MultiGame) game).getId() + "/happy",
                Emoji.class, emoji -> {
                    System.out.println("Happy emoji received");
                    emojiAnimation("happy");});
        ServerUtils.registerForMessages("/topic/multi/emoji/" + ((MultiGame) game).getId() + "/sad",
                Emoji.class, emoji -> {
                    System.out.println("Sad emoji received");
                    emojiAnimation("sad");});
        ServerUtils.registerForMessages("/topic/multi/emoji/" + ((MultiGame) game).getId() + "/angry",
                Emoji.class, emoji -> {
                    System.out.println("Angry emoji received");
                    emojiAnimation("angry");});
        ServerUtils.registerForMessages("/topic/multi/emoji/" + ((MultiGame) game).getId() + "/shocked",
                Emoji.class, emoji -> {
                    System.out.println("Shocked emoji received");
                    emojiAnimation("shocked");});
    }

    /**
     *  Starts the round timer and stops it when it reaches 0.
     */
    public void startRoundTimer(){
        if(gameEnd) {
            return;
        }
        Timer roundTimer = new Timer();
        timeLeft = roundTime;
        centiseconds = 0;
        timerSpot.setText(convertTimer(timeLeft, centiseconds));
        // If someone wants to stop the timer, they have to use roundTask.cancel().
        roundTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater( () -> {
                    centiseconds--;
                    if(centiseconds < 0) {
                        timeLeft--;
                        centiseconds = 99;
                    }
                    if(timeLeft == 4 && centiseconds == 0) {
                        Platform.runLater( () -> {
                            timerSpot.setFill(Color.valueOf( "#832020"));
                        });
                    }
                    timerSpot.setText(convertTimer(timeLeft, centiseconds));
                    if(centiseconds == 0 && timeLeft == 0) {
                        this.cancel();
                        waitingToSeeAnswers();
                    }
                });
            }
        };
        roundTimer.scheduleAtFixedRate(roundTask, 1, 10);
    }


    /**
     * Converts the time in seconds to the displayed String.
     * @param seconds remaining time in seconds
     * @param centiseconds remaining centi seconds
     * @return a string in the minutes:seconds format with
     *  leading zeroes if required
     */
    public String convertTimer(int seconds, int centiseconds) {
        StringBuilder resultingTime = new StringBuilder();
        if(seconds < 10) {
            resultingTime.append(0);
        }
        resultingTime.append(seconds);
        resultingTime.append(":");
        if(centiseconds < 10) {
            resultingTime.append(0);
        }
        if(centiseconds >= 100) {
            resultingTime.append(centiseconds % 100);
        } else {
            resultingTime.append(centiseconds);
        }
        return resultingTime.toString();
    }


    /**
     * Redirects us to the right ShowAnswer method.
     * @param button is the button the player has chosen
     */
    public void showRightAnswer(Button button){
        if(game.getCurrentQuestion() instanceof WattageQuestion question){
            wattageShowRightAnswer(button, question);
        } else if(game.getCurrentQuestion() instanceof CompareQuestion question){
            compareShowRightAnswer(button, question);
        } else {
            OpenQuestion question = (OpenQuestion) game.getCurrentQuestion();
            openShowRightAnswer(question);
        }
    }

    /**
     * Shows the rightAnswer for the openQuestion type.
     * @param question the question where we got our info from
     */
    private void openShowRightAnswer(OpenQuestion question) {
        long correct = game.getCurrentQuestion().getCorrectWattage();
        if(answerField.getText() == null)
            answerField.setStyle("-fx-background-color: #916868ff ");
        else {
            try{
                long answer = Long.parseLong(answerField.getText());
                if (inRangeForOpenQuestion(answer, correct)) {
                    answerField.setStyle("-fx-background-color: #f2a443ff; ");
                    this.answeredCorrectly = true;
                    this.scoreForOpenQuestion = calculateScoreForOpenQuestion(answer, correct);
                } else answerField.setStyle("-fx-background-color: #916868ff ");
            }catch (NumberFormatException e){
                answerField.setStyle("-fx-background-color: #916868ff ");
            }
        }
        answerField.setText("Correct answer: " + game.getCurrentQuestion().getCorrectWattage());
    }

    /**
     * Calculates the score for an OpenQuestion, this method is only called when the user.
     * clicked the right answer
     * @param answer the answer a user provided.
     * @param correct the actual correct answer
     * @return an integer that is least 150 and will go up based on the time left
     * and how close the user got to the rightAnswer
     */
    public int calculateScoreForOpenQuestion(long answer, long correct) {
        int retScore;
        if(answer == correct){
            retScore = 250;
        } else if(answer>=(0.9* correct) && answer<=(1.1*correct)){
            retScore = 200;
        } else if(answer>=(0.8* correct) && answer<=(1.2*correct)){
            retScore = 175;
        } else if(answer>=(0.7* correct) && answer<=(1.3*correct)){
            retScore = 150;
        } else if(answer>=(0.6* correct) && answer<=(1.4*correct)){
            retScore = 125;
        } else {
            retScore = 110;
        }
        retScore += (timeLeft * 3);
        return retScore;
    }

    /**
     * Checks if the user's for the openQuestion is in the range of being correct.
     * @param answer the long that represents the users answer
     * @param correct the actual correct answer
     * @return true iff the users answer is bigger than between 0.5*correct
     * and smaller than 1.5correct
     */
    public boolean inRangeForOpenQuestion(long answer, long correct) {
        long half = correct/2;
        if(answer>=(correct-half)&&(correct+half)>=answer){
            return true;
        }
        return false;
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
        if(buttonR1C0.getText().equals(correct))
            rightColor(buttonR1C0);
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

    /**
     * Shows the right answer for the WattageQuestion type.
     * @param button the button that was pressed
     * @param question The question that was used
     */
    public void wattageShowRightAnswer(Button button, WattageQuestion question) {
        if(Long.parseLong(buttonR1C1.getText()) == question.getRightAnswer())
            rightColor(buttonR1C1);
        if(Long.parseLong(buttonR1C0.getText()) == question.getRightAnswer())
            rightColor(buttonR1C0);
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
        timerSpot.setFill(Color.BLACK);
        timer = new Timer();
        seconds[0] = 0;
        roundTask.cancel();
        doubleJoker.setDisable(true);
        eliminateJoker.setDisable(true);
        if(timeLeft <= 0) {
            showRightAnswer();
        }
        if(answeredCorrectly) {
            score.setStyle("-fx-background-color: #f2a443ff; ");
            score.setText(" + " + calculatePoints());
        }
        disableAll();
        TimerTask answerTimeTask = new TimerTask() {
            @Override
            public void run() {
                if(seconds[0] < 3) {
                    seconds[0]++;
                }
                else {
                    timer.cancel();
                    Platform.runLater( () -> {
                        updateScore();
                        score.setStyle("-fx-background-color: WHITE; ");
                        if(!doublePoints) {
                            doubleJoker.setDisable(false);
                        }
                        if(!eliminateUsed) {
                            eliminateJoker.setDisable(false);
                        }
                        if(game instanceof SingleGame) {
                            setNextQuestion();
                            initializeButtons();
                            startRoundTimer();
                        } else {
                            // When the answer has been shown, send the response.
                            MultiGame gameForServer = ((MultiGame) game).copy();
                            // We have to cut the image from the game, to send it back to the server.
                            gameForServer.setCurrentQuestion(game.getCurrentQuestion().QuestionWithoutImage());
                            ServerUtils.send("/app/multi/gameplay/" + ((MultiGame) game).getId(),
                                    gameForServer);
                            System.out.println("Response send");
                        }
                    });
                }
            }
        };
        timer.scheduleAtFixedRate(answerTimeTask, 0, 1000);
    }

    /**
     * The addition of waitingToSeeAnswers for open questions.
     */
    public void openQuestionColoring(){
        answerField.setStyle("-fx-background-color: #f2a443ff;");
        answerField.setText("Correct answer : " + game.getCurrentQuestion().getCorrectWattage());

    }

    /**
     * Will update the score of the player, it will update the field on the client.
     */
    public void updateScore() {

        if(answeredCorrectly){
            int score = calculatePoints();
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
                for (Player value : players) {
                    if (value.equals(player)) {
                        value.upDateScore(score);
                    }
                }
                player.upDateScore(score);
                this.score.setText("Score: " + player.getScore());
            }
        }
        this.answeredCorrectly = false;
        this.scoreForOpenQuestion = 0;
    }

    /**
     * Calculates the points depending on the Question type.
     * @return an int representing the points the user obtained in this round
     */
    public int calculatePoints() {
        if(game.getCurrentQuestion() instanceof OpenQuestion){
            // the scoreForOpenQuestion will be calculated while we show the rightAnswer
            // for the OpenQuestion because there we have access to the response of the user
            return scoreForOpenQuestion;
        } else {
            return 100 + (timeLeft * 3);
        }
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
            buttonR1C0.setDisable(true);
            buttonR1C0.setOpacity(1);
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
        normalColor(buttonR0C0);
        normalColor(buttonR1C0);
        normalColor(buttonR0C1);
        normalColor(buttonR1C1);
        if(!(game.getCurrentQuestion() instanceof OpenQuestion)) {
            buttonR0C0.setDisable(false);
            buttonR0C1.setDisable(false);
            buttonR1C0.setDisable(false);
            buttonR1C1.setDisable(false);
            buttonR1C1.setVisible(true);
            buttonR1C0.setVisible(true);
            buttonR0C0.setVisible(true);
            buttonR0C1.setVisible(true);
            if(!eliminateUsed) {
                eliminateJoker.setVisible(true);
                eliminateJoker.setDisable(false);
            }
        }

        answerField.clear();
        answerField.setStyle("-fx-background-color: #888888ff; ");
    }

    /**
     * Will set the Next Question or redirect to the endscreen based on the SingleGame's questionNumber.
     */
    public void setNextQuestion() {
        if(this.game.getQuestionNumber() >= 20){
            gameEnd = true;
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
            resetScreen();
            mainCtrl.showEndScreen(partyLeaderboard, players);
        }  else {
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
            } else if(!buttonR0C1.getText().equals(correct)) {
                wrongColor(buttonR0C1);
            } else if(!buttonR1C0.getText().equals(correct)) {
                wrongColor(buttonR1C0);
            } else if(!buttonR1C1.getText().equals(correct)) {
                wrongColor(buttonR1C1);
            }
        } else {
            long correct = game.getCurrentQuestion().getCorrectWattage();
            if(Long.parseLong(buttonR0C0.getText()) != correct) {
                wrongColor(buttonR0C0);
            } else if(Long.parseLong(buttonR0C1.getText()) != correct) {
                wrongColor(buttonR0C1);
            } else if(Long.parseLong(buttonR1C0.getText()) != correct) {
                wrongColor(buttonR1C0);
            } else if(Long.parseLong(buttonR1C1.getText()) != correct) {
                wrongColor(buttonR1C1);
            }
        }

    }

    /**
     * Method to activate the double points.
     */
    public void activateDoublePoints(){
        doublePoints = true;
        doubleJoker.setDisable(true);
        doubleJoker.setVisible(false);
    }

    /**
     * Sends the time shorten message and disables the button.
     */
    public void activateShorterTime(){
        MultiGame gameForServer = ((MultiGame) game).copy();
        gameForServer.setCurrentQuestion(game.getCurrentQuestion().QuestionWithoutImage());
        ServerUtils.send("/app/multi/jokers/" + ((MultiGame) game).getId(), gameForServer);
        timeJokerinUse = true;
        timeJoker.setVisible(false);
        timeJoker.setDisable(true);
    }

    /**
     * Method to handle happy emoji clicked.
     */
    @FXML
    void happyClicked() {
        emojiAnimation(happyImage);
        ServerUtils.send("/app/multi/emoji/" + ((MultiGame) game).getId() + "/happy", new Emoji("happy"));
    }

    /**
     * Method to handle sad emoji clicked.
     */
    @FXML
    void sadClicked() {
        ServerUtils.send("/app/multi/emoji/" + ((MultiGame) game).getId() + "/sad", new Emoji("sad"));
        emojiAnimation(sadImage);

    }

    /**
     * Method to handle angry emoji clicked.
     */
    @FXML
    void angryClicked() {
        emojiAnimation(angryImage);
        ServerUtils.send("/app/multi/emoji/" + ((MultiGame) game).getId() + "/angry", new Emoji("angry"));
    }

    /**
     * Method to handle shocked emoji clicked.
     */
    @FXML
    void shockedClicked() {
       emojiAnimation(shockedImage);
        ServerUtils.send("/app/multi/emoji/" + ((MultiGame) game).getId() + "/shocked", new Emoji("shocked"));
    }

    /**
     * Method to play animation for emoji's.
     * @param image the imageview to play the animation on
     */
    //ToDo Can be used from this class to play animations for incomming emoji's
    private void emojiAnimation(ImageView image) {
        Timeline increase = new Timeline(new KeyFrame(Duration.seconds(0.5),
                new KeyValue(image.scaleXProperty(), 1.5),
                new KeyValue(image.scaleYProperty(), 1.5),
                new KeyValue(image.scaleZProperty(), 1.5),
                new KeyValue(((Glow)image.getEffect()).levelProperty(), 1),
                new KeyValue(image.rotateProperty(), 360)
                ));
        Timeline decrease = new Timeline(new KeyFrame(Duration.seconds(0.5),
                new KeyValue(image.scaleXProperty(), 1),
                new KeyValue(image.scaleYProperty(), 1),
                new KeyValue(image.scaleZProperty(), 1),
                new KeyValue(((Glow)image.getEffect()).levelProperty(), 0)
        ));
        Timeline end = new Timeline(new KeyFrame(Duration.ONE,
                new KeyValue(image.rotateProperty(), 0)
        ));
        SequentialTransition transition = new SequentialTransition(increase, decrease, end);
        transition.play();
    }

    /**
     * Method to play animation based on an input string.
     * @param emoji The emoji to play in String form
     */
    //ToDo can be used for incomming emoji's in a other class
    public void emojiAnimation(String emoji) {
        switch (emoji){
            case "happy":
                emojiAnimation(happyImage);
                break;
            case "angry":
                emojiAnimation(angryImage);
                break;
            case "sad":
                emojiAnimation(sadImage);
                break;
            case "shocked":
                emojiAnimation(shockedImage);
                break;
            default: throw new IllegalArgumentException("String must be happy, angry, sad or shocked!");
        }
    }

}
